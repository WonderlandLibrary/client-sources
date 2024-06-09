/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.monster.EntitySilverfish;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderSilverfish extends RenderLiving<EntitySilverfish> {
/*  9 */   private static final ResourceLocation silverfishTextures = new ResourceLocation("textures/entity/silverfish.png");
/*    */ 
/*    */   
/*    */   public RenderSilverfish(RenderManager renderManagerIn) {
/* 13 */     super(renderManagerIn, (ModelBase)new ModelSilverfish(), 0.3F);
/*    */   }
/*    */ 
/*    */   
/*    */   protected float getDeathMaxRotation(EntitySilverfish entityLivingBaseIn) {
/* 18 */     return 180.0F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntitySilverfish entity) {
/* 26 */     return silverfishTextures;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\renderer\entity\RenderSilverfish.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */