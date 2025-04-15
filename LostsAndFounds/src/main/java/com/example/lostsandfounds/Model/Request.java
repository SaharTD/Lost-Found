package com.example.lostsandfounds.Model;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Request {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;




    @NotEmpty(message = "the request type should not be empty")
    @Pattern(regexp = "^(?i)(Claim|Donation)$",message = "Type of request (Claim,Donation,Pick up)")
    @Column(columnDefinition = "varchar(10) not null ")
    private String requestType ;


    @Column(columnDefinition = "boolean  ")
    private Boolean isApproved ;


    @NotEmpty(message = "the item name should not be empty")
    @Size(max = 10, message = "the item name should be 10 letters only")
    @Column(columnDefinition = "varchar(10) not null ")
    private String itemName ;


    @NotEmpty(message = "the category should not be empty")
    @Pattern(regexp = "^(?i)(Electronics|Clothing & Accessories|Personal Items|Household Items|Pets & Animal Items)$",message = "The available categories Electronics Clothing & Accessories Personal Items Household Items Pets & Animal Items")
    @Column(columnDefinition = "varchar(30) not null ")
    private String ItemCategory ;

    @Size(max =20,message = "the location size should be 20 letters only")
    @Column(columnDefinition = "varchar(20) not null")
    private String ItemLocation;


    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "date")
    private LocalDate theDate;


    @Size(max =60 ,message = "the item description should contain only 60 letters")
    @Column(columnDefinition = "varchar(60) not null")
    private String ItemDescription ;



    @NotNull(message = "the user id should not be empty")
    @Column(columnDefinition = "int not null unique")
    private Integer userId;




}
