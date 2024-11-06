package com.jovora.config;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TenantProperties {

    private String tenantName;
    private String categoryName;
    private List<String> accessRole;
    private List<ConfigurationItem> configurationItems;

}
