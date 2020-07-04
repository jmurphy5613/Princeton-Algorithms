import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> segmentList = new ArrayList<>();

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        // finds all line segments containing 4 points
        Point[] aux = Arrays.copyOf(points, points.length);
        Arrays.sort(aux);
        int len = aux.length;
        for (int i = 1; i < len; i++) {
            if (aux[i].compareTo(aux[i-1]) == 0) {
                throw new IllegalArgumentException();
            }
        }
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                for (int k = j + 1; k < len; k++) {
                    for (int m = k + 1; m < len; m++) {
                        Point p1 = aux[i], p2 = aux[j], p3 = aux[k], p4 = aux[m];
                        if (p1.slopeTo(p2) == p1.slopeTo(p3) && p1.slopeTo(p2) == p1.slopeTo(p4))
                            segmentList.add(new LineSegment(p1, p4));
                    }
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return this.segmentList.size();
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] segments = new LineSegment[segmentList.size()];
        return this.segmentList.toArray(segments);
    }
}
