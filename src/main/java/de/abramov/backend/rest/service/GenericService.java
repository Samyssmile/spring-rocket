package de.abramov.backend.rest.service;

import com.google.common.collect.Sets;
import de.abramov.backend.configuration.cache.CachingConfiguration;
import de.abramov.backend.rest.entity.IEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class GenericService<E extends IEntity, S extends JpaRepository<E, Integer>> {
  private Logger logger = LoggerFactory.getLogger(GenericService.class);

  @Autowired private S repository;

  @CacheEvict(value = CachingConfiguration.CACHE_RESOLVER_NAME, allEntries = true)
  public E save(E element) {
    return repository.save(element);
  }

  @CacheEvict(value = CachingConfiguration.CACHE_RESOLVER_NAME, allEntries = true)
  public List<E> saveAll(Set<E> data) {
    return repository.saveAll(data);
  }

  @CacheEvict(value = CachingConfiguration.CACHE_RESOLVER_NAME, allEntries = true)
  public List<E> saveAll(List<E> data) {
    return repository.saveAll(data);
  }

  @Cacheable(
      sync = true,
      value = CachingConfiguration.CACHE_RESOLVER_NAME,
      keyGenerator = "customKeyGenerator")
  public Optional<E> findElementById(int id) {
    return repository.findById(id);
  }

  @Cacheable(
      sync = true,
      value = CachingConfiguration.CACHE_RESOLVER_NAME,
      keyGenerator = "customKeyGenerator")
  public Set<E> findAll() {
    return Sets.newHashSet(repository.findAll());
  }

  @CacheEvict(value = CachingConfiguration.CACHE_RESOLVER_NAME, allEntries = true)
  public void removeElementById(int id) {
    if (exists(id)) {
      logger.info("Requesting deletion of id: {}... found deleting..", id);
      repository.deleteById(id);
    }
  }

  @CacheEvict(value = CachingConfiguration.CACHE_RESOLVER_NAME, allEntries = true)
  public void removeElement(E element) {
    repository.delete(element);
  }

  @CacheEvict(value = CachingConfiguration.CACHE_RESOLVER_NAME, allEntries = true)
  public void deleteAll() {
    repository.deleteAllInBatch();
  }

  public boolean exists(int id) {
    return repository.existsById(id);
  }

  @CacheEvict(value = CachingConfiguration.CACHE_RESOLVER_NAME, allEntries = true)
  public E updateElement(E element) {
    return repository.save(element);
  }

  public Page<E> findPage(Pageable pageable) {
    return repository.findAll(pageable);
  }

  public Iterable<E> findSort(Sort sort) {
    return repository.findAll(sort);
  }

  public S getRepository() {
    return repository;
  }

  @Cacheable(
      sync = true,
      value = CachingConfiguration.CACHE_RESOLVER_NAME,
      keyGenerator = "customKeyGenerator")
  public List<E> findAllElements(Integer pageNo, Integer pageSize, String sortBy) {
    Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
    Page<E> pagedResult = repository.findAll(paging);

    if (pagedResult.hasContent()) {
      return pagedResult.getContent();
    } else {
      return new ArrayList<>();
    }
  }

  @Cacheable(value = CachingConfiguration.CACHE_RESOLVER_NAME, keyGenerator = "customKeyGenerator")
  public long count() {
    return repository.count();
  }
}
