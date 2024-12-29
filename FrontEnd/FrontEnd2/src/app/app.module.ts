import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { NavbarComponent } from './AdminInterfaces/navbar/navbar.component';
import { Navbar2Component } from './AdminInterfaces/navbar2/navbar2.component';
import { DashboardComponent } from './AdminInterfaces/dashboard/dashboard.component';
import { NgApexchartsModule } from 'ng-apexcharts';
import { UserslistComponent } from './AdminInterfaces/userslist/userslist.component';
import { AdduserComponent } from './AdminInterfaces/adduser/adduser.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ReactiveFormsModule } from '@angular/forms';
import { UpdateuserComponent } from './AdminInterfaces/updateuser/updateuser.component';
import { AuthInterceptor } from './auth.interceptor';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    NavbarComponent,
    Navbar2Component,
    DashboardComponent,
    UserslistComponent,
    AdduserComponent,
    UpdateuserComponent

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgApexchartsModule,
    FormsModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [
    {provide : HTTP_INTERCEPTORS,useClass:AuthInterceptor,multi:true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
