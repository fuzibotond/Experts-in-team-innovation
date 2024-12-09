import { Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { TopicsComponentComponent } from './components/topics-component/topics-component.component';
import { TopicRegistrationComponent } from './components/topic-registration/topic-registration.component';
import { AuthGuard } from './guards/auth.guard';
import { LoginFormComponent } from './components/login-form/login-form.component';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginFormComponent },
  { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard] }, // Protected route
  { path: 'topics', component: TopicsComponentComponent, canActivate: [AuthGuard] }, // Protected route
  { path: 'register', component: TopicRegistrationComponent, canActivate: [AuthGuard] }, // Protected route
  { path: '**', redirectTo: '' }, // Redirect unknown routes to the landing page
];
