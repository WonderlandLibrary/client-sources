package wtf.opal;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public final class pw extends u_<j4> {
  private boolean f;
  
  private int I;
  
  private final gm<lz> Q = this::lambda$new$0;
  
  private final gm<l7> O = this::lambda$new$1;
  
  private static boolean h;
  
  private static final long a = on.a(-2523794425717874647L, -6898373039753954540L, MethodHandles.lookup().lookupClass()).a(72441380164129L);
  
  private static final String b;
  
  private static final long[] c;
  
  private static final Integer[] d;
  
  private static final Map e;
  
  public pw(j4 paramj4) {
    super(paramj4);
  }
  
  public void X() {
    d1.q(new Object[0]).l(new Object[0]).U(new Object[] { Float.valueOf(1.0F) });
    super.X();
  }
  
  public Enum V(Object[] paramArrayOfObject) {
    return km.WATCHDOG;
  }
  
  private void lambda$new$1(l7 paraml7) {
    // Byte code:
    //   0: getstatic wtf/opal/pw.a : J
    //   3: ldc2_w 40423894899707
    //   6: lxor
    //   7: lstore_2
    //   8: invokestatic y : ()Z
    //   11: istore #4
    //   13: aload_1
    //   14: iconst_0
    //   15: anewarray java/lang/Object
    //   18: invokevirtual T : ([Ljava/lang/Object;)Lnet/minecraft/class_2561;
    //   21: invokeinterface getString : ()Ljava/lang/String;
    //   26: iload #4
    //   28: ifne -> 82
    //   31: getstatic wtf/opal/pw.b : Ljava/lang/String;
    //   34: invokevirtual equals : (Ljava/lang/Object;)Z
    //   37: ifeq -> 118
    //   40: goto -> 47
    //   43: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   46: athrow
    //   47: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   50: getfield field_1724 : Lnet/minecraft/class_746;
    //   53: invokevirtual method_31548 : ()Lnet/minecraft/class_1661;
    //   56: getfield field_7547 : Lnet/minecraft/class_2371;
    //   59: sipush #24907
    //   62: ldc2_w 858898357053251058
    //   65: lload_2
    //   66: lxor
    //   67: <illegal opcode> g : (IJ)I
    //   72: invokevirtual get : (I)Ljava/lang/Object;
    //   75: goto -> 82
    //   78: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   81: athrow
    //   82: checkcast net/minecraft/class_1799
    //   85: invokevirtual method_7909 : ()Lnet/minecraft/class_1792;
    //   88: getstatic net/minecraft/class_1802.field_8183 : Lnet/minecraft/class_1792;
    //   91: if_acmpne -> 118
    //   94: aload_0
    //   95: sipush #21520
    //   98: ldc2_w 7076583117344532648
    //   101: lload_2
    //   102: lxor
    //   103: <illegal opcode> g : (IJ)I
    //   108: putfield I : I
    //   111: goto -> 118
    //   114: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   117: athrow
    //   118: return
    // Exception table:
    //   from	to	target	type
    //   13	40	43	wtf/opal/x5
    //   31	75	78	wtf/opal/x5
    //   82	111	114	wtf/opal/x5
  }
  
  private void lambda$new$0(lz paramlz) {
    // Byte code:
    //   0: getstatic wtf/opal/pw.a : J
    //   3: ldc2_w 47848261862467
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 23280137674264
    //   13: lxor
    //   14: lstore #4
    //   16: dup2
    //   17: ldc2_w 70496805079938
    //   20: lxor
    //   21: lstore #6
    //   23: pop2
    //   24: invokestatic y : ()Z
    //   27: istore #8
    //   29: aload_0
    //   30: getfield f : Z
    //   33: iload #8
    //   35: ifne -> 92
    //   38: ifeq -> 88
    //   41: goto -> 48
    //   44: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   47: athrow
    //   48: iconst_0
    //   49: anewarray java/lang/Object
    //   52: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   55: iconst_0
    //   56: anewarray java/lang/Object
    //   59: invokevirtual l : ([Ljava/lang/Object;)Lwtf/opal/un;
    //   62: fconst_1
    //   63: iconst_1
    //   64: anewarray java/lang/Object
    //   67: dup_x1
    //   68: swap
    //   69: invokestatic valueOf : (F)Ljava/lang/Float;
    //   72: iconst_0
    //   73: swap
    //   74: aastore
    //   75: invokevirtual U : ([Ljava/lang/Object;)V
    //   78: aload_0
    //   79: iconst_0
    //   80: putfield f : Z
    //   83: return
    //   84: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   87: athrow
    //   88: aload_0
    //   89: getfield I : I
    //   92: iconst_2
    //   93: iload #8
    //   95: ifne -> 159
    //   98: if_icmpne -> 142
    //   101: goto -> 108
    //   104: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   107: athrow
    //   108: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   111: getfield field_1724 : Lnet/minecraft/class_746;
    //   114: getfield field_6017 : F
    //   117: fconst_2
    //   118: fcmpl
    //   119: ifle -> 141
    //   122: goto -> 129
    //   125: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   128: athrow
    //   129: aload_0
    //   130: iconst_1
    //   131: putfield I : I
    //   134: goto -> 141
    //   137: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   140: athrow
    //   141: return
    //   142: aload_0
    //   143: getfield I : I
    //   146: iload #8
    //   148: ifne -> 200
    //   151: iconst_1
    //   152: goto -> 159
    //   155: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   158: athrow
    //   159: if_icmpne -> 196
    //   162: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   165: getfield field_1724 : Lnet/minecraft/class_746;
    //   168: getfield field_6017 : F
    //   171: fconst_0
    //   172: fcmpl
    //   173: ifne -> 195
    //   176: goto -> 183
    //   179: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   182: athrow
    //   183: aload_0
    //   184: iconst_0
    //   185: putfield I : I
    //   188: goto -> 195
    //   191: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   194: athrow
    //   195: return
    //   196: aload_0
    //   197: getfield I : I
    //   200: iload #8
    //   202: ifne -> 234
    //   205: ifne -> 476
    //   208: goto -> 215
    //   211: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   214: athrow
    //   215: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   218: getfield field_1724 : Lnet/minecraft/class_746;
    //   221: getfield field_6017 : F
    //   224: ldc 3.0
    //   226: fcmpl
    //   227: goto -> 234
    //   230: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   233: athrow
    //   234: iload #8
    //   236: ifne -> 295
    //   239: iflt -> 476
    //   242: goto -> 249
    //   245: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   248: athrow
    //   249: sipush #16610
    //   252: ldc2_w 1196191894636422112
    //   255: lload_2
    //   256: lxor
    //   257: <illegal opcode> g : (IJ)I
    //   262: lload #6
    //   264: iconst_2
    //   265: anewarray java/lang/Object
    //   268: dup_x2
    //   269: dup_x2
    //   270: pop
    //   271: invokestatic valueOf : (J)Ljava/lang/Long;
    //   274: iconst_1
    //   275: swap
    //   276: aastore
    //   277: dup_x1
    //   278: swap
    //   279: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   282: iconst_0
    //   283: swap
    //   284: aastore
    //   285: invokestatic b : ([Ljava/lang/Object;)Z
    //   288: goto -> 295
    //   291: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   294: athrow
    //   295: iload #8
    //   297: ifne -> 329
    //   300: ifeq -> 476
    //   303: goto -> 310
    //   306: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   309: athrow
    //   310: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   313: getfield field_1724 : Lnet/minecraft/class_746;
    //   316: invokevirtual method_31549 : ()Lnet/minecraft/class_1656;
    //   319: getfield field_7478 : Z
    //   322: goto -> 329
    //   325: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   328: athrow
    //   329: iload #8
    //   331: ifne -> 363
    //   334: ifne -> 476
    //   337: goto -> 344
    //   340: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   343: athrow
    //   344: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   347: getfield field_1724 : Lnet/minecraft/class_746;
    //   350: invokevirtual method_31549 : ()Lnet/minecraft/class_1656;
    //   353: getfield field_7479 : Z
    //   356: goto -> 363
    //   359: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   362: athrow
    //   363: iload #8
    //   365: ifne -> 403
    //   368: ifne -> 476
    //   371: goto -> 378
    //   374: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   377: athrow
    //   378: lload #4
    //   380: iconst_1
    //   381: anewarray java/lang/Object
    //   384: dup_x2
    //   385: dup_x2
    //   386: pop
    //   387: invokestatic valueOf : (J)Ljava/lang/Long;
    //   390: iconst_0
    //   391: swap
    //   392: aastore
    //   393: invokestatic h : ([Ljava/lang/Object;)Z
    //   396: goto -> 403
    //   399: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   402: athrow
    //   403: ifne -> 476
    //   406: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   409: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   412: new net/minecraft/class_2828$class_5911
    //   415: dup
    //   416: iconst_1
    //   417: invokespecial <init> : (Z)V
    //   420: invokevirtual method_52787 : (Lnet/minecraft/class_2596;)V
    //   423: iconst_0
    //   424: anewarray java/lang/Object
    //   427: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   430: iconst_0
    //   431: anewarray java/lang/Object
    //   434: invokevirtual l : ([Ljava/lang/Object;)Lwtf/opal/un;
    //   437: ldc 0.5
    //   439: iconst_1
    //   440: anewarray java/lang/Object
    //   443: dup_x1
    //   444: swap
    //   445: invokestatic valueOf : (F)Ljava/lang/Float;
    //   448: iconst_0
    //   449: swap
    //   450: aastore
    //   451: invokevirtual U : ([Ljava/lang/Object;)V
    //   454: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   457: getfield field_1724 : Lnet/minecraft/class_746;
    //   460: fconst_1
    //   461: putfield field_6017 : F
    //   464: aload_0
    //   465: iconst_1
    //   466: putfield f : Z
    //   469: goto -> 476
    //   472: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   475: athrow
    //   476: return
    // Exception table:
    //   from	to	target	type
    //   29	41	44	wtf/opal/x5
    //   38	84	84	wtf/opal/x5
    //   92	101	104	wtf/opal/x5
    //   98	122	125	wtf/opal/x5
    //   108	134	137	wtf/opal/x5
    //   142	152	155	wtf/opal/x5
    //   159	176	179	wtf/opal/x5
    //   162	188	191	wtf/opal/x5
    //   200	208	211	wtf/opal/x5
    //   205	227	230	wtf/opal/x5
    //   234	242	245	wtf/opal/x5
    //   239	288	291	wtf/opal/x5
    //   295	303	306	wtf/opal/x5
    //   300	322	325	wtf/opal/x5
    //   329	337	340	wtf/opal/x5
    //   334	356	359	wtf/opal/x5
    //   363	371	374	wtf/opal/x5
    //   368	396	399	wtf/opal/x5
    //   403	469	472	wtf/opal/x5
  }
  
  public static void W(boolean paramBoolean) {
    h = paramBoolean;
  }
  
  public static boolean F() {
    return h;
  }
  
  public static boolean y() {
    boolean bool = F();
    try {
      if (!bool)
        return true; 
    } catch (x5 x5) {
      throw b(null);
    } 
    return false;
  }
  
  static {
    long l = a ^ 0x1AEA7375421FL;
    W(true);
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b = 1; b < 8; b++)
      (new byte[8])[b] = (byte)(int)(l << b * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
  }
  
  private static x5 b(x5 paramx5) {
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
  
  private static int a(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0xDA8;
    if (d[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l = c[i];
      byte[] arrayOfByte2 = { (byte)(int)(l >>> 56L), (byte)(int)(l >>> 48L), (byte)(int)(l >>> 40L), (byte)(int)(l >>> 32L), (byte)(int)(l >>> 24L), (byte)(int)(l >>> 16L), (byte)(int)(l >>> 8L), (byte)(int)l };
      Long long_ = Long.valueOf(Thread.currentThread().threadId());
      Object[] arrayOfObject = (Object[])e.get(long_);
      try {
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/NoPadding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          e.put(long_, arrayOfObject);
        } 
        DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
        SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
        Cipher cipher = (Cipher)arrayOfObject[0];
        cipher.init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
        arrayOfByte3 = cipher.doFinal(arrayOfByte2);
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/pw", exception);
      } 
      int j = (arrayOfByte3[4] & 0xFF) << 24 | (arrayOfByte3[5] & 0xFF) << 16 | (arrayOfByte3[6] & 0xFF) << 8 | arrayOfByte3[7] & 0xFF;
      d[i] = Integer.valueOf(j);
    } 
    return d[i].intValue();
  }
  
  private static int a(MethodHandles.Lookup paramLookup, MutableCallSite paramMutableCallSite, String paramString, Object[] paramArrayOfObject) {
    int i = ((Integer)paramArrayOfObject[0]).intValue();
    long l = ((Long)paramArrayOfObject[1]).longValue();
    int j = a(i, l);
    MethodHandle methodHandle = MethodHandles.constant(int.class, Integer.valueOf(j));
    paramMutableCallSite.setTarget(MethodHandles.dropArguments(methodHandle, 0, new Class[] { int.class, long.class }));
    return j;
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
    //   66: ldc_w 'wtf/opal/pw'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\pw.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */