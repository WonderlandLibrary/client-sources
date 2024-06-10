/*    */ package nightmare.hooks;
/*    */ 
/*    */ import net.minecraft.client.settings.KeyBinding;
/*    */ import nightmare.mixin.mixins.accessor.KeyBindingAccessor;
/*    */ import org.lwjgl.input.Keyboard;
/*    */ 
/*    */ 
/*    */ public class MinecraftHook
/*    */ {
/*    */   public static void updateKeyBindState() {
/* 11 */     for (KeyBinding keybinding : KeyBindingAccessor.getKeybindArray()) {
/*    */       try {
/* 13 */         int keyCode = keybinding.func_151463_i();
/* 14 */         KeyBinding.func_74510_a(keyCode, (keyCode < 256 && Keyboard.isKeyDown(keyCode)));
/* 15 */       } catch (IndexOutOfBoundsException indexOutOfBoundsException) {}
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\hooks\MinecraftHook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */