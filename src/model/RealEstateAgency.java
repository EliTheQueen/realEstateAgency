package model;

import java.util.ArrayList;

public class RealEstateAgency {

    private ArrayList<House> houses;

    public RealEstateAgency() {
        houses = new ArrayList<>();
    }

    public void addHouse(House house) {
        houses.add(house);
    }

    public void showAllHouses() {
        for (House house : houses) {
            System.out.println(house);
            System.out.printf("Price: %.0f\n", house.calculatePrice());
            System.out.println("...........................");
        }
    }

    public void showHousesForSale() {
        for (House house : houses) {
            if (house.isForSale()) {
                System.out.println(house);
            }
        }
    }

    public void showHousesByRegion(int region)  {
        for (House house : houses) {
            if(house.getRegion() == region) {
                System.out.println(house);
            }
        }
    }

    public House findMostExpensiveHouse() {
        if (houses.isEmpty()) {
            return null;
        }
        House mostExpensive = houses.get(0);
        for (House house : houses) {
            if (house.calculatePrice() > mostExpensive.calculatePrice()) {
                mostExpensive = house;
            }
        }
        return mostExpensive;
    }
}