package org.example.grahamAndJarvisAlgorithms;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Найдём самую нижнюю (левую) точку p.
 * Потом, без всяких сортировок, итерируем по массиву points с целью найти точку,
 * образующую минимальный полярный угол с предыдущей точкой, добавляем её в hull. Таким образом итерируем до момента,
 * пока не вернемся в начальную точку p.
 */
public class ConvexHullJarvisMarch {
    public List<Point> march(Collection<Point> points) {
        List<Point> hull = new ArrayList<>();

        Point startingPoint = GraphUtils.getMinY(points); // ищем самую нижнюю (левую) точку
        hull.add(startingPoint);

        Point prevVertex = startingPoint;

        while (true) {
            Point candidate = null;
            /*
             *  пройдемся по всему List<Point> и найдем точку
             *  с наименьшим полярным углом относительно prevVertex
             */
            for (Point point : points) {
                if (point == prevVertex) continue;

                if (candidate == null) {
                    candidate = point;
                    continue;
                }

                int ccw = GraphUtils.ccw(prevVertex, candidate, point);

                if (ccw == 0 && GraphUtils.dist(prevVertex, candidate) < GraphUtils.dist(prevVertex, point)) {
                    candidate = point; // если вектора коллинеарны - мы сравниваем их длины
                } else if (ccw < 0) {
                    /*
                     *  если вдруг происходит clockwise поворот - значит,
                     *  point имеет меньший полярный угол относительно prevVertex, чем candidate
                     */
                    candidate = point;
                }
            }

            if (candidate == startingPoint) break; // вернулись к стартовой точке, дело сделано

            hull.add(candidate);
            prevVertex = candidate;
        }

        return hull;
    }

    public static void main(String[] args) throws IOException {
        for (int k = 1; k < 1001; k++) {
            Path path = Path.of("ConvexHullCreation/src/main/java/org/example/grahamAndJarvisAlgorithms/data/data" + k);
            List<String> lines = Files.readAllLines(path);

            List<Point> points = new ArrayList<>();

            for (String line : lines) {
                int x = Integer.parseInt(StringUtils.substringBefore(line, " "));
                int y = Integer.parseInt(StringUtils.substringBetween(line, " ", " "));
                points.add(new Point(x, y));
            }

            System.out.print("The number of points in the set : " + points.size() + " ");

            ConvexHullJarvisMarch hull = new ConvexHullJarvisMarch();

            long start = System.nanoTime();

            System.out.println("Jarvis March: " + hull.march(points));
            long finish = System.nanoTime();
            System.out.println("Time : " + ((finish - start) / 1000));

        }
    }
}