package pooller.model.adapters;

import pooller.dto.pool.PoolMetaInfoDto;
import pooller.model.pool.PoolMainInfo;
import pooller.model.pool.PoolQuestion;

import java.util.Date;
import java.util.stream.Collectors;

public interface PoolMetaInfoAdapter extends DtoAdapter<PoolMainInfo, PoolMetaInfoDto> {
    PoolMetaInfoAdapter I = new PoolMetaInfoAdapter() {};

    default PoolMainInfo fromDto(PoolMetaInfoDto poolMetaInfoDto) {
        PoolMainInfo poolMainInfo = new PoolMainInfo();
        poolMainInfo.setTitle(poolMetaInfoDto.getTitle());
        poolMainInfo.setDescription(poolMetaInfoDto.getDescription());
        if (poolMetaInfoDto.getCreated() == null) {
            poolMainInfo.setCreated(new Date());
        } else {
            poolMainInfo.setUpdated(new Date());
        }
        return poolMainInfo;
    }

    default PoolMetaInfoDto toDto(PoolMainInfo poolMainInfo) {
        PoolMetaInfoDto poolMetaInfoDto = new PoolMetaInfoDto();
        poolMetaInfoDto.setId(poolMainInfo.getId());
        poolMetaInfoDto.setTitle(poolMainInfo.getTitle());
        poolMetaInfoDto.setDescription(poolMainInfo.getDescription());
        poolMetaInfoDto.setCreated(poolMainInfo.getCreated());
        poolMetaInfoDto.setUpdated(poolMainInfo.getUpdated());
        poolMetaInfoDto.setQuestionCount(poolMainInfo.getQuestions() == null ? 0 : poolMainInfo.getQuestions().size());
        if (poolMainInfo.getQuestions() != null) {
            poolMetaInfoDto.setQuestionIds(poolMainInfo.getQuestions().stream().map(PoolQuestion::getId).collect(Collectors.toList()));
        }
        return poolMetaInfoDto;
    }
}
