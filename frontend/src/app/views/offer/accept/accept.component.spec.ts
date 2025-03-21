/*
 *  Copyright 2024-2025 Dataport. All rights reserved. Developed as part of the POSSIBLE project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

import { AcceptComponent } from './accept.component';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ApiService } from '../../../services/mgmt/api/api.service';
import {
  IAcceptOfferResponseTO,
  INegotiationState,
  IOfferDetailsTO
} from '../../../services/mgmt/api/backend';
import { BadgeComponent , AccordionComponent, AccordionItemComponent } from '@coreui/angular';
import { CommonViewsModule } from '../../common-views/common-views.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {Component, Input} from "@angular/core";
import {OfferPrintViewComponent} from "../offer-print-view/offer-print-view.component";
import {NameMappingService} from "../../../services/mgmt/name-mapping.service";

@Component({
  selector: 'app-offer-print-view',
  template: ''
})
class MockPrintView implements Partial<OfferPrintViewComponent>{
  @Input() offer?: IOfferDetailsTO;
}

describe('AcceptOfferComponent', () => {
  let component: AcceptComponent;
  let fixture: ComponentFixture<AcceptComponent>;
  let apiService: jasmine.SpyObj<ApiService>;
  let nameMappingService: jasmine.SpyObj<NameMappingService>;

  beforeEach(async () => {
    const apiServiceSpy = jasmine.createSpyObj('ApiService', ['acceptContractOffer']);
    const nameMappingServiceSpy = jasmine.createSpyObj('NameMappingService', ['getNameById']);

    await TestBed.configureTestingModule({
      declarations: [AcceptComponent, MockPrintView],
      providers: [
        { provide: ApiService, useValue: apiServiceSpy },
        { provide: NameMappingService, useValue: nameMappingServiceSpy }
      ],
      imports: [ BadgeComponent, AccordionComponent, AccordionItemComponent, CommonViewsModule, BrowserAnimationsModule ]
    }).compileComponents();

    fixture = TestBed.createComponent(AcceptComponent);
    component = fixture.componentInstance;
    apiService = TestBed.inject(ApiService) as jasmine.SpyObj<ApiService>;
    nameMappingService = TestBed.inject(NameMappingService) as jasmine.SpyObj<NameMappingService>;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call apiService on acceptContractOffer', () => {
    const mockResponse = Promise.resolve({
      negotiationState: "ACCEPTED" as INegotiationState,
      contractAgreementId: "contractAgreementId",
      dataOffering: false
    } as IAcceptOfferResponseTO);
    apiService.acceptContractOffer.and.returnValue(mockResponse);

    component.acceptContractOffer();

    expect(apiService.acceptContractOffer).toHaveBeenCalled();
  });

  it('should emit event on Cancel button', () => {
    const spy = spyOn(component.dismiss, 'emit');

    component.cancel();

    expect(spy).toHaveBeenCalled();
  });

  it('should return name and ID string', () => {
    const id = '123';
    const name = 'Test Name';
    nameMappingService.getNameById.and.returnValue(name);

    const result = component.getNameIdStringById(id);
    expect(result).toBe(`${name} (${id})`);
  });
});
