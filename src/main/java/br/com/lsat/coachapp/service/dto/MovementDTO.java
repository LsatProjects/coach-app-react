package br.com.lsat.coachapp.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Movement entity.
 */
public class MovementDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String abreviation;

    private String description;

    private String url;

    private Long movementCategoryId;

    private Long movementSetId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbreviation() {
        return abreviation;
    }

    public void setAbreviation(String abreviation) {
        this.abreviation = abreviation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getMovementCategoryId() {
        return movementCategoryId;
    }

    public void setMovementCategoryId(Long movementCategoryId) {
        this.movementCategoryId = movementCategoryId;
    }

    public Long getMovementSetId() {
        return movementSetId;
    }

    public void setMovementSetId(Long movementSetId) {
        this.movementSetId = movementSetId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MovementDTO movementDTO = (MovementDTO) o;
        if (movementDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), movementDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MovementDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", abreviation='" + getAbreviation() + "'" +
            ", description='" + getDescription() + "'" +
            ", url='" + getUrl() + "'" +
            ", movementCategory=" + getMovementCategoryId() +
            ", movementSet=" + getMovementSetId() +
            "}";
    }
}
