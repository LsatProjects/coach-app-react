package br.com.lsat.coachapp.repository.search;

import br.com.lsat.coachapp.domain.Training;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Training entity.
 */
public interface TrainingSearchRepository extends ElasticsearchRepository<Training, Long> {
}
