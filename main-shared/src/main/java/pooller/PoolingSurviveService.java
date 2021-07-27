package pooller;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import pooller.dto.pool.PoolMetaInfoDto;
import pooller.dto.uac.CabinetDto;

@RemoteServiceRelativePath("pooling-survive")
public interface PoolingSurviveService extends RemoteService {
    CabinetDto invite(PoolMetaInfoDto pool, String email);
}
