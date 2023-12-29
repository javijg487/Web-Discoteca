import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PedirCancionComponent } from './pedir-cancion.component';

describe('PedirCancionComponent', () => {
  let component: PedirCancionComponent;
  let fixture: ComponentFixture<PedirCancionComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PedirCancionComponent]
    });
    fixture = TestBed.createComponent(PedirCancionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
