package de.tired.api.extension.processors.extensions.blatant;

import de.tired.api.util.math.MathUtil;
import de.tired.api.util.math.TimerUtil;
import de.tired.interfaces.IHook;

import java.util.Random;

public class CPSDrop implements IHook {

	private final TimerUtil timerUtil = new TimerUtil();

	private boolean wasCPSDrop;

	public int getNeededClicksNoAbuse(int minCPS, int maxCPS) {
		final Random random = new Random();
		int currCPS;

		if (timerUtil.reachedTime((long) (1000.0 / MathUtil.getRandom(minCPS, maxCPS)))) {
			wasCPSDrop = !wasCPSDrop;
			timerUtil.doReset();
		}


		currCPS = minCPS + (random.nextInt() * (maxCPS - minCPS));

		return currCPS;
	}

	public int getNeededClicks(int minCPS, int maxCPS) {
		final Random random = new Random();
		int currCPS;

		if (timerUtil.reachedTime((long) (1000.0 / MathUtil.getRandom(minCPS, maxCPS)))) {
			wasCPSDrop = !wasCPSDrop;
			timerUtil.doReset();
		}

		final double curr = System.currentTimeMillis() * random.nextInt(220);

		double timeCovert = Math.max(maxCPS, curr) / 7;
		if (wasCPSDrop) {
			currCPS = (int) timeCovert;
		} else {
			currCPS = (int) (minCPS + (random.nextInt() * (maxCPS - minCPS)) / timeCovert);
		}

		return currCPS;
	}

}
