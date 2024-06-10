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

public final class j_ extends d {
  private final ke I;
  
  private final gm<lz> R;
  
  private final gm<p1> m;
  
  private static final long a = on.a(-286351707658985060L, 8506734285296836390L, MethodHandles.lookup().lookupClass()).a(120779610222623L);
  
  private static final String[] b;
  
  private static final String[] d;
  
  private static final Map f = new HashMap<>(13);
  
  public j_(long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/j_.a : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: lload_1
    //   7: dup2
    //   8: ldc2_w 96833952619796
    //   11: lxor
    //   12: lstore_3
    //   13: dup2
    //   14: ldc2_w 130221179487297
    //   17: lxor
    //   18: lstore #5
    //   20: pop2
    //   21: aload_0
    //   22: sipush #22256
    //   25: ldc2_w 8117521399421419739
    //   28: lload_1
    //   29: lxor
    //   30: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   35: lload #5
    //   37: sipush #25555
    //   40: ldc2_w 8951941458575193593
    //   43: lload_1
    //   44: lxor
    //   45: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   50: getstatic wtf/opal/kn.PLAYER : Lwtf/opal/kn;
    //   53: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   56: aload_0
    //   57: new wtf/opal/ke
    //   60: dup
    //   61: sipush #17992
    //   64: ldc2_w 8234351392777130080
    //   67: lload_1
    //   68: lxor
    //   69: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   74: iconst_0
    //   75: invokespecial <init> : (Ljava/lang/String;Z)V
    //   78: putfield I : Lwtf/opal/ke;
    //   81: aload_0
    //   82: aload_0
    //   83: <illegal opcode> H : (Lwtf/opal/j_;)Lwtf/opal/gm;
    //   88: putfield R : Lwtf/opal/gm;
    //   91: aload_0
    //   92: aload_0
    //   93: <illegal opcode> H : (Lwtf/opal/j_;)Lwtf/opal/gm;
    //   98: putfield m : Lwtf/opal/gm;
    //   101: aload_0
    //   102: iconst_1
    //   103: anewarray wtf/opal/k3
    //   106: dup
    //   107: iconst_0
    //   108: aload_0
    //   109: getfield I : Lwtf/opal/ke;
    //   112: aastore
    //   113: lload_3
    //   114: dup2_x1
    //   115: pop2
    //   116: iconst_2
    //   117: anewarray java/lang/Object
    //   120: dup_x1
    //   121: swap
    //   122: iconst_1
    //   123: swap
    //   124: aastore
    //   125: dup_x2
    //   126: dup_x2
    //   127: pop
    //   128: invokestatic valueOf : (J)Ljava/lang/Long;
    //   131: iconst_0
    //   132: swap
    //   133: aastore
    //   134: invokevirtual o : ([Ljava/lang/Object;)V
    //   137: return
  }
  
  public ke k(Object[] paramArrayOfObject) {
    return this.I;
  }
  
  private void lambda$new$1(p1 paramp1) {
    long l1 = a ^ 0x3C67CB12CE5FL;
    long l2 = l1 ^ 0x6E4001ED62C5L;
    long l3 = l1 ^ 0x4DBC975EEDECL;
    boolean bool = j4.k();
    try {
      if (bool)
        try {
          if (this.I.z().booleanValue()) {
          
          } else {
            return;
          } 
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    try {
      if (bool)
        try {
          if (this.I.z().booleanValue()) {
            new Object[1];
          } else {
            return;
          } 
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    try {
      if (bool)
        try {
          if (this.I.z().booleanValue()) {
          
          } else {
            return;
          } 
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    try {
      if (this.I.z().booleanValue()) {
        (new Object[2])[1] = Float.valueOf(b9.c.field_1724.method_36454());
        new Object[2];
        paramp1.x(new Object[] { Float.valueOf((float)Math.toDegrees(l_.Q(new Object[] { Long.valueOf(l3) }))) });
      } 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  private void lambda$new$0(lz paramlz) {
    // Byte code:
    //   0: getstatic wtf/opal/j_.a : J
    //   3: ldc2_w 45957622662911
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 136251885333093
    //   13: lxor
    //   14: lstore #4
    //   16: pop2
    //   17: invokestatic k : ()Z
    //   20: iconst_0
    //   21: anewarray java/lang/Object
    //   24: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   27: iconst_0
    //   28: anewarray java/lang/Object
    //   31: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   34: ldc wtf/opal/xw
    //   36: iconst_1
    //   37: anewarray java/lang/Object
    //   40: dup_x1
    //   41: swap
    //   42: iconst_0
    //   43: swap
    //   44: aastore
    //   45: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   48: checkcast wtf/opal/xw
    //   51: astore #7
    //   53: istore #6
    //   55: aload #7
    //   57: iconst_0
    //   58: anewarray java/lang/Object
    //   61: invokevirtual D : ([Ljava/lang/Object;)Z
    //   64: iload #6
    //   66: ifeq -> 124
    //   69: ifeq -> 111
    //   72: goto -> 79
    //   75: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   78: athrow
    //   79: aload #7
    //   81: iconst_0
    //   82: anewarray java/lang/Object
    //   85: invokevirtual F : ([Ljava/lang/Object;)Z
    //   88: iload #6
    //   90: ifeq -> 124
    //   93: goto -> 100
    //   96: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   99: athrow
    //   100: ifne -> 111
    //   103: goto -> 110
    //   106: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   109: athrow
    //   110: return
    //   111: aload_0
    //   112: getfield I : Lwtf/opal/ke;
    //   115: invokevirtual z : ()Ljava/lang/Object;
    //   118: checkcast java/lang/Boolean
    //   121: invokevirtual booleanValue : ()Z
    //   124: iload #6
    //   126: ifeq -> 164
    //   129: ifeq -> 249
    //   132: goto -> 139
    //   135: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   138: athrow
    //   139: lload #4
    //   141: iconst_1
    //   142: anewarray java/lang/Object
    //   145: dup_x2
    //   146: dup_x2
    //   147: pop
    //   148: invokestatic valueOf : (J)Ljava/lang/Long;
    //   151: iconst_0
    //   152: swap
    //   153: aastore
    //   154: invokestatic I : ([Ljava/lang/Object;)Z
    //   157: goto -> 164
    //   160: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   163: athrow
    //   164: iload #6
    //   166: ifeq -> 207
    //   169: ifeq -> 232
    //   172: goto -> 179
    //   175: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   178: athrow
    //   179: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   182: getfield field_1724 : Lnet/minecraft/class_746;
    //   185: iload #6
    //   187: ifeq -> 245
    //   190: goto -> 197
    //   193: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   196: athrow
    //   197: invokevirtual method_6115 : ()Z
    //   200: goto -> 207
    //   203: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   206: athrow
    //   207: ifne -> 232
    //   210: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   213: getfield field_1724 : Lnet/minecraft/class_746;
    //   216: iconst_1
    //   217: invokevirtual method_5728 : (Z)V
    //   220: iload #6
    //   222: ifne -> 249
    //   225: goto -> 232
    //   228: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   231: athrow
    //   232: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   235: getfield field_1724 : Lnet/minecraft/class_746;
    //   238: goto -> 245
    //   241: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   244: athrow
    //   245: iconst_0
    //   246: invokevirtual method_5728 : (Z)V
    //   249: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   252: getfield field_1690 : Lnet/minecraft/class_315;
    //   255: getfield field_1867 : Lnet/minecraft/class_304;
    //   258: iconst_1
    //   259: invokevirtual method_23481 : (Z)V
    //   262: return
    // Exception table:
    //   from	to	target	type
    //   55	72	75	wtf/opal/x5
    //   69	93	96	wtf/opal/x5
    //   79	103	106	wtf/opal/x5
    //   124	132	135	wtf/opal/x5
    //   129	157	160	wtf/opal/x5
    //   164	172	175	wtf/opal/x5
    //   169	190	193	wtf/opal/x5
    //   179	200	203	wtf/opal/x5
    //   207	225	228	wtf/opal/x5
    //   210	238	241	wtf/opal/x5
  }
  
  static {
    long l = a ^ 0xCF46659986AL;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[3];
    boolean bool = false;
    String str;
    int i = (str = "Ð¤\035AØ¡0¾DI#±XùÅsNU'H|Íó|¯qyäc½â=ö\016\030¡1'8âó÷O6\030ûµLÞ)²V¼ûè\032\0012_ÚéÇ|`\030çô\020i¬fÆ\004QÖ{ü6LG\004\020ðx8X­³sVþC\\â").length();
    byte b2 = 72;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x802;
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
        throw new RuntimeException("wtf/opal/j_", exception);
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
    //   66: ldc_w 'wtf/opal/j_'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\j_.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */