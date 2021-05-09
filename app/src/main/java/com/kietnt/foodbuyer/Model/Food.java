package com.kietnt.foodbuyer.Model;

public class Food {

    private String food_id;
    private String FoodName;
    private String FoodImage;
    private int soLuong;
    private double Gia;
    private String NSX;
    private String HSD;
    private String categories_id;

    public Food() {
    }

    public Food(String food_id, String foodName, String foodImage, int soLuong, double gia, String NSX, String HSD, String categories_id) {
        this.food_id = food_id;
        FoodName = foodName;
        FoodImage = foodImage;
        this.soLuong = soLuong;
        Gia = gia;
        this.NSX = NSX;
        this.HSD = HSD;
        this.categories_id = categories_id;
    }


    public String getFood_id() {
        return food_id;
    }

    public void setFood_id(String food_id) {
        this.food_id = food_id;
    }

    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        FoodName = foodName;
    }

    public String getFoodImage() {
        return FoodImage;
    }

    public void setFoodImage(String foodImage) {
        FoodImage = foodImage;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getGia() {
        return Gia;
    }

    public void setGia(double gia) {
        Gia = gia;
    }

    public String getNSX() {
        return NSX;
    }

    public void setNSX(String NSX) {
        this.NSX = NSX;
    }

    public String getHSD() {
        return HSD;
    }

    public void setHSD(String HSD) {
        this.HSD = HSD;
    }

    public String getCategories_id() {
        return categories_id;
    }

    public void setCategories_id(String categories_id) {
        this.categories_id = categories_id;
    }

}
