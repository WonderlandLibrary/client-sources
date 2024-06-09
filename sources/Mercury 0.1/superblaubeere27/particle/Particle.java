/*
 * Decompiled with CFR 0.145.
 */
package superblaubeere27.particle;

import java.util.Random;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import superblaubeere27.util.MathUtil;

public class Particle {
    private static final Random random = new Random();
    private Vector2f velocity;
    private Vector2f pos;
    private float size;
    private float alpha;

    public Particle(Vector2f velocity, float x2, float y2, float size) {
        this.velocity = velocity;
        this.pos = new Vector2f(x2, y2);
        this.size = size;
    }

    public static Particle generateParticle() {
        Vector2f velocity = new Vector2f((float)(Math.random() * 2.0 - 1.0), (float)(Math.random() * 2.0 - 1.0));
        float x2 = random.nextInt(Display.getWidth());
        float y2 = random.nextInt(Display.getHeight());
        float size = (float)(Math.random() * 4.0) + 1.0f;
        return new Particle(velocity, x2, y2, size);
    }

    public float getAlpha() {
        return this.alpha;
    }

    public Vector2f getVelocity() {
        return this.velocity;
    }

    public void setVelocity(Vector2f velocity) {
        this.velocity = velocity;
    }

    public float getX() {
        return this.pos.getX();
    }

    public void setX(float x2) {
        this.pos.setX(x2);
    }

    public float getY() {
        return this.pos.getY();
    }

    public void setY(float y2) {
        this.pos.setY(y2);
    }

    public float getSize() {
        return this.size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public void tick(int delta, float speed) {
        this.pos.x += this.velocity.getX() * (float)delta * speed;
        this.pos.y += this.velocity.getY() * (float)delta * speed;
        if (this.alpha < 255.0f) {
            this.alpha += 0.05f * (float)delta;
        }
        if (this.pos.getX() > (float)Display.getWidth()) {
            this.pos.setX(0.0f);
        }
        if (this.pos.getX() < 0.0f) {
            this.pos.setX(Display.getWidth());
        }
        if (this.pos.getY() > (float)Display.getHeight()) {
            this.pos.setY(0.0f);
        }
        if (this.pos.getY() < 0.0f) {
            this.pos.setY(Display.getHeight());
        }
    }

    public float getDistanceTo(Particle particle1) {
        return this.getDistanceTo(particle1.getX(), particle1.getY());
    }

    public float getDistanceTo(float x2, float y2) {
        return (float)MathUtil.distance(this.getX(), this.getY(), x2, y2);
    }
}

