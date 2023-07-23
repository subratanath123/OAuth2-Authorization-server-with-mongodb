package com.authorization.server.authorization.server.controller.jwk;

import com.nimbusds.jose.jwk.JWKSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class JwkSetRestController {

    @Autowired
    private JWKSet jwkSet;

    @GetMapping(value = "/.well-known/jwks.json",  produces={"application/json"})
    public Map<String, Object> keys() {
        return this.jwkSet.toJSONObject();
    }
}
