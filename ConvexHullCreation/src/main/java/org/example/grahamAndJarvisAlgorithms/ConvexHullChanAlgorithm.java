package org.example.grahamAndJarvisAlgorithms;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.List;

public class ConvexHullChanAlgorithm {
    public static void main(String[] args) throws IOException {
        for (int k = 1; k < 1001; k++) {
            Path path = Path.of("ConvexHullCreation/src/main/java/org/example/grahamAndJarvisAlgorithms/data/data" + k);
            List<String> lines = Files.readAllLines(path);

            int m = 20;
            List<Point> subListHulls = new ArrayList<>();
            List<Point> subList = new ArrayList<>();

            long start = System.nanoTime();

            for (String line : lines) {
                int x = Integer.parseInt(StringUtils.substringBefore(line, " "));
                int y = Integer.parseInt(StringUtils.substringBetween(line, " ", " "));
                subList.add(new Point(x, y));
                if (subList.size() == m) {
                    ConvexHullGrahamScan grahamHull = new ConvexHullGrahamScan();
                    subListHulls.addAll(grahamHull.scan(subList));
                    subList = new ArrayList<>();
                }
            }

            ConvexHullJarvisMarch hull = new ConvexHullJarvisMarch();

            System.out.println("Chan's algorithm : " + hull.march(subListHulls));
            long finish = System.nanoTime();
            System.out.println("Time : " + ((finish - start) / 1000));

        }
    }
}