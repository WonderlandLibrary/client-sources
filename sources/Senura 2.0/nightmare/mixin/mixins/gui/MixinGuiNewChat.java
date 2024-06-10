/*     */ package nightmare.mixin.mixins.gui;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.gui.GuiNewChat;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import nightmare.Nightmare;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Shadow;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.ModifyArg;
/*     */ import org.spongepowered.asm.mixin.injection.ModifyVariable;
/*     */ import org.spongepowered.asm.mixin.injection.Redirect;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Mixin({GuiNewChat.class})
/*     */ public abstract class MixinGuiNewChat
/*     */   extends Gui
/*     */ {
/*     */   @Shadow
/*     */   private boolean field_146251_k;
/*     */   private float percentComplete;
/*     */   private int newLines;
/*  31 */   private long prevMillis = System.currentTimeMillis();
/*     */   
/*     */   private boolean configuring;
/*     */   private float animationPercent;
/*     */   
/*     */   private void updatePercentage(long diff) {
/*  37 */     if (this.percentComplete < 1.0F) this.percentComplete += 0.004F * (float)diff; 
/*  38 */     this.percentComplete = clamp(this.percentComplete, 0.0F, 1.0F);
/*     */   } private int lineBeingDrawn; @Shadow
/*     */   public abstract float func_146244_h();
/*     */   @Inject(method = {"drawChat"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void modifyChatRendering(CallbackInfo ci) {
/*  43 */     if (this.configuring) {
/*  44 */       ci.cancel();
/*     */       return;
/*     */     } 
/*  47 */     long current = System.currentTimeMillis();
/*  48 */     long diff = current - this.prevMillis;
/*  49 */     this.prevMillis = current;
/*  50 */     updatePercentage(diff);
/*  51 */     float t = this.percentComplete;
/*  52 */     this.animationPercent = clamp(1.0F - --t * t * t * t, 0.0F, 1.0F);
/*     */   }
/*     */   
/*     */   @Inject(method = {"drawChat"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;pushMatrix()V", ordinal = 0, shift = At.Shift.AFTER)})
/*     */   private void translate(CallbackInfo ci) {
/*  57 */     float y = 0.0F;
/*     */     
/*  59 */     if (Nightmare.instance.moduleManager.getModuleByName("Chat").isToggled() && Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("Chat"), "SmoothChat").getValBoolean() && !this.field_146251_k) {
/*  60 */       y += (9.0F - 9.0F * this.animationPercent) * func_146244_h();
/*     */     }
/*  62 */     GlStateManager.func_179109_b(0.0F, y, 0.0F);
/*     */   }
/*     */   
/*     */   @Redirect(method = {"drawChat"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiNewChat;drawRect(IIIII)V", ordinal = 0))
/*     */   private void transparentBackground(int left, int top, int right, int bottom, int color) {
/*  67 */     if (Nightmare.instance.moduleManager.getModuleByName("Chat").isDisabled() || (Nightmare.instance.moduleManager.getModuleByName("Chat").isToggled() && !Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("Chat"), "ClearChat").getValBoolean())) {
/*  68 */       func_73734_a(left, top, right, bottom, color);
/*     */     }
/*     */   }
/*     */   
/*     */   @ModifyArg(method = {"drawChat"}, at = @At(value = "INVOKE", target = "Ljava/util/List;get(I)Ljava/lang/Object;", ordinal = 0, remap = false), index = 0)
/*     */   private int getLineBeingDrawn(int line) {
/*  74 */     this.lineBeingDrawn = line;
/*  75 */     return line;
/*     */   }
/*     */   
/*     */   @ModifyArg(method = {"drawChat"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawStringWithShadow(Ljava/lang/String;FFI)I"), index = 3)
/*     */   private int modifyTextOpacity(int original) {
/*  80 */     if (Nightmare.instance.moduleManager.getModuleByName("Chat").isToggled() && Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("Chat"), "SmoothChat").getValBoolean() && this.lineBeingDrawn <= this.newLines) {
/*  81 */       int opacity = original >> 24 & 0xFF;
/*  82 */       opacity = (int)(opacity * this.animationPercent);
/*  83 */       return original & 0xFFFFFF | opacity << 24;
/*     */     } 
/*  85 */     return original;
/*     */   }
/*     */ 
/*     */   
/*     */   @Inject(method = {"printChatMessageWithOptionalDeletion"}, at = {@At("HEAD")})
/*     */   private void resetPercentage(CallbackInfo ci) {
/*  91 */     this.percentComplete = 0.0F;
/*     */   }
/*     */   
/*     */   @ModifyVariable(method = {"setChatLine"}, at = @At("STORE"), ordinal = 0)
/*     */   private List<IChatComponent> setNewLines(List<IChatComponent> original) {
/*  96 */     this.newLines = original.size() - 1;
/*  97 */     return original;
/*     */   }
/*     */   
/*     */   public float clamp(float number, float min, float max) {
/* 101 */     return (number < min) ? min : Math.min(number, max);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\mixin\mixins\gui\MixinGuiNewChat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */