package com.example.springbootdemo.Services;

import com.example.springbootdemo.Entity.Employee;
import com.example.springbootdemo.Entity.ResponseResult;
import com.example.springbootdemo.Repository.Repo;
import com.example.springbootdemo.Repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private Repo employeeRepository;

    @Autowired
    private ResultRepository responseRepository;

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        return employeeRepository.findById(id)
                .map(existing -> {
                    updatedEmployee.setId(id);
                    return employeeRepository.save(updatedEmployee);
                })
                .orElse(null);
    }

    public void deleteEmployee(Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
        }
    }

    public ResponseResult saveJsonResponse(Long employeeId, String jsonResponse) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        ResponseResult response = new ResponseResult();
        response.setEmployee(employee);
        response.setJsonResponse(jsonResponse);

        return responseRepository.save(response);
    }

}