package com.br.currencyConverter.apis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestWrapper;
import org.apache.http.protocol.HttpContext;

public class LogRequestInterceptor implements HttpRequestInterceptor {

    private final static Logger logger = Logger.getLogger(LogRequestInterceptor.class.getName());

    @Override
    public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
        if(request instanceof HttpRequestWrapper) {
            HttpRequestWrapper requestWrapper = (HttpRequestWrapper) request;

            if(requestWrapper.getOriginal() instanceof HttpPost) {
                HttpPost httpPost = (HttpPost)requestWrapper.getOriginal();
                String json = new BufferedReader(new InputStreamReader(httpPost.getEntity().getContent()))
                        .lines().collect(Collectors.joining("\n"));
                logger.log(Level.INFO, httpPost.toString() + ": " + json);
            } else if(requestWrapper.getOriginal() instanceof HttpGet) {
                HttpGet httpGet = (HttpGet)requestWrapper.getOriginal();
                logger.log(Level.INFO, httpGet.toString() + ": " + httpGet.getParams().toString());
            }
        }
    }

}

