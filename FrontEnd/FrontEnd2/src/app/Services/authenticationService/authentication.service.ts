import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Route, Router } from '@angular/router';
import  {JwtHelperService} from '@auth0/angular-jwt';
import { BehaviorSubject, Observable } from 'rxjs';


interface AuthResponse{
  token : string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private apiUrl = 'http/localhost:8080/api/auth';
  private tokenKey = 'authToken';
  private jwtHelper = new JwtHelperService();

  //
  private authSubject = new BehaviorSubject<boolean>(this.hasToken());



  constructor( private http :HttpClient,private router : Router) { }

  login(email:string , password:string): Observable<AuthResponse>{
    return this.http.post<AuthResponse>(`${this.apiUrl}/login`,{email,password});
  }

  register(nom:string,prenom:string,email:string,password:string):Observable<AuthResponse>{
    return this.http.post<AuthResponse>(`${this.apiUrl}/register`,{nom,prenom,email,password});
  }

  storeToken(token:string):void{
    localStorage.setItem(this.tokenKey,token);
    this.authSubject.next(true);
  }

  getToken():string|null{
    return localStorage.getItem(this.tokenKey);
  }

  // we used !! for double negation , the methode type is boolean but the getToken return a string or null , and if the token exist if we use ! then is it will be false instead of true
  //that why we use the double negation

  hasToken():boolean{
    return !!this.getToken();
  }

  getUserId() :string|null{
    const token  = this.getToken();
    if(token){
      const decodedToken = this.jwtHelper.decodeToken(token);
      console.log('Decoded Token ;' , decodedToken);
      if(decodedToken){
        if(decodedToken.id){
          return decodedToken.id;
        }
      }
    }
    return null;
  }

  getRole() :string|null{
    const token = this.getToken ();
    if(token){
      const decodedToken = this.jwtHelper.decodeToken(token);
      if(decodedToken){
        if(decodedToken.role){
          return decodedToken.role;
        } 
      }
    }
    return null;
  }

  logout():void{
    localStorage.removeItem(this.tokenKey);
    this.authSubject.next(false);
    this.router.navigate(['/login']);
  }

  isAuthenticated(): Observable<boolean>{
    return this.authSubject.asObservable();
  }

  //adding this to get the token in the header of the request to send it , it will added in an interceptor that will automatically add the header in every request before sending it to prevent adding the header manually every time
  getAuthHeader():HttpHeaders{
    return new HttpHeaders({
      'Authorization' : `Bearer ${this.getToken()}`
    });
  }
}
