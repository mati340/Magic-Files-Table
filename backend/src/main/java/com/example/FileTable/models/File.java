package com.example.FileTable.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class File {

    private String docType;
    private Integer companyId;
    private Date date;
    private Integer docId;
    private String sign;
    private Integer amount;

    public File(String docType,
                Integer companyId,
                Date date,
                Integer docId,
                String sign,
                Integer amount) {
        this.docType = docType;
        this.companyId = companyId;
        this.date = date;
        this.docId = docId;
        this.sign = sign;
        this.amount = amount;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public Integer getDocId() {
        return docId;
    }

    public void setDocId(Integer docId) {
        this.docId = docId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
