package wtf.opal;

import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public enum ki {
  ADDITION, IMPROVEMENT_OR_FIX, REMOVED;
  
  private final String Y;
  
  private final char f;
  
  ki(String paramString1, char paramChar) {
    this.Y = paramString1;
    this.f = paramChar;
  }
  
  public String toString() {
    return this.Y;
  }
  
  public char C(Object[] paramArrayOfObject) {
    return this.f;
  }
  
  static {
    long l = on.a(-7740475864972485127L, -3397860555661604234L, MethodHandles.lookup().lookupClass()).a(40216712863454L) ^ 0x4EA1DA038102L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[6];
    boolean bool = false;
    String str;
    int i = (str = "±à\035J\027\016eLûOØ^Ä]\b»êýMÆE\030\020ï\031b2ì¨ôî=\030)ÕASÛæ¿è\020!½K\023\bmO\fËru¢RæG").length();
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\ki.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */