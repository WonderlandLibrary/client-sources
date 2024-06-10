/*  1:   */ package net.minecraft.client.renderer.entity;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.model.ModelSkeleton;
/*  4:   */ import net.minecraft.entity.Entity;
/*  5:   */ import net.minecraft.entity.EntityLiving;
/*  6:   */ import net.minecraft.entity.EntityLivingBase;
/*  7:   */ import net.minecraft.entity.monster.EntitySkeleton;
/*  8:   */ import net.minecraft.util.ResourceLocation;
/*  9:   */ import org.lwjgl.opengl.GL11;
/* 10:   */ 
/* 11:   */ public class RenderSkeleton
/* 12:   */   extends RenderBiped
/* 13:   */ {
/* 14:13 */   private static final ResourceLocation skeletonTextures = new ResourceLocation("textures/entity/skeleton/skeleton.png");
/* 15:14 */   private static final ResourceLocation witherSkeletonTextures = new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");
/* 16:   */   private static final String __OBFID = "CL_00001023";
/* 17:   */   
/* 18:   */   public RenderSkeleton()
/* 19:   */   {
/* 20:19 */     super(new ModelSkeleton(), 0.5F);
/* 21:   */   }
/* 22:   */   
/* 23:   */   protected void preRenderCallback(EntitySkeleton par1EntitySkeleton, float par2)
/* 24:   */   {
/* 25:28 */     if (par1EntitySkeleton.getSkeletonType() == 1) {
/* 26:30 */       GL11.glScalef(1.2F, 1.2F, 1.2F);
/* 27:   */     }
/* 28:   */   }
/* 29:   */   
/* 30:   */   protected void func_82422_c()
/* 31:   */   {
/* 32:36 */     GL11.glTranslatef(0.09375F, 0.1875F, 0.0F);
/* 33:   */   }
/* 34:   */   
/* 35:   */   protected ResourceLocation getEntityTexture(EntitySkeleton par1EntitySkeleton)
/* 36:   */   {
/* 37:44 */     return par1EntitySkeleton.getSkeletonType() == 1 ? witherSkeletonTextures : skeletonTextures;
/* 38:   */   }
/* 39:   */   
/* 40:   */   protected ResourceLocation getEntityTexture(EntityLiving par1EntityLiving)
/* 41:   */   {
/* 42:52 */     return getEntityTexture((EntitySkeleton)par1EntityLiving);
/* 43:   */   }
/* 44:   */   
/* 45:   */   protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
/* 46:   */   {
/* 47:61 */     preRenderCallback((EntitySkeleton)par1EntityLivingBase, par2);
/* 48:   */   }
/* 49:   */   
/* 50:   */   protected ResourceLocation getEntityTexture(Entity par1Entity)
/* 51:   */   {
/* 52:69 */     return getEntityTexture((EntitySkeleton)par1Entity);
/* 53:   */   }
/* 54:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.entity.RenderSkeleton
 * JD-Core Version:    0.7.0.1
 */