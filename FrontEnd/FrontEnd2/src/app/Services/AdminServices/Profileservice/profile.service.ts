import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {
  private apiUrl = 'http://localhost:8080/api/admin';


  

  constructor(private http : HttpClient) {}



  getUserIdByEmail(email:string) : Observable<number>{
    const params = new HttpParams().set('email',email);
    return this.http.get<number>(`${this.apiUrl}/getId`,{params});
  }


}
