package br.com.lsat.coachapp.service.mapper;

import br.com.lsat.coachapp.domain.*;
import br.com.lsat.coachapp.service.dto.SportDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Sport and its DTO SportDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SportMapper extends EntityMapper<SportDTO, Sport> {



    default Sport fromId(Long id) {
        if (id == null) {
            return null;
        }
        Sport sport = new Sport();
        sport.setId(id);
        return sport;
    }
}
