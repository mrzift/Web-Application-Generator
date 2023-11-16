package com.jhipster.timothy.repository;

import com.jhipster.timothy.domain.Manager;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Manager entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {}
