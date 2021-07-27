package pooller;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import java.util.HashMap;
import java.util.logging.Logger;

public class InitializeAppListener implements ServletContextListener {

    Logger LOG = Logger.getLogger("Demo initializer");

    public InitializeAppListener() {
    }

    public void contextInitialized(ServletContextEvent sce) {
      /* This method is called when the servlet context is
         initialized(when the Web application is deployed). 
         You can initialize servlet context related data here.
      */
        ServletContext servletContext = sce.getServletContext();
        try {
            InitialContext initialContext = new InitialContext();
            int count = ServletContextAttributes.setupFromJNDI(servletContext, initialContext);
            if (count == 0) {
                LOG.warning("No resources for application");
                return;
            }
        } catch (NamingException | ClassCastException e) {
            LOG.throwing(this.getClass().getName(), "initial context", e);
            return;
        }

        HashMap<String, String> parameters = new HashMap<>();
        if ("true".equals(servletContext.getInitParameter("dropDatabase"))) {
            parameters.put("hibernate.hbm2ddl.auto", "create");
        }
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("org.hibernate.tutorial.jpa", parameters);
        ServletContextAttributes.EMF.setup(servletContext, emf);

    }

    public void contextDestroyed(ServletContextEvent sce) {
      /* This method is invoked when the Servlet Context 
         (the Web application) is undeployed or 
         Application Server shuts down.
      */
    }

    // -------------------------------------------------------
    // HttpSessionListener implementation
    // -------------------------------------------------------
    public void sessionCreated(HttpSessionEvent se) {
        /* Session is created. */
    }

    public void sessionDestroyed(HttpSessionEvent se) {
        /* Session is destroyed. */
    }

    // -------------------------------------------------------
    // HttpSessionAttributeListener implementation
    // -------------------------------------------------------

    public void attributeAdded(HttpSessionBindingEvent sbe) {
      /* This method is called when an attribute 
         is added to a session.
      */
    }

    public void attributeRemoved(HttpSessionBindingEvent sbe) {
      /* This method is called when an attribute
         is removed from a session.
      */
    }

    public void attributeReplaced(HttpSessionBindingEvent sbe) {
      /* This method is invoked when an attribute
         is replaced in a session.
      */
    }
}
