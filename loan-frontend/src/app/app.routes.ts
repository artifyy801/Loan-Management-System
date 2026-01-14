import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { ApplyLoanComponent } from './components/apply-loan/apply-loan.component';
import { SignupComponent } from './components/signup/signup.component';

export const routes: Routes = [
  { path: '', component: LoginComponent }, // Default Page
  { path: 'login', component: LoginComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'apply-loan', component: ApplyLoanComponent },
  { path: 'signup', component: SignupComponent }
];