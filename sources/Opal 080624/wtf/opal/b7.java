package wtf.opal;

import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

enum b7 {
  RANGE, HEALTH, MOST_ARMOR, LEAST_ARMOR, HURT_TIME, SMART;
  
  private final String x;
  
  private static final b7[] u;
  
  private static final long a = on.a(7431969007209458860L, -759993140130340418L, MethodHandles.lookup().lookupClass()).a(142992513313977L);
  
  private static final long b;
  
  b7(String paramString1) {
    this.x = paramString1;
  }
  
  public String toString() {
    return this.x;
  }
  
  private static b7[] M(Object[] paramArrayOfObject) {
    long l = ((Long)paramArrayOfObject[0]).longValue();
    l = a ^ l;
    return new b7[] { RANGE, HEALTH, MOST_ARMOR, LEAST_ARMOR, HURT_TIME, SMART };
  }
  
  static {
    long l1 = a ^ 0xD166AFCF7D4L;
    long l2 = l1 ^ 0x33C1F3C03B07L;
    (new byte[8])[0] = (byte)(int)(l1 >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l1 << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[12];
    boolean bool = false;
    String str;
    int i = (str = ">Ú\003tâh\024îÿlÙd\f\r?:\020B×Ü×É\005§|(Y\006ò1±å\020j<Í\030õ$\016p`[5\032#·\020n\007ð¹i\013-16\006Ñz=}\026\bª®×L9M\b?â@ Ê\b\022y*u\005ÁÚ\b5¶çH\0272Ââ\020°Ö\003CZK®\026ú=G\b.}?®(!\003a").length();
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\b7.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */