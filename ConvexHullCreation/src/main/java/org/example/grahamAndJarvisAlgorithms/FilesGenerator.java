package org.example.grahamAndJarvisAlgorithms;

import java.io.*;

public class FilesGenerator {
    public static int[][] generateFiles(int n) {
        int[][] a = new int[n][2];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 2; j++) {
                a[i][j] = (int) (Math.random() * 100000);
            }
        }
        return a;
    }

    public static void main(String[] args) throws IOException {
        for (int k = 1; k < 1001; k++) {
            int[][] m = generateFiles(k * 1000);
            File file = new File("src/main/java/org/example/grahamAndJarvisAlgorithms/data/data" + (k));
            FileWriter writer = new FileWriter(file);
            for (int i = 0; i < m.length; ++i) {
                for (int j = 0; j < m[i].length; ++j) {
                    writer.write(m[i][j] + " ");
                }
                writer.write("\n");
            }
            writer.close();
        }
    }
}
