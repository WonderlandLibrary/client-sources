package net.minecraft.client.main.neptune.Utils;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.neptune.Wrapper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class Location {
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    
    public Location(final double x, final double y, final double z, final float yaw, final float pitch) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }
    
    public Location(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = 0.0f;
        this.pitch = 0.0f;
    }
    
    public Location(final int x, final int y, final int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = 0.0f;
        this.pitch = 0.0f;
    }
    
    public Location add(final int x, final int y, final int z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }
    
    public Location add(final double x, final double y, final double z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }
    
    public Location subtract(final int x, final int y, final int z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }
    
    public Location subtract(final double x, final double y, final double z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }
    
    public Block getBlock() {
        return Wrapper.getWorld().getBlockState(this.toBlockPos()).getBlock();
    }
    
    public double getX() {
        return this.x;
    }
    
    public Location setX(final double x) {
        this.x = x;
        return this;
    }
    
    public double getY() {
        return this.y;
    }
    
    public Location setY(final double y) {
        this.y = y;
        return this;
    }
    
    public double getZ() {
        return this.z;
    }
    
    public Location setZ(final double z) {
        this.z = z;
        return this;
    }
    
    public float getYaw() {
        return this.yaw;
    }
    
    public Location setYaw(final float yaw) {
        this.yaw = yaw;
        return this;
    }
    
    public float getPitch() {
        return this.pitch;
    }
    
    public Location setPitch(final float pitch) {
        this.pitch = pitch;
        return this;
    }
    
    public static Location fromBlockPos(final BlockPos blockPos) {
        return new Location(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }
    
    public BlockPos toBlockPos() {
        return new BlockPos(this.getX(), this.getY(), this.getZ());
    }
}
