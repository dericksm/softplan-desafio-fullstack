import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { LayoutComponent } from "../layout/layout.component";
import { AuthGuard } from '../guards/auth.guard';
import { ProcessFormComponent } from './process-form/process-form.component';
import { ProcessListComponent } from './process-list/process-list.component';

const routes: Routes = [
  {
    path: "process",
    component: LayoutComponent,    
    canActivate: [AuthGuard],
    data: {
      role: ["ADMIN", "TRIADOR"],
    },
    children: [
      { path: "form", component: ProcessFormComponent },
      { path: "list", component: ProcessListComponent },      
      { path: 'form/:id', component: ProcessFormComponent },      
      { path: '', redirectTo: '/process/list', pathMatch: 'full'}
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ProcessRoutingModule {}
