package com.example.app.util.validation;

import com.example.app.repository.BulletinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.security.Principal;

public class IsCurrentUserBulletinValidator  implements ConstraintValidator<IsCurrentUserBulletin, Long> {
    private BulletinRepository bulletinRepository;

    @Autowired
    public IsCurrentUserBulletinValidator(BulletinRepository bulletinRepository) {
        this.bulletinRepository = bulletinRepository;
    }

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        return !bulletinRepository.findById(id).get().getUser().getEmail().equals(principal.getName());
    }
}
