package br.com.lsat.coachapp.service.mapper;

import br.com.lsat.coachapp.domain.*;
import br.com.lsat.coachapp.service.dto.MovementCategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MovementCategory and its DTO MovementCategoryDTO.
 */
@Mapper(componentModel = "spring", uses = {SportMapper.class})
public interface MovementCategoryMapper extends EntityMapper<MovementCategoryDTO, MovementCategory> {

    @Mapping(source = "sport.id", target = "sportId")
    MovementCategoryDTO toDto(MovementCategory movementCategory);

    @Mapping(source = "sportId", target = "sport")
    MovementCategory toEntity(MovementCategoryDTO movementCategoryDTO);

    default MovementCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        MovementCategory movementCategory = new MovementCategory();
        movementCategory.setId(id);
        return movementCategory;
    }
}
