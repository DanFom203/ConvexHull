package org.example.grahamAndJarvisAlgorithms;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

/**
 * Реализация Алгоритма Грэхэма:
 * 1. Найдем самую нижнюю (левую) точку p
 * 2. Отсортируем относительно полярного угла от точки p
 * 3. Итерируем points в отсортированном порядке и добавляем в hull,
 *      но в случае, когда получим не counter clockwise поворот - отбрасываем
 * 3.1. Отдельно рассматриваем случаи, когда из точек получаем коллинеарные вектора
 */
public class ConvexHullGrahamScan {
    /**
     * сортировка по углу, отличающая GrahamScan от JarvisMarch
     * @param ref равен minYPoint - начальная точка
     */
    private void sortByAngle(List<Point> points, Point ref) {
        Collections.sort(points, (b, c) -> {
            /*
             * точку ref сразу добавляем в hull
             */
            if (b == ref) return -1;    //  если (b == ref) то точка b точно меньше точки c, сортируем
            if (c == ref) return 1;     //  если (c == ref) то точка c точно меньше точки b, сортируем

            int ccw = GraphUtils.ccw(ref, b, c);

            if (ccw == 0) {
                /*
                 * Обрабатываем коллинеарные точки. Мы можем использовать одну из координат Y или X
                 * для сравнения таких точек, т.к. отношения (x2/x1) == (y2/y1)
                 */
                if (Float.compare(b.x, c.x) == 0) {
                    /*
                     * Обрабатываем редкий случай, когда точки совпадают по координате X,
                     * но разные по Y. Делаем проверку и берем ближнюю по Y точку
                     */
                    return b.y < c.y ? -1 : 1;
                } else {
                    return b.x < c.x ? -1 : 1;
                }
            } else {
                return ccw * -1;
            }
        });
    }

    /**
     * Сам алгоритм
     */
    public List<Point> scan(List<Point> points) {
        Deque<Point> stack = new ArrayDeque<>();

        /*
         * ищем самую нижнюю (левую) точку
         */
        Point minYPoint = GraphUtils.getMinY(points);
        sortByAngle(points, minYPoint); // сортируем по полярному углу, но помним о точке minYPoint

        stack.push(points.get(0)); //   1 точку гарантированно добавляем в оболочку
        stack.push(points.get(1)); //   добавляем также следующую точку, но с возможностью удаления, если не подойдет

        for (int i = 2; i < points.size(); i++) {
            Point next = points.get(i);
            Point p = stack.pop();

            while (stack.peek() != null && GraphUtils.ccw(stack.peek(), p, next) <= 0) {
                p = stack.pop(); // удаляем точку, в которой совершается поворот по часовой стрелке
            }

            stack.push(p);
            stack.push(points.get(i));
        }

        /*
         * самая последняя точка может быть в некоторый случаях коллинеарной, и мы это проверяем
         */
        Point p = stack.pop();
        if (GraphUtils.ccw(stack.peek(), p, minYPoint) > 0) {
            stack.push(p); // если выполняется условие, возвращаем точку в stack, она подходит
        }

        return new ArrayList<>(stack);
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

            System.out.print("The number of points in the set: " + points.size() + " ");

            ConvexHullGrahamScan hull = new ConvexHullGrahamScan();

            long start = System.nanoTime();

            System.out.println("Graham Scan: " + hull.scan(points));
            long finish = System.nanoTime();
            System.out.println("Time: " + ((finish - start) / 1000));

        }
    }
}

