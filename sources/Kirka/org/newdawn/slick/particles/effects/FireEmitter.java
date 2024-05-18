/*
 * Decompiled with CFR 0.143.
 */
package org.newdawn.slick.particles.effects;

import org.newdawn.slick.Image;
import org.newdawn.slick.particles.Particle;
import org.newdawn.slick.particles.ParticleEmitter;
import org.newdawn.slick.particles.ParticleSystem;

public class FireEmitter
implements ParticleEmitter {
    private int x;
    private int y;
    private int interval = 50;
    private int timer;
    private float size = 40.0f;

    public FireEmitter() {
    }

    public FireEmitter(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public FireEmitter(int x, int y, float size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public void update(ParticleSystem system, int delta) {
        this.timer -= delta;
        if (this.timer <= 0) {
            this.timer = this.interval;
            Particle p = system.getNewParticle(this, 1000.0f);
            p.setColor(1.0f, 1.0f, 1.0f, 0.5f);
            p.setPosition(this.x, this.y);
            p.setSize(this.size);
            float vx = (float)(-0.019999999552965164 + Math.random() * 0.03999999910593033);
            float vy = (float)(-(Math.random() * 0.15000000596046448));
            p.setVelocity(vx, vy, 1.1f);
        }
    }

    public void updateParticle(Particle particle, int delta) {
        if (particle.getLife() > 600.0f) {
            particle.adjustSize(0.07f * (float)delta);
        } else {
            particle.adjustSize(-0.04f * (float)delta * (this.size / 40.0f));
        }
        float c = 0.002f * (float)delta;
        particle.adjustColor(0.0f, -c / 2.0f, -c * 2.0f, -c / 4.0f);
    }

    public boolean isEnabled() {
        return true;
    }

    public void setEnabled(boolean enabled) {
    }

    public boolean completed() {
        return false;
    }

    public boolean useAdditive() {
        return false;
    }

    public Image getImage() {
        return null;
    }

    public boolean usePoints(ParticleSystem system) {
        return false;
    }

    public boolean isOriented() {
        return false;
    }

    public void wrapUp() {
    }

    public void resetState() {
    }
}

