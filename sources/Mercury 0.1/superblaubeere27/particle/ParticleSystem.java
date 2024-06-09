/*
 * Decompiled with CFR 0.145.
 */
package superblaubeere27.particle;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import superblaubeere27.particle.Particle;
import superblaubeere27.util.MathUtil;

public class ParticleSystem {
    private static final float SPEED = 0.1f;
    private List<Particle> particleList = new ArrayList<Particle>();
    private boolean mouse;
    private boolean rainbow;
    private int dist;

    public ParticleSystem(int initAmount, boolean mouse, boolean rainbow, int dist) {
        this.addParticles(initAmount);
        this.mouse = mouse;
        this.dist = dist;
        this.rainbow = rainbow;
    }

    public void addParticles(int amount) {
        for (int i2 = 0; i2 < amount; ++i2) {
            this.particleList.add(Particle.generateParticle());
        }
    }

    public void tick(int delta) {
        if (Mouse.isButtonDown(0)) {
            this.addParticles(1);
        }
        for (Particle particle : this.particleList) {
            particle.tick(delta, 0.1f);
        }
    }

    public void render() {
        for (Particle particle : this.particleList) {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, particle.getAlpha() / 255.0f);
            GL11.glPointSize(particle.getSize());
            GL11.glBegin(0);
            GL11.glVertex2f(particle.getX(), particle.getY());
            GL11.glEnd();
            if (this.mouse) {
                float distance;
                Color c2 = null;
                if (this.rainbow) {
                    c2 = superblaubeere27.util.Color.rainbow(50.0f, 0.0f);
                }
                if (!((distance = (float)MathUtil.distance(particle.getX(), particle.getY(), Mouse.getX(), Display.getHeight() - Mouse.getY())) < (float)this.dist)) continue;
                float alpha = Math.min(1.0f, Math.min(1.0f, 1.0f - distance / (float)this.dist));
                this.drawLine(particle.getX(), particle.getY(), Mouse.getX(), Display.getHeight() - Mouse.getY(), this.rainbow ? (float)c2.getRed() / 255.0f : 1.0f, this.rainbow ? (float)c2.getGreen() / 255.0f : 1.0f, this.rainbow ? (float)c2.getBlue() / 255.0f : 1.0f, alpha);
                continue;
            }
            float nearestDistance = 0.0f;
            Particle nearestParticle = null;
            for (Particle particle1 : this.particleList) {
                float distance = particle.getDistanceTo(particle1);
                if (!(distance <= (float)this.dist) || !(MathUtil.distance(Mouse.getX(), Display.getHeight() - Mouse.getY(), particle.getX(), particle.getY()) <= (double)this.dist) && !(MathUtil.distance(Mouse.getX(), Display.getHeight() - Mouse.getY(), particle1.getX(), particle1.getY()) <= (double)this.dist) || !(nearestDistance <= 0.0f) && !(distance <= nearestDistance)) continue;
                nearestDistance = distance;
                nearestParticle = particle1;
            }
            if (nearestParticle == null) continue;
            Color c3 = null;
            if (this.rainbow) {
                c3 = superblaubeere27.util.Color.rainbow(50.0f, 0.0f);
            }
            float alpha = Math.min(1.0f, Math.min(1.0f, 1.0f - nearestDistance / (float)this.dist));
            this.drawLine(particle.getX(), particle.getY(), nearestParticle.getX(), nearestParticle.getY(), this.rainbow ? (float)c3.getRed() / 255.0f : 1.0f, this.rainbow ? (float)c3.getGreen() / 255.0f : 1.0f, this.rainbow ? (float)c3.getBlue() / 255.0f : 1.0f, alpha);
        }
    }

    private void drawLine(float x2, float y2, float x1, float y1, float r2, float g2, float b2, float alpha) {
        GL11.glColor4f(r2, g2, b2, alpha);
        GL11.glLineWidth(0.5f);
        GL11.glBegin(1);
        GL11.glVertex2f(x2, y2);
        GL11.glVertex2f(x1, y1);
        GL11.glEnd();
    }
}

