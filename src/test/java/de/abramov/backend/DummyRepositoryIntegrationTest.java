package de.abramov.backend;

import de.abramov.backend.rest.entity.DummyEntity;
import de.abramov.backend.rest.repository.DummyRepository;
import de.abramov.backend.rest.service.DummyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DummyRepositoryIntegrationTest extends GenericTestBootstrap<DummyEntity, DummyRepository, DummyService> {
    @Autowired
    private DummyService service;

    @BeforeEach
    public void beforeEach(){
        service.save(new DummyEntity(1, "Dummy Text 1", "123456789"));
        service.save(new DummyEntity(2, "Dummy Text 2" , "123456789"));
        service.save(new DummyEntity(3, "Dummy Text 3", "123456789"));
    }

}
