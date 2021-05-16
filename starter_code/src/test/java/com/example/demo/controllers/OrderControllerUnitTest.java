package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerUnitTest {
    private OrderController orderController;
    private OrderRepository orderRepo = mock(OrderRepository.class);
    private UserRepository userRepo = mock(UserRepository.class);

    private User user = new User();
    private Item item = new Item();
    private Cart cart = new Cart();

    List<Item> list_item = new ArrayList<Item>();

    @Before
    public void init(){
        orderController = new OrderController();
        TestUtils.injectObject(orderController, "orderRepository", orderRepo);
        TestUtils.injectObject(orderController, "userRepository", userRepo);

        user.setId(0L);
        user.setUsername("arpit");
        user.setPassword("arpit123");
        when(userRepo.findByUsername("arpit")).thenReturn(user);

        item.setId(0L);
        item.setPrice(BigDecimal.valueOf(999.9));
        item.setName("gold_ring");
        item.setDescription("Gold Ring Description");
        list_item.add(item);

        cart.setId(0L);
        cart.setItems(list_item);
        cart.setUser(user);
        cart.setTotal(BigDecimal.valueOf(999.9));
        user.setCart(cart);
    }

    @Test
    public void submitTest(){
        ResponseEntity<UserOrder> response = orderController.submit("arpit");
        assertEquals(BigDecimal.valueOf(999.9), response.getBody().getTotal());
        assertEquals(1, response.getBody().getItems().size());
        assertEquals(null, response.getBody().getId());
    }

    @Test
    public void submitTestForNonExistingUser(){
        ResponseEntity<UserOrder> response = orderController.submit("randomUser");
        assertEquals("404 NOT_FOUND", response.getStatusCode().toString());
    }
}