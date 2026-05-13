package model;

import data.HashUtil;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String username;
    private String passwordHash;
    private double budget;
    private static int nextId = 1;
    private String role;


    private List<Integer> ownedHouses;
    private List<Integer> rentedHouses;
    private List<Integer> contracts;

    public User(String username, String password, double budget, String role) {
        this.id = nextId++;
        this.username = username;
        this.passwordHash = HashUtil.hashPassword(password);
        this.role = role;
        if (role == UserRole.AGENCY) {
            this.budget = 0;
        } else {
            this.budget = budget;
        }

        ownedHouses = new ArrayList<>();
        rentedHouses = new ArrayList<>();
        contracts = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        if (!role.equals(UserRole.AGENCY)) {
            this.budget = budget;
        }
    }

    public String getRole() {
        return role;
    }

    public List<Integer> getOwnedHouses() {
        return ownedHouses;
    }

    public List<Integer> getRentedHouses() {
        return rentedHouses;
    }

    public List<Integer> getContracts() {
        return contracts;
    }

    public void addOwnedHouse(int houseId) {
        ownedHouses.add(houseId);
    }

    public void removeOwnedHouse(int houseId) {
        ownedHouses.remove(Integer.valueOf(houseId));
    }

    public void addRentedHouse(int houseId) {
        rentedHouses.add(houseId);
    }

    public void addContract(int contractId) {
        contracts.add(contractId);
    }

    public void removeContract(int contractId) {
        contracts.remove(Integer.valueOf(contractId));
    }

    public void removeRentedHouse(int houseId) {
        rentedHouses.remove(Integer.valueOf(houseId));
    }

    public static void updateNextId(ArrayList<User> users) {
        int max = 0;
        for (User u : users) {
            if (u.getId() > max) {
                max = u.getId();
            }
        }
        nextId = max + 1;
    }

    @Override
    public String toString() {
        return "User ID: " + id +
                "\nUsername: " + username +
                "\nBudget: " + budget;
    }
}
