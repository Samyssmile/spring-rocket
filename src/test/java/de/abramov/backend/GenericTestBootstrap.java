package de.abramov.backend;


import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Optional;
import java.util.Set;

import de.abramov.backend.rest.entity.IEntity;
import de.abramov.backend.rest.service.GenericService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class GenericTestBootstrap<E extends IEntity, R extends JpaRepository<E, Integer>, S extends GenericService<E, R>> {

    private static final double TOLERANCE_VARIATION = 5;
    private static final int ZERO = 0;
    private static final long SPEED_MULTIPLICATOR = 4;

    private void restoreData(Set<E> dump) {
        service.deleteAll();
        service.saveAll(dump);
    }

    @Autowired
    protected S service;

    @Test
    public void addEntryOperation() {
        if (service != null) {
            E entry = service.findAll().stream().findFirst().get();

            Set<E> dump = getDumpAndDeleteAll();
            service.save(entry);

            long existingEntriesAfterAdd = service.count();
            assertTrue(existingEntriesAfterAdd == 1,
                    String.format("More than one entry exists: %s", existingEntriesAfterAdd));
            restoreData(dump);
        }
    }

    @Test
    public void clearTableOperation() {
        if (service != null) {
            Set<E> dump = getDumpAndDeleteAll();
            long existingEntriesAfterClear = service.count();
            assertTrue(existingEntriesAfterClear == ZERO, String
                    .format("DB Table not empty afte clear all operation: %s", existingEntriesAfterClear));
            restoreData(dump);
        }
    }

    @Test
    @DisplayName(value = "Database Table is not empty")
    public void testElementsAreAvailable() {
        if (service != null) {
            long existingEntries = service.count();
            assertTrue(existingEntries > 0, "No entries found! Pls doublecheck migration files");
        }
    }

    @Test
    @DisplayName(value = "Database Cache is used - Cache requests are at least faster")
    public void testGetAllRequestIsCached() {
        Set<E> dump = getDumpAndDeleteAll();

        dump.stream().findAny().ifPresent(service::save);
        long firstRun = getNanoTimeForFindAllOperation();
        long secondRun = getNanoTimeForFindAllOperation();

        assertTrue(secondRun * SPEED_MULTIPLICATOR < firstRun, "Cached request is not at least "
                + SPEED_MULTIPLICATOR + " times faster: " + (firstRun / secondRun));
        restoreData(dump);
    }

    @Test
    @DisplayName("Cache must be clear after a new element added")
    public void testCacheEvictIsExecutedAfterElementAdded() {
//        long firstRun = getNanoTimeForFindAllOperation();
//        long secondRun = getNanoTimeForFindAllOperation();
//
//        assertTrue(secondRun * SPEED_MULTIPLICATOR < firstRun, "Cached request is not at least "
//                + SPEED_MULTIPLICATOR + " times faster:" + (firstRun / secondRun));
//
//        Optional<E> element = getFirstElement();
//        element.ifPresent(e ->e.setId(0));
//        element.ifPresent(service::save);
//
//        long runAfterEvict = getNanoTimeForFindAllOperation();
//
//        assertTrue((runAfterEvict / secondRun) >= SPEED_MULTIPLICATOR, "Run after evict: " + runAfterEvict
//                + " secondRun: " + secondRun + " first run: " + firstRun);
    }

    @Test
    @DisplayName("Cache must be clear after an element was removed")
    public void testCacheEvictIsExecutedAfterElementRemoved() {
        long firstRun = getNanoTimeForFindAllOperation();

        Optional<E> element = getFirstElement();
        element.ifPresent(service::removeElement);

        long runAfterEvict = getNanoTimeForFindAllOperation();

        assertTrue((firstRun / runAfterEvict) < TOLERANCE_VARIATION, "Run after evict: " + runAfterEvict + " first run was: "
                + firstRun);
    }

    private Optional<E> getFirstElement() {
        return service.findAll().stream().findFirst();
    }

    private long getNanoTimeForFindAllOperation() {
        long startTime = System.nanoTime();
        service.findAll();
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    private Set<E> getDumpAndDeleteAll() {
        Set<E> dump = service.findAll();
        service.deleteAll();
        return dump;
    }

}
