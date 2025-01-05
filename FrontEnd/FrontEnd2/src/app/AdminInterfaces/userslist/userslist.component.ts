import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/User.model';
import { UserserviceService } from 'src/app/Services/AdminServices/UserService/userservice.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/Services/authenticationService/authentication.service';
import { BsModalService,BsModalRef } from 'ngx-bootstrap/modal';
@Component({
  selector: 'app-userslist',
  templateUrl: './userslist.component.html',
  styleUrls: ['./userslist.component.css']
})
export class UserslistComponent implements OnInit{
  users:User[]=[];
  userId!:number;
  modalRef?: BsModalRef

  constructor(private userService:UserserviceService,private toast : ToastrService
    ,private router :Router,private authService : AuthenticationService,private modalService: BsModalService){}

  loadUsers(){
    this.userService.getAllUsers().subscribe({
      next :(data)=>{
        if(data){
          console.log("data is here")
        }
        this.users = data
      },
      error :(err)=>{
        console.log("errror while fetching data")
      }
    })
  }


    // Method to open the modal
    openModal(template: any,id:number) {
      this.modalRef = this.modalService.show(template); 
      this.userId=id;// Show the modal
    }

    
  
    // Method for the action after confirmation
    confirmAction() {
      this.userService.deleteUser(this.userId).subscribe({
        next:()=>{
          this.users = this.users.filter(user=>user.userId!=this.userId);
          this.toast.success("Deleted successfully");
        }
      })
      console.log('Action confirmed');
      this.modalRef?.hide(); // Close the modal
    }
  
    // Method to handle cancel
    cancelAction() {
      console.log('Action cancelled');
      this.modalRef?.hide(); // Close the modal
    }
  







  navigateToUpdate(id:number){
    this.router.navigate(['/update-user',id])
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
    else{
    const id = localStorage.getItem('userId');
    if(id){
    this.userId = parseInt(id,10);
    }
    console.log("we are now in the users page");
    console.log("the current user id in the users page is :"+this.userId)
    this.loadUsers();
  }
}
  
  
}