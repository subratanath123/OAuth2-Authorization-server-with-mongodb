package com.authorization.server.authorization.server.controller.auth;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/*
 * Yet to be implemented
 */
@Controller
//@RequestMapping("/oauth2/consent")
public class UserConsentController {

    @GetMapping
    public String login(ModelMap modelMap) {
        return "consent";
    }
}
