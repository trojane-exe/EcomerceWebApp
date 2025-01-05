import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Payement } from 'src/app/models/Payement.model';

@Injectable({
  providedIn: 'root'
})
export class PayementService {
  private apiUrl = 'http://localhost:8083/api/payement';


  constructor(@Inject('NonAuthorizedHttpClient') private http : HttpClient) { }



  getFactures(id:number) : Observable<Payement[]>{
    const params = new HttpParams().set('userId', id.toString());
    return this.http.get<Payement[]>(`${this.apiUrl}/myFactures`,{params});
  }

  getLastId(id:number):Observable<number>{
    const params = new HttpParams().set('userId', id.toString());
    return this.http.get<number>(`${this.apiUrl}/getFactureId`,{params});

  }

  proceedToPay(payement: Payement):Observable<any>{
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post(`${this.apiUrl}/proceed`,payement ,{headers,responseType:'text',observe:'response'})
  }

  validatePayement(id:number):Observable<any>{
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    const params = new HttpParams().set('id', id.toString());
    return this.http.put(`${this.apiUrl}/validate`,null,{headers,params,responseType:'text',observe:'response'});
  }


}
