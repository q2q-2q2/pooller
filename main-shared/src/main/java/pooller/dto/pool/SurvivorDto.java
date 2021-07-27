package pooller.dto.pool;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.io.Serializable;

public class SurvivorDto implements Serializable, IsSerializable {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
