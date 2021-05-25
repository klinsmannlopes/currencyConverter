package com.br.currencyConverter.apis;

import kong.unirest.*;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Service;
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

    public <T> T get(String url, Map<String, String> headers, Map<String, Object> routeParams, Map<String, Object> queryParams, Class<T> responseBodyClass, T responseError) {
        GetRequest request = Unirest
                .get(url)
                .headers(getHeaders(headers));

        if(routeParams != null && !routeParams.isEmpty())
            request = request.routeParam(routeParams);

        if(queryParams != null && !queryParams.isEmpty())
            request = request.queryString(queryParams);

        return performRequest(request, url, responseBodyClass, responseError);
    }

    protected Map<String, String> getHeaders(Map<String, String> headers) {
        if(headers == null)
            headers = new HashMap<String, String>();

        headers.put("Content-Type", "application/json");

        return headers;
    }

    private <T> T performRequest(HttpRequest<?> request, String url, Class<T> responseBodyClass, T responseError) {
        HttpResponse<?> response = null;

        if(responseBodyClass == null || responseBodyClass.equals(Void.class))
            response = request.asEmpty();
        else {
            response = request.asObject(responseBodyClass);
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

