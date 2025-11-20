package com.example.springbootdemo.Repository;

import com.example.springbootdemo.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Repo extends JpaRepository<Employee, Long> {
}
