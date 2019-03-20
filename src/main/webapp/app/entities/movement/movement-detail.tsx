import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './movement.reducer';
import { IMovement } from 'app/shared/model/movement.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMovementDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class MovementDetail extends React.Component<IMovementDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { movementEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="coachappApp.movement.detail.title">Movement</Translate> [<b>{movementEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="coachappApp.movement.name">Name</Translate>
              </span>
            </dt>
            <dd>{movementEntity.name}</dd>
            <dt>
              <span id="abreviation">
                <Translate contentKey="coachappApp.movement.abreviation">Abreviation</Translate>
              </span>
            </dt>
            <dd>{movementEntity.abreviation}</dd>
            <dt>
              <span id="description">
                <Translate contentKey="coachappApp.movement.description">Description</Translate>
              </span>
            </dt>
            <dd>{movementEntity.description}</dd>
            <dt>
              <span id="url">
                <Translate contentKey="coachappApp.movement.url">Url</Translate>
              </span>
            </dt>
            <dd>{movementEntity.url}</dd>
            <dt>
              <Translate contentKey="coachappApp.movement.movementCategory">Movement Category</Translate>
            </dt>
            <dd>{movementEntity.movementCategoryId ? movementEntity.movementCategoryId : ''}</dd>
            <dt>
              <Translate contentKey="coachappApp.movement.movementSet">Movement Set</Translate>
            </dt>
            <dd>{movementEntity.movementSetId ? movementEntity.movementSetId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/movement" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/movement/${movementEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ movement }: IRootState) => ({
  movementEntity: movement.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(MovementDetail);
