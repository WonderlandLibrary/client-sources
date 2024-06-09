/*
 * Decompiled with CFR 0.145.
 */
package net.minecraft.world.chunk.storage;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraft.world.chunk.NibbleArray;
import optifine.Reflector;
import optifine.ReflectorClass;
import optifine.ReflectorMethod;

public class ExtendedBlockStorage {
    private int yBase;
    private int blockRefCount;
    private int tickRefCount;
    private char[] data;
    private NibbleArray blocklightArray;
    private NibbleArray skylightArray;
    private static final String __OBFID = "CL_00000375";

    public ExtendedBlockStorage(int y2, boolean storeSkylight) {
        this.yBase = y2;
        this.data = new char[4096];
        this.blocklightArray = new NibbleArray();
        if (storeSkylight) {
            this.skylightArray = new NibbleArray();
        }
    }

    public IBlockState get(int x2, int y2, int z2) {
        IBlockState var4 = (IBlockState)Block.BLOCK_STATE_IDS.getByValue(this.data[y2 << 8 | z2 << 4 | x2]);
        return var4 != null ? var4 : Blocks.air.getDefaultState();
    }

    public void set(int x2, int y2, int z2, IBlockState state) {
        if (Reflector.IExtendedBlockState.isInstance(state)) {
            state = (IBlockState)Reflector.call(state, Reflector.IExtendedBlockState_getClean, new Object[0]);
        }
        IBlockState var5 = this.get(x2, y2, z2);
        Block var6 = var5.getBlock();
        Block var7 = state.getBlock();
        if (var6 != Blocks.air) {
            --this.blockRefCount;
            if (var6.getTickRandomly()) {
                --this.tickRefCount;
            }
        }
        if (var7 != Blocks.air) {
            ++this.blockRefCount;
            if (var7.getTickRandomly()) {
                ++this.tickRefCount;
            }
        }
        this.data[y2 << 8 | z2 << 4 | x2] = (char)Block.BLOCK_STATE_IDS.get(state);
    }

    public Block getBlockByExtId(int x2, int y2, int z2) {
        return this.get(x2, y2, z2).getBlock();
    }

    public int getExtBlockMetadata(int x2, int y2, int z2) {
        IBlockState var4 = this.get(x2, y2, z2);
        return var4.getBlock().getMetaFromState(var4);
    }

    public boolean isEmpty() {
        return this.blockRefCount == 0;
    }

    public boolean getNeedsRandomTick() {
        return this.tickRefCount > 0;
    }

    public int getYLocation() {
        return this.yBase;
    }

    public void setExtSkylightValue(int x2, int y2, int z2, int value) {
        this.skylightArray.set(x2, y2, z2, value);
    }

    public int getExtSkylightValue(int x2, int y2, int z2) {
        return this.skylightArray.get(x2, y2, z2);
    }

    public void setExtBlocklightValue(int x2, int y2, int z2, int value) {
        this.blocklightArray.set(x2, y2, z2, value);
    }

    public int getExtBlocklightValue(int x2, int y2, int z2) {
        return this.blocklightArray.get(x2, y2, z2);
    }

    public void removeInvalidBlocks() {
        List blockStates = Block.BLOCK_STATE_IDS.getObjectList();
        int maxStateId = blockStates.size();
        int localBlockRefCount = 0;
        int localTickRefCount = 0;
        for (int y2 = 0; y2 < 16; ++y2) {
            int by = y2 << 8;
            for (int z2 = 0; z2 < 16; ++z2) {
                int byz = by | z2 << 4;
                for (int x2 = 0; x2 < 16; ++x2) {
                    IBlockState bs2;
                    Block var4;
                    char stateId = this.data[byz | x2];
                    if (stateId <= '\u0000') continue;
                    ++localBlockRefCount;
                    if (stateId >= maxStateId || (bs2 = (IBlockState)blockStates.get(stateId)) == null || !(var4 = bs2.getBlock()).getTickRandomly()) continue;
                    ++localTickRefCount;
                }
            }
        }
        this.blockRefCount = localBlockRefCount;
        this.tickRefCount = localTickRefCount;
    }

    public char[] getData() {
        return this.data;
    }

    public void setData(char[] dataArray) {
        this.data = dataArray;
    }

    public NibbleArray getBlocklightArray() {
        return this.blocklightArray;
    }

    public NibbleArray getSkylightArray() {
        return this.skylightArray;
    }

    public void setBlocklightArray(NibbleArray newBlocklightArray) {
        this.blocklightArray = newBlocklightArray;
    }

    public void setSkylightArray(NibbleArray newSkylightArray) {
        this.skylightArray = newSkylightArray;
    }
}

