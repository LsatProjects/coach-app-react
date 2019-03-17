import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMovement, defaultValue } from 'app/shared/model/movement.model';

export const ACTION_TYPES = {
  SEARCH_MOVEMENTS: 'movement/SEARCH_MOVEMENTS',
  FETCH_MOVEMENT_LIST: 'movement/FETCH_MOVEMENT_LIST',
  FETCH_MOVEMENT: 'movement/FETCH_MOVEMENT',
  CREATE_MOVEMENT: 'movement/CREATE_MOVEMENT',
  UPDATE_MOVEMENT: 'movement/UPDATE_MOVEMENT',
  DELETE_MOVEMENT: 'movement/DELETE_MOVEMENT',
  RESET: 'movement/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMovement>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type MovementState = Readonly<typeof initialState>;

// Reducer

export default (state: MovementState = initialState, action): MovementState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_MOVEMENTS):
    case REQUEST(ACTION_TYPES.FETCH_MOVEMENT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MOVEMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_MOVEMENT):
    case REQUEST(ACTION_TYPES.UPDATE_MOVEMENT):
    case REQUEST(ACTION_TYPES.DELETE_MOVEMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_MOVEMENTS):
    case FAILURE(ACTION_TYPES.FETCH_MOVEMENT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MOVEMENT):
    case FAILURE(ACTION_TYPES.CREATE_MOVEMENT):
    case FAILURE(ACTION_TYPES.UPDATE_MOVEMENT):
    case FAILURE(ACTION_TYPES.DELETE_MOVEMENT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_MOVEMENTS):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_MOVEMENT_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_MOVEMENT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_MOVEMENT):
    case SUCCESS(ACTION_TYPES.UPDATE_MOVEMENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_MOVEMENT):
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

const apiUrl = 'api/movements';
const apiSearchUrl = 'api/_search/movements';

// Actions

export const getSearchEntities: ICrudSearchAction<IMovement> = query => ({
  type: ACTION_TYPES.SEARCH_MOVEMENTS,
  payload: axios.get<IMovement>(`${apiSearchUrl}?query=` + query)
});

export const getEntities: ICrudGetAllAction<IMovement> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_MOVEMENT_LIST,
    payload: axios.get<IMovement>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IMovement> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MOVEMENT,
    payload: axios.get<IMovement>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IMovement> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MOVEMENT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMovement> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MOVEMENT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMovement> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MOVEMENT,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
