package com.authorization.server.authorization.server.validator.user;

import com.authorization.server.authorization.server.dao.UserDao;
import com.authorization.server.authorization.server.entity.user.User;
import com.authorization.server.authorization.server.enums.Action;
import com.authorization.server.authorization.server.validator.annotation.UserCustomValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserValidator implements ConstraintValidator<UserCustomValidation, User> {


    @Autowired
    private UserDao userDao;

    private Action action;

    @Override
    public void initialize(UserCustomValidation constraintAnnotation) {
        action = constraintAnnotation.action();

        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(User value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();

        if (action == Action.SIGNUP_ACTION) {
            return validateSignUp(value, context);

        } else if (action == Action.SIGN_IN) {
            return validateSignIn(value, context);

        } else if (action == Action.EMAIL_VERIFICATION) {
            return validateSignIn(value, context);
        }

        return true;
    }

    public boolean validateSignUp(User user, ConstraintValidatorContext context) {
        boolean isValid = true;

        if (userDao.isEmailExists(user.getEmail())) {
            addErrorMessages(context, List.of("Email already registered. Use different email?"), "email");
            isValid = false;
        }

        User dbUser = userDao.findByEmail(user.getEmail());

        if (dbUser == null && !user.getPassword().equals(user.getConfirmPassword())) {
            addErrorMessages(context, List.of("Password does not match."), "confirmPassword");
            isValid = false;
        }

        return isValid;
    }

    public boolean validateSignIn(User user, ConstraintValidatorContext context) {
        boolean isValid = true;


        User dbUser = userDao.findByEmail(user.getEmail());
        boolean emailExists = userDao.isEmailExists(user.getEmail());

        if (emailExists && dbUser.getPassword().equals(user.getPassword())) {
            addErrorMessages(context, List.of("Password does not match. Try Forgot Password?"), "email");
            isValid = false;
        }

        if (!emailExists) {
            addErrorMessages(context, List.of("Email is not registered. Do you want to signup?"), "email");
            isValid = false;
        }

        return isValid;
    }

    private void addErrorMessages(ConstraintValidatorContext context, List<String> errorMessages, String property) {
        context.buildConstraintViolationWithTemplate(String.join("\n", errorMessages))
                .addPropertyNode(property)
                .addConstraintViolation();
    }


}
