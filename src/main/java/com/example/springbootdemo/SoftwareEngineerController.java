package com.example.springbootdemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/software-engineers")

public class SoftwareEngineerController {

    @GetMapping
    public List<SoftwareEngineer> listSoftwareEngineers () {
        return softwareengineerservice.getAllSoftwareEngineers();


    }
}
