package wtf.opal.mixin;

import java.lang.invoke.MethodHandles;
import net.minecraft.class_309;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.opal.b9;
import wtf.opal.d;
import wtf.opal.d1;
import wtf.opal.on;
import wtf.opal.uc;
import wtf.opal.x5;

@Mixin({class_309.class})
public final class KeyboardMixin {
  private static final long a = on.a(2488922411776979456L, -7837601631676413492L, MethodHandles.lookup().lookupClass()).a(21499231979196L);
  
  @Inject(at = {@At("HEAD")}, method = {"onKey"})
  public void onKey(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4, CallbackInfo paramCallbackInfo) {
    long l1 = a ^ 0x644A7751B5BFL;
    long l2 = l1 ^ 0x13D8DC983F4L;
    long l3 = l1 ^ 0x78D6F953424L;
    try {
      if (paramInt3 == 1) {
        try {
          if (paramInt1 == -1)
            return; 
        } catch (x5 x5) {
          throw a(null);
        } 
        try {
          if (b9.c.field_1755 == null) {
            (new Object[2])[1] = new uc(paramInt1, l2);
            new Object[2];
            d1.q(new Object[0]).q(new Object[0]).N(new Object[] { Long.valueOf(l3) });
            d1.q(new Object[0]).x(new Object[0]).g(new Object[0]).forEach(paramInt1::lambda$onKey$0);
          } 
        } catch (x5 x5) {
          throw a(null);
        } 
      } 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  private static void lambda$onKey$0(int paramInt, d paramd) {
    long l = a ^ 0x5F8BF301285AL;
    int i = (int)((l ^ 0x5A0926225675L) >>> 48L);
    int j = (int)((l ^ 0x5A0926225675L) << 16L >>> 48L);
    int k = (int)((l ^ 0x5A0926225675L) << 32L >>> 32L);
    l ^ 0x5A0926225675L;
    try {
      if (((Integer)paramd.i(new Object[0]).z()).intValue() == paramInt)
        paramd.D(new Object[] { null, null, Integer.valueOf(k), Integer.valueOf((short)j), Integer.valueOf((short)i) }); 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\mixin\KeyboardMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */