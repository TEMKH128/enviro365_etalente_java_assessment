package com.enviro.assessment.grade001.tebogomkhize.investments.domain;

import java.util.List;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="investors")
public class Investor {
    @Id  // identifies primary key
    private String investorID;
    private String name;
    private String address;
    private String contact;
    private String age;

    @JsonIgnore  // prevents circular dependency.
    @OneToMany(mappedBy="investor")
    private List<Product> products;

    public Investor(){}

    public Investor(String name, String investorID, String address,
        String contact, String age) {

        this.name = name;
        this.investorID = investorID;
        this.address = address;
        this.contact = contact;
    }

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getAge() {return this.age;}

    public void setAge(String age) {this.age = age;}

    public String getInvestorID() {return this.investorID;}

    public void setInvestorID(String investorID) {
        this.investorID = investorID;
    }

    public String getAddress() {return address;}

    public void setAddress(String address) {this.address = address;}

    public String getContact() {return contact;}

    public void setContact(String contact) {this.contact = contact;}

    public List<Product> getProducts() {return this.products;}

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Investor{" +
                "investorID='" + investorID + '\'' +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                ", products=" + products +
                '}';
    }
}
