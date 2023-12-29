import { Injectable } from "@angular/core";
import { Consulta } from "../compartido/consulta";
import { Observable, catchError } from "rxjs";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { baseAPIURL, baseURL, httpOptions } from "../compartido/baseurl";
import { ProcesaHTTPMsjService } from "./procesa-httpmsj.service";

@Injectable({
  providedIn: "root",
})
export class ReservaService {
  constructor(
    private http: HttpClient,
    private procesaHttpmsjService: ProcesaHTTPMsjService
  ) {}

  enviarReserva(consulta: Consulta): Observable<Consulta> {
    return this.http
      .post<Consulta>(baseAPIURL + "reservas/", consulta, httpOptions)
      .pipe(catchError(this.procesaHttpmsjService.gestionError));
  }
}
