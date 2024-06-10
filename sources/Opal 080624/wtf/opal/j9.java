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
import net.minecraft.class_1297;

public final class j9 extends d {
  private final ke v;
  
  private final gm<b6> Y;
  
  private static final long a = on.a(7631006425396469147L, 5034187092855162416L, MethodHandles.lookup().lookupClass()).a(135876162977175L);
  
  private static final String[] b;
  
  private static final String[] d;
  
  private static final Map f = new HashMap<>(13);
  
  public j9(short paramShort1, int paramInt, short paramShort2) {
    // Byte code:
    //   0: iload_1
    //   1: i2l
    //   2: bipush #48
    //   4: lshl
    //   5: iload_2
    //   6: i2l
    //   7: bipush #32
    //   9: lshl
    //   10: bipush #16
    //   12: lushr
    //   13: lor
    //   14: iload_3
    //   15: i2l
    //   16: bipush #48
    //   18: lshl
    //   19: bipush #48
    //   21: lushr
    //   22: lor
    //   23: getstatic wtf/opal/j9.a : J
    //   26: lxor
    //   27: lstore #4
    //   29: lload #4
    //   31: dup2
    //   32: ldc2_w 31669881936113
    //   35: lxor
    //   36: lstore #6
    //   38: dup2
    //   39: ldc2_w 55745092272548
    //   42: lxor
    //   43: lstore #8
    //   45: pop2
    //   46: aload_0
    //   47: sipush #28181
    //   50: ldc2_w 4205158985690269297
    //   53: lload #4
    //   55: lxor
    //   56: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   61: lload #8
    //   63: sipush #15754
    //   66: ldc2_w 7572224992360240623
    //   69: lload #4
    //   71: lxor
    //   72: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   77: getstatic wtf/opal/kn.PLAYER : Lwtf/opal/kn;
    //   80: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   83: aload_0
    //   84: new wtf/opal/ke
    //   87: dup
    //   88: sipush #2426
    //   91: ldc2_w 8778443408290521373
    //   94: lload #4
    //   96: lxor
    //   97: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   102: iconst_0
    //   103: invokespecial <init> : (Ljava/lang/String;Z)V
    //   106: putfield v : Lwtf/opal/ke;
    //   109: aload_0
    //   110: aload_0
    //   111: <illegal opcode> H : (Lwtf/opal/j9;)Lwtf/opal/gm;
    //   116: putfield Y : Lwtf/opal/gm;
    //   119: aload_0
    //   120: iconst_1
    //   121: anewarray wtf/opal/k3
    //   124: dup
    //   125: iconst_0
    //   126: aload_0
    //   127: getfield v : Lwtf/opal/ke;
    //   130: aastore
    //   131: lload #6
    //   133: dup2_x1
    //   134: pop2
    //   135: iconst_2
    //   136: anewarray java/lang/Object
    //   139: dup_x1
    //   140: swap
    //   141: iconst_1
    //   142: swap
    //   143: aastore
    //   144: dup_x2
    //   145: dup_x2
    //   146: pop
    //   147: invokestatic valueOf : (J)Ljava/lang/Long;
    //   150: iconst_0
    //   151: swap
    //   152: aastore
    //   153: invokevirtual o : ([Ljava/lang/Object;)V
    //   156: return
  }
  
  private void lambda$new$1(b6 paramb6) {
    // Byte code:
    //   0: getstatic wtf/opal/j9.a : J
    //   3: ldc2_w 130281459867615
    //   6: lxor
    //   7: lstore_2
    //   8: invokestatic k : ()Z
    //   11: istore #4
    //   13: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   16: iload #4
    //   18: ifeq -> 44
    //   21: getfield field_1687 : Lnet/minecraft/class_638;
    //   24: ifnull -> 68
    //   27: goto -> 34
    //   30: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   33: athrow
    //   34: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   37: goto -> 44
    //   40: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   43: athrow
    //   44: getfield field_1724 : Lnet/minecraft/class_746;
    //   47: ifnull -> 68
    //   50: aload_1
    //   51: iconst_0
    //   52: anewarray java/lang/Object
    //   55: invokevirtual W : ([Ljava/lang/Object;)Z
    //   58: ifne -> 73
    //   61: goto -> 68
    //   64: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   67: athrow
    //   68: return
    //   69: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   72: athrow
    //   73: iconst_0
    //   74: anewarray java/lang/Object
    //   77: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   80: iconst_0
    //   81: anewarray java/lang/Object
    //   84: invokevirtual q : ([Ljava/lang/Object;)Lwtf/opal/ko;
    //   87: astore #5
    //   89: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   92: getfield field_1687 : Lnet/minecraft/class_638;
    //   95: invokevirtual method_18112 : ()Ljava/lang/Iterable;
    //   98: aload_0
    //   99: aload #5
    //   101: <illegal opcode> accept : (Lwtf/opal/j9;Lwtf/opal/ko;)Ljava/util/function/Consumer;
    //   106: invokeinterface forEach : (Ljava/util/function/Consumer;)V
    //   111: return
    // Exception table:
    //   from	to	target	type
    //   13	27	30	wtf/opal/x5
    //   21	37	40	wtf/opal/x5
    //   44	61	64	wtf/opal/x5
    //   50	69	69	wtf/opal/x5
  }
  
  private void lambda$new$0(ko paramko, class_1297 paramclass_1297) {
    // Byte code:
    //   0: getstatic wtf/opal/j9.a : J
    //   3: ldc2_w 13814525435333
    //   6: lxor
    //   7: lstore_3
    //   8: lload_3
    //   9: dup2
    //   10: ldc2_w 87599099665329
    //   13: lxor
    //   14: lstore #5
    //   16: dup2
    //   17: ldc2_w 21809660873703
    //   20: lxor
    //   21: dup2
    //   22: bipush #48
    //   24: lushr
    //   25: l2i
    //   26: istore #7
    //   28: dup2
    //   29: bipush #16
    //   31: lshl
    //   32: bipush #48
    //   34: lushr
    //   35: l2i
    //   36: istore #8
    //   38: dup2
    //   39: bipush #32
    //   41: lshl
    //   42: bipush #32
    //   44: lushr
    //   45: l2i
    //   46: istore #9
    //   48: pop2
    //   49: dup2
    //   50: ldc2_w 129097062014379
    //   53: lxor
    //   54: lstore #10
    //   56: dup2
    //   57: ldc2_w 139420146040782
    //   60: lxor
    //   61: lstore #12
    //   63: pop2
    //   64: invokestatic k : ()Z
    //   67: istore #14
    //   69: aload_2
    //   70: iload #14
    //   72: ifeq -> 96
    //   75: instanceof net/minecraft/class_1657
    //   78: ifeq -> 807
    //   81: goto -> 88
    //   84: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   87: athrow
    //   88: aload_2
    //   89: goto -> 96
    //   92: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   95: athrow
    //   96: checkcast net/minecraft/class_1657
    //   99: astore #15
    //   101: aload #15
    //   103: invokevirtual method_5667 : ()Ljava/util/UUID;
    //   106: invokevirtual version : ()I
    //   109: iconst_2
    //   110: if_icmpeq -> 238
    //   113: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   116: getfield field_1724 : Lnet/minecraft/class_746;
    //   119: iload #14
    //   121: ifeq -> 157
    //   124: goto -> 131
    //   127: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   130: athrow
    //   131: aload #15
    //   133: if_acmpeq -> 238
    //   136: goto -> 143
    //   139: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   142: athrow
    //   143: aload_0
    //   144: getfield v : Lwtf/opal/ke;
    //   147: invokevirtual z : ()Ljava/lang/Object;
    //   150: goto -> 157
    //   153: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   156: athrow
    //   157: checkcast java/lang/Boolean
    //   160: iload #14
    //   162: ifeq -> 289
    //   165: invokevirtual booleanValue : ()Z
    //   168: ifne -> 243
    //   171: goto -> 178
    //   174: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   177: athrow
    //   178: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   181: getfield field_1724 : Lnet/minecraft/class_746;
    //   184: iload #14
    //   186: ifeq -> 289
    //   189: goto -> 196
    //   192: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   195: athrow
    //   196: aload #15
    //   198: lload #5
    //   200: dup2_x2
    //   201: pop2
    //   202: iconst_3
    //   203: anewarray java/lang/Object
    //   206: dup_x1
    //   207: swap
    //   208: iconst_2
    //   209: swap
    //   210: aastore
    //   211: dup_x1
    //   212: swap
    //   213: iconst_1
    //   214: swap
    //   215: aastore
    //   216: dup_x2
    //   217: dup_x2
    //   218: pop
    //   219: invokestatic valueOf : (J)Ljava/lang/Long;
    //   222: iconst_0
    //   223: swap
    //   224: aastore
    //   225: invokestatic W : ([Ljava/lang/Object;)Z
    //   228: ifeq -> 243
    //   231: goto -> 238
    //   234: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   237: athrow
    //   238: return
    //   239: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   242: athrow
    //   243: aload_1
    //   244: iconst_0
    //   245: anewarray java/lang/Object
    //   248: invokevirtual W : ([Ljava/lang/Object;)Ljava/util/Map;
    //   251: aload #15
    //   253: new wtf/opal/lv
    //   256: dup
    //   257: iload #7
    //   259: i2s
    //   260: iload #8
    //   262: i2s
    //   263: iload #9
    //   265: invokespecial <init> : (SSI)V
    //   268: invokeinterface putIfAbsent : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   273: pop
    //   274: aload_1
    //   275: iconst_0
    //   276: anewarray java/lang/Object
    //   279: invokevirtual W : ([Ljava/lang/Object;)Ljava/util/Map;
    //   282: aload #15
    //   284: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   289: checkcast wtf/opal/lv
    //   292: astore #16
    //   294: aload #16
    //   296: iload #14
    //   298: ifeq -> 398
    //   301: aload #15
    //   303: invokevirtual method_36454 : ()F
    //   306: aload #16
    //   308: iconst_0
    //   309: anewarray java/lang/Object
    //   312: invokevirtual a : ([Ljava/lang/Object;)F
    //   315: fsub
    //   316: invokestatic abs : (F)F
    //   319: iconst_1
    //   320: anewarray java/lang/Object
    //   323: dup_x1
    //   324: swap
    //   325: invokestatic valueOf : (F)Ljava/lang/Float;
    //   328: iconst_0
    //   329: swap
    //   330: aastore
    //   331: invokevirtual v : ([Ljava/lang/Object;)V
    //   334: aload #15
    //   336: invokevirtual method_5624 : ()Z
    //   339: ifeq -> 389
    //   342: goto -> 349
    //   345: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   348: athrow
    //   349: aload #16
    //   351: aload #16
    //   353: iconst_0
    //   354: anewarray java/lang/Object
    //   357: invokevirtual a : ([Ljava/lang/Object;)I
    //   360: iconst_1
    //   361: iadd
    //   362: iconst_1
    //   363: anewarray java/lang/Object
    //   366: dup_x1
    //   367: swap
    //   368: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   371: iconst_0
    //   372: swap
    //   373: aastore
    //   374: invokevirtual r : ([Ljava/lang/Object;)V
    //   377: iload #14
    //   379: ifne -> 414
    //   382: goto -> 389
    //   385: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   388: athrow
    //   389: aload #16
    //   391: goto -> 398
    //   394: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   397: athrow
    //   398: iconst_0
    //   399: iconst_1
    //   400: anewarray java/lang/Object
    //   403: dup_x1
    //   404: swap
    //   405: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   408: iconst_0
    //   409: swap
    //   410: aastore
    //   411: invokevirtual r : ([Ljava/lang/Object;)V
    //   414: aload #15
    //   416: dconst_0
    //   417: ldc2_w -0.01
    //   420: dconst_0
    //   421: invokevirtual method_5654 : (DDD)Z
    //   424: ifne -> 485
    //   427: aload #16
    //   429: aload #16
    //   431: iconst_0
    //   432: anewarray java/lang/Object
    //   435: invokevirtual B : ([Ljava/lang/Object;)I
    //   438: iconst_1
    //   439: iadd
    //   440: iconst_1
    //   441: anewarray java/lang/Object
    //   444: dup_x1
    //   445: swap
    //   446: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   449: iconst_0
    //   450: swap
    //   451: aastore
    //   452: invokevirtual X : ([Ljava/lang/Object;)V
    //   455: aload #16
    //   457: iconst_0
    //   458: iconst_1
    //   459: anewarray java/lang/Object
    //   462: dup_x1
    //   463: swap
    //   464: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   467: iconst_0
    //   468: swap
    //   469: aastore
    //   470: invokevirtual I : ([Ljava/lang/Object;)V
    //   473: iload #14
    //   475: ifne -> 538
    //   478: goto -> 485
    //   481: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   484: athrow
    //   485: aload #16
    //   487: aload #16
    //   489: iconst_0
    //   490: anewarray java/lang/Object
    //   493: invokevirtual T : ([Ljava/lang/Object;)I
    //   496: iconst_1
    //   497: iadd
    //   498: iconst_1
    //   499: anewarray java/lang/Object
    //   502: dup_x1
    //   503: swap
    //   504: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   507: iconst_0
    //   508: swap
    //   509: aastore
    //   510: invokevirtual I : ([Ljava/lang/Object;)V
    //   513: aload #16
    //   515: iconst_0
    //   516: iconst_1
    //   517: anewarray java/lang/Object
    //   520: dup_x1
    //   521: swap
    //   522: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   525: iconst_0
    //   526: swap
    //   527: aastore
    //   528: invokevirtual X : ([Ljava/lang/Object;)V
    //   531: goto -> 538
    //   534: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   537: athrow
    //   538: aload_1
    //   539: iconst_0
    //   540: anewarray java/lang/Object
    //   543: invokevirtual b : ([Ljava/lang/Object;)Ljava/util/List;
    //   546: invokeinterface iterator : ()Ljava/util/Iterator;
    //   551: astore #17
    //   553: aload #17
    //   555: invokeinterface hasNext : ()Z
    //   560: ifeq -> 705
    //   563: aload #17
    //   565: invokeinterface next : ()Ljava/lang/Object;
    //   570: checkcast wtf/opal/dn
    //   573: astore #18
    //   575: iload #14
    //   577: ifeq -> 771
    //   580: aload #18
    //   582: aload #15
    //   584: lload #12
    //   586: iconst_2
    //   587: anewarray java/lang/Object
    //   590: dup_x2
    //   591: dup_x2
    //   592: pop
    //   593: invokestatic valueOf : (J)Ljava/lang/Long;
    //   596: iconst_1
    //   597: swap
    //   598: aastore
    //   599: dup_x1
    //   600: swap
    //   601: iconst_0
    //   602: swap
    //   603: aastore
    //   604: invokevirtual b : ([Ljava/lang/Object;)Z
    //   607: ifeq -> 700
    //   610: goto -> 617
    //   613: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   616: athrow
    //   617: aload #15
    //   619: invokevirtual method_5477 : ()Lnet/minecraft/class_2561;
    //   622: invokeinterface getString : ()Ljava/lang/String;
    //   627: aload #18
    //   629: iconst_0
    //   630: anewarray java/lang/Object
    //   633: invokevirtual Y : ([Ljava/lang/Object;)Ljava/lang/String;
    //   636: sipush #27670
    //   639: ldc2_w 8429011599943393322
    //   642: lload_3
    //   643: lxor
    //   644: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   649: swap
    //   650: sipush #21870
    //   653: ldc2_w 7220163576557001046
    //   656: lload_3
    //   657: lxor
    //   658: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   663: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   668: lload #10
    //   670: dup2_x1
    //   671: pop2
    //   672: iconst_2
    //   673: anewarray java/lang/Object
    //   676: dup_x1
    //   677: swap
    //   678: iconst_1
    //   679: swap
    //   680: aastore
    //   681: dup_x2
    //   682: dup_x2
    //   683: pop
    //   684: invokestatic valueOf : (J)Ljava/lang/Long;
    //   687: iconst_0
    //   688: swap
    //   689: aastore
    //   690: invokestatic g : ([Ljava/lang/Object;)V
    //   693: goto -> 700
    //   696: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   699: athrow
    //   700: iload #14
    //   702: ifne -> 553
    //   705: aload #16
    //   707: aload #15
    //   709: invokevirtual method_5624 : ()Z
    //   712: iconst_1
    //   713: anewarray java/lang/Object
    //   716: dup_x1
    //   717: swap
    //   718: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   721: iconst_0
    //   722: swap
    //   723: aastore
    //   724: invokevirtual w : ([Ljava/lang/Object;)V
    //   727: aload #16
    //   729: aload #15
    //   731: invokevirtual method_36454 : ()F
    //   734: iconst_1
    //   735: anewarray java/lang/Object
    //   738: dup_x1
    //   739: swap
    //   740: invokestatic valueOf : (F)Ljava/lang/Float;
    //   743: iconst_0
    //   744: swap
    //   745: aastore
    //   746: invokevirtual h : ([Ljava/lang/Object;)V
    //   749: aload #16
    //   751: aload #15
    //   753: invokevirtual method_24828 : ()Z
    //   756: iconst_1
    //   757: anewarray java/lang/Object
    //   760: dup_x1
    //   761: swap
    //   762: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   765: iconst_0
    //   766: swap
    //   767: aastore
    //   768: invokevirtual n : ([Ljava/lang/Object;)V
    //   771: aload #16
    //   773: new net/minecraft/class_243
    //   776: dup
    //   777: aload #15
    //   779: invokevirtual method_23317 : ()D
    //   782: aload #15
    //   784: invokevirtual method_23318 : ()D
    //   787: aload #15
    //   789: invokevirtual method_23321 : ()D
    //   792: invokespecial <init> : (DDD)V
    //   795: iconst_1
    //   796: anewarray java/lang/Object
    //   799: dup_x1
    //   800: swap
    //   801: iconst_0
    //   802: swap
    //   803: aastore
    //   804: invokevirtual R : ([Ljava/lang/Object;)V
    //   807: return
    // Exception table:
    //   from	to	target	type
    //   69	81	84	wtf/opal/x5
    //   75	89	92	wtf/opal/x5
    //   101	124	127	wtf/opal/x5
    //   113	136	139	wtf/opal/x5
    //   131	150	153	wtf/opal/x5
    //   157	171	174	wtf/opal/x5
    //   165	189	192	wtf/opal/x5
    //   178	231	234	wtf/opal/x5
    //   196	239	239	wtf/opal/x5
    //   294	342	345	wtf/opal/x5
    //   301	382	385	wtf/opal/x5
    //   349	391	394	wtf/opal/x5
    //   414	478	481	wtf/opal/x5
    //   427	531	534	wtf/opal/x5
    //   575	610	613	wtf/opal/x5
    //   580	693	696	wtf/opal/x5
  }
  
  static {
    long l = a ^ 0x2A1C1FEC47C2L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[5];
    boolean bool = false;
    String str;
    int i = (str = "Ñ¼»\\^\026l£»&H®Þê\020#éD¼\"¨âmpý×N d§äUÙóJ»zò7$ä$Ü$òì\025\031ð+ATº±Ê\f").length();
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x33AB;
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
        throw new RuntimeException("wtf/opal/j9", exception);
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
    //   66: ldc_w 'wtf/opal/j9'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\j9.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */