import { TestBed } from '@angular/core/testing';

import { PedirCancionService } from './pedir-cancion.service';

describe('PedirCancionService', () => {
  let service: PedirCancionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PedirCancionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
