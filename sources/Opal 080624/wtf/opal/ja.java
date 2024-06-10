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

public final class ja extends d {
  private final kt L;
  
  private final ke u;
  
  private final ke U;
  
  private final ke t;
  
  private final pa p;
  
  private final dc W;
  
  private static final long a = on.a(-7162513674129046744L, -3103475193674136250L, MethodHandles.lookup().lookupClass()).a(106260437715986L);
  
  private static final String[] b;
  
  private static final String[] d;
  
  private static final Map f = new HashMap<>(13);
  
  public ja(long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/ja.a : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: lload_1
    //   7: dup2
    //   8: ldc2_w 63753339007376
    //   11: lxor
    //   12: lstore_3
    //   13: dup2
    //   14: ldc2_w 70548867043326
    //   17: lxor
    //   18: lstore #5
    //   20: dup2
    //   21: ldc2_w 121321590920875
    //   24: lxor
    //   25: lstore #7
    //   27: pop2
    //   28: aload_0
    //   29: sipush #21642
    //   32: ldc2_w 4244117661294574786
    //   35: lload_1
    //   36: lxor
    //   37: <illegal opcode> r : (IJ)Ljava/lang/String;
    //   42: lload #7
    //   44: sipush #9259
    //   47: ldc2_w 3747908500228787298
    //   50: lload_1
    //   51: lxor
    //   52: <illegal opcode> r : (IJ)Ljava/lang/String;
    //   57: getstatic wtf/opal/kn.VISUAL : Lwtf/opal/kn;
    //   60: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   63: aload_0
    //   64: new wtf/opal/kt
    //   67: dup
    //   68: sipush #761
    //   71: ldc2_w 2058819244024785591
    //   74: lload_1
    //   75: lxor
    //   76: <illegal opcode> r : (IJ)Ljava/lang/String;
    //   81: dconst_1
    //   82: ldc2_w 0.5
    //   85: ldc2_w 1.5
    //   88: ldc2_w 0.01
    //   91: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   94: putfield L : Lwtf/opal/kt;
    //   97: aload_0
    //   98: new wtf/opal/ke
    //   101: dup
    //   102: sipush #3134
    //   105: ldc2_w 1265388128182887538
    //   108: lload_1
    //   109: lxor
    //   110: <illegal opcode> r : (IJ)Ljava/lang/String;
    //   115: iconst_1
    //   116: invokespecial <init> : (Ljava/lang/String;Z)V
    //   119: putfield u : Lwtf/opal/ke;
    //   122: aload_0
    //   123: new wtf/opal/ke
    //   126: dup
    //   127: sipush #23845
    //   130: ldc2_w 8933228859891135848
    //   133: lload_1
    //   134: lxor
    //   135: <illegal opcode> r : (IJ)Ljava/lang/String;
    //   140: iconst_1
    //   141: invokespecial <init> : (Ljava/lang/String;Z)V
    //   144: putfield U : Lwtf/opal/ke;
    //   147: aload_0
    //   148: new wtf/opal/ke
    //   151: dup
    //   152: sipush #9179
    //   155: ldc2_w 4957508082789272468
    //   158: lload_1
    //   159: lxor
    //   160: <illegal opcode> r : (IJ)Ljava/lang/String;
    //   165: iconst_1
    //   166: invokespecial <init> : (Ljava/lang/String;Z)V
    //   169: putfield t : Lwtf/opal/ke;
    //   172: aload_0
    //   173: iconst_0
    //   174: anewarray java/lang/Object
    //   177: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   180: iconst_0
    //   181: anewarray java/lang/Object
    //   184: invokevirtual z : ([Ljava/lang/Object;)Lwtf/opal/pa;
    //   187: putfield p : Lwtf/opal/pa;
    //   190: aload_0
    //   191: iconst_0
    //   192: anewarray java/lang/Object
    //   195: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   198: iconst_0
    //   199: anewarray java/lang/Object
    //   202: invokevirtual D : ([Ljava/lang/Object;)Lwtf/opal/dc;
    //   205: putfield W : Lwtf/opal/dc;
    //   208: aload_0
    //   209: iconst_4
    //   210: anewarray wtf/opal/k3
    //   213: dup
    //   214: iconst_0
    //   215: aload_0
    //   216: getfield L : Lwtf/opal/kt;
    //   219: aastore
    //   220: dup
    //   221: iconst_1
    //   222: aload_0
    //   223: getfield u : Lwtf/opal/ke;
    //   226: aastore
    //   227: dup
    //   228: iconst_2
    //   229: aload_0
    //   230: getfield U : Lwtf/opal/ke;
    //   233: aastore
    //   234: dup
    //   235: iconst_3
    //   236: aload_0
    //   237: getfield t : Lwtf/opal/ke;
    //   240: aastore
    //   241: lload #5
    //   243: dup2_x1
    //   244: pop2
    //   245: iconst_2
    //   246: anewarray java/lang/Object
    //   249: dup_x1
    //   250: swap
    //   251: iconst_1
    //   252: swap
    //   253: aastore
    //   254: dup_x2
    //   255: dup_x2
    //   256: pop
    //   257: invokestatic valueOf : (J)Ljava/lang/Long;
    //   260: iconst_0
    //   261: swap
    //   262: aastore
    //   263: invokevirtual o : ([Ljava/lang/Object;)V
    //   266: aload_0
    //   267: iconst_1
    //   268: lload_3
    //   269: iconst_2
    //   270: anewarray java/lang/Object
    //   273: dup_x2
    //   274: dup_x2
    //   275: pop
    //   276: invokestatic valueOf : (J)Ljava/lang/Long;
    //   279: iconst_1
    //   280: swap
    //   281: aastore
    //   282: dup_x1
    //   283: swap
    //   284: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   287: iconst_0
    //   288: swap
    //   289: aastore
    //   290: invokevirtual Q : ([Ljava/lang/Object;)V
    //   293: return
  }
  
  public int X(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x1C4D8CF8CDF8L;
    String str = jt.S();
    try {
      if (str == null)
        try {
          if (l1 >= 0L)
            if (!this.u.z().booleanValue())
              return 0;  
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    jl jl = (jl)d1.q(new Object[0]).x(new Object[0]).V(new Object[] { jl.class });
    try {
      if (str == null) {
        try {
          if (!jl.D(new Object[0]))
            return 0; 
        } catch (x5 x5) {
          throw a(null);
        } 
        new Object[1];
      } 
    } catch (x5 x5) {
      throw a(null);
    } 
    return (int)(jl.z(new Object[] { Long.valueOf(l2) }) / D(new Object[0]));
  }
  
  public float D(Object[] paramArrayOfObject) {
    return this.L.z().floatValue();
  }
  
  public boolean w(Object[] paramArrayOfObject) {
    return this.U.z().booleanValue();
  }
  
  public boolean Z(Object[] paramArrayOfObject) {
    return this.t.z().booleanValue();
  }
  
  static {
    long l = a ^ 0x3555B574C40FL;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[6];
    boolean bool = false;
    String str;
    int i = (str = "\007¨þï<æ\001ªÈeìÃ\017\027Èô\017Â®ÜÀzÊ:1á}í^òò<F\030¬E0\035Ò|\024¯\030ÕuÈd\017âòa ï\030Á÷ïÔbõ­ôc\032¸OæÀËäñVÕÁX9Ëçûh\016Æ\030Ú5s¿ÛUÖ¼(~¼Ûô-Äó\005¬Ý").length();
    byte b2 = 40;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x248E;
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
        throw new RuntimeException("wtf/opal/ja", exception);
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
    //   49: goto -> 103
    //   52: astore #4
    //   54: new java/lang/RuntimeException
    //   57: dup
    //   58: new java/lang/StringBuilder
    //   61: dup
    //   62: invokespecial <init> : ()V
    //   65: ldc_w 'wtf/opal/ja'
    //   68: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   71: ldc_w ' : '
    //   74: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   77: aload_1
    //   78: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   81: ldc_w ' : '
    //   84: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   87: aload_2
    //   88: invokevirtual toString : ()Ljava/lang/String;
    //   91: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   94: invokevirtual toString : ()Ljava/lang/String;
    //   97: aload #4
    //   99: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   102: athrow
    //   103: aload_3
    //   104: areturn
    // Exception table:
    //   from	to	target	type
    //   9	49	52	java/lang/Exception
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\ja.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */