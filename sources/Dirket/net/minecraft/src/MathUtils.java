package net.minecraft.src;

import net.minecraft.util.math.MathHelper;

public class MathUtils {
	public static int getAverage(int[] p_getAverage_0_) {
		if (p_getAverage_0_.length <= 0) {
			return 0;
		} else {
			int i = 0;

			for (int k : p_getAverage_0_) {
				i += k;
			}

			int l = i / p_getAverage_0_.length;
			return l;
		}
	}

	public static int roundDownToPowerOfTwo(int p_roundDownToPowerOfTwo_0_) {
		int i = MathHelper.roundUpToPowerOfTwo(p_roundDownToPowerOfTwo_0_);
		return p_roundDownToPowerOfTwo_0_ == i ? i : i / 2;
	}
}
