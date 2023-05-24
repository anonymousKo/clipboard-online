package com.ke.clipboard.controller;

import com.ke.clipboard.dao.FileRepository;
import com.ke.clipboard.model.FileDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/video")
public class VideoController {

    @Value("${directory:}")
    private String directory;

    @Autowired
    private FileRepository fileRepository;


    @GetMapping("/formats")
    public String getVideoFormats(@RequestParam("url") String videoUrl)  {
        try {
            String command = String.format("yt-dlp -F %s", videoUrl);
            log.info("command:{}", command);
            Process process = Runtime.getRuntime().exec(command);

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            process.waitFor();

            return output.toString();
        } catch (Exception e) {
            log.error("",e);
            return "Error occurred: " + e.getMessage();
        }
    }

    @GetMapping("/download")
    public String downloadVideo(@RequestParam("url")  String videoUrl, @RequestParam("formatCode")  String formatCode) throws IOException {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            String command;

            if (os.contains("win")) {
                command = String.format("yt-dlp.exe -o \"%s\\%%(title)s.%%(ext)s\" -f %s %s",
                        directory, formatCode, videoUrl);
            } else {
                command = String.format("yt-dlp -o %s/\"%%(title)s.%%(ext)s\" -f %s %s",
                        directory, formatCode, videoUrl);
            }
            log.info("command:{}", command);

            ProcessBuilder processBuilder;
            if (os.contains("win")) {
                processBuilder = new ProcessBuilder("cmd", "/c", command);
            } else {
                processBuilder = new ProcessBuilder("bash", "-c", command);
            }

            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            process.waitFor();
            initFiles();
            return output.toString();
        } catch (IOException | InterruptedException e) {
            log.error("",e);
            return "Error occurred: " + e.getMessage();
        }
    }

    @GetMapping("/list")
    public Iterable<FileDTO> getAllFiles() {
        return fileRepository.findAll();
    }

    @PostConstruct
    private void initFiles() {
        int i = 1;
        File baseDir = new File(directory);
        File[] files = baseDir.listFiles();
        List<FileDTO> fileDTOs = new ArrayList<>();

        if (files != null) {
            for (File file : files) {
                String fileId =  i++ + "";
                fileDTOs.add(new FileDTO(fileId , file.getName()));
            }
            fileRepository.deleteAll();
            fileRepository.saveAll(fileDTOs);
        }
    }

    @GetMapping("/query")
    public ResponseEntity<Resource> downloadFile(@RequestParam("id")  String id) throws IOException {
        Optional<FileDTO> fileDTO = fileRepository.findById(id);
        if (!fileDTO.isPresent()){
            log.error("file not found -> {}", id);
            return ResponseEntity.notFound().build();
        }
        String filename = fileDTO.get().getName();
        File file = new File(directory + File.separator + filename);

        if (file.exists()) {
            Resource resource = new FileSystemResource(file);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } else {
            log.error("file not found -> {}  {}", id, filename);
            return ResponseEntity.notFound().build();
        }
    }

    private String generateFileId(String filename) {
        // Generate a unique ID for the file using some algorithm
        // For simplicity, you can use the hash code of the filename
        return String.valueOf(filename.hashCode());
    }
}
