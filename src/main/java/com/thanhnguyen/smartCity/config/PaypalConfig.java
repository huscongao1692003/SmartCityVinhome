package com.thanhnguyen.smartCity.config;

import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import lombok.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class PaypalConfig {

    private static final String clientId ="ATl99ivFMXJeuxdkYdUNyyTlUDdrMNL16fjgUrhSOs_eB9xeKIFqEYsXFWv2uAy5ziG0xecLimhpZewG";
    private static final String clientSecret="EOGdNED9dxsuOmh8Xzuiv-0CHPjBPOKdOXPfX9CqGdFTC-la3xI3bzLcwxA-Hbjv8iwIFUzjXGsGOA0W";
    private String mode = "live";

    @Bean
    public Map<String, String> paypalSdkConfig() {
        Map<String, String> configMap = new HashMap<>();
        configMap.put("mode", mode);
        return configMap;
    }

    @Bean
    public OAuthTokenCredential oAuthTokenCredential() {
        return new OAuthTokenCredential(clientId, clientSecret, paypalSdkConfig());
    }

    @Bean
    public APIContext apiContext() throws PayPalRESTException {
        APIContext context = new APIContext(oAuthTokenCredential().getAccessToken());
        context.setConfigurationMap(paypalSdkConfig());
        return context;
    }

}