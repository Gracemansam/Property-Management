package com.graceman.propertymangement.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String ownerName;
    @NotNull(message = "Owner Email is mandatory")
    @NotEmpty(message = "Owner Email cannot be empty")
    @Size(min = 1, max = 50, message = "Owner Email should be between 1 to 50 characters in length")
    private String ownerEmail;
    private String phone;
    @NotNull(message = "Password cannot be null")
    @NotEmpty(message = "Password cannot be empty")
    private String password;

    private String houseNo;
    private String street;
    private String city;
    private String postalCode;
    private String country;
    private String token;

    public UserDTO(String ownerName, String ownerEmail, String phone, String token) {
        this.ownerName = ownerName;
        this.ownerEmail = ownerEmail;
        this.phone = phone;
        this.token = token;
    }
}
