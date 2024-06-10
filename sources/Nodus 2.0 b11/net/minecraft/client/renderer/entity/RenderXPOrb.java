/*  1:   */ package net.minecraft.client.renderer.entity;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.renderer.OpenGlHelper;
/*  4:   */ import net.minecraft.client.renderer.Tessellator;
/*  5:   */ import net.minecraft.entity.Entity;
/*  6:   */ import net.minecraft.entity.item.EntityXPOrb;
/*  7:   */ import net.minecraft.util.MathHelper;
/*  8:   */ import net.minecraft.util.ResourceLocation;
/*  9:   */ import org.lwjgl.opengl.GL11;
/* 10:   */ 
/* 11:   */ public class RenderXPOrb
/* 12:   */   extends Render
/* 13:   */ {
/* 14:14 */   private static final ResourceLocation experienceOrbTextures = new ResourceLocation("textures/entity/experience_orb.png");
/* 15:   */   private static final String __OBFID = "CL_00000993";
/* 16:   */   
/* 17:   */   public RenderXPOrb()
/* 18:   */   {
/* 19:19 */     this.shadowSize = 0.15F;
/* 20:20 */     this.shadowOpaque = 0.75F;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void doRender(EntityXPOrb par1EntityXPOrb, double par2, double par4, double par6, float par8, float par9)
/* 24:   */   {
/* 25:31 */     GL11.glPushMatrix();
/* 26:32 */     GL11.glTranslatef((float)par2, (float)par4, (float)par6);
/* 27:33 */     bindEntityTexture(par1EntityXPOrb);
/* 28:34 */     int var10 = par1EntityXPOrb.getTextureByXP();
/* 29:35 */     float var11 = (var10 % 4 * 16 + 0) / 64.0F;
/* 30:36 */     float var12 = (var10 % 4 * 16 + 16) / 64.0F;
/* 31:37 */     float var13 = (var10 / 4 * 16 + 0) / 64.0F;
/* 32:38 */     float var14 = (var10 / 4 * 16 + 16) / 64.0F;
/* 33:39 */     float var15 = 1.0F;
/* 34:40 */     float var16 = 0.5F;
/* 35:41 */     float var17 = 0.25F;
/* 36:42 */     int var18 = par1EntityXPOrb.getBrightnessForRender(par9);
/* 37:43 */     int var19 = var18 % 65536;
/* 38:44 */     int var20 = var18 / 65536;
/* 39:45 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var19 / 1.0F, var20 / 1.0F);
/* 40:46 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 41:47 */     float var27 = 255.0F;
/* 42:48 */     float var26 = (par1EntityXPOrb.xpColor + par9) / 2.0F;
/* 43:49 */     var20 = (int)((MathHelper.sin(var26 + 0.0F) + 1.0F) * 0.5F * var27);
/* 44:50 */     int var21 = (int)var27;
/* 45:51 */     int var22 = (int)((MathHelper.sin(var26 + 4.18879F) + 1.0F) * 0.1F * var27);
/* 46:52 */     int var23 = var20 << 16 | var21 << 8 | var22;
/* 47:53 */     GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
/* 48:54 */     GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
/* 49:55 */     float var24 = 0.3F;
/* 50:56 */     GL11.glScalef(var24, var24, var24);
/* 51:57 */     Tessellator var25 = Tessellator.instance;
/* 52:58 */     var25.startDrawingQuads();
/* 53:59 */     var25.setColorRGBA_I(var23, 128);
/* 54:60 */     var25.setNormal(0.0F, 1.0F, 0.0F);
/* 55:61 */     var25.addVertexWithUV(0.0F - var16, 0.0F - var17, 0.0D, var11, var14);
/* 56:62 */     var25.addVertexWithUV(var15 - var16, 0.0F - var17, 0.0D, var12, var14);
/* 57:63 */     var25.addVertexWithUV(var15 - var16, 1.0F - var17, 0.0D, var12, var13);
/* 58:64 */     var25.addVertexWithUV(0.0F - var16, 1.0F - var17, 0.0D, var11, var13);
/* 59:65 */     var25.draw();
/* 60:66 */     GL11.glDisable(3042);
/* 61:67 */     GL11.glDisable(32826);
/* 62:68 */     GL11.glPopMatrix();
/* 63:   */   }
/* 64:   */   
/* 65:   */   protected ResourceLocation getEntityTexture(EntityXPOrb par1EntityXPOrb)
/* 66:   */   {
/* 67:76 */     return experienceOrbTextures;
/* 68:   */   }
/* 69:   */   
/* 70:   */   protected ResourceLocation getEntityTexture(Entity par1Entity)
/* 71:   */   {
/* 72:84 */     return getEntityTexture((EntityXPOrb)par1Entity);
/* 73:   */   }
/* 74:   */   
/* 75:   */   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
/* 76:   */   {
/* 77:95 */     doRender((EntityXPOrb)par1Entity, par2, par4, par6, par8, par9);
/* 78:   */   }
/* 79:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.entity.RenderXPOrb
 * JD-Core Version:    0.7.0.1
 */