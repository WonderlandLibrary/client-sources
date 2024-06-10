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
import net.minecraft.class_1263;
import net.minecraft.class_1707;
import net.minecraft.class_476;

public final class xj extends d {
  private final ky<db> Y;
  
  private final kt l;
  
  private final kr z;
  
  private final gm<b6> G;
  
  private static final long a = on.a(-8513201147227074255L, 3902021935968284070L, MethodHandles.lookup().lookupClass()).a(154696637536739L);
  
  private static final String[] b;
  
  private static final String[] d;
  
  private static final Map f = new HashMap<>(13);
  
  private static final long[] g;
  
  private static final Integer[] k;
  
  private static final Map m;
  
  public xj(long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/xj.a : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: lload_1
    //   7: dup2
    //   8: ldc2_w 85265578023494
    //   11: lxor
    //   12: lstore_3
    //   13: dup2
    //   14: ldc2_w 109894824952595
    //   17: lxor
    //   18: lstore #5
    //   20: pop2
    //   21: aload_0
    //   22: sipush #7279
    //   25: ldc2_w 2679089192436585137
    //   28: lload_1
    //   29: lxor
    //   30: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   35: lload #5
    //   37: sipush #8838
    //   40: ldc2_w 5670388101998603358
    //   43: lload_1
    //   44: lxor
    //   45: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   50: getstatic wtf/opal/kn.WORLD : Lwtf/opal/kn;
    //   53: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   56: aload_0
    //   57: new wtf/opal/ky
    //   60: dup
    //   61: sipush #26993
    //   64: ldc2_w 4373454565702444968
    //   67: lload_1
    //   68: lxor
    //   69: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   74: getstatic wtf/opal/db.QUICK_MOVE : Lwtf/opal/db;
    //   77: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Enum;)V
    //   80: putfield Y : Lwtf/opal/ky;
    //   83: aload_0
    //   84: new wtf/opal/kt
    //   87: dup
    //   88: sipush #6960
    //   91: ldc2_w 4833703541432735212
    //   94: lload_1
    //   95: lxor
    //   96: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   101: ldc2_w 50.0
    //   104: dconst_0
    //   105: ldc2_w 500.0
    //   108: ldc2_w 5.0
    //   111: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   114: putfield l : Lwtf/opal/kt;
    //   117: aload_0
    //   118: new wtf/opal/kr
    //   121: dup
    //   122: invokespecial <init> : ()V
    //   125: putfield z : Lwtf/opal/kr;
    //   128: aload_0
    //   129: aload_0
    //   130: <illegal opcode> H : (Lwtf/opal/xj;)Lwtf/opal/gm;
    //   135: putfield G : Lwtf/opal/gm;
    //   138: aload_0
    //   139: iconst_2
    //   140: anewarray wtf/opal/k3
    //   143: dup
    //   144: iconst_0
    //   145: aload_0
    //   146: getfield Y : Lwtf/opal/ky;
    //   149: aastore
    //   150: dup
    //   151: iconst_1
    //   152: aload_0
    //   153: getfield l : Lwtf/opal/kt;
    //   156: aastore
    //   157: lload_3
    //   158: dup2_x1
    //   159: pop2
    //   160: iconst_2
    //   161: anewarray java/lang/Object
    //   164: dup_x1
    //   165: swap
    //   166: iconst_1
    //   167: swap
    //   168: aastore
    //   169: dup_x2
    //   170: dup_x2
    //   171: pop
    //   172: invokestatic valueOf : (J)Ljava/lang/Long;
    //   175: iconst_0
    //   176: swap
    //   177: aastore
    //   178: invokevirtual o : ([Ljava/lang/Object;)V
    //   181: return
  }
  
  private void lambda$new$1(b6 paramb6) {
    // Byte code:
    //   0: getstatic wtf/opal/xj.a : J
    //   3: ldc2_w 108014111520989
    //   6: lxor
    //   7: lstore_2
    //   8: invokestatic k : ()[I
    //   11: astore #4
    //   13: aload_1
    //   14: iconst_0
    //   15: anewarray java/lang/Object
    //   18: invokevirtual W : ([Ljava/lang/Object;)Z
    //   21: ifeq -> 214
    //   24: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   27: getfield field_1755 : Lnet/minecraft/class_437;
    //   30: astore #6
    //   32: aload #6
    //   34: aload #4
    //   36: ifnull -> 61
    //   39: instanceof net/minecraft/class_476
    //   42: ifeq -> 214
    //   45: goto -> 52
    //   48: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   51: athrow
    //   52: aload #6
    //   54: goto -> 61
    //   57: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   60: athrow
    //   61: checkcast net/minecraft/class_476
    //   64: astore #5
    //   66: aload #5
    //   68: invokevirtual method_17577 : ()Lnet/minecraft/class_1703;
    //   71: checkcast net/minecraft/class_1707
    //   74: astore #6
    //   76: aload #6
    //   78: invokevirtual method_7629 : ()Lnet/minecraft/class_1263;
    //   81: astore #7
    //   83: aload #5
    //   85: invokevirtual method_25440 : ()Lnet/minecraft/class_2561;
    //   88: invokeinterface getString : ()Ljava/lang/String;
    //   93: sipush #9923
    //   96: ldc2_w 1483907032779469730
    //   99: lload_2
    //   100: lxor
    //   101: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   106: invokevirtual contains : (Ljava/lang/CharSequence;)Z
    //   109: aload #4
    //   111: ifnull -> 132
    //   114: ifne -> 125
    //   117: goto -> 124
    //   120: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   123: athrow
    //   124: return
    //   125: aload #7
    //   127: invokeinterface method_5442 : ()Z
    //   132: aload #4
    //   134: ifnull -> 161
    //   137: ifne -> 176
    //   140: goto -> 147
    //   143: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   146: athrow
    //   147: iconst_0
    //   148: anewarray java/lang/Object
    //   151: invokestatic X : ([Ljava/lang/Object;)Z
    //   154: goto -> 161
    //   157: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   160: athrow
    //   161: aload #4
    //   163: ifnull -> 187
    //   166: ifeq -> 186
    //   169: goto -> 176
    //   172: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   175: athrow
    //   176: aload #5
    //   178: invokevirtual method_25419 : ()V
    //   181: return
    //   182: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   185: athrow
    //   186: iconst_0
    //   187: aload #7
    //   189: invokeinterface method_5439 : ()I
    //   194: invokestatic range : (II)Ljava/util/stream/IntStream;
    //   197: aload_0
    //   198: aload #7
    //   200: aload #6
    //   202: aload #5
    //   204: <illegal opcode> accept : (Lwtf/opal/xj;Lnet/minecraft/class_1263;Lnet/minecraft/class_1707;Lnet/minecraft/class_476;)Ljava/util/function/IntConsumer;
    //   209: invokeinterface forEach : (Ljava/util/function/IntConsumer;)V
    //   214: return
    // Exception table:
    //   from	to	target	type
    //   32	45	48	wtf/opal/x5
    //   39	54	57	wtf/opal/x5
    //   83	117	120	wtf/opal/x5
    //   132	140	143	wtf/opal/x5
    //   137	154	157	wtf/opal/x5
    //   161	169	172	wtf/opal/x5
    //   166	182	182	wtf/opal/x5
  }
  
  private void lambda$new$0(class_1263 paramclass_1263, class_1707 paramclass_1707, class_476 paramclass_476, int paramInt) {
    // Byte code:
    //   0: getstatic wtf/opal/xj.a : J
    //   3: ldc2_w 71435625061840
    //   6: lxor
    //   7: lstore #5
    //   9: lload #5
    //   11: dup2
    //   12: ldc2_w 61827460069652
    //   15: lxor
    //   16: lstore #7
    //   18: dup2
    //   19: ldc2_w 64097680647871
    //   22: lxor
    //   23: lstore #9
    //   25: dup2
    //   26: ldc2_w 59797991605747
    //   29: lxor
    //   30: lstore #11
    //   32: pop2
    //   33: invokestatic k : ()[I
    //   36: aload_1
    //   37: iload #4
    //   39: invokeinterface method_5438 : (I)Lnet/minecraft/class_1799;
    //   44: astore #14
    //   46: astore #13
    //   48: aload #14
    //   50: invokevirtual method_7960 : ()Z
    //   53: aload #13
    //   55: ifnull -> 90
    //   58: ifne -> 563
    //   61: goto -> 68
    //   64: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   67: athrow
    //   68: aload_0
    //   69: getfield l : Lwtf/opal/kt;
    //   72: invokevirtual z : ()Ljava/lang/Object;
    //   75: checkcast java/lang/Double
    //   78: invokevirtual doubleValue : ()D
    //   81: dconst_0
    //   82: dcmpl
    //   83: goto -> 90
    //   86: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   89: athrow
    //   90: aload #13
    //   92: ifnull -> 200
    //   95: ifeq -> 180
    //   98: goto -> 105
    //   101: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   104: athrow
    //   105: aload_0
    //   106: getfield z : Lwtf/opal/kr;
    //   109: aload_0
    //   110: getfield l : Lwtf/opal/kt;
    //   113: invokevirtual z : ()Ljava/lang/Object;
    //   116: checkcast java/lang/Double
    //   119: invokevirtual longValue : ()J
    //   122: lload #7
    //   124: iconst_1
    //   125: iconst_3
    //   126: anewarray java/lang/Object
    //   129: dup_x1
    //   130: swap
    //   131: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   134: iconst_2
    //   135: swap
    //   136: aastore
    //   137: dup_x2
    //   138: dup_x2
    //   139: pop
    //   140: invokestatic valueOf : (J)Ljava/lang/Long;
    //   143: iconst_1
    //   144: swap
    //   145: aastore
    //   146: dup_x2
    //   147: dup_x2
    //   148: pop
    //   149: invokestatic valueOf : (J)Ljava/lang/Long;
    //   152: iconst_0
    //   153: swap
    //   154: aastore
    //   155: invokevirtual v : ([Ljava/lang/Object;)Z
    //   158: aload #13
    //   160: ifnull -> 200
    //   163: goto -> 170
    //   166: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   169: athrow
    //   170: ifeq -> 563
    //   173: goto -> 180
    //   176: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   179: athrow
    //   180: aload_0
    //   181: getfield Y : Lwtf/opal/ky;
    //   184: invokevirtual z : ()Ljava/lang/Object;
    //   187: checkcast wtf/opal/db
    //   190: invokevirtual ordinal : ()I
    //   193: goto -> 200
    //   196: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   199: athrow
    //   200: lookupswitch default -> 563, 0 -> 228, 1 -> 265
    //   228: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   231: getfield field_1761 : Lnet/minecraft/class_636;
    //   234: aload_2
    //   235: getfield field_7763 : I
    //   238: iload #4
    //   240: iconst_0
    //   241: getstatic net/minecraft/class_1713.field_7794 : Lnet/minecraft/class_1713;
    //   244: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   247: getfield field_1724 : Lnet/minecraft/class_746;
    //   250: invokevirtual method_2906 : (IIILnet/minecraft/class_1713;Lnet/minecraft/class_1657;)V
    //   253: aload #13
    //   255: ifnonnull -> 563
    //   258: goto -> 265
    //   261: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   264: athrow
    //   265: lload #9
    //   267: iconst_1
    //   268: anewarray java/lang/Object
    //   271: dup_x2
    //   272: dup_x2
    //   273: pop
    //   274: invokestatic valueOf : (J)Ljava/lang/Long;
    //   277: iconst_0
    //   278: swap
    //   279: aastore
    //   280: invokestatic s : ([Ljava/lang/Object;)I
    //   283: istore #15
    //   285: iload #15
    //   287: iconst_m1
    //   288: aload #13
    //   290: ifnull -> 328
    //   293: if_icmpne -> 312
    //   296: goto -> 303
    //   299: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   302: athrow
    //   303: aload_3
    //   304: invokevirtual method_25419 : ()V
    //   307: return
    //   308: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   311: athrow
    //   312: iload #15
    //   314: sipush #30477
    //   317: ldc2_w 2310338974948234192
    //   320: lload #5
    //   322: lxor
    //   323: <illegal opcode> x : (IJ)I
    //   328: aload #13
    //   330: ifnull -> 417
    //   333: if_icmpge -> 374
    //   336: goto -> 343
    //   339: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   342: athrow
    //   343: aload_2
    //   344: invokevirtual method_17388 : ()I
    //   347: iconst_3
    //   348: iadd
    //   349: sipush #28143
    //   352: ldc2_w 9208028638468835635
    //   355: lload #5
    //   357: lxor
    //   358: <illegal opcode> x : (IJ)I
    //   363: imul
    //   364: iload #15
    //   366: iadd
    //   367: istore #16
    //   369: aload #13
    //   371: ifnonnull -> 420
    //   374: aload_2
    //   375: invokevirtual method_17388 : ()I
    //   378: sipush #28143
    //   381: ldc2_w 9208028638468835635
    //   384: lload #5
    //   386: lxor
    //   387: <illegal opcode> x : (IJ)I
    //   392: imul
    //   393: iload #15
    //   395: sipush #28143
    //   398: ldc2_w 9208028638468835635
    //   401: lload #5
    //   403: lxor
    //   404: <illegal opcode> x : (IJ)I
    //   409: isub
    //   410: goto -> 417
    //   413: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   416: athrow
    //   417: iadd
    //   418: istore #16
    //   420: aload_2
    //   421: invokevirtual method_34255 : ()Lnet/minecraft/class_1799;
    //   424: aload #13
    //   426: ifnull -> 453
    //   429: invokevirtual method_7960 : ()Z
    //   432: ifne -> 506
    //   435: goto -> 442
    //   438: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   441: athrow
    //   442: aload_2
    //   443: invokevirtual method_34255 : ()Lnet/minecraft/class_1799;
    //   446: goto -> 453
    //   449: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   452: athrow
    //   453: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   456: sipush #22753
    //   459: ldc2_w 2561934704461899919
    //   462: lload #5
    //   464: lxor
    //   465: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   470: swap
    //   471: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   476: lload #11
    //   478: dup2_x1
    //   479: pop2
    //   480: iconst_2
    //   481: anewarray java/lang/Object
    //   484: dup_x1
    //   485: swap
    //   486: iconst_1
    //   487: swap
    //   488: aastore
    //   489: dup_x2
    //   490: dup_x2
    //   491: pop
    //   492: invokestatic valueOf : (J)Ljava/lang/Long;
    //   495: iconst_0
    //   496: swap
    //   497: aastore
    //   498: invokestatic g : ([Ljava/lang/Object;)V
    //   501: aload #13
    //   503: ifnonnull -> 538
    //   506: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   509: getfield field_1761 : Lnet/minecraft/class_636;
    //   512: aload_2
    //   513: getfield field_7763 : I
    //   516: iload #4
    //   518: iconst_0
    //   519: getstatic net/minecraft/class_1713.field_7790 : Lnet/minecraft/class_1713;
    //   522: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   525: getfield field_1724 : Lnet/minecraft/class_746;
    //   528: invokevirtual method_2906 : (IIILnet/minecraft/class_1713;Lnet/minecraft/class_1657;)V
    //   531: goto -> 538
    //   534: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   537: athrow
    //   538: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   541: getfield field_1761 : Lnet/minecraft/class_636;
    //   544: aload_2
    //   545: getfield field_7763 : I
    //   548: iload #16
    //   550: iconst_0
    //   551: getstatic net/minecraft/class_1713.field_7790 : Lnet/minecraft/class_1713;
    //   554: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   557: getfield field_1724 : Lnet/minecraft/class_746;
    //   560: invokevirtual method_2906 : (IIILnet/minecraft/class_1713;Lnet/minecraft/class_1657;)V
    //   563: return
    // Exception table:
    //   from	to	target	type
    //   48	61	64	wtf/opal/x5
    //   58	83	86	wtf/opal/x5
    //   90	98	101	wtf/opal/x5
    //   95	163	166	wtf/opal/x5
    //   105	173	176	wtf/opal/x5
    //   170	193	196	wtf/opal/x5
    //   200	258	261	wtf/opal/x5
    //   285	296	299	wtf/opal/x5
    //   293	308	308	wtf/opal/x5
    //   328	336	339	wtf/opal/x5
    //   369	410	413	wtf/opal/x5
    //   420	435	438	wtf/opal/x5
    //   429	446	449	wtf/opal/x5
    //   453	531	534	wtf/opal/x5
  }
  
  static {
    long l = a ^ 0x3F70810401CFL;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[6];
    boolean bool = false;
    String str;
    int i = (str = "ZP²1M éz\027\037\016RHül\020\003ìþábñnýäÎIý¯Z²p\030s·YRs:eÍ³=\007mèIéÁ¹TBÙ=\001(ô29°øß[ÏÏ\032´ÿÝ¡\026d¬þX!èçëÏj\020Î8Ñ÷\037\032ò;üW").length();
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x17A6;
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
        throw new RuntimeException("wtf/opal/xj", exception);
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
    //   66: ldc_w 'wtf/opal/xj'
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x4316;
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
        throw new RuntimeException("wtf/opal/xj", exception);
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
    //   66: ldc_w 'wtf/opal/xj'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\xj.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */