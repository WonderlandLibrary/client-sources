package wtf.opal;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.class_1299;
import net.minecraft.class_243;
import net.minecraft.class_2596;
import net.minecraft.class_2604;

public final class l extends d {
  private static final ky<dl> s;
  
  private static final List<class_243> Z;
  
  private static final List<UUID> I;
  
  private final gm<lu> P;
  
  private final gm<d4> t;
  
  private static final long a = on.a(-4872393848120448789L, 2402536466886831931L, MethodHandles.lookup().lookupClass()).a(229684135068099L);
  
  private static final String[] b;
  
  private static final String[] d;
  
  private static final Map f = new HashMap<>(13);
  
  private static final long g;
  
  public l(long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/l.a : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: lload_1
    //   7: dup2
    //   8: ldc2_w 69040645483430
    //   11: lxor
    //   12: lstore_3
    //   13: dup2
    //   14: ldc2_w 18366712148723
    //   17: lxor
    //   18: lstore #5
    //   20: pop2
    //   21: aload_0
    //   22: sipush #648
    //   25: ldc2_w 5623539816140334008
    //   28: lload_1
    //   29: lxor
    //   30: <illegal opcode> z : (IJ)Ljava/lang/String;
    //   35: lload #5
    //   37: sipush #3960
    //   40: ldc2_w 4398018292253421129
    //   43: lload_1
    //   44: lxor
    //   45: <illegal opcode> z : (IJ)Ljava/lang/String;
    //   50: getstatic wtf/opal/kn.COMBAT : Lwtf/opal/kn;
    //   53: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   56: aload_0
    //   57: <illegal opcode> H : ()Lwtf/opal/gm;
    //   62: putfield P : Lwtf/opal/gm;
    //   65: aload_0
    //   66: <illegal opcode> H : ()Lwtf/opal/gm;
    //   71: putfield t : Lwtf/opal/gm;
    //   74: aload_0
    //   75: iconst_1
    //   76: anewarray wtf/opal/k3
    //   79: dup
    //   80: iconst_0
    //   81: getstatic wtf/opal/l.s : Lwtf/opal/ky;
    //   84: aastore
    //   85: lload_3
    //   86: dup2_x1
    //   87: pop2
    //   88: iconst_2
    //   89: anewarray java/lang/Object
    //   92: dup_x1
    //   93: swap
    //   94: iconst_1
    //   95: swap
    //   96: aastore
    //   97: dup_x2
    //   98: dup_x2
    //   99: pop
    //   100: invokestatic valueOf : (J)Ljava/lang/Long;
    //   103: iconst_0
    //   104: swap
    //   105: aastore
    //   106: invokevirtual o : ([Ljava/lang/Object;)V
    //   109: return
  }
  
  public String o(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    return ((dl)s.z()).toString();
  }
  
  public static boolean K(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast net/minecraft/class_1297
    //   7: astore_3
    //   8: dup
    //   9: iconst_1
    //   10: aaload
    //   11: checkcast java/lang/Long
    //   14: invokevirtual longValue : ()J
    //   17: lstore_1
    //   18: pop
    //   19: getstatic wtf/opal/l.a : J
    //   22: lload_1
    //   23: lxor
    //   24: lstore_1
    //   25: lload_1
    //   26: dup2
    //   27: ldc2_w 30558186996766
    //   30: lxor
    //   31: lstore #4
    //   33: pop2
    //   34: invokestatic y : ()I
    //   37: istore #6
    //   39: aload_3
    //   40: instanceof net/minecraft/class_1309
    //   43: iload #6
    //   45: ifeq -> 83
    //   48: ifeq -> 75
    //   51: goto -> 58
    //   54: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   57: athrow
    //   58: aload_3
    //   59: checkcast net/minecraft/class_1309
    //   62: astore #7
    //   64: lload_1
    //   65: lconst_0
    //   66: lcmp
    //   67: ifle -> 84
    //   70: iload #6
    //   72: ifne -> 84
    //   75: iconst_1
    //   76: goto -> 83
    //   79: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   82: athrow
    //   83: ireturn
    //   84: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   87: getfield field_1724 : Lnet/minecraft/class_746;
    //   90: iload #6
    //   92: lload_1
    //   93: lconst_0
    //   94: lcmp
    //   95: ifle -> 121
    //   98: ifeq -> 113
    //   101: ifnull -> 202
    //   104: goto -> 111
    //   107: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   110: athrow
    //   111: aload #7
    //   113: lload_1
    //   114: lconst_0
    //   115: lcmp
    //   116: ifle -> 149
    //   119: iload #6
    //   121: ifeq -> 149
    //   124: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   127: getfield field_1724 : Lnet/minecraft/class_746;
    //   130: if_acmpeq -> 202
    //   133: goto -> 140
    //   136: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   139: athrow
    //   140: aload #7
    //   142: goto -> 149
    //   145: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   148: athrow
    //   149: invokevirtual method_29504 : ()Z
    //   152: iload #6
    //   154: ifeq -> 203
    //   157: ifne -> 202
    //   160: goto -> 167
    //   163: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   166: athrow
    //   167: aload #7
    //   169: invokevirtual method_6032 : ()F
    //   172: fconst_0
    //   173: fcmpg
    //   174: iload #6
    //   176: lload_1
    //   177: lconst_0
    //   178: lcmp
    //   179: iflt -> 244
    //   182: ifeq -> 242
    //   185: goto -> 192
    //   188: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   191: athrow
    //   192: ifge -> 204
    //   195: goto -> 202
    //   198: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   201: athrow
    //   202: iconst_1
    //   203: ireturn
    //   204: iconst_0
    //   205: anewarray java/lang/Object
    //   208: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   211: iconst_0
    //   212: anewarray java/lang/Object
    //   215: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   218: ldc wtf/opal/l
    //   220: iconst_1
    //   221: anewarray java/lang/Object
    //   224: dup_x1
    //   225: swap
    //   226: iconst_0
    //   227: swap
    //   228: aastore
    //   229: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   232: checkcast wtf/opal/l
    //   235: iconst_0
    //   236: anewarray java/lang/Object
    //   239: invokevirtual D : ([Ljava/lang/Object;)Z
    //   242: iload #6
    //   244: ifeq -> 258
    //   247: ifne -> 259
    //   250: goto -> 257
    //   253: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   256: athrow
    //   257: iconst_0
    //   258: ireturn
    //   259: aload #7
    //   261: invokevirtual method_5477 : ()Lnet/minecraft/class_2561;
    //   264: invokeinterface getString : ()Ljava/lang/String;
    //   269: astore #8
    //   271: aload #8
    //   273: lload_1
    //   274: lconst_0
    //   275: lcmp
    //   276: ifle -> 296
    //   279: iload #6
    //   281: ifeq -> 296
    //   284: ifnull -> 320
    //   287: goto -> 294
    //   290: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   293: athrow
    //   294: aload #8
    //   296: invokevirtual isEmpty : ()Z
    //   299: iload #6
    //   301: lload_1
    //   302: lconst_0
    //   303: lcmp
    //   304: iflt -> 340
    //   307: ifeq -> 338
    //   310: ifeq -> 326
    //   313: goto -> 320
    //   316: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   319: athrow
    //   320: iconst_1
    //   321: ireturn
    //   322: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   325: athrow
    //   326: getstatic wtf/opal/l.s : Lwtf/opal/ky;
    //   329: invokevirtual z : ()Ljava/lang/Object;
    //   332: checkcast wtf/opal/dl
    //   335: invokevirtual ordinal : ()I
    //   338: iload #6
    //   340: ifeq -> 1774
    //   343: lookupswitch default -> 1773, 0 -> 372, 1 -> 1537
    //   368: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   371: athrow
    //   372: aload #7
    //   374: instanceof net/minecraft/class_1531
    //   377: iload #6
    //   379: lload_1
    //   380: lconst_0
    //   381: lcmp
    //   382: ifle -> 476
    //   385: ifeq -> 474
    //   388: goto -> 395
    //   391: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   394: athrow
    //   395: ifeq -> 469
    //   398: goto -> 405
    //   401: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   404: athrow
    //   405: aload #7
    //   407: invokevirtual method_5477 : ()Lnet/minecraft/class_2561;
    //   410: invokeinterface method_10855 : ()Ljava/util/List;
    //   415: astore #9
    //   417: aload #7
    //   419: invokevirtual method_19538 : ()Lnet/minecraft/class_243;
    //   422: astore #10
    //   424: getstatic wtf/opal/l.Z : Ljava/util/List;
    //   427: aload #10
    //   429: invokeinterface contains : (Ljava/lang/Object;)Z
    //   434: iload #6
    //   436: ifeq -> 468
    //   439: ifne -> 467
    //   442: goto -> 449
    //   445: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   448: athrow
    //   449: getstatic wtf/opal/l.Z : Ljava/util/List;
    //   452: aload #10
    //   454: invokeinterface add : (Ljava/lang/Object;)Z
    //   459: pop
    //   460: goto -> 467
    //   463: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   466: athrow
    //   467: iconst_1
    //   468: ireturn
    //   469: aload #7
    //   471: instanceof net/minecraft/class_1528
    //   474: iload #6
    //   476: lload_1
    //   477: lconst_0
    //   478: lcmp
    //   479: iflt -> 512
    //   482: ifeq -> 510
    //   485: ifeq -> 621
    //   488: goto -> 495
    //   491: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   494: athrow
    //   495: aload #7
    //   497: invokevirtual method_6032 : ()F
    //   500: ldc 145.0
    //   502: fcmpl
    //   503: goto -> 510
    //   506: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   509: athrow
    //   510: iload #6
    //   512: lload_1
    //   513: lconst_0
    //   514: lcmp
    //   515: ifle -> 554
    //   518: ifeq -> 546
    //   521: iflt -> 621
    //   524: goto -> 531
    //   527: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   530: athrow
    //   531: aload #7
    //   533: invokevirtual method_6032 : ()F
    //   536: ldc 310.0
    //   538: fcmpg
    //   539: goto -> 546
    //   542: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   545: athrow
    //   546: lload_1
    //   547: lconst_0
    //   548: lcmp
    //   549: iflt -> 606
    //   552: iload #6
    //   554: ifeq -> 606
    //   557: ifgt -> 621
    //   560: goto -> 567
    //   563: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   566: athrow
    //   567: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   570: lload_1
    //   571: lconst_0
    //   572: lcmp
    //   573: ifle -> 624
    //   576: iload #6
    //   578: ifeq -> 624
    //   581: goto -> 588
    //   584: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   587: athrow
    //   588: getfield field_1724 : Lnet/minecraft/class_746;
    //   591: aload #7
    //   593: invokevirtual method_5739 : (Lnet/minecraft/class_1297;)F
    //   596: ldc 5.0
    //   598: fcmpl
    //   599: goto -> 606
    //   602: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   605: athrow
    //   606: lload_1
    //   607: lconst_0
    //   608: lcmp
    //   609: iflt -> 616
    //   612: ifle -> 621
    //   615: iconst_1
    //   616: ireturn
    //   617: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   620: athrow
    //   621: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   624: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   627: ifnull -> 1236
    //   630: aload #7
    //   632: instanceof net/minecraft/class_745
    //   635: iload #6
    //   637: lload_1
    //   638: lconst_0
    //   639: lcmp
    //   640: ifle -> 1253
    //   643: ifeq -> 1251
    //   646: goto -> 653
    //   649: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   652: athrow
    //   653: ifeq -> 1236
    //   656: goto -> 663
    //   659: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   662: athrow
    //   663: aload #7
    //   665: checkcast net/minecraft/class_745
    //   668: astore #9
    //   670: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   673: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   676: aload #9
    //   678: invokevirtual method_5667 : ()Ljava/util/UUID;
    //   681: invokevirtual method_2871 : (Ljava/util/UUID;)Lnet/minecraft/class_640;
    //   684: astore #10
    //   686: aload #10
    //   688: lload_1
    //   689: lconst_0
    //   690: lcmp
    //   691: ifle -> 711
    //   694: iload #6
    //   696: ifeq -> 711
    //   699: ifnull -> 717
    //   702: goto -> 709
    //   705: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   708: athrow
    //   709: aload #10
    //   711: invokevirtual method_2966 : ()Lcom/mojang/authlib/GameProfile;
    //   714: ifnonnull -> 723
    //   717: iconst_1
    //   718: ireturn
    //   719: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   722: athrow
    //   723: aload #9
    //   725: invokevirtual method_5667 : ()Ljava/util/UUID;
    //   728: iload #6
    //   730: ifeq -> 853
    //   733: invokevirtual version : ()I
    //   736: iconst_2
    //   737: if_icmpne -> 848
    //   740: goto -> 747
    //   743: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   746: athrow
    //   747: lload #4
    //   749: iconst_1
    //   750: anewarray java/lang/Object
    //   753: dup_x2
    //   754: dup_x2
    //   755: pop
    //   756: invokestatic valueOf : (J)Ljava/lang/Long;
    //   759: iconst_0
    //   760: swap
    //   761: aastore
    //   762: invokestatic h : ([Ljava/lang/Object;)Z
    //   765: iload #6
    //   767: ifeq -> 847
    //   770: goto -> 777
    //   773: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   776: athrow
    //   777: lload_1
    //   778: lconst_0
    //   779: lcmp
    //   780: ifle -> 840
    //   783: ifne -> 839
    //   786: goto -> 793
    //   789: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   792: athrow
    //   793: aload #9
    //   795: iload #6
    //   797: ifeq -> 850
    //   800: goto -> 807
    //   803: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   806: athrow
    //   807: invokevirtual method_6032 : ()F
    //   810: ldc_w 20.0
    //   813: fcmpl
    //   814: ifne -> 848
    //   817: goto -> 824
    //   820: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   823: athrow
    //   824: aload #10
    //   826: invokevirtual method_2955 : ()Lnet/minecraft/class_268;
    //   829: ifnonnull -> 848
    //   832: goto -> 839
    //   835: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   838: athrow
    //   839: iconst_1
    //   840: goto -> 847
    //   843: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   846: athrow
    //   847: ireturn
    //   848: aload #7
    //   850: invokevirtual method_5667 : ()Ljava/util/UUID;
    //   853: astore #11
    //   855: getstatic wtf/opal/l.I : Ljava/util/List;
    //   858: aload #11
    //   860: invokeinterface contains : (Ljava/lang/Object;)Z
    //   865: iload #6
    //   867: lload_1
    //   868: lconst_0
    //   869: lcmp
    //   870: ifle -> 1102
    //   873: ifeq -> 1100
    //   876: ifeq -> 1095
    //   879: goto -> 886
    //   882: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   885: athrow
    //   886: aload #7
    //   888: invokevirtual method_23317 : ()D
    //   891: dstore #12
    //   893: aload #7
    //   895: invokevirtual method_23318 : ()D
    //   898: dstore #14
    //   900: aload #7
    //   902: invokevirtual method_23321 : ()D
    //   905: dstore #16
    //   907: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   910: getfield field_1724 : Lnet/minecraft/class_746;
    //   913: dload #12
    //   915: dload #14
    //   917: dload #16
    //   919: invokevirtual method_5649 : (DDD)D
    //   922: dstore #18
    //   924: aload #7
    //   926: invokevirtual method_24828 : ()Z
    //   929: iload #6
    //   931: lload_1
    //   932: lconst_0
    //   933: lcmp
    //   934: ifle -> 1013
    //   937: ifeq -> 1005
    //   940: ifne -> 993
    //   943: goto -> 950
    //   946: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   949: athrow
    //   950: aload #7
    //   952: getfield field_6012 : I
    //   955: iload #6
    //   957: lload_1
    //   958: lconst_0
    //   959: lcmp
    //   960: ifle -> 1044
    //   963: ifeq -> 1042
    //   966: goto -> 973
    //   969: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   972: athrow
    //   973: lload_1
    //   974: lconst_0
    //   975: lcmp
    //   976: iflt -> 1035
    //   979: getstatic wtf/opal/l.g : J
    //   982: l2i
    //   983: if_icmple -> 1026
    //   986: goto -> 993
    //   989: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   992: athrow
    //   993: aload #7
    //   995: invokevirtual method_5767 : ()Z
    //   998: goto -> 1005
    //   1001: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1004: athrow
    //   1005: lload_1
    //   1006: lconst_0
    //   1007: lcmp
    //   1008: ifle -> 1077
    //   1011: iload #6
    //   1013: ifeq -> 1074
    //   1016: ifeq -> 1057
    //   1019: goto -> 1026
    //   1022: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1025: athrow
    //   1026: aload #7
    //   1028: invokevirtual method_6032 : ()F
    //   1031: ldc_w 20.0
    //   1034: fcmpl
    //   1035: goto -> 1042
    //   1038: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1041: athrow
    //   1042: iload #6
    //   1044: ifeq -> 1094
    //   1047: ifle -> 1086
    //   1050: goto -> 1057
    //   1053: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1056: athrow
    //   1057: getstatic wtf/opal/l.I : Ljava/util/List;
    //   1060: aload #11
    //   1062: invokeinterface remove : (Ljava/lang/Object;)Z
    //   1067: goto -> 1074
    //   1070: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1073: athrow
    //   1074: pop
    //   1075: iload #6
    //   1077: lload_1
    //   1078: lconst_0
    //   1079: lcmp
    //   1080: ifle -> 1087
    //   1083: ifne -> 1095
    //   1086: iconst_1
    //   1087: goto -> 1094
    //   1090: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1093: athrow
    //   1094: ireturn
    //   1095: aload #10
    //   1097: invokevirtual method_2959 : ()I
    //   1100: iload #6
    //   1102: lload_1
    //   1103: lconst_0
    //   1104: lcmp
    //   1105: ifle -> 1140
    //   1108: ifeq -> 1138
    //   1111: iconst_1
    //   1112: if_icmple -> 1225
    //   1115: goto -> 1122
    //   1118: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1121: athrow
    //   1122: aload #9
    //   1124: invokevirtual method_6032 : ()F
    //   1127: ldc_w 14.0
    //   1130: fcmpl
    //   1131: goto -> 1138
    //   1134: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1137: athrow
    //   1138: iload #6
    //   1140: lload_1
    //   1141: lconst_0
    //   1142: lcmp
    //   1143: ifle -> 1177
    //   1146: ifeq -> 1175
    //   1149: ifle -> 1225
    //   1152: goto -> 1159
    //   1155: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1158: athrow
    //   1159: aload #9
    //   1161: invokevirtual method_6032 : ()F
    //   1164: ldc_w 20.0
    //   1167: fcmpg
    //   1168: goto -> 1175
    //   1171: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1174: athrow
    //   1175: iload #6
    //   1177: lload_1
    //   1178: lconst_0
    //   1179: lcmp
    //   1180: ifle -> 1210
    //   1183: ifeq -> 1208
    //   1186: ifge -> 1225
    //   1189: goto -> 1196
    //   1192: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1195: athrow
    //   1196: aload #9
    //   1198: invokevirtual method_5767 : ()Z
    //   1201: goto -> 1208
    //   1204: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1207: athrow
    //   1208: iload #6
    //   1210: ifeq -> 1224
    //   1213: ifeq -> 1225
    //   1216: goto -> 1223
    //   1219: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1222: athrow
    //   1223: iconst_1
    //   1224: ireturn
    //   1225: iload #6
    //   1227: lload_1
    //   1228: lconst_0
    //   1229: lcmp
    //   1230: ifle -> 1244
    //   1233: ifne -> 1773
    //   1236: aload #7
    //   1238: invokevirtual method_5667 : ()Ljava/util/UUID;
    //   1241: invokevirtual version : ()I
    //   1244: goto -> 1251
    //   1247: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1250: athrow
    //   1251: iload #6
    //   1253: lload_1
    //   1254: lconst_0
    //   1255: lcmp
    //   1256: iflt -> 1518
    //   1259: ifeq -> 1516
    //   1262: iconst_4
    //   1263: if_icmpeq -> 1504
    //   1266: goto -> 1273
    //   1269: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1272: athrow
    //   1273: aload #7
    //   1275: iload #6
    //   1277: ifeq -> 1308
    //   1280: goto -> 1287
    //   1283: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1286: athrow
    //   1287: invokevirtual method_5767 : ()Z
    //   1290: ifeq -> 1306
    //   1293: goto -> 1300
    //   1296: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1299: athrow
    //   1300: iconst_1
    //   1301: ireturn
    //   1302: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1305: athrow
    //   1306: aload #7
    //   1308: invokevirtual method_19538 : ()Lnet/minecraft/class_243;
    //   1311: astore #10
    //   1313: getstatic wtf/opal/l.Z : Ljava/util/List;
    //   1316: invokeinterface iterator : ()Ljava/util/Iterator;
    //   1321: astore #11
    //   1323: aload #11
    //   1325: invokeinterface hasNext : ()Z
    //   1330: ifeq -> 1396
    //   1333: aload #11
    //   1335: invokeinterface next : ()Ljava/lang/Object;
    //   1340: checkcast net/minecraft/class_243
    //   1343: astore #12
    //   1345: aload #12
    //   1347: aload #10
    //   1349: invokevirtual method_1022 : (Lnet/minecraft/class_243;)D
    //   1352: ldc2_w 2.0
    //   1355: dcmpg
    //   1356: iload #6
    //   1358: lload_1
    //   1359: lconst_0
    //   1360: lcmp
    //   1361: iflt -> 1369
    //   1364: ifeq -> 1410
    //   1367: iload #6
    //   1369: ifeq -> 1390
    //   1372: goto -> 1379
    //   1375: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1378: athrow
    //   1379: ifge -> 1391
    //   1382: goto -> 1389
    //   1385: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1388: athrow
    //   1389: iconst_1
    //   1390: ireturn
    //   1391: iload #6
    //   1393: ifne -> 1323
    //   1396: aload #8
    //   1398: lload_1
    //   1399: lconst_0
    //   1400: lcmp
    //   1401: ifle -> 1340
    //   1404: ldc_w ' '
    //   1407: invokevirtual contains : (Ljava/lang/CharSequence;)Z
    //   1410: ifeq -> 1493
    //   1413: aload #7
    //   1415: invokevirtual method_5477 : ()Lnet/minecraft/class_2561;
    //   1418: invokeinterface method_10855 : ()Ljava/util/List;
    //   1423: astore #11
    //   1425: aload #11
    //   1427: lload_1
    //   1428: lconst_0
    //   1429: lcmp
    //   1430: iflt -> 1473
    //   1433: iload #6
    //   1435: ifeq -> 1473
    //   1438: invokeinterface isEmpty : ()Z
    //   1443: lload_1
    //   1444: lconst_0
    //   1445: lcmp
    //   1446: iflt -> 1495
    //   1449: ifne -> 1493
    //   1452: goto -> 1459
    //   1455: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1458: athrow
    //   1459: aload #11
    //   1461: invokeinterface getFirst : ()Ljava/lang/Object;
    //   1466: goto -> 1473
    //   1469: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1472: athrow
    //   1473: checkcast net/minecraft/class_2561
    //   1476: invokeinterface method_10866 : ()Lnet/minecraft/class_2583;
    //   1481: invokevirtual method_10973 : ()Lnet/minecraft/class_5251;
    //   1484: ifnull -> 1493
    //   1487: iconst_1
    //   1488: ireturn
    //   1489: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1492: athrow
    //   1493: iload #6
    //   1495: lload_1
    //   1496: lconst_0
    //   1497: lcmp
    //   1498: iflt -> 1509
    //   1501: ifne -> 1773
    //   1504: aload #7
    //   1506: instanceof net/minecraft/class_1646
    //   1509: goto -> 1516
    //   1512: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1515: athrow
    //   1516: iload #6
    //   1518: ifeq -> 1774
    //   1521: ifeq -> 1773
    //   1524: goto -> 1531
    //   1527: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1530: athrow
    //   1531: iconst_1
    //   1532: ireturn
    //   1533: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1536: athrow
    //   1537: aload #8
    //   1539: sipush #8488
    //   1542: ldc2_w 4377369160671976491
    //   1545: lload_1
    //   1546: lxor
    //   1547: <illegal opcode> z : (IJ)Ljava/lang/String;
    //   1552: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1555: iload #6
    //   1557: ifeq -> 1772
    //   1560: ifne -> 1764
    //   1563: goto -> 1570
    //   1566: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1569: athrow
    //   1570: aload #8
    //   1572: sipush #31511
    //   1575: ldc2_w 4917987263248762391
    //   1578: lload_1
    //   1579: lxor
    //   1580: <illegal opcode> z : (IJ)Ljava/lang/String;
    //   1585: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1588: iload #6
    //   1590: ifeq -> 1772
    //   1593: goto -> 1600
    //   1596: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1599: athrow
    //   1600: lload_1
    //   1601: lconst_0
    //   1602: lcmp
    //   1603: ifle -> 1765
    //   1606: ifne -> 1764
    //   1609: goto -> 1616
    //   1612: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1615: athrow
    //   1616: aload #8
    //   1618: sipush #7407
    //   1621: ldc2_w 6204099032765161961
    //   1624: lload_1
    //   1625: lxor
    //   1626: <illegal opcode> z : (IJ)Ljava/lang/String;
    //   1631: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   1634: iload #6
    //   1636: ifeq -> 1772
    //   1639: goto -> 1646
    //   1642: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1645: athrow
    //   1646: lload_1
    //   1647: lconst_0
    //   1648: lcmp
    //   1649: ifle -> 1765
    //   1652: ifne -> 1764
    //   1655: goto -> 1662
    //   1658: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1661: athrow
    //   1662: aload #8
    //   1664: sipush #12245
    //   1667: ldc2_w 4690362370513425108
    //   1670: lload_1
    //   1671: lxor
    //   1672: <illegal opcode> z : (IJ)Ljava/lang/String;
    //   1677: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   1680: iload #6
    //   1682: ifeq -> 1774
    //   1685: goto -> 1692
    //   1688: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1691: athrow
    //   1692: ifeq -> 1773
    //   1695: goto -> 1702
    //   1698: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1701: athrow
    //   1702: aload #8
    //   1704: invokevirtual length : ()I
    //   1707: iload #6
    //   1709: ifeq -> 1772
    //   1712: goto -> 1719
    //   1715: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1718: athrow
    //   1719: lload_1
    //   1720: lconst_0
    //   1721: lcmp
    //   1722: ifle -> 1765
    //   1725: iconst_4
    //   1726: if_icmpeq -> 1764
    //   1729: goto -> 1736
    //   1732: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1735: athrow
    //   1736: aload #8
    //   1738: invokevirtual length : ()I
    //   1741: iload #6
    //   1743: ifeq -> 1774
    //   1746: goto -> 1753
    //   1749: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1752: athrow
    //   1753: iconst_5
    //   1754: if_icmpne -> 1773
    //   1757: goto -> 1764
    //   1760: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1763: athrow
    //   1764: iconst_1
    //   1765: goto -> 1772
    //   1768: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1771: athrow
    //   1772: ireturn
    //   1773: iconst_0
    //   1774: ireturn
    // Exception table:
    //   from	to	target	type
    //   39	51	54	wtf/opal/x5
    //   64	76	79	wtf/opal/x5
    //   84	104	107	wtf/opal/x5
    //   113	133	136	wtf/opal/x5
    //   124	142	145	wtf/opal/x5
    //   149	160	163	wtf/opal/x5
    //   157	185	188	wtf/opal/x5
    //   167	195	198	wtf/opal/x5
    //   242	250	253	wtf/opal/x5
    //   271	287	290	wtf/opal/x5
    //   296	313	316	wtf/opal/x5
    //   310	322	322	wtf/opal/x5
    //   338	368	368	wtf/opal/x5
    //   343	388	391	wtf/opal/x5
    //   372	398	401	wtf/opal/x5
    //   424	442	445	wtf/opal/x5
    //   439	460	463	wtf/opal/x5
    //   474	488	491	wtf/opal/x5
    //   485	503	506	wtf/opal/x5
    //   510	524	527	wtf/opal/x5
    //   521	539	542	wtf/opal/x5
    //   546	560	563	wtf/opal/x5
    //   557	581	584	wtf/opal/x5
    //   567	599	602	wtf/opal/x5
    //   606	617	617	wtf/opal/x5
    //   624	646	649	wtf/opal/x5
    //   630	656	659	wtf/opal/x5
    //   686	702	705	wtf/opal/x5
    //   711	719	719	wtf/opal/x5
    //   723	740	743	wtf/opal/x5
    //   733	770	773	wtf/opal/x5
    //   747	786	789	wtf/opal/x5
    //   777	800	803	wtf/opal/x5
    //   793	817	820	wtf/opal/x5
    //   807	832	835	wtf/opal/x5
    //   824	840	843	wtf/opal/x5
    //   855	879	882	wtf/opal/x5
    //   924	943	946	wtf/opal/x5
    //   940	966	969	wtf/opal/x5
    //   950	986	989	wtf/opal/x5
    //   973	998	1001	wtf/opal/x5
    //   1005	1019	1022	wtf/opal/x5
    //   1016	1035	1038	wtf/opal/x5
    //   1042	1050	1053	wtf/opal/x5
    //   1047	1067	1070	wtf/opal/x5
    //   1074	1087	1090	wtf/opal/x5
    //   1100	1115	1118	wtf/opal/x5
    //   1111	1131	1134	wtf/opal/x5
    //   1138	1152	1155	wtf/opal/x5
    //   1149	1168	1171	wtf/opal/x5
    //   1175	1189	1192	wtf/opal/x5
    //   1186	1201	1204	wtf/opal/x5
    //   1208	1216	1219	wtf/opal/x5
    //   1225	1244	1247	wtf/opal/x5
    //   1251	1266	1269	wtf/opal/x5
    //   1262	1280	1283	wtf/opal/x5
    //   1273	1293	1296	wtf/opal/x5
    //   1287	1302	1302	wtf/opal/x5
    //   1345	1372	1375	wtf/opal/x5
    //   1367	1382	1385	wtf/opal/x5
    //   1425	1452	1455	wtf/opal/x5
    //   1438	1466	1469	wtf/opal/x5
    //   1473	1489	1489	wtf/opal/x5
    //   1493	1509	1512	wtf/opal/x5
    //   1516	1524	1527	wtf/opal/x5
    //   1521	1533	1533	wtf/opal/x5
    //   1537	1563	1566	wtf/opal/x5
    //   1560	1593	1596	wtf/opal/x5
    //   1570	1609	1612	wtf/opal/x5
    //   1600	1639	1642	wtf/opal/x5
    //   1616	1655	1658	wtf/opal/x5
    //   1646	1685	1688	wtf/opal/x5
    //   1662	1695	1698	wtf/opal/x5
    //   1692	1712	1715	wtf/opal/x5
    //   1702	1729	1732	wtf/opal/x5
    //   1719	1746	1749	wtf/opal/x5
    //   1736	1757	1760	wtf/opal/x5
    //   1753	1765	1768	wtf/opal/x5
  }
  
  private static void lambda$new$1(d4 paramd4) {
    I.clear();
    Z.clear();
  }
  
  private static void lambda$new$0(lu paramlu) {
    long l1 = a ^ 0x890D8A89861L;
    int i = q.F();
    try {
      if (i == 0)
        try {
          if (b9.c.field_1724 != null) {
          
          } else {
            return;
          } 
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    if (((dl)b9.c.field_1724).equals(dl.HYPIXEL)) {
      class_2596 class_2596 = paramlu.g(new Object[0]);
      try {
        if (i == 0)
          try {
            if (class_2596 instanceof class_2604) {
            
            } else {
              return;
            } 
          } catch (x5 x5) {
            throw a(null);
          }  
      } catch (x5 x5) {
        throw a(null);
      } 
      class_2604 class_2604 = (class_2604)class_2596;
      try {
        if (class_2604.method_11169() == class_1299.field_6097)
          I.add(class_2604.method_11164()); 
      } catch (x5 x5) {
        throw a(null);
      } 
    } 
  }
  
  static {
    (new byte[8])[0] = (byte)(int)(l1 >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l1 << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[7];
    boolean bool = false;
    String str;
    int i = (str = "àHn´=ÝAM[.äNÆY\0254Ï÷]èRÞ¢õÎ.\004ÚRßñmm®ªh<$GaFÐ{ÑW|S´§4ä8§\nât-Cs\030yMËywºAì+øl_ \006r¸ôø§*þ\022ÄýÏ\033~¿\\=®Næt!c\f\020º£Ý¹5U\001À5K9\013é\020X\034µS\0163Ï¨\bU¢\002]±\\\020ÜPî²\nÝècuýsÌ\013").length();
    byte b2 = 88;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x49AB;
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
        throw new RuntimeException("wtf/opal/l", exception);
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
    long l1 = ((Long)paramArrayOfObject[1]).longValue();
    String str = a(i, l1);
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
    //   65: ldc_w 'wtf/opal/l'
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
    long l1 = a ^ 0x166984AF1746L;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\l.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */