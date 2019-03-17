package br.com.lsat.coachapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.lsat.coachapp.domain.Sport;
import br.com.lsat.coachapp.repository.SportRepository;
import br.com.lsat.coachapp.repository.search.SportSearchRepository;
import br.com.lsat.coachapp.web.rest.errors.BadRequestAlertException;
import br.com.lsat.coachapp.web.rest.util.HeaderUtil;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Sport.
 */
@RestController
@RequestMapping("/api")
public class SportResource {

    private final Logger log = LoggerFactory.getLogger(SportResource.class);

    private static final String ENTITY_NAME = "sport";

    private final SportRepository sportRepository;

    private final SportSearchRepository sportSearchRepository;

    public SportResource(SportRepository sportRepository, SportSearchRepository sportSearchRepository) {
        this.sportRepository = sportRepository;
        this.sportSearchRepository = sportSearchRepository;
    }

    /**
     * POST  /sports : Create a new sport.
     *
     * @param sport the sport to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sport, or with status 400 (Bad Request) if the sport has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sports")
    @Timed
    public ResponseEntity<Sport> createSport(@Valid @RequestBody Sport sport) throws URISyntaxException {
        log.debug("REST request to save Sport : {}", sport);
        if (sport.getId() != null) {
            throw new BadRequestAlertException("A new sport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Sport result = sportRepository.save(sport);
        sportSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/sports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sports : Updates an existing sport.
     *
     * @param sport the sport to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sport,
     * or with status 400 (Bad Request) if the sport is not valid,
     * or with status 500 (Internal Server Error) if the sport couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sports")
    @Timed
    public ResponseEntity<Sport> updateSport(@Valid @RequestBody Sport sport) throws URISyntaxException {
        log.debug("REST request to update Sport : {}", sport);
        if (sport.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Sport result = sportRepository.save(sport);
        sportSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sport.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sports : get all the sports.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sports in body
     */
    @GetMapping("/sports")
    @Timed
    public List<Sport> getAllSports() {
        log.debug("REST request to get all Sports");
        return sportRepository.findAll();
    }

    /**
     * GET  /sports/:id : get the "id" sport.
     *
     * @param id the id of the sport to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sport, or with status 404 (Not Found)
     */
    @GetMapping("/sports/{id}")
    @Timed
    public ResponseEntity<Sport> getSport(@PathVariable Long id) {
        log.debug("REST request to get Sport : {}", id);
        Optional<Sport> sport = sportRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sport);
    }

    /**
     * DELETE  /sports/:id : delete the "id" sport.
     *
     * @param id the id of the sport to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sports/{id}")
    @Timed
    public ResponseEntity<Void> deleteSport(@PathVariable Long id) {
        log.debug("REST request to delete Sport : {}", id);

        sportRepository.deleteById(id);
        sportSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/sports?query=:query : search for the sport corresponding
     * to the query.
     *
     * @param query the query of the sport search
     * @return the result of the search
     */
    @GetMapping("/_search/sports")
    @Timed
    public List<Sport> searchSports(@RequestParam String query) {
        log.debug("REST request to search Sports for query {}", query);
        return StreamSupport
            .stream(sportSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
