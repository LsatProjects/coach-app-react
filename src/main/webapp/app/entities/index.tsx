import React from 'react';
import { Switch } from 'react-router-dom';

// tslint:disable-next-line:no-unused-variable
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Sport from './sport';
import MovementCategory from './movement-category';
import Movement from './movement';
import Phase from './phase';
import MovementSet from './movement-set';
import Training from './training';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/sport`} component={Sport} />
      <ErrorBoundaryRoute path={`${match.url}/movement-category`} component={MovementCategory} />
      <ErrorBoundaryRoute path={`${match.url}/movement`} component={Movement} />
      <ErrorBoundaryRoute path={`${match.url}/phase`} component={Phase} />
      <ErrorBoundaryRoute path={`${match.url}/movement-set`} component={MovementSet} />
      <ErrorBoundaryRoute path={`${match.url}/training`} component={Training} />
      {/* jhipster-needle-add-route-path - JHipster will routes here */}
    </Switch>
  </div>
);

export default Routes;
