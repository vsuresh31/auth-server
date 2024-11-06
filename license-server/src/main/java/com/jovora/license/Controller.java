package com.jovora.license;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/license")
public class Controller {

    @PostMapping("generate")
    public String generateLicense(@Valid License license) {

    }
}
