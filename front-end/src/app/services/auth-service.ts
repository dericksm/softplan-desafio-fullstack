import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { JwtHelperService } from "@auth0/angular-jwt";
import { Observable } from "rxjs";
import { environment } from "../../environments/environment";
import { Credentials } from "../models/credentials.model";
import { LocalUser } from "../models/local-user";
import { User } from "../models/user.model";

@Injectable({
  providedIn: "root",
})
export class AuthService {
  constructor(private http: HttpClient) {}

  private apiURL: string = environment.apiURL;
  private jwtHelper: JwtHelperService = new JwtHelperService();

  save(user: User): Observable<any> {
    return this.http.post(this.apiURL, user);
  }

  login(email: string, password: string) {
    const credentials: Credentials = new Credentials(email, password);
    return this.http.post(this.apiURL + "/login", credentials, {
      observe: "response",
    });
  }

  isAuthenticated() {
    const localUser = this.getLocalUser();
    if (localUser) {
      const expired = this.jwtHelper.isTokenExpired(localUser.token);
      return !expired;
    }
    return false;
  }

  closeSession() {
    localStorage.removeItem("localUser");
  }

  successfulLogin(authorizationValue: string) {
    let tok = authorizationValue.substring(7);
    let user: LocalUser = {
      token: tok,
      email: this.jwtHelper.decodeToken(tok).sub,
      roles: this.jwtHelper.decodeToken(tok).role,
    };
    this.setLocalUser(user);
  }

  getLocalUser(): LocalUser {
    let usr = localStorage.getItem("localUser");
    if (usr == null) {
      return null;
    } else {
      return JSON.parse(usr);
    }
  }

  getLocalUserRole() {
    return this.getLocalUser().roles[0];
  }

  setLocalUser(obj: LocalUser) {
    if (obj == null) {
      localStorage.removeItem("localUser");
    } else {
      localStorage.setItem("localUser", JSON.stringify(obj));
    }
  }
}
