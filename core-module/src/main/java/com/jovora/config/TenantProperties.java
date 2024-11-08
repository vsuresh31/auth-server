package com.jovora.config;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class TenantProperties {

    @Id
    @GeneratedValue
    private long tenantId;
    private String tenantName;
//    private String categoryName;
    private String accessRole;
//    private List<ConfigurationItem> configurationItems;

}
