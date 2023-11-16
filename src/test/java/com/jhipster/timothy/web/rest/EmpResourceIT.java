package com.jhipster.timothy.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jhipster.timothy.IntegrationTest;
import com.jhipster.timothy.domain.Emp;
import com.jhipster.timothy.repository.EmpRepository;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EmpResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmpResourceIT {

    private static final Long DEFAULT_EMP_ID = 1L;
    private static final Long UPDATED_EMP_ID = 2L;

    private static final String DEFAULT_EMP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EMP_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMP_JOB = "AAAAAAAAAA";
    private static final String UPDATED_EMP_JOB = "BBBBBBBBBB";

    private static final String DEFAULT_EMP_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_EMP_ADDRESS = "BBBBBBBBBB";

    private static final Integer DEFAULT_EMP_SALARY = 1;
    private static final Integer UPDATED_EMP_SALARY = 2;

    private static final String ENTITY_API_URL = "/api/emps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmpRepository empRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmpMockMvc;

    private Emp emp;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Emp createEntity(EntityManager em) {
        Emp emp = new Emp()
            .empId(DEFAULT_EMP_ID)
            .empName(DEFAULT_EMP_NAME)
            .empJob(DEFAULT_EMP_JOB)
            .empAddress(DEFAULT_EMP_ADDRESS)
            .empSalary(DEFAULT_EMP_SALARY);
        return emp;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Emp createUpdatedEntity(EntityManager em) {
        Emp emp = new Emp()
            .empId(UPDATED_EMP_ID)
            .empName(UPDATED_EMP_NAME)
            .empJob(UPDATED_EMP_JOB)
            .empAddress(UPDATED_EMP_ADDRESS)
            .empSalary(UPDATED_EMP_SALARY);
        return emp;
    }

    @BeforeEach
    public void initTest() {
        emp = createEntity(em);
    }

    @Test
    @Transactional
    void createEmp() throws Exception {
        int databaseSizeBeforeCreate = empRepository.findAll().size();
        // Create the Emp
        restEmpMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emp)))
            .andExpect(status().isCreated());

        // Validate the Emp in the database
        List<Emp> empList = empRepository.findAll();
        assertThat(empList).hasSize(databaseSizeBeforeCreate + 1);
        Emp testEmp = empList.get(empList.size() - 1);
        assertThat(testEmp.getEmpId()).isEqualTo(DEFAULT_EMP_ID);
        assertThat(testEmp.getEmpName()).isEqualTo(DEFAULT_EMP_NAME);
        assertThat(testEmp.getEmpJob()).isEqualTo(DEFAULT_EMP_JOB);
        assertThat(testEmp.getEmpAddress()).isEqualTo(DEFAULT_EMP_ADDRESS);
        assertThat(testEmp.getEmpSalary()).isEqualTo(DEFAULT_EMP_SALARY);
    }

    @Test
    @Transactional
    void createEmpWithExistingId() throws Exception {
        // Create the Emp with an existing ID
        emp.setId(1L);

        int databaseSizeBeforeCreate = empRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmpMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emp)))
            .andExpect(status().isBadRequest());

        // Validate the Emp in the database
        List<Emp> empList = empRepository.findAll();
        assertThat(empList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEmpIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = empRepository.findAll().size();
        // set the field null
        emp.setEmpId(null);

        // Create the Emp, which fails.

        restEmpMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emp)))
            .andExpect(status().isBadRequest());

        List<Emp> empList = empRepository.findAll();
        assertThat(empList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmpNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = empRepository.findAll().size();
        // set the field null
        emp.setEmpName(null);

        // Create the Emp, which fails.

        restEmpMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emp)))
            .andExpect(status().isBadRequest());

        List<Emp> empList = empRepository.findAll();
        assertThat(empList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmpJobIsRequired() throws Exception {
        int databaseSizeBeforeTest = empRepository.findAll().size();
        // set the field null
        emp.setEmpJob(null);

        // Create the Emp, which fails.

        restEmpMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emp)))
            .andExpect(status().isBadRequest());

        List<Emp> empList = empRepository.findAll();
        assertThat(empList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEmps() throws Exception {
        // Initialize the database
        empRepository.saveAndFlush(emp);

        // Get all the empList
        restEmpMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emp.getId().intValue())))
            .andExpect(jsonPath("$.[*].empId").value(hasItem(DEFAULT_EMP_ID.intValue())))
            .andExpect(jsonPath("$.[*].empName").value(hasItem(DEFAULT_EMP_NAME)))
            .andExpect(jsonPath("$.[*].empJob").value(hasItem(DEFAULT_EMP_JOB)))
            .andExpect(jsonPath("$.[*].empAddress").value(hasItem(DEFAULT_EMP_ADDRESS)))
            .andExpect(jsonPath("$.[*].empSalary").value(hasItem(DEFAULT_EMP_SALARY)));
    }

    @Test
    @Transactional
    void getEmp() throws Exception {
        // Initialize the database
        empRepository.saveAndFlush(emp);

        // Get the emp
        restEmpMockMvc
            .perform(get(ENTITY_API_URL_ID, emp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(emp.getId().intValue()))
            .andExpect(jsonPath("$.empId").value(DEFAULT_EMP_ID.intValue()))
            .andExpect(jsonPath("$.empName").value(DEFAULT_EMP_NAME))
            .andExpect(jsonPath("$.empJob").value(DEFAULT_EMP_JOB))
            .andExpect(jsonPath("$.empAddress").value(DEFAULT_EMP_ADDRESS))
            .andExpect(jsonPath("$.empSalary").value(DEFAULT_EMP_SALARY));
    }

    @Test
    @Transactional
    void getNonExistingEmp() throws Exception {
        // Get the emp
        restEmpMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmp() throws Exception {
        // Initialize the database
        empRepository.saveAndFlush(emp);

        int databaseSizeBeforeUpdate = empRepository.findAll().size();

        // Update the emp
        Emp updatedEmp = empRepository.findById(emp.getId()).get();
        // Disconnect from session so that the updates on updatedEmp are not directly saved in db
        em.detach(updatedEmp);
        updatedEmp
            .empId(UPDATED_EMP_ID)
            .empName(UPDATED_EMP_NAME)
            .empJob(UPDATED_EMP_JOB)
            .empAddress(UPDATED_EMP_ADDRESS)
            .empSalary(UPDATED_EMP_SALARY);

        restEmpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmp.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEmp))
            )
            .andExpect(status().isOk());

        // Validate the Emp in the database
        List<Emp> empList = empRepository.findAll();
        assertThat(empList).hasSize(databaseSizeBeforeUpdate);
        Emp testEmp = empList.get(empList.size() - 1);
        assertThat(testEmp.getEmpId()).isEqualTo(UPDATED_EMP_ID);
        assertThat(testEmp.getEmpName()).isEqualTo(UPDATED_EMP_NAME);
        assertThat(testEmp.getEmpJob()).isEqualTo(UPDATED_EMP_JOB);
        assertThat(testEmp.getEmpAddress()).isEqualTo(UPDATED_EMP_ADDRESS);
        assertThat(testEmp.getEmpSalary()).isEqualTo(UPDATED_EMP_SALARY);
    }

    @Test
    @Transactional
    void putNonExistingEmp() throws Exception {
        int databaseSizeBeforeUpdate = empRepository.findAll().size();
        emp.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, emp.getId()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emp))
            )
            .andExpect(status().isBadRequest());

        // Validate the Emp in the database
        List<Emp> empList = empRepository.findAll();
        assertThat(empList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmp() throws Exception {
        int databaseSizeBeforeUpdate = empRepository.findAll().size();
        emp.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(emp))
            )
            .andExpect(status().isBadRequest());

        // Validate the Emp in the database
        List<Emp> empList = empRepository.findAll();
        assertThat(empList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmp() throws Exception {
        int databaseSizeBeforeUpdate = empRepository.findAll().size();
        emp.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emp)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Emp in the database
        List<Emp> empList = empRepository.findAll();
        assertThat(empList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmpWithPatch() throws Exception {
        // Initialize the database
        empRepository.saveAndFlush(emp);

        int databaseSizeBeforeUpdate = empRepository.findAll().size();

        // Update the emp using partial update
        Emp partialUpdatedEmp = new Emp();
        partialUpdatedEmp.setId(emp.getId());

        partialUpdatedEmp.empName(UPDATED_EMP_NAME).empJob(UPDATED_EMP_JOB).empAddress(UPDATED_EMP_ADDRESS);

        restEmpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmp.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmp))
            )
            .andExpect(status().isOk());

        // Validate the Emp in the database
        List<Emp> empList = empRepository.findAll();
        assertThat(empList).hasSize(databaseSizeBeforeUpdate);
        Emp testEmp = empList.get(empList.size() - 1);
        assertThat(testEmp.getEmpId()).isEqualTo(DEFAULT_EMP_ID);
        assertThat(testEmp.getEmpName()).isEqualTo(UPDATED_EMP_NAME);
        assertThat(testEmp.getEmpJob()).isEqualTo(UPDATED_EMP_JOB);
        assertThat(testEmp.getEmpAddress()).isEqualTo(UPDATED_EMP_ADDRESS);
        assertThat(testEmp.getEmpSalary()).isEqualTo(DEFAULT_EMP_SALARY);
    }

    @Test
    @Transactional
    void fullUpdateEmpWithPatch() throws Exception {
        // Initialize the database
        empRepository.saveAndFlush(emp);

        int databaseSizeBeforeUpdate = empRepository.findAll().size();

        // Update the emp using partial update
        Emp partialUpdatedEmp = new Emp();
        partialUpdatedEmp.setId(emp.getId());

        partialUpdatedEmp
            .empId(UPDATED_EMP_ID)
            .empName(UPDATED_EMP_NAME)
            .empJob(UPDATED_EMP_JOB)
            .empAddress(UPDATED_EMP_ADDRESS)
            .empSalary(UPDATED_EMP_SALARY);

        restEmpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmp.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmp))
            )
            .andExpect(status().isOk());

        // Validate the Emp in the database
        List<Emp> empList = empRepository.findAll();
        assertThat(empList).hasSize(databaseSizeBeforeUpdate);
        Emp testEmp = empList.get(empList.size() - 1);
        assertThat(testEmp.getEmpId()).isEqualTo(UPDATED_EMP_ID);
        assertThat(testEmp.getEmpName()).isEqualTo(UPDATED_EMP_NAME);
        assertThat(testEmp.getEmpJob()).isEqualTo(UPDATED_EMP_JOB);
        assertThat(testEmp.getEmpAddress()).isEqualTo(UPDATED_EMP_ADDRESS);
        assertThat(testEmp.getEmpSalary()).isEqualTo(UPDATED_EMP_SALARY);
    }

    @Test
    @Transactional
    void patchNonExistingEmp() throws Exception {
        int databaseSizeBeforeUpdate = empRepository.findAll().size();
        emp.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, emp.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(emp))
            )
            .andExpect(status().isBadRequest());

        // Validate the Emp in the database
        List<Emp> empList = empRepository.findAll();
        assertThat(empList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmp() throws Exception {
        int databaseSizeBeforeUpdate = empRepository.findAll().size();
        emp.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(emp))
            )
            .andExpect(status().isBadRequest());

        // Validate the Emp in the database
        List<Emp> empList = empRepository.findAll();
        assertThat(empList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmp() throws Exception {
        int databaseSizeBeforeUpdate = empRepository.findAll().size();
        emp.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(emp)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Emp in the database
        List<Emp> empList = empRepository.findAll();
        assertThat(empList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmp() throws Exception {
        // Initialize the database
        empRepository.saveAndFlush(emp);

        int databaseSizeBeforeDelete = empRepository.findAll().size();

        // Delete the emp
        restEmpMockMvc.perform(delete(ENTITY_API_URL_ID, emp.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Emp> empList = empRepository.findAll();
        assertThat(empList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
