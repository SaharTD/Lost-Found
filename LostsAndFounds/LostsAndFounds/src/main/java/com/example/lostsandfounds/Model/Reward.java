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
public class Reward {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @Size(min = 10 , message="the reward should be at least characters only")
    @Column(columnDefinition = "varchar(20) not null ")
    private String randomReward ;


    @Column(columnDefinition = "int unique")
    private Integer userId;



}
