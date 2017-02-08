package com.teamharambe.agris.aidll.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.teamharambe.agris.aidll.Models.Car;
import com.teamharambe.agris.aidll.R;

import java.util.List;

/**
 * Created by Agris on 09.11.2016..
 */

public class CarSpinnerAdapter extends ArrayAdapter<Car>{

    List<Car> cars;
    Context context;
    public CarSpinnerAdapter(Context context, List<Car> cars) {
        super(context, R.layout.car_spinner_row, cars);
        this.cars = cars;
        this.context = context;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
       return getCustomView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public Car getCarAtPosition(int position)
    {
        return cars.get(position);
    }


    public View getCustomView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());

        View row = layoutInflater.inflate(R.layout.car_spinner_row, parent, false);

        TextView spinnerCarName = (TextView) row.findViewById(R.id.spinnerCarName);
        TextView spinnerCarModel = (TextView) row.findViewById(R.id.spinnerCarModel);
        TextView modelIdField = (TextView) row.findViewById(R.id.modelIdField);
        spinnerCarName.setTextSize(30);
        spinnerCarName.setTextColor(Color.RED);
        spinnerCarModel.setTextSize(20);
        spinnerCarModel.setTextColor(Color.rgb(100, 0, 0));



//        if (position == 0)
//        {
//            spinnerCarName.setText("Izvēlieties auto....");
//
//        }
//        else
//        {

            String fullName = cars.get(position).getName() + " " + cars.get(position).getModelName();


            spinnerCarName.setText(cars.get(position).getName());
            spinnerCarModel.setText(cars.get(position).getModelName().toString());
            modelIdField.setText(cars.get(position).getModelName().id);
//        }

        return row;
    }
}
