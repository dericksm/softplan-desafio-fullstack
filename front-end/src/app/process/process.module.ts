import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { DxDataGridModule, DxSelectBoxModule } from 'devextreme-angular';
import { ProcessFormComponent } from './process-form/process-form.component';
import { ProcessListComponent } from './process-list/process-list.component';
import { ProcessRoutingModule } from './process-routing.module';



@NgModule({
  declarations: [ProcessFormComponent, ProcessListComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    RouterModule,
    DxDataGridModule,
    DxSelectBoxModule,
    ProcessRoutingModule
  ],
  exports : [
    ProcessFormComponent,
    ProcessListComponent
  ]
})
export class ProcessModule { }
