package pooller.dto.pool;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.io.Serializable;

public class QuestionVariantDto implements Serializable, IsSerializable {
    private Long id;
    private String variantText;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVariantText() {
        return variantText;
    }

    public void setVariantText(String variantText) {
        this.variantText = variantText;
    }
}
