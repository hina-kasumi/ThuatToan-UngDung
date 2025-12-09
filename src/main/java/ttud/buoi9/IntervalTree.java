package ttud.buoi9;

import java.util.LinkedList;
import java.util.Queue;

public class IntervalTree<Value> extends BST<IntervalTree.IntervalKey, Value> {
    public static class IntervalKey extends Interval1D implements Comparable<IntervalKey> {
        public IntervalKey(double min, double max) {
            super(min, max);
        }

        @Override
        public int compareTo(IntervalKey o) {
            if (this.min() < o.min()) return -1;
            else if (this.min() > o.min()) return +1;
            else return Double.compare(this.max(), o.max());
        }
    }

    public Iterable<Value> intersects(IntervalKey lo, IntervalKey hi) {
        IntervalKey query = new IntervalKey(lo.min(), hi.max());
        Queue<Value> result = new LinkedList<>();
        for (IntervalKey key : this.keys()) {
            if (key.intersects(query)) {
                result.add(this.get(key));
            }
        }
        return result;
    }

    public static void main(String[] args) {
        IntervalTree<String> tree = new IntervalTree<>();
        tree.put(new IntervalKey(1.0, 3.0), "A");
        tree.put(new IntervalKey(2.5, 4.0), "B");
        tree.put(new IntervalKey(5.0, 7.0), "C");
        tree.put(new IntervalKey(6.5, 9.0), "D");
        tree.put(new IntervalKey(9.0, 10.0), "E");

        IntervalKey lo = new IntervalKey(2.0, 3.5);
        IntervalKey hi = new IntervalKey(6.0, 8.0);
        for (String value : tree.intersects(lo, hi)) {
            System.out.println(value);
        }
    }
}