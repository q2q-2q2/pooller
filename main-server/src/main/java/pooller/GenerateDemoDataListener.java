package pooller;

import pooller.model.pool.PoolMainInfo;
import pooller.model.pool.PoolQuestion;
import pooller.model.pool.QuestionVariant;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class GenerateDemoDataListener implements ServletContextListener {

    Logger LOG = Logger.getLogger("Demo initializer");

    // Public constructor is required by servlet spec
    public GenerateDemoDataListener() {
    }

    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------
    public void contextInitialized(ServletContextEvent sce) {
      /* This method is called when the servlet context is
         initialized(when the Web application is deployed). 
         You can initialize servlet context related data here.
      */
        EntityManagerFactory emf = (EntityManagerFactory) ServletContextAttributes.EMF.getValue(sce.getServletContext());
        if (emf != null) {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();

            LOG.info("Start initializing");

            PoolMainInfo pool = getPool("First initiated pool", "Description of first pool", 10);
            pool.getQuestions().forEach(q -> {
                q.getVariants().forEach(em::persist);
                em.persist(q);
            });
            em.persist(pool);

            LOG.info("First record persisted");

            for (int k = 0; k < 30; k++) {
                PoolMainInfo poolN = getPool("Numered pool " + k, "description of " + k, 10);
                poolN.getQuestions().forEach(q -> {
                    q.getVariants().forEach(em::persist);
                    em.persist(q);
                });
                em.persist(poolN);
            }

            LOG.info("30 records persisted");

            em.getTransaction().commit();
            em.close();
        }
    }

    private PoolMainInfo getPool(String title, String description, int questions) {
        PoolMainInfo poolMainInfo = new PoolMainInfo();
        poolMainInfo.setTitle(title);
        poolMainInfo.setDescription(description);

        poolMainInfo.setQuestions(generateQuestions(questions, title));

        return poolMainInfo;
    }

    private List<PoolQuestion> generateQuestions(int i, String poolTitle) {
        List<PoolQuestion> questions = new ArrayList<>();
        Random random = new Random();
        for (int x = 0; x < i; x++) {
            PoolQuestion poolQuestion = new PoolQuestion();
            String questionText = poolTitle + " question " + x;
            poolQuestion.setQuestionText(questionText);
            poolQuestion.setVariants(generateVariants(questionText, 8));
            poolQuestion.setQuestionType(random.nextInt() % 2 == 0 ? PoolQuestionType.MANY : PoolQuestionType.SINGLE);
            questions.add(poolQuestion);
        }
        return questions;
    }

    private List<QuestionVariant> generateVariants(String poolTitle, int i) {
        List<QuestionVariant> variants = new ArrayList<>();
        for (int e = 0; e < i; e++) {
            QuestionVariant variant = new QuestionVariant();
            variant.setVariantText("Variant " + e + " of " + poolTitle);
            variants.add(variant);
        }
        return variants;
    }

    public void contextDestroyed(ServletContextEvent sce) {
      /* This method is invoked when the Servlet Context 
         (the Web application) is undeployed or 
         Application Server shuts down.
      */
    }

}
