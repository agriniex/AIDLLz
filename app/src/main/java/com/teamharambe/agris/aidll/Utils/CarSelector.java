package com.teamharambe.agris.aidll.Utils;


import com.teamharambe.agris.aidll.Models.Car;
import com.teamharambe.agris.aidll.Models.Category;
import com.teamharambe.agris.aidll.Models.CategoryValues;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CarSelector {

    public static List<Car> selectCarWithPairComparison(List<Category> categories, List<Car> cars)
    {
        List<Car> carsToReturn = new ArrayList<>();
        int categoryCount = categories.size();
        int carCount = cars.size();
        Comparator<Car>  comparator = null;

        for(int i = 0; i< categoryCount; i++)
        {
            Category currentCategory = categories.get(i);
            if(currentCategory.getName().equals(CategoryValues.Price.toString()))
            {
               comparator = new Comparator<Car>()
                {
                    @Override
                    public int compare(Car o1, Car o2) {
                        if (o1.getPrice() == o2.getPrice())
                            return 0;
                        return o1.getPrice() < o2.getPrice() ? -1  : 1 ;
                    }
                };
            }
            else if (currentCategory.getName().equals(CategoryValues.Year.toString()))
            {
                comparator = new Comparator<Car>()
                {
                    @Override
                    public int compare(Car o1, Car o2) {
                        if (o1.getYear() == o2.getYear())
                            return 0;
                        return o1.getYear() < o2.getYear() ? -1  : 1 ;
                    }
                };
            }
            else if (currentCategory.getName().equals(CategoryValues.Doors.toString()))
            {
                comparator = new Comparator<Car>()
                {
                    @Override
                    public int compare(Car o1, Car o2) {
                        if (o1.getDoors() == o2.getDoors())
                            return 0;
                        return o1.getDoors() < o2.getDoors() ? -1  : 1 ;
                    }
                };
            }
            else if (currentCategory.getName().equals(CategoryValues.FuelTank.toString()))
            {
                comparator = new Comparator<Car>()
                {
                    @Override
                    public int compare(Car o1, Car o2) {
                        if (Integer.parseInt(o1.getFuelTank().replaceAll("[^\\d.]", "")) == Integer.parseInt(o2.getFuelTank().replaceAll("[^\\d.]", "")))
                            return 0;
                        return Integer.parseInt(o1.getFuelTank().replaceAll("[^\\d.]", "")) < Integer.parseInt(o2.getFuelTank().replaceAll("[^\\d.]", "")) ? -1  : 1 ;
                    }
                };
            }
            else if (currentCategory.getName().equals(CategoryValues.Power.toString()))
            {
                comparator = new Comparator<Car>()
                {
                    @Override
                    public int compare(Car o1, Car o2) {
                        if (Integer.parseInt(o1.getPower().replaceAll("[^\\d.]", "")) == Integer.parseInt(o2.getPower().replaceAll("[^\\d.]", "")))
                            return 0;
                        return Integer.parseInt(o1.getPower().replaceAll("[^\\d.]", "")) < Integer.parseInt(o2.getPower().replaceAll("[^\\d.]", "")) ? -1  : 1 ;
                    }
                };
            }
            else if (currentCategory.getName().equals(CategoryValues.TopSpeed.toString()))
            {
                comparator = new Comparator<Car>()
                {
                    @Override
                    public int compare(Car o1, Car o2) {
                        if (Integer.parseInt(o1.getTopSpeed().replaceAll("[^\\d.]", "")) == Integer.parseInt(o2.getTopSpeed().replaceAll("[^\\d.]", "")))
                            return 0;
                        return Integer.parseInt(o1.getTopSpeed().replaceAll("[^\\d.]", "")) < Integer.parseInt(o2.getTopSpeed().replaceAll("[^\\d.]", "")) ? -1  : 1 ;
                    }
                };
            }
            else if (currentCategory.getName().equals(CategoryValues.Weight.toString()))
            {
                comparator = new Comparator<Car>()
                {
                    @Override
                    public int compare(Car o1, Car o2) {
                        if (Integer.parseInt(o1.getWeight().replaceAll("[^\\d.]", "")) == Integer.parseInt(o2.getWeight().replaceAll("[^\\d.]", "")))
                            return 0;
                        return Integer.parseInt(o1.getWeight().replaceAll("[^\\d.]", "")) < Integer.parseInt(o2.getWeight().replaceAll("[^\\d.]", "")) ? -1  : 1 ;
                    }
                };
            }
            else if (currentCategory.getName().equals(CategoryValues.MaxWeight.toString()))
            {
                comparator = new Comparator<Car>()
                {
                    @Override
                    public int compare(Car o1, Car o2) {
                        if (Integer.parseInt(o1.getMaxWeight().replaceAll("[^\\d.]", "")) == Integer.parseInt(o2.getMaxWeight().replaceAll("[^\\d.]", "")))
                            return 0;
                        return Integer.parseInt(o1.getMaxWeight().replaceAll("[^\\d.]", "")) < Integer.parseInt(o2.getMaxWeight().replaceAll("[^\\d.]", "")) ? -1  : 1 ;
                    }
                };
            }

            Collections.sort(cars, comparator);
            for (int j= 0; j< carCount; j++)
            {
                int currentRank = cars.get(j).getRank();
                if (categories.get(i).isMaximize())
                {
                    cars.get(j).setRank(currentRank + carCount - j);
                }
                else{
                    cars.get(j).setRank(currentRank+j);
                }

            }

        }
        comparator = new Comparator<Car>()
        {
            @Override
            public int compare(Car o1, Car o2) {
                if (o1.getRank() == o2.getRank())
                    return 0;
                return o1.getYear() < o2.getYear() ? -1  : 1 ;
            }
        };
        Collections.sort(cars, comparator);

        carsToReturn.add(cars.get(0));
        if (carCount > 1) {
            carsToReturn.add(cars.get(1));
        }
        return carsToReturn;
    }
}
