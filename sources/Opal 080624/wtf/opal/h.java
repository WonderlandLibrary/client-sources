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

public final class h extends d {
  public final ky<l0> T;
  
  private static final long a = on.a(-6488383909589151128L, 6932051570819005745L, MethodHandles.lookup().lookupClass()).a(42739536256022L);
  
  private static final String[] b;
  
  private static final String[] d;
  
  private static final Map f = new HashMap<>(13);
  
  public h(long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/h.a : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: lload_1
    //   7: dup2
    //   8: ldc2_w 116733032286115
    //   11: lxor
    //   12: lstore_3
    //   13: dup2
    //   14: ldc2_w 75134355382006
    //   17: lxor
    //   18: lstore #5
    //   20: dup2
    //   21: ldc2_w 79177396187780
    //   24: lxor
    //   25: lstore #7
    //   27: dup2
    //   28: ldc2_w 100871715054010
    //   31: lxor
    //   32: dup2
    //   33: bipush #48
    //   35: lushr
    //   36: l2i
    //   37: istore #9
    //   39: dup2
    //   40: bipush #16
    //   42: lshl
    //   43: bipush #48
    //   45: lushr
    //   46: l2i
    //   47: istore #10
    //   49: dup2
    //   50: bipush #32
    //   52: lshl
    //   53: bipush #32
    //   55: lushr
    //   56: l2i
    //   57: istore #11
    //   59: pop2
    //   60: pop2
    //   61: aload_0
    //   62: sipush #29827
    //   65: ldc2_w 5280659770524681561
    //   68: lload_1
    //   69: lxor
    //   70: <illegal opcode> e : (IJ)Ljava/lang/String;
    //   75: lload #5
    //   77: sipush #9673
    //   80: ldc2_w 1205794001366914065
    //   83: lload_1
    //   84: lxor
    //   85: <illegal opcode> e : (IJ)Ljava/lang/String;
    //   90: getstatic wtf/opal/kn.MOVEMENT : Lwtf/opal/kn;
    //   93: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   96: aload_0
    //   97: new wtf/opal/ky
    //   100: dup
    //   101: bipush #42
    //   103: ldc2_w 1181685900554678769
    //   106: lload_1
    //   107: lxor
    //   108: <illegal opcode> e : (IJ)Ljava/lang/String;
    //   113: aload_0
    //   114: getstatic wtf/opal/l0.WATCHDOG : Lwtf/opal/l0;
    //   117: invokespecial <init> : (Ljava/lang/String;Lwtf/opal/d;Ljava/lang/Enum;)V
    //   120: putfield T : Lwtf/opal/ky;
    //   123: aload_0
    //   124: iconst_1
    //   125: anewarray wtf/opal/k3
    //   128: dup
    //   129: iconst_0
    //   130: aload_0
    //   131: getfield T : Lwtf/opal/ky;
    //   134: aastore
    //   135: lload_3
    //   136: dup2_x1
    //   137: pop2
    //   138: iconst_2
    //   139: anewarray java/lang/Object
    //   142: dup_x1
    //   143: swap
    //   144: iconst_1
    //   145: swap
    //   146: aastore
    //   147: dup_x2
    //   148: dup_x2
    //   149: pop
    //   150: invokestatic valueOf : (J)Ljava/lang/Long;
    //   153: iconst_0
    //   154: swap
    //   155: aastore
    //   156: invokevirtual o : ([Ljava/lang/Object;)V
    //   159: aload_0
    //   160: aload_0
    //   161: getfield T : Lwtf/opal/ky;
    //   164: iconst_4
    //   165: anewarray wtf/opal/u_
    //   168: dup
    //   169: iconst_0
    //   170: new wtf/opal/uz
    //   173: dup
    //   174: lload #7
    //   176: aload_0
    //   177: invokespecial <init> : (JLwtf/opal/h;)V
    //   180: aastore
    //   181: dup
    //   182: iconst_1
    //   183: new wtf/opal/ur
    //   186: dup
    //   187: aload_0
    //   188: invokespecial <init> : (Lwtf/opal/h;)V
    //   191: aastore
    //   192: dup
    //   193: iconst_2
    //   194: new wtf/opal/u7
    //   197: dup
    //   198: aload_0
    //   199: invokespecial <init> : (Lwtf/opal/h;)V
    //   202: aastore
    //   203: dup
    //   204: iconst_3
    //   205: new wtf/opal/ul
    //   208: dup
    //   209: iload #9
    //   211: i2s
    //   212: aload_0
    //   213: iload #10
    //   215: i2s
    //   216: iload #11
    //   218: invokespecial <init> : (SLwtf/opal/h;SI)V
    //   221: aastore
    //   222: iconst_2
    //   223: anewarray java/lang/Object
    //   226: dup_x1
    //   227: swap
    //   228: iconst_1
    //   229: swap
    //   230: aastore
    //   231: dup_x1
    //   232: swap
    //   233: iconst_0
    //   234: swap
    //   235: aastore
    //   236: invokevirtual Y : ([Ljava/lang/Object;)V
    //   239: return
  }
  
  public String o(Object[] paramArrayOfObject) {
    long l = ((Long)paramArrayOfObject[0]).longValue();
    return ((l0)this.T.z()).toString();
  }
  
  static {
    long l = a ^ 0xE43679FDA28L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[3];
    boolean bool = false;
    String str;
    int i = (str = "BLYo©©ò0\033fÆ)©ÿ\024=mJó&\r\034\020­+ÖÆ7Jßõþ'Åª¡(ù\013\022L\031}Q\017wsÜ¶\036Bg\nåp\004\004Z\026òPlÎªëH±íúÛc\024").length();
    byte b2 = 24;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x7545;
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
        throw new RuntimeException("wtf/opal/h", exception);
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
    //   65: ldc 'wtf/opal/h'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\h.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */