package com.example.demo.single.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonPropertyConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilder() {
        return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder
                .locale("es_CO")
                .modules(new Jdk8Module(), new JavaTimeModule(), new ParameterNamesModule())
                .featuresToEnable(DeserializationFeature.USE_LONG_FOR_INTS)
                .build();
    }

}
