package pooller.repository;

import com.google.gwt.user.client.rpc.AsyncCallback;
import pooller.dto.PageDto;
import pooller.dto.pool.PoolQuestionDto;
import pooller.dto.pool.QuestionVariantDto;
import pooller.dto.pool.PoolMetaInfoDto;

import java.util.List;

public interface PoolingRepositoryServiceAsync {
    void savePool(PoolMetaInfoDto poolMetaInfoDto, AsyncCallback<PoolMetaInfoDto> callback);
    void listPools(AsyncCallback<List<PoolMetaInfoDto>> callback);
    void getPage(long start, int size, AsyncCallback<PageDto<PoolMetaInfoDto>> callback);
    void updatePool(PoolMetaInfoDto poolMetaInfoDto, AsyncCallback<PoolMetaInfoDto> callback);
    void addQuestion(Long poolId, PoolQuestionDto questionDto, AsyncCallback<Boolean> callback);
    void addVariant(Long questionId, QuestionVariantDto questionVariantDto, AsyncCallback<Boolean> callback);
    void removeQuestion(Long questionId, AsyncCallback<Boolean> callback);
    void removeVariant(Long variantId, AsyncCallback<Boolean> callback);
    void updateQuestion(PoolQuestionDto poolQuestionDto, AsyncCallback<Boolean> callback);
    void updateVariant(QuestionVariantDto questionVariantDto, AsyncCallback<Boolean> callback);
    void getQuestions(Long poolId, AsyncCallback<List<PoolQuestionDto>> callback);
    void getPool(Long poolId, AsyncCallback<PoolMetaInfoDto> callback);
    void getQuestion(Long questionId, AsyncCallback<PoolQuestionDto> callback);
}
