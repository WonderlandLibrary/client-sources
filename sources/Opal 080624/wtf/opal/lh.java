package wtf.opal;

import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

enum lh {
  SIMPLE, OUTLINE, LEFT_BAR, RIGHT_BAR;
  
  private final String x;
  
  private static final lh[] W;
  
  lh(String paramString1) {
    this.x = paramString1;
  }
  
  public String toString() {
    return this.x;
  }
  
  private static lh[] c(Object[] paramArrayOfObject) {
    return new lh[] { SIMPLE, OUTLINE, LEFT_BAR, RIGHT_BAR };
  }
  
  static {
    long l = on.a(3803006461453615915L, 3833226280946792817L, MethodHandles.lookup().lookupClass()).a(206621006655553L) ^ 0x79BA3F7B5D71L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[8];
    boolean bool = false;
    String str;
    int i = (str = "Ïº6Þ×«[r=«F-µÎ\"\020\023i¼\037|ò\025HCpU#®\bPQ(<\034¯\bÚ+DTMNK\020\032ñÔ7g¾zVTUÛ É\bR\r}ÔIÏdÍ").length();
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\lh.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */