package com.example.priyanka.todolistapp;

import java.io.Serializable;

/**
 * Created by PRIYANKA on 02-07-2017.
 */

public class Expense implements Serializable {
    public int id;
    String title;
    double price;
    String category;
    long epoch;

    public Expense(int id,String title, double price, String category,long epoch) {
        this.title = title;
        this.id=id;
        this.price = price;
        this.category = category;
        this.epoch=epoch;
    }

}
