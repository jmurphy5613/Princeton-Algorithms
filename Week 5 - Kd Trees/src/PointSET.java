import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Point2D;

import edu.princeton.cs.algs4.StdDraw;

import edu.princeton.cs.algs4.Stack;

import java.util.ArrayList;
import java.util.List;
/*
Write a data type to represent a set of points in the unit square
(all points have x- and y-coordinates between 0 and 1)
using a 2d-tree to support efficient range search
(find all of the points contained in a query rectangle) and nearest-neighbor
search (find a closest point to a query point).
2d-trees have numerous applications, ranging from classifying astronomical objects
to computer animation to speeding up neural networks to mining data to image retrieval.
 */


public class PointSET {

    private SET<Point2D> set;

    public PointSET(){           // construct an empty set of points
        set = new SET<>();
    }
    public boolean isEmpty(){
        return set.isEmpty();
    }
    public int size(){          // number of points in the set
        return set.size();
    }
    public void insert(Point2D p){      // add the point to the set (if it is not already in the set)
        if (!set.contains(p)) set.add(p);
    }
    public boolean contains(Point2D p){        // does the set contain point p?
        return set.contains(p);
    }
    public void draw(){         // draw all points to standard draw
        for (Point2D point : set){
            StdDraw.point(point.x(), point.y());
        }
    }
    public Iterable<Point2D> range(RectHV rect){      // all points that are inside the rectangle (or on the boundary)
        List<Point2D> points = new ArrayList<>();

        for (Point2D point : set){
            if (rect.contains(point)) points.add(point);
        }
        return points;
    }
    public Point2D nearest(Point2D p){     // a nearest neighbor in the set to point p; null if the set is empty
        if (isEmpty()) return null;
        Point2D closestPoint = null;
        for (Point2D point : set){
            if (closestPoint == null || point.distanceTo(p) > closestPoint.distanceTo(p)) closestPoint = point;
        }
        return closestPoint;
    }

    public static void main(String[] args){

    }
}
