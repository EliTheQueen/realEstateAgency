package data;

import model.Contract;
import model.House;
import model.User;

import java.io.*;
import java.util.ArrayList;

public class DataManager {

    public static void saveHouses(ArrayList<House> houses, String filename) {

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {

            oos.writeObject(houses);

        }  catch (IOException e) {

            System.err.println("error while saving: " + e.getMessage());
        }
    }

    public static ArrayList<House> loadHouses(String filename) {
        ArrayList<House> houses = new ArrayList<>();

        File file = new File(filename);
        if (!file.exists()) {
            return houses;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {

            houses = (ArrayList<House>) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {

            System.err.println("error while loading data: " + e.getMessage());

        }
        return houses;
    }

    public static void saveUsers(ArrayList<User> users, String filename) {

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {

            oos.writeObject(users);

        }  catch (IOException e) {

            System.err.println("error while saving: " + e.getMessage());
        }
    }

    public static ArrayList<User> loadUsers(String filename) {
        ArrayList<User> users = new ArrayList<>();

        File file = new File(filename);
        if (!file.exists()) {
            return users;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {

            users = (ArrayList<User>) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {

            System.err.println("error while loading data: " + e.getMessage());

        }
        return users;
    }

    public static void saveContracts(ArrayList<Contract> contracts, String filename) {

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {

            oos.writeObject(contracts);

        }  catch (IOException e) {

            System.err.println("error while saving: " + e.getMessage());
        }
    }

    public static ArrayList<Contract> loadContracts(String filename) {

        ArrayList<Contract> contracts = new ArrayList<>();

        File file = new File(filename);
        if (!file.exists()) {
            return contracts;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {

            contracts = (ArrayList<Contract>) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {

            System.err.println("error while loading data: " + e.getMessage());

        }
        return contracts;
    }

}
