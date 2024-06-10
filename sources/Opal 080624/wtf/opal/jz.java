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

public final class jz extends d {
  public final ke m;
  
  public final kt d;
  
  private static final long a = on.a(9046042119357414591L, -1067175767154763172L, MethodHandles.lookup().lookupClass()).a(80695098210035L);
  
  private static final String[] b;
  
  private static final String[] f;
  
  private static final Map g = new HashMap<>(13);
  
  private static final long[] k;
  
  private static final Integer[] l;
  
  private static final Map n;
  
  public jz(long paramLong, char paramChar) {
    // Byte code:
    //   0: lload_1
    //   1: bipush #16
    //   3: lshl
    //   4: iload_3
    //   5: i2l
    //   6: bipush #48
    //   8: lshl
    //   9: bipush #48
    //   11: lushr
    //   12: lor
    //   13: getstatic wtf/opal/jz.a : J
    //   16: lxor
    //   17: lstore #4
    //   19: lload #4
    //   21: dup2
    //   22: ldc2_w 51965944050836
    //   25: lxor
    //   26: lstore #6
    //   28: dup2
    //   29: ldc2_w 1365025680833
    //   32: lxor
    //   33: lstore #8
    //   35: pop2
    //   36: aload_0
    //   37: sipush #14004
    //   40: ldc2_w 2620087475796471614
    //   43: lload #4
    //   45: lxor
    //   46: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   51: lload #8
    //   53: sipush #22423
    //   56: ldc2_w 2703569517162106396
    //   59: lload #4
    //   61: lxor
    //   62: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   67: getstatic wtf/opal/kn.VISUAL : Lwtf/opal/kn;
    //   70: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   73: aload_0
    //   74: new wtf/opal/ke
    //   77: dup
    //   78: sipush #20162
    //   81: ldc2_w 3115176579716585293
    //   84: lload #4
    //   86: lxor
    //   87: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   92: iconst_1
    //   93: invokespecial <init> : (Ljava/lang/String;Z)V
    //   96: putfield m : Lwtf/opal/ke;
    //   99: aload_0
    //   100: new wtf/opal/kt
    //   103: dup
    //   104: sipush #16594
    //   107: ldc2_w 7476766971868158299
    //   110: lload #4
    //   112: lxor
    //   113: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   118: ldc2_w 4.0
    //   121: dconst_1
    //   122: ldc2_w 10.0
    //   125: dconst_1
    //   126: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   129: putfield d : Lwtf/opal/kt;
    //   132: aload_0
    //   133: iconst_2
    //   134: anewarray wtf/opal/k3
    //   137: dup
    //   138: iconst_0
    //   139: aload_0
    //   140: getfield m : Lwtf/opal/ke;
    //   143: aastore
    //   144: dup
    //   145: iconst_1
    //   146: aload_0
    //   147: getfield d : Lwtf/opal/kt;
    //   150: aastore
    //   151: lload #6
    //   153: dup2_x1
    //   154: pop2
    //   155: iconst_2
    //   156: anewarray java/lang/Object
    //   159: dup_x1
    //   160: swap
    //   161: iconst_1
    //   162: swap
    //   163: aastore
    //   164: dup_x2
    //   165: dup_x2
    //   166: pop
    //   167: invokestatic valueOf : (J)Ljava/lang/Long;
    //   170: iconst_0
    //   171: swap
    //   172: aastore
    //   173: invokevirtual o : ([Ljava/lang/Object;)V
    //   176: return
  }
  
  public void F(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Float
    //   7: invokevirtual floatValue : ()F
    //   10: fstore_2
    //   11: dup
    //   12: iconst_1
    //   13: aaload
    //   14: checkcast java/lang/Long
    //   17: invokevirtual longValue : ()J
    //   20: lstore_3
    //   21: pop
    //   22: getstatic wtf/opal/jz.a : J
    //   25: lload_3
    //   26: lxor
    //   27: lstore_3
    //   28: invokestatic S : ()Ljava/lang/String;
    //   31: astore #5
    //   33: aload #5
    //   35: ifnonnull -> 128
    //   38: aload_0
    //   39: getfield m : Lwtf/opal/ke;
    //   42: invokevirtual z : ()Ljava/lang/Object;
    //   45: checkcast java/lang/Boolean
    //   48: invokevirtual booleanValue : ()Z
    //   51: ifne -> 66
    //   54: goto -> 61
    //   57: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   60: athrow
    //   61: return
    //   62: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   65: athrow
    //   66: getstatic wtf/opal/lt.t : Lnet/minecraft/class_276;
    //   69: iconst_0
    //   70: invokevirtual method_1235 : (Z)V
    //   73: invokestatic enableBlend : ()V
    //   76: getstatic wtf/opal/lt.s : Lnet/minecraft/class_279;
    //   79: sipush #8366
    //   82: ldc2_w 8553238734749826643
    //   85: lload_3
    //   86: lxor
    //   87: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   92: aload_0
    //   93: getfield d : Lwtf/opal/kt;
    //   96: invokevirtual z : ()Ljava/lang/Object;
    //   99: checkcast java/lang/Double
    //   102: invokevirtual floatValue : ()F
    //   105: invokevirtual method_57799 : (Ljava/lang/String;F)V
    //   108: getstatic wtf/opal/lt.s : Lnet/minecraft/class_279;
    //   111: fload_2
    //   112: invokevirtual method_1258 : (F)V
    //   115: invokestatic disableBlend : ()V
    //   118: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   121: invokevirtual method_1522 : ()Lnet/minecraft/class_276;
    //   124: iconst_0
    //   125: invokevirtual method_1235 : (Z)V
    //   128: iconst_0
    //   129: anewarray java/lang/Object
    //   132: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   135: iconst_0
    //   136: anewarray java/lang/Object
    //   139: invokevirtual z : ([Ljava/lang/Object;)Lwtf/opal/pa;
    //   142: astore #6
    //   144: aload #6
    //   146: iconst_0
    //   147: anewarray java/lang/Object
    //   150: invokevirtual y : ([Ljava/lang/Object;)J
    //   153: invokestatic nvgBeginPath : (J)V
    //   156: aload #6
    //   158: getstatic wtf/opal/lt.t : Lnet/minecraft/class_276;
    //   161: invokevirtual method_30277 : ()I
    //   164: ldc 50.0
    //   166: ldc 50.0
    //   168: sipush #2597
    //   171: ldc2_w 2477124901611984096
    //   174: lload_3
    //   175: lxor
    //   176: <illegal opcode> p : (IJ)I
    //   181: sipush #5450
    //   184: ldc2_w 8261904149668613006
    //   187: lload_3
    //   188: lxor
    //   189: <illegal opcode> p : (IJ)I
    //   194: iconst_3
    //   195: iconst_0
    //   196: iconst_1
    //   197: bipush #8
    //   199: anewarray java/lang/Object
    //   202: dup_x1
    //   203: swap
    //   204: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   207: bipush #7
    //   209: swap
    //   210: aastore
    //   211: dup_x1
    //   212: swap
    //   213: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   216: bipush #6
    //   218: swap
    //   219: aastore
    //   220: dup_x1
    //   221: swap
    //   222: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   225: iconst_5
    //   226: swap
    //   227: aastore
    //   228: dup_x1
    //   229: swap
    //   230: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   233: iconst_4
    //   234: swap
    //   235: aastore
    //   236: dup_x1
    //   237: swap
    //   238: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   241: iconst_3
    //   242: swap
    //   243: aastore
    //   244: dup_x1
    //   245: swap
    //   246: invokestatic valueOf : (F)Ljava/lang/Float;
    //   249: iconst_2
    //   250: swap
    //   251: aastore
    //   252: dup_x1
    //   253: swap
    //   254: invokestatic valueOf : (F)Ljava/lang/Float;
    //   257: iconst_1
    //   258: swap
    //   259: aastore
    //   260: dup_x1
    //   261: swap
    //   262: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   265: iconst_0
    //   266: swap
    //   267: aastore
    //   268: invokevirtual v : ([Ljava/lang/Object;)Lorg/lwjgl/nanovg/NVGPaint;
    //   271: astore #7
    //   273: aload #6
    //   275: iconst_0
    //   276: anewarray java/lang/Object
    //   279: invokevirtual y : ([Ljava/lang/Object;)J
    //   282: aload #7
    //   284: invokestatic nvgFillPaint : (JLorg/lwjgl/nanovg/NVGPaint;)V
    //   287: aload #6
    //   289: iconst_0
    //   290: anewarray java/lang/Object
    //   293: invokevirtual y : ([Ljava/lang/Object;)J
    //   296: ldc 50.0
    //   298: ldc 50.0
    //   300: ldc 24.0
    //   302: ldc 24.0
    //   304: ldc 6.0
    //   306: invokestatic nvgRoundedRect : (JFFFFF)V
    //   309: aload #6
    //   311: iconst_0
    //   312: anewarray java/lang/Object
    //   315: invokevirtual y : ([Ljava/lang/Object;)J
    //   318: invokestatic nvgFill : (J)V
    //   321: aload #6
    //   323: iconst_0
    //   324: anewarray java/lang/Object
    //   327: invokevirtual y : ([Ljava/lang/Object;)J
    //   330: invokestatic nvgClosePath : (J)V
    //   333: return
    // Exception table:
    //   from	to	target	type
    //   33	54	57	wtf/opal/x5
    //   38	62	62	wtf/opal/x5
  }
  
  static {
    long l = a ^ 0x7954E69EDB40L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[5];
    boolean bool = false;
    String str;
    int i = (str = "f\rG\025~,h¹×@BÑd{ò\024·ñsG9N'§ óÎ\002Ï\024kË#Q$JÏ,Ë¿¾w,n*ø\013\016S×bÙ\031¨\030}ÎW\027¥Z»\002a79\0335¤Çÿ°äxuF\020").length();
    byte b2 = 24;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x6E23;
    if (f[i] == null) {
      Object[] arrayOfObject;
      try {
        Long long_ = Long.valueOf(Thread.currentThread().threadId());
        arrayOfObject = (Object[])g.get(long_);
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/PKCS5Padding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          g.put(long_, arrayOfObject);
        } 
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/jz", exception);
      } 
      byte[] arrayOfByte1 = new byte[8];
      arrayOfByte1[0] = (byte)(int)(paramLong >>> 56L);
      for (byte b = 1; b < 8; b++)
        arrayOfByte1[b] = (byte)(int)(paramLong << b * 8 >>> 56L); 
      DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
      SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
      ((Cipher)arrayOfObject[0]).init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
      byte[] arrayOfByte2 = b[i].getBytes("ISO-8859-1");
      f[i] = a(((Cipher)arrayOfObject[0]).doFinal(arrayOfByte2));
    } 
    return f[i];
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
    //   65: ldc_w 'wtf/opal/jz'
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
  
  private static int b(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x6E18;
    if (l[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l = k[i];
      byte[] arrayOfByte2 = { (byte)(int)(l >>> 56L), (byte)(int)(l >>> 48L), (byte)(int)(l >>> 40L), (byte)(int)(l >>> 32L), (byte)(int)(l >>> 24L), (byte)(int)(l >>> 16L), (byte)(int)(l >>> 8L), (byte)(int)l };
      Long long_ = Long.valueOf(Thread.currentThread().threadId());
      Object[] arrayOfObject = (Object[])n.get(long_);
      try {
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/NoPadding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          n.put(long_, arrayOfObject);
        } 
        DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
        SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
        Cipher cipher = (Cipher)arrayOfObject[0];
        cipher.init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
        arrayOfByte3 = cipher.doFinal(arrayOfByte2);
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/jz", exception);
      } 
      int j = (arrayOfByte3[4] & 0xFF) << 24 | (arrayOfByte3[5] & 0xFF) << 16 | (arrayOfByte3[6] & 0xFF) << 8 | arrayOfByte3[7] & 0xFF;
      l[i] = Integer.valueOf(j);
    } 
    return l[i].intValue();
  }
  
  private static int b(MethodHandles.Lookup paramLookup, MutableCallSite paramMutableCallSite, String paramString, Object[] paramArrayOfObject) {
    int i = ((Integer)paramArrayOfObject[0]).intValue();
    long l = ((Long)paramArrayOfObject[1]).longValue();
    int j = b(i, l);
    MethodHandle methodHandle = MethodHandles.constant(int.class, Integer.valueOf(j));
    paramMutableCallSite.setTarget(MethodHandles.dropArguments(methodHandle, 0, new Class[] { int.class, long.class }));
    return j;
  }
  
  private static CallSite b(MethodHandles.Lookup paramLookup, String paramString, MethodType paramMethodType) {
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
    //   65: ldc_w 'wtf/opal/jz'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\jz.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */