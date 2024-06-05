package alphentus.utils;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author avox | lmao
 * @since on 30.07.2020.
 */
public class RandomUtil {

    Random random = new Random();

    public final int randomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    public final long randomLong(long min, long max) {
        return ThreadLocalRandom.current().nextLong(min, max);
    }

    public final float randomFloat(float min, float max) {
        return (float)ThreadLocalRandom.current().nextDouble(min, max);
    }

    public final double randomDouble(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    public final double randomGaussian(double tolerance, double average, boolean multiplyGaussian) {
        return random.nextGaussian() * (multiplyGaussian ? random.nextGaussian() : 1) * tolerance + average;
    }
}