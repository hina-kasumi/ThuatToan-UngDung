package ttud.buoi2;

public class KWIC {
    public static void main(String[] args) {
        String text = "ABRACADABRA!";
        String search = "AB";
        int gap = 3;

        SuffixArrayX suffixArrayX = new SuffixArrayX(text);
        int index = suffixArrayX.rank(search);

        for (int i = index; i < text.length(); i++) {
            String suffix = suffixArrayX.select(i);
            if (!suffix.startsWith(search))
                break;
            int j = suffixArrayX.index(i);

            int lo = Math.max(0, j - gap);
            int hi = Math.min(j + search.length() + gap, text.length());

            System.out.println(text.substring(lo, hi));
        }
    }
}
