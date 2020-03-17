package de.abramov.backend.rest.service;

import de.abramov.backend.rest.entity.DummyEntity;
import de.abramov.backend.rest.repository.DummyRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Use this as your Service Blueprint.
 */
@Service(value = "dummy-service")
public class DummyService extends GenericService<DummyEntity, DummyRepository>{
    private final  String uuid = DummyService.class.getSimpleName();

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        DummyService other = (DummyService) obj;
        return Objects.equals(uuid, other.uuid);
    }

}
