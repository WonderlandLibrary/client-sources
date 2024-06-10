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

public final class b_ extends b5<ky<?>> {
  private final dk E;
  
  private static final long b;
  
  private static final long[] c;
  
  private static final Integer[] d;
  
  private static final Map f;
  
  public b_(ky paramky, long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/b_.b : J
    //   3: lload_2
    //   4: lxor
    //   5: lstore_2
    //   6: lload_2
    //   7: dup2
    //   8: ldc2_w 96492625961616
    //   11: lxor
    //   12: dup2
    //   13: bipush #32
    //   15: lushr
    //   16: l2i
    //   17: istore #4
    //   19: dup2
    //   20: bipush #32
    //   22: lshl
    //   23: bipush #48
    //   25: lushr
    //   26: l2i
    //   27: istore #5
    //   29: dup2
    //   30: bipush #48
    //   32: lshl
    //   33: bipush #48
    //   35: lushr
    //   36: l2i
    //   37: istore #6
    //   39: pop2
    //   40: dup2
    //   41: ldc2_w 53036171332098
    //   44: lxor
    //   45: lstore #7
    //   47: pop2
    //   48: aload_0
    //   49: aload_1
    //   50: lload #7
    //   52: invokespecial <init> : (Lwtf/opal/k3;J)V
    //   55: aload_0
    //   56: new wtf/opal/dk
    //   59: dup
    //   60: sipush #17040
    //   63: ldc2_w 1776491384946747229
    //   66: lload_2
    //   67: lxor
    //   68: <illegal opcode> c : (IJ)I
    //   73: iload #4
    //   75: dconst_1
    //   76: iload #5
    //   78: i2c
    //   79: iload #6
    //   81: getstatic wtf/opal/lp.FORWARDS : Lwtf/opal/lp;
    //   84: invokespecial <init> : (IIDCILwtf/opal/lp;)V
    //   87: putfield E : Lwtf/opal/dk;
    //   90: return
  }
  
  public void g(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast net/minecraft/class_332
    //   7: astore #7
    //   9: dup
    //   10: iconst_1
    //   11: aaload
    //   12: checkcast java/lang/Integer
    //   15: invokevirtual intValue : ()I
    //   18: istore #5
    //   20: dup
    //   21: iconst_2
    //   22: aaload
    //   23: checkcast java/lang/Integer
    //   26: invokevirtual intValue : ()I
    //   29: istore #6
    //   31: dup
    //   32: iconst_3
    //   33: aaload
    //   34: checkcast java/lang/Long
    //   37: invokevirtual longValue : ()J
    //   40: lstore_2
    //   41: dup
    //   42: iconst_4
    //   43: aaload
    //   44: checkcast java/lang/Float
    //   47: invokevirtual floatValue : ()F
    //   50: fstore #4
    //   52: pop
    //   53: lload_2
    //   54: dup2
    //   55: ldc2_w 10802718197547
    //   58: lxor
    //   59: lstore #8
    //   61: dup2
    //   62: ldc2_w 28729273170977
    //   65: lxor
    //   66: lstore #10
    //   68: dup2
    //   69: ldc2_w 69422246435098
    //   72: lxor
    //   73: lstore #12
    //   75: dup2
    //   76: ldc2_w 66468241307817
    //   79: lxor
    //   80: lstore #14
    //   82: dup2
    //   83: ldc2_w 30394948788711
    //   86: lxor
    //   87: lstore #16
    //   89: dup2
    //   90: ldc2_w 135688353470910
    //   93: lxor
    //   94: lstore #18
    //   96: dup2
    //   97: ldc2_w 40314075635121
    //   100: lxor
    //   101: lstore #20
    //   103: dup2
    //   104: ldc2_w 86228387855835
    //   107: lxor
    //   108: lstore #22
    //   110: dup2
    //   111: ldc2_w 53439933111312
    //   114: lxor
    //   115: lstore #24
    //   117: pop2
    //   118: aload_0
    //   119: getfield e : F
    //   122: ldc 9.0
    //   124: fsub
    //   125: fstore #31
    //   127: aload_0
    //   128: getfield m : F
    //   131: aload_0
    //   132: iconst_0
    //   133: anewarray java/lang/Object
    //   136: invokevirtual U : ([Ljava/lang/Object;)F
    //   139: fsub
    //   140: ldc 5.0
    //   142: fsub
    //   143: fstore #32
    //   145: getstatic wtf/opal/b_.h : Lwtf/opal/pa;
    //   148: aload_0
    //   149: getfield U : F
    //   152: aload_0
    //   153: getfield t : F
    //   156: aload_0
    //   157: getfield e : F
    //   160: aload_0
    //   161: getfield m : F
    //   164: sipush #31898
    //   167: ldc2_w 2542265409442412682
    //   170: lload_2
    //   171: lxor
    //   172: <illegal opcode> c : (IJ)I
    //   177: lload #14
    //   179: bipush #6
    //   181: anewarray java/lang/Object
    //   184: dup_x2
    //   185: dup_x2
    //   186: pop
    //   187: invokestatic valueOf : (J)Ljava/lang/Long;
    //   190: iconst_5
    //   191: swap
    //   192: aastore
    //   193: dup_x1
    //   194: swap
    //   195: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   198: iconst_4
    //   199: swap
    //   200: aastore
    //   201: dup_x1
    //   202: swap
    //   203: invokestatic valueOf : (F)Ljava/lang/Float;
    //   206: iconst_3
    //   207: swap
    //   208: aastore
    //   209: dup_x1
    //   210: swap
    //   211: invokestatic valueOf : (F)Ljava/lang/Float;
    //   214: iconst_2
    //   215: swap
    //   216: aastore
    //   217: dup_x1
    //   218: swap
    //   219: invokestatic valueOf : (F)Ljava/lang/Float;
    //   222: iconst_1
    //   223: swap
    //   224: aastore
    //   225: dup_x1
    //   226: swap
    //   227: invokestatic valueOf : (F)Ljava/lang/Float;
    //   230: iconst_0
    //   231: swap
    //   232: aastore
    //   233: invokevirtual k : ([Ljava/lang/Object;)V
    //   236: getstatic wtf/opal/b_.h : Lwtf/opal/pa;
    //   239: aload_0
    //   240: getfield U : F
    //   243: ldc 4.5
    //   245: fadd
    //   246: aload_0
    //   247: getfield t : F
    //   250: ldc 2.5
    //   252: fadd
    //   253: fload #31
    //   255: aload_0
    //   256: getfield m : F
    //   259: ldc 5.0
    //   261: fsub
    //   262: lload #20
    //   264: ldc 5.0
    //   266: fconst_1
    //   267: sipush #17854
    //   270: ldc2_w 1664281062604456367
    //   273: lload_2
    //   274: lxor
    //   275: <illegal opcode> c : (IJ)I
    //   280: bipush #8
    //   282: anewarray java/lang/Object
    //   285: dup_x1
    //   286: swap
    //   287: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   290: bipush #7
    //   292: swap
    //   293: aastore
    //   294: dup_x1
    //   295: swap
    //   296: invokestatic valueOf : (F)Ljava/lang/Float;
    //   299: bipush #6
    //   301: swap
    //   302: aastore
    //   303: dup_x1
    //   304: swap
    //   305: invokestatic valueOf : (F)Ljava/lang/Float;
    //   308: iconst_5
    //   309: swap
    //   310: aastore
    //   311: dup_x2
    //   312: dup_x2
    //   313: pop
    //   314: invokestatic valueOf : (J)Ljava/lang/Long;
    //   317: iconst_4
    //   318: swap
    //   319: aastore
    //   320: dup_x1
    //   321: swap
    //   322: invokestatic valueOf : (F)Ljava/lang/Float;
    //   325: iconst_3
    //   326: swap
    //   327: aastore
    //   328: dup_x1
    //   329: swap
    //   330: invokestatic valueOf : (F)Ljava/lang/Float;
    //   333: iconst_2
    //   334: swap
    //   335: aastore
    //   336: dup_x1
    //   337: swap
    //   338: invokestatic valueOf : (F)Ljava/lang/Float;
    //   341: iconst_1
    //   342: swap
    //   343: aastore
    //   344: dup_x1
    //   345: swap
    //   346: invokestatic valueOf : (F)Ljava/lang/Float;
    //   349: iconst_0
    //   350: swap
    //   351: aastore
    //   352: invokevirtual G : ([Ljava/lang/Object;)V
    //   355: getstatic wtf/opal/b_.Z : Lwtf/opal/bu;
    //   358: aload_0
    //   359: iconst_0
    //   360: anewarray java/lang/Object
    //   363: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   366: checkcast wtf/opal/ky
    //   369: iconst_0
    //   370: anewarray java/lang/Object
    //   373: invokevirtual r : ([Ljava/lang/Object;)Ljava/lang/String;
    //   376: ldc 6.5
    //   378: lload #8
    //   380: iconst_3
    //   381: anewarray java/lang/Object
    //   384: dup_x2
    //   385: dup_x2
    //   386: pop
    //   387: invokestatic valueOf : (J)Ljava/lang/Long;
    //   390: iconst_2
    //   391: swap
    //   392: aastore
    //   393: dup_x1
    //   394: swap
    //   395: invokestatic valueOf : (F)Ljava/lang/Float;
    //   398: iconst_1
    //   399: swap
    //   400: aastore
    //   401: dup_x1
    //   402: swap
    //   403: iconst_0
    //   404: swap
    //   405: aastore
    //   406: invokevirtual s : ([Ljava/lang/Object;)F
    //   409: fstore #33
    //   411: getstatic wtf/opal/b_.Z : Lwtf/opal/bu;
    //   414: aload_0
    //   415: iconst_0
    //   416: anewarray java/lang/Object
    //   419: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   422: checkcast wtf/opal/ky
    //   425: iconst_0
    //   426: anewarray java/lang/Object
    //   429: invokevirtual r : ([Ljava/lang/Object;)Ljava/lang/String;
    //   432: ldc 6.5
    //   434: lload #16
    //   436: sipush #28731
    //   439: ldc2_w 2470106400163460142
    //   442: lload_2
    //   443: lxor
    //   444: <illegal opcode> c : (IJ)I
    //   449: iconst_4
    //   450: anewarray java/lang/Object
    //   453: dup_x1
    //   454: swap
    //   455: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   458: iconst_3
    //   459: swap
    //   460: aastore
    //   461: dup_x2
    //   462: dup_x2
    //   463: pop
    //   464: invokestatic valueOf : (J)Ljava/lang/Long;
    //   467: iconst_2
    //   468: swap
    //   469: aastore
    //   470: dup_x1
    //   471: swap
    //   472: invokestatic valueOf : (F)Ljava/lang/Float;
    //   475: iconst_1
    //   476: swap
    //   477: aastore
    //   478: dup_x1
    //   479: swap
    //   480: iconst_0
    //   481: swap
    //   482: aastore
    //   483: invokevirtual E : ([Ljava/lang/Object;)F
    //   486: fstore #34
    //   488: invokestatic S : ()[Lwtf/opal/d;
    //   491: getstatic wtf/opal/b_.Z : Lwtf/opal/bu;
    //   494: aload #7
    //   496: aload_0
    //   497: iconst_0
    //   498: anewarray java/lang/Object
    //   501: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   504: checkcast wtf/opal/ky
    //   507: iconst_0
    //   508: anewarray java/lang/Object
    //   511: invokevirtual r : ([Ljava/lang/Object;)Ljava/lang/String;
    //   514: aload_0
    //   515: getfield U : F
    //   518: ldc 4.5
    //   520: fadd
    //   521: fload #31
    //   523: fconst_2
    //   524: fdiv
    //   525: fadd
    //   526: fload #33
    //   528: fconst_2
    //   529: fdiv
    //   530: fsub
    //   531: aload_0
    //   532: getfield t : F
    //   535: ldc 2.5
    //   537: fadd
    //   538: fload #32
    //   540: fconst_2
    //   541: fdiv
    //   542: fadd
    //   543: fload #34
    //   545: fconst_2
    //   546: fdiv
    //   547: fsub
    //   548: ldc 6.5
    //   550: iconst_m1
    //   551: iconst_1
    //   552: lload #22
    //   554: bipush #8
    //   556: anewarray java/lang/Object
    //   559: dup_x2
    //   560: dup_x2
    //   561: pop
    //   562: invokestatic valueOf : (J)Ljava/lang/Long;
    //   565: bipush #7
    //   567: swap
    //   568: aastore
    //   569: dup_x1
    //   570: swap
    //   571: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   574: bipush #6
    //   576: swap
    //   577: aastore
    //   578: dup_x1
    //   579: swap
    //   580: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   583: iconst_5
    //   584: swap
    //   585: aastore
    //   586: dup_x1
    //   587: swap
    //   588: invokestatic valueOf : (F)Ljava/lang/Float;
    //   591: iconst_4
    //   592: swap
    //   593: aastore
    //   594: dup_x1
    //   595: swap
    //   596: invokestatic valueOf : (F)Ljava/lang/Float;
    //   599: iconst_3
    //   600: swap
    //   601: aastore
    //   602: dup_x1
    //   603: swap
    //   604: invokestatic valueOf : (F)Ljava/lang/Float;
    //   607: iconst_2
    //   608: swap
    //   609: aastore
    //   610: dup_x1
    //   611: swap
    //   612: iconst_1
    //   613: swap
    //   614: aastore
    //   615: dup_x1
    //   616: swap
    //   617: iconst_0
    //   618: swap
    //   619: aastore
    //   620: invokevirtual B : ([Ljava/lang/Object;)V
    //   623: astore #30
    //   625: aload_0
    //   626: getfield E : Lwtf/opal/dk;
    //   629: aload_0
    //   630: iconst_0
    //   631: anewarray java/lang/Object
    //   634: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   637: checkcast wtf/opal/ky
    //   640: iconst_0
    //   641: anewarray java/lang/Object
    //   644: invokevirtual r : ([Ljava/lang/Object;)Z
    //   647: ifeq -> 660
    //   650: getstatic wtf/opal/lp.FORWARDS : Lwtf/opal/lp;
    //   653: goto -> 663
    //   656: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   659: athrow
    //   660: getstatic wtf/opal/lp.BACKWARDS : Lwtf/opal/lp;
    //   663: lload #12
    //   665: dup2_x1
    //   666: pop2
    //   667: iconst_2
    //   668: anewarray java/lang/Object
    //   671: dup_x1
    //   672: swap
    //   673: iconst_1
    //   674: swap
    //   675: aastore
    //   676: dup_x2
    //   677: dup_x2
    //   678: pop
    //   679: invokestatic valueOf : (J)Ljava/lang/Long;
    //   682: iconst_0
    //   683: swap
    //   684: aastore
    //   685: invokevirtual p : ([Ljava/lang/Object;)Lwtf/opal/dx;
    //   688: pop
    //   689: aload_0
    //   690: iconst_0
    //   691: anewarray java/lang/Object
    //   694: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   697: checkcast wtf/opal/ky
    //   700: iconst_0
    //   701: anewarray java/lang/Object
    //   704: invokevirtual r : ([Ljava/lang/Object;)Z
    //   707: aload #30
    //   709: ifnull -> 769
    //   712: ifne -> 772
    //   715: goto -> 722
    //   718: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   721: athrow
    //   722: aload_0
    //   723: aload #30
    //   725: lload_2
    //   726: lconst_0
    //   727: lcmp
    //   728: iflt -> 1365
    //   731: ifnull -> 1352
    //   734: goto -> 741
    //   737: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   740: athrow
    //   741: getfield E : Lwtf/opal/dk;
    //   744: lload #24
    //   746: iconst_1
    //   747: anewarray java/lang/Object
    //   750: dup_x2
    //   751: dup_x2
    //   752: pop
    //   753: invokestatic valueOf : (J)Ljava/lang/Long;
    //   756: iconst_0
    //   757: swap
    //   758: aastore
    //   759: invokevirtual H : ([Ljava/lang/Object;)Z
    //   762: goto -> 769
    //   765: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   768: athrow
    //   769: ifne -> 1344
    //   772: fconst_0
    //   773: fstore #35
    //   775: aload_0
    //   776: iconst_0
    //   777: anewarray java/lang/Object
    //   780: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   783: checkcast wtf/opal/ky
    //   786: iconst_0
    //   787: anewarray java/lang/Object
    //   790: invokevirtual A : ([Ljava/lang/Object;)[Ljava/lang/Enum;
    //   793: astore #36
    //   795: aload #36
    //   797: arraylength
    //   798: istore #37
    //   800: iconst_0
    //   801: istore #38
    //   803: iload #38
    //   805: iload #37
    //   807: if_icmpge -> 1285
    //   810: aload #36
    //   812: iload #38
    //   814: aaload
    //   815: astore #39
    //   817: getstatic wtf/opal/b_.Z : Lwtf/opal/bu;
    //   820: aload #39
    //   822: invokevirtual toString : ()Ljava/lang/String;
    //   825: ldc 6.5
    //   827: lload #8
    //   829: iconst_3
    //   830: anewarray java/lang/Object
    //   833: dup_x2
    //   834: dup_x2
    //   835: pop
    //   836: invokestatic valueOf : (J)Ljava/lang/Long;
    //   839: iconst_2
    //   840: swap
    //   841: aastore
    //   842: dup_x1
    //   843: swap
    //   844: invokestatic valueOf : (F)Ljava/lang/Float;
    //   847: iconst_1
    //   848: swap
    //   849: aastore
    //   850: dup_x1
    //   851: swap
    //   852: iconst_0
    //   853: swap
    //   854: aastore
    //   855: invokevirtual s : ([Ljava/lang/Object;)F
    //   858: fstore #40
    //   860: getstatic wtf/opal/b_.Z : Lwtf/opal/bu;
    //   863: aload #39
    //   865: invokevirtual toString : ()Ljava/lang/String;
    //   868: ldc 6.5
    //   870: lload #16
    //   872: sipush #15160
    //   875: ldc2_w 7062257850884996908
    //   878: lload_2
    //   879: lxor
    //   880: <illegal opcode> c : (IJ)I
    //   885: iconst_4
    //   886: anewarray java/lang/Object
    //   889: dup_x1
    //   890: swap
    //   891: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   894: iconst_3
    //   895: swap
    //   896: aastore
    //   897: dup_x2
    //   898: dup_x2
    //   899: pop
    //   900: invokestatic valueOf : (J)Ljava/lang/Long;
    //   903: iconst_2
    //   904: swap
    //   905: aastore
    //   906: dup_x1
    //   907: swap
    //   908: invokestatic valueOf : (F)Ljava/lang/Float;
    //   911: iconst_1
    //   912: swap
    //   913: aastore
    //   914: dup_x1
    //   915: swap
    //   916: iconst_0
    //   917: swap
    //   918: aastore
    //   919: invokevirtual E : ([Ljava/lang/Object;)F
    //   922: fstore #41
    //   924: aload #30
    //   926: lload_2
    //   927: lconst_0
    //   928: lcmp
    //   929: iflt -> 1341
    //   932: ifnull -> 1339
    //   935: getstatic wtf/opal/b_.Z : Lwtf/opal/bu;
    //   938: aload #7
    //   940: aload #39
    //   942: invokevirtual toString : ()Ljava/lang/String;
    //   945: aload_0
    //   946: getfield U : F
    //   949: ldc 4.5
    //   951: fadd
    //   952: fload #31
    //   954: fconst_2
    //   955: fdiv
    //   956: fadd
    //   957: fload #40
    //   959: fconst_2
    //   960: fdiv
    //   961: fsub
    //   962: aload_0
    //   963: getfield t : F
    //   966: ldc 2.5
    //   968: fload #32
    //   970: fconst_2
    //   971: fdiv
    //   972: fadd
    //   973: fload #41
    //   975: fconst_2
    //   976: fdiv
    //   977: fsub
    //   978: fload #34
    //   980: fadd
    //   981: aload_0
    //   982: getfield E : Lwtf/opal/dk;
    //   985: lload #18
    //   987: iconst_1
    //   988: anewarray java/lang/Object
    //   991: dup_x2
    //   992: dup_x2
    //   993: pop
    //   994: invokestatic valueOf : (J)Ljava/lang/Long;
    //   997: iconst_0
    //   998: swap
    //   999: aastore
    //   1000: invokevirtual P : ([Ljava/lang/Object;)Ljava/lang/Double;
    //   1003: invokevirtual floatValue : ()F
    //   1006: fmul
    //   1007: fadd
    //   1008: fload #35
    //   1010: fadd
    //   1011: ldc 5.0
    //   1013: fadd
    //   1014: ldc 6.5
    //   1016: aload_0
    //   1017: iconst_0
    //   1018: anewarray java/lang/Object
    //   1021: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   1024: checkcast wtf/opal/ky
    //   1027: invokevirtual z : ()Ljava/lang/Object;
    //   1030: checkcast java/lang/Enum
    //   1033: aload #39
    //   1035: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1038: aload #30
    //   1040: lload_2
    //   1041: lconst_0
    //   1042: lcmp
    //   1043: ifle -> 1178
    //   1046: ifnull -> 1141
    //   1049: goto -> 1056
    //   1052: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1055: athrow
    //   1056: lload_2
    //   1057: lconst_0
    //   1058: lcmp
    //   1059: ifle -> 1131
    //   1062: ifeq -> 1128
    //   1065: goto -> 1072
    //   1068: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1071: athrow
    //   1072: iconst_m1
    //   1073: aload_0
    //   1074: getfield E : Lwtf/opal/dk;
    //   1077: lload #18
    //   1079: iconst_1
    //   1080: anewarray java/lang/Object
    //   1083: dup_x2
    //   1084: dup_x2
    //   1085: pop
    //   1086: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1089: iconst_0
    //   1090: swap
    //   1091: aastore
    //   1092: invokevirtual P : ([Ljava/lang/Object;)Ljava/lang/Double;
    //   1095: invokevirtual floatValue : ()F
    //   1098: iconst_2
    //   1099: anewarray java/lang/Object
    //   1102: dup_x1
    //   1103: swap
    //   1104: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1107: iconst_1
    //   1108: swap
    //   1109: aastore
    //   1110: dup_x1
    //   1111: swap
    //   1112: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1115: iconst_0
    //   1116: swap
    //   1117: aastore
    //   1118: invokestatic X : ([Ljava/lang/Object;)I
    //   1121: goto -> 1189
    //   1124: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1127: athrow
    //   1128: sipush #8529
    //   1131: ldc2_w 4821641819049010498
    //   1134: lload_2
    //   1135: lxor
    //   1136: <illegal opcode> c : (IJ)I
    //   1141: aload_0
    //   1142: getfield E : Lwtf/opal/dk;
    //   1145: lload #18
    //   1147: iconst_1
    //   1148: anewarray java/lang/Object
    //   1151: dup_x2
    //   1152: dup_x2
    //   1153: pop
    //   1154: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1157: iconst_0
    //   1158: swap
    //   1159: aastore
    //   1160: invokevirtual P : ([Ljava/lang/Object;)Ljava/lang/Double;
    //   1163: invokevirtual floatValue : ()F
    //   1166: iconst_2
    //   1167: anewarray java/lang/Object
    //   1170: dup_x1
    //   1171: swap
    //   1172: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1175: iconst_1
    //   1176: swap
    //   1177: aastore
    //   1178: dup_x1
    //   1179: swap
    //   1180: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1183: iconst_0
    //   1184: swap
    //   1185: aastore
    //   1186: invokestatic X : ([Ljava/lang/Object;)I
    //   1189: istore #26
    //   1191: fstore #27
    //   1193: fstore #28
    //   1195: fstore #29
    //   1197: lload #10
    //   1199: fload #29
    //   1201: fload #28
    //   1203: fload #27
    //   1205: iload #26
    //   1207: bipush #7
    //   1209: anewarray java/lang/Object
    //   1212: dup_x1
    //   1213: swap
    //   1214: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1217: bipush #6
    //   1219: swap
    //   1220: aastore
    //   1221: dup_x1
    //   1222: swap
    //   1223: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1226: iconst_5
    //   1227: swap
    //   1228: aastore
    //   1229: dup_x1
    //   1230: swap
    //   1231: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1234: iconst_4
    //   1235: swap
    //   1236: aastore
    //   1237: dup_x1
    //   1238: swap
    //   1239: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1242: iconst_3
    //   1243: swap
    //   1244: aastore
    //   1245: dup_x2
    //   1246: dup_x2
    //   1247: pop
    //   1248: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1251: iconst_2
    //   1252: swap
    //   1253: aastore
    //   1254: dup_x1
    //   1255: swap
    //   1256: iconst_1
    //   1257: swap
    //   1258: aastore
    //   1259: dup_x1
    //   1260: swap
    //   1261: iconst_0
    //   1262: swap
    //   1263: aastore
    //   1264: invokevirtual R : ([Ljava/lang/Object;)V
    //   1267: fload #35
    //   1269: fload #41
    //   1271: ldc 4.0
    //   1273: fadd
    //   1274: fadd
    //   1275: fstore #35
    //   1277: iinc #38, 1
    //   1280: aload #30
    //   1282: ifnonnull -> 803
    //   1285: aload_0
    //   1286: fload #35
    //   1288: fconst_2
    //   1289: fadd
    //   1290: f2d
    //   1291: aload_0
    //   1292: getfield E : Lwtf/opal/dk;
    //   1295: lload #18
    //   1297: iconst_1
    //   1298: anewarray java/lang/Object
    //   1301: dup_x2
    //   1302: dup_x2
    //   1303: pop
    //   1304: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1307: iconst_0
    //   1308: swap
    //   1309: aastore
    //   1310: invokevirtual P : ([Ljava/lang/Object;)Ljava/lang/Double;
    //   1313: invokevirtual doubleValue : ()D
    //   1316: dmul
    //   1317: d2f
    //   1318: iconst_1
    //   1319: anewarray java/lang/Object
    //   1322: dup_x1
    //   1323: swap
    //   1324: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1327: iconst_0
    //   1328: swap
    //   1329: aastore
    //   1330: invokevirtual q : ([Ljava/lang/Object;)V
    //   1333: lload_2
    //   1334: lconst_0
    //   1335: lcmp
    //   1336: iflt -> 1339
    //   1339: aload #30
    //   1341: ifnonnull -> 1368
    //   1344: aload_0
    //   1345: goto -> 1352
    //   1348: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1351: athrow
    //   1352: fconst_0
    //   1353: iconst_1
    //   1354: anewarray java/lang/Object
    //   1357: dup_x1
    //   1358: swap
    //   1359: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1362: iconst_0
    //   1363: swap
    //   1364: aastore
    //   1365: invokevirtual q : ([Ljava/lang/Object;)V
    //   1368: return
    // Exception table:
    //   from	to	target	type
    //   625	656	656	wtf/opal/x5
    //   663	715	718	wtf/opal/x5
    //   712	734	737	wtf/opal/x5
    //   722	762	765	wtf/opal/x5
    //   924	1049	1052	wtf/opal/x5
    //   935	1065	1068	wtf/opal/x5
    //   1056	1124	1124	wtf/opal/x5
    //   1339	1345	1348	wtf/opal/x5
  }
  
  public void e(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Double
    //   7: invokevirtual doubleValue : ()D
    //   10: dstore_2
    //   11: dup
    //   12: iconst_1
    //   13: aaload
    //   14: checkcast java/lang/Double
    //   17: invokevirtual doubleValue : ()D
    //   20: dstore #4
    //   22: dup
    //   23: iconst_2
    //   24: aaload
    //   25: checkcast java/lang/Long
    //   28: invokevirtual longValue : ()J
    //   31: lstore #6
    //   33: dup
    //   34: iconst_3
    //   35: aaload
    //   36: checkcast java/lang/Integer
    //   39: invokevirtual intValue : ()I
    //   42: istore #8
    //   44: pop
    //   45: lload #6
    //   47: dup2
    //   48: ldc2_w 91173079021596
    //   51: lxor
    //   52: lstore #9
    //   54: dup2
    //   55: ldc2_w 50377552633992
    //   58: lxor
    //   59: lstore #11
    //   61: dup2
    //   62: ldc2_w 58562124928931
    //   65: lxor
    //   66: lstore #13
    //   68: dup2
    //   69: ldc2_w 43107960303983
    //   72: lxor
    //   73: lstore #15
    //   75: pop2
    //   76: invokestatic S : ()[Lwtf/opal/d;
    //   79: aload_0
    //   80: getfield m : F
    //   83: aload_0
    //   84: iconst_0
    //   85: anewarray java/lang/Object
    //   88: invokevirtual U : ([Ljava/lang/Object;)F
    //   91: fsub
    //   92: ldc 5.0
    //   94: fsub
    //   95: fstore #26
    //   97: astore #25
    //   99: aload_0
    //   100: getfield e : F
    //   103: ldc 9.0
    //   105: fsub
    //   106: fstore #27
    //   108: aload_0
    //   109: getfield U : F
    //   112: lload #9
    //   114: dup2_x1
    //   115: pop2
    //   116: aload_0
    //   117: getfield t : F
    //   120: aload_0
    //   121: getfield e : F
    //   124: fload #26
    //   126: dload_2
    //   127: dload #4
    //   129: bipush #7
    //   131: anewarray java/lang/Object
    //   134: dup_x2
    //   135: dup_x2
    //   136: pop
    //   137: invokestatic valueOf : (D)Ljava/lang/Double;
    //   140: bipush #6
    //   142: swap
    //   143: aastore
    //   144: dup_x2
    //   145: dup_x2
    //   146: pop
    //   147: invokestatic valueOf : (D)Ljava/lang/Double;
    //   150: iconst_5
    //   151: swap
    //   152: aastore
    //   153: dup_x1
    //   154: swap
    //   155: invokestatic valueOf : (F)Ljava/lang/Float;
    //   158: iconst_4
    //   159: swap
    //   160: aastore
    //   161: dup_x1
    //   162: swap
    //   163: invokestatic valueOf : (F)Ljava/lang/Float;
    //   166: iconst_3
    //   167: swap
    //   168: aastore
    //   169: dup_x1
    //   170: swap
    //   171: invokestatic valueOf : (F)Ljava/lang/Float;
    //   174: iconst_2
    //   175: swap
    //   176: aastore
    //   177: dup_x1
    //   178: swap
    //   179: invokestatic valueOf : (F)Ljava/lang/Float;
    //   182: iconst_1
    //   183: swap
    //   184: aastore
    //   185: dup_x2
    //   186: dup_x2
    //   187: pop
    //   188: invokestatic valueOf : (J)Ljava/lang/Long;
    //   191: iconst_0
    //   192: swap
    //   193: aastore
    //   194: invokestatic Z : ([Ljava/lang/Object;)Z
    //   197: aload #25
    //   199: ifnull -> 317
    //   202: ifeq -> 315
    //   205: goto -> 212
    //   208: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   211: athrow
    //   212: iload #8
    //   214: aload #25
    //   216: lload #6
    //   218: lconst_0
    //   219: lcmp
    //   220: ifle -> 319
    //   223: ifnull -> 317
    //   226: goto -> 233
    //   229: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   232: athrow
    //   233: iconst_1
    //   234: if_icmpne -> 315
    //   237: goto -> 244
    //   240: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   243: athrow
    //   244: aload_0
    //   245: iconst_0
    //   246: anewarray java/lang/Object
    //   249: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   252: checkcast wtf/opal/ky
    //   255: aload_0
    //   256: iconst_0
    //   257: anewarray java/lang/Object
    //   260: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   263: checkcast wtf/opal/ky
    //   266: iconst_0
    //   267: anewarray java/lang/Object
    //   270: invokevirtual r : ([Ljava/lang/Object;)Z
    //   273: aload #25
    //   275: ifnull -> 296
    //   278: goto -> 285
    //   281: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   284: athrow
    //   285: ifne -> 299
    //   288: goto -> 295
    //   291: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   294: athrow
    //   295: iconst_1
    //   296: goto -> 300
    //   299: iconst_0
    //   300: iconst_1
    //   301: anewarray java/lang/Object
    //   304: dup_x1
    //   305: swap
    //   306: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   309: iconst_0
    //   310: swap
    //   311: aastore
    //   312: invokevirtual G : ([Ljava/lang/Object;)V
    //   315: iload #8
    //   317: aload #25
    //   319: ifnull -> 357
    //   322: ifne -> 826
    //   325: goto -> 332
    //   328: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   331: athrow
    //   332: aload_0
    //   333: iconst_0
    //   334: anewarray java/lang/Object
    //   337: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   340: checkcast wtf/opal/ky
    //   343: iconst_0
    //   344: anewarray java/lang/Object
    //   347: invokevirtual r : ([Ljava/lang/Object;)Z
    //   350: goto -> 357
    //   353: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   356: athrow
    //   357: ifeq -> 826
    //   360: getstatic wtf/opal/b_.Z : Lwtf/opal/bu;
    //   363: aload_0
    //   364: iconst_0
    //   365: anewarray java/lang/Object
    //   368: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   371: checkcast wtf/opal/ky
    //   374: iconst_0
    //   375: anewarray java/lang/Object
    //   378: invokevirtual r : ([Ljava/lang/Object;)Ljava/lang/String;
    //   381: ldc 6.5
    //   383: lload #15
    //   385: sipush #15160
    //   388: ldc2_w 7062227545973829540
    //   391: lload #6
    //   393: lxor
    //   394: <illegal opcode> c : (IJ)I
    //   399: iconst_4
    //   400: anewarray java/lang/Object
    //   403: dup_x1
    //   404: swap
    //   405: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   408: iconst_3
    //   409: swap
    //   410: aastore
    //   411: dup_x2
    //   412: dup_x2
    //   413: pop
    //   414: invokestatic valueOf : (J)Ljava/lang/Long;
    //   417: iconst_2
    //   418: swap
    //   419: aastore
    //   420: dup_x1
    //   421: swap
    //   422: invokestatic valueOf : (F)Ljava/lang/Float;
    //   425: iconst_1
    //   426: swap
    //   427: aastore
    //   428: dup_x1
    //   429: swap
    //   430: iconst_0
    //   431: swap
    //   432: aastore
    //   433: invokevirtual E : ([Ljava/lang/Object;)F
    //   436: fstore #28
    //   438: fconst_0
    //   439: fstore #29
    //   441: aload_0
    //   442: iconst_0
    //   443: anewarray java/lang/Object
    //   446: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   449: checkcast wtf/opal/ky
    //   452: iconst_0
    //   453: anewarray java/lang/Object
    //   456: invokevirtual A : ([Ljava/lang/Object;)[Ljava/lang/Enum;
    //   459: astore #30
    //   461: aload #30
    //   463: arraylength
    //   464: istore #31
    //   466: iconst_0
    //   467: istore #32
    //   469: iload #32
    //   471: iload #31
    //   473: if_icmpge -> 826
    //   476: aload #30
    //   478: iload #32
    //   480: aaload
    //   481: astore #33
    //   483: getstatic wtf/opal/b_.Z : Lwtf/opal/bu;
    //   486: aload #33
    //   488: invokevirtual toString : ()Ljava/lang/String;
    //   491: ldc 6.5
    //   493: lload #13
    //   495: iconst_3
    //   496: anewarray java/lang/Object
    //   499: dup_x2
    //   500: dup_x2
    //   501: pop
    //   502: invokestatic valueOf : (J)Ljava/lang/Long;
    //   505: iconst_2
    //   506: swap
    //   507: aastore
    //   508: dup_x1
    //   509: swap
    //   510: invokestatic valueOf : (F)Ljava/lang/Float;
    //   513: iconst_1
    //   514: swap
    //   515: aastore
    //   516: dup_x1
    //   517: swap
    //   518: iconst_0
    //   519: swap
    //   520: aastore
    //   521: invokevirtual s : ([Ljava/lang/Object;)F
    //   524: fstore #34
    //   526: getstatic wtf/opal/b_.Z : Lwtf/opal/bu;
    //   529: aload #33
    //   531: invokevirtual toString : ()Ljava/lang/String;
    //   534: ldc 6.5
    //   536: lload #15
    //   538: sipush #15160
    //   541: ldc2_w 7062227545973829540
    //   544: lload #6
    //   546: lxor
    //   547: <illegal opcode> c : (IJ)I
    //   552: iconst_4
    //   553: anewarray java/lang/Object
    //   556: dup_x1
    //   557: swap
    //   558: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   561: iconst_3
    //   562: swap
    //   563: aastore
    //   564: dup_x2
    //   565: dup_x2
    //   566: pop
    //   567: invokestatic valueOf : (J)Ljava/lang/Long;
    //   570: iconst_2
    //   571: swap
    //   572: aastore
    //   573: dup_x1
    //   574: swap
    //   575: invokestatic valueOf : (F)Ljava/lang/Float;
    //   578: iconst_1
    //   579: swap
    //   580: aastore
    //   581: dup_x1
    //   582: swap
    //   583: iconst_0
    //   584: swap
    //   585: aastore
    //   586: invokevirtual E : ([Ljava/lang/Object;)F
    //   589: fstore #35
    //   591: aload_0
    //   592: getfield U : F
    //   595: ldc 4.5
    //   597: fadd
    //   598: fload #27
    //   600: fconst_2
    //   601: fdiv
    //   602: fadd
    //   603: fload #34
    //   605: fconst_2
    //   606: fdiv
    //   607: fsub
    //   608: aload_0
    //   609: getfield t : F
    //   612: ldc 2.5
    //   614: fadd
    //   615: fload #26
    //   617: fconst_2
    //   618: fdiv
    //   619: fadd
    //   620: fload #35
    //   622: fconst_2
    //   623: fdiv
    //   624: fsub
    //   625: fload #28
    //   627: fadd
    //   628: fload #29
    //   630: fadd
    //   631: ldc 5.0
    //   633: fadd
    //   634: lload #6
    //   636: lconst_0
    //   637: lcmp
    //   638: ifle -> 815
    //   641: fload #27
    //   643: aload #25
    //   645: ifnull -> 814
    //   648: fload #26
    //   650: dload_2
    //   651: dload #4
    //   653: dstore #17
    //   655: dstore #19
    //   657: fstore #21
    //   659: fstore #22
    //   661: fstore #23
    //   663: fstore #24
    //   665: lload #6
    //   667: lconst_0
    //   668: lcmp
    //   669: iflt -> 801
    //   672: lload #9
    //   674: fload #24
    //   676: fload #23
    //   678: fload #22
    //   680: fload #21
    //   682: dload #19
    //   684: dload #17
    //   686: bipush #7
    //   688: anewarray java/lang/Object
    //   691: dup_x2
    //   692: dup_x2
    //   693: pop
    //   694: invokestatic valueOf : (D)Ljava/lang/Double;
    //   697: bipush #6
    //   699: swap
    //   700: aastore
    //   701: dup_x2
    //   702: dup_x2
    //   703: pop
    //   704: invokestatic valueOf : (D)Ljava/lang/Double;
    //   707: iconst_5
    //   708: swap
    //   709: aastore
    //   710: dup_x1
    //   711: swap
    //   712: invokestatic valueOf : (F)Ljava/lang/Float;
    //   715: iconst_4
    //   716: swap
    //   717: aastore
    //   718: dup_x1
    //   719: swap
    //   720: invokestatic valueOf : (F)Ljava/lang/Float;
    //   723: iconst_3
    //   724: swap
    //   725: aastore
    //   726: dup_x1
    //   727: swap
    //   728: invokestatic valueOf : (F)Ljava/lang/Float;
    //   731: iconst_2
    //   732: swap
    //   733: aastore
    //   734: dup_x1
    //   735: swap
    //   736: invokestatic valueOf : (F)Ljava/lang/Float;
    //   739: iconst_1
    //   740: swap
    //   741: aastore
    //   742: dup_x2
    //   743: dup_x2
    //   744: pop
    //   745: invokestatic valueOf : (J)Ljava/lang/Long;
    //   748: iconst_0
    //   749: swap
    //   750: aastore
    //   751: invokestatic Z : ([Ljava/lang/Object;)Z
    //   754: ifeq -> 808
    //   757: aload_0
    //   758: iconst_0
    //   759: anewarray java/lang/Object
    //   762: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   765: checkcast wtf/opal/ky
    //   768: aload #33
    //   770: invokevirtual ordinal : ()I
    //   773: lload #11
    //   775: dup2_x1
    //   776: pop2
    //   777: iconst_2
    //   778: anewarray java/lang/Object
    //   781: dup_x1
    //   782: swap
    //   783: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   786: iconst_1
    //   787: swap
    //   788: aastore
    //   789: dup_x2
    //   790: dup_x2
    //   791: pop
    //   792: invokestatic valueOf : (J)Ljava/lang/Long;
    //   795: iconst_0
    //   796: swap
    //   797: aastore
    //   798: invokevirtual U : ([Ljava/lang/Object;)V
    //   801: goto -> 808
    //   804: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   807: athrow
    //   808: fload #29
    //   810: fload #35
    //   812: ldc 4.0
    //   814: fadd
    //   815: fadd
    //   816: fstore #29
    //   818: iinc #32, 1
    //   821: aload #25
    //   823: ifnonnull -> 469
    //   826: return
    // Exception table:
    //   from	to	target	type
    //   108	205	208	wtf/opal/x5
    //   202	226	229	wtf/opal/x5
    //   212	237	240	wtf/opal/x5
    //   233	278	281	wtf/opal/x5
    //   244	288	291	wtf/opal/x5
    //   317	325	328	wtf/opal/x5
    //   322	350	353	wtf/opal/x5
    //   665	801	804	wtf/opal/x5
  }
  
  public void c(Object[] paramArrayOfObject) {
    long l = ((Long)paramArrayOfObject[0]).longValue();
    double d1 = ((Double)paramArrayOfObject[1]).doubleValue();
    double d2 = ((Double)paramArrayOfObject[2]).doubleValue();
    int i = ((Integer)paramArrayOfObject[3]).intValue();
  }
  
  public void u() {}
  
  static {
    // Byte code:
    //   0: ldc2_w 793501999652201484
    //   3: ldc2_w -4186143604474813498
    //   6: invokestatic lookup : ()Ljava/lang/invoke/MethodHandles$Lookup;
    //   9: invokevirtual lookupClass : ()Ljava/lang/Class;
    //   12: invokestatic a : (JJLjava/lang/Object;)Lwtf/opal/t5;
    //   15: ldc2_w 190113445076311
    //   18: invokeinterface a : (J)J
    //   23: putstatic wtf/opal/b_.b : J
    //   26: new java/util/HashMap
    //   29: dup
    //   30: bipush #13
    //   32: invokespecial <init> : (I)V
    //   35: putstatic wtf/opal/b_.f : Ljava/util/Map;
    //   38: getstatic wtf/opal/b_.b : J
    //   41: ldc2_w 140615259919128
    //   44: lxor
    //   45: lstore_0
    //   46: ldc_w 'DES/CBC/NoPadding'
    //   49: invokestatic getInstance : (Ljava/lang/String;)Ljavax/crypto/Cipher;
    //   52: dup
    //   53: astore_2
    //   54: iconst_2
    //   55: ldc_w 'DES'
    //   58: invokestatic getInstance : (Ljava/lang/String;)Ljavax/crypto/SecretKeyFactory;
    //   61: bipush #8
    //   63: newarray byte
    //   65: dup
    //   66: iconst_0
    //   67: lload_0
    //   68: bipush #56
    //   70: lushr
    //   71: l2i
    //   72: i2b
    //   73: bastore
    //   74: iconst_1
    //   75: istore_3
    //   76: iload_3
    //   77: bipush #8
    //   79: if_icmpge -> 102
    //   82: dup
    //   83: iload_3
    //   84: lload_0
    //   85: iload_3
    //   86: bipush #8
    //   88: imul
    //   89: lshl
    //   90: bipush #56
    //   92: lushr
    //   93: l2i
    //   94: i2b
    //   95: bastore
    //   96: iinc #3, 1
    //   99: goto -> 76
    //   102: new javax/crypto/spec/DESKeySpec
    //   105: dup_x1
    //   106: swap
    //   107: invokespecial <init> : ([B)V
    //   110: invokevirtual generateSecret : (Ljava/security/spec/KeySpec;)Ljavax/crypto/SecretKey;
    //   113: new javax/crypto/spec/IvParameterSpec
    //   116: dup
    //   117: bipush #8
    //   119: newarray byte
    //   121: invokespecial <init> : ([B)V
    //   124: invokevirtual init : (ILjava/security/Key;Ljava/security/spec/AlgorithmParameterSpec;)V
    //   127: bipush #6
    //   129: newarray long
    //   131: astore #8
    //   133: iconst_0
    //   134: istore #5
    //   136: ldc_w 'ÎGè{³'<íøÁ¥,µÞcûµÒ)Û2G£'
    //   139: dup
    //   140: astore #6
    //   142: invokevirtual length : ()I
    //   145: istore #7
    //   147: iconst_0
    //   148: istore #4
    //   150: aload #6
    //   152: iload #4
    //   154: iinc #4, 8
    //   157: iload #4
    //   159: invokevirtual substring : (II)Ljava/lang/String;
    //   162: ldc_w 'ISO-8859-1'
    //   165: invokevirtual getBytes : (Ljava/lang/String;)[B
    //   168: astore #9
    //   170: aload #8
    //   172: iload #5
    //   174: iinc #5, 1
    //   177: aload #9
    //   179: iconst_0
    //   180: baload
    //   181: i2l
    //   182: ldc2_w 255
    //   185: land
    //   186: bipush #56
    //   188: lshl
    //   189: aload #9
    //   191: iconst_1
    //   192: baload
    //   193: i2l
    //   194: ldc2_w 255
    //   197: land
    //   198: bipush #48
    //   200: lshl
    //   201: lor
    //   202: aload #9
    //   204: iconst_2
    //   205: baload
    //   206: i2l
    //   207: ldc2_w 255
    //   210: land
    //   211: bipush #40
    //   213: lshl
    //   214: lor
    //   215: aload #9
    //   217: iconst_3
    //   218: baload
    //   219: i2l
    //   220: ldc2_w 255
    //   223: land
    //   224: bipush #32
    //   226: lshl
    //   227: lor
    //   228: aload #9
    //   230: iconst_4
    //   231: baload
    //   232: i2l
    //   233: ldc2_w 255
    //   236: land
    //   237: bipush #24
    //   239: lshl
    //   240: lor
    //   241: aload #9
    //   243: iconst_5
    //   244: baload
    //   245: i2l
    //   246: ldc2_w 255
    //   249: land
    //   250: bipush #16
    //   252: lshl
    //   253: lor
    //   254: aload #9
    //   256: bipush #6
    //   258: baload
    //   259: i2l
    //   260: ldc2_w 255
    //   263: land
    //   264: bipush #8
    //   266: lshl
    //   267: lor
    //   268: aload #9
    //   270: bipush #7
    //   272: baload
    //   273: i2l
    //   274: ldc2_w 255
    //   277: land
    //   278: lor
    //   279: iconst_m1
    //   280: goto -> 462
    //   283: lastore
    //   284: iload #4
    //   286: iload #7
    //   288: if_icmplt -> 150
    //   291: ldc_w 'zí »¿Úõ;8ì|ÞýY'
    //   294: dup
    //   295: astore #6
    //   297: invokevirtual length : ()I
    //   300: istore #7
    //   302: iconst_0
    //   303: istore #4
    //   305: aload #6
    //   307: iload #4
    //   309: iinc #4, 8
    //   312: iload #4
    //   314: invokevirtual substring : (II)Ljava/lang/String;
    //   317: ldc_w 'ISO-8859-1'
    //   320: invokevirtual getBytes : (Ljava/lang/String;)[B
    //   323: astore #9
    //   325: aload #8
    //   327: iload #5
    //   329: iinc #5, 1
    //   332: aload #9
    //   334: iconst_0
    //   335: baload
    //   336: i2l
    //   337: ldc2_w 255
    //   340: land
    //   341: bipush #56
    //   343: lshl
    //   344: aload #9
    //   346: iconst_1
    //   347: baload
    //   348: i2l
    //   349: ldc2_w 255
    //   352: land
    //   353: bipush #48
    //   355: lshl
    //   356: lor
    //   357: aload #9
    //   359: iconst_2
    //   360: baload
    //   361: i2l
    //   362: ldc2_w 255
    //   365: land
    //   366: bipush #40
    //   368: lshl
    //   369: lor
    //   370: aload #9
    //   372: iconst_3
    //   373: baload
    //   374: i2l
    //   375: ldc2_w 255
    //   378: land
    //   379: bipush #32
    //   381: lshl
    //   382: lor
    //   383: aload #9
    //   385: iconst_4
    //   386: baload
    //   387: i2l
    //   388: ldc2_w 255
    //   391: land
    //   392: bipush #24
    //   394: lshl
    //   395: lor
    //   396: aload #9
    //   398: iconst_5
    //   399: baload
    //   400: i2l
    //   401: ldc2_w 255
    //   404: land
    //   405: bipush #16
    //   407: lshl
    //   408: lor
    //   409: aload #9
    //   411: bipush #6
    //   413: baload
    //   414: i2l
    //   415: ldc2_w 255
    //   418: land
    //   419: bipush #8
    //   421: lshl
    //   422: lor
    //   423: aload #9
    //   425: bipush #7
    //   427: baload
    //   428: i2l
    //   429: ldc2_w 255
    //   432: land
    //   433: lor
    //   434: iconst_0
    //   435: goto -> 462
    //   438: lastore
    //   439: iload #4
    //   441: iload #7
    //   443: if_icmplt -> 305
    //   446: aload #8
    //   448: putstatic wtf/opal/b_.c : [J
    //   451: bipush #6
    //   453: anewarray java/lang/Integer
    //   456: putstatic wtf/opal/b_.d : [Ljava/lang/Integer;
    //   459: goto -> 680
    //   462: dup_x2
    //   463: pop
    //   464: lstore #10
    //   466: bipush #8
    //   468: newarray byte
    //   470: dup
    //   471: iconst_0
    //   472: lload #10
    //   474: bipush #56
    //   476: lushr
    //   477: l2i
    //   478: i2b
    //   479: bastore
    //   480: dup
    //   481: iconst_1
    //   482: lload #10
    //   484: bipush #48
    //   486: lushr
    //   487: l2i
    //   488: i2b
    //   489: bastore
    //   490: dup
    //   491: iconst_2
    //   492: lload #10
    //   494: bipush #40
    //   496: lushr
    //   497: l2i
    //   498: i2b
    //   499: bastore
    //   500: dup
    //   501: iconst_3
    //   502: lload #10
    //   504: bipush #32
    //   506: lushr
    //   507: l2i
    //   508: i2b
    //   509: bastore
    //   510: dup
    //   511: iconst_4
    //   512: lload #10
    //   514: bipush #24
    //   516: lushr
    //   517: l2i
    //   518: i2b
    //   519: bastore
    //   520: dup
    //   521: iconst_5
    //   522: lload #10
    //   524: bipush #16
    //   526: lushr
    //   527: l2i
    //   528: i2b
    //   529: bastore
    //   530: dup
    //   531: bipush #6
    //   533: lload #10
    //   535: bipush #8
    //   537: lushr
    //   538: l2i
    //   539: i2b
    //   540: bastore
    //   541: dup
    //   542: bipush #7
    //   544: lload #10
    //   546: l2i
    //   547: i2b
    //   548: bastore
    //   549: aload_2
    //   550: swap
    //   551: invokevirtual doFinal : ([B)[B
    //   554: astore #12
    //   556: aload #12
    //   558: iconst_0
    //   559: baload
    //   560: i2l
    //   561: ldc2_w 255
    //   564: land
    //   565: bipush #56
    //   567: lshl
    //   568: aload #12
    //   570: iconst_1
    //   571: baload
    //   572: i2l
    //   573: ldc2_w 255
    //   576: land
    //   577: bipush #48
    //   579: lshl
    //   580: lor
    //   581: aload #12
    //   583: iconst_2
    //   584: baload
    //   585: i2l
    //   586: ldc2_w 255
    //   589: land
    //   590: bipush #40
    //   592: lshl
    //   593: lor
    //   594: aload #12
    //   596: iconst_3
    //   597: baload
    //   598: i2l
    //   599: ldc2_w 255
    //   602: land
    //   603: bipush #32
    //   605: lshl
    //   606: lor
    //   607: aload #12
    //   609: iconst_4
    //   610: baload
    //   611: i2l
    //   612: ldc2_w 255
    //   615: land
    //   616: bipush #24
    //   618: lshl
    //   619: lor
    //   620: aload #12
    //   622: iconst_5
    //   623: baload
    //   624: i2l
    //   625: ldc2_w 255
    //   628: land
    //   629: bipush #16
    //   631: lshl
    //   632: lor
    //   633: aload #12
    //   635: bipush #6
    //   637: baload
    //   638: i2l
    //   639: ldc2_w 255
    //   642: land
    //   643: bipush #8
    //   645: lshl
    //   646: lor
    //   647: aload #12
    //   649: bipush #7
    //   651: baload
    //   652: i2l
    //   653: ldc2_w 255
    //   656: land
    //   657: lor
    //   658: dup2_x1
    //   659: pop2
    //   660: tableswitch default -> 283, 0 -> 438
    //   680: return
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
  
  private static int a(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x3E83;
    if (d[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l = c[i];
      byte[] arrayOfByte2 = { (byte)(int)(l >>> 56L), (byte)(int)(l >>> 48L), (byte)(int)(l >>> 40L), (byte)(int)(l >>> 32L), (byte)(int)(l >>> 24L), (byte)(int)(l >>> 16L), (byte)(int)(l >>> 8L), (byte)(int)l };
      Long long_ = Long.valueOf(Thread.currentThread().threadId());
      Object[] arrayOfObject = (Object[])f.get(long_);
      try {
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/NoPadding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          f.put(long_, arrayOfObject);
        } 
        DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
        SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
        Cipher cipher = (Cipher)arrayOfObject[0];
        cipher.init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
        arrayOfByte3 = cipher.doFinal(arrayOfByte2);
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/b_", exception);
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
    //   65: ldc_w 'wtf/opal/b_'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\b_.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */