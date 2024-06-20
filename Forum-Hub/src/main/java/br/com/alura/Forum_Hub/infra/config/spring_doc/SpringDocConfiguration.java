package br.com.alura.Forum_Hub.infra.config.spring_doc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {
    private final String INFO_TITLE, INFO_DESCRIPTION, INFO_CONTACT_NAME, INFO_CONTACT_EMAIL;

    public SpringDocConfiguration(){
        this.INFO_TITLE = "Forum Hub API";
        this.INFO_DESCRIPTION = "Challenge didático da alura (Fórum Hub) para aprendizado do uso das APIs Rest com Java e SpringBoot3";
        this.INFO_CONTACT_NAME = "Thiago Barbosa";
        this.INFO_CONTACT_EMAIL = "curiosidades381@gmail.com";
    }

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .info(new Info()
                        .title(INFO_TITLE)
                        .description(INFO_DESCRIPTION)
                        .contact(new Contact()
                                .name(INFO_CONTACT_NAME)
                                .email(INFO_CONTACT_EMAIL)
                        )
                );
    }
}
