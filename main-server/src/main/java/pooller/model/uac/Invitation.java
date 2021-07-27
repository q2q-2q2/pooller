package pooller.model.uac;

import pooller.model.actors.Survivor;
import pooller.model.pool.PoolMainInfo;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.sql.Timestamp;

@Entity
public class Invitation {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private PoolMainInfo pool;
    @ManyToOne
    private Survivor survivor;
    @Email
    private String email;
    private Timestamp created;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public PoolMainInfo getPool() {
        return pool;
    }

    public void setPool(PoolMainInfo pool) {
        this.pool = pool;
    }

    public Survivor getSurvivor() {
        return survivor;
    }

    public void setSurvivor(Survivor survivor) {
        this.survivor = survivor;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }
}
