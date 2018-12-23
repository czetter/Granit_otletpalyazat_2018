package com.example.czettergbor.nagyhf_mobweb.data;

import java.util.Date;

public class SpentItem {
    String name;
    int price;
    boolean income;
    Date date;

    public SpentItem(String n,int p, boolean i, Date d) {
        name = n;
        price = p;
        income = i;
        date = d;
    }

    public boolean isIncome() {
        return income;
    }

    public Date getDate() {
        return date;
    }

    public int getPrice() {
        return price;
    }

    public String getItemName() {
        return name;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
