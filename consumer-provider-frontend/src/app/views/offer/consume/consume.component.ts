import { Component } from '@angular/core';
import { ApiService } from '../../../services/mgmt/api/api.service';

@Component({
  selector: 'app-consume',
  templateUrl: './consume.component.html',
  styleUrl: './consume.component.scss'
})
export class ConsumeComponent {

  constructor(private apiService: ApiService) {}

  acceptContractOffer(): void {
    console.log("'Accept Contract Offer' button pressed");
    this.apiService.acceptContractOffer().subscribe(reponse => { console.log(reponse) });
  }
}
