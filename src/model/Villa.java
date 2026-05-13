package model;

public class Villa extends House {

    private double yardArea;
    private int floors;

    protected static double YARD_PRICE_PER_METER = BASE_PRICE_PER_METER * 0.5;
    protected static double FLOOR_PREMIUM = BASE_PRICE_PER_METER * 20;

    public Villa(double area, int region, String ownerName, String renterName, boolean forSale, boolean forRent, double yardArea, int floors) {

        super(area, region, ownerName, renterName, forSale, forRent);

        this.yardArea = yardArea;
        this.floors = floors;
    }

    public double getYardArea() { return yardArea; }
    public int getFloors() { return floors; }
    public void setFloors(int floors) { this.floors = floors; }
    public void setYardArea(double yardArea) { this.yardArea = yardArea; }

    @Override
    public double calculatePrice() {
        return getBasePrice() + (getYardArea() * YARD_PRICE_PER_METER) + (FLOOR_PREMIUM * getFloors());
    }

    @Override
    public String toString() {
        return " Villa: " + super.toString() + ", Yard Area: " + yardArea + ", Floors: " + floors;
    }
}
