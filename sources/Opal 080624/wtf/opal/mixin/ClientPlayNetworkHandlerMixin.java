package wtf.opal.mixin;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.lang.invoke.MethodHandles;
import java.time.Instant;
import net.minecraft.class_2535;
import net.minecraft.class_2596;
import net.minecraft.class_2797;
import net.minecraft.class_310;
import net.minecraft.class_3515;
import net.minecraft.class_634;
import net.minecraft.class_7469;
import net.minecraft.class_7608;
import net.minecraft.class_7610;
import net.minecraft.class_7637;
import net.minecraft.class_8673;
import net.minecraft.class_8675;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.opal.on;
import wtf.opal.uf;
import wtf.opal.uw;
import wtf.opal.x5;

@Mixin({class_634.class})
public abstract class ClientPlayNetworkHandlerMixin extends class_8673 {
  @Shadow
  private class_7637 field_39858;
  
  @Shadow
  private class_7610.class_7612 field_39808;
  
  @Unique
  private boolean ignoreChatMessage;
  
  private static final long a = on.a(-8949081804034920737L, 6252442042324469185L, MethodHandles.lookup().lookupClass()).a(102555838828956L);
  
  protected ClientPlayNetworkHandlerMixin(class_310 paramclass_310, class_2535 paramclass_2535, class_8675 paramclass_8675) {
    super(paramclass_310, paramclass_2535, paramclass_8675);
  }
  
  @Inject(method = {"sendChatMessage"}, at = {@At("HEAD")}, cancellable = true)
  private void onSendChatMessage(String paramString, CallbackInfo paramCallbackInfo) {
    long l1 = a ^ 0x1B17B73E577FL;
    long l2 = l1 ^ 0x65AB3351DAF5L;
    try {
      if (this.ignoreChatMessage)
        return; 
    } catch (CommandSyntaxException commandSyntaxException) {
      throw a(null);
    } 
    String str = ".";
    try {
      if (paramString.startsWith(".")) {
        try {
          uw.c(new Object[] { paramString.substring(".".length()) });
        } catch (CommandSyntaxException commandSyntaxException) {
          (new Object[2])[1] = commandSyntaxException.getMessage();
          new Object[2];
          uf.m(new Object[] { Long.valueOf(l2) });
        } 
        this.field_45588.field_1705.method_1743().method_1803(paramString);
        paramCallbackInfo.cancel();
      } else {
        Instant instant = Instant.now();
        long l = class_3515.class_7426.method_43531();
        class_7637.class_7816 class_7816 = this.field_39858.method_46266();
        class_7469 class_7469 = this.field_39808.pack(new class_7608(paramString, instant, l, class_7816.comp_1073()));
        method_52787((class_2596)new class_2797(paramString, instant, l, class_7469, class_7816.comp_1074()));
        paramCallbackInfo.cancel();
      } 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  private static Exception a(Exception paramException) {
    return paramException;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\mixin\ClientPlayNetworkHandlerMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */