package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class FileController {
    private final FileService fileService;
    private final UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping("/file")
    public RedirectView add(Authentication authentication, MultipartFile fileUpload, RedirectAttributes model) {
        String error = null;
        String success = null;

        User user = this.userService.getCurrentUser(authentication);

        if (fileUpload.isEmpty()) {
            error = "Please select a file for upload.";
        } else {
            String filename = fileUpload.getOriginalFilename();
            if (fileService.filenameExists(user.getUserId(), filename)) {
                error = "A file with that name already exists.";
            } else {
                try {
                    int rowsAffected = this.fileService.createFile(fileUpload, user.getUserId());
                    if (rowsAffected > 0) {
                        success = "File uploaded";
                    } else {
                        error = "There was an error. Please try again.";
                    }
                } catch (IOException exception) {
                    error = "There was an error. Please try again.";
                }
            }
        }

        if (error == null) {
            model.addAttribute("success", success);
        } else {
            model.addAttribute("error", error);
        }

        return new RedirectView("/");
    }

    @GetMapping("/file/delete/{id}")
    public RedirectView delete(Authentication authentication, RedirectAttributes model, @PathVariable Integer id) {
        String error = null;
        String success = null;

        User user = this.userService.getCurrentUser(authentication);
        File file = this.fileService.getFile(id);

        boolean canDelete = file.getUserId().equals(user.getUserId());
        if (canDelete) {
            int rowsAffected = this.fileService.deleteFile(file);
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

    @GetMapping("/file/download/{id}")
    public ResponseEntity<InputStreamResource> download(Authentication authentication, @PathVariable Integer id) {
        User user = this.userService.getCurrentUser(authentication);
        File file = this.fileService.getFile(id);

        boolean canView = file.getUserId().equals(user.getUserId());
        if (canView) {
            InputStream inputStream = new ByteArrayInputStream(file.getFileData());
            InputStreamResource resource = new InputStreamResource(inputStream);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getFilename())
                    .contentType(MediaType.parseMediaType(file.getContentType()))
                    .body(resource);
        }

        return ResponseEntity.badRequest().body(null);
    }
}
