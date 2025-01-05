import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { ToastrService } from 'ngx-toastr';
import { User } from 'src/app/models/User.model';
import { UserserviceService } from 'src/app/Services/AdminServices/UserService/userservice.service';
import { AccountService } from 'src/app/Services/ClientServices/accountService/account.service';
import { AuthenticationService } from 'src/app/Services/authenticationService/authentication.service';
import { CartService } from 'src/app/Services/CartService/cart.service';
import { CartItems } from 'src/app/models/CartItems.model';

@Component({
  selector: 'app-cartview',
  templateUrl: './cartview.component.html',
  styleUrls: ['./cartview.component.css']
})
export class CartviewComponent implements OnInit{
  
  cartItems :CartItems[]=[]
  isUser:boolean=false;
  user: User = new User();
  userId!:number 

  qte!:number;
  prx!:number;

  accountForm!: FormGroup;
  showModal = false;
  modalRef?: BsModalRef

  passConfirm !:string;

  



  constructor(private router:Router, private toast :ToastrService,
    private userService : UserserviceService,private authService:AuthenticationService,
    private fb: FormBuilder,private modalService: BsModalService,
  private accountService :AccountService,private cartService : CartService ){

  }


  loadUserInfo(id:number):void{
    this.userService.getSingleUser(id).subscribe({
    next:(data:User)=>{
      this.user = data
      console.log("user nom " +this.user.nom);
    }
      })
  
    }




  loadCart(id:number){
    this.cartService.getUserCartItems(id).subscribe({
      next:(data)=>{
        this.cartItems=data

      }
    })
  }























  ngOnInit():void{

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
    this.loadCart(this.userId);
    
    
    if(this.authService.isConnected()===false){
      this.router.navigate(['']);
      this.toast.error("Please log in using you credentials to proceed","",{
        toastClass:'false-login'
      })
    }

    
  
  }
    
}
