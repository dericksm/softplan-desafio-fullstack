import { Component, OnDestroy, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ActivatedRoute } from "@angular/router";
import { isNullOrUndefined } from "util";
import { ClientsService } from "../../services/clients.service";
import { ClientRole } from "../../models/client-role";
import { Client } from "../../models/client.model";
import { ErrorsUtils } from "../../utils/format-errors.utils";

@Component({
  selector: "app-clients-form",
  templateUrl: "./clients-form.component.html",
  styleUrls: ["./clients-form.component.css"],
})
export class ClientsFormComponent implements OnInit {
  public showMessage: boolean = false;
  public error: boolean = false;
  public messages: String[] = ["Salvo com sucesso"];
  private id: string;

  public roles = ClientRole;

  public formGroup: FormGroup;

  constructor(
    private clientService: ClientsService,
    private route: ActivatedRoute,
    private formBuilder: FormBuilder
  ) {}

  ngOnInit(): void {
    this.createForm();
    this.id = this.route.snapshot.paramMap.get("id");
    if (!isNullOrUndefined(this.id)) {
      this.clientService.getById(this.id).subscribe(
        (res) => {
          this.formGroup.patchValue(res);
          this.formGroup.get("password").disable();
        },
        (err) => {
          console.log(err);
        }
      );
    }
  }

  createForm() {
    this.formGroup = this.formBuilder.group({
      id: [""],
      name: ["", Validators.required],
      email: ["", Validators.required],
      password: ["", Validators.required],
      role: ["", Validators.required],
    });
  }

  save() {
    const client: Client = this.client;

    if (this.id == undefined || this.id == null) {
      delete client.id;

      this.clientService.save(client).subscribe(
        (res) => {
          this.messages = ["Salvo com sucesso"];
          this.error = false;
          this.showMessage = true;
          this.formGroup.reset();
        },
        (err) => {
          this.messages = ErrorsUtils.formatErrors(err.error.errors);
          this.showMessage = true;
          this.error = true;
        }
      );
    } else {
      this.clientService.update(client).subscribe(
        (res) => {
          this.messages = ["Usuário editado com sucesso"];
          this.error = false;
          this.showMessage = true;
        },
        (err) => {
          this.messages = ErrorsUtils.formatErrors(err.error.errors);
          this.showMessage = true;
          this.error = true;
        }
      );
    }
  }

  get client() {
    return this.formGroup.value;
  }

  public defaultSort() {
    return 0;
  }
}
