package com.districtcore.coresentinel.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "admin")
@Getter
@Setter
public class AdminConfig {
    private String username;
    private String password;
    private String email;
}
