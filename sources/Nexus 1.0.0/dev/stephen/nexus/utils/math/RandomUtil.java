package dev.stephen.nexus.utils.math;

import dev.stephen.nexus.utils.Utils;

import java.util.concurrent.ThreadLocalRandom;

public class RandomUtil implements Utils {

    public static double randomBetween(double min, double max) {
        return (max + (min - max) * Math.random());
    }

    public static int randomBetween(int min, int max) {
        return (int) (max + (min - max) * Math.random());
    }

    public static float randomBetween(float min, float max) {
        return (float) (max + (min - max) * Math.random());
    }

    public static double smartRandom(double min, double max) {
        if (min == max) {
            return min;
        } else if (min > max) {
            final double subst = min;
            min = max;
            max = subst;
        }
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    public static double randomNoise(double min, double max) {
        FastNoiseLite perlinNoise = new FastNoiseLite((int) System.currentTimeMillis());
        perlinNoise.SetNoiseType(FastNoiseLite.NoiseType.Perlin);

        float perlinValue = perlinNoise.GetNoise((float) mc.player.getX() + (float) Math.random(), (float) mc.player.getZ() + (float) Math.random());
        return map(perlinValue, -1.0f, 1.0f, (float) min, (float) max);
    }

    private static float map(float value, float inMin, float inMax, float outMin, float outMax) {
        return outMin + (value - inMin) * (outMax - outMin) / (inMax - inMin);
    }

    public static double randomLast(double min, double max, long last) {
        long timeDifference = System.currentTimeMillis() - last;
        return (timeDifference % (max - min + 1)) + min;
    }
}