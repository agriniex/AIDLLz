package com.teamharambe.agris.aidll.Web;


import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

import com.teamharambe.agris.aidll.Utils.DataRetrieverResponse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DataRetriever extends AsyncTask<Void , Void, String>{
private DataRetrieverResponse listener;

    public DataRetriever(DataRetrieverResponse listener){
        this.listener = listener;
    }

    ProgressBar progressBar;
    private Exception exception;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        progressBar = new ProgressBar(AIDLL.getAppContext());
//        progressBar.setVisibility(View.VISIBLE);
    }
//AyyLe.mov
    @Override
    protected String doInBackground(Void... params) {
        try {
            URL url = new URL("http://puu.sh/sTuyg.json");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();

            }
            finally {
                urlConnection.disconnect();
            }
        } catch (Exception e)
        {
            Log.e("[DATA RETRIEVE]", e.toString());
            return null;
        }


    }

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);
        if(response == null) {
            response = "THERE WAS AN ERROR";
        }
//        progressBar.setVisibility(View.GONE);
        Log.i("[RESPONSE]", response);
        listener.onResponseReceived(response);
    }
}
