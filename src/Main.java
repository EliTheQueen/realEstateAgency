import model.House;
import model.Penthouse;
import model.Villa;
import model.RealEstateAgency;
import model.Apartment;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        RealEstateAgency system = new RealEstateAgency();

        House v = new Villa(1, 350, 1, "Ali", "", true, false, 200, 5);

        House p = new Penthouse(2, 180, 2, "Sara", "", true, false, 80, true, 12);

        system.addHouse(v);
        system.addHouse(p);

        system.showAllHouses();
    }

}