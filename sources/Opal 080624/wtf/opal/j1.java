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

public final class j1 extends d {
  private final kt t;
  
  private final ke D;
  
  private final ke U;
  
  private final ke Z;
  
  private final gm<b6> G;
  
  private final gm<lb> v;
  
  private static final long a = on.a(-1355922488586954910L, -9033021186494705382L, MethodHandles.lookup().lookupClass()).a(138298943024753L);
  
  private static final String[] b;
  
  private static final String[] d;
  
  private static final Map f = new HashMap<>(13);
  
  public j1(long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/j1.a : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: lload_1
    //   7: dup2
    //   8: ldc2_w 71502568959717
    //   11: lxor
    //   12: lstore_3
    //   13: dup2
    //   14: ldc2_w 122567348549552
    //   17: lxor
    //   18: lstore #5
    //   20: pop2
    //   21: aload_0
    //   22: sipush #10178
    //   25: ldc2_w 1369393943779136379
    //   28: lload_1
    //   29: lxor
    //   30: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   35: lload #5
    //   37: sipush #25931
    //   40: ldc2_w 5283112760471982583
    //   43: lload_1
    //   44: lxor
    //   45: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   50: getstatic wtf/opal/kn.PLAYER : Lwtf/opal/kn;
    //   53: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   56: aload_0
    //   57: new wtf/opal/kt
    //   60: dup
    //   61: sipush #2438
    //   64: ldc2_w 5270975387219393854
    //   67: lload_1
    //   68: lxor
    //   69: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   74: dconst_0
    //   75: dconst_0
    //   76: ldc2_w 4.0
    //   79: dconst_1
    //   80: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   83: putfield t : Lwtf/opal/kt;
    //   86: aload_0
    //   87: new wtf/opal/ke
    //   90: dup
    //   91: sipush #12992
    //   94: ldc2_w 2404417083625752190
    //   97: lload_1
    //   98: lxor
    //   99: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   104: iconst_0
    //   105: invokespecial <init> : (Ljava/lang/String;Z)V
    //   108: putfield D : Lwtf/opal/ke;
    //   111: aload_0
    //   112: new wtf/opal/ke
    //   115: dup
    //   116: sipush #29856
    //   119: ldc2_w 1114480080369680413
    //   122: lload_1
    //   123: lxor
    //   124: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   129: iconst_0
    //   130: invokespecial <init> : (Ljava/lang/String;Z)V
    //   133: putfield U : Lwtf/opal/ke;
    //   136: aload_0
    //   137: new wtf/opal/ke
    //   140: dup
    //   141: sipush #22385
    //   144: ldc2_w 2461668424288609230
    //   147: lload_1
    //   148: lxor
    //   149: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   154: iconst_1
    //   155: invokespecial <init> : (Ljava/lang/String;Z)V
    //   158: putfield Z : Lwtf/opal/ke;
    //   161: aload_0
    //   162: aload_0
    //   163: <illegal opcode> H : (Lwtf/opal/j1;)Lwtf/opal/gm;
    //   168: putfield G : Lwtf/opal/gm;
    //   171: aload_0
    //   172: aload_0
    //   173: <illegal opcode> H : (Lwtf/opal/j1;)Lwtf/opal/gm;
    //   178: putfield v : Lwtf/opal/gm;
    //   181: aload_0
    //   182: iconst_4
    //   183: anewarray wtf/opal/k3
    //   186: dup
    //   187: iconst_0
    //   188: aload_0
    //   189: getfield t : Lwtf/opal/kt;
    //   192: aastore
    //   193: dup
    //   194: iconst_1
    //   195: aload_0
    //   196: getfield D : Lwtf/opal/ke;
    //   199: aastore
    //   200: dup
    //   201: iconst_2
    //   202: aload_0
    //   203: getfield U : Lwtf/opal/ke;
    //   206: aastore
    //   207: dup
    //   208: iconst_3
    //   209: aload_0
    //   210: getfield Z : Lwtf/opal/ke;
    //   213: aastore
    //   214: lload_3
    //   215: dup2_x1
    //   216: pop2
    //   217: iconst_2
    //   218: anewarray java/lang/Object
    //   221: dup_x1
    //   222: swap
    //   223: iconst_1
    //   224: swap
    //   225: aastore
    //   226: dup_x2
    //   227: dup_x2
    //   228: pop
    //   229: invokestatic valueOf : (J)Ljava/lang/Long;
    //   232: iconst_0
    //   233: swap
    //   234: aastore
    //   235: invokevirtual o : ([Ljava/lang/Object;)V
    //   238: return
  }
  
  private boolean Z(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: getstatic wtf/opal/j1.a : J
    //   15: lload_2
    //   16: lxor
    //   17: lstore_2
    //   18: invokestatic k : ()Z
    //   21: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   24: getfield field_1724 : Lnet/minecraft/class_746;
    //   27: invokevirtual method_31548 : ()Lnet/minecraft/class_1661;
    //   30: invokevirtual method_7391 : ()Lnet/minecraft/class_1799;
    //   33: invokevirtual method_7909 : ()Lnet/minecraft/class_1792;
    //   36: astore #5
    //   38: istore #4
    //   40: aload_0
    //   41: getfield Z : Lwtf/opal/ke;
    //   44: invokevirtual z : ()Ljava/lang/Object;
    //   47: checkcast java/lang/Boolean
    //   50: invokevirtual booleanValue : ()Z
    //   53: iload #4
    //   55: ifeq -> 120
    //   58: ifeq -> 107
    //   61: goto -> 68
    //   64: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   67: athrow
    //   68: aload #5
    //   70: instanceof net/minecraft/class_1747
    //   73: iload #4
    //   75: lload_2
    //   76: lconst_0
    //   77: lcmp
    //   78: iflt -> 122
    //   81: ifeq -> 120
    //   84: goto -> 91
    //   87: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   90: athrow
    //   91: ifeq -> 107
    //   94: goto -> 101
    //   97: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   100: athrow
    //   101: iconst_1
    //   102: ireturn
    //   103: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   106: athrow
    //   107: aload_0
    //   108: getfield U : Lwtf/opal/ke;
    //   111: invokevirtual z : ()Ljava/lang/Object;
    //   114: checkcast java/lang/Boolean
    //   117: invokevirtual booleanValue : ()Z
    //   120: iload #4
    //   122: lload_2
    //   123: lconst_0
    //   124: lcmp
    //   125: ifle -> 155
    //   128: ifeq -> 153
    //   131: ifeq -> 199
    //   134: goto -> 141
    //   137: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   140: athrow
    //   141: aload #5
    //   143: instanceof net/minecraft/class_1823
    //   146: goto -> 153
    //   149: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   152: athrow
    //   153: iload #4
    //   155: ifeq -> 196
    //   158: ifne -> 195
    //   161: goto -> 168
    //   164: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   167: athrow
    //   168: aload #5
    //   170: instanceof net/minecraft/class_1771
    //   173: iload #4
    //   175: ifeq -> 196
    //   178: goto -> 185
    //   181: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   184: athrow
    //   185: ifeq -> 199
    //   188: goto -> 195
    //   191: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   194: athrow
    //   195: iconst_1
    //   196: goto -> 200
    //   199: iconst_0
    //   200: ireturn
    // Exception table:
    //   from	to	target	type
    //   40	61	64	wtf/opal/x5
    //   58	84	87	wtf/opal/x5
    //   68	94	97	wtf/opal/x5
    //   91	103	103	wtf/opal/x5
    //   120	134	137	wtf/opal/x5
    //   131	146	149	wtf/opal/x5
    //   153	161	164	wtf/opal/x5
    //   158	178	181	wtf/opal/x5
    //   168	188	191	wtf/opal/x5
  }
  
  private boolean o(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast net/minecraft/class_2248
    //   7: astore #4
    //   9: dup
    //   10: iconst_1
    //   11: aaload
    //   12: checkcast java/lang/Long
    //   15: invokevirtual longValue : ()J
    //   18: lstore_2
    //   19: pop
    //   20: getstatic wtf/opal/j1.a : J
    //   23: lload_2
    //   24: lxor
    //   25: lstore_2
    //   26: invokestatic k : ()Z
    //   29: istore #5
    //   31: aload #4
    //   33: instanceof net/minecraft/class_4739
    //   36: iload #5
    //   38: ifeq -> 970
    //   41: ifne -> 969
    //   44: goto -> 51
    //   47: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   50: athrow
    //   51: aload #4
    //   53: instanceof net/minecraft/class_2363
    //   56: iload #5
    //   58: ifeq -> 970
    //   61: goto -> 68
    //   64: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   67: athrow
    //   68: ifne -> 969
    //   71: goto -> 78
    //   74: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   77: athrow
    //   78: aload #4
    //   80: instanceof net/minecraft/class_2304
    //   83: iload #5
    //   85: ifeq -> 970
    //   88: goto -> 95
    //   91: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   94: athrow
    //   95: ifne -> 969
    //   98: goto -> 105
    //   101: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   104: athrow
    //   105: aload #4
    //   107: instanceof net/minecraft/class_2401
    //   110: iload #5
    //   112: ifeq -> 970
    //   115: goto -> 122
    //   118: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   121: athrow
    //   122: ifne -> 969
    //   125: goto -> 132
    //   128: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   131: athrow
    //   132: aload #4
    //   134: instanceof net/minecraft/class_2323
    //   137: iload #5
    //   139: ifeq -> 970
    //   142: goto -> 149
    //   145: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   148: athrow
    //   149: ifne -> 969
    //   152: goto -> 159
    //   155: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   158: athrow
    //   159: aload #4
    //   161: instanceof net/minecraft/class_2533
    //   164: iload #5
    //   166: ifeq -> 970
    //   169: goto -> 176
    //   172: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   175: athrow
    //   176: ifne -> 969
    //   179: goto -> 186
    //   182: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   185: athrow
    //   186: aload #4
    //   188: instanceof net/minecraft/class_2244
    //   191: iload #5
    //   193: ifeq -> 970
    //   196: goto -> 203
    //   199: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   202: athrow
    //   203: ifne -> 969
    //   206: goto -> 213
    //   209: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   212: athrow
    //   213: aload #4
    //   215: instanceof net/minecraft/class_2457
    //   218: iload #5
    //   220: ifeq -> 970
    //   223: goto -> 230
    //   226: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   229: athrow
    //   230: ifne -> 969
    //   233: goto -> 240
    //   236: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   239: athrow
    //   240: aload #4
    //   242: instanceof net/minecraft/class_3736
    //   245: iload #5
    //   247: ifeq -> 970
    //   250: goto -> 257
    //   253: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   256: athrow
    //   257: ifne -> 969
    //   260: goto -> 267
    //   263: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   266: athrow
    //   267: aload #4
    //   269: instanceof net/minecraft/class_2377
    //   272: iload #5
    //   274: ifeq -> 970
    //   277: goto -> 284
    //   280: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   283: athrow
    //   284: ifne -> 969
    //   287: goto -> 294
    //   290: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   293: athrow
    //   294: aload #4
    //   296: instanceof net/minecraft/class_2331
    //   299: iload #5
    //   301: ifeq -> 970
    //   304: goto -> 311
    //   307: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   310: athrow
    //   311: ifne -> 969
    //   314: goto -> 321
    //   317: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   320: athrow
    //   321: aload #4
    //   323: instanceof net/minecraft/class_2428
    //   326: iload #5
    //   328: ifeq -> 970
    //   331: goto -> 338
    //   334: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   337: athrow
    //   338: ifne -> 969
    //   341: goto -> 348
    //   344: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   347: athrow
    //   348: aload #4
    //   350: instanceof net/minecraft/class_2387
    //   353: iload #5
    //   355: ifeq -> 970
    //   358: goto -> 365
    //   361: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   364: athrow
    //   365: ifne -> 969
    //   368: goto -> 375
    //   371: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   374: athrow
    //   375: aload #4
    //   377: instanceof net/minecraft/class_2272
    //   380: iload #5
    //   382: ifeq -> 970
    //   385: goto -> 392
    //   388: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   391: athrow
    //   392: ifne -> 969
    //   395: goto -> 402
    //   398: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   401: athrow
    //   402: aload #4
    //   404: instanceof net/minecraft/class_2349
    //   407: iload #5
    //   409: ifeq -> 970
    //   412: goto -> 419
    //   415: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   418: athrow
    //   419: ifne -> 969
    //   422: goto -> 429
    //   425: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   428: athrow
    //   429: aload #4
    //   431: instanceof net/minecraft/class_2260
    //   434: iload #5
    //   436: ifeq -> 970
    //   439: goto -> 446
    //   442: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   445: athrow
    //   446: ifne -> 969
    //   449: goto -> 456
    //   452: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   455: athrow
    //   456: aload #4
    //   458: instanceof net/minecraft/class_2328
    //   461: iload #5
    //   463: ifeq -> 970
    //   466: goto -> 473
    //   469: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   472: athrow
    //   473: ifne -> 969
    //   476: goto -> 483
    //   479: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   482: athrow
    //   483: aload #4
    //   485: instanceof net/minecraft/class_2288
    //   488: iload #5
    //   490: ifeq -> 970
    //   493: goto -> 500
    //   496: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   499: athrow
    //   500: ifne -> 969
    //   503: goto -> 510
    //   506: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   509: athrow
    //   510: aload #4
    //   512: instanceof net/minecraft/class_2238
    //   515: iload #5
    //   517: ifeq -> 970
    //   520: goto -> 527
    //   523: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   526: athrow
    //   527: ifne -> 969
    //   530: goto -> 537
    //   533: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   536: athrow
    //   537: aload #4
    //   539: instanceof net/minecraft/class_2199
    //   542: iload #5
    //   544: ifeq -> 970
    //   547: goto -> 554
    //   550: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   553: athrow
    //   554: ifne -> 969
    //   557: goto -> 564
    //   560: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   563: athrow
    //   564: aload #4
    //   566: instanceof net/minecraft/class_2286
    //   569: iload #5
    //   571: ifeq -> 970
    //   574: goto -> 581
    //   577: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   580: athrow
    //   581: ifne -> 969
    //   584: goto -> 591
    //   587: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   590: athrow
    //   591: aload #4
    //   593: instanceof net/minecraft/class_2462
    //   596: iload #5
    //   598: ifeq -> 970
    //   601: goto -> 608
    //   604: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   607: athrow
    //   608: ifne -> 969
    //   611: goto -> 618
    //   614: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   617: athrow
    //   618: aload #4
    //   620: instanceof net/minecraft/class_2315
    //   623: iload #5
    //   625: ifeq -> 970
    //   628: goto -> 635
    //   631: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   634: athrow
    //   635: ifne -> 969
    //   638: goto -> 645
    //   641: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   644: athrow
    //   645: aload #4
    //   647: instanceof net/minecraft/class_2480
    //   650: iload #5
    //   652: ifeq -> 970
    //   655: goto -> 662
    //   658: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   661: athrow
    //   662: ifne -> 969
    //   665: goto -> 672
    //   668: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   671: athrow
    //   672: aload #4
    //   674: instanceof net/minecraft/class_3715
    //   677: iload #5
    //   679: ifeq -> 970
    //   682: goto -> 689
    //   685: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   688: athrow
    //   689: ifne -> 969
    //   692: goto -> 699
    //   695: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   698: athrow
    //   699: aload #4
    //   701: instanceof net/minecraft/class_2362
    //   704: iload #5
    //   706: ifeq -> 970
    //   709: goto -> 716
    //   712: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   715: athrow
    //   716: ifne -> 969
    //   719: goto -> 726
    //   722: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   725: athrow
    //   726: aload #4
    //   728: instanceof net/minecraft/class_3708
    //   731: iload #5
    //   733: ifeq -> 970
    //   736: goto -> 743
    //   739: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   742: athrow
    //   743: ifne -> 969
    //   746: goto -> 753
    //   749: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   752: athrow
    //   753: aload #4
    //   755: instanceof net/minecraft/class_3709
    //   758: iload #5
    //   760: ifeq -> 970
    //   763: goto -> 770
    //   766: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   769: athrow
    //   770: ifne -> 969
    //   773: goto -> 780
    //   776: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   779: athrow
    //   780: aload #4
    //   782: instanceof net/minecraft/class_3717
    //   785: iload #5
    //   787: ifeq -> 970
    //   790: goto -> 797
    //   793: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   796: athrow
    //   797: ifne -> 969
    //   800: goto -> 807
    //   803: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   806: athrow
    //   807: aload #4
    //   809: instanceof net/minecraft/class_2406
    //   812: iload #5
    //   814: ifeq -> 970
    //   817: goto -> 824
    //   820: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   823: athrow
    //   824: ifne -> 969
    //   827: goto -> 834
    //   830: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   833: athrow
    //   834: aload #4
    //   836: instanceof net/minecraft/class_3711
    //   839: iload #5
    //   841: ifeq -> 970
    //   844: goto -> 851
    //   847: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   850: athrow
    //   851: ifne -> 969
    //   854: goto -> 861
    //   857: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   860: athrow
    //   861: aload #4
    //   863: instanceof net/minecraft/class_3713
    //   866: iload #5
    //   868: ifeq -> 970
    //   871: goto -> 878
    //   874: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   877: athrow
    //   878: ifne -> 969
    //   881: goto -> 888
    //   884: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   887: athrow
    //   888: aload #4
    //   890: instanceof net/minecraft/class_3718
    //   893: iload #5
    //   895: ifeq -> 970
    //   898: goto -> 905
    //   901: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   904: athrow
    //   905: ifne -> 969
    //   908: goto -> 915
    //   911: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   914: athrow
    //   915: aload #4
    //   917: instanceof net/minecraft/class_2478
    //   920: iload #5
    //   922: ifeq -> 970
    //   925: goto -> 932
    //   928: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   931: athrow
    //   932: ifne -> 969
    //   935: goto -> 942
    //   938: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   941: athrow
    //   942: aload #4
    //   944: instanceof net/minecraft/class_5540
    //   947: iload #5
    //   949: ifeq -> 970
    //   952: goto -> 959
    //   955: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   958: athrow
    //   959: ifeq -> 973
    //   962: goto -> 969
    //   965: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   968: athrow
    //   969: iconst_1
    //   970: goto -> 974
    //   973: iconst_0
    //   974: ireturn
    // Exception table:
    //   from	to	target	type
    //   31	44	47	wtf/opal/x5
    //   41	61	64	wtf/opal/x5
    //   51	71	74	wtf/opal/x5
    //   68	88	91	wtf/opal/x5
    //   78	98	101	wtf/opal/x5
    //   95	115	118	wtf/opal/x5
    //   105	125	128	wtf/opal/x5
    //   122	142	145	wtf/opal/x5
    //   132	152	155	wtf/opal/x5
    //   149	169	172	wtf/opal/x5
    //   159	179	182	wtf/opal/x5
    //   176	196	199	wtf/opal/x5
    //   186	206	209	wtf/opal/x5
    //   203	223	226	wtf/opal/x5
    //   213	233	236	wtf/opal/x5
    //   230	250	253	wtf/opal/x5
    //   240	260	263	wtf/opal/x5
    //   257	277	280	wtf/opal/x5
    //   267	287	290	wtf/opal/x5
    //   284	304	307	wtf/opal/x5
    //   294	314	317	wtf/opal/x5
    //   311	331	334	wtf/opal/x5
    //   321	341	344	wtf/opal/x5
    //   338	358	361	wtf/opal/x5
    //   348	368	371	wtf/opal/x5
    //   365	385	388	wtf/opal/x5
    //   375	395	398	wtf/opal/x5
    //   392	412	415	wtf/opal/x5
    //   402	422	425	wtf/opal/x5
    //   419	439	442	wtf/opal/x5
    //   429	449	452	wtf/opal/x5
    //   446	466	469	wtf/opal/x5
    //   456	476	479	wtf/opal/x5
    //   473	493	496	wtf/opal/x5
    //   483	503	506	wtf/opal/x5
    //   500	520	523	wtf/opal/x5
    //   510	530	533	wtf/opal/x5
    //   527	547	550	wtf/opal/x5
    //   537	557	560	wtf/opal/x5
    //   554	574	577	wtf/opal/x5
    //   564	584	587	wtf/opal/x5
    //   581	601	604	wtf/opal/x5
    //   591	611	614	wtf/opal/x5
    //   608	628	631	wtf/opal/x5
    //   618	638	641	wtf/opal/x5
    //   635	655	658	wtf/opal/x5
    //   645	665	668	wtf/opal/x5
    //   662	682	685	wtf/opal/x5
    //   672	692	695	wtf/opal/x5
    //   689	709	712	wtf/opal/x5
    //   699	719	722	wtf/opal/x5
    //   716	736	739	wtf/opal/x5
    //   726	746	749	wtf/opal/x5
    //   743	763	766	wtf/opal/x5
    //   753	773	776	wtf/opal/x5
    //   770	790	793	wtf/opal/x5
    //   780	800	803	wtf/opal/x5
    //   797	817	820	wtf/opal/x5
    //   807	827	830	wtf/opal/x5
    //   824	844	847	wtf/opal/x5
    //   834	854	857	wtf/opal/x5
    //   851	871	874	wtf/opal/x5
    //   861	881	884	wtf/opal/x5
    //   878	898	901	wtf/opal/x5
    //   888	908	911	wtf/opal/x5
    //   905	925	928	wtf/opal/x5
    //   915	935	938	wtf/opal/x5
    //   932	952	955	wtf/opal/x5
    //   942	962	965	wtf/opal/x5
  }
  
  public String o(Object[] paramArrayOfObject) {
    long l = ((Long)paramArrayOfObject[0]).longValue();
    return String.valueOf(this.t.z().intValue());
  }
  
  private void lambda$new$1(lb paramlb) {
    // Byte code:
    //   0: getstatic wtf/opal/j1.a : J
    //   3: ldc2_w 53113244857254
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 35406779265126
    //   13: lxor
    //   14: lstore #4
    //   16: pop2
    //   17: invokestatic n : ()Z
    //   20: aload_1
    //   21: iconst_0
    //   22: anewarray java/lang/Object
    //   25: invokevirtual J : ([Ljava/lang/Object;)Lnet/minecraft/class_2596;
    //   28: astore #8
    //   30: istore #6
    //   32: aload #8
    //   34: iload #6
    //   36: ifne -> 61
    //   39: instanceof net/minecraft/class_2885
    //   42: ifeq -> 279
    //   45: goto -> 52
    //   48: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   51: athrow
    //   52: aload #8
    //   54: goto -> 61
    //   57: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   60: athrow
    //   61: checkcast net/minecraft/class_2885
    //   64: astore #7
    //   66: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   69: getfield field_1724 : Lnet/minecraft/class_746;
    //   72: aload #7
    //   74: invokevirtual method_12546 : ()Lnet/minecraft/class_1268;
    //   77: invokevirtual method_5998 : (Lnet/minecraft/class_1268;)Lnet/minecraft/class_1799;
    //   80: invokevirtual method_7909 : ()Lnet/minecraft/class_1792;
    //   83: instanceof net/minecraft/class_1747
    //   86: ifeq -> 279
    //   89: aload #7
    //   91: invokevirtual method_12543 : ()Lnet/minecraft/class_3965;
    //   94: invokevirtual method_17777 : ()Lnet/minecraft/class_2338;
    //   97: aload #7
    //   99: invokevirtual method_12543 : ()Lnet/minecraft/class_3965;
    //   102: invokevirtual method_17780 : ()Lnet/minecraft/class_2350;
    //   105: invokevirtual method_10093 : (Lnet/minecraft/class_2350;)Lnet/minecraft/class_2338;
    //   108: astore #8
    //   110: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   113: iload #6
    //   115: ifne -> 247
    //   118: getfield field_1765 : Lnet/minecraft/class_239;
    //   121: ifnull -> 244
    //   124: goto -> 131
    //   127: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   130: athrow
    //   131: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   134: getfield field_1687 : Lnet/minecraft/class_638;
    //   137: iload #6
    //   139: ifne -> 250
    //   142: goto -> 149
    //   145: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   148: athrow
    //   149: ifnull -> 244
    //   152: goto -> 159
    //   155: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   158: athrow
    //   159: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   162: getfield field_1765 : Lnet/minecraft/class_239;
    //   165: invokevirtual method_17784 : ()Lnet/minecraft/class_243;
    //   168: astore #9
    //   170: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   173: getfield field_1687 : Lnet/minecraft/class_638;
    //   176: aload #9
    //   178: getfield field_1352 : D
    //   181: aload #9
    //   183: getfield field_1351 : D
    //   186: aload #9
    //   188: getfield field_1350 : D
    //   191: invokestatic method_49637 : (DDD)Lnet/minecraft/class_2338;
    //   194: invokevirtual method_8320 : (Lnet/minecraft/class_2338;)Lnet/minecraft/class_2680;
    //   197: invokevirtual method_26204 : ()Lnet/minecraft/class_2248;
    //   200: astore #10
    //   202: aload_0
    //   203: aload #10
    //   205: lload #4
    //   207: iconst_2
    //   208: anewarray java/lang/Object
    //   211: dup_x2
    //   212: dup_x2
    //   213: pop
    //   214: invokestatic valueOf : (J)Ljava/lang/Long;
    //   217: iconst_1
    //   218: swap
    //   219: aastore
    //   220: dup_x1
    //   221: swap
    //   222: iconst_0
    //   223: swap
    //   224: aastore
    //   225: invokevirtual o : ([Ljava/lang/Object;)Z
    //   228: iload #6
    //   230: ifne -> 261
    //   233: ifeq -> 244
    //   236: goto -> 243
    //   239: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   242: athrow
    //   243: return
    //   244: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   247: getfield field_1687 : Lnet/minecraft/class_638;
    //   250: aload #8
    //   252: invokevirtual method_8320 : (Lnet/minecraft/class_2338;)Lnet/minecraft/class_2680;
    //   255: invokevirtual method_26204 : ()Lnet/minecraft/class_2248;
    //   258: instanceof net/minecraft/class_2189
    //   261: ifeq -> 279
    //   264: aload_1
    //   265: iconst_0
    //   266: anewarray java/lang/Object
    //   269: invokevirtual Z : ([Ljava/lang/Object;)V
    //   272: goto -> 279
    //   275: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   278: athrow
    //   279: return
    // Exception table:
    //   from	to	target	type
    //   32	45	48	wtf/opal/x5
    //   39	54	57	wtf/opal/x5
    //   110	124	127	wtf/opal/x5
    //   118	142	145	wtf/opal/x5
    //   131	152	155	wtf/opal/x5
    //   202	236	239	wtf/opal/x5
    //   261	272	275	wtf/opal/x5
  }
  
  private void lambda$new$0(b6 paramb6) {
    // Byte code:
    //   0: getstatic wtf/opal/j1.a : J
    //   3: ldc2_w 106047852909028
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 56050665749114
    //   13: lxor
    //   14: lstore #4
    //   16: pop2
    //   17: invokestatic k : ()Z
    //   20: istore #6
    //   22: aload_1
    //   23: iconst_0
    //   24: anewarray java/lang/Object
    //   27: invokevirtual W : ([Ljava/lang/Object;)Z
    //   30: ifeq -> 106
    //   33: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   36: iload #6
    //   38: ifeq -> 71
    //   41: goto -> 48
    //   44: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   47: athrow
    //   48: getfield field_1724 : Lnet/minecraft/class_746;
    //   51: ifnull -> 106
    //   54: goto -> 61
    //   57: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   60: athrow
    //   61: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   64: goto -> 71
    //   67: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   70: athrow
    //   71: getfield field_1687 : Lnet/minecraft/class_638;
    //   74: ifnull -> 106
    //   77: aload_0
    //   78: lload #4
    //   80: iconst_1
    //   81: anewarray java/lang/Object
    //   84: dup_x2
    //   85: dup_x2
    //   86: pop
    //   87: invokestatic valueOf : (J)Ljava/lang/Long;
    //   90: iconst_0
    //   91: swap
    //   92: aastore
    //   93: invokevirtual Z : ([Ljava/lang/Object;)Z
    //   96: ifne -> 111
    //   99: goto -> 106
    //   102: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   105: athrow
    //   106: return
    //   107: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   110: athrow
    //   111: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   114: checkcast wtf/opal/mixin/MinecraftClientAccessor
    //   117: astore #7
    //   119: aload_0
    //   120: getfield t : Lwtf/opal/kt;
    //   123: invokevirtual z : ()Ljava/lang/Object;
    //   126: checkcast java/lang/Double
    //   129: invokevirtual intValue : ()I
    //   132: istore #8
    //   134: iload #6
    //   136: ifeq -> 235
    //   139: aload_0
    //   140: getfield D : Lwtf/opal/ke;
    //   143: invokevirtual z : ()Ljava/lang/Object;
    //   146: checkcast java/lang/Boolean
    //   149: invokevirtual booleanValue : ()Z
    //   152: ifeq -> 216
    //   155: goto -> 162
    //   158: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   161: athrow
    //   162: iload #8
    //   164: iload #6
    //   166: ifeq -> 214
    //   169: goto -> 176
    //   172: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   175: athrow
    //   176: iconst_3
    //   177: if_icmpge -> 216
    //   180: goto -> 187
    //   183: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   186: athrow
    //   187: invokestatic current : ()Ljava/util/concurrent/ThreadLocalRandom;
    //   190: iload #8
    //   192: i2d
    //   193: iload #8
    //   195: i2d
    //   196: ldc2_w 1.75
    //   199: dadd
    //   200: invokevirtual nextDouble : (DD)D
    //   203: invokestatic rint : (D)D
    //   206: d2i
    //   207: goto -> 214
    //   210: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   213: athrow
    //   214: istore #8
    //   216: aload #7
    //   218: aload #7
    //   220: invokeinterface getItemUseCooldown : ()I
    //   225: iload #8
    //   227: invokestatic min : (II)I
    //   230: invokeinterface setItemUseCooldown : (I)V
    //   235: return
    // Exception table:
    //   from	to	target	type
    //   22	41	44	wtf/opal/x5
    //   33	54	57	wtf/opal/x5
    //   48	64	67	wtf/opal/x5
    //   71	99	102	wtf/opal/x5
    //   77	107	107	wtf/opal/x5
    //   134	155	158	wtf/opal/x5
    //   139	169	172	wtf/opal/x5
    //   162	180	183	wtf/opal/x5
    //   176	207	210	wtf/opal/x5
  }
  
  static {
    long l = a ^ 0x47B483721803L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[6];
    boolean bool = false;
    String str;
    int i = (str = "P;\t[\005Nò°.§X6\035}\005j\021è\")ðÕbZÒ\024ü\035SÏh\030©y`þÙ$ðÆE\005-^ë~á\0270\"¯ÆöÀ ¥¦$xÎ0\031Ô\r¿G®îpa#B\032pàßÁµØ³!\016\020Â=y±\013É¤^;>úÌ\025k\027").length();
    byte b2 = 40;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x5965;
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
        throw new RuntimeException("wtf/opal/j1", exception);
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
    //   65: ldc_w 'wtf/opal/j1'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\j1.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */