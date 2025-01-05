import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { User } from 'src/app/models/User.model';
import { UserserviceService } from 'src/app/Services/AdminServices/UserService/userservice.service';
import { CartService } from 'src/app/Services/CartService/cart.service';
import { PayementService } from 'src/app/Services/PayementService/payement.service';


@Component({
  selector: 'app-payement-form',
  templateUrl: './payement-form.component.html',
  styleUrls: ['./payement-form.component.css']
})
export class PayementFormComponent implements OnInit{
  userId!:number;
  user:User = new User()
  name !:string
  total !:number
  factureId!:number;

  constructor(private userService: UserserviceService,
    private cartService:CartService,
    private payService :PayementService,
    private toast : ToastrService,
    private router : Router
  ){}



  loadUser(id:number){
    this.userService.getSingleUser(id).subscribe({
      next:(data:User)=>{
        this.user=data;
        this.name = this.user.nom +' '+ this.user.prenom
        console.log("fullname : "+ this.name);

      }
    })
  }

  getFactureId(id:number){
    this.payService.getLastId(id).subscribe({
      next:(data)=>{
        this.factureId = data;
      }
    })
  }


  getTotal(id:number){
    this.cartService.getTotalToPay(id).subscribe({
      next:(data)=>{
        this.total = data
        console.log("the total to pay is :" +this.total)
      }
    })
  }

  validatePayement(){
    if(this.total==null){
      this.toast.warning("There is no item in yout cart ,please order some products before proceeding");
      this.router.navigate(['/shop'])
    }
    else{
    this.payService.validatePayement(this.factureId).subscribe({
      next:()=>{
        this.toast.success("Payement Validated");
        this.router.navigate(['/orders'])
        
      }
    })
  }
  }


  ngOnInit(): void {
    const userIdFromStorage = localStorage.getItem('userId');
    if (userIdFromStorage) {
      this.userId = parseInt(userIdFromStorage, 10);
      console.log('User ID retrieved in ProfileComponent:', this.userId);
    } else {
      console.error('User ID is not available in localStorage.');
      // Handle the case where userId is not available, e.g., redirect to login
    }
    this.getTotal(this.userId);
    this.getFactureId(this.userId)





    this.loadUser(this.userId)
    
  }

}
