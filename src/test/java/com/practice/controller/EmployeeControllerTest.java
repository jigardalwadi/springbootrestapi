package com.practice.controller;

import com.practice.entity.Employee;
import com.practice.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EmployeeControllerTest {
    EmployeeController employeeController;
    EmployeeService employeeService;
    Employee employee;

    @Autowired
    private MockMvc mvc;

    @Before
    public void SetUp(){
        employeeService = Mockito.mock(EmployeeService.class);
        employee = new Employee(1l,"jigar","java","mockito",new Date());
        employeeController = new EmployeeController(employeeService);
    }

   @Test
    public void testCreateEmployee(){

        doReturn(employee).when(employeeService).save(employee);
        Employee employeeExpected = employeeController.createEmployee(employee);
        verify(employeeService,times(   1)).save(employee);
        assertEquals(employee,employeeExpected);
    }




    @Test
    public void testFind(){

        doReturn(employee).when(employeeService).findOne(employee.getId());
        Employee employee1 = employeeService.findOne(employee.getId());
        ResponseEntity<Employee> responseEntity;
        if(employee1==null) {
            responseEntity = ResponseEntity.notFound().build();
        }else {
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(employee);
        }
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        verify(employeeService).findOne(employee.getId());
        assertEquals(employee,employee1);

    }

    @Test
    public void testDelete(){
        ResponseEntity<Void> responseEntity = employeeController.deleteEmployee(employee.getId());
        assertThat(responseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.NOT_FOUND);

    }

    @Test
    public void testDeleteFailure(){
        when(employeeService.findOne(employee.getId())).thenReturn(null);
        ResponseEntity<Void> responseEntityFail = employeeController.deleteEmployee(employee.getId());
        verify(employeeService,times(0)).delete(employee);
        assertEquals(HttpStatus.NOT_FOUND.value(), responseEntityFail.getStatusCode().value());
    }


}
