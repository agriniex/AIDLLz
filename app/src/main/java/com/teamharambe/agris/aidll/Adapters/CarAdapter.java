package com.teamharambe.agris.aidll.Adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.teamharambe.agris.aidll.Models.Car;
import com.teamharambe.agris.aidll.Models.Category;
import com.teamharambe.agris.aidll.R;

import java.util.List;

public class CarAdapter extends ArrayAdapter<Car> {

    public CarAdapter(Context context, List<Car> cars) {
        super(context, R.layout.category_row, cars);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());

        View carRow = layoutInflater.inflate(R.layout.car_row, parent, false);

        Car car = getItem(position);
        TextView carName = (TextView) carRow.findViewById(R.id.carName);

        carName.setText(car.getName());

        return carRow;
    }
}
