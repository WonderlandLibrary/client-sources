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

public final class df {
  private static boolean X;
  
  private static boolean x;
  
  private static final long a = on.a(-6793831696688521505L, -6726012041792220926L, MethodHandles.lookup().lookupClass()).a(26205295382110L);
  
  private static final String[] b;
  
  private static final String[] c;
  
  private static final Map d = new HashMap<>(13);
  
  private static final long e;
  
  public static boolean v(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/String
    //   7: astore_1
    //   8: dup
    //   9: iconst_1
    //   10: aaload
    //   11: checkcast java/lang/Long
    //   14: invokevirtual longValue : ()J
    //   17: lstore_2
    //   18: pop
    //   19: getstatic wtf/opal/df.a : J
    //   22: lload_2
    //   23: lxor
    //   24: lstore_2
    //   25: invokestatic F : ()Ljava/lang/String;
    //   28: astore #4
    //   30: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   33: aload #4
    //   35: ifnonnull -> 60
    //   38: invokevirtual method_1542 : ()Z
    //   41: ifeq -> 57
    //   44: goto -> 51
    //   47: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   50: athrow
    //   51: iconst_0
    //   52: ireturn
    //   53: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   56: athrow
    //   57: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   60: invokevirtual method_1558 : ()Lnet/minecraft/class_642;
    //   63: astore #5
    //   65: aload #5
    //   67: lload_2
    //   68: lconst_0
    //   69: lcmp
    //   70: iflt -> 101
    //   73: aload #4
    //   75: ifnonnull -> 101
    //   78: ifnonnull -> 94
    //   81: goto -> 88
    //   84: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   87: athrow
    //   88: iconst_0
    //   89: ireturn
    //   90: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   93: athrow
    //   94: aload_1
    //   95: invokevirtual toLowerCase : ()Ljava/lang/String;
    //   98: astore_1
    //   99: aload #5
    //   101: getfield field_3761 : Ljava/lang/String;
    //   104: aload_1
    //   105: invokevirtual equals : (Ljava/lang/Object;)Z
    //   108: aload #4
    //   110: ifnonnull -> 160
    //   113: ifne -> 159
    //   116: goto -> 123
    //   119: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   122: athrow
    //   123: aload #5
    //   125: getfield field_3761 : Ljava/lang/String;
    //   128: aload_1
    //   129: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;)Ljava/lang/String;
    //   134: invokevirtual endsWith : (Ljava/lang/String;)Z
    //   137: aload #4
    //   139: ifnonnull -> 160
    //   142: goto -> 149
    //   145: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   148: athrow
    //   149: ifeq -> 163
    //   152: goto -> 159
    //   155: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   158: athrow
    //   159: iconst_1
    //   160: goto -> 164
    //   163: iconst_0
    //   164: ireturn
    // Exception table:
    //   from	to	target	type
    //   30	44	47	wtf/opal/x5
    //   38	53	53	wtf/opal/x5
    //   65	81	84	wtf/opal/x5
    //   78	90	90	wtf/opal/x5
    //   101	116	119	wtf/opal/x5
    //   113	142	145	wtf/opal/x5
    //   123	152	155	wtf/opal/x5
  }
  
  public static boolean x(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_1
    //   11: pop
    //   12: getstatic wtf/opal/df.a : J
    //   15: lload_1
    //   16: lxor
    //   17: lstore_1
    //   18: invokestatic F : ()Ljava/lang/String;
    //   21: astore_3
    //   22: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   25: aload_3
    //   26: ifnonnull -> 52
    //   29: getfield field_1724 : Lnet/minecraft/class_746;
    //   32: ifnull -> 69
    //   35: goto -> 42
    //   38: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   41: athrow
    //   42: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   45: goto -> 52
    //   48: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   51: athrow
    //   52: aload_3
    //   53: ifnonnull -> 78
    //   56: invokevirtual method_1542 : ()Z
    //   59: ifeq -> 75
    //   62: goto -> 69
    //   65: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   68: athrow
    //   69: iconst_0
    //   70: ireturn
    //   71: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   74: athrow
    //   75: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   78: invokevirtual method_1558 : ()Lnet/minecraft/class_642;
    //   81: astore #4
    //   83: aload #4
    //   85: ifnonnull -> 94
    //   88: iconst_0
    //   89: ireturn
    //   90: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   93: athrow
    //   94: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   97: getfield field_1724 : Lnet/minecraft/class_746;
    //   100: getfield field_3944 : Lnet/minecraft/class_634;
    //   103: invokevirtual method_52790 : ()Ljava/lang/String;
    //   106: astore #5
    //   108: aload #5
    //   110: aload_3
    //   111: lload_1
    //   112: lconst_0
    //   113: lcmp
    //   114: ifle -> 145
    //   117: ifnonnull -> 132
    //   120: ifnull -> 166
    //   123: goto -> 130
    //   126: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   129: athrow
    //   130: aload #5
    //   132: sipush #30765
    //   135: ldc2_w 6568615062370014356
    //   138: lload_1
    //   139: lxor
    //   140: <illegal opcode> y : (IJ)Ljava/lang/String;
    //   145: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   148: aload_3
    //   149: ifnonnull -> 163
    //   152: ifeq -> 166
    //   155: goto -> 162
    //   158: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   161: athrow
    //   162: iconst_1
    //   163: goto -> 167
    //   166: iconst_0
    //   167: ireturn
    // Exception table:
    //   from	to	target	type
    //   22	35	38	wtf/opal/x5
    //   29	45	48	wtf/opal/x5
    //   52	62	65	wtf/opal/x5
    //   56	71	71	wtf/opal/x5
    //   83	90	90	wtf/opal/x5
    //   108	123	126	wtf/opal/x5
    //   132	155	158	wtf/opal/x5
  }
  
  public static boolean h(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_1
    //   11: pop
    //   12: getstatic wtf/opal/df.a : J
    //   15: lload_1
    //   16: lxor
    //   17: lstore_1
    //   18: invokestatic F : ()Ljava/lang/String;
    //   21: astore_3
    //   22: getstatic wtf/opal/df.x : Z
    //   25: aload_3
    //   26: ifnonnull -> 96
    //   29: ifne -> 88
    //   32: goto -> 39
    //   35: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   38: athrow
    //   39: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   42: lload_1
    //   43: lconst_0
    //   44: lcmp
    //   45: iflt -> 82
    //   48: aload_3
    //   49: ifnonnull -> 82
    //   52: goto -> 59
    //   55: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   58: athrow
    //   59: getfield field_1724 : Lnet/minecraft/class_746;
    //   62: ifnull -> 88
    //   65: goto -> 72
    //   68: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   71: athrow
    //   72: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   75: goto -> 82
    //   78: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   81: athrow
    //   82: getfield field_1687 : Lnet/minecraft/class_638;
    //   85: ifnonnull -> 97
    //   88: iconst_1
    //   89: goto -> 96
    //   92: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   95: athrow
    //   96: ireturn
    //   97: getstatic wtf/opal/df.X : Z
    //   100: aload_3
    //   101: ifnonnull -> 115
    //   104: ifeq -> 116
    //   107: goto -> 114
    //   110: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   113: athrow
    //   114: iconst_0
    //   115: ireturn
    //   116: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   119: getfield field_1687 : Lnet/minecraft/class_638;
    //   122: invokevirtual method_18112 : ()Ljava/lang/Iterable;
    //   125: invokeinterface iterator : ()Ljava/util/Iterator;
    //   130: astore #4
    //   132: aload #4
    //   134: invokeinterface hasNext : ()Z
    //   139: ifeq -> 232
    //   142: aload #4
    //   144: invokeinterface next : ()Ljava/lang/Object;
    //   149: checkcast net/minecraft/class_1297
    //   152: astore #5
    //   154: aload #5
    //   156: invokevirtual method_5477 : ()Lnet/minecraft/class_2561;
    //   159: invokeinterface getString : ()Ljava/lang/String;
    //   164: sipush #32046
    //   167: ldc2_w 6768732145800990325
    //   170: lload_1
    //   171: lxor
    //   172: <illegal opcode> y : (IJ)Ljava/lang/String;
    //   177: invokevirtual equals : (Ljava/lang/Object;)Z
    //   180: aload_3
    //   181: lload_1
    //   182: lconst_0
    //   183: lcmp
    //   184: ifle -> 248
    //   187: ifnonnull -> 247
    //   190: aload_3
    //   191: ifnonnull -> 227
    //   194: goto -> 201
    //   197: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   200: athrow
    //   201: ifeq -> 228
    //   204: goto -> 211
    //   207: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   210: athrow
    //   211: iconst_1
    //   212: dup
    //   213: putstatic wtf/opal/df.X : Z
    //   216: putstatic wtf/opal/df.x : Z
    //   219: iconst_1
    //   220: goto -> 227
    //   223: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   226: athrow
    //   227: ireturn
    //   228: aload_3
    //   229: ifnull -> 132
    //   232: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   235: getfield field_1724 : Lnet/minecraft/class_746;
    //   238: lload_1
    //   239: lconst_0
    //   240: lcmp
    //   241: iflt -> 152
    //   244: getfield field_6012 : I
    //   247: aload_3
    //   248: ifnonnull -> 281
    //   251: getstatic wtf/opal/df.e : J
    //   254: l2i
    //   255: if_icmple -> 280
    //   258: goto -> 265
    //   261: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   264: athrow
    //   265: iconst_0
    //   266: putstatic wtf/opal/df.x : Z
    //   269: iconst_1
    //   270: putstatic wtf/opal/df.X : Z
    //   273: goto -> 280
    //   276: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   279: athrow
    //   280: iconst_0
    //   281: ireturn
    // Exception table:
    //   from	to	target	type
    //   22	32	35	wtf/opal/x5
    //   29	52	55	wtf/opal/x5
    //   39	65	68	wtf/opal/x5
    //   59	75	78	wtf/opal/x5
    //   82	89	92	wtf/opal/x5
    //   97	107	110	wtf/opal/x5
    //   154	194	197	wtf/opal/x5
    //   190	204	207	wtf/opal/x5
    //   201	220	223	wtf/opal/x5
    //   247	258	261	wtf/opal/x5
    //   251	273	276	wtf/opal/x5
  }
  
  public static void w(Object[] paramArrayOfObject) {
    x = X = false;
  }
  
  static {
    long l = a ^ 0x420C6A7752A4L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[2];
    boolean bool = false;
    String str;
    int i = (str = "x\033½ØÊ8\026#R$:l¤ø4:\017X@q\017U./ºôNæÑ(¨\n4g\005+u%Ö\016ðs½ê\n² ¹\025½ß3põ\000ÉÉ\033ïxþìb").length();
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x5B1F;
    if (c[i] == null) {
      Object[] arrayOfObject;
      try {
        Long long_ = Long.valueOf(Thread.currentThread().threadId());
        arrayOfObject = (Object[])d.get(long_);
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/PKCS5Padding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          d.put(long_, arrayOfObject);
        } 
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/df", exception);
      } 
      byte[] arrayOfByte1 = new byte[8];
      arrayOfByte1[0] = (byte)(int)(paramLong >>> 56L);
      for (byte b = 1; b < 8; b++)
        arrayOfByte1[b] = (byte)(int)(paramLong << b * 8 >>> 56L); 
      DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
      SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
      ((Cipher)arrayOfObject[0]).init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
      byte[] arrayOfByte2 = b[i].getBytes("ISO-8859-1");
      c[i] = a(((Cipher)arrayOfObject[0]).doFinal(arrayOfByte2));
    } 
    return c[i];
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
    //   65: ldc_w 'wtf/opal/df'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\df.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */