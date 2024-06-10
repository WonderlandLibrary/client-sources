/*    */ package nightmare.mixin.mixins.client;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.util.Session;
/*    */ import nightmare.Nightmare;
/*    */ import nightmare.event.impl.EventKey;
/*    */ import nightmare.event.impl.EventTick;
/*    */ import nightmare.hooks.MinecraftHook;
/*    */ import nightmare.utils.AnimationUtils;
/*    */ import org.lwjgl.Sys;
/*    */ import org.lwjgl.input.Keyboard;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({Minecraft.class})
/*    */ public class MixinMinecraft
/*    */ {
/*    */   @Shadow
/*    */   public Session field_71449_j;
/*    */   @Shadow
/*    */   private int field_71467_ac;
/*    */   @Shadow
/*    */   private int field_71429_W;
/* 31 */   long lastFrame = getTime(); long getTime() {
/* 32 */     return Sys.getTime() * 1000L / Sys.getTimerResolution();
/*    */   }
/*    */   @Inject(method = {"startGame"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;ingameGUI:Lnet/minecraft/client/gui/GuiIngame;", shift = At.Shift.AFTER)})
/*    */   private void startGame(CallbackInfo ci) {
/* 36 */     Nightmare.instance.startClient();
/*    */   }
/*    */   
/*    */   @Inject(method = {"shutdown"}, at = {@At("HEAD")})
/*    */   private void onShutdown(CallbackInfo ci) {
/* 41 */     Nightmare.instance.stopClient();
/*    */   }
/*    */   
/*    */   @Inject(method = {"runTick"}, at = {@At("TAIL")})
/*    */   private void onTick(CallbackInfo ci) {
/* 46 */     EventTick event = new EventTick();
/* 47 */     event.call();
/*    */   }
/*    */   
/*    */   @Inject(method = {"runTick"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;dispatchKeypresses()V", shift = At.Shift.AFTER)})
/*    */   private void onKey(CallbackInfo ci) {
/* 52 */     if (Keyboard.getEventKeyState() && (Minecraft.func_71410_x()).field_71462_r == null) {
/* 53 */       EventKey event = new EventKey((Keyboard.getEventKey() == 0) ? (Keyboard.getEventCharacter() + 256) : Keyboard.getEventKey());
/* 54 */       event.call();
/*    */     } 
/*    */   }
/*    */   
/*    */   @Inject(method = {"rightClickMouse"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;rightClickDelayTimer:I", shift = At.Shift.AFTER)})
/*    */   private void rightClickMouse(CallbackInfo ci) {
/* 60 */     if (Nightmare.instance.moduleManager.getModuleByName("FastPlace").isToggled()) {
/* 61 */       this.field_71467_ac = (int)Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("FastPlace"), "Delay").getValDouble();
/*    */     }
/*    */   }
/*    */   
/*    */   @Inject(method = {"clickMouse"}, at = {@At("HEAD")})
/*    */   private void clickMouse(CallbackInfo ci) {
/* 67 */     this.field_71429_W = 0;
/*    */   }
/*    */   
/*    */   @Inject(method = {"setIngameFocus"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/util/MouseHelper;grabMouseCursor()V")})
/*    */   private void setIngameFocus(CallbackInfo ci) {
/* 72 */     MinecraftHook.updateKeyBindState();
/*    */   }
/*    */   
/*    */   @Inject(method = {"runGameLoop"}, at = {@At("HEAD")})
/*    */   private void runGameLoop(CallbackInfo ci) {
/* 77 */     long currentTime = getTime();
/* 78 */     int deltaTime = (int)(currentTime - this.lastFrame);
/* 79 */     this.lastFrame = currentTime;
/* 80 */     AnimationUtils.delta = deltaTime;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\mixin\mixins\client\MixinMinecraft.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */