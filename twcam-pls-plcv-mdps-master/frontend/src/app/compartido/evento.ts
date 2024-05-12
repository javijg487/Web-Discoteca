export class Evento {
  id?: number;
  pista?: number;
  nombre: string;
  dj: {
    nombre: string;
  };
  fecha: string;
  tematica: string;
  imagen?: string;
  tieneSalasDisponibles?: boolean;

  constructor() {
    this.id = -1;
    this.nombre = "";
    this.fecha = "";
    this.dj = { nombre: "" };
    this.tematica = "";
    this.imagen = "";
  }
}
