package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class FileService {

    private FileMapper fileMapper;

    public boolean isFilenameAlreadyExists(Integer userId, String filename) {
        return fileMapper.findFileByFilename(filename, userId) == null;
    }

    public void submitFile(File file) {
        int result = fileMapper.insertFile(file);
    }

    public void deleteFile(Integer fileId, Integer userId){
        fileMapper.deleteFile(fileId, userId);
    }

    public List<File> getAllFilesByUserId(Integer userId){
        return fileMapper.findAllFilesByUserId(userId);
    }

    public File getFile(Integer fileId, Integer userId){
        return fileMapper.findFileByFileId(fileId, userId);
    }


}
