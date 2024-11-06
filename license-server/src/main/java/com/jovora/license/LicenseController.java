package com.jovora.license;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/license")
@Slf4j
public class LicenseController {

    @PostMapping("generate")
    public Map<String, String> generateLicense(@Valid License license) {
        try {
            String generatedLicense = LicenseGenerator.generate(license);
            return Map.of("license", generatedLicense);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Map.of("status", "error", "message", e.getMessage());
        }
    }
}
