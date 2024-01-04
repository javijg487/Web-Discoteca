import { Component, OnInit, Inject } from '@angular/core';
import { Cancion } from '../compartido/cancion';
import { PedirCancionService } from '../services/pedir-cancion.service';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { getUserData } from "../utils/getUserData";

@Component({
  selector: 'app-pedir-cancion',
  templateUrl: './pedir-cancion.component.html',
  styleUrls: ['./pedir-cancion.component.scss']
})
export class PedirCancionComponent implements OnInit {
  opinionForm!: FormGroup;
  vCanciones: Cancion[] = [];
  vCancionesPendientes: Cancion[] = [];
  vCancionesReproducidas: Cancion[] = [];


  cancionSeleccionada: Cancion | null = null;
  cancionReproducida: string = "Ninguna canción se esta repoduciendo";
  errorMensaje: string = "";
  Idevento : number = -1;
  userRole: string = getUserData().rol;

  duracion: string = "";
  autor: string = "";
  tematica: string = "";

  ActivarDuracion: boolean = false;
  ActivarTematica: boolean = false;
  ActivarAutor: boolean = false;
  mostrarMensaje: boolean =true;

 
  filtroTematica: boolean = false;
  filtroTematica2: boolean = false;
  filtroTematica3: boolean = false;
  filtroTematica4: boolean = false;


  constructor(private pedirCancionService: PedirCancionService, private route: ActivatedRoute,
    @Inject('baseURL') public BaseURL: string, private fb: FormBuilder) {
    this.opinionForm = this.fb.group({ duracion: ['1'], autor: [''] });
  }


  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      this.Idevento = params["eventoId"];
    });
    this.getTodasCanciones();
    this.getPendientes();
    this.getReproducidas();
    
  }

  getTodasCanciones():void{
    this.pedirCancionService.getCanciones(this.Idevento).subscribe(canciones => this.vCanciones = canciones);
  }

  getDuracion(): void {
    this.duracion = this.opinionForm.value.duracion + ":00";
    this.pedirCancionService.getCancionesDuracion(this.duracion,this.Idevento).subscribe(canciones => this.vCanciones = canciones);
  }

  getAutor(): void {
    this.pedirCancionService.getCancionesAutor(this.opinionForm.value.autor,this.Idevento).subscribe(canciones => this.vCanciones = canciones);
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
    this.pedirCancionService.getCancionesTematica(this.tematica,this.Idevento).subscribe(canciones => this.vCanciones = canciones);
  }

  getPendientes():void{
    this.pedirCancionService.getCancionesPendientes(this.Idevento).subscribe(canciones => this.vCancionesPendientes = canciones);
  }

  getReproducidas():void{
    this.pedirCancionService.getCancionesReproducidas(this.Idevento).subscribe(canciones => this.vCancionesReproducidas = canciones);
  }

  pasarCancionPendiente(cancion:Cancion):void{
    this.pedirCancionService.editarEstadoCancion(cancion.id,this.Idevento).subscribe(
      errorMensaje => this.errorMensaje = <any>errorMensaje);
      setTimeout(() => {
        this.getPendientes();
      }, 100);
  }
  pasarCancionReproduciendo(cancion:Cancion):void{
    this.pedirCancionService.pasarCancionReproducir(cancion.id,this.Idevento).subscribe(
      errorMensaje => this.errorMensaje = <any>errorMensaje);
      setTimeout(() => {
        this.getReproducidas();
      }, 100);
  }

  deleteCanciones(cancion: Cancion): void {
    this.pedirCancionService.deleteCancion(cancion.id,this.Idevento).subscribe(
      errorMensaje => this.errorMensaje = <any>errorMensaje);
      setTimeout(() => {
        this.getTodasCanciones();
        this.getPendientes();
      }, 100);
  }
  deleteCancionesReproducidas(cancion: Cancion): void {
    this.pedirCancionService.deleteCancionReproducida(cancion.id,this.Idevento).subscribe(
      errorMensaje => this.errorMensaje = <any>errorMensaje);
      setTimeout(() => {
        this.getReproducidas();
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

    this.pasarCancionReproduciendo(this.cancionSeleccionada!);

    if(this.vCancionesReproducidas.length>=1){
      this.deleteCancionesReproducidas(this.vCancionesReproducidas[0]);
      //Para indicar que seguro que no es nulo (!)
    }
    this.deleteCanciones(this.cancionSeleccionada!);
    this.cancionSeleccionada= null;
  }

  pedirCancion(){
    this.pasarCancionPendiente(this.cancionSeleccionada!);
    this.cancionSeleccionada= null;
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