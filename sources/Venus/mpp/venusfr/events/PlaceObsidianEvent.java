/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.events;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

public class PlaceObsidianEvent {
    private Block block;
    private BlockPos pos;

    public Block getBlock() {
        return this.block;
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public void setPos(BlockPos blockPos) {
        this.pos = blockPos;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (!(object instanceof PlaceObsidianEvent)) {
            return true;
        }
        PlaceObsidianEvent placeObsidianEvent = (PlaceObsidianEvent)object;
        if (!placeObsidianEvent.canEqual(this)) {
            return true;
        }
        Block block = this.getBlock();
        Block block2 = placeObsidianEvent.getBlock();
        if (block == null ? block2 != null : !block.equals(block2)) {
            return true;
        }
        BlockPos blockPos = this.getPos();
        BlockPos blockPos2 = placeObsidianEvent.getPos();
        return blockPos == null ? blockPos2 != null : !((Object)blockPos).equals(blockPos2);
    }

    protected boolean canEqual(Object object) {
        return object instanceof PlaceObsidianEvent;
    }

    public int hashCode() {
        int n = 59;
        int n2 = 1;
        Block block = this.getBlock();
        n2 = n2 * 59 + (block == null ? 43 : block.hashCode());
        BlockPos blockPos = this.getPos();
        n2 = n2 * 59 + (blockPos == null ? 43 : ((Object)blockPos).hashCode());
        return n2;
    }

    public String toString() {
        return "PlaceObsidianEvent(block=" + this.getBlock() + ", pos=" + this.getPos() + ")";
    }

    public PlaceObsidianEvent(Block block, BlockPos blockPos) {
        this.block = block;
        this.pos = blockPos;
    }
}

