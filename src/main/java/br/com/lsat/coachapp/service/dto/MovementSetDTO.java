package br.com.lsat.coachapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import br.com.lsat.coachapp.domain.enumeration.Unit;
import br.com.lsat.coachapp.domain.enumeration.Level;

/**
 * A DTO for the MovementSet entity.
 */
public class MovementSetDTO implements Serializable {

    private Long id;

    private Unit unit;

    private Integer round;

    private Float weight;

    private Level level;

    private Long phaseId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Integer getRound() {
        return round;
    }

    public void setRound(Integer round) {
        this.round = round;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Long getPhaseId() {
        return phaseId;
    }

    public void setPhaseId(Long phaseId) {
        this.phaseId = phaseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MovementSetDTO movementSetDTO = (MovementSetDTO) o;
        if (movementSetDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), movementSetDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MovementSetDTO{" +
            "id=" + getId() +
            ", unit='" + getUnit() + "'" +
            ", round=" + getRound() +
            ", weight=" + getWeight() +
            ", level='" + getLevel() + "'" +
            ", phase=" + getPhaseId() +
            "}";
    }
}
