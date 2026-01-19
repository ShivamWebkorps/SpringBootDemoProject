package com.example.springbootdemo;

import com.example.springbootdemo.Entity.Employee;
import com.example.springbootdemo.Repository.Repo;
import com.example.springbootdemo.Services.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@Testcontainers
@SpringBootTest(classes = Application.class)

public class IntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
        registry.add("spring.jpa.properties.hibernate.jdbc.time_zone", () -> "Asia/Kolkata");
    }
    /*@BeforeEach
    void cleanDatabase() {
        repository.deleteAll();
    }
*/

    @Autowired
    private EmployeeService service;

    @Autowired
    private Repo repository;

    @Test
    void getAllEmployeesTest() {

        List<Employee> employees = service.getAllEmployees();

        assertEquals(6, employees.size());
    }

    @Test
    void getEmployeeByIdTest() {

        Employee e = new Employee();
        e.setName("Shivam");

        Employee saved = repository.save(e);

        Optional<Employee> result = service.getEmployeeById(saved.getId());

        assertTrue(result.isPresent());
        assertEquals("Shivam", result.get().getName());
    }

    @Test
    void deleteEmployeeTest() {

        Employee e = new Employee();
        e.setName("Shivam");

        Employee saved = repository.save(e);
        assertTrue(repository.findById(saved.getId()).isPresent());
        service.deleteEmployee(saved.getId());

        assertFalse(repository.findById(saved.getId()).isPresent());
    }
}
