package com.jovora.config;

import com.jovora.config.entity.TenantDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/config")
public class ConfigController {

    private final ConfigService configService;

    public ConfigController(ConfigService configService) {
        this.configService = configService;
    }

    @GetMapping("/get-by-tenant/{tenant-name}")
    public ResponseEntity<TenantDetail> getByTenantId(@PathVariable("tenant-name") String tenantName) {
        return ResponseEntity.ok(configService.getTenantDetail(tenantName));
    }
}
