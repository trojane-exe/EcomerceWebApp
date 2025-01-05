import { NumberSymbol, PRECONNECT_CHECK_BLOCKLIST } from '@angular/common';
import { withNoXsrfProtection } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { data } from 'jquery';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { ToastrService } from 'ngx-toastr';
import { Product } from 'src/app/models/Product.model';
import { User } from 'src/app/models/User.model';

import { ProductService } from 'src/app/Services/AdminServices/ProductService/product.service';
import { UserserviceService } from 'src/app/Services/AdminServices/UserService/userservice.service';
import { AuthenticationService } from 'src/app/Services/authenticationService/authentication.service';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit{
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
    private modalService: BsModalService,
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
    this.productService.getAllProducts().subscribe({
      next:(data)=>{
        this.products = data;
        
        
      },
      error:()=>{
        console.log("ERROR WHILE GETTING PRODUCTS ")
      }
    })
    
  }

  
  

  updateProduct(form:NgForm){
    if(!form.valid){
      this.toast.warning("Please fill the required inputs")
    }
    if(this.img){


    this.productService.updateProduct(this.productId, this.product, this.img).subscribe(
      response => {
        this.toast.success("Product updated successfully","",{
          toastClass:'cart-toast-sucess'
        });
      })
    }
    else{
      this.productService.updateProductNoImg(this.productId, this.product).subscribe(
        response => {
          this.toast.success("Product updated successfully","",{
            toastClass:'cart-toast-sucess'
          });
        })
      
    }
    setTimeout(() => {
      window.location.reload();
    }, 1500);

  }
    
  



  openUpdateModal(id:number,template:any){
    this.modalRef = this.modalService.show(template); 
    this.toast.info("the red * marks a required fields","",{
      toastClass:'fields-warning'
    })
    this.productId=id;// Show the modal
    this.getProductById(id);
  }




      // Method to open the modal
      openModal(template: any,id:number) {
        this.modalRef = this.modalService.show(template); 

      }
      
      closeModal() {
        this.modalRef?.hide(); 

      }
  
      
    
      // Method for the action after confirmation
      // confirmAction() {
      //   this.productService.deleteProduct(this.productId).subscribe({
      //     next:()=>{
      //       this.products = this.products.filter(p=>p.productId!=this.productId);
      //       this.toast.success("Deleted successfully");
      //     }
      //   })
      //   console.log('Action confirmed');
      //   this.modalRef?.hide(); // Close the modal
      // }
    
      // Method to handle cancel
      cancelAction() {
        console.log('Action cancelled');
        this.modalRef?.hide(); // Close the modal
      }


      validateFile(event: any) {
        const file = event.target.files[0];
        if (file) {
            const validTypes = ['image/png', 'image/jpg', 'image/jpeg', 'image/JPG'];
            if (!validTypes.includes(file.type)) {
                this.toast.warning('Please upload a valid image file (PNG, JPG, or JPEG)');
                event.target.value = ''; // Clear the input if the file is invalid
            } else {
                this.img = file;
                console.log('File selected:', this.img); // Debugging line
            }
        }
    }
    
      getProductById(id:number){
        this.productService.getProductById(id).subscribe({
          next:(data)=>{
            this.product = data;
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
