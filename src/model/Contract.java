package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Contract implements Serializable {

    private static final long serialVersionUID = 1L;


    private static int nextId = 1;
    private int contractId;
    private int houseId;
    private int buyerId;
    private int sellerId;
    private double price;

    private String type;

    public Contract(int houseId, int buyerId, int sellerId, String  type, double price) {

        this.contractId = nextId++;
        this.houseId = houseId;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.type = type;
        this.price = price;

    }

    public int getContractId() {
        return contractId;
    }

    public int getHouseId() {
        return houseId;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public static void updateNextId(ArrayList<Contract> contracts) {
        int max = 0;
        for (Contract contract : contracts) {
            if (contract.getContractId() > max) {
                max = contract.getContractId();
            }
        }
        nextId = max + 1;
    }

    @Override
    public String toString() {
        return "Contract ID: " + contractId +
                ", House ID: " + houseId +
                ", Buyer ID: " + buyerId +
                ", Seller ID: " + sellerId +
                ", Type: " + type +
                ", Price: " + price;
    }


}
