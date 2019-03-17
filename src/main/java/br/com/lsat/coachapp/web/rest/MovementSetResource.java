package br.com.lsat.coachapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.lsat.coachapp.domain.MovementSet;
import br.com.lsat.coachapp.repository.MovementSetRepository;
import br.com.lsat.coachapp.repository.search.MovementSetSearchRepository;
import br.com.lsat.coachapp.web.rest.errors.BadRequestAlertException;
import br.com.lsat.coachapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing MovementSet.
 */
@RestController
@RequestMapping("/api")
public class MovementSetResource {

    private final Logger log = LoggerFactory.getLogger(MovementSetResource.class);

    private static final String ENTITY_NAME = "movementSet";

    private final MovementSetRepository movementSetRepository;

    private final MovementSetSearchRepository movementSetSearchRepository;

    public MovementSetResource(MovementSetRepository movementSetRepository, MovementSetSearchRepository movementSetSearchRepository) {
        this.movementSetRepository = movementSetRepository;
        this.movementSetSearchRepository = movementSetSearchRepository;
    }

    /**
     * POST  /movement-sets : Create a new movementSet.
     *
     * @param movementSet the movementSet to create
     * @return the ResponseEntity with status 201 (Created) and with body the new movementSet, or with status 400 (Bad Request) if the movementSet has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/movement-sets")
    @Timed
    public ResponseEntity<MovementSet> createMovementSet(@RequestBody MovementSet movementSet) throws URISyntaxException {
        log.debug("REST request to save MovementSet : {}", movementSet);
        if (movementSet.getId() != null) {
            throw new BadRequestAlertException("A new movementSet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MovementSet result = movementSetRepository.save(movementSet);
        movementSetSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/movement-sets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /movement-sets : Updates an existing movementSet.
     *
     * @param movementSet the movementSet to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated movementSet,
     * or with status 400 (Bad Request) if the movementSet is not valid,
     * or with status 500 (Internal Server Error) if the movementSet couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/movement-sets")
    @Timed
    public ResponseEntity<MovementSet> updateMovementSet(@RequestBody MovementSet movementSet) throws URISyntaxException {
        log.debug("REST request to update MovementSet : {}", movementSet);
        if (movementSet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MovementSet result = movementSetRepository.save(movementSet);
        movementSetSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, movementSet.getId().toString()))
            .body(result);
    }

    /**
     * GET  /movement-sets : get all the movementSets.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of movementSets in body
     */
    @GetMapping("/movement-sets")
    @Timed
    public List<MovementSet> getAllMovementSets() {
        log.debug("REST request to get all MovementSets");
        return movementSetRepository.findAll();
    }

    /**
     * GET  /movement-sets/:id : get the "id" movementSet.
     *
     * @param id the id of the movementSet to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the movementSet, or with status 404 (Not Found)
     */
    @GetMapping("/movement-sets/{id}")
    @Timed
    public ResponseEntity<MovementSet> getMovementSet(@PathVariable Long id) {
        log.debug("REST request to get MovementSet : {}", id);
        Optional<MovementSet> movementSet = movementSetRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(movementSet);
    }

    /**
     * DELETE  /movement-sets/:id : delete the "id" movementSet.
     *
     * @param id the id of the movementSet to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/movement-sets/{id}")
    @Timed
    public ResponseEntity<Void> deleteMovementSet(@PathVariable Long id) {
        log.debug("REST request to delete MovementSet : {}", id);

        movementSetRepository.deleteById(id);
        movementSetSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/movement-sets?query=:query : search for the movementSet corresponding
     * to the query.
     *
     * @param query the query of the movementSet search
     * @return the result of the search
     */
    @GetMapping("/_search/movement-sets")
    @Timed
    public List<MovementSet> searchMovementSets(@RequestParam String query) {
        log.debug("REST request to search MovementSets for query {}", query);
        return StreamSupport
            .stream(movementSetSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
