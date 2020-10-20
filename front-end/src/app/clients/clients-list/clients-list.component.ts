import { Component, OnInit } from "@angular/core";
import { ClientsService } from "src/app/services/clients.service";
import { ClientRoleHelper } from "../../models/client-role";
import { Client } from "../../models/client.model";

@Component({
  selector: "app-clients-list",
  templateUrl: "./clients-list.component.html",
  styleUrls: ["./clients-list.component.css"],
})
export class ClientsListComponent implements OnInit {
  public clients: Client[] = [];
  public selectedClient: Client = new Client();
  public errorMessage: string;
  public messages: string[] = [];
  public showMessage: boolean = false;
  public error: boolean = false;

  constructor(private clientService: ClientsService) {}

  ngOnInit(): void {
    this.clientService.findAll().subscribe((res) => {
      this.clients = res;
    });
  }

  delete(client: Client) {
    this.selectedClient = client;
  }

  deleteClient() {
    this.clientService.delete(this.selectedClient).subscribe(
      (res) => {
        this.showMessage = true;
        this.messages = ["Cliente deletado com sucesso"];
        this.ngOnInit();
      },
      (error) => {
        this.error = true;
        this.showMessage = true;
        this.messages = [error.error.message];
      }
    );
  }

  roleTypeHelper(value) {
    return ClientRoleHelper.translate(value);
  }
}
