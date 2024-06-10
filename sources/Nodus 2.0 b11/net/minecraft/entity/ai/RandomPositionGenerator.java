/*   1:    */ package net.minecraft.entity.ai;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.entity.EntityCreature;
/*   5:    */ import net.minecraft.util.ChunkCoordinates;
/*   6:    */ import net.minecraft.util.MathHelper;
/*   7:    */ import net.minecraft.util.Vec3;
/*   8:    */ import net.minecraft.util.Vec3Pool;
/*   9:    */ import net.minecraft.world.World;
/*  10:    */ 
/*  11:    */ public class RandomPositionGenerator
/*  12:    */ {
/*  13: 14 */   private static Vec3 staticVector = Vec3.createVectorHelper(0.0D, 0.0D, 0.0D);
/*  14:    */   private static final String __OBFID = "CL_00001629";
/*  15:    */   
/*  16:    */   public static Vec3 findRandomTarget(EntityCreature par0EntityCreature, int par1, int par2)
/*  17:    */   {
/*  18: 22 */     return findRandomTargetBlock(par0EntityCreature, par1, par2, null);
/*  19:    */   }
/*  20:    */   
/*  21:    */   public static Vec3 findRandomTargetBlockTowards(EntityCreature par0EntityCreature, int par1, int par2, Vec3 par3Vec3)
/*  22:    */   {
/*  23: 30 */     staticVector.xCoord = (par3Vec3.xCoord - par0EntityCreature.posX);
/*  24: 31 */     staticVector.yCoord = (par3Vec3.yCoord - par0EntityCreature.posY);
/*  25: 32 */     staticVector.zCoord = (par3Vec3.zCoord - par0EntityCreature.posZ);
/*  26: 33 */     return findRandomTargetBlock(par0EntityCreature, par1, par2, staticVector);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public static Vec3 findRandomTargetBlockAwayFrom(EntityCreature par0EntityCreature, int par1, int par2, Vec3 par3Vec3)
/*  30:    */   {
/*  31: 41 */     staticVector.xCoord = (par0EntityCreature.posX - par3Vec3.xCoord);
/*  32: 42 */     staticVector.yCoord = (par0EntityCreature.posY - par3Vec3.yCoord);
/*  33: 43 */     staticVector.zCoord = (par0EntityCreature.posZ - par3Vec3.zCoord);
/*  34: 44 */     return findRandomTargetBlock(par0EntityCreature, par1, par2, staticVector);
/*  35:    */   }
/*  36:    */   
/*  37:    */   private static Vec3 findRandomTargetBlock(EntityCreature par0EntityCreature, int par1, int par2, Vec3 par3Vec3)
/*  38:    */   {
/*  39: 53 */     Random var4 = par0EntityCreature.getRNG();
/*  40: 54 */     boolean var5 = false;
/*  41: 55 */     int var6 = 0;
/*  42: 56 */     int var7 = 0;
/*  43: 57 */     int var8 = 0;
/*  44: 58 */     float var9 = -99999.0F;
/*  45:    */     boolean var10;
/*  46:    */     boolean var10;
/*  47: 61 */     if (par0EntityCreature.hasHome())
/*  48:    */     {
/*  49: 63 */       double var11 = par0EntityCreature.getHomePosition().getDistanceSquared(MathHelper.floor_double(par0EntityCreature.posX), MathHelper.floor_double(par0EntityCreature.posY), MathHelper.floor_double(par0EntityCreature.posZ)) + 4.0F;
/*  50: 64 */       double var13 = par0EntityCreature.func_110174_bM() + par1;
/*  51: 65 */       var10 = var11 < var13 * var13;
/*  52:    */     }
/*  53:    */     else
/*  54:    */     {
/*  55: 69 */       var10 = false;
/*  56:    */     }
/*  57: 72 */     for (int var16 = 0; var16 < 10; var16++)
/*  58:    */     {
/*  59: 74 */       int var12 = var4.nextInt(2 * par1) - par1;
/*  60: 75 */       int var17 = var4.nextInt(2 * par2) - par2;
/*  61: 76 */       int var14 = var4.nextInt(2 * par1) - par1;
/*  62: 78 */       if ((par3Vec3 == null) || (var12 * par3Vec3.xCoord + var14 * par3Vec3.zCoord >= 0.0D))
/*  63:    */       {
/*  64: 80 */         var12 += MathHelper.floor_double(par0EntityCreature.posX);
/*  65: 81 */         var17 += MathHelper.floor_double(par0EntityCreature.posY);
/*  66: 82 */         var14 += MathHelper.floor_double(par0EntityCreature.posZ);
/*  67: 84 */         if ((!var10) || (par0EntityCreature.isWithinHomeDistance(var12, var17, var14)))
/*  68:    */         {
/*  69: 86 */           float var15 = par0EntityCreature.getBlockPathWeight(var12, var17, var14);
/*  70: 88 */           if (var15 > var9)
/*  71:    */           {
/*  72: 90 */             var9 = var15;
/*  73: 91 */             var6 = var12;
/*  74: 92 */             var7 = var17;
/*  75: 93 */             var8 = var14;
/*  76: 94 */             var5 = true;
/*  77:    */           }
/*  78:    */         }
/*  79:    */       }
/*  80:    */     }
/*  81:100 */     if (var5) {
/*  82:102 */       return par0EntityCreature.worldObj.getWorldVec3Pool().getVecFromPool(var6, var7, var8);
/*  83:    */     }
/*  84:106 */     return null;
/*  85:    */   }
/*  86:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.RandomPositionGenerator
 * JD-Core Version:    0.7.0.1
 */