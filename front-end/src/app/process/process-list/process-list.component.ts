import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup } from "@angular/forms";
import { Process } from "../../models/process.model";
import { ProcessService } from "../../services/process.service";
import { ErrorsUtils } from "../../utils/format-errors.utils";
import { AuthService } from "../../services/auth-service";
import { LocalUser } from '../../models/local-user';

@Component({
  selector: "app-process-list",
  templateUrl: "./process-list.component.html",
  styleUrls: ["./process-list.component.css"],
})
export class ProcessListComponent implements OnInit {
  public processList: Process[] = [];
  public filteredProcessList: Process[] = [];
  public showMessage: boolean = false;
  public message: string = "Nenhum registro encontrado";

  public selectedProcess: Process;
  public finishedProcess: Process;

  public formGroup: FormGroup;
  public currentUser: LocalUser;

  constructor(
    private workOrderService: ProcessService,
    private formBuilder: FormBuilder,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.search();
    this.createForm();
    this.currentUser = this.authService.getLocalUser();
  }

  createForm() {
    this.formGroup = this.formBuilder.group({
      feedback: [""],
    });
  }

  search() {
    this.workOrderService.findAll().subscribe((res) => {
      this.processList = res;      
      this.filteredProcessList = [...this.processList];
      if (this.processList.length == 0) {
        this.showMessage = true;
      } else {
        this.showMessage = false;
      }
    });
  }

  finishProcess() {
    this.workOrderService.finishProcess(this.finishedProcess.id).subscribe(
      (res) => {
        const foundIndex = this.processList.findIndex(process => process.id == this.finishedProcess.id);
        this.processList[foundIndex].finalized = true;
        this.message = "Processo encerrado com sucesso";
        this.showMessage = true;
      },
      (err) => {
        this.message = err.error.message        
        this.showMessage = true;
      }
    );
  }

  addFeedbackToProcess() {
    this.selectedProcess.feedback = this.formGroup.get("feedback").value;
    this.workOrderService.addFedback(this.selectedProcess).subscribe(
      (res) => {
        this.message = "Usuários adicionados sucesso";
        this.showMessage = true;
      },
      (err) => {
        this.message = "Não foi possível adicionar os usuários";
        this.showMessage = true;
      }
    );
  }

  prepareAddFeedback(process: Process) {
    this.selectedProcess = process;
  }

  prepareFinishProcess(process: Process) {
    this.finishedProcess = process;
  }

  filterFinalized(event) {
    const value = event.srcElement.checked;
    if (value == true) {
      this.filteredProcessList = [...this.processList ];
    } else {
      this.filteredProcessList = [
        ...this.processList.filter((process) => process.finalized == value),
      ];
    }
    if (this.filteredProcessList.length == 0) {
      this.message = "Nenhum registro encontrado";
      this.showMessage = true;
    } else {
      this.showMessage = false;
    }
  }

  isTriador() {
    const role = this.authService.getLocalUserRole();
    if (role == "FINISHER") {
      return false;
    } else {
      return true;
    }
  }
}
