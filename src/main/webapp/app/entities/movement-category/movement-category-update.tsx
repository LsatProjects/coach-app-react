import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ISport } from 'app/shared/model/sport.model';
import { getEntities as getSports } from 'app/entities/sport/sport.reducer';
import { getEntity, updateEntity, createEntity, reset } from './movement-category.reducer';
import { IMovementCategory } from 'app/shared/model/movement-category.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMovementCategoryUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IMovementCategoryUpdateState {
  isNew: boolean;
  sportId: string;
}

export class MovementCategoryUpdate extends React.Component<IMovementCategoryUpdateProps, IMovementCategoryUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      sportId: '0',
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

    this.props.getSports();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { movementCategoryEntity } = this.props;
      const entity = {
        ...movementCategoryEntity,
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
    this.props.history.push('/entity/movement-category');
  };

  render() {
    const { movementCategoryEntity, sports, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="coachappApp.movementCategory.home.createOrEditLabel">
              <Translate contentKey="coachappApp.movementCategory.home.createOrEditLabel">Create or edit a MovementCategory</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : movementCategoryEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="movement-category-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    <Translate contentKey="coachappApp.movementCategory.name">Name</Translate>
                  </Label>
                  <AvField
                    id="movement-category-name"
                    type="text"
                    name="name"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="descriptionLabel" for="description">
                    <Translate contentKey="coachappApp.movementCategory.description">Description</Translate>
                  </Label>
                  <AvField id="movement-category-description" type="text" name="description" />
                </AvGroup>
                <AvGroup>
                  <Label for="sport.id">
                    <Translate contentKey="coachappApp.movementCategory.sport">Sport</Translate>
                  </Label>
                  <AvInput id="movement-category-sport" type="select" className="form-control" name="sportId">
                    <option value="" key="0" />
                    {sports
                      ? sports.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/movement-category" replace color="info">
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
  sports: storeState.sport.entities,
  movementCategoryEntity: storeState.movementCategory.entity,
  loading: storeState.movementCategory.loading,
  updating: storeState.movementCategory.updating,
  updateSuccess: storeState.movementCategory.updateSuccess
});

const mapDispatchToProps = {
  getSports,
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
)(MovementCategoryUpdate);
