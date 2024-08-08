package me.xatzdevelopments.xatz.client.Unused.superherofx;

import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;

public abstract class FX {
	
	protected Random random;
    protected int ticksAlive;
    protected int maxTicksAlive;
    protected double x;
    protected double y;
    protected double z;
    
    public FX(final EntityLivingBase idk) {
        this.ticksAlive = 0;
        this.maxTicksAlive = 250;
        this.random = new Random();
        final Vec3 position = idk.getPositionVector();
        this.x = position.xCoord + this.getRandomHorizOffset();
        this.y = position.yCoord + this.random.nextDouble() * 0.75;
        this.z = position.zCoord + this.getRandomHorizOffset();
    }
    
    public abstract void onRender();
    
    public void onTick() {
        ++this.ticksAlive;
        this.y += this.random.nextDouble() * 0.01;
    }
    
    public double getRandomHorizOffset() {
        double offset = -0.9;
        offset += this.random.nextInt(150) / 100.0;
        return offset;
    }
    
    public int getTicksAlive() {
        return this.ticksAlive;
    }
    
    public int getMaxTicksAlive() {
        return this.maxTicksAlive;
    }
    
    public double getX() {
        return this.x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public double getZ() {
        return this.z;
    }
}