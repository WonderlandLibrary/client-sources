/*
 * Decompiled with CFR 0.145.
 */
package optifine;

import net.minecraft.block.state.BlockStateBase;
import optifine.Config;
import optifine.Matches;

public class MatchBlock {
    private int blockId = -1;
    private int[] metadatas = null;

    public MatchBlock(int blockId) {
        this.blockId = blockId;
    }

    public MatchBlock(int blockId, int metadata) {
        this.blockId = blockId;
        if (metadata >= 0 && metadata <= 15) {
            this.metadatas = new int[]{metadata};
        }
    }

    public MatchBlock(int blockId, int[] metadatas) {
        this.blockId = blockId;
        this.metadatas = metadatas;
    }

    public int getBlockId() {
        return this.blockId;
    }

    public int[] getMetadatas() {
        return this.metadatas;
    }

    public boolean matches(BlockStateBase blockState) {
        return blockState.getBlockId() != this.blockId ? false : Matches.metadata(blockState.getMetadata(), this.metadatas);
    }

    public boolean matches(int id2, int metadata) {
        return id2 != this.blockId ? false : Matches.metadata(metadata, this.metadatas);
    }

    public void addMetadata(int metadata) {
        if (this.metadatas != null && metadata >= 0 && metadata <= 15) {
            for (int i2 = 0; i2 < this.metadatas.length; ++i2) {
                if (this.metadatas[i2] != metadata) continue;
                return;
            }
            this.metadatas = Config.addIntToArray(this.metadatas, metadata);
        }
    }

    public String toString() {
        return this.blockId + ":" + Config.arrayToString(this.metadatas);
    }
}

