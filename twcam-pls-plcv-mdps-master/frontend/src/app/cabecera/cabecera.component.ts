import { Component, OnInit, Inject, HostListener } from "@angular/core";
import { getUserData } from "../utils/getUserData";

import {
  faHome,
  faInfo,
  faList,
  faAddressCard,
  faSignInAlt,
  faSignOutAlt,
  faCalendar,
  faPlus
} from "@fortawesome/free-solid-svg-icons";
import { MatDialog, MatDialogRef } from "@angular/material/dialog";
import { LoginComponent } from "../login/login.component";
import { AutenticarService } from "../services/autenticar.service";

@Component({
  selector: "app-cabecera",
  templateUrl: "./cabecera.component.html",
  styleUrls: ["./cabecera.component.scss"],
})
export class CabeceraComponent implements OnInit {
  faHome = faHome;
  faInfo = faInfo;
  faList = faList;
  faAddressCard = faAddressCard;
  faSignInAlt = faSignInAlt;
  faSignOutAlt = faSignOutAlt;
  faCalendar = faCalendar;
  faPlus = faPlus;
  userRole:String = getUserData().rol;

  login = { nombre: "", rol: "" };

  constructor(
    private autenticarService: AutenticarService,
    public dialogo: MatDialog,
    @Inject("baseURL") public BaseURL: string
  ) {
    this.autenticarService
      .getLogin()
      .subscribe((login) => (this.login = login));
    console.log("Login actualizado1", this.login);
  }

  ngOnInit(): void {}

  @HostListener("window:storage", ["$event"])
  procesar(event: StorageEvent) {
    this.autenticarService.getLogin().subscribe((login) => {
      this.login = login;
      console.log("Login actualizado:", this.login);
    });
    console.log("Se pasa el procesar");
  }

  cerrarSesion() {
    this.autenticarService
      .cerrarSesion()
      .subscribe((login) => (this.login = login));
    console.log("Login actualizado_cerrarSesion", this.login);
    return false;
  }
}
