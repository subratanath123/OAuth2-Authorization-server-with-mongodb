package com.authorization.server.authorization.server.controller.auth;


import com.authorization.server.authorization.server.dao.UserDao;
import com.authorization.server.authorization.server.dto.common.EmailOtpDetails;
import com.authorization.server.authorization.server.entity.user.Role;
import com.authorization.server.authorization.server.entity.user.User;
import com.authorization.server.authorization.server.service.mail.EmailService;
import com.authorization.server.authorization.server.service.user.UserService;
import com.authorization.server.authorization.server.utils.OtpGenerator;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import static java.util.Arrays.asList;

//@Api(tags = "Authentication")
//@RestController

@Controller
@RequestMapping("/public")
public class SignupController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

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
    public String registrationSubmit(@Valid @ModelAttribute User user,
                                     BindingResult bindingResult) throws MessagingException {

        user.setRoleList(asList(Role.VIEW));

        if (bindingResult.hasErrors()) {
            return "signup";
        }

        User existingUser = userDao.findByEmail(user.getEmail());

        if (existingUser != null && existingUser.isVerifiedUser()) {
            bindingResult.reject("user.email.exists");
        }

        if (bindingResult.hasErrors()) {
            return "signup";
        }

        userService.save(user);
        user.setVerifiedUser(false); //Initially not verified at first registration

        emailService.sendMailWithAttachment(
                new EmailOtpDetails
                        .Builder()
                        .msgBody("Your OTP code is" + OtpGenerator.generateOtp(6))
                        .recipient(user.getEmail())
                        .subject("Be Friend Me :: OTP")
                        .build()
        );

        return "done";
    }

}
