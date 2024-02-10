package com.marksapplication.app;

import com.google.firebase.Timestamp;

public class Items {

    private String quantity = "1";

    private String id;
    private Timestamp DateTime;
    private String ProductName;
    private String ProductQuantity;
    private String ProductBuyingPrice;
    private String ProductCode;
    private String ProductCategory;
    private String ProductImage;
    private String ProductSellingPrice;
    private String imageUri;

    public Items() {
    }

    public Items(String Quantity,String Id,Timestamp dateTime, String productName, String productQuantity, String productBuyingPrice, String productCode, String productCategory, String productImage, String productSellingPrice, String imageUri) {
        quantity = Quantity;
        id = Id;
        DateTime = dateTime;
        ProductName = productName;
        ProductQuantity = productQuantity;
        ProductBuyingPrice = productBuyingPrice;
        ProductCode = productCode;
        ProductCategory = productCategory;
        ProductImage = productImage;
        ProductSellingPrice = productSellingPrice;
        this.imageUri = imageUri;
    }
    public String getQuantity() {
        return quantity;
    }
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getDateTime() {
        return DateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        DateTime = dateTime;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductQuantity() {
        return ProductQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        ProductQuantity = productQuantity;
    }

    public String getProductBuyingPrice() {
        return ProductBuyingPrice;
    }

    public void setProductBuyingPrice(String productBuyingPrice) {
        ProductBuyingPrice = productBuyingPrice;
    }

    public String getProductCode() {
        return ProductCode;
    }

    public void setProductCode(String productCode) {
        ProductCode = productCode;
    }

    public String getProductCategory() {
        return ProductCategory;
    }

    public void setProductCategory(String productCategory) {
        ProductCategory = productCategory;
    }

    public String getProductImage() {
        return ProductImage;
    }

    public void setProductImage(String productImage) {
        ProductImage = productImage;
    }

    public String getProductSellingPrice() {
        return ProductSellingPrice;
    }

    public void setProductSellingPrice(String productSellingPrice) {
        ProductSellingPrice = productSellingPrice;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}