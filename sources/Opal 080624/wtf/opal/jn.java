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

public final class jn extends d {
  private float b;
  
  private boolean R;
  
  private boolean d;
  
  private boolean J;
  
  private boolean U;
  
  private final kt W;
  
  private final ke Q;
  
  private final gm<b6> k;
  
  private final gm<p1> L;
  
  private final gm<l3> P;
  
  private static final long a = on.a(3058656015034406816L, -1752381129732346217L, MethodHandles.lookup().lookupClass()).a(90398718736920L);
  
  private static final String[] f;
  
  private static final String[] g;
  
  private static final Map l = new HashMap<>(13);
  
  private static final long[] m;
  
  private static final Integer[] n;
  
  private static final Map o;
  
  public jn(long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/jn.a : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: lload_1
    //   7: dup2
    //   8: ldc2_w 12288750037635
    //   11: lxor
    //   12: lstore_3
    //   13: dup2
    //   14: ldc2_w 41041112134614
    //   17: lxor
    //   18: lstore #5
    //   20: pop2
    //   21: aload_0
    //   22: sipush #17177
    //   25: ldc2_w 7465577952016531517
    //   28: lload_1
    //   29: lxor
    //   30: <illegal opcode> m : (IJ)Ljava/lang/String;
    //   35: lload #5
    //   37: sipush #11432
    //   40: ldc2_w 6680804602162278286
    //   43: lload_1
    //   44: lxor
    //   45: <illegal opcode> m : (IJ)Ljava/lang/String;
    //   50: getstatic wtf/opal/kn.MOVEMENT : Lwtf/opal/kn;
    //   53: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   56: aload_0
    //   57: new wtf/opal/kt
    //   60: dup
    //   61: sipush #20137
    //   64: ldc2_w 1178446005981908364
    //   67: lload_1
    //   68: lxor
    //   69: <illegal opcode> m : (IJ)Ljava/lang/String;
    //   74: ldc2_w 3.0
    //   77: ldc2_w 0.1
    //   80: ldc2_w 6.0
    //   83: ldc2_w 0.1
    //   86: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   89: putfield W : Lwtf/opal/kt;
    //   92: aload_0
    //   93: new wtf/opal/ke
    //   96: dup
    //   97: sipush #23107
    //   100: ldc2_w 9089752299759540580
    //   103: lload_1
    //   104: lxor
    //   105: <illegal opcode> m : (IJ)Ljava/lang/String;
    //   110: iconst_0
    //   111: invokespecial <init> : (Ljava/lang/String;Z)V
    //   114: putfield Q : Lwtf/opal/ke;
    //   117: aload_0
    //   118: aload_0
    //   119: <illegal opcode> H : (Lwtf/opal/jn;)Lwtf/opal/gm;
    //   124: putfield k : Lwtf/opal/gm;
    //   127: aload_0
    //   128: aload_0
    //   129: <illegal opcode> H : (Lwtf/opal/jn;)Lwtf/opal/gm;
    //   134: putfield L : Lwtf/opal/gm;
    //   137: aload_0
    //   138: aload_0
    //   139: <illegal opcode> H : (Lwtf/opal/jn;)Lwtf/opal/gm;
    //   144: putfield P : Lwtf/opal/gm;
    //   147: aload_0
    //   148: iconst_2
    //   149: anewarray wtf/opal/k3
    //   152: dup
    //   153: iconst_0
    //   154: aload_0
    //   155: getfield W : Lwtf/opal/kt;
    //   158: aastore
    //   159: dup
    //   160: iconst_1
    //   161: aload_0
    //   162: getfield Q : Lwtf/opal/ke;
    //   165: aastore
    //   166: lload_3
    //   167: dup2_x1
    //   168: pop2
    //   169: iconst_2
    //   170: anewarray java/lang/Object
    //   173: dup_x1
    //   174: swap
    //   175: iconst_1
    //   176: swap
    //   177: aastore
    //   178: dup_x2
    //   179: dup_x2
    //   180: pop
    //   181: invokestatic valueOf : (J)Ljava/lang/Long;
    //   184: iconst_0
    //   185: swap
    //   186: aastore
    //   187: invokevirtual o : ([Ljava/lang/Object;)V
    //   190: return
  }
  
  public float Z(Object[] paramArrayOfObject) {
    return this.b;
  }
  
  public boolean e(Object[] paramArrayOfObject) {
    return this.R;
  }
  
  private void lambda$new$2(l3 paraml3) {
    // Byte code:
    //   0: getstatic wtf/opal/jn.a : J
    //   3: ldc2_w 94877026405545
    //   6: lxor
    //   7: lstore_2
    //   8: invokestatic P : ()Ljava/lang/String;
    //   11: astore #4
    //   13: aload_0
    //   14: getfield R : Z
    //   17: aload #4
    //   19: ifnonnull -> 58
    //   22: ifne -> 33
    //   25: goto -> 32
    //   28: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   31: athrow
    //   32: return
    //   33: aload_1
    //   34: iconst_0
    //   35: anewarray java/lang/Object
    //   38: invokevirtual T : ([Ljava/lang/Object;)Lnet/minecraft/class_243;
    //   41: aload #4
    //   43: ifnonnull -> 99
    //   46: invokevirtual method_1027 : ()D
    //   49: dconst_1
    //   50: dcmpl
    //   51: goto -> 58
    //   54: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   57: athrow
    //   58: ifle -> 91
    //   61: aload_1
    //   62: iconst_0
    //   63: anewarray java/lang/Object
    //   66: invokevirtual T : ([Ljava/lang/Object;)Lnet/minecraft/class_243;
    //   69: invokevirtual method_1029 : ()Lnet/minecraft/class_243;
    //   72: aload_1
    //   73: iconst_0
    //   74: anewarray java/lang/Object
    //   77: invokevirtual o : ([Ljava/lang/Object;)F
    //   80: f2d
    //   81: invokevirtual method_1021 : (D)Lnet/minecraft/class_243;
    //   84: goto -> 111
    //   87: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   90: athrow
    //   91: aload_1
    //   92: iconst_0
    //   93: anewarray java/lang/Object
    //   96: invokevirtual T : ([Ljava/lang/Object;)Lnet/minecraft/class_243;
    //   99: aload_1
    //   100: iconst_0
    //   101: anewarray java/lang/Object
    //   104: invokevirtual o : ([Ljava/lang/Object;)F
    //   107: f2d
    //   108: invokevirtual method_1021 : (D)Lnet/minecraft/class_243;
    //   111: astore #5
    //   113: aload_0
    //   114: getfield b : F
    //   117: f2d
    //   118: ldc2_w 0.017453292519943295
    //   121: dmul
    //   122: d2f
    //   123: invokestatic method_15374 : (F)F
    //   126: fstore #6
    //   128: aload_0
    //   129: getfield b : F
    //   132: f2d
    //   133: ldc2_w 0.017453292519943295
    //   136: dmul
    //   137: d2f
    //   138: invokestatic method_15362 : (F)F
    //   141: fstore #7
    //   143: aload_1
    //   144: new net/minecraft/class_243
    //   147: dup
    //   148: aload #5
    //   150: getfield field_1352 : D
    //   153: fload #7
    //   155: f2d
    //   156: dmul
    //   157: aload #5
    //   159: getfield field_1350 : D
    //   162: fload #6
    //   164: f2d
    //   165: dmul
    //   166: dsub
    //   167: aload #5
    //   169: getfield field_1351 : D
    //   172: aload #5
    //   174: getfield field_1350 : D
    //   177: fload #7
    //   179: f2d
    //   180: dmul
    //   181: aload #5
    //   183: getfield field_1352 : D
    //   186: fload #6
    //   188: f2d
    //   189: dmul
    //   190: dadd
    //   191: invokespecial <init> : (DDD)V
    //   194: iconst_1
    //   195: anewarray java/lang/Object
    //   198: dup_x1
    //   199: swap
    //   200: iconst_0
    //   201: swap
    //   202: aastore
    //   203: invokevirtual v : ([Ljava/lang/Object;)V
    //   206: return
    // Exception table:
    //   from	to	target	type
    //   13	25	28	wtf/opal/x5
    //   33	51	54	wtf/opal/x5
    //   58	87	87	wtf/opal/x5
  }
  
  private void lambda$new$1(p1 paramp1) {
    long l = a ^ 0x4886455D2893L;
    String str = jm.P();
    try {
      if (str == null) {
        try {
          if (!this.R)
            return; 
        } catch (x5 x5) {
          throw a(null);
        } 
        paramp1.x(new Object[] { Float.valueOf(this.b) });
      } 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  private void lambda$new$0(b6 paramb6) {
    // Byte code:
    //   0: getstatic wtf/opal/jn.a : J
    //   3: ldc2_w 72489852224292
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 58762624685217
    //   13: lxor
    //   14: lstore #4
    //   16: pop2
    //   17: invokestatic P : ()Ljava/lang/String;
    //   20: iconst_0
    //   21: anewarray java/lang/Object
    //   24: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   27: iconst_0
    //   28: anewarray java/lang/Object
    //   31: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   34: ldc wtf/opal/q
    //   36: iconst_1
    //   37: anewarray java/lang/Object
    //   40: dup_x1
    //   41: swap
    //   42: iconst_0
    //   43: swap
    //   44: aastore
    //   45: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   48: checkcast wtf/opal/q
    //   51: astore #7
    //   53: iconst_0
    //   54: anewarray java/lang/Object
    //   57: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   60: iconst_0
    //   61: anewarray java/lang/Object
    //   64: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   67: ldc wtf/opal/jm
    //   69: iconst_1
    //   70: anewarray java/lang/Object
    //   73: dup_x1
    //   74: swap
    //   75: iconst_0
    //   76: swap
    //   77: aastore
    //   78: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   81: checkcast wtf/opal/jm
    //   84: astore #8
    //   86: iconst_0
    //   87: anewarray java/lang/Object
    //   90: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   93: iconst_0
    //   94: anewarray java/lang/Object
    //   97: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   100: ldc wtf/opal/s
    //   102: iconst_1
    //   103: anewarray java/lang/Object
    //   106: dup_x1
    //   107: swap
    //   108: iconst_0
    //   109: swap
    //   110: aastore
    //   111: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   114: checkcast wtf/opal/s
    //   117: astore #9
    //   119: astore #6
    //   121: aload #6
    //   123: ifnonnull -> 332
    //   126: aload #7
    //   128: iconst_0
    //   129: anewarray java/lang/Object
    //   132: invokevirtual D : ([Ljava/lang/Object;)Z
    //   135: ifeq -> 320
    //   138: goto -> 145
    //   141: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   144: athrow
    //   145: aload #7
    //   147: iconst_0
    //   148: anewarray java/lang/Object
    //   151: invokevirtual U : ([Ljava/lang/Object;)Lnet/minecraft/class_1309;
    //   154: aload #6
    //   156: ifnonnull -> 190
    //   159: goto -> 166
    //   162: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   165: athrow
    //   166: ifnull -> 320
    //   169: goto -> 176
    //   172: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   175: athrow
    //   176: aload_0
    //   177: getfield Q : Lwtf/opal/ke;
    //   180: invokevirtual z : ()Ljava/lang/Object;
    //   183: goto -> 190
    //   186: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   189: athrow
    //   190: checkcast java/lang/Boolean
    //   193: invokevirtual booleanValue : ()Z
    //   196: aload #6
    //   198: ifnonnull -> 274
    //   201: ifeq -> 258
    //   204: goto -> 211
    //   207: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   210: athrow
    //   211: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   214: invokevirtual method_22683 : ()Lnet/minecraft/class_1041;
    //   217: invokevirtual method_4490 : ()J
    //   220: sipush #19827
    //   223: ldc2_w 7848735568793653429
    //   226: lload_2
    //   227: lxor
    //   228: <illegal opcode> j : (IJ)I
    //   233: invokestatic method_15987 : (JI)Z
    //   236: aload #6
    //   238: ifnonnull -> 274
    //   241: goto -> 248
    //   244: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   247: athrow
    //   248: ifeq -> 320
    //   251: goto -> 258
    //   254: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   257: athrow
    //   258: aload #8
    //   260: iconst_0
    //   261: anewarray java/lang/Object
    //   264: invokevirtual D : ([Ljava/lang/Object;)Z
    //   267: goto -> 274
    //   270: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   273: athrow
    //   274: aload #6
    //   276: ifnonnull -> 359
    //   279: ifne -> 333
    //   282: goto -> 289
    //   285: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   288: athrow
    //   289: aload #9
    //   291: iconst_0
    //   292: anewarray java/lang/Object
    //   295: invokevirtual D : ([Ljava/lang/Object;)Z
    //   298: aload #6
    //   300: ifnonnull -> 359
    //   303: goto -> 310
    //   306: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   309: athrow
    //   310: ifne -> 333
    //   313: goto -> 320
    //   316: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   319: athrow
    //   320: aload_0
    //   321: iconst_0
    //   322: putfield R : Z
    //   325: goto -> 332
    //   328: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   331: athrow
    //   332: return
    //   333: aload_0
    //   334: iconst_1
    //   335: aload #6
    //   337: ifnonnull -> 442
    //   340: putfield R : Z
    //   343: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   346: getfield field_1724 : Lnet/minecraft/class_746;
    //   349: getfield field_5976 : Z
    //   352: goto -> 359
    //   355: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   358: athrow
    //   359: ifeq -> 433
    //   362: aload_0
    //   363: aload #6
    //   365: ifnonnull -> 424
    //   368: goto -> 375
    //   371: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   374: athrow
    //   375: getfield J : Z
    //   378: ifne -> 423
    //   381: goto -> 388
    //   384: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   387: athrow
    //   388: aload_0
    //   389: aload_0
    //   390: getfield d : Z
    //   393: aload #6
    //   395: ifnonnull -> 416
    //   398: goto -> 405
    //   401: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   404: athrow
    //   405: ifne -> 419
    //   408: goto -> 415
    //   411: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   414: athrow
    //   415: iconst_1
    //   416: goto -> 420
    //   419: iconst_0
    //   420: putfield d : Z
    //   423: aload_0
    //   424: iconst_1
    //   425: putfield J : Z
    //   428: aload #6
    //   430: ifnull -> 445
    //   433: aload_0
    //   434: iconst_0
    //   435: goto -> 442
    //   438: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   441: athrow
    //   442: putfield J : Z
    //   445: sipush #29195
    //   448: ldc2_w 2028161536294186956
    //   451: lload_2
    //   452: lxor
    //   453: <illegal opcode> j : (IJ)I
    //   458: lload #4
    //   460: iconst_2
    //   461: anewarray java/lang/Object
    //   464: dup_x2
    //   465: dup_x2
    //   466: pop
    //   467: invokestatic valueOf : (J)Ljava/lang/Long;
    //   470: iconst_1
    //   471: swap
    //   472: aastore
    //   473: dup_x1
    //   474: swap
    //   475: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   478: iconst_0
    //   479: swap
    //   480: aastore
    //   481: invokestatic b : ([Ljava/lang/Object;)Z
    //   484: aload #6
    //   486: ifnonnull -> 522
    //   489: ifne -> 570
    //   492: goto -> 499
    //   495: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   498: athrow
    //   499: aload_0
    //   500: aload #6
    //   502: ifnonnull -> 561
    //   505: goto -> 512
    //   508: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   511: athrow
    //   512: getfield U : Z
    //   515: goto -> 522
    //   518: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   521: athrow
    //   522: ifne -> 560
    //   525: aload_0
    //   526: aload_0
    //   527: getfield d : Z
    //   530: aload #6
    //   532: ifnonnull -> 553
    //   535: goto -> 542
    //   538: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   541: athrow
    //   542: ifne -> 556
    //   545: goto -> 552
    //   548: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   551: athrow
    //   552: iconst_1
    //   553: goto -> 557
    //   556: iconst_0
    //   557: putfield d : Z
    //   560: aload_0
    //   561: iconst_1
    //   562: putfield U : Z
    //   565: aload #6
    //   567: ifnull -> 582
    //   570: aload_0
    //   571: iconst_0
    //   572: putfield U : Z
    //   575: goto -> 582
    //   578: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   581: athrow
    //   582: aload #7
    //   584: iconst_0
    //   585: anewarray java/lang/Object
    //   588: invokevirtual U : ([Ljava/lang/Object;)Lnet/minecraft/class_1309;
    //   591: astore #10
    //   593: aload #10
    //   595: iconst_1
    //   596: anewarray java/lang/Object
    //   599: dup_x1
    //   600: swap
    //   601: iconst_0
    //   602: swap
    //   603: aastore
    //   604: invokestatic x : ([Ljava/lang/Object;)Lnet/minecraft/class_241;
    //   607: getfield field_1343 : F
    //   610: sipush #31967
    //   613: ldc2_w 1096229930887274778
    //   616: lload_2
    //   617: lxor
    //   618: <illegal opcode> j : (IJ)I
    //   623: aload_0
    //   624: getfield d : Z
    //   627: aload #6
    //   629: ifnonnull -> 643
    //   632: ifeq -> 646
    //   635: goto -> 642
    //   638: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   641: athrow
    //   642: iconst_m1
    //   643: goto -> 647
    //   646: iconst_1
    //   647: imul
    //   648: i2f
    //   649: fadd
    //   650: fstore #11
    //   652: aload_0
    //   653: getfield W : Lwtf/opal/kt;
    //   656: invokevirtual z : ()Ljava/lang/Object;
    //   659: checkcast java/lang/Double
    //   662: invokevirtual doubleValue : ()D
    //   665: invokestatic random : ()D
    //   668: ldc2_w 50.0
    //   671: ddiv
    //   672: dadd
    //   673: dstore #12
    //   675: fload #11
    //   677: f2d
    //   678: invokestatic toRadians : (D)D
    //   681: d2f
    //   682: invokestatic method_15374 : (F)F
    //   685: fneg
    //   686: f2d
    //   687: dload #12
    //   689: dmul
    //   690: aload #10
    //   692: invokevirtual method_23317 : ()D
    //   695: dadd
    //   696: dstore #14
    //   698: fload #11
    //   700: f2d
    //   701: invokestatic toRadians : (D)D
    //   704: d2f
    //   705: invokestatic method_15362 : (F)F
    //   708: f2d
    //   709: dload #12
    //   711: dmul
    //   712: aload #10
    //   714: invokevirtual method_23321 : ()D
    //   717: dadd
    //   718: dstore #16
    //   720: aload_0
    //   721: new net/minecraft/class_243
    //   724: dup
    //   725: dload #14
    //   727: aload #10
    //   729: invokevirtual method_23318 : ()D
    //   732: dload #16
    //   734: invokespecial <init> : (DDD)V
    //   737: iconst_1
    //   738: anewarray java/lang/Object
    //   741: dup_x1
    //   742: swap
    //   743: iconst_0
    //   744: swap
    //   745: aastore
    //   746: invokestatic p : ([Ljava/lang/Object;)Lnet/minecraft/class_241;
    //   749: getfield field_1343 : F
    //   752: putfield b : F
    //   755: return
    // Exception table:
    //   from	to	target	type
    //   121	138	141	wtf/opal/x5
    //   126	159	162	wtf/opal/x5
    //   145	169	172	wtf/opal/x5
    //   166	183	186	wtf/opal/x5
    //   190	204	207	wtf/opal/x5
    //   201	241	244	wtf/opal/x5
    //   211	251	254	wtf/opal/x5
    //   248	267	270	wtf/opal/x5
    //   274	282	285	wtf/opal/x5
    //   279	303	306	wtf/opal/x5
    //   289	313	316	wtf/opal/x5
    //   310	325	328	wtf/opal/x5
    //   333	352	355	wtf/opal/x5
    //   359	368	371	wtf/opal/x5
    //   362	381	384	wtf/opal/x5
    //   375	398	401	wtf/opal/x5
    //   388	408	411	wtf/opal/x5
    //   424	435	438	wtf/opal/x5
    //   445	492	495	wtf/opal/x5
    //   489	505	508	wtf/opal/x5
    //   499	515	518	wtf/opal/x5
    //   522	535	538	wtf/opal/x5
    //   525	545	548	wtf/opal/x5
    //   561	575	578	wtf/opal/x5
    //   593	635	638	wtf/opal/x5
  }
  
  static {
    long l = a ^ 0x45B0554B55A6L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[4];
    boolean bool = false;
    String str;
    int i = (str = "Ýg¶5\020\f\005w¿\021\034Îí)\"NÇ ØK7ÑØ6\020Btt\t\036\016 ÞÛþQ\000\036Ç").length();
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x6A9B;
    if (g[i] == null) {
      Object[] arrayOfObject;
      try {
        Long long_ = Long.valueOf(Thread.currentThread().threadId());
        arrayOfObject = (Object[])l.get(long_);
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/PKCS5Padding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          l.put(long_, arrayOfObject);
        } 
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/jn", exception);
      } 
      byte[] arrayOfByte1 = new byte[8];
      arrayOfByte1[0] = (byte)(int)(paramLong >>> 56L);
      for (byte b = 1; b < 8; b++)
        arrayOfByte1[b] = (byte)(int)(paramLong << b * 8 >>> 56L); 
      DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
      SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
      ((Cipher)arrayOfObject[0]).init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
      byte[] arrayOfByte2 = f[i].getBytes("ISO-8859-1");
      g[i] = a(((Cipher)arrayOfObject[0]).doFinal(arrayOfByte2));
    } 
    return g[i];
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
    //   66: ldc_w 'wtf/opal/jn'
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x2C4C;
    if (n[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l = m[i];
      byte[] arrayOfByte2 = { (byte)(int)(l >>> 56L), (byte)(int)(l >>> 48L), (byte)(int)(l >>> 40L), (byte)(int)(l >>> 32L), (byte)(int)(l >>> 24L), (byte)(int)(l >>> 16L), (byte)(int)(l >>> 8L), (byte)(int)l };
      Long long_ = Long.valueOf(Thread.currentThread().threadId());
      Object[] arrayOfObject = (Object[])o.get(long_);
      try {
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/NoPadding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          o.put(long_, arrayOfObject);
        } 
        DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
        SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
        Cipher cipher = (Cipher)arrayOfObject[0];
        cipher.init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
        arrayOfByte3 = cipher.doFinal(arrayOfByte2);
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/jn", exception);
      } 
      int j = (arrayOfByte3[4] & 0xFF) << 24 | (arrayOfByte3[5] & 0xFF) << 16 | (arrayOfByte3[6] & 0xFF) << 8 | arrayOfByte3[7] & 0xFF;
      n[i] = Integer.valueOf(j);
    } 
    return n[i].intValue();
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
    //   66: ldc_w 'wtf/opal/jn'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\jn.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */