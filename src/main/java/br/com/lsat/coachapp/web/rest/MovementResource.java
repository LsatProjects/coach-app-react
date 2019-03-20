package br.com.lsat.coachapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.lsat.coachapp.service.MovementService;
import br.com.lsat.coachapp.web.rest.errors.BadRequestAlertException;
import br.com.lsat.coachapp.web.rest.util.HeaderUtil;
import br.com.lsat.coachapp.web.rest.util.PaginationUtil;
import br.com.lsat.coachapp.service.dto.MovementDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Movement.
 */
@RestController
@RequestMapping("/api")
public class MovementResource {

    private final Logger log = LoggerFactory.getLogger(MovementResource.class);

    private static final String ENTITY_NAME = "movement";

    private final MovementService movementService;

    public MovementResource(MovementService movementService) {
        this.movementService = movementService;
    }

    /**
     * POST  /movements : Create a new movement.
     *
     * @param movementDTO the movementDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new movementDTO, or with status 400 (Bad Request) if the movement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/movements")
    @Timed
    public ResponseEntity<MovementDTO> createMovement(@Valid @RequestBody MovementDTO movementDTO) throws URISyntaxException {
        log.debug("REST request to save Movement : {}", movementDTO);
        if (movementDTO.getId() != null) {
            throw new BadRequestAlertException("A new movement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MovementDTO result = movementService.save(movementDTO);
        return ResponseEntity.created(new URI("/api/movements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /movements : Updates an existing movement.
     *
     * @param movementDTO the movementDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated movementDTO,
     * or with status 400 (Bad Request) if the movementDTO is not valid,
     * or with status 500 (Internal Server Error) if the movementDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/movements")
    @Timed
    public ResponseEntity<MovementDTO> updateMovement(@Valid @RequestBody MovementDTO movementDTO) throws URISyntaxException {
        log.debug("REST request to update Movement : {}", movementDTO);
        if (movementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MovementDTO result = movementService.save(movementDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, movementDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /movements : get all the movements.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of movements in body
     */
    @GetMapping("/movements")
    @Timed
    public ResponseEntity<List<MovementDTO>> getAllMovements(Pageable pageable) {
        log.debug("REST request to get a page of Movements");
        Page<MovementDTO> page = movementService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/movements");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /movements/:id : get the "id" movement.
     *
     * @param id the id of the movementDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the movementDTO, or with status 404 (Not Found)
     */
    @GetMapping("/movements/{id}")
    @Timed
    public ResponseEntity<MovementDTO> getMovement(@PathVariable Long id) {
        log.debug("REST request to get Movement : {}", id);
        Optional<MovementDTO> movementDTO = movementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(movementDTO);
    }

    /**
     * DELETE  /movements/:id : delete the "id" movement.
     *
     * @param id the id of the movementDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/movements/{id}")
    @Timed
    public ResponseEntity<Void> deleteMovement(@PathVariable Long id) {
        log.debug("REST request to delete Movement : {}", id);
        movementService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/movements?query=:query : search for the movement corresponding
     * to the query.
     *
     * @param query the query of the movement search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/movements")
    @Timed
    public ResponseEntity<List<MovementDTO>> searchMovements(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Movements for query {}", query);
        Page<MovementDTO> page = movementService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/movements");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
