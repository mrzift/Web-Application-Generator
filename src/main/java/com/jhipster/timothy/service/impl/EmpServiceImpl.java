package com.jhipster.timothy.service.impl;

import com.jhipster.timothy.domain.Emp;
import com.jhipster.timothy.repository.EmpRepository;
import com.jhipster.timothy.service.EmpService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Emp}.
 */
@Service
@Transactional
public class EmpServiceImpl implements EmpService {

    private final Logger log = LoggerFactory.getLogger(EmpServiceImpl.class);

    private final EmpRepository empRepository;

    public EmpServiceImpl(EmpRepository empRepository) {
        this.empRepository = empRepository;
    }

    @Override
    public Emp save(Emp emp) {
        log.debug("Request to save Emp : {}", emp);
        return empRepository.save(emp);
    }

    @Override
    public Emp update(Emp emp) {
        log.debug("Request to update Emp : {}", emp);
        return empRepository.save(emp);
    }

    @Override
    public Optional<Emp> partialUpdate(Emp emp) {
        log.debug("Request to partially update Emp : {}", emp);

        return empRepository
            .findById(emp.getId())
            .map(existingEmp -> {
                if (emp.getEmpId() != null) {
                    existingEmp.setEmpId(emp.getEmpId());
                }
                if (emp.getEmpName() != null) {
                    existingEmp.setEmpName(emp.getEmpName());
                }
                if (emp.getEmpJob() != null) {
                    existingEmp.setEmpJob(emp.getEmpJob());
                }
                if (emp.getEmpAddress() != null) {
                    existingEmp.setEmpAddress(emp.getEmpAddress());
                }
                if (emp.getEmpSalary() != null) {
                    existingEmp.setEmpSalary(emp.getEmpSalary());
                }

                return existingEmp;
            })
            .map(empRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Emp> findAll(Pageable pageable) {
        log.debug("Request to get all Emps");
        return empRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Emp> findOne(Long id) {
        log.debug("Request to get Emp : {}", id);
        return empRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Emp : {}", id);
        empRepository.deleteById(id);
    }
}
