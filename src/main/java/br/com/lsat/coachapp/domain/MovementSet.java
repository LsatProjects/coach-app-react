package br.com.lsat.coachapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import br.com.lsat.coachapp.domain.enumeration.Unit;

import br.com.lsat.coachapp.domain.enumeration.Level;

/**
 * A MovementSet.
 */
@Entity
@Table(name = "movement_set")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "movementset")
public class MovementSet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "unit")
    private Unit unit;

    @Column(name = "round")
    private Integer round;

    @Column(name = "weight")
    private Float weight;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_level")
    private Level level;

    @OneToMany(mappedBy = "movementSet")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Movement> movements = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("movementSets")
    private Phase phase;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Unit getUnit() {
        return unit;
    }

    public MovementSet unit(Unit unit) {
        this.unit = unit;
        return this;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Integer getRound() {
        return round;
    }

    public MovementSet round(Integer round) {
        this.round = round;
        return this;
    }

    public void setRound(Integer round) {
        this.round = round;
    }

    public Float getWeight() {
        return weight;
    }

    public MovementSet weight(Float weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Level getLevel() {
        return level;
    }

    public MovementSet level(Level level) {
        this.level = level;
        return this;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Set<Movement> getMovements() {
        return movements;
    }

    public MovementSet movements(Set<Movement> movements) {
        this.movements = movements;
        return this;
    }

    public MovementSet addMovements(Movement movement) {
        this.movements.add(movement);
        movement.setMovementSet(this);
        return this;
    }

    public MovementSet removeMovements(Movement movement) {
        this.movements.remove(movement);
        movement.setMovementSet(null);
        return this;
    }

    public void setMovements(Set<Movement> movements) {
        this.movements = movements;
    }

    public Phase getPhase() {
        return phase;
    }

    public MovementSet phase(Phase phase) {
        this.phase = phase;
        return this;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
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
        MovementSet movementSet = (MovementSet) o;
        if (movementSet.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), movementSet.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MovementSet{" +
            "id=" + getId() +
            ", unit='" + getUnit() + "'" +
            ", round=" + getRound() +
            ", weight=" + getWeight() +
            ", level='" + getLevel() + "'" +
            "}";
    }
}
