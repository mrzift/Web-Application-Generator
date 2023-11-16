package com.jhipster.timothy.service;

import com.jhipster.timothy.domain.Emp;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Emp}.
 */
public interface EmpService {
    /**
     * Save a emp.
     *
     * @param emp the entity to save.
     * @return the persisted entity.
     */
    Emp save(Emp emp);

    /**
     * Updates a emp.
     *
     * @param emp the entity to update.
     * @return the persisted entity.
     */
    Emp update(Emp emp);

    /**
     * Partially updates a emp.
     *
     * @param emp the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Emp> partialUpdate(Emp emp);

    /**
     * Get all the emps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Emp> findAll(Pageable pageable);

    /**
     * Get the "id" emp.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Emp> findOne(Long id);

    /**
     * Delete the "id" emp.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
