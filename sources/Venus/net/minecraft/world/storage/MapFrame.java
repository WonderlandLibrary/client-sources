/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.storage;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.BlockPos;

public class MapFrame {
    private final BlockPos pos;
    private final int rotation;
    private final int entityId;

    public MapFrame(BlockPos blockPos, int n, int n2) {
        this.pos = blockPos;
        this.rotation = n;
        this.entityId = n2;
    }

    public static MapFrame read(CompoundNBT compoundNBT) {
        BlockPos blockPos = NBTUtil.readBlockPos(compoundNBT.getCompound("Pos"));
        int n = compoundNBT.getInt("Rotation");
        int n2 = compoundNBT.getInt("EntityId");
        return new MapFrame(blockPos, n, n2);
    }

    public CompoundNBT write() {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.put("Pos", NBTUtil.writeBlockPos(this.pos));
        compoundNBT.putInt("Rotation", this.rotation);
        compoundNBT.putInt("EntityId", this.entityId);
        return compoundNBT;
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public int getRotation() {
        return this.rotation;
    }

    public int getEntityId() {
        return this.entityId;
    }

    public String getFrameName() {
        return MapFrame.getFrameNameWithPos(this.pos);
    }

    public static String getFrameNameWithPos(BlockPos blockPos) {
        return "frame-" + blockPos.getX() + "," + blockPos.getY() + "," + blockPos.getZ();
    }
}

