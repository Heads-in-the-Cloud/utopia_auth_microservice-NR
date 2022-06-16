package com.smoothstack.utopia.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping(path = "/api/auth/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("OK");
    }
}
