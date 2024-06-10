package wtf.opal;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.class_437;

public final class n extends d {
  private final kt x;
  
  private final kt E;
  
  private final kt o;
  
  private final kt s;
  
  private final kt k;
  
  private final ke z;
  
  private final ke r;
  
  private final ke D;
  
  private final ke Z;
  
  private final ke W;
  
  private final ke T;
  
  private final ke L;
  
  private final ThreadLocalRandom J;
  
  private long A;
  
  private long n;
  
  private long I;
  
  private long u;
  
  private double b;
  
  private boolean y;
  
  private final kr d;
  
  private final kr m;
  
  private boolean l;
  
  private final gm<uj> R;
  
  private static final long a = on.a(5052133790510561568L, 3476545465475471856L, MethodHandles.lookup().lookupClass()).a(23186107559194L);
  
  private static final String[] f;
  
  private static final String[] g;
  
  private static final Map p = new HashMap<>(13);
  
  private static final long[] q;
  
  private static final Integer[] t;
  
  private static final Map v;
  
  private static final long[] w;
  
  private static final Long[] B;
  
  private static final Map G;
  
  public n(int paramInt1, char paramChar, int paramInt2) {
    // Byte code:
    //   0: iload_1
    //   1: i2l
    //   2: bipush #32
    //   4: lshl
    //   5: iload_2
    //   6: i2l
    //   7: bipush #48
    //   9: lshl
    //   10: bipush #32
    //   12: lushr
    //   13: lor
    //   14: iload_3
    //   15: i2l
    //   16: bipush #48
    //   18: lshl
    //   19: bipush #48
    //   21: lushr
    //   22: lor
    //   23: getstatic wtf/opal/n.a : J
    //   26: lxor
    //   27: lstore #4
    //   29: lload #4
    //   31: dup2
    //   32: ldc2_w 57101027199302
    //   35: lxor
    //   36: lstore #6
    //   38: dup2
    //   39: ldc2_w 128598979967546
    //   42: lxor
    //   43: lstore #8
    //   45: dup2
    //   46: ldc2_w 99554573198191
    //   49: lxor
    //   50: lstore #10
    //   52: pop2
    //   53: aload_0
    //   54: sipush #3412
    //   57: ldc2_w 8879290124133629179
    //   60: lload #4
    //   62: lxor
    //   63: <illegal opcode> o : (IJ)Ljava/lang/String;
    //   68: lload #10
    //   70: sipush #1159
    //   73: ldc2_w 3525467079071397156
    //   76: lload #4
    //   78: lxor
    //   79: <illegal opcode> o : (IJ)Ljava/lang/String;
    //   84: getstatic wtf/opal/kn.COMBAT : Lwtf/opal/kn;
    //   87: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   90: aload_0
    //   91: new wtf/opal/kt
    //   94: dup
    //   95: sipush #16508
    //   98: ldc2_w 6753557676896137682
    //   101: lload #4
    //   103: lxor
    //   104: <illegal opcode> o : (IJ)Ljava/lang/String;
    //   109: ldc2_w 10.0
    //   112: dconst_1
    //   113: ldc2_w 20.0
    //   116: ldc2_w 0.1
    //   119: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   122: putfield x : Lwtf/opal/kt;
    //   125: aload_0
    //   126: new wtf/opal/kt
    //   129: dup
    //   130: sipush #566
    //   133: ldc2_w 3700588774152598423
    //   136: lload #4
    //   138: lxor
    //   139: <illegal opcode> o : (IJ)Ljava/lang/String;
    //   144: ldc2_w 14.0
    //   147: dconst_1
    //   148: ldc2_w 20.0
    //   151: ldc2_w 0.1
    //   154: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   157: putfield E : Lwtf/opal/kt;
    //   160: aload_0
    //   161: new wtf/opal/kt
    //   164: dup
    //   165: sipush #11605
    //   168: ldc2_w 1134553809267219697
    //   171: lload #4
    //   173: lxor
    //   174: <illegal opcode> o : (IJ)Ljava/lang/String;
    //   179: dconst_0
    //   180: dconst_0
    //   181: ldc2_w 100.0
    //   184: ldc2_w 50.0
    //   187: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   190: putfield o : Lwtf/opal/kt;
    //   193: aload_0
    //   194: new wtf/opal/kt
    //   197: dup
    //   198: sipush #18822
    //   201: ldc2_w 2742454111916865580
    //   204: lload #4
    //   206: lxor
    //   207: <illegal opcode> o : (IJ)Ljava/lang/String;
    //   212: dconst_0
    //   213: dconst_0
    //   214: ldc2_w 100.0
    //   217: ldc2_w 5.0
    //   220: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   223: putfield s : Lwtf/opal/kt;
    //   226: aload_0
    //   227: new wtf/opal/kt
    //   230: dup
    //   231: sipush #26665
    //   234: ldc2_w 4051019363105577355
    //   237: lload #4
    //   239: lxor
    //   240: <illegal opcode> o : (IJ)Ljava/lang/String;
    //   245: ldc2_w 0.5
    //   248: ldc2_w 0.01
    //   251: dconst_1
    //   252: ldc2_w 0.01
    //   255: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   258: putfield k : Lwtf/opal/kt;
    //   261: aload_0
    //   262: new wtf/opal/ke
    //   265: dup
    //   266: sipush #20250
    //   269: ldc2_w 3412246517549874866
    //   272: lload #4
    //   274: lxor
    //   275: <illegal opcode> o : (IJ)Ljava/lang/String;
    //   280: iconst_1
    //   281: invokespecial <init> : (Ljava/lang/String;Z)V
    //   284: putfield z : Lwtf/opal/ke;
    //   287: aload_0
    //   288: new wtf/opal/ke
    //   291: dup
    //   292: sipush #6948
    //   295: ldc2_w 6652555300193790593
    //   298: lload #4
    //   300: lxor
    //   301: <illegal opcode> o : (IJ)Ljava/lang/String;
    //   306: iconst_0
    //   307: invokespecial <init> : (Ljava/lang/String;Z)V
    //   310: putfield r : Lwtf/opal/ke;
    //   313: aload_0
    //   314: new wtf/opal/ke
    //   317: dup
    //   318: sipush #24627
    //   321: ldc2_w 4032092082159784344
    //   324: lload #4
    //   326: lxor
    //   327: <illegal opcode> o : (IJ)Ljava/lang/String;
    //   332: iconst_1
    //   333: invokespecial <init> : (Ljava/lang/String;Z)V
    //   336: putfield D : Lwtf/opal/ke;
    //   339: aload_0
    //   340: new wtf/opal/ke
    //   343: dup
    //   344: sipush #16600
    //   347: ldc2_w 1423632108968784241
    //   350: lload #4
    //   352: lxor
    //   353: <illegal opcode> o : (IJ)Ljava/lang/String;
    //   358: iconst_1
    //   359: invokespecial <init> : (Ljava/lang/String;Z)V
    //   362: putfield Z : Lwtf/opal/ke;
    //   365: aload_0
    //   366: new wtf/opal/ke
    //   369: dup
    //   370: sipush #22913
    //   373: ldc2_w 760461069120186406
    //   376: lload #4
    //   378: lxor
    //   379: <illegal opcode> o : (IJ)Ljava/lang/String;
    //   384: iconst_0
    //   385: invokespecial <init> : (Ljava/lang/String;Z)V
    //   388: putfield W : Lwtf/opal/ke;
    //   391: aload_0
    //   392: new wtf/opal/ke
    //   395: dup
    //   396: sipush #7140
    //   399: ldc2_w 6231539208879925826
    //   402: lload #4
    //   404: lxor
    //   405: <illegal opcode> o : (IJ)Ljava/lang/String;
    //   410: iconst_0
    //   411: invokespecial <init> : (Ljava/lang/String;Z)V
    //   414: putfield T : Lwtf/opal/ke;
    //   417: invokestatic y : ()I
    //   420: aload_0
    //   421: new wtf/opal/ke
    //   424: dup
    //   425: sipush #2779
    //   428: ldc2_w 4368094078724602747
    //   431: lload #4
    //   433: lxor
    //   434: <illegal opcode> o : (IJ)Ljava/lang/String;
    //   439: iconst_1
    //   440: invokespecial <init> : (Ljava/lang/String;Z)V
    //   443: putfield L : Lwtf/opal/ke;
    //   446: aload_0
    //   447: invokestatic current : ()Ljava/util/concurrent/ThreadLocalRandom;
    //   450: putfield J : Ljava/util/concurrent/ThreadLocalRandom;
    //   453: aload_0
    //   454: new wtf/opal/kr
    //   457: dup
    //   458: invokespecial <init> : ()V
    //   461: putfield d : Lwtf/opal/kr;
    //   464: aload_0
    //   465: new wtf/opal/kr
    //   468: dup
    //   469: invokespecial <init> : ()V
    //   472: putfield m : Lwtf/opal/kr;
    //   475: aload_0
    //   476: aload_0
    //   477: <illegal opcode> H : (Lwtf/opal/n;)Lwtf/opal/gm;
    //   482: putfield R : Lwtf/opal/gm;
    //   485: istore #12
    //   487: aload_0
    //   488: getfield s : Lwtf/opal/kt;
    //   491: aload_0
    //   492: getfield W : Lwtf/opal/ke;
    //   495: <illegal opcode> test : ()Ljava/util/function/Predicate;
    //   500: lload #6
    //   502: dup2_x1
    //   503: pop2
    //   504: iconst_3
    //   505: anewarray java/lang/Object
    //   508: dup_x1
    //   509: swap
    //   510: iconst_2
    //   511: swap
    //   512: aastore
    //   513: dup_x2
    //   514: dup_x2
    //   515: pop
    //   516: invokestatic valueOf : (J)Ljava/lang/Long;
    //   519: iconst_1
    //   520: swap
    //   521: aastore
    //   522: dup_x1
    //   523: swap
    //   524: iconst_0
    //   525: swap
    //   526: aastore
    //   527: invokevirtual C : ([Ljava/lang/Object;)V
    //   530: aload_0
    //   531: getfield k : Lwtf/opal/kt;
    //   534: aload_0
    //   535: getfield r : Lwtf/opal/ke;
    //   538: <illegal opcode> test : ()Ljava/util/function/Predicate;
    //   543: lload #6
    //   545: dup2_x1
    //   546: pop2
    //   547: iconst_3
    //   548: anewarray java/lang/Object
    //   551: dup_x1
    //   552: swap
    //   553: iconst_2
    //   554: swap
    //   555: aastore
    //   556: dup_x2
    //   557: dup_x2
    //   558: pop
    //   559: invokestatic valueOf : (J)Ljava/lang/Long;
    //   562: iconst_1
    //   563: swap
    //   564: aastore
    //   565: dup_x1
    //   566: swap
    //   567: iconst_0
    //   568: swap
    //   569: aastore
    //   570: invokevirtual C : ([Ljava/lang/Object;)V
    //   573: aload_0
    //   574: sipush #12924
    //   577: ldc2_w 3634594561962265626
    //   580: lload #4
    //   582: lxor
    //   583: <illegal opcode> o : (IJ)I
    //   588: anewarray wtf/opal/k3
    //   591: dup
    //   592: iconst_0
    //   593: aload_0
    //   594: getfield x : Lwtf/opal/kt;
    //   597: aastore
    //   598: dup
    //   599: iconst_1
    //   600: aload_0
    //   601: getfield E : Lwtf/opal/kt;
    //   604: aastore
    //   605: dup
    //   606: iconst_2
    //   607: aload_0
    //   608: getfield o : Lwtf/opal/kt;
    //   611: aastore
    //   612: dup
    //   613: iconst_3
    //   614: aload_0
    //   615: getfield s : Lwtf/opal/kt;
    //   618: aastore
    //   619: dup
    //   620: iconst_4
    //   621: aload_0
    //   622: getfield z : Lwtf/opal/ke;
    //   625: aastore
    //   626: dup
    //   627: iconst_5
    //   628: aload_0
    //   629: getfield r : Lwtf/opal/ke;
    //   632: aastore
    //   633: dup
    //   634: sipush #23331
    //   637: ldc2_w 5581121014094931267
    //   640: lload #4
    //   642: lxor
    //   643: <illegal opcode> o : (IJ)I
    //   648: aload_0
    //   649: getfield k : Lwtf/opal/kt;
    //   652: aastore
    //   653: dup
    //   654: sipush #28315
    //   657: ldc2_w 907724917019906289
    //   660: lload #4
    //   662: lxor
    //   663: <illegal opcode> o : (IJ)I
    //   668: aload_0
    //   669: getfield D : Lwtf/opal/ke;
    //   672: aastore
    //   673: dup
    //   674: sipush #4652
    //   677: ldc2_w 2942557520149376078
    //   680: lload #4
    //   682: lxor
    //   683: <illegal opcode> o : (IJ)I
    //   688: aload_0
    //   689: getfield Z : Lwtf/opal/ke;
    //   692: aastore
    //   693: dup
    //   694: sipush #7705
    //   697: ldc2_w 2560143230411137148
    //   700: lload #4
    //   702: lxor
    //   703: <illegal opcode> o : (IJ)I
    //   708: aload_0
    //   709: getfield W : Lwtf/opal/ke;
    //   712: aastore
    //   713: dup
    //   714: sipush #17291
    //   717: ldc2_w 4732427030501768682
    //   720: lload #4
    //   722: lxor
    //   723: <illegal opcode> o : (IJ)I
    //   728: aload_0
    //   729: getfield T : Lwtf/opal/ke;
    //   732: aastore
    //   733: dup
    //   734: sipush #32612
    //   737: ldc2_w 5143634504138031363
    //   740: lload #4
    //   742: lxor
    //   743: <illegal opcode> o : (IJ)I
    //   748: aload_0
    //   749: getfield L : Lwtf/opal/ke;
    //   752: aastore
    //   753: lload #8
    //   755: dup2_x1
    //   756: pop2
    //   757: iconst_2
    //   758: anewarray java/lang/Object
    //   761: dup_x1
    //   762: swap
    //   763: iconst_1
    //   764: swap
    //   765: aastore
    //   766: dup_x2
    //   767: dup_x2
    //   768: pop
    //   769: invokestatic valueOf : (J)Ljava/lang/Long;
    //   772: iconst_0
    //   773: swap
    //   774: aastore
    //   775: invokevirtual o : ([Ljava/lang/Object;)V
    //   778: invokestatic D : ()[Lwtf/opal/d;
    //   781: ifnull -> 799
    //   784: iinc #12, 1
    //   787: iload #12
    //   789: invokestatic H : (I)V
    //   792: goto -> 799
    //   795: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   798: athrow
    //   799: return
    // Exception table:
    //   from	to	target	type
    //   487	792	795	wtf/opal/x5
  }
  
  private void V(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: getstatic wtf/opal/n.a : J
    //   15: lload_2
    //   16: lxor
    //   17: lstore_2
    //   18: lload_2
    //   19: dup2
    //   20: ldc2_w 32310560587691
    //   23: lxor
    //   24: lstore #4
    //   26: pop2
    //   27: invokestatic F : ()I
    //   30: aload_0
    //   31: getfield x : Lwtf/opal/kt;
    //   34: invokevirtual z : ()Ljava/lang/Object;
    //   37: checkcast java/lang/Double
    //   40: invokevirtual doubleValue : ()D
    //   43: dstore #7
    //   45: aload_0
    //   46: getfield E : Lwtf/opal/kt;
    //   49: invokevirtual z : ()Ljava/lang/Object;
    //   52: checkcast java/lang/Double
    //   55: invokevirtual doubleValue : ()D
    //   58: dstore #9
    //   60: istore #6
    //   62: aload_0
    //   63: getfield o : Lwtf/opal/kt;
    //   66: invokevirtual z : ()Ljava/lang/Object;
    //   69: checkcast java/lang/Double
    //   72: invokevirtual doubleValue : ()D
    //   75: dconst_0
    //   76: iload #6
    //   78: ifne -> 239
    //   81: dcmpl
    //   82: ifeq -> 215
    //   85: goto -> 92
    //   88: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   91: athrow
    //   92: aload_0
    //   93: iload #6
    //   95: ifne -> 216
    //   98: goto -> 105
    //   101: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   104: athrow
    //   105: getfield m : Lwtf/opal/kr;
    //   108: aload_0
    //   109: getfield o : Lwtf/opal/kt;
    //   112: invokevirtual z : ()Ljava/lang/Object;
    //   115: checkcast java/lang/Double
    //   118: invokevirtual longValue : ()J
    //   121: lload #4
    //   123: iconst_1
    //   124: iconst_3
    //   125: anewarray java/lang/Object
    //   128: dup_x1
    //   129: swap
    //   130: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   133: iconst_2
    //   134: swap
    //   135: aastore
    //   136: dup_x2
    //   137: dup_x2
    //   138: pop
    //   139: invokestatic valueOf : (J)Ljava/lang/Long;
    //   142: iconst_1
    //   143: swap
    //   144: aastore
    //   145: dup_x2
    //   146: dup_x2
    //   147: pop
    //   148: invokestatic valueOf : (J)Ljava/lang/Long;
    //   151: iconst_0
    //   152: swap
    //   153: aastore
    //   154: invokevirtual v : ([Ljava/lang/Object;)Z
    //   157: ifne -> 215
    //   160: goto -> 167
    //   163: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   166: athrow
    //   167: ldc2_w 0.5
    //   170: aload_0
    //   171: getfield m : Lwtf/opal/kr;
    //   174: iconst_0
    //   175: anewarray java/lang/Object
    //   178: invokevirtual E : ([Ljava/lang/Object;)J
    //   181: l2d
    //   182: aload_0
    //   183: getfield o : Lwtf/opal/kt;
    //   186: invokevirtual z : ()Ljava/lang/Object;
    //   189: checkcast java/lang/Double
    //   192: invokevirtual doubleValue : ()D
    //   195: ddiv
    //   196: invokestatic max : (DD)D
    //   199: dstore #11
    //   201: dload #7
    //   203: dload #11
    //   205: dmul
    //   206: dstore #7
    //   208: dload #9
    //   210: dload #11
    //   212: dmul
    //   213: dstore #9
    //   215: aload_0
    //   216: getfield J : Ljava/util/concurrent/ThreadLocalRandom;
    //   219: dload #7
    //   221: dload #9
    //   223: invokevirtual nextDouble : (DD)D
    //   226: aload_0
    //   227: getfield J : Ljava/util/concurrent/ThreadLocalRandom;
    //   230: ldc2_w 0.25
    //   233: ldc2_w 0.75
    //   236: invokevirtual nextDouble : (DD)D
    //   239: dadd
    //   240: dstore #11
    //   242: ldc2_w 1000.0
    //   245: dload #11
    //   247: ddiv
    //   248: d2l
    //   249: lstore #13
    //   251: invokestatic currentTimeMillis : ()J
    //   254: aload_0
    //   255: getfield I : J
    //   258: lcmp
    //   259: iload #6
    //   261: lload_2
    //   262: lconst_0
    //   263: lcmp
    //   264: ifle -> 488
    //   267: ifne -> 480
    //   270: ifle -> 476
    //   273: goto -> 280
    //   276: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   279: athrow
    //   280: aload_0
    //   281: iload #6
    //   283: ifne -> 429
    //   286: goto -> 293
    //   289: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   292: athrow
    //   293: lload_2
    //   294: lconst_0
    //   295: lcmp
    //   296: iflt -> 422
    //   299: getfield y : Z
    //   302: ifne -> 421
    //   305: goto -> 312
    //   308: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   311: athrow
    //   312: aload_0
    //   313: iload #6
    //   315: lload_2
    //   316: lconst_0
    //   317: lcmp
    //   318: iflt -> 430
    //   321: ifne -> 429
    //   324: goto -> 331
    //   327: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   330: athrow
    //   331: lload_2
    //   332: lconst_0
    //   333: lcmp
    //   334: iflt -> 422
    //   337: getfield J : Ljava/util/concurrent/ThreadLocalRandom;
    //   340: sipush #29212
    //   343: ldc2_w 2288649479809160198
    //   346: lload_2
    //   347: lxor
    //   348: <illegal opcode> o : (IJ)I
    //   353: invokevirtual nextInt : (I)I
    //   356: sipush #1684
    //   359: ldc2_w 6351906161378795663
    //   362: lload_2
    //   363: lxor
    //   364: <illegal opcode> o : (IJ)I
    //   369: if_icmplt -> 421
    //   372: goto -> 379
    //   375: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   378: athrow
    //   379: aload_0
    //   380: iconst_1
    //   381: putfield y : Z
    //   384: aload_0
    //   385: lload_2
    //   386: lconst_0
    //   387: lcmp
    //   388: iflt -> 434
    //   391: ldc2_w 1.1
    //   394: aload_0
    //   395: getfield J : Ljava/util/concurrent/ThreadLocalRandom;
    //   398: invokevirtual nextDouble : ()D
    //   401: ldc2_w 0.2
    //   404: dmul
    //   405: dadd
    //   406: putfield b : D
    //   409: iload #6
    //   411: ifeq -> 433
    //   414: goto -> 421
    //   417: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   420: athrow
    //   421: aload_0
    //   422: goto -> 429
    //   425: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   428: athrow
    //   429: iconst_0
    //   430: putfield y : Z
    //   433: aload_0
    //   434: invokestatic currentTimeMillis : ()J
    //   437: sipush #4525
    //   440: ldc2_w 5116169194230328388
    //   443: lload_2
    //   444: lxor
    //   445: <illegal opcode> e : (IJ)J
    //   450: ladd
    //   451: aload_0
    //   452: getfield J : Ljava/util/concurrent/ThreadLocalRandom;
    //   455: sipush #1301
    //   458: ldc2_w 670397734621389580
    //   461: lload_2
    //   462: lxor
    //   463: <illegal opcode> o : (IJ)I
    //   468: invokevirtual nextInt : (I)I
    //   471: i2l
    //   472: ladd
    //   473: putfield I : J
    //   476: aload_0
    //   477: getfield y : Z
    //   480: lload_2
    //   481: lconst_0
    //   482: lcmp
    //   483: ifle -> 519
    //   486: iload #6
    //   488: ifne -> 519
    //   491: ifeq -> 511
    //   494: goto -> 501
    //   497: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   500: athrow
    //   501: lload #13
    //   503: aload_0
    //   504: getfield b : D
    //   507: d2l
    //   508: lmul
    //   509: lstore #13
    //   511: invokestatic currentTimeMillis : ()J
    //   514: aload_0
    //   515: getfield u : J
    //   518: lcmp
    //   519: ifle -> 572
    //   522: aload_0
    //   523: invokestatic currentTimeMillis : ()J
    //   526: sipush #7888
    //   529: ldc2_w 1249843669900108602
    //   532: lload_2
    //   533: lxor
    //   534: <illegal opcode> e : (IJ)J
    //   539: ladd
    //   540: aload_0
    //   541: getfield J : Ljava/util/concurrent/ThreadLocalRandom;
    //   544: sipush #2690
    //   547: ldc2_w 7902342269429427348
    //   550: lload_2
    //   551: lxor
    //   552: <illegal opcode> o : (IJ)I
    //   557: invokevirtual nextInt : (I)I
    //   560: i2l
    //   561: ladd
    //   562: putfield u : J
    //   565: goto -> 572
    //   568: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   571: athrow
    //   572: aload_0
    //   573: invokestatic currentTimeMillis : ()J
    //   576: lload #13
    //   578: ladd
    //   579: putfield n : J
    //   582: aload_0
    //   583: invokestatic currentTimeMillis : ()J
    //   586: lload #13
    //   588: sipush #4339
    //   591: ldc2_w 1629764695015042331
    //   594: lload_2
    //   595: lxor
    //   596: <illegal opcode> e : (IJ)J
    //   601: ldiv
    //   602: ladd
    //   603: aload_0
    //   604: getfield J : Ljava/util/concurrent/ThreadLocalRandom;
    //   607: sipush #5318
    //   610: ldc2_w 427952203511837399
    //   613: lload_2
    //   614: lxor
    //   615: <illegal opcode> o : (IJ)I
    //   620: invokevirtual nextInt : (I)I
    //   623: i2l
    //   624: lsub
    //   625: putfield A : J
    //   628: return
    // Exception table:
    //   from	to	target	type
    //   62	85	88	wtf/opal/x5
    //   81	98	101	wtf/opal/x5
    //   92	160	163	wtf/opal/x5
    //   251	273	276	wtf/opal/x5
    //   270	286	289	wtf/opal/x5
    //   280	305	308	wtf/opal/x5
    //   293	324	327	wtf/opal/x5
    //   312	372	375	wtf/opal/x5
    //   331	414	417	wtf/opal/x5
    //   379	422	425	wtf/opal/x5
    //   480	494	497	wtf/opal/x5
    //   519	565	568	wtf/opal/x5
  }
  
  private void s(Object[] paramArrayOfObject) {
    class_437 class_437 = b9.c.field_1755;
  }
  
  private boolean b(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: getstatic wtf/opal/n.a : J
    //   15: lload_2
    //   16: lxor
    //   17: lstore_2
    //   18: lload_2
    //   19: dup2
    //   20: ldc2_w 55042686555025
    //   23: lxor
    //   24: lstore #4
    //   26: pop2
    //   27: invokestatic F : ()I
    //   30: istore #6
    //   32: aload_0
    //   33: getfield D : Lwtf/opal/ke;
    //   36: invokevirtual z : ()Ljava/lang/Object;
    //   39: checkcast java/lang/Boolean
    //   42: invokevirtual booleanValue : ()Z
    //   45: iload #6
    //   47: ifne -> 94
    //   50: ifeq -> 148
    //   53: goto -> 60
    //   56: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   59: athrow
    //   60: lload #4
    //   62: iconst_1
    //   63: iconst_2
    //   64: anewarray java/lang/Object
    //   67: dup_x1
    //   68: swap
    //   69: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   72: iconst_1
    //   73: swap
    //   74: aastore
    //   75: dup_x2
    //   76: dup_x2
    //   77: pop
    //   78: invokestatic valueOf : (J)Ljava/lang/Long;
    //   81: iconst_0
    //   82: swap
    //   83: aastore
    //   84: invokestatic g : ([Ljava/lang/Object;)Z
    //   87: goto -> 94
    //   90: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   93: athrow
    //   94: iload #6
    //   96: lload_2
    //   97: lconst_0
    //   98: lcmp
    //   99: iflt -> 131
    //   102: ifne -> 129
    //   105: ifeq -> 148
    //   108: goto -> 115
    //   111: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   114: athrow
    //   115: iconst_0
    //   116: anewarray java/lang/Object
    //   119: invokestatic g : ([Ljava/lang/Object;)Z
    //   122: goto -> 129
    //   125: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   128: athrow
    //   129: iload #6
    //   131: ifne -> 145
    //   134: ifeq -> 148
    //   137: goto -> 144
    //   140: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   143: athrow
    //   144: iconst_1
    //   145: goto -> 149
    //   148: iconst_0
    //   149: ireturn
    // Exception table:
    //   from	to	target	type
    //   32	53	56	wtf/opal/x5
    //   50	87	90	wtf/opal/x5
    //   94	108	111	wtf/opal/x5
    //   105	122	125	wtf/opal/x5
    //   129	137	140	wtf/opal/x5
  }
  
  private void lambda$new$0(uj paramuj) {
    // Byte code:
    //   0: getstatic wtf/opal/n.a : J
    //   3: ldc2_w 27510932755547
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 16773717030191
    //   13: lxor
    //   14: lstore #4
    //   16: dup2
    //   17: ldc2_w 107487024604073
    //   20: lxor
    //   21: lstore #6
    //   23: dup2
    //   24: ldc2_w 29979380001348
    //   27: lxor
    //   28: lstore #8
    //   30: dup2
    //   31: ldc2_w 74211418772294
    //   34: lxor
    //   35: lstore #10
    //   37: dup2
    //   38: ldc2_w 99687405900024
    //   41: lxor
    //   42: lstore #12
    //   44: pop2
    //   45: invokestatic F : ()I
    //   48: istore #14
    //   50: aload_0
    //   51: getfield x : Lwtf/opal/kt;
    //   54: iload #14
    //   56: ifne -> 103
    //   59: invokevirtual z : ()Ljava/lang/Object;
    //   62: checkcast java/lang/Double
    //   65: invokevirtual doubleValue : ()D
    //   68: aload_0
    //   69: getfield E : Lwtf/opal/kt;
    //   72: invokevirtual z : ()Ljava/lang/Object;
    //   75: checkcast java/lang/Double
    //   78: invokevirtual doubleValue : ()D
    //   81: dcmpl
    //   82: ifle -> 125
    //   85: goto -> 92
    //   88: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   91: athrow
    //   92: aload_0
    //   93: getfield x : Lwtf/opal/kt;
    //   96: goto -> 103
    //   99: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   102: athrow
    //   103: aload_0
    //   104: getfield E : Lwtf/opal/kt;
    //   107: invokevirtual z : ()Ljava/lang/Object;
    //   110: checkcast java/lang/Double
    //   113: iconst_1
    //   114: anewarray java/lang/Object
    //   117: dup_x1
    //   118: swap
    //   119: iconst_0
    //   120: swap
    //   121: aastore
    //   122: invokevirtual V : ([Ljava/lang/Object;)V
    //   125: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   128: getfield field_1690 : Lnet/minecraft/class_315;
    //   131: getfield field_1886 : Lnet/minecraft/class_304;
    //   134: astore #15
    //   136: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   139: getfield field_1690 : Lnet/minecraft/class_315;
    //   142: getfield field_1904 : Lnet/minecraft/class_304;
    //   145: astore #16
    //   147: aload #15
    //   149: invokevirtual method_1429 : ()Lnet/minecraft/class_3675$class_306;
    //   152: invokevirtual method_1444 : ()I
    //   155: istore #17
    //   157: aload #16
    //   159: invokevirtual method_1429 : ()Lnet/minecraft/class_3675$class_306;
    //   162: invokevirtual method_1444 : ()I
    //   165: istore #18
    //   167: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   170: getfield field_1755 : Lnet/minecraft/class_437;
    //   173: iload #14
    //   175: ifne -> 1299
    //   178: ifnonnull -> 1285
    //   181: goto -> 188
    //   184: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   187: athrow
    //   188: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   191: invokevirtual method_1569 : ()Z
    //   194: iload #14
    //   196: ifne -> 1305
    //   199: goto -> 206
    //   202: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   205: athrow
    //   206: ifeq -> 1285
    //   209: goto -> 216
    //   212: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   215: athrow
    //   216: aload_0
    //   217: getfield W : Lwtf/opal/ke;
    //   220: invokevirtual z : ()Ljava/lang/Object;
    //   223: checkcast java/lang/Boolean
    //   226: invokevirtual booleanValue : ()Z
    //   229: ifeq -> 297
    //   232: goto -> 239
    //   235: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   238: athrow
    //   239: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   242: getfield field_1765 : Lnet/minecraft/class_239;
    //   245: iload #14
    //   247: ifne -> 280
    //   250: goto -> 257
    //   253: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   256: athrow
    //   257: ifnull -> 297
    //   260: goto -> 267
    //   263: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   266: athrow
    //   267: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   270: getfield field_1765 : Lnet/minecraft/class_239;
    //   273: goto -> 280
    //   276: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   279: athrow
    //   280: invokevirtual method_17783 : ()Lnet/minecraft/class_239$class_240;
    //   283: getstatic net/minecraft/class_239$class_240.field_1332 : Lnet/minecraft/class_239$class_240;
    //   286: if_acmpne -> 297
    //   289: iconst_1
    //   290: goto -> 298
    //   293: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   296: athrow
    //   297: iconst_0
    //   298: istore #19
    //   300: iload #19
    //   302: iload #14
    //   304: ifne -> 449
    //   307: ifeq -> 429
    //   310: goto -> 317
    //   313: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   316: athrow
    //   317: aload_0
    //   318: iload #14
    //   320: ifne -> 666
    //   323: goto -> 330
    //   326: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   329: athrow
    //   330: getfield s : Lwtf/opal/kt;
    //   333: invokevirtual z : ()Ljava/lang/Object;
    //   336: checkcast java/lang/Double
    //   339: invokevirtual doubleValue : ()D
    //   342: dconst_0
    //   343: dcmpl
    //   344: ifeq -> 633
    //   347: goto -> 354
    //   350: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   353: athrow
    //   354: aload_0
    //   355: iload #14
    //   357: ifne -> 666
    //   360: goto -> 367
    //   363: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   366: athrow
    //   367: getfield d : Lwtf/opal/kr;
    //   370: aload_0
    //   371: getfield s : Lwtf/opal/kt;
    //   374: invokevirtual z : ()Ljava/lang/Object;
    //   377: checkcast java/lang/Double
    //   380: invokevirtual longValue : ()J
    //   383: lload #6
    //   385: iconst_0
    //   386: iconst_3
    //   387: anewarray java/lang/Object
    //   390: dup_x1
    //   391: swap
    //   392: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   395: iconst_2
    //   396: swap
    //   397: aastore
    //   398: dup_x2
    //   399: dup_x2
    //   400: pop
    //   401: invokestatic valueOf : (J)Ljava/lang/Long;
    //   404: iconst_1
    //   405: swap
    //   406: aastore
    //   407: dup_x2
    //   408: dup_x2
    //   409: pop
    //   410: invokestatic valueOf : (J)Ljava/lang/Long;
    //   413: iconst_0
    //   414: swap
    //   415: aastore
    //   416: invokevirtual v : ([Ljava/lang/Object;)Z
    //   419: ifne -> 633
    //   422: goto -> 429
    //   425: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   428: athrow
    //   429: aload_0
    //   430: getfield T : Lwtf/opal/ke;
    //   433: invokevirtual z : ()Ljava/lang/Object;
    //   436: checkcast java/lang/Boolean
    //   439: invokevirtual booleanValue : ()Z
    //   442: goto -> 449
    //   445: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   448: athrow
    //   449: iload #14
    //   451: ifne -> 514
    //   454: ifeq -> 498
    //   457: goto -> 464
    //   460: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   463: athrow
    //   464: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   467: getfield field_1690 : Lnet/minecraft/class_315;
    //   470: getfield field_1832 : Lnet/minecraft/class_304;
    //   473: iload #14
    //   475: ifne -> 635
    //   478: goto -> 485
    //   481: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   484: athrow
    //   485: invokevirtual method_1434 : ()Z
    //   488: ifne -> 633
    //   491: goto -> 498
    //   494: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   497: athrow
    //   498: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   501: getfield field_1724 : Lnet/minecraft/class_746;
    //   504: invokevirtual method_6115 : ()Z
    //   507: goto -> 514
    //   510: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   513: athrow
    //   514: iload #14
    //   516: ifne -> 578
    //   519: ifeq -> 558
    //   522: goto -> 529
    //   525: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   528: athrow
    //   529: iconst_0
    //   530: anewarray java/lang/Object
    //   533: invokestatic g : ([Ljava/lang/Object;)Z
    //   536: iload #14
    //   538: ifne -> 578
    //   541: goto -> 548
    //   544: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   547: athrow
    //   548: ifeq -> 633
    //   551: goto -> 558
    //   554: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   557: athrow
    //   558: aload_0
    //   559: getfield L : Lwtf/opal/ke;
    //   562: invokevirtual z : ()Ljava/lang/Object;
    //   565: checkcast java/lang/Boolean
    //   568: invokevirtual booleanValue : ()Z
    //   571: goto -> 578
    //   574: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   577: athrow
    //   578: iload #14
    //   580: ifne -> 684
    //   583: ifeq -> 671
    //   586: goto -> 593
    //   589: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   592: athrow
    //   593: lload #10
    //   595: iconst_1
    //   596: anewarray java/lang/Object
    //   599: dup_x2
    //   600: dup_x2
    //   601: pop
    //   602: invokestatic valueOf : (J)Ljava/lang/Long;
    //   605: iconst_0
    //   606: swap
    //   607: aastore
    //   608: invokestatic J : ([Ljava/lang/Object;)Z
    //   611: iload #14
    //   613: ifne -> 684
    //   616: goto -> 623
    //   619: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   622: athrow
    //   623: ifne -> 671
    //   626: goto -> 633
    //   629: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   632: athrow
    //   633: aload #15
    //   635: lload #8
    //   637: iconst_0
    //   638: iconst_2
    //   639: anewarray java/lang/Object
    //   642: dup_x1
    //   643: swap
    //   644: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   647: iconst_1
    //   648: swap
    //   649: aastore
    //   650: dup_x2
    //   651: dup_x2
    //   652: pop
    //   653: invokestatic valueOf : (J)Ljava/lang/Long;
    //   656: iconst_0
    //   657: swap
    //   658: aastore
    //   659: invokestatic g : ([Ljava/lang/Object;)Z
    //   662: invokevirtual method_23481 : (Z)V
    //   665: aload_0
    //   666: iconst_0
    //   667: putfield l : Z
    //   670: return
    //   671: aload_0
    //   672: getfield W : Lwtf/opal/ke;
    //   675: invokevirtual z : ()Ljava/lang/Object;
    //   678: checkcast java/lang/Boolean
    //   681: invokevirtual booleanValue : ()Z
    //   684: iload #14
    //   686: ifne -> 768
    //   689: ifeq -> 741
    //   692: goto -> 699
    //   695: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   698: athrow
    //   699: iload #19
    //   701: iload #14
    //   703: ifne -> 768
    //   706: goto -> 713
    //   709: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   712: athrow
    //   713: ifne -> 741
    //   716: goto -> 723
    //   719: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   722: athrow
    //   723: aload_0
    //   724: getfield d : Lwtf/opal/kr;
    //   727: iconst_0
    //   728: anewarray java/lang/Object
    //   731: invokevirtual z : ([Ljava/lang/Object;)V
    //   734: goto -> 741
    //   737: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   740: athrow
    //   741: lload #8
    //   743: iconst_0
    //   744: iconst_2
    //   745: anewarray java/lang/Object
    //   748: dup_x1
    //   749: swap
    //   750: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   753: iconst_1
    //   754: swap
    //   755: aastore
    //   756: dup_x2
    //   757: dup_x2
    //   758: pop
    //   759: invokestatic valueOf : (J)Ljava/lang/Long;
    //   762: iconst_0
    //   763: swap
    //   764: aastore
    //   765: invokestatic g : ([Ljava/lang/Object;)Z
    //   768: iload #14
    //   770: ifne -> 808
    //   773: ifeq -> 1258
    //   776: goto -> 783
    //   779: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   782: athrow
    //   783: aload_0
    //   784: iload #14
    //   786: ifne -> 1235
    //   789: goto -> 796
    //   792: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   795: athrow
    //   796: getfield n : J
    //   799: lconst_0
    //   800: lcmp
    //   801: goto -> 808
    //   804: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   807: athrow
    //   808: ifle -> 1227
    //   811: aload_0
    //   812: iload #14
    //   814: ifne -> 1235
    //   817: goto -> 824
    //   820: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   823: athrow
    //   824: getfield A : J
    //   827: lconst_0
    //   828: lcmp
    //   829: ifle -> 1227
    //   832: goto -> 839
    //   835: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   838: athrow
    //   839: invokestatic currentTimeMillis : ()J
    //   842: aload_0
    //   843: getfield n : J
    //   846: lcmp
    //   847: iload #14
    //   849: ifne -> 1147
    //   852: goto -> 859
    //   855: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   858: athrow
    //   859: ifle -> 1132
    //   862: goto -> 869
    //   865: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   868: athrow
    //   869: aload_0
    //   870: getfield r : Lwtf/opal/ke;
    //   873: invokevirtual z : ()Ljava/lang/Object;
    //   876: checkcast java/lang/Boolean
    //   879: invokevirtual booleanValue : ()Z
    //   882: iload #14
    //   884: ifne -> 1021
    //   887: goto -> 894
    //   890: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   893: athrow
    //   894: ifeq -> 979
    //   897: goto -> 904
    //   900: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   903: athrow
    //   904: aload_0
    //   905: getfield k : Lwtf/opal/kt;
    //   908: invokevirtual z : ()Ljava/lang/Object;
    //   911: checkcast java/lang/Double
    //   914: invokevirtual floatValue : ()F
    //   917: fstore #20
    //   919: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   922: getfield field_1724 : Lnet/minecraft/class_746;
    //   925: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   928: getfield field_1724 : Lnet/minecraft/class_746;
    //   931: invokevirtual method_36454 : ()F
    //   934: invokestatic current : ()Ljava/util/concurrent/ThreadLocalRandom;
    //   937: fload #20
    //   939: fneg
    //   940: fload #20
    //   942: invokevirtual nextFloat : (FF)F
    //   945: fadd
    //   946: invokevirtual method_36456 : (F)V
    //   949: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   952: getfield field_1724 : Lnet/minecraft/class_746;
    //   955: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   958: getfield field_1724 : Lnet/minecraft/class_746;
    //   961: invokevirtual method_36455 : ()F
    //   964: invokestatic current : ()Ljava/util/concurrent/ThreadLocalRandom;
    //   967: fload #20
    //   969: fneg
    //   970: fload #20
    //   972: invokevirtual nextFloat : (FF)F
    //   975: fadd
    //   976: invokevirtual method_36457 : (F)V
    //   979: aload #15
    //   981: iconst_1
    //   982: invokevirtual method_23481 : (Z)V
    //   985: getstatic net/minecraft/class_3675$class_307.field_1672 : Lnet/minecraft/class_3675$class_307;
    //   988: aload #15
    //   990: invokevirtual method_1429 : ()Lnet/minecraft/class_3675$class_306;
    //   993: invokevirtual method_1444 : ()I
    //   996: invokevirtual method_1447 : (I)Lnet/minecraft/class_3675$class_306;
    //   999: invokestatic method_1420 : (Lnet/minecraft/class_3675$class_306;)V
    //   1002: aload_0
    //   1003: lload #12
    //   1005: iconst_1
    //   1006: anewarray java/lang/Object
    //   1009: dup_x2
    //   1010: dup_x2
    //   1011: pop
    //   1012: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1015: iconst_0
    //   1016: swap
    //   1017: aastore
    //   1018: invokevirtual b : ([Ljava/lang/Object;)Z
    //   1021: iload #14
    //   1023: ifne -> 1101
    //   1026: ifeq -> 1066
    //   1029: goto -> 1036
    //   1032: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1035: athrow
    //   1036: aload #16
    //   1038: iconst_1
    //   1039: invokevirtual method_23481 : (Z)V
    //   1042: getstatic net/minecraft/class_3675$class_307.field_1672 : Lnet/minecraft/class_3675$class_307;
    //   1045: aload #16
    //   1047: invokevirtual method_1429 : ()Lnet/minecraft/class_3675$class_306;
    //   1050: invokevirtual method_1444 : ()I
    //   1053: invokevirtual method_1447 : (I)Lnet/minecraft/class_3675$class_306;
    //   1056: invokestatic method_1420 : (Lnet/minecraft/class_3675$class_306;)V
    //   1059: goto -> 1066
    //   1062: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1065: athrow
    //   1066: aload_0
    //   1067: lload #4
    //   1069: iconst_1
    //   1070: anewarray java/lang/Object
    //   1073: dup_x2
    //   1074: dup_x2
    //   1075: pop
    //   1076: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1079: iconst_0
    //   1080: swap
    //   1081: aastore
    //   1082: invokevirtual V : ([Ljava/lang/Object;)V
    //   1085: aload_0
    //   1086: iload #14
    //   1088: ifne -> 1117
    //   1091: getfield l : Z
    //   1094: goto -> 1101
    //   1097: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1100: athrow
    //   1101: ifne -> 1280
    //   1104: aload_0
    //   1105: iconst_1
    //   1106: putfield l : Z
    //   1109: aload_0
    //   1110: goto -> 1117
    //   1113: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1116: athrow
    //   1117: getfield m : Lwtf/opal/kr;
    //   1120: iconst_0
    //   1121: anewarray java/lang/Object
    //   1124: invokevirtual z : ([Ljava/lang/Object;)V
    //   1127: iload #14
    //   1129: ifeq -> 1280
    //   1132: invokestatic currentTimeMillis : ()J
    //   1135: aload_0
    //   1136: getfield A : J
    //   1139: lcmp
    //   1140: goto -> 1147
    //   1143: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1146: athrow
    //   1147: iload #14
    //   1149: ifne -> 1206
    //   1152: ifle -> 1280
    //   1155: goto -> 1162
    //   1158: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1161: athrow
    //   1162: aload #15
    //   1164: iconst_0
    //   1165: invokevirtual method_23481 : (Z)V
    //   1168: iload #14
    //   1170: ifne -> 1222
    //   1173: goto -> 1180
    //   1176: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1179: athrow
    //   1180: aload_0
    //   1181: lload #12
    //   1183: iconst_1
    //   1184: anewarray java/lang/Object
    //   1187: dup_x2
    //   1188: dup_x2
    //   1189: pop
    //   1190: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1193: iconst_0
    //   1194: swap
    //   1195: aastore
    //   1196: invokevirtual b : ([Ljava/lang/Object;)Z
    //   1199: goto -> 1206
    //   1202: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1205: athrow
    //   1206: ifeq -> 1280
    //   1209: aload #16
    //   1211: iconst_0
    //   1212: invokevirtual method_23481 : (Z)V
    //   1215: goto -> 1222
    //   1218: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1221: athrow
    //   1222: iload #14
    //   1224: ifeq -> 1280
    //   1227: aload_0
    //   1228: goto -> 1235
    //   1231: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1234: athrow
    //   1235: lload #4
    //   1237: iconst_1
    //   1238: anewarray java/lang/Object
    //   1241: dup_x2
    //   1242: dup_x2
    //   1243: pop
    //   1244: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1247: iconst_0
    //   1248: swap
    //   1249: aastore
    //   1250: invokevirtual V : ([Ljava/lang/Object;)V
    //   1253: iload #14
    //   1255: ifeq -> 1280
    //   1258: aload_0
    //   1259: iconst_0
    //   1260: putfield l : Z
    //   1263: aload_0
    //   1264: aload_0
    //   1265: lconst_0
    //   1266: dup2_x1
    //   1267: putfield n : J
    //   1270: putfield A : J
    //   1273: goto -> 1280
    //   1276: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1279: athrow
    //   1280: iload #14
    //   1282: ifeq -> 1570
    //   1285: aload_0
    //   1286: getfield Z : Lwtf/opal/ke;
    //   1289: invokevirtual z : ()Ljava/lang/Object;
    //   1292: goto -> 1299
    //   1295: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1298: athrow
    //   1299: checkcast java/lang/Boolean
    //   1302: invokevirtual booleanValue : ()Z
    //   1305: iload #14
    //   1307: ifne -> 1336
    //   1310: ifeq -> 1570
    //   1313: goto -> 1320
    //   1316: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1319: athrow
    //   1320: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1323: getfield field_1755 : Lnet/minecraft/class_437;
    //   1326: instanceof net/minecraft/class_476
    //   1329: goto -> 1336
    //   1332: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1335: athrow
    //   1336: iload #14
    //   1338: ifne -> 1385
    //   1341: ifeq -> 1570
    //   1344: goto -> 1351
    //   1347: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1350: athrow
    //   1351: lload #8
    //   1353: iconst_0
    //   1354: iconst_2
    //   1355: anewarray java/lang/Object
    //   1358: dup_x1
    //   1359: swap
    //   1360: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1363: iconst_1
    //   1364: swap
    //   1365: aastore
    //   1366: dup_x2
    //   1367: dup_x2
    //   1368: pop
    //   1369: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1372: iconst_0
    //   1373: swap
    //   1374: aastore
    //   1375: invokestatic g : ([Ljava/lang/Object;)Z
    //   1378: goto -> 1385
    //   1381: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1384: athrow
    //   1385: iload #14
    //   1387: ifne -> 1474
    //   1390: ifne -> 1449
    //   1393: goto -> 1400
    //   1396: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1399: athrow
    //   1400: lload #8
    //   1402: iconst_1
    //   1403: iconst_2
    //   1404: anewarray java/lang/Object
    //   1407: dup_x1
    //   1408: swap
    //   1409: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1412: iconst_1
    //   1413: swap
    //   1414: aastore
    //   1415: dup_x2
    //   1416: dup_x2
    //   1417: pop
    //   1418: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1421: iconst_0
    //   1422: swap
    //   1423: aastore
    //   1424: invokestatic g : ([Ljava/lang/Object;)Z
    //   1427: iload #14
    //   1429: ifne -> 1474
    //   1432: goto -> 1439
    //   1435: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1438: athrow
    //   1439: ifeq -> 1570
    //   1442: goto -> 1449
    //   1445: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1448: athrow
    //   1449: aload_0
    //   1450: iload #14
    //   1452: ifne -> 1506
    //   1455: goto -> 1462
    //   1458: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1461: athrow
    //   1462: getfield A : J
    //   1465: lconst_0
    //   1466: lcmp
    //   1467: goto -> 1474
    //   1470: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1473: athrow
    //   1474: ifeq -> 1505
    //   1477: aload_0
    //   1478: getfield n : J
    //   1481: lconst_0
    //   1482: lcmp
    //   1483: iload #14
    //   1485: ifne -> 1533
    //   1488: goto -> 1495
    //   1491: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1494: athrow
    //   1495: ifne -> 1525
    //   1498: goto -> 1505
    //   1501: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1504: athrow
    //   1505: aload_0
    //   1506: lload #4
    //   1508: iconst_1
    //   1509: anewarray java/lang/Object
    //   1512: dup_x2
    //   1513: dup_x2
    //   1514: pop
    //   1515: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1518: iconst_0
    //   1519: swap
    //   1520: aastore
    //   1521: invokevirtual V : ([Ljava/lang/Object;)V
    //   1524: return
    //   1525: invokestatic currentTimeMillis : ()J
    //   1528: aload_0
    //   1529: getfield n : J
    //   1532: lcmp
    //   1533: ifle -> 1570
    //   1536: aload_0
    //   1537: lload #4
    //   1539: iconst_1
    //   1540: anewarray java/lang/Object
    //   1543: dup_x2
    //   1544: dup_x2
    //   1545: pop
    //   1546: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1549: iconst_0
    //   1550: swap
    //   1551: aastore
    //   1552: invokevirtual V : ([Ljava/lang/Object;)V
    //   1555: aload_0
    //   1556: iconst_0
    //   1557: anewarray java/lang/Object
    //   1560: invokevirtual s : ([Ljava/lang/Object;)V
    //   1563: goto -> 1570
    //   1566: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1569: athrow
    //   1570: return
    // Exception table:
    //   from	to	target	type
    //   50	85	88	wtf/opal/x5
    //   59	96	99	wtf/opal/x5
    //   167	181	184	wtf/opal/x5
    //   178	199	202	wtf/opal/x5
    //   188	209	212	wtf/opal/x5
    //   206	232	235	wtf/opal/x5
    //   216	250	253	wtf/opal/x5
    //   239	260	263	wtf/opal/x5
    //   257	273	276	wtf/opal/x5
    //   280	293	293	wtf/opal/x5
    //   300	310	313	wtf/opal/x5
    //   307	323	326	wtf/opal/x5
    //   317	347	350	wtf/opal/x5
    //   330	360	363	wtf/opal/x5
    //   354	422	425	wtf/opal/x5
    //   367	442	445	wtf/opal/x5
    //   449	457	460	wtf/opal/x5
    //   454	478	481	wtf/opal/x5
    //   464	491	494	wtf/opal/x5
    //   485	507	510	wtf/opal/x5
    //   514	522	525	wtf/opal/x5
    //   519	541	544	wtf/opal/x5
    //   529	551	554	wtf/opal/x5
    //   548	571	574	wtf/opal/x5
    //   578	586	589	wtf/opal/x5
    //   583	616	619	wtf/opal/x5
    //   593	626	629	wtf/opal/x5
    //   684	692	695	wtf/opal/x5
    //   689	706	709	wtf/opal/x5
    //   699	716	719	wtf/opal/x5
    //   713	734	737	wtf/opal/x5
    //   768	776	779	wtf/opal/x5
    //   773	789	792	wtf/opal/x5
    //   783	801	804	wtf/opal/x5
    //   808	817	820	wtf/opal/x5
    //   811	832	835	wtf/opal/x5
    //   824	852	855	wtf/opal/x5
    //   839	862	865	wtf/opal/x5
    //   859	887	890	wtf/opal/x5
    //   869	897	900	wtf/opal/x5
    //   1021	1029	1032	wtf/opal/x5
    //   1026	1059	1062	wtf/opal/x5
    //   1066	1094	1097	wtf/opal/x5
    //   1101	1110	1113	wtf/opal/x5
    //   1117	1140	1143	wtf/opal/x5
    //   1147	1155	1158	wtf/opal/x5
    //   1152	1173	1176	wtf/opal/x5
    //   1162	1199	1202	wtf/opal/x5
    //   1206	1215	1218	wtf/opal/x5
    //   1222	1228	1231	wtf/opal/x5
    //   1235	1273	1276	wtf/opal/x5
    //   1280	1292	1295	wtf/opal/x5
    //   1305	1313	1316	wtf/opal/x5
    //   1310	1329	1332	wtf/opal/x5
    //   1336	1344	1347	wtf/opal/x5
    //   1341	1378	1381	wtf/opal/x5
    //   1385	1393	1396	wtf/opal/x5
    //   1390	1432	1435	wtf/opal/x5
    //   1400	1442	1445	wtf/opal/x5
    //   1439	1455	1458	wtf/opal/x5
    //   1449	1467	1470	wtf/opal/x5
    //   1474	1488	1491	wtf/opal/x5
    //   1477	1498	1501	wtf/opal/x5
    //   1533	1563	1566	wtf/opal/x5
  }
  
  static {
    long l = a ^ 0x79411A28EF27L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[14];
    boolean bool = false;
    String str;
    int i = (str = "m*\"¸S\006ë\030¢2uÐ×\026)Í¬Ä!Ò~·õ¢÷ýÉ!k\\ÀöÌoV4õ®Z´»þ°;J(8\007×bÆ7\035øLQc¨ÝØ\027ç¡p±óo¢N\024\016° ATãNbXë\020$ù{áyí\016à(K¾c\n ÕICÔ®GäPY\021ºìXñß%)#u\000\013ÿÀ¢S¬«Þ\030\021×\013rDorw9?æåÑ7Qîw\b¡ª«Å C¢¯ñå% \002S1;RÝ\035ºí\016±\027§Ì>)¥´¯%½P\020à\tyóí+\0067×DÝ\027 o¢\0079~\005Þx¡§Ç4.ùù·ú±|\025B.Nä,E~4\b >Z«:\\.`:ÛÀ?Í¤\033êÆj¡÷x\025«-¸­²(V)\003\035\026?uñ¿jéç¼6\\zõ\032³\\\\[ @Øê\031=Î\032H]3\"ôÄj¶ \r)ÂT8Ä\020öÃÅ2\031¼äþ(G±¼Z'ÇCôþã\føÌé?(r`:I¾\0034CV\031'Ð9âóD4è0ÞY\016Ì\032[¤4~ûË@òô÷xo ").length();
    byte b2 = 56;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x8A5;
    if (g[i] == null) {
      Object[] arrayOfObject;
      try {
        Long long_ = Long.valueOf(Thread.currentThread().threadId());
        arrayOfObject = (Object[])p.get(long_);
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/PKCS5Padding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          p.put(long_, arrayOfObject);
        } 
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/n", exception);
      } 
      byte[] arrayOfByte1 = new byte[8];
      arrayOfByte1[0] = (byte)(int)(paramLong >>> 56L);
      for (byte b = 1; b < 8; b++)
        arrayOfByte1[b] = (byte)(int)(paramLong << b * 8 >>> 56L); 
      DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
      SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
      ((Cipher)arrayOfObject[0]).init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
      byte[] arrayOfByte2 = f[i].getBytes("ISO-8859-1");
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
    //   66: ldc_w 'wtf/opal/n'
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
  
  private static int b(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x767;
    if (t[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l = q[i];
      byte[] arrayOfByte2 = { (byte)(int)(l >>> 56L), (byte)(int)(l >>> 48L), (byte)(int)(l >>> 40L), (byte)(int)(l >>> 32L), (byte)(int)(l >>> 24L), (byte)(int)(l >>> 16L), (byte)(int)(l >>> 8L), (byte)(int)l };
      Long long_ = Long.valueOf(Thread.currentThread().threadId());
      Object[] arrayOfObject = (Object[])v.get(long_);
      try {
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/NoPadding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          v.put(long_, arrayOfObject);
        } 
        DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
        SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
        Cipher cipher = (Cipher)arrayOfObject[0];
        cipher.init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
        arrayOfByte3 = cipher.doFinal(arrayOfByte2);
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/n", exception);
      } 
      int j = (arrayOfByte3[4] & 0xFF) << 24 | (arrayOfByte3[5] & 0xFF) << 16 | (arrayOfByte3[6] & 0xFF) << 8 | arrayOfByte3[7] & 0xFF;
      t[i] = Integer.valueOf(j);
    } 
    return t[i].intValue();
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
    //   66: ldc_w 'wtf/opal/n'
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
  
  private static long c(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x1C9C;
    if (B[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l1 = w[i];
      byte[] arrayOfByte2 = { (byte)(int)(l1 >>> 56L), (byte)(int)(l1 >>> 48L), (byte)(int)(l1 >>> 40L), (byte)(int)(l1 >>> 32L), (byte)(int)(l1 >>> 24L), (byte)(int)(l1 >>> 16L), (byte)(int)(l1 >>> 8L), (byte)(int)l1 };
      Long long_ = Long.valueOf(Thread.currentThread().threadId());
      Object[] arrayOfObject = (Object[])G.get(long_);
      try {
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/NoPadding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          G.put(long_, arrayOfObject);
        } 
        DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
        SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
        Cipher cipher = (Cipher)arrayOfObject[0];
        cipher.init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
        arrayOfByte3 = cipher.doFinal(arrayOfByte2);
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/n", exception);
      } 
      long l2 = (arrayOfByte3[0] & 0xFFL) << 56L | (arrayOfByte3[1] & 0xFFL) << 48L | (arrayOfByte3[2] & 0xFFL) << 40L | (arrayOfByte3[3] & 0xFFL) << 32L | (arrayOfByte3[4] & 0xFFL) << 24L | (arrayOfByte3[5] & 0xFFL) << 16L | (arrayOfByte3[6] & 0xFFL) << 8L | arrayOfByte3[7] & 0xFFL;
      B[i] = Long.valueOf(l2);
    } 
    return B[i].longValue();
  }
  
  private static long c(MethodHandles.Lookup paramLookup, MutableCallSite paramMutableCallSite, String paramString, Object[] paramArrayOfObject) {
    int i = ((Integer)paramArrayOfObject[0]).intValue();
    long l1 = ((Long)paramArrayOfObject[1]).longValue();
    long l2 = c(i, l1);
    MethodHandle methodHandle = MethodHandles.constant(long.class, Long.valueOf(l2));
    paramMutableCallSite.setTarget(MethodHandles.dropArguments(methodHandle, 0, new Class[] { int.class, long.class }));
    return l2;
  }
  
  private static CallSite c(MethodHandles.Lookup paramLookup, String paramString, MethodType paramMethodType) {
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
    //   66: ldc_w 'wtf/opal/n'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\n.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */