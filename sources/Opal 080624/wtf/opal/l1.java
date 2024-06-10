package wtf.opal;

import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public enum l1 {
  VANILLA, WATCHDOG, WATCHDOG_LOWHOP, WATCHDOG_LOWHOP_2, WATCHDOG_DYNAMIC, EXPERIMENTAL;
  
  private final String L;
  
  private static final l1[] j;
  
  private static final long a = on.a(1549261675122249337L, 2418468142392279695L, MethodHandles.lookup().lookupClass()).a(214946862310656L);
  
  private static final long b;
  
  l1(String paramString1) {
    this.L = paramString1;
  }
  
  public String toString() {
    return this.L;
  }
  
  private static l1[] r(Object[] paramArrayOfObject) {
    long l = ((Long)paramArrayOfObject[0]).longValue();
    l = a ^ l;
    return new l1[] { VANILLA, WATCHDOG, WATCHDOG_LOWHOP, WATCHDOG_LOWHOP_2, WATCHDOG_DYNAMIC, EXPERIMENTAL };
  }
  
  static {
    long l2 = a ^ 0x3C8B03E644C7L;
    long l3 = l2 ^ 0x20B76CD839D7L;
    (new byte[8])[0] = (byte)(int)(l2 >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l2 << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[12];
    boolean bool = false;
    String str;
    int i = (str = "üh\031üA\005Oz\020`iJ¾(üë.\025wØÒö·\030~\024057ð°\033Ù@\"ú@§°êÆ\003\r\020~\024057ð°\033á±ô\034%H9\020ê¿ed ½_bv=,\026Z}\"\024\020øé\"qpJ=¡ \n3\020ê¿ed ½_bX ©%\004Û\030ê¿ed ½_bþ\016Yë¶ð`Ç\005Í«Ø\034\004\030~\024057ð°\033Ùòï(gÚô?.C?\013\b\020~\024057ð°\033î°a\017°¼¤\016").length();
    byte b2 = 8;
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\l1.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */