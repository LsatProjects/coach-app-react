import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import sport, {
  SportState
} from 'app/entities/sport/sport.reducer';
// prettier-ignore
import movementCategory, {
  MovementCategoryState
} from 'app/entities/movement-category/movement-category.reducer';
// prettier-ignore
import movement, {
  MovementState
} from 'app/entities/movement/movement.reducer';
// prettier-ignore
import phase, {
  PhaseState
} from 'app/entities/phase/phase.reducer';
// prettier-ignore
import movementSet, {
  MovementSetState
} from 'app/entities/movement-set/movement-set.reducer';
// prettier-ignore
import training, {
  TrainingState
} from 'app/entities/training/training.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly sport: SportState;
  readonly movementCategory: MovementCategoryState;
  readonly movement: MovementState;
  readonly phase: PhaseState;
  readonly movementSet: MovementSetState;
  readonly training: TrainingState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  sport,
  movementCategory,
  movement,
  phase,
  movementSet,
  training,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;
