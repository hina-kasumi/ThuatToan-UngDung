package ttud.buoi1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class LSD {
    private static final int BITS_PER_BYTE = 8;

    public static void sort(String[] a, int w) {
        int n = a.length;
        int R = 65536;

        String[] aux = new String[n];
        for (int d = w - 1; d >= 0; d--) {
            int[] count = new int[R + 1];
            for (String string : a) count[string.charAt(d) + 1]++;

            // compute cumulates
            for (int r = 0; r < R; r++)
                count[r + 1] += count[r];

            // move data
            for (String s : a) aux[count[s.charAt(d)]++] = s;

            // copy back
            System.arraycopy(aux, 0, a, 0, n);
        }
    }

    public static void sort(int[] a) {
        final int BITS = 32;                 // each int is 32 bits
        final int R = 1 << BITS_PER_BYTE;    // each byte is between 0 and 255
        final int MASK = R - 1;              // 0xFF
        final int w = BITS / BITS_PER_BYTE;  // each int is 4 bytes

        int n = a.length;
        int[] aux = new int[n];

        for (int d = 0; d < w; d++) {

            // compute frequency counts
            int[] count = new int[R + 1];
            for (int j : a) {
                int c = (j >> BITS_PER_BYTE * d) & MASK;
                count[c + 1]++;
            }

            // compute cumulates
            for (int r = 0; r < R; r++)
                count[r + 1] += count[r];

            // for most significant byte, 0x80-0xFF comes before 0x00-0x7F
            if (d == w - 1) {
                int shift1 = count[R] - count[R / 2];
                int shift2 = count[R / 2];
                for (int r = 0; r < R / 2; r++)
                    count[r] += shift1;
                for (int r = R / 2; r < R; r++)
                    count[r] -= shift2;
            }

            // move data
            for (int j : a) {
                int c = (j >> BITS_PER_BYTE * d) & MASK;
                aux[count[c]++] = j;
            }

            // optimization: swap a[] and aux[] references instead of copying
            // (since w is even, the argument a[] to sort() will be the array
            // with the sorted integers)
            int[] temp = a;
            a = aux;
            aux = temp;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("app/src/main/java/ttud/buoi2/student.csv");
        Scanner sc = new Scanner(file);
        List<Integer> list = new ArrayList<>();
        while (sc.hasNextLine()) {
            String line =  sc.nextLine().trim();
            list.add(Integer.parseInt(line.split(",")[0]));
        }
        sc.close();

        int[] msv = list.stream().mapToInt(Integer::intValue).toArray();
        sort(msv);
        Arrays.stream(msv).forEach(System.out::println);
    }
}
