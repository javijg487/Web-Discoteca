import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { ProcesaHTTPMsjService } from "./procesa-httpmsj.service";
import {baseAPIURL, httpOptions } from "../compartido/baseurl";
import { Observable } from "rxjs";
import { Cancion } from '../compartido/cancion';
import {catchError } from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class PedirCancionService {

  constructor(private http: HttpClient,
    private procesaHttpmsjService: ProcesaHTTPMsjService) { }

    
    getCanciones(id: number): Observable<Cancion[]> {
      const headers = new HttpHeaders().set('Action', 'Todas');
      const options = { headers: headers };
      return this.http
        .get<Cancion[]>(baseAPIURL + "canciones/" + id, options)
        .pipe(catchError(this.procesaHttpmsjService.gestionError));
    }

    getCancionesDuracion(tiempo: string, id: number): Observable<Cancion[]> {
      const headers = new HttpHeaders().set('Action', 'Duracion');
      const options = { headers: headers };
      return this.http
        .get<Cancion[]>(baseAPIURL + "canciones/" + id + "?duracion="+ tiempo, options)
        .pipe(catchError(this.procesaHttpmsjService.gestionError));
    }

    getCancionesTematica(tematica: string, id: number): Observable<Cancion[]> {
      const headers = new HttpHeaders().set('Action', 'Tematica');
      const options = { headers: headers };
      return this.http
        .get<Cancion[]>(baseAPIURL + "canciones/" + id+ "?tematica="+ tematica, options)
        .pipe(catchError(this.procesaHttpmsjService.gestionError));
    }

    getCancionesAutor(autor: string, id: number): Observable<Cancion[]> {
      const headers = new HttpHeaders().set('Action', 'Autor');
      const options = { headers: headers };
      return this.http
        .get<Cancion[]>(baseAPIURL + "canciones/" +id+ "?autor="+ autor, options)
        .pipe(catchError(this.procesaHttpmsjService.gestionError));
    }

    editarEstadoCancion(id: number,idEvento: number): Observable<Cancion[]> {
      return this.http
        .put<Cancion[]>(baseAPIURL + "canciones/" + idEvento+ "?pendiente="+ id, httpOptions)
        .pipe(catchError(this.procesaHttpmsjService.gestionError));
    } 


    deleteCancion(id: number, idEvento: number): Observable<Cancion[]> {
      return this.http
        .delete<Cancion[]>(baseAPIURL + "canciones/" + idEvento+ "?eliminar="+ id)
        .pipe(catchError(this.procesaHttpmsjService.gestionError));
       
    }

}


