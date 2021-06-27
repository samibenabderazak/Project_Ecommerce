import { TestBed } from '@angular/core/testing';

import { OderService } from './oder.service';

describe('OderService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: OderService = TestBed.get(OderService);
    expect(service).toBeTruthy();
  });
});
