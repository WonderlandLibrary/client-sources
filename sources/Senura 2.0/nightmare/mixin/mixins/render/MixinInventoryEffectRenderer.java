/*    */ package nightmare.mixin.mixins.render;
/*    */ 
/*    */ import net.minecraft.client.gui.inventory.GuiContainer;
/*    */ import net.minecraft.client.renderer.InventoryEffectRenderer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import nightmare.Nightmare;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Overwrite;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ 
/*    */ @Mixin({InventoryEffectRenderer.class})
/*    */ public abstract class MixinInventoryEffectRenderer
/*    */   extends GuiContainer
/*    */ {
/*    */   @Shadow
/*    */   private boolean field_147045_u;
/*    */   
/*    */   public MixinInventoryEffectRenderer(Container inventorySlotsIn) {
/* 19 */     super(inventorySlotsIn);
/*    */   }
/*    */ 
/*    */   
/*    */   @Overwrite
/*    */   protected void func_175378_g() {
/* 25 */     if (!this.field_146297_k.field_71439_g.func_70651_bq().isEmpty()) {
/*    */       
/* 27 */       if (Nightmare.instance.moduleManager.getModuleByName("NoPotionShift").isToggled()) {
/* 28 */         this.field_147003_i = (this.field_146294_l - this.field_146999_f) / 2;
/*    */       } else {
/* 30 */         this.field_147003_i = 160 + (this.field_146294_l - this.field_146999_f - 200) / 2;
/*    */       } 
/*    */       
/* 33 */       this.field_147045_u = true;
/*    */     }
/*    */     else {
/*    */       
/* 37 */       this.field_147003_i = (this.field_146294_l - this.field_146999_f) / 2;
/* 38 */       this.field_147045_u = false;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\mixin\mixins\render\MixinInventoryEffectRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */