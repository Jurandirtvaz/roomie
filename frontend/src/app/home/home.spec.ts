import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Home } from './home';
import { provideRouter } from '@angular/router';
import { Auth } from '../auth/auth';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { By } from '@angular/platform-browser';

describe('Home', () => {
  let component: Home;
  let fixture: ComponentFixture<Home>;

  let mockAuth = { logout: () => {} };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Home, HttpClientTestingModule, ReactiveFormsModule],
      providers: [
        provideRouter([]),
        { provide: Auth, useValue: mockAuth }
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Home);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create component Home', () => {
    expect(component).toBeTruthy();
  });

  it('should render the header with the "Anunciar Imóvel" button', () => {
    const buttons = fixture.debugElement.queryAll(By.css('.profile-btn'));
    const anunciarBtn = buttons.find(b => b.nativeElement.textContent.includes('Anunciar Imóvel'));
    expect(anunciarBtn).toBeTruthy();
  });

  it('should render the logout button', () => {
    const buttons = fixture.debugElement.queryAll(By.css('.profile-btn'));
    const sairBtn = buttons.find(b => b.nativeElement.textContent.includes('Sair'));
    expect(sairBtn).toBeTruthy();
  });
});