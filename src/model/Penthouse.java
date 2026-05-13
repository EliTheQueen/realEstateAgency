package model;

public class Penthouse extends House{

    private double terraceArea;
    private boolean hasPool;
    private int floorNumber;

    protected static final double Luxury_Coefficient = 1.5;
    protected static final double Terrace_Price_Per_Meter = BASE_PRICE_PER_METER * 0.8;
    protected static final double Pool_Value = 1_000_000_000;

    public Penthouse(double area, int region, String ownerName, String renterName, boolean forSale, boolean forRent, double terraceArea, boolean hasPool, int floorNumber) {

        super(area, region, ownerName, renterName, forSale, forRent);

        this.terraceArea = terraceArea;
        this.hasPool = hasPool;
        this.floorNumber = floorNumber;
    }

    public double getTerraceArea() { return terraceArea; }
    public boolean hasPool() { return hasPool; }
    public int getFloorNumber() { return floorNumber; }
    public void setFloorNumber(int floorNumber) { this.floorNumber = floorNumber; }
    public void setHasPool(boolean hasPool) { this.hasPool = hasPool; }
    public void setTerraceArea(double terraceArea) {this.terraceArea = terraceArea;}

    private double poolPrice() {
        return hasPool ? Pool_Value : 0;
    }

    @Override
    public double calculatePrice() {
        return getBasePrice() * Luxury_Coefficient + getTerraceArea() * Terrace_Price_Per_Meter + poolPrice();
    }

    @Override
    public String toString() {
        return "Penthouse: " + super.toString() + ", Terrace_Area: " + terraceArea + ", Has Pool: " + hasPool() + ", Floor: " + floorNumber;
    }
}
