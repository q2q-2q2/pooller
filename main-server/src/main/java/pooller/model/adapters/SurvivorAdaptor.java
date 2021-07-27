package pooller.model.adapters;

import pooller.dto.pool.SurvivorDto;
import pooller.model.actors.Survivor;

public interface SurvivorAdaptor extends DtoAdapter<Survivor, SurvivorDto> {
    SurvivorAdaptor I = new SurvivorAdaptor() {};

    @Override
    default Survivor fromDto(SurvivorDto dto) {
        Survivor survivor = new Survivor();
        survivor.setId(dto.getId());
        return survivor;
    }

    @Override
    default SurvivorDto toDto(Survivor entity) {
        SurvivorDto survivorDto = new SurvivorDto();
        survivorDto.setId(entity.getId());
        return survivorDto;
    }
}
