package com.practice.service;


import com.practice.entity.Employee;
import com.practice.repositary.EmployeeRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;


@Service
public class EmployeeService {

    EmployeeRepository employeeRepository;
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }



    /*to save an employee*/

    public Employee save(Employee emp) {
        return employeeRepository.save(emp);
    }


    /* search all employees*/

    public List<Employee> findAll(){
        return employeeRepository.findAll();
    }


    /*get an employee by id*/
    public Employee findOne(Long empid) {
        Optional<S> one = employeeRepository.findOne(empid);
        return one;
    }


    /*delete an employee*/

    public void delete(Employee emp) {
        employeeRepository.delete(emp);
    }


}