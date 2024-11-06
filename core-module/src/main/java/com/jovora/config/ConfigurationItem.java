package com.jovora.config;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ConfigurationItem {
    private String configKey;
    private String configValue;
    private List<String> accessRole;
    private boolean encrypted;
}
