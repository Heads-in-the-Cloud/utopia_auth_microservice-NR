package com.smoothstack.utopia.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class DeployController {

    @GetMapping(path = "/api/auth/deployment")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> res = new HashMap<>();
        res.put("Version", "Blue");
        return ResponseEntity.ok(res);
    }
}
