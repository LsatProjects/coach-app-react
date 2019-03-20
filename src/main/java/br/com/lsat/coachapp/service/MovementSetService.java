package br.com.lsat.coachapp.service;

import br.com.lsat.coachapp.domain.MovementSet;
import br.com.lsat.coachapp.repository.MovementSetRepository;
import br.com.lsat.coachapp.repository.search.MovementSetSearchRepository;
import br.com.lsat.coachapp.service.dto.MovementSetDTO;
import br.com.lsat.coachapp.service.mapper.MovementSetMapper;
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
 * Service Implementation for managing MovementSet.
 */
@Service
@Transactional
public class MovementSetService {

    private final Logger log = LoggerFactory.getLogger(MovementSetService.class);

    private final MovementSetRepository movementSetRepository;

    private final MovementSetMapper movementSetMapper;

    private final MovementSetSearchRepository movementSetSearchRepository;

    public MovementSetService(MovementSetRepository movementSetRepository, MovementSetMapper movementSetMapper, MovementSetSearchRepository movementSetSearchRepository) {
        this.movementSetRepository = movementSetRepository;
        this.movementSetMapper = movementSetMapper;
        this.movementSetSearchRepository = movementSetSearchRepository;
    }

    /**
     * Save a movementSet.
     *
     * @param movementSetDTO the entity to save
     * @return the persisted entity
     */
    public MovementSetDTO save(MovementSetDTO movementSetDTO) {
        log.debug("Request to save MovementSet : {}", movementSetDTO);

        MovementSet movementSet = movementSetMapper.toEntity(movementSetDTO);
        movementSet = movementSetRepository.save(movementSet);
        MovementSetDTO result = movementSetMapper.toDto(movementSet);
        movementSetSearchRepository.save(movementSet);
        return result;
    }

    /**
     * Get all the movementSets.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<MovementSetDTO> findAll() {
        log.debug("Request to get all MovementSets");
        return movementSetRepository.findAll().stream()
            .map(movementSetMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one movementSet by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<MovementSetDTO> findOne(Long id) {
        log.debug("Request to get MovementSet : {}", id);
        return movementSetRepository.findById(id)
            .map(movementSetMapper::toDto);
    }

    /**
     * Delete the movementSet by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MovementSet : {}", id);
        movementSetRepository.deleteById(id);
        movementSetSearchRepository.deleteById(id);
    }

    /**
     * Search for the movementSet corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<MovementSetDTO> search(String query) {
        log.debug("Request to search MovementSets for query {}", query);
        return StreamSupport
            .stream(movementSetSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(movementSetMapper::toDto)
            .collect(Collectors.toList());
    }
}
