import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MeusImoveis } from './meus-imoveis';
import { PropertyService } from '../../services/propertyService';
import { of } from 'rxjs';

describe('MeusImoveis', () => {
  let component: MeusImoveis;
  let fixture: ComponentFixture<MeusImoveis>;

  const mockPropertyService = {
    getMyProperties: () => of([]),
    publishProperty: () => of(null)
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MeusImoveis],
      providers: [
        { provide: PropertyService, useValue: mockPropertyService }
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MeusImoveis);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
