package net.minecraft.src;

import java.util.*;

public class Particle
{
    private static Random rand;
    public double posX;
    public double posY;
    public double prevPosX;
    public double prevPosY;
    public double velocityX;
    public double velocityY;
    public double accelScale;
    public boolean isDead;
    public int timeTick;
    public int timeLimit;
    public double tintRed;
    public double tintGreen;
    public double tintBlue;
    public double tintAlpha;
    public double prevTintRed;
    public double prevTintGreen;
    public double prevTintBlue;
    public double prevTintAlpha;
    
    static {
        Particle.rand = new Random();
    }
    
    public void update(final GuiParticle par1GuiParticle) {
        this.posX += this.velocityX;
        this.posY += this.velocityY;
        this.velocityX *= this.accelScale;
        this.velocityY *= this.accelScale;
        this.velocityY += 0.1;
        if (++this.timeTick > this.timeLimit) {
            this.setDead();
        }
        this.tintAlpha = 2.0 - this.timeTick / this.timeLimit * 2.0;
        if (this.tintAlpha > 1.0) {
            this.tintAlpha = 1.0;
        }
        this.tintAlpha *= this.tintAlpha;
        this.tintAlpha *= 0.5;
    }
    
    public void preUpdate() {
        this.prevTintRed = this.tintRed;
        this.prevTintGreen = this.tintGreen;
        this.prevTintBlue = this.tintBlue;
        this.prevTintAlpha = this.tintAlpha;
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
    }
    
    public void setDead() {
        this.isDead = true;
    }
}
