package com.example.android.project_mc.Model;

public class UserData {
    private String user_Name;
    private String password;
    private String Image;
    private String adress;
    private String phone;
   public UserData(){

   }

    public UserData(String user_Name, String password, String image) {
        this.user_Name = user_Name;
        this.password = password;
        Image = image;
    }

    public UserData(String user_Name, String password, String image, String adress, String phone) {
        this.user_Name = user_Name;
        this.password = password;
        this.Image = image;
        this.adress = adress;
        this.phone = phone;
    }

    public String getUser_Name() {
        return user_Name;
    }
    public void setUser_Name(String user_Name) {
        this.user_Name = user_Name;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getAdress() {
        return adress;
    }



    public void setAdress(String adress) {
        this.adress = adress;

    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
