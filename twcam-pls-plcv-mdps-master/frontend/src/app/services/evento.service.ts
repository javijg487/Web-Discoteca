import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Evento } from '../compartido/evento';
import { HttpClient } from "@angular/common/http";
import {baseAPIURL, httpOptions } from "../compartido/baseurl";
import { ProcesaHTTPMsjService } from "./procesa-httpmsj.service";
import {catchError, tap } from "rxjs/operators";


@Injectable({
  providedIn: 'root'
})
export class EventoService {

  constructor(private http: HttpClient,
    private procesaHttpmsjService: ProcesaHTTPMsjService) { }

  getEventos():Observable<Evento[]>{
    return this.http
        .get<Evento[]>(baseAPIURL + "eventos/")
        .pipe(catchError(this.procesaHttpmsjService.gestionError));
  }

  getEvento(id:number){
    return this.http
      .get<Evento>(baseAPIURL + "eventos/" + id)
      .pipe(catchError(this.procesaHttpmsjService.gestionError));
  }

  enviarEvento(consulta: Evento):Observable<Evento>{
    return this.http
      .post<Evento>(baseAPIURL + "eventos/", consulta, httpOptions)
      .pipe(catchError(this.procesaHttpmsjService.gestionError));
  }

  /*
  enviarEvento(evento: Evento): Observable<Evento> {
    return this.http.post<Evento>(baseAPIURL + "eventos/", evento, httpOptions)
      .pipe(
        tap((nuevoEvento: Evento) => console.log(`Evento añadido w/ id=${nuevoEvento.id}`)),
        catchError(this.procesaHttpmsjService.gestionError)
      );
  }*/
  
}
