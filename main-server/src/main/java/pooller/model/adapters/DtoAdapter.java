package pooller.model.adapters;

import java.io.Serializable;

public interface DtoAdapter<E, D extends Serializable> {
    default E fromDto(D dto) {
        return null;
    }

    default D toDto(E entity) {
        return null;
    }
}
