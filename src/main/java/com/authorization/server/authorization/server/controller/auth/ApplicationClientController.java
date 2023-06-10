package com.authorization.server.authorization.server.controller.auth;

import com.authorization.server.authorization.server.dao.ApplicationClientRepository;
import com.authorization.server.authorization.server.dao.UserDao;
import com.authorization.server.authorization.server.entity.application.ApplicationClient;
import com.authorization.server.authorization.server.entity.user.Role;
import com.authorization.server.authorization.server.entity.user.User;
import com.authorization.server.authorization.server.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;

import static java.util.Arrays.asList;
import static org.springframework.security.oauth2.core.AuthorizationGrantType.*;
import static org.springframework.security.oauth2.core.ClientAuthenticationMethod.*;
import static org.springframework.security.oauth2.core.oidc.OidcScopes.OPENID;

@Controller
@RequestMapping("/public/client/registration")
public class ApplicationClientController {

    @Autowired
    private ApplicationClientRepository applicationClientRepository;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping
    public String showRegistration(ModelMap modelMap) {
        modelMap.put("applicationClient", new ApplicationClient());

        return "application-client";
    }

    @PostMapping
    public ResponseEntity<?> registrationSubmit(@ModelAttribute ApplicationClient applicationClient,
                                                BindingResult bindingResult) {

        applicationClient
                .setClientAuthenticationMethods(new HashSet<>(asList(CLIENT_SECRET_BASIC)));

        applicationClient
                .setAuthorizationGrantTypes(new HashSet<>(asList(AUTHORIZATION_CODE, REFRESH_TOKEN)));

        applicationClient
                .setScopes(new HashSet<>(asList(OPENID, "read")));

        RegisteredClient registeredClient = applicationClientRepository.converToRegisteredClient(applicationClient);

        applicationClientRepository.save(registeredClient);

        return ResponseEntity.accepted().body(registeredClient);
    }

}
