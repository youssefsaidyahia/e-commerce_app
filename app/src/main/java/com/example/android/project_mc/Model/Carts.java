package com.example.android.project_mc.Model;

public class Carts {
    String pid , pName ,pSalary ,discount,quantity;

     public  Carts(){}
    public Carts(String pid, String pName, String pSalary, String discount, String quantity) {
        this.pid = pid;
        this.pName = pName;
        this.pSalary = pSalary;
        this.discount = discount;
        this.quantity = quantity;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpSalary() {
        return pSalary;
    }

    public void setpSalary(String pSalary) {
        this.pSalary = pSalary;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
