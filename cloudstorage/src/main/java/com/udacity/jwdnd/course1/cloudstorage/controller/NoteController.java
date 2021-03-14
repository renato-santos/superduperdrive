package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/note")
public class NoteController {
    private NoteService noteService;
    private UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping("/submit")
    public String createNote(
                             @RequestParam("noteTitle") String noteTitle,
                             @RequestParam("noteDescription") String noteDescription,
                             @RequestParam("noteId") Integer noteId,
                             Authentication auth,
                             Model model) {

        User user = userService.getUser(auth.getName());

        Note note = Note.builder()
                        .noteId(noteId)
                        .noteDescription(noteDescription)
                        .noteTitle(noteTitle)
                        .userId(user.getUserId())
                        .build();

        model.addAttribute("selectedTab", "notes");

        try {
            noteService.submitNote(note);
            model.addAttribute("successFlag",true);
            return "result";

        }catch (Exception e){
            model.addAttribute("successFlag",false);
            return "result";
        }
    }

    @GetMapping("/delete/{noteId}")
    public String deleteNote(@PathVariable Integer noteId, Authentication auth, Model model) {

        User user = userService.getUser(auth.getName());

        model.addAttribute("tab", "notes");

        try {
            noteService.deleteNote(noteId, user.getUserId());
            model.addAttribute("successFlag",true);
            return "result";

        }catch (Exception e){
            model.addAttribute("successFlag",false);
            return "result";
        }
    }
}
