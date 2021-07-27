package pooller.dto.pool;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.io.Serializable;
import java.sql.Timestamp;

public class SurviveDto implements Serializable, IsSerializable {
    private Long id;
    private Timestamp created;
    private Timestamp finished;
    private SurvivorDto survivor;
    private PoolMetaInfoDto poolMainInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getFinished() {
        return finished;
    }

    public void setFinished(Timestamp finished) {
        this.finished = finished;
    }

    public SurvivorDto getSurvivor() {
        return survivor;
    }

    public void setSurvivor(SurvivorDto survivor) {
        this.survivor = survivor;
    }

    public PoolMetaInfoDto getPoolMainInfo() {
        return poolMainInfo;
    }

    public void setPoolMainInfo(PoolMetaInfoDto poolMainInfo) {
        this.poolMainInfo = poolMainInfo;
    }
}
