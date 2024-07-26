package com.laft17s.mscompaccountmovement.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ManagementServiceConfig {
    @Value("${management.url.client.person.component}")
    private String clientPersonComponentUrl;

    @Value("${management.endpoint.find.client.dni}")
    private String findClientByDniEndpoint;

    public String getClientPersonComponentUrl() {
        return clientPersonComponentUrl;
    }

    public String getFindClientByDniEndpoint() {
        return findClientByDniEndpoint;
    }

    public String getFindClientByDniUrl() {
        return clientPersonComponentUrl + findClientByDniEndpoint;
    }
}
