package model;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class House implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private double area;
    private int region;
    private String ownerName;
    private String renterName;
    private boolean forSale;
    private boolean forRent;

    protected static final double BASE_PRICE_PER_METER = 10_000_000;
    protected static final double RENT_RATE = 0.004;
    private static int Id = 1;

    public House(double area, int region, String ownerName, String renterName, boolean forSale, boolean forRent) {
        this.id = Id++;
        this.area = area;
        this.region = region;
        this.ownerName = ownerName;
        this.renterName = renterName;
        this.forSale = forSale;
        this.forRent = forRent;
    }

    public int getId() { return id; }
    public double getArea() { return area; }
    public int getRegion() { return region; }
    public String  getOwnerName() { return ownerName; }
    public String getRenterName() { return renterName; }
    public boolean isForSale() { return forSale; }
    public boolean isForRent() { return forRent; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }
    public void setForSale(boolean forSale) { this.forSale = forSale; }
    public void setForRent(boolean forRent) { this.forRent = forRent; }
    public void setRenterName(String renterName) { this.renterName = renterName; }


    public abstract double calculatePrice();

    protected double getBasePrice() {
        double regionCoefficient = switch (region) {
            case 1 -> 1.8;
            case 2 -> 1.4;
            case 3 -> 1.1;
            case 4 -> 0.8;
            default -> 1.0;
        };
        return area * BASE_PRICE_PER_METER * regionCoefficient;
    }

    public double calculateMonthlyRent() {
        return calculatePrice() * RENT_RATE;
    }

    public static void updateId(ArrayList<House> houses) {
        int max = 0;
        for (House house : houses) {
            if (house.getId() > max) {
                max = house.getId();
            }
        }
        Id = max + 1;
    }

    @Override
    public String toString() {
        return "House ID: " + id +
                ", Owner: " + ownerName +
                ", Area: " + area +
                ", Region: " + region +
                ", For Sale: " + forSale +
                ", For Rent: " + forRent;
    }

}
