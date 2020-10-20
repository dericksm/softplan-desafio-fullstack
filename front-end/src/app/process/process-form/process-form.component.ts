import { Component, OnDestroy, OnInit, ViewChild } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ActivatedRoute } from "@angular/router";
import { DxDataGridComponent } from "devextreme-angular";
import { Subject } from "rxjs";
import { takeUntil } from "rxjs/operators";
import { ClientRoleHelper } from "../../models/client-role";
import { Client } from "../../models/client.model";
import { Process } from "../../models/process.model";
import { ClientsService } from "../../services/clients.service";
import { ProcessService } from "../../services/process.service";

@Component({
  selector: "app-process-form",
  templateUrl: "./process-form.component.html",
  styleUrls: ["./process-form.component.css"],
})
export class ProcessFormComponent implements OnInit, OnDestroy {
  @ViewChild("dataGridRef") dataGrid: DxDataGridComponent;

  public clients: Client[] = [];

  public showMessage: boolean = false;
  public error: boolean = false;
  public messages: String[] = ["Salvo com sucesso"];
  public formGroup: FormGroup;
  public id: string;
  public selectedRowsKeys = [];

  private unsubscribe: Subject<void> = new Subject();

  constructor(
    private clientService: ClientsService,
    private processService: ProcessService,
    private formBuilder: FormBuilder,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get("id");
    if (this.id != null) {
      this.processService
        .getById(this.id)
        .pipe(takeUntil(this.unsubscribe))
        .subscribe((res: Process) => {
          this.formGroup.patchValue(res);
          this.formGroup.get('name').disable()
          res.responsibleClients.forEach((user) => {
            this.selectedRowsKeys.push(user.id);
          });
        });
    }

    this.clientService.findAll().subscribe((res) => (this.clients = res));
    this.createForm();
  }

  ngOnDestroy(): void {
    this.unsubscribe.next();
    this.unsubscribe.complete();
  }

  createForm() {
    this.formGroup = this.formBuilder.group({
      id: [""],
      name: ["", Validators.required],
      feedback: [""],
    });
  }

  save() {
    const process: Process = this.formGroup.value;
    process.responsibleClients = this.dataGrid.instance.getSelectedRowsData();

    if (this.id == null || this.id == undefined) {
      delete process.id;

      this.processService.save(process).subscribe(
        (res) => {
          this.showMessage = true;
          this.error = false;
          this.formGroup.reset()
          this.dataGrid.instance.clearSelection()
        },
        (error) => {
          this.showMessage = true;
          this.error = true;
          this.messages = [error.error.errors];
        }
      );
    } else {      
      const responsibleClients = this.dataGrid.instance.getSelectedRowsData();
      this.processService.addResponsibleClients(this.id, responsibleClients).subscribe(
        (res) => {
          this.messages = ["Usuários adicionados com sucesso"];
          this.showMessage = true;
          this.error = false;
        },
        (error) => {
          this.showMessage = true;
          this.error = true;
          this.messages = [error.error.errors];
        }
      );
    }
  }

  roleTypeHelper(value) {
    return ClientRoleHelper.translate(value.role);
  }
}
