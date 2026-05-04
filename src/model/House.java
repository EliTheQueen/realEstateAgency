package model;

public abstract class House {

    private int id;
    private double area;
    private int region;
    private String ownerName;
    private String renterName;
    private boolean forSale;
    private boolean forRent;

    protected static final double BASE_PRICE_PER_METER = 10_000_000;
    protected static final double RENT_RATE = 0.004;

    public House(int id, double area, int region, String ownerName, String renterName, boolean forSale, boolean forRent) {
        this.id = id;
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
    public String getOwnerName() { return ownerName; }
    public String getRenterName() { return renterName; }
    public boolean isForSale() { return forSale; }
    public boolean isForRent() { return forRent; }

    public void setReneterName(String renterName) { this.renterName = renterName; }

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
