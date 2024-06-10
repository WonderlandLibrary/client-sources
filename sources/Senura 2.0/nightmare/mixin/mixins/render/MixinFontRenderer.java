/*    */ package nightmare.mixin.mixins.render;
/*    */ 
/*    */ import net.minecraft.client.gui.FontRenderer;
/*    */ import nightmare.Nightmare;
/*    */ import nightmare.event.impl.EventText;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.ModifyVariable;
/*    */ 
/*    */ 
/*    */ @Mixin({FontRenderer.class})
/*    */ public class MixinFontRenderer
/*    */ {
/*    */   @ModifyVariable(method = {"renderString"}, at = @At("HEAD"), ordinal = 0)
/*    */   private String renderString(String text) {
/* 16 */     if (text == null || Nightmare.instance.eventManager == null) {
/* 17 */       return text;
/*    */     }
/*    */     
/* 20 */     EventText event = new EventText(text);
/* 21 */     event.call();
/*    */     
/* 23 */     return event.getOutputText();
/*    */   }
/*    */   
/*    */   @ModifyVariable(method = {"getStringWidth"}, at = @At("HEAD"), ordinal = 0)
/*    */   private String getStringWidth(String text) {
/* 28 */     if (text == null || Nightmare.instance.eventManager == null) {
/* 29 */       return text;
/*    */     }
/*    */     
/* 32 */     EventText event = new EventText(text);
/* 33 */     event.call();
/*    */     
/* 35 */     return event.getOutputText();
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\mixin\mixins\render\MixinFontRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */