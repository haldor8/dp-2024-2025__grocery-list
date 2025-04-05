package com.fges.data_structures;

public class Item {
    private String name;
    private int quantity;
    private String category;

    public Item() {
        // nécessaire pour Jackson
    }

    public Item(String name, int quantity, String category) {
        this.name = name;
        this.quantity = quantity;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void addQuantity(int quantity) { this.quantity += quantity; }
}
