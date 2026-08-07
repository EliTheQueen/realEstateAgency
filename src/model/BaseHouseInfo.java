package model;

public class BaseHouseInfo {

    private final double area;
    private final int region;
    private final boolean sale;
    private final boolean rent;

    public BaseHouseInfo(double area, int region, boolean sale, boolean rent) {
        this.area = area;
        this.region = region;
        this.sale = sale;
        this.rent = rent;
    }

    public double getArea() {
        return area;
    }

    public int getRegion() {
        return region;
    }

    public boolean isSale() {
        return sale;
    }

    public boolean isRent() {
        return rent;
    }

}