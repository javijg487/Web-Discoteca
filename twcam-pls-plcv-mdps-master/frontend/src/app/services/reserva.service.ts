import { Injectable } from "@angular/core";
import { Observable, catchError } from "rxjs";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { baseAPIURL, baseURL, httpOptions } from "../compartido/baseurl";
import { ProcesaHTTPMsjService } from "./procesa-httpmsj.service";
import { Reserva } from "../compartido/reserva";

@Injectable({
  providedIn: "root",
})
export class ReservaService {
  constructor(
    private http: HttpClient,
    private procesaHttpmsjService: ProcesaHTTPMsjService
  ) {}

  getReserva(id: number) {
    return this.http
      .get<Reserva>(baseAPIURL + "reservas/" + id)
      .pipe(catchError(this.procesaHttpmsjService.gestionError));
  }

  getReservas(usernameForGet: String | null): Observable<Reserva[]> {
    const usernameParam = usernameForGet ? `?username=${usernameForGet}` : "";
    return this.http
      .get<Reserva[]>(baseAPIURL + "reservas" + usernameParam)
      .pipe(catchError(this.procesaHttpmsjService.gestionError));
  }

  enviarReserva(consulta: Reserva): Observable<Reserva> {
    return this.http
      .post<Reserva>(baseAPIURL + "reservas", consulta, httpOptions)
      .pipe(catchError(this.procesaHttpmsjService.gestionError));
  }

  editarEstadoReserva(reserva: Reserva): Observable<Reserva> {
    return this.http
      .put<Reserva>(baseAPIURL + `reservas/${reserva.id}`, reserva, httpOptions)
      .pipe(catchError(this.procesaHttpmsjService.gestionError));
  }
}
