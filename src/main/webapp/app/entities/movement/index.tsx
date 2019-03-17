import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Movement from './movement';
import MovementDetail from './movement-detail';
import MovementUpdate from './movement-update';
import MovementDeleteDialog from './movement-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MovementUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MovementUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MovementDetail} />
      <ErrorBoundaryRoute path={match.url} component={Movement} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={MovementDeleteDialog} />
  </>
);

export default Routes;
