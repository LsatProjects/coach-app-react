import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMovementSet, defaultValue } from 'app/shared/model/movement-set.model';

export const ACTION_TYPES = {
  SEARCH_MOVEMENTSETS: 'movementSet/SEARCH_MOVEMENTSETS',
  FETCH_MOVEMENTSET_LIST: 'movementSet/FETCH_MOVEMENTSET_LIST',
  FETCH_MOVEMENTSET: 'movementSet/FETCH_MOVEMENTSET',
  CREATE_MOVEMENTSET: 'movementSet/CREATE_MOVEMENTSET',
  UPDATE_MOVEMENTSET: 'movementSet/UPDATE_MOVEMENTSET',
  DELETE_MOVEMENTSET: 'movementSet/DELETE_MOVEMENTSET',
  RESET: 'movementSet/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMovementSet>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type MovementSetState = Readonly<typeof initialState>;

// Reducer

export default (state: MovementSetState = initialState, action): MovementSetState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_MOVEMENTSETS):
    case REQUEST(ACTION_TYPES.FETCH_MOVEMENTSET_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MOVEMENTSET):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_MOVEMENTSET):
    case REQUEST(ACTION_TYPES.UPDATE_MOVEMENTSET):
    case REQUEST(ACTION_TYPES.DELETE_MOVEMENTSET):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_MOVEMENTSETS):
    case FAILURE(ACTION_TYPES.FETCH_MOVEMENTSET_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MOVEMENTSET):
    case FAILURE(ACTION_TYPES.CREATE_MOVEMENTSET):
    case FAILURE(ACTION_TYPES.UPDATE_MOVEMENTSET):
    case FAILURE(ACTION_TYPES.DELETE_MOVEMENTSET):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_MOVEMENTSETS):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_MOVEMENTSET_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_MOVEMENTSET):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_MOVEMENTSET):
    case SUCCESS(ACTION_TYPES.UPDATE_MOVEMENTSET):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_MOVEMENTSET):
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

const apiUrl = 'api/movement-sets';
const apiSearchUrl = 'api/_search/movement-sets';

// Actions

export const getSearchEntities: ICrudSearchAction<IMovementSet> = query => ({
  type: ACTION_TYPES.SEARCH_MOVEMENTSETS,
  payload: axios.get<IMovementSet>(`${apiSearchUrl}?query=` + query)
});

export const getEntities: ICrudGetAllAction<IMovementSet> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_MOVEMENTSET_LIST,
  payload: axios.get<IMovementSet>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IMovementSet> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MOVEMENTSET,
    payload: axios.get<IMovementSet>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IMovementSet> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MOVEMENTSET,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMovementSet> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MOVEMENTSET,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMovementSet> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MOVEMENTSET,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
