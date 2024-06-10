package fr.litarvan.openauth.model;

import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import wtf.opal.on;

public class AuthAgent {
  public static final AuthAgent MINECRAFT;
  
  public static final AuthAgent SCROLLS;
  
  private String name;
  
  private int version;
  
  public AuthAgent(String paramString, int paramInt) {
    this.name = paramString;
    this.version = paramInt;
  }
  
  public void setName(String paramString) {
    this.name = paramString;
  }
  
  public String getName() {
    return this.name;
  }
  
  public void setVersion(int paramInt) {
    this.version = paramInt;
  }
  
  public int getVersion() {
    return this.version;
  }
  
  static {
    long l = on.a(1258365672082188579L, 1447249268892012273L, MethodHandles.lookup().lookupClass()).a(38994542711264L) ^ 0x760C73D30ECDL;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[2];
    boolean bool = false;
    String str;
    int i = (str = "p¸;BÕ¾3ÞÃ!T\002÷­$\bðÀ\021gx").length();
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\fr\litarvan\openauth\model\AuthAgent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */