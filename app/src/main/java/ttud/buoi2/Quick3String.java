package ttud.buoi2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Quick3String {
    private static final int CUTOFF = 15;

    public static void sort(String[] a) {
        sort(a, 0, a.length - 1, 0);
    }

    private static void sort(String[] a, int lo, int hi, int d) {
        if (hi <= lo + CUTOFF) {
            insertion(a, lo, hi, d);
            return;
        }

        int lt = lo, gt = hi;
        int v = charAt(a[lo], d);
        int i = lo + 1;
        while (i <= gt) {
            int t = charAt(a[i], d);
            if (t < v)
                exch(a, lt++, i++);
            else if (t > v)
                exch(a, i, gt--);
            else
                i++;
        }

        sort(a, lo, lt - 1, d);
        if (v >= 0) {
            sort(a, lt, gt, d + 1);
        }
        sort(a, gt + 1, hi, d);
    }

    private static void insertion(String[] a, int lo, int hi, int d) {
        for (int i = lo; i <= hi; i++)
            for (int j = i; j > lo && less(a[j], a[j - 1], d); j--)
                exch(a, j, j - 1);
    }

    private static boolean less(String v, String w, int d) {
        for (int i = d; i < Math.min(v.length(), w.length()); i++) {
            if (v.charAt(i) < w.charAt(i))
                return true;
            if (v.charAt(i) > w.charAt(i))
                return false;
        }
        return v.length() < w.length();
    }

    private static int charAt(String s, int d) {
        assert d >= 0 && d <= s.length();
        if (d == s.length())
            return -1;
        return s.charAt(d);
    }

    // exchange a[i] and a[j]
    private static void exch(String[] a, int i, int j) {
        String temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static void main(String[] args) throws FileNotFoundException {
        List<String> students = new ArrayList<>();
        File file = new File("app/src/main/java/ttud/buoi2/student.csv");
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            String[] line = sc.nextLine().split(",");
            String fullname = line[1].trim();
            students.add(fullname);
        }
        sc.close();

        String[] list = new String[students.size()];
        for (int i = 0; i < list.length; i++) {
            list[i] = students.get(i);
        }
        sort(list);

        for (String student : list) {
            System.out.println(student);
        }
    }
}
