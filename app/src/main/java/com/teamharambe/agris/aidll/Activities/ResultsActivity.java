package com.teamharambe.agris.aidll.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.teamharambe.agris.aidll.Adapters.CarSpinnerAdapter;
import com.teamharambe.agris.aidll.FileManagement.SelectedCars;
import com.teamharambe.agris.aidll.FileManagement.SelectedCategories;
import com.teamharambe.agris.aidll.Models.Car;
import com.teamharambe.agris.aidll.Models.Cars;
import com.teamharambe.agris.aidll.Models.Categories;
import com.teamharambe.agris.aidll.Models.Category;
import com.teamharambe.agris.aidll.R;
import com.teamharambe.agris.aidll.Utils.CarSelector;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ResultsActivity extends AppCompatActivity {

    ImageView mainCarImage;
    ImageView honorableCarImage;
    ImageView goldMedal;
    ImageView youTriedMedal;
    TextView priceText;
    TextView yearText;
    TextView doorsCountText;
    TextView bodyTypeText;
    TextView fuelTankText;
    TextView fuelTypeText;
    TextView powerText;
    TextView topSpeedText;
    TextView weightText;
    TextView maxWeightText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        goldMedal = (ImageView) findViewById(R.id.gold_medal);
        youTriedMedal = (ImageView) findViewById(R.id.you_tried_medal);

        goldMedal.setVisibility(View.INVISIBLE);
        youTriedMedal.setVisibility(View.INVISIBLE);

        List<Car> cars = Cars.getAllSelectedCars(this);
        List<Category> categories = Categories.getAllSelectedCategories(this);

        List<Car> chosenCars = CarSelector.selectCarWithPairComparison(categories, cars);
//        List<Car> chosenOne = new ArrayList<>();
//        chosenOne.add(chosenCars.get(0));

        priceText = (TextView) findViewById(R.id.priceText);
        doorsCountText = (TextView) findViewById(R.id.doorsCountText);
        yearText = (TextView) findViewById(R.id.yearText);
        bodyTypeText = (TextView) findViewById(R.id.bodyTypeText);
        fuelTankText = (TextView) findViewById(R.id.fuelTankText);
        fuelTypeText = (TextView) findViewById(R.id.fuelTypeText);
        powerText = (TextView) findViewById(R.id.powerText);
        topSpeedText = (TextView) findViewById(R.id.topSpeedText);
        weightText = (TextView) findViewById(R.id.weightText);
        maxWeightText = (TextView) findViewById(R.id.maxWeightText);

        mainCarImage = (ImageView) findViewById(R.id.main_car_image);
        honorableCarImage = (ImageView) findViewById(R.id.honorable_car_image);
        TextView selectedCarName = (TextView) findViewById(R.id.selected_car_name);
        TextView selectedCarModel = (TextView) findViewById(R.id.selected_car_model);
        TextView honorableText = (TextView) findViewById(R.id.honorable_mention_text);

        Car car = chosenCars.get(0);
        selectedCarName.setText(car.getName());
        selectedCarModel.setText(car.getModelName().model);

        priceText.setText(Integer.toString(car.getPrice())+"Eur");
        yearText.setText(Integer.toString(car.getYear()));
        doorsCountText.setText(Integer.toString(car.getDoors()));
        bodyTypeText.setText(car.getBodyType());
        fuelTankText.setText(car.getFuelTank());
        fuelTypeText.setText(car.getFuel());
        topSpeedText.setText(car.getTopSpeed());
        powerText.setText(car.getPower());
        weightText.setText(car.getWeight());
        maxWeightText.setText(car.getMaxWeight());


        new DownloadMainImageTask().execute(chosenCars.get(0).getPictureLink());

        if(chosenCars.size() > 1) {
            honorableText.setText(chosenCars.get(1).getName() + " " + chosenCars.get(1).getModelName());
            new DownloadHonorableImageTask().execute(chosenCars.get(1).getPictureLink());
        }
        else{
            TextView honorableMentionLabel = (TextView) findViewById(R.id.honorable_mention_label);
            honorableMentionLabel.setVisibility(View.INVISIBLE);
            honorableCarImage.setVisibility(View.INVISIBLE);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.resultsToolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onBackPressed() {
        SelectedCars.deleteAllSelectedCars(getBaseContext());
        SelectedCategories.deleteAllSelectedCategories(getBaseContext());
        super.onBackPressed();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_results, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_results_to_main) {
            SelectedCars.deleteAllSelectedCars(getBaseContext());
            SelectedCategories.deleteAllSelectedCategories(getBaseContext());
            finish();
//            Intent compareCarsIntent = new Intent(getBaseContext(), MainActivity.class);
//            startActivity(compareCarsIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    public class DownloadMainImageTask extends AsyncTask<String, Void, Bitmap> {

        String url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            this.url = urls[0];
            return downloadImage(url);
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            mainCarImage.setImageBitmap(result);
            goldMedal.setVisibility(View.VISIBLE);
        }


        private Bitmap downloadImage(String url) {

            Bitmap bitmap = null;
            try {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                bitmap = BitmapFactory.decodeStream((InputStream)new URL(url).getContent());

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bitmap;
        }
    }

    public class DownloadHonorableImageTask extends AsyncTask<String, Void, Bitmap> {

        String url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            this.url = urls[0];
            return downloadImage(url);
        }

        @Override
        protected void onPostExecute(Bitmap result) {

            honorableCarImage.setImageBitmap(result);
            youTriedMedal.setVisibility(View.VISIBLE);
        }


        private Bitmap downloadImage(String url) {

            Bitmap bitmap = null;
            try {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                bitmap = BitmapFactory.decodeStream((InputStream)new URL(url).getContent());

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bitmap;
        }
    }
}
