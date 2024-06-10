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

public final class jj extends d {
  private final ky<k> X;
  
  public final kt w;
  
  private static final long a = on.a(-290806571363824708L, -8666275625413882748L, MethodHandles.lookup().lookupClass()).a(3267454057308L);
  
  private static final String[] b;
  
  private static final String[] d;
  
  private static final Map f = new HashMap<>(13);
  
  public jj(long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/jj.a : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: lload_1
    //   7: dup2
    //   8: ldc2_w 55503908796255
    //   11: lxor
    //   12: lstore_3
    //   13: dup2
    //   14: ldc2_w 30810228403722
    //   17: lxor
    //   18: lstore #5
    //   20: dup2
    //   21: ldc2_w 19822465089137
    //   24: lxor
    //   25: lstore #7
    //   27: pop2
    //   28: aload_0
    //   29: sipush #6504
    //   32: ldc2_w 8876616426929592931
    //   35: lload_1
    //   36: lxor
    //   37: <illegal opcode> r : (IJ)Ljava/lang/String;
    //   42: lload #5
    //   44: sipush #11057
    //   47: ldc2_w 6345630193321781305
    //   50: lload_1
    //   51: lxor
    //   52: <illegal opcode> r : (IJ)Ljava/lang/String;
    //   57: getstatic wtf/opal/kn.MOVEMENT : Lwtf/opal/kn;
    //   60: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   63: aload_0
    //   64: new wtf/opal/ky
    //   67: dup
    //   68: sipush #7784
    //   71: ldc2_w 3207634976082533729
    //   74: lload_1
    //   75: lxor
    //   76: <illegal opcode> r : (IJ)Ljava/lang/String;
    //   81: aload_0
    //   82: getstatic wtf/opal/k.MOTION : Lwtf/opal/k;
    //   85: invokespecial <init> : (Ljava/lang/String;Lwtf/opal/d;Ljava/lang/Enum;)V
    //   88: putfield X : Lwtf/opal/ky;
    //   91: aload_0
    //   92: new wtf/opal/kt
    //   95: dup
    //   96: sipush #21661
    //   99: ldc2_w 6291048582842177431
    //   102: lload_1
    //   103: lxor
    //   104: <illegal opcode> r : (IJ)Ljava/lang/String;
    //   109: dconst_1
    //   110: dconst_1
    //   111: ldc2_w 10.0
    //   114: dconst_1
    //   115: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   118: putfield w : Lwtf/opal/kt;
    //   121: aload_0
    //   122: iconst_2
    //   123: anewarray wtf/opal/k3
    //   126: dup
    //   127: iconst_0
    //   128: aload_0
    //   129: getfield X : Lwtf/opal/ky;
    //   132: aastore
    //   133: dup
    //   134: iconst_1
    //   135: aload_0
    //   136: getfield w : Lwtf/opal/kt;
    //   139: aastore
    //   140: lload_3
    //   141: dup2_x1
    //   142: pop2
    //   143: iconst_2
    //   144: anewarray java/lang/Object
    //   147: dup_x1
    //   148: swap
    //   149: iconst_1
    //   150: swap
    //   151: aastore
    //   152: dup_x2
    //   153: dup_x2
    //   154: pop
    //   155: invokestatic valueOf : (J)Ljava/lang/Long;
    //   158: iconst_0
    //   159: swap
    //   160: aastore
    //   161: invokevirtual o : ([Ljava/lang/Object;)V
    //   164: aload_0
    //   165: aload_0
    //   166: getfield X : Lwtf/opal/ky;
    //   169: iconst_1
    //   170: anewarray wtf/opal/u_
    //   173: dup
    //   174: iconst_0
    //   175: new wtf/opal/ps
    //   178: dup
    //   179: aload_0
    //   180: lload #7
    //   182: invokespecial <init> : (Lwtf/opal/jj;J)V
    //   185: aastore
    //   186: iconst_2
    //   187: anewarray java/lang/Object
    //   190: dup_x1
    //   191: swap
    //   192: iconst_1
    //   193: swap
    //   194: aastore
    //   195: dup_x1
    //   196: swap
    //   197: iconst_0
    //   198: swap
    //   199: aastore
    //   200: invokevirtual Y : ([Ljava/lang/Object;)V
    //   203: return
  }
  
  static {
    long l = a ^ 0x7B72ACBCCCE4L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[4];
    boolean bool = false;
    String str;
    int i = (str = "\n\034\024æ\031ý5{>\002ù\016E3\020ø\017Ù# ¯\"Íh´rzéZâ»").length();
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
  
  private static String a(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x7768;
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
        throw new RuntimeException("wtf/opal/jj", exception);
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
    //   13: ldc [Ljava/lang/Object;
    //   15: aload_2
    //   16: invokevirtual parameterCount : ()I
    //   19: invokevirtual asCollector : (Ljava/lang/Class;I)Ljava/lang/invoke/MethodHandle;
    //   22: iconst_0
    //   23: iconst_3
    //   24: anewarray java/lang/Object
    //   27: dup
    //   28: iconst_0
    //   29: aload_0
    //   30: aastore
    //   31: dup
    //   32: iconst_1
    //   33: aload_3
    //   34: aastore
    //   35: dup
    //   36: iconst_2
    //   37: aload_1
    //   38: aastore
    //   39: invokestatic insertArguments : (Ljava/lang/invoke/MethodHandle;I[Ljava/lang/Object;)Ljava/lang/invoke/MethodHandle;
    //   42: aload_2
    //   43: invokestatic explicitCastArguments : (Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;
    //   46: invokevirtual setTarget : (Ljava/lang/invoke/MethodHandle;)V
    //   49: goto -> 102
    //   52: astore #4
    //   54: new java/lang/RuntimeException
    //   57: dup
    //   58: new java/lang/StringBuilder
    //   61: dup
    //   62: invokespecial <init> : ()V
    //   65: ldc 'wtf/opal/jj'
    //   67: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   70: ldc_w ' : '
    //   73: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   76: aload_1
    //   77: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   80: ldc_w ' : '
    //   83: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   86: aload_2
    //   87: invokevirtual toString : ()Ljava/lang/String;
    //   90: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   93: invokevirtual toString : ()Ljava/lang/String;
    //   96: aload #4
    //   98: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   101: athrow
    //   102: aload_3
    //   103: areturn
    // Exception table:
    //   from	to	target	type
    //   9	49	52	java/lang/Exception
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\jj.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */