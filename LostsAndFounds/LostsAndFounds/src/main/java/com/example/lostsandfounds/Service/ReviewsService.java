package com.example.lostsandfounds.Service;


import com.example.lostsandfounds.Model.FoundItem;
import com.example.lostsandfounds.Model.Reviews;
import com.example.lostsandfounds.Model.Reward;
import com.example.lostsandfounds.Model.User;
import com.example.lostsandfounds.Repository.ReviewsRepository;
import com.example.lostsandfounds.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ReviewsService {


    private final ReviewsRepository reviewsRepository;
    private final UserRepository userRepository;


    public List<Reviews> getAllReviews(){
        return reviewsRepository.findAll();
    }


    public String addReviews(Reviews review){
        User user = userRepository.findUserById(review.getUserId());
        if (user == null) {
            return "userNF";
        }
         else reviewsRepository.save(review);
        return "done";

    }






    public Boolean updateReviews (Reviews review, Integer id)
    {
        Reviews oldReview= reviewsRepository.findReviewsById(review.getId());

        if(oldReview!=null){
            oldReview.setContent(review.getContent());

            reviewsRepository.save(oldReview);
            return true;
        }
        return false;
    }



    public Boolean deleteReviews (Integer id){

        if(reviewsRepository.findReviewsById(id)!=null){
            reviewsRepository.delete(reviewsRepository.findReviewsById(id));
            return true;
        }
        return false;

    }



    public List<Reviews> findReviewWithSpecifWord(String keyWord) {
        return reviewsRepository.findReviewWithSpecifWord(keyWord);
    }





    }










