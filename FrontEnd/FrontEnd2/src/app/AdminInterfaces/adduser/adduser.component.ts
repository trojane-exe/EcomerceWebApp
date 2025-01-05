import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { User } from 'src/app/models/User.model';
import { Form,FormBuilder } from '@angular/forms';
import { NgForm } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { UserserviceService } from 'src/app/Services/AdminServices/UserService/userservice.service';
import { AuthenticationService } from 'src/app/Services/authenticationService/authentication.service';

@Component({
  selector: 'app-adduser',
  templateUrl: './adduser.component.html',
  styleUrls: ['./adduser.component.css']
})
export class AdduserComponent implements OnInit{
  user :User = new User();
  passwordConfirm !:string;
  isUser:boolean=false;

  



  constructor(private router:Router, private toast :ToastrService,private userService : UserserviceService,private authService:AuthenticationService){

  }


  

  addUser(form :NgForm){
    if(!form.valid){
      this.toast.warning("Please fill all the Fields and try again","",{toastClass:'ngx-toastr shake-toast login-warning'});
    }
    else{
       if(!this.confirmPassword())
        return;
      else{
    if(this.isUser===true){
      this.user.role='Admin';
    }
    else{
      this.user.role='User'
    }
    console.log("user role "+this.user.role);
    this.userService.addUser(this.user).subscribe({
      next :()=>{
        this.toast.success("User added successfully");
        this.router.navigate(['/users']);
      },
      error:(err)=>{
        this.toast.error("Error while inserting the user");
        
      }
    })
  }
}


  
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



  
  close(){
    this.router.navigate(['/users']);
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
    this.toast.info("the red * marks a required fields","",{
      toastClass:'fields-warning'
    })
  }

    


}
