package service.util;

import java.util.Random;

public class RandomIdGenerator {
    public static int generate() {
        Random rand = new Random();
        return rand.nextInt(Integer.MAX_VALUE);
    }
}