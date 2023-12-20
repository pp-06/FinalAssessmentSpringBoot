package com.example.springbootecommerce.controller;

import com.example.springbootecommerce.model.Category;
import com.example.springbootecommerce.SpringBootEcommerceApplicationTests;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringBootEcommerceApplicationTests.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CategoryControllerTest {
    // For mocking the web layer
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext categoryContext;
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(categoryContext).build();
    }

    public static String asJson(final Object object) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            final String jsonContent = objectMapper.writeValueAsString(object);
            System.out.println(jsonContent);
            return jsonContent;
        }catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Test
    public void verifyGetCategoryById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/category/getById/65827a487737292d7342243b")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect((jsonPath("$.id")).value("65827a487737292d7342243b"))
                .andDo(print());
    }

    @Test
    public void verfiySaveCategory_EXCEPTION() throws Exception{
        Category category = new Category(new ObjectId("65827a487737292d7342243b"), "Mobile", "phones");

        mockMvc.perform(MockMvcRequestBuilders.post("/category/add-category")
                        .content(asJson(category))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.errorCode").value(400))
                .andExpect(jsonPath("$.message").value("PAYLOAD MALFORMED. OBJECT ID MUST NOT BE DEFINED"))
                .andDo(print());
    }



    @Test
    public void verifyUpdateCategory() throws Exception{
        Category category = new Category(new ObjectId("65827a487737292d7342243b"), "mobile", "phones");

        mockMvc.perform(MockMvcRequestBuilders.post("/category/update")
                        .content(asJson(category))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").value("65827a487737292d7342243b"))
                .andExpect(jsonPath("$.name").value("mobile"))
                .andExpect(jsonPath("$.description").value("phones"))
                .andDo(print());
    }

    @Test
    public void verifyUpdateCategory_EXCEPTION() throws Exception{
        Category category = new Category(new ObjectId("65803515f431076e8e80b089"), "LenovoK8", "keyboard");

        mockMvc.perform(MockMvcRequestBuilders.post("/category/update")
                        .content(asJson(category))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.errorCode").value(404))
                .andExpect(jsonPath("$.message").value("Category DOESN'T EXISTS"))
                .andDo(print());
    }



    @Test
    public void verifyDeleteById() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.post("/category/delete/65803515f431076e8e80b089")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Successfully Deleted !!"))
                .andDo(print());
    }

    @Test
    public void verifyDeleteById_EXCEPTION() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.post("/category/delete/65803515f431076e8e80b089")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorCode").value(404))
                .andExpect(jsonPath("$.message").value("Category DOESN'T Exists"))
                .andDo(print());
    }
}

