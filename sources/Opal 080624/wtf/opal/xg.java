package wtf.opal;

import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public enum xg {
  OPAL, ASTOLFO, EXHIBITION;
  
  private final String n;
  
  private static final xg[] X;
  
  xg(String paramString1) {
    this.n = paramString1;
  }
  
  public String toString() {
    return this.n;
  }
  
  private static xg[] w(Object[] paramArrayOfObject) {
    return new xg[] { OPAL, ASTOLFO, EXHIBITION };
  }
  
  static {
    long l = on.a(4964802104031756312L, -4342909449267890344L, MethodHandles.lookup().lookupClass()).a(136209800114532L) ^ 0x14814E999FAFL;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[6];
    boolean bool = false;
    String str;
    int i = (str = "\037y\000\034 Ó5\020·(îÎ[\":F¾vÒZ^d\bJÄ»G\023¸\030\020íùþ\001±6>VÄs¶píÈ\021\022").length();
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\xg.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */