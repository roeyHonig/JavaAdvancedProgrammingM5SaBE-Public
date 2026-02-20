import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Util {
    public static ArrayList<MenuItem> loadMenuFromFile(String fileName) {
        ArrayList<MenuItem> menu = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            String line;

            while ((line = br.readLine()) != null) {

                String[] parts = line.split(",");

                int id = Integer.parseInt(parts[0]);
                String type = parts[1];
                String description = parts[2];
                int price = Integer.parseInt(parts[3]);

                menu.add(new MenuItem(id, type, description, price));
            }

        } catch (IOException e) {
            System.out.println("error reading menu file " + e.getLocalizedMessage());
            e.printStackTrace();
        }

        return menu;
    }
}
