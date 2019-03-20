package br.com.lsat.coachapp.service;

import br.com.lsat.coachapp.domain.Training;
import br.com.lsat.coachapp.repository.TrainingRepository;
import br.com.lsat.coachapp.repository.search.TrainingSearchRepository;
import br.com.lsat.coachapp.service.dto.TrainingDTO;
import br.com.lsat.coachapp.service.mapper.TrainingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Training.
 */
@Service
@Transactional
public class TrainingService {

    private final Logger log = LoggerFactory.getLogger(TrainingService.class);

    private final TrainingRepository trainingRepository;

    private final TrainingMapper trainingMapper;

    private final TrainingSearchRepository trainingSearchRepository;

    public TrainingService(TrainingRepository trainingRepository, TrainingMapper trainingMapper, TrainingSearchRepository trainingSearchRepository) {
        this.trainingRepository = trainingRepository;
        this.trainingMapper = trainingMapper;
        this.trainingSearchRepository = trainingSearchRepository;
    }

    /**
     * Save a training.
     *
     * @param trainingDTO the entity to save
     * @return the persisted entity
     */
    public TrainingDTO save(TrainingDTO trainingDTO) {
        log.debug("Request to save Training : {}", trainingDTO);

        Training training = trainingMapper.toEntity(trainingDTO);
        training = trainingRepository.save(training);
        TrainingDTO result = trainingMapper.toDto(training);
        trainingSearchRepository.save(training);
        return result;
    }

    /**
     * Get all the trainings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TrainingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Trainings");
        return trainingRepository.findAll(pageable)
            .map(trainingMapper::toDto);
    }


    /**
     * Get one training by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<TrainingDTO> findOne(Long id) {
        log.debug("Request to get Training : {}", id);
        return trainingRepository.findById(id)
            .map(trainingMapper::toDto);
    }

    /**
     * Delete the training by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Training : {}", id);
        trainingRepository.deleteById(id);
        trainingSearchRepository.deleteById(id);
    }

    /**
     * Search for the training corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TrainingDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Trainings for query {}", query);
        return trainingSearchRepository.search(queryStringQuery(query), pageable)
            .map(trainingMapper::toDto);
    }
}
