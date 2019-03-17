import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITraining, defaultValue } from 'app/shared/model/training.model';

export const ACTION_TYPES = {
  SEARCH_TRAININGS: 'training/SEARCH_TRAININGS',
  FETCH_TRAINING_LIST: 'training/FETCH_TRAINING_LIST',
  FETCH_TRAINING: 'training/FETCH_TRAINING',
  CREATE_TRAINING: 'training/CREATE_TRAINING',
  UPDATE_TRAINING: 'training/UPDATE_TRAINING',
  DELETE_TRAINING: 'training/DELETE_TRAINING',
  RESET: 'training/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITraining>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type TrainingState = Readonly<typeof initialState>;

// Reducer

export default (state: TrainingState = initialState, action): TrainingState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_TRAININGS):
    case REQUEST(ACTION_TYPES.FETCH_TRAINING_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TRAINING):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_TRAINING):
    case REQUEST(ACTION_TYPES.UPDATE_TRAINING):
    case REQUEST(ACTION_TYPES.DELETE_TRAINING):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_TRAININGS):
    case FAILURE(ACTION_TYPES.FETCH_TRAINING_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TRAINING):
    case FAILURE(ACTION_TYPES.CREATE_TRAINING):
    case FAILURE(ACTION_TYPES.UPDATE_TRAINING):
    case FAILURE(ACTION_TYPES.DELETE_TRAINING):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_TRAININGS):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_TRAINING_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_TRAINING):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_TRAINING):
    case SUCCESS(ACTION_TYPES.UPDATE_TRAINING):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_TRAINING):
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

const apiUrl = 'api/trainings';
const apiSearchUrl = 'api/_search/trainings';

// Actions

export const getSearchEntities: ICrudSearchAction<ITraining> = query => ({
  type: ACTION_TYPES.SEARCH_TRAININGS,
  payload: axios.get<ITraining>(`${apiSearchUrl}?query=` + query)
});

export const getEntities: ICrudGetAllAction<ITraining> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_TRAINING_LIST,
    payload: axios.get<ITraining>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ITraining> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TRAINING,
    payload: axios.get<ITraining>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ITraining> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TRAINING,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITraining> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TRAINING,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITraining> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TRAINING,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
