package wtf.opal;

import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public enum km {
  BLINK, NO_GROUND, COLLISION, WATCHDOG;
  
  private final String R;
  
  private static final km[] l;
  
  km(String paramString1) {
    this.R = paramString1;
  }
  
  public String toString() {
    return this.R;
  }
  
  private static km[] t(Object[] paramArrayOfObject) {
    return new km[] { BLINK, NO_GROUND, COLLISION, WATCHDOG };
  }
  
  static {
    long l = on.a(6212810879608649377L, -8870312388895676007L, MethodHandles.lookup().lookupClass()).a(2592941955441L) ^ 0x40959CDC3FC9L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[8];
    boolean bool = false;
    String str;
    int i = (str = "\036\000EÊºÂYPê¡pÒ¬*i\020\020\003L+/eÕÎ±g©u©£\020W×ÄQúoý+ûcØ\034Z(\"\bãCÙÙ\007T\bpr\".ò\013þV\020¯\004¾tPÅ¦\020®0/Æ").length();
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\km.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */