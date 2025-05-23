package com.example.lostsandfounds.Repository;

import com.example.lostsandfounds.Model.Request;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface RequestRepository extends JpaRepository<Request,Integer> {

    Request findRequestById(Integer id);

    List<Request> findRequestByUserId(Integer userId);


}
