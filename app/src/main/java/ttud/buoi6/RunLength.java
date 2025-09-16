package ttud.buoi6;

public class RunLength {
    private static final int R = 256;
    private static final int LG_R = 8;
    private static final BinaryOut BINARY_OUT = new BinaryOut("app/src/main/java/ttud/buoi6/files/compressed.txt");
    private static final BinaryIn BINARY_IN = new BinaryIn("app/src/main/java/ttud/buoi6/files/origin.txt");

    public static void expand(String fileInput, String fileOutput) {
        final BinaryIn BINARY_IN = new BinaryIn(fileInput);
        final BinaryOut BINARY_OUT = new BinaryOut(fileOutput);
        boolean b = false;
        while (!BINARY_IN.isEmpty()) {
            int run = BINARY_IN.readInt(LG_R);
            for (int i = 0; i < run; i++)
                BINARY_OUT.write(b);
            b = !b;
        }
        BINARY_OUT.close();
    }

    public static void compress(String fileInput, String fileOutput) {
        final BinaryIn BINARY_IN = new BinaryIn(fileInput);
        final BinaryOut BINARY_OUT = new BinaryOut(fileOutput);
        char run = 0;
        boolean old = false;
        while (!BINARY_IN.isEmpty()) {
            boolean b = BINARY_IN.readBoolean();
            if (b != old) {
                BINARY_OUT.write(run, LG_R);
                run = 1;
                old = !old;
            } else {
                if (run == R - 1) {
                    BINARY_OUT.write(run, LG_R);
                    run = 0;
                    BINARY_OUT.write(run, LG_R);
                }
                run++;
            }
        }
        BINARY_OUT.write(run, LG_R);
        BINARY_OUT.close();
    }

    public static void main(String[] args) {
        expand(
                "app/src/main/java/ttud/buoi6/files/q32x48.bin",
                "app/src/main/java/ttud/buoi6/files/expanded.txt"
        );
    }
}
