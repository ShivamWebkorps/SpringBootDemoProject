package com.example.springbootdemo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "employee_responses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    @JsonIgnore
    private Employee employee;

    @Column(name = "json_response", columnDefinition = "TEXT")
    private String jsonResponse;
}
