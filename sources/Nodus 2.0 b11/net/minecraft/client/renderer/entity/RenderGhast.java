/*  1:   */ package net.minecraft.client.renderer.entity;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.model.ModelGhast;
/*  4:   */ import net.minecraft.entity.Entity;
/*  5:   */ import net.minecraft.entity.EntityLivingBase;
/*  6:   */ import net.minecraft.entity.monster.EntityGhast;
/*  7:   */ import net.minecraft.util.ResourceLocation;
/*  8:   */ import org.lwjgl.opengl.GL11;
/*  9:   */ 
/* 10:   */ public class RenderGhast
/* 11:   */   extends RenderLiving
/* 12:   */ {
/* 13:12 */   private static final ResourceLocation ghastTextures = new ResourceLocation("textures/entity/ghast/ghast.png");
/* 14:13 */   private static final ResourceLocation ghastShootingTextures = new ResourceLocation("textures/entity/ghast/ghast_shooting.png");
/* 15:   */   private static final String __OBFID = "CL_00000997";
/* 16:   */   
/* 17:   */   public RenderGhast()
/* 18:   */   {
/* 19:18 */     super(new ModelGhast(), 0.5F);
/* 20:   */   }
/* 21:   */   
/* 22:   */   protected ResourceLocation getEntityTexture(EntityGhast par1EntityGhast)
/* 23:   */   {
/* 24:26 */     return par1EntityGhast.func_110182_bF() ? ghastShootingTextures : ghastTextures;
/* 25:   */   }
/* 26:   */   
/* 27:   */   protected void preRenderCallback(EntityGhast par1EntityGhast, float par2)
/* 28:   */   {
/* 29:35 */     float var4 = (par1EntityGhast.prevAttackCounter + (par1EntityGhast.attackCounter - par1EntityGhast.prevAttackCounter) * par2) / 20.0F;
/* 30:37 */     if (var4 < 0.0F) {
/* 31:39 */       var4 = 0.0F;
/* 32:   */     }
/* 33:42 */     var4 = 1.0F / (var4 * var4 * var4 * var4 * var4 * 2.0F + 1.0F);
/* 34:43 */     float var5 = (8.0F + var4) / 2.0F;
/* 35:44 */     float var6 = (8.0F + 1.0F / var4) / 2.0F;
/* 36:45 */     GL11.glScalef(var6, var5, var6);
/* 37:46 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 38:   */   }
/* 39:   */   
/* 40:   */   protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
/* 41:   */   {
/* 42:55 */     preRenderCallback((EntityGhast)par1EntityLivingBase, par2);
/* 43:   */   }
/* 44:   */   
/* 45:   */   protected ResourceLocation getEntityTexture(Entity par1Entity)
/* 46:   */   {
/* 47:63 */     return getEntityTexture((EntityGhast)par1Entity);
/* 48:   */   }
/* 49:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.entity.RenderGhast
 * JD-Core Version:    0.7.0.1
 */