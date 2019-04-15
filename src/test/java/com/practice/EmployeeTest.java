
package com.practice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.entity.Employee;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = EmployeeApplication.class)
@WebAppConfiguration
@ActiveProfiles
public class EmployeeTest {

    private MockMvc mvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetAll() throws Exception {

        MvcResult mvcResult =
                mvc.perform(MockMvcRequestBuilders
                        .get("/company/employees/{id}", 3)
                        .accept(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id", is(3)))
                        .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);


    }
    @Test
    public void deleteEmployeeAPI() throws Exception
    {
        mvc.perform( MockMvcRequestBuilders.delete("company/employees/{id}", 3l) )
        ;
    }


    @Test
    public void testAddEmployee() throws Exception {
        Employee employee = new Employee(3l, "jigar", "java", "mockito", new Date());

        MvcResult mvcResult =
                mvc.perform(MockMvcRequestBuilders
                        .post("/company/employee/")
                        .content(asJsonString(employee))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andReturn();
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }}
