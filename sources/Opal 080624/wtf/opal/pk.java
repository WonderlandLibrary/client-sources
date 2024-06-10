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
import net.minecraft.class_1309;
import net.minecraft.class_332;

public final class pk extends u_<jb> {
  private final pa c = d1.q(new Object[0]).z(new Object[0]);
  
  private final bu L;
  
  private final gm<uj> b;
  
  private static String T;
  
  private static final long a;
  
  private static final long[] d;
  
  private static final Integer[] e;
  
  private static final Map f;
  
  public pk(jb paramjb, long paramLong) {
    super(paramjb);
    String str = s();
    try {
      this.L = d1.q(new Object[0]).h(new Object[0]);
      this.b = this::lambda$new$2;
      if (d.D() != null)
        P("amDX6"); 
    } catch (x5 x5) {
      throw b(null);
    } 
  }
  
  public Enum V(Object[] paramArrayOfObject) {
    return xg.OPAL;
  }
  
  private void lambda$new$2(uj paramuj) {
    // Byte code:
    //   0: getstatic wtf/opal/pk.a : J
    //   3: ldc2_w 53189488030124
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 126139807379486
    //   13: lxor
    //   14: lstore #4
    //   16: dup2
    //   17: ldc2_w 132170811329774
    //   20: lxor
    //   21: lstore #6
    //   23: dup2
    //   24: ldc2_w 113277273386270
    //   27: lxor
    //   28: lstore #8
    //   30: dup2
    //   31: ldc2_w 24617535777084
    //   34: lxor
    //   35: lstore #10
    //   37: dup2
    //   38: ldc2_w 45889645550725
    //   41: lxor
    //   42: lstore #12
    //   44: dup2
    //   45: ldc2_w 65758003601374
    //   48: lxor
    //   49: lstore #14
    //   51: pop2
    //   52: invokestatic s : ()Ljava/lang/String;
    //   55: aload_0
    //   56: getfield G : Lwtf/opal/d;
    //   59: checkcast wtf/opal/jb
    //   62: lload #4
    //   64: iconst_1
    //   65: anewarray java/lang/Object
    //   68: dup_x2
    //   69: dup_x2
    //   70: pop
    //   71: invokestatic valueOf : (J)Ljava/lang/Long;
    //   74: iconst_0
    //   75: swap
    //   76: aastore
    //   77: invokevirtual E : ([Ljava/lang/Object;)Lnet/minecraft/class_1309;
    //   80: astore #17
    //   82: astore #16
    //   84: aload #17
    //   86: ifnonnull -> 94
    //   89: return
    //   90: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   93: athrow
    //   94: iconst_0
    //   95: anewarray java/lang/Object
    //   98: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   101: iconst_0
    //   102: anewarray java/lang/Object
    //   105: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   108: ldc wtf/opal/jt
    //   110: iconst_1
    //   111: anewarray java/lang/Object
    //   114: dup_x1
    //   115: swap
    //   116: iconst_0
    //   117: swap
    //   118: aastore
    //   119: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   122: checkcast wtf/opal/jt
    //   125: astore #18
    //   127: aload_1
    //   128: iconst_0
    //   129: anewarray java/lang/Object
    //   132: invokevirtual B : ([Ljava/lang/Object;)Lnet/minecraft/class_332;
    //   135: astore #19
    //   137: aload #17
    //   139: instanceof net/minecraft/class_742
    //   142: aload #16
    //   144: ifnull -> 170
    //   147: ifeq -> 173
    //   150: goto -> 157
    //   153: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   156: athrow
    //   157: sipush #3277
    //   160: ldc2_w 4126864548113416550
    //   163: lload_2
    //   164: lxor
    //   165: <illegal opcode> p : (IJ)I
    //   170: goto -> 174
    //   173: iconst_0
    //   174: istore #20
    //   176: aload_0
    //   177: getfield G : Lwtf/opal/d;
    //   180: checkcast wtf/opal/jb
    //   183: getfield z : Lwtf/opal/k6;
    //   186: sipush #25606
    //   189: ldc2_w 1451353626199026091
    //   192: lload_2
    //   193: lxor
    //   194: <illegal opcode> p : (IJ)I
    //   199: iload #20
    //   201: iadd
    //   202: i2f
    //   203: ldc 30.0
    //   205: iconst_2
    //   206: anewarray java/lang/Object
    //   209: dup_x1
    //   210: swap
    //   211: invokestatic valueOf : (F)Ljava/lang/Float;
    //   214: iconst_1
    //   215: swap
    //   216: aastore
    //   217: dup_x1
    //   218: swap
    //   219: invokestatic valueOf : (F)Ljava/lang/Float;
    //   222: iconst_0
    //   223: swap
    //   224: aastore
    //   225: invokevirtual Y : ([Ljava/lang/Object;)V
    //   228: sipush #21498
    //   231: aload_0
    //   232: getfield G : Lwtf/opal/d;
    //   235: checkcast wtf/opal/jb
    //   238: getfield z : Lwtf/opal/k6;
    //   241: ldc 6.0
    //   243: iconst_1
    //   244: anewarray java/lang/Object
    //   247: dup_x1
    //   248: swap
    //   249: invokestatic valueOf : (F)Ljava/lang/Float;
    //   252: iconst_0
    //   253: swap
    //   254: aastore
    //   255: invokevirtual U : ([Ljava/lang/Object;)V
    //   258: ldc2_w 6911367652389772880
    //   261: lload_2
    //   262: lxor
    //   263: aload_0
    //   264: getfield G : Lwtf/opal/d;
    //   267: checkcast wtf/opal/jb
    //   270: iconst_0
    //   271: anewarray java/lang/Object
    //   274: invokevirtual k : ([Ljava/lang/Object;)F
    //   277: fstore #21
    //   279: aload_0
    //   280: getfield G : Lwtf/opal/d;
    //   283: checkcast wtf/opal/jb
    //   286: getfield z : Lwtf/opal/k6;
    //   289: lload #12
    //   291: iconst_1
    //   292: anewarray java/lang/Object
    //   295: dup_x2
    //   296: dup_x2
    //   297: pop
    //   298: invokestatic valueOf : (J)Ljava/lang/Long;
    //   301: iconst_0
    //   302: swap
    //   303: aastore
    //   304: invokevirtual t : ([Ljava/lang/Object;)F
    //   307: fstore #22
    //   309: aload_0
    //   310: getfield G : Lwtf/opal/d;
    //   313: checkcast wtf/opal/jb
    //   316: getfield z : Lwtf/opal/k6;
    //   319: iconst_0
    //   320: anewarray java/lang/Object
    //   323: invokevirtual x : ([Ljava/lang/Object;)F
    //   326: fstore #23
    //   328: <illegal opcode> p : (IJ)I
    //   333: iload #20
    //   335: iadd
    //   336: i2f
    //   337: fstore #24
    //   339: ldc 30.0
    //   341: fstore #25
    //   343: fconst_1
    //   344: fstore #26
    //   346: aload_0
    //   347: getfield G : Lwtf/opal/d;
    //   350: checkcast wtf/opal/jb
    //   353: getfield f : Lwtf/opal/ke;
    //   356: invokevirtual z : ()Ljava/lang/Object;
    //   359: checkcast java/lang/Boolean
    //   362: invokevirtual booleanValue : ()Z
    //   365: ifeq -> 571
    //   368: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   371: getfield field_1769 : Lnet/minecraft/class_761;
    //   374: checkcast wtf/opal/mixin/WorldRendererAccessor
    //   377: invokeinterface getFrustum : ()Lnet/minecraft/class_4604;
    //   382: astore #27
    //   384: aload #17
    //   386: aload_1
    //   387: iconst_0
    //   388: anewarray java/lang/Object
    //   391: invokevirtual N : ([Ljava/lang/Object;)F
    //   394: lload #14
    //   396: iconst_3
    //   397: anewarray java/lang/Object
    //   400: dup_x2
    //   401: dup_x2
    //   402: pop
    //   403: invokestatic valueOf : (J)Ljava/lang/Long;
    //   406: iconst_2
    //   407: swap
    //   408: aastore
    //   409: dup_x1
    //   410: swap
    //   411: invokestatic valueOf : (F)Ljava/lang/Float;
    //   414: iconst_1
    //   415: swap
    //   416: aastore
    //   417: dup_x1
    //   418: swap
    //   419: iconst_0
    //   420: swap
    //   421: aastore
    //   422: invokestatic n : ([Ljava/lang/Object;)Lorg/joml/Vector4d;
    //   425: astore #28
    //   427: aload #28
    //   429: ifnull -> 452
    //   432: aload #27
    //   434: aload #17
    //   436: invokevirtual method_5829 : ()Lnet/minecraft/class_238;
    //   439: invokevirtual method_23093 : (Lnet/minecraft/class_238;)Z
    //   442: ifne -> 457
    //   445: goto -> 452
    //   448: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   451: athrow
    //   452: return
    //   453: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   456: athrow
    //   457: aload_0
    //   458: getfield G : Lwtf/opal/d;
    //   461: checkcast wtf/opal/jb
    //   464: lload #8
    //   466: aload #28
    //   468: fload #24
    //   470: f2d
    //   471: ldc2_w 30.0
    //   474: iconst_4
    //   475: anewarray java/lang/Object
    //   478: dup_x2
    //   479: dup_x2
    //   480: pop
    //   481: invokestatic valueOf : (D)Ljava/lang/Double;
    //   484: iconst_3
    //   485: swap
    //   486: aastore
    //   487: dup_x2
    //   488: dup_x2
    //   489: pop
    //   490: invokestatic valueOf : (D)Ljava/lang/Double;
    //   493: iconst_2
    //   494: swap
    //   495: aastore
    //   496: dup_x1
    //   497: swap
    //   498: iconst_1
    //   499: swap
    //   500: aastore
    //   501: dup_x2
    //   502: dup_x2
    //   503: pop
    //   504: invokestatic valueOf : (J)Ljava/lang/Long;
    //   507: iconst_0
    //   508: swap
    //   509: aastore
    //   510: invokevirtual K : ([Ljava/lang/Object;)Loshi/util/tuples/Pair;
    //   513: astore #29
    //   515: aload #29
    //   517: invokevirtual getA : ()Ljava/lang/Object;
    //   520: checkcast java/lang/Double
    //   523: invokevirtual floatValue : ()F
    //   526: fstore #22
    //   528: aload #29
    //   530: invokevirtual getB : ()Ljava/lang/Object;
    //   533: checkcast java/lang/Double
    //   536: invokevirtual floatValue : ()F
    //   539: fstore #23
    //   541: aload #28
    //   543: getfield z : D
    //   546: aload #28
    //   548: getfield x : D
    //   551: dsub
    //   552: ldc2_w 1.4
    //   555: dmul
    //   556: dstore #30
    //   558: dconst_1
    //   559: dload #30
    //   561: fload #24
    //   563: f2d
    //   564: ddiv
    //   565: invokestatic min : (DD)D
    //   568: d2f
    //   569: fstore #26
    //   571: fload #22
    //   573: fstore #27
    //   575: fload #23
    //   577: fstore #28
    //   579: fload #26
    //   581: fstore #29
    //   583: aload_0
    //   584: getfield c : Lwtf/opal/pa;
    //   587: aload_0
    //   588: fload #29
    //   590: fload #27
    //   592: fload #28
    //   594: fload #24
    //   596: iload #20
    //   598: fload #21
    //   600: aload #18
    //   602: aload #17
    //   604: aload #19
    //   606: <illegal opcode> run : (Lwtf/opal/pk;FFFFIFLwtf/opal/jt;Lnet/minecraft/class_1309;Lnet/minecraft/class_332;)Ljava/lang/Runnable;
    //   611: lload #6
    //   613: iconst_2
    //   614: anewarray java/lang/Object
    //   617: dup_x2
    //   618: dup_x2
    //   619: pop
    //   620: invokestatic valueOf : (J)Ljava/lang/Long;
    //   623: iconst_1
    //   624: swap
    //   625: aastore
    //   626: dup_x1
    //   627: swap
    //   628: iconst_0
    //   629: swap
    //   630: aastore
    //   631: invokevirtual F : ([Ljava/lang/Object;)V
    //   634: aload_0
    //   635: getfield G : Lwtf/opal/d;
    //   638: checkcast wtf/opal/jb
    //   641: lload #10
    //   643: iconst_1
    //   644: anewarray java/lang/Object
    //   647: dup_x2
    //   648: dup_x2
    //   649: pop
    //   650: invokestatic valueOf : (J)Ljava/lang/Long;
    //   653: iconst_0
    //   654: swap
    //   655: aastore
    //   656: invokevirtual x : ([Ljava/lang/Object;)V
    //   659: return
    // Exception table:
    //   from	to	target	type
    //   84	90	90	wtf/opal/x5
    //   137	150	153	wtf/opal/x5
    //   427	445	448	wtf/opal/x5
    //   432	453	453	wtf/opal/x5
  }
  
  private void lambda$new$1(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, int paramInt, float paramFloat5, jt paramjt, class_1309 paramclass_1309, class_332 paramclass_332) {
    // Byte code:
    //   0: getstatic wtf/opal/pk.a : J
    //   3: ldc2_w 14783746397678
    //   6: lxor
    //   7: lstore #10
    //   9: lload #10
    //   11: dup2
    //   12: ldc2_w 90774788268466
    //   15: lxor
    //   16: lstore #12
    //   18: pop2
    //   19: aload_0
    //   20: getfield c : Lwtf/opal/pa;
    //   23: ldc_w 0.5
    //   26: aload_0
    //   27: getfield G : Lwtf/opal/d;
    //   30: checkcast wtf/opal/jb
    //   33: getfield Q : Lwtf/opal/d2;
    //   36: lload #12
    //   38: iconst_1
    //   39: anewarray java/lang/Object
    //   42: dup_x2
    //   43: dup_x2
    //   44: pop
    //   45: invokestatic valueOf : (J)Ljava/lang/Long;
    //   48: iconst_0
    //   49: swap
    //   50: aastore
    //   51: invokevirtual P : ([Ljava/lang/Object;)Ljava/lang/Double;
    //   54: invokevirtual floatValue : ()F
    //   57: fadd
    //   58: fload_1
    //   59: fmul
    //   60: fload_2
    //   61: fload_3
    //   62: fload #4
    //   64: ldc 30.0
    //   66: aload_0
    //   67: fload_2
    //   68: fload_3
    //   69: fload #4
    //   71: iload #5
    //   73: fload #6
    //   75: aload #7
    //   77: aload #8
    //   79: aload #9
    //   81: <illegal opcode> run : (Lwtf/opal/pk;FFFIFLwtf/opal/jt;Lnet/minecraft/class_1309;Lnet/minecraft/class_332;)Ljava/lang/Runnable;
    //   86: bipush #6
    //   88: anewarray java/lang/Object
    //   91: dup_x1
    //   92: swap
    //   93: iconst_5
    //   94: swap
    //   95: aastore
    //   96: dup_x1
    //   97: swap
    //   98: invokestatic valueOf : (F)Ljava/lang/Float;
    //   101: iconst_4
    //   102: swap
    //   103: aastore
    //   104: dup_x1
    //   105: swap
    //   106: invokestatic valueOf : (F)Ljava/lang/Float;
    //   109: iconst_3
    //   110: swap
    //   111: aastore
    //   112: dup_x1
    //   113: swap
    //   114: invokestatic valueOf : (F)Ljava/lang/Float;
    //   117: iconst_2
    //   118: swap
    //   119: aastore
    //   120: dup_x1
    //   121: swap
    //   122: invokestatic valueOf : (F)Ljava/lang/Float;
    //   125: iconst_1
    //   126: swap
    //   127: aastore
    //   128: dup_x1
    //   129: swap
    //   130: invokestatic valueOf : (F)Ljava/lang/Float;
    //   133: iconst_0
    //   134: swap
    //   135: aastore
    //   136: invokevirtual r : ([Ljava/lang/Object;)V
    //   139: return
  }
  
  private void lambda$new$0(float paramFloat1, float paramFloat2, float paramFloat3, int paramInt, float paramFloat4, jt paramjt, class_1309 paramclass_1309, class_332 paramclass_332) {
    // Byte code:
    //   0: getstatic wtf/opal/pk.a : J
    //   3: ldc2_w 111968951084241
    //   6: lxor
    //   7: lstore #9
    //   9: lload #9
    //   11: dup2
    //   12: ldc2_w 66891935503563
    //   15: lxor
    //   16: lstore #11
    //   18: dup2
    //   19: ldc2_w 22811227495023
    //   22: lxor
    //   23: lstore #13
    //   25: dup2
    //   26: ldc2_w 61504969541736
    //   29: lxor
    //   30: lstore #15
    //   32: dup2
    //   33: ldc2_w 111627827055644
    //   36: lxor
    //   37: lstore #17
    //   39: dup2
    //   40: ldc2_w 99611358040335
    //   43: lxor
    //   44: lstore #19
    //   46: pop2
    //   47: aload_0
    //   48: getfield c : Lwtf/opal/pa;
    //   51: fload_1
    //   52: fload_2
    //   53: fload_3
    //   54: ldc 30.0
    //   56: ldc 6.0
    //   58: sipush #18677
    //   61: ldc2_w 3883192843489234983
    //   64: lload #9
    //   66: lxor
    //   67: <illegal opcode> p : (IJ)I
    //   72: lload #13
    //   74: bipush #7
    //   76: anewarray java/lang/Object
    //   79: dup_x2
    //   80: dup_x2
    //   81: pop
    //   82: invokestatic valueOf : (J)Ljava/lang/Long;
    //   85: bipush #6
    //   87: swap
    //   88: aastore
    //   89: dup_x1
    //   90: swap
    //   91: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   94: iconst_5
    //   95: swap
    //   96: aastore
    //   97: dup_x1
    //   98: swap
    //   99: invokestatic valueOf : (F)Ljava/lang/Float;
    //   102: iconst_4
    //   103: swap
    //   104: aastore
    //   105: dup_x1
    //   106: swap
    //   107: invokestatic valueOf : (F)Ljava/lang/Float;
    //   110: iconst_3
    //   111: swap
    //   112: aastore
    //   113: dup_x1
    //   114: swap
    //   115: invokestatic valueOf : (F)Ljava/lang/Float;
    //   118: iconst_2
    //   119: swap
    //   120: aastore
    //   121: dup_x1
    //   122: swap
    //   123: invokestatic valueOf : (F)Ljava/lang/Float;
    //   126: iconst_1
    //   127: swap
    //   128: aastore
    //   129: dup_x1
    //   130: swap
    //   131: invokestatic valueOf : (F)Ljava/lang/Float;
    //   134: iconst_0
    //   135: swap
    //   136: aastore
    //   137: invokevirtual M : ([Ljava/lang/Object;)V
    //   140: aload_0
    //   141: getfield c : Lwtf/opal/pa;
    //   144: fload_1
    //   145: iload #4
    //   147: i2f
    //   148: fadd
    //   149: ldc_w 5.0
    //   152: fadd
    //   153: fload_2
    //   154: ldc_w 21.0
    //   157: fadd
    //   158: ldc_w 90.0
    //   161: ldc_w 4.5
    //   164: fconst_2
    //   165: sipush #24081
    //   168: ldc2_w 8655712885848360640
    //   171: lload #9
    //   173: lxor
    //   174: <illegal opcode> p : (IJ)I
    //   179: lload #13
    //   181: bipush #7
    //   183: anewarray java/lang/Object
    //   186: dup_x2
    //   187: dup_x2
    //   188: pop
    //   189: invokestatic valueOf : (J)Ljava/lang/Long;
    //   192: bipush #6
    //   194: swap
    //   195: aastore
    //   196: dup_x1
    //   197: swap
    //   198: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   201: iconst_5
    //   202: swap
    //   203: aastore
    //   204: dup_x1
    //   205: swap
    //   206: invokestatic valueOf : (F)Ljava/lang/Float;
    //   209: iconst_4
    //   210: swap
    //   211: aastore
    //   212: dup_x1
    //   213: swap
    //   214: invokestatic valueOf : (F)Ljava/lang/Float;
    //   217: iconst_3
    //   218: swap
    //   219: aastore
    //   220: dup_x1
    //   221: swap
    //   222: invokestatic valueOf : (F)Ljava/lang/Float;
    //   225: iconst_2
    //   226: swap
    //   227: aastore
    //   228: dup_x1
    //   229: swap
    //   230: invokestatic valueOf : (F)Ljava/lang/Float;
    //   233: iconst_1
    //   234: swap
    //   235: aastore
    //   236: dup_x1
    //   237: swap
    //   238: invokestatic valueOf : (F)Ljava/lang/Float;
    //   241: iconst_0
    //   242: swap
    //   243: aastore
    //   244: invokevirtual M : ([Ljava/lang/Object;)V
    //   247: aload_0
    //   248: getfield c : Lwtf/opal/pa;
    //   251: fload_1
    //   252: iload #4
    //   254: i2f
    //   255: fadd
    //   256: ldc_w 5.0
    //   259: fadd
    //   260: fload_2
    //   261: ldc_w 21.0
    //   264: fadd
    //   265: ldc_w 90.0
    //   268: fload #5
    //   270: ldc_w 4.5
    //   273: fmul
    //   274: invokestatic min : (FF)F
    //   277: ldc_w 4.5
    //   280: lload #11
    //   282: fconst_2
    //   283: aload #6
    //   285: iconst_0
    //   286: anewarray java/lang/Object
    //   289: invokevirtual W : ([Ljava/lang/Object;)Lwtf/opal/kg;
    //   292: invokevirtual z : ()Ljava/lang/Object;
    //   295: checkcast java/lang/Integer
    //   298: invokevirtual intValue : ()I
    //   301: aload #6
    //   303: iconst_0
    //   304: anewarray java/lang/Object
    //   307: invokevirtual L : ([Ljava/lang/Object;)Lwtf/opal/kg;
    //   310: invokevirtual z : ()Ljava/lang/Object;
    //   313: checkcast java/lang/Integer
    //   316: invokevirtual intValue : ()I
    //   319: fconst_0
    //   320: bipush #9
    //   322: anewarray java/lang/Object
    //   325: dup_x1
    //   326: swap
    //   327: invokestatic valueOf : (F)Ljava/lang/Float;
    //   330: bipush #8
    //   332: swap
    //   333: aastore
    //   334: dup_x1
    //   335: swap
    //   336: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   339: bipush #7
    //   341: swap
    //   342: aastore
    //   343: dup_x1
    //   344: swap
    //   345: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   348: bipush #6
    //   350: swap
    //   351: aastore
    //   352: dup_x1
    //   353: swap
    //   354: invokestatic valueOf : (F)Ljava/lang/Float;
    //   357: iconst_5
    //   358: swap
    //   359: aastore
    //   360: dup_x2
    //   361: dup_x2
    //   362: pop
    //   363: invokestatic valueOf : (J)Ljava/lang/Long;
    //   366: iconst_4
    //   367: swap
    //   368: aastore
    //   369: dup_x1
    //   370: swap
    //   371: invokestatic valueOf : (F)Ljava/lang/Float;
    //   374: iconst_3
    //   375: swap
    //   376: aastore
    //   377: dup_x1
    //   378: swap
    //   379: invokestatic valueOf : (F)Ljava/lang/Float;
    //   382: iconst_2
    //   383: swap
    //   384: aastore
    //   385: dup_x1
    //   386: swap
    //   387: invokestatic valueOf : (F)Ljava/lang/Float;
    //   390: iconst_1
    //   391: swap
    //   392: aastore
    //   393: dup_x1
    //   394: swap
    //   395: invokestatic valueOf : (F)Ljava/lang/Float;
    //   398: iconst_0
    //   399: swap
    //   400: aastore
    //   401: invokevirtual e : ([Ljava/lang/Object;)V
    //   404: invokestatic s : ()Ljava/lang/String;
    //   407: aload #7
    //   409: invokevirtual method_6032 : ()F
    //   412: aload #7
    //   414: invokevirtual method_6067 : ()F
    //   417: fadd
    //   418: aload #7
    //   420: invokevirtual method_6063 : ()F
    //   423: aload #7
    //   425: invokevirtual method_6067 : ()F
    //   428: fadd
    //   429: fdiv
    //   430: fconst_0
    //   431: fconst_1
    //   432: invokestatic method_15363 : (FFF)F
    //   435: fstore #22
    //   437: fload #22
    //   439: ldc_w 100.0
    //   442: fmul
    //   443: invokestatic round : (F)I
    //   446: <illegal opcode> makeConcatWithConstants : (I)Ljava/lang/String;
    //   451: astore #23
    //   453: aload_0
    //   454: getfield L : Lwtf/opal/bu;
    //   457: iconst_0
    //   458: putfield t : Z
    //   461: aload_0
    //   462: getfield L : Lwtf/opal/bu;
    //   465: getstatic wtf/opal/lx.SEMI_BOLD : Lwtf/opal/lx;
    //   468: aload #8
    //   470: aload #7
    //   472: invokevirtual method_5477 : ()Lnet/minecraft/class_2561;
    //   475: invokeinterface getString : ()Ljava/lang/String;
    //   480: fload_1
    //   481: iload #4
    //   483: i2f
    //   484: fadd
    //   485: ldc_w 5.0
    //   488: fadd
    //   489: fload_2
    //   490: ldc_w 4.0
    //   493: fadd
    //   494: lload #15
    //   496: dup2_x1
    //   497: pop2
    //   498: ldc_w 9.5
    //   501: iconst_m1
    //   502: bipush #8
    //   504: anewarray java/lang/Object
    //   507: dup_x1
    //   508: swap
    //   509: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   512: bipush #7
    //   514: swap
    //   515: aastore
    //   516: dup_x1
    //   517: swap
    //   518: invokestatic valueOf : (F)Ljava/lang/Float;
    //   521: bipush #6
    //   523: swap
    //   524: aastore
    //   525: dup_x1
    //   526: swap
    //   527: invokestatic valueOf : (F)Ljava/lang/Float;
    //   530: iconst_5
    //   531: swap
    //   532: aastore
    //   533: dup_x2
    //   534: dup_x2
    //   535: pop
    //   536: invokestatic valueOf : (J)Ljava/lang/Long;
    //   539: iconst_4
    //   540: swap
    //   541: aastore
    //   542: dup_x1
    //   543: swap
    //   544: invokestatic valueOf : (F)Ljava/lang/Float;
    //   547: iconst_3
    //   548: swap
    //   549: aastore
    //   550: dup_x1
    //   551: swap
    //   552: iconst_2
    //   553: swap
    //   554: aastore
    //   555: dup_x1
    //   556: swap
    //   557: iconst_1
    //   558: swap
    //   559: aastore
    //   560: dup_x1
    //   561: swap
    //   562: iconst_0
    //   563: swap
    //   564: aastore
    //   565: invokevirtual z : ([Ljava/lang/Object;)V
    //   568: astore #21
    //   570: aload_0
    //   571: getfield L : Lwtf/opal/bu;
    //   574: getstatic wtf/opal/lx.MEDIUM : Lwtf/opal/lx;
    //   577: aload #8
    //   579: aload #23
    //   581: fload_1
    //   582: aload_0
    //   583: getfield L : Lwtf/opal/bu;
    //   586: getstatic wtf/opal/lx.MEDIUM : Lwtf/opal/lx;
    //   589: lload #17
    //   591: aload #23
    //   593: ldc 6.0
    //   595: sipush #18051
    //   598: ldc2_w 3237041375523069527
    //   601: lload #9
    //   603: lxor
    //   604: <illegal opcode> p : (IJ)I
    //   609: iconst_5
    //   610: anewarray java/lang/Object
    //   613: dup_x1
    //   614: swap
    //   615: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   618: iconst_4
    //   619: swap
    //   620: aastore
    //   621: dup_x1
    //   622: swap
    //   623: invokestatic valueOf : (F)Ljava/lang/Float;
    //   626: iconst_3
    //   627: swap
    //   628: aastore
    //   629: dup_x1
    //   630: swap
    //   631: iconst_2
    //   632: swap
    //   633: aastore
    //   634: dup_x2
    //   635: dup_x2
    //   636: pop
    //   637: invokestatic valueOf : (J)Ljava/lang/Long;
    //   640: iconst_1
    //   641: swap
    //   642: aastore
    //   643: dup_x1
    //   644: swap
    //   645: iconst_0
    //   646: swap
    //   647: aastore
    //   648: invokevirtual h : ([Ljava/lang/Object;)F
    //   651: fsub
    //   652: ldc_w 83.5
    //   655: fload #5
    //   657: ldc_w 4.5
    //   660: fmul
    //   661: invokestatic min : (FF)F
    //   664: fadd
    //   665: iload #4
    //   667: i2f
    //   668: fadd
    //   669: ldc_w 12.0
    //   672: fadd
    //   673: fload_2
    //   674: ldc_w 14.5
    //   677: fadd
    //   678: lload #15
    //   680: dup2_x1
    //   681: pop2
    //   682: ldc 6.0
    //   684: iconst_m1
    //   685: bipush #8
    //   687: anewarray java/lang/Object
    //   690: dup_x1
    //   691: swap
    //   692: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   695: bipush #7
    //   697: swap
    //   698: aastore
    //   699: dup_x1
    //   700: swap
    //   701: invokestatic valueOf : (F)Ljava/lang/Float;
    //   704: bipush #6
    //   706: swap
    //   707: aastore
    //   708: dup_x1
    //   709: swap
    //   710: invokestatic valueOf : (F)Ljava/lang/Float;
    //   713: iconst_5
    //   714: swap
    //   715: aastore
    //   716: dup_x2
    //   717: dup_x2
    //   718: pop
    //   719: invokestatic valueOf : (J)Ljava/lang/Long;
    //   722: iconst_4
    //   723: swap
    //   724: aastore
    //   725: dup_x1
    //   726: swap
    //   727: invokestatic valueOf : (F)Ljava/lang/Float;
    //   730: iconst_3
    //   731: swap
    //   732: aastore
    //   733: dup_x1
    //   734: swap
    //   735: iconst_2
    //   736: swap
    //   737: aastore
    //   738: dup_x1
    //   739: swap
    //   740: iconst_1
    //   741: swap
    //   742: aastore
    //   743: dup_x1
    //   744: swap
    //   745: iconst_0
    //   746: swap
    //   747: aastore
    //   748: invokevirtual z : ([Ljava/lang/Object;)V
    //   751: aload_0
    //   752: getfield L : Lwtf/opal/bu;
    //   755: iconst_1
    //   756: putfield t : Z
    //   759: aload #7
    //   761: aload #21
    //   763: ifnull -> 788
    //   766: instanceof net/minecraft/class_742
    //   769: ifeq -> 1354
    //   772: goto -> 779
    //   775: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   778: athrow
    //   779: aload #7
    //   781: goto -> 788
    //   784: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   787: athrow
    //   788: checkcast net/minecraft/class_742
    //   791: astore #24
    //   793: aload #24
    //   795: invokevirtual method_52814 : ()Lnet/minecraft/class_8685;
    //   798: astore #25
    //   800: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   803: invokevirtual method_1531 : ()Lnet/minecraft/class_1060;
    //   806: aload #25
    //   808: invokevirtual comp_1626 : ()Lnet/minecraft/class_2960;
    //   811: invokevirtual method_4619 : (Lnet/minecraft/class_2960;)Lnet/minecraft/class_1044;
    //   814: invokevirtual method_4624 : ()I
    //   817: istore #26
    //   819: aload_0
    //   820: getfield c : Lwtf/opal/pa;
    //   823: iconst_0
    //   824: anewarray java/lang/Object
    //   827: invokevirtual y : ([Ljava/lang/Object;)J
    //   830: lstore #27
    //   832: aconst_null
    //   833: astore #29
    //   835: aload #7
    //   837: getfield field_6235 : I
    //   840: aload #21
    //   842: ifnull -> 867
    //   845: ifle -> 964
    //   848: goto -> 855
    //   851: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   854: athrow
    //   855: aload #7
    //   857: getfield field_6235 : I
    //   860: goto -> 867
    //   863: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   866: athrow
    //   867: i2f
    //   868: aload #7
    //   870: getfield field_6254 : I
    //   873: i2f
    //   874: fdiv
    //   875: fstore #30
    //   877: ldc_w 0.6
    //   880: fstore #31
    //   882: fconst_1
    //   883: fconst_1
    //   884: ldc_w 0.39999998
    //   887: fload #30
    //   889: fmul
    //   890: fadd
    //   891: invokestatic min : (FF)F
    //   894: fstore #32
    //   896: fconst_1
    //   897: fload #30
    //   899: ldc_w 0.6
    //   902: fmul
    //   903: fsub
    //   904: fstore #33
    //   906: fconst_1
    //   907: fload #30
    //   909: ldc_w 0.6
    //   912: fmul
    //   913: fsub
    //   914: fstore #34
    //   916: aload_0
    //   917: getfield c : Lwtf/opal/pa;
    //   920: new java/awt/Color
    //   923: dup
    //   924: fload #32
    //   926: fload #33
    //   928: fload #34
    //   930: invokespecial <init> : (FFF)V
    //   933: invokevirtual getRGB : ()I
    //   936: lload #19
    //   938: iconst_2
    //   939: anewarray java/lang/Object
    //   942: dup_x2
    //   943: dup_x2
    //   944: pop
    //   945: invokestatic valueOf : (J)Ljava/lang/Long;
    //   948: iconst_1
    //   949: swap
    //   950: aastore
    //   951: dup_x1
    //   952: swap
    //   953: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   956: iconst_0
    //   957: swap
    //   958: aastore
    //   959: invokevirtual Y : ([Ljava/lang/Object;)Lorg/lwjgl/nanovg/NVGColor;
    //   962: astore #29
    //   964: lload #27
    //   966: invokestatic nvgBeginPath : (J)V
    //   969: aload_0
    //   970: getfield c : Lwtf/opal/pa;
    //   973: iload #26
    //   975: fload_1
    //   976: fload_2
    //   977: sipush #32137
    //   980: ldc2_w 8366326451809903964
    //   983: lload #9
    //   985: lxor
    //   986: <illegal opcode> p : (IJ)I
    //   991: sipush #4697
    //   994: ldc2_w 6800948148386204298
    //   997: lload #9
    //   999: lxor
    //   1000: <illegal opcode> p : (IJ)I
    //   1005: iconst_3
    //   1006: iconst_0
    //   1007: iconst_1
    //   1008: bipush #8
    //   1010: anewarray java/lang/Object
    //   1013: dup_x1
    //   1014: swap
    //   1015: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1018: bipush #7
    //   1020: swap
    //   1021: aastore
    //   1022: dup_x1
    //   1023: swap
    //   1024: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1027: bipush #6
    //   1029: swap
    //   1030: aastore
    //   1031: dup_x1
    //   1032: swap
    //   1033: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1036: iconst_5
    //   1037: swap
    //   1038: aastore
    //   1039: dup_x1
    //   1040: swap
    //   1041: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1044: iconst_4
    //   1045: swap
    //   1046: aastore
    //   1047: dup_x1
    //   1048: swap
    //   1049: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1052: iconst_3
    //   1053: swap
    //   1054: aastore
    //   1055: dup_x1
    //   1056: swap
    //   1057: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1060: iconst_2
    //   1061: swap
    //   1062: aastore
    //   1063: dup_x1
    //   1064: swap
    //   1065: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1068: iconst_1
    //   1069: swap
    //   1070: aastore
    //   1071: dup_x1
    //   1072: swap
    //   1073: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1076: iconst_0
    //   1077: swap
    //   1078: aastore
    //   1079: invokevirtual v : ([Ljava/lang/Object;)Lorg/lwjgl/nanovg/NVGPaint;
    //   1082: astore #30
    //   1084: aload #29
    //   1086: aload #21
    //   1088: ifnull -> 1295
    //   1091: ifnull -> 1116
    //   1094: goto -> 1101
    //   1097: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1100: athrow
    //   1101: aload #30
    //   1103: aload #29
    //   1105: invokevirtual innerColor : (Lorg/lwjgl/nanovg/NVGColor;)Lorg/lwjgl/nanovg/NVGPaint;
    //   1108: pop
    //   1109: goto -> 1116
    //   1112: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1115: athrow
    //   1116: lload #27
    //   1118: aload #30
    //   1120: invokestatic nvgFillPaint : (JLorg/lwjgl/nanovg/NVGPaint;)V
    //   1123: lload #27
    //   1125: fload_1
    //   1126: ldc_w 3.0
    //   1129: fadd
    //   1130: fload_2
    //   1131: ldc_w 3.0
    //   1134: fadd
    //   1135: ldc_w 24.0
    //   1138: ldc_w 24.0
    //   1141: ldc_w 4.5
    //   1144: invokestatic nvgRoundedRect : (JFFFFF)V
    //   1147: lload #27
    //   1149: invokestatic nvgFill : (J)V
    //   1152: lload #27
    //   1154: invokestatic nvgClosePath : (J)V
    //   1157: lload #27
    //   1159: invokestatic nvgBeginPath : (J)V
    //   1162: aload_0
    //   1163: getfield c : Lwtf/opal/pa;
    //   1166: iload #26
    //   1168: fload_1
    //   1169: ldc_w 96.0
    //   1172: fsub
    //   1173: fload_2
    //   1174: sipush #4697
    //   1177: ldc2_w 6800948148386204298
    //   1180: lload #9
    //   1182: lxor
    //   1183: <illegal opcode> p : (IJ)I
    //   1188: sipush #4697
    //   1191: ldc2_w 6800948148386204298
    //   1194: lload #9
    //   1196: lxor
    //   1197: <illegal opcode> p : (IJ)I
    //   1202: iconst_3
    //   1203: iconst_0
    //   1204: iconst_1
    //   1205: bipush #8
    //   1207: anewarray java/lang/Object
    //   1210: dup_x1
    //   1211: swap
    //   1212: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1215: bipush #7
    //   1217: swap
    //   1218: aastore
    //   1219: dup_x1
    //   1220: swap
    //   1221: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1224: bipush #6
    //   1226: swap
    //   1227: aastore
    //   1228: dup_x1
    //   1229: swap
    //   1230: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1233: iconst_5
    //   1234: swap
    //   1235: aastore
    //   1236: dup_x1
    //   1237: swap
    //   1238: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1241: iconst_4
    //   1242: swap
    //   1243: aastore
    //   1244: dup_x1
    //   1245: swap
    //   1246: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1249: iconst_3
    //   1250: swap
    //   1251: aastore
    //   1252: dup_x1
    //   1253: swap
    //   1254: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1257: iconst_2
    //   1258: swap
    //   1259: aastore
    //   1260: dup_x1
    //   1261: swap
    //   1262: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1265: iconst_1
    //   1266: swap
    //   1267: aastore
    //   1268: dup_x1
    //   1269: swap
    //   1270: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1273: iconst_0
    //   1274: swap
    //   1275: aastore
    //   1276: invokevirtual v : ([Ljava/lang/Object;)Lorg/lwjgl/nanovg/NVGPaint;
    //   1279: astore #30
    //   1281: aload #21
    //   1283: ifnull -> 1349
    //   1286: aload #29
    //   1288: goto -> 1295
    //   1291: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1294: athrow
    //   1295: ifnull -> 1313
    //   1298: aload #30
    //   1300: aload #29
    //   1302: invokevirtual innerColor : (Lorg/lwjgl/nanovg/NVGColor;)Lorg/lwjgl/nanovg/NVGPaint;
    //   1305: pop
    //   1306: goto -> 1313
    //   1309: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1312: athrow
    //   1313: lload #27
    //   1315: aload #30
    //   1317: invokestatic nvgFillPaint : (JLorg/lwjgl/nanovg/NVGPaint;)V
    //   1320: lload #27
    //   1322: fload_1
    //   1323: ldc_w 3.0
    //   1326: fadd
    //   1327: fload_2
    //   1328: ldc_w 3.0
    //   1331: fadd
    //   1332: ldc_w 24.0
    //   1335: ldc_w 24.0
    //   1338: ldc_w 4.5
    //   1341: invokestatic nvgRoundedRect : (JFFFFF)V
    //   1344: lload #27
    //   1346: invokestatic nvgFill : (J)V
    //   1349: lload #27
    //   1351: invokestatic nvgClosePath : (J)V
    //   1354: return
    // Exception table:
    //   from	to	target	type
    //   570	772	775	wtf/opal/x5
    //   766	781	784	wtf/opal/x5
    //   835	848	851	wtf/opal/x5
    //   845	860	863	wtf/opal/x5
    //   1084	1094	1097	wtf/opal/x5
    //   1091	1109	1112	wtf/opal/x5
    //   1281	1288	1291	wtf/opal/x5
    //   1295	1306	1309	wtf/opal/x5
  }
  
  public static void P(String paramString) {
    T = paramString;
  }
  
  public static String s() {
    return T;
  }
  
  static {
    // Byte code:
    //   0: ldc2_w 1573951404509484450
    //   3: ldc2_w -4225067214270709828
    //   6: invokestatic lookup : ()Ljava/lang/invoke/MethodHandles$Lookup;
    //   9: invokevirtual lookupClass : ()Ljava/lang/Class;
    //   12: invokestatic a : (JJLjava/lang/Object;)Lwtf/opal/t5;
    //   15: ldc2_w 67721226830135
    //   18: invokeinterface a : (J)J
    //   23: putstatic wtf/opal/pk.a : J
    //   26: ldc_w 'eUZOTb'
    //   29: new java/util/HashMap
    //   32: dup
    //   33: bipush #13
    //   35: invokespecial <init> : (I)V
    //   38: putstatic wtf/opal/pk.f : Ljava/util/Map;
    //   41: invokestatic P : (Ljava/lang/String;)V
    //   44: getstatic wtf/opal/pk.a : J
    //   47: ldc2_w 93737445011784
    //   50: lxor
    //   51: lstore_0
    //   52: ldc_w 'DES/CBC/NoPadding'
    //   55: invokestatic getInstance : (Ljava/lang/String;)Ljavax/crypto/Cipher;
    //   58: dup
    //   59: astore_2
    //   60: iconst_2
    //   61: ldc_w 'DES'
    //   64: invokestatic getInstance : (Ljava/lang/String;)Ljavax/crypto/SecretKeyFactory;
    //   67: bipush #8
    //   69: newarray byte
    //   71: dup
    //   72: iconst_0
    //   73: lload_0
    //   74: bipush #56
    //   76: lushr
    //   77: l2i
    //   78: i2b
    //   79: bastore
    //   80: iconst_1
    //   81: istore_3
    //   82: iload_3
    //   83: bipush #8
    //   85: if_icmpge -> 108
    //   88: dup
    //   89: iload_3
    //   90: lload_0
    //   91: iload_3
    //   92: bipush #8
    //   94: imul
    //   95: lshl
    //   96: bipush #56
    //   98: lushr
    //   99: l2i
    //   100: i2b
    //   101: bastore
    //   102: iinc #3, 1
    //   105: goto -> 82
    //   108: new javax/crypto/spec/DESKeySpec
    //   111: dup_x1
    //   112: swap
    //   113: invokespecial <init> : ([B)V
    //   116: invokevirtual generateSecret : (Ljava/security/spec/KeySpec;)Ljavax/crypto/SecretKey;
    //   119: new javax/crypto/spec/IvParameterSpec
    //   122: dup
    //   123: bipush #8
    //   125: newarray byte
    //   127: invokespecial <init> : ([B)V
    //   130: invokevirtual init : (ILjava/security/Key;Ljava/security/spec/AlgorithmParameterSpec;)V
    //   133: bipush #8
    //   135: newarray long
    //   137: astore #8
    //   139: iconst_0
    //   140: istore #5
    //   142: ldc_w 'MqÐ\\b7©aI×h\\\tbànºEÌòæ!Ý¢¡§!'CAIÜ(víðóNDR®¸'
    //   145: dup
    //   146: astore #6
    //   148: invokevirtual length : ()I
    //   151: istore #7
    //   153: iconst_0
    //   154: istore #4
    //   156: aload #6
    //   158: iload #4
    //   160: iinc #4, 8
    //   163: iload #4
    //   165: invokevirtual substring : (II)Ljava/lang/String;
    //   168: ldc_w 'ISO-8859-1'
    //   171: invokevirtual getBytes : (Ljava/lang/String;)[B
    //   174: astore #9
    //   176: aload #8
    //   178: iload #5
    //   180: iinc #5, 1
    //   183: aload #9
    //   185: iconst_0
    //   186: baload
    //   187: i2l
    //   188: ldc2_w 255
    //   191: land
    //   192: bipush #56
    //   194: lshl
    //   195: aload #9
    //   197: iconst_1
    //   198: baload
    //   199: i2l
    //   200: ldc2_w 255
    //   203: land
    //   204: bipush #48
    //   206: lshl
    //   207: lor
    //   208: aload #9
    //   210: iconst_2
    //   211: baload
    //   212: i2l
    //   213: ldc2_w 255
    //   216: land
    //   217: bipush #40
    //   219: lshl
    //   220: lor
    //   221: aload #9
    //   223: iconst_3
    //   224: baload
    //   225: i2l
    //   226: ldc2_w 255
    //   229: land
    //   230: bipush #32
    //   232: lshl
    //   233: lor
    //   234: aload #9
    //   236: iconst_4
    //   237: baload
    //   238: i2l
    //   239: ldc2_w 255
    //   242: land
    //   243: bipush #24
    //   245: lshl
    //   246: lor
    //   247: aload #9
    //   249: iconst_5
    //   250: baload
    //   251: i2l
    //   252: ldc2_w 255
    //   255: land
    //   256: bipush #16
    //   258: lshl
    //   259: lor
    //   260: aload #9
    //   262: bipush #6
    //   264: baload
    //   265: i2l
    //   266: ldc2_w 255
    //   269: land
    //   270: bipush #8
    //   272: lshl
    //   273: lor
    //   274: aload #9
    //   276: bipush #7
    //   278: baload
    //   279: i2l
    //   280: ldc2_w 255
    //   283: land
    //   284: lor
    //   285: iconst_m1
    //   286: goto -> 468
    //   289: lastore
    //   290: iload #4
    //   292: iload #7
    //   294: if_icmplt -> 156
    //   297: ldc_w 'Â$NÄ³­ók¼¾P^¿'
    //   300: dup
    //   301: astore #6
    //   303: invokevirtual length : ()I
    //   306: istore #7
    //   308: iconst_0
    //   309: istore #4
    //   311: aload #6
    //   313: iload #4
    //   315: iinc #4, 8
    //   318: iload #4
    //   320: invokevirtual substring : (II)Ljava/lang/String;
    //   323: ldc_w 'ISO-8859-1'
    //   326: invokevirtual getBytes : (Ljava/lang/String;)[B
    //   329: astore #9
    //   331: aload #8
    //   333: iload #5
    //   335: iinc #5, 1
    //   338: aload #9
    //   340: iconst_0
    //   341: baload
    //   342: i2l
    //   343: ldc2_w 255
    //   346: land
    //   347: bipush #56
    //   349: lshl
    //   350: aload #9
    //   352: iconst_1
    //   353: baload
    //   354: i2l
    //   355: ldc2_w 255
    //   358: land
    //   359: bipush #48
    //   361: lshl
    //   362: lor
    //   363: aload #9
    //   365: iconst_2
    //   366: baload
    //   367: i2l
    //   368: ldc2_w 255
    //   371: land
    //   372: bipush #40
    //   374: lshl
    //   375: lor
    //   376: aload #9
    //   378: iconst_3
    //   379: baload
    //   380: i2l
    //   381: ldc2_w 255
    //   384: land
    //   385: bipush #32
    //   387: lshl
    //   388: lor
    //   389: aload #9
    //   391: iconst_4
    //   392: baload
    //   393: i2l
    //   394: ldc2_w 255
    //   397: land
    //   398: bipush #24
    //   400: lshl
    //   401: lor
    //   402: aload #9
    //   404: iconst_5
    //   405: baload
    //   406: i2l
    //   407: ldc2_w 255
    //   410: land
    //   411: bipush #16
    //   413: lshl
    //   414: lor
    //   415: aload #9
    //   417: bipush #6
    //   419: baload
    //   420: i2l
    //   421: ldc2_w 255
    //   424: land
    //   425: bipush #8
    //   427: lshl
    //   428: lor
    //   429: aload #9
    //   431: bipush #7
    //   433: baload
    //   434: i2l
    //   435: ldc2_w 255
    //   438: land
    //   439: lor
    //   440: iconst_0
    //   441: goto -> 468
    //   444: lastore
    //   445: iload #4
    //   447: iload #7
    //   449: if_icmplt -> 311
    //   452: aload #8
    //   454: putstatic wtf/opal/pk.d : [J
    //   457: bipush #8
    //   459: anewarray java/lang/Integer
    //   462: putstatic wtf/opal/pk.e : [Ljava/lang/Integer;
    //   465: goto -> 684
    //   468: dup_x2
    //   469: pop
    //   470: lstore #10
    //   472: bipush #8
    //   474: newarray byte
    //   476: dup
    //   477: iconst_0
    //   478: lload #10
    //   480: bipush #56
    //   482: lushr
    //   483: l2i
    //   484: i2b
    //   485: bastore
    //   486: dup
    //   487: iconst_1
    //   488: lload #10
    //   490: bipush #48
    //   492: lushr
    //   493: l2i
    //   494: i2b
    //   495: bastore
    //   496: dup
    //   497: iconst_2
    //   498: lload #10
    //   500: bipush #40
    //   502: lushr
    //   503: l2i
    //   504: i2b
    //   505: bastore
    //   506: dup
    //   507: iconst_3
    //   508: lload #10
    //   510: bipush #32
    //   512: lushr
    //   513: l2i
    //   514: i2b
    //   515: bastore
    //   516: dup
    //   517: iconst_4
    //   518: lload #10
    //   520: bipush #24
    //   522: lushr
    //   523: l2i
    //   524: i2b
    //   525: bastore
    //   526: dup
    //   527: iconst_5
    //   528: lload #10
    //   530: bipush #16
    //   532: lushr
    //   533: l2i
    //   534: i2b
    //   535: bastore
    //   536: dup
    //   537: bipush #6
    //   539: lload #10
    //   541: bipush #8
    //   543: lushr
    //   544: l2i
    //   545: i2b
    //   546: bastore
    //   547: dup
    //   548: bipush #7
    //   550: lload #10
    //   552: l2i
    //   553: i2b
    //   554: bastore
    //   555: aload_2
    //   556: swap
    //   557: invokevirtual doFinal : ([B)[B
    //   560: astore #12
    //   562: aload #12
    //   564: iconst_0
    //   565: baload
    //   566: i2l
    //   567: ldc2_w 255
    //   570: land
    //   571: bipush #56
    //   573: lshl
    //   574: aload #12
    //   576: iconst_1
    //   577: baload
    //   578: i2l
    //   579: ldc2_w 255
    //   582: land
    //   583: bipush #48
    //   585: lshl
    //   586: lor
    //   587: aload #12
    //   589: iconst_2
    //   590: baload
    //   591: i2l
    //   592: ldc2_w 255
    //   595: land
    //   596: bipush #40
    //   598: lshl
    //   599: lor
    //   600: aload #12
    //   602: iconst_3
    //   603: baload
    //   604: i2l
    //   605: ldc2_w 255
    //   608: land
    //   609: bipush #32
    //   611: lshl
    //   612: lor
    //   613: aload #12
    //   615: iconst_4
    //   616: baload
    //   617: i2l
    //   618: ldc2_w 255
    //   621: land
    //   622: bipush #24
    //   624: lshl
    //   625: lor
    //   626: aload #12
    //   628: iconst_5
    //   629: baload
    //   630: i2l
    //   631: ldc2_w 255
    //   634: land
    //   635: bipush #16
    //   637: lshl
    //   638: lor
    //   639: aload #12
    //   641: bipush #6
    //   643: baload
    //   644: i2l
    //   645: ldc2_w 255
    //   648: land
    //   649: bipush #8
    //   651: lshl
    //   652: lor
    //   653: aload #12
    //   655: bipush #7
    //   657: baload
    //   658: i2l
    //   659: ldc2_w 255
    //   662: land
    //   663: lor
    //   664: dup2_x1
    //   665: pop2
    //   666: tableswitch default -> 289, 0 -> 444
    //   684: return
  }
  
  private static x5 b(x5 paramx5) {
    return paramx5;
  }
  
  private static int a(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x6B75;
    if (e[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l = d[i];
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
        throw new RuntimeException("wtf/opal/pk", exception);
      } 
      int j = (arrayOfByte3[4] & 0xFF) << 24 | (arrayOfByte3[5] & 0xFF) << 16 | (arrayOfByte3[6] & 0xFF) << 8 | arrayOfByte3[7] & 0xFF;
      e[i] = Integer.valueOf(j);
    } 
    return e[i].intValue();
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
    //   66: ldc_w 'wtf/opal/pk'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\pk.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */