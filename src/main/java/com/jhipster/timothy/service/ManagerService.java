package com.jhipster.timothy.service;

import com.jhipster.timothy.domain.Manager;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Manager}.
 */
public interface ManagerService {
    /**
     * Save a manager.
     *
     * @param manager the entity to save.
     * @return the persisted entity.
     */
    Manager save(Manager manager);

    /**
     * Updates a manager.
     *
     * @param manager the entity to update.
     * @return the persisted entity.
     */
    Manager update(Manager manager);

    /**
     * Partially updates a manager.
     *
     * @param manager the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Manager> partialUpdate(Manager manager);

    /**
     * Get all the managers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Manager> findAll(Pageable pageable);

    /**
     * Get the "id" manager.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Manager> findOne(Long id);

    /**
     * Delete the "id" manager.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
