package br.com.lsat.coachapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.lsat.coachapp.service.PhaseService;
import br.com.lsat.coachapp.web.rest.errors.BadRequestAlertException;
import br.com.lsat.coachapp.web.rest.util.HeaderUtil;
import br.com.lsat.coachapp.service.dto.PhaseDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing Phase.
 */
@RestController
@RequestMapping("/api")
public class PhaseResource {

    private final Logger log = LoggerFactory.getLogger(PhaseResource.class);

    private static final String ENTITY_NAME = "phase";

    private final PhaseService phaseService;

    public PhaseResource(PhaseService phaseService) {
        this.phaseService = phaseService;
    }

    /**
     * POST  /phases : Create a new phase.
     *
     * @param phaseDTO the phaseDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new phaseDTO, or with status 400 (Bad Request) if the phase has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/phases")
    @Timed
    public ResponseEntity<PhaseDTO> createPhase(@Valid @RequestBody PhaseDTO phaseDTO) throws URISyntaxException {
        log.debug("REST request to save Phase : {}", phaseDTO);
        if (phaseDTO.getId() != null) {
            throw new BadRequestAlertException("A new phase cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PhaseDTO result = phaseService.save(phaseDTO);
        return ResponseEntity.created(new URI("/api/phases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /phases : Updates an existing phase.
     *
     * @param phaseDTO the phaseDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated phaseDTO,
     * or with status 400 (Bad Request) if the phaseDTO is not valid,
     * or with status 500 (Internal Server Error) if the phaseDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/phases")
    @Timed
    public ResponseEntity<PhaseDTO> updatePhase(@Valid @RequestBody PhaseDTO phaseDTO) throws URISyntaxException {
        log.debug("REST request to update Phase : {}", phaseDTO);
        if (phaseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PhaseDTO result = phaseService.save(phaseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, phaseDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /phases : get all the phases.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of phases in body
     */
    @GetMapping("/phases")
    @Timed
    public List<PhaseDTO> getAllPhases() {
        log.debug("REST request to get all Phases");
        return phaseService.findAll();
    }

    /**
     * GET  /phases/:id : get the "id" phase.
     *
     * @param id the id of the phaseDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the phaseDTO, or with status 404 (Not Found)
     */
    @GetMapping("/phases/{id}")
    @Timed
    public ResponseEntity<PhaseDTO> getPhase(@PathVariable Long id) {
        log.debug("REST request to get Phase : {}", id);
        Optional<PhaseDTO> phaseDTO = phaseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(phaseDTO);
    }

    /**
     * DELETE  /phases/:id : delete the "id" phase.
     *
     * @param id the id of the phaseDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/phases/{id}")
    @Timed
    public ResponseEntity<Void> deletePhase(@PathVariable Long id) {
        log.debug("REST request to delete Phase : {}", id);
        phaseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/phases?query=:query : search for the phase corresponding
     * to the query.
     *
     * @param query the query of the phase search
     * @return the result of the search
     */
    @GetMapping("/_search/phases")
    @Timed
    public List<PhaseDTO> searchPhases(@RequestParam String query) {
        log.debug("REST request to search Phases for query {}", query);
        return phaseService.search(query);
    }

}
