package br.com.lsat.coachapp.service.mapper;

import br.com.lsat.coachapp.domain.*;
import br.com.lsat.coachapp.service.dto.MovementSetDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MovementSet and its DTO MovementSetDTO.
 */
@Mapper(componentModel = "spring", uses = {PhaseMapper.class})
public interface MovementSetMapper extends EntityMapper<MovementSetDTO, MovementSet> {

    @Mapping(source = "phase.id", target = "phaseId")
    MovementSetDTO toDto(MovementSet movementSet);

    @Mapping(target = "movements", ignore = true)
    @Mapping(source = "phaseId", target = "phase")
    MovementSet toEntity(MovementSetDTO movementSetDTO);

    default MovementSet fromId(Long id) {
        if (id == null) {
            return null;
        }
        MovementSet movementSet = new MovementSet();
        movementSet.setId(id);
        return movementSet;
    }
}
