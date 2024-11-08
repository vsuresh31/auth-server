package com.jovora.config.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tenant_details")
public class TenantDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tenantId;
    private String tenantName;
    @Transient
    private String categoryName;
    private String accessRole;
    @Transient
    private List<ConfigurationItem> configurationItems;

}
