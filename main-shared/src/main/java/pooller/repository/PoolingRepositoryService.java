package pooller.repository;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import pooller.dto.PageDto;
import pooller.dto.pool.PoolQuestionDto;
import pooller.dto.pool.QuestionVariantDto;
import pooller.dto.pool.PoolMetaInfoDto;

import java.util.List;

@RemoteServiceRelativePath("pooling-storage")
public interface PoolingRepositoryService extends RemoteService {
    PoolMetaInfoDto savePool(PoolMetaInfoDto poolMetaInfoDto);
    List<PoolMetaInfoDto> listPools();
    PageDto<PoolMetaInfoDto> getPage(long start, int size);
    PoolMetaInfoDto updatePool(PoolMetaInfoDto poolMetaInfoDto);
    boolean addQuestion(Long poolId, PoolQuestionDto questionDto);
    boolean addVariant(Long questionId, QuestionVariantDto questionVariantDto);
    boolean removeQuestion(Long questionId);
    boolean removeVariant(Long variantId);
    boolean updateQuestion(PoolQuestionDto poolQuestionDto);
    boolean updateVariant(QuestionVariantDto questionVariantDto);
    List<PoolQuestionDto> getQuestions(Long poolId);
    PoolMetaInfoDto getPool(Long poolId);
    PoolQuestionDto getQuestion(Long questionId);
}
