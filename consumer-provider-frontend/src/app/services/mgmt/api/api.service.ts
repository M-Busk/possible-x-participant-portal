import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { ConsumeOfferRequest } from '../model/data';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private baseUrl = environment.api_url;

  constructor(private http: HttpClient) {}

  getHealth(): Observable<any> {
    return this.http.get(`${this.baseUrl}/health`);
  }

  createOffer(): Observable<any> {
    return this.http.post(`${this.baseUrl}/provider/offer`, null);
  }

  acceptContractOffer(): Observable<any> {
    let request: ConsumeOfferRequest = {
      counterPartyAddress: environment.counter_party_address
    }

    return this.http.post(`${this.baseUrl}/consumer/acceptContractOffer`, request);
  }

}
