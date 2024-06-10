package wtf.opal;

import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public enum bn {
  STRAFE, VANILLA, VERUS, WATCHDOG;
  
  private final String k;
  
  private static final bn[] P;
  
  bn(String paramString1) {
    this.k = paramString1;
  }
  
  public String toString() {
    return this.k;
  }
  
  private static bn[] q(Object[] paramArrayOfObject) {
    return new bn[] { STRAFE, VANILLA, VERUS, WATCHDOG };
  }
  
  static {
    long l = on.a(-6582085294302662520L, 395707226424408107L, MethodHandles.lookup().lookupClass()).a(193873343508201L) ^ 0x41E005770AF8L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[8];
    boolean bool = false;
    String str;
    int i = (str = "\002« nñ\024¶Jã\006§ÛMc\btpKQiIÑø\bë{n:\bØ@j\025[@\001\020a=2þ³p¥¯¦Ed\"à\032\bÇ©\f^»lÑ").length();
    byte b2 = 16;
    byte b = -1;
    while (true);
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\bn.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */