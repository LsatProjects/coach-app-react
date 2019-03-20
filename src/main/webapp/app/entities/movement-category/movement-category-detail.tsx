import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './movement-category.reducer';
import { IMovementCategory } from 'app/shared/model/movement-category.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMovementCategoryDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class MovementCategoryDetail extends React.Component<IMovementCategoryDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { movementCategoryEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="coachappApp.movementCategory.detail.title">MovementCategory</Translate> [
            <b>{movementCategoryEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="coachappApp.movementCategory.name">Name</Translate>
              </span>
            </dt>
            <dd>{movementCategoryEntity.name}</dd>
            <dt>
              <span id="description">
                <Translate contentKey="coachappApp.movementCategory.description">Description</Translate>
              </span>
            </dt>
            <dd>{movementCategoryEntity.description}</dd>
            <dt>
              <Translate contentKey="coachappApp.movementCategory.sport">Sport</Translate>
            </dt>
            <dd>{movementCategoryEntity.sportId ? movementCategoryEntity.sportId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/movement-category" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/movement-category/${movementCategoryEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ movementCategory }: IRootState) => ({
  movementCategoryEntity: movementCategory.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(MovementCategoryDetail);
