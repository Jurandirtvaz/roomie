import { Routes } from '@angular/router';
import { authGuard } from './auth/auth-guard';
import {Login} from './auth/login/login';
import {Register} from './auth/register/register';

export const routes: Routes = [
  {
    path: 'login',
    component: Login },
  {
    path: 'register',
    component: Register,
  }
];
