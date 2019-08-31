package com.example.sportlightsales;

import java.io.Serializable;

public class FaqModel implements Serializable {
    private int faqId;
    private String faqName;
    private int faqType;
    private int faqParentId;
    private String faqValue;

    public int getFaqId() {
        return faqId;
    }

    public void setFaqId(int faqId) {
        this.faqId = faqId;
    }

    public String getFaqName() {
        return faqName;
    }

    public void setFaqName(String faqName) {
        this.faqName = faqName;
    }

    public int getFaqType() {
        return faqType;
    }

    public void setFaqType(int faqType) {
        this.faqType = faqType;
    }

    public int getFaqParentId() {
        return faqParentId;
    }

    public void setFaqParentId(int faqParentId) {
        this.faqParentId = faqParentId;
    }

    public String getFaqValue() {
        return faqValue;
    }

    public void setFaqValue(String faqValue) {
        this.faqValue = faqValue;
    }
}
