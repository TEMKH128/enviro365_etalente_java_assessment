package com.enviro.assessment.grade001.tebogomkhize.investments.dataTransferObjects;

import java.time.LocalDate;

public class NoticeStatementRequest {
    private String productID;
    private LocalDate startDate;
    private LocalDate endDate;

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
