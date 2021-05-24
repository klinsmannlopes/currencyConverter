package com.br.currencyConverter.apis.exchangerate;

import com.br.currencyConverter.apis.UnirestService;
import com.br.currencyConverter.dtos.outputs.RateDTO;
import com.br.currencyConverter.exceptions.BusinessRuleException;
import lombok.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ExchangeRatesApi extends UnirestService {

    //@Value("${app.somapay.backoffice_url}")
    private String BASE_URL = "http://api.exchangeratesapi.io";

    private final String GET_RATE = "/latest";

    public RateDTO getListRates(String accessKey, String symbols) throws BusinessRuleException {
        Map<String, Object> queryParams = new HashMap<String, Object>();

        queryParams.put("access_key", accessKey);
        //queryParams.put("symbols", symbols);

        return get(GET_RATE, null, null, queryParams, RateDTO.class, null);
    }

    @Override
    public <T> T get(String uri, Map<String, String> headers, Map<String, Object> routeParams,
                     Map<String, Object> queryParams, Class<T> responseBodyClass, T responseError) throws BusinessRuleException {
        return super.get(BASE_URL.concat(uri), headers, routeParams, queryParams, responseBodyClass, responseError);
    }
}
