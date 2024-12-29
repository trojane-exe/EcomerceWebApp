import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { NavbarComponent } from './AdminInterfaces/navbar/navbar.component';
import { Navbar2Component } from './AdminInterfaces/navbar2/navbar2.component';
import { DashboardComponent } from './AdminInterfaces/dashboard/dashboard.component';
import { UserslistComponent } from './AdminInterfaces/userslist/userslist.component';
import { AdduserComponent } from './AdminInterfaces/adduser/adduser.component';
import { UpdateuserComponent } from './AdminInterfaces/updateuser/updateuser.component';


const routes: Routes = [
  {path:'login',component:LoginComponent},
  {path:'',component:LoginComponent},
  {path:'navbar',component:NavbarComponent},
  {path:'navtest',component:Navbar2Component },
  {path:'dashboard',component:DashboardComponent},
  {path:'users',component:UserslistComponent},
  {path:'add-user',component:AdduserComponent},
  {path:'update-user',component:UpdateuserComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
