package pooller.model.pool;

import pooller.PoolQuestionType;

import javax.persistence.*;
import java.util.List;

@Entity
public class PoolQuestion {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "questionOrder")
    private Integer order;
    private PoolQuestionType questionType;

    @OneToMany
    private List<QuestionVariant> variants;

    private String questionText;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<QuestionVariant> getVariants() {
        return variants;
    }

    public void setVariants(List<QuestionVariant> variants) {
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
