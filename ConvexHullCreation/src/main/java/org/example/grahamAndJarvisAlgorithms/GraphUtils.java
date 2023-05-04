package org.example.grahamAndJarvisAlgorithms;

import java.util.Collection;
import java.util.Iterator;

public class GraphUtils {

    /**
     * находит самую нижнюю (левую) точку среди points
     */
    static Point getMinY(Collection<Point> points) {

        Iterator<Point> it = points.iterator();
        Point min = it.next();

        while (it.hasNext()) {
            Point point = it.next();
            if (point.y <= min.y) {
                if (point.y < min.y) {
                    min = point;
                } else if (point.x < min.x) { // point.y == min.y, выбираем наиболее левый
                    min = point;
                }
            }
        }

        return min;
    }

    /**
     * У нас есть ломаная прямая abc, мы её достраиваем до параллелограмма,
     * а потом считаем площадь этого параллелограмма
     *
     * @return 1 если в точке b  есть поворот против часовой стрелки, -1 если по часовой, 0 если вектора коллинеарны
     */
    static int ccw(Point a, Point b, Point c) {
        /*
         * формула ориентированной площади параллелограмма через три точки на плоскости
         */
        float area = (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x); //  (x2 - x1) * (y3 - y1) - (y2 - y1) * (x3 - x1)

        if (area < 0) return -1; // clockwise

        if (area > 0) return 1; // counter-clockwise

        return 0; // collinear
    }

    static double dist(Point a, Point b) {
        return Math.sqrt(((a.x - b.x) * (a.x - b.x)) + ((a.y - b.y) * (a.y - b.y)));
    }

}
