import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { NavbarComponent } from './AdminInterfaces/navbar/navbar.component';
import { Navbar2Component } from './AdminInterfaces/SideBar/navbar2.component';

import { DashboardComponent } from './AdminInterfaces/dashboard/dashboard.component';
import { NgApexchartsModule } from 'ng-apexcharts';
import { UserslistComponent } from './AdminInterfaces/userslist/userslist.component';
import { AdduserComponent } from './AdminInterfaces/adduser/adduser.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ReactiveFormsModule } from '@angular/forms';
import { UpdateuserComponent } from './AdminInterfaces/updateuser/updateuser.component';
import { AuthInterceptor } from './auth.interceptor';
import { HTTP_INTERCEPTORS, HttpClient, HttpHandler} from '@angular/common/http';
import { ToastrModule } from 'ngx-toastr';
import { HttpClientModule } from '@angular/common/http';
import { ModalModule } from 'ngx-bootstrap/modal';
import { AccountComponent } from './UserInterfaces/account/account.component';
import { UserSideBarComponent } from './user-side-bar/user-side-bar.component';
import { ProductsComponent } from './AdminInterfaces/products/products.component';
import { ProductsShopComponent } from './UserInterfaces/products-shop/products-shop.component';
import { CartComponent } from './cart/cart.component';
import { FilterPipe } from './filter.pipe';
import { CartviewComponent } from './UserInterfaces/cartview/cartview.component';
import { AddProductComponent } from './AdminInterfaces/add-product/add-product.component';
import { AvailableProductComponent } from './AdminInterfaces/available-product/available-product.component';
import { OutofstockProductComponent } from './AdminInterfaces/outofstock-product/outofstock-product.component';
import { StockManagementComponent } from './AdminInterfaces/stock-management/stock-management.component';
import { PayementFormComponent } from './UserInterfaces/payement-form/payement-form.component';
import { OrdersComponent } from './UserInterfaces/orders/orders.component';



@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    NavbarComponent,
    Navbar2Component,
    DashboardComponent,
    UserslistComponent,
    AdduserComponent,
    UpdateuserComponent,
    AccountComponent,
    UserSideBarComponent,
    ProductsComponent,
    ProductsShopComponent,
    CartComponent,
    FilterPipe,
    CartviewComponent,
    AddProductComponent,
    AvailableProductComponent,
    OutofstockProductComponent,
    StockManagementComponent,
    PayementFormComponent,
    OrdersComponent,
    
    

  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    AppRoutingModule,
    NgApexchartsModule,
    FormsModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    ToastrModule.forRoot(),
    ModalModule.forRoot()
    
  ],
  providers: [
    {provide : HTTP_INTERCEPTORS,useClass:AuthInterceptor,multi:true},
    {provide:'NonAuthorizedHttpClient',useFactory:(handler : HttpHandler)=>new HttpClient(handler),deps:[HttpHandler],}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
