package com.teamharambe.agris.aidll.FileManagement;

import android.content.Context;
import android.util.Log;

import com.teamharambe.agris.aidll.Models.Cars;
import com.teamharambe.agris.aidll.Models.Category;
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
import java.util.List;

/**
 * Created by Agris on 17.11.2016..
 */

public class SelectedCategories {


    static String filename = "selectedCategories.json";




    public static void addCategoryToFile(Context context, Category data)
    {
        List<Category> categories = getSelectedCategories(context);

        File allCategories = new File(context.getFilesDir(), filename);


//         this is used to add stuff
//        JSONArray items  = new JSONArray(getAllSelectedCarJson(context));
        JSONArray items = new JSONArray();
        for(int i = 0; i < categories.size(); i++)
        {
            JSONObject categoryToAdd = new JSONObject();
            try {

                categoryToAdd.put("name", categories.get(i).getName());
                categoryToAdd.put("weight", categories.get(i).getWeight());
                categoryToAdd.put("maximize", String.valueOf(categories.get(i).isMaximize()));
            } catch (JSONException e)
            {
                Log.e("Selected Categories", e.toString());
            }

            items.put(categoryToAdd);
        }
        try {
            JSONObject toAdd = new JSONObject() ;
            toAdd.put("name", data.getName());
            toAdd.put("weight", data.getWeight());
            toAdd.put("maximize", data.isMaximize());
            items.put(toAdd);
        } catch (JSONException e)
        {
            Log.e("Selected Cars", e.toString());
        }



        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));
            outputStreamWriter.write(items.toString());
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("[Selected Categories]", "File write failed: " + e.toString());
        }

    }

    public static List<Category> getSelectedCategories(Context context)
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
            Log.e("[Selected Categories]", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("[Selected Categories]", "Can not read file: " + e.toString());
        }

        return JSonUtils.toCategoryList(ret);
    }


    public static void deleteAllSelectedCategories(Context context)
    {
        File allCars = new File(context.getFilesDir(), filename);

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));
            outputStreamWriter.write("");
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("[Selected Categories]", "File delete failed: " + e.toString());
        }


    }
}
