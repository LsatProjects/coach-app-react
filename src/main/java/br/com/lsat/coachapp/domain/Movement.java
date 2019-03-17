package br.com.lsat.coachapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Movement.
 */
@Entity
@Table(name = "movement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "movement")
public class Movement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "abreviation")
    private String abreviation;

    @Column(name = "description")
    private String description;

    @Column(name = "url")
    private String url;

    @ManyToOne
    @JsonIgnoreProperties("")
    private MovementCategory movementCategory;

    @ManyToOne
    @JsonIgnoreProperties("movements")
    private MovementSet movementSet;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Movement name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbreviation() {
        return abreviation;
    }

    public Movement abreviation(String abreviation) {
        this.abreviation = abreviation;
        return this;
    }

    public void setAbreviation(String abreviation) {
        this.abreviation = abreviation;
    }

    public String getDescription() {
        return description;
    }

    public Movement description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public Movement url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public MovementCategory getMovementCategory() {
        return movementCategory;
    }

    public Movement movementCategory(MovementCategory movementCategory) {
        this.movementCategory = movementCategory;
        return this;
    }

    public void setMovementCategory(MovementCategory movementCategory) {
        this.movementCategory = movementCategory;
    }

    public MovementSet getMovementSet() {
        return movementSet;
    }

    public Movement movementSet(MovementSet movementSet) {
        this.movementSet = movementSet;
        return this;
    }

    public void setMovementSet(MovementSet movementSet) {
        this.movementSet = movementSet;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Movement movement = (Movement) o;
        if (movement.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), movement.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Movement{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", abreviation='" + getAbreviation() + "'" +
            ", description='" + getDescription() + "'" +
            ", url='" + getUrl() + "'" +
            "}";
    }
}
