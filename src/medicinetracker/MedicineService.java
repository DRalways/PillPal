package medicinetracker;

import java.io.*;
import java.util.*;

public class MedicineService {
    private static final String FILE = "data/medicines.txt";

    public static void addMedicine(String name, String dosage) {
        try (FileWriter fw = new FileWriter(FILE, true)) {
            fw.write(name + "," + dosage + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Medicine> getMedicines() {
        List<Medicine> list = new ArrayList<>();
        File file = new File(FILE);
        if (!file.exists()) return list;

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String[] parts = sc.nextLine().split(",");
                if (parts.length == 2) list.add(new Medicine(parts[0], parts[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
