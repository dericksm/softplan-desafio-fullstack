import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { AuthService } from "../services/auth-service";

@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.css"],
})
export class LoginComponent implements OnInit {
  constructor(private router: Router, private authService: AuthService) {}

  public username: string = "";
  public password: string = "";
  public loginError: boolean = false;
  public newUser: boolean = false;
  public successMessage: string;
  public errors: string[] = [];
  ngOnInit(): void {}

  onSubmit() {
    this.authService.login(this.username, this.password).subscribe(
      (res) => {
        this.authService.successfulLogin(res.headers.get("Authorization"));
        this.router.navigate(["/process"]);
      },  
      (err) => {
        this.errors = ["Usuário e/ou senha incorretos."];
      }
    );
  }
}
