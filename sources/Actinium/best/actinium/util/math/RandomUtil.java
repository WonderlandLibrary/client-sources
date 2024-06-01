package best.actinium.util.math;

import best.actinium.util.io.TimerUtil;
import net.minecraft.util.MathHelper;

import java.security.SecureRandom;
import java.util.Random;

public class RandomUtil {
    private static TimerUtil t = new TimerUtil();
    public static float getAdvancedRandom(float min, float max) {
        SecureRandom random = new SecureRandom();

        long finalSeed = System.nanoTime();

        for (int i = 0; i < 5; ++i) {
            long seed = (long) (Math.random() * 1_000_000_000);

            seed ^= (seed << 13);
            seed ^= (seed >>> 17);
            seed ^= (seed << 15);

            finalSeed += seed;
        }

        random.setSeed(finalSeed);

        return random.nextFloat() * (max - min) + min;
    }

    public static float getShitRandom(float min,float max) {
        return MathHelper.randomFloatClamp(new Random(),min,max);
    }
    //u can only use this function once at a time i could make it in a component and fix it but i am lazy todo:fix this
    public static long calculatePing(long min,long max,long repeatMs,boolean randomLow) {
        if(t.hasTimeElapsed(repeatMs)) {
            t.reset();
            return (long) getAdvancedRandom(min,max);
        } else {
            return randomLow ? min : max;
        }
    }

    private static long lastRandom = 0;
    public static long getRandomPing(long min, long max) {
        long d;

        if (t.hasTimeElapsed(600)) {
            d = (long) getAdvancedRandom(min, max);
            lastRandom = d;
            t.reset();
        } else {
            d = lastRandom;
        }

        return d;
    }

    private static long lastUpdateTime = System.currentTimeMillis();
    private static long currentPing = 0;
    //and when i do add it to the back track and make it a component or smth
    public static long calculatePing(long mean, long standardDeviation, long repeatMs, long min, long max) {
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastUpdateTime >= repeatMs) {
            lastUpdateTime = currentTime;

            Random random = new Random();
            long pingTime = (long) (random.nextGaussian() * standardDeviation + mean);
            currentPing = Math.min(Math.max(pingTime, min), max);
        }

        return currentPing;
    }
}