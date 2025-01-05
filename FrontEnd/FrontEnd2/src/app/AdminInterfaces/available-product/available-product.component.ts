import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { ToastrService } from 'ngx-toastr';
import { Product } from 'src/app/models/Product.model';
import { User } from 'src/app/models/User.model';
import { ProductService } from 'src/app/Services/AdminServices/ProductService/product.service';
import { UserserviceService } from 'src/app/Services/AdminServices/UserService/userservice.service';
import { AuthenticationService } from 'src/app/Services/authenticationService/authentication.service';

@Component({
  selector: 'app-available-product',
  templateUrl: './available-product.component.html',
  styleUrls: ['./available-product.component.css']
})
export class AvailableProductComponent implements OnInit {

  products : Product []=[]
  userId!:number
  user: User = new User();
  product : Product = new Product();
  productId!:number
  qte : { [key: number]: number } = {};
  modalRef?: BsModalRef
  searchtext : any;

  selectedFile: File | null = null;
  img!: File ;
  
  constructor(private productService : ProductService,private authService:AuthenticationService,
    
    private toast :ToastrService,private router:Router,
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
    this.productService.getAvailableProducts().subscribe({
      next:(data)=>{
        this.products = data;
        
        
      },
      error:()=>{
        console.log("ERROR WHILE GETTING PRODUCTS ")
      }
    })
    
  }

  

  

      


  ngOnInit(): void {

    if(this.authService.getRole()==='User'){
      this.toast.info("Regular users are not allowed to access this page")
      this.authService.logout();
      this.router.navigate([''])
    }

    if(this.authService.isConnected()===false){
      this.authService.logout();
      this.router.navigate(['']);
      this.toast.error("Please log in using you credentials to proceed","",{
        toastClass:'false-login'
      })
    }
    const id =  localStorage.getItem('userId');
    if(id){this.userId = parseInt(id,10)}
    this.loadUserInfo(this.userId);
    this.loadProducts();
    
    if(this.authService.isConnected()===false){
      this.router.navigate(['']);
      this.toast.error("Please log in using you credentials to proceed","",{
        toastClass:'false-login'
      })
    }
  
  }
}