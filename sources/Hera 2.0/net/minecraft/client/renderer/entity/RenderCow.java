/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.passive.EntityCow;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderCow extends RenderLiving<EntityCow> {
/*  9 */   private static final ResourceLocation cowTextures = new ResourceLocation("textures/entity/cow/cow.png");
/*    */ 
/*    */   
/*    */   public RenderCow(RenderManager renderManagerIn, ModelBase modelBaseIn, float shadowSizeIn) {
/* 13 */     super(renderManagerIn, modelBaseIn, shadowSizeIn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityCow entity) {
/* 21 */     return cowTextures;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\renderer\entity\RenderCow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */