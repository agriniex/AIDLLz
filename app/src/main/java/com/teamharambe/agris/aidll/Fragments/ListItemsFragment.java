package com.teamharambe.agris.aidll.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.teamharambe.agris.aidll.Activities.CarDetails;
import com.teamharambe.agris.aidll.Activities.CategoryDetails;
import com.teamharambe.agris.aidll.Adapters.CarAdapter;
import com.teamharambe.agris.aidll.Adapters.CarSpinnerAdapter;
import com.teamharambe.agris.aidll.Adapters.CategoryAdapter;
import com.teamharambe.agris.aidll.FileManagement.SelectedCars;
import com.teamharambe.agris.aidll.FileManagement.SelectedCategories;
import com.teamharambe.agris.aidll.Listeners.ListViewListeners;
import com.teamharambe.agris.aidll.Models.Car;
import com.teamharambe.agris.aidll.Models.Cars;
import com.teamharambe.agris.aidll.Models.Categories;
import com.teamharambe.agris.aidll.Models.Category;
import com.teamharambe.agris.aidll.R;
import com.teamharambe.agris.aidll.Utils.CacheManager;
import com.teamharambe.agris.aidll.Utils.JSonUtils;

import java.util.ArrayList;
import java.util.List;

public class ListItemsFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        ListView carListView;
        ListView categoryListView;
//    ListView listItems;
        FloatingActionButton fab;
        View categoryView;
        View carView;

        public ListItemsFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static ListItemsFragment newInstance(int sectionNumber) {
            ListItemsFragment fragment = new ListItemsFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            int sectionNr = getArguments().getInt(ARG_SECTION_NUMBER);
            if (sectionNr == 1)
            {
                categoryView = inflater.inflate(R.layout.list_items, container, false);
                categoryListView = (ListView) categoryView.findViewById(R.id.item_list);

                categoryListView.setOnItemClickListener(ListViewListeners.setCategoryItemListener());

                fab = (FloatingActionButton) categoryView.findViewById(R.id.fab);
                setCategoryViewDetails(categoryView);
                return categoryView;
            }
            else if (sectionNr == 2)
            {
                carView = inflater.inflate(R.layout.list_items, container, false);

                carListView = (ListView) carView.findViewById(R.id.item_list);

                carListView.setOnItemClickListener(ListViewListeners.setCarItemListener());
                carListView.setOnItemLongClickListener(ListViewListeners.setCarItemLongListener(getActivity()));

                    fab = (FloatingActionButton) carView.findViewById(R.id.fab);
                    setCarViewDetails(carView);
                    return carView;
            }
            else throw new AssertionError("No section found with ID " + sectionNr);
        }



    @Override
    public void onResume() {
        super.onResume();

        int sectionNr = getArguments().getInt(ARG_SECTION_NUMBER);
        switch (sectionNr){
            case 1:
                setCategoryItems();
                break;
            case 2:
                setCarItems();
                break;
        }

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public static class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return ListItemsFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Criteria";
                case 1:
                    return "Car List";
            }
            return null;
        }
    }

    private void setCategoryViewDetails(View rootView)
    {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent categoryDetailsIntent = new Intent(getContext(), CategoryDetails.class);
                startActivity(categoryDetailsIntent);
            }
        });

        categoryListView = (ListView)rootView.findViewById(R.id.item_list);

        setCategoryItems();

    }

    private void setCategoryItems()
    {
        ArrayAdapter<Category> adapter = new CategoryAdapter(getContext(), SelectedCategories.getSelectedCategories(getContext()));
        categoryListView.setAdapter(adapter);
    }

    private void setCarViewDetails(View rootView)
    {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent carDetailsIntent = new Intent(getContext(), CarDetails.class);
                startActivity(carDetailsIntent);
            }
        });

        carListView = (ListView)rootView.findViewById(R.id.item_list);

        setCarItems();
    }



    private void setCarItems()
    {
//        Cars carsHelper = new Cars();
        List<Car> cars = new ArrayList<>();
        List<String> allCarsId = SelectedCars.getSelectedCars(getContext());

        for(int i = 0; i < allCarsId.size(); i++)
        {
            cars.add(JSonUtils.getCarByID(allCarsId.get(i)));
        }
        ArrayAdapter<Car> adapter = new CarSpinnerAdapter(getContext(), cars);
        carListView.setAdapter(adapter);
    }

}


