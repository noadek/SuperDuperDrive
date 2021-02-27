package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class HomeController {

    private final UserService userService;
    private final NoteService noteService;
    private final CredentialService credentialService;
    //private final FileService fileService;
    private final EncryptionService encryptionService;

    public HomeController(
            UserService userService,
            NoteService noteService,
            CredentialService credentialService,
            // FileService fileService,
            EncryptionService encryptionService
    ) {
        this.userService = userService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        // this.fileService = fileService
        this.encryptionService = encryptionService;
    }

    @GetMapping("/")
    public String getHomePage(Authentication authentication, Model model) {
        User user = this.userService.getUser(authentication.getName());
        List<Note> notes = this.noteService.getUserNotes(user.getUserId());
        List<Credential> credentials = this.credentialService.getUserCredentials(user.getUserId());

        model.addAttribute("notes", notes);
        model.addAttribute("credentials", credentials);
        // model.addAttribute("files", files);
        model.addAttribute("encryptionService", this.encryptionService);

        return "home";
    }

}
