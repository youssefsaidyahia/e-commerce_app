package com.example.android.project_mc.Model;

public class Orders {

    String Address, Date, Email, Name, Number, State, Time ,UserName ,Type;
    Long Total_Price ;

    public Orders() {
    }

    public Orders(String address, String date, String email, String name, String number, String state, String time, String userName, String type, Long total_Price) {
        Address = address;
        Date = date;
        Email = email;
        Name = name;
        Number = number;
        State = state;
        Time = time;
        UserName = userName;
        Type = type;
        Total_Price = total_Price;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public Long getTotal_Price() {
        return Total_Price;
    }

    public void setTotal_Price(Long total_Price) {
        Total_Price = total_Price;
    }
}