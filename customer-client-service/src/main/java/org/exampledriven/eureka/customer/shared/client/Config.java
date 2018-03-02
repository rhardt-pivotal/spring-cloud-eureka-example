package org.exampledriven.eureka.customer.shared.client;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;

/**
 * Created by Peter_Szanto on 6/2/2016.
 */
@Configuration
public class Config {

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() throws Exception {
        return new RestTemplate(clientHttpRequestFactory());
    }


    private ClientHttpRequestFactory clientHttpRequestFactory() throws Exception {
        return new HttpComponentsClientHttpRequestFactory(httpClient());
    }

    private HttpClient httpClient() throws Exception {
        // Load our keystore and truststore containing certificates that we trust.
        SSLContext sslcontext =
                SSLContexts.createSystemDefault();
        SSLConnectionSocketFactory sslConnectionSocketFactory =
                new SSLConnectionSocketFactory(sslcontext, new DefaultHostnameVerifier());
        return HttpClients.custom().setSSLSocketFactory(sslConnectionSocketFactory).build();
    }


}
