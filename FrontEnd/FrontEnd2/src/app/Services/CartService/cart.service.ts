import { Injectable,Inject } from '@angular/core';
import { HttpClient, HttpHeaderResponse, HttpHeaders, HttpParams, HttpResponse } from '@angular/common/http';
import { CartItems } from 'src/app/models/CartItems.model';
import { Observable } from 'rxjs';
import { observableToBeFn } from 'rxjs/internal/testing/TestScheduler';


@Injectable({
  providedIn: 'root'
})
export class CartService {
  private apiUrl = 'http://localhost:8082/api/carts';


  constructor(@Inject('NonAuthorizedHttpClient') private http : HttpClient) { }


  getUserCartItems(id: number): Observable<CartItems[]> {
    const params = new HttpParams().set('userId', id.toString());
    return this.http.get<CartItems[]>(`${this.apiUrl}/items`, { params });
  }


  getCartIdOfUser(id: number): Observable<number> {
    const params = new HttpParams().set('userId', id.toString());
    return this.http.get<number>(`${this.apiUrl}/cartId`, { params });
  }



  updateItemQte(userId:number,cartItemId:number,qte:number) : Observable<HttpResponse<string>>{
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    const params = new HttpParams().set('userId',userId.toString()).set('cartItemId',cartItemId.toString()).set('qte',qte.toString());
    return this.http.put(`${this.apiUrl}/updateCart`,null,{headers,params,responseType:'text',observe:'response'})

  }


  deleteItemFromCart(userId:number,cartItemId:number):Observable<HttpResponse<any>>{
    const params = new HttpParams().set('userId',userId.toString()).set('cartItemId',cartItemId.toString());
    return this.http.delete<any>(`${this.apiUrl}/removeItem`,{params})
  }

  addToCart(userId:number,productId:number,qte:number):Observable<HttpResponse<string>>{
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    const params = new HttpParams().set('userId',userId.toString()).set('productId',productId.toString()).set('qte',qte.toString());
    return this.http.post(`${this.apiUrl}/addToCart`,null,{headers,params,responseType:'text',observe:'response'});
  }


  getTotalToPay(id:number):Observable<number>{
    const params = new HttpParams().set('userId', id.toString());
    return this.http.get<number>(`${this.apiUrl}/totalToPay`, { params });
    
  }

  // getItemProductImg(id:number):Observable<string>{
  //   const params = new HttpParams().set('productId', id.toString());
  //   return this.http.get<string>(`${this.apiUrl}/img`,{params});
  // }
  
}
