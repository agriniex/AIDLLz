package com.teamharambe.agris.aidll.Models;


public class Category {

    private String name;
    private int weight;
    private boolean maximize;
    private String value;


    public Category(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }

    public Category()
    {
        this.name = "TestCategory";
        this.weight = 50;
    }

    public void setMaximize(boolean maximize) {
        this.maximize = maximize;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public String getName() { return name; }
    public int getWeight() {return weight; }
    public boolean isMaximize() { return maximize; }
    public String getValue() { return value; }

}
