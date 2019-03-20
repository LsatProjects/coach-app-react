package br.com.lsat.coachapp.service;

import br.com.lsat.coachapp.domain.Phase;
import br.com.lsat.coachapp.repository.PhaseRepository;
import br.com.lsat.coachapp.repository.search.PhaseSearchRepository;
import br.com.lsat.coachapp.service.dto.PhaseDTO;
import br.com.lsat.coachapp.service.mapper.PhaseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Phase.
 */
@Service
@Transactional
public class PhaseService {

    private final Logger log = LoggerFactory.getLogger(PhaseService.class);

    private final PhaseRepository phaseRepository;

    private final PhaseMapper phaseMapper;

    private final PhaseSearchRepository phaseSearchRepository;

    public PhaseService(PhaseRepository phaseRepository, PhaseMapper phaseMapper, PhaseSearchRepository phaseSearchRepository) {
        this.phaseRepository = phaseRepository;
        this.phaseMapper = phaseMapper;
        this.phaseSearchRepository = phaseSearchRepository;
    }

    /**
     * Save a phase.
     *
     * @param phaseDTO the entity to save
     * @return the persisted entity
     */
    public PhaseDTO save(PhaseDTO phaseDTO) {
        log.debug("Request to save Phase : {}", phaseDTO);

        Phase phase = phaseMapper.toEntity(phaseDTO);
        phase = phaseRepository.save(phase);
        PhaseDTO result = phaseMapper.toDto(phase);
        phaseSearchRepository.save(phase);
        return result;
    }

    /**
     * Get all the phases.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PhaseDTO> findAll() {
        log.debug("Request to get all Phases");
        return phaseRepository.findAll().stream()
            .map(phaseMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one phase by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<PhaseDTO> findOne(Long id) {
        log.debug("Request to get Phase : {}", id);
        return phaseRepository.findById(id)
            .map(phaseMapper::toDto);
    }

    /**
     * Delete the phase by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Phase : {}", id);
        phaseRepository.deleteById(id);
        phaseSearchRepository.deleteById(id);
    }

    /**
     * Search for the phase corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PhaseDTO> search(String query) {
        log.debug("Request to search Phases for query {}", query);
        return StreamSupport
            .stream(phaseSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(phaseMapper::toDto)
            .collect(Collectors.toList());
    }
}
