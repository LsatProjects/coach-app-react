import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Sport from './sport';
import SportDetail from './sport-detail';
import SportUpdate from './sport-update';
import SportDeleteDialog from './sport-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SportUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SportUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SportDetail} />
      <ErrorBoundaryRoute path={match.url} component={Sport} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={SportDeleteDialog} />
  </>
);

export default Routes;
