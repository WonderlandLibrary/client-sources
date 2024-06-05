package me.vaziak.sensation.utils.math;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.Random;

/**
 * Created by Jonathan H. (Niada)
 * -------------------------------
 * -------------------------------
 * <p>
 * Well not really created by, more like took a bunch of public math utils i got from stackoverflow and shit
 * and put them into one fat ass class, they really do come in handly tho I'll say that much, now some of these I did actually make.
 **/
public class MathUtils {

    private static Random rng;
    public static Double clamp(double number, double min, double max) {
        if (number < min)
            return min;
        else if (number > max)
            return max;
        else
            return number;
    }

    public static double getDifference(double num1, double num2) {
        if (num1 > num2) {
            double tempNum = num1;
            num1 = num2;
            num2 = tempNum;
        }
        return num2 - num1;
    }

    public static double getRandomInRange(double min, double max) {
        Random random = new Random();
        double range = max - min;
        double scaled = random.nextDouble() * range;
        if (scaled > max) {
            scaled = max;
        }
        double shifted = scaled + min;

        if (shifted > max) {
            shifted = max;
        }
        return shifted;
    }

    public static float getRandomInRange(float min, float max) {
        Random random = new Random();
        float range = max - min;
        float scaled = random.nextFloat() * range;
        return scaled + min;
    }

    public static int getRandomInRange(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static double newRound(double value, String pattern) {
        DecimalFormat df = new DecimalFormat(pattern);
        df.setRoundingMode(RoundingMode.CEILING);

        return Double.parseDouble(df.format(value));
    }
    
	public static boolean isInputBetween(double input, double min, double max) {
		return input >= min && input <= max;
	}
	
    public static float getMiddle(float first, float second) {
        return (first + second) / 2;
    }

    public static int getRandom(final int floor, final int cap) {
        return floor + MathUtils.rng.nextInt(cap - floor + 1);
    }
    
    static {
        MathUtils.rng = new Random();
    }

	public static float round2(float value, float offset) {
		return value = value % offset;
	}
}
