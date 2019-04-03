package com.practice.controller;


import com.practice.entity.Employee;
import com.practice.service.EmployeeService;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/company")
public class EmployeeController {


    EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/employees")
    public Employee createEmployee(@Valid @RequestBody Employee emp) {
        return employeeService.save(emp);
    }


    @GetMapping("/employees")
    public List<Employee> getAllEmployees(){
        return employeeService.findAll();
    }

    /*get employee by empid*/
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(value="id") Long empid){

        Employee emp= employeeService.findOne(empid);

        if(emp==null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(emp);

    }


    /*update an employee by empid*/
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value="id") Long empid,@Valid @RequestBody Employee empDetails){

        Employee emp= employeeService.findOne(empid);
        if(emp==null) {
            return ResponseEntity.notFound().build();
        }

        emp.setName(empDetails.getName());
        emp.setDesignation(empDetails.getDesignation());
        emp.setExpertise(empDetails.getExpertise());

        Employee updateEmployee= employeeService.save(emp);
        return ResponseEntity.ok().body(updateEmployee);



    }

    /*Delete an employee*/
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable(value="id") Long empid) throws NullPointerException{

        Employee emp= employeeService.findOne(empid);
        if(emp==null) {
            return ResponseEntity.notFound().build();
        }
        employeeService.delete(emp);

        return ResponseEntity.ok().build();


    }



}
