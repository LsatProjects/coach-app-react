package br.com.lsat.coachapp.service;

import br.com.lsat.coachapp.domain.Movement;
import br.com.lsat.coachapp.repository.MovementRepository;
import br.com.lsat.coachapp.repository.search.MovementSearchRepository;
import br.com.lsat.coachapp.service.dto.MovementDTO;
import br.com.lsat.coachapp.service.mapper.MovementMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Movement.
 */
@Service
@Transactional
public class MovementService {

    private final Logger log = LoggerFactory.getLogger(MovementService.class);

    private final MovementRepository movementRepository;

    private final MovementMapper movementMapper;

    private final MovementSearchRepository movementSearchRepository;

    public MovementService(MovementRepository movementRepository, MovementMapper movementMapper, MovementSearchRepository movementSearchRepository) {
        this.movementRepository = movementRepository;
        this.movementMapper = movementMapper;
        this.movementSearchRepository = movementSearchRepository;
    }

    /**
     * Save a movement.
     *
     * @param movementDTO the entity to save
     * @return the persisted entity
     */
    public MovementDTO save(MovementDTO movementDTO) {
        log.debug("Request to save Movement : {}", movementDTO);

        Movement movement = movementMapper.toEntity(movementDTO);
        movement = movementRepository.save(movement);
        MovementDTO result = movementMapper.toDto(movement);
        movementSearchRepository.save(movement);
        return result;
    }

    /**
     * Get all the movements.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MovementDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Movements");
        return movementRepository.findAll(pageable)
            .map(movementMapper::toDto);
    }


    /**
     * Get one movement by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<MovementDTO> findOne(Long id) {
        log.debug("Request to get Movement : {}", id);
        return movementRepository.findById(id)
            .map(movementMapper::toDto);
    }

    /**
     * Delete the movement by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Movement : {}", id);
        movementRepository.deleteById(id);
        movementSearchRepository.deleteById(id);
    }

    /**
     * Search for the movement corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MovementDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Movements for query {}", query);
        return movementSearchRepository.search(queryStringQuery(query), pageable)
            .map(movementMapper::toDto);
    }
}
