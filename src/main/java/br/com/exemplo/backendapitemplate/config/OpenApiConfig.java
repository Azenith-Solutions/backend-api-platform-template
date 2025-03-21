package br.com.exemplo.backendapitemplate.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("Backend API")
                        .description("Documentação do Backend - API com versionamento")
                        .version("1.0"))
                .servers(List.of(
                        new Server().url(contextPath).description("API Server")
                ));
    }

    @Bean
    public GroupedOpenApi v1ApiGroup() {
        return GroupedOpenApi.builder()
                .group("v1")
                .pathsToMatch("/v1/**")
                .packagesToScan("br.com.exemplo.backendapitemplate.v1")
                .build();
    }
}