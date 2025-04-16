package com.example.lostsandfounds.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Appointment {



    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;


    @NotNull(message = "the date should not be empty")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "date")
    private LocalDate theDate;



    @NotNull(message = "the time should not be empty")
    @JsonFormat(pattern = "HH:mm:ss")
    @Column(columnDefinition = "time")
    private LocalTime theTime;


    @NotEmpty(message = "the location should not be empty , example = Al-Balad ,Corniche Jeddah , RedSea Mall")
    @Size(max =20,message = "the location size should be 20 letters only")
    @Column(columnDefinition = "varchar(20) not null")
    private String location;


    @NotNull(message = "the user id should not be empty")
    @Column(columnDefinition = "int not null unique")
    private Integer userId;





}
