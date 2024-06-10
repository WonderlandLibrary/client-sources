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
import net.minecraft.class_1309;
import net.minecraft.class_1657;

public final class j6 extends d {
  private final ky<bm> G;
  
  private final Map<String, b2> B;
  
  private final gm<pt> q;
  
  private final gm<l7> l;
  
  private final gm<lm> w;
  
  private final gm<d4> J;
  
  private static final long a = on.a(-2529867306088314908L, 1527909963410301191L, MethodHandles.lookup().lookupClass()).a(85740307930378L);
  
  private static final String[] b;
  
  private static final String[] d;
  
  private static final Map f = new HashMap<>(13);
  
  private static final long[] g;
  
  private static final Integer[] k;
  
  private static final Map m;
  
  public j6(long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/j6.a : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: lload_1
    //   7: dup2
    //   8: ldc2_w 39131662148418
    //   11: lxor
    //   12: lstore_3
    //   13: dup2
    //   14: ldc2_w 15297040759319
    //   17: lxor
    //   18: lstore #5
    //   20: pop2
    //   21: aload_0
    //   22: sipush #16832
    //   25: ldc2_w 6695677345063427509
    //   28: lload_1
    //   29: lxor
    //   30: <illegal opcode> f : (IJ)Ljava/lang/String;
    //   35: lload #5
    //   37: sipush #26344
    //   40: ldc2_w 3947569026417982110
    //   43: lload_1
    //   44: lxor
    //   45: <illegal opcode> f : (IJ)Ljava/lang/String;
    //   50: getstatic wtf/opal/kn.VISUAL : Lwtf/opal/kn;
    //   53: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   56: aload_0
    //   57: new wtf/opal/ky
    //   60: dup
    //   61: sipush #19921
    //   64: ldc2_w 1543322222428017062
    //   67: lload_1
    //   68: lxor
    //   69: <illegal opcode> f : (IJ)Ljava/lang/String;
    //   74: getstatic wtf/opal/bm.BLOOD_EXPLOSION : Lwtf/opal/bm;
    //   77: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Enum;)V
    //   80: putfield G : Lwtf/opal/ky;
    //   83: aload_0
    //   84: new java/util/concurrent/ConcurrentHashMap
    //   87: dup
    //   88: invokespecial <init> : ()V
    //   91: putfield B : Ljava/util/Map;
    //   94: aload_0
    //   95: aload_0
    //   96: <illegal opcode> H : (Lwtf/opal/j6;)Lwtf/opal/gm;
    //   101: putfield q : Lwtf/opal/gm;
    //   104: aload_0
    //   105: aload_0
    //   106: <illegal opcode> H : (Lwtf/opal/j6;)Lwtf/opal/gm;
    //   111: putfield l : Lwtf/opal/gm;
    //   114: aload_0
    //   115: aload_0
    //   116: <illegal opcode> H : (Lwtf/opal/j6;)Lwtf/opal/gm;
    //   121: putfield w : Lwtf/opal/gm;
    //   124: aload_0
    //   125: aload_0
    //   126: <illegal opcode> H : (Lwtf/opal/j6;)Lwtf/opal/gm;
    //   131: putfield J : Lwtf/opal/gm;
    //   134: aload_0
    //   135: iconst_1
    //   136: anewarray wtf/opal/k3
    //   139: dup
    //   140: iconst_0
    //   141: aload_0
    //   142: getfield G : Lwtf/opal/ky;
    //   145: aastore
    //   146: lload_3
    //   147: dup2_x1
    //   148: pop2
    //   149: iconst_2
    //   150: anewarray java/lang/Object
    //   153: dup_x1
    //   154: swap
    //   155: iconst_1
    //   156: swap
    //   157: aastore
    //   158: dup_x2
    //   159: dup_x2
    //   160: pop
    //   161: invokestatic valueOf : (J)Ljava/lang/Long;
    //   164: iconst_0
    //   165: swap
    //   166: aastore
    //   167: invokevirtual o : ([Ljava/lang/Object;)V
    //   170: return
  }
  
  public bm r(Object[] paramArrayOfObject) {
    return this.G.z();
  }
  
  private void lambda$new$3(d4 paramd4) {
    this.B.clear();
  }
  
  private void lambda$new$2(lm paramlm) {
    long l = a ^ 0x5BD4C5AB5E1L;
    String str = jt.S();
    try {
      if (str == null)
        try {
          if (!paramlm.B(new Object[0])) {
          
          } else {
            return;
          } 
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    class_1309 class_1309 = paramlm.j(new Object[0]);
    try {
      if (str == null)
        try {
          if (class_1309 instanceof class_1657) {
          
          } else {
            return;
          } 
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    class_1657 class_1657 = (class_1657)class_1309;
    if (!class_1657.equals(b9.c.field_1724)) {
      b2 b2 = new b2(class_1657.method_23317(), class_1657.method_23318(), class_1657.method_23321(), class_1657.method_18381(class_1657.method_18376()));
      this.B.put(class_1657.method_7334().getName(), b2);
    } 
  }
  
  private void lambda$new$1(l7 paraml7) {
    // Byte code:
    //   0: getstatic wtf/opal/j6.a : J
    //   3: ldc2_w 63998354200813
    //   6: lxor
    //   7: lstore_2
    //   8: invokestatic S : ()Ljava/lang/String;
    //   11: astore #4
    //   13: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   16: aload #4
    //   18: ifnonnull -> 44
    //   21: getfield field_1724 : Lnet/minecraft/class_746;
    //   24: ifnull -> 50
    //   27: goto -> 34
    //   30: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   33: athrow
    //   34: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   37: goto -> 44
    //   40: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   43: athrow
    //   44: invokevirtual method_1542 : ()Z
    //   47: ifeq -> 55
    //   50: return
    //   51: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   54: athrow
    //   55: aload_1
    //   56: iconst_0
    //   57: anewarray java/lang/Object
    //   60: invokevirtual T : ([Ljava/lang/Object;)Lnet/minecraft/class_2561;
    //   63: invokeinterface getString : ()Ljava/lang/String;
    //   68: astore #5
    //   70: aload #5
    //   72: sipush #18993
    //   75: ldc2_w 495601574679486498
    //   78: lload_2
    //   79: lxor
    //   80: <illegal opcode> n : (IJ)I
    //   85: invokevirtual indexOf : (I)I
    //   88: iconst_m1
    //   89: aload #4
    //   91: ifnonnull -> 142
    //   94: if_icmpne -> 262
    //   97: goto -> 104
    //   100: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   103: athrow
    //   104: aload #5
    //   106: sipush #8269
    //   109: ldc2_w 3744672778851699295
    //   112: lload_2
    //   113: lxor
    //   114: <illegal opcode> n : (IJ)I
    //   119: invokevirtual indexOf : (I)I
    //   122: aload #4
    //   124: ifnonnull -> 202
    //   127: goto -> 134
    //   130: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   133: athrow
    //   134: iconst_m1
    //   135: goto -> 142
    //   138: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   141: athrow
    //   142: if_icmpne -> 262
    //   145: aload #5
    //   147: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   150: getfield field_1724 : Lnet/minecraft/class_746;
    //   153: invokevirtual method_5477 : ()Lnet/minecraft/class_2561;
    //   156: invokeinterface getString : ()Ljava/lang/String;
    //   161: sipush #2992
    //   164: ldc2_w 6330438555303794062
    //   167: lload_2
    //   168: lxor
    //   169: <illegal opcode> f : (IJ)Ljava/lang/String;
    //   174: swap
    //   175: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   180: aload #4
    //   182: ifnonnull -> 217
    //   185: goto -> 192
    //   188: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   191: athrow
    //   192: invokevirtual contains : (Ljava/lang/CharSequence;)Z
    //   195: goto -> 202
    //   198: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   201: athrow
    //   202: ifeq -> 262
    //   205: aload #5
    //   207: ldc_w ' '
    //   210: goto -> 217
    //   213: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   216: athrow
    //   217: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
    //   220: iconst_0
    //   221: aaload
    //   222: astore #6
    //   224: aload_0
    //   225: getfield B : Ljava/util/Map;
    //   228: aload #6
    //   230: invokeinterface remove : (Ljava/lang/Object;)Ljava/lang/Object;
    //   235: checkcast wtf/opal/b2
    //   238: astore #7
    //   240: aload #7
    //   242: aload #4
    //   244: ifnonnull -> 259
    //   247: ifnull -> 262
    //   250: goto -> 257
    //   253: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   256: athrow
    //   257: aload #7
    //   259: invokevirtual a : ()V
    //   262: return
    // Exception table:
    //   from	to	target	type
    //   13	27	30	wtf/opal/x5
    //   21	37	40	wtf/opal/x5
    //   44	51	51	wtf/opal/x5
    //   70	97	100	wtf/opal/x5
    //   94	127	130	wtf/opal/x5
    //   104	135	138	wtf/opal/x5
    //   142	185	188	wtf/opal/x5
    //   145	195	198	wtf/opal/x5
    //   202	210	213	wtf/opal/x5
    //   240	250	253	wtf/opal/x5
  }
  
  private void lambda$new$0(pt parampt) {
    // Byte code:
    //   0: getstatic wtf/opal/j6.a : J
    //   3: ldc2_w 40624817513783
    //   6: lxor
    //   7: lstore_2
    //   8: invokestatic S : ()Ljava/lang/String;
    //   11: aload_1
    //   12: iconst_0
    //   13: anewarray java/lang/Object
    //   16: invokevirtual v : ([Ljava/lang/Object;)Lnet/minecraft/class_1309;
    //   19: astore #5
    //   21: astore #4
    //   23: aload #5
    //   25: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   28: getfield field_1724 : Lnet/minecraft/class_746;
    //   31: if_acmpeq -> 64
    //   34: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   37: getfield field_1724 : Lnet/minecraft/class_746;
    //   40: aload_1
    //   41: iconst_0
    //   42: anewarray java/lang/Object
    //   45: invokevirtual S : ([Ljava/lang/Object;)Lnet/minecraft/class_1282;
    //   48: invokevirtual method_5529 : ()Lnet/minecraft/class_1297;
    //   51: invokevirtual equals : (Ljava/lang/Object;)Z
    //   54: ifne -> 69
    //   57: goto -> 64
    //   60: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   63: athrow
    //   64: return
    //   65: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   68: athrow
    //   69: aconst_null
    //   70: astore #6
    //   72: aload #5
    //   74: aload #4
    //   76: ifnonnull -> 101
    //   79: instanceof net/minecraft/class_1657
    //   82: ifeq -> 127
    //   85: goto -> 92
    //   88: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   91: athrow
    //   92: aload #5
    //   94: goto -> 101
    //   97: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   100: athrow
    //   101: invokevirtual method_5477 : ()Lnet/minecraft/class_2561;
    //   104: invokeinterface getString : ()Ljava/lang/String;
    //   109: astore #7
    //   111: aload_0
    //   112: getfield B : Ljava/util/Map;
    //   115: aload #7
    //   117: invokeinterface remove : (Ljava/lang/Object;)Ljava/lang/Object;
    //   122: checkcast wtf/opal/b2
    //   125: astore #6
    //   127: aload #6
    //   129: aload #4
    //   131: ifnonnull -> 181
    //   134: ifnonnull -> 179
    //   137: goto -> 144
    //   140: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   143: athrow
    //   144: new wtf/opal/b2
    //   147: dup
    //   148: aload #5
    //   150: invokevirtual method_23317 : ()D
    //   153: aload #5
    //   155: invokevirtual method_23318 : ()D
    //   158: aload #5
    //   160: invokevirtual method_23321 : ()D
    //   163: aload #5
    //   165: aload #5
    //   167: invokevirtual method_18376 : ()Lnet/minecraft/class_4050;
    //   170: invokevirtual method_18381 : (Lnet/minecraft/class_4050;)F
    //   173: f2d
    //   174: invokespecial <init> : (DDDD)V
    //   177: astore #6
    //   179: aload #6
    //   181: invokevirtual a : ()V
    //   184: return
    // Exception table:
    //   from	to	target	type
    //   23	57	60	wtf/opal/x5
    //   34	65	65	wtf/opal/x5
    //   72	85	88	wtf/opal/x5
    //   79	94	97	wtf/opal/x5
    //   127	137	140	wtf/opal/x5
  }
  
  static {
    long l = a ^ 0x41B9ED6DCD90L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[4];
    boolean bool = false;
    String str;
    int i = (str = "e^\023©\020\020k¨f!\022ÏsPícüõ[¾;Ç\0344¼Ä&­¡¬'>5XÂs×½F=d£×ÿd¦[³>÷ÇáèÉò\017ìG\fè\024,»[=à\016Òá[¶vø ÐSô<\003»ñêã").length();
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x7C09;
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
        throw new RuntimeException("wtf/opal/j6", exception);
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
    //   66: ldc_w 'wtf/opal/j6'
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x4827;
    if (k[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l = g[i];
      byte[] arrayOfByte2 = { (byte)(int)(l >>> 56L), (byte)(int)(l >>> 48L), (byte)(int)(l >>> 40L), (byte)(int)(l >>> 32L), (byte)(int)(l >>> 24L), (byte)(int)(l >>> 16L), (byte)(int)(l >>> 8L), (byte)(int)l };
      Long long_ = Long.valueOf(Thread.currentThread().threadId());
      Object[] arrayOfObject = (Object[])m.get(long_);
      try {
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/NoPadding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          m.put(long_, arrayOfObject);
        } 
        DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
        SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
        Cipher cipher = (Cipher)arrayOfObject[0];
        cipher.init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
        arrayOfByte3 = cipher.doFinal(arrayOfByte2);
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/j6", exception);
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
    //   66: ldc_w 'wtf/opal/j6'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\j6.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */