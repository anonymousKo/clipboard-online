package com.ke.clipboard.controller;

import com.ke.clipboard.common.Result;
import com.ke.clipboard.model.CopyText;
import com.ke.clipboard.service.CopyTextService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@Slf4j
@RestController
public class CopyTextController {
    @Autowired
    CopyTextService copyTextService;

    @RequestMapping(value = "/add")
    public  Result<List<CopyText>> add(@RequestBody String msg) throws ParseException {
        log.info("received request: {}" , msg);
        copyTextService.insert(msg);
        return Result.success();
    }
    @GetMapping("/findall")
    public Result<List<CopyText>> findAll(@RequestParam(value = "count", defaultValue = "") Integer count){
        return Result.success(copyTextService.find(count));
    }

}
