package com.example.springbestellsystem;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ShopService shopService;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ProductRepo productRepo;

    @Test
    @DirtiesContext
    void expectPineapple_WhenGetProductById2() throws Exception {
        //Given


        //When
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/shop/products/2")
    )

        //Then
                .andExpect(
                        MockMvcResultMatchers.content().json("""
                {"id": "2",
                "name": "Pineapple"
                }
                """
                ));
    }


    @Test
    @DirtiesContext
    void listAllProductsTest() throws Exception {
        //When
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/shop/products")
                )

                //Then
                .andExpect(
                        MockMvcResultMatchers.content().json("""
                [
                    {
                        "id": "1",
                        "name": "Banana"
                    },
                    {
                        "id": "2",
                        "name": "Pineapple"
                    }
                ]
                """
                        ));
    }
   // @DeleteMapping("/products/{id}")
   // public void deleteProducts(@PathVariable String id) {
    //    this.shopService.deleteProduct(id);
    //}


    @Test
    @DirtiesContext
    void deleteProductsTest() throws Exception {
        //When
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/shop/products/1")
                        .content("1"))
                        .andExpect(status().isOk());
        //Then
        List<Product> productList = productRepo.listProductList();
        Assertions.assertThat(productList)
                .doesNotContain(new Product("1", "Banana"));
    }




    @Test
    @DirtiesContext
    void addOrderTest() throws Exception {
        //When
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/shop")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        ["1"]
                        """)).andExpect(status().isOk());

                        //then
                        List<Order> orders = orderRepo.listAllOrders();
                        Assertions.assertThat(orders)
                                .containsExactly(
                                        new Order(
                                                "33", List.of(new Product(
                                                "1", "Banana"))
                                ));
    }

    @PutMapping("/update/order/{id}")
    public Order updateOrder(@PathVariable String id, @RequestBody Order order) {
        if (order.getId().equals(id)) {
            return shopService.updateOrder(order);
        }
        return null;
    }

    @Test
    @DirtiesContext
    void updateOrderTest() throws Exception {
        //when
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/shop/update/order/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "id": "3",
                                    "orderList": [
                                        {
                                            "id": "1",
                                            "name": "Banana"
                                        }
                                    ]
                                }
                                """)

        ).andExpect(status().isOk());
        //then
        List<Order> orders = orderRepo.listAllOrders();
        Assertions.assertThat(orders)
                .containsExactly(
                        new Order(
                                "3", List.of(new Product(
                                "1", "Banana"))
                        ));
    }
}

