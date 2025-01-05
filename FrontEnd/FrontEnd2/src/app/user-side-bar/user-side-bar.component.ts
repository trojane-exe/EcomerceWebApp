import { Component } from '@angular/core';
import { AuthenticationService } from '../Services/authenticationService/authentication.service';

@Component({
  selector: 'app-user-side-bar',
  templateUrl: './user-side-bar.component.html',
  styleUrls: ['./user-side-bar.component.css']
})
export class UserSideBarComponent {

    constructor(private authServive :AuthenticationService){}
  
    logout(){
      this.authServive.logout();
      localStorage.clear();
  
    }

}
