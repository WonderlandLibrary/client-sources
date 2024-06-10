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

public final class b4 extends b5<kg> {
  private boolean G;
  
  private boolean I;
  
  private float Y;
  
  private float l;
  
  private float p;
  
  private final da H;
  
  private final da q;
  
  private final da X;
  
  private static final long b;
  
  private static final long[] c;
  
  private static final Integer[] d;
  
  private static final Map f;
  
  public b4(kg paramkg, long paramLong) {
    super(paramkg, l2);
    this.H = new da(l1);
    this.q = new da(l1);
    this.X = new da(l1);
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
    //   18: istore #6
    //   20: dup
    //   21: iconst_2
    //   22: aaload
    //   23: checkcast java/lang/Integer
    //   26: invokevirtual intValue : ()I
    //   29: istore #5
    //   31: dup
    //   32: iconst_3
    //   33: aaload
    //   34: checkcast java/lang/Long
    //   37: invokevirtual longValue : ()J
    //   40: lstore_3
    //   41: dup
    //   42: iconst_4
    //   43: aaload
    //   44: checkcast java/lang/Float
    //   47: invokevirtual floatValue : ()F
    //   50: fstore_2
    //   51: pop
    //   52: lload_3
    //   53: dup2
    //   54: ldc2_w 121478191638676
    //   57: lxor
    //   58: lstore #8
    //   60: dup2
    //   61: ldc2_w 60545063372
    //   64: lxor
    //   65: lstore #10
    //   67: dup2
    //   68: ldc2_w 10802718197547
    //   71: lxor
    //   72: lstore #12
    //   74: dup2
    //   75: ldc2_w 138087081025016
    //   78: lxor
    //   79: lstore #14
    //   81: dup2
    //   82: ldc2_w 94556900610908
    //   85: lxor
    //   86: lstore #16
    //   88: dup2
    //   89: ldc2_w 66468241307817
    //   92: lxor
    //   93: lstore #18
    //   95: dup2
    //   96: ldc2_w 86228387855835
    //   99: lxor
    //   100: lstore #20
    //   102: dup2
    //   103: ldc2_w 40314075635121
    //   106: lxor
    //   107: lstore #22
    //   109: dup2
    //   110: ldc2_w 35241617684692
    //   113: lxor
    //   114: lstore #24
    //   116: dup2
    //   117: ldc2_w 55100416438967
    //   120: lxor
    //   121: lstore #26
    //   123: pop2
    //   124: invokestatic S : ()[Lwtf/opal/d;
    //   127: getstatic wtf/opal/b4.h : Lwtf/opal/pa;
    //   130: aload_0
    //   131: getfield U : F
    //   134: aload_0
    //   135: getfield t : F
    //   138: aload_0
    //   139: getfield e : F
    //   142: aload_0
    //   143: getfield m : F
    //   146: sipush #726
    //   149: ldc2_w 476568022969211735
    //   152: lload_3
    //   153: lxor
    //   154: <illegal opcode> p : (IJ)I
    //   159: lload #18
    //   161: bipush #6
    //   163: anewarray java/lang/Object
    //   166: dup_x2
    //   167: dup_x2
    //   168: pop
    //   169: invokestatic valueOf : (J)Ljava/lang/Long;
    //   172: iconst_5
    //   173: swap
    //   174: aastore
    //   175: dup_x1
    //   176: swap
    //   177: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   180: iconst_4
    //   181: swap
    //   182: aastore
    //   183: dup_x1
    //   184: swap
    //   185: invokestatic valueOf : (F)Ljava/lang/Float;
    //   188: iconst_3
    //   189: swap
    //   190: aastore
    //   191: dup_x1
    //   192: swap
    //   193: invokestatic valueOf : (F)Ljava/lang/Float;
    //   196: iconst_2
    //   197: swap
    //   198: aastore
    //   199: dup_x1
    //   200: swap
    //   201: invokestatic valueOf : (F)Ljava/lang/Float;
    //   204: iconst_1
    //   205: swap
    //   206: aastore
    //   207: dup_x1
    //   208: swap
    //   209: invokestatic valueOf : (F)Ljava/lang/Float;
    //   212: iconst_0
    //   213: swap
    //   214: aastore
    //   215: invokevirtual k : ([Ljava/lang/Object;)V
    //   218: astore #28
    //   220: aload_0
    //   221: ldc 73.0
    //   223: iconst_1
    //   224: anewarray java/lang/Object
    //   227: dup_x1
    //   228: swap
    //   229: invokestatic valueOf : (F)Ljava/lang/Float;
    //   232: iconst_0
    //   233: swap
    //   234: aastore
    //   235: invokevirtual q : ([Ljava/lang/Object;)V
    //   238: aload_0
    //   239: getfield m : F
    //   242: ldc 30.0
    //   244: fsub
    //   245: fstore #29
    //   247: aload_0
    //   248: getfield e : F
    //   251: ldc 9.0
    //   253: fsub
    //   254: fstore #30
    //   256: aload_0
    //   257: getfield U : F
    //   260: ldc 4.5
    //   262: fadd
    //   263: fstore #31
    //   265: aload_0
    //   266: getfield t : F
    //   269: ldc 15.0
    //   271: fadd
    //   272: fstore #32
    //   274: getstatic wtf/opal/b4.Z : Lwtf/opal/bu;
    //   277: aload #7
    //   279: aload_0
    //   280: iconst_0
    //   281: anewarray java/lang/Object
    //   284: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   287: checkcast wtf/opal/kg
    //   290: iconst_0
    //   291: anewarray java/lang/Object
    //   294: invokevirtual r : ([Ljava/lang/Object;)Ljava/lang/String;
    //   297: fload #31
    //   299: fload #30
    //   301: fconst_2
    //   302: fdiv
    //   303: fadd
    //   304: getstatic wtf/opal/b4.Z : Lwtf/opal/bu;
    //   307: aload_0
    //   308: iconst_0
    //   309: anewarray java/lang/Object
    //   312: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   315: checkcast wtf/opal/kg
    //   318: iconst_0
    //   319: anewarray java/lang/Object
    //   322: invokevirtual r : ([Ljava/lang/Object;)Ljava/lang/String;
    //   325: ldc 8.0
    //   327: lload #12
    //   329: iconst_3
    //   330: anewarray java/lang/Object
    //   333: dup_x2
    //   334: dup_x2
    //   335: pop
    //   336: invokestatic valueOf : (J)Ljava/lang/Long;
    //   339: iconst_2
    //   340: swap
    //   341: aastore
    //   342: dup_x1
    //   343: swap
    //   344: invokestatic valueOf : (F)Ljava/lang/Float;
    //   347: iconst_1
    //   348: swap
    //   349: aastore
    //   350: dup_x1
    //   351: swap
    //   352: iconst_0
    //   353: swap
    //   354: aastore
    //   355: invokevirtual s : ([Ljava/lang/Object;)F
    //   358: fconst_2
    //   359: fdiv
    //   360: fsub
    //   361: aload_0
    //   362: getfield t : F
    //   365: ldc 3.5
    //   367: fadd
    //   368: ldc 8.0
    //   370: iconst_m1
    //   371: iconst_0
    //   372: lload #20
    //   374: bipush #8
    //   376: anewarray java/lang/Object
    //   379: dup_x2
    //   380: dup_x2
    //   381: pop
    //   382: invokestatic valueOf : (J)Ljava/lang/Long;
    //   385: bipush #7
    //   387: swap
    //   388: aastore
    //   389: dup_x1
    //   390: swap
    //   391: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   394: bipush #6
    //   396: swap
    //   397: aastore
    //   398: dup_x1
    //   399: swap
    //   400: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   403: iconst_5
    //   404: swap
    //   405: aastore
    //   406: dup_x1
    //   407: swap
    //   408: invokestatic valueOf : (F)Ljava/lang/Float;
    //   411: iconst_4
    //   412: swap
    //   413: aastore
    //   414: dup_x1
    //   415: swap
    //   416: invokestatic valueOf : (F)Ljava/lang/Float;
    //   419: iconst_3
    //   420: swap
    //   421: aastore
    //   422: dup_x1
    //   423: swap
    //   424: invokestatic valueOf : (F)Ljava/lang/Float;
    //   427: iconst_2
    //   428: swap
    //   429: aastore
    //   430: dup_x1
    //   431: swap
    //   432: iconst_1
    //   433: swap
    //   434: aastore
    //   435: dup_x1
    //   436: swap
    //   437: iconst_0
    //   438: swap
    //   439: aastore
    //   440: invokevirtual B : ([Ljava/lang/Object;)V
    //   443: iconst_3
    //   444: newarray float
    //   446: astore #33
    //   448: new java/awt/Color
    //   451: dup
    //   452: aload_0
    //   453: iconst_0
    //   454: anewarray java/lang/Object
    //   457: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   460: checkcast wtf/opal/kg
    //   463: invokevirtual z : ()Ljava/lang/Object;
    //   466: checkcast java/lang/Integer
    //   469: invokevirtual intValue : ()I
    //   472: invokespecial <init> : (I)V
    //   475: astore #34
    //   477: aload #34
    //   479: invokevirtual getRed : ()I
    //   482: aload #34
    //   484: invokevirtual getGreen : ()I
    //   487: aload #34
    //   489: invokevirtual getBlue : ()I
    //   492: aload #33
    //   494: invokestatic RGBtoHSB : (III[F)[F
    //   497: astore #33
    //   499: fconst_1
    //   500: fconst_0
    //   501: iload #6
    //   503: i2f
    //   504: fload #31
    //   506: fsub
    //   507: fload #30
    //   509: fdiv
    //   510: invokestatic max : (FF)F
    //   513: invokestatic min : (FF)F
    //   516: fstore #35
    //   518: aload_0
    //   519: getfield I : Z
    //   522: aload #28
    //   524: ifnull -> 703
    //   527: ifeq -> 699
    //   530: goto -> 537
    //   533: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   536: athrow
    //   537: aload #33
    //   539: iconst_0
    //   540: fload #35
    //   542: fastore
    //   543: aload_0
    //   544: iload #6
    //   546: i2f
    //   547: putfield p : F
    //   550: lload #8
    //   552: fload #31
    //   554: fload #32
    //   556: fload #29
    //   558: fadd
    //   559: ldc 4.5
    //   561: fadd
    //   562: fload #30
    //   564: ldc 6.0
    //   566: iload #6
    //   568: i2d
    //   569: iload #5
    //   571: i2d
    //   572: bipush #7
    //   574: anewarray java/lang/Object
    //   577: dup_x2
    //   578: dup_x2
    //   579: pop
    //   580: invokestatic valueOf : (D)Ljava/lang/Double;
    //   583: bipush #6
    //   585: swap
    //   586: aastore
    //   587: dup_x2
    //   588: dup_x2
    //   589: pop
    //   590: invokestatic valueOf : (D)Ljava/lang/Double;
    //   593: iconst_5
    //   594: swap
    //   595: aastore
    //   596: dup_x1
    //   597: swap
    //   598: invokestatic valueOf : (F)Ljava/lang/Float;
    //   601: iconst_4
    //   602: swap
    //   603: aastore
    //   604: dup_x1
    //   605: swap
    //   606: invokestatic valueOf : (F)Ljava/lang/Float;
    //   609: iconst_3
    //   610: swap
    //   611: aastore
    //   612: dup_x1
    //   613: swap
    //   614: invokestatic valueOf : (F)Ljava/lang/Float;
    //   617: iconst_2
    //   618: swap
    //   619: aastore
    //   620: dup_x1
    //   621: swap
    //   622: invokestatic valueOf : (F)Ljava/lang/Float;
    //   625: iconst_1
    //   626: swap
    //   627: aastore
    //   628: dup_x2
    //   629: dup_x2
    //   630: pop
    //   631: invokestatic valueOf : (J)Ljava/lang/Long;
    //   634: iconst_0
    //   635: swap
    //   636: aastore
    //   637: invokestatic Z : ([Ljava/lang/Object;)Z
    //   640: aload #28
    //   642: ifnull -> 1613
    //   645: goto -> 652
    //   648: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   651: athrow
    //   652: ifne -> 885
    //   655: goto -> 662
    //   658: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   661: athrow
    //   662: aload_0
    //   663: iconst_0
    //   664: putfield I : Z
    //   667: lload_3
    //   668: lconst_0
    //   669: lcmp
    //   670: iflt -> 1595
    //   673: aload #28
    //   675: ifnonnull -> 885
    //   678: goto -> 685
    //   681: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   684: athrow
    //   685: iconst_4
    //   686: anewarray wtf/opal/d
    //   689: invokestatic p : ([Lwtf/opal/d;)V
    //   692: goto -> 699
    //   695: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   698: athrow
    //   699: aload_0
    //   700: getfield G : Z
    //   703: aload #28
    //   705: ifnull -> 1613
    //   708: ifeq -> 885
    //   711: goto -> 718
    //   714: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   717: athrow
    //   718: aload #33
    //   720: iconst_2
    //   721: fconst_1
    //   722: fconst_0
    //   723: fconst_1
    //   724: iload #5
    //   726: i2f
    //   727: fload #32
    //   729: fsub
    //   730: fload #29
    //   732: fdiv
    //   733: fsub
    //   734: invokestatic max : (FF)F
    //   737: invokestatic min : (FF)F
    //   740: fastore
    //   741: aload #33
    //   743: iconst_1
    //   744: fload #35
    //   746: fastore
    //   747: aload_0
    //   748: iload #6
    //   750: i2f
    //   751: putfield Y : F
    //   754: aload_0
    //   755: iload #5
    //   757: i2f
    //   758: putfield l : F
    //   761: lload #8
    //   763: fload #31
    //   765: fload #32
    //   767: fload #30
    //   769: fload #29
    //   771: iload #6
    //   773: i2d
    //   774: iload #5
    //   776: i2d
    //   777: bipush #7
    //   779: anewarray java/lang/Object
    //   782: dup_x2
    //   783: dup_x2
    //   784: pop
    //   785: invokestatic valueOf : (D)Ljava/lang/Double;
    //   788: bipush #6
    //   790: swap
    //   791: aastore
    //   792: dup_x2
    //   793: dup_x2
    //   794: pop
    //   795: invokestatic valueOf : (D)Ljava/lang/Double;
    //   798: iconst_5
    //   799: swap
    //   800: aastore
    //   801: dup_x1
    //   802: swap
    //   803: invokestatic valueOf : (F)Ljava/lang/Float;
    //   806: iconst_4
    //   807: swap
    //   808: aastore
    //   809: dup_x1
    //   810: swap
    //   811: invokestatic valueOf : (F)Ljava/lang/Float;
    //   814: iconst_3
    //   815: swap
    //   816: aastore
    //   817: dup_x1
    //   818: swap
    //   819: invokestatic valueOf : (F)Ljava/lang/Float;
    //   822: iconst_2
    //   823: swap
    //   824: aastore
    //   825: dup_x1
    //   826: swap
    //   827: invokestatic valueOf : (F)Ljava/lang/Float;
    //   830: iconst_1
    //   831: swap
    //   832: aastore
    //   833: dup_x2
    //   834: dup_x2
    //   835: pop
    //   836: invokestatic valueOf : (J)Ljava/lang/Long;
    //   839: iconst_0
    //   840: swap
    //   841: aastore
    //   842: invokestatic Z : ([Ljava/lang/Object;)Z
    //   845: lload_3
    //   846: lconst_0
    //   847: lcmp
    //   848: ifle -> 1613
    //   851: aload #28
    //   853: ifnull -> 1613
    //   856: goto -> 863
    //   859: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   862: athrow
    //   863: ifne -> 885
    //   866: goto -> 873
    //   869: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   872: athrow
    //   873: aload_0
    //   874: iconst_0
    //   875: putfield G : Z
    //   878: goto -> 885
    //   881: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   884: athrow
    //   885: aload_0
    //   886: getfield X : Lwtf/opal/da;
    //   889: aload_0
    //   890: getfield p : F
    //   893: fload #31
    //   895: invokestatic max : (FF)F
    //   898: fload #31
    //   900: fload #30
    //   902: fadd
    //   903: invokestatic min : (FF)F
    //   906: lload #10
    //   908: sipush #31892
    //   911: ldc2_w 4886263485704250646
    //   914: lload_3
    //   915: lxor
    //   916: <illegal opcode> p : (IJ)I
    //   921: iconst_3
    //   922: anewarray java/lang/Object
    //   925: dup_x1
    //   926: swap
    //   927: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   930: iconst_2
    //   931: swap
    //   932: aastore
    //   933: dup_x2
    //   934: dup_x2
    //   935: pop
    //   936: invokestatic valueOf : (J)Ljava/lang/Long;
    //   939: iconst_1
    //   940: swap
    //   941: aastore
    //   942: dup_x1
    //   943: swap
    //   944: invokestatic valueOf : (F)Ljava/lang/Float;
    //   947: iconst_0
    //   948: swap
    //   949: aastore
    //   950: invokevirtual e : ([Ljava/lang/Object;)V
    //   953: aload_0
    //   954: getfield H : Lwtf/opal/da;
    //   957: aload_0
    //   958: getfield Y : F
    //   961: fload #31
    //   963: invokestatic max : (FF)F
    //   966: fload #31
    //   968: fload #30
    //   970: fadd
    //   971: invokestatic min : (FF)F
    //   974: lload #10
    //   976: sipush #26455
    //   979: ldc2_w 2513916582469025492
    //   982: lload_3
    //   983: lxor
    //   984: <illegal opcode> p : (IJ)I
    //   989: iconst_3
    //   990: anewarray java/lang/Object
    //   993: dup_x1
    //   994: swap
    //   995: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   998: iconst_2
    //   999: swap
    //   1000: aastore
    //   1001: dup_x2
    //   1002: dup_x2
    //   1003: pop
    //   1004: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1007: iconst_1
    //   1008: swap
    //   1009: aastore
    //   1010: dup_x1
    //   1011: swap
    //   1012: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1015: iconst_0
    //   1016: swap
    //   1017: aastore
    //   1018: invokevirtual e : ([Ljava/lang/Object;)V
    //   1021: aload_0
    //   1022: getfield q : Lwtf/opal/da;
    //   1025: aload_0
    //   1026: getfield l : F
    //   1029: fload #32
    //   1031: invokestatic max : (FF)F
    //   1034: fload #32
    //   1036: fload #29
    //   1038: fadd
    //   1039: invokestatic min : (FF)F
    //   1042: lload #10
    //   1044: sipush #26455
    //   1047: ldc2_w 2513916582469025492
    //   1050: lload_3
    //   1051: lxor
    //   1052: <illegal opcode> p : (IJ)I
    //   1057: iconst_3
    //   1058: anewarray java/lang/Object
    //   1061: dup_x1
    //   1062: swap
    //   1063: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1066: iconst_2
    //   1067: swap
    //   1068: aastore
    //   1069: dup_x2
    //   1070: dup_x2
    //   1071: pop
    //   1072: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1075: iconst_1
    //   1076: swap
    //   1077: aastore
    //   1078: dup_x1
    //   1079: swap
    //   1080: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1083: iconst_0
    //   1084: swap
    //   1085: aastore
    //   1086: invokevirtual e : ([Ljava/lang/Object;)V
    //   1089: aload_0
    //   1090: iconst_0
    //   1091: anewarray java/lang/Object
    //   1094: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   1097: checkcast wtf/opal/kg
    //   1100: new java/awt/Color
    //   1103: dup
    //   1104: aload #33
    //   1106: iconst_0
    //   1107: faload
    //   1108: aload #33
    //   1110: iconst_1
    //   1111: faload
    //   1112: aload #33
    //   1114: iconst_2
    //   1115: faload
    //   1116: invokestatic HSBtoRGB : (FFF)I
    //   1119: invokespecial <init> : (I)V
    //   1122: invokevirtual getRGB : ()I
    //   1125: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1128: iconst_1
    //   1129: anewarray java/lang/Object
    //   1132: dup_x1
    //   1133: swap
    //   1134: iconst_0
    //   1135: swap
    //   1136: aastore
    //   1137: invokevirtual V : ([Ljava/lang/Object;)V
    //   1140: getstatic wtf/opal/b4.h : Lwtf/opal/pa;
    //   1143: fload #31
    //   1145: fload #32
    //   1147: fload #30
    //   1149: fload #29
    //   1151: ldc_w 5.0
    //   1154: aload #33
    //   1156: iconst_0
    //   1157: faload
    //   1158: fconst_1
    //   1159: fconst_1
    //   1160: invokestatic getHSBColor : (FFF)Ljava/awt/Color;
    //   1163: invokevirtual getRGB : ()I
    //   1166: lload #16
    //   1168: bipush #7
    //   1170: anewarray java/lang/Object
    //   1173: dup_x2
    //   1174: dup_x2
    //   1175: pop
    //   1176: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1179: bipush #6
    //   1181: swap
    //   1182: aastore
    //   1183: dup_x1
    //   1184: swap
    //   1185: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1188: iconst_5
    //   1189: swap
    //   1190: aastore
    //   1191: dup_x1
    //   1192: swap
    //   1193: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1196: iconst_4
    //   1197: swap
    //   1198: aastore
    //   1199: dup_x1
    //   1200: swap
    //   1201: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1204: iconst_3
    //   1205: swap
    //   1206: aastore
    //   1207: dup_x1
    //   1208: swap
    //   1209: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1212: iconst_2
    //   1213: swap
    //   1214: aastore
    //   1215: dup_x1
    //   1216: swap
    //   1217: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1220: iconst_1
    //   1221: swap
    //   1222: aastore
    //   1223: dup_x1
    //   1224: swap
    //   1225: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1228: iconst_0
    //   1229: swap
    //   1230: aastore
    //   1231: invokevirtual M : ([Ljava/lang/Object;)V
    //   1234: getstatic wtf/opal/b4.h : Lwtf/opal/pa;
    //   1237: fload #31
    //   1239: fload #32
    //   1241: fload #30
    //   1243: fload #29
    //   1245: lload #14
    //   1247: ldc_w 5.0
    //   1250: aload #33
    //   1252: iconst_0
    //   1253: faload
    //   1254: fconst_0
    //   1255: fconst_1
    //   1256: invokestatic getHSBColor : (FFF)Ljava/awt/Color;
    //   1259: invokevirtual getRGB : ()I
    //   1262: aload #33
    //   1264: iconst_0
    //   1265: faload
    //   1266: fconst_0
    //   1267: fconst_1
    //   1268: invokestatic getHSBColor : (FFF)Ljava/awt/Color;
    //   1271: fconst_0
    //   1272: iconst_2
    //   1273: anewarray java/lang/Object
    //   1276: dup_x1
    //   1277: swap
    //   1278: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1281: iconst_1
    //   1282: swap
    //   1283: aastore
    //   1284: dup_x1
    //   1285: swap
    //   1286: iconst_0
    //   1287: swap
    //   1288: aastore
    //   1289: invokestatic Y : ([Ljava/lang/Object;)Ljava/awt/Color;
    //   1292: invokevirtual getRGB : ()I
    //   1295: fconst_0
    //   1296: bipush #9
    //   1298: anewarray java/lang/Object
    //   1301: dup_x1
    //   1302: swap
    //   1303: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1306: bipush #8
    //   1308: swap
    //   1309: aastore
    //   1310: dup_x1
    //   1311: swap
    //   1312: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1315: bipush #7
    //   1317: swap
    //   1318: aastore
    //   1319: dup_x1
    //   1320: swap
    //   1321: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1324: bipush #6
    //   1326: swap
    //   1327: aastore
    //   1328: dup_x1
    //   1329: swap
    //   1330: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1333: iconst_5
    //   1334: swap
    //   1335: aastore
    //   1336: dup_x2
    //   1337: dup_x2
    //   1338: pop
    //   1339: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1342: iconst_4
    //   1343: swap
    //   1344: aastore
    //   1345: dup_x1
    //   1346: swap
    //   1347: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1350: iconst_3
    //   1351: swap
    //   1352: aastore
    //   1353: dup_x1
    //   1354: swap
    //   1355: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1358: iconst_2
    //   1359: swap
    //   1360: aastore
    //   1361: dup_x1
    //   1362: swap
    //   1363: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1366: iconst_1
    //   1367: swap
    //   1368: aastore
    //   1369: dup_x1
    //   1370: swap
    //   1371: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1374: iconst_0
    //   1375: swap
    //   1376: aastore
    //   1377: invokevirtual e : ([Ljava/lang/Object;)V
    //   1380: getstatic wtf/opal/b4.h : Lwtf/opal/pa;
    //   1383: fload #31
    //   1385: fload #32
    //   1387: fload #30
    //   1389: fload #29
    //   1391: lload #14
    //   1393: ldc_w 5.0
    //   1396: aload #33
    //   1398: iconst_0
    //   1399: faload
    //   1400: fconst_1
    //   1401: fconst_0
    //   1402: invokestatic getHSBColor : (FFF)Ljava/awt/Color;
    //   1405: fconst_0
    //   1406: iconst_2
    //   1407: anewarray java/lang/Object
    //   1410: dup_x1
    //   1411: swap
    //   1412: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1415: iconst_1
    //   1416: swap
    //   1417: aastore
    //   1418: dup_x1
    //   1419: swap
    //   1420: iconst_0
    //   1421: swap
    //   1422: aastore
    //   1423: invokestatic Y : ([Ljava/lang/Object;)Ljava/awt/Color;
    //   1426: invokevirtual getRGB : ()I
    //   1429: aload #33
    //   1431: iconst_0
    //   1432: faload
    //   1433: fconst_1
    //   1434: fconst_0
    //   1435: invokestatic getHSBColor : (FFF)Ljava/awt/Color;
    //   1438: invokevirtual getRGB : ()I
    //   1441: ldc_w 90.0
    //   1444: bipush #9
    //   1446: anewarray java/lang/Object
    //   1449: dup_x1
    //   1450: swap
    //   1451: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1454: bipush #8
    //   1456: swap
    //   1457: aastore
    //   1458: dup_x1
    //   1459: swap
    //   1460: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1463: bipush #7
    //   1465: swap
    //   1466: aastore
    //   1467: dup_x1
    //   1468: swap
    //   1469: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1472: bipush #6
    //   1474: swap
    //   1475: aastore
    //   1476: dup_x1
    //   1477: swap
    //   1478: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1481: iconst_5
    //   1482: swap
    //   1483: aastore
    //   1484: dup_x2
    //   1485: dup_x2
    //   1486: pop
    //   1487: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1490: iconst_4
    //   1491: swap
    //   1492: aastore
    //   1493: dup_x1
    //   1494: swap
    //   1495: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1498: iconst_3
    //   1499: swap
    //   1500: aastore
    //   1501: dup_x1
    //   1502: swap
    //   1503: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1506: iconst_2
    //   1507: swap
    //   1508: aastore
    //   1509: dup_x1
    //   1510: swap
    //   1511: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1514: iconst_1
    //   1515: swap
    //   1516: aastore
    //   1517: dup_x1
    //   1518: swap
    //   1519: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1522: iconst_0
    //   1523: swap
    //   1524: aastore
    //   1525: invokevirtual e : ([Ljava/lang/Object;)V
    //   1528: getstatic wtf/opal/b4.h : Lwtf/opal/pa;
    //   1531: lload #24
    //   1533: fload #31
    //   1535: fload #32
    //   1537: fload #29
    //   1539: fadd
    //   1540: ldc 4.5
    //   1542: fadd
    //   1543: fload #30
    //   1545: ldc 6.0
    //   1547: iconst_5
    //   1548: anewarray java/lang/Object
    //   1551: dup_x1
    //   1552: swap
    //   1553: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1556: iconst_4
    //   1557: swap
    //   1558: aastore
    //   1559: dup_x1
    //   1560: swap
    //   1561: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1564: iconst_3
    //   1565: swap
    //   1566: aastore
    //   1567: dup_x1
    //   1568: swap
    //   1569: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1572: iconst_2
    //   1573: swap
    //   1574: aastore
    //   1575: dup_x1
    //   1576: swap
    //   1577: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1580: iconst_1
    //   1581: swap
    //   1582: aastore
    //   1583: dup_x2
    //   1584: dup_x2
    //   1585: pop
    //   1586: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1589: iconst_0
    //   1590: swap
    //   1591: aastore
    //   1592: invokevirtual v : ([Ljava/lang/Object;)V
    //   1595: aload_0
    //   1596: aload #28
    //   1598: ifnull -> 1833
    //   1601: getfield Y : F
    //   1604: fconst_0
    //   1605: fcmpl
    //   1606: goto -> 1613
    //   1609: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1612: athrow
    //   1613: ifeq -> 1811
    //   1616: aload_0
    //   1617: aload #28
    //   1619: ifnull -> 1833
    //   1622: goto -> 1629
    //   1625: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1628: athrow
    //   1629: lload_3
    //   1630: lconst_0
    //   1631: lcmp
    //   1632: iflt -> 1826
    //   1635: getfield l : F
    //   1638: fconst_0
    //   1639: fcmpl
    //   1640: ifeq -> 1811
    //   1643: goto -> 1650
    //   1646: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1649: athrow
    //   1650: getstatic wtf/opal/b4.h : Lwtf/opal/pa;
    //   1653: aload_0
    //   1654: getfield H : Lwtf/opal/da;
    //   1657: lload #26
    //   1659: iconst_1
    //   1660: anewarray java/lang/Object
    //   1663: dup_x2
    //   1664: dup_x2
    //   1665: pop
    //   1666: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1669: iconst_0
    //   1670: swap
    //   1671: aastore
    //   1672: invokevirtual s : ([Ljava/lang/Object;)F
    //   1675: ldc_w 2.5
    //   1678: fsub
    //   1679: aload_0
    //   1680: getfield q : Lwtf/opal/da;
    //   1683: lload #26
    //   1685: iconst_1
    //   1686: anewarray java/lang/Object
    //   1689: dup_x2
    //   1690: dup_x2
    //   1691: pop
    //   1692: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1695: iconst_0
    //   1696: swap
    //   1697: aastore
    //   1698: invokevirtual s : ([Ljava/lang/Object;)F
    //   1701: ldc_w 2.5
    //   1704: fsub
    //   1705: ldc_w 5.0
    //   1708: ldc_w 5.0
    //   1711: lload #22
    //   1713: ldc_w 2.5
    //   1716: fconst_1
    //   1717: iconst_m1
    //   1718: bipush #8
    //   1720: anewarray java/lang/Object
    //   1723: dup_x1
    //   1724: swap
    //   1725: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1728: bipush #7
    //   1730: swap
    //   1731: aastore
    //   1732: dup_x1
    //   1733: swap
    //   1734: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1737: bipush #6
    //   1739: swap
    //   1740: aastore
    //   1741: dup_x1
    //   1742: swap
    //   1743: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1746: iconst_5
    //   1747: swap
    //   1748: aastore
    //   1749: dup_x2
    //   1750: dup_x2
    //   1751: pop
    //   1752: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1755: iconst_4
    //   1756: swap
    //   1757: aastore
    //   1758: dup_x1
    //   1759: swap
    //   1760: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1763: iconst_3
    //   1764: swap
    //   1765: aastore
    //   1766: dup_x1
    //   1767: swap
    //   1768: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1771: iconst_2
    //   1772: swap
    //   1773: aastore
    //   1774: dup_x1
    //   1775: swap
    //   1776: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1779: iconst_1
    //   1780: swap
    //   1781: aastore
    //   1782: dup_x1
    //   1783: swap
    //   1784: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1787: iconst_0
    //   1788: swap
    //   1789: aastore
    //   1790: invokevirtual G : ([Ljava/lang/Object;)V
    //   1793: lload_3
    //   1794: lconst_0
    //   1795: lcmp
    //   1796: iflt -> 1846
    //   1799: aload #28
    //   1801: ifnonnull -> 1846
    //   1804: goto -> 1811
    //   1807: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1810: athrow
    //   1811: aload_0
    //   1812: fload #31
    //   1814: fload #30
    //   1816: aload #33
    //   1818: iconst_1
    //   1819: faload
    //   1820: fmul
    //   1821: fadd
    //   1822: putfield Y : F
    //   1825: aload_0
    //   1826: goto -> 1833
    //   1829: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1832: athrow
    //   1833: fload #32
    //   1835: fload #29
    //   1837: aload #33
    //   1839: iconst_2
    //   1840: faload
    //   1841: fmul
    //   1842: fadd
    //   1843: putfield l : F
    //   1846: lload_3
    //   1847: lconst_0
    //   1848: lcmp
    //   1849: iflt -> 1983
    //   1852: aload_0
    //   1853: getfield p : F
    //   1856: fconst_0
    //   1857: fcmpl
    //   1858: ifeq -> 1995
    //   1861: getstatic wtf/opal/b4.h : Lwtf/opal/pa;
    //   1864: aload_0
    //   1865: getfield X : Lwtf/opal/da;
    //   1868: lload #26
    //   1870: iconst_1
    //   1871: anewarray java/lang/Object
    //   1874: dup_x2
    //   1875: dup_x2
    //   1876: pop
    //   1877: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1880: iconst_0
    //   1881: swap
    //   1882: aastore
    //   1883: invokevirtual s : ([Ljava/lang/Object;)F
    //   1886: fload #32
    //   1888: fload #29
    //   1890: fadd
    //   1891: ldc_w 4.8
    //   1894: fadd
    //   1895: ldc_w 5.0
    //   1898: ldc_w 5.0
    //   1901: lload #22
    //   1903: ldc_w 2.5
    //   1906: fconst_1
    //   1907: iconst_m1
    //   1908: bipush #8
    //   1910: anewarray java/lang/Object
    //   1913: dup_x1
    //   1914: swap
    //   1915: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1918: bipush #7
    //   1920: swap
    //   1921: aastore
    //   1922: dup_x1
    //   1923: swap
    //   1924: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1927: bipush #6
    //   1929: swap
    //   1930: aastore
    //   1931: dup_x1
    //   1932: swap
    //   1933: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1936: iconst_5
    //   1937: swap
    //   1938: aastore
    //   1939: dup_x2
    //   1940: dup_x2
    //   1941: pop
    //   1942: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1945: iconst_4
    //   1946: swap
    //   1947: aastore
    //   1948: dup_x1
    //   1949: swap
    //   1950: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1953: iconst_3
    //   1954: swap
    //   1955: aastore
    //   1956: dup_x1
    //   1957: swap
    //   1958: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1961: iconst_2
    //   1962: swap
    //   1963: aastore
    //   1964: dup_x1
    //   1965: swap
    //   1966: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1969: iconst_1
    //   1970: swap
    //   1971: aastore
    //   1972: dup_x1
    //   1973: swap
    //   1974: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1977: iconst_0
    //   1978: swap
    //   1979: aastore
    //   1980: invokevirtual G : ([Ljava/lang/Object;)V
    //   1983: aload #28
    //   1985: ifnonnull -> 2059
    //   1988: goto -> 1995
    //   1991: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1994: athrow
    //   1995: new java/awt/Color
    //   1998: dup
    //   1999: aload_0
    //   2000: iconst_0
    //   2001: anewarray java/lang/Object
    //   2004: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   2007: checkcast wtf/opal/kg
    //   2010: invokevirtual z : ()Ljava/lang/Object;
    //   2013: checkcast java/lang/Integer
    //   2016: invokevirtual intValue : ()I
    //   2019: invokespecial <init> : (I)V
    //   2022: astore #36
    //   2024: aload #36
    //   2026: invokevirtual getRed : ()I
    //   2029: aload #36
    //   2031: invokevirtual getGreen : ()I
    //   2034: aload #36
    //   2036: invokevirtual getBlue : ()I
    //   2039: aconst_null
    //   2040: invokestatic RGBtoHSB : (III[F)[F
    //   2043: astore #37
    //   2045: aload_0
    //   2046: fload #31
    //   2048: fload #30
    //   2050: aload #37
    //   2052: iconst_0
    //   2053: faload
    //   2054: fmul
    //   2055: fadd
    //   2056: putfield p : F
    //   2059: return
    // Exception table:
    //   from	to	target	type
    //   518	530	533	wtf/opal/x5
    //   527	645	648	wtf/opal/x5
    //   537	655	658	wtf/opal/x5
    //   652	678	681	wtf/opal/x5
    //   662	692	695	wtf/opal/x5
    //   703	711	714	wtf/opal/x5
    //   708	856	859	wtf/opal/x5
    //   718	866	869	wtf/opal/x5
    //   863	878	881	wtf/opal/x5
    //   885	1606	1609	wtf/opal/x5
    //   1613	1622	1625	wtf/opal/x5
    //   1616	1643	1646	wtf/opal/x5
    //   1629	1804	1807	wtf/opal/x5
    //   1650	1826	1829	wtf/opal/x5
    //   1846	1988	1991	wtf/opal/x5
  }
  
  public void e(Object[] paramArrayOfObject) {
    double d1 = ((Double)paramArrayOfObject[0]).doubleValue();
    double d2 = ((Double)paramArrayOfObject[1]).doubleValue();
    long l1 = ((Long)paramArrayOfObject[2]).longValue();
    int i = ((Integer)paramArrayOfObject[3]).intValue();
    long l2 = l1 ^ 0x52EBE2FCF81CL;
    float f1 = this.m - 30.0F;
    float f2 = this.e - 9.0F;
    d[] arrayOfD = bg.S();
    float f3 = this.U + 4.5F;
    float f4 = this.t + 15.0F;
    try {
      new Object[7];
      (new Object[7])[6] = Double.valueOf(d2);
      new Object[7];
      (new Object[7])[5] = Double.valueOf(d1);
      (new Object[7])[4] = Float.valueOf(6.0F);
      (new Object[7])[3] = Float.valueOf(f2);
      (new Object[7])[2] = Float.valueOf(f4 + f1 + 4.5F);
      (new Object[7])[1] = Float.valueOf(f3);
      new Object[7];
      if (arrayOfD != null) {
        try {
          if (u1.Z(new Object[] { Long.valueOf(l2) }))
            this.I = true; 
        } catch (x5 x5) {
          throw a(null);
        } 
        new Object[7];
        (new Object[7])[6] = Double.valueOf(d2);
        new Object[7];
        (new Object[7])[5] = Double.valueOf(d1);
        (new Object[7])[4] = Float.valueOf(f1);
        (new Object[7])[3] = Float.valueOf(f2);
        (new Object[7])[2] = Float.valueOf(f4);
        (new Object[7])[1] = Float.valueOf(f3);
        new Object[7];
      } 
    } catch (x5 x5) {
      throw a(null);
    } 
    try {
      if (u1.Z(new Object[] { Long.valueOf(l2) }))
        this.G = true; 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  public void c(Object[] paramArrayOfObject) {
    long l = ((Long)paramArrayOfObject[0]).longValue();
    double d1 = ((Double)paramArrayOfObject[1]).doubleValue();
    double d2 = ((Double)paramArrayOfObject[2]).doubleValue();
    int i = ((Integer)paramArrayOfObject[3]).intValue();
    this.G = this.I = false;
  }
  
  public void u() {}
  
  static {
    // Byte code:
    //   0: ldc2_w -1525138408408953712
    //   3: ldc2_w 67954029377655869
    //   6: invokestatic lookup : ()Ljava/lang/invoke/MethodHandles$Lookup;
    //   9: invokevirtual lookupClass : ()Ljava/lang/Class;
    //   12: invokestatic a : (JJLjava/lang/Object;)Lwtf/opal/t5;
    //   15: ldc2_w 117835735082355
    //   18: invokeinterface a : (J)J
    //   23: putstatic wtf/opal/b4.b : J
    //   26: new java/util/HashMap
    //   29: dup
    //   30: bipush #13
    //   32: invokespecial <init> : (I)V
    //   35: putstatic wtf/opal/b4.f : Ljava/util/Map;
    //   38: getstatic wtf/opal/b4.b : J
    //   41: ldc2_w 39223959803870
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
    //   127: iconst_3
    //   128: newarray long
    //   130: astore #8
    //   132: iconst_0
    //   133: istore #5
    //   135: ldc_w '¤[KNÇ!»jú7ÂOCRÔ|]ÿ'
    //   138: dup
    //   139: astore #6
    //   141: invokevirtual length : ()I
    //   144: istore #7
    //   146: iconst_0
    //   147: istore #4
    //   149: aload #6
    //   151: iload #4
    //   153: iinc #4, 8
    //   156: iload #4
    //   158: invokevirtual substring : (II)Ljava/lang/String;
    //   161: ldc_w 'ISO-8859-1'
    //   164: invokevirtual getBytes : (Ljava/lang/String;)[B
    //   167: astore #9
    //   169: aload #8
    //   171: iload #5
    //   173: iinc #5, 1
    //   176: aload #9
    //   178: iconst_0
    //   179: baload
    //   180: i2l
    //   181: ldc2_w 255
    //   184: land
    //   185: bipush #56
    //   187: lshl
    //   188: aload #9
    //   190: iconst_1
    //   191: baload
    //   192: i2l
    //   193: ldc2_w 255
    //   196: land
    //   197: bipush #48
    //   199: lshl
    //   200: lor
    //   201: aload #9
    //   203: iconst_2
    //   204: baload
    //   205: i2l
    //   206: ldc2_w 255
    //   209: land
    //   210: bipush #40
    //   212: lshl
    //   213: lor
    //   214: aload #9
    //   216: iconst_3
    //   217: baload
    //   218: i2l
    //   219: ldc2_w 255
    //   222: land
    //   223: bipush #32
    //   225: lshl
    //   226: lor
    //   227: aload #9
    //   229: iconst_4
    //   230: baload
    //   231: i2l
    //   232: ldc2_w 255
    //   235: land
    //   236: bipush #24
    //   238: lshl
    //   239: lor
    //   240: aload #9
    //   242: iconst_5
    //   243: baload
    //   244: i2l
    //   245: ldc2_w 255
    //   248: land
    //   249: bipush #16
    //   251: lshl
    //   252: lor
    //   253: aload #9
    //   255: bipush #6
    //   257: baload
    //   258: i2l
    //   259: ldc2_w 255
    //   262: land
    //   263: bipush #8
    //   265: lshl
    //   266: lor
    //   267: aload #9
    //   269: bipush #7
    //   271: baload
    //   272: i2l
    //   273: ldc2_w 255
    //   276: land
    //   277: lor
    //   278: iconst_m1
    //   279: goto -> 305
    //   282: lastore
    //   283: iload #4
    //   285: iload #7
    //   287: if_icmplt -> 149
    //   290: aload #8
    //   292: putstatic wtf/opal/b4.c : [J
    //   295: iconst_3
    //   296: anewarray java/lang/Integer
    //   299: putstatic wtf/opal/b4.d : [Ljava/lang/Integer;
    //   302: goto -> 507
    //   305: dup_x2
    //   306: pop
    //   307: lstore #10
    //   309: bipush #8
    //   311: newarray byte
    //   313: dup
    //   314: iconst_0
    //   315: lload #10
    //   317: bipush #56
    //   319: lushr
    //   320: l2i
    //   321: i2b
    //   322: bastore
    //   323: dup
    //   324: iconst_1
    //   325: lload #10
    //   327: bipush #48
    //   329: lushr
    //   330: l2i
    //   331: i2b
    //   332: bastore
    //   333: dup
    //   334: iconst_2
    //   335: lload #10
    //   337: bipush #40
    //   339: lushr
    //   340: l2i
    //   341: i2b
    //   342: bastore
    //   343: dup
    //   344: iconst_3
    //   345: lload #10
    //   347: bipush #32
    //   349: lushr
    //   350: l2i
    //   351: i2b
    //   352: bastore
    //   353: dup
    //   354: iconst_4
    //   355: lload #10
    //   357: bipush #24
    //   359: lushr
    //   360: l2i
    //   361: i2b
    //   362: bastore
    //   363: dup
    //   364: iconst_5
    //   365: lload #10
    //   367: bipush #16
    //   369: lushr
    //   370: l2i
    //   371: i2b
    //   372: bastore
    //   373: dup
    //   374: bipush #6
    //   376: lload #10
    //   378: bipush #8
    //   380: lushr
    //   381: l2i
    //   382: i2b
    //   383: bastore
    //   384: dup
    //   385: bipush #7
    //   387: lload #10
    //   389: l2i
    //   390: i2b
    //   391: bastore
    //   392: aload_2
    //   393: swap
    //   394: invokevirtual doFinal : ([B)[B
    //   397: astore #12
    //   399: aload #12
    //   401: iconst_0
    //   402: baload
    //   403: i2l
    //   404: ldc2_w 255
    //   407: land
    //   408: bipush #56
    //   410: lshl
    //   411: aload #12
    //   413: iconst_1
    //   414: baload
    //   415: i2l
    //   416: ldc2_w 255
    //   419: land
    //   420: bipush #48
    //   422: lshl
    //   423: lor
    //   424: aload #12
    //   426: iconst_2
    //   427: baload
    //   428: i2l
    //   429: ldc2_w 255
    //   432: land
    //   433: bipush #40
    //   435: lshl
    //   436: lor
    //   437: aload #12
    //   439: iconst_3
    //   440: baload
    //   441: i2l
    //   442: ldc2_w 255
    //   445: land
    //   446: bipush #32
    //   448: lshl
    //   449: lor
    //   450: aload #12
    //   452: iconst_4
    //   453: baload
    //   454: i2l
    //   455: ldc2_w 255
    //   458: land
    //   459: bipush #24
    //   461: lshl
    //   462: lor
    //   463: aload #12
    //   465: iconst_5
    //   466: baload
    //   467: i2l
    //   468: ldc2_w 255
    //   471: land
    //   472: bipush #16
    //   474: lshl
    //   475: lor
    //   476: aload #12
    //   478: bipush #6
    //   480: baload
    //   481: i2l
    //   482: ldc2_w 255
    //   485: land
    //   486: bipush #8
    //   488: lshl
    //   489: lor
    //   490: aload #12
    //   492: bipush #7
    //   494: baload
    //   495: i2l
    //   496: ldc2_w 255
    //   499: land
    //   500: lor
    //   501: dup2_x1
    //   502: pop2
    //   503: pop
    //   504: goto -> 282
    //   507: return
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
  
  private static int a(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x6310;
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
        throw new RuntimeException("wtf/opal/b4", exception);
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
    //   65: ldc_w 'wtf/opal/b4'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\b4.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */