package com.teamharambe.agris.aidll.Models;

import android.content.Context;
import android.util.Log;

import com.teamharambe.agris.aidll.AIDLL;
import com.teamharambe.agris.aidll.Utils.JSonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class Cars {

    private static final String TAG = "[Cars]";
    static String filename = "selectedCars.json";
//    File selectedCars;

//    public Cars() {
//        Log.i(TAG, "Creating file: " + filename);
//        selectedCars = new File(AIDLL.getAppContext().getFilesDir(), filename);
//    }

    public static void addCar(String id, Context context)
    {
        JSONArray items = new JSONArray();
        File selectedCars = new File(context.getFilesDir(), filename);
        try {
            JSONObject carToAdd = new JSONObject();
            carToAdd.put("id", id);

            items  = new JSONArray(getAllSelectedCarJson(context));
            items.put(carToAdd);

        }

            catch (JSONException jse){
                Log.e("[JSON EXCEPTION]", jse.toString());
            }
        writeInFile(items, context);
    }

    public static void updateCar(Context context, int position, String newValue)
    {
        List<String> cars = getAllSelectedCarIdList(context);
        cars.set(position, newValue);

        JSONArray items = new JSONArray();

        for (int i = 0; i < cars.size(); i++)
        {
            try {
                JSONObject carToAdd = new JSONObject();
                carToAdd.put("id", cars.get(i));

                items.put(carToAdd);

            } catch (JSONException jse){
                Log.e("[JSON EXCEPTION]", jse.toString());
            }

        }
        writeInFile(items, context);
    }

    public static void deleteCar(Context context, int position)
    {
        List<String> cars = getAllSelectedCarIdList(context);
        cars.remove(position);

        JSONArray items = new JSONArray();

        for (int i = 0; i < cars.size(); i++)
        {
            try {
                JSONObject carToAdd = new JSONObject();
                carToAdd.put("id", cars.get(i));

                items.put(carToAdd);

            } catch (JSONException jse){
                Log.e("[JSON EXCEPTION]", jse.toString());
            }

        }
        writeInFile(items, context);


    }

//    public static void addCar(String id, Context context)
//    {
//        try {
//            JSONObject carToAdd = new JSONObject();
//            carToAdd.put("id", id);
//
//            List<Car> cars = getAllSelectedCars(AIDLL.getAppContext());
//
//            JSONArray items = JSonUtils.carsToJsonArray(cars);
//            items.put(carToAdd);
//
//            Log.i(TAG, "trying to add record");
//            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));
//            outputStreamWriter.write(items.toString());
//            outputStreamWriter.close();
//        }
//        catch (IOException e) {
//            Log.e("[Exception]", "File write failed: " + e.toString());
//        }
//        catch (JSONException jse){
//            Log.e("[JSON EXCEPTION]", jse.toString());
//        }
//    }


    public static void delete(Context context)
    {
        Log.i(TAG, "deleting file: " + filename);
        context.deleteFile(filename);
    }

    public static String getAllSelectedCarJson(Context context)
    {
        File selectedCars = new File(context.getFilesDir(), filename);
        String ret = "";

        Log.i(TAG, "Fetching car Id's");
        try {
            InputStream inputStream = context.openFileInput(filename);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }
                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e(TAG, "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e(TAG, "Can not read file: " + e.toString());
        }

        Log.i("[JSon response]" , ret);
        return ret;
    }

    public static List<String> getAllSelectedCarIdList(Context context)
    {
        return JSonUtils.toSelectedCarIdList(getAllSelectedCarJson(context));
    }

    public static List<Car> getAllSelectedCars(Context context)
    {
        List<String> identificators = getAllSelectedCarIdList(context);
        List<Car> cars = new ArrayList<>();
        for( int i = 0; i< identificators.size(); i++)
        {
            cars.add(JSonUtils.getCarByID(identificators.get(i)));
        }

        return cars;

    }

    public static List<Car> removeSelectedCars(Context context, List<Car> cars)
    {
        List<String> identificators = getAllSelectedCarIdList(context);
        for(int i = 0; i < identificators.size(); i++)
        {
            for(int j = 0; j < cars.size(); j++)
            {
                if (identificators.get(i).equals(cars.get(j).getModelName().id))
                {
                    cars.remove(j);
                }
            }
        }
        return cars;
    }

    private static void writeInFile(JSONArray items, Context context) {

        try {
            Log.i(TAG, "trying to add record");
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));
            outputStreamWriter.write(items.toString());
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("[Exception]", "File write failed: " + e.toString());
        }
    }

}
