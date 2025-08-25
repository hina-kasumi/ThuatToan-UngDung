package ttud.buoi3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AvgPoint {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("app/src/main/java/ttud/buoi3/student.csv");
        Scanner sc = new Scanner(file);
        TST<Double> tst = new TST<>();
        List<String> names = new ArrayList<>();

        while (sc.hasNextLine()) {
            String[] line = sc.nextLine().split(",");
            String msv = line[0].trim();
            String name = line[1].trim();
            double point = Double.parseDouble(line[2].trim());
            tst.put(name, point);
            names.add(name);
        }

        int avg = 0;
        for (String name : names) {
            avg += tst.get(name);
        }
        avg /= names.size();
        System.out.println(avg);
    }
}
