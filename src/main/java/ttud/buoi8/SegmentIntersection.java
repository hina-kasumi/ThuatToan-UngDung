package ttud.buoi8;

public class SegmentIntersection {
    public static class VerticalSegment implements Comparable<VerticalSegment> {
        private int x0, y0, x1, y1;
        public VerticalSegment(int x0, int y0, int length) {
            this.x0 = x0;
            this.y0 = y0;
            this.x1 = x0;
            this.y1 = y0 + length;
        }

        @Override
        public int compareTo(VerticalSegment o) {
            return 0;
        }
    }

    public static void main(String[] args) {

    }
}
