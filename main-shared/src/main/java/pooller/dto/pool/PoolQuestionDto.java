package pooller.dto.pool;

import com.google.gwt.user.client.rpc.IsSerializable;
import pooller.PoolQuestionType;

import java.io.Serializable;
import java.util.List;

public class PoolQuestionDto implements Serializable, IsSerializable {
    private Long id;
    private Integer order;
    private PoolQuestionType questionType;

    private List<QuestionVariantDto> variants;

    private String questionText;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<QuestionVariantDto> getVariants() {
        return variants;
    }

    public void setVariants(List<QuestionVariantDto> variants) {
        this.variants = variants;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public PoolQuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(PoolQuestionType questionType) {
        this.questionType = questionType;
    }
}
