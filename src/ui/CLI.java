package ui;

import data.DataManager;
import model.*;
import service.RealEstateAgency;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CLI {

    private RealEstateAgency system;
    private Scanner scanner;
    private User currentUser;

    public CLI(RealEstateAgency system) {
        this.system = system;
        this.scanner = new Scanner(System.in);
    }

    public void start() {

        while (true) {

            if(currentUser == null){
                showLoginMenu();

                int choice = readInt("Choice: ");

                switch (choice) {
                    case 1:
                        signUp();
                        saveData();
                        break;

                    case 2:
                        logIn();
                        break;

                    case 3:
                        System.out.println("Bye Bye!");
                        saveData();
                        return;

                    default:
                        System.out.println("Invalid option");
                }

            } else {
                showMainMenu();

                int choice = readInt("Choice: ");

                switch (choice) {

                    case 1:
                        searchHouseById();
                        break;

                    case 2:
                        searchHouseByRegion();
                        break;

                    case 3:
                        system.findMostExpensiveHouse();
                        break;

                    case 4:
                        myOwnedHouse();
                        break;

                    case 5:
                        myRentedHouse();
                        break;

                    case 6:
                        System.out.printf("My Budget: %.2f",
                                currentUser.getBudget());
                        break;

                    case 7:
                        myContracts();;
                        chooseContract();
                        break;

                    case 8:
                        system.showAllHouses();
                        chooseHouse();
                        break;

                    case 9:
                        system.showHousesForSale();
                        chooseHouse();
                        break;

                    case 10:
                        system.showHousesForRent();
                        chooseHouse();
                        break;

                    case 11:
                        buyHouse();
                        saveData();
                        break;

                    case 12:
                        rentHouse();
                        saveData();
                        break;

                    case 13:
                        addHouse();
                        saveData();
                        break;

                    case 14:
                        saleHouse();
                        saveData();
                        break;

                    case 15:
                        forRentHouse();
                        saveData();
                        break;

                    case 17:
                        cancelContract();
                        saveData();
                        break;

                    case 16:
                        instantSell();
                        saveData();
                        break;

                    case 18:
                        specialBuy();
                        saveData();
                        break;

                    case 19:
                        currentUser = null;
                        saveData();
                        break;

                    case 20:
                        System.out.println("Bye Bye!");
                        saveData();
                        return;

                    default:
                        System.out.println("Invalid option");
                }
            }
        }
    }

    private void showMainMenu() {

        System.out.println("\n===== ✩Queen✩ Real Estate Agency =====");
        System.out.printf("My Budget: %.2f | My ID: %d%n",
                currentUser.getBudget(),
                currentUser.getId());

        System.out.println("1. Search by ID");
        System.out.println("2. Search by Location");
        System.out.println("3. Find Most Expensive Property");
        System.out.println("4. My Own Properties");
        System.out.println("5. My Rent Properties");
        System.out.println("6. My Budget");
        System.out.println("7. My Contracts");
        System.out.println("8. Show All Properties");
        System.out.println("9. Show Properties For Sale");
        System.out.println("10. Show Properties For Rent");
        System.out.println("11. Buy Properties");
        System.out.println("12. To Rent Properties");
        System.out.println("13. Add Properties");
        System.out.println("14. Sell a Property");
        System.out.println("15. Rent a Property");
        System.out.println("16. Instant Sell To Agency");
        System.out.println("17. Cancel Contract");
        System.out.println("18. Special Buy");
        System.out.println("19. Log Out");
        System.out.println("20. Exit");

    }
    private void showLoginMenu() {
        System.out.println("\n===== ✩Queen✩ Real Estate System =====");
        System.out.println("1. Sign Up");
        System.out.println("2. Log In");
        System.out.println("3. Exit");

    }

    private void addHouse() {

        System.out.println("1 Apartment");
        System.out.println("2 Villa");
        System.out.println("3 Penthouse");

        int type = readInt("Type: (0 to back)");
        List<Integer>  list = Arrays.asList(1, 2, 3);
        if (!list.contains(type)) {
            System.out.println("Invalid type");
            return;
        }
        if (backToPreviousMenu(type)) {
            return;
        }

        switch (type) {
            case 1:
                addApartment();
                break;

            case 2:
                addVilla();
                break;

            case 3:
                addPenthouse();
                break;
        }

    }

    private BaseHouseInfo getBaseInformation() {

        double area;
        int region;
        boolean sale;
        boolean rent;

        while (true) {
            System.out.print("Area: ");

            if (scanner.hasNextDouble()) {
                area = scanner.nextDouble();

                if (area > 0) {
                    break;
                } else {
                    System.out.println("Area must be greater than 0.");
                }

            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }

        while (true) {
            System.out.print("Region (1-4): ");

            if (scanner.hasNextInt()) {
                region = scanner.nextInt();

                if (region > 0 && region < 5) {
                    break;
                } else {
                    System.out.println("Invalid region. Enter a number between 0 and 5.");
                }

            } else {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.next();
            }
        }

        while (true) {
            System.out.print("Is it for sale? (yes/no): ");

            String s = scanner.next().trim().toLowerCase();

            if (s.equals("yes") || s.equals("no")) {
                sale = s.equals("yes");
                break;
            } else {
                System.out.println("Please enter yes or no.");
            }
        }

        while (true) {
            System.out.print("Is it for rent? (yes/no): ");

            String r = scanner.next().trim().toLowerCase();

            if (r.equals("yes") || r.equals("no")) {
                rent = r.equals("yes");
                break;
            } else {
                System.out.println("Please enter yes or no.");
            }
        }

        return new BaseHouseInfo(area, region, sale, rent);

    }


    private void addApartment() {

        BaseHouseInfo info = getBaseInformation();

        if (info == null) {
            System.out.println("Invalid input");
            return;
        }

        int bedrooms;

        do {
            bedrooms = readInt("Bedrooms: ");

            if (bedrooms <= 0) {
                System.out.println("Bedrooms must be greater than 0.");
            }
        } while (bedrooms <= 0);


        int floor;

        do {
            floor = readInt("Floor: ");
            if (floor <= 0) {
                System.out.println("Floor must be greater than 0.");
            }
        }  while (floor <= 0);


        House apartment = new Apartment(
                info.getArea(),
                info.getRegion(),
                "",
                "",
                info.isSale(),
                info.isRent(),
                bedrooms,
                floor
        );

        apartment.setOwnerName(currentUser.getUsername());

        system.addHouse(apartment);

        currentUser.addOwnedHouse(apartment.getId());

        System.out.println("Apartment added.");
    }


    private void addVilla() {

        BaseHouseInfo info = getBaseInformation();


        if (info == null) {
            System.out.println("Invalid input");
            return;
        }
        double yard;

        do {
            yard = readDouble("Yard Area: ");
            if (yard <= 0) {
                System.out.println("Yard must be greater than 0.");
            }

        }  while (yard <= 0);


        int floors;

        do {
            floors = readInt("Floors: ");
            if (floors <= 0) {
                System.out.println("Floors must be greater than 0.");
            }
        }   while (floors <= 0);


        House villa = new Villa(
                info.getArea(),
                info.getRegion(),
                "",
                "",
                info.isSale(),
                info.isRent(),
                yard,
                floors);

        villa.setOwnerName(currentUser.getUsername());

        system.addHouse(villa);

        currentUser.addOwnedHouse(villa.getId());

        System.out.println("Villa added.");
    }

    private void addPenthouse() {

        BaseHouseInfo info = getBaseInformation();

        if (info == null) {
            System.out.println("Invalid input");
            return;
        }
        double terraceArea;

        do {
            terraceArea = readDouble("Terrace Area: ");
            if (terraceArea <= 0) {
                System.out.println("Terrace must be greater than 0.");
            }
        } while (terraceArea <= 0);

        boolean hasPool = readBoolean("Has Pool? (Enter true for yes and false for no) ");

        int floorNumber;

        do {
            floorNumber = readInt("Floor: ");
            if (floorNumber <= 0) {
                System.out.println("Floor must be greater than 0.");
            }
        }  while (floorNumber <= 0);

        House penthouse = new Penthouse(
                info.getArea(),
                info.getRegion(),
                "",
                "",
                info.isSale(),
                info.isRent(),
                terraceArea,
                hasPool,
                floorNumber);

        penthouse.setOwnerName(currentUser.getUsername());

        system.addHouse(penthouse);

        currentUser.addOwnedHouse(penthouse.getId());

        System.out.println("Penthouse  added.");
    }

    private void signUp() {

        System.out.print("Username: ");
        String username = scanner.next();

        System.out.print("Password: ");
        String password = scanner.next();

        System.out.print("Confirm password: ");
        String confirmation = scanner.next();

        if (!password.equals(confirmation)) {
            System.out.println("Passwords do not match.");
            return;
        }

        double budget;

        while (true) {
            budget = readDouble("Budget: ");

            if (budget >= 0) {
                break;
            }

            System.out.println("Budget cannot be negative.");
        }

        User user = system.signUp(username, password, budget);

        if (user != null) {
            currentUser = user;
        }
    }

    private void logIn() {

        System.out.println("Username: ");
        String username = scanner.next();

        System.out.println("Password: ");
        String password = scanner.next();

        User user = system.logIn(username, password);
        if (user != null) {
            currentUser = user;
            System.out.println("Welcome " + currentUser.getUsername());
        }
    }

    private void buyHouse() {

        int houseId = readInt("House ID: (0 to back) ");

        if (backToPreviousMenu(houseId)) {
            return;
        }

        if (system.buyHouse(houseId, currentUser)) {

            System.out.println("Yeahhh!");

        }  else {
            System.out.println("Try again!");
        }

    }

    private void rentHouse() {

        int houseId = readInt("House ID: (0 to back) ");

        if (backToPreviousMenu(houseId)) {
            return;
        }
        if (system.rentHouse(houseId, currentUser)) {
            System.out.println("Yeahhh!");
        }   else {
            System.out.println("Try again!");
        }
    }

    private void chooseHouse() {
        int houseId = readInt("Choose House: (0 to back) ");
        if (backToPreviousMenu(houseId)) {
            return;
        }
        House house = system.findHouseById(houseId);
        if (house != null) {
            System.out.println(house);
        }  else {
            System.out.println("This House doesn't exist!");
        }
    }

    private void myOwnedHouse() {
        for (int id : currentUser.getOwnedHouses()) {
            System.out.println("House ID: " + id);
        }
        if (currentUser.getOwnedHouses().size() == 0) {
            System.out.println("You have no House yet!");
            return;
        }
        chooseHouse();
    }

    private void myRentedHouse() {
        for (int id : currentUser.getRentedHouses()) {
            System.out.println("House ID: " + id);
        }
        if (currentUser.getRentedHouses().size() == 0) {
            System.out.println("You have no House yet!");
            return;
        }
        chooseHouse();

    }

    private void searchHouseById() {

        int houseId = readInt("Choose House ID (0 to back): ");

        if (backToPreviousMenu(houseId)) {
            return;
        }

        House house = system.findHouseById(houseId);

        if (house != null) {
            System.out.println("House not found.");
        }

        System.out.println(house);
    }

    private void instantSell() {
        int id = readInt("House ID: (0 to back) ");
        if (backToPreviousMenu(id)) {
            return;
        }
        system.instantSellToAgency(currentUser.getId(), id);
    }

    private void specialBuy() {
        int houseId = readInt("House ID: (0 to back) ");
        if (backToPreviousMenu(houseId)) {
            return;
        }

        system.specialBuy(currentUser.getId(), houseId);
    }

    private void myContracts() {

        for (int id : currentUser.getContracts()) {

            Contract myContract = system.findContractById(id);
            System.out.println("ID: " + myContract.getContractId() + " seller id: " + myContract.getBuyerId() + " type: " + myContract.getType());
            System.out.println("...........................");


        }
    }
    private void chooseContract() {
        int contractId = readInt("Choose contract: (0 to back)");

        if (backToPreviousMenu(contractId)) {
            return;
        }
        Contract contract = system.findContractById(contractId);

        if (contract != null) {
            if (contract.getBuyerId() != currentUser.getId() && contract.getSellerId() != currentUser.getId()) {
                System.out.println("This contract is not Yours.");
                return;
            }
            System.out.println(contract);
        }  else {
            System.out.println("This contract doesn't exist!");
        }
    }

    private void searchHouseByRegion() {
        int region = readInt("enter Region number: (0 to back): ");
        if (backToPreviousMenu(region)) {
            return;
        }
        if (region < 5 && region > 0) {
           system.showHousesByRegion(region);
        }else{
            System.out.println("Invalid Region!");
        }
    }

    private void saveData() {
        DataManager.saveHouses(system.getHouses(), "houses.dat");
        DataManager.saveUsers(system.getUsers(), "users.dat");
        DataManager.saveContracts(system.getContracts(), "contracts.dat");
    }

    private void cancelContract() {
        int contractId = readInt("Contract ID: (0 to back): ");
        if (backToPreviousMenu(contractId)) {
            return;
        }
        system.cancelContract(contractId, currentUser);
    }

    private void saleHouse() {
        int houseId = readInt("House ID (0 to back): ");
        if (backToPreviousMenu(houseId)) {
            return;
        }
        system.saleHouse(houseId, currentUser);
    }

    private void forRentHouse() {
        int houseId = readInt("House ID: (0 to back): ");

        if (backToPreviousMenu(houseId)) {
            return;
        }

        system.forRentHouse(houseId, currentUser);
    }

    private boolean backToPreviousMenu(int input) {
        return input == 0;
    }

    private int readInt(String message) {

        while (true) {

            System.out.print(message);

            if (scanner.hasNextInt()) {
                return scanner.nextInt();
            } else {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.next();
            }
        }
    }

    private double readDouble(String message) {

        while (true) {

            System.out.print(message);

            if (scanner.hasNextDouble()) {
                return scanner.nextDouble();
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }
    }

    private boolean readBoolean(String message) {

        while (true) {

            System.out.print(message);

            String input = scanner.next().trim().toLowerCase();

            if (input.equals("true")) {
                return true;
            }

            if (input.equals("false")) {
                return false;
            }

            System.out.println("Please enter true or false.");
        }
    }

    private String readYesNo(String message) {

        while (true) {

            System.out.print(message);

            String input = scanner.next().trim().toLowerCase();

            if (input.equals("yes") || input.equals("no")) {
                return input;
            }

            System.out.println("Please enter yes or no.");
        }
    }

}
