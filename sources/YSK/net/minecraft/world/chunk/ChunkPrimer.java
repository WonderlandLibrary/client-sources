package net.minecraft.world.chunk;

import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.init.*;

public class ChunkPrimer
{
    private final short[] data;
    private final IBlockState defaultState;
    private static final String[] I;
    
    public void setBlockState(final int n, final IBlockState blockState) {
        if (n < 0 || n >= this.data.length) {
            throw new IndexOutOfBoundsException(ChunkPrimer.I[" ".length()]);
        }
        this.data[n] = (short)Block.BLOCK_STATE_IDS.get(blockState);
        "".length();
        if (0 >= 2) {
            throw null;
        }
    }
    
    public void setBlockState(final int n, final int n2, final int n3, final IBlockState blockState) {
        this.setBlockState(n << (0x40 ^ 0x4C) | n3 << (0x7D ^ 0x75) | n2, blockState);
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
            if (!true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
    }
    
    public IBlockState getBlockState(final int n, final int n2, final int n3) {
        return this.getBlockState(n << (0x42 ^ 0x4E) | n3 << (0x1F ^ 0x17) | n2);
    }
    
    public IBlockState getBlockState(final int n) {
        if (n >= 0 && n < this.data.length) {
            final IBlockState blockState = (IBlockState)Block.BLOCK_STATE_IDS.getByValue(this.data[n]);
            IBlockState defaultState;
            if (blockState != null) {
                defaultState = blockState;
                "".length();
                if (3 <= 0) {
                    throw null;
                }
            }
            else {
                defaultState = this.defaultState;
            }
            return defaultState;
        }
        throw new IndexOutOfBoundsException(ChunkPrimer.I["".length()]);
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("\u001d<?z\u0014&;(>\u001e'5.?W 'z5\u0002=t5<W;54=\u0012", "ITZZw");
        ChunkPrimer.I[" ".length()] = I("\u00010<a3:7+%9;9-$p<+y.%!x6'p'97&5", "UXYAP");
    }
    
    public ChunkPrimer() {
        this.data = new short[20885 + 52367 - 65671 + 57955];
        this.defaultState = Blocks.air.getDefaultState();
    }
}
