import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";

import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";

import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { MatToolbarModule } from "@angular/material/toolbar";
import { FlexLayoutModule } from "@angular/flex-layout";

import "hammerjs";
import { MatListModule } from "@angular/material/list";

import { MatGridListModule } from "@angular/material/grid-list";
import { MatCardModule } from "@angular/material/card";
import { MatButtonModule } from "@angular/material/button";
import { MatDialogModule } from "@angular/material/dialog";

import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { MatCheckboxModule } from "@angular/material/checkbox";
import { FormsModule } from "@angular/forms";

import { MatSelectModule } from "@angular/material/select";
import { MatSlideToggleModule } from "@angular/material/slide-toggle";
import { MatRadioModule } from "@angular/material/radio";
import { ReactiveFormsModule } from "@angular/forms";
import { MatSliderModule } from "@angular/material/slider";
import { MatProgressSpinnerModule } from "@angular/material/progress-spinner";

import { FontAwesomeModule } from "@fortawesome/angular-fontawesome";

import { HttpClientModule } from "@angular/common/http";

import { ProcesaHTTPMsjService } from "./services/procesa-httpmsj.service";

import { CabeceraComponent } from "./cabecera/cabecera.component";
import { PieComponent } from "./pie/pie.component";
import { InicioComponent } from "./inicio/inicio.component";
import { LoginComponent } from "./login/login.component";
import { baseURL } from "./compartido/baseurl";
import { CrearReservaComponent } from "./crear-reserva/crear-reserva.component";
import { ListReservasComponent } from "./list-reservas/list-reservas.component";
import { MatTableModule } from "@angular/material/table";
import { PagosComponent } from './pagos/pagos.component';
import { PedirCancionComponent } from './pedir-cancion/pedir-cancion.component';
import { EventosComponent } from "./eventos/eventos.component";
import { CrearEventoComponent } from './crear-evento/crear-evento.component';

@NgModule({
  declarations: [
    AppComponent,
    CabeceraComponent,
    PieComponent,
    InicioComponent,
    LoginComponent,
    CrearReservaComponent,
    ListReservasComponent,
    PagosComponent,
    PedirCancionComponent,
    EventosComponent,
    CrearEventoComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    FlexLayoutModule,
    MatListModule,
    MatGridListModule,
    MatCardModule,
    MatButtonModule,
    FontAwesomeModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatCheckboxModule,
    FormsModule,
    MatSelectModule,
    MatSlideToggleModule,
    ReactiveFormsModule,
    MatSliderModule,
    MatProgressSpinnerModule,
    HttpClientModule,
    MatTableModule,
    MatRadioModule
  ],
  providers: [
    { provide: "baseURL", useValue: baseURL },
    ProcesaHTTPMsjService,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
