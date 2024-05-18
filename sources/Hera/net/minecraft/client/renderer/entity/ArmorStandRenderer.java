/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import net.minecraft.client.model.ModelArmorStand;
/*    */ import net.minecraft.client.model.ModelArmorStandArmor;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.item.EntityArmorStand;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class ArmorStandRenderer
/*    */   extends RendererLivingEntity<EntityArmorStand> {
/* 17 */   public static final ResourceLocation TEXTURE_ARMOR_STAND = new ResourceLocation("textures/entity/armorstand/wood.png");
/*    */ 
/*    */   
/*    */   public ArmorStandRenderer(RenderManager p_i46195_1_) {
/* 21 */     super(p_i46195_1_, (ModelBase)new ModelArmorStand(), 0.0F);
/* 22 */     LayerBipedArmor layerbipedarmor = new LayerBipedArmor(this)
/*    */       {
/*    */         protected void initArmor()
/*    */         {
/* 26 */           this.field_177189_c = (ModelBase)new ModelArmorStandArmor(0.5F);
/* 27 */           this.field_177186_d = (ModelBase)new ModelArmorStandArmor(1.0F);
/*    */         }
/*    */       };
/* 30 */     addLayer(layerbipedarmor);
/* 31 */     addLayer(new LayerHeldItem(this));
/* 32 */     addLayer(new LayerCustomHead((getMainModel()).bipedHead));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityArmorStand entity) {
/* 40 */     return TEXTURE_ARMOR_STAND;
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelArmorStand getMainModel() {
/* 45 */     return (ModelArmorStand)super.getMainModel();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void rotateCorpse(EntityArmorStand bat, float p_77043_2_, float p_77043_3_, float partialTicks) {
/* 50 */     GlStateManager.rotate(180.0F - p_77043_3_, 0.0F, 1.0F, 0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean canRenderName(EntityArmorStand entity) {
/* 55 */     return entity.getAlwaysRenderNameTag();
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\renderer\entity\ArmorStandRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */