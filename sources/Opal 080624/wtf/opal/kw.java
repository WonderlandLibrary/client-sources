package wtf.opal;

import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

enum kw {
  COMPACT, SMALL, LARGE, ONETAP, GAMESENSE, OPEL;
  
  private final String e;
  
  private static final kw[] t;
  
  private static final long a = on.a(1162505548141520725L, -7801801383999183086L, MethodHandles.lookup().lookupClass()).a(184005396649756L);
  
  private static final long b;
  
  kw(String paramString1) {
    this.e = paramString1;
  }
  
  public String toString() {
    return this.e;
  }
  
  private static kw[] l(Object[] paramArrayOfObject) {
    long l = ((Long)paramArrayOfObject[0]).longValue();
    l = a ^ l;
    return new kw[] { COMPACT, SMALL, LARGE, ONETAP, GAMESENSE, OPEL };
  }
  
  static {
    long l1 = a ^ 0x1D518075B210L;
    long l2 = l1 ^ 0x7FED8DCCC65CL;
    (new byte[8])[0] = (byte)(int)(l1 >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l1 << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[12];
    boolean bool = false;
    String str;
    int i = (str = "Ï¨{£MwH\t\020òa\b\026wï`yâ÷ÃjØ_\b¡¤U\001\034V²\b»\002ò~w ª\b\f©WZ_àÀ\b°\032¾wu(K\b³å&?\bhyLþþ¥É\b¬\023_½¼ªäw\bì&\024#m¥\\B").length();
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\kw.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */