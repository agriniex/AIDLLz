package com.teamharambe.agris.aidll.Adapters;


import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.teamharambe.agris.aidll.Models.Category;
import com.teamharambe.agris.aidll.R;

import java.util.List;

public class CategoryAdapter  extends ArrayAdapter<Category>
{
    public CategoryAdapter(Context context, List<Category> categories) {
        super(context, R.layout.category_row, categories);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());

        View categoryRow = layoutInflater.inflate(R.layout.category_row, parent, false);

        Category category = getItem(position);
        TextView categoryName = (TextView) categoryRow.findViewById(R.id.categoryName);
        TextView sortingOrder = (TextView) categoryRow.findViewById(R.id.sorting_order);

        categoryName.setTextSize(30);
        categoryName.setTextColor(Color.RED);
        sortingOrder.setTextSize(20);
        sortingOrder.setTextColor(Color.rgb(100, 0, 0));

        categoryName.setText(category.getName());
        if (category.isMaximize())
        {
            sortingOrder.setText("Maximize");
        }
        else
        {
            sortingOrder.setText("Minimize");
        }
//        sortingOrder.setText(Integer.toString(category.getWeight()));

        return categoryRow;
    }
}
