package com.example.demo.multi.application.factories;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.util.Objects;
import java.util.Properties;

public class YamlPropertyReader implements PropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) {

        YamlPropertiesFactoryBean bean = new YamlPropertiesFactoryBean();
        bean.setResources(resource.getResource());
        Properties props = bean.getObject();
        String fileName = resource.getResource().getFilename();
        return new PropertiesPropertySource(Objects.requireNonNull(fileName, "The filename is null"),
                Objects.requireNonNull(props, "Props are null or couldn't be loaded"));
    }
}
