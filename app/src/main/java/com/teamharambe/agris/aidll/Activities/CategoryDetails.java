package com.teamharambe.agris.aidll.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.teamharambe.agris.aidll.FileManagement.SelectedCars;
import com.teamharambe.agris.aidll.FileManagement.SelectedCategories;
import com.teamharambe.agris.aidll.Models.Categories;
import com.teamharambe.agris.aidll.Models.Category;
import com.teamharambe.agris.aidll.Models.CategoryValues;
import com.teamharambe.agris.aidll.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CategoryDetails extends AppCompatActivity {

    Spinner categorySpinner;
    ToggleButton maximizeButton;
    Bundle extras;
    int categoriesAviable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_details);

        maximizeButton = (ToggleButton) findViewById(R.id.maximize_button);

        extras = getIntent().getExtras();

        if (extras != null)
        {
            Boolean maximize = (Boolean) extras.get("maximize");
            if (maximize!= null && maximize)
            {
                maximizeButton.setChecked(true);
            }
            else{
                maximizeButton.setChecked(false);
            }
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.category_details_toolbar);
        setSupportActionBar(toolbar);

        categorySpinner = (Spinner) findViewById(R.id.category_spinner);
        ArrayList<String> categories = toCategoryStringList(Arrays.asList(CategoryValues.values()));

        categoriesAviable = removeSelectedCategories(categories).size();
        ArrayAdapter<String>  categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, removeSelectedCategories(categories));
        categorySpinner.setAdapter(categoryAdapter);

    }

    private ArrayList<String> toCategoryStringList(List<CategoryValues> categoryEnums)
    {
        ArrayList<String> categoryNames = new ArrayList<>();
        for (int i = 0; i< categoryEnums.size(); i++)
        {
            categoryNames.add(categoryEnums.get(i).toString());
        }
        return categoryNames;
    }


    private ArrayList<String> removeSelectedCategories(ArrayList<String> categories)
    {
       List<Category> categoryList = Categories.getAllSelectedCategories(getApplicationContext());
        for(int i  = 0; i< categoryList.size(); i++ )
        {
            for (int j = 0; j < categories.size(); j++) {
                if (categoryList.get(i).getName().equals(categories.get(j))) {
                    categories.remove(j);
                }
            }
        }

        return categories;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_categories, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add_category) {
            if (categoriesAviable > 0) {

                String name = categorySpinner.getSelectedItem().toString();
                Category category = new Category(name, 0);
                boolean maximize = maximizeButton.isChecked();
                category.setMaximize(maximize);
                if (extras == null) {
                    SelectedCategories.addCategoryToFile(getApplicationContext(), category);
                } else {
                    Categories.updateCategory(CategoryDetails.this, Integer.parseInt(extras.getString("position")), category);
                }
            }
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
