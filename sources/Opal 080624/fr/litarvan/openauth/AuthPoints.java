package fr.litarvan.openauth;

import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import wtf.opal.on;

public class AuthPoints {
  public static final AuthPoints NORMAL_AUTH_POINTS;
  
  private String authenticatePoint;
  
  private String refreshPoint;
  
  private String validatePoint;
  
  private String signoutPoint;
  
  private String invalidatePoint;
  
  public AuthPoints(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) {
    this.authenticatePoint = paramString1;
    this.refreshPoint = paramString2;
    this.validatePoint = paramString3;
    this.signoutPoint = paramString4;
    this.invalidatePoint = paramString5;
  }
  
  public String getAuthenticatePoint() {
    return this.authenticatePoint;
  }
  
  public String getRefreshPoint() {
    return this.refreshPoint;
  }
  
  public String getValidatePoint() {
    return this.validatePoint;
  }
  
  public String getSignoutPoint() {
    return this.signoutPoint;
  }
  
  public String getInvalidatePoint() {
    return this.invalidatePoint;
  }
  
  static {
    long l = on.a(-2017922213345545643L, -2122870653633174748L, MethodHandles.lookup().lookupClass()).a(85831942149063L) ^ 0x215E804E181AL;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[5];
    boolean bool = false;
    String str;
    int i = (str = "§j¢Å÷üÁ\n\020¤PPD\033Õßñµßµg+)\020bú'-ÕU{n\036\\,\027\037©").length();
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\fr\litarvan\openauth\AuthPoints.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */