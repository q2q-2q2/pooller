package pooller;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import javax.mail.Session;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Objects;
import java.util.concurrent.Executor;

public abstract class AbstractEntityRemoteServiceServlet extends RemoteServiceServlet {
    protected EntityManagerFactory emf;
    protected Session session;
    protected Executor executor;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        log("Init entity manager factory");
        ServletContext servletContext = config.getServletContext();
        emf = (EntityManagerFactory) ServletContextAttributes.EMF.getValue(servletContext);
        session = (Session) ServletContextAttributes.MAIL_SESSION.getValue(servletContext);
        executor = (Executor) ServletContextAttributes.MAILS_THREAD_POOL.getValue(servletContext);
        Objects.requireNonNull(emf, "Emf must be not null");
        Objects.requireNonNull(session, "MailSession must be not null");
        Objects.requireNonNull(executor, "ThreadPool must be not null");
    }

    @Override
    public void destroy() {
        if (emf != null && emf.isOpen()) {
            log("Close entity manager factory");
            emf.close();
        }
        super.destroy();
    }
}
