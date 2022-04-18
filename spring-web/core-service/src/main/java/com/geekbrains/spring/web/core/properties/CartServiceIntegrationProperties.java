package com.geekbrains.spring.web.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "integrations.cart-service")
@Data
public class CartServiceIntegrationProperties {
    private String url;
    private Timeouts timeouts = new Timeouts();

    @Data
    public static class Timeouts{
        private Integer connect;
        private Integer read;
        private Integer write;
    }
}
