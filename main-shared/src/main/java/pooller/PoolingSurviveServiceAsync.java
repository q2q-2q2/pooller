package pooller;

import com.google.gwt.user.client.rpc.AsyncCallback;
import pooller.dto.pool.PoolMetaInfoDto;
import pooller.dto.uac.CabinetDto;

public interface PoolingSurviveServiceAsync {
    void invite(PoolMetaInfoDto pool, String email, AsyncCallback<CabinetDto> callback);
}
