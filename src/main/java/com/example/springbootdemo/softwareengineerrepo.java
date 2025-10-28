package com.example.springbootdemo;

import com.example.springbootdemo.SoftwareEngineer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface softwareengineerrepo extends JpaRepository<SoftwareEngineer, Integer> {
}
