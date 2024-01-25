package com.enviro.assessment.grade001.tebogomkhize.investments.domain;

import java.time.LocalDate;
import jakarta.persistence.*;


@Entity
@Table(name="transactions")
public class Transaction {
    @Id  // identifies primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // auto-increment columns.
    private Long transactionID;
    private String type;

    @ManyToOne
    @PrimaryKeyJoinColumn
    private Product product;
    private LocalDate date;
    private float amount;

    public Transaction(){}

    public Transaction(String type, Long transactionID, Product product,
       LocalDate date, float amount) {

        this.type = type;
        this.transactionID = transactionID;
        this.product = product;
        this.date = date;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(Long transactionID) {
        this.transactionID = transactionID;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionID=" + transactionID +
                ", type='" + type + '\'' +
                ", product=" + product +
                ", date='" + date + '\'' +
                ", amount=" + amount +
                '}';
    }
}