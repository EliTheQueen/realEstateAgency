package ui;

import data.DataManager;
import model.*;
import service.RealEstateAgency;
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

                int choice = scanner.nextInt();

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

                int choice = scanner.nextInt();

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

                    case 13:
                        addHouse();
                        saveData();
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



                    case 20:
                        System.out.println("Bye Bye!");
                        saveData();
                        return;

                    case 11:
                        buyHouse();
                        saveData();
                        break;

                    case 12:
                        rentHouse();
                        saveData();
                        break;

                    case 6:
                        System.out.printf("My Budget: %.2f",
                                currentUser.getBudget());
                        break;

                    case 5:
                        myRentedHouse();
                        break;



                    case 19:
                        currentUser = null;
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

                    case 7:
                        myContracts();
                        break;



                    case 17:
                        cancelContract();
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

        System.out.print("Choice: ");
    }
    private void showLoginMenu() {
        System.out.println("\n===== ✩Queen✩ Real Estate System =====");
        System.out.println("1. Sign Up");
        System.out.println("2. Log In");
        System.out.println("3. Exit");
        System.out.print("Choice: ");

    }

    private void addHouse() {

        System.out.println("1 Apartment");
        System.out.println("2 Villa");
        System.out.println("3 Penthouse");

        System.out.print("Type: ");

        int type = scanner.nextInt();

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

        BaseHouseInfo info = new BaseHouseInfo();

        System.out.print("Area: ");
        info.area = scanner.nextDouble();

        System.out.print("Region: ");
        info.region = scanner.nextInt();

        System.out.print("Is it for sale? ");
        String s = scanner.next();
        info.sale = s.equalsIgnoreCase("yes");

        System.out.print("Is it for rent? ");
        String r = scanner.next();
        info.rent = r.equalsIgnoreCase("yes");

        return info;
    }

    private void addApartment() {

        BaseHouseInfo info = getBaseInformation();

        System.out.print("Bedrooms: ");
        int bedrooms = scanner.nextInt();

        System.out.print("Floor: ");
        int floor = scanner.nextInt();

        House apartment = new Apartment(
                info.area,
                info.region,
                "",
                "",
                info.sale,
                info.rent,
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

        System.out.print("Yard Area: ");
        double yard = scanner.nextDouble();

        System.out.print("Floors: ");
        int floors = scanner.nextInt();

        House villa = new Villa(
                info.area,
                info.region,
                "",
                "",
                info.sale,
                info.rent,
                yard,
                floors);

        villa.setOwnerName(currentUser.getUsername());

        system.addHouse(villa);

        currentUser.addOwnedHouse(villa.getId());

        System.out.println("Villa added.");
    }

    private void addPenthouse() {

        BaseHouseInfo info = getBaseInformation();

        System.out.print("Terrace Area: ");
        double terraceArea = scanner.nextDouble();

        System.out.print("Has Pool? (Enter true for yes and false for no) ");
        boolean hasPool = scanner.nextBoolean();

        System.out.print("Floor Number: ");
        int floorNumber = scanner.nextInt();

        House penthouse = new Penthouse(
                info.area,
                info.region,
                "",
                "",
                info.sale,
                info.rent,
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

        System.out.print("Budget: ");
        double budget = scanner.nextDouble();

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

        System.out.println("House ID: ");
        int houseId = scanner.nextInt();


        if (system.buyHouse(houseId, currentUser)) {

            System.out.println("Yeahhh!");

        }  else {
            System.out.println("Try again!");
        }

    }

    private void rentHouse() {

        System.out.println("House ID: ");
        int houseId = scanner.nextInt();

        if (system.rentHouse(houseId, currentUser)) {
            System.out.println("Yeahhh!");
        }   else {
            System.out.println("Try again!");
        }
    }

    private void chooseHouse() {
        System.out.println("Choose House: ");
        int houseId = scanner.nextInt();

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
        System.out.println("House ID: ");
        int houseId = scanner.nextInt();

        System.out.println(system.findHouseById(houseId));
    }

    private void instantSell() {
        System.out.println("House ID:");
        int id = scanner.nextInt();

        system.instantSellToAgency(currentUser.getId(), id);
    }

    private void specialBuy() {
        System.out.println("House ID:");
        int houseId = scanner.nextInt();

        system.specialBuy(currentUser.getId(), houseId);
    }

    private void myContracts() {

        for (int id : currentUser.getContracts()) {

            Contract c = system.findContractById(id);
            System.out.println(c);

        }
    }

    private void searchHouseByRegion() {
        System.out.println("Region:");
        int region = scanner.nextInt();

        if (region < 5 && region > 0) {
           system.showHousesByRegion(region);
        }
    }

    private void saveData() {
        DataManager.saveHouses(system.getHouses(), "houses.dat");
        DataManager.saveUsers(system.getUsers(), "users.dat");
        DataManager.saveContracts(system.getContracts(), "contracts.dat");
    }

    private void cancelContract() {
        System.out.println("Contract ID:");
        int contractId = scanner.nextInt();

        system.cancelContract(contractId, currentUser);
    }

    private void saleHouse() {
        System.out.println("House ID:");
        int houseId = scanner.nextInt();

        system.saleHouse(houseId, currentUser);
    }

    private void forRentHouse() {
        System.out.println("House ID:");
        int houseId = scanner.nextInt();

        system.forRentHouse(houseId, currentUser);
    }
}
