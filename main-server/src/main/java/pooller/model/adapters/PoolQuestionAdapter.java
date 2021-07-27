package pooller.model.adapters;

import pooller.PoolQuestionType;
import pooller.dto.pool.PoolQuestionDto;
import pooller.model.pool.PoolQuestion;

import java.util.stream.Collectors;

public interface PoolQuestionAdapter extends DtoAdapter<PoolQuestion, PoolQuestionDto> {
    PoolQuestionAdapter I = new PoolQuestionAdapter() {};
    default PoolQuestion fromDto(PoolQuestionDto question) {
        PoolQuestion poolQuestion = new PoolQuestion();
        poolQuestion.setId(question.getId());
        poolQuestion.setQuestionText(question.getQuestionText());
        if (question.getVariants() != null) {
            poolQuestion.setVariants(question.getVariants().stream().map(QuestionVariantAdapter.I::fromDto).collect(Collectors.toList()));
        }
        if (question.getQuestionType() == null) {
            poolQuestion.setQuestionType(PoolQuestionType.MANY);
        } else {
            poolQuestion.setQuestionType(question.getQuestionType());
        }
        return poolQuestion;
    }

    default PoolQuestionDto toDto(PoolQuestion poolQuestion) {
        PoolQuestionDto poolQuestionDto = new PoolQuestionDto();
        poolQuestionDto.setId(poolQuestion.getId());
        poolQuestionDto.setQuestionText(poolQuestion.getQuestionText());
        if (poolQuestion.getVariants() != null) {
            poolQuestionDto.setVariants(poolQuestion.getVariants().stream().map(QuestionVariantAdapter.I::toDto).collect(Collectors.toList()));
        }
        if (poolQuestion.getQuestionType() == null) {
            poolQuestionDto.setQuestionType(PoolQuestionType.MANY);
        } else {
            poolQuestionDto.setQuestionType(poolQuestion.getQuestionType());
        }
        return poolQuestionDto;
    }
}
