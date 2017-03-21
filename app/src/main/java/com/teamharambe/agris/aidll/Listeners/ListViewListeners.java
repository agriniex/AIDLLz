package com.teamharambe.agris.aidll.Listeners;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.teamharambe.agris.aidll.Activities.CarDetails;
import com.teamharambe.agris.aidll.Activities.CategoryDetails;
import com.teamharambe.agris.aidll.FileManagement.SelectedCategories;
import com.teamharambe.agris.aidll.Models.Car;
import com.teamharambe.agris.aidll.Models.Cars;
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
                categoryDetailsIntent.putExtra("position", Integer.toString(position));
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
               ListViewListeners.openCarDetails(view, position);
            }
        };
    }

    public static AdapterView.OnItemLongClickListener setCarItemLongListener(Activity activity)
    {
        final Activity activityToPass = activity;
        return new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final View viewToPass = view;
                Car car = Cars.getAllSelectedCars(view.getContext()).get(position);
                new AlertDialog.Builder(view.getContext())
                        .setTitle(car.getName() + " " + car.getModelName().model)
                        .setMessage("Choose an action")
                        .setPositiveButton("View", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                ListViewListeners.openCarDetails(viewToPass, position);
                            }
                        })
                        .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //do nothing
                                }
                        })
                        .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Cars.deleteCar(viewToPass.getContext(), position);
                                //todo remove filthy workaround, refresh only fragment
                                activityToPass.finish();
                                activityToPass.startActivity(activityToPass.getIntent());
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return true;
            }
        };
    }

    private static void openCarDetails(View view, int position)
    {
        Car car = Cars.getAllSelectedCars(view.getContext()).get(position);


        Intent carDetailsIntent = new Intent(view.getContext(), CarDetails.class);
        carDetailsIntent.putExtra("carId", car.getModelName().id);
        carDetailsIntent.putExtra("position", Integer.toString(position));
        view.getContext().startActivity(carDetailsIntent);
    }





}
