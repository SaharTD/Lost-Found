package com.example.lostsandfounds.Controller;

import com.example.lostsandfounds.Api.ApiResponse;
import com.example.lostsandfounds.Model.Reviews;
import com.example.lostsandfounds.Model.Reward;
import com.example.lostsandfounds.Service.ReviewsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/review")
@RequiredArgsConstructor
public class ReviewsController {


    private final ReviewsService reviewsService;



    @GetMapping("get")
    public ResponseEntity getAllReviews(){
        return ResponseEntity.status(200).body(reviewsService.getAllReviews()) ;
    }


    /// 15
    @PostMapping("add")
    public ResponseEntity addReviews(@Valid @RequestBody Reviews review, Errors e ){

        if (e.hasErrors()){
            return ResponseEntity.status(400).body(e.getFieldError().getDefaultMessage());

        }
        String result =  reviewsService.addReviews(review);
        if (result.equals("userNF")) {
            return ResponseEntity.status(400).body(new ApiResponse("The user id  is not found"));
        }else  return ResponseEntity.status(200).body(new ApiResponse("The review is added successfully"));

    }


    @PutMapping("update/{id}")
    public ResponseEntity updateReviews(@Valid @RequestBody Reviews review, Errors e, @PathVariable Integer id){

        if(e.hasErrors()){
            return ResponseEntity.status(400).body(e.getFieldError().getDefaultMessage());
        }


        if(reviewsService.updateReviews(review,id)){
            return ResponseEntity.status(200).body(new ApiResponse("The review is added successfully"));

        }
        return ResponseEntity.status(400).body(new ApiResponse("The review is not found"));


    }


    @DeleteMapping("delete/{id}")
    public ResponseEntity deleteReviews(@PathVariable Integer id){

        if(reviewsService.deleteReviews(id)){
            return ResponseEntity.status(200).body(new ApiResponse("The review is deleted successfully"));

        }
        return ResponseEntity.status(400).body(new ApiResponse("The review is not found"));

    }











}
