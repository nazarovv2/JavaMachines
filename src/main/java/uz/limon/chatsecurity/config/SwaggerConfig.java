package uz.limon.chatsecurity.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@EnableSwagger2
@EnableWebMvc
@Configuration
public class SwaggerConfig extends WebMvcConfigurerAdapter {

    private final Logger log = LoggerFactory.getLogger(SwaggerConfig.class);

    public static final String AUTHORIZATION_HEADER = "Authorization";

    @Bean
    public Docket api() {
        log.debug("Starting Swagger");

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(
                        new ApiInfo(
                                "Chat with TCP -- Not UDP :) ",
                                "This project developed for examination purposes only",
                                "1.0.0",
                                "https://apelsin.uz",
                                new Contact("Abduvohid Ergashev", "doc.javadoc.uz", "abduvohid0131@gmail.com"),
                                "MIT",
                                "https://opensource.org/licenses/MIT",
                                new ArrayList<>())
                )
                .forCodeGeneration(true)
                .genericModelSubstitutes(ResponseEntity.class)
                .ignoredParameterTypes(Pageable.class)
                .securityContexts(securityContext())
                .securitySchemes(apiKey())
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    private List<ApiKey> apiKey() {
        return List.of(new ApiKey("JWT", AUTHORIZATION_HEADER, "header"));
    }

    private List<SecurityContext> securityContext() {
        return List.of(
                SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build());
    }

    List<SecurityReference> defaultAuth() {
        return List.of(
                new SecurityReference(
                        "JWT",
                        new AuthorizationScope[]{
                                new AuthorizationScope(
                                        "global",
                                        "access everything")}));
    }
}
