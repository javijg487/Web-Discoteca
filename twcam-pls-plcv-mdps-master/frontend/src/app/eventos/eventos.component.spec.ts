import { ComponentFixture, TestBed } from "@angular/core/testing";
import { RouterTestingModule } from "@angular/router/testing";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { baseURL } from "../compartido/baseurl";
import { Observable, of } from "rxjs";
import { MatListModule } from "@angular/material/list";
import { MatGridListModule } from "@angular/material/grid-list";
import { MatCardModule } from "@angular/material/card";
import { MatButtonModule } from "@angular/material/button";
import { MatDialogModule } from "@angular/material/dialog";
import { MatSliderModule } from "@angular/material/slider";
import { MatProgressSpinnerModule } from "@angular/material/progress-spinner";
import { MatFormFieldModule, MatFormField } from "@angular/material/form-field";
import { MatInputModule, MatInput } from "@angular/material/input";
import { MatCheckboxModule } from "@angular/material/checkbox";
import { FormsModule } from "@angular/forms";
import { MatSelectModule } from "@angular/material/select";
import { MatSlideToggleModule } from "@angular/material/slide-toggle";
import { ReactiveFormsModule } from "@angular/forms";
import { EventosComponent } from "./eventos.component";
import { Evento } from "../compartido/evento";
import { EVENTOS } from "../compartido/eventos";
import { EventoService } from "../services/evento.service";
import { HttpClientModule } from "@angular/common/http";
import { DebugElement } from "@angular/core";
import { By } from "@angular/platform-browser";

describe("EventosComponent", () => {
  let component: EventosComponent;
  let fixture: ComponentFixture<EventosComponent>;

  beforeEach(() => {
    let eventoServiceReplica = {
      getEventos: function (): Observable<Evento[]> {
        return of(EVENTOS);
      },
    };

    TestBed.configureTestingModule({
      imports: [
        BrowserAnimationsModule,
        MatListModule,
        MatGridListModule,
        MatCardModule,
        MatButtonModule,
        MatFormFieldModule,
        MatInputModule,
        MatCheckboxModule,
        FormsModule,
        MatSelectModule,
        MatSlideToggleModule,
        ReactiveFormsModule,
        MatSliderModule,
        MatProgressSpinnerModule,
        MatDialogModule,
        MatSliderModule,
        MatProgressSpinnerModule,
        HttpClientModule,
        RouterTestingModule.withRoutes([
          { path: "eventos", component: EventosComponent },
        ]),
      ],
      declarations: [EventosComponent, EventosComponent],
      providers: [
        { provide: EventoService, useValue: eventoServiceReplica },
        { provide: "baseURL", useValue: baseURL },
      ],
    });

    TestBed.configureTestingModule({
      declarations: [EventosComponent],
    });
    fixture = TestBed.createComponent(EventosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });

  it("eventos deben ser 3", () => {
    expect(component.vEventos.length).toBe(3);
    expect(component.vEventos[1].nombre).toBe("Evento 2");
    expect(component.vEventos[3]).toBeFalsy();
  });

  it('vProductos contiene un nombre de producto', () => {
    fixture.detectChanges();
    let de: DebugElement;
    let el: HTMLElement;
    de = fixture.debugElement.query(By.css('h1.mat-line'));
    el = de.nativeElement;
    expect(el.textContent).toContain(EVENTOS[0].nombre.toUpperCase());
  });
});
