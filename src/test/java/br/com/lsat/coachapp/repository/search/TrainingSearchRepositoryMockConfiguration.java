package br.com.lsat.coachapp.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of TrainingSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class TrainingSearchRepositoryMockConfiguration {

    @MockBean
    private TrainingSearchRepository mockTrainingSearchRepository;

}
