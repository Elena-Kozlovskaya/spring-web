package com.geekbrains.spring.web.endpoints;

import com.geekbrains.spring.web.services.CategoriesService;
import com.geekbrains.spring.web.soap.categories.GetCategoryByTitleRequest;
import com.geekbrains.spring.web.soap.categories.GetCategoryByTitleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@RequiredArgsConstructor
public class CategoriesEndpoint {
    private static final String NAMESPACE_URI = "http://www.geekbrains.com/spring/web/categories";
    private final CategoriesService categoriesService;

    /*
        Пример запроса: POST http://localhost:8189/app/ws

        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
          xmlns:f="http://www.geekbrains.com/spring/web/categories">
            <soapenv:Header/>
            <soapenv:Body>
                <f:getCategoryByTitleRequest>
                    <f:title>ABC-123</f:title>
                </f:getCategoryByTitleRequest>
            </soapenv:Body>
        </soapenv:Envelope>
     */

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCategoryByTitleRequest")
    @ResponsePayload
    @Transactional
    public GetCategoryByTitleResponse getCategoryByTitle(@RequestPayload GetCategoryByTitleRequest request) {
        GetCategoryByTitleResponse response = new GetCategoryByTitleResponse();
        response.setCategory(categoriesService.getByTitle(request.getTitle()));
        return response;
    }
}
