import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Client } from "../models/client.model";
import { environment } from "../../environments/environment";

@Injectable({
  providedIn: "root",
})
export class ClientsService {
  constructor(private http: HttpClient) {}

  apiURL = environment.apiURL + '/clients'

  save(client: Client): Observable<Client> {
    return this.http.post<Client>(`${this.apiURL}`, client);
  }

  findAll(): Observable<Client[]> {
    return this.http.get<Client[]>(`${this.apiURL}`);
  }

  getById(id: string): Observable<Client> {
    return this.http.get<Client>(`${this.apiURL}/${id}`);
  }

  update(client: Client): Observable<Client> {
    return this.http.put<any>(
      `${this.apiURL}/${client.id}`,
      client
    );
  }

  delete(client: Client): Observable<Client> {
    return this.http.delete<any>(
      `${this.apiURL}/${client.id}`
    );
  }
}
