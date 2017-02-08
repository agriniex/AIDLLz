package com.teamharambe.agris.aidll.Models;

import android.graphics.Color;

/**
 * Created by Agris on 02.11.2016..
 */

public class Car {
//    private int id;
    private String name;
    private ModelWithId modelName;
//    private String modelName;
    private int price;
    private int year;
    private String bodyType;
    private int doors;
    private boolean manual;
    private String wheelDrive;
    private String fuel;
    private String fuelTank;
    private String power;
    private String topSpeed;
    private String weight;
    private String maxWeight;
    private Color color;
    private String pictureLink;
    private int rank;

    public Car() {}

    public Car(String name){
        this.name = name;
    }

//    public int getId() { return  id; }

    public String getName() { return name; }

//    public String getModelName() {
//        return modelName;
//    }

        public ModelWithId getModelName() {
        return modelName;
    }

    public int getPrice() {
        return price;
    }

    public int getYear() {
        return year;
    }

    public String getBodyType() {
        return bodyType;
    }

    public int getDoors() {
        return doors;
    }

    public boolean isManual() {
        return manual;
    }

    public String getWheelDrive() {
        return wheelDrive;
    }

    public String getFuelTank() {
        return fuelTank;
    }

    public String getFuel() { return fuel;}

    public String getPower() {
        return power;
    }

    public String getTopSpeed() {
        return topSpeed;
    }

    public String getWeight() {
        return weight;
    }

    public String getMaxWeight() {
        return maxWeight;
    }

    public Color getColor() {
        return color;
    }

    public String getPictureLink() { return pictureLink; }

//    public void setId(int id) {
//        this.id = id;
//    }


    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setModelName(ModelWithId modelName) {

        this.modelName = modelName;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setDoors(int doors) {
        this.doors = doors;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public void setManual(boolean manual) {
        this.manual = manual;
    }

    public void setWheelDrive(String wheelDrive) {
        this.wheelDrive = wheelDrive;
    }

    public void setFuel(String fuel)
    {
        this.fuel = fuel;
    }

    public void setFuelTank(String fuelTank) {
        this.fuelTank = fuelTank;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public void setTopSpeed(String topSpeed) {
        this.topSpeed = topSpeed;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setMaxWeight(String maxWeight) {
        this.maxWeight = maxWeight;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setPictureLink(String pictureLink) { this.pictureLink = pictureLink; }



}

