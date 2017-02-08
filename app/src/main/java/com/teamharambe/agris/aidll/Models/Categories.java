package com.teamharambe.agris.aidll.Models;


import android.content.Context;
import android.util.Log;

import com.teamharambe.agris.aidll.AIDLL;
import com.teamharambe.agris.aidll.Listeners.ListViewListeners;
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

public class Categories {


    private static final String TAG = "[Categories]";
    static String filename = "selectedCategories.json";
    File selectedCategories;

    public Categories() {
        Log.i(TAG, "Creating file: " + filename);
        Log.i(TAG, "Destination xdd:" + AIDLL.getAppContext().getFilesDir().getAbsolutePath() + filename);
       selectedCategories = new File(AIDLL.getAppContext().getFilesDir(), filename);
    }

    public static void addCategory(Category category, Context context)
    {
        File selectedCategories = new File(AIDLL.getAppContext().getFilesDir(), filename);
        JSONArray items = new JSONArray();
        try {
            JSONObject categoryToAdd = new JSONObject();
            categoryToAdd.put("name", category.getName());
            categoryToAdd.put("weight", category.getWeight());

            List<Category> categories = getAllSelectedCategories(AIDLL.getAppContext());

            items = JSonUtils.categoriesToJsonArray(categories);
            items.put(categoryToAdd);
        }
        catch (JSONException jse){
            Log.e("[JSON EXCEPTION]", jse.toString());
        }
        writeInFile(items, context);
    }


    public static void updateCategory(Context context, int position, Category newValue)
    {
        List<Category> categories = getAllSelectedCategories(context);
        categories.set(position, newValue);

        JSONArray items = JSonUtils.categoriesToJsonArray(categories);
        writeInFile(items, context);
    }


    public static void delete(Context context)
    {
        Log.i(TAG, "Deleting file: " + filename);
        context.deleteFile(filename);
    }

    public static List<Category> getAllSelectedCategories(Context context)
    {
        String ret = "";

        Log.i(TAG, "Fetching categories");
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
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        Log.i("[JSon response]" , ret);
        return JSonUtils.toCategoryList(ret);


    }

    private static void writeInFile(JSONArray items, Context context)
    {

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
