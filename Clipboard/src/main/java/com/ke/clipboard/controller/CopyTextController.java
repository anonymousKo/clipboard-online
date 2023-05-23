package com.ke.clipboard.controller;

import com.ke.clipboard.common.Result;
import com.ke.clipboard.model.CopyText;
import com.ke.clipboard.service.CopyTextService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.List;

@Slf4j
@RestController
public class CopyTextController {
    @Autowired
    CopyTextService copyTextService;

    @Value("${directory:}")
    private String directory;

    @RequestMapping(value = "/add")
    public  Result<Integer> add(@RequestBody String msg) throws ParseException {
        log.info("received request: {}" , msg);
        int id = copyTextService.insert(msg);
        return Result.success(id);
    }
    @GetMapping("/findall")
    public Result<List<CopyText>> findAll(
            @RequestParam(value = "count", defaultValue = "") Integer count,
            @RequestParam(value = "isOnlyMarked", defaultValue = "false") Boolean isOnlyMarked){
        return Result.success(copyTextService.find(count, isOnlyMarked));
    }

    @GetMapping("/query")
    public Result<List<CopyText>> findByParam(@RequestParam(value = "msg", defaultValue = "") String msg){
        return Result.success(copyTextService.query(msg));
    }

    @GetMapping("/remark")
    public Result<Void> remark(@RequestParam(value = "id") Integer id){
        copyTextService.remark(id);
        return Result.success();
    }

    @GetMapping("/formats")
    public String getVideoFormats(@RequestParam("url") String videoUrl)  {
        try {
            String command = String.format("youtube-dl -F %s", videoUrl);
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
                command = String.format("youtube-dl.exe -o \"%s\\%%(title)s.%%(ext)s\" -f %s %s",
                        directory, formatCode, videoUrl);
            } else {
                command = String.format("youtube-dl -o %s/\"%%(title)s.%%(ext)s\" -f %s %s",
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

            return output.toString();
        } catch (IOException | InterruptedException e) {
            log.error("",e);
            return "Error occurred: " + e.getMessage();
        }
    }
}
