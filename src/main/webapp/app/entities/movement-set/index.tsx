import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MovementSet from './movement-set';
import MovementSetDetail from './movement-set-detail';
import MovementSetUpdate from './movement-set-update';
import MovementSetDeleteDialog from './movement-set-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MovementSetUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MovementSetUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MovementSetDetail} />
      <ErrorBoundaryRoute path={match.url} component={MovementSet} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={MovementSetDeleteDialog} />
  </>
);

export default Routes;
