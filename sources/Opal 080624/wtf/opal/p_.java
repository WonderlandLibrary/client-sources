package wtf.opal;

import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.class_408;
import net.minecraft.class_437;

public final class p_ {
  private final gm<uj> n = p_::lambda$new$0;
  
  private final gm<d4> H;
  
  private final gm<b6> D;
  
  private static final long a = on.a(56081706115180613L, 5098064189095158303L, MethodHandles.lookup().lookupClass()).a(232369673910547L);
  
  private static final long b;
  
  public p_(long paramLong) {
    String str = b9.V();
    try {
      this.H = p_::lambda$new$1;
      this.D = p_::lambda$new$2;
      if (d.D() != null)
        b9.n("gnDdnb"); 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  private static void lambda$new$2(b6 paramb6) {
    long l = a ^ 0x52021C23040L;
    String str = b9.V();
    try {
      if (!paramb6.W(new Object[0])) {
        try {
          if (str != null)
            try {
              if (b9.c.field_1755 == null) {
              
              } else {
                return;
              } 
            } catch (x5 x5) {
              throw a(null);
            }  
        } catch (x5 x5) {
          throw a(null);
        } 
        try {
          if (b9.c.method_18506() == null)
            try {
              if (dj.D(new Object[] { Integer.valueOf((int)b) }))
                b9.c.method_1507((class_437)new class_408(".")); 
            } catch (x5 x5) {
              throw a(null);
            }  
        } catch (x5 x5) {
          throw a(null);
        } 
      } 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  private static void lambda$new$1(d4 paramd4) {
    df.w(new Object[0]);
  }
  
  private static void lambda$new$0(uj paramuj) {
    long l1 = a ^ 0x5D0C649A0239L;
    long l2 = l1 ^ 0x41403D49D70CL;
    xw xw = (xw)d1.q(new Object[0]).x(new Object[0]).V(new Object[] { xw.class });
    (new Object[2])[1] = paramuj.B(new Object[0]);
    new Object[2];
    xw.P(new Object[] { Long.valueOf(l2) });
  }
  
  static {
    long l = a ^ 0x3100E502DB61L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b = 1; b < 8; b++)
      (new byte[8])[b] = (byte)(int)(l << b * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/NoPadding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\p_.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */