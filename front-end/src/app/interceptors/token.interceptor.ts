import { Injectable } from "@angular/core";
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
} from "@angular/common/http";
import { Observable } from "rxjs";
import { AuthService } from "../services/auth-service";

@Injectable()
export class TokenInterceptor implements HttpInterceptor {
  constructor(private authService: AuthService) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const user: any = this.authService.getLocalUser();
    const url = request.url;

    if (user && !url.endsWith("/login")) {
      request = request.clone({
        setHeaders: {
          Authorization: "Bearer " + user.token,
        },
      });
    }

    return next.handle(request);
  }
}
