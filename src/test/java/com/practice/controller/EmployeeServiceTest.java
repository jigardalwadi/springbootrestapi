package com.practice.controller;

import com.practice.service.EmployeeService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.practice.entity.Employee;
import com.practice.repositary.EmployeeRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;



public class EmployeeServiceTest {

   /* @Mock
    EmployeeRepository employeeRepository;
*/

    EmployeeRepository employeeRepository;
    EmployeeService employeeService;

   @Before
   public void setUP(){
       employeeRepository = Mockito.mock(EmployeeRepository.class);
       employeeService = new EmployeeService(employeeRepository);
   }


    @Test
    public void testSave() {

        Employee employee = new Employee(1l, "mock", "ceo", "nothing", new Date());
        Employee expectedEmployee = new Employee();

        Mockito.when(employeeRepository.save(employee)).thenReturn(employee);

        Employee saved =employeeService.save(employee);
        verify(employeeRepository, times(1)).save(employee);
        Assert.assertEquals(employee,saved);
    }


    @Test
    public void testfindAll(){
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        Employee e = new Employee(1l, "mock", "ceo", "nothing", new Date());
        Employee e1 = new Employee(2l, "junit", "ceo", "nothing", new Date());
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        List<Employee> employeeList = new ArrayList<Employee>();
        employeeList.add(e);
        employeeList.add(e1);
        Mockito.when(employeeRepository.findAll()).thenReturn(employeeList);

        List<Employee> employees = employeeService.findAll();
        Assert.assertEquals(employeeList, employees);
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    public void testfindOne(){
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        Employee e = new Employee(1l, "pappu", "ceo", "nothing", new Date());
        EmployeeService employeeService = new EmployeeService(employeeRepository);

        Mockito.when(employeeRepository.findOne(e.getId())).thenReturn(e);
        Employee employee = employeeService.findOne(e.getId());
        Assert.assertEquals(employee,e);
    }


    @Test
    public void testDelete(){
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        Employee employee = new Employee(1l, "pappu", "ceo", "nothing", new Date());
        verify(employeeRepository, times(1)).delete(employee);

        Assert.assertNull(employeeService.findOne(employee.getId()));

    }


}









/*

  EmployeeService spy = Mockito.spy(employeeService);
        EmployeeController c  = new EmployeeController(spy);
        Employee employee = c.createEmployee(e);
        verify(spy, times(1)).save(e);*/
