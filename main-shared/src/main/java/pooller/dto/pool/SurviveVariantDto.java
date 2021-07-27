package pooller.dto.pool;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.io.Serializable;
import java.util.List;

public class SurviveVariantDto implements Serializable, IsSerializable {
    SurviveDto surviveDto;
    PoolQuestionDto questionDto;
    List<QuestionVariantDto> variantDto;

    public SurviveDto getSurviveDto() {
        return surviveDto;
    }

    public void setSurviveDto(SurviveDto surviveDto) {
        this.surviveDto = surviveDto;
    }

    public PoolQuestionDto getQuestionDto() {
        return questionDto;
    }

    public void setQuestionDto(PoolQuestionDto questionDto) {
        this.questionDto = questionDto;
    }

    public List<QuestionVariantDto> getVariantDto() {
        return variantDto;
    }

    public void setVariantDto(List<QuestionVariantDto> variantDto) {
        this.variantDto = variantDto;
    }
}
