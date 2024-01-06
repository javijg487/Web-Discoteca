import { Comentario } from "./comentario";

export class Reserva {
  id?: number;
  eventoId: number;
  usuario: string;
  estado?: string;
  invitados: Array<{nombre: string; dni: string}>;
  esIndividual: boolean;

  constructor() {
    this.id = -1;
    this.eventoId = -1;
    this.usuario = "";
    this.estado = "";
    this.invitados = [];
    this.esIndividual = false;
  }
}
