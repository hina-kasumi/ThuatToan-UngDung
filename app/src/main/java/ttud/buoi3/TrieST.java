package ttud.buoi3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class TrieST<T> {
    private static final int R = 65536;

    private Node root;
    private int n;

    private static class Node {
        private Object val;
        private final Node[] next = new Node[R];
    }

    public T get(String key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        Node x = get(root, key, 0);
        if (x == null) return null;
        return (T) x.val;
    }

    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) return x;
        char c = key.charAt(d);
        return get(x.next[c], key, d + 1);
    }

    public boolean contains(String key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    public void put(String key, T value) {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");
        if (value == null) delete(key);
        else root = put(root, key, value, 0);
    }

    private Node put(Node x, String key, T val, int d) {
        if (x == null) x = new Node();
        if (d == key.length()) {
            if (x.val == null) n++;
            x.val = val;
            return x;
        }
        char c = key.charAt(d);
        x.next[c] = put(x.next[c], key, val, d + 1);
        return x;
    }

    public void delete(String key) {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");
        root = delete(root, key, 0);
    }

    private Node delete(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) {
            if (x.val != null) n--;
            x.val = null;
        } else {
            char c = key.charAt(d);
            x.next[c] = delete(x.next[c], key, d + 1);
        }

        // remove subtrie rooted at x if it is completely empty
        if (x.val != null) return x;
        for (int c = 0; c < R; c++)
            if (x.next[c] != null)
                return x;
        return null;
    }

    // trả về các khóa khớp với mẫu trong đó . là từ đại diện
    public Iterable<String> keysThatMatch(String pattern) {
        Queue<String> results = new LinkedList<>();
        collect(root, new StringBuilder(), pattern, results);
        return results;
    }

    private void collect(Node x, StringBuilder prefix, String pattern, Queue<String> results) {
        if (x == null) return;
        int d = prefix.length();
        if (d == pattern.length() && x.val != null)
            results.add(prefix.toString());
        if (d == pattern.length())
            return;
        char c = pattern.charAt(d);
        if (c == '.') {
            for (int ch = 0; ch < R; ch++) {
                prefix.append(Character.toChars(ch));
                collect(x.next[ch], prefix, pattern, results);
                prefix.deleteCharAt(prefix.length() - 1);
            }
        } else {
            prefix.append(c);
            collect(x.next[c], prefix, pattern, results);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }

    public Iterable<String> keys() {
        return keysWithPrefix("");
    }

    // trả về các key bắt đầu bằng prefix
    public Iterable<String> keysWithPrefix(String prefix) {
        Queue<String> results = new LinkedList<>();
        Node x = get(root, prefix, 0);
        collect(x, new StringBuilder(prefix), results);
        return results;
    }

    private void collect(Node x, StringBuilder prefix, Queue<String> results) {
        if (x == null) return;
        if (x.val != null) results.add(prefix.toString());
        for (int c = 0; c < R; c++) {
            prefix.append(Character.toChars(c));
            collect(x.next[c], prefix, results);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }

    // trả về khóa dài nhất bất đầu bằng tiên tố
    public String longestPrefixOf(String query) {
        if (query == null) throw new IllegalArgumentException("argument to longestPrefixOf() is null");
        int length = longestPrefixOf(root, query, 0, -1);
        if (length == -1) return null;
        else return query.substring(0, length);
    }

    private int longestPrefixOf(Node x, String query, int d, int length) {
        if (x == null) return length;
        if (x.val != null) length = d;
        if (d == query.length()) return length;
        char c = query.charAt(d);
        return longestPrefixOf(x.next[c], query, d + 1, length);
    }

    public static void main(String[] args) throws FileNotFoundException {
        TrieST<Integer> trieST = new TrieST<>();
        File file = new File("app/src/main/java/ttud/buoi3/doc.txt");
        Scanner scanner = new Scanner(file);
        String[] text = scanner.nextLine().split("/");
        for (int i = 0; i < text.length; i++) {
            text[i] = text[i].trim();
            trieST.put(text[i], i);
        }
        scanner.close();

        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < 10; i++) {
            String line = sc.nextLine().trim();
            Iterable<String> keys = trieST.keysWithPrefix(line);

            if (!keys.iterator().hasNext()) {
                trieST.put(line, i);
                continue;
            }
            keys.forEach(System.out::println);
        }

        sc.close();
    }
}
