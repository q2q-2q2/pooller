package pooller.repository;


import pooller.AbstractEntityRemoteServiceServlet;
import pooller.dto.PageDto;
import pooller.dto.pool.PoolMetaInfoDto;
import pooller.dto.pool.PoolQuestionDto;
import pooller.dto.pool.QuestionVariantDto;
import pooller.model.adapters.PoolMetaInfoAdapter;
import pooller.model.adapters.PoolQuestionAdapter;
import pooller.model.adapters.QuestionVariantAdapter;
import pooller.model.pool.PoolMainInfo;
import pooller.model.pool.PoolQuestion;
import pooller.model.pool.QuestionVariant;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class PoolRepoServiceImpl extends AbstractEntityRemoteServiceServlet implements PoolingRepositoryService {
    @Override
    public PoolMetaInfoDto savePool(PoolMetaInfoDto poolMetaInfoDto) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        PoolMainInfo poolMainInfo = PoolMetaInfoAdapter.I.fromDto(poolMetaInfoDto);
        em.persist(poolMainInfo);
        em.getTransaction().commit();
        em.close();
        return PoolMetaInfoAdapter.I.toDto(poolMainInfo);
    }

    @Override
    public List<PoolMetaInfoDto> listPools() {
        EntityManager em = emf.createEntityManager();
        List<PoolMetaInfoDto> list = em.createQuery("select p from PoolMainInfo p", PoolMainInfo.class)
                .setMaxResults(20)
                .getResultStream()
                .map(PoolMetaInfoAdapter.I::toDto)
                .collect(Collectors.toList());
        em.close();
        return list;
    }

    @Override
    @RolesAllowed({"maker"})
    public PageDto<PoolMetaInfoDto> getPage(long start, int size) {
        EntityManager em = emf.createEntityManager();
        Long count = em.createQuery("select count(p.id) from PoolMainInfo p", Long.class).getSingleResult();
        em.close();
        em = emf.createEntityManager();
        List<PoolMetaInfoDto> list = em.createQuery("select p from PoolMainInfo p", PoolMainInfo.class)
                .setMaxResults(size)
                .setFirstResult((int) start).getResultList().stream()
                .map(PoolMetaInfoAdapter.I::toDto)
                .collect(Collectors.toList());
        PageDto<PoolMetaInfoDto> page = new PageDto<>();
        page.setData(list);
        page.setStart(start);
        page.setSize(size);
        page.setCount(count);
        em.close();
        return page;
    }

    @Override
    public PoolMetaInfoDto updatePool(PoolMetaInfoDto poolMetaInfoDto) {
        if (poolMetaInfoDto.getId() == null) {
            return poolMetaInfoDto;
        }
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        PoolMainInfo poolMainInfo = em.find(PoolMainInfo.class, poolMetaInfoDto.getId());
        poolMainInfo.setDescription(poolMetaInfoDto.getDescription());
        poolMainInfo.setTitle(poolMetaInfoDto.getTitle());
        poolMainInfo.setUpdated(new Date());
        em.merge(poolMainInfo);
        em.getTransaction().commit();
        em.close();
        return PoolMetaInfoAdapter.I.toDto(poolMainInfo);
    }

    @Override
    public boolean addQuestion(Long poolId, PoolQuestionDto questionDto) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        PoolMainInfo poolMainInfo = em.find(PoolMainInfo.class, poolId);
        if (poolMainInfo.getQuestions() == null) {
            poolMainInfo.setQuestions(new ArrayList<>());
        }
        PoolQuestion poolQuestion = PoolQuestionAdapter.I.fromDto(questionDto);
        poolQuestion.setOrder(poolMainInfo.getQuestions().size());
        em.persist(poolQuestion);
        poolMainInfo.getQuestions().add(poolQuestion);
        em.merge(poolMainInfo);
        em.getTransaction().commit();
        em.close();
        return true;
    }

    @Override
    public boolean addVariant(Long questionId, QuestionVariantDto questionVariantDto) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        PoolQuestion poolQuestion = em.find(PoolQuestion.class, questionId);
        if (poolQuestion.getVariants() == null) {
            poolQuestion.setVariants(new ArrayList<>());
        }
        QuestionVariant questionVariant = QuestionVariantAdapter.I.fromDto(questionVariantDto);
        em.persist(questionVariant);
        poolQuestion.getVariants().add(questionVariant);
        em.merge(poolQuestion);
        em.getTransaction().commit();
        em.close();
        return true;
    }

    @Override
    public boolean removeQuestion(Long questionId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.find(PoolQuestion.class, questionId));
        em.getTransaction().commit();
        em.close();
        return true;
    }

    @Override
    public boolean removeVariant(Long variantId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.find(QuestionVariant.class, variantId));
        em.getTransaction().commit();
        em.close();
        return true;
    }

    @Override
    public boolean updateQuestion(PoolQuestionDto poolQuestionDto) {
        if (poolQuestionDto.getId() == null
                && poolQuestionDto.getQuestionText() == null
                && poolQuestionDto.getOrder() == null
        ) {
            return true;
        }
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        PoolQuestion poolQuestion = em.find(PoolQuestion.class, poolQuestionDto.getId());
        if (poolQuestion != null) {
            poolQuestion.setQuestionText(poolQuestionDto.getQuestionText());
            poolQuestion.setOrder(poolQuestionDto.getOrder());
            em.merge(poolQuestion);
        }
        em.getTransaction().commit();
        em.close();
        return true;
    }

    @Override
    public boolean updateVariant(QuestionVariantDto questionVariantDto) {
        if (questionVariantDto.getId() == null) {
            return true;
        }
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        QuestionVariant questionVariant = em.find(QuestionVariant.class, questionVariantDto.getId());
        if (questionVariant != null) {
            questionVariant.setVariantText(questionVariant.getVariantText());
            em.merge(questionVariant);
        }
        em.getTransaction().commit();
        em.close();
        return true;
    }

    @Override
    public List<PoolQuestionDto> getQuestions(Long poolId) {
        EntityManager em = emf.createEntityManager();
        PoolMainInfo poolMainInfo = em.find(PoolMainInfo.class, poolId);
        if (poolMainInfo.getQuestions() == null) {
            return new ArrayList<>();
        }
        List<PoolQuestionDto> list = poolMainInfo.getQuestions().stream()
                .map(PoolQuestionAdapter.I::toDto)
                .collect(Collectors.toList());
        em.close();
        return list;
    }

    @Override
    public PoolMetaInfoDto getPool(Long poolId) {
        EntityManager em = emf.createEntityManager();
        PoolMainInfo poolMainInfo = em.find(PoolMainInfo.class, poolId);
        PoolMetaInfoDto poolMetaInfoDto = PoolMetaInfoAdapter.I.toDto(poolMainInfo);
        em.close();
        return poolMetaInfoDto;
    }

    @Override
    public PoolQuestionDto getQuestion(Long questionId) {
        EntityManager em = emf.createEntityManager();
        PoolQuestionDto poolQuestionDto = PoolQuestionAdapter.I.toDto(em.find(PoolQuestion.class, questionId));
        em.close();
        return poolQuestionDto;
    }
}
