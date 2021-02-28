package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {
    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public File getFile(Integer fileId) {
        return this.fileMapper.getById(fileId);
    }

    public List<File> getUserFiles(Integer userId) { return this.fileMapper.getAllByUserId(userId); }

    public int createFile(MultipartFile fileUpload, Integer userId) throws IOException {
        return this.fileMapper.insert(new File(
                null,
                fileUpload.getOriginalFilename(),
                fileUpload.getContentType(),
                String.valueOf(fileUpload.getSize()),
                userId,
                fileUpload.getBytes()
        ));
    }

    public int deleteFile(File file) {
        return this.fileMapper.delete(file);
    }

    public boolean filenameExists(int userId, String filename) {
        File file = this.fileMapper.getByFilename(userId, filename);

        return file != null;
    }
}
