package wtf.opal;

import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.class_2596;
import net.minecraft.class_2815;

public final class u8 extends u_<o> {
  private final kr R = new kr();
  
  private final kt m;
  
  private final gm<lz> Y;
  
  private final gm<lb> F;
  
  private static d[] i;
  
  private static final long a = on.a(7518749540079066241L, -6879583718438139182L, MethodHandles.lookup().lookupClass()).a(110347571335914L);
  
  private static final String b;
  
  public u8(long paramLong, o paramo) {
    super(paramo);
    d[] arrayOfD = e();
    try {
      this.m = new kt(b, this, 400.0D, 100.0D, 1000.0D, 50.0D, l);
      this.Y = this::lambda$new$0;
      this.F = u8::lambda$new$1;
      if (d.D() != null)
        X(new d[4]); 
    } catch (x5 x5) {
      throw b(null);
    } 
  }
  
  public Enum V(Object[] paramArrayOfObject) {
    return xk.WATCHDOG_INV;
  }
  
  private static void lambda$new$1(lb paramlb) {
    long l = a ^ 0x660F3525FA1L;
    class_2596 class_2596 = paramlb.J(new Object[0]);
    d[] arrayOfD = e();
    try {
      if (arrayOfD != null)
        try {
          if (class_2596 instanceof class_2815) {
          
          } else {
            return;
          } 
        } catch (x5 x5) {
          throw b(null);
        }  
    } catch (x5 x5) {
      throw b(null);
    } 
    class_2815 class_2815 = (class_2815)class_2596;
  }
  
  private void lambda$new$0(lz paramlz) {
    long l1 = a ^ 0x1A580A66A32EL;
    long l2 = l1 ^ 0x6DF49901DC3DL;
    long l3 = l1 ^ 0x29C60E79DB9L;
    d[] arrayOfD = e();
    try {
      (new Object[3])[2] = Boolean.valueOf(true);
      new Object[3];
      (new Object[3])[1] = Long.valueOf(l2);
      new Object[3];
      if (arrayOfD != null)
        try {
          if (this.R.v(new Object[] { Long.valueOf(this.m.z().longValue()) })) {
          
          } else {
            return;
          } 
        } catch (x5 x5) {
          throw b(null);
        }  
    } catch (x5 x5) {
      throw b(null);
    } 
    try {
      if (arrayOfD != null)
        try {
          if (!this.R.v(new Object[] { Long.valueOf(this.m.z().longValue()) })) {
            new Object[1];
          } else {
            return;
          } 
        } catch (x5 x5) {
          throw b(null);
        }  
    } catch (x5 x5) {
      throw b(null);
    } 
    try {
      if (this.R.v(new Object[] { Long.valueOf(this.m.z().longValue()) }))
        b9.c.method_1562().method_52787((class_2596)new class_2815(b9.c.field_1724.field_7512.field_7763)); 
    } catch (x5 x5) {
      throw b(null);
    } 
  }
  
  public static void X(d[] paramArrayOfd) {
    i = paramArrayOfd;
  }
  
  public static d[] e() {
    return i;
  }
  
  static {
    long l = a ^ 0x2C7C0C41D274L;
    X(new d[4]);
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b = 1; b < 8; b++)
      (new byte[8])[b] = (byte)(int)(l << b * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
  }
  
  private static x5 b(x5 paramx5) {
    return paramx5;
  }
  
  private static String a(byte[] paramArrayOfbyte) {
    byte b1 = 0;
    int i;
    char[] arrayOfChar = new char[i = paramArrayOfbyte.length];
    for (byte b2 = 0; b2 < i; b2++) {
      int j;
      if ((j = 0xFF & paramArrayOfbyte[b2]) < 192) {
        arrayOfChar[b1++] = (char)j;
      } else if (j < 224) {
        char c = (char)((char)(j & 0x1F) << 6);
        j = paramArrayOfbyte[++b2];
        c = (char)(c | (char)(j & 0x3F));
        arrayOfChar[b1++] = c;
      } else if (b2 < i - 2) {
        char c = (char)((char)(j & 0xF) << 12);
        j = paramArrayOfbyte[++b2];
        c = (char)(c | (char)(j & 0x3F) << 6);
        j = paramArrayOfbyte[++b2];
        c = (char)(c | (char)(j & 0x3F));
        arrayOfChar[b1++] = c;
      } 
    } 
    return new String(arrayOfChar, 0, b1);
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opa\\u8.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */