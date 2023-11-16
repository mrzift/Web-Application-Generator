package com.jhipster.timothy.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Emp.
 */
@Entity
@Table(name = "emp")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Emp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @NotNull
    @Column(name = "emp_id", nullable = false, unique = true)
    private Long empId;

    @NotNull
    @Column(name = "emp_name", nullable = false)
    private String empName;

    @NotNull
    @Column(name = "emp_job", nullable = false)
    private String empJob;

    @Column(name = "emp_address")
    private String empAddress;

    @Column(name = "emp_salary")
    private Integer empSalary;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = { "emps" }, allowSetters = true)
    private Manager manager;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Emp id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmpId() {
        return this.empId;
    }

    public Emp empId(Long empId) {
        this.setEmpId(empId);
        return this;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return this.empName;
    }

    public Emp empName(String empName) {
        this.setEmpName(empName);
        return this;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpJob() {
        return this.empJob;
    }

    public Emp empJob(String empJob) {
        this.setEmpJob(empJob);
        return this;
    }

    public void setEmpJob(String empJob) {
        this.empJob = empJob;
    }

    public String getEmpAddress() {
        return this.empAddress;
    }

    public Emp empAddress(String empAddress) {
        this.setEmpAddress(empAddress);
        return this;
    }

    public void setEmpAddress(String empAddress) {
        this.empAddress = empAddress;
    }

    public Integer getEmpSalary() {
        return this.empSalary;
    }

    public Emp empSalary(Integer empSalary) {
        this.setEmpSalary(empSalary);
        return this;
    }

    public void setEmpSalary(Integer empSalary) {
        this.empSalary = empSalary;
    }

    public Manager getManager() {
        return this.manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public Emp manager(Manager manager) {
        this.setManager(manager);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Emp)) {
            return false;
        }
        return id != null && id.equals(((Emp) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Emp{" +
            "id=" + getId() +
            ", empId=" + getEmpId() +
            ", empName='" + getEmpName() + "'" +
            ", empJob='" + getEmpJob() + "'" +
            ", empAddress='" + getEmpAddress() + "'" +
            ", empSalary=" + getEmpSalary() +
            "}";
    }
}
