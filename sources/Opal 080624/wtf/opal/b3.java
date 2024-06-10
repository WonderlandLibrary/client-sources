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

public final class b3 extends b5<kd> {
  private final dk V;
  
  private static final long b;
  
  private static final long[] c;
  
  private static final Integer[] d;
  
  private static final Map f;
  
  public b3(kd paramkd, long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/b3.b : J
    //   3: lload_2
    //   4: lxor
    //   5: lstore_2
    //   6: lload_2
    //   7: dup2
    //   8: ldc2_w 32874775415318
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
    //   41: ldc2_w 134244636107396
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
    //   60: sipush #15200
    //   63: ldc2_w 3470848236000622946
    //   66: lload_2
    //   67: lxor
    //   68: <illegal opcode> d : (IJ)I
    //   73: iload #4
    //   75: dconst_1
    //   76: iload #5
    //   78: i2c
    //   79: iload #6
    //   81: getstatic wtf/opal/lp.FORWARDS : Lwtf/opal/lp;
    //   84: invokespecial <init> : (IIDCILwtf/opal/lp;)V
    //   87: putfield V : Lwtf/opal/dk;
    //   90: return
  }
  
  public void g(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast net/minecraft/class_332
    //   7: astore_3
    //   8: dup
    //   9: iconst_1
    //   10: aaload
    //   11: checkcast java/lang/Integer
    //   14: invokevirtual intValue : ()I
    //   17: istore #7
    //   19: dup
    //   20: iconst_2
    //   21: aaload
    //   22: checkcast java/lang/Integer
    //   25: invokevirtual intValue : ()I
    //   28: istore_2
    //   29: dup
    //   30: iconst_3
    //   31: aaload
    //   32: checkcast java/lang/Long
    //   35: invokevirtual longValue : ()J
    //   38: lstore #4
    //   40: dup
    //   41: iconst_4
    //   42: aaload
    //   43: checkcast java/lang/Float
    //   46: invokevirtual floatValue : ()F
    //   49: fstore #6
    //   51: pop
    //   52: lload #4
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
    //   145: getstatic wtf/opal/b3.h : Lwtf/opal/pa;
    //   148: aload_0
    //   149: getfield U : F
    //   152: aload_0
    //   153: getfield t : F
    //   156: aload_0
    //   157: getfield e : F
    //   160: aload_0
    //   161: getfield m : F
    //   164: sipush #19966
    //   167: ldc2_w 4130464584643167907
    //   170: lload #4
    //   172: lxor
    //   173: <illegal opcode> d : (IJ)I
    //   178: lload #14
    //   180: bipush #6
    //   182: anewarray java/lang/Object
    //   185: dup_x2
    //   186: dup_x2
    //   187: pop
    //   188: invokestatic valueOf : (J)Ljava/lang/Long;
    //   191: iconst_5
    //   192: swap
    //   193: aastore
    //   194: dup_x1
    //   195: swap
    //   196: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   199: iconst_4
    //   200: swap
    //   201: aastore
    //   202: dup_x1
    //   203: swap
    //   204: invokestatic valueOf : (F)Ljava/lang/Float;
    //   207: iconst_3
    //   208: swap
    //   209: aastore
    //   210: dup_x1
    //   211: swap
    //   212: invokestatic valueOf : (F)Ljava/lang/Float;
    //   215: iconst_2
    //   216: swap
    //   217: aastore
    //   218: dup_x1
    //   219: swap
    //   220: invokestatic valueOf : (F)Ljava/lang/Float;
    //   223: iconst_1
    //   224: swap
    //   225: aastore
    //   226: dup_x1
    //   227: swap
    //   228: invokestatic valueOf : (F)Ljava/lang/Float;
    //   231: iconst_0
    //   232: swap
    //   233: aastore
    //   234: invokevirtual k : ([Ljava/lang/Object;)V
    //   237: getstatic wtf/opal/b3.h : Lwtf/opal/pa;
    //   240: aload_0
    //   241: getfield U : F
    //   244: ldc 4.5
    //   246: fadd
    //   247: aload_0
    //   248: getfield t : F
    //   251: ldc 2.5
    //   253: fadd
    //   254: fload #31
    //   256: aload_0
    //   257: getfield m : F
    //   260: ldc 5.0
    //   262: fsub
    //   263: lload #20
    //   265: ldc 5.0
    //   267: fconst_1
    //   268: sipush #848
    //   271: ldc2_w 989911448960272399
    //   274: lload #4
    //   276: lxor
    //   277: <illegal opcode> d : (IJ)I
    //   282: bipush #8
    //   284: anewarray java/lang/Object
    //   287: dup_x1
    //   288: swap
    //   289: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   292: bipush #7
    //   294: swap
    //   295: aastore
    //   296: dup_x1
    //   297: swap
    //   298: invokestatic valueOf : (F)Ljava/lang/Float;
    //   301: bipush #6
    //   303: swap
    //   304: aastore
    //   305: dup_x1
    //   306: swap
    //   307: invokestatic valueOf : (F)Ljava/lang/Float;
    //   310: iconst_5
    //   311: swap
    //   312: aastore
    //   313: dup_x2
    //   314: dup_x2
    //   315: pop
    //   316: invokestatic valueOf : (J)Ljava/lang/Long;
    //   319: iconst_4
    //   320: swap
    //   321: aastore
    //   322: dup_x1
    //   323: swap
    //   324: invokestatic valueOf : (F)Ljava/lang/Float;
    //   327: iconst_3
    //   328: swap
    //   329: aastore
    //   330: dup_x1
    //   331: swap
    //   332: invokestatic valueOf : (F)Ljava/lang/Float;
    //   335: iconst_2
    //   336: swap
    //   337: aastore
    //   338: dup_x1
    //   339: swap
    //   340: invokestatic valueOf : (F)Ljava/lang/Float;
    //   343: iconst_1
    //   344: swap
    //   345: aastore
    //   346: dup_x1
    //   347: swap
    //   348: invokestatic valueOf : (F)Ljava/lang/Float;
    //   351: iconst_0
    //   352: swap
    //   353: aastore
    //   354: invokevirtual G : ([Ljava/lang/Object;)V
    //   357: invokestatic S : ()[Lwtf/opal/d;
    //   360: getstatic wtf/opal/b3.Z : Lwtf/opal/bu;
    //   363: aload_0
    //   364: iconst_0
    //   365: anewarray java/lang/Object
    //   368: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   371: checkcast wtf/opal/kd
    //   374: iconst_0
    //   375: anewarray java/lang/Object
    //   378: invokevirtual r : ([Ljava/lang/Object;)Ljava/lang/String;
    //   381: ldc 6.5
    //   383: lload #8
    //   385: iconst_3
    //   386: anewarray java/lang/Object
    //   389: dup_x2
    //   390: dup_x2
    //   391: pop
    //   392: invokestatic valueOf : (J)Ljava/lang/Long;
    //   395: iconst_2
    //   396: swap
    //   397: aastore
    //   398: dup_x1
    //   399: swap
    //   400: invokestatic valueOf : (F)Ljava/lang/Float;
    //   403: iconst_1
    //   404: swap
    //   405: aastore
    //   406: dup_x1
    //   407: swap
    //   408: iconst_0
    //   409: swap
    //   410: aastore
    //   411: invokevirtual s : ([Ljava/lang/Object;)F
    //   414: fstore #33
    //   416: astore #30
    //   418: getstatic wtf/opal/b3.Z : Lwtf/opal/bu;
    //   421: aload_0
    //   422: iconst_0
    //   423: anewarray java/lang/Object
    //   426: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   429: checkcast wtf/opal/kd
    //   432: iconst_0
    //   433: anewarray java/lang/Object
    //   436: invokevirtual r : ([Ljava/lang/Object;)Ljava/lang/String;
    //   439: ldc 6.5
    //   441: lload #16
    //   443: sipush #16574
    //   446: ldc2_w 632902649347197922
    //   449: lload #4
    //   451: lxor
    //   452: <illegal opcode> d : (IJ)I
    //   457: iconst_4
    //   458: anewarray java/lang/Object
    //   461: dup_x1
    //   462: swap
    //   463: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   466: iconst_3
    //   467: swap
    //   468: aastore
    //   469: dup_x2
    //   470: dup_x2
    //   471: pop
    //   472: invokestatic valueOf : (J)Ljava/lang/Long;
    //   475: iconst_2
    //   476: swap
    //   477: aastore
    //   478: dup_x1
    //   479: swap
    //   480: invokestatic valueOf : (F)Ljava/lang/Float;
    //   483: iconst_1
    //   484: swap
    //   485: aastore
    //   486: dup_x1
    //   487: swap
    //   488: iconst_0
    //   489: swap
    //   490: aastore
    //   491: invokevirtual E : ([Ljava/lang/Object;)F
    //   494: fstore #34
    //   496: getstatic wtf/opal/b3.Z : Lwtf/opal/bu;
    //   499: aload_3
    //   500: aload_0
    //   501: iconst_0
    //   502: anewarray java/lang/Object
    //   505: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   508: checkcast wtf/opal/kd
    //   511: iconst_0
    //   512: anewarray java/lang/Object
    //   515: invokevirtual r : ([Ljava/lang/Object;)Ljava/lang/String;
    //   518: aload_0
    //   519: getfield U : F
    //   522: ldc 4.5
    //   524: fadd
    //   525: fload #31
    //   527: fconst_2
    //   528: fdiv
    //   529: fadd
    //   530: fload #33
    //   532: fconst_2
    //   533: fdiv
    //   534: fsub
    //   535: aload_0
    //   536: getfield t : F
    //   539: ldc 2.5
    //   541: fadd
    //   542: fload #32
    //   544: fconst_2
    //   545: fdiv
    //   546: fadd
    //   547: fload #34
    //   549: fconst_2
    //   550: fdiv
    //   551: fsub
    //   552: ldc 6.5
    //   554: iconst_m1
    //   555: iconst_1
    //   556: lload #22
    //   558: bipush #8
    //   560: anewarray java/lang/Object
    //   563: dup_x2
    //   564: dup_x2
    //   565: pop
    //   566: invokestatic valueOf : (J)Ljava/lang/Long;
    //   569: bipush #7
    //   571: swap
    //   572: aastore
    //   573: dup_x1
    //   574: swap
    //   575: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   578: bipush #6
    //   580: swap
    //   581: aastore
    //   582: dup_x1
    //   583: swap
    //   584: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   587: iconst_5
    //   588: swap
    //   589: aastore
    //   590: dup_x1
    //   591: swap
    //   592: invokestatic valueOf : (F)Ljava/lang/Float;
    //   595: iconst_4
    //   596: swap
    //   597: aastore
    //   598: dup_x1
    //   599: swap
    //   600: invokestatic valueOf : (F)Ljava/lang/Float;
    //   603: iconst_3
    //   604: swap
    //   605: aastore
    //   606: dup_x1
    //   607: swap
    //   608: invokestatic valueOf : (F)Ljava/lang/Float;
    //   611: iconst_2
    //   612: swap
    //   613: aastore
    //   614: dup_x1
    //   615: swap
    //   616: iconst_1
    //   617: swap
    //   618: aastore
    //   619: dup_x1
    //   620: swap
    //   621: iconst_0
    //   622: swap
    //   623: aastore
    //   624: invokevirtual B : ([Ljava/lang/Object;)V
    //   627: aload_0
    //   628: getfield V : Lwtf/opal/dk;
    //   631: aload_0
    //   632: iconst_0
    //   633: anewarray java/lang/Object
    //   636: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   639: checkcast wtf/opal/kd
    //   642: iconst_0
    //   643: anewarray java/lang/Object
    //   646: invokevirtual q : ([Ljava/lang/Object;)Z
    //   649: ifeq -> 662
    //   652: getstatic wtf/opal/lp.FORWARDS : Lwtf/opal/lp;
    //   655: goto -> 665
    //   658: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   661: athrow
    //   662: getstatic wtf/opal/lp.BACKWARDS : Lwtf/opal/lp;
    //   665: lload #12
    //   667: dup2_x1
    //   668: pop2
    //   669: iconst_2
    //   670: anewarray java/lang/Object
    //   673: dup_x1
    //   674: swap
    //   675: iconst_1
    //   676: swap
    //   677: aastore
    //   678: dup_x2
    //   679: dup_x2
    //   680: pop
    //   681: invokestatic valueOf : (J)Ljava/lang/Long;
    //   684: iconst_0
    //   685: swap
    //   686: aastore
    //   687: invokevirtual p : ([Ljava/lang/Object;)Lwtf/opal/dx;
    //   690: pop
    //   691: aload_0
    //   692: iconst_0
    //   693: anewarray java/lang/Object
    //   696: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   699: checkcast wtf/opal/kd
    //   702: iconst_0
    //   703: anewarray java/lang/Object
    //   706: invokevirtual q : ([Ljava/lang/Object;)Z
    //   709: aload #30
    //   711: ifnull -> 772
    //   714: ifne -> 775
    //   717: goto -> 724
    //   720: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   723: athrow
    //   724: aload_0
    //   725: aload #30
    //   727: lload #4
    //   729: lconst_0
    //   730: lcmp
    //   731: ifle -> 1378
    //   734: ifnull -> 1365
    //   737: goto -> 744
    //   740: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   743: athrow
    //   744: getfield V : Lwtf/opal/dk;
    //   747: lload #24
    //   749: iconst_1
    //   750: anewarray java/lang/Object
    //   753: dup_x2
    //   754: dup_x2
    //   755: pop
    //   756: invokestatic valueOf : (J)Ljava/lang/Long;
    //   759: iconst_0
    //   760: swap
    //   761: aastore
    //   762: invokevirtual H : ([Ljava/lang/Object;)Z
    //   765: goto -> 772
    //   768: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   771: athrow
    //   772: ifne -> 1357
    //   775: fconst_0
    //   776: fstore #35
    //   778: aload_0
    //   779: iconst_0
    //   780: anewarray java/lang/Object
    //   783: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   786: checkcast wtf/opal/kd
    //   789: invokevirtual z : ()Ljava/lang/Object;
    //   792: checkcast java/util/HashMap
    //   795: invokevirtual values : ()Ljava/util/Collection;
    //   798: invokeinterface iterator : ()Ljava/util/Iterator;
    //   803: astore #36
    //   805: aload #36
    //   807: invokeinterface hasNext : ()Z
    //   812: ifeq -> 1297
    //   815: aload #36
    //   817: invokeinterface next : ()Ljava/lang/Object;
    //   822: checkcast wtf/opal/ke
    //   825: astore #37
    //   827: getstatic wtf/opal/b3.Z : Lwtf/opal/bu;
    //   830: aload #37
    //   832: iconst_0
    //   833: anewarray java/lang/Object
    //   836: invokevirtual r : ([Ljava/lang/Object;)Ljava/lang/String;
    //   839: ldc 6.5
    //   841: lload #8
    //   843: iconst_3
    //   844: anewarray java/lang/Object
    //   847: dup_x2
    //   848: dup_x2
    //   849: pop
    //   850: invokestatic valueOf : (J)Ljava/lang/Long;
    //   853: iconst_2
    //   854: swap
    //   855: aastore
    //   856: dup_x1
    //   857: swap
    //   858: invokestatic valueOf : (F)Ljava/lang/Float;
    //   861: iconst_1
    //   862: swap
    //   863: aastore
    //   864: dup_x1
    //   865: swap
    //   866: iconst_0
    //   867: swap
    //   868: aastore
    //   869: invokevirtual s : ([Ljava/lang/Object;)F
    //   872: fstore #38
    //   874: getstatic wtf/opal/b3.Z : Lwtf/opal/bu;
    //   877: aload #37
    //   879: iconst_0
    //   880: anewarray java/lang/Object
    //   883: invokevirtual r : ([Ljava/lang/Object;)Ljava/lang/String;
    //   886: ldc 6.5
    //   888: lload #16
    //   890: sipush #16574
    //   893: ldc2_w 632902649347197922
    //   896: lload #4
    //   898: lxor
    //   899: <illegal opcode> d : (IJ)I
    //   904: iconst_4
    //   905: anewarray java/lang/Object
    //   908: dup_x1
    //   909: swap
    //   910: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   913: iconst_3
    //   914: swap
    //   915: aastore
    //   916: dup_x2
    //   917: dup_x2
    //   918: pop
    //   919: invokestatic valueOf : (J)Ljava/lang/Long;
    //   922: iconst_2
    //   923: swap
    //   924: aastore
    //   925: dup_x1
    //   926: swap
    //   927: invokestatic valueOf : (F)Ljava/lang/Float;
    //   930: iconst_1
    //   931: swap
    //   932: aastore
    //   933: dup_x1
    //   934: swap
    //   935: iconst_0
    //   936: swap
    //   937: aastore
    //   938: invokevirtual E : ([Ljava/lang/Object;)F
    //   941: fstore #39
    //   943: aload #30
    //   945: lload #4
    //   947: lconst_0
    //   948: lcmp
    //   949: ifle -> 1354
    //   952: ifnull -> 1352
    //   955: getstatic wtf/opal/b3.Z : Lwtf/opal/bu;
    //   958: aload_3
    //   959: aload #37
    //   961: iconst_0
    //   962: anewarray java/lang/Object
    //   965: invokevirtual r : ([Ljava/lang/Object;)Ljava/lang/String;
    //   968: aload_0
    //   969: getfield U : F
    //   972: ldc 4.5
    //   974: fadd
    //   975: fload #31
    //   977: fconst_2
    //   978: fdiv
    //   979: fadd
    //   980: fload #38
    //   982: fconst_2
    //   983: fdiv
    //   984: fsub
    //   985: aload_0
    //   986: getfield t : F
    //   989: ldc 2.5
    //   991: fload #32
    //   993: fconst_2
    //   994: fdiv
    //   995: fadd
    //   996: fload #39
    //   998: fconst_2
    //   999: fdiv
    //   1000: fsub
    //   1001: fload #34
    //   1003: fadd
    //   1004: aload_0
    //   1005: getfield V : Lwtf/opal/dk;
    //   1008: lload #18
    //   1010: iconst_1
    //   1011: anewarray java/lang/Object
    //   1014: dup_x2
    //   1015: dup_x2
    //   1016: pop
    //   1017: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1020: iconst_0
    //   1021: swap
    //   1022: aastore
    //   1023: invokevirtual P : ([Ljava/lang/Object;)Ljava/lang/Double;
    //   1026: invokevirtual floatValue : ()F
    //   1029: fmul
    //   1030: fadd
    //   1031: fload #35
    //   1033: fadd
    //   1034: ldc 5.0
    //   1036: fadd
    //   1037: ldc 6.5
    //   1039: aload #37
    //   1041: invokevirtual z : ()Ljava/lang/Object;
    //   1044: checkcast java/lang/Boolean
    //   1047: invokevirtual booleanValue : ()Z
    //   1050: aload #30
    //   1052: lload #4
    //   1054: lconst_0
    //   1055: lcmp
    //   1056: iflt -> 1193
    //   1059: ifnull -> 1156
    //   1062: goto -> 1069
    //   1065: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1068: athrow
    //   1069: lload #4
    //   1071: lconst_0
    //   1072: lcmp
    //   1073: ifle -> 1145
    //   1076: ifeq -> 1142
    //   1079: goto -> 1086
    //   1082: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1085: athrow
    //   1086: iconst_m1
    //   1087: aload_0
    //   1088: getfield V : Lwtf/opal/dk;
    //   1091: lload #18
    //   1093: iconst_1
    //   1094: anewarray java/lang/Object
    //   1097: dup_x2
    //   1098: dup_x2
    //   1099: pop
    //   1100: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1103: iconst_0
    //   1104: swap
    //   1105: aastore
    //   1106: invokevirtual P : ([Ljava/lang/Object;)Ljava/lang/Double;
    //   1109: invokevirtual floatValue : ()F
    //   1112: iconst_2
    //   1113: anewarray java/lang/Object
    //   1116: dup_x1
    //   1117: swap
    //   1118: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1121: iconst_1
    //   1122: swap
    //   1123: aastore
    //   1124: dup_x1
    //   1125: swap
    //   1126: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1129: iconst_0
    //   1130: swap
    //   1131: aastore
    //   1132: invokestatic X : ([Ljava/lang/Object;)I
    //   1135: goto -> 1204
    //   1138: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1141: athrow
    //   1142: sipush #3242
    //   1145: ldc2_w 8324750635302160372
    //   1148: lload #4
    //   1150: lxor
    //   1151: <illegal opcode> d : (IJ)I
    //   1156: aload_0
    //   1157: getfield V : Lwtf/opal/dk;
    //   1160: lload #18
    //   1162: iconst_1
    //   1163: anewarray java/lang/Object
    //   1166: dup_x2
    //   1167: dup_x2
    //   1168: pop
    //   1169: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1172: iconst_0
    //   1173: swap
    //   1174: aastore
    //   1175: invokevirtual P : ([Ljava/lang/Object;)Ljava/lang/Double;
    //   1178: invokevirtual floatValue : ()F
    //   1181: iconst_2
    //   1182: anewarray java/lang/Object
    //   1185: dup_x1
    //   1186: swap
    //   1187: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1190: iconst_1
    //   1191: swap
    //   1192: aastore
    //   1193: dup_x1
    //   1194: swap
    //   1195: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1198: iconst_0
    //   1199: swap
    //   1200: aastore
    //   1201: invokestatic X : ([Ljava/lang/Object;)I
    //   1204: istore #26
    //   1206: fstore #27
    //   1208: fstore #28
    //   1210: fstore #29
    //   1212: lload #10
    //   1214: fload #29
    //   1216: fload #28
    //   1218: fload #27
    //   1220: iload #26
    //   1222: bipush #7
    //   1224: anewarray java/lang/Object
    //   1227: dup_x1
    //   1228: swap
    //   1229: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1232: bipush #6
    //   1234: swap
    //   1235: aastore
    //   1236: dup_x1
    //   1237: swap
    //   1238: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1241: iconst_5
    //   1242: swap
    //   1243: aastore
    //   1244: dup_x1
    //   1245: swap
    //   1246: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1249: iconst_4
    //   1250: swap
    //   1251: aastore
    //   1252: dup_x1
    //   1253: swap
    //   1254: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1257: iconst_3
    //   1258: swap
    //   1259: aastore
    //   1260: dup_x2
    //   1261: dup_x2
    //   1262: pop
    //   1263: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1266: iconst_2
    //   1267: swap
    //   1268: aastore
    //   1269: dup_x1
    //   1270: swap
    //   1271: iconst_1
    //   1272: swap
    //   1273: aastore
    //   1274: dup_x1
    //   1275: swap
    //   1276: iconst_0
    //   1277: swap
    //   1278: aastore
    //   1279: invokevirtual R : ([Ljava/lang/Object;)V
    //   1282: fload #35
    //   1284: fload #39
    //   1286: ldc 4.0
    //   1288: fadd
    //   1289: fadd
    //   1290: fstore #35
    //   1292: aload #30
    //   1294: ifnonnull -> 805
    //   1297: aload_0
    //   1298: fload #35
    //   1300: fconst_2
    //   1301: fadd
    //   1302: f2d
    //   1303: aload_0
    //   1304: getfield V : Lwtf/opal/dk;
    //   1307: lload #18
    //   1309: iconst_1
    //   1310: anewarray java/lang/Object
    //   1313: dup_x2
    //   1314: dup_x2
    //   1315: pop
    //   1316: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1319: iconst_0
    //   1320: swap
    //   1321: aastore
    //   1322: invokevirtual P : ([Ljava/lang/Object;)Ljava/lang/Double;
    //   1325: invokevirtual doubleValue : ()D
    //   1328: dmul
    //   1329: d2f
    //   1330: iconst_1
    //   1331: anewarray java/lang/Object
    //   1334: dup_x1
    //   1335: swap
    //   1336: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1339: iconst_0
    //   1340: swap
    //   1341: aastore
    //   1342: invokevirtual q : ([Ljava/lang/Object;)V
    //   1345: lload #4
    //   1347: lconst_0
    //   1348: lcmp
    //   1349: ifle -> 1352
    //   1352: aload #30
    //   1354: ifnonnull -> 1381
    //   1357: aload_0
    //   1358: goto -> 1365
    //   1361: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1364: athrow
    //   1365: fconst_0
    //   1366: iconst_1
    //   1367: anewarray java/lang/Object
    //   1370: dup_x1
    //   1371: swap
    //   1372: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1375: iconst_0
    //   1376: swap
    //   1377: aastore
    //   1378: invokevirtual q : ([Ljava/lang/Object;)V
    //   1381: return
    // Exception table:
    //   from	to	target	type
    //   496	658	658	wtf/opal/x5
    //   665	717	720	wtf/opal/x5
    //   714	737	740	wtf/opal/x5
    //   724	765	768	wtf/opal/x5
    //   943	1062	1065	wtf/opal/x5
    //   955	1079	1082	wtf/opal/x5
    //   1069	1138	1138	wtf/opal/x5
    //   1352	1358	1361	wtf/opal/x5
  }
  
  public void e(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Double
    //   7: invokevirtual doubleValue : ()D
    //   10: dstore_3
    //   11: dup
    //   12: iconst_1
    //   13: aaload
    //   14: checkcast java/lang/Double
    //   17: invokevirtual doubleValue : ()D
    //   20: dstore #5
    //   22: dup
    //   23: iconst_2
    //   24: aaload
    //   25: checkcast java/lang/Long
    //   28: invokevirtual longValue : ()J
    //   31: lstore #7
    //   33: dup
    //   34: iconst_3
    //   35: aaload
    //   36: checkcast java/lang/Integer
    //   39: invokevirtual intValue : ()I
    //   42: istore_2
    //   43: pop
    //   44: lload #7
    //   46: dup2
    //   47: ldc2_w 91173079021596
    //   50: lxor
    //   51: lstore #9
    //   53: dup2
    //   54: ldc2_w 58562124928931
    //   57: lxor
    //   58: lstore #11
    //   60: dup2
    //   61: ldc2_w 8719649998748
    //   64: lxor
    //   65: lstore #13
    //   67: dup2
    //   68: ldc2_w 43107960303983
    //   71: lxor
    //   72: lstore #15
    //   74: dup2
    //   75: ldc2_w 79131862812982
    //   78: lxor
    //   79: lstore #17
    //   81: pop2
    //   82: invokestatic S : ()[Lwtf/opal/d;
    //   85: getstatic wtf/opal/b3.Z : Lwtf/opal/bu;
    //   88: aload_0
    //   89: iconst_0
    //   90: anewarray java/lang/Object
    //   93: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   96: checkcast wtf/opal/kd
    //   99: iconst_0
    //   100: anewarray java/lang/Object
    //   103: invokevirtual r : ([Ljava/lang/Object;)Ljava/lang/String;
    //   106: ldc 6.5
    //   108: lload #11
    //   110: iconst_3
    //   111: anewarray java/lang/Object
    //   114: dup_x2
    //   115: dup_x2
    //   116: pop
    //   117: invokestatic valueOf : (J)Ljava/lang/Long;
    //   120: iconst_2
    //   121: swap
    //   122: aastore
    //   123: dup_x1
    //   124: swap
    //   125: invokestatic valueOf : (F)Ljava/lang/Float;
    //   128: iconst_1
    //   129: swap
    //   130: aastore
    //   131: dup_x1
    //   132: swap
    //   133: iconst_0
    //   134: swap
    //   135: aastore
    //   136: invokevirtual s : ([Ljava/lang/Object;)F
    //   139: fstore #28
    //   141: getstatic wtf/opal/b3.Z : Lwtf/opal/bu;
    //   144: aload_0
    //   145: iconst_0
    //   146: anewarray java/lang/Object
    //   149: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   152: checkcast wtf/opal/kd
    //   155: iconst_0
    //   156: anewarray java/lang/Object
    //   159: invokevirtual r : ([Ljava/lang/Object;)Ljava/lang/String;
    //   162: ldc 6.5
    //   164: lload #15
    //   166: sipush #25273
    //   169: ldc2_w 2908737953286584683
    //   172: lload #7
    //   174: lxor
    //   175: <illegal opcode> d : (IJ)I
    //   180: iconst_4
    //   181: anewarray java/lang/Object
    //   184: dup_x1
    //   185: swap
    //   186: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   189: iconst_3
    //   190: swap
    //   191: aastore
    //   192: dup_x2
    //   193: dup_x2
    //   194: pop
    //   195: invokestatic valueOf : (J)Ljava/lang/Long;
    //   198: iconst_2
    //   199: swap
    //   200: aastore
    //   201: dup_x1
    //   202: swap
    //   203: invokestatic valueOf : (F)Ljava/lang/Float;
    //   206: iconst_1
    //   207: swap
    //   208: aastore
    //   209: dup_x1
    //   210: swap
    //   211: iconst_0
    //   212: swap
    //   213: aastore
    //   214: invokevirtual E : ([Ljava/lang/Object;)F
    //   217: fstore #29
    //   219: astore #27
    //   221: aload_0
    //   222: getfield e : F
    //   225: ldc 9.0
    //   227: fsub
    //   228: fstore #30
    //   230: aload_0
    //   231: getfield m : F
    //   234: aload_0
    //   235: iconst_0
    //   236: anewarray java/lang/Object
    //   239: invokevirtual U : ([Ljava/lang/Object;)F
    //   242: fsub
    //   243: ldc 5.0
    //   245: fsub
    //   246: fstore #31
    //   248: aload_0
    //   249: getfield U : F
    //   252: lload #9
    //   254: dup2_x1
    //   255: pop2
    //   256: aload_0
    //   257: getfield t : F
    //   260: aload_0
    //   261: getfield e : F
    //   264: fload #31
    //   266: dload_3
    //   267: dload #5
    //   269: bipush #7
    //   271: anewarray java/lang/Object
    //   274: dup_x2
    //   275: dup_x2
    //   276: pop
    //   277: invokestatic valueOf : (D)Ljava/lang/Double;
    //   280: bipush #6
    //   282: swap
    //   283: aastore
    //   284: dup_x2
    //   285: dup_x2
    //   286: pop
    //   287: invokestatic valueOf : (D)Ljava/lang/Double;
    //   290: iconst_5
    //   291: swap
    //   292: aastore
    //   293: dup_x1
    //   294: swap
    //   295: invokestatic valueOf : (F)Ljava/lang/Float;
    //   298: iconst_4
    //   299: swap
    //   300: aastore
    //   301: dup_x1
    //   302: swap
    //   303: invokestatic valueOf : (F)Ljava/lang/Float;
    //   306: iconst_3
    //   307: swap
    //   308: aastore
    //   309: dup_x1
    //   310: swap
    //   311: invokestatic valueOf : (F)Ljava/lang/Float;
    //   314: iconst_2
    //   315: swap
    //   316: aastore
    //   317: dup_x1
    //   318: swap
    //   319: invokestatic valueOf : (F)Ljava/lang/Float;
    //   322: iconst_1
    //   323: swap
    //   324: aastore
    //   325: dup_x2
    //   326: dup_x2
    //   327: pop
    //   328: invokestatic valueOf : (J)Ljava/lang/Long;
    //   331: iconst_0
    //   332: swap
    //   333: aastore
    //   334: invokestatic Z : ([Ljava/lang/Object;)Z
    //   337: aload #27
    //   339: ifnull -> 455
    //   342: ifeq -> 454
    //   345: goto -> 352
    //   348: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   351: athrow
    //   352: iload_2
    //   353: aload #27
    //   355: lload #7
    //   357: lconst_0
    //   358: lcmp
    //   359: iflt -> 457
    //   362: ifnull -> 455
    //   365: goto -> 372
    //   368: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   371: athrow
    //   372: iconst_1
    //   373: if_icmpne -> 454
    //   376: goto -> 383
    //   379: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   382: athrow
    //   383: aload_0
    //   384: iconst_0
    //   385: anewarray java/lang/Object
    //   388: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   391: checkcast wtf/opal/kd
    //   394: aload_0
    //   395: iconst_0
    //   396: anewarray java/lang/Object
    //   399: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   402: checkcast wtf/opal/kd
    //   405: iconst_0
    //   406: anewarray java/lang/Object
    //   409: invokevirtual q : ([Ljava/lang/Object;)Z
    //   412: aload #27
    //   414: ifnull -> 435
    //   417: goto -> 424
    //   420: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   423: athrow
    //   424: ifne -> 438
    //   427: goto -> 434
    //   430: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   433: athrow
    //   434: iconst_1
    //   435: goto -> 439
    //   438: iconst_0
    //   439: iconst_1
    //   440: anewarray java/lang/Object
    //   443: dup_x1
    //   444: swap
    //   445: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   448: iconst_0
    //   449: swap
    //   450: aastore
    //   451: invokevirtual o : ([Ljava/lang/Object;)V
    //   454: iload_2
    //   455: aload #27
    //   457: ifnull -> 495
    //   460: ifne -> 912
    //   463: goto -> 470
    //   466: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   469: athrow
    //   470: aload_0
    //   471: iconst_0
    //   472: anewarray java/lang/Object
    //   475: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   478: checkcast wtf/opal/kd
    //   481: iconst_0
    //   482: anewarray java/lang/Object
    //   485: invokevirtual q : ([Ljava/lang/Object;)Z
    //   488: goto -> 495
    //   491: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   494: athrow
    //   495: ifeq -> 912
    //   498: fconst_0
    //   499: fstore #32
    //   501: aload_0
    //   502: iconst_0
    //   503: anewarray java/lang/Object
    //   506: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   509: checkcast wtf/opal/kd
    //   512: invokevirtual z : ()Ljava/lang/Object;
    //   515: checkcast java/util/HashMap
    //   518: invokevirtual values : ()Ljava/util/Collection;
    //   521: invokeinterface iterator : ()Ljava/util/Iterator;
    //   526: astore #33
    //   528: aload #33
    //   530: invokeinterface hasNext : ()Z
    //   535: ifeq -> 912
    //   538: aload #33
    //   540: invokeinterface next : ()Ljava/lang/Object;
    //   545: checkcast wtf/opal/ke
    //   548: astore #34
    //   550: getstatic wtf/opal/b3.Z : Lwtf/opal/bu;
    //   553: aload #34
    //   555: iconst_0
    //   556: anewarray java/lang/Object
    //   559: invokevirtual r : ([Ljava/lang/Object;)Ljava/lang/String;
    //   562: ldc 6.5
    //   564: lload #11
    //   566: iconst_3
    //   567: anewarray java/lang/Object
    //   570: dup_x2
    //   571: dup_x2
    //   572: pop
    //   573: invokestatic valueOf : (J)Ljava/lang/Long;
    //   576: iconst_2
    //   577: swap
    //   578: aastore
    //   579: dup_x1
    //   580: swap
    //   581: invokestatic valueOf : (F)Ljava/lang/Float;
    //   584: iconst_1
    //   585: swap
    //   586: aastore
    //   587: dup_x1
    //   588: swap
    //   589: iconst_0
    //   590: swap
    //   591: aastore
    //   592: invokevirtual s : ([Ljava/lang/Object;)F
    //   595: fstore #35
    //   597: getstatic wtf/opal/b3.Z : Lwtf/opal/bu;
    //   600: aload #34
    //   602: iconst_0
    //   603: anewarray java/lang/Object
    //   606: invokevirtual r : ([Ljava/lang/Object;)Ljava/lang/String;
    //   609: ldc 6.5
    //   611: lload #15
    //   613: sipush #16574
    //   616: ldc2_w 632959204763065194
    //   619: lload #7
    //   621: lxor
    //   622: <illegal opcode> d : (IJ)I
    //   627: iconst_4
    //   628: anewarray java/lang/Object
    //   631: dup_x1
    //   632: swap
    //   633: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   636: iconst_3
    //   637: swap
    //   638: aastore
    //   639: dup_x2
    //   640: dup_x2
    //   641: pop
    //   642: invokestatic valueOf : (J)Ljava/lang/Long;
    //   645: iconst_2
    //   646: swap
    //   647: aastore
    //   648: dup_x1
    //   649: swap
    //   650: invokestatic valueOf : (F)Ljava/lang/Float;
    //   653: iconst_1
    //   654: swap
    //   655: aastore
    //   656: dup_x1
    //   657: swap
    //   658: iconst_0
    //   659: swap
    //   660: aastore
    //   661: invokevirtual E : ([Ljava/lang/Object;)F
    //   664: fstore #36
    //   666: aload_0
    //   667: getfield U : F
    //   670: ldc 4.5
    //   672: fadd
    //   673: fload #30
    //   675: fconst_2
    //   676: fdiv
    //   677: fadd
    //   678: fload #35
    //   680: fconst_2
    //   681: fdiv
    //   682: fsub
    //   683: aload_0
    //   684: getfield t : F
    //   687: ldc 2.5
    //   689: fload #31
    //   691: fconst_2
    //   692: fdiv
    //   693: fadd
    //   694: fload #36
    //   696: fconst_2
    //   697: fdiv
    //   698: fsub
    //   699: fload #29
    //   701: fadd
    //   702: aload_0
    //   703: getfield V : Lwtf/opal/dk;
    //   706: lload #17
    //   708: iconst_1
    //   709: anewarray java/lang/Object
    //   712: dup_x2
    //   713: dup_x2
    //   714: pop
    //   715: invokestatic valueOf : (J)Ljava/lang/Long;
    //   718: iconst_0
    //   719: swap
    //   720: aastore
    //   721: invokevirtual P : ([Ljava/lang/Object;)Ljava/lang/Double;
    //   724: invokevirtual floatValue : ()F
    //   727: fmul
    //   728: fadd
    //   729: fload #32
    //   731: fadd
    //   732: ldc 5.0
    //   734: fadd
    //   735: lload #7
    //   737: lconst_0
    //   738: lcmp
    //   739: iflt -> 904
    //   742: fload #30
    //   744: aload #27
    //   746: ifnull -> 903
    //   749: fload #31
    //   751: dload_3
    //   752: dload #5
    //   754: dstore #19
    //   756: dstore #21
    //   758: fstore #23
    //   760: fstore #24
    //   762: fstore #25
    //   764: fstore #26
    //   766: lload #7
    //   768: lconst_0
    //   769: lcmp
    //   770: ifle -> 878
    //   773: lload #9
    //   775: fload #26
    //   777: fload #25
    //   779: fload #24
    //   781: fload #23
    //   783: dload #21
    //   785: dload #19
    //   787: bipush #7
    //   789: anewarray java/lang/Object
    //   792: dup_x2
    //   793: dup_x2
    //   794: pop
    //   795: invokestatic valueOf : (D)Ljava/lang/Double;
    //   798: bipush #6
    //   800: swap
    //   801: aastore
    //   802: dup_x2
    //   803: dup_x2
    //   804: pop
    //   805: invokestatic valueOf : (D)Ljava/lang/Double;
    //   808: iconst_5
    //   809: swap
    //   810: aastore
    //   811: dup_x1
    //   812: swap
    //   813: invokestatic valueOf : (F)Ljava/lang/Float;
    //   816: iconst_4
    //   817: swap
    //   818: aastore
    //   819: dup_x1
    //   820: swap
    //   821: invokestatic valueOf : (F)Ljava/lang/Float;
    //   824: iconst_3
    //   825: swap
    //   826: aastore
    //   827: dup_x1
    //   828: swap
    //   829: invokestatic valueOf : (F)Ljava/lang/Float;
    //   832: iconst_2
    //   833: swap
    //   834: aastore
    //   835: dup_x1
    //   836: swap
    //   837: invokestatic valueOf : (F)Ljava/lang/Float;
    //   840: iconst_1
    //   841: swap
    //   842: aastore
    //   843: dup_x2
    //   844: dup_x2
    //   845: pop
    //   846: invokestatic valueOf : (J)Ljava/lang/Long;
    //   849: iconst_0
    //   850: swap
    //   851: aastore
    //   852: invokestatic Z : ([Ljava/lang/Object;)Z
    //   855: ifeq -> 890
    //   858: aload #34
    //   860: lload #13
    //   862: iconst_1
    //   863: anewarray java/lang/Object
    //   866: dup_x2
    //   867: dup_x2
    //   868: pop
    //   869: invokestatic valueOf : (J)Ljava/lang/Long;
    //   872: iconst_0
    //   873: swap
    //   874: aastore
    //   875: invokevirtual x : ([Ljava/lang/Object;)V
    //   878: aload #27
    //   880: ifnonnull -> 912
    //   883: goto -> 890
    //   886: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   889: athrow
    //   890: fload #32
    //   892: fload #36
    //   894: ldc 4.0
    //   896: goto -> 903
    //   899: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   902: athrow
    //   903: fadd
    //   904: fadd
    //   905: fstore #32
    //   907: aload #27
    //   909: ifnonnull -> 528
    //   912: return
    // Exception table:
    //   from	to	target	type
    //   248	345	348	wtf/opal/x5
    //   342	365	368	wtf/opal/x5
    //   352	376	379	wtf/opal/x5
    //   372	417	420	wtf/opal/x5
    //   383	427	430	wtf/opal/x5
    //   455	463	466	wtf/opal/x5
    //   460	488	491	wtf/opal/x5
    //   766	883	886	wtf/opal/x5
    //   858	896	899	wtf/opal/x5
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
    //   0: ldc2_w -2878033601292359402
    //   3: ldc2_w -1988903393048457804
    //   6: invokestatic lookup : ()Ljava/lang/invoke/MethodHandles$Lookup;
    //   9: invokevirtual lookupClass : ()Ljava/lang/Class;
    //   12: invokestatic a : (JJLjava/lang/Object;)Lwtf/opal/t5;
    //   15: ldc2_w 70800243274098
    //   18: invokeinterface a : (J)J
    //   23: putstatic wtf/opal/b3.b : J
    //   26: new java/util/HashMap
    //   29: dup
    //   30: bipush #13
    //   32: invokespecial <init> : (I)V
    //   35: putstatic wtf/opal/b3.f : Ljava/util/Map;
    //   38: getstatic wtf/opal/b3.b : J
    //   41: ldc2_w 138858727657102
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
    //   136: ldc_w '¯û[¢;ï.Ó\\bÇcNå$f0_X-ê.I¡0c1ÏÕà'
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
    //   291: ldc_w '~qoÓ¥HÔuªÍèÀ'
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
    //   448: putstatic wtf/opal/b3.c : [J
    //   451: bipush #6
    //   453: anewarray java/lang/Integer
    //   456: putstatic wtf/opal/b3.d : [Ljava/lang/Integer;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x31CD;
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
        throw new RuntimeException("wtf/opal/b3", exception);
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
    //   65: ldc_w 'wtf/opal/b3'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\b3.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */