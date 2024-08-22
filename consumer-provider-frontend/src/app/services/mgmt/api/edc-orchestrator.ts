/* tslint:disable */
/* eslint-disable */
// Generated using typescript-generator version 3.2.1263 on 2024-08-22 13:25:15.

export interface IAssetRequest {
    id: number;
    assetName: string;
}

export interface IConsumeOfferRequest {
    counterPartyAddress: string;
}

export interface IExceptionTO {
    httpStatusCode: number;
    message: string;
}

export interface HttpClient {

    request<R>(requestConfig: { method: string; url: string; queryParams?: any; data?: any; copyFn?: (data: R) => R; }): RestResponse<R>;
}

export class RestApplicationClient {

    constructor(protected httpClient: HttpClient) {
    }

    /**
     * HTTP POST /consumer/acceptContractOffer
     * Java method: eu.possible_x.edc_orchestrator.controller.ConsumerController.acceptContractOffer
     */
    acceptContractOffer(request: IConsumeOfferRequest): RestResponse<any> {
        return this.httpClient.request({ method: "POST", url: uriEncoding`consumer/acceptContractOffer`, data: request });
    }

    /**
     * HTTP POST /provider/asset
     * Java method: eu.possible_x.edc_orchestrator.controller.ProviderController.createAsset
     */
    createAsset(assetRequest: IAssetRequest): RestResponse<string> {
        return this.httpClient.request({ method: "POST", url: uriEncoding`provider/asset`, data: assetRequest });
    }

    /**
     * HTTP POST /provider/offer
     * Java method: eu.possible_x.edc_orchestrator.controller.ProviderController.createOffer
     */
    createOffer(): RestResponse<any> {
        return this.httpClient.request({ method: "POST", url: uriEncoding`provider/offer` });
    }
}

export type RestResponse<R> = Promise<R>;

function uriEncoding(template: TemplateStringsArray, ...substitutions: any[]): string {
    let result = "";
    for (let i = 0; i < substitutions.length; i++) {
        result += template[i];
        result += encodeURIComponent(substitutions[i]);
    }
    result += template[template.length - 1];
    return result;
}
