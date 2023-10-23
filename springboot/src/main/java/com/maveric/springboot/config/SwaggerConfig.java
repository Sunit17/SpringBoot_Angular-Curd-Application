package com.maveric.springboot.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(){

        return  new OpenAPI()
                .info(new Info().title("Employee Management")
                        .description("This is Employee Curd App")
                        .version("V0.0.1")
                        .contact(new Contact().name("Sunit"))
                )
                .externalDocs(new ExternalDocumentation());

    }



}
