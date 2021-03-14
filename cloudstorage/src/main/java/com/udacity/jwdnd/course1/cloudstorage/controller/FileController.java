package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Objects.isNull;

@Controller
@RequestMapping("/file")
public class FileController {
    private FileService fileService;
    private UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping("/upload")
    public String createFile( @RequestParam("fileUpload") MultipartFile fileUploaded,
                              Authentication auth,
                              Model model) {

        User user = userService.getUser(auth.getName());
        model.addAttribute("selectedTab", "files");


        String errorMessage = null;
        if(!fileService.isFilenameAlreadyExists(user.getUserId(), fileUploaded.getOriginalFilename())){
            errorMessage = "The Filename already exists.";
            model.addAttribute("errorFlag",true);
            model.addAttribute("errorMessage", errorMessage);
        }

        if(isNull(errorMessage)) {
            try {
                File file = File.builder()
                        .filename(fileUploaded.getOriginalFilename())
                        .contenttype(fileUploaded.getContentType())
                        .filesize(fileUploaded.getSize())
                        .userid(user.getUserId())
                        .filedata(fileUploaded.getBytes())
                        .build();

                fileService.submitFile(file);
                model.addAttribute("successFlag", true);

            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("successFlag", false);
            }
        }

        return "result";
    }

    @GetMapping("/delete/{fileId}")
    public String deleteFile(@PathVariable Integer fileId, Authentication auth, Model model) {

        User user = userService.getUser(auth.getName());
        model.addAttribute("selectedTab", "files");

        try {
            fileService.deleteFile(fileId, user.getUserId());
            model.addAttribute("successFlag",true);

        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("successFlag",false);
        }

        return "result";
    }

    @GetMapping("/view/{fileId}")
    public void viewFile(@PathVariable Integer fileId, Authentication auth, HttpServletResponse response, Model model) throws IOException {

        User user = userService.getUser(auth.getName());
        File file = fileService.getFile(fileId, user.getUserId());

        response.setContentType(file.getContenttype());

        try {
            response.getOutputStream().write(file.getFiledata(), 0, file.getFiledata().length);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            response.getOutputStream().close();
        }
    }
}
