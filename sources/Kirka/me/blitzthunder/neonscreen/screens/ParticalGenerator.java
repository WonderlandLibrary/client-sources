/*
 * Decompiled with CFR 0.143.
 */
package me.blitzthunder.neonscreen.screens;

import java.util.ArrayList;
import java.util.Random;
import ml.teamreliant.reliant.utils.RenderingUtils;
import ml.teamreliant.reliant.utils.Timer;
import net.minecraft.util.MathHelper;

public class ParticalGenerator {
    private int count;
    private int width;
    private int height;
    private ArrayList<Particle> particles = new ArrayList();
    private Random random = new Random();
    private Timer timer = new Timer();
    int state = 0;
    int a = 255;
    int r = 255;
    int g = 0;
    int b = 0;

    public ParticalGenerator(int count, int width, int height) {
        this.count = count;
        this.width = width;
        this.height = height;
        for (int i = 0; i < count; ++i) {
            this.particles.add(new Particle(this.random.nextInt(width), this.random.nextInt(height)));
        }
    }

    public void drawParticles() {
        for (Particle p : this.particles) {
            if (p.reset) {
                p.resetPosSize();
                p.access$1(p, false);
            }
            p.draw();
        }
    }

    public class Particle {
        private int x;
        private int y;
        private int k;
        private float size;
        private boolean reset;
        private Random random = new Random();
        private Timer timer = new Timer();

        public Particle(int x, int y) {
            this.x = x;
            this.y = y;
            this.size = this.genRandom(1.0f, 3.0f);
        }

        public void draw() {
            if (this.size <= 0.0f) {
                this.reset = true;
            }
            this.size -= 0.05f;
            ++this.k;
            int xx = (int)(MathHelper.cos(0.1f * (float)(this.x + this.k)) * 10.0f);
            int yy = (int)(MathHelper.cos(0.1f * (float)(this.y + this.k)) * 10.0f);
            RenderingUtils.drawBorderedCircle((int)(this.x + xx), (int)(this.y + yy), (float)this.size, (int)0, (int)553648127);
        }

        public void resetPosSize() {
            this.x = this.random.nextInt(ParticalGenerator.this.width);
            this.y = this.random.nextInt(ParticalGenerator.this.height);
            this.size = this.genRandom(1.0f, 3.0f);
        }

        public float genRandom(float min, float max) {
            return (float)((double)min + Math.random() * (double)(max - min + 1.0f));
        }

        public void access$1(Particle p, boolean reset) {
            p.reset = reset;
        }
    }

}

