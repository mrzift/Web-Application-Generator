package com.jhipster.timothy.repository;

import com.jhipster.timothy.domain.Emp;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Emp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmpRepository extends JpaRepository<Emp, Long> {}
