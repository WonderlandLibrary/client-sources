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
import net.minecraft.class_243;
import net.minecraft.class_2596;
import net.minecraft.class_2828;
import wtf.opal.mixin.PlayerMoveC2SPacketAccessor;

public final class j0 extends d {
  private final kt k;
  
  private final List<class_2596<?>> T;
  
  private class_243 x;
  
  private final gm<lb> A;
  
  private static final long a = on.a(2517329200566716524L, -3085849498019697069L, MethodHandles.lookup().lookupClass()).a(152346630130409L);
  
  private static final String[] b;
  
  private static final String[] d;
  
  private static final Map f = new HashMap<>(13);
  
  private static final long g;
  
  public j0(long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/j0.a : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: lload_1
    //   7: dup2
    //   8: ldc2_w 85030666141513
    //   11: lxor
    //   12: lstore_3
    //   13: dup2
    //   14: ldc2_w 109037087237660
    //   17: lxor
    //   18: lstore #5
    //   20: pop2
    //   21: aload_0
    //   22: sipush #1104
    //   25: ldc2_w 3012907422727618164
    //   28: lload_1
    //   29: lxor
    //   30: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   35: lload #5
    //   37: sipush #26114
    //   40: ldc2_w 4349802741531040804
    //   43: lload_1
    //   44: lxor
    //   45: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   50: getstatic wtf/opal/kn.PLAYER : Lwtf/opal/kn;
    //   53: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   56: aload_0
    //   57: new wtf/opal/kt
    //   60: dup
    //   61: sipush #20740
    //   64: ldc2_w 1630776565742398243
    //   67: lload_1
    //   68: lxor
    //   69: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   74: ldc2_w 3.0
    //   77: dconst_1
    //   78: ldc2_w 20.0
    //   81: dconst_1
    //   82: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   85: putfield k : Lwtf/opal/kt;
    //   88: aload_0
    //   89: new java/util/ArrayList
    //   92: dup
    //   93: invokespecial <init> : ()V
    //   96: putfield T : Ljava/util/List;
    //   99: aload_0
    //   100: aload_0
    //   101: <illegal opcode> H : (Lwtf/opal/j0;)Lwtf/opal/gm;
    //   106: putfield A : Lwtf/opal/gm;
    //   109: aload_0
    //   110: iconst_1
    //   111: anewarray wtf/opal/k3
    //   114: dup
    //   115: iconst_0
    //   116: aload_0
    //   117: getfield k : Lwtf/opal/kt;
    //   120: aastore
    //   121: lload_3
    //   122: dup2_x1
    //   123: pop2
    //   124: iconst_2
    //   125: anewarray java/lang/Object
    //   128: dup_x1
    //   129: swap
    //   130: iconst_1
    //   131: swap
    //   132: aastore
    //   133: dup_x2
    //   134: dup_x2
    //   135: pop
    //   136: invokestatic valueOf : (J)Ljava/lang/Long;
    //   139: iconst_0
    //   140: swap
    //   141: aastore
    //   142: invokevirtual o : ([Ljava/lang/Object;)V
    //   145: return
  }
  
  private void lambda$new$1(lb paramlb) {
    // Byte code:
    //   0: getstatic wtf/opal/j0.a : J
    //   3: ldc2_w 63161398874111
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 111890515902227
    //   13: lxor
    //   14: lstore #4
    //   16: pop2
    //   17: invokestatic k : ()Z
    //   20: aload_1
    //   21: iconst_0
    //   22: anewarray java/lang/Object
    //   25: invokevirtual J : ([Ljava/lang/Object;)Lnet/minecraft/class_2596;
    //   28: astore #8
    //   30: istore #6
    //   32: aload #8
    //   34: iload #6
    //   36: ifeq -> 61
    //   39: instanceof net/minecraft/class_2828
    //   42: ifeq -> 530
    //   45: goto -> 52
    //   48: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   51: athrow
    //   52: aload #8
    //   54: goto -> 61
    //   57: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   60: athrow
    //   61: checkcast net/minecraft/class_2828
    //   64: astore #7
    //   66: getstatic wtf/opal/j0.g : J
    //   69: l2i
    //   70: lload #4
    //   72: iconst_2
    //   73: anewarray java/lang/Object
    //   76: dup_x2
    //   77: dup_x2
    //   78: pop
    //   79: invokestatic valueOf : (J)Ljava/lang/Long;
    //   82: iconst_1
    //   83: swap
    //   84: aastore
    //   85: dup_x1
    //   86: swap
    //   87: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   90: iconst_0
    //   91: swap
    //   92: aastore
    //   93: invokestatic b : ([Ljava/lang/Object;)Z
    //   96: iload #6
    //   98: ifeq -> 483
    //   101: ifne -> 442
    //   104: goto -> 111
    //   107: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   110: athrow
    //   111: iconst_0
    //   112: anewarray java/lang/Object
    //   115: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   118: iconst_0
    //   119: anewarray java/lang/Object
    //   122: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   125: ldc wtf/opal/xw
    //   127: iconst_1
    //   128: anewarray java/lang/Object
    //   131: dup_x1
    //   132: swap
    //   133: iconst_0
    //   134: swap
    //   135: aastore
    //   136: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   139: checkcast wtf/opal/xw
    //   142: iconst_0
    //   143: anewarray java/lang/Object
    //   146: invokevirtual D : ([Ljava/lang/Object;)Z
    //   149: iload #6
    //   151: ifeq -> 483
    //   154: goto -> 161
    //   157: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   160: athrow
    //   161: ifne -> 442
    //   164: goto -> 171
    //   167: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   170: athrow
    //   171: iconst_0
    //   172: anewarray java/lang/Object
    //   175: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   178: iconst_0
    //   179: anewarray java/lang/Object
    //   182: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   185: ldc wtf/opal/s
    //   187: iconst_1
    //   188: anewarray java/lang/Object
    //   191: dup_x1
    //   192: swap
    //   193: iconst_0
    //   194: swap
    //   195: aastore
    //   196: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   199: checkcast wtf/opal/s
    //   202: iconst_0
    //   203: anewarray java/lang/Object
    //   206: invokevirtual D : ([Ljava/lang/Object;)Z
    //   209: iload #6
    //   211: ifeq -> 483
    //   214: goto -> 221
    //   217: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   220: athrow
    //   221: ifne -> 442
    //   224: goto -> 231
    //   227: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   230: athrow
    //   231: iconst_0
    //   232: anewarray java/lang/Object
    //   235: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   238: iconst_0
    //   239: anewarray java/lang/Object
    //   242: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   245: ldc wtf/opal/h
    //   247: iconst_1
    //   248: anewarray java/lang/Object
    //   251: dup_x1
    //   252: swap
    //   253: iconst_0
    //   254: swap
    //   255: aastore
    //   256: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   259: checkcast wtf/opal/h
    //   262: iconst_0
    //   263: anewarray java/lang/Object
    //   266: invokevirtual D : ([Ljava/lang/Object;)Z
    //   269: iload #6
    //   271: ifeq -> 483
    //   274: goto -> 281
    //   277: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   280: athrow
    //   281: ifne -> 442
    //   284: goto -> 291
    //   287: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   290: athrow
    //   291: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   294: getfield field_1724 : Lnet/minecraft/class_746;
    //   297: getfield field_6017 : F
    //   300: f2d
    //   301: aload_0
    //   302: getfield k : Lwtf/opal/kt;
    //   305: invokevirtual z : ()Ljava/lang/Object;
    //   308: checkcast java/lang/Double
    //   311: invokevirtual doubleValue : ()D
    //   314: dcmpg
    //   315: iload #6
    //   317: ifeq -> 403
    //   320: goto -> 327
    //   323: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   326: athrow
    //   327: ifge -> 375
    //   330: goto -> 337
    //   333: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   336: athrow
    //   337: aload_1
    //   338: iconst_0
    //   339: anewarray java/lang/Object
    //   342: invokevirtual Z : ([Ljava/lang/Object;)V
    //   345: aload_0
    //   346: getfield T : Ljava/util/List;
    //   349: aload_1
    //   350: iconst_0
    //   351: anewarray java/lang/Object
    //   354: invokevirtual J : ([Ljava/lang/Object;)Lnet/minecraft/class_2596;
    //   357: invokeinterface add : (Ljava/lang/Object;)Z
    //   362: pop
    //   363: iload #6
    //   365: ifne -> 530
    //   368: goto -> 375
    //   371: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   374: athrow
    //   375: aload_0
    //   376: getfield T : Ljava/util/List;
    //   379: iload #6
    //   381: ifeq -> 432
    //   384: goto -> 391
    //   387: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   390: athrow
    //   391: invokeinterface isEmpty : ()Z
    //   396: goto -> 403
    //   399: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   402: athrow
    //   403: ifne -> 530
    //   406: aload_0
    //   407: getfield T : Ljava/util/List;
    //   410: aload_0
    //   411: <illegal opcode> accept : (Lwtf/opal/j0;)Ljava/util/function/Consumer;
    //   416: invokeinterface forEach : (Ljava/util/function/Consumer;)V
    //   421: aload_0
    //   422: getfield T : Ljava/util/List;
    //   425: goto -> 432
    //   428: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   431: athrow
    //   432: invokeinterface clear : ()V
    //   437: iload #6
    //   439: ifne -> 530
    //   442: aload_0
    //   443: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   446: getfield field_1724 : Lnet/minecraft/class_746;
    //   449: invokevirtual method_19538 : ()Lnet/minecraft/class_243;
    //   452: putfield x : Lnet/minecraft/class_243;
    //   455: aload_0
    //   456: getfield T : Ljava/util/List;
    //   459: iload #6
    //   461: ifeq -> 525
    //   464: goto -> 471
    //   467: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   470: athrow
    //   471: invokeinterface isEmpty : ()Z
    //   476: goto -> 483
    //   479: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   482: athrow
    //   483: ifne -> 530
    //   486: aload_0
    //   487: getfield T : Ljava/util/List;
    //   490: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   493: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   496: invokevirtual method_48296 : ()Lnet/minecraft/class_2535;
    //   499: dup
    //   500: invokestatic requireNonNull : (Ljava/lang/Object;)Ljava/lang/Object;
    //   503: pop
    //   504: <illegal opcode> accept : (Lnet/minecraft/class_2535;)Ljava/util/function/Consumer;
    //   509: invokeinterface forEach : (Ljava/util/function/Consumer;)V
    //   514: aload_0
    //   515: getfield T : Ljava/util/List;
    //   518: goto -> 525
    //   521: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   524: athrow
    //   525: invokeinterface clear : ()V
    //   530: return
    // Exception table:
    //   from	to	target	type
    //   32	45	48	wtf/opal/x5
    //   39	54	57	wtf/opal/x5
    //   66	104	107	wtf/opal/x5
    //   101	154	157	wtf/opal/x5
    //   111	164	167	wtf/opal/x5
    //   161	214	217	wtf/opal/x5
    //   171	224	227	wtf/opal/x5
    //   221	274	277	wtf/opal/x5
    //   231	284	287	wtf/opal/x5
    //   281	320	323	wtf/opal/x5
    //   291	330	333	wtf/opal/x5
    //   327	368	371	wtf/opal/x5
    //   337	384	387	wtf/opal/x5
    //   375	396	399	wtf/opal/x5
    //   403	425	428	wtf/opal/x5
    //   432	464	467	wtf/opal/x5
    //   442	476	479	wtf/opal/x5
    //   483	518	521	wtf/opal/x5
  }
  
  private void lambda$new$0(class_2596 paramclass_2596) {
    long l = a ^ 0x54A6E4D928DCL;
    boolean bool = j4.n();
    try {
      if (!bool)
        try {
          if (paramclass_2596 instanceof class_2828) {
          
          } else {
            return;
          } 
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    class_2828 class_2828 = (class_2828)paramclass_2596;
    if (this.x != null) {
      PlayerMoveC2SPacketAccessor playerMoveC2SPacketAccessor = (PlayerMoveC2SPacketAccessor)class_2828;
      playerMoveC2SPacketAccessor.setX(this.x.method_10216());
      playerMoveC2SPacketAccessor.setY(this.x.method_10214());
      playerMoveC2SPacketAccessor.setZ(this.x.method_10215());
      b9.c.method_1562().method_48296().method_10743((class_2596)class_2828);
    } 
  }
  
  static {
    long l = a ^ 0x1519996B7C60L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[3];
    boolean bool = false;
    String str;
    int i = (str = "ñ2µQ\023iå\t*\013¿\005hãÂKâ,\ty¡§\033ç@ºã\020Cÿá\023+\\´ÚÀâ'yj\001d\\½\025wjÞ\024`+ \036$\005\"ã{tQò4üÒ³î4Uç_\003@â{À+b\r 8Q\026ãwÏ$1¹&oäª¨ÌcÀ7à\007Ö\0374\t|Ð&ë6").length();
    byte b2 = 64;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x4653;
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
        throw new RuntimeException("wtf/opal/j0", exception);
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
    //   66: ldc_w 'wtf/opal/j0'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\j0.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */