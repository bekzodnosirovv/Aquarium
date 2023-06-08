package org.example;

import java.util.Random;

public class RandomUtil {

    private static Random random = new Random();

    public static int getNumber(int i) {
        return random.nextInt(i + 1);
    }

    public static boolean nextBoolean() {
        return random.nextBoolean();
    }
}
