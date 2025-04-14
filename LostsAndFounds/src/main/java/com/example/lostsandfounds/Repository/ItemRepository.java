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


    @Query("select i from Item i where i.itemType=?1")
    Item findLostItemByUserId(String type, Integer userId);

    Item findItemByUserId(Integer userId);

    List<Item> findItemByItemNameAndCategoryAndTheDateAndLocation(String itemName, String category, LocalDate theDate, String location);

    Item findItemByCategoryAndItemNameAndIsReadyForDonation(String category, String itemName, Boolean isReadyForDonation);

    @Query("select i from Item i where  i.location=?1 and i.itemType='Lost'")
    List <Item> findLostItemBasedOnTheLocation(String Location);
}
