package com.swich.carlo;

import java.io.Serializable;

public class MyListData implements Serializable {
    String id;
    String company;
    String model;
    String Description;
    String ownerID;
    String path;
    String price;

    public MyListData(String id, String company, String model, String description, String ownerID, String path, String price) {
        this.id = id;
        this.company = company;
        this.model = model;
        Description = description;
        this.ownerID = ownerID;
        this.path = path;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


}
