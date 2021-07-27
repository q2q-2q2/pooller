package pooller.repository;

import pooller.AbstractEntityRemoteServiceServlet;
import pooller.dto.PageDto;
import pooller.dto.pool.QuestionVariantDto;
import pooller.dto.pool.SurviveDto;
import pooller.dto.pool.SurviveVariantDto;
import pooller.model.adapters.SurviveAdapter;
import pooller.model.pool.PoolMainInfo;
import pooller.model.pool.QuestionVariant;
import pooller.model.pool.Survive;
import pooller.model.pool.SurvivorVariants;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Objects;

public class SurviveRepoServiceImpl extends AbstractEntityRemoteServiceServlet implements SurviveRepositoryService {
    @Override
    public SurviveDto startSurvive(SurviveDto surviveDto) {
        Survive survive = SurviveAdapter.I.fromDto(surviveDto);
        survive.setCreated(new Timestamp(System.currentTimeMillis()));
        EntityManager em = emf.createEntityManager();
        survive.setPoolMainInfo(em.find(PoolMainInfo.class, surviveDto.getPoolMainInfo().getId()));
        em.persist(survive);
        surviveDto = SurviveAdapter.I.toDto(survive);
        em.close();
        return surviveDto;
    }

    @Override
    public Long setVariant(SurviveVariantDto variantDto) {
        if (Objects.nonNull(variantDto.getSurviveDto())) {
            Long surviveId = variantDto.getSurviveDto().getId();
            EntityManager em = emf.createEntityManager();
            Survive survive = em.find(Survive.class, surviveId);
            SurvivorVariants e = new SurvivorVariants();
            ArrayList<QuestionVariant> variants = new ArrayList<>();
            for (QuestionVariantDto qVariantDto : variantDto.getVariantDto()) {
                variants.add(em.find(QuestionVariant.class, qVariantDto.getId()));
            }
            e.setQuestionVariant(variants);
            survive.getVariants().add(e);
            em.persist(e);
            em.close();
            return e.getId();
        }
        return null;
    }

    @Override
    public boolean endSurvive(SurviveDto surviveDto) {
        EntityManager em = emf.createEntityManager();
        Survive survive = em.find(Survive.class, surviveDto.getId());
        survive.setFinished(new Timestamp(System.currentTimeMillis()));
        em.merge(survive);
        em.close();
        return false;
    }

    @Override
    public PageDto<SurviveDto> pageSurvive(long start, int count) {
        return null;
    }
}
