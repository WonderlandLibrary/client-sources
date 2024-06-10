package wtf.opal;

import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class ks {
  public static final String j = "d";
  
  public static final String Q;
  
  public static final String p;
  
  public static final String F = "t";
  
  public static final ks r;
  
  public static final ks a;
  
  public static final ks A;
  
  public static final ks f;
  
  public static final ks k;
  
  private final String u;
  
  private final String N;
  
  private final String V;
  
  private static String[] c;
  
  public ks(String paramString1, String paramString2, String paramString3) {
    this.u = paramString1;
    this.N = paramString2;
    this.V = paramString3;
  }
  
  public final String U(Object[] paramArrayOfObject) {
    return this.u;
  }
  
  public final String u(Object[] paramArrayOfObject) {
    return this.N;
  }
  
  public final String e(Object[] paramArrayOfObject) {
    return this.V;
  }
  
  static {
    long l = on.a(325759119994544356L, -2211531399146815336L, MethodHandles.lookup().lookupClass()).a(10317657470871L) ^ 0x5AC6AB7CB655L;
    if (A() != null)
      t(new String[4]); 
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[10];
    boolean bool = false;
    String str;
    int i = (str = "ÂP'Û\\IF5#CjzÀe\004Óf\003Ø¸<²n«\013Jö\rÁ\016\b)Ç\022lj£SJè(ýÒ IV¢â£}»&\004M\006â©¸Cn\026Æ¹úQ÷S\0261¤{¨²É!0ÂP'Û\\IF5#CjzÀe\004Óf\003Ø¸<²n«\013Jö\rÁ\016\b)Ç\022lj£SJè Új8e\bW¤*\003¶eÜzúI\036l¾ºrBû\002tT(>ü1ª|åU{õ½eë\021<-W}î\t\\Ø²\031%·>\030ÒîèÌ(Ð\f]Æ·Æ|G~\t!û¿÷1\020'­ÎjQb41Ï¥òõWk\007;K% Új8e\bW¤*\003¶eÜzúI\036l¾ºrBû\002tT(WM\020l\bÃ\025Ð¾_¤Vìî{F\\ü[<ÆÛù\004\001|LºûðP¶èl").length();
    byte b2 = 48;
    byte b = -1;
    while (true);
  }
  
  public static void t(String[] paramArrayOfString) {
    c = paramArrayOfString;
  }
  
  public static String[] A() {
    return c;
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\ks.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */