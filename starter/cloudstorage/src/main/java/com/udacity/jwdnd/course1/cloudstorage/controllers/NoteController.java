package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class NoteController {

    private final NoteService noteService;
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping("/note")
    public RedirectView add(Authentication authentication, Note note, RedirectAttributes model) {
        String error = null;
        String success = null;

        User user = this.userService.getUser(authentication.getName());
        note.setUserId(user.getUserId());

        if (note.getNoteId() == null) {
            int rowsAdded = this.noteService.createNote(note);
            if (rowsAdded < 0) {
                error = "There was an error. Please try again.";
            } else {
                success = "Note added";
            }
        } else {
            int rowsAffected = this.noteService.updateNote(note);
            if (rowsAffected > 0) {
                success = "Note updated";
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

    @GetMapping("/note/delete/{id}")
    public RedirectView delete(Authentication authentication, RedirectAttributes model, @PathVariable Integer id) {
        String error = null;
        String success = null;

        User user = this.userService.getUser(authentication.getName());
        Note note = this.noteService.getNote(id);

        boolean canDelete = note.getUserId().equals(user.getUserId());
        if (canDelete) {
            int rowsAffected = this.noteService.deleteNote(note);
            if (rowsAffected > 0) {
                success = "Note deleted";
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
