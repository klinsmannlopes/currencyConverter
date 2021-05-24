package com.br.currencyConverter.apis;

import com.br.currencyConverter.errors.ApiErrorDTO;
import com.br.currencyConverter.errors.ErrorDTO;
import com.br.currencyConverter.exceptions.BusinessRuleException;
import kong.unirest.*;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class UnirestService {

    private final static Logger logger = Logger.getLogger(UnirestService.class.getName());

    static {
        Unirest.config().getInterceptor().add(new LogRequestInterceptor());
        Unirest.config().setObjectMapper(new JacksonObjectMapper());
    }

    public <T> T post(String url, Map<String, String> headers, Map<String, Object> routeParams, Map<String, Object> queryParams, Object requestBody, Class<T> responseBodyClass, T responseError) throws BusinessRuleException {
        RequestBodyEntity request = Unirest
                .post(url)
                .headers(getHeaders(headers))
                .body(requestBody);

        if(routeParams != null && !routeParams.isEmpty())
            request = request.routeParam(routeParams);

        if(queryParams != null && !queryParams.isEmpty())
            request = request.queryString(queryParams);

        return performRequest(request, url, responseBodyClass, responseError);
    }

    public <T> Map<String, Object> postAndGetResponseHeaders(String url, Map<String, String> headers,
                                                             Map<String, Object> routeParams, Map<String, Object> queryParams, Object requestBody,
                                                             Class<T> responseBodyClass, T responseError) throws BusinessRuleException {
        Map<String, Object> result = new HashMap<String, Object>();

        RequestBodyEntity request = Unirest
                .post(url)
                .headers(getHeaders(headers))
                .body(requestBody);

        if(routeParams != null && !routeParams.isEmpty())
            request = request.routeParam(routeParams);

        if(queryParams != null && !queryParams.isEmpty())
            request = request.queryString(queryParams);

        HttpResponse<?> response = request.asObject(responseBodyClass);

        Map<String, String> responseHeaders = new HashMap<String, String>();
        response.getHeaders().all().forEach(header -> responseHeaders.put(header.getName(), header.getValue()));

        result.put("headers", responseHeaders);
        result.put("body", performRequest(request, url, responseBodyClass, responseError));

        return result;
    }

    public <T> T get(String url, Map<String, String> headers, Map<String, Object> routeParams, Map<String, Object> queryParams, Class<T> responseBodyClass, T responseError) throws BusinessRuleException {
        GetRequest request = Unirest
                .get(url)
                .headers(getHeaders(headers));

        if(routeParams != null && !routeParams.isEmpty())
            request = request.routeParam(routeParams);

        if(queryParams != null && !queryParams.isEmpty())
            request = request.queryString(queryParams);

        return performRequest(request, url, responseBodyClass, responseError);
    }

    public <T> byte[] getBytes(String url, Map<String, String> headers, Map<String, Object> routeParams, Map<String, Object> queryParams, T responseError) throws BusinessRuleException {
        GetRequest request = Unirest
                .get(url)
                .headers(getHeaders(headers));

        if(routeParams != null && !routeParams.isEmpty())
            request = request.routeParam(routeParams);

        if(queryParams != null && !queryParams.isEmpty())
            request = request.queryString(queryParams);

        HttpResponse<byte[]> response = request.asBytes();

        if(!response.isSuccess())
            return null;

        return response.getBody();
    }

    public <T> T put(String url, Map<String, String> headers, Map<String, Object> routeParams, Object requestBody, Class<T> responseBodyClass, T responseError) throws BusinessRuleException {
        RequestBodyEntity request = Unirest
                .put(url)
                .headers(getHeaders(headers))
                .body(requestBody);

        if(routeParams != null && !routeParams.isEmpty())
            request = request.routeParam(routeParams);

        return performRequest(request, url, responseBodyClass, responseError);
    }

    protected Map<String, String> getHeaders(Map<String, String> headers) {
        if(headers == null)
            headers = new HashMap<String, String>();

        headers.put("Content-Type", "application/json");

        return headers;
    }

    private <T> T performRequest(HttpRequest<?> request, String url, Class<T> responseBodyClass, T responseError) throws BusinessRuleException {
        HttpResponse<?> response = null;

        final ApiErrorDTO apiErrorDTO = new ApiErrorDTO();
        if(responseBodyClass == null || responseBodyClass.equals(Void.class))
            response = request.asEmpty();
        else {
            response = request.asObject(responseBodyClass).ifFailure(ApiErrorDTO.class, r -> {
                UnirestParsingException ex = r.getParsingError().orElse(null);

                if(ex != null)
                    apiErrorDTO.setErrors(Arrays.asList(new ErrorDTO(99, ex.getOriginalBody(), "Unexpected error. Url: " + url)));
                else if(r.getBody() != null)
                    apiErrorDTO.setErrors(r.getBody().getErrors());
                else
                    apiErrorDTO.setErrors(Arrays.asList(new ErrorDTO(r.getStatus(), r.getStatusText(), "Unexpected error. Url: " + url)));
            });

            if(apiErrorDTO.getErrors() != null && !apiErrorDTO.getErrors().isEmpty())
                throw new BusinessRuleException(apiErrorDTO.getErrors().get(0));
        }

        return checkResponse(response, url, responseBodyClass, responseError);
    }

    private <T> T checkResponse(HttpResponse<?> response, String url, Class<T> responseBodyClass, T responseError) {
        if(response.isSuccess()) {
            logger.log(Level.INFO, url.concat(" returned a valid response"));
            return responseBodyClass == null ? null : responseBodyClass.cast(response.getBody());
        } else if(response.getStatus() == HttpStatus.SC_UNAUTHORIZED)
            logger.log(Level.INFO, url.concat(" returned unauthorized"));
        else
            logger.log(Level.INFO, url.concat(" returned status ".concat(String.valueOf(response.getStatus()))));

        response.getParsingError().ifPresent(e -> {
            logger.log(Level.INFO, "Parsing Exception: ", e);
            logger.log(Level.INFO, "Original body: " + e.getOriginalBody());
        });

        return responseError;
    }

}

