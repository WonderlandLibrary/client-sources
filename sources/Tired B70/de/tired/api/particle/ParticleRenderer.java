package de.tired.api.particle;

import de.tired.api.extension.Extension;
import de.tired.api.util.shader.renderapi.AnimationUtil;
import de.tired.api.util.math.MathUtil;
import de.tired.api.util.math.Vec;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.BufferUtils;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

public class ParticleRenderer {
	private final List<Particle> particles;

	private int alpha;

	public ParticleRenderer() {
		this.alpha = 0;
		this.particles = new ArrayList<>();
		final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		for (int i = 0; i < (int) (sr.getScaledWidth() * .2F); i++) {
			this.particles.add(new Particle(sr.getScaledWidth(), sr.getScaledHeight()));
		}
	}

	public final void draw(int mouseX, int mouseY) {

		alpha = (int) AnimationUtil.getAnimationState(alpha, MathUtil.getRandom(0, 244), 122);

		final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		float mouseX2 = (float) Mouse.getX() / (float) Minecraft.getMinecraft().displayWidth;
		float mouseY2 = (float) Mouse.getY() / (float) Minecraft.getMinecraft().displayHeight;
		FloatBuffer mouseBuffer = BufferUtils.createFloatBuffer(2);
		this.particles.forEach(particle -> {
			double sin = Math.sin(particle.rotation);
			double cos = Math.cos(particle.rotation);
			particle.pos.x += sin * particle.speedX;
			particle.pos.y += cos * particle.speedY;

			double maxDist = Math.sqrt(sr.getScaledWidth() * sr.getScaledWidth() + sr.getScaledHeight() * sr.getScaledHeight()) / 4;
			double mDist = Math.sqrt(Math.pow(particle.pos.x - Mouse.getX() / 2f, 2) + Math.pow(particle.pos.y - Mouse.getY() / 2, 2));
			if (mDist < maxDist) {
				double ang = -Math.atan2(Mouse.getX() / 1.5 - particle.pos.x, particle.pos.y - Mouse.getY() / 1.5);
				particle.rotation = 2 * Math.PI * mouseX2 * mouseY2;
			}

			particle.setAlpha(alpha);

			particle.update();
			particle.draw();
			this.particles.forEach(particle2 -> {
				if (particle == particle2) return;
				final double maxDistance = 60;
				final double dist = particle.getPos().distance(particle2.getPos());

				if (dist < maxDistance) {
					int color = new Color(244, 244, 244, alpha).getRGB();
					Extension.EXTENSION.getGenerallyProcessor().renderProcessor.line(particle.getPos(), particle2.getPos(), color);
				}
			});
		});
	}

}
