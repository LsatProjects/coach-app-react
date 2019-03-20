package br.com.lsat.coachapp.web.rest;

import br.com.lsat.coachapp.CoachappApp;

import br.com.lsat.coachapp.domain.Phase;
import br.com.lsat.coachapp.repository.PhaseRepository;
import br.com.lsat.coachapp.repository.search.PhaseSearchRepository;
import br.com.lsat.coachapp.service.PhaseService;
import br.com.lsat.coachapp.service.dto.PhaseDTO;
import br.com.lsat.coachapp.service.mapper.PhaseMapper;
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

/**
 * Test class for the PhaseResource REST controller.
 *
 * @see PhaseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoachappApp.class)
public class PhaseResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private PhaseRepository phaseRepository;

    @Autowired
    private PhaseMapper phaseMapper;

    @Autowired
    private PhaseService phaseService;

    /**
     * This repository is mocked in the br.com.lsat.coachapp.repository.search test package.
     *
     * @see br.com.lsat.coachapp.repository.search.PhaseSearchRepositoryMockConfiguration
     */
    @Autowired
    private PhaseSearchRepository mockPhaseSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPhaseMockMvc;

    private Phase phase;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PhaseResource phaseResource = new PhaseResource(phaseService);
        this.restPhaseMockMvc = MockMvcBuilders.standaloneSetup(phaseResource)
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
    public static Phase createEntity(EntityManager em) {
        Phase phase = new Phase()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return phase;
    }

    @Before
    public void initTest() {
        phase = createEntity(em);
    }

    @Test
    @Transactional
    public void createPhase() throws Exception {
        int databaseSizeBeforeCreate = phaseRepository.findAll().size();

        // Create the Phase
        PhaseDTO phaseDTO = phaseMapper.toDto(phase);
        restPhaseMockMvc.perform(post("/api/phases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(phaseDTO)))
            .andExpect(status().isCreated());

        // Validate the Phase in the database
        List<Phase> phaseList = phaseRepository.findAll();
        assertThat(phaseList).hasSize(databaseSizeBeforeCreate + 1);
        Phase testPhase = phaseList.get(phaseList.size() - 1);
        assertThat(testPhase.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPhase.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the Phase in Elasticsearch
        verify(mockPhaseSearchRepository, times(1)).save(testPhase);
    }

    @Test
    @Transactional
    public void createPhaseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = phaseRepository.findAll().size();

        // Create the Phase with an existing ID
        phase.setId(1L);
        PhaseDTO phaseDTO = phaseMapper.toDto(phase);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPhaseMockMvc.perform(post("/api/phases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(phaseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Phase in the database
        List<Phase> phaseList = phaseRepository.findAll();
        assertThat(phaseList).hasSize(databaseSizeBeforeCreate);

        // Validate the Phase in Elasticsearch
        verify(mockPhaseSearchRepository, times(0)).save(phase);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = phaseRepository.findAll().size();
        // set the field null
        phase.setName(null);

        // Create the Phase, which fails.
        PhaseDTO phaseDTO = phaseMapper.toDto(phase);

        restPhaseMockMvc.perform(post("/api/phases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(phaseDTO)))
            .andExpect(status().isBadRequest());

        List<Phase> phaseList = phaseRepository.findAll();
        assertThat(phaseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPhases() throws Exception {
        // Initialize the database
        phaseRepository.saveAndFlush(phase);

        // Get all the phaseList
        restPhaseMockMvc.perform(get("/api/phases?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(phase.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getPhase() throws Exception {
        // Initialize the database
        phaseRepository.saveAndFlush(phase);

        // Get the phase
        restPhaseMockMvc.perform(get("/api/phases/{id}", phase.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(phase.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPhase() throws Exception {
        // Get the phase
        restPhaseMockMvc.perform(get("/api/phases/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePhase() throws Exception {
        // Initialize the database
        phaseRepository.saveAndFlush(phase);

        int databaseSizeBeforeUpdate = phaseRepository.findAll().size();

        // Update the phase
        Phase updatedPhase = phaseRepository.findById(phase.getId()).get();
        // Disconnect from session so that the updates on updatedPhase are not directly saved in db
        em.detach(updatedPhase);
        updatedPhase
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        PhaseDTO phaseDTO = phaseMapper.toDto(updatedPhase);

        restPhaseMockMvc.perform(put("/api/phases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(phaseDTO)))
            .andExpect(status().isOk());

        // Validate the Phase in the database
        List<Phase> phaseList = phaseRepository.findAll();
        assertThat(phaseList).hasSize(databaseSizeBeforeUpdate);
        Phase testPhase = phaseList.get(phaseList.size() - 1);
        assertThat(testPhase.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPhase.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the Phase in Elasticsearch
        verify(mockPhaseSearchRepository, times(1)).save(testPhase);
    }

    @Test
    @Transactional
    public void updateNonExistingPhase() throws Exception {
        int databaseSizeBeforeUpdate = phaseRepository.findAll().size();

        // Create the Phase
        PhaseDTO phaseDTO = phaseMapper.toDto(phase);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhaseMockMvc.perform(put("/api/phases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(phaseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Phase in the database
        List<Phase> phaseList = phaseRepository.findAll();
        assertThat(phaseList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Phase in Elasticsearch
        verify(mockPhaseSearchRepository, times(0)).save(phase);
    }

    @Test
    @Transactional
    public void deletePhase() throws Exception {
        // Initialize the database
        phaseRepository.saveAndFlush(phase);

        int databaseSizeBeforeDelete = phaseRepository.findAll().size();

        // Get the phase
        restPhaseMockMvc.perform(delete("/api/phases/{id}", phase.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Phase> phaseList = phaseRepository.findAll();
        assertThat(phaseList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Phase in Elasticsearch
        verify(mockPhaseSearchRepository, times(1)).deleteById(phase.getId());
    }

    @Test
    @Transactional
    public void searchPhase() throws Exception {
        // Initialize the database
        phaseRepository.saveAndFlush(phase);
        when(mockPhaseSearchRepository.search(queryStringQuery("id:" + phase.getId())))
            .thenReturn(Collections.singletonList(phase));
        // Search the phase
        restPhaseMockMvc.perform(get("/api/_search/phases?query=id:" + phase.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(phase.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Phase.class);
        Phase phase1 = new Phase();
        phase1.setId(1L);
        Phase phase2 = new Phase();
        phase2.setId(phase1.getId());
        assertThat(phase1).isEqualTo(phase2);
        phase2.setId(2L);
        assertThat(phase1).isNotEqualTo(phase2);
        phase1.setId(null);
        assertThat(phase1).isNotEqualTo(phase2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PhaseDTO.class);
        PhaseDTO phaseDTO1 = new PhaseDTO();
        phaseDTO1.setId(1L);
        PhaseDTO phaseDTO2 = new PhaseDTO();
        assertThat(phaseDTO1).isNotEqualTo(phaseDTO2);
        phaseDTO2.setId(phaseDTO1.getId());
        assertThat(phaseDTO1).isEqualTo(phaseDTO2);
        phaseDTO2.setId(2L);
        assertThat(phaseDTO1).isNotEqualTo(phaseDTO2);
        phaseDTO1.setId(null);
        assertThat(phaseDTO1).isNotEqualTo(phaseDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(phaseMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(phaseMapper.fromId(null)).isNull();
    }
}
