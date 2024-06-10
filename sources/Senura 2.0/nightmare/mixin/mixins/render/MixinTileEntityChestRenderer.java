/*    */ package nightmare.mixin.mixins.render;
/*    */ 
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityChestRenderer;
/*    */ import nightmare.Nightmare;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ @Mixin({TileEntityChestRenderer.class})
/*    */ public class MixinTileEntityChestRenderer
/*    */ {
/*    */   @Inject(method = {"renderTileEntityAt"}, at = {@At("HEAD")})
/*    */   private void startChams(CallbackInfo ci) {
/* 17 */     if (Nightmare.instance.moduleManager.getModuleByName("Chams").isToggled() && Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("Chams"), "Chest").getValBoolean()) {
/* 18 */       GL11.glEnable(32823);
/* 19 */       GL11.glPolygonOffset(1.0F, -1000000.0F);
/*    */     } 
/*    */   }
/*    */   
/*    */   @Inject(method = {"renderTileEntityAt"}, at = {@At("RETURN")})
/*    */   private void stopChams(CallbackInfo ci) {
/* 25 */     if (Nightmare.instance.moduleManager.getModuleByName("Chams").isToggled() && Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("Chams"), "Chest").getValBoolean()) {
/* 26 */       GL11.glPolygonOffset(1.0F, 1000000.0F);
/* 27 */       GL11.glDisable(32823);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\mixin\mixins\render\MixinTileEntityChestRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */