package br.com.lsat.coachapp.repository.search;

import br.com.lsat.coachapp.domain.MovementSet;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the MovementSet entity.
 */
public interface MovementSetSearchRepository extends ElasticsearchRepository<MovementSet, Long> {
}
