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
import net.minecraft.class_342;
import net.minecraft.class_437;

public final class gs extends class_437 {
  private final class_342 B;
  
  private final class_342 Y;
  
  private static String d;
  
  private static final long a = on.a(8557417256671082198L, -2050609044213893421L, MethodHandles.lookup().lookupClass()).a(92397583425583L);
  
  private static final String[] b;
  
  private static final String[] c;
  
  private static final Map e = new HashMap<>(13);
  
  private static final long[] f;
  
  private static final Integer[] g;
  
  private static final Map h;
  
  public gs(long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/gs.a : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: invokestatic H : ()Ljava/lang/String;
    //   9: aload_0
    //   10: ldc ''
    //   12: invokestatic method_43470 : (Ljava/lang/String;)Lnet/minecraft/class_5250;
    //   15: invokespecial <init> : (Lnet/minecraft/class_2561;)V
    //   18: aload_0
    //   19: new net/minecraft/class_342
    //   22: dup
    //   23: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   26: getfield field_1772 : Lnet/minecraft/class_327;
    //   29: sipush #19108
    //   32: ldc2_w 3221386115201121963
    //   35: lload_1
    //   36: lxor
    //   37: <illegal opcode> u : (IJ)I
    //   42: sipush #7234
    //   45: ldc2_w 7871257456262965315
    //   48: lload_1
    //   49: lxor
    //   50: <illegal opcode> u : (IJ)I
    //   55: sipush #22646
    //   58: ldc2_w 5068105943183731835
    //   61: lload_1
    //   62: lxor
    //   63: <illegal opcode> u : (IJ)I
    //   68: sipush #31419
    //   71: ldc2_w 5121246642462297779
    //   74: lload_1
    //   75: lxor
    //   76: <illegal opcode> u : (IJ)I
    //   81: sipush #12675
    //   84: ldc2_w 7321243450382456122
    //   87: lload_1
    //   88: lxor
    //   89: <illegal opcode> n : (IJ)Ljava/lang/String;
    //   94: invokestatic method_43470 : (Ljava/lang/String;)Lnet/minecraft/class_5250;
    //   97: invokespecial <init> : (Lnet/minecraft/class_327;IIIILnet/minecraft/class_2561;)V
    //   100: putfield B : Lnet/minecraft/class_342;
    //   103: astore_3
    //   104: aload_0
    //   105: new net/minecraft/class_342
    //   108: dup
    //   109: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   112: getfield field_1772 : Lnet/minecraft/class_327;
    //   115: sipush #7234
    //   118: ldc2_w 7871257456262965315
    //   121: lload_1
    //   122: lxor
    //   123: <illegal opcode> u : (IJ)I
    //   128: sipush #15329
    //   131: ldc2_w 1944841000372424674
    //   134: lload_1
    //   135: lxor
    //   136: <illegal opcode> u : (IJ)I
    //   141: sipush #21895
    //   144: ldc2_w 4701562594681135497
    //   147: lload_1
    //   148: lxor
    //   149: <illegal opcode> u : (IJ)I
    //   154: sipush #21120
    //   157: ldc2_w 8887664533240620677
    //   160: lload_1
    //   161: lxor
    //   162: <illegal opcode> u : (IJ)I
    //   167: sipush #24261
    //   170: ldc2_w 4687185004581223037
    //   173: lload_1
    //   174: lxor
    //   175: <illegal opcode> n : (IJ)Ljava/lang/String;
    //   180: invokestatic method_43470 : (Ljava/lang/String;)Lnet/minecraft/class_5250;
    //   183: invokespecial <init> : (Lnet/minecraft/class_327;IIIILnet/minecraft/class_2561;)V
    //   186: putfield Y : Lnet/minecraft/class_342;
    //   189: aload_0
    //   190: getfield B : Lnet/minecraft/class_342;
    //   193: iconst_1
    //   194: invokevirtual method_25365 : (Z)V
    //   197: invokestatic D : ()[Lwtf/opal/d;
    //   200: ifnull -> 215
    //   203: ldc 'jEUUqb'
    //   205: invokestatic y : (Ljava/lang/String;)V
    //   208: goto -> 215
    //   211: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   214: athrow
    //   215: return
    // Exception table:
    //   from	to	target	type
    //   104	208	211	wtf/opal/x5
  }
  
  public void method_25394(class_332 paramclass_332, int paramInt1, int paramInt2, float paramFloat) {
    // Byte code:
    //   0: getstatic wtf/opal/gs.a : J
    //   3: ldc2_w 80258543175181
    //   6: lxor
    //   7: lstore #5
    //   9: aload_0
    //   10: aload_1
    //   11: iload_2
    //   12: iload_3
    //   13: fload #4
    //   15: invokevirtual method_25420 : (Lnet/minecraft/class_332;IIF)V
    //   18: sipush #21120
    //   21: ldc2_w 8887667767746928864
    //   24: lload #5
    //   26: lxor
    //   27: <illegal opcode> u : (IJ)I
    //   32: istore #8
    //   34: sipush #21895
    //   37: ldc2_w 4701559232130983916
    //   40: lload #5
    //   42: lxor
    //   43: <illegal opcode> u : (IJ)I
    //   48: istore #9
    //   50: sipush #7234
    //   53: ldc2_w 7871262459236544038
    //   56: lload #5
    //   58: lxor
    //   59: <illegal opcode> u : (IJ)I
    //   64: istore #10
    //   66: aload_1
    //   67: invokevirtual method_51421 : ()I
    //   70: iload #9
    //   72: isub
    //   73: i2f
    //   74: fconst_2
    //   75: fdiv
    //   76: f2i
    //   77: istore #11
    //   79: aload_1
    //   80: invokevirtual method_51443 : ()I
    //   83: i2f
    //   84: fconst_2
    //   85: fdiv
    //   86: iload #8
    //   88: iload #10
    //   90: iadd
    //   91: i2f
    //   92: fsub
    //   93: f2i
    //   94: istore #12
    //   96: iload #12
    //   98: iload #8
    //   100: iadd
    //   101: iload #10
    //   103: iadd
    //   104: istore #13
    //   106: aload_0
    //   107: getfield B : Lnet/minecraft/class_342;
    //   110: iload #11
    //   112: invokevirtual method_46421 : (I)V
    //   115: invokestatic H : ()Ljava/lang/String;
    //   118: aload_0
    //   119: getfield B : Lnet/minecraft/class_342;
    //   122: iload #12
    //   124: invokevirtual method_46419 : (I)V
    //   127: aload_0
    //   128: getfield Y : Lnet/minecraft/class_342;
    //   131: iload #11
    //   133: invokevirtual method_46421 : (I)V
    //   136: aload_0
    //   137: getfield Y : Lnet/minecraft/class_342;
    //   140: iload #13
    //   142: invokevirtual method_46419 : (I)V
    //   145: astore #7
    //   147: aload_0
    //   148: getfield B : Lnet/minecraft/class_342;
    //   151: aload_1
    //   152: iload_2
    //   153: iload_3
    //   154: fload #4
    //   156: invokevirtual method_25394 : (Lnet/minecraft/class_332;IIF)V
    //   159: aload_0
    //   160: getfield Y : Lnet/minecraft/class_342;
    //   163: aload_1
    //   164: iload_2
    //   165: iload_3
    //   166: fload #4
    //   168: invokevirtual method_25394 : (Lnet/minecraft/class_332;IIF)V
    //   171: aload_1
    //   172: invokevirtual method_51421 : ()I
    //   175: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   178: getfield field_1772 : Lnet/minecraft/class_327;
    //   181: sipush #5422
    //   184: ldc2_w 988140150209460212
    //   187: lload #5
    //   189: lxor
    //   190: <illegal opcode> n : (IJ)Ljava/lang/String;
    //   195: invokevirtual method_1727 : (Ljava/lang/String;)I
    //   198: isub
    //   199: i2f
    //   200: fconst_2
    //   201: fdiv
    //   202: f2i
    //   203: istore #14
    //   205: aload_1
    //   206: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   209: getfield field_1772 : Lnet/minecraft/class_327;
    //   212: sipush #27600
    //   215: ldc2_w 770612721099884811
    //   218: lload #5
    //   220: lxor
    //   221: <illegal opcode> n : (IJ)Ljava/lang/String;
    //   226: iload #14
    //   228: iload #12
    //   230: iload #10
    //   232: sipush #25361
    //   235: ldc2_w 4561743400382108022
    //   238: lload #5
    //   240: lxor
    //   241: <illegal opcode> u : (IJ)I
    //   246: iadd
    //   247: isub
    //   248: sipush #12803
    //   251: ldc2_w 7336415290603782255
    //   254: lload #5
    //   256: lxor
    //   257: <illegal opcode> u : (IJ)I
    //   262: iconst_1
    //   263: invokevirtual method_51433 : (Lnet/minecraft/class_327;Ljava/lang/String;IIIZ)I
    //   266: pop
    //   267: aload_1
    //   268: invokevirtual method_51421 : ()I
    //   271: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   274: getfield field_1772 : Lnet/minecraft/class_327;
    //   277: sipush #23396
    //   280: ldc2_w 2657054016652540349
    //   283: lload #5
    //   285: lxor
    //   286: <illegal opcode> n : (IJ)Ljava/lang/String;
    //   291: invokevirtual method_1727 : (Ljava/lang/String;)I
    //   294: isub
    //   295: i2f
    //   296: fconst_2
    //   297: fdiv
    //   298: f2i
    //   299: istore #15
    //   301: aload_1
    //   302: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   305: getfield field_1772 : Lnet/minecraft/class_327;
    //   308: sipush #11383
    //   311: ldc2_w 8127830735759380136
    //   314: lload #5
    //   316: lxor
    //   317: <illegal opcode> n : (IJ)Ljava/lang/String;
    //   322: iload #15
    //   324: iload #12
    //   326: iload #10
    //   328: iconst_3
    //   329: iadd
    //   330: isub
    //   331: iconst_m1
    //   332: iconst_1
    //   333: invokevirtual method_51433 : (Lnet/minecraft/class_327;Ljava/lang/String;IIIZ)I
    //   336: pop
    //   337: aload_1
    //   338: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   341: getfield field_1772 : Lnet/minecraft/class_327;
    //   344: sipush #21303
    //   347: ldc2_w 1446983588117770735
    //   350: lload #5
    //   352: lxor
    //   353: <illegal opcode> n : (IJ)Ljava/lang/String;
    //   358: iload #11
    //   360: iconst_4
    //   361: iadd
    //   362: iload #12
    //   364: iconst_4
    //   365: iadd
    //   366: sipush #23984
    //   369: ldc2_w 8757096503673335774
    //   372: lload #5
    //   374: lxor
    //   375: <illegal opcode> u : (IJ)I
    //   380: iconst_1
    //   381: invokevirtual method_51433 : (Lnet/minecraft/class_327;Ljava/lang/String;IIIZ)I
    //   384: pop
    //   385: aload_1
    //   386: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   389: getfield field_1772 : Lnet/minecraft/class_327;
    //   392: sipush #13892
    //   395: ldc2_w 9039871832299508890
    //   398: lload #5
    //   400: lxor
    //   401: <illegal opcode> n : (IJ)Ljava/lang/String;
    //   406: iload #11
    //   408: iconst_4
    //   409: iadd
    //   410: iload #13
    //   412: iconst_4
    //   413: iadd
    //   414: sipush #23127
    //   417: ldc2_w 6625007382360762418
    //   420: lload #5
    //   422: lxor
    //   423: <illegal opcode> u : (IJ)I
    //   428: iconst_1
    //   429: invokevirtual method_51433 : (Lnet/minecraft/class_327;Ljava/lang/String;IIIZ)I
    //   432: pop
    //   433: iconst_0
    //   434: anewarray java/lang/Object
    //   437: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   440: iconst_0
    //   441: anewarray java/lang/Object
    //   444: invokevirtual C : ([Ljava/lang/Object;)Lwtf/opal/lf;
    //   447: iconst_0
    //   448: anewarray java/lang/Object
    //   451: invokevirtual c : ([Ljava/lang/Object;)Lwtf/opal/po;
    //   454: ifnull -> 477
    //   457: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   460: new net/minecraft/class_442
    //   463: dup
    //   464: invokespecial <init> : ()V
    //   467: invokevirtual method_1507 : (Lnet/minecraft/class_437;)V
    //   470: goto -> 477
    //   473: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   476: athrow
    //   477: aload #7
    //   479: ifnonnull -> 496
    //   482: iconst_1
    //   483: anewarray wtf/opal/d
    //   486: invokestatic p : ([Lwtf/opal/d;)V
    //   489: goto -> 496
    //   492: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   495: athrow
    //   496: return
    // Exception table:
    //   from	to	target	type
    //   301	470	473	wtf/opal/x5
    //   477	489	492	wtf/opal/x5
  }
  
  public boolean method_25404(int paramInt1, int paramInt2, int paramInt3) {
    // Byte code:
    //   0: getstatic wtf/opal/gs.a : J
    //   3: ldc2_w 138826861101646
    //   6: lxor
    //   7: lstore #4
    //   9: lload #4
    //   11: dup2
    //   12: ldc2_w 127776134242351
    //   15: lxor
    //   16: lstore #6
    //   18: pop2
    //   19: invokestatic H : ()Ljava/lang/String;
    //   22: aload_0
    //   23: getfield B : Lnet/minecraft/class_342;
    //   26: iload_1
    //   27: iload_2
    //   28: iload_3
    //   29: invokevirtual method_25404 : (III)Z
    //   32: pop
    //   33: aload_0
    //   34: getfield Y : Lnet/minecraft/class_342;
    //   37: iload_1
    //   38: iload_2
    //   39: iload_3
    //   40: invokevirtual method_25404 : (III)Z
    //   43: pop
    //   44: astore #8
    //   46: iload_1
    //   47: sipush #2579
    //   50: ldc2_w 2669286604985891903
    //   53: lload #4
    //   55: lxor
    //   56: <illegal opcode> u : (IJ)I
    //   61: aload #8
    //   63: ifnull -> 180
    //   66: if_icmpne -> 146
    //   69: goto -> 76
    //   72: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   75: athrow
    //   76: iconst_0
    //   77: anewarray java/lang/Object
    //   80: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   83: iconst_0
    //   84: anewarray java/lang/Object
    //   87: invokevirtual C : ([Ljava/lang/Object;)Lwtf/opal/lf;
    //   90: aload_0
    //   91: getfield B : Lnet/minecraft/class_342;
    //   94: invokevirtual method_1882 : ()Ljava/lang/String;
    //   97: lload #6
    //   99: dup2_x1
    //   100: pop2
    //   101: aload_0
    //   102: getfield Y : Lnet/minecraft/class_342;
    //   105: invokevirtual method_1882 : ()Ljava/lang/String;
    //   108: iconst_3
    //   109: anewarray java/lang/Object
    //   112: dup_x1
    //   113: swap
    //   114: iconst_2
    //   115: swap
    //   116: aastore
    //   117: dup_x1
    //   118: swap
    //   119: iconst_1
    //   120: swap
    //   121: aastore
    //   122: dup_x2
    //   123: dup_x2
    //   124: pop
    //   125: invokestatic valueOf : (J)Ljava/lang/Long;
    //   128: iconst_0
    //   129: swap
    //   130: aastore
    //   131: invokevirtual x : ([Ljava/lang/Object;)V
    //   134: aload #8
    //   136: ifnonnull -> 292
    //   139: goto -> 146
    //   142: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   145: athrow
    //   146: iload_1
    //   147: aload #8
    //   149: ifnull -> 293
    //   152: goto -> 159
    //   155: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   158: athrow
    //   159: sipush #32302
    //   162: ldc2_w 6384867385213356036
    //   165: lload #4
    //   167: lxor
    //   168: <illegal opcode> u : (IJ)I
    //   173: goto -> 180
    //   176: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   179: athrow
    //   180: if_icmpne -> 292
    //   183: aload_0
    //   184: getfield B : Lnet/minecraft/class_342;
    //   187: invokevirtual method_25370 : ()Z
    //   190: aload #8
    //   192: ifnull -> 254
    //   195: goto -> 202
    //   198: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   201: athrow
    //   202: ifeq -> 240
    //   205: goto -> 212
    //   208: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   211: athrow
    //   212: aload_0
    //   213: getfield B : Lnet/minecraft/class_342;
    //   216: iconst_0
    //   217: invokevirtual method_25365 : (Z)V
    //   220: aload_0
    //   221: getfield Y : Lnet/minecraft/class_342;
    //   224: iconst_1
    //   225: invokevirtual method_25365 : (Z)V
    //   228: aload #8
    //   230: ifnonnull -> 292
    //   233: goto -> 240
    //   236: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   239: athrow
    //   240: aload_0
    //   241: getfield Y : Lnet/minecraft/class_342;
    //   244: invokevirtual method_25370 : ()Z
    //   247: goto -> 254
    //   250: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   253: athrow
    //   254: aload #8
    //   256: ifnull -> 293
    //   259: ifeq -> 292
    //   262: goto -> 269
    //   265: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   268: athrow
    //   269: aload_0
    //   270: getfield Y : Lnet/minecraft/class_342;
    //   273: iconst_0
    //   274: invokevirtual method_25365 : (Z)V
    //   277: aload_0
    //   278: getfield B : Lnet/minecraft/class_342;
    //   281: iconst_1
    //   282: invokevirtual method_25365 : (Z)V
    //   285: goto -> 292
    //   288: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   291: athrow
    //   292: iconst_0
    //   293: ireturn
    // Exception table:
    //   from	to	target	type
    //   46	69	72	wtf/opal/x5
    //   66	139	142	wtf/opal/x5
    //   76	152	155	wtf/opal/x5
    //   146	173	176	wtf/opal/x5
    //   180	195	198	wtf/opal/x5
    //   183	205	208	wtf/opal/x5
    //   202	233	236	wtf/opal/x5
    //   212	247	250	wtf/opal/x5
    //   254	262	265	wtf/opal/x5
    //   259	285	288	wtf/opal/x5
  }
  
  public boolean method_25400(char paramChar, int paramInt) {
    this.B.method_25400(paramChar, paramInt);
    this.Y.method_25400(paramChar, paramInt);
    return false;
  }
  
  public boolean method_25402(double paramDouble1, double paramDouble2, int paramInt) {
    this.B.method_25365(this.B.method_25402(paramDouble1, paramDouble2, paramInt));
    this.Y.method_25365(this.Y.method_25402(paramDouble1, paramDouble2, paramInt));
    return false;
  }
  
  public static void y(String paramString) {
    d = paramString;
  }
  
  public static String H() {
    return d;
  }
  
  static {
    long l = a ^ 0x7FCA6B418B2AL;
    y("Zb6WFc");
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[8];
    boolean bool = false;
    String str;
    int i = (str = "/ \024ÐõwiÏ%\037bÁ2&D#,þÉ¯\005©¬£S)nRà ÏýËÄ½c/û¤v=ïÍ3oA?¢°ïø\006í<}úXñg\030S\001Í¾\000gO\023Æµ{µ\024g+\\×68W\fì\001¬\006;Á\001FjrØm\005©ÎJ#~·\000\020ií¨÷ÒÖ Tý°!a%JJËÂ×\023h`KÃ\023ä/,xíÛC z=^48ñ\f\035w¢v%ðI¡ð\007\006â'³oyxpÿj@úz\0010ýãVóiYö\005ßºÍÀ$ãx\004<\002}^TÊÁZê(T$®a±!où{cZâ±%åGÚ\017Æ8fA´Í,& ýÂ;\024ou)\n1ph\bµVMâ]Îò/,Ü\035\017o").length();
    byte b2 = 32;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x5FBC;
    if (c[i] == null) {
      Object[] arrayOfObject;
      try {
        Long long_ = Long.valueOf(Thread.currentThread().threadId());
        arrayOfObject = (Object[])e.get(long_);
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/PKCS5Padding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          e.put(long_, arrayOfObject);
        } 
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/gs", exception);
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
    //   66: ldc_w 'wtf/opal/gs'
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0xF0D;
    if (g[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l = f[i];
      byte[] arrayOfByte2 = { (byte)(int)(l >>> 56L), (byte)(int)(l >>> 48L), (byte)(int)(l >>> 40L), (byte)(int)(l >>> 32L), (byte)(int)(l >>> 24L), (byte)(int)(l >>> 16L), (byte)(int)(l >>> 8L), (byte)(int)l };
      Long long_ = Long.valueOf(Thread.currentThread().threadId());
      Object[] arrayOfObject = (Object[])h.get(long_);
      try {
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/NoPadding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          h.put(long_, arrayOfObject);
        } 
        DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
        SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
        Cipher cipher = (Cipher)arrayOfObject[0];
        cipher.init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
        arrayOfByte3 = cipher.doFinal(arrayOfByte2);
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/gs", exception);
      } 
      int j = (arrayOfByte3[4] & 0xFF) << 24 | (arrayOfByte3[5] & 0xFF) << 16 | (arrayOfByte3[6] & 0xFF) << 8 | arrayOfByte3[7] & 0xFF;
      g[i] = Integer.valueOf(j);
    } 
    return g[i].intValue();
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
    //   66: ldc_w 'wtf/opal/gs'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\gs.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */