import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IPhase } from 'app/shared/model/phase.model';
import { getEntities as getPhases } from 'app/entities/phase/phase.reducer';
import { getEntity, updateEntity, createEntity, reset } from './movement-set.reducer';
import { IMovementSet } from 'app/shared/model/movement-set.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMovementSetUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IMovementSetUpdateState {
  isNew: boolean;
  phaseId: string;
}

export class MovementSetUpdate extends React.Component<IMovementSetUpdateProps, IMovementSetUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      phaseId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getPhases();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { movementSetEntity } = this.props;
      const entity = {
        ...movementSetEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/movement-set');
  };

  render() {
    const { movementSetEntity, phases, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="coachappApp.movementSet.home.createOrEditLabel">
              <Translate contentKey="coachappApp.movementSet.home.createOrEditLabel">Create or edit a MovementSet</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : movementSetEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="movement-set-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="unitLabel">
                    <Translate contentKey="coachappApp.movementSet.unit">Unit</Translate>
                  </Label>
                  <AvInput
                    id="movement-set-unit"
                    type="select"
                    className="form-control"
                    name="unit"
                    value={(!isNew && movementSetEntity.unit) || 'REP'}
                  >
                    <option value="REP">
                      <Translate contentKey="coachappApp.Unit.REP" />
                    </option>
                    <option value="CAL">
                      <Translate contentKey="coachappApp.Unit.CAL" />
                    </option>
                    <option value="METER">
                      <Translate contentKey="coachappApp.Unit.METER" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="roundLabel" for="round">
                    <Translate contentKey="coachappApp.movementSet.round">Round</Translate>
                  </Label>
                  <AvField id="movement-set-round" type="string" className="form-control" name="round" />
                </AvGroup>
                <AvGroup>
                  <Label id="weightLabel" for="weight">
                    <Translate contentKey="coachappApp.movementSet.weight">Weight</Translate>
                  </Label>
                  <AvField id="movement-set-weight" type="string" className="form-control" name="weight" />
                </AvGroup>
                <AvGroup>
                  <Label id="levelLabel">
                    <Translate contentKey="coachappApp.movementSet.level">Level</Translate>
                  </Label>
                  <AvInput
                    id="movement-set-level"
                    type="select"
                    className="form-control"
                    name="level"
                    value={(!isNew && movementSetEntity.level) || 'RX'}
                  >
                    <option value="RX">
                      <Translate contentKey="coachappApp.Level.RX" />
                    </option>
                    <option value="SCALE">
                      <Translate contentKey="coachappApp.Level.SCALE" />
                    </option>
                    <option value="BEGINNER">
                      <Translate contentKey="coachappApp.Level.BEGINNER" />
                    </option>
                    <option value="INTERMEDIATE">
                      <Translate contentKey="coachappApp.Level.INTERMEDIATE" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="phase.id">
                    <Translate contentKey="coachappApp.movementSet.phase">Phase</Translate>
                  </Label>
                  <AvInput id="movement-set-phase" type="select" className="form-control" name="phaseId">
                    <option value="" key="0" />
                    {phases
                      ? phases.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/movement-set" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  phases: storeState.phase.entities,
  movementSetEntity: storeState.movementSet.entity,
  loading: storeState.movementSet.loading,
  updating: storeState.movementSet.updating,
  updateSuccess: storeState.movementSet.updateSuccess
});

const mapDispatchToProps = {
  getPhases,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(MovementSetUpdate);
