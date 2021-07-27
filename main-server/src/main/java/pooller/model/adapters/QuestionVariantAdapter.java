package pooller.model.adapters;

import pooller.dto.pool.QuestionVariantDto;
import pooller.model.pool.QuestionVariant;

public interface QuestionVariantAdapter extends DtoAdapter<QuestionVariant, QuestionVariantDto> {
    QuestionVariantAdapter I = new QuestionVariantAdapter() {};
    default QuestionVariant fromDto(QuestionVariantDto questionVariantDto) {
        QuestionVariant questionVariant = new QuestionVariant();
        questionVariant.setId(questionVariantDto.getId());
        questionVariant.setVariantText(questionVariantDto.getVariantText());
        return questionVariant;
    }

    default QuestionVariantDto toDto(QuestionVariant questionVariant) {
        QuestionVariantDto questionVariantDto = new QuestionVariantDto();
        questionVariantDto.setId(questionVariant.getId());
        questionVariantDto.setVariantText(questionVariant.getVariantText());
        return questionVariantDto;
    }
}
