package net.minecraft.world.chunk.storage;

import net.minecraft.world.chunk.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import java.util.*;

public class ExtendedBlockStorage
{
    private static final String __OBFID;
    private NibbleArray skylightArray;
    private char[] data;
    private static final String[] I;
    private int tickRefCount;
    private int yBase;
    private int blockRefCount;
    private NibbleArray blocklightArray;
    
    public int getExtBlocklightValue(final int n, final int n2, final int n3) {
        return this.blocklightArray.get(n, n2, n3);
    }
    
    public NibbleArray getBlocklightArray() {
        return this.blocklightArray;
    }
    
    public void set(final int n, final int n2, final int n3, final IBlockState blockState) {
        final Block block = this.get(n, n2, n3).getBlock();
        final Block block2 = blockState.getBlock();
        if (block != Blocks.air) {
            this.blockRefCount -= " ".length();
            if (block.getTickRandomly()) {
                this.tickRefCount -= " ".length();
            }
        }
        if (block2 != Blocks.air) {
            this.blockRefCount += " ".length();
            if (block2.getTickRandomly()) {
                this.tickRefCount += " ".length();
            }
        }
        this.data[n2 << (0x9D ^ 0x95) | n3 << (0x58 ^ 0x5C) | n] = (char)Block.BLOCK_STATE_IDS.get(blockState);
    }
    
    static {
        I();
        __OBFID = ExtendedBlockStorage.I["".length()];
    }
    
    public int getExtSkylightValue(final int n, final int n2, final int n3) {
        return this.skylightArray.get(n, n2, n3);
    }
    
    public boolean isEmpty() {
        if (this.blockRefCount == 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (1 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public int getYLocation() {
        return this.yBase;
    }
    
    public ExtendedBlockStorage(final int yBase, final boolean b) {
        this.yBase = yBase;
        this.data = new char[240 + 318 + 2268 + 1270];
        this.blocklightArray = new NibbleArray();
        if (b) {
            this.skylightArray = new NibbleArray();
        }
    }
    
    public int getExtBlockMetadata(final int n, final int n2, final int n3) {
        final IBlockState value = this.get(n, n2, n3);
        return value.getBlock().getMetaFromState(value);
    }
    
    public Block getBlockByExtId(final int n, final int n2, final int n3) {
        return this.get(n, n2, n3).getBlock();
    }
    
    public void setExtBlocklightValue(final int n, final int n2, final int n3, final int n4) {
        this.blocklightArray.set(n, n2, n3, n4);
    }
    
    public IBlockState get(final int n, final int n2, final int n3) {
        final IBlockState blockState = (IBlockState)Block.BLOCK_STATE_IDS.getByValue(this.data[n2 << (0x24 ^ 0x2C) | n3 << (0xE ^ 0xA) | n]);
        IBlockState defaultState;
        if (blockState != null) {
            defaultState = blockState;
            "".length();
            if (2 <= -1) {
                throw null;
            }
        }
        else {
            defaultState = Blocks.air.getDefaultState();
        }
        return defaultState;
    }
    
    public boolean getNeedsRandomTick() {
        if (this.tickRefCount > 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public void setSkylightArray(final NibbleArray skylightArray) {
        this.skylightArray = skylightArray;
    }
    
    public char[] getData() {
        return this.data;
    }
    
    public void removeInvalidBlocks() {
        final List objectList = Block.BLOCK_STATE_IDS.getObjectList();
        final int size = objectList.size();
        int length = "".length();
        int length2 = "".length();
        int i = "".length();
        "".length();
        if (0 >= 1) {
            throw null;
        }
        while (i < (0x3A ^ 0x2A)) {
            final int n = i << (0x6C ^ 0x64);
            int j = "".length();
            "".length();
            if (4 <= 3) {
                throw null;
            }
            while (j < (0x8E ^ 0x9E)) {
                final int n2 = n | j << (0x62 ^ 0x66);
                int k = "".length();
                "".length();
                if (3 < 3) {
                    throw null;
                }
                while (k < (0x96 ^ 0x86)) {
                    final char c = this.data[n2 | k];
                    if (c > '\0') {
                        ++length;
                        if (c < size) {
                            final IBlockState blockState = objectList.get(c);
                            if (blockState != null && blockState.getBlock().getTickRandomly()) {
                                ++length2;
                            }
                        }
                    }
                    ++k;
                }
                ++j;
            }
            ++i;
        }
        this.blockRefCount = length;
        this.tickRefCount = length2;
    }
    
    public void setBlocklightArray(final NibbleArray blocklightArray) {
        this.blocklightArray = blocklightArray;
    }
    
    public NibbleArray getSkylightArray() {
        return this.skylightArray;
    }
    
    public void setExtSkylightValue(final int n, final int n2, final int n3, final int n4) {
        this.skylightArray.set(n, n2, n3, n4);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0000\u001a0b[sf_a\\v", "CVoRk");
    }
    
    public void setData(final char[] data) {
        this.data = data;
    }
}
