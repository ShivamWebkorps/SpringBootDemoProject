package com.example.springbootdemo.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString


@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @JsonIgnore
    private String building;
    @JsonIgnore
    private String street;
    @JsonIgnore
    private String city;
    @JsonProperty("address_line")
    public String getAddressLine() {
        StringBuilder sb = new StringBuilder();

        if (building != null) sb.append(building).append(", ");
        if (street != null) sb.append(street).append(", ");
        if (city != null) sb.append(city);
        return sb.toString().trim();
    }


}
