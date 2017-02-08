package com.teamharambe.agris.aidll.FileManagement;


import android.content.Context;
import android.util.Log;

import com.teamharambe.agris.aidll.Models.Car;
import com.teamharambe.agris.aidll.Models.Cars;
import com.teamharambe.agris.aidll.Utils.CacheManager;
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

public class SelectedCars {

    static String filename = "selectedCars.json";


    public static void deleteAllSelectedCars(Context context)
    {
        File allCars = new File(context.getFilesDir(), filename);

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));
            outputStreamWriter.write("");
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("[Selected Cars]", "File write failed: " + e.toString());
        }


    }
    public static void addCarsToFile(Context context, String data)
    {
        List<String> identificators = JSonUtils.toSelectedCarIdList(Cars.getAllSelectedCarJson(context));

        File allCars = new File(context.getFilesDir(), filename);


//         this is used to add stuff
//        JSONArray items  = new JSONArray(getAllSelectedCarJson(context));
        JSONArray items = new JSONArray();
        for(int i = 0; i < identificators.size(); i++)
        {
            JSONObject carToAdd = new JSONObject();
            try {

                carToAdd.put("id", identificators.get(i));
            } catch (JSONException e)
            {
                Log.e("Selected Cars", e.toString());
            }

            items.put(carToAdd);
        }
        JSONObject carToAdd = new JSONObject();
        try {

            carToAdd.put("id", data);
        } catch (JSONException e)
        {
            Log.e("Selected Cars", e.toString());
        }

        items.put(carToAdd);



        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));
            outputStreamWriter.write(items.toString());
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("[Selected Cars]", "File write failed: " + e.toString());
        }

    }


    public static List<String> getSelectedCars(Context context)
    {
        return JSonUtils.toSelectedCarIdList(getSelectedCarsRaw(context));
    }

    public static String getSelectedCarsRaw(Context context)
    {
        String ret="";
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
            Log.e("[Selected cars]", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("[Selected cars]", "Can not read file: " + e.toString());
        }

        return ret;
    }
}
