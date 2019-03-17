import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Phase from './phase';
import PhaseDetail from './phase-detail';
import PhaseUpdate from './phase-update';
import PhaseDeleteDialog from './phase-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PhaseUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PhaseUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PhaseDetail} />
      <ErrorBoundaryRoute path={match.url} component={Phase} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={PhaseDeleteDialog} />
  </>
);

export default Routes;
