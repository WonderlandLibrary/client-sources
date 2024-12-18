/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.passive.EntityOcelot;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderOcelot extends RenderLiving<EntityOcelot> {
/* 10 */   private static final ResourceLocation blackOcelotTextures = new ResourceLocation("textures/entity/cat/black.png");
/* 11 */   private static final ResourceLocation ocelotTextures = new ResourceLocation("textures/entity/cat/ocelot.png");
/* 12 */   private static final ResourceLocation redOcelotTextures = new ResourceLocation("textures/entity/cat/red.png");
/* 13 */   private static final ResourceLocation siameseOcelotTextures = new ResourceLocation("textures/entity/cat/siamese.png");
/*    */ 
/*    */   
/*    */   public RenderOcelot(RenderManager renderManagerIn, ModelBase modelBaseIn, float shadowSizeIn) {
/* 17 */     super(renderManagerIn, modelBaseIn, shadowSizeIn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityOcelot entity) {
/* 25 */     switch (entity.getTameSkin()) {
/*    */ 
/*    */       
/*    */       default:
/* 29 */         return ocelotTextures;
/*    */       
/*    */       case 1:
/* 32 */         return blackOcelotTextures;
/*    */       
/*    */       case 2:
/* 35 */         return redOcelotTextures;
/*    */       case 3:
/*    */         break;
/* 38 */     }  return siameseOcelotTextures;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void preRenderCallback(EntityOcelot entitylivingbaseIn, float partialTickTime) {
/* 48 */     super.preRenderCallback(entitylivingbaseIn, partialTickTime);
/*    */     
/* 50 */     if (entitylivingbaseIn.isTamed())
/*    */     {
/* 52 */       GlStateManager.scale(0.8F, 0.8F, 0.8F);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\renderer\entity\RenderOcelot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */