package net.minecraft.src;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class MathUtils
{
	
	private static final Random rng = new Random();
    public static int getAverage(int[] vals)
    {
        if (vals.length <= 0)
        {
            return 0;
        }
        else
        {
            int sum = 0;
            int avg;

            for (avg = 0; avg < vals.length; ++avg)
            {
                int val = vals[avg];
                sum += val;
            }

            avg = sum / vals.length;
            return avg;
        }
    }

    public static double round(final double value, final int places) {
		if (places < 0) {
			throw new IllegalArgumentException();
		}
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	public static float getRandom() {
		return rng.nextFloat();
	}

	public static int getRandom(int cap) {
		return rng.nextInt(cap);
	}

	public static int getRandom(int floor, int cap) {
		return floor + rng.nextInt(cap - floor + 1);
	}

}
