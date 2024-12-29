import { Injectable } from "@angular/core";
import { HttpInterceptor,HttpRequest,HttpHandler, HttpEvent } from "@angular/common/http";

import { AuthenticationService } from "./Services/authenticationService/authentication.service";



@Injectable()
export class AuthInterceptor implements HttpInterceptor{
    constructor (private authService : AuthenticationService){}
    intercept(req: HttpRequest<any>, next: HttpHandler){
        const token = this.authService.getToken();
        if(token){
            req = req.clone({
                setHeaders:{
                    Authorization : `Bearer ${token}`
                }
            });
        }
        return next.handle(req);
    }

}