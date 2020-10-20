import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { AuthService } from "../../services/auth-service";

@Component({
  selector: "app-sidebar",
  templateUrl: "./sidebar.component.html",
  styleUrls: ["./sidebar.component.css"],
})
export class SidebarComponent implements OnInit {
  constructor(private authService: AuthService, private router: Router) {}

  public currentUser: string;

  ngOnInit(): void {
    this.currentUser = this.authService.getLocalUser().email;
  }

  logout() {
    this.authService.closeSession();
    this.router.navigate(["/login"]);
  }

  isAdmin() {
    const role = this.authService.getLocalUserRole();
    if (role == "ADMIN") {
      return true;
    } else {
      return false;
    }
  }
}
