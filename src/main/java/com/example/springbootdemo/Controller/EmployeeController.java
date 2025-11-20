package com.example.springbootdemo.Controller;

import com.example.springbootdemo.Entity.Employee;
import com.example.springbootdemo.Entity.ResponseResult;
import com.example.springbootdemo.Repository.ResultRepository;
import com.example.springbootdemo.Services.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id)
                .map(employee -> {
                    try {
                        String json = objectMapper.writeValueAsString(employee);
                        employeeService.saveJsonResponse(id, json);
                    } catch (Exception ignored) {}
                    return ResponseEntity.ok(employee);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        return ResponseEntity.ok(employeeService.createEmployee(employee));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        Employee updated = employeeService.updateEmployee(id, employee);
        return (updated != null) ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Employee> patchEmployee(@PathVariable Long id, @RequestBody Employee updates) {
        Optional<Employee> existingOpt = employeeService.getEmployeeById(id);
        if (existingOpt.isEmpty()) return ResponseEntity.notFound().build();

        Employee existing = existingOpt.get();
        if (updates.getName() != null) existing.setName(updates.getName());
        if (updates.getEmail() != null) existing.setEmail(updates.getEmail());
        if (updates.getDepartment() != null) existing.setDepartment(updates.getDepartment());
        if (updates.getPhoneNumber() != null) existing.setPhoneNumber(updates.getPhoneNumber());
        if (updates.getInHandSalary() != null) existing.setInHandSalary(updates.getInHandSalary());
        if (updates.getDeductions() != null) existing.setDeductions(updates.getDeductions());
        if (updates.getAddress() != null) existing.setAddress(updates.getAddress());
        if (updates.getCountry() != null) existing.setCountry(updates.getCountry());

        return ResponseEntity.ok(employeeService.updateEmployee(id, existing));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @Autowired
    private ResultRepository resultRepository;

    @GetMapping("/{id}/download")
    public ResponseEntity<String> downloadResponse(@PathVariable Long id) {

        ResponseResult res = resultRepository.findByEmployee_Id(id);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=response_" + id + ".json")
                .header("Content-Type", "application/json")
                .body(res.getJsonResponse());
    }


}
