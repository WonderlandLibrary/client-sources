/*  1:   */ package net.minecraft.client.renderer.entity;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.model.ModelMagmaCube;
/*  4:   */ import net.minecraft.entity.Entity;
/*  5:   */ import net.minecraft.entity.EntityLivingBase;
/*  6:   */ import net.minecraft.entity.monster.EntityMagmaCube;
/*  7:   */ import net.minecraft.util.ResourceLocation;
/*  8:   */ import org.lwjgl.opengl.GL11;
/*  9:   */ 
/* 10:   */ public class RenderMagmaCube
/* 11:   */   extends RenderLiving
/* 12:   */ {
/* 13:12 */   private static final ResourceLocation magmaCubeTextures = new ResourceLocation("textures/entity/slime/magmacube.png");
/* 14:   */   private static final String __OBFID = "CL_00001009";
/* 15:   */   
/* 16:   */   public RenderMagmaCube()
/* 17:   */   {
/* 18:17 */     super(new ModelMagmaCube(), 0.25F);
/* 19:   */   }
/* 20:   */   
/* 21:   */   protected ResourceLocation getEntityTexture(EntityMagmaCube par1EntityMagmaCube)
/* 22:   */   {
/* 23:25 */     return magmaCubeTextures;
/* 24:   */   }
/* 25:   */   
/* 26:   */   protected void preRenderCallback(EntityMagmaCube par1EntityMagmaCube, float par2)
/* 27:   */   {
/* 28:34 */     int var3 = par1EntityMagmaCube.getSlimeSize();
/* 29:35 */     float var4 = (par1EntityMagmaCube.prevSquishFactor + (par1EntityMagmaCube.squishFactor - par1EntityMagmaCube.prevSquishFactor) * par2) / (var3 * 0.5F + 1.0F);
/* 30:36 */     float var5 = 1.0F / (var4 + 1.0F);
/* 31:37 */     float var6 = var3;
/* 32:38 */     GL11.glScalef(var5 * var6, 1.0F / var5 * var6, var5 * var6);
/* 33:   */   }
/* 34:   */   
/* 35:   */   protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
/* 36:   */   {
/* 37:47 */     preRenderCallback((EntityMagmaCube)par1EntityLivingBase, par2);
/* 38:   */   }
/* 39:   */   
/* 40:   */   protected ResourceLocation getEntityTexture(Entity par1Entity)
/* 41:   */   {
/* 42:55 */     return getEntityTexture((EntityMagmaCube)par1Entity);
/* 43:   */   }
/* 44:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.entity.RenderMagmaCube
 * JD-Core Version:    0.7.0.1
 */