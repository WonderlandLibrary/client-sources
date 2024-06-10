package wtf.opal;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_2374;
import net.minecraft.class_2382;
import net.minecraft.class_241;
import net.minecraft.class_243;
import net.minecraft.class_2596;
import net.minecraft.class_2846;
import net.minecraft.class_3965;

public final class xn extends d {
  private final kt P;
  
  private final List<class_2338> y;
  
  private class_241 T;
  
  private final gm<lz> W;
  
  private final gm<d4> I;
  
  private static final long a = on.a(611254709499159860L, 1524751647389141830L, MethodHandles.lookup().lookupClass()).a(46565413543812L);
  
  private static final String[] b;
  
  private static final String[] d;
  
  private static final Map f = new HashMap<>(13);
  
  public xn(char paramChar1, char paramChar2, int paramInt) {
    // Byte code:
    //   0: iload_1
    //   1: i2l
    //   2: bipush #48
    //   4: lshl
    //   5: iload_2
    //   6: i2l
    //   7: bipush #48
    //   9: lshl
    //   10: bipush #16
    //   12: lushr
    //   13: lor
    //   14: iload_3
    //   15: i2l
    //   16: bipush #32
    //   18: lshl
    //   19: bipush #32
    //   21: lushr
    //   22: lor
    //   23: getstatic wtf/opal/xn.a : J
    //   26: lxor
    //   27: lstore #4
    //   29: lload #4
    //   31: dup2
    //   32: ldc2_w 80652243012610
    //   35: lxor
    //   36: lstore #6
    //   38: dup2
    //   39: ldc2_w 113407574922583
    //   42: lxor
    //   43: lstore #8
    //   45: pop2
    //   46: aload_0
    //   47: sipush #31130
    //   50: ldc2_w 8408404024578269586
    //   53: lload #4
    //   55: lxor
    //   56: <illegal opcode> b : (IJ)Ljava/lang/String;
    //   61: lload #8
    //   63: sipush #27838
    //   66: ldc2_w 2611148194952082612
    //   69: lload #4
    //   71: lxor
    //   72: <illegal opcode> b : (IJ)Ljava/lang/String;
    //   77: getstatic wtf/opal/kn.WORLD : Lwtf/opal/kn;
    //   80: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   83: aload_0
    //   84: new wtf/opal/kt
    //   87: dup
    //   88: sipush #18947
    //   91: ldc2_w 1500918516399165960
    //   94: lload #4
    //   96: lxor
    //   97: <illegal opcode> b : (IJ)Ljava/lang/String;
    //   102: ldc2_w 4.0
    //   105: dconst_1
    //   106: ldc2_w 6.0
    //   109: dconst_1
    //   110: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   113: putfield P : Lwtf/opal/kt;
    //   116: aload_0
    //   117: new java/util/concurrent/CopyOnWriteArrayList
    //   120: dup
    //   121: invokespecial <init> : ()V
    //   124: putfield y : Ljava/util/List;
    //   127: aload_0
    //   128: aload_0
    //   129: <illegal opcode> H : (Lwtf/opal/xn;)Lwtf/opal/gm;
    //   134: putfield W : Lwtf/opal/gm;
    //   137: aload_0
    //   138: aload_0
    //   139: <illegal opcode> H : (Lwtf/opal/xn;)Lwtf/opal/gm;
    //   144: putfield I : Lwtf/opal/gm;
    //   147: aload_0
    //   148: iconst_1
    //   149: anewarray wtf/opal/k3
    //   152: dup
    //   153: iconst_0
    //   154: aload_0
    //   155: getfield P : Lwtf/opal/kt;
    //   158: aastore
    //   159: lload #6
    //   161: dup2_x1
    //   162: pop2
    //   163: iconst_2
    //   164: anewarray java/lang/Object
    //   167: dup_x1
    //   168: swap
    //   169: iconst_1
    //   170: swap
    //   171: aastore
    //   172: dup_x2
    //   173: dup_x2
    //   174: pop
    //   175: invokestatic valueOf : (J)Ljava/lang/Long;
    //   178: iconst_0
    //   179: swap
    //   180: aastore
    //   181: invokevirtual o : ([Ljava/lang/Object;)V
    //   184: return
  }
  
  protected void K(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    long l2 = l1 ^ 0x0L;
    this.y.clear();
    this.T = null;
    new Object[1];
    super.K(new Object[] { Long.valueOf(l2) });
  }
  
  private void lambda$new$1(d4 paramd4) {
    this.y.clear();
  }
  
  private void lambda$new$0(lz paramlz) {
    long l1 = a ^ 0x4AED32A31A9AL;
    long l2 = l1 ^ 0xEDFFED5E09CL;
    int[] arrayOfInt = xw.k();
    try {
      new Object[1];
      if (df.h(new Object[] { Long.valueOf(l2) }))
        return; 
    } catch (x5 x5) {
      throw a(null);
    } 
    class_243 class_243 = b9.c.field_1724.method_19538().method_1031(0.0D, b9.c.field_1724.method_18381(b9.c.field_1724.method_18376()), 0.0D);
    class_2338 class_23381 = class_2338.method_49638((class_2374)class_243);
    int i = this.P.z().intValue();
    class_2338 class_23382 = class_23381.method_10059(new class_2382(i, i, i));
    class_2338 class_23383 = class_23381.method_10081(new class_2382(i, i, i));
    class_2338 class_23384 = new class_2338(Math.min(class_23382.method_10263(), class_23383.method_10263()), Math.min(class_23382.method_10264(), class_23383.method_10264()), Math.min(class_23382.method_10260(), class_23383.method_10260()));
    class_2338 class_23385 = new class_2338(Math.max(class_23382.method_10263(), class_23383.method_10263()), Math.max(class_23382.method_10264(), class_23383.method_10264()), Math.max(class_23382.method_10260(), class_23383.method_10260()));
    int j = class_23384.method_10263();
    while (true) {
      label43: while (j <= class_23385.method_10263()) {
        int k = class_23384.method_10264();
        while (true) {
          label40: while (k <= class_23385.method_10264()) {
            if (arrayOfInt != null) {
              int m = class_23384.method_10260();
              while (m <= class_23385.method_10260()) {
                class_2338 class_2338 = new class_2338(j, k, m);
                class_241 class_2411 = ln.V(new Object[] { null, class_2350.field_11036, class_2338 });
                (new Object[5])[4] = b9.c.field_1687.method_8320(class_2338);
                (new Object[5])[3] = class_2338;
                (new Object[5])[2] = Float.valueOf(class_2411.field_1342);
                (new Object[5])[1] = Float.valueOf(class_2411.field_1343);
                new Object[5];
                class_3965 class_3965 = up.W(new Object[] { Double.valueOf(6.0D) });
                try {
                  if (arrayOfInt != null)
                    if (arrayOfInt != null) {
                      try {
                        if (!b9.c.field_1687.method_8320(class_2338).method_26215())
                          try {
                            if (class_3965 != null)
                              b9.c.method_1562().method_52787((class_2596)new class_2846(class_2846.class_2847.field_12968, class_3965.method_17777(), class_3965.method_17780())); 
                          } catch (x5 x5) {
                            throw a(null);
                          }  
                      } catch (x5 x5) {
                        throw a(null);
                      } 
                      m++;
                    } else {
                      continue label40;
                    }  
                } catch (x5 x5) {
                  throw a(null);
                } 
                if (arrayOfInt == null)
                  break; 
              } 
              k++;
              if (arrayOfInt == null)
                break; 
              continue;
            } 
            continue label43;
          } 
          break;
        } 
        j++;
        if (arrayOfInt == null)
          break; 
      } 
      break;
    } 
  }
  
  static {
    long l = a ^ 0x4B78AC1482CBL;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[3];
    boolean bool = false;
    String str;
    int i = (str = "\b¼ï~ò8H:â#\000*·üÞhÐBºCøó\027 Þ1ÛøJ\005Ã­Ä!·\027\020RbñØfÔ\037\023!<a9Q!]\020{ÖEMj\ti\f_$rÅ").length();
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x1734;
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
        throw new RuntimeException("wtf/opal/xn", exception);
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
    //   66: ldc_w 'wtf/opal/xn'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\xn.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */