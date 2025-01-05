import { Component } from '@angular/core';
import { AuthenticationService } from 'src/app/Services/authenticationService/authentication.service';

@Component({
  selector: 'app-navbar2',
  templateUrl: './navbar2.component.html',
  styleUrls: ['./navbar2.component.css']
})
export class Navbar2Component {
  constructor(private authServive :AuthenticationService){}

  logout(){
    this.authServive.logout();
    localStorage.clear();

  }

}
