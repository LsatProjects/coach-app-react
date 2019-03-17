package br.com.lsat.coachapp.repository.search;

import br.com.lsat.coachapp.domain.Phase;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Phase entity.
 */
public interface PhaseSearchRepository extends ElasticsearchRepository<Phase, Long> {
}
