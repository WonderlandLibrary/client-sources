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

public final class xs extends d {
  private final kt G;
  
  private final ke X;
  
  private final kr n;
  
  private boolean f;
  
  private boolean t;
  
  private boolean a;
  
  private final gm<b6> E;
  
  private static final long b = on.a(-4960811209914010984L, 7491612159791910816L, MethodHandles.lookup().lookupClass()).a(210600862469321L);
  
  private static final String[] d;
  
  private static final String[] g;
  
  private static final Map k = new HashMap<>(13);
  
  private static final long[] l;
  
  private static final Integer[] m;
  
  private static final Map o;
  
  public xs(long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/xs.b : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: lload_1
    //   7: dup2
    //   8: ldc2_w 8137836031277
    //   11: lxor
    //   12: lstore_3
    //   13: dup2
    //   14: ldc2_w 45183839188088
    //   17: lxor
    //   18: lstore #5
    //   20: pop2
    //   21: aload_0
    //   22: sipush #2894
    //   25: ldc2_w 7393155419344105507
    //   28: lload_1
    //   29: lxor
    //   30: <illegal opcode> y : (IJ)Ljava/lang/String;
    //   35: lload #5
    //   37: sipush #19808
    //   40: ldc2_w 3738796695994185228
    //   43: lload_1
    //   44: lxor
    //   45: <illegal opcode> y : (IJ)Ljava/lang/String;
    //   50: getstatic wtf/opal/kn.WORLD : Lwtf/opal/kn;
    //   53: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   56: aload_0
    //   57: new wtf/opal/kt
    //   60: dup
    //   61: sipush #15080
    //   64: ldc2_w 1711211270342394242
    //   67: lload_1
    //   68: lxor
    //   69: <illegal opcode> y : (IJ)Ljava/lang/String;
    //   74: ldc2_w 10.0
    //   77: dconst_0
    //   78: ldc2_w 500.0
    //   81: ldc2_w 5.0
    //   84: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   87: putfield G : Lwtf/opal/kt;
    //   90: aload_0
    //   91: new wtf/opal/ke
    //   94: dup
    //   95: sipush #2315
    //   98: ldc2_w 9114576452852052580
    //   101: lload_1
    //   102: lxor
    //   103: <illegal opcode> y : (IJ)Ljava/lang/String;
    //   108: iconst_1
    //   109: invokespecial <init> : (Ljava/lang/String;Z)V
    //   112: putfield X : Lwtf/opal/ke;
    //   115: aload_0
    //   116: new wtf/opal/kr
    //   119: dup
    //   120: invokespecial <init> : ()V
    //   123: putfield n : Lwtf/opal/kr;
    //   126: aload_0
    //   127: aload_0
    //   128: <illegal opcode> H : (Lwtf/opal/xs;)Lwtf/opal/gm;
    //   133: putfield E : Lwtf/opal/gm;
    //   136: aload_0
    //   137: iconst_2
    //   138: anewarray wtf/opal/k3
    //   141: dup
    //   142: iconst_0
    //   143: aload_0
    //   144: getfield G : Lwtf/opal/kt;
    //   147: aastore
    //   148: dup
    //   149: iconst_1
    //   150: aload_0
    //   151: getfield X : Lwtf/opal/ke;
    //   154: aastore
    //   155: lload_3
    //   156: dup2_x1
    //   157: pop2
    //   158: iconst_2
    //   159: anewarray java/lang/Object
    //   162: dup_x1
    //   163: swap
    //   164: iconst_1
    //   165: swap
    //   166: aastore
    //   167: dup_x2
    //   168: dup_x2
    //   169: pop
    //   170: invokestatic valueOf : (J)Ljava/lang/Long;
    //   173: iconst_0
    //   174: swap
    //   175: aastore
    //   176: invokevirtual o : ([Ljava/lang/Object;)V
    //   179: return
  }
  
  private boolean X(Object[] paramArrayOfObject) {
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
    //   14: checkcast net/minecraft/class_1792
    //   17: astore_2
    //   18: pop
    //   19: getstatic wtf/opal/xs.b : J
    //   22: lload_3
    //   23: lxor
    //   24: lstore_3
    //   25: invokestatic k : ()[I
    //   28: astore #5
    //   30: aload_2
    //   31: getstatic net/minecraft/class_1802.field_8620 : Lnet/minecraft/class_1792;
    //   34: aload #5
    //   36: ifnull -> 60
    //   39: if_acmpeq -> 127
    //   42: goto -> 49
    //   45: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   48: athrow
    //   49: aload_2
    //   50: getstatic net/minecraft/class_1802.field_8695 : Lnet/minecraft/class_1792;
    //   53: goto -> 60
    //   56: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   59: athrow
    //   60: aload #5
    //   62: lload_3
    //   63: lconst_0
    //   64: lcmp
    //   65: ifle -> 100
    //   68: ifnull -> 92
    //   71: if_acmpeq -> 127
    //   74: goto -> 81
    //   77: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   80: athrow
    //   81: aload_2
    //   82: getstatic net/minecraft/class_1802.field_8477 : Lnet/minecraft/class_1792;
    //   85: goto -> 92
    //   88: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   91: athrow
    //   92: lload_3
    //   93: lconst_0
    //   94: lcmp
    //   95: iflt -> 124
    //   98: aload #5
    //   100: ifnull -> 124
    //   103: if_acmpeq -> 127
    //   106: goto -> 113
    //   109: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   112: athrow
    //   113: aload_2
    //   114: getstatic net/minecraft/class_1802.field_8687 : Lnet/minecraft/class_1792;
    //   117: goto -> 124
    //   120: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   123: athrow
    //   124: if_acmpne -> 135
    //   127: iconst_1
    //   128: goto -> 136
    //   131: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   134: athrow
    //   135: iconst_0
    //   136: ireturn
    // Exception table:
    //   from	to	target	type
    //   30	42	45	wtf/opal/x5
    //   39	53	56	wtf/opal/x5
    //   60	74	77	wtf/opal/x5
    //   71	85	88	wtf/opal/x5
    //   92	106	109	wtf/opal/x5
    //   103	117	120	wtf/opal/x5
    //   124	131	131	wtf/opal/x5
  }
  
  private void lambda$new$0(b6 paramb6) {
    // Byte code:
    //   0: getstatic wtf/opal/xs.b : J
    //   3: ldc2_w 49374215771632
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 73325796707460
    //   13: lxor
    //   14: lstore #4
    //   16: dup2
    //   17: ldc2_w 65128357765879
    //   20: lxor
    //   21: lstore #6
    //   23: pop2
    //   24: invokestatic k : ()[I
    //   27: astore #8
    //   29: aload_1
    //   30: iconst_0
    //   31: anewarray java/lang/Object
    //   34: invokevirtual W : ([Ljava/lang/Object;)Z
    //   37: ifne -> 45
    //   40: return
    //   41: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   44: athrow
    //   45: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   48: getfield field_1755 : Lnet/minecraft/class_437;
    //   51: astore #10
    //   53: aload #8
    //   55: ifnull -> 770
    //   58: aload #10
    //   60: instanceof net/minecraft/class_476
    //   63: ifeq -> 755
    //   66: goto -> 73
    //   69: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   72: athrow
    //   73: aload #10
    //   75: checkcast net/minecraft/class_476
    //   78: astore #9
    //   80: aload #8
    //   82: ifnull -> 770
    //   85: aload #9
    //   87: invokevirtual method_25440 : ()Lnet/minecraft/class_2561;
    //   90: invokeinterface getString : ()Ljava/lang/String;
    //   95: sipush #15826
    //   98: ldc2_w 347899461794282230
    //   101: lload_2
    //   102: lxor
    //   103: <illegal opcode> y : (IJ)Ljava/lang/String;
    //   108: invokevirtual contains : (Ljava/lang/CharSequence;)Z
    //   111: ifeq -> 755
    //   114: goto -> 121
    //   117: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   120: athrow
    //   121: aload_0
    //   122: getfield X : Lwtf/opal/ke;
    //   125: invokevirtual z : ()Ljava/lang/Object;
    //   128: checkcast java/lang/Boolean
    //   131: invokevirtual booleanValue : ()Z
    //   134: aload #8
    //   136: ifnull -> 252
    //   139: goto -> 146
    //   142: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   145: athrow
    //   146: ifeq -> 194
    //   149: goto -> 156
    //   152: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   155: athrow
    //   156: aload_0
    //   157: getfield f : Z
    //   160: aload #8
    //   162: ifnull -> 252
    //   165: goto -> 172
    //   168: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   171: athrow
    //   172: ifne -> 194
    //   175: goto -> 182
    //   178: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   181: athrow
    //   182: aload_0
    //   183: iconst_1
    //   184: putfield t : Z
    //   187: goto -> 194
    //   190: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   193: athrow
    //   194: aload_0
    //   195: iconst_1
    //   196: putfield f : Z
    //   199: aload_0
    //   200: getfield n : Lwtf/opal/kr;
    //   203: aload_0
    //   204: getfield G : Lwtf/opal/kt;
    //   207: invokevirtual z : ()Ljava/lang/Object;
    //   210: checkcast java/lang/Double
    //   213: invokevirtual longValue : ()J
    //   216: lload #4
    //   218: iconst_0
    //   219: iconst_3
    //   220: anewarray java/lang/Object
    //   223: dup_x1
    //   224: swap
    //   225: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   228: iconst_2
    //   229: swap
    //   230: aastore
    //   231: dup_x2
    //   232: dup_x2
    //   233: pop
    //   234: invokestatic valueOf : (J)Ljava/lang/Long;
    //   237: iconst_1
    //   238: swap
    //   239: aastore
    //   240: dup_x2
    //   241: dup_x2
    //   242: pop
    //   243: invokestatic valueOf : (J)Ljava/lang/Long;
    //   246: iconst_0
    //   247: swap
    //   248: aastore
    //   249: invokevirtual v : ([Ljava/lang/Object;)Z
    //   252: ifeq -> 781
    //   255: aload #9
    //   257: invokevirtual method_17577 : ()Lnet/minecraft/class_1703;
    //   260: checkcast net/minecraft/class_1707
    //   263: astore #10
    //   265: aload #10
    //   267: invokevirtual method_7629 : ()Lnet/minecraft/class_1263;
    //   270: astore #11
    //   272: sipush #11070
    //   275: ldc2_w 570987485703301668
    //   278: lload_2
    //   279: lxor
    //   280: <illegal opcode> r : (IJ)I
    //   285: iconst_1
    //   286: anewarray java/lang/Object
    //   289: dup_x1
    //   290: swap
    //   291: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   294: iconst_0
    //   295: swap
    //   296: aastore
    //   297: invokestatic D : ([Ljava/lang/Object;)Z
    //   300: aload #8
    //   302: ifnull -> 372
    //   305: ifeq -> 337
    //   308: goto -> 315
    //   311: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   314: athrow
    //   315: aload_0
    //   316: iconst_0
    //   317: putfield t : Z
    //   320: aload_0
    //   321: iconst_1
    //   322: putfield a : Z
    //   325: aload #8
    //   327: ifnonnull -> 404
    //   330: goto -> 337
    //   333: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   336: athrow
    //   337: sipush #4551
    //   340: ldc2_w 8339152725486325982
    //   343: lload_2
    //   344: lxor
    //   345: <illegal opcode> r : (IJ)I
    //   350: iconst_1
    //   351: anewarray java/lang/Object
    //   354: dup_x1
    //   355: swap
    //   356: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   359: iconst_0
    //   360: swap
    //   361: aastore
    //   362: invokestatic D : ([Ljava/lang/Object;)Z
    //   365: goto -> 372
    //   368: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   371: athrow
    //   372: aload #8
    //   374: ifnull -> 408
    //   377: ifeq -> 404
    //   380: goto -> 387
    //   383: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   386: athrow
    //   387: aload_0
    //   388: iconst_1
    //   389: putfield t : Z
    //   392: aload_0
    //   393: iconst_0
    //   394: putfield a : Z
    //   397: goto -> 404
    //   400: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   403: athrow
    //   404: aload_0
    //   405: getfield t : Z
    //   408: aload #8
    //   410: ifnull -> 599
    //   413: ifeq -> 595
    //   416: goto -> 423
    //   419: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   422: athrow
    //   423: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   426: getfield field_1724 : Lnet/minecraft/class_746;
    //   429: getfield field_7498 : Lnet/minecraft/class_1723;
    //   432: getfield field_7761 : Lnet/minecraft/class_2371;
    //   435: invokevirtual iterator : ()Ljava/util/Iterator;
    //   438: astore #12
    //   440: aload #12
    //   442: invokeinterface hasNext : ()Z
    //   447: ifeq -> 590
    //   450: aload #12
    //   452: invokeinterface next : ()Ljava/lang/Object;
    //   457: checkcast net/minecraft/class_1735
    //   460: astore #13
    //   462: aload_0
    //   463: aload #8
    //   465: ifnull -> 574
    //   468: aload #13
    //   470: invokevirtual method_7677 : ()Lnet/minecraft/class_1799;
    //   473: invokevirtual method_7909 : ()Lnet/minecraft/class_1792;
    //   476: lload #6
    //   478: dup2_x1
    //   479: pop2
    //   480: iconst_2
    //   481: anewarray java/lang/Object
    //   484: dup_x1
    //   485: swap
    //   486: iconst_1
    //   487: swap
    //   488: aastore
    //   489: dup_x2
    //   490: dup_x2
    //   491: pop
    //   492: invokestatic valueOf : (J)Ljava/lang/Long;
    //   495: iconst_0
    //   496: swap
    //   497: aastore
    //   498: invokevirtual X : ([Ljava/lang/Object;)Z
    //   501: aload #8
    //   503: ifnull -> 599
    //   506: goto -> 513
    //   509: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   512: athrow
    //   513: ifeq -> 585
    //   516: goto -> 523
    //   519: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   522: athrow
    //   523: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   526: getfield field_1761 : Lnet/minecraft/class_636;
    //   529: aload #10
    //   531: getfield field_7763 : I
    //   534: aload #13
    //   536: getfield field_7874 : I
    //   539: sipush #9720
    //   542: ldc2_w 4822798926781375712
    //   545: lload_2
    //   546: lxor
    //   547: <illegal opcode> r : (IJ)I
    //   552: iadd
    //   553: iconst_0
    //   554: getstatic net/minecraft/class_1713.field_7794 : Lnet/minecraft/class_1713;
    //   557: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   560: getfield field_1724 : Lnet/minecraft/class_746;
    //   563: invokevirtual method_2906 : (IIILnet/minecraft/class_1713;Lnet/minecraft/class_1657;)V
    //   566: aload_0
    //   567: goto -> 574
    //   570: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   573: athrow
    //   574: getfield n : Lwtf/opal/kr;
    //   577: iconst_0
    //   578: anewarray java/lang/Object
    //   581: invokevirtual z : ([Ljava/lang/Object;)V
    //   584: return
    //   585: aload #8
    //   587: ifnonnull -> 440
    //   590: aload_0
    //   591: iconst_0
    //   592: putfield t : Z
    //   595: aload_0
    //   596: getfield a : Z
    //   599: aload #8
    //   601: ifnull -> 615
    //   604: ifeq -> 752
    //   607: goto -> 614
    //   610: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   613: athrow
    //   614: iconst_0
    //   615: istore #12
    //   617: iload #12
    //   619: aload #11
    //   621: invokeinterface method_5439 : ()I
    //   626: if_icmpge -> 747
    //   629: aload_0
    //   630: aload #8
    //   632: ifnull -> 748
    //   635: aload #8
    //   637: ifnull -> 728
    //   640: goto -> 647
    //   643: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   646: athrow
    //   647: aload #11
    //   649: iload #12
    //   651: invokeinterface method_5438 : (I)Lnet/minecraft/class_1799;
    //   656: invokevirtual method_7909 : ()Lnet/minecraft/class_1792;
    //   659: lload #6
    //   661: dup2_x1
    //   662: pop2
    //   663: iconst_2
    //   664: anewarray java/lang/Object
    //   667: dup_x1
    //   668: swap
    //   669: iconst_1
    //   670: swap
    //   671: aastore
    //   672: dup_x2
    //   673: dup_x2
    //   674: pop
    //   675: invokestatic valueOf : (J)Ljava/lang/Long;
    //   678: iconst_0
    //   679: swap
    //   680: aastore
    //   681: invokevirtual X : ([Ljava/lang/Object;)Z
    //   684: ifeq -> 739
    //   687: goto -> 694
    //   690: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   693: athrow
    //   694: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   697: getfield field_1761 : Lnet/minecraft/class_636;
    //   700: aload #10
    //   702: getfield field_7763 : I
    //   705: iload #12
    //   707: iconst_0
    //   708: getstatic net/minecraft/class_1713.field_7794 : Lnet/minecraft/class_1713;
    //   711: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   714: getfield field_1724 : Lnet/minecraft/class_746;
    //   717: invokevirtual method_2906 : (IIILnet/minecraft/class_1713;Lnet/minecraft/class_1657;)V
    //   720: aload_0
    //   721: goto -> 728
    //   724: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   727: athrow
    //   728: getfield n : Lwtf/opal/kr;
    //   731: iconst_0
    //   732: anewarray java/lang/Object
    //   735: invokevirtual z : ([Ljava/lang/Object;)V
    //   738: return
    //   739: iinc #12, 1
    //   742: aload #8
    //   744: ifnonnull -> 617
    //   747: aload_0
    //   748: iconst_0
    //   749: putfield a : Z
    //   752: goto -> 781
    //   755: aload_0
    //   756: aload_0
    //   757: aload_0
    //   758: iconst_0
    //   759: dup_x1
    //   760: putfield a : Z
    //   763: dup_x1
    //   764: putfield t : Z
    //   767: putfield f : Z
    //   770: aload_0
    //   771: getfield n : Lwtf/opal/kr;
    //   774: iconst_0
    //   775: anewarray java/lang/Object
    //   778: invokevirtual z : ([Ljava/lang/Object;)V
    //   781: return
    // Exception table:
    //   from	to	target	type
    //   29	41	41	wtf/opal/x5
    //   53	66	69	wtf/opal/x5
    //   80	114	117	wtf/opal/x5
    //   85	139	142	wtf/opal/x5
    //   121	149	152	wtf/opal/x5
    //   146	165	168	wtf/opal/x5
    //   156	175	178	wtf/opal/x5
    //   172	187	190	wtf/opal/x5
    //   272	308	311	wtf/opal/x5
    //   305	330	333	wtf/opal/x5
    //   315	365	368	wtf/opal/x5
    //   372	380	383	wtf/opal/x5
    //   377	397	400	wtf/opal/x5
    //   408	416	419	wtf/opal/x5
    //   462	506	509	wtf/opal/x5
    //   468	516	519	wtf/opal/x5
    //   513	567	570	wtf/opal/x5
    //   599	607	610	wtf/opal/x5
    //   629	640	643	wtf/opal/x5
    //   635	687	690	wtf/opal/x5
    //   647	721	724	wtf/opal/x5
  }
  
  static {
    long l = b ^ 0x26CD24A1A6C1L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[5];
    boolean bool = false;
    String str;
    int i = (str = ">Üa)¹(an\013quøBô# ÿjÎ¿¬sÒn¹8qÄ\0003¤«kÝQH49.ÊénLô§P®t¡&Þ%â/ª82.7Ñ.Þ$óØêIWaêú7Dü<,ý¦ñ^ðÄè.sß¤\0328yÒ£$-´ûfç.è\022\030\020jwÐ\024\034¤¨}àn¨#\037d").length();
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x157F;
    if (g[i] == null) {
      Object[] arrayOfObject;
      try {
        Long long_ = Long.valueOf(Thread.currentThread().threadId());
        arrayOfObject = (Object[])k.get(long_);
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/PKCS5Padding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          k.put(long_, arrayOfObject);
        } 
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/xs", exception);
      } 
      byte[] arrayOfByte1 = new byte[8];
      arrayOfByte1[0] = (byte)(int)(paramLong >>> 56L);
      for (byte b = 1; b < 8; b++)
        arrayOfByte1[b] = (byte)(int)(paramLong << b * 8 >>> 56L); 
      DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
      SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
      ((Cipher)arrayOfObject[0]).init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
      byte[] arrayOfByte2 = d[i].getBytes("ISO-8859-1");
      g[i] = a(((Cipher)arrayOfObject[0]).doFinal(arrayOfByte2));
    } 
    return g[i];
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
    //   65: ldc_w 'wtf/opal/xs'
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x5343;
    if (m[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l = l[i];
      byte[] arrayOfByte2 = { (byte)(int)(l >>> 56L), (byte)(int)(l >>> 48L), (byte)(int)(l >>> 40L), (byte)(int)(l >>> 32L), (byte)(int)(l >>> 24L), (byte)(int)(l >>> 16L), (byte)(int)(l >>> 8L), (byte)(int)l };
      Long long_ = Long.valueOf(Thread.currentThread().threadId());
      Object[] arrayOfObject = (Object[])o.get(long_);
      try {
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/NoPadding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          o.put(long_, arrayOfObject);
        } 
        DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
        SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
        Cipher cipher = (Cipher)arrayOfObject[0];
        cipher.init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
        arrayOfByte3 = cipher.doFinal(arrayOfByte2);
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/xs", exception);
      } 
      int j = (arrayOfByte3[4] & 0xFF) << 24 | (arrayOfByte3[5] & 0xFF) << 16 | (arrayOfByte3[6] & 0xFF) << 8 | arrayOfByte3[7] & 0xFF;
      m[i] = Integer.valueOf(j);
    } 
    return m[i].intValue();
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
    //   65: ldc_w 'wtf/opal/xs'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\xs.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */