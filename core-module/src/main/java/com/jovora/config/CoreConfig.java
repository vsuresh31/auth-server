package com.jovora.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class CoreConfig implements ApplicationRunner {
    private static List<TenantProperties> tenantProperties = new ArrayList<>();

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Constructor constructor = new Constructor(TenantProperties[].class, new LoaderOptions());
        Yaml yaml = new Yaml(constructor);
        InputStream inputStream = new ClassPathResource("core-config.yml").getInputStream();
        TenantProperties[] configProperties = yaml.load(inputStream);
        tenantProperties.addAll(List.of(configProperties));
    }
}
