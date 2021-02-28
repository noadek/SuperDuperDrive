package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.security.SecureRandom;
import java.util.Base64;

@Controller
public class CredentialController {
    private final CredentialService credentialService;
    private final UserService userService;
    private final EncryptionService encryptionService;

    public CredentialController(
            CredentialService credentialService,
            UserService userService,
            EncryptionService encryptionService
    ) {
        this.credentialService = credentialService;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    @PostMapping("/credential")
    public RedirectView add(Authentication authentication, Credential credential, RedirectAttributes model) {
        String error = null;
        String success = null;

        User user = this.userService.getCurrentUser(authentication);

        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = this.encryptionService.encryptValue(credential.getPassword(), encodedKey);

        credential.setKey(encodedKey);
        credential.setPassword(encryptedPassword);
        credential.setUserId(user.getUserId());

        if (credential.getCredentialId() == null) {
            int rowsAdded = this.credentialService.createCredential(credential);
            if (rowsAdded < 0) {
                error = "There was an error. Please try again.";
            } else {
                success = "Credential added";
            }
        } else {
            int rowsAffected = this.credentialService.updateCredential(credential);
            if (rowsAffected > 0) {
                success = "Credential updated";
            } else {
                error = "There was an error. Please try again.";
            }
        }

        if (error == null) {
            model.addAttribute("success", success);
        } else {
            model.addAttribute("error", error);
        }

        return new RedirectView("/");
    }

    @GetMapping("/credential/delete/{id}")
    public RedirectView delete(Authentication authentication, RedirectAttributes model, @PathVariable Integer id) {
        String error = null;
        String success = null;

        User user = this.userService.getCurrentUser(authentication);
        Credential credential = this.credentialService.getCredential(id);

        boolean canDelete = credential.getUserId().equals(user.getUserId());
        if (canDelete) {
            int rowsAffected = this.credentialService.deleteCredential(credential);
            if (rowsAffected > 0) {
                success = "Credential deleted";
            } else {
                error = "There was an error. Please try again.";
            }
        } else {
            error = "Unauthorized access";
        }

        if (error == null) {
            model.addAttribute("success", success);
        } else {
            model.addAttribute("error", error);
        }

        return new RedirectView("/");
    }
}
