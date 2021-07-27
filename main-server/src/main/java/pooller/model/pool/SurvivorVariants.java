package pooller.model.pool;

import javax.persistence.*;
import java.util.List;

@Entity
public class SurvivorVariants {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private PoolQuestion poolQuestion;
    @OneToMany
    private List<QuestionVariant> questionVariants;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PoolQuestion getPoolQuestion() {
        return poolQuestion;
    }

    public void setPoolQuestion(PoolQuestion poolQuestion) {
        this.poolQuestion = poolQuestion;
    }

    public List<QuestionVariant> getQuestionVariants() {
        return questionVariants;
    }

    public void setQuestionVariant(List<QuestionVariant> questionVariants) {
        this.questionVariants = questionVariants;
    }
}
