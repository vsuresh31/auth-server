package com.jovora.config;

import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

import java.util.HashMap;

@Configuration
public class RestConfig implements PropertySourceLocator {




    @Override
    public PropertySource<?> locate(Environment environment) {
        return new MapPropertySource("config", new HashMap<>());
    }
}
