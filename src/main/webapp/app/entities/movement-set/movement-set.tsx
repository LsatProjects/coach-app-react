import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudSearchAction, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './movement-set.reducer';
import { IMovementSet } from 'app/shared/model/movement-set.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMovementSetProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export interface IMovementSetState {
  search: string;
}

export class MovementSet extends React.Component<IMovementSetProps, IMovementSetState> {
  state: IMovementSetState = {
    search: ''
  };

  componentDidMount() {
    this.props.getEntities();
  }

  search = () => {
    if (this.state.search) {
      this.props.getSearchEntities(this.state.search);
    }
  };

  clear = () => {
    this.props.getEntities();
    this.setState({
      search: ''
    });
  };

  handleSearch = event => this.setState({ search: event.target.value });

  render() {
    const { movementSetList, match } = this.props;
    return (
      <div>
        <h2 id="movement-set-heading">
          <Translate contentKey="coachappApp.movementSet.home.title">Movement Sets</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="coachappApp.movementSet.home.createLabel">Create new Movement Set</Translate>
          </Link>
        </h2>
        <Row>
          <Col sm="12">
            <AvForm onSubmit={this.search}>
              <AvGroup>
                <InputGroup>
                  <AvInput
                    type="text"
                    name="search"
                    value={this.state.search}
                    onChange={this.handleSearch}
                    placeholder={translate('coachappApp.movementSet.home.search')}
                  />
                  <Button className="input-group-addon">
                    <FontAwesomeIcon icon="search" />
                  </Button>
                  <Button type="reset" className="input-group-addon" onClick={this.clear}>
                    <FontAwesomeIcon icon="trash" />
                  </Button>
                </InputGroup>
              </AvGroup>
            </AvForm>
          </Col>
        </Row>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="coachappApp.movementSet.unit">Unit</Translate>
                </th>
                <th>
                  <Translate contentKey="coachappApp.movementSet.round">Round</Translate>
                </th>
                <th>
                  <Translate contentKey="coachappApp.movementSet.weight">Weight</Translate>
                </th>
                <th>
                  <Translate contentKey="coachappApp.movementSet.level">Level</Translate>
                </th>
                <th>
                  <Translate contentKey="coachappApp.movementSet.phase">Phase</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {movementSetList.map((movementSet, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${movementSet.id}`} color="link" size="sm">
                      {movementSet.id}
                    </Button>
                  </td>
                  <td>
                    <Translate contentKey={`coachappApp.Unit.${movementSet.unit}`} />
                  </td>
                  <td>{movementSet.round}</td>
                  <td>{movementSet.weight}</td>
                  <td>
                    <Translate contentKey={`coachappApp.Level.${movementSet.level}`} />
                  </td>
                  <td>{movementSet.phase ? <Link to={`phase/${movementSet.phase.id}`}>{movementSet.phase.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${movementSet.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${movementSet.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${movementSet.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ movementSet }: IRootState) => ({
  movementSetList: movementSet.entities
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(MovementSet);
