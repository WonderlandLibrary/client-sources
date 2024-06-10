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

public final class jo extends d {
  private final ky<kw> f;
  
  private final pa x;
  
  private final bu w;
  
  private final lo a;
  
  private final gm<uj> T;
  
  private static final long b = on.a(-3580850090211627621L, -5698688833726024048L, MethodHandles.lookup().lookupClass()).a(204888920507294L);
  
  private static final String[] d;
  
  private static final String[] g;
  
  private static final Map k = new HashMap<>(13);
  
  private static final long[] l;
  
  private static final Integer[] m;
  
  private static final Map n;
  
  public jo(long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/jo.b : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: lload_1
    //   7: dup2
    //   8: ldc2_w 1336187909189
    //   11: lxor
    //   12: lstore_3
    //   13: dup2
    //   14: ldc2_w 132926288579115
    //   17: lxor
    //   18: lstore #5
    //   20: dup2
    //   21: ldc2_w 95226979554174
    //   24: lxor
    //   25: lstore #7
    //   27: pop2
    //   28: aload_0
    //   29: sipush #24839
    //   32: ldc2_w 8093941679130134423
    //   35: lload_1
    //   36: lxor
    //   37: <illegal opcode> z : (IJ)Ljava/lang/String;
    //   42: lload #7
    //   44: sipush #27412
    //   47: ldc2_w 9207223606716386706
    //   50: lload_1
    //   51: lxor
    //   52: <illegal opcode> z : (IJ)Ljava/lang/String;
    //   57: getstatic wtf/opal/kn.VISUAL : Lwtf/opal/kn;
    //   60: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   63: aload_0
    //   64: new wtf/opal/ky
    //   67: dup
    //   68: sipush #11164
    //   71: ldc2_w 4328274506189212937
    //   74: lload_1
    //   75: lxor
    //   76: <illegal opcode> z : (IJ)Ljava/lang/String;
    //   81: getstatic wtf/opal/kw.SMALL : Lwtf/opal/kw;
    //   84: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Enum;)V
    //   87: putfield f : Lwtf/opal/ky;
    //   90: aload_0
    //   91: iconst_0
    //   92: anewarray java/lang/Object
    //   95: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   98: iconst_0
    //   99: anewarray java/lang/Object
    //   102: invokevirtual z : ([Ljava/lang/Object;)Lwtf/opal/pa;
    //   105: putfield x : Lwtf/opal/pa;
    //   108: aload_0
    //   109: iconst_0
    //   110: anewarray java/lang/Object
    //   113: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   116: iconst_0
    //   117: anewarray java/lang/Object
    //   120: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/bu;
    //   123: putfield w : Lwtf/opal/bu;
    //   126: aload_0
    //   127: iconst_0
    //   128: anewarray java/lang/Object
    //   131: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   134: iconst_0
    //   135: anewarray java/lang/Object
    //   138: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/lo;
    //   141: putfield a : Lwtf/opal/lo;
    //   144: aload_0
    //   145: aload_0
    //   146: <illegal opcode> H : (Lwtf/opal/jo;)Lwtf/opal/gm;
    //   151: putfield T : Lwtf/opal/gm;
    //   154: aload_0
    //   155: iconst_1
    //   156: anewarray wtf/opal/k3
    //   159: dup
    //   160: iconst_0
    //   161: aload_0
    //   162: getfield f : Lwtf/opal/ky;
    //   165: aastore
    //   166: lload #5
    //   168: dup2_x1
    //   169: pop2
    //   170: iconst_2
    //   171: anewarray java/lang/Object
    //   174: dup_x1
    //   175: swap
    //   176: iconst_1
    //   177: swap
    //   178: aastore
    //   179: dup_x2
    //   180: dup_x2
    //   181: pop
    //   182: invokestatic valueOf : (J)Ljava/lang/Long;
    //   185: iconst_0
    //   186: swap
    //   187: aastore
    //   188: invokevirtual o : ([Ljava/lang/Object;)V
    //   191: aload_0
    //   192: iconst_1
    //   193: lload_3
    //   194: iconst_2
    //   195: anewarray java/lang/Object
    //   198: dup_x2
    //   199: dup_x2
    //   200: pop
    //   201: invokestatic valueOf : (J)Ljava/lang/Long;
    //   204: iconst_1
    //   205: swap
    //   206: aastore
    //   207: dup_x1
    //   208: swap
    //   209: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   212: iconst_0
    //   213: swap
    //   214: aastore
    //   215: invokevirtual Q : ([Ljava/lang/Object;)V
    //   218: return
  }
  
  private void lambda$new$1(uj paramuj) {
    // Byte code:
    //   0: getstatic wtf/opal/jo.b : J
    //   3: ldc2_w 135792211433826
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 16339567532991
    //   13: lxor
    //   14: lstore #4
    //   16: pop2
    //   17: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   20: invokevirtual method_53526 : ()Lnet/minecraft/class_340;
    //   23: invokevirtual method_53536 : ()Z
    //   26: ifeq -> 34
    //   29: return
    //   30: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   33: athrow
    //   34: iconst_0
    //   35: anewarray java/lang/Object
    //   38: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   41: iconst_0
    //   42: anewarray java/lang/Object
    //   45: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   48: ldc wtf/opal/jt
    //   50: iconst_1
    //   51: anewarray java/lang/Object
    //   54: dup_x1
    //   55: swap
    //   56: iconst_0
    //   57: swap
    //   58: aastore
    //   59: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   62: checkcast wtf/opal/jt
    //   65: astore #6
    //   67: aload #6
    //   69: iconst_0
    //   70: anewarray java/lang/Object
    //   73: invokevirtual L : ([Ljava/lang/Object;)Lwtf/opal/kg;
    //   76: invokevirtual z : ()Ljava/lang/Object;
    //   79: checkcast java/lang/Integer
    //   82: invokevirtual intValue : ()I
    //   85: istore #7
    //   87: aload #6
    //   89: iconst_0
    //   90: anewarray java/lang/Object
    //   93: invokevirtual W : ([Ljava/lang/Object;)Lwtf/opal/kg;
    //   96: invokevirtual z : ()Ljava/lang/Object;
    //   99: checkcast java/lang/Integer
    //   102: invokevirtual intValue : ()I
    //   105: istore #8
    //   107: aload_1
    //   108: iconst_0
    //   109: anewarray java/lang/Object
    //   112: invokevirtual B : ([Ljava/lang/Object;)Lnet/minecraft/class_332;
    //   115: astore #9
    //   117: aload_0
    //   118: getfield x : Lwtf/opal/pa;
    //   121: aload_0
    //   122: aload #9
    //   124: aload #6
    //   126: iload #7
    //   128: iload #8
    //   130: <illegal opcode> run : (Lwtf/opal/jo;Lnet/minecraft/class_332;Lwtf/opal/jt;II)Ljava/lang/Runnable;
    //   135: lload #4
    //   137: iconst_2
    //   138: anewarray java/lang/Object
    //   141: dup_x2
    //   142: dup_x2
    //   143: pop
    //   144: invokestatic valueOf : (J)Ljava/lang/Long;
    //   147: iconst_1
    //   148: swap
    //   149: aastore
    //   150: dup_x1
    //   151: swap
    //   152: iconst_0
    //   153: swap
    //   154: aastore
    //   155: invokevirtual F : ([Ljava/lang/Object;)V
    //   158: return
    // Exception table:
    //   from	to	target	type
    //   17	30	30	wtf/opal/x5
  }
  
  private void lambda$new$0(class_332 paramclass_332, jt paramjt, int paramInt1, int paramInt2) {
    // Byte code:
    //   0: getstatic wtf/opal/jo.b : J
    //   3: ldc2_w 137042105070156
    //   6: lxor
    //   7: lstore #5
    //   9: lload #5
    //   11: dup2
    //   12: ldc2_w 33802090999007
    //   15: lxor
    //   16: lstore #7
    //   18: dup2
    //   19: ldc2_w 87014191376614
    //   22: lxor
    //   23: lstore #9
    //   25: dup2
    //   26: ldc2_w 139767922039824
    //   29: lxor
    //   30: lstore #11
    //   32: dup2
    //   33: ldc2_w 98642749615002
    //   36: lxor
    //   37: lstore #13
    //   39: dup2
    //   40: ldc2_w 49233615315560
    //   43: lxor
    //   44: lstore #15
    //   46: dup2
    //   47: ldc2_w 98185513949336
    //   50: lxor
    //   51: lstore #17
    //   53: dup2
    //   54: ldc2_w 97563058712079
    //   57: lxor
    //   58: lstore #19
    //   60: dup2
    //   61: ldc2_w 128280859504021
    //   64: lxor
    //   65: lstore #21
    //   67: dup2
    //   68: ldc2_w 36942823708738
    //   71: lxor
    //   72: lstore #23
    //   74: dup2
    //   75: ldc2_w 119762938586906
    //   78: lxor
    //   79: lstore #25
    //   81: dup2
    //   82: ldc2_w 25168492621196
    //   85: lxor
    //   86: lstore #27
    //   88: dup2
    //   89: ldc2_w 66856076513952
    //   92: lxor
    //   93: lstore #29
    //   95: dup2
    //   96: ldc2_w 47639025049066
    //   99: lxor
    //   100: lstore #31
    //   102: pop2
    //   103: invokestatic S : ()Ljava/lang/String;
    //   106: aload_0
    //   107: getfield w : Lwtf/opal/bu;
    //   110: iconst_0
    //   111: putfield t : Z
    //   114: astore #33
    //   116: aload_0
    //   117: aload #33
    //   119: ifnonnull -> 3172
    //   122: getfield f : Lwtf/opal/ky;
    //   125: invokevirtual z : ()Ljava/lang/Object;
    //   128: checkcast wtf/opal/kw
    //   131: invokevirtual ordinal : ()I
    //   134: tableswitch default -> 3171, 0 -> 176, 1 -> 447, 2 -> 731, 3 -> 1015, 4 -> 1836, 5 -> 3061
    //   172: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   175: athrow
    //   176: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   179: invokevirtual method_47599 : ()I
    //   182: sipush #31004
    //   185: ldc2_w 9180167327886904365
    //   188: lload #5
    //   190: lxor
    //   191: <illegal opcode> z : (IJ)Ljava/lang/String;
    //   196: <illegal opcode> makeConcatWithConstants : (ILjava/lang/String;)Ljava/lang/String;
    //   201: astore #34
    //   203: aload_0
    //   204: getfield a : Lwtf/opal/lo;
    //   207: lload #27
    //   209: sipush #32508
    //   212: ldc2_w 4358565354023619547
    //   215: lload #5
    //   217: lxor
    //   218: <illegal opcode> z : (IJ)Ljava/lang/String;
    //   223: iconst_2
    //   224: anewarray java/lang/Object
    //   227: dup_x1
    //   228: swap
    //   229: iconst_1
    //   230: swap
    //   231: aastore
    //   232: dup_x2
    //   233: dup_x2
    //   234: pop
    //   235: invokestatic valueOf : (J)Ljava/lang/Long;
    //   238: iconst_0
    //   239: swap
    //   240: aastore
    //   241: invokevirtual l : ([Ljava/lang/Object;)Lwtf/opal/dq;
    //   244: ldc_w 5.0
    //   247: lload #23
    //   249: ldc_w 5.0
    //   252: ldc_w 10.0
    //   255: ldc_w 10.0
    //   258: iconst_5
    //   259: anewarray java/lang/Object
    //   262: dup_x1
    //   263: swap
    //   264: invokestatic valueOf : (F)Ljava/lang/Float;
    //   267: iconst_4
    //   268: swap
    //   269: aastore
    //   270: dup_x1
    //   271: swap
    //   272: invokestatic valueOf : (F)Ljava/lang/Float;
    //   275: iconst_3
    //   276: swap
    //   277: aastore
    //   278: dup_x1
    //   279: swap
    //   280: invokestatic valueOf : (F)Ljava/lang/Float;
    //   283: iconst_2
    //   284: swap
    //   285: aastore
    //   286: dup_x2
    //   287: dup_x2
    //   288: pop
    //   289: invokestatic valueOf : (J)Ljava/lang/Long;
    //   292: iconst_1
    //   293: swap
    //   294: aastore
    //   295: dup_x1
    //   296: swap
    //   297: invokestatic valueOf : (F)Ljava/lang/Float;
    //   300: iconst_0
    //   301: swap
    //   302: aastore
    //   303: invokevirtual q : ([Ljava/lang/Object;)V
    //   306: aload_0
    //   307: getfield w : Lwtf/opal/bu;
    //   310: getstatic wtf/opal/lx.SEMI_BOLD : Lwtf/opal/lx;
    //   313: aload_1
    //   314: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   317: invokevirtual method_47599 : ()I
    //   320: sipush #5754
    //   323: ldc2_w 6055603202111955796
    //   326: lload #5
    //   328: lxor
    //   329: <illegal opcode> z : (IJ)Ljava/lang/String;
    //   334: swap
    //   335: sipush #21758
    //   338: ldc2_w 5394933477399732690
    //   341: lload #5
    //   343: lxor
    //   344: <illegal opcode> z : (IJ)Ljava/lang/String;
    //   349: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;ILjava/lang/String;)Ljava/lang/String;
    //   354: ldc_w 18.0
    //   357: lload #21
    //   359: ldc_w 5.5
    //   362: ldc_w 10.0
    //   365: iconst_m1
    //   366: iconst_1
    //   367: bipush #9
    //   369: anewarray java/lang/Object
    //   372: dup_x1
    //   373: swap
    //   374: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   377: bipush #8
    //   379: swap
    //   380: aastore
    //   381: dup_x1
    //   382: swap
    //   383: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   386: bipush #7
    //   388: swap
    //   389: aastore
    //   390: dup_x1
    //   391: swap
    //   392: invokestatic valueOf : (F)Ljava/lang/Float;
    //   395: bipush #6
    //   397: swap
    //   398: aastore
    //   399: dup_x1
    //   400: swap
    //   401: invokestatic valueOf : (F)Ljava/lang/Float;
    //   404: iconst_5
    //   405: swap
    //   406: aastore
    //   407: dup_x2
    //   408: dup_x2
    //   409: pop
    //   410: invokestatic valueOf : (J)Ljava/lang/Long;
    //   413: iconst_4
    //   414: swap
    //   415: aastore
    //   416: dup_x1
    //   417: swap
    //   418: invokestatic valueOf : (F)Ljava/lang/Float;
    //   421: iconst_3
    //   422: swap
    //   423: aastore
    //   424: dup_x1
    //   425: swap
    //   426: iconst_2
    //   427: swap
    //   428: aastore
    //   429: dup_x1
    //   430: swap
    //   431: iconst_1
    //   432: swap
    //   433: aastore
    //   434: dup_x1
    //   435: swap
    //   436: iconst_0
    //   437: swap
    //   438: aastore
    //   439: invokevirtual H : ([Ljava/lang/Object;)V
    //   442: aload #33
    //   444: ifnull -> 3171
    //   447: aload_0
    //   448: getfield a : Lwtf/opal/lo;
    //   451: lload #27
    //   453: sipush #9965
    //   456: ldc2_w 5937470381117821897
    //   459: lload #5
    //   461: lxor
    //   462: <illegal opcode> z : (IJ)Ljava/lang/String;
    //   467: iconst_2
    //   468: anewarray java/lang/Object
    //   471: dup_x1
    //   472: swap
    //   473: iconst_1
    //   474: swap
    //   475: aastore
    //   476: dup_x2
    //   477: dup_x2
    //   478: pop
    //   479: invokestatic valueOf : (J)Ljava/lang/Long;
    //   482: iconst_0
    //   483: swap
    //   484: aastore
    //   485: invokevirtual l : ([Ljava/lang/Object;)Lwtf/opal/dq;
    //   488: ldc_w 10.0
    //   491: lload #23
    //   493: ldc_w 10.0
    //   496: ldc_w 16.0
    //   499: ldc_w 16.0
    //   502: iconst_5
    //   503: anewarray java/lang/Object
    //   506: dup_x1
    //   507: swap
    //   508: invokestatic valueOf : (F)Ljava/lang/Float;
    //   511: iconst_4
    //   512: swap
    //   513: aastore
    //   514: dup_x1
    //   515: swap
    //   516: invokestatic valueOf : (F)Ljava/lang/Float;
    //   519: iconst_3
    //   520: swap
    //   521: aastore
    //   522: dup_x1
    //   523: swap
    //   524: invokestatic valueOf : (F)Ljava/lang/Float;
    //   527: iconst_2
    //   528: swap
    //   529: aastore
    //   530: dup_x2
    //   531: dup_x2
    //   532: pop
    //   533: invokestatic valueOf : (J)Ljava/lang/Long;
    //   536: iconst_1
    //   537: swap
    //   538: aastore
    //   539: dup_x1
    //   540: swap
    //   541: invokestatic valueOf : (F)Ljava/lang/Float;
    //   544: iconst_0
    //   545: swap
    //   546: aastore
    //   547: invokevirtual q : ([Ljava/lang/Object;)V
    //   550: aload_0
    //   551: getfield w : Lwtf/opal/bu;
    //   554: getstatic wtf/opal/lx.SEMI_BOLD : Lwtf/opal/lx;
    //   557: aload_1
    //   558: sipush #10122
    //   561: ldc2_w 1135907405800264354
    //   564: lload #5
    //   566: lxor
    //   567: <illegal opcode> z : (IJ)Ljava/lang/String;
    //   572: ldc_w 32.0
    //   575: ldc_w 11.0
    //   578: ldc_w 16.0
    //   581: sipush #32639
    //   584: ldc2_w 4547883296715271485
    //   587: lload #5
    //   589: lxor
    //   590: <illegal opcode> b : (IJ)I
    //   595: iconst_0
    //   596: sipush #5546
    //   599: ldc2_w 2133174008879515630
    //   602: lload #5
    //   604: lxor
    //   605: <illegal opcode> b : (IJ)I
    //   610: lload #29
    //   612: sipush #19860
    //   615: ldc2_w 6242302243298762719
    //   618: lload #5
    //   620: lxor
    //   621: <illegal opcode> b : (IJ)I
    //   626: bipush #11
    //   628: anewarray java/lang/Object
    //   631: dup_x1
    //   632: swap
    //   633: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   636: bipush #10
    //   638: swap
    //   639: aastore
    //   640: dup_x2
    //   641: dup_x2
    //   642: pop
    //   643: invokestatic valueOf : (J)Ljava/lang/Long;
    //   646: bipush #9
    //   648: swap
    //   649: aastore
    //   650: dup_x1
    //   651: swap
    //   652: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   655: bipush #8
    //   657: swap
    //   658: aastore
    //   659: dup_x1
    //   660: swap
    //   661: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   664: bipush #7
    //   666: swap
    //   667: aastore
    //   668: dup_x1
    //   669: swap
    //   670: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   673: bipush #6
    //   675: swap
    //   676: aastore
    //   677: dup_x1
    //   678: swap
    //   679: invokestatic valueOf : (F)Ljava/lang/Float;
    //   682: iconst_5
    //   683: swap
    //   684: aastore
    //   685: dup_x1
    //   686: swap
    //   687: invokestatic valueOf : (F)Ljava/lang/Float;
    //   690: iconst_4
    //   691: swap
    //   692: aastore
    //   693: dup_x1
    //   694: swap
    //   695: invokestatic valueOf : (F)Ljava/lang/Float;
    //   698: iconst_3
    //   699: swap
    //   700: aastore
    //   701: dup_x1
    //   702: swap
    //   703: iconst_2
    //   704: swap
    //   705: aastore
    //   706: dup_x1
    //   707: swap
    //   708: iconst_1
    //   709: swap
    //   710: aastore
    //   711: dup_x1
    //   712: swap
    //   713: iconst_0
    //   714: swap
    //   715: aastore
    //   716: invokevirtual E : ([Ljava/lang/Object;)V
    //   719: aload #33
    //   721: ifnull -> 3171
    //   724: goto -> 731
    //   727: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   730: athrow
    //   731: aload_0
    //   732: getfield a : Lwtf/opal/lo;
    //   735: lload #27
    //   737: sipush #1164
    //   740: ldc2_w 6905656926559171006
    //   743: lload #5
    //   745: lxor
    //   746: <illegal opcode> z : (IJ)Ljava/lang/String;
    //   751: iconst_2
    //   752: anewarray java/lang/Object
    //   755: dup_x1
    //   756: swap
    //   757: iconst_1
    //   758: swap
    //   759: aastore
    //   760: dup_x2
    //   761: dup_x2
    //   762: pop
    //   763: invokestatic valueOf : (J)Ljava/lang/Long;
    //   766: iconst_0
    //   767: swap
    //   768: aastore
    //   769: invokevirtual l : ([Ljava/lang/Object;)Lwtf/opal/dq;
    //   772: ldc_w 10.0
    //   775: lload #23
    //   777: ldc_w 10.0
    //   780: ldc_w 32.0
    //   783: ldc_w 32.0
    //   786: iconst_5
    //   787: anewarray java/lang/Object
    //   790: dup_x1
    //   791: swap
    //   792: invokestatic valueOf : (F)Ljava/lang/Float;
    //   795: iconst_4
    //   796: swap
    //   797: aastore
    //   798: dup_x1
    //   799: swap
    //   800: invokestatic valueOf : (F)Ljava/lang/Float;
    //   803: iconst_3
    //   804: swap
    //   805: aastore
    //   806: dup_x1
    //   807: swap
    //   808: invokestatic valueOf : (F)Ljava/lang/Float;
    //   811: iconst_2
    //   812: swap
    //   813: aastore
    //   814: dup_x2
    //   815: dup_x2
    //   816: pop
    //   817: invokestatic valueOf : (J)Ljava/lang/Long;
    //   820: iconst_1
    //   821: swap
    //   822: aastore
    //   823: dup_x1
    //   824: swap
    //   825: invokestatic valueOf : (F)Ljava/lang/Float;
    //   828: iconst_0
    //   829: swap
    //   830: aastore
    //   831: invokevirtual q : ([Ljava/lang/Object;)V
    //   834: aload_0
    //   835: getfield w : Lwtf/opal/bu;
    //   838: getstatic wtf/opal/lx.SEMI_BOLD : Lwtf/opal/lx;
    //   841: aload_1
    //   842: sipush #31702
    //   845: ldc2_w 763245060294217467
    //   848: lload #5
    //   850: lxor
    //   851: <illegal opcode> z : (IJ)Ljava/lang/String;
    //   856: ldc_w 52.0
    //   859: ldc_w 17.5
    //   862: ldc_w 20.0
    //   865: sipush #8173
    //   868: ldc2_w 7814685549756550572
    //   871: lload #5
    //   873: lxor
    //   874: <illegal opcode> b : (IJ)I
    //   879: iconst_0
    //   880: sipush #27283
    //   883: ldc2_w 3996628879794862300
    //   886: lload #5
    //   888: lxor
    //   889: <illegal opcode> b : (IJ)I
    //   894: lload #29
    //   896: sipush #9822
    //   899: ldc2_w 354863082958009373
    //   902: lload #5
    //   904: lxor
    //   905: <illegal opcode> b : (IJ)I
    //   910: bipush #11
    //   912: anewarray java/lang/Object
    //   915: dup_x1
    //   916: swap
    //   917: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   920: bipush #10
    //   922: swap
    //   923: aastore
    //   924: dup_x2
    //   925: dup_x2
    //   926: pop
    //   927: invokestatic valueOf : (J)Ljava/lang/Long;
    //   930: bipush #9
    //   932: swap
    //   933: aastore
    //   934: dup_x1
    //   935: swap
    //   936: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   939: bipush #8
    //   941: swap
    //   942: aastore
    //   943: dup_x1
    //   944: swap
    //   945: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   948: bipush #7
    //   950: swap
    //   951: aastore
    //   952: dup_x1
    //   953: swap
    //   954: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   957: bipush #6
    //   959: swap
    //   960: aastore
    //   961: dup_x1
    //   962: swap
    //   963: invokestatic valueOf : (F)Ljava/lang/Float;
    //   966: iconst_5
    //   967: swap
    //   968: aastore
    //   969: dup_x1
    //   970: swap
    //   971: invokestatic valueOf : (F)Ljava/lang/Float;
    //   974: iconst_4
    //   975: swap
    //   976: aastore
    //   977: dup_x1
    //   978: swap
    //   979: invokestatic valueOf : (F)Ljava/lang/Float;
    //   982: iconst_3
    //   983: swap
    //   984: aastore
    //   985: dup_x1
    //   986: swap
    //   987: iconst_2
    //   988: swap
    //   989: aastore
    //   990: dup_x1
    //   991: swap
    //   992: iconst_1
    //   993: swap
    //   994: aastore
    //   995: dup_x1
    //   996: swap
    //   997: iconst_0
    //   998: swap
    //   999: aastore
    //   1000: invokevirtual E : ([Ljava/lang/Object;)V
    //   1003: aload #33
    //   1005: ifnull -> 3171
    //   1008: goto -> 1015
    //   1011: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1014: athrow
    //   1015: getstatic wtf/opal/p7.Y : Lwtf/opal/xf;
    //   1018: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   1021: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1024: invokevirtual method_1558 : ()Lnet/minecraft/class_642;
    //   1027: aload #33
    //   1029: ifnonnull -> 1076
    //   1032: goto -> 1039
    //   1035: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1038: athrow
    //   1039: ifnonnull -> 1070
    //   1042: goto -> 1049
    //   1045: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1048: athrow
    //   1049: sipush #32555
    //   1052: ldc2_w 785261668925639176
    //   1055: lload #5
    //   1057: lxor
    //   1058: <illegal opcode> z : (IJ)Ljava/lang/String;
    //   1063: goto -> 1082
    //   1066: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1069: athrow
    //   1070: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1073: invokevirtual method_1558 : ()Lnet/minecraft/class_642;
    //   1076: getfield field_3761 : Ljava/lang/String;
    //   1079: invokevirtual toLowerCase : ()Ljava/lang/String;
    //   1082: sipush #14251
    //   1085: ldc2_w 507947583600018052
    //   1088: lload #5
    //   1090: lxor
    //   1091: <illegal opcode> z : (IJ)Ljava/lang/String;
    //   1096: dup_x2
    //   1097: pop
    //   1098: sipush #15826
    //   1101: ldc2_w 7970061972765722875
    //   1104: lload #5
    //   1106: lxor
    //   1107: <illegal opcode> z : (IJ)Ljava/lang/String;
    //   1112: swap
    //   1113: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   1118: astore #34
    //   1120: aload_0
    //   1121: getfield w : Lwtf/opal/bu;
    //   1124: aload #34
    //   1126: ldc_w 8.0
    //   1129: lload #25
    //   1131: iconst_3
    //   1132: anewarray java/lang/Object
    //   1135: dup_x2
    //   1136: dup_x2
    //   1137: pop
    //   1138: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1141: iconst_2
    //   1142: swap
    //   1143: aastore
    //   1144: dup_x1
    //   1145: swap
    //   1146: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1149: iconst_1
    //   1150: swap
    //   1151: aastore
    //   1152: dup_x1
    //   1153: swap
    //   1154: iconst_0
    //   1155: swap
    //   1156: aastore
    //   1157: invokevirtual s : ([Ljava/lang/Object;)F
    //   1160: fstore #35
    //   1162: fload #35
    //   1164: ldc_w 18.0
    //   1167: fadd
    //   1168: fstore #36
    //   1170: aload_0
    //   1171: getfield x : Lwtf/opal/pa;
    //   1174: ldc_w 5.0
    //   1177: ldc_w 5.0
    //   1180: fload #36
    //   1182: ldc_w 15.0
    //   1185: sipush #4593
    //   1188: ldc2_w 3057544696402674620
    //   1191: lload #5
    //   1193: lxor
    //   1194: <illegal opcode> b : (IJ)I
    //   1199: lload #17
    //   1201: bipush #6
    //   1203: anewarray java/lang/Object
    //   1206: dup_x2
    //   1207: dup_x2
    //   1208: pop
    //   1209: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1212: iconst_5
    //   1213: swap
    //   1214: aastore
    //   1215: dup_x1
    //   1216: swap
    //   1217: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1220: iconst_4
    //   1221: swap
    //   1222: aastore
    //   1223: dup_x1
    //   1224: swap
    //   1225: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1228: iconst_3
    //   1229: swap
    //   1230: aastore
    //   1231: dup_x1
    //   1232: swap
    //   1233: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1236: iconst_2
    //   1237: swap
    //   1238: aastore
    //   1239: dup_x1
    //   1240: swap
    //   1241: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1244: iconst_1
    //   1245: swap
    //   1246: aastore
    //   1247: dup_x1
    //   1248: swap
    //   1249: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1252: iconst_0
    //   1253: swap
    //   1254: aastore
    //   1255: invokevirtual k : ([Ljava/lang/Object;)V
    //   1258: aload_0
    //   1259: getfield a : Lwtf/opal/lo;
    //   1262: lload #27
    //   1264: sipush #17427
    //   1267: ldc2_w 415795508728508722
    //   1270: lload #5
    //   1272: lxor
    //   1273: <illegal opcode> z : (IJ)Ljava/lang/String;
    //   1278: iconst_2
    //   1279: anewarray java/lang/Object
    //   1282: dup_x1
    //   1283: swap
    //   1284: iconst_1
    //   1285: swap
    //   1286: aastore
    //   1287: dup_x2
    //   1288: dup_x2
    //   1289: pop
    //   1290: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1293: iconst_0
    //   1294: swap
    //   1295: aastore
    //   1296: invokevirtual l : ([Ljava/lang/Object;)Lwtf/opal/dq;
    //   1299: ldc_w 8.0
    //   1302: lload #23
    //   1304: ldc_w 8.0
    //   1307: ldc_w 9.0
    //   1310: ldc_w 9.0
    //   1313: iconst_5
    //   1314: anewarray java/lang/Object
    //   1317: dup_x1
    //   1318: swap
    //   1319: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1322: iconst_4
    //   1323: swap
    //   1324: aastore
    //   1325: dup_x1
    //   1326: swap
    //   1327: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1330: iconst_3
    //   1331: swap
    //   1332: aastore
    //   1333: dup_x1
    //   1334: swap
    //   1335: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1338: iconst_2
    //   1339: swap
    //   1340: aastore
    //   1341: dup_x2
    //   1342: dup_x2
    //   1343: pop
    //   1344: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1347: iconst_1
    //   1348: swap
    //   1349: aastore
    //   1350: dup_x1
    //   1351: swap
    //   1352: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1355: iconst_0
    //   1356: swap
    //   1357: aastore
    //   1358: invokevirtual q : ([Ljava/lang/Object;)V
    //   1361: aload_0
    //   1362: getfield w : Lwtf/opal/bu;
    //   1365: aload_1
    //   1366: aload #34
    //   1368: ldc_w 20.0
    //   1371: ldc_w 9.0
    //   1374: aload_2
    //   1375: lload #7
    //   1377: iconst_1
    //   1378: anewarray java/lang/Object
    //   1381: dup_x2
    //   1382: dup_x2
    //   1383: pop
    //   1384: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1387: iconst_0
    //   1388: swap
    //   1389: aastore
    //   1390: invokevirtual S : ([Ljava/lang/Object;)Lwtf/opal/dv;
    //   1393: iconst_0
    //   1394: anewarray java/lang/Object
    //   1397: invokevirtual b : ([Ljava/lang/Object;)F
    //   1400: fconst_2
    //   1401: fdiv
    //   1402: fsub
    //   1403: ldc_w 8.0
    //   1406: iconst_m1
    //   1407: iconst_1
    //   1408: lload #31
    //   1410: bipush #8
    //   1412: anewarray java/lang/Object
    //   1415: dup_x2
    //   1416: dup_x2
    //   1417: pop
    //   1418: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1421: bipush #7
    //   1423: swap
    //   1424: aastore
    //   1425: dup_x1
    //   1426: swap
    //   1427: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   1430: bipush #6
    //   1432: swap
    //   1433: aastore
    //   1434: dup_x1
    //   1435: swap
    //   1436: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1439: iconst_5
    //   1440: swap
    //   1441: aastore
    //   1442: dup_x1
    //   1443: swap
    //   1444: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1447: iconst_4
    //   1448: swap
    //   1449: aastore
    //   1450: dup_x1
    //   1451: swap
    //   1452: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1455: iconst_3
    //   1456: swap
    //   1457: aastore
    //   1458: dup_x1
    //   1459: swap
    //   1460: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1463: iconst_2
    //   1464: swap
    //   1465: aastore
    //   1466: dup_x1
    //   1467: swap
    //   1468: iconst_1
    //   1469: swap
    //   1470: aastore
    //   1471: dup_x1
    //   1472: swap
    //   1473: iconst_0
    //   1474: swap
    //   1475: aastore
    //   1476: invokevirtual B : ([Ljava/lang/Object;)V
    //   1479: iconst_0
    //   1480: istore #37
    //   1482: iload #37
    //   1484: fload #36
    //   1486: f2i
    //   1487: if_icmpge -> 1831
    //   1490: sipush #19067
    //   1493: ldc2_w 5092657575262443569
    //   1496: lload #5
    //   1498: lxor
    //   1499: <illegal opcode> b : (IJ)I
    //   1504: iload #37
    //   1506: iconst_1
    //   1507: isub
    //   1508: iconst_3
    //   1509: imul
    //   1510: lload #9
    //   1512: iload_3
    //   1513: iload #4
    //   1515: iconst_5
    //   1516: anewarray java/lang/Object
    //   1519: dup_x1
    //   1520: swap
    //   1521: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1524: iconst_4
    //   1525: swap
    //   1526: aastore
    //   1527: dup_x1
    //   1528: swap
    //   1529: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1532: iconst_3
    //   1533: swap
    //   1534: aastore
    //   1535: dup_x2
    //   1536: dup_x2
    //   1537: pop
    //   1538: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1541: iconst_2
    //   1542: swap
    //   1543: aastore
    //   1544: dup_x1
    //   1545: swap
    //   1546: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1549: iconst_1
    //   1550: swap
    //   1551: aastore
    //   1552: dup_x1
    //   1553: swap
    //   1554: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1557: iconst_0
    //   1558: swap
    //   1559: aastore
    //   1560: invokestatic K : ([Ljava/lang/Object;)I
    //   1563: lload #19
    //   1565: iconst_2
    //   1566: anewarray java/lang/Object
    //   1569: dup_x2
    //   1570: dup_x2
    //   1571: pop
    //   1572: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1575: iconst_1
    //   1576: swap
    //   1577: aastore
    //   1578: dup_x1
    //   1579: swap
    //   1580: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1583: iconst_0
    //   1584: swap
    //   1585: aastore
    //   1586: invokestatic S : ([Ljava/lang/Object;)I
    //   1589: istore #38
    //   1591: sipush #12394
    //   1594: ldc2_w 6390194541499916844
    //   1597: lload #5
    //   1599: lxor
    //   1600: <illegal opcode> b : (IJ)I
    //   1605: iload #37
    //   1607: iconst_3
    //   1608: imul
    //   1609: lload #9
    //   1611: iload_3
    //   1612: iload #4
    //   1614: iconst_5
    //   1615: anewarray java/lang/Object
    //   1618: dup_x1
    //   1619: swap
    //   1620: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1623: iconst_4
    //   1624: swap
    //   1625: aastore
    //   1626: dup_x1
    //   1627: swap
    //   1628: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1631: iconst_3
    //   1632: swap
    //   1633: aastore
    //   1634: dup_x2
    //   1635: dup_x2
    //   1636: pop
    //   1637: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1640: iconst_2
    //   1641: swap
    //   1642: aastore
    //   1643: dup_x1
    //   1644: swap
    //   1645: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1648: iconst_1
    //   1649: swap
    //   1650: aastore
    //   1651: dup_x1
    //   1652: swap
    //   1653: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1656: iconst_0
    //   1657: swap
    //   1658: aastore
    //   1659: invokestatic K : ([Ljava/lang/Object;)I
    //   1662: lload #19
    //   1664: iconst_2
    //   1665: anewarray java/lang/Object
    //   1668: dup_x2
    //   1669: dup_x2
    //   1670: pop
    //   1671: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1674: iconst_1
    //   1675: swap
    //   1676: aastore
    //   1677: dup_x1
    //   1678: swap
    //   1679: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1682: iconst_0
    //   1683: swap
    //   1684: aastore
    //   1685: invokestatic S : ([Ljava/lang/Object;)I
    //   1688: istore #39
    //   1690: aload_0
    //   1691: aload #33
    //   1693: ifnonnull -> 3172
    //   1696: getfield x : Lwtf/opal/pa;
    //   1699: iconst_5
    //   1700: iload #37
    //   1702: iadd
    //   1703: i2f
    //   1704: ldc_w 5.0
    //   1707: iload #37
    //   1709: fload #36
    //   1711: f2i
    //   1712: iconst_1
    //   1713: isub
    //   1714: if_icmpne -> 1737
    //   1717: goto -> 1724
    //   1720: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1723: athrow
    //   1724: fload #36
    //   1726: iload #37
    //   1728: i2f
    //   1729: fsub
    //   1730: goto -> 1738
    //   1733: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1736: athrow
    //   1737: fconst_1
    //   1738: fconst_1
    //   1739: iload #38
    //   1741: lload #13
    //   1743: iload #39
    //   1745: ldc_w 180.0
    //   1748: bipush #8
    //   1750: anewarray java/lang/Object
    //   1753: dup_x1
    //   1754: swap
    //   1755: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1758: bipush #7
    //   1760: swap
    //   1761: aastore
    //   1762: dup_x1
    //   1763: swap
    //   1764: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1767: bipush #6
    //   1769: swap
    //   1770: aastore
    //   1771: dup_x2
    //   1772: dup_x2
    //   1773: pop
    //   1774: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1777: iconst_5
    //   1778: swap
    //   1779: aastore
    //   1780: dup_x1
    //   1781: swap
    //   1782: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1785: iconst_4
    //   1786: swap
    //   1787: aastore
    //   1788: dup_x1
    //   1789: swap
    //   1790: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1793: iconst_3
    //   1794: swap
    //   1795: aastore
    //   1796: dup_x1
    //   1797: swap
    //   1798: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1801: iconst_2
    //   1802: swap
    //   1803: aastore
    //   1804: dup_x1
    //   1805: swap
    //   1806: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1809: iconst_1
    //   1810: swap
    //   1811: aastore
    //   1812: dup_x1
    //   1813: swap
    //   1814: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1817: iconst_0
    //   1818: swap
    //   1819: aastore
    //   1820: invokevirtual Q : ([Ljava/lang/Object;)V
    //   1823: iinc #37, 1
    //   1826: aload #33
    //   1828: ifnull -> 1482
    //   1831: aload #33
    //   1833: ifnull -> 3171
    //   1836: getstatic wtf/opal/p7.Y : Lwtf/opal/xf;
    //   1839: invokevirtual toString : ()Ljava/lang/String;
    //   1842: invokevirtual toLowerCase : ()Ljava/lang/String;
    //   1845: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1848: invokevirtual method_1558 : ()Lnet/minecraft/class_642;
    //   1851: aload #33
    //   1853: ifnonnull -> 1900
    //   1856: goto -> 1863
    //   1859: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1862: athrow
    //   1863: ifnonnull -> 1894
    //   1866: goto -> 1873
    //   1869: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1872: athrow
    //   1873: sipush #20417
    //   1876: ldc2_w 203860842566531819
    //   1879: lload #5
    //   1881: lxor
    //   1882: <illegal opcode> z : (IJ)Ljava/lang/String;
    //   1887: goto -> 1906
    //   1890: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1893: athrow
    //   1894: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1897: invokevirtual method_1558 : ()Lnet/minecraft/class_642;
    //   1900: getfield field_3761 : Ljava/lang/String;
    //   1903: invokevirtual toLowerCase : ()Ljava/lang/String;
    //   1906: sipush #6615
    //   1909: ldc2_w 307126190486121724
    //   1912: lload #5
    //   1914: lxor
    //   1915: <illegal opcode> z : (IJ)Ljava/lang/String;
    //   1920: dup_x2
    //   1921: pop
    //   1922: sipush #32750
    //   1925: ldc2_w 8181387092052740808
    //   1928: lload #5
    //   1930: lxor
    //   1931: <illegal opcode> z : (IJ)Ljava/lang/String;
    //   1936: swap
    //   1937: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   1942: astore #34
    //   1944: aload_0
    //   1945: getfield w : Lwtf/opal/bu;
    //   1948: aload #34
    //   1950: ldc_w 8.0
    //   1953: lload #25
    //   1955: iconst_3
    //   1956: anewarray java/lang/Object
    //   1959: dup_x2
    //   1960: dup_x2
    //   1961: pop
    //   1962: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1965: iconst_2
    //   1966: swap
    //   1967: aastore
    //   1968: dup_x1
    //   1969: swap
    //   1970: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1973: iconst_1
    //   1974: swap
    //   1975: aastore
    //   1976: dup_x1
    //   1977: swap
    //   1978: iconst_0
    //   1979: swap
    //   1980: aastore
    //   1981: invokevirtual s : ([Ljava/lang/Object;)F
    //   1984: fstore #35
    //   1986: new java/awt/Color
    //   1989: dup
    //   1990: sipush #24087
    //   1993: ldc2_w 3674589281136397403
    //   1996: lload #5
    //   1998: lxor
    //   1999: <illegal opcode> b : (IJ)I
    //   2004: sipush #5399
    //   2007: ldc2_w 8092630063940536153
    //   2010: lload #5
    //   2012: lxor
    //   2013: <illegal opcode> b : (IJ)I
    //   2018: sipush #20490
    //   2021: ldc2_w 3407643745084457551
    //   2024: lload #5
    //   2026: lxor
    //   2027: <illegal opcode> b : (IJ)I
    //   2032: invokespecial <init> : (III)V
    //   2035: invokevirtual darker : ()Ljava/awt/Color;
    //   2038: invokevirtual getRGB : ()I
    //   2041: istore #36
    //   2043: ldc_w 5.0
    //   2046: fstore #37
    //   2048: ldc_w 5.0
    //   2051: fstore #38
    //   2053: aload_0
    //   2054: getfield x : Lwtf/opal/pa;
    //   2057: ldc_w 5.0
    //   2060: ldc_w 5.0
    //   2063: fload #35
    //   2065: ldc_w 8.5
    //   2068: fadd
    //   2069: ldc_w 18.5
    //   2072: new java/awt/Color
    //   2075: dup
    //   2076: sipush #18719
    //   2079: ldc2_w 6836251465698335575
    //   2082: lload #5
    //   2084: lxor
    //   2085: <illegal opcode> b : (IJ)I
    //   2090: sipush #20490
    //   2093: ldc2_w 3407643745084457551
    //   2096: lload #5
    //   2098: lxor
    //   2099: <illegal opcode> b : (IJ)I
    //   2104: sipush #20490
    //   2107: ldc2_w 3407643745084457551
    //   2110: lload #5
    //   2112: lxor
    //   2113: <illegal opcode> b : (IJ)I
    //   2118: invokespecial <init> : (III)V
    //   2121: invokevirtual getRGB : ()I
    //   2124: lload #17
    //   2126: bipush #6
    //   2128: anewarray java/lang/Object
    //   2131: dup_x2
    //   2132: dup_x2
    //   2133: pop
    //   2134: invokestatic valueOf : (J)Ljava/lang/Long;
    //   2137: iconst_5
    //   2138: swap
    //   2139: aastore
    //   2140: dup_x1
    //   2141: swap
    //   2142: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   2145: iconst_4
    //   2146: swap
    //   2147: aastore
    //   2148: dup_x1
    //   2149: swap
    //   2150: invokestatic valueOf : (F)Ljava/lang/Float;
    //   2153: iconst_3
    //   2154: swap
    //   2155: aastore
    //   2156: dup_x1
    //   2157: swap
    //   2158: invokestatic valueOf : (F)Ljava/lang/Float;
    //   2161: iconst_2
    //   2162: swap
    //   2163: aastore
    //   2164: dup_x1
    //   2165: swap
    //   2166: invokestatic valueOf : (F)Ljava/lang/Float;
    //   2169: iconst_1
    //   2170: swap
    //   2171: aastore
    //   2172: dup_x1
    //   2173: swap
    //   2174: invokestatic valueOf : (F)Ljava/lang/Float;
    //   2177: iconst_0
    //   2178: swap
    //   2179: aastore
    //   2180: invokevirtual k : ([Ljava/lang/Object;)V
    //   2183: aload_0
    //   2184: getfield x : Lwtf/opal/pa;
    //   2187: ldc_w 7.5
    //   2190: ldc_w 7.5
    //   2193: fload #35
    //   2195: ldc_w 3.5
    //   2198: fadd
    //   2199: ldc_w 13.0
    //   2202: new java/awt/Color
    //   2205: dup
    //   2206: sipush #16846
    //   2209: ldc2_w 7551183316776735625
    //   2212: lload #5
    //   2214: lxor
    //   2215: <illegal opcode> b : (IJ)I
    //   2220: sipush #31409
    //   2223: ldc2_w 7390157182706051320
    //   2226: lload #5
    //   2228: lxor
    //   2229: <illegal opcode> b : (IJ)I
    //   2234: sipush #31409
    //   2237: ldc2_w 7390157182706051320
    //   2240: lload #5
    //   2242: lxor
    //   2243: <illegal opcode> b : (IJ)I
    //   2248: invokespecial <init> : (III)V
    //   2251: invokevirtual getRGB : ()I
    //   2254: lload #17
    //   2256: bipush #6
    //   2258: anewarray java/lang/Object
    //   2261: dup_x2
    //   2262: dup_x2
    //   2263: pop
    //   2264: invokestatic valueOf : (J)Ljava/lang/Long;
    //   2267: iconst_5
    //   2268: swap
    //   2269: aastore
    //   2270: dup_x1
    //   2271: swap
    //   2272: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   2275: iconst_4
    //   2276: swap
    //   2277: aastore
    //   2278: dup_x1
    //   2279: swap
    //   2280: invokestatic valueOf : (F)Ljava/lang/Float;
    //   2283: iconst_3
    //   2284: swap
    //   2285: aastore
    //   2286: dup_x1
    //   2287: swap
    //   2288: invokestatic valueOf : (F)Ljava/lang/Float;
    //   2291: iconst_2
    //   2292: swap
    //   2293: aastore
    //   2294: dup_x1
    //   2295: swap
    //   2296: invokestatic valueOf : (F)Ljava/lang/Float;
    //   2299: iconst_1
    //   2300: swap
    //   2301: aastore
    //   2302: dup_x1
    //   2303: swap
    //   2304: invokestatic valueOf : (F)Ljava/lang/Float;
    //   2307: iconst_0
    //   2308: swap
    //   2309: aastore
    //   2310: invokevirtual k : ([Ljava/lang/Object;)V
    //   2313: aload_0
    //   2314: getfield x : Lwtf/opal/pa;
    //   2317: ldc_w 6.5
    //   2320: ldc_w 6.5
    //   2323: fload #35
    //   2325: ldc_w 6.0
    //   2328: fadd
    //   2329: lload #15
    //   2331: ldc_w 16.0
    //   2334: ldc_w 0.5
    //   2337: iload #36
    //   2339: bipush #7
    //   2341: anewarray java/lang/Object
    //   2344: dup_x1
    //   2345: swap
    //   2346: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   2349: bipush #6
    //   2351: swap
    //   2352: aastore
    //   2353: dup_x1
    //   2354: swap
    //   2355: invokestatic valueOf : (F)Ljava/lang/Float;
    //   2358: iconst_5
    //   2359: swap
    //   2360: aastore
    //   2361: dup_x1
    //   2362: swap
    //   2363: invokestatic valueOf : (F)Ljava/lang/Float;
    //   2366: iconst_4
    //   2367: swap
    //   2368: aastore
    //   2369: dup_x2
    //   2370: dup_x2
    //   2371: pop
    //   2372: invokestatic valueOf : (J)Ljava/lang/Long;
    //   2375: iconst_3
    //   2376: swap
    //   2377: aastore
    //   2378: dup_x1
    //   2379: swap
    //   2380: invokestatic valueOf : (F)Ljava/lang/Float;
    //   2383: iconst_2
    //   2384: swap
    //   2385: aastore
    //   2386: dup_x1
    //   2387: swap
    //   2388: invokestatic valueOf : (F)Ljava/lang/Float;
    //   2391: iconst_1
    //   2392: swap
    //   2393: aastore
    //   2394: dup_x1
    //   2395: swap
    //   2396: invokestatic valueOf : (F)Ljava/lang/Float;
    //   2399: iconst_0
    //   2400: swap
    //   2401: aastore
    //   2402: invokevirtual P : ([Ljava/lang/Object;)V
    //   2405: fload #35
    //   2407: ldc_w 3.5
    //   2410: fadd
    //   2411: fstore #39
    //   2413: iconst_0
    //   2414: istore #40
    //   2416: iload #40
    //   2418: fload #39
    //   2420: f2i
    //   2421: if_icmpge -> 2767
    //   2424: sipush #12394
    //   2427: ldc2_w 6390194541499916844
    //   2430: lload #5
    //   2432: lxor
    //   2433: <illegal opcode> b : (IJ)I
    //   2438: iload #40
    //   2440: iconst_1
    //   2441: isub
    //   2442: iconst_3
    //   2443: imul
    //   2444: lload #9
    //   2446: iload_3
    //   2447: iload #4
    //   2449: iconst_5
    //   2450: anewarray java/lang/Object
    //   2453: dup_x1
    //   2454: swap
    //   2455: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   2458: iconst_4
    //   2459: swap
    //   2460: aastore
    //   2461: dup_x1
    //   2462: swap
    //   2463: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   2466: iconst_3
    //   2467: swap
    //   2468: aastore
    //   2469: dup_x2
    //   2470: dup_x2
    //   2471: pop
    //   2472: invokestatic valueOf : (J)Ljava/lang/Long;
    //   2475: iconst_2
    //   2476: swap
    //   2477: aastore
    //   2478: dup_x1
    //   2479: swap
    //   2480: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   2483: iconst_1
    //   2484: swap
    //   2485: aastore
    //   2486: dup_x1
    //   2487: swap
    //   2488: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   2491: iconst_0
    //   2492: swap
    //   2493: aastore
    //   2494: invokestatic K : ([Ljava/lang/Object;)I
    //   2497: lload #19
    //   2499: iconst_2
    //   2500: anewarray java/lang/Object
    //   2503: dup_x2
    //   2504: dup_x2
    //   2505: pop
    //   2506: invokestatic valueOf : (J)Ljava/lang/Long;
    //   2509: iconst_1
    //   2510: swap
    //   2511: aastore
    //   2512: dup_x1
    //   2513: swap
    //   2514: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   2517: iconst_0
    //   2518: swap
    //   2519: aastore
    //   2520: invokestatic S : ([Ljava/lang/Object;)I
    //   2523: istore #41
    //   2525: sipush #12394
    //   2528: ldc2_w 6390194541499916844
    //   2531: lload #5
    //   2533: lxor
    //   2534: <illegal opcode> b : (IJ)I
    //   2539: iload #40
    //   2541: iconst_3
    //   2542: imul
    //   2543: lload #9
    //   2545: iload_3
    //   2546: iload #4
    //   2548: iconst_5
    //   2549: anewarray java/lang/Object
    //   2552: dup_x1
    //   2553: swap
    //   2554: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   2557: iconst_4
    //   2558: swap
    //   2559: aastore
    //   2560: dup_x1
    //   2561: swap
    //   2562: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   2565: iconst_3
    //   2566: swap
    //   2567: aastore
    //   2568: dup_x2
    //   2569: dup_x2
    //   2570: pop
    //   2571: invokestatic valueOf : (J)Ljava/lang/Long;
    //   2574: iconst_2
    //   2575: swap
    //   2576: aastore
    //   2577: dup_x1
    //   2578: swap
    //   2579: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   2582: iconst_1
    //   2583: swap
    //   2584: aastore
    //   2585: dup_x1
    //   2586: swap
    //   2587: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   2590: iconst_0
    //   2591: swap
    //   2592: aastore
    //   2593: invokestatic K : ([Ljava/lang/Object;)I
    //   2596: lload #19
    //   2598: iconst_2
    //   2599: anewarray java/lang/Object
    //   2602: dup_x2
    //   2603: dup_x2
    //   2604: pop
    //   2605: invokestatic valueOf : (J)Ljava/lang/Long;
    //   2608: iconst_1
    //   2609: swap
    //   2610: aastore
    //   2611: dup_x1
    //   2612: swap
    //   2613: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   2616: iconst_0
    //   2617: swap
    //   2618: aastore
    //   2619: invokestatic S : ([Ljava/lang/Object;)I
    //   2622: istore #42
    //   2624: aload_0
    //   2625: getfield x : Lwtf/opal/pa;
    //   2628: ldc_w 7.5
    //   2631: iload #40
    //   2633: i2f
    //   2634: fadd
    //   2635: ldc_w 19.5
    //   2638: aload #33
    //   2640: ifnonnull -> 2777
    //   2643: iload #40
    //   2645: fload #39
    //   2647: f2i
    //   2648: iconst_1
    //   2649: isub
    //   2650: if_icmpne -> 2673
    //   2653: goto -> 2660
    //   2656: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   2659: athrow
    //   2660: fload #39
    //   2662: iload #40
    //   2664: i2f
    //   2665: fsub
    //   2666: goto -> 2674
    //   2669: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   2672: athrow
    //   2673: fconst_1
    //   2674: fconst_1
    //   2675: iload #41
    //   2677: lload #13
    //   2679: iload #42
    //   2681: ldc_w 180.0
    //   2684: bipush #8
    //   2686: anewarray java/lang/Object
    //   2689: dup_x1
    //   2690: swap
    //   2691: invokestatic valueOf : (F)Ljava/lang/Float;
    //   2694: bipush #7
    //   2696: swap
    //   2697: aastore
    //   2698: dup_x1
    //   2699: swap
    //   2700: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   2703: bipush #6
    //   2705: swap
    //   2706: aastore
    //   2707: dup_x2
    //   2708: dup_x2
    //   2709: pop
    //   2710: invokestatic valueOf : (J)Ljava/lang/Long;
    //   2713: iconst_5
    //   2714: swap
    //   2715: aastore
    //   2716: dup_x1
    //   2717: swap
    //   2718: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   2721: iconst_4
    //   2722: swap
    //   2723: aastore
    //   2724: dup_x1
    //   2725: swap
    //   2726: invokestatic valueOf : (F)Ljava/lang/Float;
    //   2729: iconst_3
    //   2730: swap
    //   2731: aastore
    //   2732: dup_x1
    //   2733: swap
    //   2734: invokestatic valueOf : (F)Ljava/lang/Float;
    //   2737: iconst_2
    //   2738: swap
    //   2739: aastore
    //   2740: dup_x1
    //   2741: swap
    //   2742: invokestatic valueOf : (F)Ljava/lang/Float;
    //   2745: iconst_1
    //   2746: swap
    //   2747: aastore
    //   2748: dup_x1
    //   2749: swap
    //   2750: invokestatic valueOf : (F)Ljava/lang/Float;
    //   2753: iconst_0
    //   2754: swap
    //   2755: aastore
    //   2756: invokevirtual Q : ([Ljava/lang/Object;)V
    //   2759: iinc #40, 1
    //   2762: aload #33
    //   2764: ifnull -> 2416
    //   2767: aload_0
    //   2768: getfield x : Lwtf/opal/pa;
    //   2771: ldc_w 7.5
    //   2774: ldc_w 21.0
    //   2777: fload #35
    //   2779: ldc_w 3.5
    //   2782: fadd
    //   2783: ldc_w 0.5
    //   2786: iload #36
    //   2788: lload #17
    //   2790: bipush #6
    //   2792: anewarray java/lang/Object
    //   2795: dup_x2
    //   2796: dup_x2
    //   2797: pop
    //   2798: invokestatic valueOf : (J)Ljava/lang/Long;
    //   2801: iconst_5
    //   2802: swap
    //   2803: aastore
    //   2804: dup_x1
    //   2805: swap
    //   2806: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   2809: iconst_4
    //   2810: swap
    //   2811: aastore
    //   2812: dup_x1
    //   2813: swap
    //   2814: invokestatic valueOf : (F)Ljava/lang/Float;
    //   2817: iconst_3
    //   2818: swap
    //   2819: aastore
    //   2820: dup_x1
    //   2821: swap
    //   2822: invokestatic valueOf : (F)Ljava/lang/Float;
    //   2825: iconst_2
    //   2826: swap
    //   2827: aastore
    //   2828: dup_x1
    //   2829: swap
    //   2830: invokestatic valueOf : (F)Ljava/lang/Float;
    //   2833: iconst_1
    //   2834: swap
    //   2835: aastore
    //   2836: dup_x1
    //   2837: swap
    //   2838: invokestatic valueOf : (F)Ljava/lang/Float;
    //   2841: iconst_0
    //   2842: swap
    //   2843: aastore
    //   2844: invokevirtual k : ([Ljava/lang/Object;)V
    //   2847: sipush #27283
    //   2850: ldc2_w 3996628879794862300
    //   2853: lload #5
    //   2855: lxor
    //   2856: <illegal opcode> b : (IJ)I
    //   2861: iconst_0
    //   2862: lload #9
    //   2864: iload_3
    //   2865: iload #4
    //   2867: iconst_5
    //   2868: anewarray java/lang/Object
    //   2871: dup_x1
    //   2872: swap
    //   2873: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   2876: iconst_4
    //   2877: swap
    //   2878: aastore
    //   2879: dup_x1
    //   2880: swap
    //   2881: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   2884: iconst_3
    //   2885: swap
    //   2886: aastore
    //   2887: dup_x2
    //   2888: dup_x2
    //   2889: pop
    //   2890: invokestatic valueOf : (J)Ljava/lang/Long;
    //   2893: iconst_2
    //   2894: swap
    //   2895: aastore
    //   2896: dup_x1
    //   2897: swap
    //   2898: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   2901: iconst_1
    //   2902: swap
    //   2903: aastore
    //   2904: dup_x1
    //   2905: swap
    //   2906: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   2909: iconst_0
    //   2910: swap
    //   2911: aastore
    //   2912: invokestatic K : ([Ljava/lang/Object;)I
    //   2915: lload #19
    //   2917: iconst_2
    //   2918: anewarray java/lang/Object
    //   2921: dup_x2
    //   2922: dup_x2
    //   2923: pop
    //   2924: invokestatic valueOf : (J)Ljava/lang/Long;
    //   2927: iconst_1
    //   2928: swap
    //   2929: aastore
    //   2930: dup_x1
    //   2931: swap
    //   2932: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   2935: iconst_0
    //   2936: swap
    //   2937: aastore
    //   2938: invokestatic S : ([Ljava/lang/Object;)I
    //   2941: istore #40
    //   2943: aload_0
    //   2944: getfield w : Lwtf/opal/bu;
    //   2947: aload_1
    //   2948: aload #34
    //   2950: lload #11
    //   2952: ldc_w 9.5
    //   2955: ldc_w 5.0
    //   2958: aload_2
    //   2959: lload #7
    //   2961: iconst_1
    //   2962: anewarray java/lang/Object
    //   2965: dup_x2
    //   2966: dup_x2
    //   2967: pop
    //   2968: invokestatic valueOf : (J)Ljava/lang/Long;
    //   2971: iconst_0
    //   2972: swap
    //   2973: aastore
    //   2974: invokevirtual S : ([Ljava/lang/Object;)Lwtf/opal/dv;
    //   2977: iconst_0
    //   2978: anewarray java/lang/Object
    //   2981: invokevirtual b : ([Ljava/lang/Object;)F
    //   2984: fconst_2
    //   2985: fdiv
    //   2986: fsub
    //   2987: ldc_w 4.5
    //   2990: fadd
    //   2991: ldc_w 8.0
    //   2994: iload #40
    //   2996: bipush #7
    //   2998: anewarray java/lang/Object
    //   3001: dup_x1
    //   3002: swap
    //   3003: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   3006: bipush #6
    //   3008: swap
    //   3009: aastore
    //   3010: dup_x1
    //   3011: swap
    //   3012: invokestatic valueOf : (F)Ljava/lang/Float;
    //   3015: iconst_5
    //   3016: swap
    //   3017: aastore
    //   3018: dup_x1
    //   3019: swap
    //   3020: invokestatic valueOf : (F)Ljava/lang/Float;
    //   3023: iconst_4
    //   3024: swap
    //   3025: aastore
    //   3026: dup_x1
    //   3027: swap
    //   3028: invokestatic valueOf : (F)Ljava/lang/Float;
    //   3031: iconst_3
    //   3032: swap
    //   3033: aastore
    //   3034: dup_x2
    //   3035: dup_x2
    //   3036: pop
    //   3037: invokestatic valueOf : (J)Ljava/lang/Long;
    //   3040: iconst_2
    //   3041: swap
    //   3042: aastore
    //   3043: dup_x1
    //   3044: swap
    //   3045: iconst_1
    //   3046: swap
    //   3047: aastore
    //   3048: dup_x1
    //   3049: swap
    //   3050: iconst_0
    //   3051: swap
    //   3052: aastore
    //   3053: invokevirtual R : ([Ljava/lang/Object;)V
    //   3056: aload #33
    //   3058: ifnull -> 3171
    //   3061: aload_0
    //   3062: getfield a : Lwtf/opal/lo;
    //   3065: lload #27
    //   3067: sipush #623
    //   3070: ldc2_w 2213527247897753421
    //   3073: lload #5
    //   3075: lxor
    //   3076: <illegal opcode> z : (IJ)Ljava/lang/String;
    //   3081: iconst_2
    //   3082: anewarray java/lang/Object
    //   3085: dup_x1
    //   3086: swap
    //   3087: iconst_1
    //   3088: swap
    //   3089: aastore
    //   3090: dup_x2
    //   3091: dup_x2
    //   3092: pop
    //   3093: invokestatic valueOf : (J)Ljava/lang/Long;
    //   3096: iconst_0
    //   3097: swap
    //   3098: aastore
    //   3099: invokevirtual l : ([Ljava/lang/Object;)Lwtf/opal/dq;
    //   3102: ldc_w 10.0
    //   3105: lload #23
    //   3107: ldc_w 10.0
    //   3110: ldc_w 64.0
    //   3113: ldc_w 18.0
    //   3116: iconst_5
    //   3117: anewarray java/lang/Object
    //   3120: dup_x1
    //   3121: swap
    //   3122: invokestatic valueOf : (F)Ljava/lang/Float;
    //   3125: iconst_4
    //   3126: swap
    //   3127: aastore
    //   3128: dup_x1
    //   3129: swap
    //   3130: invokestatic valueOf : (F)Ljava/lang/Float;
    //   3133: iconst_3
    //   3134: swap
    //   3135: aastore
    //   3136: dup_x1
    //   3137: swap
    //   3138: invokestatic valueOf : (F)Ljava/lang/Float;
    //   3141: iconst_2
    //   3142: swap
    //   3143: aastore
    //   3144: dup_x2
    //   3145: dup_x2
    //   3146: pop
    //   3147: invokestatic valueOf : (J)Ljava/lang/Long;
    //   3150: iconst_1
    //   3151: swap
    //   3152: aastore
    //   3153: dup_x1
    //   3154: swap
    //   3155: invokestatic valueOf : (F)Ljava/lang/Float;
    //   3158: iconst_0
    //   3159: swap
    //   3160: aastore
    //   3161: invokevirtual q : ([Ljava/lang/Object;)V
    //   3164: goto -> 3171
    //   3167: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   3170: athrow
    //   3171: aload_0
    //   3172: getfield w : Lwtf/opal/bu;
    //   3175: iconst_1
    //   3176: putfield t : Z
    //   3179: return
    // Exception table:
    //   from	to	target	type
    //   116	172	172	wtf/opal/x5
    //   203	724	727	wtf/opal/x5
    //   447	1008	1011	wtf/opal/x5
    //   731	1032	1035	wtf/opal/x5
    //   1015	1042	1045	wtf/opal/x5
    //   1039	1066	1066	wtf/opal/x5
    //   1690	1717	1720	wtf/opal/x5
    //   1696	1733	1733	wtf/opal/x5
    //   1831	1856	1859	wtf/opal/x5
    //   1836	1866	1869	wtf/opal/x5
    //   1863	1890	1890	wtf/opal/x5
    //   2624	2653	2656	wtf/opal/x5
    //   2643	2669	2669	wtf/opal/x5
    //   2943	3164	3167	wtf/opal/x5
  }
  
  static {
    long l = b ^ 0x1A628A0C2E8AL;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[19];
    boolean bool = false;
    String str;
    int i = (str = "Ò\004äC\021\017B°õb\036joÂ\nÄa\030jôkvÏ7ÌET\032\001%^'\nBÿÌ7IªÉ8fx­9ØÈ\001\t¾Zì4Õ µQå\"\021BA@|ã9¹t°¬Ï±Häæ\003SÀX?æ);ÂrôI!XðÄB\0201rªcgÔf¡Ã­\001\033G8GUÜ»ý*3Â\020qv\016S\\Èõ¼¬Ü.Ã¡N¸\036\003´°\016I¦É7}(¼Â×\016öÂÅ<wôö¼\r\002³(\000ÈmÒhÙzÏb\021\006ÕÔh\032½Ø7\n\0055Ò\032RÊ¾ï¯Õ¸ÐRúÍ_Ë\030±%\005su;|'»³+ÉþÃæ­Ðâðc¥Ú8íÛ\006L¶ji°¡­p¾nC\006'k\033V\021h#ÞHÔö\027¹|/\024×à±(¿táa'ª½Â{A_Ú8Ä½ÕÒÿ\r{Ó\025\"\003Ô$\020\023\fÕ¤EæÛ Òÿ²÷dr¥\032 Î<ÖÈ:ê¡\017¢øýVPk\032¹G\030çciÝ%\027=<Ü\020©E£´\024\004qUþÏ(w4m\032ì:B¨Øl\007\017*¿Ý\020Âã.<-æv\007\013ØÃD÷û\037ª\035\020\023\031\026ÌPc0l©cN¢Ü^ ïÝN0úäq\017¼çFnìëª\002Îå[û\020¾\036\006¼\037\033n  }ÚEäy\f\004\nx3«A\001ÏáÁë¨øJ9xLM0çswíè\020óiÇÌ·OþÀÒ´\027õp,\030ðL#Ê÷\007\023C\001âÂÊ6e±üÆ»¿Ì(\034ûÃ\013¬]SW3ñSVgCÌÜx\027§ÊT\000j\nfáÆTr#ÏÝ").length();
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x6F81;
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
        throw new RuntimeException("wtf/opal/jo", exception);
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
    //   66: ldc_w 'wtf/opal/jo'
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x8ED;
    if (m[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l = l[i];
      byte[] arrayOfByte2 = { (byte)(int)(l >>> 56L), (byte)(int)(l >>> 48L), (byte)(int)(l >>> 40L), (byte)(int)(l >>> 32L), (byte)(int)(l >>> 24L), (byte)(int)(l >>> 16L), (byte)(int)(l >>> 8L), (byte)(int)l };
      Long long_ = Long.valueOf(Thread.currentThread().threadId());
      Object[] arrayOfObject = (Object[])n.get(long_);
      try {
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/NoPadding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          n.put(long_, arrayOfObject);
        } 
        DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
        SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
        Cipher cipher = (Cipher)arrayOfObject[0];
        cipher.init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
        arrayOfByte3 = cipher.doFinal(arrayOfByte2);
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/jo", exception);
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
    //   66: ldc_w 'wtf/opal/jo'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\jo.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */