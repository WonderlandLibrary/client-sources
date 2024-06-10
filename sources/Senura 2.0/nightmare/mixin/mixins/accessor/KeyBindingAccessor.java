/*    */ package nightmare.mixin.mixins.accessor;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.client.settings.KeyBinding;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.gen.Accessor;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({KeyBinding.class})
/*    */ public interface KeyBindingAccessor
/*    */ {
/*    */   @Accessor
/*    */   static List<KeyBinding> getKeybindArray() {
/* 15 */     throw new UnsupportedOperationException("Mixin failed to inject!");
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\mixin\mixins\accessor\KeyBindingAccessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */