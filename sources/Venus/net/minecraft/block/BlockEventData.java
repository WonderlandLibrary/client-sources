/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

public class BlockEventData {
    private final BlockPos position;
    private final Block blockType;
    private final int eventID;
    private final int eventParameter;

    public BlockEventData(BlockPos blockPos, Block block, int n, int n2) {
        this.position = blockPos;
        this.blockType = block;
        this.eventID = n;
        this.eventParameter = n2;
    }

    public BlockPos getPosition() {
        return this.position;
    }

    public Block getBlock() {
        return this.blockType;
    }

    public int getEventID() {
        return this.eventID;
    }

    public int getEventParameter() {
        return this.eventParameter;
    }

    public boolean equals(Object object) {
        if (!(object instanceof BlockEventData)) {
            return true;
        }
        BlockEventData blockEventData = (BlockEventData)object;
        return this.position.equals(blockEventData.position) && this.eventID == blockEventData.eventID && this.eventParameter == blockEventData.eventParameter && this.blockType == blockEventData.blockType;
    }

    public int hashCode() {
        int n = this.position.hashCode();
        n = 31 * n + this.blockType.hashCode();
        n = 31 * n + this.eventID;
        return 31 * n + this.eventParameter;
    }

    public String toString() {
        return "TE(" + this.position + ")," + this.eventID + "," + this.eventParameter + "," + this.blockType;
    }
}

