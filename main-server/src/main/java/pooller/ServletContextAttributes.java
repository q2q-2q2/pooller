package pooller;

import javax.mail.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContext;
import java.util.concurrent.Executor;
import java.util.logging.Logger;

public enum ServletContextAttributes {
    MAIL_SESSION("mail-session", Session.class, "java:comp/env/mail/MailSession"),
    MAILS_THREAD_POOL("mails-thread-pool", Executor.class, "java:comp/env/thread/pool/mailsender"),
    EMF("emf", EntityManagerFactory.class),
    ;
    private final String name;
    private final Class type;
    private final String jndi;
    ServletContextAttributes(String name, Class type) {
        this.name = name;
        this.type = type;
        this.jndi = null;
    }

    ServletContextAttributes(String name, Class type, String jndi) {
        this.name = name;
        this.type = type;
        this.jndi = jndi;
    }

    public boolean setup(ServletContext ctx, Object value) {
        if (ctx == null) {
            return false;
        }
        if (type.isInstance(value)) {
            ctx.setAttribute(name, value);
        }
        return false;
    }

    public Object getValue(ServletContext ctx) {
        if (ctx == null) {
            return null;
        }
        return ctx.getAttribute(name);
    }

    static Logger LOG = Logger.getLogger("ServletContextAttributes");
    static int setupFromJNDI(ServletContext ctx, InitialContext initialContext) {
        int successCount = 0;
        if (ctx != null && initialContext != null) {
            for (ServletContextAttributes attr : values()) {
                if (attr.jndi != null && attr.name != null) {
                    try {
                        ctx.setAttribute(attr.name, initialContext.lookup(attr.jndi));
                        successCount++;
                    } catch (NamingException e) {
                        LOG.throwing("ServletContextAttributes", "setupFromJNDI", e);
                    }
                }
            }
        }
        return successCount;
    }

    public Object getFromJNDI(InitialContext initialContext) {
        if (initialContext != null) {
            if (jndi != null) {
                try {
                    return initialContext.lookup(jndi);
                } catch (NamingException e) {
                    LOG.throwing("ServletContextAttributes", "getFromJNDI", e);
                }
            }
        }
        return null;
    }
}
