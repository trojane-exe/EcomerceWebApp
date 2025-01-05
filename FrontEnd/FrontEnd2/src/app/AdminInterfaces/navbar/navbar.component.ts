import { Component, OnInit } from '@angular/core';
import { data } from 'jquery';
import { User } from 'src/app/models/User.model';
import { UserserviceService } from 'src/app/Services/AdminServices/UserService/userservice.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  user: User = new User();
  userId!:number


  constructor(private userService : UserserviceService){}

  loadUserInfo(id:number):void{

    this.userService.getSingleUser(id).subscribe({
      next:(data:User)=>{
        this.user = data
      }
    })

  }


  ngOnInit(): void {
    const id =  localStorage.getItem('userId');
    if(id){this.userId = parseInt(id,10)}
    this.loadUserInfo(this.userId);
    console.log("user nom"+this.user.nom);
    
  }
}
