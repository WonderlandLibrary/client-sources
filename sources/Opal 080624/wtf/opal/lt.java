package wtf.opal;

import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.class_276;
import net.minecraft.class_279;

public final class lt {
  public static class_279 s;
  
  public static class_276 t;
  
  public static boolean j;
  
  private static boolean H;
  
  private static final long a = on.a(-7744015703456910658L, -9113985169065611053L, MethodHandles.lookup().lookupClass()).a(10674504760286L);
  
  private static final String b;
  
  public static void L(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_1
    //   11: dup
    //   12: iconst_1
    //   13: aaload
    //   14: checkcast net/minecraft/class_5912
    //   17: astore_3
    //   18: pop
    //   19: getstatic wtf/opal/lt.a : J
    //   22: lload_1
    //   23: lxor
    //   24: lstore_1
    //   25: invokestatic u : ()Z
    //   28: istore #4
    //   30: getstatic wtf/opal/lt.s : Lnet/minecraft/class_279;
    //   33: iload #4
    //   35: ifne -> 51
    //   38: ifnull -> 54
    //   41: goto -> 48
    //   44: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   47: athrow
    //   48: getstatic wtf/opal/lt.s : Lnet/minecraft/class_279;
    //   51: invokevirtual close : ()V
    //   54: lload_1
    //   55: lconst_0
    //   56: lcmp
    //   57: iflt -> 163
    //   60: getstatic wtf/opal/lt.t : Lnet/minecraft/class_276;
    //   63: ifnonnull -> 105
    //   66: new net/minecraft/class_6367
    //   69: dup
    //   70: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   73: invokevirtual method_22683 : ()Lnet/minecraft/class_1041;
    //   76: invokevirtual method_4489 : ()I
    //   79: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   82: invokevirtual method_22683 : ()Lnet/minecraft/class_1041;
    //   85: invokevirtual method_4506 : ()I
    //   88: iconst_0
    //   89: getstatic net/minecraft/class_310.field_1703 : Z
    //   92: invokespecial <init> : (IIZZ)V
    //   95: putstatic wtf/opal/lt.t : Lnet/minecraft/class_276;
    //   98: goto -> 105
    //   101: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   104: athrow
    //   105: iconst_1
    //   106: putstatic wtf/opal/lt.j : Z
    //   109: new net/minecraft/class_279
    //   112: dup
    //   113: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   116: invokevirtual method_1531 : ()Lnet/minecraft/class_1060;
    //   119: aload_3
    //   120: getstatic wtf/opal/lt.t : Lnet/minecraft/class_276;
    //   123: new net/minecraft/class_2960
    //   126: dup
    //   127: getstatic wtf/opal/lt.b : Ljava/lang/String;
    //   130: invokespecial <init> : (Ljava/lang/String;)V
    //   133: invokespecial <init> : (Lnet/minecraft/class_1060;Lnet/minecraft/class_5912;Lnet/minecraft/class_276;Lnet/minecraft/class_2960;)V
    //   136: putstatic wtf/opal/lt.s : Lnet/minecraft/class_279;
    //   139: getstatic wtf/opal/lt.s : Lnet/minecraft/class_279;
    //   142: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   145: invokevirtual method_22683 : ()Lnet/minecraft/class_1041;
    //   148: invokevirtual method_4489 : ()I
    //   151: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   154: invokevirtual method_22683 : ()Lnet/minecraft/class_1041;
    //   157: invokevirtual method_4506 : ()I
    //   160: invokevirtual method_1259 : (II)V
    //   163: goto -> 173
    //   166: astore #5
    //   168: aload #5
    //   170: invokevirtual printStackTrace : ()V
    //   173: return
    // Exception table:
    //   from	to	target	type
    //   30	41	44	java/io/IOException
    //   54	98	101	java/io/IOException
    //   105	163	166	java/io/IOException
  }
  
  public static void k(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Integer
    //   7: invokevirtual intValue : ()I
    //   10: istore #4
    //   12: dup
    //   13: iconst_1
    //   14: aaload
    //   15: checkcast java/lang/Long
    //   18: invokevirtual longValue : ()J
    //   21: lstore_1
    //   22: dup
    //   23: iconst_2
    //   24: aaload
    //   25: checkcast java/lang/Integer
    //   28: invokevirtual intValue : ()I
    //   31: istore_3
    //   32: pop
    //   33: getstatic wtf/opal/lt.a : J
    //   36: lload_1
    //   37: lxor
    //   38: lstore_1
    //   39: invokestatic h : ()Z
    //   42: istore #5
    //   44: getstatic wtf/opal/lt.s : Lnet/minecraft/class_279;
    //   47: iload #5
    //   49: ifeq -> 65
    //   52: ifnull -> 71
    //   55: goto -> 62
    //   58: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   61: athrow
    //   62: getstatic wtf/opal/lt.s : Lnet/minecraft/class_279;
    //   65: iload #4
    //   67: iload_3
    //   68: invokevirtual method_1259 : (II)V
    //   71: getstatic wtf/opal/lt.t : Lnet/minecraft/class_276;
    //   74: iload #5
    //   76: lload_1
    //   77: lconst_0
    //   78: lcmp
    //   79: ifle -> 100
    //   82: ifeq -> 98
    //   85: ifnull -> 107
    //   88: goto -> 95
    //   91: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   94: athrow
    //   95: getstatic wtf/opal/lt.t : Lnet/minecraft/class_276;
    //   98: iload #4
    //   100: iload_3
    //   101: getstatic net/minecraft/class_310.field_1703 : Z
    //   104: invokevirtual method_1234 : (IIZ)V
    //   107: return
    // Exception table:
    //   from	to	target	type
    //   44	55	58	wtf/opal/x5
    //   71	88	91	wtf/opal/x5
  }
  
  public static void d(boolean paramBoolean) {
    H = paramBoolean;
  }
  
  public static boolean u() {
    return H;
  }
  
  public static boolean h() {
    boolean bool = u();
    try {
      if (!bool)
        return true; 
    } catch (x5 x5) {
      throw a(null);
    } 
    return false;
  }
  
  static {
    long l = a ^ 0xF9C527E0989L;
    d(false);
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b = 1; b < 8; b++)
      (new byte[8])[b] = (byte)(int)(l << b * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
  }
  
  private static Exception a(Exception paramException) {
    return paramException;
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\lt.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */