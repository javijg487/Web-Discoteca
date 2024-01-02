import { Routes } from "@angular/router";

import { ProductosComponent } from "./productos/productos.component";
import { DetalleProductoComponent } from "./detalle-producto/detalle-producto.component";
import { InicioComponent } from "./inicio/inicio.component";
import { NosotrosComponent } from "./nosotros/nosotros.component";
import { ContactoComponent } from "./contacto/contacto.component";
import { LoginComponent } from "./login/login.component";
import { CrearReservaComponent } from "./crear-reserva/crear-reserva.component";
import { ListReservasComponent } from "./list-reservas/list-reservas.component";
import { PagosComponent } from "./pagos/pagos.component";
import { PedirCancionComponent } from "./pedir-cancion/pedir-cancion.component";

export const rutas: Routes = [
  { path: "inicio", component: InicioComponent },
  { path: "productos", component: ProductosComponent },
  { path: "contacto", component: ContactoComponent },
  { path: "nosotros", component: NosotrosComponent },
  { path: "login", component: LoginComponent },
  { path: "reservas", component: ListReservasComponent },
  { path: "crear-reserva/:eventoId", component: CrearReservaComponent },
  { path: "detalleProducto/:id", component: DetalleProductoComponent },
  { path: "pagos/:reservaId", component: PagosComponent },
  {path: "canciones/:eventoId", component: PedirCancionComponent},
  { path: "", redirectTo: "/inicio", pathMatch: "full" },
];
