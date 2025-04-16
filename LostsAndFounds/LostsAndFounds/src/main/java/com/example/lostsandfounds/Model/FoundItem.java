package com.example.lostsandfounds.Model;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FoundItem {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;


    @NotEmpty(message = "the category should not be empty")
    @Pattern(regexp = "^(?i)(Electronics|Clothing & Accessories|Personal Items|Household Items|Pets & Animal Items)$",message = "The available categories Electronics Clothing & Accessories Personal Items Household Items Pets & Animal Items")
    @Column(columnDefinition = "varchar(30) not null ")
    private String category ;

    @NotEmpty(message = "the item name should not be empty")
    @Size(max = 10, message = "the item name should be 10 letters only")
    @Column(columnDefinition = "varchar(10) not null ")
    private String itemName ;



    @NotEmpty(message = "the item type should not be empty")
    @Pattern(regexp = "^(?i)(Found|Returned|Donated)$",message = "item status (Returned,Found,donated)")
    @Column(columnDefinition = "varchar(15) not null ")
    private String itemStatus ;


    @NotEmpty(message = "the location should not be empty , example = Al-Balad ,Corniche Jeddah , RedSea Mall")
    @Size(max =20,message = "the location size should be 20 letters only")
    @Column(columnDefinition = "varchar(20) not null")
    private String location;



    @NotNull(message = "the date should not be empty")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "date")
    private LocalDate theDate;



    @NotEmpty(message = "The description should not be empty . note: you can add key words")
    @Size(max =60 ,message = "the item description should contain only 60 letters")
    @Column(columnDefinition = "varchar(60) not null")
    private String description ;



    @Column(columnDefinition = "boolean ")
    private Boolean isClaimed ;


    @Column(columnDefinition = "boolean ")
    private Boolean isReadyForDonation ;


    @Column(columnDefinition = "boolean ")
    private Boolean Donated ;


    @NotNull(message = "the user id should not be empty")
    @Column(columnDefinition = "int ")
    private Integer userId;




    @Column(columnDefinition = "int ")
    private Integer itemId;



}
