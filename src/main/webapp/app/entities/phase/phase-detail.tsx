import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './phase.reducer';
import { IPhase } from 'app/shared/model/phase.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPhaseDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class PhaseDetail extends React.Component<IPhaseDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { phaseEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="coachappApp.phase.detail.title">Phase</Translate> [<b>{phaseEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="coachappApp.phase.name">Name</Translate>
              </span>
            </dt>
            <dd>{phaseEntity.name}</dd>
            <dt>
              <span id="description">
                <Translate contentKey="coachappApp.phase.description">Description</Translate>
              </span>
            </dt>
            <dd>{phaseEntity.description}</dd>
            <dt>
              <Translate contentKey="coachappApp.phase.training">Training</Translate>
            </dt>
            <dd>{phaseEntity.training ? phaseEntity.training.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/phase" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/phase/${phaseEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ phase }: IRootState) => ({
  phaseEntity: phase.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PhaseDetail);