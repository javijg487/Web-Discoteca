import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-crear-evento',
  templateUrl: './crear-evento.component.html',
  styleUrls: ['./crear-evento.component.scss']
})
export class CrearEventoComponent implements OnInit{
  eventoForm!:FormGroup;

  ngOnInit() {
    this.eventoForm = new FormGroup({
      nombre: new FormControl('', Validators.required),
      dj: new FormControl('', Validators.required),
      fecha: new FormControl('', Validators.required),
      tematica: new FormControl('', Validators.required),
      imagen: new FormControl('', Validators.required)
    });
  }

  onSubmit() {
    console.log(this.eventoForm.value);
    // Aquí procesarías los datos del formulario
  }
}

