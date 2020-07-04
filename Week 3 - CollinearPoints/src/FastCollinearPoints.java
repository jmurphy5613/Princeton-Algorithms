import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {


    private double[] slopes;
    private List<LineSegment> segments = new ArrayList<>();
    private List<Double> slopesFound = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {
        points = insertionSort(points);
        int N = points.length;
        slopes = new double[N];

        //Create array copy and reference their slope
        for (int i = 0; i < N; i++) {
            //set point p and give an array of slopes to examine
            Point p = points[i];
            slopes = sortBySlope(points, p);
            double[] slopeCopy = new double[N];
            //scan 3 elements at a time, once you find 3 or more slopes that are similar,
            //use slope find to find all points with that slope
            for (int y = 0; y <= N - 4; y++) {

                //check if the current y is a slope that has already been found to avoid repeating anything
                if (flagCheckSlope(y)) {
                    continue;
                }

                if (Arrays.stream(slopes, y, y + 3).sum() == slopes[y] * 3) {
                    Point[] pointsWithSimilarSlopes = slopeFind(points, p, slopes[y]);
                    //check if the segment already exists
                    if (flagCheckSegment(pointsWithSimilarSlopes, y)) continue;
                    segments.add(getSegment(pointsWithSimilarSlopes, p));
                    slopesFound.add(slopes[y]);
                }
            }
            slopes = slopeCopy;
        }
    }

    public double[] sortBySlope(Point[] a, Point p){
        for (int i = 0; i < a.length; i++){
            if (p == a[i]) continue;
            slopes[i] = p.slopeTo(a[i]);
        }
        Arrays.sort(slopes);
        return Arrays.copyOfRange(slopes, 1 ,slopes.length);
    }

    //return the points that have a certain slope
    public Point[] slopeFind(Point[] a, Point p, double s){
        int N = a.length;

        List<Point> points = new ArrayList<>();

        for (int i = 0; i < N; i++){
            if (p.slopeTo(a[i]) == s){
                points.add(a[i]);
            }
        }

        Point[] storedPoints = new Point[points.size()];

        for (int j = 0; j < points.size(); j++){
            storedPoints[j] = points.get(j);
        }
        return storedPoints;
    }

    //adding a segment
    public LineSegment getSegment(Point[] a, Point p){
        Arrays.sort(a);
        Point minPoint = p;
        Point maxPoint = a[a.length - 1];
        return new LineSegment(minPoint, maxPoint);
    }

    //check if the slope has already been found
    public boolean flagCheckSlope(double slope){
        for (double value : slopesFound) {
            if (value == slope) return true;
        }
        return false;
    }

    public int numberOfSegments(){
        return segments.size();
    }
    public LineSegment[] segments(){
        LineSegment[] lineSegments = new LineSegment[numberOfSegments()];
        for (int i = 0; i < segments.size(); i++){
            lineSegments[i] = segments.get(i);
        }
        return lineSegments;
    }

    public Point[] insertionSort(Point[] a){
        Point key;
        Point temp;
        int i, j;
        for (i = 1; i < a.length; i++){
            key = a[i];
            j = i - 1;
            while (j >= 0 && key.x < a[j].x){
                temp = a[j];
                a[j] = a[j + 1];
                a[j + 1] = temp;
                j--;
            }
        }
        return a;
    }

    private boolean flagCheckSegment(Point[] a, double y){
        for (int j = 0; j < slopesFound.size(); j++){
            double b = slopesFound.get(j);
            Arrays.sort(a);
            Point maxPoint = a[a.length - 1];
            for (int i = 0; i < segments.size(); i++){
                if (segments.get(i).q == maxPoint && b == y) return true;
            }
        }
        return false;
    }
}


