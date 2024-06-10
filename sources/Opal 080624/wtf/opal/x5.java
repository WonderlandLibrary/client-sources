package wtf.opal;

import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class x5 extends RuntimeException {
  private final l9 M;
  
  private static final long a = on.a(2713156639209743319L, 8214465520813834892L, MethodHandles.lookup().lookupClass()).a(154044015741092L);
  
  private static final String b;
  
  x5(String paramString, int paramInt1, short paramShort, int paramInt2, l9 paraml9) {
    super(paramString + paramString + b);
    this.M = paraml9;
  }
  
  public l9 Q(Object[] paramArrayOfObject) {
    return this.M;
  }
  
  @Deprecated
  public int E(Object[] paramArrayOfObject) {
    return this.M.D;
  }
  
  @Deprecated
  public int Q(Object[] paramArrayOfObject) {
    return this.M.s;
  }
  
  @Deprecated
  public int b(Object[] paramArrayOfObject) {
    return this.M.l;
  }
  
  static {
    long l = a ^ 0x657C47DF5CFFL;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b = 1; b < 8; b++)
      (new byte[8])[b] = (byte)(int)(l << b * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\x5.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */