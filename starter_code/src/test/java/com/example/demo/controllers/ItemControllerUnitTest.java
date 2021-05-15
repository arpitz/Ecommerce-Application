package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerUnitTest {
    private ItemController itemController;
    private ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void init(){
        itemController = new ItemController();
        TestUtils.injectObject(itemController, "itemRepository", itemRepository);

        //create 2 ArrayList
        List<Item> list_item_1 = new ArrayList<>();
        List<Item> list_item_2 = new ArrayList<>();

        //create 2 items
        Item item1 = new Item();
        item1.setId(0L);
        item1.setName("gold ring");
        when(itemRepository.findById(0L)).thenReturn(Optional.of(item1));

        Item item2 = new Item();
        item1.setName("feather cap");

        //add item1 to list_item_1
        list_item_1.add(item1);
        when(itemRepository.findByName("gold ring")).thenReturn(list_item_1);

        //add item1 and item2 to list_item_2
        list_item_2.add(item1);
        list_item_2.add(item2);
        when(itemRepository.findAll()).thenReturn(list_item_2);
    }

    @Test
    public void getItemByIdTest(){
        ResponseEntity<Item> response = itemController.getItemById(0L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void getItemsByNameTest(){
        ResponseEntity<List<Item>> response = itemController.getItemsByName("gold ring");
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void getItemsTest(){
        ResponseEntity<List<Item>> response = itemController.getItems();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
    }
}