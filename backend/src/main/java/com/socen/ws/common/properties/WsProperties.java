package com.socen.ws.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Component
@Configuration
@ConfigurationProperties(prefix = "ws")
public class WsProperties {

    private ShiroProperties shiro = new ShiroProperties();

    private boolean openAopLog = true;

    private SwaggerProperties swagger = new SwaggerProperties();
}
