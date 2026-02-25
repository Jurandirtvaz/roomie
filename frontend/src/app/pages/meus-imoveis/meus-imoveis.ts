import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PropertyService } from '../../services/propertyService';
import { Property } from '../../models/property';

@Component({
  selector: 'app-meus-imoveis',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './meus-imoveis.html',
  styleUrl: './meus-imoveis.css',
})
export class MeusImoveis implements OnInit {

  properties: Property[] = [];

  constructor(private propertyService: PropertyService) {}

  ngOnInit(): void {
    this.loadProperties();
  }

  loadProperties() {
    this.propertyService.getMyProperties().subscribe({
      next: (data: Property[]) => {
        this.properties = data;
      },
      error: (err: any) => {
        console.error('Erro ao buscar imóveis', err);
      }
    });
  }

  publish(id: number) {
    this.propertyService.publishProperty(id).subscribe({
      next: () => {
        alert('Imóvel publicado com sucesso!');
        this.loadProperties();
      },
      error: (err: any) => {
        console.error('Erro ao publicar', err);
      }
    });
  }
}
