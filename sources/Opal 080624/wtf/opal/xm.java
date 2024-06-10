package wtf.opal;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.class_2338;
import net.minecraft.class_241;
import net.minecraft.class_3545;

public final class xm extends d {
  private final class_3545<class_2338, class_2338> Z;
  
  private class_241 x;
  
  private final gm<u0> v;
  
  private final gm<b6> Q;
  
  private static final long a = on.a(1175977009035457978L, -6651097790324482837L, MethodHandles.lookup().lookupClass()).a(213927453712255L);
  
  private static final String[] b;
  
  private static final String[] d;
  
  private static final Map f = new HashMap<>(13);
  
  public xm(long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/xm.a : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: lload_1
    //   7: dup2
    //   8: ldc2_w 57678219688262
    //   11: lxor
    //   12: lstore_3
    //   13: pop2
    //   14: aload_0
    //   15: sipush #27589
    //   18: ldc2_w 7558263534230341627
    //   21: lload_1
    //   22: lxor
    //   23: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   28: lload_3
    //   29: sipush #11506
    //   32: ldc2_w 7247861653111521485
    //   35: lload_1
    //   36: lxor
    //   37: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   42: getstatic wtf/opal/kn.WORLD : Lwtf/opal/kn;
    //   45: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   48: aload_0
    //   49: new net/minecraft/class_3545
    //   52: dup
    //   53: aconst_null
    //   54: aconst_null
    //   55: invokespecial <init> : (Ljava/lang/Object;Ljava/lang/Object;)V
    //   58: putfield Z : Lnet/minecraft/class_3545;
    //   61: aload_0
    //   62: aload_0
    //   63: <illegal opcode> H : (Lwtf/opal/xm;)Lwtf/opal/gm;
    //   68: putfield v : Lwtf/opal/gm;
    //   71: aload_0
    //   72: aload_0
    //   73: <illegal opcode> H : (Lwtf/opal/xm;)Lwtf/opal/gm;
    //   78: putfield Q : Lwtf/opal/gm;
    //   81: return
  }
  
  private List V(Object[] paramArrayOfObject) {
    class_2338 class_2338 = (class_2338)paramArrayOfObject[0];
    return Arrays.asList(new class_2338[] { class_2338.method_10084(), class_2338.method_10067(), class_2338.method_10095(), class_2338.method_10078(), class_2338.method_10072() });
  }
  
  private void lambda$new$1(b6 paramb6) {
    long l1 = a ^ 0x4AE1BCE18447L;
    long l2 = l1 ^ 0x7C884DE81AF2L;
    try {
      if (this.x != null) {
        (new Object[4])[3] = Float.valueOf(1.0F);
        (new Object[4])[2] = Float.valueOf(1.0F);
        new Object[4];
        d1.q(new Object[0]).i(new Object[0]).g(new Object[] { null, Long.valueOf(l2), this.x });
      } 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  private void lambda$new$0(u0 paramu0) {
    // Byte code:
    //   0: getstatic wtf/opal/xm.a : J
    //   3: ldc2_w 128475464331375
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 38691307188606
    //   13: lxor
    //   14: lstore #4
    //   16: pop2
    //   17: invokestatic k : ()[I
    //   20: astore #6
    //   22: aload_1
    //   23: iconst_0
    //   24: anewarray java/lang/Object
    //   27: invokevirtual K : ([Ljava/lang/Object;)Z
    //   30: aload #6
    //   32: ifnull -> 82
    //   35: ifne -> 85
    //   38: goto -> 45
    //   41: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   44: athrow
    //   45: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   48: getfield field_1724 : Lnet/minecraft/class_746;
    //   51: aload #6
    //   53: ifnull -> 97
    //   56: goto -> 63
    //   59: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   62: athrow
    //   63: invokevirtual method_31548 : ()Lnet/minecraft/class_1661;
    //   66: invokevirtual method_7391 : ()Lnet/minecraft/class_1799;
    //   69: invokevirtual method_7909 : ()Lnet/minecraft/class_1792;
    //   72: instanceof net/minecraft/class_1747
    //   75: goto -> 82
    //   78: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   81: athrow
    //   82: ifne -> 86
    //   85: return
    //   86: aload_0
    //   87: aconst_null
    //   88: putfield x : Lnet/minecraft/class_241;
    //   91: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   94: getfield field_1724 : Lnet/minecraft/class_746;
    //   97: invokevirtual method_19538 : ()Lnet/minecraft/class_243;
    //   100: dconst_0
    //   101: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   104: getfield field_1724 : Lnet/minecraft/class_746;
    //   107: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   110: getfield field_1724 : Lnet/minecraft/class_746;
    //   113: invokevirtual method_18376 : ()Lnet/minecraft/class_4050;
    //   116: invokevirtual method_18381 : (Lnet/minecraft/class_4050;)F
    //   119: f2d
    //   120: dconst_0
    //   121: invokevirtual method_1031 : (DDD)Lnet/minecraft/class_243;
    //   124: astore #7
    //   126: aload #7
    //   128: invokestatic method_49638 : (Lnet/minecraft/class_2374;)Lnet/minecraft/class_2338;
    //   131: astore #8
    //   133: aload #8
    //   135: new net/minecraft/class_2382
    //   138: dup
    //   139: iconst_4
    //   140: iconst_4
    //   141: iconst_4
    //   142: invokespecial <init> : (III)V
    //   145: invokevirtual method_10059 : (Lnet/minecraft/class_2382;)Lnet/minecraft/class_2338;
    //   148: astore #9
    //   150: aload #8
    //   152: new net/minecraft/class_2382
    //   155: dup
    //   156: iconst_4
    //   157: iconst_4
    //   158: iconst_4
    //   159: invokespecial <init> : (III)V
    //   162: invokevirtual method_10081 : (Lnet/minecraft/class_2382;)Lnet/minecraft/class_2338;
    //   165: astore #10
    //   167: new net/minecraft/class_2338
    //   170: dup
    //   171: aload #9
    //   173: invokevirtual method_10263 : ()I
    //   176: aload #10
    //   178: invokevirtual method_10263 : ()I
    //   181: invokestatic min : (II)I
    //   184: aload #9
    //   186: invokevirtual method_10264 : ()I
    //   189: aload #10
    //   191: invokevirtual method_10264 : ()I
    //   194: invokestatic min : (II)I
    //   197: aload #9
    //   199: invokevirtual method_10260 : ()I
    //   202: aload #10
    //   204: invokevirtual method_10260 : ()I
    //   207: invokestatic min : (II)I
    //   210: invokespecial <init> : (III)V
    //   213: astore #11
    //   215: new net/minecraft/class_2338
    //   218: dup
    //   219: aload #9
    //   221: invokevirtual method_10263 : ()I
    //   224: aload #10
    //   226: invokevirtual method_10263 : ()I
    //   229: invokestatic max : (II)I
    //   232: aload #9
    //   234: invokevirtual method_10264 : ()I
    //   237: aload #10
    //   239: invokevirtual method_10264 : ()I
    //   242: invokestatic max : (II)I
    //   245: aload #9
    //   247: invokevirtual method_10260 : ()I
    //   250: aload #10
    //   252: invokevirtual method_10260 : ()I
    //   255: invokestatic max : (II)I
    //   258: invokespecial <init> : (III)V
    //   261: astore #12
    //   263: aload #11
    //   265: invokevirtual method_10263 : ()I
    //   268: istore #13
    //   270: iload #13
    //   272: aload #12
    //   274: invokevirtual method_10263 : ()I
    //   277: if_icmpgt -> 580
    //   280: aload #11
    //   282: aload #6
    //   284: ifnull -> 599
    //   287: invokevirtual method_10264 : ()I
    //   290: istore #14
    //   292: iload #14
    //   294: aload #12
    //   296: invokevirtual method_10264 : ()I
    //   299: if_icmpgt -> 572
    //   302: aload #11
    //   304: invokevirtual method_10260 : ()I
    //   307: aload #6
    //   309: ifnull -> 272
    //   312: istore #15
    //   314: iload #15
    //   316: aload #12
    //   318: invokevirtual method_10260 : ()I
    //   321: if_icmpgt -> 564
    //   324: new net/minecraft/class_2338
    //   327: dup
    //   328: iload #13
    //   330: iload #14
    //   332: iload #15
    //   334: invokespecial <init> : (III)V
    //   337: astore #16
    //   339: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   342: getfield field_1687 : Lnet/minecraft/class_638;
    //   345: aload #16
    //   347: invokevirtual method_8320 : (Lnet/minecraft/class_2338;)Lnet/minecraft/class_2680;
    //   350: invokevirtual method_26204 : ()Lnet/minecraft/class_2248;
    //   353: astore #18
    //   355: aload #6
    //   357: ifnull -> 559
    //   360: aload #18
    //   362: instanceof net/minecraft/class_2244
    //   365: aload #6
    //   367: ifnull -> 294
    //   370: goto -> 377
    //   373: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   376: athrow
    //   377: ifeq -> 556
    //   380: aload #18
    //   382: checkcast net/minecraft/class_2244
    //   385: astore #17
    //   387: aload #6
    //   389: ifnull -> 559
    //   392: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   395: getfield field_1724 : Lnet/minecraft/class_746;
    //   398: invokevirtual method_22861 : ()I
    //   401: lload #4
    //   403: dup2_x1
    //   404: pop2
    //   405: iconst_2
    //   406: anewarray java/lang/Object
    //   409: dup_x1
    //   410: swap
    //   411: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   414: iconst_1
    //   415: swap
    //   416: aastore
    //   417: dup_x2
    //   418: dup_x2
    //   419: pop
    //   420: invokestatic valueOf : (J)Ljava/lang/Long;
    //   423: iconst_0
    //   424: swap
    //   425: aastore
    //   426: invokestatic r : ([Ljava/lang/Object;)I
    //   429: aload #17
    //   431: invokevirtual method_9487 : ()Lnet/minecraft/class_1767;
    //   434: invokevirtual method_16357 : ()I
    //   437: if_icmpne -> 556
    //   440: goto -> 447
    //   443: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   446: athrow
    //   447: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   450: getfield field_1687 : Lnet/minecraft/class_638;
    //   453: aload #16
    //   455: invokevirtual method_8320 : (Lnet/minecraft/class_2338;)Lnet/minecraft/class_2680;
    //   458: invokestatic method_24164 : (Lnet/minecraft/class_2680;)Lnet/minecraft/class_4732$class_4733;
    //   461: getstatic net/minecraft/class_4732$class_4733.field_21784 : Lnet/minecraft/class_4732$class_4733;
    //   464: invokevirtual equals : (Ljava/lang/Object;)Z
    //   467: aload #6
    //   469: ifnull -> 537
    //   472: goto -> 479
    //   475: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   478: athrow
    //   479: ifeq -> 510
    //   482: goto -> 489
    //   485: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   488: athrow
    //   489: aload_0
    //   490: getfield Z : Lnet/minecraft/class_3545;
    //   493: aload #16
    //   495: invokevirtual method_34964 : (Ljava/lang/Object;)V
    //   498: aload #6
    //   500: ifnonnull -> 556
    //   503: goto -> 510
    //   506: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   509: athrow
    //   510: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   513: getfield field_1687 : Lnet/minecraft/class_638;
    //   516: aload #16
    //   518: invokevirtual method_8320 : (Lnet/minecraft/class_2338;)Lnet/minecraft/class_2680;
    //   521: invokestatic method_24164 : (Lnet/minecraft/class_2680;)Lnet/minecraft/class_4732$class_4733;
    //   524: getstatic net/minecraft/class_4732$class_4733.field_21785 : Lnet/minecraft/class_4732$class_4733;
    //   527: invokevirtual equals : (Ljava/lang/Object;)Z
    //   530: goto -> 537
    //   533: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   536: athrow
    //   537: ifeq -> 556
    //   540: aload_0
    //   541: getfield Z : Lnet/minecraft/class_3545;
    //   544: aload #16
    //   546: invokevirtual method_34965 : (Ljava/lang/Object;)V
    //   549: goto -> 556
    //   552: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   555: athrow
    //   556: iinc #15, 1
    //   559: aload #6
    //   561: ifnonnull -> 314
    //   564: iinc #14, 1
    //   567: aload #6
    //   569: ifnonnull -> 292
    //   572: iinc #13, 1
    //   575: aload #6
    //   577: ifnonnull -> 270
    //   580: aload_0
    //   581: getfield Z : Lnet/minecraft/class_3545;
    //   584: aload #6
    //   586: ifnull -> 1097
    //   589: invokevirtual method_15442 : ()Ljava/lang/Object;
    //   592: goto -> 599
    //   595: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   598: athrow
    //   599: ifnull -> 1085
    //   602: aload_0
    //   603: getfield Z : Lnet/minecraft/class_3545;
    //   606: aload #6
    //   608: ifnull -> 1097
    //   611: goto -> 618
    //   614: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   617: athrow
    //   618: invokevirtual method_15441 : ()Ljava/lang/Object;
    //   621: ifnull -> 1085
    //   624: goto -> 631
    //   627: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   630: athrow
    //   631: aload_0
    //   632: getfield Z : Lnet/minecraft/class_3545;
    //   635: invokevirtual method_15442 : ()Ljava/lang/Object;
    //   638: checkcast net/minecraft/class_2338
    //   641: astore #13
    //   643: aload_0
    //   644: getfield Z : Lnet/minecraft/class_3545;
    //   647: invokevirtual method_15441 : ()Ljava/lang/Object;
    //   650: checkcast net/minecraft/class_2338
    //   653: astore #14
    //   655: aload_0
    //   656: aload #13
    //   658: iconst_1
    //   659: anewarray java/lang/Object
    //   662: dup_x1
    //   663: swap
    //   664: iconst_0
    //   665: swap
    //   666: aastore
    //   667: invokevirtual V : ([Ljava/lang/Object;)Ljava/util/List;
    //   670: invokeinterface iterator : ()Ljava/util/Iterator;
    //   675: astore #15
    //   677: aload #15
    //   679: invokeinterface hasNext : ()Z
    //   684: ifeq -> 737
    //   687: aload #15
    //   689: invokeinterface next : ()Ljava/lang/Object;
    //   694: checkcast net/minecraft/class_2338
    //   697: astore #16
    //   699: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   702: getfield field_1687 : Lnet/minecraft/class_638;
    //   705: aload #16
    //   707: invokevirtual method_8320 : (Lnet/minecraft/class_2338;)Lnet/minecraft/class_2680;
    //   710: invokevirtual method_26215 : ()Z
    //   713: aload #6
    //   715: ifnull -> 766
    //   718: ifeq -> 732
    //   721: goto -> 728
    //   724: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   727: athrow
    //   728: aload #16
    //   730: astore #13
    //   732: aload #6
    //   734: ifnonnull -> 677
    //   737: aload_0
    //   738: aload #14
    //   740: iconst_1
    //   741: anewarray java/lang/Object
    //   744: dup_x1
    //   745: swap
    //   746: iconst_0
    //   747: swap
    //   748: aastore
    //   749: invokevirtual V : ([Ljava/lang/Object;)Ljava/util/List;
    //   752: invokeinterface iterator : ()Ljava/util/Iterator;
    //   757: astore #15
    //   759: aload #15
    //   761: invokeinterface hasNext : ()Z
    //   766: ifeq -> 819
    //   769: aload #15
    //   771: invokeinterface next : ()Ljava/lang/Object;
    //   776: checkcast net/minecraft/class_2338
    //   779: astore #16
    //   781: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   784: getfield field_1687 : Lnet/minecraft/class_638;
    //   787: aload #16
    //   789: invokevirtual method_8320 : (Lnet/minecraft/class_2338;)Lnet/minecraft/class_2680;
    //   792: invokevirtual method_26215 : ()Z
    //   795: aload #6
    //   797: ifnull -> 833
    //   800: ifeq -> 814
    //   803: goto -> 810
    //   806: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   809: athrow
    //   810: aload #16
    //   812: astore #14
    //   814: aload #6
    //   816: ifnonnull -> 759
    //   819: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   822: getfield field_1687 : Lnet/minecraft/class_638;
    //   825: aload #13
    //   827: invokevirtual method_8320 : (Lnet/minecraft/class_2338;)Lnet/minecraft/class_2680;
    //   830: invokevirtual method_26215 : ()Z
    //   833: aload #6
    //   835: ifnull -> 972
    //   838: ifeq -> 958
    //   841: goto -> 848
    //   844: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   847: athrow
    //   848: aload #13
    //   850: invokevirtual method_10074 : ()Lnet/minecraft/class_2338;
    //   853: astore #13
    //   855: new net/minecraft/class_3965
    //   858: dup
    //   859: aload #13
    //   861: invokevirtual method_46558 : ()Lnet/minecraft/class_243;
    //   864: getstatic net/minecraft/class_2350.field_11036 : Lnet/minecraft/class_2350;
    //   867: aload #13
    //   869: iconst_0
    //   870: invokespecial <init> : (Lnet/minecraft/class_243;Lnet/minecraft/class_2350;Lnet/minecraft/class_2338;Z)V
    //   873: astore #15
    //   875: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   878: aload #6
    //   880: ifnull -> 923
    //   883: getfield field_1761 : Lnet/minecraft/class_636;
    //   886: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   889: getfield field_1724 : Lnet/minecraft/class_746;
    //   892: getstatic net/minecraft/class_1268.field_5808 : Lnet/minecraft/class_1268;
    //   895: aload #15
    //   897: invokevirtual method_2896 : (Lnet/minecraft/class_746;Lnet/minecraft/class_1268;Lnet/minecraft/class_3965;)Lnet/minecraft/class_1269;
    //   900: invokevirtual method_23665 : ()Z
    //   903: ifeq -> 932
    //   906: goto -> 913
    //   909: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   912: athrow
    //   913: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   916: goto -> 923
    //   919: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   922: athrow
    //   923: getfield field_1724 : Lnet/minecraft/class_746;
    //   926: getstatic net/minecraft/class_1268.field_5808 : Lnet/minecraft/class_1268;
    //   929: invokevirtual method_6104 : (Lnet/minecraft/class_1268;)V
    //   932: aload_0
    //   933: aload #13
    //   935: getstatic net/minecraft/class_2350.field_11033 : Lnet/minecraft/class_2350;
    //   938: iconst_2
    //   939: anewarray java/lang/Object
    //   942: dup_x1
    //   943: swap
    //   944: iconst_1
    //   945: swap
    //   946: aastore
    //   947: dup_x1
    //   948: swap
    //   949: iconst_0
    //   950: swap
    //   951: aastore
    //   952: invokestatic V : ([Ljava/lang/Object;)Lnet/minecraft/class_241;
    //   955: putfield x : Lnet/minecraft/class_241;
    //   958: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   961: getfield field_1687 : Lnet/minecraft/class_638;
    //   964: aload #14
    //   966: invokevirtual method_8320 : (Lnet/minecraft/class_2338;)Lnet/minecraft/class_2680;
    //   969: invokevirtual method_26215 : ()Z
    //   972: ifeq -> 1085
    //   975: aload #14
    //   977: invokevirtual method_10074 : ()Lnet/minecraft/class_2338;
    //   980: astore #14
    //   982: new net/minecraft/class_3965
    //   985: dup
    //   986: aload #14
    //   988: invokevirtual method_46558 : ()Lnet/minecraft/class_243;
    //   991: getstatic net/minecraft/class_2350.field_11036 : Lnet/minecraft/class_2350;
    //   994: aload #14
    //   996: iconst_0
    //   997: invokespecial <init> : (Lnet/minecraft/class_243;Lnet/minecraft/class_2350;Lnet/minecraft/class_2338;Z)V
    //   1000: astore #15
    //   1002: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1005: aload #6
    //   1007: ifnull -> 1050
    //   1010: getfield field_1761 : Lnet/minecraft/class_636;
    //   1013: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1016: getfield field_1724 : Lnet/minecraft/class_746;
    //   1019: getstatic net/minecraft/class_1268.field_5808 : Lnet/minecraft/class_1268;
    //   1022: aload #15
    //   1024: invokevirtual method_2896 : (Lnet/minecraft/class_746;Lnet/minecraft/class_1268;Lnet/minecraft/class_3965;)Lnet/minecraft/class_1269;
    //   1027: invokevirtual method_23665 : ()Z
    //   1030: ifeq -> 1059
    //   1033: goto -> 1040
    //   1036: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1039: athrow
    //   1040: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1043: goto -> 1050
    //   1046: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1049: athrow
    //   1050: getfield field_1724 : Lnet/minecraft/class_746;
    //   1053: getstatic net/minecraft/class_1268.field_5808 : Lnet/minecraft/class_1268;
    //   1056: invokevirtual method_6104 : (Lnet/minecraft/class_1268;)V
    //   1059: aload_0
    //   1060: aload #14
    //   1062: getstatic net/minecraft/class_2350.field_11033 : Lnet/minecraft/class_2350;
    //   1065: iconst_2
    //   1066: anewarray java/lang/Object
    //   1069: dup_x1
    //   1070: swap
    //   1071: iconst_1
    //   1072: swap
    //   1073: aastore
    //   1074: dup_x1
    //   1075: swap
    //   1076: iconst_0
    //   1077: swap
    //   1078: aastore
    //   1079: invokestatic V : ([Ljava/lang/Object;)Lnet/minecraft/class_241;
    //   1082: putfield x : Lnet/minecraft/class_241;
    //   1085: aload_0
    //   1086: getfield Z : Lnet/minecraft/class_3545;
    //   1089: aconst_null
    //   1090: invokevirtual method_34964 : (Ljava/lang/Object;)V
    //   1093: aload_0
    //   1094: getfield Z : Lnet/minecraft/class_3545;
    //   1097: aconst_null
    //   1098: invokevirtual method_34965 : (Ljava/lang/Object;)V
    //   1101: return
    // Exception table:
    //   from	to	target	type
    //   22	38	41	wtf/opal/x5
    //   35	56	59	wtf/opal/x5
    //   45	75	78	wtf/opal/x5
    //   355	370	373	wtf/opal/x5
    //   387	440	443	wtf/opal/x5
    //   392	472	475	wtf/opal/x5
    //   447	482	485	wtf/opal/x5
    //   479	503	506	wtf/opal/x5
    //   489	530	533	wtf/opal/x5
    //   537	549	552	wtf/opal/x5
    //   580	592	595	wtf/opal/x5
    //   599	611	614	wtf/opal/x5
    //   602	624	627	wtf/opal/x5
    //   699	721	724	wtf/opal/x5
    //   781	803	806	wtf/opal/x5
    //   833	841	844	wtf/opal/x5
    //   875	906	909	wtf/opal/x5
    //   883	916	919	wtf/opal/x5
    //   1002	1033	1036	wtf/opal/x5
    //   1010	1043	1046	wtf/opal/x5
  }
  
  static {
    long l = a ^ 0x6F6DFF759783L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[2];
    boolean bool = false;
    String str;
    int i = (str = "{ª§TçEèáf\021wh^*8M8rçU>>8èyé\006ÿçÜ\bhrkî\026D\032\032EÐæ;\000n_$Ì\036\007déÑCdÛÌM\007i\013Ú\017`\013ÂúÐ¨ëyØ.").length();
    byte b2 = 24;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0xF11;
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
        throw new RuntimeException("wtf/opal/xm", exception);
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
    //   66: ldc_w 'wtf/opal/xm'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\xm.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */