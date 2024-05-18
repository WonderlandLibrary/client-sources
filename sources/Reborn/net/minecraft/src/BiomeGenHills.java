package net.minecraft.src;

import java.util.*;

public class BiomeGenHills extends BiomeGenBase
{
    private WorldGenerator theWorldGenerator;
    
    protected BiomeGenHills(final int par1) {
        super(par1);
        this.theWorldGenerator = new WorldGenMinable(Block.silverfish.blockID, 8);
    }
    
    @Override
    public void decorate(final World par1World, final Random par2Random, final int par3, final int par4) {
        super.decorate(par1World, par2Random, par3, par4);
        for (int var5 = 3 + par2Random.nextInt(6), var6 = 0; var6 < var5; ++var6) {
            final int var7 = par3 + par2Random.nextInt(16);
            final int var8 = par2Random.nextInt(28) + 4;
            final int var9 = par4 + par2Random.nextInt(16);
            final int var10 = par1World.getBlockId(var7, var8, var9);
            if (var10 == Block.stone.blockID) {
                par1World.setBlock(var7, var8, var9, Block.oreEmerald.blockID, 0, 2);
            }
        }
        for (int var5 = 0; var5 < 7; ++var5) {
            final int var6 = par3 + par2Random.nextInt(16);
            final int var7 = par2Random.nextInt(64);
            final int var8 = par4 + par2Random.nextInt(16);
            this.theWorldGenerator.generate(par1World, par2Random, var6, var7, var8);
        }
    }
}
