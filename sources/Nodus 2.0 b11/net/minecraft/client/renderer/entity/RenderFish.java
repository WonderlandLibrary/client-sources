/*   1:    */ package net.minecraft.client.renderer.entity;
/*   2:    */ 
/*   3:    */ import net.minecraft.client.Minecraft;
/*   4:    */ import net.minecraft.client.renderer.Tessellator;
/*   5:    */ import net.minecraft.client.settings.GameSettings;
/*   6:    */ import net.minecraft.entity.Entity;
/*   7:    */ import net.minecraft.entity.player.EntityPlayer;
/*   8:    */ import net.minecraft.entity.projectile.EntityFishHook;
/*   9:    */ import net.minecraft.util.MathHelper;
/*  10:    */ import net.minecraft.util.ResourceLocation;
/*  11:    */ import net.minecraft.util.Vec3;
/*  12:    */ import net.minecraft.util.Vec3Pool;
/*  13:    */ import net.minecraft.world.World;
/*  14:    */ import org.lwjgl.opengl.GL11;
/*  15:    */ 
/*  16:    */ public class RenderFish
/*  17:    */   extends Render
/*  18:    */ {
/*  19: 15 */   private static final ResourceLocation field_110792_a = new ResourceLocation("textures/particle/particles.png");
/*  20:    */   private static final String __OBFID = "CL_00000996";
/*  21:    */   
/*  22:    */   public void doRender(EntityFishHook p_147922_1_, double p_147922_2_, double p_147922_4_, double p_147922_6_, float p_147922_8_, float p_147922_9_)
/*  23:    */   {
/*  24: 26 */     GL11.glPushMatrix();
/*  25: 27 */     GL11.glTranslatef((float)p_147922_2_, (float)p_147922_4_, (float)p_147922_6_);
/*  26: 28 */     GL11.glEnable(32826);
/*  27: 29 */     GL11.glScalef(0.5F, 0.5F, 0.5F);
/*  28: 30 */     bindEntityTexture(p_147922_1_);
/*  29: 31 */     Tessellator var10 = Tessellator.instance;
/*  30: 32 */     byte var11 = 1;
/*  31: 33 */     byte var12 = 2;
/*  32: 34 */     float var13 = (var11 * 8 + 0) / 128.0F;
/*  33: 35 */     float var14 = (var11 * 8 + 8) / 128.0F;
/*  34: 36 */     float var15 = (var12 * 8 + 0) / 128.0F;
/*  35: 37 */     float var16 = (var12 * 8 + 8) / 128.0F;
/*  36: 38 */     float var17 = 1.0F;
/*  37: 39 */     float var18 = 0.5F;
/*  38: 40 */     float var19 = 0.5F;
/*  39: 41 */     GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
/*  40: 42 */     GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
/*  41: 43 */     var10.startDrawingQuads();
/*  42: 44 */     var10.setNormal(0.0F, 1.0F, 0.0F);
/*  43: 45 */     var10.addVertexWithUV(0.0F - var18, 0.0F - var19, 0.0D, var13, var16);
/*  44: 46 */     var10.addVertexWithUV(var17 - var18, 0.0F - var19, 0.0D, var14, var16);
/*  45: 47 */     var10.addVertexWithUV(var17 - var18, 1.0F - var19, 0.0D, var14, var15);
/*  46: 48 */     var10.addVertexWithUV(0.0F - var18, 1.0F - var19, 0.0D, var13, var15);
/*  47: 49 */     var10.draw();
/*  48: 50 */     GL11.glDisable(32826);
/*  49: 51 */     GL11.glPopMatrix();
/*  50: 53 */     if (p_147922_1_.field_146042_b != null)
/*  51:    */     {
/*  52: 55 */       float var20 = p_147922_1_.field_146042_b.getSwingProgress(p_147922_9_);
/*  53: 56 */       float var21 = MathHelper.sin(MathHelper.sqrt_float(var20) * 3.141593F);
/*  54: 57 */       Vec3 var22 = p_147922_1_.worldObj.getWorldVec3Pool().getVecFromPool(-0.5D, 0.03D, 0.8D);
/*  55: 58 */       var22.rotateAroundX(-(p_147922_1_.field_146042_b.prevRotationPitch + (p_147922_1_.field_146042_b.rotationPitch - p_147922_1_.field_146042_b.prevRotationPitch) * p_147922_9_) * 3.141593F / 180.0F);
/*  56: 59 */       var22.rotateAroundY(-(p_147922_1_.field_146042_b.prevRotationYaw + (p_147922_1_.field_146042_b.rotationYaw - p_147922_1_.field_146042_b.prevRotationYaw) * p_147922_9_) * 3.141593F / 180.0F);
/*  57: 60 */       var22.rotateAroundY(var21 * 0.5F);
/*  58: 61 */       var22.rotateAroundX(-var21 * 0.7F);
/*  59: 62 */       double var23 = p_147922_1_.field_146042_b.prevPosX + (p_147922_1_.field_146042_b.posX - p_147922_1_.field_146042_b.prevPosX) * p_147922_9_ + var22.xCoord;
/*  60: 63 */       double var25 = p_147922_1_.field_146042_b.prevPosY + (p_147922_1_.field_146042_b.posY - p_147922_1_.field_146042_b.prevPosY) * p_147922_9_ + var22.yCoord;
/*  61: 64 */       double var27 = p_147922_1_.field_146042_b.prevPosZ + (p_147922_1_.field_146042_b.posZ - p_147922_1_.field_146042_b.prevPosZ) * p_147922_9_ + var22.zCoord;
/*  62: 65 */       double var29 = p_147922_1_.field_146042_b == Minecraft.getMinecraft().thePlayer ? 0.0D : p_147922_1_.field_146042_b.getEyeHeight();
/*  63: 67 */       if ((this.renderManager.options.thirdPersonView > 0) || (p_147922_1_.field_146042_b != Minecraft.getMinecraft().thePlayer))
/*  64:    */       {
/*  65: 69 */         float var31 = (p_147922_1_.field_146042_b.prevRenderYawOffset + (p_147922_1_.field_146042_b.renderYawOffset - p_147922_1_.field_146042_b.prevRenderYawOffset) * p_147922_9_) * 3.141593F / 180.0F;
/*  66: 70 */         double var32 = MathHelper.sin(var31);
/*  67: 71 */         double var34 = MathHelper.cos(var31);
/*  68: 72 */         var23 = p_147922_1_.field_146042_b.prevPosX + (p_147922_1_.field_146042_b.posX - p_147922_1_.field_146042_b.prevPosX) * p_147922_9_ - var34 * 0.35D - var32 * 0.85D;
/*  69: 73 */         var25 = p_147922_1_.field_146042_b.prevPosY + var29 + (p_147922_1_.field_146042_b.posY - p_147922_1_.field_146042_b.prevPosY) * p_147922_9_ - 0.45D;
/*  70: 74 */         var27 = p_147922_1_.field_146042_b.prevPosZ + (p_147922_1_.field_146042_b.posZ - p_147922_1_.field_146042_b.prevPosZ) * p_147922_9_ - var32 * 0.35D + var34 * 0.85D;
/*  71:    */       }
/*  72: 77 */       double var46 = p_147922_1_.prevPosX + (p_147922_1_.posX - p_147922_1_.prevPosX) * p_147922_9_;
/*  73: 78 */       double var33 = p_147922_1_.prevPosY + (p_147922_1_.posY - p_147922_1_.prevPosY) * p_147922_9_ + 0.25D;
/*  74: 79 */       double var35 = p_147922_1_.prevPosZ + (p_147922_1_.posZ - p_147922_1_.prevPosZ) * p_147922_9_;
/*  75: 80 */       double var37 = (float)(var23 - var46);
/*  76: 81 */       double var39 = (float)(var25 - var33);
/*  77: 82 */       double var41 = (float)(var27 - var35);
/*  78: 83 */       GL11.glDisable(3553);
/*  79: 84 */       GL11.glDisable(2896);
/*  80: 85 */       var10.startDrawing(3);
/*  81: 86 */       var10.setColorOpaque_I(0);
/*  82: 87 */       byte var43 = 16;
/*  83: 89 */       for (int var44 = 0; var44 <= var43; var44++)
/*  84:    */       {
/*  85: 91 */         float var45 = var44 / var43;
/*  86: 92 */         var10.addVertex(p_147922_2_ + var37 * var45, p_147922_4_ + var39 * (var45 * var45 + var45) * 0.5D + 0.25D, p_147922_6_ + var41 * var45);
/*  87:    */       }
/*  88: 95 */       var10.draw();
/*  89: 96 */       GL11.glEnable(2896);
/*  90: 97 */       GL11.glEnable(3553);
/*  91:    */     }
/*  92:    */   }
/*  93:    */   
/*  94:    */   protected ResourceLocation getEntityTexture(EntityFishHook p_147921_1_)
/*  95:    */   {
/*  96:106 */     return field_110792_a;
/*  97:    */   }
/*  98:    */   
/*  99:    */   protected ResourceLocation getEntityTexture(Entity par1Entity)
/* 100:    */   {
/* 101:114 */     return getEntityTexture((EntityFishHook)par1Entity);
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
/* 105:    */   {
/* 106:125 */     doRender((EntityFishHook)par1Entity, par2, par4, par6, par8, par9);
/* 107:    */   }
/* 108:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.entity.RenderFish
 * JD-Core Version:    0.7.0.1
 */