package com.smoothstack.utopia.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeployController {

    @GetMapping(path = "/api/auth/deployment")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Blue");
    }
}
