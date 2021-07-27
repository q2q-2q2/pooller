package pooller.model.pool;

import pooller.model.actors.Survivor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
public class Survive {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Timestamp created;
    private Timestamp finished;
    @OneToOne
    private Survivor survivor;
    @OneToOne
    private PoolMainInfo poolMainInfo;
    @OneToMany
    private List<SurvivorVariants> variants;

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

    public Survivor getSurvivor() {
        return survivor;
    }

    public void setSurvivor(Survivor survivor) {
        this.survivor = survivor;
    }

    public PoolMainInfo getPoolMainInfo() {
        return poolMainInfo;
    }

    public void setPoolMainInfo(PoolMainInfo poolMainInfo) {
        this.poolMainInfo = poolMainInfo;
    }

    public List<SurvivorVariants> getVariants() {
        return variants;
    }

    public void setVariants(List<SurvivorVariants> variants) {
        this.variants = variants;
    }
}
