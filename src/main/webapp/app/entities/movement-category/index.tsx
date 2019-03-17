import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MovementCategory from './movement-category';
import MovementCategoryDetail from './movement-category-detail';
import MovementCategoryUpdate from './movement-category-update';
import MovementCategoryDeleteDialog from './movement-category-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MovementCategoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MovementCategoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MovementCategoryDetail} />
      <ErrorBoundaryRoute path={match.url} component={MovementCategory} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={MovementCategoryDeleteDialog} />
  </>
);

export default Routes;
