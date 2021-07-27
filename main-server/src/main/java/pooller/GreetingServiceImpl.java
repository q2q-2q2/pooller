package pooller;

import pooller.model.actors.Survivor;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends AbstractEntityRemoteServiceServlet implements GreetingService {

	InitialContext initialContext;
	Session session;
	Executor executor;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try {
			initialContext = new InitialContext();
			session = (Session) initialContext.lookup("java:comp/env/mail/GMailSession");
			executor = (Executor) initialContext.lookup("java:comp/env/thread/pool/mailsender");
		} catch (NamingException e) {
			log("initial context", e);
		}
	}

	public GreetingResponse greetServer(String input) throws IllegalArgumentException {
		// Verify that the input is valid.
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException(
					"Name must be at least 4 characters long");
		}

		GreetingResponse response = new GreetingResponse();

		response.setServerInfo(getServletContext().getServerInfo());
		response.setUserAgent(getThreadLocalRequest().getHeader("User-Agent"));

		response.setGreeting("Hello, " + input + "!");

		try {
			EntityManager entityManager = emf.createEntityManager();
			entityManager.getTransaction().begin();
			entityManager.persist(new Survivor());
			entityManager.getTransaction().commit();
			List<Survivor> list = entityManager.createQuery("select s from Survivor s", Survivor.class).getResultList();
			response.setGreeting(response.getGreeting() + " count of survivors " + list.size());
			entityManager.close();
		} catch (Throwable t) {
			Logger.getLogger("").log(Level.SEVERE, "oops", t);
		}

		if (executor != null && session != null) {
			executor.execute(() -> {
				try {
					MimeMessage message = new MimeMessage(session);
					InternetAddress internetAddress = new InternetAddress("wstarcev-ga@yandex.ru");
					message.setRecipients(Message.RecipientType.TO, new Address[]{ internetAddress });
					message.setSubject("Greeting Response");
					message.setText(response.getGreeting());
					message.saveChanges();
					Transport smtp = session.getTransport("smtp");
					smtp.connect();
					smtp.sendMessage(message, new Address[]{ internetAddress });
				} catch (ClassCastException | MessagingException e) {
					log("sending email", e);
				}
			});
		}

		return response;
	}

}
