package me.swezedcode.client.utils.math;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class MathUtils {

	private static final Random rng;

	static {
		rng = new Random();
	}

	public static double round(final double value, final int places) {
		if (places < 0) {
			throw new IllegalArgumentException();
		}
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	public static Random getRng() {
		return MathUtils.rng;
	}

	public static int customRandInt(final int min, final int max) {
		return new Random().nextInt(max - min + 1) + min;
	}

	public static int getRandom(final int cap) {
		return MathUtils.rng.nextInt(cap);
	}

	public static int getRandom(final int floor, final int cap) {
		return floor + MathUtils.rng.nextInt(cap - floor + 1);
	}

	public static double roundToPlace(double value, int places) {
		if (places < 0) {
			throw new IllegalArgumentException();
		}
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
	
	public static int getRandomInRange(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

}
