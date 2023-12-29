import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { ProcesaHTTPMsjService } from "./procesa-httpmsj.service";
import { baseURL, baseAPIURL } from "../compartido/baseurl";
import { Observable } from "rxjs";
import { Cancion } from '../compartido/cancion';
import {catchError } from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class PedirCancionService {

  constructor(private http: HttpClient,
    private procesaHttpmsjService: ProcesaHTTPMsjService) { }

    
    getCanciones(): Observable<Cancion[]> {
      const headers = new HttpHeaders().set('Action', 'Todas');
      const options = { headers: headers };
      return this.http
        .get<Cancion[]>(baseAPIURL + "canciones", options)
        .pipe(catchError(this.procesaHttpmsjService.gestionError));
    }

    getCancionesDuracion(tiempo: string): Observable<Cancion[]> {
      const headers = new HttpHeaders().set('Action', 'Duracion');
      const options = { headers: headers };
      return this.http
        .get<Cancion[]>(baseAPIURL + "canciones?duracion="+ tiempo, options)
        .pipe(catchError(this.procesaHttpmsjService.gestionError));
    }

    getCancionesTematica(tematica: string): Observable<Cancion[]> {
      const headers = new HttpHeaders().set('Action', 'Tematica');
      const options = { headers: headers };
      return this.http
        .get<Cancion[]>(baseAPIURL + "canciones?tematica="+ tematica, options)
        .pipe(catchError(this.procesaHttpmsjService.gestionError));
    }

    getCancionesAutor(autor: string): Observable<Cancion[]> {
      const headers = new HttpHeaders().set('Action', 'Autor');
      const options = { headers: headers };
      return this.http
        .get<Cancion[]>(baseAPIURL + "canciones?autor="+ autor, options)
        .pipe(catchError(this.procesaHttpmsjService.gestionError));
    }

    deleteCancion(id: number): Observable<Cancion[]> {
      return this.http
        .delete<Cancion[]>(baseAPIURL + "canciones?eliminar="+ id)
        .pipe(catchError(this.procesaHttpmsjService.gestionError));
       
    }

}


