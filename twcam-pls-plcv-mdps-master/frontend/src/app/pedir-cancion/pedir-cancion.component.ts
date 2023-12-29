import { Component, OnInit, Inject } from '@angular/core';
import { Cancion } from '../compartido/cancion';
import { PedirCancionService } from '../services/pedir-cancion.service';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-pedir-cancion',
  templateUrl: './pedir-cancion.component.html',
  styleUrls: ['./pedir-cancion.component.scss']
})
export class PedirCancionComponent implements OnInit {
  opinionForm!: FormGroup;
  vCanciones: Cancion[] = [];
  cancionSeleccionada: Cancion | null = null;
  cancionReproducida: string = "Ninguna canción se esta repoduciendo";
  errorMensaje: string = "";

  duracion: string = "";
  autor: string = "";
  tematica: string = "";

  ActivarDuracion: boolean = false;
  ActivarTematica: boolean = false;
  ActivarAutor: boolean = false;

  vCancionesfiltro: Cancion[] = [];

  filtroTematica: boolean = false;
  filtroTematica2: boolean = false;
  filtroTematica3: boolean = false;
  filtroTematica4: boolean = false;


  constructor(private pedirCancionService: PedirCancionService,
    @Inject('baseURL') public BaseURL: string, private fb: FormBuilder) {
    this.opinionForm = this.fb.group({ duracion: ['1'], autor: [''] });
  }


  ngOnInit(): void {
    this.getTodasCanciones();
  }

  getTodasCanciones():void{
    this.pedirCancionService.getCanciones().subscribe(canciones => this.vCanciones = canciones,
      errorMensaje => this.errorMensaje = <any>errorMensaje);
  }
  getDuracion(): void {
    this.duracion = this.opinionForm.value.duracion + ":00";
    this.pedirCancionService.getCancionesDuracion(this.duracion).subscribe(canciones => this.vCanciones = canciones,
      errorMensaje => this.errorMensaje = <any>errorMensaje);
  }

  getAutor(): void {
    this.pedirCancionService.getCancionesAutor(this.opinionForm.value.autor).subscribe(canciones => this.vCanciones = canciones,
      errorMensaje => this.errorMensaje = <any>errorMensaje);
  }

  getTematica(): void {
    if (this.filtroTematica) {
      this.tematica = "rock";
    } else if (this.filtroTematica2) {
      this.tematica = "electro";
    } else if (this.filtroTematica3) {
      this.tematica = "reggaeton";
    } else if (this.filtroTematica4) {
      this.tematica = "pop";
    }
    this.pedirCancionService.getCancionesTematica(this.tematica).subscribe(canciones => this.vCanciones = canciones,
      errorMensaje => this.errorMensaje = <any>errorMensaje);
  }

  deleteCanciones(cancion: Cancion): void {
    this.pedirCancionService.deleteCancion(cancion.id).subscribe(
      errorMensaje => this.errorMensaje = <any>errorMensaje);
      setTimeout(() => {
        this.getTodasCanciones();
      }, 100);
  }

  onSelectCancion(cancion: Cancion): void {
    this.cancionSeleccionada = cancion;
  }


  filtrarCancion(): void {
    if (this.ActivarAutor) {
      this.getAutor();
    } else if (this.ActivarDuracion) {
      this.getDuracion();
    } else if (this.ActivarTematica) {
      this.getTematica();
    }
    this.cancionSeleccionada = null;
  }

  reproducirCancion(): void {
    this.cancionReproducida = this.cancionSeleccionada?.nombre + " - " + this.cancionSeleccionada?.autor;
    this.deleteCanciones(this.cancionSeleccionada!); //Para indicar que seguro que no es nulo (!)
  }


  isDisabled(): boolean {
    return !(this.ActivarAutor || this.ActivarTematica || this.ActivarDuracion);
  }

  isDisabledReproducir(): boolean {
    return this.cancionSeleccionada == null;
  }
  quitarFiltro():void{
    this.ActivarDuracion= false;
    this.ActivarTematica= false;
    this.ActivarAutor = false;
    this.getTodasCanciones();
  }


}