package me.rich.helpers.combat;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;

public class LPositionHelper {

    private double x, y, z;
    private boolean ground;

    public LPositionHelper(double x, double y, double z, boolean ground) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.ground = ground;
    }

    public LPositionHelper(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.ground = true;
    }

    public LPositionHelper(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.ground = true;
    }

    public static LPositionHelper fromBlockPos(BlockPos blockPos) {
        return new LPositionHelper(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    public LPositionHelper add(int x, int y, int z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public LPositionHelper add(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public LPositionHelper subtract(int x, int y, int z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;

        return this;
    }

    public LPositionHelper subtract(double x, double y, double z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;

        return this;
    }

    public Block getBlock() {
        return Minecraft.getMinecraft().world.getBlockState(this.toBlockPos()).getBlock();
    }

    public boolean isOnGround() {
        return this.ground;
    }

    public LPositionHelper setOnGround(boolean ground) {
        this.ground = ground;
        return this;
    }

    public double getX() {
        return x;
    }

    public LPositionHelper setX(double x) {
        this.x = x;
        return this;
    }

    public double getY() {
        return y;
    }

    public LPositionHelper setY(double y) {
        this.y = y;
        return this;
    }

    public double getZ() {
        return z;
    }

    public LPositionHelper setZ(double z) {
        this.z = z;
        return this;
    }

    public BlockPos toBlockPos() {
        return new BlockPos(getX(), getY(), getZ());
    }

}