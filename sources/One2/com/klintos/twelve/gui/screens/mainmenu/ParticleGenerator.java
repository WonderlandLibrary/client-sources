// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.gui.screens.mainmenu;

import com.klintos.twelve.utils.GuiUtils;
import net.minecraft.util.MathHelper;
import java.util.Iterator;
import com.klintos.twelve.utils.TimerUtil;
import java.util.Random;
import java.util.ArrayList;

public class ParticleGenerator
{
    private int count;
    private int width;
    private int height;
    private ArrayList<Particle> particles;
    private Random random;
    private TimerUtil timer;
    int state;
    int a;
    int r;
    int g;
    int b;
    
    public ParticleGenerator(final int count, final int width, final int height) {
        this.particles = new ArrayList<Particle>();
        this.random = new Random();
        this.timer = new TimerUtil();
        this.state = 0;
        this.a = 255;
        this.r = 255;
        this.g = 0;
        this.b = 0;
        this.count = count;
        this.width = width;
        this.height = height;
        for (int i = 0; i < count; ++i) {
            this.particles.add(new Particle(this.random.nextInt(width), this.random.nextInt(height)));
        }
    }
    
    public void drawParticles() {
        for (final Particle p : this.particles) {
            if (p.reset) {
                p.resetPosSize();
                p.access$1(p, false);
            }
            p.draw();
        }
    }
    
    public class Particle
    {
        private int x;
        private int y;
        private int k;
        private float size;
        private boolean reset;
        private Random random;
        private TimerUtil timer;
        
        public Particle(final int x, final int y) {
            this.random = new Random();
            this.timer = new TimerUtil();
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
            final int xx = (int)(MathHelper.cos(0.1f * (this.x + this.k)) * 10.0f);
            final int yy = (int)(MathHelper.cos(0.1f * (this.y + this.k)) * 10.0f);
            GuiUtils.drawBorderedCircle(this.x + xx, this.y + yy, this.size, 0, 553648127);
        }
        
        public void resetPosSize() {
            this.x = this.random.nextInt(ParticleGenerator.this.width);
            this.y = this.random.nextInt(ParticleGenerator.this.height);
            this.size = this.genRandom(1.0f, 3.0f);
        }
        
        public float genRandom(final float min, final float max) {
            return (float)(min + Math.random() * (max - min + 1.0f));
        }
        
        public void access$1(final Particle particle, final boolean reset) {
            particle.reset = reset;
        }
    }
}
