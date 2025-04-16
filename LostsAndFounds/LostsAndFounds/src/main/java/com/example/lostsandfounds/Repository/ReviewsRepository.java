package com.example.lostsandfounds.Repository;
import com.example.lostsandfounds.Model.FoundItem;
import com.example.lostsandfounds.Model.Reviews;
import com.example.lostsandfounds.Model.Reward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface  ReviewsRepository extends JpaRepository<Reviews,Integer> {
    Reviews findReviewsById(Integer id);


    @Query("select r from Reviews r where  r.content like %?1%")
    List<Reviews> findReviewWithSpecifWord(String keyWord);
}
