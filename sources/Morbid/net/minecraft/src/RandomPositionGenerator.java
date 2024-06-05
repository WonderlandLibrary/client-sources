package net.minecraft.src;

import java.util.*;

public class RandomPositionGenerator
{
    private static Vec3 staticVector;
    
    static {
        RandomPositionGenerator.staticVector = Vec3.createVectorHelper(0.0, 0.0, 0.0);
    }
    
    public static Vec3 findRandomTarget(final EntityCreature par0EntityCreature, final int par1, final int par2) {
        return findRandomTargetBlock(par0EntityCreature, par1, par2, null);
    }
    
    public static Vec3 findRandomTargetBlockTowards(final EntityCreature par0EntityCreature, final int par1, final int par2, final Vec3 par3Vec3) {
        RandomPositionGenerator.staticVector.xCoord = par3Vec3.xCoord - par0EntityCreature.posX;
        RandomPositionGenerator.staticVector.yCoord = par3Vec3.yCoord - par0EntityCreature.posY;
        RandomPositionGenerator.staticVector.zCoord = par3Vec3.zCoord - par0EntityCreature.posZ;
        return findRandomTargetBlock(par0EntityCreature, par1, par2, RandomPositionGenerator.staticVector);
    }
    
    public static Vec3 findRandomTargetBlockAwayFrom(final EntityCreature par0EntityCreature, final int par1, final int par2, final Vec3 par3Vec3) {
        RandomPositionGenerator.staticVector.xCoord = par0EntityCreature.posX - par3Vec3.xCoord;
        RandomPositionGenerator.staticVector.yCoord = par0EntityCreature.posY - par3Vec3.yCoord;
        RandomPositionGenerator.staticVector.zCoord = par0EntityCreature.posZ - par3Vec3.zCoord;
        return findRandomTargetBlock(par0EntityCreature, par1, par2, RandomPositionGenerator.staticVector);
    }
    
    private static Vec3 findRandomTargetBlock(final EntityCreature par0EntityCreature, final int par1, final int par2, final Vec3 par3Vec3) {
        final Random var4 = par0EntityCreature.getRNG();
        boolean var5 = false;
        int var6 = 0;
        int var7 = 0;
        int var8 = 0;
        float var9 = -99999.0f;
        boolean var12;
        if (par0EntityCreature.hasHome()) {
            final double var10 = par0EntityCreature.getHomePosition().getDistanceSquared(MathHelper.floor_double(par0EntityCreature.posX), MathHelper.floor_double(par0EntityCreature.posY), MathHelper.floor_double(par0EntityCreature.posZ)) + 4.0f;
            final double var11 = par0EntityCreature.getMaximumHomeDistance() + par1;
            var12 = (var10 < var11 * var11);
        }
        else {
            var12 = false;
        }
        for (int var13 = 0; var13 < 10; ++var13) {
            int var14 = var4.nextInt(2 * par1) - par1;
            int var15 = var4.nextInt(2 * par2) - par2;
            int var16 = var4.nextInt(2 * par1) - par1;
            if (par3Vec3 == null || var14 * par3Vec3.xCoord + var16 * par3Vec3.zCoord >= 0.0) {
                var14 += MathHelper.floor_double(par0EntityCreature.posX);
                var15 += MathHelper.floor_double(par0EntityCreature.posY);
                var16 += MathHelper.floor_double(par0EntityCreature.posZ);
                if (!var12 || par0EntityCreature.isWithinHomeDistance(var14, var15, var16)) {
                    final float var17 = par0EntityCreature.getBlockPathWeight(var14, var15, var16);
                    if (var17 > var9) {
                        var9 = var17;
                        var6 = var14;
                        var7 = var15;
                        var8 = var16;
                        var5 = true;
                    }
                }
            }
        }
        if (var5) {
            return par0EntityCreature.worldObj.getWorldVec3Pool().getVecFromPool(var6, var7, var8);
        }
        return null;
    }
}
