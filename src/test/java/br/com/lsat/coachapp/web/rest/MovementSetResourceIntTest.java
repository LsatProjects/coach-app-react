package br.com.lsat.coachapp.web.rest;

import br.com.lsat.coachapp.CoachappApp;

import br.com.lsat.coachapp.domain.MovementSet;
import br.com.lsat.coachapp.repository.MovementSetRepository;
import br.com.lsat.coachapp.repository.search.MovementSetSearchRepository;
import br.com.lsat.coachapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;


import static br.com.lsat.coachapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.lsat.coachapp.domain.enumeration.Unit;
import br.com.lsat.coachapp.domain.enumeration.Level;
/**
 * Test class for the MovementSetResource REST controller.
 *
 * @see MovementSetResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoachappApp.class)
public class MovementSetResourceIntTest {

    private static final Unit DEFAULT_UNIT = Unit.REP;
    private static final Unit UPDATED_UNIT = Unit.CAL;

    private static final Integer DEFAULT_ROUND = 1;
    private static final Integer UPDATED_ROUND = 2;

    private static final Float DEFAULT_WEIGHT = 1F;
    private static final Float UPDATED_WEIGHT = 2F;

    private static final Level DEFAULT_LEVEL = Level.RX;
    private static final Level UPDATED_LEVEL = Level.SCALE;

    @Autowired
    private MovementSetRepository movementSetRepository;

    /**
     * This repository is mocked in the br.com.lsat.coachapp.repository.search test package.
     *
     * @see br.com.lsat.coachapp.repository.search.MovementSetSearchRepositoryMockConfiguration
     */
    @Autowired
    private MovementSetSearchRepository mockMovementSetSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMovementSetMockMvc;

    private MovementSet movementSet;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MovementSetResource movementSetResource = new MovementSetResource(movementSetRepository, mockMovementSetSearchRepository);
        this.restMovementSetMockMvc = MockMvcBuilders.standaloneSetup(movementSetResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MovementSet createEntity(EntityManager em) {
        MovementSet movementSet = new MovementSet()
            .unit(DEFAULT_UNIT)
            .round(DEFAULT_ROUND)
            .weight(DEFAULT_WEIGHT)
            .level(DEFAULT_LEVEL);
        return movementSet;
    }

    @Before
    public void initTest() {
        movementSet = createEntity(em);
    }

    @Test
    @Transactional
    public void createMovementSet() throws Exception {
        int databaseSizeBeforeCreate = movementSetRepository.findAll().size();

        // Create the MovementSet
        restMovementSetMockMvc.perform(post("/api/movement-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movementSet)))
            .andExpect(status().isCreated());

        // Validate the MovementSet in the database
        List<MovementSet> movementSetList = movementSetRepository.findAll();
        assertThat(movementSetList).hasSize(databaseSizeBeforeCreate + 1);
        MovementSet testMovementSet = movementSetList.get(movementSetList.size() - 1);
        assertThat(testMovementSet.getUnit()).isEqualTo(DEFAULT_UNIT);
        assertThat(testMovementSet.getRound()).isEqualTo(DEFAULT_ROUND);
        assertThat(testMovementSet.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testMovementSet.getLevel()).isEqualTo(DEFAULT_LEVEL);

        // Validate the MovementSet in Elasticsearch
        verify(mockMovementSetSearchRepository, times(1)).save(testMovementSet);
    }

    @Test
    @Transactional
    public void createMovementSetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = movementSetRepository.findAll().size();

        // Create the MovementSet with an existing ID
        movementSet.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMovementSetMockMvc.perform(post("/api/movement-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movementSet)))
            .andExpect(status().isBadRequest());

        // Validate the MovementSet in the database
        List<MovementSet> movementSetList = movementSetRepository.findAll();
        assertThat(movementSetList).hasSize(databaseSizeBeforeCreate);

        // Validate the MovementSet in Elasticsearch
        verify(mockMovementSetSearchRepository, times(0)).save(movementSet);
    }

    @Test
    @Transactional
    public void getAllMovementSets() throws Exception {
        // Initialize the database
        movementSetRepository.saveAndFlush(movementSet);

        // Get all the movementSetList
        restMovementSetMockMvc.perform(get("/api/movement-sets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(movementSet.getId().intValue())))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT.toString())))
            .andExpect(jsonPath("$.[*].round").value(hasItem(DEFAULT_ROUND)))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL.toString())));
    }
    
    @Test
    @Transactional
    public void getMovementSet() throws Exception {
        // Initialize the database
        movementSetRepository.saveAndFlush(movementSet);

        // Get the movementSet
        restMovementSetMockMvc.perform(get("/api/movement-sets/{id}", movementSet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(movementSet.getId().intValue()))
            .andExpect(jsonPath("$.unit").value(DEFAULT_UNIT.toString()))
            .andExpect(jsonPath("$.round").value(DEFAULT_ROUND))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMovementSet() throws Exception {
        // Get the movementSet
        restMovementSetMockMvc.perform(get("/api/movement-sets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMovementSet() throws Exception {
        // Initialize the database
        movementSetRepository.saveAndFlush(movementSet);

        int databaseSizeBeforeUpdate = movementSetRepository.findAll().size();

        // Update the movementSet
        MovementSet updatedMovementSet = movementSetRepository.findById(movementSet.getId()).get();
        // Disconnect from session so that the updates on updatedMovementSet are not directly saved in db
        em.detach(updatedMovementSet);
        updatedMovementSet
            .unit(UPDATED_UNIT)
            .round(UPDATED_ROUND)
            .weight(UPDATED_WEIGHT)
            .level(UPDATED_LEVEL);

        restMovementSetMockMvc.perform(put("/api/movement-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMovementSet)))
            .andExpect(status().isOk());

        // Validate the MovementSet in the database
        List<MovementSet> movementSetList = movementSetRepository.findAll();
        assertThat(movementSetList).hasSize(databaseSizeBeforeUpdate);
        MovementSet testMovementSet = movementSetList.get(movementSetList.size() - 1);
        assertThat(testMovementSet.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testMovementSet.getRound()).isEqualTo(UPDATED_ROUND);
        assertThat(testMovementSet.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testMovementSet.getLevel()).isEqualTo(UPDATED_LEVEL);

        // Validate the MovementSet in Elasticsearch
        verify(mockMovementSetSearchRepository, times(1)).save(testMovementSet);
    }

    @Test
    @Transactional
    public void updateNonExistingMovementSet() throws Exception {
        int databaseSizeBeforeUpdate = movementSetRepository.findAll().size();

        // Create the MovementSet

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMovementSetMockMvc.perform(put("/api/movement-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movementSet)))
            .andExpect(status().isBadRequest());

        // Validate the MovementSet in the database
        List<MovementSet> movementSetList = movementSetRepository.findAll();
        assertThat(movementSetList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MovementSet in Elasticsearch
        verify(mockMovementSetSearchRepository, times(0)).save(movementSet);
    }

    @Test
    @Transactional
    public void deleteMovementSet() throws Exception {
        // Initialize the database
        movementSetRepository.saveAndFlush(movementSet);

        int databaseSizeBeforeDelete = movementSetRepository.findAll().size();

        // Get the movementSet
        restMovementSetMockMvc.perform(delete("/api/movement-sets/{id}", movementSet.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MovementSet> movementSetList = movementSetRepository.findAll();
        assertThat(movementSetList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MovementSet in Elasticsearch
        verify(mockMovementSetSearchRepository, times(1)).deleteById(movementSet.getId());
    }

    @Test
    @Transactional
    public void searchMovementSet() throws Exception {
        // Initialize the database
        movementSetRepository.saveAndFlush(movementSet);
        when(mockMovementSetSearchRepository.search(queryStringQuery("id:" + movementSet.getId())))
            .thenReturn(Collections.singletonList(movementSet));
        // Search the movementSet
        restMovementSetMockMvc.perform(get("/api/_search/movement-sets?query=id:" + movementSet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(movementSet.getId().intValue())))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT.toString())))
            .andExpect(jsonPath("$.[*].round").value(hasItem(DEFAULT_ROUND)))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MovementSet.class);
        MovementSet movementSet1 = new MovementSet();
        movementSet1.setId(1L);
        MovementSet movementSet2 = new MovementSet();
        movementSet2.setId(movementSet1.getId());
        assertThat(movementSet1).isEqualTo(movementSet2);
        movementSet2.setId(2L);
        assertThat(movementSet1).isNotEqualTo(movementSet2);
        movementSet1.setId(null);
        assertThat(movementSet1).isNotEqualTo(movementSet2);
    }
}
