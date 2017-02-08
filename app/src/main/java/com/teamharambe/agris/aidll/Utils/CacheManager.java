package com.teamharambe.agris.aidll.Utils;

import android.content.Context;
import android.util.Log;

import com.teamharambe.agris.aidll.AIDLL;
import com.teamharambe.agris.aidll.Models.Car;
import com.teamharambe.agris.aidll.Web.DataRetriever;

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
 * Created by Agris on 11.11.2016..
 */

public class CacheManager {

    public static final String filename = "allCarList.json";

    public static void populateCarListToCache(Context context, String data)
    {
        File allCars = new File(context.getFilesDir(), filename);

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("[Cache Manager]", "File write failed: " + e.toString());
        }

    }

    public static String getCarsInCache(Context context)
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
            Log.e("[Cache]", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("[Cache]", "Can not read file: " + e.toString());
        }
        return ret;
    }

}
