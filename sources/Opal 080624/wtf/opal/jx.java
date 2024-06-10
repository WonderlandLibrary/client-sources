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

public final class jx extends d {
  private final ke B;
  
  private final ke t;
  
  private final ke A;
  
  private final kl z;
  
  private final kt a;
  
  private final gm<l7> L;
  
  private static final long b = on.a(4904797576867422726L, 140084862849966800L, MethodHandles.lookup().lookupClass()).a(188447009931955L);
  
  private static final String[] d;
  
  private static final String[] f;
  
  private static final Map g = new HashMap<>(13);
  
  public jx(long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/jx.b : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: lload_1
    //   7: dup2
    //   8: ldc2_w 66709309397103
    //   11: lxor
    //   12: lstore_3
    //   13: dup2
    //   14: ldc2_w 135999841970963
    //   17: lxor
    //   18: lstore #5
    //   20: dup2
    //   21: ldc2_w 94345335763526
    //   24: lxor
    //   25: lstore #7
    //   27: pop2
    //   28: aload_0
    //   29: sipush #25079
    //   32: ldc2_w 943892467242664859
    //   35: lload_1
    //   36: lxor
    //   37: <illegal opcode> y : (IJ)Ljava/lang/String;
    //   42: lload #7
    //   44: sipush #3482
    //   47: ldc2_w 6099487354311602174
    //   50: lload_1
    //   51: lxor
    //   52: <illegal opcode> y : (IJ)Ljava/lang/String;
    //   57: getstatic wtf/opal/kn.OTHER : Lwtf/opal/kn;
    //   60: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   63: aload_0
    //   64: new wtf/opal/ke
    //   67: dup
    //   68: sipush #21641
    //   71: ldc2_w 7437398331286304481
    //   74: lload_1
    //   75: lxor
    //   76: <illegal opcode> y : (IJ)Ljava/lang/String;
    //   81: iconst_1
    //   82: invokespecial <init> : (Ljava/lang/String;Z)V
    //   85: putfield B : Lwtf/opal/ke;
    //   88: aload_0
    //   89: new wtf/opal/ke
    //   92: dup
    //   93: sipush #13375
    //   96: ldc2_w 3385014451818813013
    //   99: lload_1
    //   100: lxor
    //   101: <illegal opcode> y : (IJ)Ljava/lang/String;
    //   106: iconst_1
    //   107: invokespecial <init> : (Ljava/lang/String;Z)V
    //   110: putfield t : Lwtf/opal/ke;
    //   113: aload_0
    //   114: new wtf/opal/ke
    //   117: dup
    //   118: sipush #198
    //   121: ldc2_w 4194552300315206309
    //   124: lload_1
    //   125: lxor
    //   126: <illegal opcode> y : (IJ)Ljava/lang/String;
    //   131: iconst_0
    //   132: invokespecial <init> : (Ljava/lang/String;Z)V
    //   135: putfield A : Lwtf/opal/ke;
    //   138: aload_0
    //   139: new wtf/opal/kl
    //   142: dup
    //   143: sipush #24674
    //   146: ldc2_w 446530247042990595
    //   149: lload_1
    //   150: lxor
    //   151: <illegal opcode> y : (IJ)Ljava/lang/String;
    //   156: sipush #26393
    //   159: ldc2_w 7555291202094761339
    //   162: lload_1
    //   163: lxor
    //   164: <illegal opcode> y : (IJ)Ljava/lang/String;
    //   169: invokespecial <init> : (Ljava/lang/String;Ljava/lang/String;)V
    //   172: putfield z : Lwtf/opal/kl;
    //   175: aload_0
    //   176: new wtf/opal/kt
    //   179: dup
    //   180: sipush #25170
    //   183: ldc2_w 6732582224742252594
    //   186: lload_1
    //   187: lxor
    //   188: <illegal opcode> y : (IJ)Ljava/lang/String;
    //   193: ldc2_w 2.5
    //   196: dconst_1
    //   197: ldc2_w 8.0
    //   200: ldc2_w 0.5
    //   203: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   206: putfield a : Lwtf/opal/kt;
    //   209: invokestatic z : ()Ljava/lang/String;
    //   212: aload_0
    //   213: aload_0
    //   214: <illegal opcode> H : (Lwtf/opal/jx;)Lwtf/opal/gm;
    //   219: putfield L : Lwtf/opal/gm;
    //   222: aload_0
    //   223: getfield a : Lwtf/opal/kt;
    //   226: aload_0
    //   227: getfield t : Lwtf/opal/ke;
    //   230: <illegal opcode> test : ()Ljava/util/function/Predicate;
    //   235: lload_3
    //   236: dup2_x1
    //   237: pop2
    //   238: iconst_3
    //   239: anewarray java/lang/Object
    //   242: dup_x1
    //   243: swap
    //   244: iconst_2
    //   245: swap
    //   246: aastore
    //   247: dup_x2
    //   248: dup_x2
    //   249: pop
    //   250: invokestatic valueOf : (J)Ljava/lang/Long;
    //   253: iconst_1
    //   254: swap
    //   255: aastore
    //   256: dup_x1
    //   257: swap
    //   258: iconst_0
    //   259: swap
    //   260: aastore
    //   261: invokevirtual C : ([Ljava/lang/Object;)V
    //   264: aload_0
    //   265: getfield z : Lwtf/opal/kl;
    //   268: aload_0
    //   269: getfield B : Lwtf/opal/ke;
    //   272: <illegal opcode> test : ()Ljava/util/function/Predicate;
    //   277: lload_3
    //   278: dup2_x1
    //   279: pop2
    //   280: iconst_3
    //   281: anewarray java/lang/Object
    //   284: dup_x1
    //   285: swap
    //   286: iconst_2
    //   287: swap
    //   288: aastore
    //   289: dup_x2
    //   290: dup_x2
    //   291: pop
    //   292: invokestatic valueOf : (J)Ljava/lang/Long;
    //   295: iconst_1
    //   296: swap
    //   297: aastore
    //   298: dup_x1
    //   299: swap
    //   300: iconst_0
    //   301: swap
    //   302: aastore
    //   303: invokevirtual C : ([Ljava/lang/Object;)V
    //   306: astore #9
    //   308: aload_0
    //   309: iconst_5
    //   310: anewarray wtf/opal/k3
    //   313: dup
    //   314: iconst_0
    //   315: aload_0
    //   316: getfield B : Lwtf/opal/ke;
    //   319: aastore
    //   320: dup
    //   321: iconst_1
    //   322: aload_0
    //   323: getfield z : Lwtf/opal/kl;
    //   326: aastore
    //   327: dup
    //   328: iconst_2
    //   329: aload_0
    //   330: getfield t : Lwtf/opal/ke;
    //   333: aastore
    //   334: dup
    //   335: iconst_3
    //   336: aload_0
    //   337: getfield A : Lwtf/opal/ke;
    //   340: aastore
    //   341: dup
    //   342: iconst_4
    //   343: aload_0
    //   344: getfield a : Lwtf/opal/kt;
    //   347: aastore
    //   348: lload #5
    //   350: dup2_x1
    //   351: pop2
    //   352: iconst_2
    //   353: anewarray java/lang/Object
    //   356: dup_x1
    //   357: swap
    //   358: iconst_1
    //   359: swap
    //   360: aastore
    //   361: dup_x2
    //   362: dup_x2
    //   363: pop
    //   364: invokestatic valueOf : (J)Ljava/lang/Long;
    //   367: iconst_0
    //   368: swap
    //   369: aastore
    //   370: invokevirtual o : ([Ljava/lang/Object;)V
    //   373: aload #9
    //   375: ifnull -> 392
    //   378: iconst_1
    //   379: anewarray wtf/opal/d
    //   382: invokestatic p : ([Lwtf/opal/d;)V
    //   385: goto -> 392
    //   388: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   391: athrow
    //   392: return
    // Exception table:
    //   from	to	target	type
    //   308	385	388	wtf/opal/x5
  }
  
  private void g(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/String
    //   7: astore #4
    //   9: dup
    //   10: iconst_1
    //   11: aaload
    //   12: checkcast java/lang/Long
    //   15: invokevirtual longValue : ()J
    //   18: lstore_2
    //   19: pop
    //   20: getstatic wtf/opal/jx.b : J
    //   23: lload_2
    //   24: lxor
    //   25: lstore_2
    //   26: aload_0
    //   27: getfield a : Lwtf/opal/kt;
    //   30: invokevirtual z : ()Ljava/lang/Object;
    //   33: checkcast java/lang/Double
    //   36: invokevirtual doubleValue : ()D
    //   39: dstore #5
    //   41: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   44: invokevirtual method_1566 : ()Lnet/minecraft/class_374;
    //   47: new net/minecraft/class_370
    //   50: dup
    //   51: getstatic net/minecraft/class_370$class_9037.field_47588 : Lnet/minecraft/class_370$class_9037;
    //   54: sipush #15016
    //   57: ldc2_w 8730119650518106950
    //   60: lload_2
    //   61: lxor
    //   62: <illegal opcode> y : (IJ)Ljava/lang/String;
    //   67: invokestatic method_43470 : (Ljava/lang/String;)Lnet/minecraft/class_5250;
    //   70: dload #5
    //   72: dconst_0
    //   73: dcmpl
    //   74: ifle -> 108
    //   77: dload #5
    //   79: sipush #18444
    //   82: ldc2_w 7767629694658507242
    //   85: lload_2
    //   86: lxor
    //   87: <illegal opcode> y : (IJ)Ljava/lang/String;
    //   92: dup_x2
    //   93: pop
    //   94: ldc 's'
    //   96: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;DLjava/lang/String;)Ljava/lang/String;
    //   101: goto -> 110
    //   104: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   107: athrow
    //   108: ldc ''
    //   110: sipush #12802
    //   113: ldc2_w 6468866573520276448
    //   116: lload_2
    //   117: lxor
    //   118: <illegal opcode> y : (IJ)Ljava/lang/String;
    //   123: swap
    //   124: ldc '!'
    //   126: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   131: invokestatic method_43470 : (Ljava/lang/String;)Lnet/minecraft/class_5250;
    //   134: invokespecial <init> : (Lnet/minecraft/class_370$class_9037;Lnet/minecraft/class_2561;Lnet/minecraft/class_2561;)V
    //   137: invokevirtual method_1999 : (Lnet/minecraft/class_368;)V
    //   140: new wtf/opal/bo
    //   143: dup
    //   144: aload_0
    //   145: sipush #14221
    //   148: ldc2_w 3822880133524229757
    //   151: lload_2
    //   152: lxor
    //   153: <illegal opcode> y : (IJ)Ljava/lang/String;
    //   158: dload #5
    //   160: aload #4
    //   162: invokespecial <init> : (Lwtf/opal/jx;Ljava/lang/String;DLjava/lang/String;)V
    //   165: invokevirtual start : ()V
    //   168: return
    // Exception table:
    //   from	to	target	type
    //   41	104	104	wtf/opal/x5
  }
  
  private void lambda$new$0(l7 paraml7) {
    // Byte code:
    //   0: getstatic wtf/opal/jx.b : J
    //   3: ldc2_w 125175738209565
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 91666552940217
    //   13: lxor
    //   14: lstore #4
    //   16: pop2
    //   17: invokestatic z : ()Ljava/lang/String;
    //   20: astore #6
    //   22: aload_0
    //   23: getfield A : Lwtf/opal/ke;
    //   26: invokevirtual z : ()Ljava/lang/Object;
    //   29: checkcast java/lang/Boolean
    //   32: invokevirtual booleanValue : ()Z
    //   35: ifeq -> 185
    //   38: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   41: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   44: ifnull -> 185
    //   47: goto -> 54
    //   50: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   53: athrow
    //   54: aload_1
    //   55: iconst_0
    //   56: anewarray java/lang/Object
    //   59: invokevirtual T : ([Ljava/lang/Object;)Lnet/minecraft/class_2561;
    //   62: aload #6
    //   64: ifnonnull -> 193
    //   67: goto -> 74
    //   70: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   73: athrow
    //   74: invokeinterface getString : ()Ljava/lang/String;
    //   79: sipush #32073
    //   82: ldc2_w 3745346352975042426
    //   85: lload_2
    //   86: lxor
    //   87: <illegal opcode> y : (IJ)Ljava/lang/String;
    //   92: invokevirtual equals : (Ljava/lang/Object;)Z
    //   95: ifeq -> 185
    //   98: goto -> 105
    //   101: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   104: athrow
    //   105: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   108: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   111: sipush #6701
    //   114: ldc2_w 5967557421920661512
    //   117: lload_2
    //   118: lxor
    //   119: <illegal opcode> y : (IJ)Ljava/lang/String;
    //   124: invokevirtual method_45729 : (Ljava/lang/String;)V
    //   127: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   130: invokevirtual method_1566 : ()Lnet/minecraft/class_374;
    //   133: new net/minecraft/class_370
    //   136: dup
    //   137: getstatic net/minecraft/class_370$class_9037.field_47588 : Lnet/minecraft/class_370$class_9037;
    //   140: sipush #25079
    //   143: ldc2_w 943793538944617425
    //   146: lload_2
    //   147: lxor
    //   148: <illegal opcode> y : (IJ)Ljava/lang/String;
    //   153: invokestatic method_43470 : (Ljava/lang/String;)Lnet/minecraft/class_5250;
    //   156: sipush #27716
    //   159: ldc2_w 1479002286804125285
    //   162: lload_2
    //   163: lxor
    //   164: <illegal opcode> y : (IJ)Ljava/lang/String;
    //   169: invokestatic method_43470 : (Ljava/lang/String;)Lnet/minecraft/class_5250;
    //   172: invokespecial <init> : (Lnet/minecraft/class_370$class_9037;Lnet/minecraft/class_2561;Lnet/minecraft/class_2561;)V
    //   175: invokevirtual method_1999 : (Lnet/minecraft/class_368;)V
    //   178: goto -> 185
    //   181: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   184: athrow
    //   185: aload_1
    //   186: iconst_0
    //   187: anewarray java/lang/Object
    //   190: invokevirtual T : ([Ljava/lang/Object;)Lnet/minecraft/class_2561;
    //   193: invokeinterface method_10855 : ()Ljava/util/List;
    //   198: astore #7
    //   200: aload #7
    //   202: invokeinterface iterator : ()Ljava/util/Iterator;
    //   207: astore #8
    //   209: aload #8
    //   211: invokeinterface hasNext : ()Z
    //   216: ifeq -> 623
    //   219: aload #8
    //   221: invokeinterface next : ()Ljava/lang/Object;
    //   226: checkcast net/minecraft/class_2561
    //   229: astore #9
    //   231: aload #9
    //   233: invokeinterface method_10866 : ()Lnet/minecraft/class_2583;
    //   238: invokevirtual method_10970 : ()Lnet/minecraft/class_2558;
    //   241: astore #10
    //   243: aload #10
    //   245: aload #6
    //   247: ifnonnull -> 262
    //   250: ifnull -> 618
    //   253: goto -> 260
    //   256: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   259: athrow
    //   260: aload #10
    //   262: aload #6
    //   264: ifnonnull -> 292
    //   267: invokevirtual method_10845 : ()Lnet/minecraft/class_2558$class_2559;
    //   270: getstatic net/minecraft/class_2558$class_2559.field_11750 : Lnet/minecraft/class_2558$class_2559;
    //   273: if_acmpne -> 618
    //   276: goto -> 283
    //   279: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   282: athrow
    //   283: aload #10
    //   285: goto -> 292
    //   288: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   291: athrow
    //   292: invokevirtual method_10844 : ()Ljava/lang/String;
    //   295: astore #11
    //   297: aload #11
    //   299: sipush #32330
    //   302: ldc2_w 9094596624634467430
    //   305: lload_2
    //   306: lxor
    //   307: <illegal opcode> y : (IJ)Ljava/lang/String;
    //   312: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   315: aload #6
    //   317: ifnonnull -> 355
    //   320: ifeq -> 618
    //   323: goto -> 330
    //   326: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   329: athrow
    //   330: aload #11
    //   332: sipush #24272
    //   335: ldc2_w 6072988363390514420
    //   338: lload_2
    //   339: lxor
    //   340: <illegal opcode> y : (IJ)Ljava/lang/String;
    //   345: invokevirtual equals : (Ljava/lang/Object;)Z
    //   348: goto -> 355
    //   351: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   354: athrow
    //   355: aload #6
    //   357: ifnonnull -> 390
    //   360: ifne -> 618
    //   363: goto -> 370
    //   366: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   369: athrow
    //   370: aload_0
    //   371: getfield B : Lwtf/opal/ke;
    //   374: invokevirtual z : ()Ljava/lang/Object;
    //   377: checkcast java/lang/Boolean
    //   380: invokevirtual booleanValue : ()Z
    //   383: goto -> 390
    //   386: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   389: athrow
    //   390: aload #6
    //   392: ifnonnull -> 589
    //   395: ifeq -> 564
    //   398: goto -> 405
    //   401: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   404: athrow
    //   405: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   408: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   411: aload #6
    //   413: ifnonnull -> 583
    //   416: goto -> 423
    //   419: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   422: athrow
    //   423: ifnull -> 564
    //   426: goto -> 433
    //   429: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   432: athrow
    //   433: aload_1
    //   434: iconst_0
    //   435: anewarray java/lang/Object
    //   438: invokevirtual T : ([Ljava/lang/Object;)Lnet/minecraft/class_2561;
    //   441: invokeinterface getString : ()Ljava/lang/String;
    //   446: sipush #27372
    //   449: ldc2_w 6130516559180710110
    //   452: lload_2
    //   453: lxor
    //   454: <illegal opcode> y : (IJ)Ljava/lang/String;
    //   459: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   462: aload #6
    //   464: ifnonnull -> 589
    //   467: goto -> 474
    //   470: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   473: athrow
    //   474: ifne -> 564
    //   477: goto -> 484
    //   480: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   483: athrow
    //   484: aload_0
    //   485: getfield z : Lwtf/opal/kl;
    //   488: invokevirtual z : ()Ljava/lang/Object;
    //   491: checkcast java/lang/String
    //   494: invokevirtual isEmpty : ()Z
    //   497: aload #6
    //   499: ifnonnull -> 589
    //   502: goto -> 509
    //   505: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   508: athrow
    //   509: ifne -> 564
    //   512: goto -> 519
    //   515: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   518: athrow
    //   519: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   522: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   525: aload_0
    //   526: getfield z : Lwtf/opal/kl;
    //   529: invokevirtual z : ()Ljava/lang/Object;
    //   532: checkcast java/lang/String
    //   535: sipush #26851
    //   538: ldc2_w 7351128330033971918
    //   541: lload_2
    //   542: lxor
    //   543: <illegal opcode> y : (IJ)Ljava/lang/String;
    //   548: swap
    //   549: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   554: invokevirtual method_45729 : (Ljava/lang/String;)V
    //   557: goto -> 564
    //   560: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   563: athrow
    //   564: aload_0
    //   565: aload #6
    //   567: ifnonnull -> 593
    //   570: getfield t : Lwtf/opal/ke;
    //   573: invokevirtual z : ()Ljava/lang/Object;
    //   576: goto -> 583
    //   579: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   582: athrow
    //   583: checkcast java/lang/Boolean
    //   586: invokevirtual booleanValue : ()Z
    //   589: ifeq -> 618
    //   592: aload_0
    //   593: aload #11
    //   595: lload #4
    //   597: iconst_2
    //   598: anewarray java/lang/Object
    //   601: dup_x2
    //   602: dup_x2
    //   603: pop
    //   604: invokestatic valueOf : (J)Ljava/lang/Long;
    //   607: iconst_1
    //   608: swap
    //   609: aastore
    //   610: dup_x1
    //   611: swap
    //   612: iconst_0
    //   613: swap
    //   614: aastore
    //   615: invokevirtual g : ([Ljava/lang/Object;)V
    //   618: aload #6
    //   620: ifnull -> 209
    //   623: return
    // Exception table:
    //   from	to	target	type
    //   22	47	50	wtf/opal/x5
    //   38	67	70	wtf/opal/x5
    //   54	98	101	wtf/opal/x5
    //   74	178	181	wtf/opal/x5
    //   243	253	256	wtf/opal/x5
    //   262	276	279	wtf/opal/x5
    //   267	285	288	wtf/opal/x5
    //   297	323	326	wtf/opal/x5
    //   320	348	351	wtf/opal/x5
    //   355	363	366	wtf/opal/x5
    //   360	383	386	wtf/opal/x5
    //   390	398	401	wtf/opal/x5
    //   395	416	419	wtf/opal/x5
    //   405	426	429	wtf/opal/x5
    //   423	467	470	wtf/opal/x5
    //   433	477	480	wtf/opal/x5
    //   474	502	505	wtf/opal/x5
    //   484	512	515	wtf/opal/x5
    //   509	557	560	wtf/opal/x5
    //   564	576	579	wtf/opal/x5
  }
  
  static {
    long l = b ^ 0x612462F4F6F7L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[19];
    boolean bool = false;
    String str;
    int i = (str = "[Î\022Ã¼¡-R·]ÛiÍæÃ½qã½Í\005¢­\030b²X4üUSo@)e\027i~êØy^(à]\020é\027I¼À\003ú'fþÍñ\n'H\024Â8`Â\\\030çO»õ@;æWN%8)(`´¬ÊdÄj\t\036|æúýv\fµâ\003áÎ7¹1|\037c\0168¼#-ÏÉkåh/\037µÉìh©\030ûïµ\031ÍÈÐ&=U´ÎÜ}+rÁ\fVâ@\020\0024Éþ\0031#\bÈ¸Ñ\002~ I\007?N¯7V>Ñ\022 U'\tÕÚãÕ¹§[ô¬¬<8 r\020ST\t¡ÿÀñoâ£à2øØe\030\023jTñ\013¬¬³\033ÛxþË³0à;\032¶]Å\030÷\000FDÉ8\032/k<I\007\033\021Õ¬(ãNØrÖ H\033 - \033ðð·A\025@(9\000WHaª­ äKàZ@S ¦BÒ$'dUµëËlªÐ\007cãS)Uódè>è\020«/=\në¸* Ó¿\007s:ö[ Q#T\nè7:ÅÌ\026f[î¬½K\026¤Ò+¢¥¤(\\9Z@v²dbû7!\027åk\002s·®³v\003ð_Âl?Ú\017HçØ\000\007Üû=éÿ½Y\030û÷¶>×½}»ÿ\007V&j@Iy&Af©ºl0\020´OCÇ{x$êBÖèà!\020Ø?dµ8¾\004»û@\031}Hm 5üû\bPôsc\035^öî\006Ë\023ß\bq5J2è\020~,³£iÃó\"i»Ö£ÊHÅ1X'ÎÑ'*ú_°Ê\030å`\\'òZ>ØÊ ").length();
    byte b2 = 48;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x4A46;
    if (f[i] == null) {
      Object[] arrayOfObject;
      try {
        Long long_ = Long.valueOf(Thread.currentThread().threadId());
        arrayOfObject = (Object[])g.get(long_);
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/PKCS5Padding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          g.put(long_, arrayOfObject);
        } 
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/jx", exception);
      } 
      byte[] arrayOfByte1 = new byte[8];
      arrayOfByte1[0] = (byte)(int)(paramLong >>> 56L);
      for (byte b = 1; b < 8; b++)
        arrayOfByte1[b] = (byte)(int)(paramLong << b * 8 >>> 56L); 
      DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
      SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
      ((Cipher)arrayOfObject[0]).init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
      byte[] arrayOfByte2 = d[i].getBytes("ISO-8859-1");
      f[i] = a(((Cipher)arrayOfObject[0]).doFinal(arrayOfByte2));
    } 
    return f[i];
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
    //   65: ldc_w 'wtf/opal/jx'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\jx.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */