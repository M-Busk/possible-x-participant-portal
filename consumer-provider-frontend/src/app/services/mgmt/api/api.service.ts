import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';

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
    return this.http.post(`${this.baseUrl}/consumer/acceptContractOffer`, null);
  }

}
