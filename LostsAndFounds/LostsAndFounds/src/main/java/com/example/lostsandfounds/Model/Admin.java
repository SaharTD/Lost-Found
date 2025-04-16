package com.example.lostsandfounds.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Admin {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;



    @NotEmpty(message = "the user should not be empty")
    @Size(min = 5,max = 8 ,message = "the username size must be from 5 to 8 characters")
    @Column(columnDefinition = "varchar(8) not null unique")
    private String username ;

    @NotEmpty(message = "the password should not be empty")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$")
    @Column(columnDefinition = "varchar(10) not null")
    private String password;


    @NotEmpty(message = "the user should not be empty")
    @Size(max = 25,message = "the full name  size must be 25v letters")
    @Pattern(regexp = "^[A-Za-z]+( [A-Za-z]+)*$",message = "the full name should have letters only ")
    @Column(columnDefinition = "varchar(25) not null ")
    private String full_name ;

    @NotEmpty(message = "the phone number should not be empty")
    @Size(max = 15,message = "the phone number size must be 10 numbers")
    @Pattern(regexp = "^[0-9]+$",message = "the phone number should be in the write format  ")
    @Column(columnDefinition = "varchar(15) not null unique")
    private String phone_number;


    @NotEmpty(message = "the email should not be empty")
    @Email(message = "email should be in the right format")
    @Column(columnDefinition = "varchar(30) not null unique")
    private String email;





}
