package com.authorization.server.authorization.server.controller.auth;

import com.authorization.server.authorization.server.dao.UserDao;
import com.authorization.server.authorization.server.entity.Role;
import com.authorization.server.authorization.server.entity.User;
import com.authorization.server.authorization.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import static java.util.Arrays.asList;

public class ApplicationClientController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService userService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("role*");
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping("/registration")
    public String showRegistration(ModelMap modelMap) {
        modelMap.put("user", new User());

        return "signup";
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registrationSubmit(@ModelAttribute User user,
                                                BindingResult bindingResult) {

        user.setRoleList(asList(Role.VIEW));

        if (userDao.isEmailExists(user.getEmail())) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        userService.save(user);

        return ResponseEntity.accepted().body(user);
    }

}
