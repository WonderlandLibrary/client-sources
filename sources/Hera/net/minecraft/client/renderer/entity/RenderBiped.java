/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelBiped;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderBiped<T extends EntityLiving> extends RenderLiving<T> {
/* 12 */   private static final ResourceLocation DEFAULT_RES_LOC = new ResourceLocation("textures/entity/steve.png");
/*    */   
/*    */   protected ModelBiped modelBipedMain;
/*    */   protected float field_77070_b;
/*    */   
/*    */   public RenderBiped(RenderManager renderManagerIn, ModelBiped modelBipedIn, float shadowSize) {
/* 18 */     this(renderManagerIn, modelBipedIn, shadowSize, 1.0F);
/* 19 */     addLayer(new LayerHeldItem(this));
/*    */   }
/*    */ 
/*    */   
/*    */   public RenderBiped(RenderManager renderManagerIn, ModelBiped modelBipedIn, float shadowSize, float p_i46169_4_) {
/* 24 */     super(renderManagerIn, (ModelBase)modelBipedIn, shadowSize);
/* 25 */     this.modelBipedMain = modelBipedIn;
/* 26 */     this.field_77070_b = p_i46169_4_;
/* 27 */     addLayer(new LayerCustomHead(modelBipedIn.bipedHead));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(T entity) {
/* 35 */     return DEFAULT_RES_LOC;
/*    */   }
/*    */ 
/*    */   
/*    */   public void transformHeldFull3DItemLayer() {
/* 40 */     GlStateManager.translate(0.0F, 0.1875F, 0.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\renderer\entity\RenderBiped.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */