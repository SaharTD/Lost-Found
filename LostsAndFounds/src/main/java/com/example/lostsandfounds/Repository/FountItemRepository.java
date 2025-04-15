package com.example.lostsandfounds.Repository;

import com.example.lostsandfounds.Model.FoundItem;
import com.example.lostsandfounds.Model.Item;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface FountItemRepository extends JpaRepository<FoundItem,Integer> {
    FoundItem findFoundItemById(Integer id);

    List<FoundItem> findFoundItemByItemNameAndCategoryAndTheDateAndLocation( String itemName, String category, LocalDate theDate, String location);


    FoundItem findFoundItemByCategoryAndItemNameAndIsReadyForDonation( String category,  String itemName,Boolean status);

    @Query("select i from FoundItem i where  i.itemStatus=?1")
    List<FoundItem> findItemsByStatus(String status);



}
