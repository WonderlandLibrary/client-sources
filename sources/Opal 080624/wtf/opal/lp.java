package wtf.opal;

import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public enum lp {
  FORWARDS, BACKWARDS;
  
  private static final long a = on.a(-4581892724130315125L, 3523181212739577124L, MethodHandles.lookup().lookupClass()).a(210027412387274L);
  
  public final lp U(Object[] paramArrayOfObject) {
    long l = ((Long)paramArrayOfObject[0]).longValue();
    l = a ^ l;
    d[] arrayOfD = dx.F();
    try {
      if (arrayOfD == null)
        try {
        
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    return (this == FORWARDS) ? BACKWARDS : FORWARDS;
  }
  
  public final boolean b(Object[] paramArrayOfObject) {
    long l = ((Long)paramArrayOfObject[0]).longValue();
    l = a ^ l;
    try {
    
    } catch (x5 x5) {
      throw a(null);
    } 
    return (this == FORWARDS);
  }
  
  public final boolean A(Object[] paramArrayOfObject) {
    long l = ((Long)paramArrayOfObject[0]).longValue();
    l = a ^ l;
    try {
    
    } catch (x5 x5) {
      throw a(null);
    } 
    return (this == BACKWARDS);
  }
  
  static {
    long l = a ^ 0x22DBE6D4A369L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[2];
    boolean bool = false;
    String str;
    int i = (str = "ñ%§ÕDjRÕß;ÔÙÐ\020å\025.ÌÂÜï¥b\031K°i½#L").length();
    byte b2 = 16;
    byte b = -1;
    while (true);
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\lp.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */