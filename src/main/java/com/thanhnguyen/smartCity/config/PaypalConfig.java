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

    private static final String clientId ="Aavwnj54toFLt6wBoB3baDDN6DE1Qy_gOJlrMEfZ0eJREpLxXiCVds20rcHRo5r-EtZP94-j2ODUvHwW";
    private static final String clientSecret="ELdMXZ7JQ1FeZyAWa4Dk68BCS2kFj1ONAylzVSoUgB8qSfa_h0VpjDxNrbtpfum4HeG6YKsCxoBt_uWV";
    private String mode = "sandbox";

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