import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { baseAPIURL } from '../compartido/baseurl';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AutenticarService {
  
  usuario = { nombre: '', rol: ''};
  constructor(private http: HttpClient) { }

  login(nombre:string, password: string): Observable<any>{
    const credenciales ={ nombre, password};
    return this.http.post(baseAPIURL + 'login',credenciales);
  }

  guardardatos(userData: any):void{
    localStorage.setItem("userData", JSON.stringify(userData));
    
  }

  cerrarSesion(): Observable<any> {
    localStorage.removeItem("userData");
    this.usuario = { nombre: '', rol: ''};
    return of(this.usuario);
  }

  getLogin(): Observable<any> {
    let usuario = localStorage.getItem("userData");
    console.log("Usuario obtenido localStorage:", usuario);
    if (usuario != null) {
      this.usuario = JSON.parse(usuario);
      console.log("Usuario parseado:", this.usuario);
    }
    else {
      this.usuario = { nombre: '', rol: ''};
    }
    return of(this.usuario);
  }

}
