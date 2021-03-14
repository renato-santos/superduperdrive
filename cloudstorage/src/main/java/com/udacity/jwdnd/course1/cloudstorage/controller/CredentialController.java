package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.Base64;

@Controller
@RequestMapping("/credential")
public class CredentialController {
    private CredentialService credentialService;
    private UserService userService;
    private EncryptionService encryptionService;

    public CredentialController(CredentialService credentialService, UserService userService, EncryptionService encryptionService) {
        this.credentialService = credentialService;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    @PostMapping("/submit")
    public String createCredential(
                             @RequestParam("url") String url,
                             @RequestParam("username") String username,
                             @RequestParam("password") String password,
                             @RequestParam("credentialId") Integer credentialId,
                             Authentication auth,
                             Model model) {

        User user = userService.getUser(auth.getName());

        SecureRandom random = new SecureRandom();
        byte[] keyValue = new byte[16];
        random.nextBytes(keyValue);
        String key = Base64.getEncoder().encodeToString(keyValue);
        String encryptedPassword = encryptionService.encryptValue(password, key);

        Credential credential = Credential.builder()
                        .credentialId(credentialId)
                        .url(url)
                        .username(username)
                        .key(key)
                        .password(encryptedPassword)
                        .userId(user.getUserId())
                        .build();

        model.addAttribute("selectedTab", "credentials");

        try {
            credentialService.submitCredential(credential);
            model.addAttribute("successFlag",true);
            return "result";

        }catch (Exception e){
            model.addAttribute("successFlag",false);
            return "result";
        }
    }

    @GetMapping("/delete/{credentialId}")
    public String deleteNote(@PathVariable Integer credentialId, Authentication auth, Model model) {

        User user = userService.getUser(auth.getName());

        model.addAttribute("selectedTab", "credentials");

        try {
            credentialService.deleteCredential(credentialId, user.getUserId());
            model.addAttribute("successFlag",true);
            return "result";

        }catch (Exception e){
            model.addAttribute("successFlag",false);
            return "result";
        }

    }

}
