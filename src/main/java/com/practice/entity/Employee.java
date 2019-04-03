package com.practice.entity;


import java.util.Date;

import javax.persistence.*;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name="Employees")
@EntityListeners(AuditingEntityListener.class)
public class Employee {



    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Column(name="name")
    private String name;

    @NotBlank
    @Column(name = "designation")
    private String designation;

    @NotBlank
    @Column(name ="expertise")
    private String expertise;


    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    @Column(name = "Date")
    private Date createdAt;


    public Employee() {
    }

    public Employee(Long id, String name, String designation, String expertise, Date createdAt) {
        this.id = id;
        this.name = name;
        this.designation = designation;
        this.expertise = expertise;
        this.createdAt = createdAt;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

}