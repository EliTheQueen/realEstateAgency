package service;

import data.HashUtil;
import model.Contract;
import model.ContractType;
import model.House;
import model.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RealEstateAgency implements Serializable {

    private static final long serialVersionUID = 1L;

    private ArrayList<House> houses;

    private ArrayList<User> users;

    private ArrayList<Contract>  contracts;

    private User agency;

    public RealEstateAgency() {
        houses = new ArrayList<>();
        users = new ArrayList<>();
        contracts = new ArrayList<>();
    }

    public ArrayList<House> getHouses() {
        return this.houses;
    }

    public void addHouse(House house) {
        houses.add(house);
    }

    public ArrayList<User> getUsers() {
        return this.users;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public ArrayList<Contract> getContracts() {
        return this.contracts;
    }

    public void addContract(Contract contract) {
        contracts.add(contract);
    }

    public void showAllHouses() {
        for (House house : houses) {
            //if (house.isForSale() || house.isForRent()) {
                System.out.println("ID: " + house.getId() + " Region: " + house.getRegion() + " For Sale: " + house.isForSale() + " For Rent: " + house.isForRent() + " ");
                System.out.printf("Price: %.0f\n", house.calculatePrice());
                System.out.println("...........................");
            //}
        }
    }

    public void showHousesForSale() {
        for (House house : houses) {
            if (house.isForSale()) {
                System.out.println("ID: " + house.getId() + " Region: " + house.getRegion() + " For Sale: " + house.isForSale() + " For Rent: " + house.isForRent() + " ");
                System.out.printf("Price: %.0f\n", house.calculatePrice());
            }
        }
    }

    public void showHousesForRent() {
        for (House house : houses) {
            if (house.isForRent()) {
                System.out.println("ID: " + house.getId() + " Region: " + house.getRegion() + " For Sale: " + house.isForSale() + " For Rent: " + house.isForRent()+ " ");
                System.out.printf("Price: %.0f\n", house.calculatePrice());
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

    public void findMostExpensiveHouse() {
        if (houses.isEmpty()) {
            return;
        }
        House mostExpensive = houses.get(0);
        for (House house : houses) {
            if (house.calculatePrice() > mostExpensive.calculatePrice()) {
                mostExpensive = house;
            }
        }
        System.out.println("Most Expensive House: " + mostExpensive);
        System.out.printf("Price: %f0\n" + mostExpensive.calculatePrice());
    }

    public House findHouseById(int id) {

        for (House house : houses) {

            if (house.getId() == id) {
                return house;
            }

        }

        return null;
    }

    public void showHouseById(int id)  {

        House house = findHouseById(id);

        if (house != null) {
            System.out.println(house);
        } else {
            System.out.println("House not found\n");
        }
    }

    public User findUserByUsername(String username) {

        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }

        return null;
    }

    public User findUserById(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }

        return null;
    }

    public User signUp(String username, String password, double budget) {

        if (findUserByUsername(username) != null) {
            System.out.println("Username already exists.");
            return null;
        }

        User newUser = new User(username, password, budget,  User.UserRole.NORMAL);

        users.add(newUser);

        System.out.println("User created successfully.");

        return newUser;
    }

    public User logIn(String username, String password) {

        User user = findUserByUsername(username);

        if (user == null) {
            System.out.println("Invalid username or password.");
            return null;
        }

        if (user.getRole() == User.UserRole.AGENCY) {
            System.out.println("Agency cannot log in.");
            return null;
        }

        String inputHash = HashUtil.hashPassword(password);

        if (inputHash.equals(user.getPasswordHash())) {
            System.out.println("User successfully logged in.");
            return user;
        }

        System.out.println("Invalid username or password.");
        return null;
    }


    public boolean buyHouse(int houseId, User buyer) {

        House house = findHouseById(houseId);

        if (house == null) {
            System.out.println("House not found.");
            return false;
        }

        if (!house.isForSale()) {
            System.out.println("This house is not for sale.");
            return false;
        }

        if (house.getOwnerName().equals(buyer.getUsername())) {
            System.out.println("You already own this house.");
            return false;
        }

        double price = house.calculatePrice();

        if (buyer.getBudget() < price) {
            System.out.println("Not enough budget.");
            return false;
        }

        buyer.setBudget(buyer.getBudget() - price);

        int sellerId = -1;

        User seller = findUserByUsername(house.getOwnerName());

        if (seller != null) {
            seller.removeOwnedHouse(houseId);
            sellerId = seller.getId();
            seller.setBudget(seller.getBudget() + price);
        }

        house.setOwnerName(buyer.getUsername());
        house.setForSale(false);
        house.setForRent(false);
        house.setRenterName("");

        buyer.addOwnedHouse(houseId);

        Contract contract = new Contract(
                house.getId(),
                buyer.getId(),
                sellerId,
                ContractType.SALE,
                price
        );

        contracts.add(contract);

        buyer.addContract(contract.getContractId());

        if (seller != null) {
            seller.addContract(contract.getContractId());
        }

        System.out.println("House purchased successfully.");
        return true;
    }

    public boolean rentHouse(int houseId, User renter) {

        House house = findHouseById(houseId);

        if (house == null) {
            System.out.println("House not found.");
            return false;
        }

        if (!house.isForRent()) {
            System.out.println("This house is not for rent.");
            return false;
        }

        if (house.getOwnerName().equals(renter.getUsername())) {
            System.out.println("You cannot rent your own house.");
            return false;
        }

        if (!house.getRenterName().equals("")) {
            System.out.println("House is already rented.");
            return false;
        }

        double rentPrice = house.calculateMonthlyRent();

        if (renter.getBudget() < rentPrice) {
            System.out.println("Not enough budget.");
            return false;
        }

        renter.setBudget(renter.getBudget() - rentPrice);

        house.setRenterName(renter.getUsername());

        renter.addRentedHouse(houseId);

        house.setForSale(false);
        house.setForRent(false);

        User owner = findUserByUsername(house.getOwnerName());
        int ownerId = -1;

        if (owner != null) {
            ownerId = owner.getId();
        }

        Contract contract = new Contract(
                house.getId(),
                renter.getId(),
                ownerId,
                ContractType.RENT,
                rentPrice
        );

        contracts.add(contract);

        if (owner != null && owner.getRole() != User.UserRole.AGENCY) {
            owner.setBudget(owner.getBudget() + rentPrice);
        }

        renter.addContract(contract.getContractId());

        if (owner != null) {
            owner.addContract(contract.getContractId());
        }


        System.out.println("House rented successfully.");

        return true;
    }

    public boolean instantSellToAgency(int sellerId, int houseId) {

        User seller = findUserById(sellerId);

        if (seller == null) {
            System.out.println("Seller not found.");
            return false;
        }

        House house = findHouseById(houseId);

        if (house == null) {
            System.out.println("House not found.");
            return false;
        }

        if (!seller.getOwnedHouses().contains(houseId)) {
            System.out.println("This house is not Yours.");
            return false;
        }

        User agency = findAgencyUser();

        if (agency == null) {
            System.out.println("Agency user not found.");
            return false;
        }

        if (house.getRenterName() != null && !house.getRenterName().isEmpty()) {
            System.out.println("Cannot instant sell a rented house.");
            return false;
        }

        double originalPrice = house.calculatePrice();

        double instantPrice = originalPrice * 0.9;

        seller.setBudget(seller.getBudget() + instantPrice);

        seller.removeOwnedHouse(houseId);

        agency.addOwnedHouse(houseId);

        house.setOwnerName(agency.getUsername());

        house.setForSale(true);
        house.setForRent(true);

        Contract contract = new Contract(
                houseId,
                agency.getId(),
                seller.getId(),
                ContractType.INSTANT_SALE,
                instantPrice
        );

        contracts.add(contract);

        seller.addContract(contract.getContractId());

        agency.addContract(contract.getContractId());

        System.out.println("Instant sale completed successfully.");

        return true;
    }

    public User findAgencyUser() {

        for (User user : users) {

            if (user.getRole() == User.UserRole.AGENCY) {
                return user;
            }
        }

        return null;
    }

    public boolean specialBuy(int buyerId, int houseId) {
        User buyer = findUserById(buyerId);
        House house = findHouseById(houseId);

        if (buyer == null || house == null) {
            System.out.println("Buyer or House not found.");
            return false;
        }

        User currentOwner = findUserByUsername(house.getOwnerName());

        if (currentOwner != null && currentOwner.getId() == buyerId) {
            System.out.println("You already own this house!");
            return false;
        }

        double specialPrice = house.calculatePrice() * 2;

        if (buyer.getBudget() < specialPrice) {
            System.out.println("You're poor!");
            return false;
        }


        buyer.setBudget(buyer.getBudget() - specialPrice);
        if (currentOwner != null) {
            currentOwner.setBudget(currentOwner.getBudget() + specialPrice);
        }

        if (currentOwner != null) {
            currentOwner.removeOwnedHouse(houseId);
        }
        buyer.addOwnedHouse(houseId);

        house.setOwnerName(buyer.getUsername());
        house.setForSale(false);
        house.setForRent(false);

        Contract contract = new Contract(
                houseId,
                buyerId,
                (currentOwner != null) ? currentOwner.getId() : -1,
                ContractType.SPECIAL_BUY,
                specialPrice
        );
        contracts.add(contract);

        buyer.addContract(contract.getContractId());
        if (currentOwner != null) {
            currentOwner.addContract(contract.getContractId());
        }

        System.out.println("Special Buy successful! ");
        return true;
    }

    public Contract findContractById(int id) {
        for (Contract contract : contracts) {
            if (contract.getContractId() == id) {
                return contract;
            }
        }
        return null;
    }

    public void cancelContract(int contractId, User user) {

        Contract c =  findContractById(contractId);
        if (c.getType() != ContractType.RENT) {
            System.out.println("You can't cancel this contract!");
            return;
        }
        if (c == null) {
            System.out.println("Contract not found.");
            return;
        }
        if (c.getBuyerId() != user.getId()) {
            System.out.println("This contract is not Yours.");
            return;
        }
        House h = findHouseById(c.getHouseId());

        double penalty = h.calculateMonthlyRent() * 0.2;
        if (user.getBudget() < penalty) {
            System.out.println("You're poor!");
            return;
        }

        user.setBudget(user.getBudget() - penalty);

        h.setRenterName("");

        user.removeRentedHouse(h.getId());

        user.removeContract(contractId);

        findUserById(c.getSellerId()).removeContract(contractId);

        c.setType(ContractType.CANCEL);

        System.out.println("Canceled this contract!");
    }

    public void saleHouse(int houseId, User user) {
        House house = findHouseById(houseId);
        if (house == null) {
            System.out.println("House not found.");
            return;
        }
        if (house.getOwnerName() != user.getUsername()) {
            System.out.println("This House is not Yours.");
            return;
        }

        house.setForSale(true);
        System.out.println("Done!");
    }

    public void forRentHouse(int houseId, User user) {
        House house = findHouseById(houseId);
        if (house == null) {
            System.out.println("House not found.");
            return;
        }
        if (house.getOwnerName() != user.getUsername()) {
            System.out.println("This House is not Yours.");
            return;
        }

        house.setForRent(true);
        System.out.println("Done!");
    }

}