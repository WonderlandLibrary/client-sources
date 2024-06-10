package wtf.opal.mixin;

import java.lang.invoke.MethodHandles;
import net.minecraft.class_5223;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import wtf.opal.d1;
import wtf.opal.ju;
import wtf.opal.on;
import wtf.opal.x2;
import wtf.opal.x5;

@Mixin({class_5223.class})
public final class TextVisitFactoryMixin {
  private static final long a = on.a(-4454594929706269620L, 4816457955235087686L, MethodHandles.lookup().lookupClass()).a(93509580050702L);
  
  @ModifyVariable(method = {"visitFormatted(Ljava/lang/String;ILnet/minecraft/text/Style;Lnet/minecraft/text/Style;Lnet/minecraft/text/CharacterVisitor;)Z"}, at = @At("HEAD"), argsOnly = true, ordinal = 0)
  private static String modifyVisitFormatted(String paramString) {
    long l1 = a ^ 0x45EE8F9285DFL;
    long l2 = l1 ^ 0x16AC1E5D52DCL;
    d1 d1 = d1.q(new Object[0]);
    try {
      if (d1 == null)
        return paramString; 
    } catch (x5 x5) {
      throw a(null);
    } 
    x2 x2 = d1.x(new Object[0]);
    try {
      if (x2 == null)
        return paramString; 
    } catch (x5 x5) {
      throw a(null);
    } 
    ju ju = (ju)x2.V(new Object[] { ju.class });
    try {
      if (ju.D(new Object[0])) {
        (new Object[2])[1] = paramString;
        new Object[2];
        return ju.S(new Object[] { Long.valueOf(l2) });
      } 
    } catch (x5 x5) {
      throw a(null);
    } 
    return paramString;
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\mixin\TextVisitFactoryMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */