package wtf.opal;

import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

enum ll {
  VANILLA, WATCHDOG, WATCHDOG2, WATCHDOGJUMP, WATCHDOGJUMP2;
  
  private final String J;
  
  private static final ll[] p;
  
  ll(String paramString1) {
    this.J = paramString1;
  }
  
  public String toString() {
    return this.J;
  }
  
  private static ll[] M(Object[] paramArrayOfObject) {
    return new ll[] { VANILLA, WATCHDOG, WATCHDOG2, WATCHDOGJUMP, WATCHDOGJUMP2 };
  }
  
  static {
    long l = on.a(3978914816955982123L, -8732467332888551712L, MethodHandles.lookup().lookupClass()).a(174881783262093L) ^ 0x523C6B2030C7L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[10];
    boolean bool = false;
    String str;
    int i = (str = "ìÒÄÊØ\f­\013\\ÀÒ\020$\027L[¯'¡üy¾¤ñEòS\020ìÒÄÊØÙ\035\032ËRs\020$\027L[¯'¡X8a'µñZ\020ìÒÄÊØø\022w6½_Q÷\b}ÐÔ3.\003f\020ìÒÄÊØ`O[S 1\bi<\033­`X").length();
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\ll.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */