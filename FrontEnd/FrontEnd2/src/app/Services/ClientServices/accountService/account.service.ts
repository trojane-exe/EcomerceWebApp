import { HttpClient, HttpResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from 'src/app/models/User.model';

@Injectable({
  providedIn: 'root'
})
export class AccountService { private apiUrl = 'http://localhost:8080/api/users';
  

  constructor(private http : HttpClient) {
    
   }


  updateInfos(user:User , userId:number) : Observable<HttpResponse<String>>{
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.put(`${this.apiUrl}/update-user/${userId}`,user,{headers,responseType:'text',observe:'response'});
  }

  deleteAccount(userId:number) : Observable<HttpResponse<any>>{
    return this.http.delete<any>(`${this.apiUrl}/delete-user/${userId}`);
  }

}
