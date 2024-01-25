package com.enviro.assessment.grade001.tebogomkhize.investments.dataTransferObjects;

public class WithdrawRequest {
    private String productID;
    private float amount;

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
