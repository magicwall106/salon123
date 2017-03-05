package com.salon123.config.apidoc;

import com.salon123.config.Constants;
import com.salon123.config.Salon123Properties;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Springfox Swagger configuration.
 *
 * Warning! When having a lot of REST endpoints, Springfox can become a performance issue. In that
 * case, you can use a specific Spring profile for this class, so that only front-end developers
 * have access to the Swagger view.
 */
@Configuration
@EnableSwagger2
@Profile(Constants.SPRING_PROFILE_SWAGGER)
public class SwaggerConfiguration {

    private final Logger log = LoggerFactory.getLogger(SwaggerConfiguration.class);

    public static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";

    /**
     * Swagger Springfox configuration.
     *
     * @param salon123Properties the properties of the application
     * @return the Swagger Springfox configuration
     */
    @Bean
    public Docket swaggerSpringfoxDocket(Salon123Properties salon123Properties) {
        log.debug("Starting Swagger");
        StopWatch watch = new StopWatch();
        watch.start();
        Contact contact = new Contact(
            salon123Properties.getSwagger().getContactName(),
            salon123Properties.getSwagger().getContactUrl(),
            salon123Properties.getSwagger().getContactEmail());

        ApiInfo apiInfo = new ApiInfo(
            salon123Properties.getSwagger().getTitle(),
            salon123Properties.getSwagger().getDescription(),
            salon123Properties.getSwagger().getVersion(),
            salon123Properties.getSwagger().getTermsOfServiceUrl(),
            contact,
            salon123Properties.getSwagger().getLicense(),
            salon123Properties.getSwagger().getLicenseUrl());

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo)
            .forCodeGeneration(true)
            .genericModelSubstitutes(ResponseEntity.class)
            .ignoredParameterTypes(java.sql.Date.class)
            .directModelSubstitute(java.time.LocalDate.class, java.sql.Date.class)
            .directModelSubstitute(java.time.ZonedDateTime.class, Date.class)
            .directModelSubstitute(java.time.LocalDateTime.class, Date.class)
            .select()
            .paths(regex(DEFAULT_INCLUDE_PATTERN))
            .build();
        watch.stop();
        log.debug("Started Swagger in {} ms", watch.getTotalTimeMillis());
        return docket;
    }
}
