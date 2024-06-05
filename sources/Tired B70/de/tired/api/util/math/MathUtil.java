package de.tired.api.util.math;

import java.security.SecureRandom;
import java.util.Random;

public class MathUtil {
	private static final Random rand = new Random();

	public static int getRandom(int min, int max) {
		return min + (int) (Math.random() * (double) (max - min + 1));
	}

	public static double getRandom(double min, double max) {
		return rand.nextDouble() * (max - min) + min;
	}

	public static final SecureRandom RANDOM = new SecureRandom();

	public static double toPercent(double current, double max) {
		return Math.round(current / max * 1000.0) / 100.0;
	}

	public static double getRandomSin(double min, double max) {
		double random = Math.sin((double) System.currentTimeMillis()) * (max - min);
		if (random < 0.0D) {
			random = Math.abs(random);
		}

		return random + min;
	}


}
