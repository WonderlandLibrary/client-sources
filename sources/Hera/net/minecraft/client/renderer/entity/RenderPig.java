/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerSaddle;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.passive.EntityPig;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderPig extends RenderLiving<EntityPig> {
/* 10 */   private static final ResourceLocation pigTextures = new ResourceLocation("textures/entity/pig/pig.png");
/*    */ 
/*    */   
/*    */   public RenderPig(RenderManager renderManagerIn, ModelBase modelBaseIn, float shadowSizeIn) {
/* 14 */     super(renderManagerIn, modelBaseIn, shadowSizeIn);
/* 15 */     addLayer(new LayerSaddle(this));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityPig entity) {
/* 23 */     return pigTextures;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\renderer\entity\RenderPig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */