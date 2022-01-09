package com.getir.readingisgood.infrastructure.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import java.util.ArrayList;
import java.util.Collections;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

@ConditionalOnProperty(prefix = "springdoc.swagger-ui", name = "enabled", havingValue = "true")
@Configuration
public class OpenApi3Config {

  private final String appName;

  public OpenApi3Config(@Value("${spring.application.name}") String appName) {
    this.appName = appName;
  }

  @Bean
  public OpenAPI openAPI() {

    return new OpenAPI()
        .components(new Components()
            .addSecuritySchemes("spring_oauth",
                new SecurityScheme()
                    .name("Authorization")
                    .type(SecurityScheme.Type.APIKEY)
                    .in(SecurityScheme.In.HEADER)
                    .scheme("bearer")
                    .bearerFormat("JWT"))
            .addParameters("Accept-Language", new Parameter().in("header").schema(new StringSchema().example("tr")).name("Accept-Language").required(true))
        )
        .security(Collections.singletonList(
            new SecurityRequirement().addList("spring_oauth")
        ))
        .info(new Info()
            .title("READING-IS-GOOD API")
            .description(appName)
            .contact(new Contact()
                .name("Getir")
                .url("https://www.getir.com")
                .email("ilkertopuzz@gmail.com")));
  }


  @Bean
  public OpenApiCustomiser openApiCustomiser() {
    return openApi -> openApi.getPaths().values().stream()
        .flatMap(pathItem -> pathItem.readOperations().stream())
        .forEach(operation -> {
          var updatedList = new ArrayList<Parameter>();
          if (!CollectionUtils.isEmpty(operation.getParameters())) {
            updatedList.addAll(operation.getParameters());
          }
          updatedList.add(new HeaderParameter().$ref("#/components/parameters/Accept-Language"));
          operation.setParameters(updatedList);
        });
  }

}