package br.com.lsat.coachapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.lsat.coachapp.service.TrainingService;
import br.com.lsat.coachapp.web.rest.errors.BadRequestAlertException;
import br.com.lsat.coachapp.web.rest.util.HeaderUtil;
import br.com.lsat.coachapp.web.rest.util.PaginationUtil;
import br.com.lsat.coachapp.service.dto.TrainingDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Training.
 */
@RestController
@RequestMapping("/api")
public class TrainingResource {

    private final Logger log = LoggerFactory.getLogger(TrainingResource.class);

    private static final String ENTITY_NAME = "training";

    private final TrainingService trainingService;

    public TrainingResource(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    /**
     * POST  /trainings : Create a new training.
     *
     * @param trainingDTO the trainingDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new trainingDTO, or with status 400 (Bad Request) if the training has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/trainings")
    @Timed
    public ResponseEntity<TrainingDTO> createTraining(@RequestBody TrainingDTO trainingDTO) throws URISyntaxException {
        log.debug("REST request to save Training : {}", trainingDTO);
        if (trainingDTO.getId() != null) {
            throw new BadRequestAlertException("A new training cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TrainingDTO result = trainingService.save(trainingDTO);
        return ResponseEntity.created(new URI("/api/trainings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /trainings : Updates an existing training.
     *
     * @param trainingDTO the trainingDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated trainingDTO,
     * or with status 400 (Bad Request) if the trainingDTO is not valid,
     * or with status 500 (Internal Server Error) if the trainingDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/trainings")
    @Timed
    public ResponseEntity<TrainingDTO> updateTraining(@RequestBody TrainingDTO trainingDTO) throws URISyntaxException {
        log.debug("REST request to update Training : {}", trainingDTO);
        if (trainingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TrainingDTO result = trainingService.save(trainingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, trainingDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /trainings : get all the trainings.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of trainings in body
     */
    @GetMapping("/trainings")
    @Timed
    public ResponseEntity<List<TrainingDTO>> getAllTrainings(Pageable pageable) {
        log.debug("REST request to get a page of Trainings");
        Page<TrainingDTO> page = trainingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/trainings");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /trainings/:id : get the "id" training.
     *
     * @param id the id of the trainingDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the trainingDTO, or with status 404 (Not Found)
     */
    @GetMapping("/trainings/{id}")
    @Timed
    public ResponseEntity<TrainingDTO> getTraining(@PathVariable Long id) {
        log.debug("REST request to get Training : {}", id);
        Optional<TrainingDTO> trainingDTO = trainingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(trainingDTO);
    }

    /**
     * DELETE  /trainings/:id : delete the "id" training.
     *
     * @param id the id of the trainingDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/trainings/{id}")
    @Timed
    public ResponseEntity<Void> deleteTraining(@PathVariable Long id) {
        log.debug("REST request to delete Training : {}", id);
        trainingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/trainings?query=:query : search for the training corresponding
     * to the query.
     *
     * @param query the query of the training search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/trainings")
    @Timed
    public ResponseEntity<List<TrainingDTO>> searchTrainings(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Trainings for query {}", query);
        Page<TrainingDTO> page = trainingService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/trainings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
