package de.abramov.backend.rest.repository;

import de.abramov.backend.rest.entity.DummyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/** Use this as your Blueprint. */
@Transactional
public interface DummyRepository extends JpaRepository<DummyEntity, Integer> {

   public List<DummyEntity> findTop10ByOrderByIdDesc();

}
