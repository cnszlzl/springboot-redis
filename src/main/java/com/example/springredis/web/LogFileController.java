package com.example.springredis.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author carzy
 * @date 2018/07/2018/7/29
 */
@RestController
public class LogFileController {

    @Value("${logging.path}")
    private String path;

    @GetMapping("logfile")
    public void outLogFile() {

    }
}
