package com.teamharambe.agris.aidll.Activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.teamharambe.agris.aidll.Adapters.CarSpinnerAdapter;
import com.teamharambe.agris.aidll.FileManagement.SelectedCars;
import com.teamharambe.agris.aidll.FileManagement.SelectedCategories;
import com.teamharambe.agris.aidll.Models.Car;
import com.teamharambe.agris.aidll.Models.Cars;
import com.teamharambe.agris.aidll.R;
import com.teamharambe.agris.aidll.Utils.CacheManager;
import com.teamharambe.agris.aidll.Utils.JSonUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class CarDetails extends AppCompatActivity{

    TextView carName;
    Spinner allCarsSpinner;
    List<Car> cars;
    CarSpinnerAdapter spinnerAdapter;
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
    ImageView carImage;
    Bundle extras;


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);

        extras = getIntent().getExtras();

        Toolbar toolbar = (Toolbar) findViewById(R.id.car_details_toolbar);
        setSupportActionBar(toolbar);

        priceText = (TextView) findViewById(R.id.priceText);
        carName = (TextView) findViewById(R.id.carName);
        doorsCountText = (TextView) findViewById(R.id.doorsCountText);
        yearText = (TextView) findViewById(R.id.yearText);
        bodyTypeText = (TextView) findViewById(R.id.bodyTypeText);
        fuelTankText = (TextView) findViewById(R.id.fuelTankText);
        fuelTypeText = (TextView) findViewById(R.id.fuelTypeText);
        powerText = (TextView) findViewById(R.id.powerText);
        topSpeedText = (TextView) findViewById(R.id.topSpeedText);
        weightText = (TextView) findViewById(R.id.weightText);
        maxWeightText = (TextView) findViewById(R.id.maxWeightText);
        carImage = (ImageView) findViewById(R.id.carImage);


        List<Car> cars = Cars.removeSelectedCars(this,JSonUtils.toCarListFromJson(CacheManager.getCarsInCache(this)));
        if (extras != null)
        {
            List<Car> selected = Cars.getAllSelectedCars(getApplicationContext());
            for(int i = 0; i < selected.size(); i++)
            {
                if (selected.get(i).getModelName().id.equals(extras.get("carId")));
                {
                    cars.add(0, selected.get(i));
                }
            }

        }
        allCarsSpinner = (Spinner) findViewById(R.id.allCarsSpinner);

        spinnerAdapter = new CarSpinnerAdapter(getApplicationContext(), cars);
        allCarsSpinner.setAdapter(spinnerAdapter);

        allCarsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Car selected = spinnerAdapter.getCarAtPosition(position);
                String carId = selected.getModelName().id;
                Car car = JSonUtils.getCarByID(carId);
                if( car != null) {
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

                    new DownloadImageTask().execute(car.getPictureLink());
//                    carImage.setTag(car.getPictureLink());
//                    try {
//                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//                        StrictMode.setThreadPolicy(policy);
//
//                        Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(car.getPictureLink()).getContent());
//                        carImage.setImageBitmap(bitmap);
//                    } catch (MalformedURLException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cars, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add_car) {
            View view = allCarsSpinner.getSelectedView();
            TextView idField = (TextView)view.findViewById(R.id.modelIdField);
            String carId = idField.getText().toString();

            if(extras == null) {
                SelectedCars.addCarsToFile(getApplicationContext(), carId);
            }
            else{
                Cars.updateCar(getApplicationContext(), Integer.parseInt(extras.getString("position")) ,carId);
            }
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        String url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            carImage.setImageResource(0);
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            this.url = urls[0];
            return downloadImage(url);
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            carImage.setImageBitmap(result);
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
