package wtf.opal;

import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public enum l0 {
  WATCHDOG, INSTANT_WATCHDOG, EXPERIMENTAL, FIREBALL;
  
  private final String K;
  
  private static final l0[] W;
  
  l0(String paramString1) {
    this.K = paramString1;
  }
  
  public String toString() {
    return this.K;
  }
  
  private static l0[] l(Object[] paramArrayOfObject) {
    return new l0[] { WATCHDOG, INSTANT_WATCHDOG, EXPERIMENTAL, FIREBALL };
  }
  
  static {
    long l = on.a(-6759932894449098523L, 8534539177305764391L, MethodHandles.lookup().lookupClass()).a(42837448451989L) ^ 0x4706119A8F2BL;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[8];
    boolean bool = false;
    String str;
    int i = (str = "\013K,«\bí_ÚÙù\001rÛ¸Ý×\\¸\030í\t\034¦¦`âïI\021¶\buØcUm\003\020:ZÏ,{rßÔþ\021ÏN\020q;¾8}\tÞqSëO¥\fu\022­\020\nf¶V»\036¬\023\030¨\023ui£\002\020;?t³PÞ\023¤1;J>¥Ý").length();
    byte b2 = 24;
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\l0.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */