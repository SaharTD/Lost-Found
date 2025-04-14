package com.example.lostsandfounds.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Reviews {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @Size(max = 60 , message="the review should be 60 characters only")
    @Column(columnDefinition = "varchar(60) not null")
    private String content ;



    @Column(columnDefinition = "int unique")
    private Integer userId;





}
