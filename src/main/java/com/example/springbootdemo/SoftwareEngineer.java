package com.example.springbootdemo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class SoftwareEngineer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;
    String TechStack;

    public SoftwareEngineer() {
    }

     public SoftwareEngineer(int id, String name, String techStack) {
        this.id = id;
        this.name = name;
        TechStack = techStack;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTechStack() {
        return TechStack;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SoftwareEngineer that)) return false;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(TechStack, that.TechStack);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, TechStack);
    }

    public void setTechStack(String techStack) {
        TechStack = techStack;
    }
}
