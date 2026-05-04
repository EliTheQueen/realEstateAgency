package model;

public class Apartment extends House {

    private int bedrooms;
    private int floor;

    public Apartment(int id, double area, int region, String ownerName, String renterName, boolean forSale, boolean forRent, int bedrooms, int floor) {

        super(id, area, region, ownerName, renterName,forSale, forRent);

        this.bedrooms = bedrooms;
        this.floor = floor;
    }

    public int getBedrooms() { return bedrooms; }
    public int getFloor() { return floor; }
    public void setBedrooms(int bedrooms) { this.bedrooms = bedrooms; }
    public void setFloor(int floor) { this.floor = floor; }

    @Override
    public double calculatePrice() {
        return getBasePrice() * (1 + 0.03 * getBedrooms()) * (1 + 0.01 * getFloor());
    }

    @Override
    public String toString() {
        return super.toString() + "Bedrooms: " + bedrooms + ", Floor: " + floor;
    }
}
