import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ISport, defaultValue } from 'app/shared/model/sport.model';

export const ACTION_TYPES = {
  SEARCH_SPORTS: 'sport/SEARCH_SPORTS',
  FETCH_SPORT_LIST: 'sport/FETCH_SPORT_LIST',
  FETCH_SPORT: 'sport/FETCH_SPORT',
  CREATE_SPORT: 'sport/CREATE_SPORT',
  UPDATE_SPORT: 'sport/UPDATE_SPORT',
  DELETE_SPORT: 'sport/DELETE_SPORT',
  RESET: 'sport/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ISport>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type SportState = Readonly<typeof initialState>;

// Reducer

export default (state: SportState = initialState, action): SportState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_SPORTS):
    case REQUEST(ACTION_TYPES.FETCH_SPORT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SPORT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_SPORT):
    case REQUEST(ACTION_TYPES.UPDATE_SPORT):
    case REQUEST(ACTION_TYPES.DELETE_SPORT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_SPORTS):
    case FAILURE(ACTION_TYPES.FETCH_SPORT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SPORT):
    case FAILURE(ACTION_TYPES.CREATE_SPORT):
    case FAILURE(ACTION_TYPES.UPDATE_SPORT):
    case FAILURE(ACTION_TYPES.DELETE_SPORT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_SPORTS):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_SPORT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_SPORT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_SPORT):
    case SUCCESS(ACTION_TYPES.UPDATE_SPORT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_SPORT):
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

const apiUrl = 'api/sports';
const apiSearchUrl = 'api/_search/sports';

// Actions

export const getSearchEntities: ICrudSearchAction<ISport> = query => ({
  type: ACTION_TYPES.SEARCH_SPORTS,
  payload: axios.get<ISport>(`${apiSearchUrl}?query=` + query)
});

export const getEntities: ICrudGetAllAction<ISport> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_SPORT_LIST,
  payload: axios.get<ISport>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ISport> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SPORT,
    payload: axios.get<ISport>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ISport> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SPORT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ISport> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SPORT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ISport> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SPORT,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
