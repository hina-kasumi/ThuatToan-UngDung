package ttud.buoi2;

public class LongestRepeatedSubstring {
    public static void main(String[] args) {
        int maxCommonPrefixLength = 0;
        int index = 0;
        String query = "ABRACADABRA!";
        SuffixArrayX suffixArrayX = new SuffixArrayX(query);

        for (int i = 1; i < query.length(); i++) {
            int len = suffixArrayX.lcp(i);
            if (len > maxCommonPrefixLength){
                maxCommonPrefixLength = len;
                index = i;
            }
        }

        int lo = suffixArrayX.index(index);
        System.out.println(maxCommonPrefixLength);
        System.out.println(query.substring(lo, lo + maxCommonPrefixLength));
    }
}
