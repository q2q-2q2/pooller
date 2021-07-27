package pooller.repository;

import com.google.gwt.user.client.rpc.AsyncCallback;
import pooller.dto.PageDto;
import pooller.dto.pool.SurviveDto;
import pooller.dto.pool.SurviveVariantDto;

public interface SurviveRepositoryServiceAsync {
    void startSurvive(SurviveDto surviveDto, AsyncCallback<SurviveDto> callback);
    void setVariant(SurviveVariantDto variantDto, AsyncCallback<Long> callback);
    void endSurvive(SurviveDto surviveDto, AsyncCallback<Boolean> callback);
    void pageSurvive(long start, int count, AsyncCallback<PageDto<SurviveDto>> callback);
}
