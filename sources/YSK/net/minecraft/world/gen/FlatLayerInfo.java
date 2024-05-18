package net.minecraft.world.gen;

import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.util.*;

public class FlatLayerInfo
{
    private IBlockState field_175901_b;
    private int layerMinimumY;
    private int layerCount;
    private final int field_175902_a;
    private static final String[] I;
    
    public FlatLayerInfo(final int n, final Block block) {
        this("   ".length(), n, block);
    }
    
    private Block func_151536_b() {
        return this.field_175901_b.getBlock();
    }
    
    public IBlockState func_175900_c() {
        return this.field_175901_b;
    }
    
    public FlatLayerInfo(final int field_175902_a, final int layerCount, final Block block) {
        this.layerCount = " ".length();
        this.field_175902_a = field_175902_a;
        this.layerCount = layerCount;
        this.field_175901_b = block.getDefaultState();
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
            if (3 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public String toString() {
        String s;
        if (this.field_175902_a >= "   ".length()) {
            final ResourceLocation resourceLocation = Block.blockRegistry.getNameForObject(this.func_151536_b());
            String string;
            if (resourceLocation == null) {
                string = FlatLayerInfo.I["".length()];
                "".length();
                if (false) {
                    throw null;
                }
            }
            else {
                string = resourceLocation.toString();
            }
            s = string;
            if (this.layerCount > " ".length()) {
                s = String.valueOf(this.layerCount) + FlatLayerInfo.I[" ".length()] + s;
                "".length();
                if (-1 == 0) {
                    throw null;
                }
            }
        }
        else {
            s = Integer.toString(Block.getIdFromBlock(this.func_151536_b()));
            if (this.layerCount > " ".length()) {
                s = String.valueOf(this.layerCount) + FlatLayerInfo.I["  ".length()] + s;
            }
        }
        final int fillBlockMeta = this.getFillBlockMeta();
        if (fillBlockMeta > 0) {
            s = String.valueOf(s) + FlatLayerInfo.I["   ".length()] + fillBlockMeta;
        }
        return s;
    }
    
    private int getFillBlockMeta() {
        return this.field_175901_b.getBlock().getMetaFromState(this.field_175901_b);
    }
    
    static {
        I();
    }
    
    public FlatLayerInfo(final int n, final int n2, final Block block, final int n3) {
        this(n, n2, block);
        this.field_175901_b = block.getStateFromMeta(n3);
    }
    
    public void setMinY(final int layerMinimumY) {
        this.layerMinimumY = layerMinimumY;
    }
    
    public int getLayerCount() {
        return this.layerCount;
    }
    
    public int getMinY() {
        return this.layerMinimumY;
    }
    
    private static void I() {
        (I = new String[0x36 ^ 0x32])["".length()] = I("\u0007\u0003\u001c9", "ivpUs");
        FlatLayerInfo.I[" ".length()] = I("x", "RLruZ");
        FlatLayerInfo.I["  ".length()] = I("1", "InqIB");
        FlatLayerInfo.I["   ".length()] = I("I", "shGcF");
    }
}
