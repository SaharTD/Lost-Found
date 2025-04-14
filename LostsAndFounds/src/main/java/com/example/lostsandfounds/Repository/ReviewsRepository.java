package com.example.lostsandfounds.Repository;
import com.example.lostsandfounds.Model.Reviews;
import com.example.lostsandfounds.Model.Reward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface  ReviewsRepository extends JpaRepository<Reviews,Integer> {
    Reviews findReviewsById(Integer id);

}
