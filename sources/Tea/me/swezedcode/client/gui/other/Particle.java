package me.swezedcode.client.gui.other;

import net.minecraft.util.MathHelper;

import java.awt.Color;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import me.swezedcode.client.utils.render.RenderUtils;

public class Particle {
	private static final float HALF_PI = 1.5707964F;
	public Vector2f pos;
	public float magnitude;
	public float angle;
	public float mass;

	public Particle(Vector2f pos, float magnitude, float angle, float mass) {
		this.pos = pos;
		this.magnitude = magnitude;
		this.angle = angle;
		this.mass = mass;
	}

	public void applyForce(float x, float y, float mass, boolean attractor) {
		if ((this.pos.x - x) * (this.pos.x - x) + (this.pos.y - y) * (this.pos.y - y) != 0.0F) {
			float f = this.mass * 6 * mass;
			float mx = (this.mass * this.pos.x + mass * x) / (this.mass + mass);
			float my = (this.mass * this.pos.y + mass * y) / (this.mass + mass);

			float angle = attractor ? findAngle(mx - this.pos.x, my - this.pos.y)
					: findAngle(this.pos.x - mx, this.pos.y - my);

			mx = f * MathHelper.cos(angle);
			my = f * MathHelper.sin(angle);

			mx += this.magnitude * MathHelper.cos(this.angle);
			my += this.magnitude * MathHelper.sin(this.angle);

			this.magnitude = ((float) Math.sqrt(mx * mx + my * my));
			this.angle = findAngle(mx, my);
		}
	}

	public void update(float delta) {
		this.pos.x += this.magnitude * MathHelper.cos(this.angle) * delta;
		this.pos.y += this.magnitude * MathHelper.sin(this.angle) * delta;
	}

	public void render() {
		GL11.glBegin(0);
		GL11.glVertex2f(this.pos.x, this.pos.y);
		GL11.glEnd();
	}

	private static float findAngle(float x, float y) {
		float theta = 0.0F;
		if (x == 0.0F) {
			theta = y > 0.0F ? 1.5707964F : 4.712389F;
		} else {
			theta = (float) Math.atan(y / x);
			if ((x < 0.0F) && (y >= 0.0F)) {
				theta = (float) (theta + 3.141592653589793D);
			}
			if ((x < 0.0F) && (y < 0.0F)) {
				theta = (float) (theta - 3.141592653589793D);
			}
		}
		return theta;
	}
}