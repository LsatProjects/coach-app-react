package br.com.lsat.coachapp.service.mapper;

import br.com.lsat.coachapp.domain.*;
import br.com.lsat.coachapp.service.dto.PhaseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Phase and its DTO PhaseDTO.
 */
@Mapper(componentModel = "spring", uses = {TrainingMapper.class})
public interface PhaseMapper extends EntityMapper<PhaseDTO, Phase> {

    @Mapping(source = "training.id", target = "trainingId")
    PhaseDTO toDto(Phase phase);

    @Mapping(target = "movementSets", ignore = true)
    @Mapping(source = "trainingId", target = "training")
    Phase toEntity(PhaseDTO phaseDTO);

    default Phase fromId(Long id) {
        if (id == null) {
            return null;
        }
        Phase phase = new Phase();
        phase.setId(id);
        return phase;
    }
}
