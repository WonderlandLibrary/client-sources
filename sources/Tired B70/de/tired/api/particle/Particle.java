package de.tired.api.particle;

import de.tired.api.extension.Extension;
import de.tired.interfaces.IHook;
import de.tired.api.util.math.Vec;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class Particle implements IHook {

	public final Vec pos;
	private final double width, height;

	public double speedX;
	public double speedY;

	public double rotation;

	private int alpha;
	public Particle(double width, double height) {
		this.pos = Vec.random(0, width, 0, height);
		this.width = width;
		this.height = height;

		double minSpeed = .1;
		double maxSpeed = .3;
		this.speedX = ThreadLocalRandom.current().nextBoolean() ? -ThreadLocalRandom.current().nextDouble(minSpeed, maxSpeed) : ThreadLocalRandom.current().nextDouble(minSpeed, maxSpeed);
		this.speedY = -ThreadLocalRandom.current().nextDouble(minSpeed, maxSpeed);
		rotation = 0;
	}

	public final void draw() {
		Extension.EXTENSION.getGenerallyProcessor().renderProcessor.renderObjectCircle(pos.x, pos.y, 1, new Color(255, 255, 255, alpha).getRGB());
	}

	public final void update() {
		pos.add((speedX * (Extension.EXTENSION.getGenerallyProcessor().renderProcessor.delta * 22.1F)), speedY * (Extension.EXTENSION.getGenerallyProcessor().renderProcessor.delta * 22.1F));

		if (pos.getX() < -60) {
			pos.x = width + 60;
		}
		if (pos.getX() > width + 60) {
			pos.x = -60;
		}
		if (pos.getY() < -30) {
			pos.y = height + 60;
		}

	}

	public void setAlpha(int alpha) {
		this.alpha = alpha;
	}

	public final Vec getPos() {
		return pos;
	}
}