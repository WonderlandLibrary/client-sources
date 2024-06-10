package wtf.opal;

import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class k2 {
  private final xt Z;
  
  private final kj v;
  
  private final String Q;
  
  private String F;
  
  private int K;
  
  private static final long a = on.a(8142059378294694426L, 3741491495119444215L, MethodHandles.lookup().lookupClass()).a(95526512049132L);
  
  private static final String b;
  
  private static final long c;
  
  private k2(long paramLong, xt paramxt, kj paramkj, String paramString) {
    this.Z = paramxt;
    this.v = paramkj;
    this.Q = paramString;
    this.F = b;
    this.K = (int)c;
  }
  
  public k2 J(Object[] paramArrayOfObject) {
    String str = (String)paramArrayOfObject[0];
    this.F = str;
    return this;
  }
  
  public k2 Q(Object[] paramArrayOfObject) {
    int i = ((Integer)paramArrayOfObject[0]).intValue();
    this.K = i;
    return this;
  }
  
  public py O(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x34469F1C6B2DL;
    d[] arrayOfD = xt.P();
    try {
      if (d.D() != null)
        xt.K(new d[1]); 
    } catch (x5 x5) {
      throw a(null);
    } 
    return this.Z.B(new Object[] { new py(this.v, this.F, this.Q, l2, this.K) });
  }
  
  static {
    long l = a ^ 0x44E3F4E36D1FL;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b = 1; b < 8; b++)
      (new byte[8])[b] = (byte)(int)(l << b * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
  }
  
  private static x5 a(x5 paramx5) {
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\k2.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */