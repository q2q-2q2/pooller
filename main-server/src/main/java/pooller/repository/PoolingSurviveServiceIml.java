package pooller.repository;

import pooller.AbstractEntityRemoteServiceServlet;
import pooller.PoolingSurviveService;
import pooller.dto.pool.PoolMetaInfoDto;
import pooller.dto.uac.CabinetDto;
import pooller.model.pool.PoolMainInfo;
import pooller.model.uac.Invitation;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Email;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class PoolingSurviveServiceIml extends AbstractEntityRemoteServiceServlet implements PoolingSurviveService {
    @Override
    public CabinetDto invite(PoolMetaInfoDto pool, @Email String email) {
        if (pool == null || pool.getId() == null) {
            throw new Error("Pool is null");
        }
        EntityManager em = emf.createEntityManager();
        PoolMainInfo poolMainInfo = em.find(PoolMainInfo.class, pool.getId());
        if (poolMainInfo == null) {
            em.close();
            throw new Error("Pool not found " + pool.getId());
        }
        em.getTransaction().begin();
        Invitation invitation = new Invitation();
        invitation.setEmail(email);
        invitation.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        invitation.setPool(poolMainInfo);
        em.persist(invitation);
        try {
            MimeMessage message = new MimeMessage(session);
            InternetAddress internetAddress = new InternetAddress(email);
            message.setRecipients(Message.RecipientType.TO, new Address[]{internetAddress});
            message.setSubject("Greeting Response");
            HttpServletRequest request = getThreadLocalRequest();
            String url = request.getScheme() + "://" + request.getServerName()
                    + (request.getServerPort() == 80 ? "" : ":" + request.getServerPort())
                    + "/index.html?invitation=" + invitation.getId()
                    + "&pool=" + pool.getId();

            message.setText("You are invited to poolling " + pool.getTitle() + ". Go by url " + url);
            message.saveChanges();
            Transport smtp = session.getTransport("smtp");
            smtp.connect();
            smtp.sendMessage(message, new Address[]{internetAddress});
            em.getTransaction().commit();
        } catch (MessagingException e) {
            em.getTransaction().rollback();
            log("send message", e);
            throw new Error("send message", e);
        }
        em.close();
        CabinetDto cabinetDto = new CabinetDto();
        cabinetDto.setEmail(email);
        cabinetDto.setId(String.valueOf(invitation.getId()));
        return cabinetDto;
    }
}
