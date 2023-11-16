package com.jhipster.timothy.web.rest;

import com.jhipster.timothy.domain.Manager;
import com.jhipster.timothy.repository.ManagerRepository;
import com.jhipster.timothy.service.ManagerService;
import com.jhipster.timothy.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.jhipster.timothy.domain.Manager}.
 */
@RestController
@RequestMapping("/api")
public class ManagerResource {

    private final Logger log = LoggerFactory.getLogger(ManagerResource.class);

    private static final String ENTITY_NAME = "manager";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ManagerService managerService;

    private final ManagerRepository managerRepository;

    public ManagerResource(ManagerService managerService, ManagerRepository managerRepository) {
        this.managerService = managerService;
        this.managerRepository = managerRepository;
    }

    /**
     * {@code POST  /managers} : Create a new manager.
     *
     * @param manager the manager to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new manager, or with status {@code 400 (Bad Request)} if the manager has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/managers")
    public ResponseEntity<Manager> createManager(@Valid @RequestBody Manager manager) throws URISyntaxException {
        log.debug("REST request to save Manager : {}", manager);
        if (manager.getId() != null) {
            throw new BadRequestAlertException("A new manager cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Manager result = managerService.save(manager);
        return ResponseEntity
            .created(new URI("/api/managers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /managers/:id} : Updates an existing manager.
     *
     * @param id the id of the manager to save.
     * @param manager the manager to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated manager,
     * or with status {@code 400 (Bad Request)} if the manager is not valid,
     * or with status {@code 500 (Internal Server Error)} if the manager couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/managers/{id}")
    public ResponseEntity<Manager> updateManager(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Manager manager
    ) throws URISyntaxException {
        log.debug("REST request to update Manager : {}, {}", id, manager);
        if (manager.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, manager.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!managerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Manager result = managerService.update(manager);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, manager.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /managers/:id} : Partial updates given fields of an existing manager, field will ignore if it is null
     *
     * @param id the id of the manager to save.
     * @param manager the manager to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated manager,
     * or with status {@code 400 (Bad Request)} if the manager is not valid,
     * or with status {@code 404 (Not Found)} if the manager is not found,
     * or with status {@code 500 (Internal Server Error)} if the manager couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/managers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Manager> partialUpdateManager(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Manager manager
    ) throws URISyntaxException {
        log.debug("REST request to partial update Manager partially : {}, {}", id, manager);
        if (manager.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, manager.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!managerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Manager> result = managerService.partialUpdate(manager);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, manager.getId().toString())
        );
    }

    /**
     * {@code GET  /managers} : get all the managers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of managers in body.
     */
    @GetMapping("/managers")
    public ResponseEntity<List<Manager>> getAllManagers(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Managers");
        Page<Manager> page = managerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /managers/:id} : get the "id" manager.
     *
     * @param id the id of the manager to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the manager, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/managers/{id}")
    public ResponseEntity<Manager> getManager(@PathVariable Long id) {
        log.debug("REST request to get Manager : {}", id);
        Optional<Manager> manager = managerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(manager);
    }

    /**
     * {@code DELETE  /managers/:id} : delete the "id" manager.
     *
     * @param id the id of the manager to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/managers/{id}")
    public ResponseEntity<Void> deleteManager(@PathVariable Long id) {
        log.debug("REST request to delete Manager : {}", id);
        managerService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
