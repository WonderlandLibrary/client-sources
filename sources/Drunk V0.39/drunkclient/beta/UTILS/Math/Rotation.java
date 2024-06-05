/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.UTILS.Math;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class Rotation {
    public float yaw;
    public float pitch;
    private BlockPos currentPos;
    private EnumFacing currentFacing;
    private static final Minecraft mc = Minecraft.getMinecraft();

    public Rotation(float yaw, float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public float getYaw() {
        return this.yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public void setBlockAndFacing(BlockPos var1) {
        if (Rotation.mc.theWorld.getBlockState(var1.add(0, -1, 0)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(0, -1, 0);
            this.currentFacing = EnumFacing.UP;
            return;
        }
        if (Rotation.mc.theWorld.getBlockState(var1.add(-1, 0, 0)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(-1, 0, 0);
            this.currentFacing = EnumFacing.EAST;
            return;
        }
        if (Rotation.mc.theWorld.getBlockState(var1.add(1, 0, 0)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(1, 0, 0);
            this.currentFacing = EnumFacing.WEST;
            return;
        }
        if (Rotation.mc.theWorld.getBlockState(var1.add(0, 0, -1)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(0, 0, -1);
            this.currentFacing = EnumFacing.SOUTH;
            return;
        }
        if (Rotation.mc.theWorld.getBlockState(var1.add(0, 0, 1)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(0, 0, 1);
            this.currentFacing = EnumFacing.NORTH;
            return;
        }
        if (Rotation.mc.theWorld.getBlockState(var1.add(-1, 0, -1)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(-1, 0, -1);
            this.currentFacing = EnumFacing.EAST;
            return;
        }
        if (Rotation.mc.theWorld.getBlockState(var1.add(-1, 0, 1)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(-1, 0, 1);
            this.currentFacing = EnumFacing.EAST;
            return;
        }
        if (Rotation.mc.theWorld.getBlockState(var1.add(1, 0, -1)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(1, 0, -1);
            this.currentFacing = EnumFacing.WEST;
            return;
        }
        if (Rotation.mc.theWorld.getBlockState(var1.add(1, 0, 1)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(1, 0, 1);
            this.currentFacing = EnumFacing.WEST;
            return;
        }
        if (Rotation.mc.theWorld.getBlockState(var1.add(-1, -1, 0)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(-1, -1, 0);
            this.currentFacing = EnumFacing.EAST;
            return;
        }
        if (Rotation.mc.theWorld.getBlockState(var1.add(1, -1, 0)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(1, -1, 0);
            this.currentFacing = EnumFacing.WEST;
            return;
        }
        if (Rotation.mc.theWorld.getBlockState(var1.add(0, -1, -1)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(0, -1, -1);
            this.currentFacing = EnumFacing.SOUTH;
            return;
        }
        if (Rotation.mc.theWorld.getBlockState(var1.add(0, -1, 1)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(0, -1, 1);
            this.currentFacing = EnumFacing.NORTH;
            return;
        }
        if (Rotation.mc.theWorld.getBlockState(var1.add(-1, -1, -1)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(-1, -1, -1);
            this.currentFacing = EnumFacing.EAST;
            return;
        }
        if (Rotation.mc.theWorld.getBlockState(var1.add(-1, -1, 1)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(-1, -1, 1);
            this.currentFacing = EnumFacing.EAST;
            return;
        }
        if (Rotation.mc.theWorld.getBlockState(var1.add(1, -1, -1)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(1, -1, -1);
            this.currentFacing = EnumFacing.WEST;
            return;
        }
        if (Rotation.mc.theWorld.getBlockState(var1.add(1, -1, 1)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(1, -1, 1);
            this.currentFacing = EnumFacing.WEST;
            return;
        }
        if (Rotation.mc.theWorld.getBlockState(var1.add(-2, 0, 0)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(-2, 0, 0);
            this.currentFacing = EnumFacing.EAST;
            return;
        }
        if (Rotation.mc.theWorld.getBlockState(var1.add(2, 0, 0)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(2, 0, 0);
            this.currentFacing = EnumFacing.WEST;
            return;
        }
        if (Rotation.mc.theWorld.getBlockState(var1.add(0, 0, -2)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(0, 0, -2);
            this.currentFacing = EnumFacing.SOUTH;
            return;
        }
        if (Rotation.mc.theWorld.getBlockState(var1.add(0, 0, 2)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(0, 0, 2);
            this.currentFacing = EnumFacing.NORTH;
            return;
        }
        if (Rotation.mc.theWorld.getBlockState(var1.add(-2, 0, -2)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(-2, 0, -2);
            this.currentFacing = EnumFacing.EAST;
            return;
        }
        if (Rotation.mc.theWorld.getBlockState(var1.add(-2, 0, 2)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(-2, 0, 2);
            this.currentFacing = EnumFacing.EAST;
            return;
        }
        if (Rotation.mc.theWorld.getBlockState(var1.add(2, 0, -2)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(2, 0, -2);
            this.currentFacing = EnumFacing.WEST;
            return;
        }
        if (Rotation.mc.theWorld.getBlockState(var1.add(2, 0, 2)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(2, 0, 2);
            this.currentFacing = EnumFacing.WEST;
            return;
        }
        if (Rotation.mc.theWorld.getBlockState(var1.add(0, 1, 0)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(0, 1, 0);
            this.currentFacing = EnumFacing.DOWN;
            return;
        }
        if (Rotation.mc.theWorld.getBlockState(var1.add(-1, 1, 0)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(-1, 1, 0);
            this.currentFacing = EnumFacing.EAST;
            return;
        }
        if (Rotation.mc.theWorld.getBlockState(var1.add(1, 1, 0)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(1, 1, 0);
            this.currentFacing = EnumFacing.WEST;
            return;
        }
        if (Rotation.mc.theWorld.getBlockState(var1.add(0, 1, -1)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(0, 1, -1);
            this.currentFacing = EnumFacing.SOUTH;
            return;
        }
        if (Rotation.mc.theWorld.getBlockState(var1.add(0, 1, 1)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(0, 1, 1);
            this.currentFacing = EnumFacing.NORTH;
            return;
        }
        if (Rotation.mc.theWorld.getBlockState(var1.add(-1, 1, -1)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(-1, 1, -1);
            this.currentFacing = EnumFacing.EAST;
            return;
        }
        if (Rotation.mc.theWorld.getBlockState(var1.add(-1, 1, 1)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(-1, 1, 1);
            this.currentFacing = EnumFacing.EAST;
            return;
        }
        if (Rotation.mc.theWorld.getBlockState(var1.add(1, 1, -1)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(1, 1, -1);
            this.currentFacing = EnumFacing.WEST;
            return;
        }
        if (Rotation.mc.theWorld.getBlockState(var1.add(1, 1, 1)).getBlock() == Blocks.air) return;
        this.currentPos = var1.add(1, 1, 1);
        this.currentFacing = EnumFacing.WEST;
    }
}

