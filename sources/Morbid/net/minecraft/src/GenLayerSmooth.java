package net.minecraft.src;

public class GenLayerSmooth extends GenLayer
{
    public GenLayerSmooth(final long par1, final GenLayer par3GenLayer) {
        super(par1);
        super.parent = par3GenLayer;
    }
    
    @Override
    public int[] getInts(final int par1, final int par2, final int par3, final int par4) {
        final int var5 = par1 - 1;
        final int var6 = par2 - 1;
        final int var7 = par3 + 2;
        final int var8 = par4 + 2;
        final int[] var9 = this.parent.getInts(var5, var6, var7, var8);
        final int[] var10 = IntCache.getIntCache(par3 * par4);
        for (int var11 = 0; var11 < par4; ++var11) {
            for (int var12 = 0; var12 < par3; ++var12) {
                final int var13 = var9[var12 + 0 + (var11 + 1) * var7];
                final int var14 = var9[var12 + 2 + (var11 + 1) * var7];
                final int var15 = var9[var12 + 1 + (var11 + 0) * var7];
                final int var16 = var9[var12 + 1 + (var11 + 2) * var7];
                int var17 = var9[var12 + 1 + (var11 + 1) * var7];
                if (var13 == var14 && var15 == var16) {
                    this.initChunkSeed(var12 + par1, var11 + par2);
                    if (this.nextInt(2) == 0) {
                        var17 = var13;
                    }
                    else {
                        var17 = var15;
                    }
                }
                else {
                    if (var13 == var14) {
                        var17 = var13;
                    }
                    if (var15 == var16) {
                        var17 = var15;
                    }
                }
                var10[var12 + var11 * par3] = var17;
            }
        }
        return var10;
    }
}
