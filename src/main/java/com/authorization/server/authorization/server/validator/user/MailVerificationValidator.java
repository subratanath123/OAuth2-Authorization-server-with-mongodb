package com.authorization.server.authorization.server.validator.user;

import com.authorization.server.authorization.server.dao.UserDao;
import com.authorization.server.authorization.server.dto.common.EmailOtpDetails;
import com.authorization.server.authorization.server.entity.user.User;
import com.authorization.server.authorization.server.enums.Action;
import com.authorization.server.authorization.server.utils.JsonUtils;
import com.authorization.server.authorization.server.validator.annotation.MailVerification;
import com.authorization.server.authorization.server.validator.annotation.UserCustomValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static java.time.Duration.ofMinutes;
import static org.springframework.http.HttpStatus.*;

@Component
public class MailVerificationValidator implements ConstraintValidator<MailVerification, EmailOtpDetails> {

    @Autowired
    private UserDao userDao;

    private Action action;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void initialize(MailVerification constraintAnnotation) {
        action = constraintAnnotation.action();

        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(EmailOtpDetails value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();

        if (action == Action.EMAIL_VERIFICATION) {
            return validateMailVerification(value, context);
        }

        return true;
    }

    public boolean validateMailVerification(EmailOtpDetails emailOtpDetails, ConstraintValidatorContext context) {
        List<String> errorMessages = new ArrayList<>();

        String otpDetailsString = redisTemplate.opsForValue().getAndExpire("otpDetails:" + emailOtpDetails.getEmail(), ofMinutes(15));

        EmailOtpDetails otpDetails = JsonUtils.convertJsonToObject(otpDetailsString, EmailOtpDetails.class);

        if (otpDetails == null) {
            errorMessages.add("Invalid request made");

        } else if (otpDetails.getVerificationCode().equals(emailOtpDetails.getVerificationCode())) {
            errorMessages.add("Wrong Verification Code");
        }

        if (errorMessages.isEmpty()) {
            return true;

        } else {

            context.buildConstraintViolationWithTemplate(String.join("\n", errorMessages))
                    .addPropertyNode("verificationCode")
                    .addConstraintViolation();

            return false;
        }
    }


}
