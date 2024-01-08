import { Routes } from "@angular/router";

import { InicioComponent } from "./inicio/inicio.component";
import { LoginComponent } from "./login/login.component";
import { CrearReservaComponent } from "./crear-reserva/crear-reserva.component";
import { ListReservasComponent } from "./list-reservas/list-reservas.component";
import { PagosComponent } from "./pagos/pagos.component";
import { PedirCancionComponent } from "./pedir-cancion/pedir-cancion.component";
import { EventosComponent } from "./eventos/eventos.component";
import { CrearEventoComponent } from "./crear-evento/crear-evento.component";

export const rutas: Routes = [
  { path: "inicio", component: InicioComponent },
  { path: "login", component: LoginComponent },
  { path: "reservas", component: ListReservasComponent },
  { path: "crear-reserva/:eventoId", component: CrearReservaComponent },
  { path: "pagos/:reservaId", component: PagosComponent },
  {path: "canciones/:eventoId", component: PedirCancionComponent},
  {path: "eventos", component: EventosComponent},
  {path: "crear-evento", component: CrearEventoComponent},
  { path: "", redirectTo: "/inicio", pathMatch: "full" },
];
