package net.minecraft.src;

public class GenLayerIsland extends GenLayer
{
    public GenLayerIsland(final long par1) {
        super(par1);
    }
    
    @Override
    public int[] getInts(final int par1, final int par2, final int par3, final int par4) {
        final int[] var5 = IntCache.getIntCache(par3 * par4);
        for (int var6 = 0; var6 < par4; ++var6) {
            for (int var7 = 0; var7 < par3; ++var7) {
                this.initChunkSeed(par1 + var7, par2 + var6);
                var5[var7 + var6 * par3] = ((this.nextInt(10) == 0) ? 1 : 0);
            }
        }
        if (par1 > -par3 && par1 <= 0 && par2 > -par4 && par2 <= 0) {
            var5[-par1 + -par2 * par3] = 1;
        }
        return var5;
    }
}
