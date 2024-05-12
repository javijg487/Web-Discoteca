import { Component, OnInit, Inject } from "@angular/core";
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { getUserData } from "../utils/getUserData";
import { DjService } from "../services/dj.service";
import { FormBuilder } from "@angular/forms";
import { EventoService } from "../services/evento.service";
import { Router } from "@angular/router";

@Component({
  selector: "app-crear-evento",
  templateUrl: "./crear-evento.component.html",
  styleUrls: ["./crear-evento.component.scss"],
})
export class CrearEventoComponent implements OnInit {
  eventForm!: FormGroup;
  vDjs: any[] = [];
  errorMensaje: string = "";
  eventoNombre!: String;
  eventoDJ!: String;
  eventoFecha!: String;
  eventoTematica!: String;

  constructor(
    private djService: DjService,
    @Inject("baseURL") public BaseURL: string,
    private fb: FormBuilder,
    private eventoService: EventoService,
    private router: Router
  ) {
    this.crearFormulario();
  }

  erroresForm: any = {
    'nombre': '',
    'fecha': '',
    'tematica':'',
    'dj':'',
  };

  mensajesError: any = {

    'nombre': {
      'required': 'El nombre es obligatorio.',

      'minlength': 'El nombre debe tener una longitud mínima de 2 caracteres.',

      'maxlength': 'El nombre no puede exceder de 25 caracteres.'
    },

    'fecha': {
      'required': 'La fecha es obligatoria.',
    },

    'tematica': {
      'required': 'La temática es obligatoria.',
    },

    'dj': {
      'required': 'El DJ es obligatorio.',
    },


  }

  ngOnInit() {
    this.djService.getDjs().subscribe((djs) => (this.vDjs = djs));
  }

  crearFormulario() {
    this.eventForm = this.fb.group({
      nombre: [
        "",
        [
          Validators.required,
          Validators.minLength(2),
          Validators.maxLength(25),
        ],
      ],
      dj: ["", [Validators.required]],
      fecha: ["", [Validators.required]],
      tematica: ["", [Validators.required]],
    });

    this.eventForm.valueChanges.subscribe(datos => this.onCambioValor(datos));
  }

  onSubmitEvento() {
    console.log(this.eventForm.status)

    if (this.eventForm.status == "VALID") {
      this.eventoService
        .enviarEvento({
          ...this.eventForm.value,
          dj: { nombre: this.eventForm.value.dj },
        })
        .subscribe(() =>
          this.router.navigate(['/eventos/']));
    }


    this.eventForm.reset({
      nombre: "",
      dj: "",
      fecha: "",
      tematica: "",
    });
  }

  onCambioValor(data?: any) {
    if (!this.eventForm) { return; }
    const form = this.eventForm;
    for (const field in this.erroresForm) {
      // Se borrarán los mensajes de error previos
      this.erroresForm[field] = '';
      const control = form.get(field);
      if (control && control.dirty && !control.valid) {
        const messages = this.mensajesError[field];
        for (const key in control.errors) {
          this.erroresForm[field] += messages[key] + ' ';
        }
      }
    }
  }
}
