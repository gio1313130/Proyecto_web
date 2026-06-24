import { Routes } from '@angular/router';
import { LandingPageComponent } from './pages/landing-page/landing-page.component';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { TemasComponent } from './pages/temas/temas.component';
import { TemaDetalleComponent } from './pages/tema-detalle/tema-detalle.component';
import { CuestionarioComponent } from './pages/cuestionario/cuestionario.component';
import { PerfilComponent } from './pages/perfil/perfil.component';
import { AdminLayoutComponent } from './layouts/admin-layout/admin-layout.component';
import { AdminMateriasComponent } from './pages/admin/admin-materias/admin-materias.component';
import { AdminTemasComponent } from './pages/admin/admin-temas/admin-temas.component';
import { AdminRecursosComponent } from './pages/admin/admin-recursos/admin-recursos.component';
import { AdminCuestionariosComponent } from './pages/admin/admin-cuestionarios/admin-cuestionarios.component';
import { AdminUsuariosComponent } from './pages/admin/admin-usuarios/admin-usuarios.component';
import { authGuard } from './guards/auth.guard';
import { adminGuard } from './guards/admin.guard';

export const routes: Routes = [
  { path: '', component: LandingPageComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'dashboard', component: DashboardComponent, canActivate: [authGuard] },
  { path: 'materias/:id/temas', component: TemasComponent, canActivate: [authGuard] },
  { path: 'temas/:id', component: TemaDetalleComponent, canActivate: [authGuard] },
  { path: 'cuestionarios/:id', component: CuestionarioComponent, canActivate: [authGuard] },
  { path: 'perfil', component: PerfilComponent, canActivate: [authGuard] },
  
  // Admin Routes
  {
    path: 'admin',
    component: AdminLayoutComponent,
    canActivate: [adminGuard],
    children: [
      { path: '', redirectTo: 'materias', pathMatch: 'full' },
      { path: 'materias', component: AdminMateriasComponent },
      { path: 'temas', component: AdminTemasComponent },
      { path: 'recursos', component: AdminRecursosComponent },
      { path: 'cuestionarios', component: AdminCuestionariosComponent },
      { path: 'usuarios', component: AdminUsuariosComponent },
    ]
  },
  
  { path: '**', redirectTo: '' }
];
