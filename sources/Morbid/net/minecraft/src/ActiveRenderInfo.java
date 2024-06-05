package net.minecraft.src;

import java.nio.*;
import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.*;

public class ActiveRenderInfo
{
    public static float objectX;
    public static float objectY;
    public static float objectZ;
    private static IntBuffer viewport;
    private static FloatBuffer modelview;
    private static FloatBuffer projection;
    private static FloatBuffer objectCoords;
    public static float rotationX;
    public static float rotationXZ;
    public static float rotationZ;
    public static float rotationYZ;
    public static float rotationXY;
    
    static {
        ActiveRenderInfo.objectX = 0.0f;
        ActiveRenderInfo.objectY = 0.0f;
        ActiveRenderInfo.objectZ = 0.0f;
        ActiveRenderInfo.viewport = GLAllocation.createDirectIntBuffer(16);
        ActiveRenderInfo.modelview = GLAllocation.createDirectFloatBuffer(16);
        ActiveRenderInfo.projection = GLAllocation.createDirectFloatBuffer(16);
        ActiveRenderInfo.objectCoords = GLAllocation.createDirectFloatBuffer(3);
    }
    
    public static void updateRenderInfo(final EntityPlayer par0EntityPlayer, final boolean par1) {
        GL11.glGetFloat(2982, ActiveRenderInfo.modelview);
        GL11.glGetFloat(2983, ActiveRenderInfo.projection);
        GL11.glGetInteger(2978, ActiveRenderInfo.viewport);
        final float var2 = (ActiveRenderInfo.viewport.get(0) + ActiveRenderInfo.viewport.get(2)) / 2;
        final float var3 = (ActiveRenderInfo.viewport.get(1) + ActiveRenderInfo.viewport.get(3)) / 2;
        GLU.gluUnProject(var2, var3, 0.0f, ActiveRenderInfo.modelview, ActiveRenderInfo.projection, ActiveRenderInfo.viewport, ActiveRenderInfo.objectCoords);
        ActiveRenderInfo.objectX = ActiveRenderInfo.objectCoords.get(0);
        ActiveRenderInfo.objectY = ActiveRenderInfo.objectCoords.get(1);
        ActiveRenderInfo.objectZ = ActiveRenderInfo.objectCoords.get(2);
        final int var4 = par1 ? 1 : 0;
        final float var5 = par0EntityPlayer.rotationPitch;
        final float var6 = par0EntityPlayer.rotationYaw;
        ActiveRenderInfo.rotationX = MathHelper.cos(var6 * 3.1415927f / 180.0f) * (1 - var4 * 2);
        ActiveRenderInfo.rotationZ = MathHelper.sin(var6 * 3.1415927f / 180.0f) * (1 - var4 * 2);
        ActiveRenderInfo.rotationYZ = -ActiveRenderInfo.rotationZ * MathHelper.sin(var5 * 3.1415927f / 180.0f) * (1 - var4 * 2);
        ActiveRenderInfo.rotationXY = ActiveRenderInfo.rotationX * MathHelper.sin(var5 * 3.1415927f / 180.0f) * (1 - var4 * 2);
        ActiveRenderInfo.rotationXZ = MathHelper.cos(var5 * 3.1415927f / 180.0f);
    }
    
    public static Vec3 projectViewFromEntity(final EntityLiving par0EntityLiving, final double par1) {
        final double var3 = par0EntityLiving.prevPosX + (par0EntityLiving.posX - par0EntityLiving.prevPosX) * par1;
        final double var4 = par0EntityLiving.prevPosY + (par0EntityLiving.posY - par0EntityLiving.prevPosY) * par1 + par0EntityLiving.getEyeHeight();
        final double var5 = par0EntityLiving.prevPosZ + (par0EntityLiving.posZ - par0EntityLiving.prevPosZ) * par1;
        final double var6 = var3 + ActiveRenderInfo.objectX * 1.0f;
        final double var7 = var4 + ActiveRenderInfo.objectY * 1.0f;
        final double var8 = var5 + ActiveRenderInfo.objectZ * 1.0f;
        return par0EntityLiving.worldObj.getWorldVec3Pool().getVecFromPool(var6, var7, var8);
    }
    
    public static int getBlockIdAtEntityViewpoint(final World par0World, final EntityLiving par1EntityLiving, final float par2) {
        final Vec3 var3 = projectViewFromEntity(par1EntityLiving, par2);
        final ChunkPosition var4 = new ChunkPosition(var3);
        int var5 = par0World.getBlockId(var4.x, var4.y, var4.z);
        if (var5 != 0 && Block.blocksList[var5].blockMaterial.isLiquid()) {
            final float var6 = BlockFluid.getFluidHeightPercent(par0World.getBlockMetadata(var4.x, var4.y, var4.z)) - 0.11111111f;
            final float var7 = var4.y + 1 - var6;
            if (var3.yCoord >= var7) {
                var5 = par0World.getBlockId(var4.x, var4.y + 1, var4.z);
            }
        }
        return var5;
    }
}
