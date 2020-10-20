import { Injectable } from "@angular/core";
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot,
} from "@angular/router";
import { AuthService } from "../services/auth-service";

@Injectable({
  providedIn: "root",
})
export class AuthGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const authenticated = this.authService.isAuthenticated();
    const userRole = this.authService.getLocalUserRole();

    if (authenticated) {
      if (next.data.role && next.data.role.indexOf(userRole) === -1) {
        this.router.navigate(['/process']);
        return false;
      }

      return true;
    } else {
      this.router.navigate(["/login"]);
      return false;
    }
  }
}
