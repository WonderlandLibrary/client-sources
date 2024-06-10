/*    */ package nightmare.mixin.mixins.gui;
/*    */ 
/*    */ import net.minecraft.client.gui.Gui;
/*    */ import net.minecraft.client.gui.GuiIngame;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import nightmare.event.impl.EventRender2D;
/*    */ import nightmare.utils.GlUtils;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ @Mixin({GuiIngame.class})
/*    */ public abstract class MixinGuiIngame
/*    */   extends Gui
/*    */ {
/*    */   @Shadow
/* 21 */   protected static final ResourceLocation field_110330_c = new ResourceLocation("textures/gui/widgets.png");
/*    */   
/*    */   @Shadow
/*    */   protected abstract void func_175184_a(int paramInt1, int paramInt2, int paramInt3, float paramFloat, EntityPlayer paramEntityPlayer);
/*    */   
/*    */   @Inject(method = {"renderTooltip"}, at = {@At("RETURN")})
/*    */   private void renderTooltip(ScaledResolution sr, float partialTicks, CallbackInfo ci) {
/* 28 */     EventRender2D event = new EventRender2D();
/* 29 */     event.call();
/*    */   }
/*    */   
/*    */   @Inject(method = {"renderHotbarItem"}, at = {@At("HEAD")})
/*    */   protected void renderHotbarItemHead(int index, int xPos, int yPos, float partialTicks, EntityPlayer player, CallbackInfo ci) {
/* 34 */     GlUtils.fixGlint();
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\mixin\mixins\gui\MixinGuiIngame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */