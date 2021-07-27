package pooller.model.adapters;

import pooller.dto.pool.SurviveDto;
import pooller.model.pool.Survive;

public interface SurviveAdapter extends DtoAdapter<Survive, SurviveDto> {
    SurviveAdapter I = new SurviveAdapter() {};

    @Override
    default Survive fromDto(SurviveDto dto) {
        Survive survive = new Survive();
        survive.setId(dto.getId());
        survive.setPoolMainInfo(PoolMetaInfoAdapter.I.fromDto(dto.getPoolMainInfo()));
        if (dto.getSurvivor() != null) {
            survive.setSurvivor(SurvivorAdaptor.I.fromDto(dto.getSurvivor()));
        }
        return survive;
    }

    @Override
    default SurviveDto toDto(Survive entity) {
        SurviveDto surviveDto = new SurviveDto();
        surviveDto.setId(entity.getId());
        surviveDto.setCreated(entity.getCreated());
        surviveDto.setFinished(entity.getFinished());
        surviveDto.setPoolMainInfo(PoolMetaInfoAdapter.I.toDto(entity.getPoolMainInfo()));
        if (entity.getSurvivor() != null) {
            surviveDto.setSurvivor(SurvivorAdaptor.I.toDto(entity.getSurvivor()));
        }
        return surviveDto;
    }
}
