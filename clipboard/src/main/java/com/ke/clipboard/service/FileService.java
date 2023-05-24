package com.ke.clipboard.service;

import com.ke.clipboard.dao.FileRepository;
import com.ke.clipboard.model.FileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FileService {
    private final FileRepository userRepository;

    @Autowired
    public FileService(FileRepository userRepository) {
        this.userRepository = userRepository;
    }

    public FileDTO saveFile(FileDTO user) {
        return userRepository.save(user);
    }

    public Optional<FileDTO> getFile(String id) {
        return userRepository.findById(id);
    }

    public void deleteFile(String id) {
        userRepository.deleteById(id);
    }
}
