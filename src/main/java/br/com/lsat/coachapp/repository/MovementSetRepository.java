package br.com.lsat.coachapp.repository;

import br.com.lsat.coachapp.domain.MovementSet;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MovementSet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MovementSetRepository extends JpaRepository<MovementSet, Long> {

}
