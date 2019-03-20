package br.com.lsat.coachapp.service;

import br.com.lsat.coachapp.domain.MovementCategory;
import br.com.lsat.coachapp.repository.MovementCategoryRepository;
import br.com.lsat.coachapp.repository.search.MovementCategorySearchRepository;
import br.com.lsat.coachapp.service.dto.MovementCategoryDTO;
import br.com.lsat.coachapp.service.mapper.MovementCategoryMapper;
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
 * Service Implementation for managing MovementCategory.
 */
@Service
@Transactional
public class MovementCategoryService {

    private final Logger log = LoggerFactory.getLogger(MovementCategoryService.class);

    private final MovementCategoryRepository movementCategoryRepository;

    private final MovementCategoryMapper movementCategoryMapper;

    private final MovementCategorySearchRepository movementCategorySearchRepository;

    public MovementCategoryService(MovementCategoryRepository movementCategoryRepository, MovementCategoryMapper movementCategoryMapper, MovementCategorySearchRepository movementCategorySearchRepository) {
        this.movementCategoryRepository = movementCategoryRepository;
        this.movementCategoryMapper = movementCategoryMapper;
        this.movementCategorySearchRepository = movementCategorySearchRepository;
    }

    /**
     * Save a movementCategory.
     *
     * @param movementCategoryDTO the entity to save
     * @return the persisted entity
     */
    public MovementCategoryDTO save(MovementCategoryDTO movementCategoryDTO) {
        log.debug("Request to save MovementCategory : {}", movementCategoryDTO);

        MovementCategory movementCategory = movementCategoryMapper.toEntity(movementCategoryDTO);
        movementCategory = movementCategoryRepository.save(movementCategory);
        MovementCategoryDTO result = movementCategoryMapper.toDto(movementCategory);
        movementCategorySearchRepository.save(movementCategory);
        return result;
    }

    /**
     * Get all the movementCategories.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<MovementCategoryDTO> findAll() {
        log.debug("Request to get all MovementCategories");
        return movementCategoryRepository.findAll().stream()
            .map(movementCategoryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one movementCategory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<MovementCategoryDTO> findOne(Long id) {
        log.debug("Request to get MovementCategory : {}", id);
        return movementCategoryRepository.findById(id)
            .map(movementCategoryMapper::toDto);
    }

    /**
     * Delete the movementCategory by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MovementCategory : {}", id);
        movementCategoryRepository.deleteById(id);
        movementCategorySearchRepository.deleteById(id);
    }

    /**
     * Search for the movementCategory corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<MovementCategoryDTO> search(String query) {
        log.debug("Request to search MovementCategories for query {}", query);
        return StreamSupport
            .stream(movementCategorySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(movementCategoryMapper::toDto)
            .collect(Collectors.toList());
    }
}
