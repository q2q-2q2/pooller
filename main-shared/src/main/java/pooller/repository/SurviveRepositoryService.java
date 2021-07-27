package pooller.repository;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import pooller.dto.PageDto;
import pooller.dto.pool.SurviveDto;
import pooller.dto.pool.SurviveVariantDto;

@RemoteServiceRelativePath("survive-storage")
public interface SurviveRepositoryService extends RemoteService {
    SurviveDto startSurvive(SurviveDto surviveDto);
    Long setVariant(SurviveVariantDto variantDto);
    boolean endSurvive(SurviveDto surviveDto);
    PageDto<SurviveDto> pageSurvive(long start, int count);
}
