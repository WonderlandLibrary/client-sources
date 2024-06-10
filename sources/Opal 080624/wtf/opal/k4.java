package wtf.opal;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

class k4 extends kv {
  private final String Y;
  
  private static final long a = on.a(4874040945251823707L, -5504969194386118245L, MethodHandles.lookup().lookupClass()).a(266219733305508L);
  
  private static final String g;
  
  k4(String paramString, long paramLong) {
    int i = lq.O();
    try {
      if (i != 0) {
        try {
          if (paramString == null)
            throw new NullPointerException(g); 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        } 
        this.Y = paramString;
      } 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
  }
  
  void x(Object[] paramArrayOfObject) throws IOException {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    l6 l6 = (l6)paramArrayOfObject[1];
    long l2 = l1 ^ 0x373B431FF3CCL;
    new Object[2];
    l6.S(new Object[] { null, Long.valueOf(l2), this.Y });
  }
  
  public boolean l(Object[] paramArrayOfObject) {
    return true;
  }
  
  public String F() {
    return this.Y;
  }
  
  public int hashCode() {
    return this.Y.hashCode();
  }
  
  public boolean equals(Object paramObject) {
    long l = a ^ 0x5644D2BD94BCL;
    int i = lq.O();
    try {
      if (i != 0)
        try {
          if (this == paramObject)
            return true; 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        }  
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (paramObject == null)
        return false; 
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    try {
      if (i != 0)
        try {
          if (getClass() != paramObject.getClass())
            return false; 
        } catch (NullPointerException nullPointerException) {
          throw a(null);
        }  
    } catch (NullPointerException nullPointerException) {
      throw a(null);
    } 
    k4 k41 = (k4)paramObject;
    return this.Y.equals(k41.Y);
  }
  
  static {
    long l = a ^ 0x3F00622469DBL;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b = 1; b < 8; b++)
      (new byte[8])[b] = (byte)(int)(l << b * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
  }
  
  private static NullPointerException a(NullPointerException paramNullPointerException) {
    return paramNullPointerException;
  }
  
  private static String b(byte[] paramArrayOfbyte) {
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\k4.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */