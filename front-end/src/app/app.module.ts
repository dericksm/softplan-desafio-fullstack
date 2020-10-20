import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ClientsModule } from './clients/clients.module';
import { TokenInterceptor } from './interceptors/token.interceptor';
import { LayoutComponent } from './layout/layout.component';
import { LoginComponent } from './login/login.component';
import { AuthService } from './services/auth-service';
import { ClientsService } from './services/clients.service';
import { ProcessService } from './services/process.service';
import { TemplateModule } from './template/template.module';
import { ProcessModule } from './process/process.module';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    LayoutComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    RouterModule,
    TemplateModule,
    ClientsModule,
    ProcessModule,
    HttpClientModule
  ],
  providers: [ClientsService, ProcessService, AuthService,
  {
    provide: HTTP_INTERCEPTORS,
    useClass: TokenInterceptor,
    multi: true
  }],
  bootstrap: [AppComponent]
})
export class AppModule { }
