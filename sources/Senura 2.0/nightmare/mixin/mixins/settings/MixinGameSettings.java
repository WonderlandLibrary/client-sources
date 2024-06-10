/*    */ package nightmare.mixin.mixins.settings;
/*    */ 
/*    */ import net.minecraft.client.settings.GameSettings;
/*    */ import net.minecraft.client.settings.KeyBinding;
/*    */ import org.lwjgl.input.Keyboard;
/*    */ import org.lwjgl.input.Mouse;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Overwrite;
/*    */ 
/*    */ 
/*    */ @Mixin({GameSettings.class})
/*    */ public class MixinGameSettings
/*    */ {
/*    */   @Overwrite
/*    */   public static boolean func_100015_a(KeyBinding key) {
/* 16 */     int keyCode = key.func_151463_i();
/* 17 */     if (keyCode != 0 && keyCode < 256) {
/* 18 */       return (keyCode < 0) ? Mouse.isButtonDown(keyCode + 100) : Keyboard.isKeyDown(keyCode);
/*    */     }
/* 20 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\mixin\mixins\settings\MixinGameSettings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */