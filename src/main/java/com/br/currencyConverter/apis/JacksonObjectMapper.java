package com.br.currencyConverter.apis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import kong.unirest.ObjectMapper;

public class JacksonObjectMapper implements ObjectMapper {

    private com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();

    public JacksonObjectMapper() {
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new JavaTimeModule());
    }

    public String writeValue(Object value) {
        try {
            return mapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T readValue(String value, Class<T> valueType) {
        try {
            return mapper.readValue(value, valueType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}

