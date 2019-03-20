package br.com.lsat.coachapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.lsat.coachapp.service.MovementSetService;
import br.com.lsat.coachapp.web.rest.errors.BadRequestAlertException;
import br.com.lsat.coachapp.web.rest.util.HeaderUtil;
import br.com.lsat.coachapp.service.dto.MovementSetDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
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

    private final MovementSetService movementSetService;

    public MovementSetResource(MovementSetService movementSetService) {
        this.movementSetService = movementSetService;
    }

    /**
     * POST  /movement-sets : Create a new movementSet.
     *
     * @param movementSetDTO the movementSetDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new movementSetDTO, or with status 400 (Bad Request) if the movementSet has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/movement-sets")
    @Timed
    public ResponseEntity<MovementSetDTO> createMovementSet(@RequestBody MovementSetDTO movementSetDTO) throws URISyntaxException {
        log.debug("REST request to save MovementSet : {}", movementSetDTO);
        if (movementSetDTO.getId() != null) {
            throw new BadRequestAlertException("A new movementSet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MovementSetDTO result = movementSetService.save(movementSetDTO);
        return ResponseEntity.created(new URI("/api/movement-sets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /movement-sets : Updates an existing movementSet.
     *
     * @param movementSetDTO the movementSetDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated movementSetDTO,
     * or with status 400 (Bad Request) if the movementSetDTO is not valid,
     * or with status 500 (Internal Server Error) if the movementSetDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/movement-sets")
    @Timed
    public ResponseEntity<MovementSetDTO> updateMovementSet(@RequestBody MovementSetDTO movementSetDTO) throws URISyntaxException {
        log.debug("REST request to update MovementSet : {}", movementSetDTO);
        if (movementSetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MovementSetDTO result = movementSetService.save(movementSetDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, movementSetDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /movement-sets : get all the movementSets.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of movementSets in body
     */
    @GetMapping("/movement-sets")
    @Timed
    public List<MovementSetDTO> getAllMovementSets() {
        log.debug("REST request to get all MovementSets");
        return movementSetService.findAll();
    }

    /**
     * GET  /movement-sets/:id : get the "id" movementSet.
     *
     * @param id the id of the movementSetDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the movementSetDTO, or with status 404 (Not Found)
     */
    @GetMapping("/movement-sets/{id}")
    @Timed
    public ResponseEntity<MovementSetDTO> getMovementSet(@PathVariable Long id) {
        log.debug("REST request to get MovementSet : {}", id);
        Optional<MovementSetDTO> movementSetDTO = movementSetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(movementSetDTO);
    }

    /**
     * DELETE  /movement-sets/:id : delete the "id" movementSet.
     *
     * @param id the id of the movementSetDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/movement-sets/{id}")
    @Timed
    public ResponseEntity<Void> deleteMovementSet(@PathVariable Long id) {
        log.debug("REST request to delete MovementSet : {}", id);
        movementSetService.delete(id);
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
    public List<MovementSetDTO> searchMovementSets(@RequestParam String query) {
        log.debug("REST request to search MovementSets for query {}", query);
        return movementSetService.search(query);
    }

}
