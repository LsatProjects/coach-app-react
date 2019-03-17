import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMovementCategory, defaultValue } from 'app/shared/model/movement-category.model';

export const ACTION_TYPES = {
  SEARCH_MOVEMENTCATEGORIES: 'movementCategory/SEARCH_MOVEMENTCATEGORIES',
  FETCH_MOVEMENTCATEGORY_LIST: 'movementCategory/FETCH_MOVEMENTCATEGORY_LIST',
  FETCH_MOVEMENTCATEGORY: 'movementCategory/FETCH_MOVEMENTCATEGORY',
  CREATE_MOVEMENTCATEGORY: 'movementCategory/CREATE_MOVEMENTCATEGORY',
  UPDATE_MOVEMENTCATEGORY: 'movementCategory/UPDATE_MOVEMENTCATEGORY',
  DELETE_MOVEMENTCATEGORY: 'movementCategory/DELETE_MOVEMENTCATEGORY',
  RESET: 'movementCategory/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMovementCategory>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type MovementCategoryState = Readonly<typeof initialState>;

// Reducer

export default (state: MovementCategoryState = initialState, action): MovementCategoryState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_MOVEMENTCATEGORIES):
    case REQUEST(ACTION_TYPES.FETCH_MOVEMENTCATEGORY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MOVEMENTCATEGORY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_MOVEMENTCATEGORY):
    case REQUEST(ACTION_TYPES.UPDATE_MOVEMENTCATEGORY):
    case REQUEST(ACTION_TYPES.DELETE_MOVEMENTCATEGORY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_MOVEMENTCATEGORIES):
    case FAILURE(ACTION_TYPES.FETCH_MOVEMENTCATEGORY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MOVEMENTCATEGORY):
    case FAILURE(ACTION_TYPES.CREATE_MOVEMENTCATEGORY):
    case FAILURE(ACTION_TYPES.UPDATE_MOVEMENTCATEGORY):
    case FAILURE(ACTION_TYPES.DELETE_MOVEMENTCATEGORY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_MOVEMENTCATEGORIES):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_MOVEMENTCATEGORY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_MOVEMENTCATEGORY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_MOVEMENTCATEGORY):
    case SUCCESS(ACTION_TYPES.UPDATE_MOVEMENTCATEGORY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_MOVEMENTCATEGORY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/movement-categories';
const apiSearchUrl = 'api/_search/movement-categories';

// Actions

export const getSearchEntities: ICrudSearchAction<IMovementCategory> = query => ({
  type: ACTION_TYPES.SEARCH_MOVEMENTCATEGORIES,
  payload: axios.get<IMovementCategory>(`${apiSearchUrl}?query=` + query)
});

export const getEntities: ICrudGetAllAction<IMovementCategory> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_MOVEMENTCATEGORY_LIST,
  payload: axios.get<IMovementCategory>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IMovementCategory> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MOVEMENTCATEGORY,
    payload: axios.get<IMovementCategory>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IMovementCategory> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MOVEMENTCATEGORY,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMovementCategory> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MOVEMENTCATEGORY,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMovementCategory> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MOVEMENTCATEGORY,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
