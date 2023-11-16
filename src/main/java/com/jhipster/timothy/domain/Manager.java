package com.jhipster.timothy.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Manager.
 */
@Entity
@Table(name = "manager")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Manager implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "man_id", nullable = false, unique = true)
    private Long manId;

    @NotNull
    @Column(name = "man_name", nullable = false)
    private String manName;

    @NotNull
    @Column(name = "man_email", nullable = false)
    private String manEmail;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "manager")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "manager" }, allowSetters = true)
    private Set<Emp> emps = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Manager id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getManId() {
        return this.manId;
    }

    public Manager manId(Long manId) {
        this.setManId(manId);
        return this;
    }

    public void setManId(Long manId) {
        this.manId = manId;
    }

    public String getManName() {
        return this.manName;
    }

    public Manager manName(String manName) {
        this.setManName(manName);
        return this;
    }

    public void setManName(String manName) {
        this.manName = manName;
    }

    public String getManEmail() {
        return this.manEmail;
    }

    public Manager manEmail(String manEmail) {
        this.setManEmail(manEmail);
        return this;
    }

    public void setManEmail(String manEmail) {
        this.manEmail = manEmail;
    }

    public Set<Emp> getEmps() {
        return this.emps;
    }

    public void setEmps(Set<Emp> emps) {
        if (this.emps != null) {
            this.emps.forEach(i -> i.setManager(null));
        }
        if (emps != null) {
            emps.forEach(i -> i.setManager(this));
        }
        this.emps = emps;
    }

    public Manager emps(Set<Emp> emps) {
        this.setEmps(emps);
        return this;
    }

    public Manager addEmp(Emp emp) {
        this.emps.add(emp);
        emp.setManager(this);
        return this;
    }

    public Manager removeEmp(Emp emp) {
        this.emps.remove(emp);
        emp.setManager(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Manager)) {
            return false;
        }
        return id != null && id.equals(((Manager) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Manager{" +
            "id=" + getId() +
            ", manId=" + getManId() +
            ", manName='" + getManName() + "'" +
            ", manEmail='" + getManEmail() + "'" +
            "}";
    }
}
