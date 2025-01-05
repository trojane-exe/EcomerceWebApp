import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthenticationService } from '../Services/authenticationService/authentication.service';
import { ProfileService } from '../Services/AdminServices/Profileservice/profile.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{
  loginForm: FormGroup;
  registrationForm: FormGroup;

   constructor(private fb: FormBuilder,private route : Router,private toast : ToastrService,private authService : AuthenticationService,
    private profile : ProfileService ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });

    this.registrationForm = this.fb.group({
      nom: ['', Validators.required],
      prenom: ['', Validators.required],
      your_email: ['', [Validators.required, Validators.email]],
      your_password: ['', Validators.required],
      confirm_password: ['', Validators.required]
    }, { validator: this.passwordMatchValidator });
  }

  passwordMatchValidator(formGroup: FormGroup) {
    const yourPassword = formGroup.get('your_password');
    const confirmPassword = formGroup.get('confirm_password');

    if (yourPassword && confirmPassword) {
      return yourPassword.value === confirmPassword.value ? null : { mismatch: true };
    }
    return null;
  }


  onLoginSubmit() {
    if (!this.loginForm.valid) {
      this.toast.warning("Please fill all the required fiels","error",{
        toastClass: 'ngx-toastr shake-toast login-warning',
    });
  }
    else{
      const{email,password} = this.loginForm.value;
      this.authService.login(email,password).subscribe({
        next : (response)=>{
          console.log("sucess : "+ response.token)
          this.authService.storeToken(response.token);
          console.log(this.authService.isConnected());
          this.profile.getUserIdByEmail(email).subscribe(
            (id: number) => {
              localStorage.setItem('userId', id.toString());
              console.log('User ID set in LoginComponent:', id);
            const role = this.authService.getRole();
            if(role==="Admin"){
              console.log("role is  : "+role);
              this.route.navigate(['/dashboard'])
            }
            else if(role==='User'){
              this.route.navigate(['/account'])
              console.log("Current role :"+role);
            }
          },
          (error) => {
            console.error('Error fetching user ID:', error);
          },
            
        );
      }
    })
  }
}

  onRegisterSubmit() {
    if (!this.registrationForm.valid) {
      this.toast.warning("Please fill all the required fiels","error",{
        toastClass: 'ngx-toastr shake-toast login-warning',});
    }
    else if (this.registrationForm.errors?.['mismatch']) {
      this.toast.warning("Passwords do not match!", "Error", {
        toastClass: 'ngx-toastr shake-toast login-warning',
      });
    }
    else{
      const{nom,prenom,your_email,your_password} = this.registrationForm.value;
      this.authService.register(nom,prenom,your_email,your_password).subscribe({
        next:()=>{
          this.toast.success("Account created successfully , please log in using your credentials","",{
            toastClass:'false-login'
          });
          this.route.navigate(['']);
        }
      })

    }
  }

  getLoginFormControl(name: string) {
    return this.loginForm.get(name);
  }

  getRegistrationFormControl(name: string) {
    return this.registrationForm.get(name);
  }

  ngOnInit(): void {
    this.toast.info("the red * marks a required fields","",{
      toastClass:'fields-warning'
    })
  }
}
