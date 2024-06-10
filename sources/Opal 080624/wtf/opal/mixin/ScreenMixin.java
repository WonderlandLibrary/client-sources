package wtf.opal.mixin;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.class_2558;
import net.minecraft.class_2583;
import net.minecraft.class_437;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import wtf.opal.uq;
import wtf.opal.uw;
import wtf.opal.x5;

@Mixin({class_437.class})
public final class ScreenMixin {
  @Inject(method = {"handleTextClick"}, at = {@At("HEAD")}, cancellable = true)
  private void onInvalidClickEvent(class_2583 paramclass_2583, CallbackInfoReturnable<Boolean> paramCallbackInfoReturnable) {
    class_2558 class_2558 = paramclass_2583.method_10970();
    if (class_2558 instanceof uq) {
      uq uq = (uq)class_2558;
      uq.s(new Object[0]).run();
      paramCallbackInfoReturnable.setReturnValue(Boolean.valueOf(true));
    } 
  }
  
  @Inject(method = {"handleTextClick"}, at = {@At(value = "INVOKE", target = "Lorg/slf4j/Logger;error(Ljava/lang/String;Ljava/lang/Object;)V", ordinal = 1, remap = false)}, cancellable = true)
  private void onRunCommand(class_2583 paramclass_2583, CallbackInfoReturnable<Boolean> paramCallbackInfoReturnable) {
    class_2558 class_2558 = paramclass_2583.method_10970();
    if (class_2558 instanceof uq) {
      uq uq = (uq)class_2558;
      try {
        if (uq.method_10844().startsWith("."))
          try {
            uw.c(new Object[] { paramclass_2583.method_10970().method_10844() });
            paramCallbackInfoReturnable.setReturnValue(Boolean.valueOf(true));
          } catch (CommandSyntaxException commandSyntaxException) {
            commandSyntaxException.printStackTrace();
          }  
      } catch (x5 x5) {
        throw a(null);
      } 
    } 
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\mixin\ScreenMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */