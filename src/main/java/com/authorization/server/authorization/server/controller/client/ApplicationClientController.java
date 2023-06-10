package com.authorization.server.authorization.server.controller.client;

import com.authorization.server.authorization.server.dao.ApplicationClientRepository;
import com.authorization.server.authorization.server.dto.common.DoneBean;
import com.authorization.server.authorization.server.dto.common.Type;
import com.authorization.server.authorization.server.entity.application.ApplicationClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.List;

import static java.util.Arrays.asList;
import static org.springframework.security.oauth2.core.AuthorizationGrantType.AUTHORIZATION_CODE;
import static org.springframework.security.oauth2.core.AuthorizationGrantType.REFRESH_TOKEN;
import static org.springframework.security.oauth2.core.ClientAuthenticationMethod.CLIENT_SECRET_BASIC;
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
    public String registrationSubmit(@Validated @ModelAttribute ApplicationClient applicationClient,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes,
                                     ModelMap modelMap) {

        if (bindingResult.hasErrors()) {
            modelMap.put("applicationClient", applicationClient);

            return "application-client";
        }

        applicationClient
                .setClientAuthenticationMethods(new HashSet<>(List.of(CLIENT_SECRET_BASIC)));

        applicationClient
                .setAuthorizationGrantTypes(new HashSet<>(asList(AUTHORIZATION_CODE, REFRESH_TOKEN)));

        applicationClient
                .setScopes(new HashSet<>(asList(OPENID, "read")));

        RegisteredClient registeredClient = applicationClientRepository.converToRegisteredClient(applicationClient);

        applicationClientRepository.save(registeredClient);

        redirectAttributes.addFlashAttribute("doneBean", new DoneBean(applicationClient.toString(), Type.SUCCESS));

        return "redirect:/public/done";
    }

}
