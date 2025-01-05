import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { ToastrService } from 'ngx-toastr';
import { User } from 'src/app/models/User.model';
import { UserserviceService } from 'src/app/Services/AdminServices/UserService/userservice.service';
import { AccountService } from 'src/app/Services/ClientServices/accountService/account.service';
import { AuthenticationService } from 'src/app/Services/authenticationService/authentication.service';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountComponent implements OnInit{
  user :User = new User();
  isUser:boolean=false;

  userId!:number 

  accountForm!: FormGroup;
  showModal = false;
  modalRef?: BsModalRef

  passConfirm !:string;

  



  constructor(private router:Router, private toast :ToastrService,
    private userService : UserserviceService,private authService:AuthenticationService,
    private fb: FormBuilder,private modalService: BsModalService,
  private accountService :AccountService ){

  }


  loadUserInfo(id:number):void{
    this.userService.getSingleUser(id).subscribe({
      next :(data:User)=>{
        this.user = data;
        if(this.user.role==='User'){
          this.isUser=false
        }
        else{this.isUser=true}
        this.passConfirm=this.user.password
        
      },
      error :(err)=>{
        console.log("ERROR WHILE FETCHING DATA")
      }
    })
  }



  openModal(template: any) {
    this.modalRef = this.modalService.show(template); 
    this.toast.info("the red * marks a required fields","",{
      toastClass:'fields-warning'
    })
  }

  openDeleteModal(template: any) {
    this.modalRef = this.modalService.show(template); 
    this.toast.error("this action is irreversible","",{
      toastClass:'fields-warning'
    })
  }

  

  // Closes the modal without saving
  closeModal(): void {
    this.modalRef?.hide();
  }

  confirmAction() {
    this.userService.deleteUser(this.userId).subscribe({
      next:()=>{
        this.toast.success("Deleted successfully","Your account has been deleted with all your data");
        this.router.navigate([''])
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


    updateUser(form :NgForm){
        if(!form.valid){
          this.toast.warning("Please fill all the Fields and try again","",{toastClass:'ngx-toastr shake-toast login-warning'});
        }
        else{
           if(!this.confirmPassword())
            return;
          else{
        this.userService.updateUser(this.user,this.userId).subscribe({
          next :()=>{
            this.toast.success("User Updated successfully");
            this.router.navigate(['/account']);
          },
          error:(err)=>{
            this.toast.error("Error while inserting the user");
            
          }
        })
      }
        }
        this.modalRef?.hide(); 
      }
  
      
    confirmPassword(){
      const pass = (document.getElementById("passwordId") as HTMLInputElement).value;
      const confirm = (document.getElementById("confirmId") as HTMLInputElement).value
      if(pass!=confirm){
        this.toast.warning("The passwords are not matching","",{toastClass:'ngx-toastr shake-toast login-warning'})
        return false;
      }
      return true;
    }
  
    
  




ngOnInit():void{

  const userIdFromStorage = localStorage.getItem('userId');
  if (userIdFromStorage) {
    this.userId = parseInt(userIdFromStorage, 10);
    console.log('User ID retrieved in ProfileComponent:', this.userId);
  } else {
    console.error('User ID is not available in localStorage.');
    // Handle the case where userId is not available, e.g., redirect to login
  }
  this.loadUserInfo(this.userId);
  }

  

}
