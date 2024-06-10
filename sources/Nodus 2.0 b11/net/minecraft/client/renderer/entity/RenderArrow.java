/*   1:    */ package net.minecraft.client.renderer.entity;
/*   2:    */ 
/*   3:    */ import net.minecraft.client.renderer.Tessellator;
/*   4:    */ import net.minecraft.entity.Entity;
/*   5:    */ import net.minecraft.entity.projectile.EntityArrow;
/*   6:    */ import net.minecraft.util.MathHelper;
/*   7:    */ import net.minecraft.util.ResourceLocation;
/*   8:    */ import org.lwjgl.opengl.GL11;
/*   9:    */ 
/*  10:    */ public class RenderArrow
/*  11:    */   extends Render
/*  12:    */ {
/*  13: 13 */   private static final ResourceLocation arrowTextures = new ResourceLocation("textures/entity/arrow.png");
/*  14:    */   private static final String __OBFID = "CL_00000978";
/*  15:    */   
/*  16:    */   public void doRender(EntityArrow par1EntityArrow, double par2, double par4, double par6, float par8, float par9)
/*  17:    */   {
/*  18: 24 */     bindEntityTexture(par1EntityArrow);
/*  19: 25 */     GL11.glPushMatrix();
/*  20: 26 */     GL11.glTranslatef((float)par2, (float)par4, (float)par6);
/*  21: 27 */     GL11.glRotatef(par1EntityArrow.prevRotationYaw + (par1EntityArrow.rotationYaw - par1EntityArrow.prevRotationYaw) * par9 - 90.0F, 0.0F, 1.0F, 0.0F);
/*  22: 28 */     GL11.glRotatef(par1EntityArrow.prevRotationPitch + (par1EntityArrow.rotationPitch - par1EntityArrow.prevRotationPitch) * par9, 0.0F, 0.0F, 1.0F);
/*  23: 29 */     Tessellator var10 = Tessellator.instance;
/*  24: 30 */     byte var11 = 0;
/*  25: 31 */     float var12 = 0.0F;
/*  26: 32 */     float var13 = 0.5F;
/*  27: 33 */     float var14 = (0 + var11 * 10) / 32.0F;
/*  28: 34 */     float var15 = (5 + var11 * 10) / 32.0F;
/*  29: 35 */     float var16 = 0.0F;
/*  30: 36 */     float var17 = 0.15625F;
/*  31: 37 */     float var18 = (5 + var11 * 10) / 32.0F;
/*  32: 38 */     float var19 = (10 + var11 * 10) / 32.0F;
/*  33: 39 */     float var20 = 0.05625F;
/*  34: 40 */     GL11.glEnable(32826);
/*  35: 41 */     float var21 = par1EntityArrow.arrowShake - par9;
/*  36: 43 */     if (var21 > 0.0F)
/*  37:    */     {
/*  38: 45 */       float var22 = -MathHelper.sin(var21 * 3.0F) * var21;
/*  39: 46 */       GL11.glRotatef(var22, 0.0F, 0.0F, 1.0F);
/*  40:    */     }
/*  41: 49 */     GL11.glRotatef(45.0F, 1.0F, 0.0F, 0.0F);
/*  42: 50 */     GL11.glScalef(var20, var20, var20);
/*  43: 51 */     GL11.glTranslatef(-4.0F, 0.0F, 0.0F);
/*  44: 52 */     GL11.glNormal3f(var20, 0.0F, 0.0F);
/*  45: 53 */     var10.startDrawingQuads();
/*  46: 54 */     var10.addVertexWithUV(-7.0D, -2.0D, -2.0D, var16, var18);
/*  47: 55 */     var10.addVertexWithUV(-7.0D, -2.0D, 2.0D, var17, var18);
/*  48: 56 */     var10.addVertexWithUV(-7.0D, 2.0D, 2.0D, var17, var19);
/*  49: 57 */     var10.addVertexWithUV(-7.0D, 2.0D, -2.0D, var16, var19);
/*  50: 58 */     var10.draw();
/*  51: 59 */     GL11.glNormal3f(-var20, 0.0F, 0.0F);
/*  52: 60 */     var10.startDrawingQuads();
/*  53: 61 */     var10.addVertexWithUV(-7.0D, 2.0D, -2.0D, var16, var18);
/*  54: 62 */     var10.addVertexWithUV(-7.0D, 2.0D, 2.0D, var17, var18);
/*  55: 63 */     var10.addVertexWithUV(-7.0D, -2.0D, 2.0D, var17, var19);
/*  56: 64 */     var10.addVertexWithUV(-7.0D, -2.0D, -2.0D, var16, var19);
/*  57: 65 */     var10.draw();
/*  58: 67 */     for (int var23 = 0; var23 < 4; var23++)
/*  59:    */     {
/*  60: 69 */       GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/*  61: 70 */       GL11.glNormal3f(0.0F, 0.0F, var20);
/*  62: 71 */       var10.startDrawingQuads();
/*  63: 72 */       var10.addVertexWithUV(-8.0D, -2.0D, 0.0D, var12, var14);
/*  64: 73 */       var10.addVertexWithUV(8.0D, -2.0D, 0.0D, var13, var14);
/*  65: 74 */       var10.addVertexWithUV(8.0D, 2.0D, 0.0D, var13, var15);
/*  66: 75 */       var10.addVertexWithUV(-8.0D, 2.0D, 0.0D, var12, var15);
/*  67: 76 */       var10.draw();
/*  68:    */     }
/*  69: 79 */     GL11.glDisable(32826);
/*  70: 80 */     GL11.glPopMatrix();
/*  71:    */   }
/*  72:    */   
/*  73:    */   protected ResourceLocation getEntityTexture(EntityArrow par1EntityArrow)
/*  74:    */   {
/*  75: 88 */     return arrowTextures;
/*  76:    */   }
/*  77:    */   
/*  78:    */   protected ResourceLocation getEntityTexture(Entity par1Entity)
/*  79:    */   {
/*  80: 96 */     return getEntityTexture((EntityArrow)par1Entity);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
/*  84:    */   {
/*  85:107 */     doRender((EntityArrow)par1Entity, par2, par4, par6, par8, par9);
/*  86:    */   }
/*  87:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.entity.RenderArrow
 * JD-Core Version:    0.7.0.1
 */