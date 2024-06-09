/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBiped;
/*    */ import net.minecraft.client.model.ModelZombieVillager;
/*    */ import net.minecraft.client.renderer.entity.RendererLivingEntity;
/*    */ 
/*    */ public class LayerVillagerArmor
/*    */   extends LayerBipedArmor {
/*    */   public LayerVillagerArmor(RendererLivingEntity<?> rendererIn) {
/* 10 */     super(rendererIn);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void initArmor() {
/* 15 */     this.field_177189_c = (ModelBiped)new ModelZombieVillager(0.5F, 0.0F, true);
/* 16 */     this.field_177186_d = (ModelBiped)new ModelZombieVillager(1.0F, 0.0F, true);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\renderer\entity\layers\LayerVillagerArmor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */