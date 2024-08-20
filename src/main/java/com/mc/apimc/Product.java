package com.mc.apimc;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Product {
    private String id;
    private String title;
    private String subtitle;
    private String categoryId;
    private double price;
    private String currencyId;
    private int availableQuantity;
    private int soldQuantity;
    private String buyingMode;
    private String condition;
    private String listingTypeId;
    private String startTime;
    private String endTime;
    private String description;
    private String warranty;
    private String catalogProductId;
    private JsonArray pictures;
    private JsonObject shippingInfo;

    public Product() {
    }

    public Product(String id, String subtitle, String title, String currencyId, String categoryId, double price, int availableQuantity, int soldQuantity, String buyingMode, String condition, String listingTypeId, String startTime, String endTime, String description, String warranty, String catalogProductId, JsonArray pictures, JsonObject shippingInfo) {
        this.id = id;
        this.subtitle = subtitle;
        this.title = title;
        this.currencyId = currencyId;
        this.categoryId = categoryId;
        this.price = price;
        this.availableQuantity = availableQuantity;
        this.soldQuantity = soldQuantity;
        this.buyingMode = buyingMode;
        this.condition = condition;
        this.listingTypeId = listingTypeId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.warranty = warranty;
        this.catalogProductId = catalogProductId;
        this.pictures = pictures;
        this.shippingInfo = shippingInfo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(int soldQuantity) {
        this.soldQuantity = soldQuantity;
    }

    public String getBuyingMode() {
        return buyingMode;
    }

    public void setBuyingMode(String buyingMode) {
        this.buyingMode = buyingMode;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getListingTypeId() {
        return listingTypeId;
    }

    public void setListingTypeId(String listingTypeId) {
        this.listingTypeId = listingTypeId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public String getCatalogProductId() {
        return catalogProductId;
    }

    public void setCatalogProductId(String catalogProductId) {
        this.catalogProductId = catalogProductId;
    }

    public JsonArray getPictures() {
        return pictures;
    }

    public void setPictures(JsonArray pictures) {
        this.pictures = pictures;
    }

    public JsonObject getShippingInfo() {
        return shippingInfo;
    }

    public void setShippingInfo(JsonObject shippingInfo) {
        this.shippingInfo = shippingInfo;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\n' +
                ", title='" + title + '\n' +
                ", subtitle='" + subtitle + '\n' +
                ", categoryId='" + categoryId + '\n' +
                ", price=" + price + '\n' +
                ", currencyId='" + currencyId + '\n' +
                ", availableQuantity=" + availableQuantity + '\n' +
                ", soldQuantity=" + soldQuantity + '\n' +
                ", buyingMode='" + buyingMode + '\n' +
                ", condition='" + condition + '\n' +
                ", listingTypeId='" + listingTypeId + '\n' +
                ", startTime='" + startTime + '\n' +
                ", endTime='" + endTime + '\n' +
                ", description='" + description + '\n' +
                ", warranty='" + warranty + '\n' +
                ", catalogProductId='" + catalogProductId + '\n' +
                ", pictures=" + pictures + '\n' +
                ", shippingInfo=" + shippingInfo + '\n' +
                '}' + '\n' +
                "-------------------------------------------------------------------------------------------------";
    }
}
