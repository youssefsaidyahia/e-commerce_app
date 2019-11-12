package com.example.android.project_mc.Model;

public class Products {
    String ProducName , ProductDescription ,ProductSalary,ProducImage,ProductcurrentData ,ProductcurrentTime,ProductCategory ,productRandomKey , productstate;

    public Products() {
    }

    public Products(String producName, String productDescription, String productSalary, String producImage, String productcurrentData, String productcurrentTime, String productCategory, String productRandomKey, String productstate) {
        ProducName = producName;
        ProductDescription = productDescription;
        ProductSalary = productSalary;
        ProducImage = producImage;
        ProductcurrentData = productcurrentData;
        ProductcurrentTime = productcurrentTime;
        ProductCategory = productCategory;
        this.productRandomKey = productRandomKey;
        this.productstate = productstate;
    }

    public String getProducName() {
        return ProducName;
    }

    public void setProducName(String producName) {
        ProducName = producName;
    }

    public String getProductDescription() {
        return ProductDescription;
    }

    public void setProductDescription(String productDescription) {
        ProductDescription = productDescription;
    }

    public String getProductSalary() {
        return ProductSalary;
    }

    public void setProductSalary(String productSalary) {
        ProductSalary = productSalary;
    }

    public String getProducImage() {
        return ProducImage;
    }

    public void setProducImage(String producImage) {
        ProducImage = producImage;
    }

    public String getProductcurrentData() {
        return ProductcurrentData;
    }

    public void setProductcurrentData(String productcurrentData) {
        ProductcurrentData = productcurrentData;
    }

    public String getProductcurrentTime() {
        return ProductcurrentTime;
    }

    public void setProductcurrentTime(String productcurrentTime) {
        ProductcurrentTime = productcurrentTime;
    }

    public String getProductCategory() {
        return ProductCategory;
    }

    public void setProductCategory(String productCategory) {
        ProductCategory = productCategory;
    }

    public String getProductRandomKey() {
        return productRandomKey;
    }

    public void setProductRandomKey(String productRandomKey) {
        this.productRandomKey = productRandomKey;
    }

    public String getProductstate() {
        return productstate;
    }

    public void setProductstate(String productstate) {
        this.productstate = productstate;
    }
}
