package com.stori.sofa.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class TesModuleController {

    @GetMapping("/test/module")
    public String testModule() throws IOException {

        return "";
    }

}
