package com.example.lostsandfounds.Repository;

import com.example.lostsandfounds.Model.Item;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface ItemRepository extends JpaRepository<Item,Integer> {

    Item findItemById(Integer id);



    Item findItemByUserId(Integer userId);

}
