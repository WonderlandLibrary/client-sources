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
import net.minecraft.class_1268;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_2596;
import net.minecraft.class_2885;
import net.minecraft.class_3965;

public final class a extends d {
  private final ky<bs> r;
  
  private final ke D;
  
  private final ke x;
  
  private final ke k;
  
  private final ke v;
  
  private final gm<bp> b;
  
  private static final long a = on.a(6397055580338697889L, -5408098754766526054L, MethodHandles.lookup().lookupClass()).a(250552482749186L);
  
  private static final String[] d;
  
  private static final String[] f;
  
  private static final Map g = new HashMap<>(13);
  
  private static final long l;
  
  public a(long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/a.a : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: lload_1
    //   7: dup2
    //   8: ldc2_w 24890271115731
    //   11: lxor
    //   12: lstore_3
    //   13: dup2
    //   14: ldc2_w 62524619288710
    //   17: lxor
    //   18: lstore #5
    //   20: pop2
    //   21: aload_0
    //   22: sipush #30236
    //   25: ldc2_w 5307146230772691959
    //   28: lload_1
    //   29: lxor
    //   30: <illegal opcode> o : (IJ)Ljava/lang/String;
    //   35: lload #5
    //   37: sipush #2200
    //   40: ldc2_w 865411382561017205
    //   43: lload_1
    //   44: lxor
    //   45: <illegal opcode> o : (IJ)Ljava/lang/String;
    //   50: getstatic wtf/opal/kn.MOVEMENT : Lwtf/opal/kn;
    //   53: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   56: aload_0
    //   57: new wtf/opal/ky
    //   60: dup
    //   61: sipush #15273
    //   64: ldc2_w 155750607713713734
    //   67: lload_1
    //   68: lxor
    //   69: <illegal opcode> o : (IJ)Ljava/lang/String;
    //   74: getstatic wtf/opal/bs.VANILLA : Lwtf/opal/bs;
    //   77: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Enum;)V
    //   80: putfield r : Lwtf/opal/ky;
    //   83: aload_0
    //   84: new wtf/opal/ke
    //   87: dup
    //   88: sipush #6400
    //   91: ldc2_w 4718065493608078570
    //   94: lload_1
    //   95: lxor
    //   96: <illegal opcode> o : (IJ)Ljava/lang/String;
    //   101: iconst_1
    //   102: invokespecial <init> : (Ljava/lang/String;Z)V
    //   105: putfield D : Lwtf/opal/ke;
    //   108: aload_0
    //   109: new wtf/opal/ke
    //   112: dup
    //   113: sipush #19454
    //   116: ldc2_w 348232827204554258
    //   119: lload_1
    //   120: lxor
    //   121: <illegal opcode> o : (IJ)Ljava/lang/String;
    //   126: iconst_0
    //   127: invokespecial <init> : (Ljava/lang/String;Z)V
    //   130: putfield x : Lwtf/opal/ke;
    //   133: aload_0
    //   134: new wtf/opal/ke
    //   137: dup
    //   138: sipush #25483
    //   141: ldc2_w 7175869915111188069
    //   144: lload_1
    //   145: lxor
    //   146: <illegal opcode> o : (IJ)Ljava/lang/String;
    //   151: iconst_1
    //   152: invokespecial <init> : (Ljava/lang/String;Z)V
    //   155: putfield k : Lwtf/opal/ke;
    //   158: aload_0
    //   159: new wtf/opal/ke
    //   162: dup
    //   163: sipush #10832
    //   166: ldc2_w 5108182221654765496
    //   169: lload_1
    //   170: lxor
    //   171: <illegal opcode> o : (IJ)Ljava/lang/String;
    //   176: iconst_1
    //   177: invokespecial <init> : (Ljava/lang/String;Z)V
    //   180: putfield v : Lwtf/opal/ke;
    //   183: aload_0
    //   184: aload_0
    //   185: <illegal opcode> H : (Lwtf/opal/a;)Lwtf/opal/gm;
    //   190: putfield b : Lwtf/opal/gm;
    //   193: aload_0
    //   194: iconst_5
    //   195: anewarray wtf/opal/k3
    //   198: dup
    //   199: iconst_0
    //   200: aload_0
    //   201: getfield r : Lwtf/opal/ky;
    //   204: aastore
    //   205: dup
    //   206: iconst_1
    //   207: aload_0
    //   208: getfield D : Lwtf/opal/ke;
    //   211: aastore
    //   212: dup
    //   213: iconst_2
    //   214: aload_0
    //   215: getfield x : Lwtf/opal/ke;
    //   218: aastore
    //   219: dup
    //   220: iconst_3
    //   221: aload_0
    //   222: getfield k : Lwtf/opal/ke;
    //   225: aastore
    //   226: dup
    //   227: iconst_4
    //   228: aload_0
    //   229: getfield v : Lwtf/opal/ke;
    //   232: aastore
    //   233: lload_3
    //   234: dup2_x1
    //   235: pop2
    //   236: iconst_2
    //   237: anewarray java/lang/Object
    //   240: dup_x1
    //   241: swap
    //   242: iconst_1
    //   243: swap
    //   244: aastore
    //   245: dup_x2
    //   246: dup_x2
    //   247: pop
    //   248: invokestatic valueOf : (J)Ljava/lang/Long;
    //   251: iconst_0
    //   252: swap
    //   253: aastore
    //   254: invokevirtual o : ([Ljava/lang/Object;)V
    //   257: return
  }
  
  public String o(Object[] paramArrayOfObject) {
    long l = ((Long)paramArrayOfObject[0]).longValue();
    return ((bs)this.r.z()).toString();
  }
  
  private void lambda$new$1(bp parambp) {
    // Byte code:
    //   0: getstatic wtf/opal/a.a : J
    //   3: ldc2_w 111862588741377
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 92774085988132
    //   13: lxor
    //   14: lstore #4
    //   16: dup2
    //   17: ldc2_w 119239580329024
    //   20: lxor
    //   21: dup2
    //   22: bipush #48
    //   24: lushr
    //   25: l2i
    //   26: istore #6
    //   28: dup2
    //   29: bipush #16
    //   31: lshl
    //   32: bipush #32
    //   34: lushr
    //   35: l2i
    //   36: istore #7
    //   38: dup2
    //   39: bipush #48
    //   41: lshl
    //   42: bipush #48
    //   44: lushr
    //   45: l2i
    //   46: istore #8
    //   48: pop2
    //   49: pop2
    //   50: invokestatic P : ()Ljava/lang/String;
    //   53: iconst_0
    //   54: anewarray java/lang/Object
    //   57: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   60: iconst_0
    //   61: anewarray java/lang/Object
    //   64: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   67: ldc wtf/opal/q
    //   69: iconst_1
    //   70: anewarray java/lang/Object
    //   73: dup_x1
    //   74: swap
    //   75: iconst_0
    //   76: swap
    //   77: aastore
    //   78: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   81: checkcast wtf/opal/q
    //   84: astore #10
    //   86: astore #9
    //   88: aload_0
    //   89: getfield r : Lwtf/opal/ky;
    //   92: invokevirtual z : ()Ljava/lang/Object;
    //   95: checkcast wtf/opal/bs
    //   98: getstatic wtf/opal/bs.WATCHDOG : Lwtf/opal/bs;
    //   101: invokevirtual equals : (Ljava/lang/Object;)Z
    //   104: aload #9
    //   106: ifnonnull -> 327
    //   109: ifeq -> 308
    //   112: goto -> 119
    //   115: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   118: athrow
    //   119: aload #10
    //   121: iconst_0
    //   122: anewarray java/lang/Object
    //   125: invokevirtual D : ([Ljava/lang/Object;)Z
    //   128: aload #9
    //   130: ifnonnull -> 185
    //   133: goto -> 140
    //   136: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   139: athrow
    //   140: ifeq -> 169
    //   143: goto -> 150
    //   146: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   149: athrow
    //   150: aload #10
    //   152: iconst_0
    //   153: anewarray java/lang/Object
    //   156: invokevirtual U : ([Ljava/lang/Object;)Lnet/minecraft/class_1309;
    //   159: ifnonnull -> 308
    //   162: goto -> 169
    //   165: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   168: athrow
    //   169: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   172: getfield field_1724 : Lnet/minecraft/class_746;
    //   175: invokevirtual method_6115 : ()Z
    //   178: goto -> 185
    //   181: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   184: athrow
    //   185: aload #9
    //   187: ifnonnull -> 327
    //   190: ifeq -> 308
    //   193: goto -> 200
    //   196: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   199: athrow
    //   200: lload #4
    //   202: iconst_1
    //   203: anewarray java/lang/Object
    //   206: dup_x2
    //   207: dup_x2
    //   208: pop
    //   209: invokestatic valueOf : (J)Ljava/lang/Long;
    //   212: iconst_0
    //   213: swap
    //   214: aastore
    //   215: invokestatic I : ([Ljava/lang/Object;)Z
    //   218: aload #9
    //   220: ifnonnull -> 327
    //   223: goto -> 230
    //   226: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   229: athrow
    //   230: ifeq -> 308
    //   233: goto -> 240
    //   236: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   239: athrow
    //   240: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   243: getfield field_1724 : Lnet/minecraft/class_746;
    //   246: getfield field_6012 : I
    //   249: getstatic wtf/opal/a.l : J
    //   252: l2i
    //   253: irem
    //   254: aload #9
    //   256: ifnonnull -> 327
    //   259: goto -> 266
    //   262: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   265: athrow
    //   266: ifne -> 308
    //   269: goto -> 276
    //   272: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   275: athrow
    //   276: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   279: getfield field_1761 : Lnet/minecraft/class_636;
    //   282: checkcast wtf/opal/mixin/ClientPlayerInteractionManagerAccessor
    //   285: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   288: getfield field_1687 : Lnet/minecraft/class_638;
    //   291: <illegal opcode> predict : ()Lnet/minecraft/class_7204;
    //   296: invokeinterface callSendSequencedPacket : (Lnet/minecraft/class_638;Lnet/minecraft/class_7204;)V
    //   301: goto -> 308
    //   304: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   307: athrow
    //   308: getstatic wtf/opal/xd.x : [I
    //   311: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   314: getfield field_1724 : Lnet/minecraft/class_746;
    //   317: invokevirtual method_6030 : ()Lnet/minecraft/class_1799;
    //   320: invokevirtual method_7976 : ()Lnet/minecraft/class_1839;
    //   323: invokevirtual ordinal : ()I
    //   326: iaload
    //   327: aload #9
    //   329: ifnonnull -> 651
    //   332: tableswitch default -> 602, 1 -> 364, 2 -> 445, 3 -> 526
    //   360: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   363: athrow
    //   364: aload_0
    //   365: getfield D : Lwtf/opal/ke;
    //   368: invokevirtual z : ()Ljava/lang/Object;
    //   371: checkcast java/lang/Boolean
    //   374: invokevirtual booleanValue : ()Z
    //   377: aload #9
    //   379: ifnonnull -> 651
    //   382: goto -> 389
    //   385: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   388: athrow
    //   389: ifeq -> 602
    //   392: goto -> 399
    //   395: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   398: athrow
    //   399: aload_1
    //   400: fconst_1
    //   401: iconst_1
    //   402: anewarray java/lang/Object
    //   405: dup_x1
    //   406: swap
    //   407: invokestatic valueOf : (F)Ljava/lang/Float;
    //   410: iconst_0
    //   411: swap
    //   412: aastore
    //   413: invokevirtual f : ([Ljava/lang/Object;)V
    //   416: aload_1
    //   417: fconst_1
    //   418: iconst_1
    //   419: anewarray java/lang/Object
    //   422: dup_x1
    //   423: swap
    //   424: invokestatic valueOf : (F)Ljava/lang/Float;
    //   427: iconst_0
    //   428: swap
    //   429: aastore
    //   430: invokevirtual U : ([Ljava/lang/Object;)V
    //   433: aload #9
    //   435: ifnull -> 602
    //   438: goto -> 445
    //   441: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   444: athrow
    //   445: aload_0
    //   446: getfield x : Lwtf/opal/ke;
    //   449: invokevirtual z : ()Ljava/lang/Object;
    //   452: checkcast java/lang/Boolean
    //   455: invokevirtual booleanValue : ()Z
    //   458: aload #9
    //   460: ifnonnull -> 651
    //   463: goto -> 470
    //   466: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   469: athrow
    //   470: ifeq -> 602
    //   473: goto -> 480
    //   476: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   479: athrow
    //   480: aload_1
    //   481: fconst_1
    //   482: iconst_1
    //   483: anewarray java/lang/Object
    //   486: dup_x1
    //   487: swap
    //   488: invokestatic valueOf : (F)Ljava/lang/Float;
    //   491: iconst_0
    //   492: swap
    //   493: aastore
    //   494: invokevirtual f : ([Ljava/lang/Object;)V
    //   497: aload_1
    //   498: fconst_1
    //   499: iconst_1
    //   500: anewarray java/lang/Object
    //   503: dup_x1
    //   504: swap
    //   505: invokestatic valueOf : (F)Ljava/lang/Float;
    //   508: iconst_0
    //   509: swap
    //   510: aastore
    //   511: invokevirtual U : ([Ljava/lang/Object;)V
    //   514: aload #9
    //   516: ifnull -> 602
    //   519: goto -> 526
    //   522: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   525: athrow
    //   526: aload_0
    //   527: getfield v : Lwtf/opal/ke;
    //   530: invokevirtual z : ()Ljava/lang/Object;
    //   533: checkcast java/lang/Boolean
    //   536: invokevirtual booleanValue : ()Z
    //   539: aload #9
    //   541: ifnonnull -> 651
    //   544: goto -> 551
    //   547: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   550: athrow
    //   551: ifeq -> 602
    //   554: goto -> 561
    //   557: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   560: athrow
    //   561: aload_1
    //   562: fconst_1
    //   563: iconst_1
    //   564: anewarray java/lang/Object
    //   567: dup_x1
    //   568: swap
    //   569: invokestatic valueOf : (F)Ljava/lang/Float;
    //   572: iconst_0
    //   573: swap
    //   574: aastore
    //   575: invokevirtual f : ([Ljava/lang/Object;)V
    //   578: aload_1
    //   579: fconst_1
    //   580: iconst_1
    //   581: anewarray java/lang/Object
    //   584: dup_x1
    //   585: swap
    //   586: invokestatic valueOf : (F)Ljava/lang/Float;
    //   589: iconst_0
    //   590: swap
    //   591: aastore
    //   592: invokevirtual U : ([Ljava/lang/Object;)V
    //   595: goto -> 602
    //   598: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   601: athrow
    //   602: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   605: getfield field_1724 : Lnet/minecraft/class_746;
    //   608: iload #6
    //   610: i2s
    //   611: iload #7
    //   613: iload #8
    //   615: iconst_4
    //   616: anewarray java/lang/Object
    //   619: dup_x1
    //   620: swap
    //   621: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   624: iconst_3
    //   625: swap
    //   626: aastore
    //   627: dup_x1
    //   628: swap
    //   629: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   632: iconst_2
    //   633: swap
    //   634: aastore
    //   635: dup_x1
    //   636: swap
    //   637: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   640: iconst_1
    //   641: swap
    //   642: aastore
    //   643: dup_x1
    //   644: swap
    //   645: iconst_0
    //   646: swap
    //   647: aastore
    //   648: invokestatic J : ([Ljava/lang/Object;)Z
    //   651: aload #9
    //   653: ifnonnull -> 686
    //   656: ifeq -> 730
    //   659: goto -> 666
    //   662: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   665: athrow
    //   666: aload_0
    //   667: getfield k : Lwtf/opal/ke;
    //   670: invokevirtual z : ()Ljava/lang/Object;
    //   673: checkcast java/lang/Boolean
    //   676: invokevirtual booleanValue : ()Z
    //   679: goto -> 686
    //   682: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   685: athrow
    //   686: ifeq -> 730
    //   689: aload_1
    //   690: fconst_1
    //   691: iconst_1
    //   692: anewarray java/lang/Object
    //   695: dup_x1
    //   696: swap
    //   697: invokestatic valueOf : (F)Ljava/lang/Float;
    //   700: iconst_0
    //   701: swap
    //   702: aastore
    //   703: invokevirtual f : ([Ljava/lang/Object;)V
    //   706: aload_1
    //   707: fconst_1
    //   708: iconst_1
    //   709: anewarray java/lang/Object
    //   712: dup_x1
    //   713: swap
    //   714: invokestatic valueOf : (F)Ljava/lang/Float;
    //   717: iconst_0
    //   718: swap
    //   719: aastore
    //   720: invokevirtual U : ([Ljava/lang/Object;)V
    //   723: goto -> 730
    //   726: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   729: athrow
    //   730: return
    // Exception table:
    //   from	to	target	type
    //   88	112	115	wtf/opal/x5
    //   109	133	136	wtf/opal/x5
    //   119	143	146	wtf/opal/x5
    //   140	162	165	wtf/opal/x5
    //   150	178	181	wtf/opal/x5
    //   185	193	196	wtf/opal/x5
    //   190	223	226	wtf/opal/x5
    //   200	233	236	wtf/opal/x5
    //   230	259	262	wtf/opal/x5
    //   240	269	272	wtf/opal/x5
    //   266	301	304	wtf/opal/x5
    //   327	360	360	wtf/opal/x5
    //   332	382	385	wtf/opal/x5
    //   364	392	395	wtf/opal/x5
    //   389	438	441	wtf/opal/x5
    //   399	463	466	wtf/opal/x5
    //   445	473	476	wtf/opal/x5
    //   470	519	522	wtf/opal/x5
    //   480	544	547	wtf/opal/x5
    //   526	554	557	wtf/opal/x5
    //   551	595	598	wtf/opal/x5
    //   651	659	662	wtf/opal/x5
    //   656	679	682	wtf/opal/x5
    //   686	723	726	wtf/opal/x5
  }
  
  private static class_2596 lambda$new$0(int paramInt) {
    return (class_2596)new class_2885(class_1268.field_5808, new class_3965(class_2338.field_10980.method_46558(), class_2350.field_11033, class_2338.field_10980, false), paramInt);
  }
  
  static {
    long l = a ^ 0x13F1B84B477FL;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[7];
    boolean bool = false;
    String str;
    int i = (str = "XÜ¼Åb¬Ûî«\fåo\021òU<\020nxn#ø?AÄ¦\013$j¨`ìÛ\020BéÙ°X+@¤)Ä\003\035Ð@ÀÆXX\rª\031F¶«©q¦N/ªìÒQýU×kßNêÊóù:qì\037ÅXe°Æ/§Ù.\025vâáS©o&Ó\032wk¸å\020\0259!¿óRQ[=×A+\023u',").length();
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x2F01;
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
        throw new RuntimeException("wtf/opal/a", exception);
      } 
      byte[] arrayOfByte1 = new byte[8];
      arrayOfByte1[0] = (byte)(int)(paramLong >>> 56L);
      for (byte b = 1; b < 8; b++)
        arrayOfByte1[b] = (byte)(int)(paramLong << b * 8 >>> 56L); 
      DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
      SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
      ((Cipher)arrayOfObject[0]).init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
      byte[] arrayOfByte2 = d[i].getBytes("ISO-8859-1");
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
    //   66: ldc_w 'wtf/opal/a'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\a.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */