import { Component } from '@angular/core';
import { ApiService } from '../../../services/mgmt/api/api.service'

@Component({
  selector: 'app-provide',
  templateUrl: './provide.component.html',
  styleUrl: './provide.component.scss'
})
export class ProvideComponent {
  offerType: string = "";
  offerName: string = "";

  constructor(private apiService: ApiService) {}

  getHealth(): void {
    this.apiService.getHealth().subscribe(response => {
      console.log(response);
    });
  }

  createOffer(): void {
    this.apiService.createOffer().subscribe(response => {
      console.log(response);
    });
  }

  createCatalogEntry(): void {
    this.apiService.createCatalogEntry().subscribe(response => {
      console.log(response);
    });
  }
}
