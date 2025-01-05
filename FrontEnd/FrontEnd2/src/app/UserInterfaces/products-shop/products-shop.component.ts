import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Subject } from 'rxjs';
import { Product } from 'src/app/models/Product.model';
import { User } from 'src/app/models/User.model';
import { ProductService } from 'src/app/Services/AdminServices/ProductService/product.service';
import { UserserviceService } from 'src/app/Services/AdminServices/UserService/userservice.service';
import { AuthenticationService } from 'src/app/Services/authenticationService/authentication.service';
import { CartService } from 'src/app/Services/CartService/cart.service';

@Component({
  selector: 'app-products-shop',
  templateUrl: './products-shop.component.html',
  styleUrls: ['./products-shop.component.css']
})
export class ProductsShopComponent implements OnInit{
  products : Product []=[]

  user: User = new User();
  userId!:number
  qte : { [key: number]: number } = {};


  filteredProducts: any[] = []; // Filtered products based on search
  searchQuery: any = ''; // Search query input from user

  

  constructor(private productService : ProductService,private authService:AuthenticationService,
    private toast :ToastrService,private route:Router,
    private cartService : CartService,
    private userService : UserserviceService){}


    loadUserInfo(id:number):void{
      this.userService.getSingleUser(id).subscribe({
      next:(data:User)=>{
        this.user = data
        console.log("user nom " +this.user.nom);
      }
        })
    
      }

      
    


  loadProducts(){
    this.productService.getAllProducts().subscribe({
      next:(data)=>{
        this.products = data;
        
      },
      error:()=>{
        console.log("ERROR WHILE GETTING PRODUCTS ")
      }
    })
    
  }

  increase(id:number){
    if (this.qte[id] == null) {
      this.qte[id] = 1; // Initialize quantity if it's undefined
    } else {
      this.qte[id] += 1; // Increase quantity
    }
  }
  decrease(id:number){
    if (this.qte[id] == null) {
      this.qte[id] = 1; // Initialize quantity if it's undefined
    } else {
      this.qte[id] -= 1; // Increase quantity
    }
  }



  isSidebarOpen = false;

  toggleSidebar(): void {
    this.isSidebarOpen = !this.isSidebarOpen;
  }

  addToCart(userId:number,productId:number,qte:number){
    this.cartService.addToCart(userId,productId,qte).subscribe({
      next:()=>{
        
        this.toast.success("added to cart",'',{toastClass:'cart-toast-sucess'});
        setTimeout(() => {
          window.location.reload();
        }, 1000);
        
      },
      error:()=>{
        this.toast.error("Insuficient stock",'',{
          toastClass:'cart-toast-sucess '
        });
      }
    })
  }

  onSearchChange(): void {
    if (this.searchQuery.trim() === '') {
      this.filteredProducts = this.products; // Show all products if search is empty
    } else {
      this.filteredProducts = this.products.filter((product) =>
        product.nom.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    }
  }

  
































  ngOnInit(): void {

    if(this.authService.isConnected()===false){
      this.authService.logout();
      this.route.navigate(['']);
      this.toast.error("Please log in using you credentials to proceed","",{
        toastClass:'false-login'
      })
    }
    const id =  localStorage.getItem('userId');
    if(id){this.userId = parseInt(id,10)}
    this.loadUserInfo(this.userId);
    this.loadProducts();
    
    if(this.authService.isConnected()===false){
      this.route.navigate(['']);
      this.toast.error("Please log in using you credentials to proceed","",{
        toastClass:'false-login'
      })
    }

    
  
  }
  }