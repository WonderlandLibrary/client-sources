package net.minecraft.src;

public class GenLayerVoronoiZoom extends GenLayer
{
    public GenLayerVoronoiZoom(final long par1, final GenLayer par3GenLayer) {
        super(par1);
        super.parent = par3GenLayer;
    }
    
    @Override
    public int[] getInts(int par1, int par2, final int par3, final int par4) {
        par1 -= 2;
        par2 -= 2;
        final byte var5 = 2;
        final int var6 = 1 << var5;
        final int var7 = par1 >> var5;
        final int var8 = par2 >> var5;
        final int var9 = (par3 >> var5) + 3;
        final int var10 = (par4 >> var5) + 3;
        final int[] var11 = this.parent.getInts(var7, var8, var9, var10);
        final int var12 = var9 << var5;
        final int var13 = var10 << var5;
        final int[] var14 = IntCache.getIntCache(var12 * var13);
        for (int var15 = 0; var15 < var10 - 1; ++var15) {
            int var16 = var11[0 + (var15 + 0) * var9];
            int var17 = var11[0 + (var15 + 1) * var9];
            for (int var18 = 0; var18 < var9 - 1; ++var18) {
                final double var19 = var6 * 0.9;
                this.initChunkSeed(var18 + var7 << var5, var15 + var8 << var5);
                final double var20 = (this.nextInt(1024) / 1024.0 - 0.5) * var19;
                final double var21 = (this.nextInt(1024) / 1024.0 - 0.5) * var19;
                this.initChunkSeed(var18 + var7 + 1 << var5, var15 + var8 << var5);
                final double var22 = (this.nextInt(1024) / 1024.0 - 0.5) * var19 + var6;
                final double var23 = (this.nextInt(1024) / 1024.0 - 0.5) * var19;
                this.initChunkSeed(var18 + var7 << var5, var15 + var8 + 1 << var5);
                final double var24 = (this.nextInt(1024) / 1024.0 - 0.5) * var19;
                final double var25 = (this.nextInt(1024) / 1024.0 - 0.5) * var19 + var6;
                this.initChunkSeed(var18 + var7 + 1 << var5, var15 + var8 + 1 << var5);
                final double var26 = (this.nextInt(1024) / 1024.0 - 0.5) * var19 + var6;
                final double var27 = (this.nextInt(1024) / 1024.0 - 0.5) * var19 + var6;
                final int var28 = var11[var18 + 1 + (var15 + 0) * var9];
                final int var29 = var11[var18 + 1 + (var15 + 1) * var9];
                for (int var30 = 0; var30 < var6; ++var30) {
                    int var31 = ((var15 << var5) + var30) * var12 + (var18 << var5);
                    for (int var32 = 0; var32 < var6; ++var32) {
                        final double var33 = (var30 - var21) * (var30 - var21) + (var32 - var20) * (var32 - var20);
                        final double var34 = (var30 - var23) * (var30 - var23) + (var32 - var22) * (var32 - var22);
                        final double var35 = (var30 - var25) * (var30 - var25) + (var32 - var24) * (var32 - var24);
                        final double var36 = (var30 - var27) * (var30 - var27) + (var32 - var26) * (var32 - var26);
                        if (var33 < var34 && var33 < var35 && var33 < var36) {
                            var14[var31++] = var16;
                        }
                        else if (var34 < var33 && var34 < var35 && var34 < var36) {
                            var14[var31++] = var28;
                        }
                        else if (var35 < var33 && var35 < var34 && var35 < var36) {
                            var14[var31++] = var17;
                        }
                        else {
                            var14[var31++] = var29;
                        }
                    }
                }
                var16 = var28;
                var17 = var29;
            }
        }
        final int[] var37 = IntCache.getIntCache(par3 * par4);
        for (int var16 = 0; var16 < par4; ++var16) {
            System.arraycopy(var14, (var16 + (par2 & var6 - 1)) * (var9 << var5) + (par1 & var6 - 1), var37, var16 * par3, par3);
        }
        return var37;
    }
}
