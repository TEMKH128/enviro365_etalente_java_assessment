package com.enviro.assessment.grade001.tebogomkhize.investments.domain;

import java.util.List;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="products")
public class Product {
    @Id  // identifies primary key
    private String productID;
    private String name;

    @JsonIgnore
    @ManyToOne
    @PrimaryKeyJoinColumn  // identifies foreign key.
    private Investor investor;
    private String type;
    private float balance;

    @JsonIgnore  // prevents circular dependency.
    @OneToMany(mappedBy="product")
    private List<Transaction> transactions;

    public Product(){}

    public Product(String name, String productID, Investor investor,
    String type, float balance) {

        this.name = name;
        this.productID = productID;
        this.investor = investor;
        this.type = type;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public Investor getInvestor() {return investor;}

    public void setInvestor(Investor investor) {this.investor = investor;}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactions() {return this.transactions;}

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productID='" + productID + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", balance=" + balance +
                '}';
    }
}
