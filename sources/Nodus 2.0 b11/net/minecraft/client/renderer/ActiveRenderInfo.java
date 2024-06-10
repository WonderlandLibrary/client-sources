/*   1:    */ package net.minecraft.client.renderer;
/*   2:    */ 
/*   3:    */ import java.nio.FloatBuffer;
/*   4:    */ import java.nio.IntBuffer;
/*   5:    */ import net.minecraft.block.Block;
/*   6:    */ import net.minecraft.block.BlockLiquid;
/*   7:    */ import net.minecraft.block.material.Material;
/*   8:    */ import net.minecraft.entity.EntityLivingBase;
/*   9:    */ import net.minecraft.entity.player.EntityPlayer;
/*  10:    */ import net.minecraft.util.MathHelper;
/*  11:    */ import net.minecraft.util.Vec3;
/*  12:    */ import net.minecraft.util.Vec3Pool;
/*  13:    */ import net.minecraft.world.ChunkPosition;
/*  14:    */ import net.minecraft.world.World;
/*  15:    */ import org.lwjgl.opengl.GL11;
/*  16:    */ import org.lwjgl.util.glu.GLU;
/*  17:    */ 
/*  18:    */ public class ActiveRenderInfo
/*  19:    */ {
/*  20:    */   public static float objectX;
/*  21:    */   public static float objectY;
/*  22:    */   public static float objectZ;
/*  23: 28 */   private static IntBuffer viewport = GLAllocation.createDirectIntBuffer(16);
/*  24: 31 */   private static FloatBuffer modelview = GLAllocation.createDirectFloatBuffer(16);
/*  25: 34 */   private static FloatBuffer projection = GLAllocation.createDirectFloatBuffer(16);
/*  26: 37 */   private static FloatBuffer objectCoords = GLAllocation.createDirectFloatBuffer(3);
/*  27:    */   public static float rotationX;
/*  28:    */   public static float rotationXZ;
/*  29:    */   public static float rotationZ;
/*  30:    */   public static float rotationYZ;
/*  31:    */   public static float rotationXY;
/*  32:    */   private static final String __OBFID = "CL_00000626";
/*  33:    */   
/*  34:    */   public static void updateRenderInfo(EntityPlayer par0EntityPlayer, boolean par1)
/*  35:    */   {
/*  36: 64 */     GL11.glGetFloat(2982, modelview);
/*  37: 65 */     GL11.glGetFloat(2983, projection);
/*  38: 66 */     GL11.glGetInteger(2978, viewport);
/*  39: 67 */     float var2 = (viewport.get(0) + viewport.get(2)) / 2;
/*  40: 68 */     float var3 = (viewport.get(1) + viewport.get(3)) / 2;
/*  41: 69 */     GLU.gluUnProject(var2, var3, 0.0F, modelview, projection, viewport, objectCoords);
/*  42: 70 */     objectX = objectCoords.get(0);
/*  43: 71 */     objectY = objectCoords.get(1);
/*  44: 72 */     objectZ = objectCoords.get(2);
/*  45: 73 */     int var4 = par1 ? 1 : 0;
/*  46: 74 */     float var5 = par0EntityPlayer.rotationPitch;
/*  47: 75 */     float var6 = par0EntityPlayer.rotationYaw;
/*  48: 76 */     rotationX = MathHelper.cos(var6 * 3.141593F / 180.0F) * (1 - var4 * 2);
/*  49: 77 */     rotationZ = MathHelper.sin(var6 * 3.141593F / 180.0F) * (1 - var4 * 2);
/*  50: 78 */     rotationYZ = -rotationZ * MathHelper.sin(var5 * 3.141593F / 180.0F) * (1 - var4 * 2);
/*  51: 79 */     rotationXY = rotationX * MathHelper.sin(var5 * 3.141593F / 180.0F) * (1 - var4 * 2);
/*  52: 80 */     rotationXZ = MathHelper.cos(var5 * 3.141593F / 180.0F);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public static Vec3 projectViewFromEntity(EntityLivingBase par0EntityLivingBase, double par1)
/*  56:    */   {
/*  57: 88 */     double var3 = par0EntityLivingBase.prevPosX + (par0EntityLivingBase.posX - par0EntityLivingBase.prevPosX) * par1;
/*  58: 89 */     double var5 = par0EntityLivingBase.prevPosY + (par0EntityLivingBase.posY - par0EntityLivingBase.prevPosY) * par1 + par0EntityLivingBase.getEyeHeight();
/*  59: 90 */     double var7 = par0EntityLivingBase.prevPosZ + (par0EntityLivingBase.posZ - par0EntityLivingBase.prevPosZ) * par1;
/*  60: 91 */     double var9 = var3 + objectX * 1.0F;
/*  61: 92 */     double var11 = var5 + objectY * 1.0F;
/*  62: 93 */     double var13 = var7 + objectZ * 1.0F;
/*  63: 94 */     return par0EntityLivingBase.worldObj.getWorldVec3Pool().getVecFromPool(var9, var11, var13);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public static Block getBlockAtEntityViewpoint(World p_151460_0_, EntityLivingBase p_151460_1_, float p_151460_2_)
/*  67:    */   {
/*  68: 99 */     Vec3 var3 = projectViewFromEntity(p_151460_1_, p_151460_2_);
/*  69:100 */     ChunkPosition var4 = new ChunkPosition(var3);
/*  70:101 */     Block var5 = p_151460_0_.getBlock(var4.field_151329_a, var4.field_151327_b, var4.field_151328_c);
/*  71:103 */     if (var5.getMaterial().isLiquid())
/*  72:    */     {
/*  73:105 */       float var6 = BlockLiquid.func_149801_b(p_151460_0_.getBlockMetadata(var4.field_151329_a, var4.field_151327_b, var4.field_151328_c)) - 0.1111111F;
/*  74:106 */       float var7 = var4.field_151327_b + 1 - var6;
/*  75:108 */       if (var3.yCoord >= var7) {
/*  76:110 */         var5 = p_151460_0_.getBlock(var4.field_151329_a, var4.field_151327_b + 1, var4.field_151328_c);
/*  77:    */       }
/*  78:    */     }
/*  79:114 */     return var5;
/*  80:    */   }
/*  81:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.ActiveRenderInfo
 * JD-Core Version:    0.7.0.1
 */