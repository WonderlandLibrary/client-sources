/*    */ package nightmare.mixin.mixins.render;
/*    */ 
/*    */ import net.minecraft.client.gui.FontRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderItem;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import nightmare.utils.GlUtils;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ @Mixin({RenderItem.class})
/*    */ public class MixinRenderItem
/*    */ {
/*    */   @Inject(method = {"renderItemAndEffectIntoGUI"}, at = {@At("HEAD")})
/*    */   public void renderItemAndEffectIntoGUIhead(ItemStack stack, int xPosition, int yPosition, CallbackInfo ci) {
/* 18 */     GlUtils.fixGlint();
/*    */   }
/*    */   
/*    */   @Inject(method = {"renderItemOverlayIntoGUI"}, at = {@At("HEAD")})
/*    */   public void renderItemOverlayIntoGUIhead(FontRenderer fr, ItemStack stack, int xPosition, int yPosition, String text, CallbackInfo ci) {
/* 23 */     GlUtils.fixGlint();
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\mixin\mixins\render\MixinRenderItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */