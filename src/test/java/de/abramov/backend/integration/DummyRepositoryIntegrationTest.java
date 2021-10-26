package de.abramov.backend.integration;

import de.abramov.backend.GenericTestBootstrap;
import de.abramov.backend.rest.entity.DummyEntity;
import de.abramov.backend.rest.repository.DummyRepository;
import de.abramov.backend.rest.service.DummyService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Set;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DummyRepositoryIntegrationTest extends GenericTestBootstrap<DummyEntity, DummyRepository, DummyService> {

    @Autowired
    private DummyService service;

    @BeforeEach
    public void beforeEach() {
        IntStream.range(0, 100).forEach(index -> service.save(new DummyEntity(index, "Dummy Text " + index, "123456789" + index)));
    }

    @DisplayName("Save Dummy Entity Test")
    @Test
    void saveDummyEntityTest() {
        service.deleteAll();
        DummyEntity dummyEntity = new DummyEntity();
        dummyEntity.setDummyNumber(1132421);
        dummyEntity.setPhone("012381237");
        dummyEntity.setDummyText("Lorem Ipsum Dolore et.");
        DummyEntity result = service.save(dummyEntity);

        assertNotNull(result);
        Set<DummyEntity> dbContent = service.findAll();
        assertEquals(1, dbContent.size());
        assertEquals(dbContent.stream().findFirst().get(), dummyEntity);
        assertTrue(dbContent.contains(dummyEntity));
    }


}
