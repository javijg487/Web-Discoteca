import { Component, OnInit } from "@angular/core";
import { MatDialog, MatDialogRef } from "@angular/material/dialog";
import { AutenticarService } from "../services/autenticar.service";
import { Router } from '@angular/router';

@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.scss"],
})
export class LoginComponent implements OnInit {
  nombre: string = "";
  password: string = "";
  login = {nombre: '', rol: ''};
  login_incorrecto =false;
  constructor(private autenticarService: AutenticarService, private router: Router) {}

  ngOnInit(): void {}

  onSubmit() {
    this.autenticarService.login(this.nombre, this.password).subscribe(
      (response) => {
        // Manejar la respuesta del servidor
        this.autenticarService.guardardatos(response);
        console.log("Inicio de sesión exitoso", response);
        window.location.href ='/';
        
      },
      (error) => {
        console.log("Credencias incorrectos", error);
        this.login_incorrecto=true;
      }
    );
  }
}
