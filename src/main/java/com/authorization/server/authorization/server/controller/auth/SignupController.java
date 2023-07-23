package com.authorization.server.authorization.server.controller.auth;


import com.authorization.server.authorization.server.dao.UserDao;
import com.authorization.server.authorization.server.dto.common.EmailOtpDetails;
import com.authorization.server.authorization.server.entity.user.Role;
import com.authorization.server.authorization.server.entity.user.User;
import com.authorization.server.authorization.server.service.mail.EmailService;
import com.authorization.server.authorization.server.service.user.UserService;
import com.authorization.server.authorization.server.utils.JsonUtils;
import com.authorization.server.authorization.server.utils.OtpGenerator;
import com.authorization.server.authorization.server.validator.annotation.MailVerification;
import com.authorization.server.authorization.server.validator.annotation.UserCustomValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.authorization.server.authorization.server.enums.Action.EMAIL_VERIFICATION;
import static com.authorization.server.authorization.server.enums.Action.SIGNUP_ACTION;
import static org.springframework.http.HttpStatus.ACCEPTED;

@RestController
@RequestMapping("/public")
@CrossOrigin(origins = "*")
@Validated //its must needed for custom annotation validator
public class SignupController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("role*");
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registrationSubmit(@Validated
                                                @RequestBody
                                                @UserCustomValidation(action = SIGNUP_ACTION) User user) throws Exception {
        user.setRoleList(List.of(Role.VIEW));

        redisTemplate.convertAndSend("signUpLog", JsonUtils.convertObjectToJson(user));

        userService.save(user);
        user.setVerifiedUser(false); //Initially not verified at first registration

        String verificationCode = OtpGenerator.generateOtp(6);
        EmailOtpDetails otpDetails = new EmailOtpDetails
                .Builder()
                .msgBody("Your OTP code is " + verificationCode)
                .recipient(user.getEmail())
                .verificationCode(verificationCode)
                .subject("Be Friend Me :: OTP")
                .build();

        emailService.sendMailWithAttachment(otpDetails);

        redisTemplate.opsForValue().set("otpDetails:" + otpDetails.getEmail(), JsonUtils.convertObjectToJson(otpDetails));

        return new ResponseEntity<>(user, ACCEPTED);
    }

    @PostMapping("/mail-verification")
    public ResponseEntity<?> mailVerification(@Validated
                                              @MailVerification(action = EMAIL_VERIFICATION)
                                              @RequestBody EmailOtpDetails otpDetails) {

        User user = userDao.findByEmail(otpDetails.getEmail());
        user.setVerifiedUser(true);
        userDao.save(user);

        return new ResponseEntity<>(ACCEPTED);
    }

}
