import data.DataManager;
import model.*;
import service.RealEstateAgency;
import ui.CLI;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        RealEstateAgency system = new RealEstateAgency();

        CLI cli = new CLI(system);

        ArrayList<House> loadedHouses = DataManager.loadHouses("houses.dat");
        for (House h : loadedHouses) {
            system.addHouse(h);
        }
        House.updateId(loadedHouses);

        ArrayList<User> loadedUsers = DataManager.loadUsers("users.dat");
        for (User user : loadedUsers) {
            system.addUser(user);
        }
        User.updateNextId(loadedUsers);

        ArrayList<Contract> loadedContracts = DataManager.loadContracts("contracts.dat");
        for (Contract contract : loadedContracts) {
            system.addContract(contract);
        }
        Contract.updateNextId(loadedContracts);

        cli.start();

    }
}