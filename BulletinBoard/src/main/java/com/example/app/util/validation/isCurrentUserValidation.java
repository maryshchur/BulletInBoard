package com.example.app.util.validation;

import com.example.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.security.Principal;

public class isCurrentUserValidation implements ConstraintValidator<isCurrentUser, Long> {
    private UserRepository userRepository;

    @Autowired
    public isCurrentUserValidation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        return !userRepository.findUserByEmail(principal.getName()).get().getId().equals(id);
    }
}
