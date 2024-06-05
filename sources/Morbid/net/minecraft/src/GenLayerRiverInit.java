package net.minecraft.src;

public class GenLayerRiverInit extends GenLayer
{
    public GenLayerRiverInit(final long par1, final GenLayer par3GenLayer) {
        super(par1);
        this.parent = par3GenLayer;
    }
    
    @Override
    public int[] getInts(final int par1, final int par2, final int par3, final int par4) {
        final int[] var5 = this.parent.getInts(par1, par2, par3, par4);
        final int[] var6 = IntCache.getIntCache(par3 * par4);
        for (int var7 = 0; var7 < par4; ++var7) {
            for (int var8 = 0; var8 < par3; ++var8) {
                this.initChunkSeed(var8 + par1, var7 + par2);
                var6[var8 + var7 * par3] = ((var5[var8 + var7 * par3] > 0) ? (this.nextInt(2) + 2) : 0);
            }
        }
        return var6;
    }
}
