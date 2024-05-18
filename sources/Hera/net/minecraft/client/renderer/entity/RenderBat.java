/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelBat;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.passive.EntityBat;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderBat extends RenderLiving<EntityBat> {
/* 11 */   private static final ResourceLocation batTextures = new ResourceLocation("textures/entity/bat.png");
/*    */ 
/*    */   
/*    */   public RenderBat(RenderManager renderManagerIn) {
/* 15 */     super(renderManagerIn, (ModelBase)new ModelBat(), 0.25F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityBat entity) {
/* 23 */     return batTextures;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void preRenderCallback(EntityBat entitylivingbaseIn, float partialTickTime) {
/* 32 */     GlStateManager.scale(0.35F, 0.35F, 0.35F);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void rotateCorpse(EntityBat bat, float p_77043_2_, float p_77043_3_, float partialTicks) {
/* 37 */     if (!bat.getIsBatHanging()) {
/*    */       
/* 39 */       GlStateManager.translate(0.0F, MathHelper.cos(p_77043_2_ * 0.3F) * 0.1F, 0.0F);
/*    */     }
/*    */     else {
/*    */       
/* 43 */       GlStateManager.translate(0.0F, -0.1F, 0.0F);
/*    */     } 
/*    */     
/* 46 */     super.rotateCorpse(bat, p_77043_2_, p_77043_3_, partialTicks);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\renderer\entity\RenderBat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */