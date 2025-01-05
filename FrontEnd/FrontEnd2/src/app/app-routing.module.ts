import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { NavbarComponent } from './AdminInterfaces/navbar/navbar.component';
import { Navbar2Component } from './AdminInterfaces/SideBar/navbar2.component';
import { DashboardComponent } from './AdminInterfaces/dashboard/dashboard.component';
import { UserslistComponent } from './AdminInterfaces/userslist/userslist.component';
import { AdduserComponent } from './AdminInterfaces/adduser/adduser.component';
import { UpdateuserComponent } from './AdminInterfaces/updateuser/updateuser.component';
import { AccountComponent } from './UserInterfaces/account/account.component';
import { ProductsComponent } from './AdminInterfaces/products/products.component';
import { ProductsShopComponent } from './UserInterfaces/products-shop/products-shop.component';
import { CartComponent } from './cart/cart.component';
import { CartviewComponent } from './UserInterfaces/cartview/cartview.component';
import { AddProductComponent } from './AdminInterfaces/add-product/add-product.component';
import { AvailableProductComponent } from './AdminInterfaces/available-product/available-product.component';
import { OutofstockProductComponent } from './AdminInterfaces/outofstock-product/outofstock-product.component';
import { StockManagementComponent } from './AdminInterfaces/stock-management/stock-management.component';
import { PayementFormComponent } from './UserInterfaces/payement-form/payement-form.component';
import { OrdersComponent } from './UserInterfaces/orders/orders.component';

const routes: Routes = [
  {path:'login',component:LoginComponent},
  {path:'',component:LoginComponent},
  {path:'navbar',component:NavbarComponent},
  {path:'navtest',component:Navbar2Component },
  {path:'dashboard',component:DashboardComponent},
  {path:'users',component:UserslistComponent},
  {path:'add-user',component:AdduserComponent},
  {path:'update-user/:id',component:UpdateuserComponent},
  {path:'account',component:AccountComponent},
  {path:'products',component:ProductsComponent},
  {path:'shop',component:ProductsShopComponent},
  {path:'cart',component:CartComponent},
  {path:'mycart',component:CartviewComponent},
  {path:'addproduct',component:AddProductComponent},
  {path:'available',component:AvailableProductComponent},
  {path:'out',component:OutofstockProductComponent},
  {path:'stock',component:StockManagementComponent},
  {path:'payements',component:PayementFormComponent},
  {path:'orders',component:OrdersComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
