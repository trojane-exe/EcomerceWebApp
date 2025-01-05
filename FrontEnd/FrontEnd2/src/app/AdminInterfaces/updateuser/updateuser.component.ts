import { Component, OnInit } from '@angular/core';

import { ActivatedRoute,Router } from '@angular/router';
import { AuthenticationService } from 'src/app/Services/authenticationService/authentication.service';
import { User } from 'src/app/models/User.model';
import { UserserviceService } from 'src/app/Services/AdminServices/UserService/userservice.service';
import { ToastrService } from 'ngx-toastr';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-updateuser',
  templateUrl: './updateuser.component.html',
  styleUrls: ['./updateuser.component.css']
})
export class UpdateuserComponent implements OnInit{

  userid!:number;
  user:User = new User();
  isUser:boolean=false;
  passConfirm !:string;

  constructor(private route:ActivatedRoute,
    private userService : UserserviceService,
    private authService : AuthenticationService,
    private toast : ToastrService,
    private router : Router
  ){}

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



    updateUser(form :NgForm){
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
      this.userService.updateUser(this.user,this.userid).subscribe({
        next :()=>{
          this.toast.success("User Updated successfully");
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

    this.userid = this.route.snapshot.params['id']
    console.log(this.userid)
    this.loadUserInfo(this.userid);
    
  }

}
