package com.teamharambe.agris.aidll.Utils;


import android.graphics.Color;
import android.util.Log;

import com.teamharambe.agris.aidll.AIDLL;
import com.teamharambe.agris.aidll.Models.Car;
import com.teamharambe.agris.aidll.Models.Categories;
import com.teamharambe.agris.aidll.Models.Category;
import com.teamharambe.agris.aidll.Models.ModelWithId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Agris on 30.10.2016..
 */

public class JSonUtils {


    public static List<Category> toCategoryList(String input)
    {
        List<Category> categories = new ArrayList<>();
        try {
            JSONArray  jsonarray = new JSONArray(input);
            int itemCount = jsonarray.length();
            if (itemCount > 0 ) {
                for (int i = 0; i < itemCount; i++) {
                    JSONObject jsonCategory = jsonarray.getJSONObject(i);
                    Category c = new Category(jsonCategory.getString("name"), Integer.parseInt(jsonCategory.getString("weight")));
                    c.setMaximize(Boolean.parseBoolean(jsonCategory.getString("maximize")));
                    categories.add(c);
                }
            }
        } catch (Exception e) {
            Log.e("[EXCEPTION]", e.toString());
        }

        return categories;
    }


    public static JSONArray categoriesToJsonArray(List<Category> categories)
    {
        JSONArray jsonArray = new JSONArray();
        try {
            for (Category category : categories) {
                JSONObject categoryToAdd = new JSONObject();
                categoryToAdd.put("name", category.getName());
                categoryToAdd.put("weight", category.getWeight());
                categoryToAdd.put("maximize", category.isMaximize());
                jsonArray.put(categoryToAdd);
            }
        } catch (JSONException e)
        {
            e.printStackTrace();
        }

        return jsonArray;
    }


    public static List<String> toSelectedCarIdList(String input)
    {
        List<String> selectedCars = new ArrayList<>();
        try {
            JSONArray jsonarray = new JSONArray(input);
            int itemsCount = jsonarray.length();
            for (int i = 0 ; i<itemsCount; i++)
            {
                JSONObject data = jsonarray.getJSONObject(i);
                selectedCars.add(data.getString("id"));
            }
        }catch (Exception e) {
            Log.e("[EXCEPTION]", e.toString());
        }
        return selectedCars;
    }


//    public static List<Car> toCarList(String input)
//    {
//        List<Car> cars = new ArrayList<>();
//        try {
//            JSONArray  jsonarray = new JSONArray(input);
//            int itemCount = jsonarray.length();
//            if (itemCount > 0) {
//                for (int i = 0; i < itemCount; i++) {
//                    JSONObject jsonCar = jsonarray.getJSONObject(i);
//                    Car c = new Car(jsonCar.getString("name"));
////                    c.setId(Integer.parseInt(jsonCar.getString("id")));
////                    c.setName(jsonCar.getString("name"));
////                    c.setModelName(jsonCar.getString("modelName"));
////                    c.setPrice(Integer.parseInt(jsonCar.getString("price")));
////                    c.setYear(Integer.parseInt(jsonCar.getString("year")));
////                    c.setBodyType(jsonCar.getString("bodyType"));
////                    c.setDoors(Integer.parseInt(jsonCar.getString("doors")));
////                    c.setManual(jsonCar.getString("transmission").equals("Manual"));
////                    c.setWheelDrive(jsonCar.getString("wheelDrive"));
////                    c.setFuel(jsonCar.getString("fuel"));
////                    c.setFuelTank(jsonCar.getString("fuelTank"));
//                    cars.add(c);
//                }
//            }
//        } catch (Exception e) {
//            Log.e("[EXCEPTION]", e.toString());
//        }
//
//        return cars;
//    }

    public static List<Car> toCarListFromJson(String input)
    {
        List<Car> cars = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(input);
            JSONArray carsArray = jsonObject.getJSONArray("Cars");
            for (int i = 0; i < carsArray.length(); i++)
            {
                JSONObject manufacturer = carsArray.getJSONObject(i);
                JSONArray carModelsArray = manufacturer.getJSONArray("models");
                int itemCount = carModelsArray.length();
                if (itemCount > 0) {
                    for (int j = 0; j < itemCount; j++) {
                        JSONObject jsonCar = carModelsArray.getJSONObject(j);
                        Car c = new Car(manufacturer.getString("name"));
                        c.setModelName(new ModelWithId(jsonCar.getString("modelName"), jsonCar.getString("id")));
                        c.setPrice(Integer.parseInt(jsonCar.getString("price")));
                        c.setYear(Integer.parseInt(jsonCar.getString("year")));
                        c.setBodyType(jsonCar.getString("bodyType"));
                        c.setDoors(Integer.parseInt(jsonCar.getString("doors")));
                        c.setManual(jsonCar.getString("transmission").equals("Manual"));
                        c.setWheelDrive(jsonCar.getString("wheelDrive"));
                        c.setFuel(jsonCar.getString("fuel"));
                        c.setFuelTank(jsonCar.getString("fuelTank"));
                        c.setPower(jsonCar.getString("power"));
                        c.setTopSpeed(jsonCar.getString("topSpeed"));
                        c.setWeight(jsonCar.getString("weight"));
                        c.setMaxWeight(jsonCar.getString("maxWeight"));
                        c.setPictureLink(jsonCar.getString("image"));

                        //// TODO: 11.11.2016. string to color
//                        c.setColor(Color.tparseColor(jsonCar.getString("color")));

                        cars.add(c);
                    }
                }
            }

        } catch (Exception e) {
            Log.e("[EXCEPTION]", e.toString());
        }

        return cars;
    }

    public static Car getCarByID(String id)
    {
        String input = CacheManager.getCarsInCache(AIDLL.getAppContext());
        List<Car> cars = toCarListFromJson(input);
        for(int i = 0; i<cars.size(); i++)
        {
            if (cars.get(i).getModelName().id.equals(id))
            {
                return cars.get(i);
            }
        }
        Log.e("[Get Car]", "No car found by id: " + id);
        return null;
    }

//    public static JSONArray carsToJsonArray(List<Car> cars)
//    {
//        JSONArray jsonArray = new JSONArray();
//        try {
//            for (Car car : cars) {
//                JSONObject carToAdd = new JSONObject();
//                carToAdd.put("name", car.getName());
//                jsonArray.put(carToAdd);
//            }
//        } catch (JSONException e)
//        {
//            e.printStackTrace();
//        }
//
//        return jsonArray;
//    }
}
