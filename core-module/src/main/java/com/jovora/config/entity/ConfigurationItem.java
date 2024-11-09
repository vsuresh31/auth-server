package com.jovora.config.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "configuration_item")
public class ConfigurationItem {

    @Id
    private Long configurationItemId;
    private long categoryId;
    private String configKey;
    private String configValue;
    private String accessRole;
    private String encryptedIndicator;
}
