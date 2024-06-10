/*    */ package nightmare.mixin.mixins.gui;
/*    */ 
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.client.gui.inventory.GuiContainer;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ @Mixin({GuiContainer.class})
/*    */ public abstract class MixinGuiContainer
/*    */   extends GuiScreen
/*    */ {
/*    */   @Shadow
/*    */   protected abstract boolean func_146983_a(int paramInt);
/*    */   
/*    */   @Inject(method = {"mouseClicked"}, at = {@At("TAIL")})
/*    */   private void mouseClicked(int mouseX, int mouseY, int mouseButton, CallbackInfo ci) {
/* 20 */     func_146983_a(mouseButton - 100);
/*    */   }
/*    */   
/*    */   @Inject(method = {"mouseClicked"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void mouseClicked2(int mouseX, int mouseY, int mouseButton, CallbackInfo ci) {
/* 25 */     if (mouseButton - 100 == this.field_146297_k.field_71474_y.field_151445_Q.func_151463_i()) {
/* 26 */       this.field_146297_k.field_71439_g.func_71053_j();
/* 27 */       ci.cancel();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\mixin\mixins\gui\MixinGuiContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */