package org.example.grahamAndJarvisAlgorithms;

public class Point {
    final float x;
    final float y;

    Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Point) {
            Point p = (Point) o;
            return p.x == this.x && p.y == this.y;
        } else {
            return false;
        }
    }
}
