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

public final class jf extends d {
  private final ke R;
  
  private final gm<lu> Y;
  
  private static final long a = on.a(6787167660292213373L, 56865964871389700L, MethodHandles.lookup().lookupClass()).a(111181148733133L);
  
  private static final String[] b;
  
  private static final String[] d;
  
  private static final Map f = new HashMap<>(13);
  
  private static final long[] g;
  
  private static final Integer[] k;
  
  private static final Map l;
  
  private static final long m;
  
  public jf(int paramInt, char paramChar1, char paramChar2) {
    // Byte code:
    //   0: iload_1
    //   1: i2l
    //   2: bipush #32
    //   4: lshl
    //   5: iload_2
    //   6: i2l
    //   7: bipush #48
    //   9: lshl
    //   10: bipush #32
    //   12: lushr
    //   13: lor
    //   14: iload_3
    //   15: i2l
    //   16: bipush #48
    //   18: lshl
    //   19: bipush #48
    //   21: lushr
    //   22: lor
    //   23: getstatic wtf/opal/jf.a : J
    //   26: lxor
    //   27: lstore #4
    //   29: lload #4
    //   31: dup2
    //   32: ldc2_w 63569938852964
    //   35: lxor
    //   36: lstore #6
    //   38: dup2
    //   39: ldc2_w 26042901460273
    //   42: lxor
    //   43: lstore #8
    //   45: pop2
    //   46: aload_0
    //   47: sipush #16294
    //   50: ldc2_w 5580319720123882864
    //   53: lload #4
    //   55: lxor
    //   56: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   61: lload #8
    //   63: sipush #721
    //   66: ldc2_w 5611365015838646278
    //   69: lload #4
    //   71: lxor
    //   72: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   77: getstatic wtf/opal/kn.PLAYER : Lwtf/opal/kn;
    //   80: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   83: aload_0
    //   84: new wtf/opal/ke
    //   87: dup
    //   88: sipush #27412
    //   91: ldc2_w 8054889735338363335
    //   94: lload #4
    //   96: lxor
    //   97: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   102: iconst_0
    //   103: invokespecial <init> : (Ljava/lang/String;Z)V
    //   106: putfield R : Lwtf/opal/ke;
    //   109: aload_0
    //   110: aload_0
    //   111: <illegal opcode> H : (Lwtf/opal/jf;)Lwtf/opal/gm;
    //   116: putfield Y : Lwtf/opal/gm;
    //   119: aload_0
    //   120: iconst_1
    //   121: anewarray wtf/opal/k3
    //   124: dup
    //   125: iconst_0
    //   126: aload_0
    //   127: getfield R : Lwtf/opal/ke;
    //   130: aastore
    //   131: lload #6
    //   133: dup2_x1
    //   134: pop2
    //   135: iconst_2
    //   136: anewarray java/lang/Object
    //   139: dup_x1
    //   140: swap
    //   141: iconst_1
    //   142: swap
    //   143: aastore
    //   144: dup_x2
    //   145: dup_x2
    //   146: pop
    //   147: invokestatic valueOf : (J)Ljava/lang/Long;
    //   150: iconst_0
    //   151: swap
    //   152: aastore
    //   153: invokevirtual o : ([Ljava/lang/Object;)V
    //   156: return
  }
  
  private void lambda$new$0(lu paramlu) {
    // Byte code:
    //   0: getstatic wtf/opal/jf.a : J
    //   3: ldc2_w 80831477785088
    //   6: lxor
    //   7: lstore_2
    //   8: invokestatic n : ()Z
    //   11: aload_1
    //   12: iconst_0
    //   13: anewarray java/lang/Object
    //   16: invokevirtual g : ([Ljava/lang/Object;)Lnet/minecraft/class_2596;
    //   19: astore #6
    //   21: istore #4
    //   23: aload #6
    //   25: iload #4
    //   27: ifne -> 52
    //   30: instanceof net/minecraft/class_7439
    //   33: ifeq -> 361
    //   36: goto -> 43
    //   39: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   42: athrow
    //   43: aload #6
    //   45: goto -> 52
    //   48: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   51: athrow
    //   52: checkcast net/minecraft/class_7439
    //   55: astore #5
    //   57: aload #5
    //   59: invokevirtual comp_763 : ()Lnet/minecraft/class_2561;
    //   62: invokeinterface getString : ()Ljava/lang/String;
    //   67: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   70: getfield field_1724 : Lnet/minecraft/class_746;
    //   73: invokevirtual method_5477 : ()Lnet/minecraft/class_2561;
    //   76: invokeinterface getString : ()Ljava/lang/String;
    //   81: invokevirtual contains : (Ljava/lang/CharSequence;)Z
    //   84: iload #4
    //   86: ifne -> 144
    //   89: ifeq -> 361
    //   92: goto -> 99
    //   95: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   98: athrow
    //   99: aload #5
    //   101: invokevirtual comp_763 : ()Lnet/minecraft/class_2561;
    //   104: invokeinterface getString : ()Ljava/lang/String;
    //   109: iload #4
    //   111: ifne -> 224
    //   114: goto -> 121
    //   117: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   120: athrow
    //   121: sipush #15109
    //   124: ldc2_w 3985515314273466091
    //   127: lload_2
    //   128: lxor
    //   129: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   134: invokevirtual contains : (Ljava/lang/CharSequence;)Z
    //   137: goto -> 144
    //   140: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   143: athrow
    //   144: ifeq -> 361
    //   147: new java/util/Random
    //   150: dup
    //   151: invokespecial <init> : ()V
    //   154: sipush #30934
    //   157: ldc2_w 2751861152707537226
    //   160: lload_2
    //   161: lxor
    //   162: <illegal opcode> q : (IJ)I
    //   167: sipush #23552
    //   170: ldc2_w 4892484002270399901
    //   173: lload_2
    //   174: lxor
    //   175: <illegal opcode> q : (IJ)I
    //   180: invokevirtual ints : (II)Ljava/util/stream/IntStream;
    //   183: getstatic wtf/opal/jf.m : J
    //   186: invokeinterface limit : (J)Ljava/util/stream/IntStream;
    //   191: <illegal opcode> get : ()Ljava/util/function/Supplier;
    //   196: <illegal opcode> accept : ()Ljava/util/function/ObjIntConsumer;
    //   201: <illegal opcode> accept : ()Ljava/util/function/BiConsumer;
    //   206: invokeinterface collect : (Ljava/util/function/Supplier;Ljava/util/function/ObjIntConsumer;Ljava/util/function/BiConsumer;)Ljava/lang/Object;
    //   211: checkcast java/lang/StringBuilder
    //   214: invokevirtual toString : ()Ljava/lang/String;
    //   217: goto -> 224
    //   220: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   223: athrow
    //   224: astore #6
    //   226: aload #5
    //   228: invokevirtual comp_763 : ()Lnet/minecraft/class_2561;
    //   231: invokeinterface getString : ()Ljava/lang/String;
    //   236: ldc ' '
    //   238: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
    //   241: astore #7
    //   243: aload #7
    //   245: iconst_3
    //   246: aaload
    //   247: invokevirtual toUpperCase : ()Ljava/lang/String;
    //   250: aload #6
    //   252: sipush #18779
    //   255: ldc2_w 8094623535402402999
    //   258: lload_2
    //   259: lxor
    //   260: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   265: swap
    //   266: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   271: astore #8
    //   273: iload #4
    //   275: ifne -> 338
    //   278: aload_0
    //   279: getfield R : Lwtf/opal/ke;
    //   282: invokevirtual z : ()Ljava/lang/Object;
    //   285: checkcast java/lang/Boolean
    //   288: invokevirtual booleanValue : ()Z
    //   291: ifeq -> 343
    //   294: goto -> 301
    //   297: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   300: athrow
    //   301: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   304: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   307: aload #8
    //   309: sipush #15735
    //   312: ldc2_w 638281834713034906
    //   315: lload_2
    //   316: lxor
    //   317: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   322: swap
    //   323: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   328: invokevirtual method_45729 : (Ljava/lang/String;)V
    //   331: goto -> 338
    //   334: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   337: athrow
    //   338: iload #4
    //   340: ifeq -> 361
    //   343: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   346: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   349: aload #8
    //   351: invokevirtual method_45729 : (Ljava/lang/String;)V
    //   354: goto -> 361
    //   357: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   360: athrow
    //   361: return
    // Exception table:
    //   from	to	target	type
    //   23	36	39	wtf/opal/x5
    //   30	45	48	wtf/opal/x5
    //   57	92	95	wtf/opal/x5
    //   89	114	117	wtf/opal/x5
    //   99	137	140	wtf/opal/x5
    //   144	217	220	wtf/opal/x5
    //   273	294	297	wtf/opal/x5
    //   278	331	334	wtf/opal/x5
    //   338	354	357	wtf/opal/x5
  }
  
  static {
    long l = a ^ 0x33A7AA473E8L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[6];
    boolean bool = false;
    String str;
    int i = (str = "1Ì«s°¥Àæ\037²L¨'Êð(ÊIáóøÊp.\030W(XÌÑ¿'Ó\f\fâÜË\030bË\025^±\0241\013Æä\020t¤1Î Ú^øXøz·ýeòH$#=[ëGÉ\025J\016\026´YÞÞY,#\026èô[¸Ã\017Ö3åY òà\005O|n\"\0303\031\003\007\034$ÚK\025Êc`¾zõ×þ-\005PÁDÆBð\005psä").length();
    byte b2 = 16;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x4D8B;
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
        throw new RuntimeException("wtf/opal/jf", exception);
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
    //   66: ldc_w 'wtf/opal/jf'
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
  
  private static int b(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x51F8;
    if (k[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l = g[i];
      byte[] arrayOfByte2 = { (byte)(int)(l >>> 56L), (byte)(int)(l >>> 48L), (byte)(int)(l >>> 40L), (byte)(int)(l >>> 32L), (byte)(int)(l >>> 24L), (byte)(int)(l >>> 16L), (byte)(int)(l >>> 8L), (byte)(int)l };
      Long long_ = Long.valueOf(Thread.currentThread().threadId());
      Object[] arrayOfObject = (Object[])l.get(long_);
      try {
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/NoPadding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          l.put(long_, arrayOfObject);
        } 
        DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
        SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
        Cipher cipher = (Cipher)arrayOfObject[0];
        cipher.init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
        arrayOfByte3 = cipher.doFinal(arrayOfByte2);
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/jf", exception);
      } 
      int j = (arrayOfByte3[4] & 0xFF) << 24 | (arrayOfByte3[5] & 0xFF) << 16 | (arrayOfByte3[6] & 0xFF) << 8 | arrayOfByte3[7] & 0xFF;
      k[i] = Integer.valueOf(j);
    } 
    return k[i].intValue();
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
    //   66: ldc_w 'wtf/opal/jf'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\jf.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */