import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPhase, defaultValue } from 'app/shared/model/phase.model';

export const ACTION_TYPES = {
  SEARCH_PHASES: 'phase/SEARCH_PHASES',
  FETCH_PHASE_LIST: 'phase/FETCH_PHASE_LIST',
  FETCH_PHASE: 'phase/FETCH_PHASE',
  CREATE_PHASE: 'phase/CREATE_PHASE',
  UPDATE_PHASE: 'phase/UPDATE_PHASE',
  DELETE_PHASE: 'phase/DELETE_PHASE',
  RESET: 'phase/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPhase>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type PhaseState = Readonly<typeof initialState>;

// Reducer

export default (state: PhaseState = initialState, action): PhaseState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_PHASES):
    case REQUEST(ACTION_TYPES.FETCH_PHASE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PHASE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PHASE):
    case REQUEST(ACTION_TYPES.UPDATE_PHASE):
    case REQUEST(ACTION_TYPES.DELETE_PHASE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_PHASES):
    case FAILURE(ACTION_TYPES.FETCH_PHASE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PHASE):
    case FAILURE(ACTION_TYPES.CREATE_PHASE):
    case FAILURE(ACTION_TYPES.UPDATE_PHASE):
    case FAILURE(ACTION_TYPES.DELETE_PHASE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_PHASES):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_PHASE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_PHASE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PHASE):
    case SUCCESS(ACTION_TYPES.UPDATE_PHASE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PHASE):
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

const apiUrl = 'api/phases';
const apiSearchUrl = 'api/_search/phases';

// Actions

export const getSearchEntities: ICrudSearchAction<IPhase> = query => ({
  type: ACTION_TYPES.SEARCH_PHASES,
  payload: axios.get<IPhase>(`${apiSearchUrl}?query=` + query)
});

export const getEntities: ICrudGetAllAction<IPhase> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PHASE_LIST,
  payload: axios.get<IPhase>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IPhase> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PHASE,
    payload: axios.get<IPhase>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IPhase> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PHASE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPhase> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PHASE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPhase> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PHASE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
