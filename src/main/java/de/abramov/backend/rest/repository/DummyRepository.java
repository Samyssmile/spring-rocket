package de.abramov.backend.rest.repository;

import de.abramov.backend.rest.entity.DummyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/** Use this as your Blueprint. */
@Transactional
public interface DummyRepository extends JpaRepository<DummyEntity, Integer> {}
