import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { ProcesaHTTPMsjService } from "./procesa-httpmsj.service";
import {baseAPIURL, httpOptions } from "../compartido/baseurl";
import { Observable } from "rxjs";
import {catchError } from "rxjs/operators";


@Injectable({
  providedIn: 'root'
})
export class DjService {

  constructor(private http: HttpClient,
    private procesaHttpmsjService: ProcesaHTTPMsjService) { }
  
  getDjs():Observable<any[]>{
      return this.http
        .get<any[]>(baseAPIURL + "login?rol=DJ")
        .pipe(catchError(this.procesaHttpmsjService.gestionError));
  }
}
