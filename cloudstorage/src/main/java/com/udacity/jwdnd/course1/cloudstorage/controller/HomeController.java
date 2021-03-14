package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HomeController {


    private UserService userService;
    private NoteService noteService;
    private EncryptionService encryptionService;
    private CredentialService credentialService;
    private FileService fileService;


    public HomeController(UserService userService, EncryptionService encryptionService, NoteService noteService, CredentialService credentialService, FileService fileService) {
        this.userService = userService;
        this.encryptionService = encryptionService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.fileService = fileService;
    }

    @RequestMapping("/home")
    public String getHomePage(Authentication auth, Model model) {

        User user = userService.getUser(auth.getName());

        List<Note> noteList = noteService.getAllNotesByUserId(user.getUserId());

        List<Credential> credentialList = credentialService.getAllCredentialsByUserId(user.getUserId());

        List<File> fileList = fileService.getAllFilesByUserId(user.getUserId());

        model.addAttribute("noteList", noteList);

        model.addAttribute("credentialList", credentialList);

        model.addAttribute("fileList", fileList);

        return "home";
    }

}
