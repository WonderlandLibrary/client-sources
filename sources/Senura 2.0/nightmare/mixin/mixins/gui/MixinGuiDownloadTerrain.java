/*    */ package nightmare.mixin.mixins.gui;
/*    */ 
/*    */ import net.minecraft.client.gui.GuiDownloadTerrain;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import nightmare.event.impl.EventLoadWorld;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Overwrite;
/*    */ 
/*    */ 
/*    */ @Mixin({GuiDownloadTerrain.class})
/*    */ public class MixinGuiDownloadTerrain
/*    */   extends GuiScreen
/*    */ {
/*    */   @Overwrite
/*    */   public void func_73866_w_() {
/* 16 */     this.field_146292_n.clear();
/* 17 */     EventLoadWorld event = new EventLoadWorld();
/* 18 */     event.call();
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\mixin\mixins\gui\MixinGuiDownloadTerrain.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */