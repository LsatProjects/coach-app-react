import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Training from './training';
import TrainingDetail from './training-detail';
import TrainingUpdate from './training-update';
import TrainingDeleteDialog from './training-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TrainingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TrainingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TrainingDetail} />
      <ErrorBoundaryRoute path={match.url} component={Training} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={TrainingDeleteDialog} />
  </>
);

export default Routes;
