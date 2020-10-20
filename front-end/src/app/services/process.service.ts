import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "../../environments/environment";
import { Process } from '../models/process.model';

@Injectable({
  providedIn: "root",
})
export class ProcessService {
  constructor(private http: HttpClient) {}
  apiURL = environment.apiURL + "/process";

  save(process: Process): Observable<Process> {
    return this.http.post<Process>(this.apiURL, process);
  }

  findAll(): Observable<Process[]> {
    return this.http.get<Process[]>(`${this.apiURL}`);
  }

  getById(id: string): Observable<Process> {
    return this.http.get<Process>(`${this.apiURL}/${id}`);
  }

  finishProcess(id: number) {
    return this.http.get(`${this.apiURL}/${id}/finish-process`);
  }
  
  addFedback(process: Process) {
    return this.http.put(`${this.apiURL}/${process.id}/add-feedback`, process);
  }

  addResponsibleClients(id, responsibleClients) {
    return this.http.put(`${this.apiURL}/${id}/responsible-clients`, responsibleClients);
  }
}
