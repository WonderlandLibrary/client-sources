package wtf.opal;

import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public enum xk {
  CUBECRAFT, VERUS, WATCHDOG_BLINK_INV, WATCHDOG_INV;
  
  private final String G;
  
  private static final xk[] Q;
  
  xk(String paramString1) {
    this.G = paramString1;
  }
  
  public String toString() {
    return this.G;
  }
  
  private static xk[] t(Object[] paramArrayOfObject) {
    return new xk[] { CUBECRAFT, VERUS, WATCHDOG_BLINK_INV, WATCHDOG_INV };
  }
  
  static {
    long l = on.a(2748989886836163963L, 561324396164971927L, MethodHandles.lookup().lookupClass()).a(203453867174726L) ^ 0x26CC3BE17CCDL;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[8];
    boolean bool = false;
    String str;
    int i = (str = "8\007\03052ÌágGíÝq]àY\033Â\fz\020\023~ûüÚ-6ìh.æø\030\023~ûüÚ-6ì¨\b:Ð3\025±Z´r0+NrT\020wïª{p·Sa\031äÚ&CÔA\0208\007\03052Ìá\0266ç\fÎ\bbfÏöå#J").length();
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\xk.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */