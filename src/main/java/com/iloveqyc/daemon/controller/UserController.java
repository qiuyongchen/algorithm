package com.iloveqyc.daemon.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/")
    public String view() throws InterruptedException {
//        log.info("receive request, ...");

        Thread.sleep(10);

        return "hi";
    }

}
