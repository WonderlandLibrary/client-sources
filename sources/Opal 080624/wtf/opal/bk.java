package wtf.opal;

import com.google.common.base.Preconditions;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.class_286;
import net.minecraft.class_287;
import net.minecraft.class_291;

public final class bk {
  private static final long a = on.a(-5603902189625974722L, -7783463423470959813L, MethodHandles.lookup().lookupClass()).a(45515679689667L);
  
  private static final String b;
  
  public static void t(Object[] paramArrayOfObject) {
    class_287 class_287 = (class_287)paramArrayOfObject[0];
    class_286.method_43433(class_287.method_1326());
  }
  
  public static class_291 r(Object[] paramArrayOfObject) {
    class_287.class_7433 class_7433 = (class_287.class_7433)paramArrayOfObject[0];
    class_291.class_8555 class_8555 = (class_291.class_8555)paramArrayOfObject[1];
    class_291 class_291 = new class_291(class_8555);
    class_291.method_1353();
    class_291.method_1352(class_7433);
    class_291.method_1354();
    return class_291;
  }
  
  public static void j(Object[] paramArrayOfObject) {
    long l = ((Long)paramArrayOfObject[0]).longValue();
    class_287.class_7433 class_7433 = (class_287.class_7433)paramArrayOfObject[1];
    class_291 class_291 = (class_291)paramArrayOfObject[2];
    l = a ^ l;
    boolean bool = pu.P();
    try {
      if (bool)
        if (!class_291.method_43444()) {
        
        } else {
          Preconditions.checkArgument(false, b);
          class_291.method_1353();
          class_291.method_1352(class_7433);
          class_291.method_1354();
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  static {
    long l = a ^ 0x71055AF9D757L;
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\bk.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */