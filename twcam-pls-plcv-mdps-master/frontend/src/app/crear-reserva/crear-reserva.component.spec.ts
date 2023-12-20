import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CrearReservaComponent } from './crear-reserva.component';

describe('CrearReservaComponent', () => {
  let component: CrearReservaComponent;
  let fixture: ComponentFixture<CrearReservaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CrearReservaComponent]
    });
    fixture = TestBed.createComponent(CrearReservaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
