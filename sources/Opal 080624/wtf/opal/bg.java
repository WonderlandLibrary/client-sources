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
import net.minecraft.class_156;
import net.minecraft.class_3532;
import net.minecraft.class_5225;

public class bg extends b5<kl> {
  private boolean y;
  
  private int c;
  
  private int K;
  
  private int i;
  
  private static d[] g;
  
  private static final long b;
  
  private static final long[] d;
  
  private static final Integer[] f;
  
  private static final Map j;
  
  public bg(long paramLong, kl paramkl) {
    super(paramkl, l);
  }
  
  public void g(Object[] paramArrayOfObject) {
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
    //   11: checkcast java/lang/Integer
    //   14: invokevirtual intValue : ()I
    //   17: istore #4
    //   19: dup
    //   20: iconst_2
    //   21: aaload
    //   22: checkcast java/lang/Integer
    //   25: invokevirtual intValue : ()I
    //   28: istore #7
    //   30: dup
    //   31: iconst_3
    //   32: aaload
    //   33: checkcast java/lang/Long
    //   36: invokevirtual longValue : ()J
    //   39: lstore #5
    //   41: dup
    //   42: iconst_4
    //   43: aaload
    //   44: checkcast java/lang/Float
    //   47: invokevirtual floatValue : ()F
    //   50: fstore_3
    //   51: pop
    //   52: lload #5
    //   54: dup2
    //   55: ldc2_w 10802718197547
    //   58: lxor
    //   59: lstore #8
    //   61: dup2
    //   62: ldc2_w 28729273170977
    //   65: lxor
    //   66: lstore #10
    //   68: dup2
    //   69: ldc2_w 94556900610908
    //   72: lxor
    //   73: lstore #12
    //   75: dup2
    //   76: ldc2_w 66468241307817
    //   79: lxor
    //   80: lstore #14
    //   82: dup2
    //   83: ldc2_w 130132861335437
    //   86: lxor
    //   87: lstore #16
    //   89: dup2
    //   90: ldc2_w 40314075635121
    //   93: lxor
    //   94: lstore #18
    //   96: pop2
    //   97: aload_0
    //   98: ldc 8.0
    //   100: iconst_1
    //   101: anewarray java/lang/Object
    //   104: dup_x1
    //   105: swap
    //   106: invokestatic valueOf : (F)Ljava/lang/Float;
    //   109: iconst_0
    //   110: swap
    //   111: aastore
    //   112: invokevirtual q : ([Ljava/lang/Object;)V
    //   115: invokestatic S : ()[Lwtf/opal/d;
    //   118: getstatic wtf/opal/bg.h : Lwtf/opal/pa;
    //   121: aload_0
    //   122: getfield U : F
    //   125: aload_0
    //   126: getfield t : F
    //   129: aload_0
    //   130: getfield e : F
    //   133: aload_0
    //   134: getfield m : F
    //   137: sipush #18336
    //   140: ldc2_w 2042496173153520477
    //   143: lload #5
    //   145: lxor
    //   146: <illegal opcode> u : (IJ)I
    //   151: lload #14
    //   153: bipush #6
    //   155: anewarray java/lang/Object
    //   158: dup_x2
    //   159: dup_x2
    //   160: pop
    //   161: invokestatic valueOf : (J)Ljava/lang/Long;
    //   164: iconst_5
    //   165: swap
    //   166: aastore
    //   167: dup_x1
    //   168: swap
    //   169: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   172: iconst_4
    //   173: swap
    //   174: aastore
    //   175: dup_x1
    //   176: swap
    //   177: invokestatic valueOf : (F)Ljava/lang/Float;
    //   180: iconst_3
    //   181: swap
    //   182: aastore
    //   183: dup_x1
    //   184: swap
    //   185: invokestatic valueOf : (F)Ljava/lang/Float;
    //   188: iconst_2
    //   189: swap
    //   190: aastore
    //   191: dup_x1
    //   192: swap
    //   193: invokestatic valueOf : (F)Ljava/lang/Float;
    //   196: iconst_1
    //   197: swap
    //   198: aastore
    //   199: dup_x1
    //   200: swap
    //   201: invokestatic valueOf : (F)Ljava/lang/Float;
    //   204: iconst_0
    //   205: swap
    //   206: aastore
    //   207: invokevirtual k : ([Ljava/lang/Object;)V
    //   210: getstatic wtf/opal/bg.Z : Lwtf/opal/bu;
    //   213: aload_2
    //   214: aload_0
    //   215: iconst_0
    //   216: anewarray java/lang/Object
    //   219: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   222: checkcast wtf/opal/kl
    //   225: iconst_0
    //   226: anewarray java/lang/Object
    //   229: invokevirtual r : ([Ljava/lang/Object;)Ljava/lang/String;
    //   232: aload_0
    //   233: getfield U : F
    //   236: aload_0
    //   237: getfield e : F
    //   240: fconst_2
    //   241: fdiv
    //   242: fadd
    //   243: getstatic wtf/opal/bg.Z : Lwtf/opal/bu;
    //   246: aload_0
    //   247: iconst_0
    //   248: anewarray java/lang/Object
    //   251: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   254: checkcast wtf/opal/kl
    //   257: iconst_0
    //   258: anewarray java/lang/Object
    //   261: invokevirtual r : ([Ljava/lang/Object;)Ljava/lang/String;
    //   264: ldc 6.5
    //   266: lload #8
    //   268: iconst_3
    //   269: anewarray java/lang/Object
    //   272: dup_x2
    //   273: dup_x2
    //   274: pop
    //   275: invokestatic valueOf : (J)Ljava/lang/Long;
    //   278: iconst_2
    //   279: swap
    //   280: aastore
    //   281: dup_x1
    //   282: swap
    //   283: invokestatic valueOf : (F)Ljava/lang/Float;
    //   286: iconst_1
    //   287: swap
    //   288: aastore
    //   289: dup_x1
    //   290: swap
    //   291: iconst_0
    //   292: swap
    //   293: aastore
    //   294: invokevirtual s : ([Ljava/lang/Object;)F
    //   297: fconst_2
    //   298: fdiv
    //   299: fsub
    //   300: lload #10
    //   302: dup2_x1
    //   303: pop2
    //   304: aload_0
    //   305: getfield t : F
    //   308: ldc 3.0
    //   310: fadd
    //   311: ldc 6.5
    //   313: iconst_m1
    //   314: bipush #7
    //   316: anewarray java/lang/Object
    //   319: dup_x1
    //   320: swap
    //   321: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   324: bipush #6
    //   326: swap
    //   327: aastore
    //   328: dup_x1
    //   329: swap
    //   330: invokestatic valueOf : (F)Ljava/lang/Float;
    //   333: iconst_5
    //   334: swap
    //   335: aastore
    //   336: dup_x1
    //   337: swap
    //   338: invokestatic valueOf : (F)Ljava/lang/Float;
    //   341: iconst_4
    //   342: swap
    //   343: aastore
    //   344: dup_x1
    //   345: swap
    //   346: invokestatic valueOf : (F)Ljava/lang/Float;
    //   349: iconst_3
    //   350: swap
    //   351: aastore
    //   352: dup_x2
    //   353: dup_x2
    //   354: pop
    //   355: invokestatic valueOf : (J)Ljava/lang/Long;
    //   358: iconst_2
    //   359: swap
    //   360: aastore
    //   361: dup_x1
    //   362: swap
    //   363: iconst_1
    //   364: swap
    //   365: aastore
    //   366: dup_x1
    //   367: swap
    //   368: iconst_0
    //   369: swap
    //   370: aastore
    //   371: invokevirtual R : ([Ljava/lang/Object;)V
    //   374: astore #20
    //   376: getstatic wtf/opal/bg.h : Lwtf/opal/pa;
    //   379: aload_0
    //   380: getfield U : F
    //   383: ldc 10.0
    //   385: fadd
    //   386: aload_0
    //   387: getfield t : F
    //   390: ldc 12.0
    //   392: fadd
    //   393: aload_0
    //   394: getfield e : F
    //   397: ldc 20.0
    //   399: fsub
    //   400: aload_0
    //   401: getfield m : F
    //   404: ldc 15.0
    //   406: fsub
    //   407: lload #18
    //   409: fconst_2
    //   410: fconst_2
    //   411: sipush #13791
    //   414: ldc2_w 5569104684594428197
    //   417: lload #5
    //   419: lxor
    //   420: <illegal opcode> u : (IJ)I
    //   425: bipush #8
    //   427: anewarray java/lang/Object
    //   430: dup_x1
    //   431: swap
    //   432: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   435: bipush #7
    //   437: swap
    //   438: aastore
    //   439: dup_x1
    //   440: swap
    //   441: invokestatic valueOf : (F)Ljava/lang/Float;
    //   444: bipush #6
    //   446: swap
    //   447: aastore
    //   448: dup_x1
    //   449: swap
    //   450: invokestatic valueOf : (F)Ljava/lang/Float;
    //   453: iconst_5
    //   454: swap
    //   455: aastore
    //   456: dup_x2
    //   457: dup_x2
    //   458: pop
    //   459: invokestatic valueOf : (J)Ljava/lang/Long;
    //   462: iconst_4
    //   463: swap
    //   464: aastore
    //   465: dup_x1
    //   466: swap
    //   467: invokestatic valueOf : (F)Ljava/lang/Float;
    //   470: iconst_3
    //   471: swap
    //   472: aastore
    //   473: dup_x1
    //   474: swap
    //   475: invokestatic valueOf : (F)Ljava/lang/Float;
    //   478: iconst_2
    //   479: swap
    //   480: aastore
    //   481: dup_x1
    //   482: swap
    //   483: invokestatic valueOf : (F)Ljava/lang/Float;
    //   486: iconst_1
    //   487: swap
    //   488: aastore
    //   489: dup_x1
    //   490: swap
    //   491: invokestatic valueOf : (F)Ljava/lang/Float;
    //   494: iconst_0
    //   495: swap
    //   496: aastore
    //   497: invokevirtual G : ([Ljava/lang/Object;)V
    //   500: getstatic wtf/opal/bg.h : Lwtf/opal/pa;
    //   503: aload_0
    //   504: getfield U : F
    //   507: ldc 10.0
    //   509: fadd
    //   510: aload_0
    //   511: getfield t : F
    //   514: ldc 12.0
    //   516: fadd
    //   517: aload_0
    //   518: getfield e : F
    //   521: ldc 20.0
    //   523: fsub
    //   524: aload_0
    //   525: getfield m : F
    //   528: ldc 15.0
    //   530: fsub
    //   531: fconst_2
    //   532: sipush #17253
    //   535: ldc2_w 2525475873608568731
    //   538: lload #5
    //   540: lxor
    //   541: <illegal opcode> u : (IJ)I
    //   546: lload #12
    //   548: bipush #7
    //   550: anewarray java/lang/Object
    //   553: dup_x2
    //   554: dup_x2
    //   555: pop
    //   556: invokestatic valueOf : (J)Ljava/lang/Long;
    //   559: bipush #6
    //   561: swap
    //   562: aastore
    //   563: dup_x1
    //   564: swap
    //   565: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   568: iconst_5
    //   569: swap
    //   570: aastore
    //   571: dup_x1
    //   572: swap
    //   573: invokestatic valueOf : (F)Ljava/lang/Float;
    //   576: iconst_4
    //   577: swap
    //   578: aastore
    //   579: dup_x1
    //   580: swap
    //   581: invokestatic valueOf : (F)Ljava/lang/Float;
    //   584: iconst_3
    //   585: swap
    //   586: aastore
    //   587: dup_x1
    //   588: swap
    //   589: invokestatic valueOf : (F)Ljava/lang/Float;
    //   592: iconst_2
    //   593: swap
    //   594: aastore
    //   595: dup_x1
    //   596: swap
    //   597: invokestatic valueOf : (F)Ljava/lang/Float;
    //   600: iconst_1
    //   601: swap
    //   602: aastore
    //   603: dup_x1
    //   604: swap
    //   605: invokestatic valueOf : (F)Ljava/lang/Float;
    //   608: iconst_0
    //   609: swap
    //   610: aastore
    //   611: invokevirtual M : ([Ljava/lang/Object;)V
    //   614: getstatic wtf/opal/bg.Z : Lwtf/opal/bu;
    //   617: aload_0
    //   618: iconst_0
    //   619: anewarray java/lang/Object
    //   622: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   625: checkcast wtf/opal/kl
    //   628: iconst_0
    //   629: anewarray java/lang/Object
    //   632: invokevirtual r : ([Ljava/lang/Object;)Ljava/lang/String;
    //   635: lload #16
    //   637: dup2_x1
    //   638: pop2
    //   639: ldc 6.5
    //   641: iconst_3
    //   642: anewarray java/lang/Object
    //   645: dup_x1
    //   646: swap
    //   647: invokestatic valueOf : (F)Ljava/lang/Float;
    //   650: iconst_2
    //   651: swap
    //   652: aastore
    //   653: dup_x1
    //   654: swap
    //   655: iconst_1
    //   656: swap
    //   657: aastore
    //   658: dup_x2
    //   659: dup_x2
    //   660: pop
    //   661: invokestatic valueOf : (J)Ljava/lang/Long;
    //   664: iconst_0
    //   665: swap
    //   666: aastore
    //   667: invokevirtual A : ([Ljava/lang/Object;)F
    //   670: ldc 3.0
    //   672: fadd
    //   673: fstore #21
    //   675: aload_0
    //   676: getfield m : F
    //   679: fload #21
    //   681: fsub
    //   682: fstore #22
    //   684: aload_0
    //   685: iconst_0
    //   686: anewarray java/lang/Object
    //   689: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   692: checkcast wtf/opal/kl
    //   695: invokevirtual z : ()Ljava/lang/Object;
    //   698: checkcast java/lang/String
    //   701: aload #20
    //   703: ifnull -> 756
    //   706: invokevirtual isEmpty : ()Z
    //   709: ifne -> 1295
    //   712: goto -> 719
    //   715: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   718: athrow
    //   719: aload_0
    //   720: aload_0
    //   721: iconst_0
    //   722: anewarray java/lang/Object
    //   725: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   728: checkcast wtf/opal/kl
    //   731: invokevirtual z : ()Ljava/lang/Object;
    //   734: checkcast java/lang/String
    //   737: iconst_1
    //   738: anewarray java/lang/Object
    //   741: dup_x1
    //   742: swap
    //   743: iconst_0
    //   744: swap
    //   745: aastore
    //   746: invokevirtual p : ([Ljava/lang/Object;)Ljava/lang/String;
    //   749: goto -> 756
    //   752: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   755: athrow
    //   756: astore #23
    //   758: lload #5
    //   760: lconst_0
    //   761: lcmp
    //   762: ifle -> 903
    //   765: aload #23
    //   767: invokevirtual isEmpty : ()Z
    //   770: ifne -> 910
    //   773: getstatic wtf/opal/bg.h : Lwtf/opal/pa;
    //   776: aload_0
    //   777: getfield U : F
    //   780: ldc 12.0
    //   782: fadd
    //   783: aload_0
    //   784: getfield t : F
    //   787: ldc 14.5
    //   789: fadd
    //   790: getstatic wtf/opal/bg.Z : Lwtf/opal/bu;
    //   793: aload #23
    //   795: ldc 7.0
    //   797: lload #8
    //   799: iconst_3
    //   800: anewarray java/lang/Object
    //   803: dup_x2
    //   804: dup_x2
    //   805: pop
    //   806: invokestatic valueOf : (J)Ljava/lang/Long;
    //   809: iconst_2
    //   810: swap
    //   811: aastore
    //   812: dup_x1
    //   813: swap
    //   814: invokestatic valueOf : (F)Ljava/lang/Float;
    //   817: iconst_1
    //   818: swap
    //   819: aastore
    //   820: dup_x1
    //   821: swap
    //   822: iconst_0
    //   823: swap
    //   824: aastore
    //   825: invokevirtual s : ([Ljava/lang/Object;)F
    //   828: ldc 7.0
    //   830: sipush #25840
    //   833: ldc2_w 5361590325977294857
    //   836: lload #5
    //   838: lxor
    //   839: <illegal opcode> u : (IJ)I
    //   844: lload #14
    //   846: bipush #6
    //   848: anewarray java/lang/Object
    //   851: dup_x2
    //   852: dup_x2
    //   853: pop
    //   854: invokestatic valueOf : (J)Ljava/lang/Long;
    //   857: iconst_5
    //   858: swap
    //   859: aastore
    //   860: dup_x1
    //   861: swap
    //   862: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   865: iconst_4
    //   866: swap
    //   867: aastore
    //   868: dup_x1
    //   869: swap
    //   870: invokestatic valueOf : (F)Ljava/lang/Float;
    //   873: iconst_3
    //   874: swap
    //   875: aastore
    //   876: dup_x1
    //   877: swap
    //   878: invokestatic valueOf : (F)Ljava/lang/Float;
    //   881: iconst_2
    //   882: swap
    //   883: aastore
    //   884: dup_x1
    //   885: swap
    //   886: invokestatic valueOf : (F)Ljava/lang/Float;
    //   889: iconst_1
    //   890: swap
    //   891: aastore
    //   892: dup_x1
    //   893: swap
    //   894: invokestatic valueOf : (F)Ljava/lang/Float;
    //   897: iconst_0
    //   898: swap
    //   899: aastore
    //   900: invokevirtual k : ([Ljava/lang/Object;)V
    //   903: goto -> 910
    //   906: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   909: athrow
    //   910: fload #22
    //   912: fconst_2
    //   913: fdiv
    //   914: getstatic wtf/opal/bg.Z : Lwtf/opal/bu;
    //   917: aload_0
    //   918: iconst_0
    //   919: anewarray java/lang/Object
    //   922: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   925: checkcast wtf/opal/kl
    //   928: invokevirtual z : ()Ljava/lang/Object;
    //   931: lload #16
    //   933: dup2_x1
    //   934: pop2
    //   935: checkcast java/lang/String
    //   938: ldc 7.0
    //   940: iconst_3
    //   941: anewarray java/lang/Object
    //   944: dup_x1
    //   945: swap
    //   946: invokestatic valueOf : (F)Ljava/lang/Float;
    //   949: iconst_2
    //   950: swap
    //   951: aastore
    //   952: dup_x1
    //   953: swap
    //   954: iconst_1
    //   955: swap
    //   956: aastore
    //   957: dup_x2
    //   958: dup_x2
    //   959: pop
    //   960: invokestatic valueOf : (J)Ljava/lang/Long;
    //   963: iconst_0
    //   964: swap
    //   965: aastore
    //   966: invokevirtual A : ([Ljava/lang/Object;)F
    //   969: fconst_2
    //   970: fdiv
    //   971: fsub
    //   972: fstore #24
    //   974: getstatic wtf/opal/bg.Z : Lwtf/opal/bu;
    //   977: aload_2
    //   978: aload_0
    //   979: iconst_0
    //   980: anewarray java/lang/Object
    //   983: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   986: checkcast wtf/opal/kl
    //   989: invokevirtual z : ()Ljava/lang/Object;
    //   992: checkcast java/lang/String
    //   995: aload_0
    //   996: getfield U : F
    //   999: ldc 12.0
    //   1001: fadd
    //   1002: lload #10
    //   1004: dup2_x1
    //   1005: pop2
    //   1006: aload_0
    //   1007: getfield t : F
    //   1010: fload #21
    //   1012: fadd
    //   1013: fload #24
    //   1015: fadd
    //   1016: ldc 7.0
    //   1018: sipush #10471
    //   1021: ldc2_w 6396265516830450716
    //   1024: lload #5
    //   1026: lxor
    //   1027: <illegal opcode> u : (IJ)I
    //   1032: bipush #7
    //   1034: anewarray java/lang/Object
    //   1037: dup_x1
    //   1038: swap
    //   1039: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1042: bipush #6
    //   1044: swap
    //   1045: aastore
    //   1046: dup_x1
    //   1047: swap
    //   1048: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1051: iconst_5
    //   1052: swap
    //   1053: aastore
    //   1054: dup_x1
    //   1055: swap
    //   1056: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1059: iconst_4
    //   1060: swap
    //   1061: aastore
    //   1062: dup_x1
    //   1063: swap
    //   1064: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1067: iconst_3
    //   1068: swap
    //   1069: aastore
    //   1070: dup_x2
    //   1071: dup_x2
    //   1072: pop
    //   1073: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1076: iconst_2
    //   1077: swap
    //   1078: aastore
    //   1079: dup_x1
    //   1080: swap
    //   1081: iconst_1
    //   1082: swap
    //   1083: aastore
    //   1084: dup_x1
    //   1085: swap
    //   1086: iconst_0
    //   1087: swap
    //   1088: aastore
    //   1089: invokevirtual R : ([Ljava/lang/Object;)V
    //   1092: aload_0
    //   1093: getfield y : Z
    //   1096: lload #5
    //   1098: lconst_0
    //   1099: lcmp
    //   1100: iflt -> 1130
    //   1103: aload #20
    //   1105: ifnull -> 1130
    //   1108: ifeq -> 1295
    //   1111: goto -> 1118
    //   1114: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1117: athrow
    //   1118: aload #23
    //   1120: invokevirtual isEmpty : ()Z
    //   1123: goto -> 1130
    //   1126: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1129: athrow
    //   1130: ifeq -> 1295
    //   1133: getstatic wtf/opal/bg.h : Lwtf/opal/pa;
    //   1136: aload_0
    //   1137: getfield U : F
    //   1140: ldc 12.0
    //   1142: fadd
    //   1143: getstatic wtf/opal/bg.Z : Lwtf/opal/bu;
    //   1146: aload_0
    //   1147: iconst_0
    //   1148: anewarray java/lang/Object
    //   1151: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   1154: checkcast wtf/opal/kl
    //   1157: invokevirtual z : ()Ljava/lang/Object;
    //   1160: checkcast java/lang/String
    //   1163: iconst_0
    //   1164: aload_0
    //   1165: getfield c : I
    //   1168: invokevirtual substring : (II)Ljava/lang/String;
    //   1171: ldc 7.0
    //   1173: lload #8
    //   1175: iconst_3
    //   1176: anewarray java/lang/Object
    //   1179: dup_x2
    //   1180: dup_x2
    //   1181: pop
    //   1182: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1185: iconst_2
    //   1186: swap
    //   1187: aastore
    //   1188: dup_x1
    //   1189: swap
    //   1190: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1193: iconst_1
    //   1194: swap
    //   1195: aastore
    //   1196: dup_x1
    //   1197: swap
    //   1198: iconst_0
    //   1199: swap
    //   1200: aastore
    //   1201: invokevirtual s : ([Ljava/lang/Object;)F
    //   1204: fadd
    //   1205: aload_0
    //   1206: getfield t : F
    //   1209: ldc 14.5
    //   1211: fadd
    //   1212: fconst_1
    //   1213: ldc 7.0
    //   1215: sipush #22559
    //   1218: ldc2_w 6281637777860564199
    //   1221: lload #5
    //   1223: lxor
    //   1224: <illegal opcode> u : (IJ)I
    //   1229: lload #14
    //   1231: bipush #6
    //   1233: anewarray java/lang/Object
    //   1236: dup_x2
    //   1237: dup_x2
    //   1238: pop
    //   1239: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1242: iconst_5
    //   1243: swap
    //   1244: aastore
    //   1245: dup_x1
    //   1246: swap
    //   1247: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1250: iconst_4
    //   1251: swap
    //   1252: aastore
    //   1253: dup_x1
    //   1254: swap
    //   1255: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1258: iconst_3
    //   1259: swap
    //   1260: aastore
    //   1261: dup_x1
    //   1262: swap
    //   1263: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1266: iconst_2
    //   1267: swap
    //   1268: aastore
    //   1269: dup_x1
    //   1270: swap
    //   1271: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1274: iconst_1
    //   1275: swap
    //   1276: aastore
    //   1277: dup_x1
    //   1278: swap
    //   1279: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1282: iconst_0
    //   1283: swap
    //   1284: aastore
    //   1285: invokevirtual k : ([Ljava/lang/Object;)V
    //   1288: goto -> 1295
    //   1291: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1294: athrow
    //   1295: return
    // Exception table:
    //   from	to	target	type
    //   684	712	715	wtf/opal/x5
    //   706	749	752	wtf/opal/x5
    //   758	903	906	wtf/opal/x5
    //   974	1111	1114	wtf/opal/x5
    //   1108	1123	1126	wtf/opal/x5
    //   1130	1288	1291	wtf/opal/x5
  }
  
  public void o(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore #5
    //   12: dup
    //   13: iconst_1
    //   14: aaload
    //   15: checkcast java/lang/Integer
    //   18: invokevirtual intValue : ()I
    //   21: istore #4
    //   23: dup
    //   24: iconst_2
    //   25: aaload
    //   26: checkcast java/lang/Integer
    //   29: invokevirtual intValue : ()I
    //   32: istore_3
    //   33: dup
    //   34: iconst_3
    //   35: aaload
    //   36: checkcast java/lang/Integer
    //   39: invokevirtual intValue : ()I
    //   42: istore_2
    //   43: pop
    //   44: lload #5
    //   46: dup2
    //   47: ldc2_w 59751068200200
    //   50: lxor
    //   51: lstore #7
    //   53: dup2
    //   54: ldc2_w 72160074374334
    //   57: lxor
    //   58: lstore #9
    //   60: dup2
    //   61: ldc2_w 95379227427565
    //   64: lxor
    //   65: lstore #11
    //   67: pop2
    //   68: invokestatic S : ()[Lwtf/opal/d;
    //   71: astore #13
    //   73: aload_0
    //   74: getfield y : Z
    //   77: aload #13
    //   79: ifnull -> 98
    //   82: ifne -> 93
    //   85: goto -> 92
    //   88: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   91: athrow
    //   92: return
    //   93: iload #4
    //   95: invokestatic method_25439 : (I)Z
    //   98: aload #13
    //   100: lload #5
    //   102: lconst_0
    //   103: lcmp
    //   104: iflt -> 161
    //   107: ifnull -> 159
    //   110: ifeq -> 154
    //   113: goto -> 120
    //   116: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   119: athrow
    //   120: aload_0
    //   121: iconst_0
    //   122: putfield K : I
    //   125: aload_0
    //   126: aload_0
    //   127: iconst_0
    //   128: anewarray java/lang/Object
    //   131: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   134: checkcast wtf/opal/kl
    //   137: invokevirtual z : ()Ljava/lang/Object;
    //   140: checkcast java/lang/String
    //   143: invokevirtual length : ()I
    //   146: putfield c : I
    //   149: return
    //   150: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   153: athrow
    //   154: iload #4
    //   156: invokestatic method_25438 : (I)Z
    //   159: aload #13
    //   161: lload #5
    //   163: lconst_0
    //   164: lcmp
    //   165: ifle -> 239
    //   168: ifnull -> 230
    //   171: ifeq -> 225
    //   174: goto -> 181
    //   177: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   180: athrow
    //   181: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   184: getfield field_1774 : Lnet/minecraft/class_309;
    //   187: aload_0
    //   188: aload_0
    //   189: iconst_0
    //   190: anewarray java/lang/Object
    //   193: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   196: checkcast wtf/opal/kl
    //   199: invokevirtual z : ()Ljava/lang/Object;
    //   202: checkcast java/lang/String
    //   205: iconst_1
    //   206: anewarray java/lang/Object
    //   209: dup_x1
    //   210: swap
    //   211: iconst_0
    //   212: swap
    //   213: aastore
    //   214: invokevirtual p : ([Ljava/lang/Object;)Ljava/lang/String;
    //   217: invokevirtual method_1455 : (Ljava/lang/String;)V
    //   220: return
    //   221: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   224: athrow
    //   225: iload #4
    //   227: invokestatic method_25437 : (I)Z
    //   230: lload #5
    //   232: lconst_0
    //   233: lcmp
    //   234: ifle -> 279
    //   237: aload #13
    //   239: ifnull -> 279
    //   242: ifeq -> 276
    //   245: goto -> 252
    //   248: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   251: athrow
    //   252: aload_0
    //   253: lload #9
    //   255: iconst_1
    //   256: anewarray java/lang/Object
    //   259: dup_x2
    //   260: dup_x2
    //   261: pop
    //   262: invokestatic valueOf : (J)Ljava/lang/Long;
    //   265: iconst_0
    //   266: swap
    //   267: aastore
    //   268: invokevirtual s : ([Ljava/lang/Object;)V
    //   271: return
    //   272: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   275: athrow
    //   276: invokestatic method_25441 : ()Z
    //   279: ifeq -> 292
    //   282: getstatic net/minecraft/class_3728$class_7279.field_38309 : Lnet/minecraft/class_3728$class_7279;
    //   285: goto -> 295
    //   288: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   291: athrow
    //   292: getstatic net/minecraft/class_3728$class_7279.field_38308 : Lnet/minecraft/class_3728$class_7279;
    //   295: astore #14
    //   297: iload #4
    //   299: sipush #9398
    //   302: ldc2_w 7468616190618586685
    //   305: lload #5
    //   307: lxor
    //   308: <illegal opcode> u : (IJ)I
    //   313: aload #13
    //   315: lload #5
    //   317: lconst_0
    //   318: lcmp
    //   319: ifle -> 393
    //   322: ifnull -> 391
    //   325: if_icmpne -> 375
    //   328: goto -> 335
    //   331: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   334: athrow
    //   335: aload_0
    //   336: lload #11
    //   338: iconst_m1
    //   339: aload #14
    //   341: iconst_3
    //   342: anewarray java/lang/Object
    //   345: dup_x1
    //   346: swap
    //   347: iconst_2
    //   348: swap
    //   349: aastore
    //   350: dup_x1
    //   351: swap
    //   352: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   355: iconst_1
    //   356: swap
    //   357: aastore
    //   358: dup_x2
    //   359: dup_x2
    //   360: pop
    //   361: invokestatic valueOf : (J)Ljava/lang/Long;
    //   364: iconst_0
    //   365: swap
    //   366: aastore
    //   367: invokevirtual M : ([Ljava/lang/Object;)V
    //   370: return
    //   371: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   374: athrow
    //   375: iload #4
    //   377: sipush #17242
    //   380: ldc2_w 5856243330551392722
    //   383: lload #5
    //   385: lxor
    //   386: <illegal opcode> u : (IJ)I
    //   391: aload #13
    //   393: lload #5
    //   395: lconst_0
    //   396: lcmp
    //   397: ifle -> 478
    //   400: ifnull -> 469
    //   403: if_icmpne -> 453
    //   406: goto -> 413
    //   409: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   412: athrow
    //   413: aload_0
    //   414: lload #11
    //   416: iconst_1
    //   417: aload #14
    //   419: iconst_3
    //   420: anewarray java/lang/Object
    //   423: dup_x1
    //   424: swap
    //   425: iconst_2
    //   426: swap
    //   427: aastore
    //   428: dup_x1
    //   429: swap
    //   430: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   433: iconst_1
    //   434: swap
    //   435: aastore
    //   436: dup_x2
    //   437: dup_x2
    //   438: pop
    //   439: invokestatic valueOf : (J)Ljava/lang/Long;
    //   442: iconst_0
    //   443: swap
    //   444: aastore
    //   445: invokevirtual M : ([Ljava/lang/Object;)V
    //   448: return
    //   449: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   452: athrow
    //   453: iload #4
    //   455: sipush #994
    //   458: ldc2_w 8923235832540916067
    //   461: lload #5
    //   463: lxor
    //   464: <illegal opcode> u : (IJ)I
    //   469: lload #5
    //   471: lconst_0
    //   472: lcmp
    //   473: ifle -> 558
    //   476: aload #13
    //   478: ifnull -> 558
    //   481: if_icmpne -> 542
    //   484: goto -> 491
    //   487: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   490: athrow
    //   491: aload_0
    //   492: iconst_m1
    //   493: invokestatic method_25442 : ()Z
    //   496: aload #14
    //   498: lload #7
    //   500: iconst_4
    //   501: anewarray java/lang/Object
    //   504: dup_x2
    //   505: dup_x2
    //   506: pop
    //   507: invokestatic valueOf : (J)Ljava/lang/Long;
    //   510: iconst_3
    //   511: swap
    //   512: aastore
    //   513: dup_x1
    //   514: swap
    //   515: iconst_2
    //   516: swap
    //   517: aastore
    //   518: dup_x1
    //   519: swap
    //   520: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   523: iconst_1
    //   524: swap
    //   525: aastore
    //   526: dup_x1
    //   527: swap
    //   528: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   531: iconst_0
    //   532: swap
    //   533: aastore
    //   534: invokevirtual K : ([Ljava/lang/Object;)V
    //   537: return
    //   538: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   541: athrow
    //   542: iload #4
    //   544: sipush #16468
    //   547: ldc2_w 3606889469862882004
    //   550: lload #5
    //   552: lxor
    //   553: <illegal opcode> u : (IJ)I
    //   558: if_icmpne -> 612
    //   561: aload_0
    //   562: iconst_1
    //   563: invokestatic method_25442 : ()Z
    //   566: aload #14
    //   568: lload #7
    //   570: iconst_4
    //   571: anewarray java/lang/Object
    //   574: dup_x2
    //   575: dup_x2
    //   576: pop
    //   577: invokestatic valueOf : (J)Ljava/lang/Long;
    //   580: iconst_3
    //   581: swap
    //   582: aastore
    //   583: dup_x1
    //   584: swap
    //   585: iconst_2
    //   586: swap
    //   587: aastore
    //   588: dup_x1
    //   589: swap
    //   590: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   593: iconst_1
    //   594: swap
    //   595: aastore
    //   596: dup_x1
    //   597: swap
    //   598: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   601: iconst_0
    //   602: swap
    //   603: aastore
    //   604: invokevirtual K : ([Ljava/lang/Object;)V
    //   607: return
    //   608: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   611: athrow
    //   612: return
    // Exception table:
    //   from	to	target	type
    //   73	85	88	wtf/opal/x5
    //   98	113	116	wtf/opal/x5
    //   110	150	150	wtf/opal/x5
    //   159	174	177	wtf/opal/x5
    //   171	221	221	wtf/opal/x5
    //   230	245	248	wtf/opal/x5
    //   242	272	272	wtf/opal/x5
    //   279	288	288	wtf/opal/x5
    //   297	328	331	wtf/opal/x5
    //   325	371	371	wtf/opal/x5
    //   391	406	409	wtf/opal/x5
    //   403	449	449	wtf/opal/x5
    //   469	484	487	wtf/opal/x5
    //   481	538	538	wtf/opal/x5
    //   558	608	608	wtf/opal/x5
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
    //   55: ldc2_w 58562124928931
    //   58: lxor
    //   59: lstore #11
    //   61: pop2
    //   62: invokestatic S : ()[Lwtf/opal/d;
    //   65: astore #13
    //   67: aload_0
    //   68: iload #8
    //   70: aload #13
    //   72: ifnull -> 195
    //   75: ifne -> 214
    //   78: goto -> 85
    //   81: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   84: athrow
    //   85: aload_0
    //   86: getfield U : F
    //   89: ldc 10.0
    //   91: fadd
    //   92: lload #9
    //   94: dup2_x1
    //   95: pop2
    //   96: aload_0
    //   97: getfield t : F
    //   100: ldc 12.0
    //   102: fadd
    //   103: aload_0
    //   104: getfield e : F
    //   107: ldc 20.0
    //   109: fsub
    //   110: aload_0
    //   111: getfield m : F
    //   114: ldc 15.0
    //   116: fsub
    //   117: dload_2
    //   118: dload #4
    //   120: bipush #7
    //   122: anewarray java/lang/Object
    //   125: dup_x2
    //   126: dup_x2
    //   127: pop
    //   128: invokestatic valueOf : (D)Ljava/lang/Double;
    //   131: bipush #6
    //   133: swap
    //   134: aastore
    //   135: dup_x2
    //   136: dup_x2
    //   137: pop
    //   138: invokestatic valueOf : (D)Ljava/lang/Double;
    //   141: iconst_5
    //   142: swap
    //   143: aastore
    //   144: dup_x1
    //   145: swap
    //   146: invokestatic valueOf : (F)Ljava/lang/Float;
    //   149: iconst_4
    //   150: swap
    //   151: aastore
    //   152: dup_x1
    //   153: swap
    //   154: invokestatic valueOf : (F)Ljava/lang/Float;
    //   157: iconst_3
    //   158: swap
    //   159: aastore
    //   160: dup_x1
    //   161: swap
    //   162: invokestatic valueOf : (F)Ljava/lang/Float;
    //   165: iconst_2
    //   166: swap
    //   167: aastore
    //   168: dup_x1
    //   169: swap
    //   170: invokestatic valueOf : (F)Ljava/lang/Float;
    //   173: iconst_1
    //   174: swap
    //   175: aastore
    //   176: dup_x2
    //   177: dup_x2
    //   178: pop
    //   179: invokestatic valueOf : (J)Ljava/lang/Long;
    //   182: iconst_0
    //   183: swap
    //   184: aastore
    //   185: invokestatic Z : ([Ljava/lang/Object;)Z
    //   188: goto -> 195
    //   191: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   194: athrow
    //   195: aload #13
    //   197: ifnull -> 211
    //   200: ifeq -> 214
    //   203: goto -> 210
    //   206: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   209: athrow
    //   210: iconst_1
    //   211: goto -> 215
    //   214: iconst_0
    //   215: putfield y : Z
    //   218: aload_0
    //   219: getfield y : Z
    //   222: putstatic wtf/opal/x9.Q : Z
    //   225: aload_0
    //   226: getfield y : Z
    //   229: ifeq -> 475
    //   232: dload_2
    //   233: aload_0
    //   234: getfield U : F
    //   237: ldc 12.0
    //   239: fadd
    //   240: f2d
    //   241: dsub
    //   242: dstore #14
    //   244: fconst_0
    //   245: fstore #16
    //   247: iconst_0
    //   248: istore #17
    //   250: iload #17
    //   252: aload_0
    //   253: iconst_0
    //   254: anewarray java/lang/Object
    //   257: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   260: checkcast wtf/opal/kl
    //   263: invokevirtual z : ()Ljava/lang/Object;
    //   266: checkcast java/lang/String
    //   269: invokevirtual length : ()I
    //   272: if_icmpge -> 431
    //   275: getstatic wtf/opal/bg.Z : Lwtf/opal/bu;
    //   278: aload_0
    //   279: iconst_0
    //   280: anewarray java/lang/Object
    //   283: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   286: checkcast wtf/opal/kl
    //   289: invokevirtual z : ()Ljava/lang/Object;
    //   292: checkcast java/lang/String
    //   295: iload #17
    //   297: invokevirtual charAt : (I)C
    //   300: invokestatic valueOf : (C)Ljava/lang/String;
    //   303: ldc 7.0
    //   305: lload #11
    //   307: iconst_3
    //   308: anewarray java/lang/Object
    //   311: dup_x2
    //   312: dup_x2
    //   313: pop
    //   314: invokestatic valueOf : (J)Ljava/lang/Long;
    //   317: iconst_2
    //   318: swap
    //   319: aastore
    //   320: dup_x1
    //   321: swap
    //   322: invokestatic valueOf : (F)Ljava/lang/Float;
    //   325: iconst_1
    //   326: swap
    //   327: aastore
    //   328: dup_x1
    //   329: swap
    //   330: iconst_0
    //   331: swap
    //   332: aastore
    //   333: invokevirtual s : ([Ljava/lang/Object;)F
    //   336: fstore #18
    //   338: aload #13
    //   340: lload #6
    //   342: lconst_0
    //   343: lcmp
    //   344: iflt -> 352
    //   347: ifnull -> 467
    //   350: aload #13
    //   352: lload #6
    //   354: lconst_0
    //   355: lcmp
    //   356: ifle -> 428
    //   359: ifnull -> 426
    //   362: goto -> 369
    //   365: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   368: athrow
    //   369: lload #6
    //   371: lconst_0
    //   372: lcmp
    //   373: iflt -> 423
    //   376: dload #14
    //   378: fload #16
    //   380: fload #18
    //   382: fconst_2
    //   383: fdiv
    //   384: fadd
    //   385: f2d
    //   386: dcmpg
    //   387: ifge -> 416
    //   390: goto -> 397
    //   393: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   396: athrow
    //   397: lload #6
    //   399: lconst_0
    //   400: lcmp
    //   401: iflt -> 467
    //   404: aload #13
    //   406: ifnonnull -> 431
    //   409: goto -> 416
    //   412: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   415: athrow
    //   416: fload #16
    //   418: fload #18
    //   420: fadd
    //   421: fstore #16
    //   423: iinc #17, 1
    //   426: aload #13
    //   428: ifnonnull -> 250
    //   431: aload_0
    //   432: iload #17
    //   434: aload_0
    //   435: iconst_0
    //   436: anewarray java/lang/Object
    //   439: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   442: checkcast wtf/opal/kl
    //   445: invokevirtual z : ()Ljava/lang/Object;
    //   448: checkcast java/lang/String
    //   451: invokevirtual length : ()I
    //   454: invokestatic min : (II)I
    //   457: putfield c : I
    //   460: lload #6
    //   462: lconst_0
    //   463: lcmp
    //   464: ifle -> 467
    //   467: aload_0
    //   468: aload_0
    //   469: getfield c : I
    //   472: putfield K : I
    //   475: return
    // Exception table:
    //   from	to	target	type
    //   67	78	81	wtf/opal/x5
    //   75	188	191	wtf/opal/x5
    //   195	203	206	wtf/opal/x5
    //   338	362	365	wtf/opal/x5
    //   350	390	393	wtf/opal/x5
    //   369	409	412	wtf/opal/x5
  }
  
  public void d(Object[] paramArrayOfObject) {
    int j = ((Integer)paramArrayOfObject[0]).intValue();
    int i = ((Integer)paramArrayOfObject[1]).intValue();
    long l1 = ((Long)paramArrayOfObject[2]).longValue();
    long l2 = l1 ^ 0x49AD11729550L;
    d[] arrayOfD = S();
    try {
      if (arrayOfD != null)
        try {
          if (this.y) {
          
          } else {
            return;
          } 
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    if (this.y) {
      new Object[2];
      b(new Object[] { null, Long.valueOf(l2), Character.toString(j) });
      return;
    } 
  }
  
  private void r(Object[] paramArrayOfObject) {
    long l = ((Long)paramArrayOfObject[0]).longValue();
    boolean bool = ((Boolean)paramArrayOfObject[1]).booleanValue();
    l = b ^ l;
    try {
      if (!bool)
        this.K = this.c; 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  public void a(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    int i = ((Integer)paramArrayOfObject[1]).intValue();
    boolean bool = ((Boolean)paramArrayOfObject[2]).booleanValue();
    l1 = b ^ l1;
    long l2 = l1 ^ 0x6B110CDA5AD3L;
    this.c = class_156.method_27761(((kl)h(new Object[0])).z(), this.c, i);
    (new Object[2])[1] = Boolean.valueOf(bool);
    new Object[2];
    r(new Object[] { Long.valueOf(l2) });
  }
  
  public void V(Object[] paramArrayOfObject) {
    int i = ((Integer)paramArrayOfObject[0]).intValue();
    long l1 = ((Long)paramArrayOfObject[1]).longValue();
    boolean bool = ((Boolean)paramArrayOfObject[2]).booleanValue();
    l1 = b ^ l1;
    long l2 = l1 ^ 0x629729E3DE24L;
    this.c = class_5225.method_27483(((kl)h(new Object[0])).z(), i, this.c, true);
    (new Object[2])[1] = Boolean.valueOf(bool);
    new Object[2];
    r(new Object[] { Long.valueOf(l2) });
  }
  
  public void s(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    l1 = b ^ l1;
    long l2 = l1 ^ 0x171BE7E291F7L;
    new Object[2];
    b(new Object[] { null, Long.valueOf(l2), b9.c.field_1774.method_1460() });
    this.K = this.c;
  }
  
  public void K(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Integer
    //   7: invokevirtual intValue : ()I
    //   10: istore_2
    //   11: dup
    //   12: iconst_1
    //   13: aaload
    //   14: checkcast java/lang/Boolean
    //   17: invokevirtual booleanValue : ()Z
    //   20: istore_3
    //   21: dup
    //   22: iconst_2
    //   23: aaload
    //   24: checkcast net/minecraft/class_3728$class_7279
    //   27: astore #6
    //   29: dup
    //   30: iconst_3
    //   31: aaload
    //   32: checkcast java/lang/Long
    //   35: invokevirtual longValue : ()J
    //   38: lstore #4
    //   40: pop
    //   41: getstatic wtf/opal/bg.b : J
    //   44: lload #4
    //   46: lxor
    //   47: lstore #4
    //   49: lload #4
    //   51: dup2
    //   52: ldc2_w 60523676810807
    //   55: lxor
    //   56: lstore #7
    //   58: dup2
    //   59: ldc2_w 68779094063808
    //   62: lxor
    //   63: lstore #9
    //   65: pop2
    //   66: invokestatic S : ()[Lwtf/opal/d;
    //   69: astore #11
    //   71: aload #11
    //   73: ifnull -> 160
    //   76: getstatic wtf/opal/p5.S : [I
    //   79: aload #6
    //   81: invokevirtual ordinal : ()I
    //   84: iaload
    //   85: lookupswitch default -> 216, 1 -> 116, 2 -> 172
    //   112: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   115: athrow
    //   116: aload_0
    //   117: lload #9
    //   119: iload_2
    //   120: iload_3
    //   121: iconst_3
    //   122: anewarray java/lang/Object
    //   125: dup_x1
    //   126: swap
    //   127: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   130: iconst_2
    //   131: swap
    //   132: aastore
    //   133: dup_x1
    //   134: swap
    //   135: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   138: iconst_1
    //   139: swap
    //   140: aastore
    //   141: dup_x2
    //   142: dup_x2
    //   143: pop
    //   144: invokestatic valueOf : (J)Ljava/lang/Long;
    //   147: iconst_0
    //   148: swap
    //   149: aastore
    //   150: invokevirtual a : ([Ljava/lang/Object;)V
    //   153: goto -> 160
    //   156: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   159: athrow
    //   160: lload #4
    //   162: lconst_0
    //   163: lcmp
    //   164: ifle -> 209
    //   167: aload #11
    //   169: ifnonnull -> 216
    //   172: aload_0
    //   173: iload_2
    //   174: lload #7
    //   176: iload_3
    //   177: iconst_3
    //   178: anewarray java/lang/Object
    //   181: dup_x1
    //   182: swap
    //   183: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   186: iconst_2
    //   187: swap
    //   188: aastore
    //   189: dup_x2
    //   190: dup_x2
    //   191: pop
    //   192: invokestatic valueOf : (J)Ljava/lang/Long;
    //   195: iconst_1
    //   196: swap
    //   197: aastore
    //   198: dup_x1
    //   199: swap
    //   200: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   203: iconst_0
    //   204: swap
    //   205: aastore
    //   206: invokevirtual V : ([Ljava/lang/Object;)V
    //   209: goto -> 216
    //   212: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   215: athrow
    //   216: return
    // Exception table:
    //   from	to	target	type
    //   71	112	112	wtf/opal/x5
    //   76	153	156	wtf/opal/x5
    //   160	209	212	wtf/opal/x5
  }
  
  private void b(Object[] paramArrayOfObject) {
    String str1 = (String)paramArrayOfObject[0];
    long l1 = ((Long)paramArrayOfObject[1]).longValue();
    l1 = b ^ l1;
    long l2 = l1 ^ 0x218C47D4E2E1L;
    String str2 = ((kl)h(new Object[0])).z();
    d[] arrayOfD = S();
    try {
      if (arrayOfD != null)
        if (this.K != this.c) {
          new Object[2];
          str2 = m(new Object[] { null, Long.valueOf(l2), str2 });
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    this.c = class_3532.method_15340(this.c, 0, str2.length());
    String str3 = (new StringBuilder(str2)).insert(this.c, str1).toString();
    ((kl)h(new Object[0])).V(new Object[] { str3 });
    this.K = this.c = Math.min(str3.length(), this.c + str1.length());
  }
  
  public void M(Object[] paramArrayOfObject) {
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
    //   14: checkcast java/lang/Integer
    //   17: invokevirtual intValue : ()I
    //   20: istore #5
    //   22: dup
    //   23: iconst_2
    //   24: aaload
    //   25: checkcast net/minecraft/class_3728$class_7279
    //   28: astore #4
    //   30: pop
    //   31: getstatic wtf/opal/bg.b : J
    //   34: lload_2
    //   35: lxor
    //   36: lstore_2
    //   37: lload_2
    //   38: dup2
    //   39: ldc2_w 26279593745365
    //   42: lxor
    //   43: lstore #6
    //   45: dup2
    //   46: ldc2_w 125381327136235
    //   49: lxor
    //   50: lstore #8
    //   52: pop2
    //   53: invokestatic S : ()[Lwtf/opal/d;
    //   56: astore #10
    //   58: aload #10
    //   60: ifnull -> 140
    //   63: getstatic wtf/opal/p5.S : [I
    //   66: aload #4
    //   68: invokevirtual ordinal : ()I
    //   71: iaload
    //   72: lookupswitch default -> 187, 1 -> 104, 2 -> 151
    //   100: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   103: athrow
    //   104: aload_0
    //   105: iload #5
    //   107: lload #6
    //   109: iconst_2
    //   110: anewarray java/lang/Object
    //   113: dup_x2
    //   114: dup_x2
    //   115: pop
    //   116: invokestatic valueOf : (J)Ljava/lang/Long;
    //   119: iconst_1
    //   120: swap
    //   121: aastore
    //   122: dup_x1
    //   123: swap
    //   124: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   127: iconst_0
    //   128: swap
    //   129: aastore
    //   130: invokevirtual F : ([Ljava/lang/Object;)V
    //   133: goto -> 140
    //   136: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   139: athrow
    //   140: lload_2
    //   141: lconst_0
    //   142: lcmp
    //   143: ifle -> 180
    //   146: aload #10
    //   148: ifnonnull -> 187
    //   151: aload_0
    //   152: iload #5
    //   154: lload #8
    //   156: iconst_2
    //   157: anewarray java/lang/Object
    //   160: dup_x2
    //   161: dup_x2
    //   162: pop
    //   163: invokestatic valueOf : (J)Ljava/lang/Long;
    //   166: iconst_1
    //   167: swap
    //   168: aastore
    //   169: dup_x1
    //   170: swap
    //   171: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   174: iconst_0
    //   175: swap
    //   176: aastore
    //   177: invokevirtual D : ([Ljava/lang/Object;)V
    //   180: goto -> 187
    //   183: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   186: athrow
    //   187: return
    // Exception table:
    //   from	to	target	type
    //   58	100	100	wtf/opal/x5
    //   63	133	136	wtf/opal/x5
    //   140	180	183	wtf/opal/x5
  }
  
  public void D(Object[] paramArrayOfObject) {
    int i = ((Integer)paramArrayOfObject[0]).intValue();
    long l1 = ((Long)paramArrayOfObject[1]).longValue();
    l1 = b ^ l1;
    long l2 = l1 ^ 0x2EFBED053C48L;
    int j = class_5225.method_27483(((kl)h(new Object[0])).z(), i, this.c, true);
    new Object[2];
    F(new Object[] { null, Long.valueOf(l2), Integer.valueOf(j - this.c) });
  }
  
  public void F(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Integer
    //   7: invokevirtual intValue : ()I
    //   10: istore_2
    //   11: dup
    //   12: iconst_1
    //   13: aaload
    //   14: checkcast java/lang/Long
    //   17: invokevirtual longValue : ()J
    //   20: lstore_3
    //   21: pop
    //   22: getstatic wtf/opal/bg.b : J
    //   25: lload_3
    //   26: lxor
    //   27: lstore_3
    //   28: lload_3
    //   29: dup2
    //   30: ldc2_w 59851284559504
    //   33: lxor
    //   34: lstore #5
    //   36: pop2
    //   37: invokestatic S : ()[Lwtf/opal/d;
    //   40: astore #7
    //   42: aload_0
    //   43: iconst_0
    //   44: anewarray java/lang/Object
    //   47: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   50: checkcast wtf/opal/kl
    //   53: invokevirtual z : ()Ljava/lang/Object;
    //   56: checkcast java/lang/String
    //   59: invokevirtual isEmpty : ()Z
    //   62: aload #7
    //   64: ifnull -> 88
    //   67: ifne -> 306
    //   70: goto -> 77
    //   73: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   76: athrow
    //   77: aload_0
    //   78: getfield K : I
    //   81: goto -> 88
    //   84: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   87: athrow
    //   88: aload #7
    //   90: ifnull -> 193
    //   93: aload_0
    //   94: getfield c : I
    //   97: if_icmpeq -> 161
    //   100: goto -> 107
    //   103: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   106: athrow
    //   107: aload_0
    //   108: aload_0
    //   109: iconst_0
    //   110: anewarray java/lang/Object
    //   113: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   116: checkcast wtf/opal/kl
    //   119: invokevirtual z : ()Ljava/lang/Object;
    //   122: checkcast java/lang/String
    //   125: lload #5
    //   127: iconst_2
    //   128: anewarray java/lang/Object
    //   131: dup_x2
    //   132: dup_x2
    //   133: pop
    //   134: invokestatic valueOf : (J)Ljava/lang/Long;
    //   137: iconst_1
    //   138: swap
    //   139: aastore
    //   140: dup_x1
    //   141: swap
    //   142: iconst_0
    //   143: swap
    //   144: aastore
    //   145: invokevirtual m : ([Ljava/lang/Object;)Ljava/lang/String;
    //   148: astore #8
    //   150: aload #7
    //   152: lload_3
    //   153: lconst_0
    //   154: lcmp
    //   155: ifle -> 175
    //   158: ifnonnull -> 281
    //   161: aload_0
    //   162: iconst_0
    //   163: anewarray java/lang/Object
    //   166: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   169: checkcast wtf/opal/kl
    //   172: invokevirtual z : ()Ljava/lang/Object;
    //   175: checkcast java/lang/String
    //   178: aload_0
    //   179: getfield c : I
    //   182: iload_2
    //   183: invokestatic method_27761 : (Ljava/lang/String;II)I
    //   186: goto -> 193
    //   189: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   192: athrow
    //   193: istore #9
    //   195: iload #9
    //   197: aload_0
    //   198: getfield c : I
    //   201: invokestatic min : (II)I
    //   204: istore #10
    //   206: iload #9
    //   208: aload_0
    //   209: getfield c : I
    //   212: invokestatic max : (II)I
    //   215: istore #11
    //   217: new java/lang/StringBuilder
    //   220: dup
    //   221: aload_0
    //   222: iconst_0
    //   223: anewarray java/lang/Object
    //   226: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   229: checkcast wtf/opal/kl
    //   232: invokevirtual z : ()Ljava/lang/Object;
    //   235: checkcast java/lang/String
    //   238: invokespecial <init> : (Ljava/lang/String;)V
    //   241: iload #10
    //   243: iload #11
    //   245: invokevirtual delete : (II)Ljava/lang/StringBuilder;
    //   248: invokevirtual toString : ()Ljava/lang/String;
    //   251: astore #8
    //   253: lload_3
    //   254: lconst_0
    //   255: lcmp
    //   256: iflt -> 274
    //   259: iload_2
    //   260: ifge -> 281
    //   263: aload_0
    //   264: aload_0
    //   265: iload #10
    //   267: dup_x1
    //   268: putfield c : I
    //   271: putfield K : I
    //   274: goto -> 281
    //   277: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   280: athrow
    //   281: aload_0
    //   282: iconst_0
    //   283: anewarray java/lang/Object
    //   286: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   289: checkcast wtf/opal/kl
    //   292: aload #8
    //   294: iconst_1
    //   295: anewarray java/lang/Object
    //   298: dup_x1
    //   299: swap
    //   300: iconst_0
    //   301: swap
    //   302: aastore
    //   303: invokevirtual V : ([Ljava/lang/Object;)V
    //   306: return
    // Exception table:
    //   from	to	target	type
    //   42	70	73	wtf/opal/x5
    //   67	81	84	wtf/opal/x5
    //   88	100	103	wtf/opal/x5
    //   150	186	189	wtf/opal/x5
    //   253	274	277	wtf/opal/x5
  }
  
  private String m(Object[] paramArrayOfObject) {
    String str = (String)paramArrayOfObject[0];
    long l = ((Long)paramArrayOfObject[1]).longValue();
    l = b ^ l;
    d[] arrayOfD = S();
    try {
      if (arrayOfD != null)
        try {
          if (this.K == this.c)
            return str; 
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    int i = Math.min(this.c, this.K);
    int j = Math.max(this.c, this.K);
    this.K = this.c = i;
    return str.substring(0, i) + str.substring(0, i);
  }
  
  private String p(Object[] paramArrayOfObject) {
    String str = (String)paramArrayOfObject[0];
    int i = Math.min(this.c, this.K);
    int j = Math.max(this.c, this.K);
    return str.substring(i, j);
  }
  
  public void c(Object[] paramArrayOfObject) {
    long l = ((Long)paramArrayOfObject[0]).longValue();
    double d1 = ((Double)paramArrayOfObject[1]).doubleValue();
    double d2 = ((Double)paramArrayOfObject[2]).doubleValue();
    int i = ((Integer)paramArrayOfObject[3]).intValue();
  }
  
  public void u() {}
  
  public static void r(d[] paramArrayOfd) {
    g = paramArrayOfd;
  }
  
  public static d[] S() {
    return g;
  }
  
  static {
    // Byte code:
    //   0: ldc2_w 3448054821174595431
    //   3: ldc2_w 8572759777620904343
    //   6: invokestatic lookup : ()Ljava/lang/invoke/MethodHandles$Lookup;
    //   9: invokevirtual lookupClass : ()Ljava/lang/Class;
    //   12: invokestatic a : (JJLjava/lang/Object;)Lwtf/opal/t5;
    //   15: ldc2_w 60667242001736
    //   18: invokeinterface a : (J)J
    //   23: putstatic wtf/opal/bg.b : J
    //   26: iconst_5
    //   27: anewarray wtf/opal/d
    //   30: new java/util/HashMap
    //   33: dup
    //   34: bipush #13
    //   36: invokespecial <init> : (I)V
    //   39: putstatic wtf/opal/bg.j : Ljava/util/Map;
    //   42: invokestatic r : ([Lwtf/opal/d;)V
    //   45: getstatic wtf/opal/bg.b : J
    //   48: ldc2_w 56276606017582
    //   51: lxor
    //   52: lstore_0
    //   53: ldc_w 'DES/CBC/NoPadding'
    //   56: invokestatic getInstance : (Ljava/lang/String;)Ljavax/crypto/Cipher;
    //   59: dup
    //   60: astore_2
    //   61: iconst_2
    //   62: ldc_w 'DES'
    //   65: invokestatic getInstance : (Ljava/lang/String;)Ljavax/crypto/SecretKeyFactory;
    //   68: bipush #8
    //   70: newarray byte
    //   72: dup
    //   73: iconst_0
    //   74: lload_0
    //   75: bipush #56
    //   77: lushr
    //   78: l2i
    //   79: i2b
    //   80: bastore
    //   81: iconst_1
    //   82: istore_3
    //   83: iload_3
    //   84: bipush #8
    //   86: if_icmpge -> 109
    //   89: dup
    //   90: iload_3
    //   91: lload_0
    //   92: iload_3
    //   93: bipush #8
    //   95: imul
    //   96: lshl
    //   97: bipush #56
    //   99: lushr
    //   100: l2i
    //   101: i2b
    //   102: bastore
    //   103: iinc #3, 1
    //   106: goto -> 83
    //   109: new javax/crypto/spec/DESKeySpec
    //   112: dup_x1
    //   113: swap
    //   114: invokespecial <init> : ([B)V
    //   117: invokevirtual generateSecret : (Ljava/security/spec/KeySpec;)Ljavax/crypto/SecretKey;
    //   120: new javax/crypto/spec/IvParameterSpec
    //   123: dup
    //   124: bipush #8
    //   126: newarray byte
    //   128: invokespecial <init> : ([B)V
    //   131: invokevirtual init : (ILjava/security/Key;Ljava/security/spec/AlgorithmParameterSpec;)V
    //   134: bipush #10
    //   136: newarray long
    //   138: astore #8
    //   140: iconst_0
    //   141: istore #5
    //   143: ldc_w 'ï Uåô~¼rókä(¿.*ðÊëåW$:ê¥ÇÛvÕê¯ñK\\tÞeàÒ¬)lh{±q÷*ÜöE'
    //   146: dup
    //   147: astore #6
    //   149: invokevirtual length : ()I
    //   152: istore #7
    //   154: iconst_0
    //   155: istore #4
    //   157: aload #6
    //   159: iload #4
    //   161: iinc #4, 8
    //   164: iload #4
    //   166: invokevirtual substring : (II)Ljava/lang/String;
    //   169: ldc_w 'ISO-8859-1'
    //   172: invokevirtual getBytes : (Ljava/lang/String;)[B
    //   175: astore #9
    //   177: aload #8
    //   179: iload #5
    //   181: iinc #5, 1
    //   184: aload #9
    //   186: iconst_0
    //   187: baload
    //   188: i2l
    //   189: ldc2_w 255
    //   192: land
    //   193: bipush #56
    //   195: lshl
    //   196: aload #9
    //   198: iconst_1
    //   199: baload
    //   200: i2l
    //   201: ldc2_w 255
    //   204: land
    //   205: bipush #48
    //   207: lshl
    //   208: lor
    //   209: aload #9
    //   211: iconst_2
    //   212: baload
    //   213: i2l
    //   214: ldc2_w 255
    //   217: land
    //   218: bipush #40
    //   220: lshl
    //   221: lor
    //   222: aload #9
    //   224: iconst_3
    //   225: baload
    //   226: i2l
    //   227: ldc2_w 255
    //   230: land
    //   231: bipush #32
    //   233: lshl
    //   234: lor
    //   235: aload #9
    //   237: iconst_4
    //   238: baload
    //   239: i2l
    //   240: ldc2_w 255
    //   243: land
    //   244: bipush #24
    //   246: lshl
    //   247: lor
    //   248: aload #9
    //   250: iconst_5
    //   251: baload
    //   252: i2l
    //   253: ldc2_w 255
    //   256: land
    //   257: bipush #16
    //   259: lshl
    //   260: lor
    //   261: aload #9
    //   263: bipush #6
    //   265: baload
    //   266: i2l
    //   267: ldc2_w 255
    //   270: land
    //   271: bipush #8
    //   273: lshl
    //   274: lor
    //   275: aload #9
    //   277: bipush #7
    //   279: baload
    //   280: i2l
    //   281: ldc2_w 255
    //   284: land
    //   285: lor
    //   286: iconst_m1
    //   287: goto -> 469
    //   290: lastore
    //   291: iload #4
    //   293: iload #7
    //   295: if_icmplt -> 157
    //   298: ldc_w '!Ü\\f# Òÿ¾à|o|pq'
    //   301: dup
    //   302: astore #6
    //   304: invokevirtual length : ()I
    //   307: istore #7
    //   309: iconst_0
    //   310: istore #4
    //   312: aload #6
    //   314: iload #4
    //   316: iinc #4, 8
    //   319: iload #4
    //   321: invokevirtual substring : (II)Ljava/lang/String;
    //   324: ldc_w 'ISO-8859-1'
    //   327: invokevirtual getBytes : (Ljava/lang/String;)[B
    //   330: astore #9
    //   332: aload #8
    //   334: iload #5
    //   336: iinc #5, 1
    //   339: aload #9
    //   341: iconst_0
    //   342: baload
    //   343: i2l
    //   344: ldc2_w 255
    //   347: land
    //   348: bipush #56
    //   350: lshl
    //   351: aload #9
    //   353: iconst_1
    //   354: baload
    //   355: i2l
    //   356: ldc2_w 255
    //   359: land
    //   360: bipush #48
    //   362: lshl
    //   363: lor
    //   364: aload #9
    //   366: iconst_2
    //   367: baload
    //   368: i2l
    //   369: ldc2_w 255
    //   372: land
    //   373: bipush #40
    //   375: lshl
    //   376: lor
    //   377: aload #9
    //   379: iconst_3
    //   380: baload
    //   381: i2l
    //   382: ldc2_w 255
    //   385: land
    //   386: bipush #32
    //   388: lshl
    //   389: lor
    //   390: aload #9
    //   392: iconst_4
    //   393: baload
    //   394: i2l
    //   395: ldc2_w 255
    //   398: land
    //   399: bipush #24
    //   401: lshl
    //   402: lor
    //   403: aload #9
    //   405: iconst_5
    //   406: baload
    //   407: i2l
    //   408: ldc2_w 255
    //   411: land
    //   412: bipush #16
    //   414: lshl
    //   415: lor
    //   416: aload #9
    //   418: bipush #6
    //   420: baload
    //   421: i2l
    //   422: ldc2_w 255
    //   425: land
    //   426: bipush #8
    //   428: lshl
    //   429: lor
    //   430: aload #9
    //   432: bipush #7
    //   434: baload
    //   435: i2l
    //   436: ldc2_w 255
    //   439: land
    //   440: lor
    //   441: iconst_0
    //   442: goto -> 469
    //   445: lastore
    //   446: iload #4
    //   448: iload #7
    //   450: if_icmplt -> 312
    //   453: aload #8
    //   455: putstatic wtf/opal/bg.d : [J
    //   458: bipush #10
    //   460: anewarray java/lang/Integer
    //   463: putstatic wtf/opal/bg.f : [Ljava/lang/Integer;
    //   466: goto -> 684
    //   469: dup_x2
    //   470: pop
    //   471: lstore #10
    //   473: bipush #8
    //   475: newarray byte
    //   477: dup
    //   478: iconst_0
    //   479: lload #10
    //   481: bipush #56
    //   483: lushr
    //   484: l2i
    //   485: i2b
    //   486: bastore
    //   487: dup
    //   488: iconst_1
    //   489: lload #10
    //   491: bipush #48
    //   493: lushr
    //   494: l2i
    //   495: i2b
    //   496: bastore
    //   497: dup
    //   498: iconst_2
    //   499: lload #10
    //   501: bipush #40
    //   503: lushr
    //   504: l2i
    //   505: i2b
    //   506: bastore
    //   507: dup
    //   508: iconst_3
    //   509: lload #10
    //   511: bipush #32
    //   513: lushr
    //   514: l2i
    //   515: i2b
    //   516: bastore
    //   517: dup
    //   518: iconst_4
    //   519: lload #10
    //   521: bipush #24
    //   523: lushr
    //   524: l2i
    //   525: i2b
    //   526: bastore
    //   527: dup
    //   528: iconst_5
    //   529: lload #10
    //   531: bipush #16
    //   533: lushr
    //   534: l2i
    //   535: i2b
    //   536: bastore
    //   537: dup
    //   538: bipush #6
    //   540: lload #10
    //   542: bipush #8
    //   544: lushr
    //   545: l2i
    //   546: i2b
    //   547: bastore
    //   548: dup
    //   549: bipush #7
    //   551: lload #10
    //   553: l2i
    //   554: i2b
    //   555: bastore
    //   556: aload_2
    //   557: swap
    //   558: invokevirtual doFinal : ([B)[B
    //   561: astore #12
    //   563: aload #12
    //   565: iconst_0
    //   566: baload
    //   567: i2l
    //   568: ldc2_w 255
    //   571: land
    //   572: bipush #56
    //   574: lshl
    //   575: aload #12
    //   577: iconst_1
    //   578: baload
    //   579: i2l
    //   580: ldc2_w 255
    //   583: land
    //   584: bipush #48
    //   586: lshl
    //   587: lor
    //   588: aload #12
    //   590: iconst_2
    //   591: baload
    //   592: i2l
    //   593: ldc2_w 255
    //   596: land
    //   597: bipush #40
    //   599: lshl
    //   600: lor
    //   601: aload #12
    //   603: iconst_3
    //   604: baload
    //   605: i2l
    //   606: ldc2_w 255
    //   609: land
    //   610: bipush #32
    //   612: lshl
    //   613: lor
    //   614: aload #12
    //   616: iconst_4
    //   617: baload
    //   618: i2l
    //   619: ldc2_w 255
    //   622: land
    //   623: bipush #24
    //   625: lshl
    //   626: lor
    //   627: aload #12
    //   629: iconst_5
    //   630: baload
    //   631: i2l
    //   632: ldc2_w 255
    //   635: land
    //   636: bipush #16
    //   638: lshl
    //   639: lor
    //   640: aload #12
    //   642: bipush #6
    //   644: baload
    //   645: i2l
    //   646: ldc2_w 255
    //   649: land
    //   650: bipush #8
    //   652: lshl
    //   653: lor
    //   654: aload #12
    //   656: bipush #7
    //   658: baload
    //   659: i2l
    //   660: ldc2_w 255
    //   663: land
    //   664: lor
    //   665: dup2_x1
    //   666: pop2
    //   667: tableswitch default -> 290, 0 -> 445
    //   684: return
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
  
  private static int a(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x766E;
    if (f[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l = d[i];
      byte[] arrayOfByte2 = { (byte)(int)(l >>> 56L), (byte)(int)(l >>> 48L), (byte)(int)(l >>> 40L), (byte)(int)(l >>> 32L), (byte)(int)(l >>> 24L), (byte)(int)(l >>> 16L), (byte)(int)(l >>> 8L), (byte)(int)l };
      Long long_ = Long.valueOf(Thread.currentThread().threadId());
      Object[] arrayOfObject = (Object[])j.get(long_);
      try {
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/NoPadding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          j.put(long_, arrayOfObject);
        } 
        DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
        SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
        Cipher cipher = (Cipher)arrayOfObject[0];
        cipher.init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
        arrayOfByte3 = cipher.doFinal(arrayOfByte2);
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/bg", exception);
      } 
      int j = (arrayOfByte3[4] & 0xFF) << 24 | (arrayOfByte3[5] & 0xFF) << 16 | (arrayOfByte3[6] & 0xFF) << 8 | arrayOfByte3[7] & 0xFF;
      f[i] = Integer.valueOf(j);
    } 
    return f[i].intValue();
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
    //   65: ldc_w 'wtf/opal/bg'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\bg.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */