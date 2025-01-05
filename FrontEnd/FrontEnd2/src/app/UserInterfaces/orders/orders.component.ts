import { Component, OnInit } from '@angular/core';
import { data } from 'jquery';
import { Payement } from 'src/app/models/Payement.model';
import { PayementService } from 'src/app/Services/PayementService/payement.service';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit{

  payement : Payement[]=[];
  userId!:number;
  constructor(private payService : PayementService){}



  loadOrders(id:number){
    this.payService.getFactures(id).subscribe({
      next:(data)=>{
        this.payement=data
      }
    })
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
    this.loadOrders(this.userId);
    
    
  }

}
