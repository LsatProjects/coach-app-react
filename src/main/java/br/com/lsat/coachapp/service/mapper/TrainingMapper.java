package br.com.lsat.coachapp.service.mapper;

import br.com.lsat.coachapp.domain.*;
import br.com.lsat.coachapp.service.dto.TrainingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Training and its DTO TrainingDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TrainingMapper extends EntityMapper<TrainingDTO, Training> {


    @Mapping(target = "phases", ignore = true)
    Training toEntity(TrainingDTO trainingDTO);

    default Training fromId(Long id) {
        if (id == null) {
            return null;
        }
        Training training = new Training();
        training.setId(id);
        return training;
    }
}
