import { Injectable,Inject } from '@angular/core';
import { HttpClient, HttpHeaderResponse, HttpHeaders, HttpParams, HttpResponse } from '@angular/common/http';
import { Product } from 'src/app/models/Product.model';
import { Observable } from 'rxjs';
import { NgStyle } from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private apiUrl ='http://localhost:8081/api/product'

  

  constructor(@Inject('NonAuthorizedHttpClient') private http :HttpClient) { }




  getAllProducts():Observable<Product[]>{
    return this.http.get<Product[]>(`${this.apiUrl}`);
  }

  // addProduct(product : Product):Observable<HttpResponse<string>>{
  //   const headers = new HttpHeaders({'Content-Type':'mulitipart/form-data'});
  //   return this.http.post(`${this.apiUrl}/add-product`,product,{headers,responseType:'text',observe:'response'});
  // }


  addProduct(product: Product, img: File): Observable<any> {
    const formData: FormData = new FormData();
    formData.append('nom', product.nom);
    formData.append('description', product.description);
    formData.append('stock', product.stock.toString());
    formData.append('prix', product.prix.toString());
    formData.append('categorie', product.categorie); // Ensure this field is included
    formData.append('img', img, img.name);

    // Debugging line to log the FormData object
    formData.forEach((value, key) => {
      console.log(key + ', ' + value);
    });

    return this.http.post<any>(`${this.apiUrl}/add-product`, formData);
  }


  updateProduct(id: number, product: Product, img: File): Observable<any> {
    const formData: FormData = new FormData();
    formData.append('nom', product.nom);
    formData.append('description', product.description);
    formData.append('stock', product.stock.toString());
    formData.append('prix', product.prix.toString());
    formData.append('categorie', product.categorie);
    if(img){
    formData.append('img', img, img.name);
    }
    

    // Debugging line to log the FormData object
    formData.forEach((value, key) => {
      console.log(key + ', ' + value);
    });

    return this.http.put<any>(`${this.apiUrl}/update-product/${id}`, formData);
  }

  updateProductNoImg(id: number, product: Product): Observable<any> {
    const formData: FormData = new FormData();
    formData.append('nom', product.nom);
    formData.append('description', product.description);
    formData.append('stock', product.stock.toString());
    formData.append('prix', product.prix.toString());
    formData.append('categorie', product.categorie);
    

    // Debugging line to log the FormData object
    formData.forEach((value, key) => {
      console.log(key + ', ' + value);
    });

    return this.http.put<any>(`${this.apiUrl}/update-product-noImg/${id}`, formData);
  }


  getProductByName(name:string):Observable< any[]>{
    const params = new HttpParams().set('name',name.toString());
    return this.http.get<any[]>(`${this.apiUrl}/find-name`,{params});
  }

  getProductById(id:number):Observable<Product>{
    return this.http.get<Product>(`${this.apiUrl}/${id}`);
  }


  getAvailableProducts():Observable<Product[]>{
    return this.http.get<Product[]>(`${this.apiUrl}/available`);
  }

  getOutOfStockProducts():Observable<Product[]>{
    return this.http.get<Product[]>(`${this.apiUrl}/out`);
  }
  

  updateStock(id: number, qte: number): Observable<HttpResponse<string>> {
    const params = new HttpParams().set('stock', qte.toString());
    console.log(params)

    return this.http.put(`${this.apiUrl}/update-stock/${id}`, null, { params, responseType: 'text', observe: 'response' });
  }



}
