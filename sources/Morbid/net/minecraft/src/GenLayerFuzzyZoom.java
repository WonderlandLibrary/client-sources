package net.minecraft.src;

public class GenLayerFuzzyZoom extends GenLayer
{
    public GenLayerFuzzyZoom(final long par1, final GenLayer par3GenLayer) {
        super(par1);
        super.parent = par3GenLayer;
    }
    
    @Override
    public int[] getInts(final int par1, final int par2, final int par3, final int par4) {
        final int var5 = par1 >> 1;
        final int var6 = par2 >> 1;
        final int var7 = (par3 >> 1) + 3;
        final int var8 = (par4 >> 1) + 3;
        final int[] var9 = this.parent.getInts(var5, var6, var7, var8);
        final int[] var10 = IntCache.getIntCache(var7 * 2 * var8 * 2);
        final int var11 = var7 << 1;
        for (int var12 = 0; var12 < var8 - 1; ++var12) {
            final int var13 = var12 << 1;
            int var14 = var13 * var11;
            int var15 = var9[0 + (var12 + 0) * var7];
            int var16 = var9[0 + (var12 + 1) * var7];
            for (int var17 = 0; var17 < var7 - 1; ++var17) {
                this.initChunkSeed(var17 + var5 << 1, var12 + var6 << 1);
                final int var18 = var9[var17 + 1 + (var12 + 0) * var7];
                final int var19 = var9[var17 + 1 + (var12 + 1) * var7];
                var10[var14] = var15;
                var10[var14++ + var11] = this.choose(var15, var16);
                var10[var14] = this.choose(var15, var18);
                var10[var14++ + var11] = this.choose(var15, var18, var16, var19);
                var15 = var18;
                var16 = var19;
            }
        }
        final int[] var20 = IntCache.getIntCache(par3 * par4);
        for (int var13 = 0; var13 < par4; ++var13) {
            System.arraycopy(var10, (var13 + (par2 & 0x1)) * (var7 << 1) + (par1 & 0x1), var20, var13 * par3, par3);
        }
        return var20;
    }
    
    protected int choose(final int par1, final int par2) {
        return (this.nextInt(2) == 0) ? par1 : par2;
    }
    
    protected int choose(final int par1, final int par2, final int par3, final int par4) {
        final int var5 = this.nextInt(4);
        return (var5 == 0) ? par1 : ((var5 == 1) ? par2 : ((var5 == 2) ? par3 : par4));
    }
}
