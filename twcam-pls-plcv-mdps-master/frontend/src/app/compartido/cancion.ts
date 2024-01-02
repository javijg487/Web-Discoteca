
export class Cancion{
    id:number;
    nombre: string;
    autor: string;
    duracion: string;
    tematica: string;
    estado: string;

    constructor(){
        this.id = -1;
		this.nombre = "";
		this.autor = "";
        this.duracion = "";
        this.tematica = "";
        this.estado = "";
        
    }
}