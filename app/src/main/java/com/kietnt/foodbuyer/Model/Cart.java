package com.kietnt.foodbuyer.Model;

public class Cart {
    private String id_food;
    private String nameFood;
    private double priceFood;
    private int Amount;
    private String email_buyer;

    public Cart() {
    }

    public Cart(String id_food, String nameFood, double priceFood, int amount, String email_buyer) {
        this.id_food = id_food;
        this.nameFood = nameFood;
        this.priceFood = priceFood;
        Amount = amount;
        this.email_buyer = email_buyer;
    }

    public String getId_food() {
        return id_food;
    }

    public void setId_food(String id_food) {
        this.id_food = id_food;
    }

    public String getNameFood() {
        return nameFood;
    }

    public void setNameFood(String nameFood) {
        this.nameFood = nameFood;
    }

    public double getPriceFood() {
        return priceFood;
    }

    public void setPriceFood(double priceFood) {
        this.priceFood = priceFood;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public String getEmail_buyer() {
        return email_buyer;
    }

    public void setEmail_buyer(String email_buyer) {
        this.email_buyer = email_buyer;
    }
}
