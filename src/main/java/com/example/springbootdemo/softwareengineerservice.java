package com.example.springbootdemo;

import com.example.springbootdemo.SoftwareEngineer;
import com.example.springbootdemo.softwareengineerrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class softwareengineerservice {
    private static softwareengineerrepo SoftwareEngineerrepo;

    @Autowired
    public softwareengineerservice(softwareengineerrepo SoftwareEngineerrepo) {
        this.SoftwareEngineerrepo = SoftwareEngineerrepo;
    }

    public static List<SoftwareEngineer> getAllSoftwareEngineers() {
        return SoftwareEngineerrepo.findAll();
    }
}
