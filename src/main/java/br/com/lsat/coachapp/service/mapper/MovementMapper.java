package br.com.lsat.coachapp.service.mapper;

import br.com.lsat.coachapp.domain.*;
import br.com.lsat.coachapp.service.dto.MovementDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Movement and its DTO MovementDTO.
 */
@Mapper(componentModel = "spring", uses = {MovementCategoryMapper.class, MovementSetMapper.class})
public interface MovementMapper extends EntityMapper<MovementDTO, Movement> {

    @Mapping(source = "movementCategory.id", target = "movementCategoryId")
    @Mapping(source = "movementSet.id", target = "movementSetId")
    MovementDTO toDto(Movement movement);

    @Mapping(source = "movementCategoryId", target = "movementCategory")
    @Mapping(source = "movementSetId", target = "movementSet")
    Movement toEntity(MovementDTO movementDTO);

    default Movement fromId(Long id) {
        if (id == null) {
            return null;
        }
        Movement movement = new Movement();
        movement.setId(id);
        return movement;
    }
}
