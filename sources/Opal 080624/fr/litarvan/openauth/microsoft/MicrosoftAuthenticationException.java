package fr.litarvan.openauth.microsoft;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import wtf.opal.on;
import wtf.opal.x5;

public class MicrosoftAuthenticationException extends Exception {
  private static boolean v;
  
  private static final long a = on.a(7370182547344394804L, -5646903046259038818L, MethodHandles.lookup().lookupClass()).a(265903663803031L);
  
  private static final String b;
  
  public MicrosoftAuthenticationException(String paramString) {
    super(paramString);
  }
  
  public MicrosoftAuthenticationException(IOException paramIOException) {
    super(b, paramIOException);
  }
  
  public MicrosoftAuthenticationException(Throwable paramThrowable) {
    super(paramThrowable);
  }
  
  public static void m(boolean paramBoolean) {
    v = paramBoolean;
  }
  
  public static boolean E() {
    return v;
  }
  
  public static boolean g() {
    boolean bool = E();
    try {
      if (!bool)
        return true; 
    } catch (x5 x5) {
      throw a(null);
    } 
    return false;
  }
  
  static {
    long l = a ^ 0x2E079D0D4289L;
    m(false);
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b = 1; b < 8; b++)
      (new byte[8])[b] = (byte)(int)(l << b * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\fr\litarvan\openauth\microsoft\MicrosoftAuthenticationException.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */