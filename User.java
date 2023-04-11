package com.digdes.school;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
    private Long id;
    private String lastName;
    private Double cost;
    private Long age;
    private Boolean active;

    private Boolean updateId = false;
    private Boolean updateLastName = false;
    private Boolean updateCost = false;
    private Boolean updateAge = false;
    private Boolean updateActive = false;


    public User(){

    }



    public Boolean getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Boolean updateId) {
        this.updateId = updateId;
    }

    public Boolean getUpdateLastName() {
        return updateLastName;
    }

    public void setUpdateLastName(Boolean updateLastName) {
        this.updateLastName = updateLastName;
    }

    public Boolean getUpdateCost() {
        return updateCost;
    }

    public void setUpdateCost(Boolean updateCost) {
        this.updateCost = updateCost;
    }

    public Boolean getUpdateAge() {
        return updateAge;
    }

    public void setUpdateAge(Boolean updateAge) {
        this.updateAge = updateAge;
    }

    public Boolean getUpdateActive() {
        return updateActive;
    }

    public void setUpdateActive(Boolean updateActive) {
        this.updateActive = updateActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", cost=" + cost +
                ", age=" + age +
                ", active=" + active +
                '}';
    }
}
