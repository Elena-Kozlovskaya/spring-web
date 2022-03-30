package com.geekbrains.spring.web.recommendation.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "integrations.core-service")
@Data
public class OrderServiceIntegrationProperties {
    private String url;
    private Timeouts timeouts = new Timeouts();

    @Data
    public static class Timeouts{
        private Integer connect;
        private Integer read;
        private Integer write;
    }
}
