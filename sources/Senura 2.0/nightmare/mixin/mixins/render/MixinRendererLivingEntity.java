/*    */ package nightmare.mixin.mixins.render;
/*    */ 
/*    */ import net.minecraft.client.renderer.entity.RendererLivingEntity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import nightmare.Nightmare;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({RendererLivingEntity.class})
/*    */ public abstract class MixinRendererLivingEntity<T extends EntityLivingBase>
/*    */   extends MixinRender<T>
/*    */ {
/*    */   @Inject(method = {"doRender"}, at = {@At("HEAD")})
/*    */   private void startChams(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
/* 20 */     if (Nightmare.instance.moduleManager.getModuleByName("Chams").isToggled() && Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("Chams"), "Player").getValBoolean()) {
/* 21 */       GL11.glEnable(32823);
/* 22 */       GL11.glPolygonOffset(1.0F, -1000000.0F);
/*    */     } 
/*    */   }
/*    */   
/*    */   @Inject(method = {"doRender"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void hideArmorStand(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
/* 28 */     if (Nightmare.instance.moduleManager.getModuleByName("FPSBoost").isToggled() && Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("FPSBoost"), "HideArmorStand").getValBoolean() && 
/* 29 */       entity instanceof net.minecraft.entity.item.EntityArmorStand) {
/* 30 */       ci.cancel();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   @Inject(method = {"doRender"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void hideBat(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
/* 37 */     if (Nightmare.instance.moduleManager.getModuleByName("FPSBoost").isToggled() && Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("FPSBoost"), "HideBat").getValBoolean() && 
/* 38 */       entity instanceof net.minecraft.entity.passive.EntityBat) {
/* 39 */       ci.cancel();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   @Inject(method = {"doRender"}, at = {@At("RETURN")})
/*    */   private void stopChams(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
/* 46 */     if (Nightmare.instance.moduleManager.getModuleByName("Chams").isToggled() && Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("Chams"), "Player").getValBoolean()) {
/* 47 */       GL11.glPolygonOffset(1.0F, 1000000.0F);
/* 48 */       GL11.glDisable(32823);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\mixin\mixins\render\MixinRendererLivingEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */