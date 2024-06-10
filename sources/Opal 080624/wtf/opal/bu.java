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
import net.minecraft.class_332;

public final class bu {
  private static final String j;
  
  private static final int[] s;
  
  private static final float O = 10.0F;
  
  private final dc X = d1.q(new Object[0]).D(new Object[0]);
  
  public boolean t = true;
  
  public boolean R = false;
  
  private static d[] r;
  
  private static final long a = on.a(-1840906957712236166L, 7242129340066784745L, MethodHandles.lookup().lookupClass()).a(177593564340037L);
  
  private static final String[] b;
  
  private static final String[] c;
  
  private static final Map d = new HashMap<>(13);
  
  private static final long[] e;
  
  private static final Integer[] f;
  
  private static final Map g;
  
  public void E(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast wtf/opal/lx
    //   7: astore_3
    //   8: dup
    //   9: iconst_1
    //   10: aaload
    //   11: checkcast net/minecraft/class_332
    //   14: astore #11
    //   16: dup
    //   17: iconst_2
    //   18: aaload
    //   19: checkcast java/lang/String
    //   22: astore #6
    //   24: dup
    //   25: iconst_3
    //   26: aaload
    //   27: checkcast java/lang/Float
    //   30: invokevirtual floatValue : ()F
    //   33: fstore #10
    //   35: dup
    //   36: iconst_4
    //   37: aaload
    //   38: checkcast java/lang/Float
    //   41: invokevirtual floatValue : ()F
    //   44: fstore_2
    //   45: dup
    //   46: iconst_5
    //   47: aaload
    //   48: checkcast java/lang/Float
    //   51: invokevirtual floatValue : ()F
    //   54: fstore #8
    //   56: dup
    //   57: bipush #6
    //   59: aaload
    //   60: checkcast java/lang/Integer
    //   63: invokevirtual intValue : ()I
    //   66: istore #7
    //   68: dup
    //   69: bipush #7
    //   71: aaload
    //   72: checkcast java/lang/Boolean
    //   75: invokevirtual booleanValue : ()Z
    //   78: istore #9
    //   80: dup
    //   81: bipush #8
    //   83: aaload
    //   84: checkcast java/lang/Integer
    //   87: invokevirtual intValue : ()I
    //   90: istore #4
    //   92: dup
    //   93: bipush #9
    //   95: aaload
    //   96: checkcast java/lang/Long
    //   99: invokevirtual longValue : ()J
    //   102: lstore #12
    //   104: dup
    //   105: bipush #10
    //   107: aaload
    //   108: checkcast java/lang/Integer
    //   111: invokevirtual intValue : ()I
    //   114: istore #5
    //   116: pop
    //   117: getstatic wtf/opal/bu.a : J
    //   120: lload #12
    //   122: lxor
    //   123: lstore #12
    //   125: lload #12
    //   127: dup2
    //   128: ldc2_w 88391835376714
    //   131: lxor
    //   132: lstore #14
    //   134: dup2
    //   135: ldc2_w 98868496906675
    //   138: lxor
    //   139: lstore #16
    //   141: dup2
    //   142: ldc2_w 132407700001098
    //   145: lxor
    //   146: lstore #18
    //   148: dup2
    //   149: ldc2_w 35163624857493
    //   152: lxor
    //   153: lstore #20
    //   155: dup2
    //   156: ldc2_w 91251546238105
    //   159: lxor
    //   160: lstore #22
    //   162: dup2
    //   163: ldc2_w 6561685196797
    //   166: lxor
    //   167: lstore #24
    //   169: pop2
    //   170: invokestatic n : ()[Lwtf/opal/d;
    //   173: iconst_0
    //   174: anewarray java/lang/Object
    //   177: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   180: iconst_0
    //   181: anewarray java/lang/Object
    //   184: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   187: ldc wtf/opal/jt
    //   189: iconst_1
    //   190: anewarray java/lang/Object
    //   193: dup_x1
    //   194: swap
    //   195: iconst_0
    //   196: swap
    //   197: aastore
    //   198: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   201: checkcast wtf/opal/jt
    //   204: astore #27
    //   206: astore #26
    //   208: aload #27
    //   210: lload #14
    //   212: iconst_1
    //   213: anewarray java/lang/Object
    //   216: dup_x2
    //   217: dup_x2
    //   218: pop
    //   219: invokestatic valueOf : (J)Ljava/lang/Long;
    //   222: iconst_0
    //   223: swap
    //   224: aastore
    //   225: invokevirtual S : ([Ljava/lang/Object;)Lwtf/opal/dv;
    //   228: astore #28
    //   230: iload #5
    //   232: iconst_4
    //   233: iand
    //   234: aload #26
    //   236: ifnull -> 258
    //   239: iconst_4
    //   240: if_icmpne -> 261
    //   243: goto -> 250
    //   246: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   249: athrow
    //   250: iconst_1
    //   251: goto -> 258
    //   254: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   257: athrow
    //   258: goto -> 262
    //   261: iconst_0
    //   262: istore #29
    //   264: iload #7
    //   266: istore #30
    //   268: fload_2
    //   269: aload #28
    //   271: iconst_0
    //   272: anewarray java/lang/Object
    //   275: invokevirtual b : ([Ljava/lang/Object;)F
    //   278: fadd
    //   279: fstore_2
    //   280: aload #28
    //   282: aload #26
    //   284: lload #12
    //   286: lconst_0
    //   287: lcmp
    //   288: ifle -> 298
    //   291: ifnull -> 360
    //   294: iconst_0
    //   295: anewarray java/lang/Object
    //   298: invokevirtual e : ([Ljava/lang/Object;)Z
    //   301: ifeq -> 358
    //   304: goto -> 311
    //   307: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   310: athrow
    //   311: fload #8
    //   313: lload #12
    //   315: lconst_0
    //   316: lcmp
    //   317: ifle -> 356
    //   320: ldc 9.0
    //   322: aload #26
    //   324: ifnull -> 355
    //   327: goto -> 334
    //   330: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   333: athrow
    //   334: fcmpl
    //   335: iflt -> 358
    //   338: goto -> 345
    //   341: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   344: athrow
    //   345: fload #8
    //   347: fconst_1
    //   348: goto -> 355
    //   351: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   354: athrow
    //   355: fsub
    //   356: fstore #8
    //   358: aload #28
    //   360: getstatic wtf/opal/dv.MINECRAFT : Lwtf/opal/dv;
    //   363: if_acmpne -> 584
    //   366: fload #8
    //   368: ldc 10.0
    //   370: fdiv
    //   371: fstore #31
    //   373: aload_3
    //   374: getstatic wtf/opal/lx.REGULAR : Lwtf/opal/lx;
    //   377: if_acmpeq -> 440
    //   380: aload #6
    //   382: ldc '§'
    //   384: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   387: aload #26
    //   389: lload #12
    //   391: lconst_0
    //   392: lcmp
    //   393: iflt -> 444
    //   396: ifnull -> 442
    //   399: goto -> 406
    //   402: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   405: athrow
    //   406: ifne -> 440
    //   409: goto -> 416
    //   412: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   415: athrow
    //   416: aload #6
    //   418: sipush #5585
    //   421: ldc2_w 8078726435441913243
    //   424: lload #12
    //   426: lxor
    //   427: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   432: swap
    //   433: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   438: astore #6
    //   440: iload #29
    //   442: aload #26
    //   444: ifnull -> 569
    //   447: ifeq -> 474
    //   450: goto -> 457
    //   453: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   456: athrow
    //   457: fload #10
    //   459: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   462: getfield field_1772 : Lnet/minecraft/class_327;
    //   465: aload #6
    //   467: invokevirtual method_1727 : (Ljava/lang/String;)I
    //   470: i2f
    //   471: fsub
    //   472: fstore #10
    //   474: aload #11
    //   476: invokevirtual method_51448 : ()Lnet/minecraft/class_4587;
    //   479: invokevirtual method_22903 : ()V
    //   482: aload #11
    //   484: invokevirtual method_51448 : ()Lnet/minecraft/class_4587;
    //   487: fload #10
    //   489: fload_2
    //   490: fconst_1
    //   491: fadd
    //   492: fconst_0
    //   493: invokevirtual method_46416 : (FFF)V
    //   496: aload #11
    //   498: invokevirtual method_51448 : ()Lnet/minecraft/class_4587;
    //   501: fload #31
    //   503: fload #31
    //   505: fconst_1
    //   506: invokevirtual method_22905 : (FFF)V
    //   509: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   512: getfield field_1772 : Lnet/minecraft/class_327;
    //   515: aload #6
    //   517: fconst_0
    //   518: fconst_0
    //   519: iload #7
    //   521: iload #9
    //   523: aload #11
    //   525: invokevirtual method_51448 : ()Lnet/minecraft/class_4587;
    //   528: invokevirtual method_23760 : ()Lnet/minecraft/class_4587$class_4665;
    //   531: invokevirtual method_23761 : ()Lorg/joml/Matrix4f;
    //   534: aload #11
    //   536: invokevirtual method_51450 : ()Lnet/minecraft/class_4597$class_4598;
    //   539: getstatic net/minecraft/class_327$class_6415.field_33993 : Lnet/minecraft/class_327$class_6415;
    //   542: iconst_0
    //   543: sipush #1812
    //   546: ldc2_w 4935063582754119458
    //   549: lload #12
    //   551: lxor
    //   552: <illegal opcode> q : (IJ)I
    //   557: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   560: getfield field_1772 : Lnet/minecraft/class_327;
    //   563: invokevirtual method_1726 : ()Z
    //   566: invokevirtual method_27522 : (Ljava/lang/String;FFIZLorg/joml/Matrix4f;Lnet/minecraft/class_4597;Lnet/minecraft/class_327$class_6415;IIZ)I
    //   569: pop
    //   570: aload #11
    //   572: invokevirtual method_51448 : ()Lnet/minecraft/class_4587;
    //   575: invokevirtual method_22909 : ()V
    //   578: aload #11
    //   580: invokevirtual method_51452 : ()V
    //   583: return
    //   584: new java/lang/StringBuilder
    //   587: dup
    //   588: invokespecial <init> : ()V
    //   591: astore #31
    //   593: iconst_0
    //   594: istore #32
    //   596: aload_0
    //   597: lload #22
    //   599: aload #6
    //   601: iconst_2
    //   602: anewarray java/lang/Object
    //   605: dup_x1
    //   606: swap
    //   607: iconst_1
    //   608: swap
    //   609: aastore
    //   610: dup_x2
    //   611: dup_x2
    //   612: pop
    //   613: invokestatic valueOf : (J)Ljava/lang/Long;
    //   616: iconst_0
    //   617: swap
    //   618: aastore
    //   619: invokevirtual l : ([Ljava/lang/Object;)Ljava/lang/String;
    //   622: astore #6
    //   624: iconst_0
    //   625: istore #33
    //   627: iload #33
    //   629: aload #6
    //   631: invokevirtual length : ()I
    //   634: if_icmpge -> 1428
    //   637: aload #6
    //   639: iload #33
    //   641: invokevirtual charAt : (I)C
    //   644: istore #34
    //   646: iload #34
    //   648: aload #26
    //   650: lload #12
    //   652: lconst_0
    //   653: lcmp
    //   654: ifle -> 662
    //   657: ifnull -> 1446
    //   660: aload #26
    //   662: lload #12
    //   664: lconst_0
    //   665: lcmp
    //   666: ifle -> 751
    //   669: ifnull -> 749
    //   672: goto -> 679
    //   675: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   678: athrow
    //   679: sipush #28093
    //   682: ldc2_w 8690483472519984525
    //   685: lload #12
    //   687: lxor
    //   688: <illegal opcode> q : (IJ)I
    //   693: if_icmpeq -> 744
    //   696: goto -> 703
    //   699: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   702: athrow
    //   703: aload #31
    //   705: iload #34
    //   707: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   710: pop
    //   711: aload #26
    //   713: lload #12
    //   715: lconst_0
    //   716: lcmp
    //   717: iflt -> 1425
    //   720: ifnonnull -> 1420
    //   723: goto -> 730
    //   726: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   729: athrow
    //   730: iconst_1
    //   731: anewarray wtf/opal/d
    //   734: invokestatic p : ([Lwtf/opal/d;)V
    //   737: goto -> 744
    //   740: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   743: athrow
    //   744: aload #31
    //   746: invokevirtual isEmpty : ()Z
    //   749: aload #26
    //   751: lload #12
    //   753: lconst_0
    //   754: lcmp
    //   755: ifle -> 1074
    //   758: ifnull -> 1072
    //   761: ifne -> 1070
    //   764: goto -> 771
    //   767: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   770: athrow
    //   771: iload #32
    //   773: ifeq -> 831
    //   776: goto -> 783
    //   779: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   782: athrow
    //   783: aload_0
    //   784: getfield X : Lwtf/opal/dc;
    //   787: sipush #18287
    //   790: ldc2_w 6751044137647144737
    //   793: lload #12
    //   795: lxor
    //   796: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   801: lload #20
    //   803: iconst_2
    //   804: anewarray java/lang/Object
    //   807: dup_x2
    //   808: dup_x2
    //   809: pop
    //   810: invokestatic valueOf : (J)Ljava/lang/Long;
    //   813: iconst_1
    //   814: swap
    //   815: aastore
    //   816: dup_x1
    //   817: swap
    //   818: iconst_0
    //   819: swap
    //   820: aastore
    //   821: invokevirtual W : ([Ljava/lang/Object;)Lwtf/opal/u2;
    //   824: goto -> 863
    //   827: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   830: athrow
    //   831: aload_0
    //   832: aload #28
    //   834: aload_3
    //   835: lload #18
    //   837: iconst_3
    //   838: anewarray java/lang/Object
    //   841: dup_x2
    //   842: dup_x2
    //   843: pop
    //   844: invokestatic valueOf : (J)Ljava/lang/Long;
    //   847: iconst_2
    //   848: swap
    //   849: aastore
    //   850: dup_x1
    //   851: swap
    //   852: iconst_1
    //   853: swap
    //   854: aastore
    //   855: dup_x1
    //   856: swap
    //   857: iconst_0
    //   858: swap
    //   859: aastore
    //   860: invokevirtual H : ([Ljava/lang/Object;)Lwtf/opal/u2;
    //   863: astore #35
    //   865: aload #31
    //   867: invokevirtual toString : ()Ljava/lang/String;
    //   870: astore #36
    //   872: aload #35
    //   874: aload #36
    //   876: fload #10
    //   878: fload_2
    //   879: fload #8
    //   881: iload #7
    //   883: lload #24
    //   885: iload #9
    //   887: iload #4
    //   889: iload #5
    //   891: bipush #9
    //   893: anewarray java/lang/Object
    //   896: dup_x1
    //   897: swap
    //   898: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   901: bipush #8
    //   903: swap
    //   904: aastore
    //   905: dup_x1
    //   906: swap
    //   907: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   910: bipush #7
    //   912: swap
    //   913: aastore
    //   914: dup_x1
    //   915: swap
    //   916: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   919: bipush #6
    //   921: swap
    //   922: aastore
    //   923: dup_x2
    //   924: dup_x2
    //   925: pop
    //   926: invokestatic valueOf : (J)Ljava/lang/Long;
    //   929: iconst_5
    //   930: swap
    //   931: aastore
    //   932: dup_x1
    //   933: swap
    //   934: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   937: iconst_4
    //   938: swap
    //   939: aastore
    //   940: dup_x1
    //   941: swap
    //   942: invokestatic valueOf : (F)Ljava/lang/Float;
    //   945: iconst_3
    //   946: swap
    //   947: aastore
    //   948: dup_x1
    //   949: swap
    //   950: invokestatic valueOf : (F)Ljava/lang/Float;
    //   953: iconst_2
    //   954: swap
    //   955: aastore
    //   956: dup_x1
    //   957: swap
    //   958: invokestatic valueOf : (F)Ljava/lang/Float;
    //   961: iconst_1
    //   962: swap
    //   963: aastore
    //   964: dup_x1
    //   965: swap
    //   966: iconst_0
    //   967: swap
    //   968: aastore
    //   969: invokevirtual w : ([Ljava/lang/Object;)V
    //   972: aload #35
    //   974: aload #36
    //   976: lload #16
    //   978: fload #8
    //   980: iload #5
    //   982: iconst_4
    //   983: anewarray java/lang/Object
    //   986: dup_x1
    //   987: swap
    //   988: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   991: iconst_3
    //   992: swap
    //   993: aastore
    //   994: dup_x1
    //   995: swap
    //   996: invokestatic valueOf : (F)Ljava/lang/Float;
    //   999: iconst_2
    //   1000: swap
    //   1001: aastore
    //   1002: dup_x2
    //   1003: dup_x2
    //   1004: pop
    //   1005: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1008: iconst_1
    //   1009: swap
    //   1010: aastore
    //   1011: dup_x1
    //   1012: swap
    //   1013: iconst_0
    //   1014: swap
    //   1015: aastore
    //   1016: invokevirtual M : ([Ljava/lang/Object;)F
    //   1019: fstore #37
    //   1021: aload #26
    //   1023: lload #12
    //   1025: lconst_0
    //   1026: lcmp
    //   1027: iflt -> 1054
    //   1030: ifnull -> 1052
    //   1033: iload #29
    //   1035: ifeq -> 1057
    //   1038: goto -> 1045
    //   1041: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1044: athrow
    //   1045: fload #10
    //   1047: fload #37
    //   1049: fsub
    //   1050: fstore #10
    //   1052: aload #26
    //   1054: ifnonnull -> 1064
    //   1057: fload #10
    //   1059: fload #37
    //   1061: fadd
    //   1062: fstore #10
    //   1064: aload #31
    //   1066: iconst_0
    //   1067: invokevirtual setLength : (I)V
    //   1070: iload #33
    //   1072: aload #26
    //   1074: ifnull -> 1115
    //   1077: aload #6
    //   1079: invokevirtual length : ()I
    //   1082: iconst_1
    //   1083: isub
    //   1084: if_icmpne -> 1118
    //   1087: goto -> 1094
    //   1090: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1093: athrow
    //   1094: sipush #27887
    //   1097: ldc2_w 3101111325907645661
    //   1100: lload #12
    //   1102: lxor
    //   1103: <illegal opcode> q : (IJ)I
    //   1108: goto -> 1115
    //   1111: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1114: athrow
    //   1115: goto -> 1144
    //   1118: sipush #22187
    //   1121: ldc2_w 4730401832294346470
    //   1124: lload #12
    //   1126: lxor
    //   1127: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   1132: aload #6
    //   1134: iload #33
    //   1136: iconst_1
    //   1137: iadd
    //   1138: invokevirtual charAt : (I)C
    //   1141: invokevirtual indexOf : (I)I
    //   1144: istore #35
    //   1146: iload #35
    //   1148: aload #26
    //   1150: lload #12
    //   1152: lconst_0
    //   1153: lcmp
    //   1154: iflt -> 1315
    //   1157: ifnull -> 1313
    //   1160: sipush #26448
    //   1163: ldc2_w 1536334833784389481
    //   1166: lload #12
    //   1168: lxor
    //   1169: <illegal opcode> q : (IJ)I
    //   1174: if_icmpge -> 1304
    //   1177: goto -> 1184
    //   1180: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1183: athrow
    //   1184: getstatic wtf/opal/lx.REGULAR : Lwtf/opal/lx;
    //   1187: astore_3
    //   1188: iconst_0
    //   1189: istore #32
    //   1191: iload #35
    //   1193: aload #26
    //   1195: ifnull -> 1256
    //   1198: ifge -> 1224
    //   1201: goto -> 1208
    //   1204: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1207: athrow
    //   1208: sipush #23486
    //   1211: ldc2_w 2133116297707045761
    //   1214: lload #12
    //   1216: lxor
    //   1217: <illegal opcode> q : (IJ)I
    //   1222: istore #35
    //   1224: iload #7
    //   1226: sipush #28698
    //   1229: ldc2_w 5990627079085647906
    //   1232: lload #12
    //   1234: lxor
    //   1235: <illegal opcode> q : (IJ)I
    //   1240: ishr
    //   1241: sipush #17627
    //   1244: ldc2_w 7820343683272367336
    //   1247: lload #12
    //   1249: lxor
    //   1250: <illegal opcode> q : (IJ)I
    //   1255: iand
    //   1256: istore #36
    //   1258: getstatic wtf/opal/bu.s : [I
    //   1261: iload #35
    //   1263: iaload
    //   1264: iload #36
    //   1266: i2f
    //   1267: iconst_2
    //   1268: anewarray java/lang/Object
    //   1271: dup_x1
    //   1272: swap
    //   1273: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1276: iconst_1
    //   1277: swap
    //   1278: aastore
    //   1279: dup_x1
    //   1280: swap
    //   1281: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1284: iconst_0
    //   1285: swap
    //   1286: aastore
    //   1287: invokestatic X : ([Ljava/lang/Object;)I
    //   1290: lload #12
    //   1292: lconst_0
    //   1293: lcmp
    //   1294: ifle -> 1306
    //   1297: istore #7
    //   1299: aload #26
    //   1301: ifnonnull -> 1417
    //   1304: iload #35
    //   1306: goto -> 1313
    //   1309: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1312: athrow
    //   1313: aload #26
    //   1315: ifnull -> 1415
    //   1318: lookupswitch default -> 1410, 17 -> 1356, 21 -> 1372, 22 -> 1395
    //   1352: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1355: athrow
    //   1356: getstatic wtf/opal/lx.SEMI_BOLD : Lwtf/opal/lx;
    //   1359: astore_3
    //   1360: aload #26
    //   1362: lload #12
    //   1364: lconst_0
    //   1365: lcmp
    //   1366: iflt -> 1385
    //   1369: ifnonnull -> 1417
    //   1372: getstatic wtf/opal/lx.REGULAR : Lwtf/opal/lx;
    //   1375: astore_3
    //   1376: iconst_0
    //   1377: istore #32
    //   1379: iload #30
    //   1381: istore #7
    //   1383: aload #26
    //   1385: lload #12
    //   1387: lconst_0
    //   1388: lcmp
    //   1389: iflt -> 1407
    //   1392: ifnonnull -> 1417
    //   1395: iconst_1
    //   1396: istore #32
    //   1398: lload #12
    //   1400: lconst_0
    //   1401: lcmp
    //   1402: ifle -> 1414
    //   1405: aload #26
    //   1407: ifnonnull -> 1417
    //   1410: getstatic wtf/opal/lx.REGULAR : Lwtf/opal/lx;
    //   1413: astore_3
    //   1414: iconst_1
    //   1415: istore #32
    //   1417: iinc #33, 1
    //   1420: iinc #33, 1
    //   1423: aload #26
    //   1425: ifnonnull -> 627
    //   1428: aload #31
    //   1430: invokevirtual toString : ()Ljava/lang/String;
    //   1433: invokevirtual trim : ()Ljava/lang/String;
    //   1436: lload #12
    //   1438: lconst_0
    //   1439: lcmp
    //   1440: ifle -> 639
    //   1443: invokevirtual isEmpty : ()Z
    //   1446: ifne -> 1586
    //   1449: aload_0
    //   1450: aload #28
    //   1452: aload_3
    //   1453: lload #18
    //   1455: iconst_3
    //   1456: anewarray java/lang/Object
    //   1459: dup_x2
    //   1460: dup_x2
    //   1461: pop
    //   1462: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1465: iconst_2
    //   1466: swap
    //   1467: aastore
    //   1468: dup_x1
    //   1469: swap
    //   1470: iconst_1
    //   1471: swap
    //   1472: aastore
    //   1473: dup_x1
    //   1474: swap
    //   1475: iconst_0
    //   1476: swap
    //   1477: aastore
    //   1478: invokevirtual H : ([Ljava/lang/Object;)Lwtf/opal/u2;
    //   1481: astore #33
    //   1483: aload #33
    //   1485: aload #31
    //   1487: invokevirtual toString : ()Ljava/lang/String;
    //   1490: fload #10
    //   1492: fload_2
    //   1493: fload #8
    //   1495: iload #7
    //   1497: lload #24
    //   1499: iload #9
    //   1501: iload #4
    //   1503: iload #5
    //   1505: bipush #9
    //   1507: anewarray java/lang/Object
    //   1510: dup_x1
    //   1511: swap
    //   1512: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1515: bipush #8
    //   1517: swap
    //   1518: aastore
    //   1519: dup_x1
    //   1520: swap
    //   1521: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1524: bipush #7
    //   1526: swap
    //   1527: aastore
    //   1528: dup_x1
    //   1529: swap
    //   1530: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   1533: bipush #6
    //   1535: swap
    //   1536: aastore
    //   1537: dup_x2
    //   1538: dup_x2
    //   1539: pop
    //   1540: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1543: iconst_5
    //   1544: swap
    //   1545: aastore
    //   1546: dup_x1
    //   1547: swap
    //   1548: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1551: iconst_4
    //   1552: swap
    //   1553: aastore
    //   1554: dup_x1
    //   1555: swap
    //   1556: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1559: iconst_3
    //   1560: swap
    //   1561: aastore
    //   1562: dup_x1
    //   1563: swap
    //   1564: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1567: iconst_2
    //   1568: swap
    //   1569: aastore
    //   1570: dup_x1
    //   1571: swap
    //   1572: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1575: iconst_1
    //   1576: swap
    //   1577: aastore
    //   1578: dup_x1
    //   1579: swap
    //   1580: iconst_0
    //   1581: swap
    //   1582: aastore
    //   1583: invokevirtual w : ([Ljava/lang/Object;)V
    //   1586: return
    // Exception table:
    //   from	to	target	type
    //   230	243	246	wtf/opal/x5
    //   239	251	254	wtf/opal/x5
    //   280	304	307	wtf/opal/x5
    //   294	327	330	wtf/opal/x5
    //   311	338	341	wtf/opal/x5
    //   334	348	351	wtf/opal/x5
    //   373	399	402	wtf/opal/x5
    //   380	409	412	wtf/opal/x5
    //   442	450	453	wtf/opal/x5
    //   646	672	675	wtf/opal/x5
    //   660	696	699	wtf/opal/x5
    //   679	723	726	wtf/opal/x5
    //   703	737	740	wtf/opal/x5
    //   749	764	767	wtf/opal/x5
    //   761	776	779	wtf/opal/x5
    //   771	827	827	wtf/opal/x5
    //   1021	1038	1041	wtf/opal/x5
    //   1072	1087	1090	wtf/opal/x5
    //   1077	1108	1111	wtf/opal/x5
    //   1146	1177	1180	wtf/opal/x5
    //   1191	1201	1204	wtf/opal/x5
    //   1299	1306	1309	wtf/opal/x5
    //   1313	1352	1352	wtf/opal/x5
  }
  
  public void z(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast wtf/opal/lx
    //   7: astore #4
    //   9: dup
    //   10: iconst_1
    //   11: aaload
    //   12: checkcast net/minecraft/class_332
    //   15: astore #7
    //   17: dup
    //   18: iconst_2
    //   19: aaload
    //   20: checkcast java/lang/String
    //   23: astore #6
    //   25: dup
    //   26: iconst_3
    //   27: aaload
    //   28: checkcast java/lang/Float
    //   31: invokevirtual floatValue : ()F
    //   34: fstore #5
    //   36: dup
    //   37: iconst_4
    //   38: aaload
    //   39: checkcast java/lang/Long
    //   42: invokevirtual longValue : ()J
    //   45: lstore #9
    //   47: dup
    //   48: iconst_5
    //   49: aaload
    //   50: checkcast java/lang/Float
    //   53: invokevirtual floatValue : ()F
    //   56: fstore_3
    //   57: dup
    //   58: bipush #6
    //   60: aaload
    //   61: checkcast java/lang/Float
    //   64: invokevirtual floatValue : ()F
    //   67: fstore_2
    //   68: dup
    //   69: bipush #7
    //   71: aaload
    //   72: checkcast java/lang/Integer
    //   75: invokevirtual intValue : ()I
    //   78: istore #8
    //   80: pop
    //   81: getstatic wtf/opal/bu.a : J
    //   84: lload #9
    //   86: lxor
    //   87: lstore #9
    //   89: lload #9
    //   91: dup2
    //   92: ldc2_w 102622097658367
    //   95: lxor
    //   96: lstore #11
    //   98: pop2
    //   99: aload_0
    //   100: aload #4
    //   102: aload #7
    //   104: aload #6
    //   106: fload #5
    //   108: fload_3
    //   109: fload_2
    //   110: iload #8
    //   112: iconst_0
    //   113: iconst_0
    //   114: lload #11
    //   116: sipush #15704
    //   119: ldc2_w 5470125723237209767
    //   122: lload #9
    //   124: lxor
    //   125: <illegal opcode> q : (IJ)I
    //   130: bipush #11
    //   132: anewarray java/lang/Object
    //   135: dup_x1
    //   136: swap
    //   137: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   140: bipush #10
    //   142: swap
    //   143: aastore
    //   144: dup_x2
    //   145: dup_x2
    //   146: pop
    //   147: invokestatic valueOf : (J)Ljava/lang/Long;
    //   150: bipush #9
    //   152: swap
    //   153: aastore
    //   154: dup_x1
    //   155: swap
    //   156: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   159: bipush #8
    //   161: swap
    //   162: aastore
    //   163: dup_x1
    //   164: swap
    //   165: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   168: bipush #7
    //   170: swap
    //   171: aastore
    //   172: dup_x1
    //   173: swap
    //   174: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   177: bipush #6
    //   179: swap
    //   180: aastore
    //   181: dup_x1
    //   182: swap
    //   183: invokestatic valueOf : (F)Ljava/lang/Float;
    //   186: iconst_5
    //   187: swap
    //   188: aastore
    //   189: dup_x1
    //   190: swap
    //   191: invokestatic valueOf : (F)Ljava/lang/Float;
    //   194: iconst_4
    //   195: swap
    //   196: aastore
    //   197: dup_x1
    //   198: swap
    //   199: invokestatic valueOf : (F)Ljava/lang/Float;
    //   202: iconst_3
    //   203: swap
    //   204: aastore
    //   205: dup_x1
    //   206: swap
    //   207: iconst_2
    //   208: swap
    //   209: aastore
    //   210: dup_x1
    //   211: swap
    //   212: iconst_1
    //   213: swap
    //   214: aastore
    //   215: dup_x1
    //   216: swap
    //   217: iconst_0
    //   218: swap
    //   219: aastore
    //   220: invokevirtual E : ([Ljava/lang/Object;)V
    //   223: return
  }
  
  public void H(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast wtf/opal/lx
    //   7: astore #9
    //   9: dup
    //   10: iconst_1
    //   11: aaload
    //   12: checkcast net/minecraft/class_332
    //   15: astore_2
    //   16: dup
    //   17: iconst_2
    //   18: aaload
    //   19: checkcast java/lang/String
    //   22: astore #11
    //   24: dup
    //   25: iconst_3
    //   26: aaload
    //   27: checkcast java/lang/Float
    //   30: invokevirtual floatValue : ()F
    //   33: fstore_3
    //   34: dup
    //   35: iconst_4
    //   36: aaload
    //   37: checkcast java/lang/Long
    //   40: invokevirtual longValue : ()J
    //   43: lstore #5
    //   45: dup
    //   46: iconst_5
    //   47: aaload
    //   48: checkcast java/lang/Float
    //   51: invokevirtual floatValue : ()F
    //   54: fstore #10
    //   56: dup
    //   57: bipush #6
    //   59: aaload
    //   60: checkcast java/lang/Float
    //   63: invokevirtual floatValue : ()F
    //   66: fstore #8
    //   68: dup
    //   69: bipush #7
    //   71: aaload
    //   72: checkcast java/lang/Integer
    //   75: invokevirtual intValue : ()I
    //   78: istore #4
    //   80: dup
    //   81: bipush #8
    //   83: aaload
    //   84: checkcast java/lang/Boolean
    //   87: invokevirtual booleanValue : ()Z
    //   90: istore #7
    //   92: pop
    //   93: getstatic wtf/opal/bu.a : J
    //   96: lload #5
    //   98: lxor
    //   99: lstore #5
    //   101: lload #5
    //   103: dup2
    //   104: ldc2_w 64260451163392
    //   107: lxor
    //   108: lstore #12
    //   110: pop2
    //   111: aload_0
    //   112: aload #9
    //   114: aload_2
    //   115: aload #11
    //   117: fload_3
    //   118: fload #10
    //   120: fload #8
    //   122: iload #4
    //   124: iload #7
    //   126: iconst_0
    //   127: lload #12
    //   129: sipush #27782
    //   132: ldc2_w 8739195498892004231
    //   135: lload #5
    //   137: lxor
    //   138: <illegal opcode> q : (IJ)I
    //   143: bipush #11
    //   145: anewarray java/lang/Object
    //   148: dup_x1
    //   149: swap
    //   150: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   153: bipush #10
    //   155: swap
    //   156: aastore
    //   157: dup_x2
    //   158: dup_x2
    //   159: pop
    //   160: invokestatic valueOf : (J)Ljava/lang/Long;
    //   163: bipush #9
    //   165: swap
    //   166: aastore
    //   167: dup_x1
    //   168: swap
    //   169: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   172: bipush #8
    //   174: swap
    //   175: aastore
    //   176: dup_x1
    //   177: swap
    //   178: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   181: bipush #7
    //   183: swap
    //   184: aastore
    //   185: dup_x1
    //   186: swap
    //   187: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   190: bipush #6
    //   192: swap
    //   193: aastore
    //   194: dup_x1
    //   195: swap
    //   196: invokestatic valueOf : (F)Ljava/lang/Float;
    //   199: iconst_5
    //   200: swap
    //   201: aastore
    //   202: dup_x1
    //   203: swap
    //   204: invokestatic valueOf : (F)Ljava/lang/Float;
    //   207: iconst_4
    //   208: swap
    //   209: aastore
    //   210: dup_x1
    //   211: swap
    //   212: invokestatic valueOf : (F)Ljava/lang/Float;
    //   215: iconst_3
    //   216: swap
    //   217: aastore
    //   218: dup_x1
    //   219: swap
    //   220: iconst_2
    //   221: swap
    //   222: aastore
    //   223: dup_x1
    //   224: swap
    //   225: iconst_1
    //   226: swap
    //   227: aastore
    //   228: dup_x1
    //   229: swap
    //   230: iconst_0
    //   231: swap
    //   232: aastore
    //   233: invokevirtual E : ([Ljava/lang/Object;)V
    //   236: return
  }
  
  public void b(Object[] paramArrayOfObject) {
    lx lx = (lx)paramArrayOfObject[0];
    class_332 class_332 = (class_332)paramArrayOfObject[1];
    String str = (String)paramArrayOfObject[2];
    float f1 = ((Float)paramArrayOfObject[3]).floatValue();
    float f2 = ((Float)paramArrayOfObject[4]).floatValue();
    long l1 = ((Long)paramArrayOfObject[5]).longValue();
    float f3 = ((Float)paramArrayOfObject[6]).floatValue();
    int j = ((Integer)paramArrayOfObject[7]).intValue();
    boolean bool = ((Boolean)paramArrayOfObject[8]).booleanValue();
    int i = ((Integer)paramArrayOfObject[9]).intValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x310922B4FB16L;
    (new Object[11])[10] = Integer.valueOf(i);
    new Object[11];
    E(new Object[] { 
          null, null, null, null, null, null, null, null, null, Long.valueOf(l2), 
          Integer.valueOf(0), Boolean.valueOf(bool), Integer.valueOf(j), Float.valueOf(f3), Float.valueOf(f2), Float.valueOf(f1), str, class_332, lx });
  }
  
  public void p(Object[] paramArrayOfObject) {
    class_332 class_332 = (class_332)paramArrayOfObject[0];
    String str = (String)paramArrayOfObject[1];
    float f2 = ((Float)paramArrayOfObject[2]).floatValue();
    float f1 = ((Float)paramArrayOfObject[3]).floatValue();
    float f3 = ((Float)paramArrayOfObject[4]).floatValue();
    long l1 = ((Long)paramArrayOfObject[5]).longValue();
    int i = ((Integer)paramArrayOfObject[6]).intValue();
    boolean bool = ((Boolean)paramArrayOfObject[7]).booleanValue();
    int j = ((Integer)paramArrayOfObject[8]).intValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x8D63DA168DL;
    (new Object[11])[10] = Integer.valueOf(j);
    new Object[11];
    E(new Object[] { 
          null, null, null, null, null, null, null, null, null, Long.valueOf(l2), 
          Integer.valueOf(0), Boolean.valueOf(bool), Integer.valueOf(i), Float.valueOf(f3), Float.valueOf(f1), Float.valueOf(f2), str, class_332, lx.REGULAR });
  }
  
  public void B(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast net/minecraft/class_332
    //   7: astore_2
    //   8: dup
    //   9: iconst_1
    //   10: aaload
    //   11: checkcast java/lang/String
    //   14: astore #7
    //   16: dup
    //   17: iconst_2
    //   18: aaload
    //   19: checkcast java/lang/Float
    //   22: invokevirtual floatValue : ()F
    //   25: fstore #10
    //   27: dup
    //   28: iconst_3
    //   29: aaload
    //   30: checkcast java/lang/Float
    //   33: invokevirtual floatValue : ()F
    //   36: fstore #4
    //   38: dup
    //   39: iconst_4
    //   40: aaload
    //   41: checkcast java/lang/Float
    //   44: invokevirtual floatValue : ()F
    //   47: fstore #8
    //   49: dup
    //   50: iconst_5
    //   51: aaload
    //   52: checkcast java/lang/Integer
    //   55: invokevirtual intValue : ()I
    //   58: istore_3
    //   59: dup
    //   60: bipush #6
    //   62: aaload
    //   63: checkcast java/lang/Boolean
    //   66: invokevirtual booleanValue : ()Z
    //   69: istore #9
    //   71: dup
    //   72: bipush #7
    //   74: aaload
    //   75: checkcast java/lang/Long
    //   78: invokevirtual longValue : ()J
    //   81: lstore #5
    //   83: pop
    //   84: getstatic wtf/opal/bu.a : J
    //   87: lload #5
    //   89: lxor
    //   90: lstore #5
    //   92: lload #5
    //   94: dup2
    //   95: ldc2_w 111641928199551
    //   98: lxor
    //   99: lstore #11
    //   101: pop2
    //   102: aload_0
    //   103: getstatic wtf/opal/lx.REGULAR : Lwtf/opal/lx;
    //   106: aload_2
    //   107: aload #7
    //   109: fload #10
    //   111: fload #4
    //   113: fload #8
    //   115: iload_3
    //   116: iload #9
    //   118: iconst_0
    //   119: lload #11
    //   121: sipush #15704
    //   124: ldc2_w 5470169859265554983
    //   127: lload #5
    //   129: lxor
    //   130: <illegal opcode> q : (IJ)I
    //   135: bipush #11
    //   137: anewarray java/lang/Object
    //   140: dup_x1
    //   141: swap
    //   142: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   145: bipush #10
    //   147: swap
    //   148: aastore
    //   149: dup_x2
    //   150: dup_x2
    //   151: pop
    //   152: invokestatic valueOf : (J)Ljava/lang/Long;
    //   155: bipush #9
    //   157: swap
    //   158: aastore
    //   159: dup_x1
    //   160: swap
    //   161: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   164: bipush #8
    //   166: swap
    //   167: aastore
    //   168: dup_x1
    //   169: swap
    //   170: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   173: bipush #7
    //   175: swap
    //   176: aastore
    //   177: dup_x1
    //   178: swap
    //   179: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   182: bipush #6
    //   184: swap
    //   185: aastore
    //   186: dup_x1
    //   187: swap
    //   188: invokestatic valueOf : (F)Ljava/lang/Float;
    //   191: iconst_5
    //   192: swap
    //   193: aastore
    //   194: dup_x1
    //   195: swap
    //   196: invokestatic valueOf : (F)Ljava/lang/Float;
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
    //   212: iconst_2
    //   213: swap
    //   214: aastore
    //   215: dup_x1
    //   216: swap
    //   217: iconst_1
    //   218: swap
    //   219: aastore
    //   220: dup_x1
    //   221: swap
    //   222: iconst_0
    //   223: swap
    //   224: aastore
    //   225: invokevirtual E : ([Ljava/lang/Object;)V
    //   228: return
  }
  
  public void R(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast net/minecraft/class_332
    //   7: astore #4
    //   9: dup
    //   10: iconst_1
    //   11: aaload
    //   12: checkcast java/lang/String
    //   15: astore_3
    //   16: dup
    //   17: iconst_2
    //   18: aaload
    //   19: checkcast java/lang/Long
    //   22: invokevirtual longValue : ()J
    //   25: lstore #6
    //   27: dup
    //   28: iconst_3
    //   29: aaload
    //   30: checkcast java/lang/Float
    //   33: invokevirtual floatValue : ()F
    //   36: fstore #8
    //   38: dup
    //   39: iconst_4
    //   40: aaload
    //   41: checkcast java/lang/Float
    //   44: invokevirtual floatValue : ()F
    //   47: fstore #9
    //   49: dup
    //   50: iconst_5
    //   51: aaload
    //   52: checkcast java/lang/Float
    //   55: invokevirtual floatValue : ()F
    //   58: fstore #5
    //   60: dup
    //   61: bipush #6
    //   63: aaload
    //   64: checkcast java/lang/Integer
    //   67: invokevirtual intValue : ()I
    //   70: istore_2
    //   71: pop
    //   72: getstatic wtf/opal/bu.a : J
    //   75: lload #6
    //   77: lxor
    //   78: lstore #6
    //   80: lload #6
    //   82: dup2
    //   83: ldc2_w 54718873906309
    //   86: lxor
    //   87: lstore #10
    //   89: pop2
    //   90: aload_0
    //   91: getstatic wtf/opal/lx.REGULAR : Lwtf/opal/lx;
    //   94: aload #4
    //   96: aload_3
    //   97: fload #8
    //   99: fload #9
    //   101: fload #5
    //   103: iload_2
    //   104: iconst_0
    //   105: iconst_0
    //   106: lload #10
    //   108: sipush #15704
    //   111: ldc2_w 5470086620354980829
    //   114: lload #6
    //   116: lxor
    //   117: <illegal opcode> q : (IJ)I
    //   122: bipush #11
    //   124: anewarray java/lang/Object
    //   127: dup_x1
    //   128: swap
    //   129: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   132: bipush #10
    //   134: swap
    //   135: aastore
    //   136: dup_x2
    //   137: dup_x2
    //   138: pop
    //   139: invokestatic valueOf : (J)Ljava/lang/Long;
    //   142: bipush #9
    //   144: swap
    //   145: aastore
    //   146: dup_x1
    //   147: swap
    //   148: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   151: bipush #8
    //   153: swap
    //   154: aastore
    //   155: dup_x1
    //   156: swap
    //   157: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   160: bipush #7
    //   162: swap
    //   163: aastore
    //   164: dup_x1
    //   165: swap
    //   166: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   169: bipush #6
    //   171: swap
    //   172: aastore
    //   173: dup_x1
    //   174: swap
    //   175: invokestatic valueOf : (F)Ljava/lang/Float;
    //   178: iconst_5
    //   179: swap
    //   180: aastore
    //   181: dup_x1
    //   182: swap
    //   183: invokestatic valueOf : (F)Ljava/lang/Float;
    //   186: iconst_4
    //   187: swap
    //   188: aastore
    //   189: dup_x1
    //   190: swap
    //   191: invokestatic valueOf : (F)Ljava/lang/Float;
    //   194: iconst_3
    //   195: swap
    //   196: aastore
    //   197: dup_x1
    //   198: swap
    //   199: iconst_2
    //   200: swap
    //   201: aastore
    //   202: dup_x1
    //   203: swap
    //   204: iconst_1
    //   205: swap
    //   206: aastore
    //   207: dup_x1
    //   208: swap
    //   209: iconst_0
    //   210: swap
    //   211: aastore
    //   212: invokevirtual E : ([Ljava/lang/Object;)V
    //   215: return
  }
  
  public void n(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast wtf/opal/lx
    //   7: astore #14
    //   9: dup
    //   10: iconst_1
    //   11: aaload
    //   12: checkcast net/minecraft/class_332
    //   15: astore #11
    //   17: dup
    //   18: iconst_2
    //   19: aaload
    //   20: checkcast java/lang/String
    //   23: astore #5
    //   25: dup
    //   26: iconst_3
    //   27: aaload
    //   28: checkcast java/lang/Float
    //   31: invokevirtual floatValue : ()F
    //   34: fstore_3
    //   35: dup
    //   36: iconst_4
    //   37: aaload
    //   38: checkcast java/lang/Float
    //   41: invokevirtual floatValue : ()F
    //   44: fstore #8
    //   46: dup
    //   47: iconst_5
    //   48: aaload
    //   49: checkcast java/lang/Float
    //   52: invokevirtual floatValue : ()F
    //   55: fstore #10
    //   57: dup
    //   58: bipush #6
    //   60: aaload
    //   61: checkcast java/lang/Integer
    //   64: invokevirtual intValue : ()I
    //   67: istore_2
    //   68: dup
    //   69: bipush #7
    //   71: aaload
    //   72: checkcast java/lang/Integer
    //   75: invokevirtual intValue : ()I
    //   78: istore #9
    //   80: dup
    //   81: bipush #8
    //   83: aaload
    //   84: checkcast java/lang/Boolean
    //   87: invokevirtual booleanValue : ()Z
    //   90: istore #6
    //   92: dup
    //   93: bipush #9
    //   95: aaload
    //   96: checkcast java/lang/Long
    //   99: invokevirtual longValue : ()J
    //   102: lstore #12
    //   104: dup
    //   105: bipush #10
    //   107: aaload
    //   108: checkcast java/lang/Integer
    //   111: invokevirtual intValue : ()I
    //   114: istore #4
    //   116: dup
    //   117: bipush #11
    //   119: aaload
    //   120: checkcast java/lang/Integer
    //   123: invokevirtual intValue : ()I
    //   126: istore #7
    //   128: pop
    //   129: getstatic wtf/opal/bu.a : J
    //   132: lload #12
    //   134: lxor
    //   135: lstore #12
    //   137: lload #12
    //   139: dup2
    //   140: ldc2_w 20429934141413
    //   143: lxor
    //   144: lstore #15
    //   146: dup2
    //   147: ldc2_w 107172380878243
    //   150: lxor
    //   151: lstore #17
    //   153: dup2
    //   154: ldc2_w 31123779998237
    //   157: lxor
    //   158: lstore #19
    //   160: dup2
    //   161: ldc2_w 5551454230796
    //   164: lxor
    //   165: lstore #21
    //   167: pop2
    //   168: invokestatic n : ()[Lwtf/opal/d;
    //   171: fconst_0
    //   172: fstore #24
    //   174: iconst_0
    //   175: istore #25
    //   177: astore #23
    //   179: iload #25
    //   181: aload #5
    //   183: invokevirtual length : ()I
    //   186: if_icmpge -> 505
    //   189: aload #5
    //   191: iload #25
    //   193: invokevirtual charAt : (I)C
    //   196: istore #26
    //   198: sipush #16318
    //   201: iload #26
    //   203: invokestatic valueOf : (C)Ljava/lang/String;
    //   206: astore #27
    //   208: ldc2_w 3047603234881314838
    //   211: lload #12
    //   213: lxor
    //   214: aload_0
    //   215: aload #14
    //   217: lload #19
    //   219: aload #27
    //   221: fload #10
    //   223: iload #7
    //   225: iconst_5
    //   226: anewarray java/lang/Object
    //   229: dup_x1
    //   230: swap
    //   231: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   234: iconst_4
    //   235: swap
    //   236: aastore
    //   237: dup_x1
    //   238: swap
    //   239: invokestatic valueOf : (F)Ljava/lang/Float;
    //   242: iconst_3
    //   243: swap
    //   244: aastore
    //   245: dup_x1
    //   246: swap
    //   247: iconst_2
    //   248: swap
    //   249: aastore
    //   250: dup_x2
    //   251: dup_x2
    //   252: pop
    //   253: invokestatic valueOf : (J)Ljava/lang/Long;
    //   256: iconst_1
    //   257: swap
    //   258: aastore
    //   259: dup_x1
    //   260: swap
    //   261: iconst_0
    //   262: swap
    //   263: aastore
    //   264: invokevirtual h : ([Ljava/lang/Object;)F
    //   267: fstore #28
    //   269: <illegal opcode> q : (IJ)I
    //   274: iload #25
    //   276: sipush #18958
    //   279: ldc2_w 4663812411182405028
    //   282: lload #12
    //   284: lxor
    //   285: <illegal opcode> q : (IJ)I
    //   290: imul
    //   291: lload #15
    //   293: iload_2
    //   294: iload #9
    //   296: iconst_5
    //   297: anewarray java/lang/Object
    //   300: dup_x1
    //   301: swap
    //   302: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   305: iconst_4
    //   306: swap
    //   307: aastore
    //   308: dup_x1
    //   309: swap
    //   310: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   313: iconst_3
    //   314: swap
    //   315: aastore
    //   316: dup_x2
    //   317: dup_x2
    //   318: pop
    //   319: invokestatic valueOf : (J)Ljava/lang/Long;
    //   322: iconst_2
    //   323: swap
    //   324: aastore
    //   325: dup_x1
    //   326: swap
    //   327: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   330: iconst_1
    //   331: swap
    //   332: aastore
    //   333: dup_x1
    //   334: swap
    //   335: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   338: iconst_0
    //   339: swap
    //   340: aastore
    //   341: invokestatic K : ([Ljava/lang/Object;)I
    //   344: lload #21
    //   346: iconst_2
    //   347: anewarray java/lang/Object
    //   350: dup_x2
    //   351: dup_x2
    //   352: pop
    //   353: invokestatic valueOf : (J)Ljava/lang/Long;
    //   356: iconst_1
    //   357: swap
    //   358: aastore
    //   359: dup_x1
    //   360: swap
    //   361: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   364: iconst_0
    //   365: swap
    //   366: aastore
    //   367: invokestatic S : ([Ljava/lang/Object;)I
    //   370: istore #29
    //   372: aload_0
    //   373: aload #14
    //   375: aload #11
    //   377: aload #27
    //   379: fload_3
    //   380: fload #24
    //   382: fadd
    //   383: fload #8
    //   385: fload #10
    //   387: iload #29
    //   389: iload #6
    //   391: iload #4
    //   393: lload #17
    //   395: iload #7
    //   397: bipush #11
    //   399: anewarray java/lang/Object
    //   402: dup_x1
    //   403: swap
    //   404: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   407: bipush #10
    //   409: swap
    //   410: aastore
    //   411: dup_x2
    //   412: dup_x2
    //   413: pop
    //   414: invokestatic valueOf : (J)Ljava/lang/Long;
    //   417: bipush #9
    //   419: swap
    //   420: aastore
    //   421: dup_x1
    //   422: swap
    //   423: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   426: bipush #8
    //   428: swap
    //   429: aastore
    //   430: dup_x1
    //   431: swap
    //   432: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   435: bipush #7
    //   437: swap
    //   438: aastore
    //   439: dup_x1
    //   440: swap
    //   441: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   444: bipush #6
    //   446: swap
    //   447: aastore
    //   448: dup_x1
    //   449: swap
    //   450: invokestatic valueOf : (F)Ljava/lang/Float;
    //   453: iconst_5
    //   454: swap
    //   455: aastore
    //   456: dup_x1
    //   457: swap
    //   458: invokestatic valueOf : (F)Ljava/lang/Float;
    //   461: iconst_4
    //   462: swap
    //   463: aastore
    //   464: dup_x1
    //   465: swap
    //   466: invokestatic valueOf : (F)Ljava/lang/Float;
    //   469: iconst_3
    //   470: swap
    //   471: aastore
    //   472: dup_x1
    //   473: swap
    //   474: iconst_2
    //   475: swap
    //   476: aastore
    //   477: dup_x1
    //   478: swap
    //   479: iconst_1
    //   480: swap
    //   481: aastore
    //   482: dup_x1
    //   483: swap
    //   484: iconst_0
    //   485: swap
    //   486: aastore
    //   487: invokevirtual E : ([Ljava/lang/Object;)V
    //   490: fload #24
    //   492: fload #28
    //   494: fadd
    //   495: fstore #24
    //   497: iinc #25, 1
    //   500: aload #23
    //   502: ifnonnull -> 179
    //   505: return
  }
  
  public float h(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast wtf/opal/lx
    //   7: astore #5
    //   9: dup
    //   10: iconst_1
    //   11: aaload
    //   12: checkcast java/lang/Long
    //   15: invokevirtual longValue : ()J
    //   18: lstore #6
    //   20: dup
    //   21: iconst_2
    //   22: aaload
    //   23: checkcast java/lang/String
    //   26: astore_3
    //   27: dup
    //   28: iconst_3
    //   29: aaload
    //   30: checkcast java/lang/Float
    //   33: invokevirtual floatValue : ()F
    //   36: fstore #4
    //   38: dup
    //   39: iconst_4
    //   40: aaload
    //   41: checkcast java/lang/Integer
    //   44: invokevirtual intValue : ()I
    //   47: istore_2
    //   48: pop
    //   49: getstatic wtf/opal/bu.a : J
    //   52: lload #6
    //   54: lxor
    //   55: lstore #6
    //   57: lload #6
    //   59: dup2
    //   60: ldc2_w 49837753641972
    //   63: lxor
    //   64: lstore #8
    //   66: dup2
    //   67: ldc2_w 40527576419853
    //   70: lxor
    //   71: lstore #10
    //   73: dup2
    //   74: ldc2_w 5888459171572
    //   77: lxor
    //   78: lstore #12
    //   80: dup2
    //   81: ldc2_w 108631420217387
    //   84: lxor
    //   85: lstore #14
    //   87: dup2
    //   88: ldc2_w 52543381527335
    //   91: lxor
    //   92: lstore #16
    //   94: pop2
    //   95: invokestatic n : ()[Lwtf/opal/d;
    //   98: iconst_0
    //   99: anewarray java/lang/Object
    //   102: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   105: iconst_0
    //   106: anewarray java/lang/Object
    //   109: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   112: ldc wtf/opal/jt
    //   114: iconst_1
    //   115: anewarray java/lang/Object
    //   118: dup_x1
    //   119: swap
    //   120: iconst_0
    //   121: swap
    //   122: aastore
    //   123: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   126: checkcast wtf/opal/jt
    //   129: astore #19
    //   131: astore #18
    //   133: aload #19
    //   135: lload #8
    //   137: iconst_1
    //   138: anewarray java/lang/Object
    //   141: dup_x2
    //   142: dup_x2
    //   143: pop
    //   144: invokestatic valueOf : (J)Ljava/lang/Long;
    //   147: iconst_0
    //   148: swap
    //   149: aastore
    //   150: invokevirtual S : ([Ljava/lang/Object;)Lwtf/opal/dv;
    //   153: astore #20
    //   155: aload #20
    //   157: aload #18
    //   159: ifnull -> 228
    //   162: iconst_0
    //   163: anewarray java/lang/Object
    //   166: invokevirtual e : ([Ljava/lang/Object;)Z
    //   169: ifeq -> 226
    //   172: goto -> 179
    //   175: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   178: athrow
    //   179: fload #4
    //   181: lload #6
    //   183: lconst_0
    //   184: lcmp
    //   185: ifle -> 224
    //   188: ldc 9.0
    //   190: aload #18
    //   192: ifnull -> 223
    //   195: goto -> 202
    //   198: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   201: athrow
    //   202: fcmpl
    //   203: iflt -> 226
    //   206: goto -> 213
    //   209: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   212: athrow
    //   213: fload #4
    //   215: fconst_1
    //   216: goto -> 223
    //   219: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   222: athrow
    //   223: fsub
    //   224: fstore #4
    //   226: aload #20
    //   228: getstatic wtf/opal/dv.MINECRAFT : Lwtf/opal/dv;
    //   231: if_acmpne -> 317
    //   234: aload #5
    //   236: getstatic wtf/opal/lx.REGULAR : Lwtf/opal/lx;
    //   239: if_acmpeq -> 299
    //   242: goto -> 249
    //   245: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   248: athrow
    //   249: aload_3
    //   250: ldc '§'
    //   252: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   255: aload #18
    //   257: ifnull -> 309
    //   260: goto -> 267
    //   263: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   266: athrow
    //   267: ifne -> 299
    //   270: goto -> 277
    //   273: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   276: athrow
    //   277: aload_3
    //   278: sipush #31530
    //   281: ldc2_w 7984173067371459800
    //   284: lload #6
    //   286: lxor
    //   287: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   292: swap
    //   293: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   298: astore_3
    //   299: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   302: getfield field_1772 : Lnet/minecraft/class_327;
    //   305: aload_3
    //   306: invokevirtual method_1727 : (Ljava/lang/String;)I
    //   309: i2f
    //   310: fload #4
    //   312: ldc 10.0
    //   314: fdiv
    //   315: fmul
    //   316: freturn
    //   317: new java/lang/StringBuilder
    //   320: dup
    //   321: invokespecial <init> : ()V
    //   324: astore #21
    //   326: fconst_0
    //   327: fstore #22
    //   329: iconst_0
    //   330: istore #23
    //   332: aload_0
    //   333: lload #16
    //   335: aload_3
    //   336: iconst_2
    //   337: anewarray java/lang/Object
    //   340: dup_x1
    //   341: swap
    //   342: iconst_1
    //   343: swap
    //   344: aastore
    //   345: dup_x2
    //   346: dup_x2
    //   347: pop
    //   348: invokestatic valueOf : (J)Ljava/lang/Long;
    //   351: iconst_0
    //   352: swap
    //   353: aastore
    //   354: invokevirtual l : ([Ljava/lang/Object;)Ljava/lang/String;
    //   357: astore_3
    //   358: iconst_0
    //   359: istore #24
    //   361: iload #24
    //   363: aload_3
    //   364: invokevirtual length : ()I
    //   367: if_icmpge -> 847
    //   370: aload_3
    //   371: iload #24
    //   373: invokevirtual charAt : (I)C
    //   376: istore #25
    //   378: iload #25
    //   380: aload #18
    //   382: lload #6
    //   384: lconst_0
    //   385: lcmp
    //   386: iflt -> 394
    //   389: ifnull -> 859
    //   392: aload #18
    //   394: lload #6
    //   396: lconst_0
    //   397: lcmp
    //   398: iflt -> 483
    //   401: ifnull -> 481
    //   404: goto -> 411
    //   407: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   410: athrow
    //   411: lload #6
    //   413: lconst_0
    //   414: lcmp
    //   415: iflt -> 474
    //   418: sipush #8348
    //   421: ldc2_w 1185184095875583769
    //   424: lload #6
    //   426: lxor
    //   427: <illegal opcode> q : (IJ)I
    //   432: if_icmpeq -> 469
    //   435: goto -> 442
    //   438: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   441: athrow
    //   442: aload #21
    //   444: iload #25
    //   446: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   449: pop
    //   450: aload #18
    //   452: lload #6
    //   454: lconst_0
    //   455: lcmp
    //   456: iflt -> 844
    //   459: ifnonnull -> 839
    //   462: goto -> 469
    //   465: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   468: athrow
    //   469: aload #21
    //   471: invokevirtual isEmpty : ()Z
    //   474: goto -> 481
    //   477: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   480: athrow
    //   481: aload #18
    //   483: lload #6
    //   485: lconst_0
    //   486: lcmp
    //   487: ifle -> 662
    //   490: ifnull -> 660
    //   493: ifne -> 658
    //   496: goto -> 503
    //   499: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   502: athrow
    //   503: iload #23
    //   505: ifeq -> 563
    //   508: goto -> 515
    //   511: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   514: athrow
    //   515: aload_0
    //   516: getfield X : Lwtf/opal/dc;
    //   519: sipush #18023
    //   522: ldc2_w 6733523487804524944
    //   525: lload #6
    //   527: lxor
    //   528: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   533: lload #14
    //   535: iconst_2
    //   536: anewarray java/lang/Object
    //   539: dup_x2
    //   540: dup_x2
    //   541: pop
    //   542: invokestatic valueOf : (J)Ljava/lang/Long;
    //   545: iconst_1
    //   546: swap
    //   547: aastore
    //   548: dup_x1
    //   549: swap
    //   550: iconst_0
    //   551: swap
    //   552: aastore
    //   553: invokevirtual W : ([Ljava/lang/Object;)Lwtf/opal/u2;
    //   556: goto -> 596
    //   559: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   562: athrow
    //   563: aload_0
    //   564: aload #20
    //   566: aload #5
    //   568: lload #12
    //   570: iconst_3
    //   571: anewarray java/lang/Object
    //   574: dup_x2
    //   575: dup_x2
    //   576: pop
    //   577: invokestatic valueOf : (J)Ljava/lang/Long;
    //   580: iconst_2
    //   581: swap
    //   582: aastore
    //   583: dup_x1
    //   584: swap
    //   585: iconst_1
    //   586: swap
    //   587: aastore
    //   588: dup_x1
    //   589: swap
    //   590: iconst_0
    //   591: swap
    //   592: aastore
    //   593: invokevirtual H : ([Ljava/lang/Object;)Lwtf/opal/u2;
    //   596: astore #26
    //   598: fload #22
    //   600: aload #26
    //   602: aload #21
    //   604: invokevirtual toString : ()Ljava/lang/String;
    //   607: lload #10
    //   609: fload #4
    //   611: iload_2
    //   612: iconst_4
    //   613: anewarray java/lang/Object
    //   616: dup_x1
    //   617: swap
    //   618: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   621: iconst_3
    //   622: swap
    //   623: aastore
    //   624: dup_x1
    //   625: swap
    //   626: invokestatic valueOf : (F)Ljava/lang/Float;
    //   629: iconst_2
    //   630: swap
    //   631: aastore
    //   632: dup_x2
    //   633: dup_x2
    //   634: pop
    //   635: invokestatic valueOf : (J)Ljava/lang/Long;
    //   638: iconst_1
    //   639: swap
    //   640: aastore
    //   641: dup_x1
    //   642: swap
    //   643: iconst_0
    //   644: swap
    //   645: aastore
    //   646: invokevirtual M : ([Ljava/lang/Object;)F
    //   649: fadd
    //   650: fstore #22
    //   652: aload #21
    //   654: iconst_0
    //   655: invokevirtual setLength : (I)V
    //   658: iload #24
    //   660: aload #18
    //   662: ifnull -> 702
    //   665: aload_3
    //   666: invokevirtual length : ()I
    //   669: iconst_1
    //   670: isub
    //   671: if_icmpne -> 705
    //   674: goto -> 681
    //   677: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   680: athrow
    //   681: sipush #15506
    //   684: ldc2_w 7939863249463890717
    //   687: lload #6
    //   689: lxor
    //   690: <illegal opcode> q : (IJ)I
    //   695: goto -> 702
    //   698: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   701: athrow
    //   702: goto -> 730
    //   705: sipush #2955
    //   708: ldc2_w 807149897285289085
    //   711: lload #6
    //   713: lxor
    //   714: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   719: aload_3
    //   720: iload #24
    //   722: iconst_1
    //   723: iadd
    //   724: invokevirtual charAt : (I)C
    //   727: invokevirtual indexOf : (I)I
    //   730: istore #26
    //   732: iload #26
    //   734: aload #18
    //   736: ifnull -> 834
    //   739: lookupswitch default -> 828, 17 -> 776, 21 -> 793, 22 -> 813
    //   772: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   775: athrow
    //   776: getstatic wtf/opal/lx.SEMI_BOLD : Lwtf/opal/lx;
    //   779: astore #5
    //   781: aload #18
    //   783: lload #6
    //   785: lconst_0
    //   786: lcmp
    //   787: iflt -> 803
    //   790: ifnonnull -> 836
    //   793: getstatic wtf/opal/lx.REGULAR : Lwtf/opal/lx;
    //   796: astore #5
    //   798: iconst_0
    //   799: istore #23
    //   801: aload #18
    //   803: lload #6
    //   805: lconst_0
    //   806: lcmp
    //   807: ifle -> 825
    //   810: ifnonnull -> 836
    //   813: iconst_1
    //   814: istore #23
    //   816: lload #6
    //   818: lconst_0
    //   819: lcmp
    //   820: ifle -> 833
    //   823: aload #18
    //   825: ifnonnull -> 836
    //   828: getstatic wtf/opal/lx.REGULAR : Lwtf/opal/lx;
    //   831: astore #5
    //   833: iconst_0
    //   834: istore #23
    //   836: iinc #24, 1
    //   839: iinc #24, 1
    //   842: aload #18
    //   844: ifnonnull -> 361
    //   847: lload #6
    //   849: lconst_0
    //   850: lcmp
    //   851: iflt -> 951
    //   854: aload #21
    //   856: invokevirtual isEmpty : ()Z
    //   859: ifne -> 951
    //   862: aload_0
    //   863: aload #20
    //   865: aload #5
    //   867: lload #12
    //   869: iconst_3
    //   870: anewarray java/lang/Object
    //   873: dup_x2
    //   874: dup_x2
    //   875: pop
    //   876: invokestatic valueOf : (J)Ljava/lang/Long;
    //   879: iconst_2
    //   880: swap
    //   881: aastore
    //   882: dup_x1
    //   883: swap
    //   884: iconst_1
    //   885: swap
    //   886: aastore
    //   887: dup_x1
    //   888: swap
    //   889: iconst_0
    //   890: swap
    //   891: aastore
    //   892: invokevirtual H : ([Ljava/lang/Object;)Lwtf/opal/u2;
    //   895: astore #24
    //   897: fload #22
    //   899: aload #24
    //   901: aload #21
    //   903: invokevirtual toString : ()Ljava/lang/String;
    //   906: lload #10
    //   908: fload #4
    //   910: iload_2
    //   911: iconst_4
    //   912: anewarray java/lang/Object
    //   915: dup_x1
    //   916: swap
    //   917: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   920: iconst_3
    //   921: swap
    //   922: aastore
    //   923: dup_x1
    //   924: swap
    //   925: invokestatic valueOf : (F)Ljava/lang/Float;
    //   928: iconst_2
    //   929: swap
    //   930: aastore
    //   931: dup_x2
    //   932: dup_x2
    //   933: pop
    //   934: invokestatic valueOf : (J)Ljava/lang/Long;
    //   937: iconst_1
    //   938: swap
    //   939: aastore
    //   940: dup_x1
    //   941: swap
    //   942: iconst_0
    //   943: swap
    //   944: aastore
    //   945: invokevirtual M : ([Ljava/lang/Object;)F
    //   948: fadd
    //   949: fstore #22
    //   951: fload #22
    //   953: freturn
    // Exception table:
    //   from	to	target	type
    //   155	172	175	wtf/opal/x5
    //   162	195	198	wtf/opal/x5
    //   179	206	209	wtf/opal/x5
    //   202	216	219	wtf/opal/x5
    //   228	242	245	wtf/opal/x5
    //   234	260	263	wtf/opal/x5
    //   249	270	273	wtf/opal/x5
    //   378	404	407	wtf/opal/x5
    //   392	435	438	wtf/opal/x5
    //   411	462	465	wtf/opal/x5
    //   442	474	477	wtf/opal/x5
    //   481	496	499	wtf/opal/x5
    //   493	508	511	wtf/opal/x5
    //   503	559	559	wtf/opal/x5
    //   660	674	677	wtf/opal/x5
    //   665	695	698	wtf/opal/x5
    //   732	772	772	wtf/opal/x5
  }
  
  public float p(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast wtf/opal/lx
    //   7: astore #4
    //   9: dup
    //   10: iconst_1
    //   11: aaload
    //   12: checkcast java/lang/String
    //   15: astore #5
    //   17: dup
    //   18: iconst_2
    //   19: aaload
    //   20: checkcast java/lang/Float
    //   23: invokevirtual floatValue : ()F
    //   26: fstore #6
    //   28: dup
    //   29: iconst_3
    //   30: aaload
    //   31: checkcast java/lang/Long
    //   34: invokevirtual longValue : ()J
    //   37: lstore_2
    //   38: pop
    //   39: getstatic wtf/opal/bu.a : J
    //   42: lload_2
    //   43: lxor
    //   44: lstore_2
    //   45: lload_2
    //   46: dup2
    //   47: ldc2_w 18444203156793
    //   50: lxor
    //   51: lstore #7
    //   53: pop2
    //   54: aload_0
    //   55: aload #4
    //   57: lload #7
    //   59: aload #5
    //   61: fload #6
    //   63: sipush #15704
    //   66: ldc2_w 5470179171410843103
    //   69: lload_2
    //   70: lxor
    //   71: <illegal opcode> q : (IJ)I
    //   76: iconst_5
    //   77: anewarray java/lang/Object
    //   80: dup_x1
    //   81: swap
    //   82: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   85: iconst_4
    //   86: swap
    //   87: aastore
    //   88: dup_x1
    //   89: swap
    //   90: invokestatic valueOf : (F)Ljava/lang/Float;
    //   93: iconst_3
    //   94: swap
    //   95: aastore
    //   96: dup_x1
    //   97: swap
    //   98: iconst_2
    //   99: swap
    //   100: aastore
    //   101: dup_x2
    //   102: dup_x2
    //   103: pop
    //   104: invokestatic valueOf : (J)Ljava/lang/Long;
    //   107: iconst_1
    //   108: swap
    //   109: aastore
    //   110: dup_x1
    //   111: swap
    //   112: iconst_0
    //   113: swap
    //   114: aastore
    //   115: invokevirtual h : ([Ljava/lang/Object;)F
    //   118: freturn
  }
  
  public float s(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/String
    //   7: astore_2
    //   8: dup
    //   9: iconst_1
    //   10: aaload
    //   11: checkcast java/lang/Float
    //   14: invokevirtual floatValue : ()F
    //   17: fstore #5
    //   19: dup
    //   20: iconst_2
    //   21: aaload
    //   22: checkcast java/lang/Long
    //   25: invokevirtual longValue : ()J
    //   28: lstore_3
    //   29: pop
    //   30: getstatic wtf/opal/bu.a : J
    //   33: lload_3
    //   34: lxor
    //   35: lstore_3
    //   36: lload_3
    //   37: dup2
    //   38: ldc2_w 104460371304497
    //   41: lxor
    //   42: lstore #6
    //   44: pop2
    //   45: aload_0
    //   46: getstatic wtf/opal/lx.REGULAR : Lwtf/opal/lx;
    //   49: lload #6
    //   51: aload_2
    //   52: fload #5
    //   54: sipush #15704
    //   57: ldc2_w 5470102502773969111
    //   60: lload_3
    //   61: lxor
    //   62: <illegal opcode> q : (IJ)I
    //   67: iconst_5
    //   68: anewarray java/lang/Object
    //   71: dup_x1
    //   72: swap
    //   73: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   76: iconst_4
    //   77: swap
    //   78: aastore
    //   79: dup_x1
    //   80: swap
    //   81: invokestatic valueOf : (F)Ljava/lang/Float;
    //   84: iconst_3
    //   85: swap
    //   86: aastore
    //   87: dup_x1
    //   88: swap
    //   89: iconst_2
    //   90: swap
    //   91: aastore
    //   92: dup_x2
    //   93: dup_x2
    //   94: pop
    //   95: invokestatic valueOf : (J)Ljava/lang/Long;
    //   98: iconst_1
    //   99: swap
    //   100: aastore
    //   101: dup_x1
    //   102: swap
    //   103: iconst_0
    //   104: swap
    //   105: aastore
    //   106: invokevirtual h : ([Ljava/lang/Object;)F
    //   109: freturn
  }
  
  public float e(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    lx lx = (lx)paramArrayOfObject[1];
    String str = (String)paramArrayOfObject[2];
    float f = ((Float)paramArrayOfObject[3]).floatValue();
    int i = ((Integer)paramArrayOfObject[4]).intValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x12F058EC0E5L;
    long l3 = l1 ^ 0x2927C7908DE5L;
    long l4 = l1 ^ 0x5C6913C8C9EDL;
    jt jt = (jt)d1.q(new Object[0]).x(new Object[0]).V(new Object[] { jt.class });
    new Object[1];
    dv dv = jt.S(new Object[] { Long.valueOf(l2) });
    d[] arrayOfD = n();
    try {
      if (arrayOfD != null) {
        try {
          if (dv == dv.MINECRAFT);
        } catch (x5 x5) {
          throw a(null);
        } 
      } else {
        return 9.0F * f / 10.0F;
      } 
    } catch (x5 x5) {
      throw a(null);
    } 
    new Object[3];
    (new Object[4])[3] = Integer.valueOf(i);
    new Object[4];
    return H(new Object[] { null, null, Long.valueOf(l3), lx, dv }).w(new Object[] { null, null, Long.valueOf(l4), Float.valueOf(f), str });
  }
  
  public float E(Object[] paramArrayOfObject) {
    String str = (String)paramArrayOfObject[0];
    float f = ((Float)paramArrayOfObject[1]).floatValue();
    long l1 = ((Long)paramArrayOfObject[2]).longValue();
    int i = ((Integer)paramArrayOfObject[3]).intValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x610A8349ADECL;
    (new Object[5])[4] = Integer.valueOf(i);
    (new Object[5])[3] = Float.valueOf(f);
    (new Object[5])[2] = str;
    (new Object[5])[1] = lx.REGULAR;
    new Object[5];
    return e(new Object[] { Long.valueOf(l2) });
  }
  
  public float A(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: dup
    //   12: iconst_1
    //   13: aaload
    //   14: checkcast java/lang/String
    //   17: astore #5
    //   19: dup
    //   20: iconst_2
    //   21: aaload
    //   22: checkcast java/lang/Float
    //   25: invokevirtual floatValue : ()F
    //   28: fstore #4
    //   30: pop
    //   31: getstatic wtf/opal/bu.a : J
    //   34: lload_2
    //   35: lxor
    //   36: lstore_2
    //   37: lload_2
    //   38: dup2
    //   39: ldc2_w 14244419517318
    //   42: lxor
    //   43: lstore #6
    //   45: pop2
    //   46: aload_0
    //   47: lload #6
    //   49: getstatic wtf/opal/lx.REGULAR : Lwtf/opal/lx;
    //   52: aload #5
    //   54: fload #4
    //   56: sipush #15704
    //   59: ldc2_w 5470126111021473905
    //   62: lload_2
    //   63: lxor
    //   64: <illegal opcode> q : (IJ)I
    //   69: iconst_5
    //   70: anewarray java/lang/Object
    //   73: dup_x1
    //   74: swap
    //   75: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   78: iconst_4
    //   79: swap
    //   80: aastore
    //   81: dup_x1
    //   82: swap
    //   83: invokestatic valueOf : (F)Ljava/lang/Float;
    //   86: iconst_3
    //   87: swap
    //   88: aastore
    //   89: dup_x1
    //   90: swap
    //   91: iconst_2
    //   92: swap
    //   93: aastore
    //   94: dup_x1
    //   95: swap
    //   96: iconst_1
    //   97: swap
    //   98: aastore
    //   99: dup_x2
    //   100: dup_x2
    //   101: pop
    //   102: invokestatic valueOf : (J)Ljava/lang/Long;
    //   105: iconst_0
    //   106: swap
    //   107: aastore
    //   108: invokevirtual e : ([Ljava/lang/Object;)F
    //   111: freturn
  }
  
  public float j(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Float
    //   7: invokevirtual floatValue : ()F
    //   10: fstore_2
    //   11: dup
    //   12: iconst_1
    //   13: aaload
    //   14: checkcast java/lang/Long
    //   17: invokevirtual longValue : ()J
    //   20: lstore_3
    //   21: pop
    //   22: getstatic wtf/opal/bu.a : J
    //   25: lload_3
    //   26: lxor
    //   27: lstore_3
    //   28: lload_3
    //   29: dup2
    //   30: ldc2_w 74536947941212
    //   33: lxor
    //   34: lstore #5
    //   36: pop2
    //   37: aload_0
    //   38: lload #5
    //   40: getstatic wtf/opal/lx.REGULAR : Lwtf/opal/lx;
    //   43: ldc_w ''
    //   46: fload_2
    //   47: sipush #15704
    //   50: ldc2_w 5470050048993354923
    //   53: lload_3
    //   54: lxor
    //   55: <illegal opcode> q : (IJ)I
    //   60: iconst_5
    //   61: anewarray java/lang/Object
    //   64: dup_x1
    //   65: swap
    //   66: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   69: iconst_4
    //   70: swap
    //   71: aastore
    //   72: dup_x1
    //   73: swap
    //   74: invokestatic valueOf : (F)Ljava/lang/Float;
    //   77: iconst_3
    //   78: swap
    //   79: aastore
    //   80: dup_x1
    //   81: swap
    //   82: iconst_2
    //   83: swap
    //   84: aastore
    //   85: dup_x1
    //   86: swap
    //   87: iconst_1
    //   88: swap
    //   89: aastore
    //   90: dup_x2
    //   91: dup_x2
    //   92: pop
    //   93: invokestatic valueOf : (J)Ljava/lang/Long;
    //   96: iconst_0
    //   97: swap
    //   98: aastore
    //   99: invokevirtual e : ([Ljava/lang/Object;)F
    //   102: freturn
  }
  
  private u2 H(Object[] paramArrayOfObject) {
    dv dv = (dv)paramArrayOfObject[0];
    lx lx = (lx)paramArrayOfObject[1];
    long l1 = ((Long)paramArrayOfObject[2]).longValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x1583E4516CEAL;
    new Object[2];
    return this.X.W(new Object[] { null, Long.valueOf(l2), dv.toString().replace(" ", "") + "-" + dv.toString().replace(" ", "") });
  }
  
  private String l(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_3
    //   11: dup
    //   12: iconst_1
    //   13: aaload
    //   14: checkcast java/lang/String
    //   17: astore_2
    //   18: pop
    //   19: getstatic wtf/opal/bu.a : J
    //   22: lload_3
    //   23: lxor
    //   24: lstore_3
    //   25: invokestatic n : ()[Lwtf/opal/d;
    //   28: new java/lang/StringBuilder
    //   31: dup
    //   32: invokespecial <init> : ()V
    //   35: astore #6
    //   37: astore #5
    //   39: iconst_0
    //   40: istore #7
    //   42: iconst_0
    //   43: istore #8
    //   45: iload #8
    //   47: aload_2
    //   48: invokevirtual length : ()I
    //   51: if_icmpge -> 369
    //   54: aload_2
    //   55: aload #5
    //   57: ifnull -> 374
    //   60: iload #8
    //   62: invokevirtual charAt : (I)C
    //   65: istore #9
    //   67: iload #7
    //   69: aload #5
    //   71: ifnull -> 221
    //   74: ifne -> 212
    //   77: goto -> 84
    //   80: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   83: athrow
    //   84: iload #9
    //   86: aload #5
    //   88: ifnull -> 221
    //   91: goto -> 98
    //   94: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   97: athrow
    //   98: lload_3
    //   99: lconst_0
    //   100: lcmp
    //   101: ifle -> 214
    //   104: sipush #28093
    //   107: ldc2_w 8690518220264241953
    //   110: lload_3
    //   111: lxor
    //   112: <illegal opcode> q : (IJ)I
    //   117: if_icmpne -> 212
    //   120: goto -> 127
    //   123: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   126: athrow
    //   127: aload_2
    //   128: iload #8
    //   130: iconst_1
    //   131: iadd
    //   132: invokevirtual charAt : (I)C
    //   135: aload #5
    //   137: lload_3
    //   138: lconst_0
    //   139: lcmp
    //   140: ifle -> 229
    //   143: ifnull -> 221
    //   146: goto -> 153
    //   149: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   152: athrow
    //   153: sipush #20014
    //   156: ldc2_w 2024924399838582968
    //   159: lload_3
    //   160: lxor
    //   161: <illegal opcode> q : (IJ)I
    //   166: if_icmpne -> 212
    //   169: goto -> 176
    //   172: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   175: athrow
    //   176: aload #6
    //   178: sipush #878
    //   181: ldc2_w 610566426288338313
    //   184: lload_3
    //   185: lxor
    //   186: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   191: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   194: pop
    //   195: iconst_1
    //   196: istore #7
    //   198: iinc #8, 1
    //   201: aload #5
    //   203: lload_3
    //   204: lconst_0
    //   205: lcmp
    //   206: ifle -> 366
    //   209: ifnonnull -> 361
    //   212: iload #7
    //   214: goto -> 221
    //   217: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   220: athrow
    //   221: lload_3
    //   222: lconst_0
    //   223: lcmp
    //   224: ifle -> 244
    //   227: aload #5
    //   229: ifnull -> 244
    //   232: ifne -> 346
    //   235: goto -> 242
    //   238: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   241: athrow
    //   242: iload #9
    //   244: sipush #28093
    //   247: ldc2_w 8690518220264241953
    //   250: lload_3
    //   251: lxor
    //   252: <illegal opcode> q : (IJ)I
    //   257: aload #5
    //   259: lload_3
    //   260: lconst_0
    //   261: lcmp
    //   262: ifle -> 308
    //   265: ifnull -> 300
    //   268: if_icmpeq -> 346
    //   271: goto -> 278
    //   274: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   277: athrow
    //   278: iload #9
    //   280: sipush #4901
    //   283: ldc2_w 5235979481684018622
    //   286: lload_3
    //   287: lxor
    //   288: <illegal opcode> q : (IJ)I
    //   293: goto -> 300
    //   296: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   299: athrow
    //   300: lload_3
    //   301: lconst_0
    //   302: lcmp
    //   303: iflt -> 343
    //   306: aload #5
    //   308: ifnull -> 343
    //   311: if_icmplt -> 361
    //   314: goto -> 321
    //   317: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   320: athrow
    //   321: iload #9
    //   323: sipush #2791
    //   326: ldc2_w 3243876799130639463
    //   329: lload_3
    //   330: lxor
    //   331: <illegal opcode> q : (IJ)I
    //   336: goto -> 343
    //   339: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   342: athrow
    //   343: if_icmpgt -> 361
    //   346: aload #6
    //   348: iload #9
    //   350: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   353: pop
    //   354: goto -> 361
    //   357: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   360: athrow
    //   361: iinc #8, 1
    //   364: aload #5
    //   366: ifnonnull -> 45
    //   369: aload #6
    //   371: invokevirtual toString : ()Ljava/lang/String;
    //   374: areturn
    // Exception table:
    //   from	to	target	type
    //   67	77	80	wtf/opal/x5
    //   74	91	94	wtf/opal/x5
    //   84	120	123	wtf/opal/x5
    //   98	146	149	wtf/opal/x5
    //   127	169	172	wtf/opal/x5
    //   198	214	217	wtf/opal/x5
    //   221	235	238	wtf/opal/x5
    //   244	271	274	wtf/opal/x5
    //   268	293	296	wtf/opal/x5
    //   300	314	317	wtf/opal/x5
    //   311	336	339	wtf/opal/x5
    //   343	354	357	wtf/opal/x5
  }
  
  static {
    Y(new d[1]);
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[8];
    boolean bool = false;
    String str;
    int i = (str = "¶Kîª5sÉøz\037¾Þa¾\020ÔM/B¶>£Eq^HU\036(É­ë=ya[Gj\005±«\be}\023Á@ésfºÞJ\\u¸m\036\013%8 óïÀRç{BNc\036åorßèò6tA$$¬:¨Ñ#ÕK(&üzâ aÈ×\\;l»ì¸t\000aÐi0G±¸9±q\024hük)\025Ð­bZQ(!ãpT<ªd$\003\002\024û ÅcÁt\n\037ø²W\004s¿Ø5>Ç&ÐòJ").length();
    byte b2 = 16;
    byte b = -1;
    while (true);
  }
  
  public static void Y(d[] paramArrayOfd) {
    r = paramArrayOfd;
  }
  
  public static d[] n() {
    return r;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x127D;
    if (c[i] == null) {
      Object[] arrayOfObject;
      try {
        Long long_ = Long.valueOf(Thread.currentThread().threadId());
        arrayOfObject = (Object[])d.get(long_);
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/PKCS5Padding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          d.put(long_, arrayOfObject);
        } 
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/bu", exception);
      } 
      byte[] arrayOfByte1 = new byte[8];
      arrayOfByte1[0] = (byte)(int)(paramLong >>> 56L);
      for (byte b = 1; b < 8; b++)
        arrayOfByte1[b] = (byte)(int)(paramLong << b * 8 >>> 56L); 
      DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
      SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
      ((Cipher)arrayOfObject[0]).init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
      byte[] arrayOfByte2 = b[i].getBytes("ISO-8859-1");
      c[i] = a(((Cipher)arrayOfObject[0]).doFinal(arrayOfByte2));
    } 
    return c[i];
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
    //   65: ldc_w 'wtf/opal/bu'
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
  
  private static int b(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x320B;
    if (f[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l = e[i];
      byte[] arrayOfByte2 = { (byte)(int)(l >>> 56L), (byte)(int)(l >>> 48L), (byte)(int)(l >>> 40L), (byte)(int)(l >>> 32L), (byte)(int)(l >>> 24L), (byte)(int)(l >>> 16L), (byte)(int)(l >>> 8L), (byte)(int)l };
      Long long_ = Long.valueOf(Thread.currentThread().threadId());
      Object[] arrayOfObject = (Object[])g.get(long_);
      try {
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/NoPadding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          g.put(long_, arrayOfObject);
        } 
        DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
        SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
        Cipher cipher = (Cipher)arrayOfObject[0];
        cipher.init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
        arrayOfByte3 = cipher.doFinal(arrayOfByte2);
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/bu", exception);
      } 
      int j = (arrayOfByte3[4] & 0xFF) << 24 | (arrayOfByte3[5] & 0xFF) << 16 | (arrayOfByte3[6] & 0xFF) << 8 | arrayOfByte3[7] & 0xFF;
      f[i] = Integer.valueOf(j);
    } 
    return f[i].intValue();
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
    //   65: ldc_w 'wtf/opal/bu'
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
  
  static {
    long l = a ^ 0x24CA9F2902DEL;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\bu.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */