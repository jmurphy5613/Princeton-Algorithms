import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Point2D;

import edu.princeton.cs.algs4.StdDraw;

import edu.princeton.cs.algs4.Stack;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
/*
2d-tree implementation. Write a mutable data type KdTree.java
that uses a 2d-tree to implement the same API
(but replace PointSET with KdTree). A 2d-tree is a generalization of a BST to two-dimensional keys.
The idea is to build a BST with points in the nodes, using the x- and y-coordinates of
the points as keys in strictly alternating sequence.
 */


public class KdTree {
    private Node root;
    private int size;

    private static class Node{
        private final Point2D point;
        private int level; //0 is horizontal & 1 is vertical
        private Node left;
        private Node right;
        private RectHV rect;
        /*
         The rectangle represents the subdivision of the plane.
         For example, the root node rectangle is the entire plane.
         The left child of the root node is the entire area to
         the left of the root node point's vertical divider, and so on.
         */

        public Node(Point2D point, RectHV rect, int level) {
            this.point = point;
            this.level = level;

            //if rect is null create a new rect(0, 0, 1, 1)
            if (rect == null) rect = new RectHV(0, 0, 1, 1);

            this.rect = rect;

        }

        public int compare(Point2D pointToCompare){         //returns 1, -1, or 0
            if (level % 2 != 0){
                if (point.x() - pointToCompare.x() < 0) return -1;
                if (point.x() - pointToCompare.x() == 0) return 0;
                if (point.x() - pointToCompare.x() > 0) return 1;
            }
            else {
                if (point.y() - pointToCompare.y() < 0) return -1;
                if (point.y() - pointToCompare.y() == 0) return 0;
                if (point.y() - pointToCompare.y() > 0) return 1;
            }
            return 0;
        }
    }

    public KdTree(){           // construct an empty set of points
        root = null;
        size = 0;
    }
    public boolean isEmpty(){
        return size == 0;
    }
    public int size(){          // number of points in the set
        return size;
    }

    private Node insertHorizontal(Node node, Point2D p, RectHV rectHV){
        int num = node.compare(p);
        RectHV rect;

        if (num < 0){  //point p is larger than node.point
            if (node.right == null){
                rect = new RectHV(node.point.x(), rectHV.ymin(), rectHV.xmax(), rectHV.ymax());
            }
            else{
                rect = node.right.rect;
                return insertVertical(node.right, p, rect);
            }

        }
        else {
            if (node.left == null){
                rect = new RectHV(rectHV.xmin(), rectHV.ymin(), node.point.x(), rectHV.ymax());
            }
            else {
                rect = node.left.rect;
                return insertVertical(node.left, p, rect);
            }
        }
        return new Node(p, rect, 0);
    }

    private Node insertVertical(Node node, Point2D p, RectHV rectHV){
        if (node == null) {          //check if it's null
            return new Node(p, rectHV, 1);
        }
        if (node.point == p) return node;  //check if they are the same
        if(node.level == 1) return insertHorizontal(node, p, rectHV); //check if the node is horizontal

        int num = node.compare(p);
        RectHV rect; //axis aligned rectangle

        if (num < 0){   //point p is larger than node.point
            if (node.right == null){
                rect = new RectHV(rectHV.xmin(), node.point.y(), rectHV.xmax(), rectHV.ymax());
            }
            else{
                rect = node.right.rect;
                return insertHorizontal(node.right, p, rect);
            }
        }
        else {
            if (node.left == null){
                rect = new RectHV(rectHV.xmin(), rectHV.ymin(), rectHV.xmax(), node.point.y());
            }
            else{
                rect = node.left.rect;
                return insertHorizontal(node.left, p, rect);
            }
        }
         return new Node(p, rect, 1);
    }

    public void insert(Point2D p){      // add the point to the set (if it is not already in the set)
        if (p == null) throw new IllegalArgumentException("Can not inset a null point");

        if (isEmpty()){
            root = insertVertical(root, p, null);
        }
        insertVertical(root, p, root.rect);
        size++;
    }

    private boolean contains(Node node, Point2D p, int level){
        if (node == null) return false;
        if (node.point == p) return true;
        int num = node.compare(p);

        if (num < 0){ //p is larger in terms of X_ORDER than node.point
            contains(node.right, p, 0);
        }
        else{
            contains(node.left, p, 0);
        }
        return false;
    }

    public boolean contains(Point2D p){        // does the set contain point p?
        if (p == null) return false;
        else return contains(root, p, 1);
    }

    private void draw(Node root, int level){
        if (root == null) return;

        StdDraw.setPenColor(Color.black);
        StdDraw.setPenRadius(0.01);
        StdDraw.point(root.point.x(), root.point.y());

        if (level == 1){
            StdDraw.setPenColor(Color.red);
            StdDraw.line(root.point.x(), root.rect.ymin(), root.point.x(), root.rect.ymax());
            draw(root.left, 0);
            draw(root.right, 0);
        }
        else{
            StdDraw.setPenColor(Color.blue);
            StdDraw.line(root.rect.xmin(), root.point.y(), root.rect.xmax(), root.point.y());
            draw(root.left, 1);
            draw(root.right, 1);
        }
    }

    public void draw(){         // draw all points to standard draw
        StdDraw.rectangle(0.5, 0.5, 0.5, 0.5);
        if (isEmpty()) return;
        draw(root, 1);
    }

    private void range(Node node, RectHV rect, List<Point2D> range){
        if (node == null) return;
        if (rect.contains(node.point)) range.add(node.point);
        if (node.right != null && rect.intersects(node.right.rect)) range(node.right, rect, range);
        if (node.left != null && rect.intersects(node.left.rect)) range(node.left, rect, range);

    }

    public Iterable<Point2D> range(RectHV rect){      // all points that are inside the rectangle (or on the boundary)
        List<Point2D> range = new ArrayList<>();
        range(root, rect, range);
        return range;
    }

    private Point2D nearest(Node root, Point2D point, Point2D minimum, int level) {
        Point2D min = minimum;
        if (level == 1) {
            int num = root.compare(point);
            if (num == 1) {
                min = nearest(root.right, point, min, 0);
                if (root.left != null && min.distanceSquaredTo(point) > root.left.rect.distanceSquaredTo(point)) {
                    min = nearest(root.left, point, min, 0);
                }
            }
            if (num == -1) { //larger
                min = nearest(root.left, point, min, 0);
                if (root.left != null && min.distanceSquaredTo(point) > root.right.rect.distanceSquaredTo(point)) {
                    min = nearest(root.right, point, min, 0);
                }
            }
        } else {
            int num = root.compare(point);
            if (num == 1) {
                min = nearest(root.right, point, min, 1);
                if (root.left != null && min.distanceSquaredTo(point) > root.left.rect.distanceSquaredTo(point)) {
                    min = nearest(root.left, point, min, 1);
                } else {
                    min = nearest(root.left, point, min, 1);
                    if (root.left != null && min.distanceSquaredTo(point) > root.right.rect.distanceSquaredTo(point)) {
                        min = nearest(root.right, point, min, 1);
                    }
                }
            }
        }
        return min;
    }

    public Point2D nearest(Point2D p){     // a nearest neighbor in the set to point p; null if the set is empty
        if (isEmpty()) return null;
        else return nearest(root, p, root.point, 1);

    }

    public static void main(String[] args){

    }
}

