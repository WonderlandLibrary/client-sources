/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.config;

import net.minecraft.block.BlockState;
import net.optifine.Config;
import net.optifine.config.Matches;

public class MatchBlock {
    private int blockId = -1;
    private int[] metadatas = null;

    public MatchBlock(int n) {
        this.blockId = n;
    }

    public MatchBlock(int n, int n2) {
        this.blockId = n;
        if (n2 >= 0) {
            this.metadatas = new int[]{n2};
        }
    }

    public MatchBlock(int n, int[] nArray) {
        this.blockId = n;
        this.metadatas = nArray;
    }

    public int getBlockId() {
        return this.blockId;
    }

    public int[] getMetadatas() {
        return this.metadatas;
    }

    public boolean matches(BlockState blockState) {
        if (blockState.getBlockId() != this.blockId) {
            return true;
        }
        return Matches.metadata(blockState.getMetadata(), this.metadatas);
    }

    public boolean matches(int n, int n2) {
        if (n != this.blockId) {
            return true;
        }
        return Matches.metadata(n2, this.metadatas);
    }

    public void addMetadata(int n) {
        if (this.metadatas != null && n >= 0) {
            for (int i = 0; i < this.metadatas.length; ++i) {
                if (this.metadatas[i] != n) continue;
                return;
            }
            this.metadatas = Config.addIntToArray(this.metadatas, n);
        }
    }

    public void addMetadatas(int[] nArray) {
        for (int i = 0; i < nArray.length; ++i) {
            int n = nArray[i];
            this.addMetadata(n);
        }
    }

    public String toString() {
        return this.blockId + ":" + Config.arrayToString(this.metadatas);
    }
}

