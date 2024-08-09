/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders;

import java.util.ArrayList;
import java.util.HashSet;
import net.optifine.Config;
import net.optifine.config.MatchBlock;

public class BlockAlias {
    private int aliasBlockId;
    private int aliasMetadata;
    private MatchBlock[] matchBlocks;

    public BlockAlias(int n, int n2, MatchBlock[] matchBlockArray) {
        this.aliasBlockId = n;
        this.aliasMetadata = n2;
        this.matchBlocks = matchBlockArray;
    }

    public BlockAlias(int n, MatchBlock[] matchBlockArray) {
        this.aliasBlockId = n;
        this.matchBlocks = matchBlockArray;
    }

    public int getAliasBlockId() {
        return this.aliasBlockId;
    }

    public int getAliasMetadata() {
        return this.aliasMetadata;
    }

    public MatchBlock[] getMatchBlocks() {
        return this.matchBlocks;
    }

    public boolean matches(int n, int n2) {
        for (int i = 0; i < this.matchBlocks.length; ++i) {
            MatchBlock matchBlock = this.matchBlocks[i];
            if (!matchBlock.matches(n, n2)) continue;
            return false;
        }
        return true;
    }

    public int[] getMatchBlockIds() {
        HashSet<Integer> hashSet = new HashSet<Integer>();
        for (int i = 0; i < this.matchBlocks.length; ++i) {
            MatchBlock matchBlock = this.matchBlocks[i];
            int n = matchBlock.getBlockId();
            hashSet.add(n);
        }
        Integer[] integerArray = hashSet.toArray(new Integer[hashSet.size()]);
        return Config.toPrimitive(integerArray);
    }

    public MatchBlock[] getMatchBlocks(int n) {
        ArrayList<MatchBlock> arrayList = new ArrayList<MatchBlock>();
        for (int i = 0; i < this.matchBlocks.length; ++i) {
            MatchBlock matchBlock = this.matchBlocks[i];
            if (matchBlock.getBlockId() != n) continue;
            arrayList.add(matchBlock);
        }
        return arrayList.toArray(new MatchBlock[arrayList.size()]);
    }

    public String toString() {
        return "block." + this.aliasBlockId + ":" + this.aliasMetadata + "=" + Config.arrayToString(this.matchBlocks);
    }
}

