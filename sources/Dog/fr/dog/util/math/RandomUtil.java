package fr.dog.util.math;

import java.util.concurrent.ThreadLocalRandom;

public class RandomUtil {

    public static double random(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max + 1);
    }

    public static float random(float min, float max) {
        return ThreadLocalRandom.current().nextFloat(min, max + 1);
    }

    public static int random(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

}
