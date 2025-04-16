package com.example.lostsandfounds.Service;


import com.example.lostsandfounds.Model.Item;
import com.example.lostsandfounds.Model.User;
import com.example.lostsandfounds.Repository.FountItemRepository;
import com.example.lostsandfounds.Repository.ItemRepository;
import com.example.lostsandfounds.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {


    private final ItemRepository itemRepository;
    private final FountItemRepository fountItemRepository;
    private final UserRepository userRepository;



    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }


    public Boolean addItem(Item item) {

        User user = userRepository.findUserById(item.getUserId());
        if (user == null) {
            return false;
        }

        itemRepository.save(item);
        return true;
    }


    public Boolean updateItem(Item item, Integer itemId, Integer userId) {

        Item oldItem = itemRepository.findItemById(itemId);
        ///check if the item was added by the user and if the item is not null
        /// the item type can not be changed only the info
        if (itemRepository.findItemByUserId(userId) != null && oldItem != null) {

            oldItem.setItemName(item.getItemName());
            oldItem.setCategory(item.getCategory());
            oldItem.setDescription(item.getDescription());
            oldItem.setLocation(item.getLocation());
            oldItem.setTheDate(item.getTheDate());


            itemRepository.save(oldItem);
            return true;


        }
        return false;
    }


    public Boolean deleteItem(Integer id) {

        Item item = itemRepository.findItemById(id);
        //check if the delete is made by the admin and the item is not empty
        if ( item != null){
            itemRepository.delete(item);
            return true;

        }
        return false;
    }












}
