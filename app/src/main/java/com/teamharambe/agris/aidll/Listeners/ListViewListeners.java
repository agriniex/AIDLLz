package com.teamharambe.agris.aidll.Listeners;

import android.app.Fragment;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.teamharambe.agris.aidll.AIDLL;
import com.teamharambe.agris.aidll.Activities.CarDetails;
import com.teamharambe.agris.aidll.Activities.CategoryDetails;
import com.teamharambe.agris.aidll.Activities.MainActivity;
import com.teamharambe.agris.aidll.FileManagement.SelectedCars;
import com.teamharambe.agris.aidll.FileManagement.SelectedCategories;
import com.teamharambe.agris.aidll.Fragments.ListItemsFragment;
import com.teamharambe.agris.aidll.Models.Car;
import com.teamharambe.agris.aidll.Models.Cars;
import com.teamharambe.agris.aidll.Models.Categories;
import com.teamharambe.agris.aidll.Models.Category;

/**
 * Created by Agris on 02.11.2016..
 */

public class ListViewListeners {

    public static AdapterView.OnItemClickListener setCategoryItemListener()
    {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Category categoryToEdit = SelectedCategories.getSelectedCategories(view.getContext()).get(position);

                Intent categoryDetailsIntent = new Intent(view.getContext(), CategoryDetails.class);
                categoryDetailsIntent.putExtra("name", categoryToEdit.getName());
                categoryDetailsIntent.putExtra("maximize", categoryToEdit.isMaximize());
                categoryDetailsIntent.putExtra("position", position);
                view.getContext().startActivity(categoryDetailsIntent);
            }
        };
    }

    public static AdapterView.OnItemClickListener setCarItemListener()
    {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                String carIdToEdit = SelectedCars.getSelectedCars(view.getContext()).get(position);
                Car car = Cars.getAllSelectedCars(view.getContext()).get(position);


                Intent carDetailsIntent = new Intent(view.getContext(), CarDetails.class);
                carDetailsIntent.putExtra("carId", car.getModelName().id);
                carDetailsIntent.putExtra("position", Integer.toString(position));
                view.getContext().startActivity(carDetailsIntent);
            }
        };
    }



}
