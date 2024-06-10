package wtf.opal;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public final class j4 extends d {
  public final ky<km> t;
  
  private static boolean T;
  
  private static final long a = on.a(-8112093514963671279L, -5355788367599236737L, MethodHandles.lookup().lookupClass()).a(175557274648249L);
  
  private static final String[] b;
  
  private static final String[] d;
  
  private static final Map f = new HashMap<>(13);
  
  public j4(long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/j4.a : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: lload_1
    //   7: dup2
    //   8: ldc2_w 22824132630906
    //   11: lxor
    //   12: lstore_3
    //   13: dup2
    //   14: ldc2_w 64582251773999
    //   17: lxor
    //   18: lstore #5
    //   20: dup2
    //   21: ldc2_w 78587023754932
    //   24: lxor
    //   25: lstore #7
    //   27: dup2
    //   28: ldc2_w 83412850900705
    //   31: lxor
    //   32: lstore #9
    //   34: pop2
    //   35: aload_0
    //   36: sipush #29575
    //   39: ldc2_w 3825743073591502191
    //   42: lload_1
    //   43: lxor
    //   44: <illegal opcode> t : (IJ)Ljava/lang/String;
    //   49: lload #5
    //   51: sipush #24264
    //   54: ldc2_w 2094682075151566882
    //   57: lload_1
    //   58: lxor
    //   59: <illegal opcode> t : (IJ)Ljava/lang/String;
    //   64: getstatic wtf/opal/kn.PLAYER : Lwtf/opal/kn;
    //   67: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   70: aload_0
    //   71: new wtf/opal/ky
    //   74: dup
    //   75: sipush #29861
    //   78: ldc2_w 3518673618950431310
    //   81: lload_1
    //   82: lxor
    //   83: <illegal opcode> t : (IJ)Ljava/lang/String;
    //   88: aload_0
    //   89: getstatic wtf/opal/km.BLINK : Lwtf/opal/km;
    //   92: invokespecial <init> : (Ljava/lang/String;Lwtf/opal/d;Ljava/lang/Enum;)V
    //   95: putfield t : Lwtf/opal/ky;
    //   98: aload_0
    //   99: aload_0
    //   100: getfield t : Lwtf/opal/ky;
    //   103: iconst_4
    //   104: anewarray wtf/opal/u_
    //   107: dup
    //   108: iconst_0
    //   109: new wtf/opal/pc
    //   112: dup
    //   113: lload #7
    //   115: aload_0
    //   116: invokespecial <init> : (JLwtf/opal/j4;)V
    //   119: aastore
    //   120: dup
    //   121: iconst_1
    //   122: new wtf/opal/pw
    //   125: dup
    //   126: aload_0
    //   127: invokespecial <init> : (Lwtf/opal/j4;)V
    //   130: aastore
    //   131: dup
    //   132: iconst_2
    //   133: new wtf/opal/pn
    //   136: dup
    //   137: lload #9
    //   139: aload_0
    //   140: invokespecial <init> : (JLwtf/opal/j4;)V
    //   143: aastore
    //   144: dup
    //   145: iconst_3
    //   146: new wtf/opal/pj
    //   149: dup
    //   150: aload_0
    //   151: invokespecial <init> : (Lwtf/opal/j4;)V
    //   154: aastore
    //   155: iconst_2
    //   156: anewarray java/lang/Object
    //   159: dup_x1
    //   160: swap
    //   161: iconst_1
    //   162: swap
    //   163: aastore
    //   164: dup_x1
    //   165: swap
    //   166: iconst_0
    //   167: swap
    //   168: aastore
    //   169: invokevirtual Y : ([Ljava/lang/Object;)V
    //   172: aload_0
    //   173: iconst_1
    //   174: anewarray wtf/opal/k3
    //   177: dup
    //   178: iconst_0
    //   179: aload_0
    //   180: getfield t : Lwtf/opal/ky;
    //   183: aastore
    //   184: lload_3
    //   185: dup2_x1
    //   186: pop2
    //   187: iconst_2
    //   188: anewarray java/lang/Object
    //   191: dup_x1
    //   192: swap
    //   193: iconst_1
    //   194: swap
    //   195: aastore
    //   196: dup_x2
    //   197: dup_x2
    //   198: pop
    //   199: invokestatic valueOf : (J)Ljava/lang/Long;
    //   202: iconst_0
    //   203: swap
    //   204: aastore
    //   205: invokevirtual o : ([Ljava/lang/Object;)V
    //   208: return
  }
  
  public String o(Object[] paramArrayOfObject) {
    long l = ((Long)paramArrayOfObject[0]).longValue();
    return ((km)this.t.z()).toString();
  }
  
  public static void W(boolean paramBoolean) {
    T = paramBoolean;
  }
  
  public static boolean n() {
    return T;
  }
  
  public static boolean k() {
    boolean bool = n();
    try {
      if (!bool)
        return true; 
    } catch (x5 x5) {
      throw a(null);
    } 
    return false;
  }
  
  static {
    W(false);
    long l = a ^ 0x278FE93CDFF4L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[3];
    boolean bool = false;
    String str;
    int i = (str = "ce_ÜmÕ\035-|\033übMÄ2\025]í}\025#ÈÁ\036Q\r\rP\020ý\020CF%\000äN¸Iô\004ÍmP\021\020¶â!{&s9ÁÛ<úIS").length();
    byte b2 = 32;
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
  
  private static String a(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0xCAC;
    if (d[i] == null) {
      Object[] arrayOfObject;
      try {
        Long long_ = Long.valueOf(Thread.currentThread().threadId());
        arrayOfObject = (Object[])f.get(long_);
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/PKCS5Padding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          f.put(long_, arrayOfObject);
        } 
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/j4", exception);
      } 
      byte[] arrayOfByte1 = new byte[8];
      arrayOfByte1[0] = (byte)(int)(paramLong >>> 56L);
      for (byte b = 1; b < 8; b++)
        arrayOfByte1[b] = (byte)(int)(paramLong << b * 8 >>> 56L); 
      DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
      SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
      ((Cipher)arrayOfObject[0]).init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
      byte[] arrayOfByte2 = b[i].getBytes("ISO-8859-1");
      d[i] = a(((Cipher)arrayOfObject[0]).doFinal(arrayOfByte2));
    } 
    return d[i];
  }
  
  private static Object a(MethodHandles.Lookup paramLookup, MutableCallSite paramMutableCallSite, String paramString, Object[] paramArrayOfObject) {
    int i = ((Integer)paramArrayOfObject[0]).intValue();
    long l = ((Long)paramArrayOfObject[1]).longValue();
    String str = a(i, l);
    MethodHandle methodHandle = MethodHandles.constant(String.class, str);
    paramMutableCallSite.setTarget(MethodHandles.dropArguments(methodHandle, 0, new Class[] { int.class, long.class }));
    return str;
  }
  
  private static CallSite a(MethodHandles.Lookup paramLookup, String paramString, MethodType paramMethodType) {
    // Byte code:
    //   0: new java/lang/invoke/MutableCallSite
    //   3: dup
    //   4: aload_2
    //   5: invokespecial <init> : (Ljava/lang/invoke/MethodType;)V
    //   8: astore_3
    //   9: aload_3
    //   10: ldc_w
    //   13: ldc_w [Ljava/lang/Object;
    //   16: aload_2
    //   17: invokevirtual parameterCount : ()I
    //   20: invokevirtual asCollector : (Ljava/lang/Class;I)Ljava/lang/invoke/MethodHandle;
    //   23: iconst_0
    //   24: iconst_3
    //   25: anewarray java/lang/Object
    //   28: dup
    //   29: iconst_0
    //   30: aload_0
    //   31: aastore
    //   32: dup
    //   33: iconst_1
    //   34: aload_3
    //   35: aastore
    //   36: dup
    //   37: iconst_2
    //   38: aload_1
    //   39: aastore
    //   40: invokestatic insertArguments : (Ljava/lang/invoke/MethodHandle;I[Ljava/lang/Object;)Ljava/lang/invoke/MethodHandle;
    //   43: aload_2
    //   44: invokestatic explicitCastArguments : (Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;
    //   47: invokevirtual setTarget : (Ljava/lang/invoke/MethodHandle;)V
    //   50: goto -> 104
    //   53: astore #4
    //   55: new java/lang/RuntimeException
    //   58: dup
    //   59: new java/lang/StringBuilder
    //   62: dup
    //   63: invokespecial <init> : ()V
    //   66: ldc_w 'wtf/opal/j4'
    //   69: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   72: ldc_w ' : '
    //   75: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   78: aload_1
    //   79: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   82: ldc_w ' : '
    //   85: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   88: aload_2
    //   89: invokevirtual toString : ()Ljava/lang/String;
    //   92: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   95: invokevirtual toString : ()Ljava/lang/String;
    //   98: aload #4
    //   100: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   103: athrow
    //   104: aload_3
    //   105: areturn
    // Exception table:
    //   from	to	target	type
    //   9	50	53	java/lang/Exception
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\j4.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */