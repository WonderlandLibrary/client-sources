package me.swezedcode.client.gui.other;

import java.awt.Color;
import java.util.Random;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

import me.swezedcode.client.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public final class ParticleTest {
	public static ParticleTest pEngine;
	private static final int PARTICLE_COUNT = 90000;
	private static final Random RANDOM = new Random();
	private static Color colorA = Color.WHITE;
	private static Color colorB = Color.WHITE;
	private Particle[] particles = new Particle[90000];
	private long ticks;
	private long updates;
	private Vector2f clickPos = new Vector2f();

	public ParticleTest() {
		for (int i = 0; i < 90000; i++) {
			this.particles[i] = new Particle(new Vector2f(RANDOM.nextInt(Minecraft.getMinecraft().displayWidth),
					RANDOM.nextInt(Minecraft.getMinecraft().displayHeight)), 0.0F, 0.0F, 1.0F);
		}
	}

	public void update(ScaledResolution sr, float mouseX, float mouseY, float delta) {
		if (this.ticks % 40L == 0L) {
			this.clickPos.x = (sr.getScaledWidth() / 2
					+ RANDOM.nextInt(sr.getScaledWidth() / 2) * (RANDOM.nextInt(1) == 0 ? -1 : 1));
			this.clickPos.y = (sr.getScaledHeight() / 2
					+ RANDOM.nextInt(sr.getScaledHeight() / 2) * (RANDOM.nextInt(1) == 0 ? -1 : 1));
		}
		Particle[] arrayOfParticle;
		int j = (arrayOfParticle = this.particles).length;
		for (int i = 0; i < j; i++) {
			Particle p = arrayOfParticle[i];
			if (Mouse.isButtonDown(1)) {
				p.applyForce(mouseX, mouseY, 0.1F, false);
			}
			if (this.ticks % 40L == 0L) {
				p.applyForce(this.clickPos.x, this.clickPos.y, 1.0F, RANDOM.nextBoolean());
			}
			p.magnitude *= 0.985F;

			p.update(delta);
		}
		this.ticks += 1L;
	}

	public void onRenderIntoGui(float mouseX, float mouseY, ScaledResolution sr) {
		double width = sr.getScaledWidth_double();
		double height = sr.getScaledHeight_double();

		update(sr, mouseX, mouseY, Minecraft.getMinecraft().timer.renderPartialTicks);

		RenderUtils.setColor(new Color(0xFFFFFFFF, true));
		Particle[] arrayOfParticle;
		int j = (arrayOfParticle = this.particles).length;
		for (int i = 0; i < j; i++) {
			Particle p = arrayOfParticle[i];
			p.render();
		}
		this.updates += 1L;
	}
}