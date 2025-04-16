package com.example.lostsandfounds.Controller;

import com.example.lostsandfounds.Api.ApiResponse;
import com.example.lostsandfounds.Model.Item;
import com.example.lostsandfounds.Service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/item")
@RequiredArgsConstructor
public class ItemController {


    /// 1 endpoint (Match items,unclimed)

    private final ItemService itemService;


    @GetMapping("get")
    public ResponseEntity getAllItems() {
        return ResponseEntity.status(200).body(itemService.getAllItems());
    }


/// 9
    @PostMapping("add")
    public ResponseEntity addItem(@Valid @RequestBody Item item, Errors e) {

        if (e.hasErrors()) {
            return ResponseEntity.status(400).body(e.getFieldError().getDefaultMessage());

        }
        if (itemService.addItem(item)){
            return ResponseEntity.status(200).body(new ApiResponse("The item is added successfully"));

        }else return ResponseEntity.status(400).body(new ApiResponse("The user is not found"));

    }


    @PutMapping("update/{itemId}/{userId}")
    public ResponseEntity updateItem(@Valid @RequestBody Item item, Errors e, @PathVariable Integer itemId, @PathVariable Integer userId) {

        if (e.hasErrors()) {
            return ResponseEntity.status(400).body(e.getFieldError().getDefaultMessage());
        }


        if (itemService.updateItem(item, itemId, userId)) {
            return ResponseEntity.status(200).body(new ApiResponse("The item is added successfully"));

        }
        return ResponseEntity.status(400).body(new ApiResponse("The item or the user are not found"));


    }


    @DeleteMapping("delete/{id}")
    public ResponseEntity deleteItem(@PathVariable Integer id ) {

        if (itemService.deleteItem(id)) {
            return ResponseEntity.status(200).body(new ApiResponse("The item is deleted successfully"));

        }
        return ResponseEntity.status(400).body(new ApiResponse("The admin or the item are not found"));

    }



}
