import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { UserserviceService } from '../Services/AdminServices/UserService/userservice.service';
import { AuthenticationService } from '../Services/authenticationService/authentication.service';
import { CartService } from '../Services/CartService/cart.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Cart } from '../models/Cart.model';
import { data } from 'jquery';
import { CartItems } from '../models/CartItems.model';
import { NumberValueAccessor } from '@angular/forms';
import { PayementService } from '../Services/PayementService/payement.service';
import { Payement } from '../models/Payement.model';



@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {

  isSidebarOpen = false;
  userId!:number
  cartitems : CartItems[]=[]
  imgUrl !:string;
  qte !:number
  payement : Payement=new Payement();

  totalToPay!:number;







  constructor(private userService:UserserviceService,
    private authService:AuthenticationService,
    private cartItemService : CartService,
    private router : Router,
    private cdr: ChangeDetectorRef,
    private payService : PayementService,
    private toast : ToastrService){


    }




    toggleSidebar(): void {
      this.isSidebarOpen = !this.isSidebarOpen;
    }

    updateQte(userId:number,cartItemId:number,qte:number){
      console.log(userId+'  '+'  '+cartItemId+'  '+'  '+qte)
      this.cartItemService.updateItemQte(userId,cartItemId,qte).subscribe({
        next:()=>{
          this.toast.success("updated");
        },
        error:(err)=>{
          this.toast.error("Insuficent stock ");
        }
      })
      setTimeout(() => {
        window.location.reload();
      }, 1500);
      
    }

    deleteItem(userId:number,cartItemId:number){
      console.log(userId+'  '+'  '+cartItemId+'  ')
      this.cartItemService.deleteItemFromCart(userId,cartItemId).subscribe({
        next:()=>{
        
         this.cartitems=this.cartitems.filter(itm=>itm.id!=cartItemId);
          this.toast.success("Order removed from your cart","",{toastClass:'cart-toast-sucess'       })
        },
        error:(err)=>{
          this.toast.error("error , try again later"+err);
        }
      })
      setTimeout(() => {
        window.location.reload();
      }, 1000);
       
    }

    


    getItems(id:number){
      this.cartItemService.getUserCartItems(id).subscribe({
        next:(data)=>{
          this.cartitems=data;
          this.cdr.detectChanges();
          
        }
      })
    }

    getCartID(id:number){
      this.cartItemService.getCartIdOfUser(id).subscribe({
        next:(data)=>{
          this.payement.cartId=data;
          console.log("the cart id is : "+this.payement.cartId)
        }
      })
    }
  



    // getProductImg(id:number){
    //   this.cartItemService.getItemProductImg(id).subscribe({
    //     next:(data)=>{
    //       this.imgUrl=data
    //     }
    //   })
    // }

    proceedToPayement():void{
      this.getTotal(this.userId);
      if(this.totalToPay==null){
        this.toast.warning("Your cart is still empty , please order something before proceeding")
        window.location.reload();
      }
      else{
      this.payement.userId = this.userId
      this.payement.total = this.totalToPay     

      this.payService.proceedToPay(this.payement).subscribe({
        next:()=>{
          this.router.navigate(['/payements']);
        }
      })
    }

    }

    getTotal(id:number){
      this.cartItemService.getTotalToPay(id).subscribe({
        next:(data)=>{
          this.totalToPay = data;
          console.log('total :'+ this.totalToPay)
        }
      })
    }
  
    

  






    
  


  ngOnInit(): void {



    if(this.authService.isConnected()===false){
      this.authService.logout();
      this.router.navigate(['']);
      this.toast.error("Please log in using you credentials to proceed","",{
        toastClass:'false-login'
      })
    }
    else{
    const id = localStorage.getItem('userId');
    if(id){
    this.userId = parseInt(id,10);
    this.getItems(this.userId);
    }
    this.getTotal(this.userId);
    this.getCartID(this.userId);
    // console.log("the total to pay will be : "+this.totalToPay)
    console.log("we are now in the users page");
    console.log("the current user id in the users page is :"+this.userId);
  }
    
  }
}
