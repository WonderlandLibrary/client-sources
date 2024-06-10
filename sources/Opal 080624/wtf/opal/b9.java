package wtf.opal;

import java.lang.invoke.MethodHandles;
import java.text.DecimalFormat;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.class_310;

public final class b9 {
  public static final class_310 c;
  
  public static final String m;
  
  public static final String x;
  
  public static final String b;
  
  public static final String J;
  
  public static final DecimalFormat n;
  
  public static final DecimalFormat Z;
  
  private static String w;
  
  static {
    long l = on.a(-5441541256227026979L, -4253546861895106803L, MethodHandles.lookup().lookupClass()).a(19465438529875L) ^ 0x3983BECD95FL;
    if (V() == null)
      n("zgyCTc"); 
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[6];
    boolean bool = false;
    String str;
    int i = (str = "`PÍí½ýè\b·Qz\fL'a\buîZ-Ã«(l_Ì§PÙbþónEíÙx.Wß\030¿Ö(\026ØÇ¡ZnbyÍBÛ¦a_t,¼").length();
    byte b2 = 8;
    byte b = -1;
    while (true);
  }
  
  public static void n(String paramString) {
    w = paramString;
  }
  
  public static String V() {
    return w;
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\b9.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */