package wtf.opal;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.class_243;
import net.minecraft.class_2596;

public final class u7 extends u_<h> {
  private boolean M;
  
  private boolean J;
  
  private boolean w;
  
  private double E;
  
  private int P;
  
  private final List<class_2596<?>> c = new ArrayList<>();
  
  private final gm<b6> T = this::lambda$new$0;
  
  private final gm<lb> a = this::lambda$new$1;
  
  private final gm<lu> B = this::lambda$new$2;
  
  private final gm<p> W = this::lambda$new$3;
  
  private static final long b;
  
  private static final long[] d;
  
  private static final Integer[] e;
  
  private static final Map f;
  
  public u7(h paramh) {
    super(paramh);
  }
  
  public void s(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: lload_2
    //   13: dup2
    //   14: ldc2_w 0
    //   17: lxor
    //   18: lstore #4
    //   20: pop2
    //   21: aload_0
    //   22: dconst_0
    //   23: putfield E : D
    //   26: aload_0
    //   27: sipush #27024
    //   30: ldc2_w 3060649191146646839
    //   33: lload_2
    //   34: lxor
    //   35: <illegal opcode> k : (IJ)I
    //   40: putfield P : I
    //   43: aload_0
    //   44: aload_0
    //   45: aload_0
    //   46: iconst_0
    //   47: dup_x1
    //   48: putfield w : Z
    //   51: dup_x1
    //   52: putfield J : Z
    //   55: putfield M : Z
    //   58: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   61: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   64: invokevirtual method_48296 : ()Lnet/minecraft/class_2535;
    //   67: new net/minecraft/class_2828$class_2829
    //   70: dup
    //   71: invokestatic random : ()D
    //   74: ldc2_w 200.0
    //   77: dmul
    //   78: invokestatic random : ()D
    //   81: ldc2_w 100.0
    //   84: dmul
    //   85: invokestatic random : ()D
    //   88: ldc2_w 500.0
    //   91: dmul
    //   92: iconst_0
    //   93: invokespecial <init> : (DDDZ)V
    //   96: invokevirtual method_10743 : (Lnet/minecraft/class_2596;)V
    //   99: aload_0
    //   100: lload #4
    //   102: iconst_1
    //   103: anewarray java/lang/Object
    //   106: dup_x2
    //   107: dup_x2
    //   108: pop
    //   109: invokestatic valueOf : (J)Ljava/lang/Long;
    //   112: iconst_0
    //   113: swap
    //   114: aastore
    //   115: invokespecial s : ([Ljava/lang/Object;)V
    //   118: return
  }
  
  public void X() {
    Objects.requireNonNull(b9.c.method_1562().method_48296());
    this.c.forEach(b9.c.method_1562().method_48296()::method_10743);
    this.c.clear();
    d1.q(new Object[0]).l(new Object[0]).U(new Object[] { Float.valueOf(1.0F) });
    super.X();
  }
  
  public Enum V(Object[] paramArrayOfObject) {
    return l0.INSTANT_WATCHDOG;
  }
  
  private void lambda$new$3(p paramp) {
    long l = b ^ 0x5C9ADDEB70FBL;
    try {
      if (!this.M)
        paramp.q(new Object[] { new class_243(0.0D, paramp.S(new Object[0]).method_10214(), 0.0D) }); 
    } catch (x5 x5) {
      throw b(null);
    } 
  }
  
  private void lambda$new$2(lu paramlu) {
    // Byte code:
    //   0: getstatic wtf/opal/u7.b : J
    //   3: ldc2_w 33732839209782
    //   6: lxor
    //   7: lstore_2
    //   8: invokestatic M : ()I
    //   11: aload_1
    //   12: iconst_0
    //   13: anewarray java/lang/Object
    //   16: invokevirtual g : ([Ljava/lang/Object;)Lnet/minecraft/class_2596;
    //   19: astore #6
    //   21: istore #4
    //   23: aload #6
    //   25: instanceof net/minecraft/class_2708
    //   28: iload #4
    //   30: ifeq -> 273
    //   33: ifeq -> 246
    //   36: goto -> 43
    //   39: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   42: athrow
    //   43: aload #6
    //   45: checkcast net/minecraft/class_2708
    //   48: astore #5
    //   50: aload_0
    //   51: getfield M : Z
    //   54: iload #4
    //   56: ifeq -> 273
    //   59: ifne -> 246
    //   62: goto -> 69
    //   65: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   68: athrow
    //   69: aload_1
    //   70: iconst_0
    //   71: anewarray java/lang/Object
    //   74: invokevirtual Z : ([Ljava/lang/Object;)V
    //   77: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   80: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   83: invokevirtual method_48296 : ()Lnet/minecraft/class_2535;
    //   86: new net/minecraft/class_2828$class_2830
    //   89: dup
    //   90: aload #5
    //   92: invokevirtual method_11734 : ()D
    //   95: aload #5
    //   97: invokevirtual method_11735 : ()D
    //   100: aload #5
    //   102: invokevirtual method_11738 : ()D
    //   105: aload #5
    //   107: invokevirtual method_11736 : ()F
    //   110: aload #5
    //   112: invokevirtual method_11739 : ()F
    //   115: iconst_0
    //   116: invokespecial <init> : (DDDFFZ)V
    //   119: invokevirtual method_10743 : (Lnet/minecraft/class_2596;)V
    //   122: ldc_w 0.42
    //   125: fstore #6
    //   127: ldc_w 3.0
    //   130: fstore #7
    //   132: iconst_0
    //   133: istore #8
    //   135: iload #8
    //   137: i2f
    //   138: fload #7
    //   140: fload #6
    //   142: fdiv
    //   143: fcmpg
    //   144: ifge -> 241
    //   147: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   150: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   153: invokevirtual method_48296 : ()Lnet/minecraft/class_2535;
    //   156: new net/minecraft/class_2828$class_2829
    //   159: dup
    //   160: aload #5
    //   162: invokevirtual method_11734 : ()D
    //   165: aload #5
    //   167: invokevirtual method_11735 : ()D
    //   170: fload #6
    //   172: f2d
    //   173: dadd
    //   174: aload #5
    //   176: invokevirtual method_11738 : ()D
    //   179: iconst_0
    //   180: invokespecial <init> : (DDDZ)V
    //   183: invokevirtual method_10743 : (Lnet/minecraft/class_2596;)V
    //   186: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   189: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   192: invokevirtual method_48296 : ()Lnet/minecraft/class_2535;
    //   195: new net/minecraft/class_2828$class_2829
    //   198: dup
    //   199: aload #5
    //   201: invokevirtual method_11734 : ()D
    //   204: aload #5
    //   206: invokevirtual method_11735 : ()D
    //   209: aload #5
    //   211: invokevirtual method_11738 : ()D
    //   214: iconst_0
    //   215: invokespecial <init> : (DDDZ)V
    //   218: invokevirtual method_10743 : (Lnet/minecraft/class_2596;)V
    //   221: iinc #8, 1
    //   224: iload #4
    //   226: ifeq -> 246
    //   229: iload #4
    //   231: ifne -> 135
    //   234: goto -> 241
    //   237: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   240: athrow
    //   241: aload_0
    //   242: iconst_1
    //   243: putfield M : Z
    //   246: aload_1
    //   247: iconst_0
    //   248: anewarray java/lang/Object
    //   251: invokevirtual g : ([Ljava/lang/Object;)Lnet/minecraft/class_2596;
    //   254: astore #6
    //   256: aload #6
    //   258: iload #4
    //   260: ifeq -> 278
    //   263: instanceof net/minecraft/class_2743
    //   266: goto -> 273
    //   269: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   272: athrow
    //   273: ifeq -> 377
    //   276: aload #6
    //   278: checkcast net/minecraft/class_2743
    //   281: astore #5
    //   283: aload #5
    //   285: invokevirtual method_11818 : ()I
    //   288: iload #4
    //   290: ifeq -> 335
    //   293: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   296: getfield field_1724 : Lnet/minecraft/class_746;
    //   299: invokevirtual method_5628 : ()I
    //   302: if_icmpne -> 377
    //   305: goto -> 312
    //   308: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   311: athrow
    //   312: aload_0
    //   313: iload #4
    //   315: ifeq -> 365
    //   318: goto -> 325
    //   321: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   324: athrow
    //   325: getfield w : Z
    //   328: goto -> 335
    //   331: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   334: athrow
    //   335: ifne -> 377
    //   338: aload_0
    //   339: iconst_1
    //   340: putfield w : Z
    //   343: aload_0
    //   344: aload #5
    //   346: invokevirtual method_11816 : ()I
    //   349: i2d
    //   350: ldc2_w 8000.0
    //   353: ddiv
    //   354: putfield E : D
    //   357: aload_0
    //   358: goto -> 365
    //   361: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   364: athrow
    //   365: iconst_0
    //   366: putfield P : I
    //   369: aload_1
    //   370: iconst_0
    //   371: anewarray java/lang/Object
    //   374: invokevirtual Z : ([Ljava/lang/Object;)V
    //   377: return
    // Exception table:
    //   from	to	target	type
    //   23	36	39	wtf/opal/x5
    //   50	62	65	wtf/opal/x5
    //   147	234	237	wtf/opal/x5
    //   256	266	269	wtf/opal/x5
    //   283	305	308	wtf/opal/x5
    //   293	318	321	wtf/opal/x5
    //   312	328	331	wtf/opal/x5
    //   335	358	361	wtf/opal/x5
  }
  
  private void lambda$new$1(lb paramlb) {
    // Byte code:
    //   0: getstatic wtf/opal/u7.b : J
    //   3: ldc2_w 61726470522816
    //   6: lxor
    //   7: lstore_2
    //   8: invokestatic M : ()I
    //   11: aload_1
    //   12: iconst_0
    //   13: anewarray java/lang/Object
    //   16: invokevirtual J : ([Ljava/lang/Object;)Lnet/minecraft/class_2596;
    //   19: astore #6
    //   21: istore #4
    //   23: aload #6
    //   25: instanceof net/minecraft/class_2828
    //   28: iload #4
    //   30: ifeq -> 268
    //   33: ifeq -> 257
    //   36: goto -> 43
    //   39: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   42: athrow
    //   43: aload #6
    //   45: checkcast net/minecraft/class_2828
    //   48: astore #5
    //   50: aload #5
    //   52: checkcast wtf/opal/mixin/PlayerMoveC2SPacketAccessor
    //   55: astore #6
    //   57: aload_0
    //   58: getfield M : Z
    //   61: iload #4
    //   63: ifeq -> 100
    //   66: ifne -> 91
    //   69: goto -> 76
    //   72: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   75: athrow
    //   76: aload_1
    //   77: iconst_0
    //   78: anewarray java/lang/Object
    //   81: invokevirtual Z : ([Ljava/lang/Object;)V
    //   84: goto -> 91
    //   87: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   90: athrow
    //   91: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   94: getfield field_1724 : Lnet/minecraft/class_746;
    //   97: invokevirtual method_24828 : ()Z
    //   100: iload #4
    //   102: ifeq -> 158
    //   105: ifeq -> 135
    //   108: goto -> 115
    //   111: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   114: athrow
    //   115: aload #6
    //   117: iconst_0
    //   118: invokeinterface setOnGround : (Z)V
    //   123: iload #4
    //   125: ifne -> 257
    //   128: goto -> 135
    //   131: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   134: athrow
    //   135: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   138: getfield field_1724 : Lnet/minecraft/class_746;
    //   141: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   144: invokevirtual method_10214 : ()D
    //   147: ldc2_w 0.2
    //   150: dcmpl
    //   151: goto -> 158
    //   154: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   157: athrow
    //   158: iload #4
    //   160: ifeq -> 268
    //   163: ifle -> 257
    //   166: goto -> 173
    //   169: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   172: athrow
    //   173: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   176: getfield field_1724 : Lnet/minecraft/class_746;
    //   179: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   182: invokevirtual method_10214 : ()D
    //   185: ldc2_w 0.25
    //   188: dcmpg
    //   189: iload #4
    //   191: ifeq -> 268
    //   194: goto -> 201
    //   197: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   200: athrow
    //   201: ifge -> 257
    //   204: goto -> 211
    //   207: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   210: athrow
    //   211: aload_0
    //   212: getfield J : Z
    //   215: iload #4
    //   217: ifeq -> 268
    //   220: goto -> 227
    //   223: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   226: athrow
    //   227: ifne -> 257
    //   230: goto -> 237
    //   233: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   236: athrow
    //   237: aload #6
    //   239: iconst_1
    //   240: invokeinterface setOnGround : (Z)V
    //   245: aload_0
    //   246: iconst_1
    //   247: putfield J : Z
    //   250: goto -> 257
    //   253: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   256: athrow
    //   257: aload_1
    //   258: iconst_0
    //   259: anewarray java/lang/Object
    //   262: invokevirtual J : ([Ljava/lang/Object;)Lnet/minecraft/class_2596;
    //   265: instanceof net/minecraft/class_6374
    //   268: iload #4
    //   270: ifeq -> 327
    //   273: ifne -> 316
    //   276: goto -> 283
    //   279: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   282: athrow
    //   283: aload_1
    //   284: iconst_0
    //   285: anewarray java/lang/Object
    //   288: invokevirtual J : ([Ljava/lang/Object;)Lnet/minecraft/class_2596;
    //   291: instanceof net/minecraft/class_2827
    //   294: iload #4
    //   296: ifeq -> 327
    //   299: goto -> 306
    //   302: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   305: athrow
    //   306: ifeq -> 388
    //   309: goto -> 316
    //   312: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   315: athrow
    //   316: aload_0
    //   317: getfield P : I
    //   320: goto -> 327
    //   323: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   326: athrow
    //   327: iload #4
    //   329: ifeq -> 387
    //   332: sipush #12176
    //   335: ldc2_w 5867833421895476677
    //   338: lload_2
    //   339: lxor
    //   340: <illegal opcode> k : (IJ)I
    //   345: if_icmpge -> 388
    //   348: goto -> 355
    //   351: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   354: athrow
    //   355: aload_1
    //   356: iconst_0
    //   357: anewarray java/lang/Object
    //   360: invokevirtual Z : ([Ljava/lang/Object;)V
    //   363: aload_0
    //   364: getfield c : Ljava/util/List;
    //   367: aload_1
    //   368: iconst_0
    //   369: anewarray java/lang/Object
    //   372: invokevirtual J : ([Ljava/lang/Object;)Lnet/minecraft/class_2596;
    //   375: invokeinterface add : (Ljava/lang/Object;)Z
    //   380: goto -> 387
    //   383: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   386: athrow
    //   387: pop
    //   388: return
    // Exception table:
    //   from	to	target	type
    //   23	36	39	wtf/opal/x5
    //   57	69	72	wtf/opal/x5
    //   66	84	87	wtf/opal/x5
    //   100	108	111	wtf/opal/x5
    //   105	128	131	wtf/opal/x5
    //   115	151	154	wtf/opal/x5
    //   158	166	169	wtf/opal/x5
    //   163	194	197	wtf/opal/x5
    //   173	204	207	wtf/opal/x5
    //   201	220	223	wtf/opal/x5
    //   211	230	233	wtf/opal/x5
    //   227	250	253	wtf/opal/x5
    //   268	276	279	wtf/opal/x5
    //   273	299	302	wtf/opal/x5
    //   283	309	312	wtf/opal/x5
    //   306	320	323	wtf/opal/x5
    //   327	348	351	wtf/opal/x5
    //   332	380	383	wtf/opal/x5
  }
  
  private void lambda$new$0(b6 paramb6) {
    // Byte code:
    //   0: getstatic wtf/opal/u7.b : J
    //   3: ldc2_w 64556910975141
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 13291779606043
    //   13: lxor
    //   14: lstore #4
    //   16: dup2
    //   17: ldc2_w 32305624532094
    //   20: lxor
    //   21: dup2
    //   22: bipush #48
    //   24: lushr
    //   25: l2i
    //   26: istore #6
    //   28: dup2
    //   29: bipush #16
    //   31: lshl
    //   32: bipush #48
    //   34: lushr
    //   35: l2i
    //   36: istore #7
    //   38: dup2
    //   39: bipush #32
    //   41: lshl
    //   42: bipush #32
    //   44: lushr
    //   45: l2i
    //   46: istore #8
    //   48: pop2
    //   49: dup2
    //   50: ldc2_w 25270554198060
    //   53: lxor
    //   54: lstore #9
    //   56: pop2
    //   57: invokestatic M : ()I
    //   60: istore #11
    //   62: aload_1
    //   63: iconst_0
    //   64: anewarray java/lang/Object
    //   67: invokevirtual W : ([Ljava/lang/Object;)Z
    //   70: iload #11
    //   72: ifeq -> 96
    //   75: ifeq -> 111
    //   78: goto -> 85
    //   81: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   84: athrow
    //   85: aload_0
    //   86: getfield M : Z
    //   89: goto -> 96
    //   92: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   95: athrow
    //   96: iload #11
    //   98: ifeq -> 121
    //   101: ifne -> 112
    //   104: goto -> 111
    //   107: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   110: athrow
    //   111: return
    //   112: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   115: getfield field_1724 : Lnet/minecraft/class_746;
    //   118: invokevirtual method_24828 : ()Z
    //   121: iload #11
    //   123: ifeq -> 229
    //   126: ifeq -> 225
    //   129: goto -> 136
    //   132: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   135: athrow
    //   136: aload_0
    //   137: getfield J : Z
    //   140: iload #11
    //   142: ifeq -> 229
    //   145: goto -> 152
    //   148: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   151: athrow
    //   152: ifne -> 225
    //   155: goto -> 162
    //   158: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   161: athrow
    //   162: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   165: getfield field_1724 : Lnet/minecraft/class_746;
    //   168: invokevirtual method_6043 : ()V
    //   171: lload #4
    //   173: iconst_1
    //   174: anewarray java/lang/Object
    //   177: dup_x2
    //   178: dup_x2
    //   179: pop
    //   180: invokestatic valueOf : (J)Ljava/lang/Long;
    //   183: iconst_0
    //   184: swap
    //   185: aastore
    //   186: invokestatic m : ([Ljava/lang/Object;)D
    //   189: lload #9
    //   191: dup2_x2
    //   192: pop2
    //   193: iconst_2
    //   194: anewarray java/lang/Object
    //   197: dup_x2
    //   198: dup_x2
    //   199: pop
    //   200: invokestatic valueOf : (D)Ljava/lang/Double;
    //   203: iconst_1
    //   204: swap
    //   205: aastore
    //   206: dup_x2
    //   207: dup_x2
    //   208: pop
    //   209: invokestatic valueOf : (J)Ljava/lang/Long;
    //   212: iconst_0
    //   213: swap
    //   214: aastore
    //   215: invokestatic k : ([Ljava/lang/Object;)V
    //   218: goto -> 225
    //   221: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   224: athrow
    //   225: aload_0
    //   226: getfield w : Z
    //   229: iload #11
    //   231: ifeq -> 265
    //   234: ifeq -> 585
    //   237: goto -> 244
    //   240: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   243: athrow
    //   244: aload_0
    //   245: dup
    //   246: getfield P : I
    //   249: iconst_1
    //   250: iadd
    //   251: putfield P : I
    //   254: aload_0
    //   255: getfield P : I
    //   258: goto -> 265
    //   261: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   264: athrow
    //   265: sipush #14818
    //   268: ldc2_w 5253531592949460179
    //   271: lload_2
    //   272: lxor
    //   273: <illegal opcode> k : (IJ)I
    //   278: iload #11
    //   280: ifeq -> 382
    //   283: if_icmpne -> 365
    //   286: goto -> 293
    //   289: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   292: athrow
    //   293: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   296: getfield field_1724 : Lnet/minecraft/class_746;
    //   299: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   302: getfield field_1724 : Lnet/minecraft/class_746;
    //   305: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   308: getstatic net/minecraft/class_2350$class_2351.field_11052 : Lnet/minecraft/class_2350$class_2351;
    //   311: aload_0
    //   312: getfield E : D
    //   315: invokevirtual method_38499 : (Lnet/minecraft/class_2350$class_2351;D)Lnet/minecraft/class_243;
    //   318: invokevirtual method_18799 : (Lnet/minecraft/class_243;)V
    //   321: aload_0
    //   322: getfield c : Ljava/util/List;
    //   325: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   328: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   331: invokevirtual method_48296 : ()Lnet/minecraft/class_2535;
    //   334: dup
    //   335: invokestatic requireNonNull : (Ljava/lang/Object;)Ljava/lang/Object;
    //   338: pop
    //   339: <illegal opcode> accept : (Lnet/minecraft/class_2535;)Ljava/util/function/Consumer;
    //   344: invokeinterface forEach : (Ljava/util/function/Consumer;)V
    //   349: aload_0
    //   350: getfield c : Ljava/util/List;
    //   353: invokeinterface clear : ()V
    //   358: goto -> 365
    //   361: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   364: athrow
    //   365: aload_0
    //   366: getfield P : I
    //   369: sipush #12176
    //   372: ldc2_w 5867831862849239712
    //   375: lload_2
    //   376: lxor
    //   377: <illegal opcode> k : (IJ)I
    //   382: iload #11
    //   384: ifeq -> 482
    //   387: if_icmple -> 453
    //   390: goto -> 397
    //   393: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   396: athrow
    //   397: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   400: getfield field_1724 : Lnet/minecraft/class_746;
    //   403: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   406: getfield field_1724 : Lnet/minecraft/class_746;
    //   409: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   412: invokevirtual method_10216 : ()D
    //   415: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   418: getfield field_1724 : Lnet/minecraft/class_746;
    //   421: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   424: invokevirtual method_10214 : ()D
    //   427: ldc2_w 0.028
    //   430: dadd
    //   431: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   434: getfield field_1724 : Lnet/minecraft/class_746;
    //   437: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   440: invokevirtual method_10215 : ()D
    //   443: invokevirtual method_18800 : (DDD)V
    //   446: goto -> 453
    //   449: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   452: athrow
    //   453: aload_0
    //   454: getfield P : I
    //   457: iload #11
    //   459: ifeq -> 513
    //   462: sipush #12176
    //   465: ldc2_w 5867831862849239712
    //   468: lload_2
    //   469: lxor
    //   470: <illegal opcode> k : (IJ)I
    //   475: goto -> 482
    //   478: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   481: athrow
    //   482: if_icmple -> 585
    //   485: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   488: getfield field_1724 : Lnet/minecraft/class_746;
    //   491: iload #11
    //   493: ifeq -> 529
    //   496: goto -> 503
    //   499: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   502: athrow
    //   503: invokevirtual method_24828 : ()Z
    //   506: goto -> 513
    //   509: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   512: athrow
    //   513: ifeq -> 585
    //   516: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   519: getfield field_1724 : Lnet/minecraft/class_746;
    //   522: goto -> 529
    //   525: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   528: athrow
    //   529: dconst_0
    //   530: dconst_0
    //   531: dconst_0
    //   532: invokevirtual method_18800 : (DDD)V
    //   535: aload_0
    //   536: iconst_0
    //   537: anewarray java/lang/Object
    //   540: invokevirtual y : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   543: checkcast wtf/opal/h
    //   546: iload #6
    //   548: i2s
    //   549: iload #7
    //   551: i2s
    //   552: iload #8
    //   554: iconst_3
    //   555: anewarray java/lang/Object
    //   558: dup_x1
    //   559: swap
    //   560: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   563: iconst_2
    //   564: swap
    //   565: aastore
    //   566: dup_x1
    //   567: swap
    //   568: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   571: iconst_1
    //   572: swap
    //   573: aastore
    //   574: dup_x1
    //   575: swap
    //   576: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   579: iconst_0
    //   580: swap
    //   581: aastore
    //   582: invokevirtual D : ([Ljava/lang/Object;)V
    //   585: return
    // Exception table:
    //   from	to	target	type
    //   62	78	81	wtf/opal/x5
    //   75	89	92	wtf/opal/x5
    //   96	104	107	wtf/opal/x5
    //   121	129	132	wtf/opal/x5
    //   126	145	148	wtf/opal/x5
    //   136	155	158	wtf/opal/x5
    //   152	218	221	wtf/opal/x5
    //   229	237	240	wtf/opal/x5
    //   234	258	261	wtf/opal/x5
    //   265	286	289	wtf/opal/x5
    //   283	358	361	wtf/opal/x5
    //   382	390	393	wtf/opal/x5
    //   387	446	449	wtf/opal/x5
    //   453	475	478	wtf/opal/x5
    //   482	496	499	wtf/opal/x5
    //   485	506	509	wtf/opal/x5
    //   513	522	525	wtf/opal/x5
  }
  
  static {
    // Byte code:
    //   0: ldc2_w -2283112466473788051
    //   3: ldc2_w -1071484299566920366
    //   6: invokestatic lookup : ()Ljava/lang/invoke/MethodHandles$Lookup;
    //   9: invokevirtual lookupClass : ()Ljava/lang/Class;
    //   12: invokestatic a : (JJLjava/lang/Object;)Lwtf/opal/t5;
    //   15: ldc2_w 101762440194047
    //   18: invokeinterface a : (J)J
    //   23: putstatic wtf/opal/u7.b : J
    //   26: new java/util/HashMap
    //   29: dup
    //   30: bipush #13
    //   32: invokespecial <init> : (I)V
    //   35: putstatic wtf/opal/u7.f : Ljava/util/Map;
    //   38: getstatic wtf/opal/u7.b : J
    //   41: ldc2_w 94309972406566
    //   44: lxor
    //   45: lstore_0
    //   46: ldc_w 'DES/CBC/NoPadding'
    //   49: invokestatic getInstance : (Ljava/lang/String;)Ljavax/crypto/Cipher;
    //   52: dup
    //   53: astore_2
    //   54: iconst_2
    //   55: ldc_w 'DES'
    //   58: invokestatic getInstance : (Ljava/lang/String;)Ljavax/crypto/SecretKeyFactory;
    //   61: bipush #8
    //   63: newarray byte
    //   65: dup
    //   66: iconst_0
    //   67: lload_0
    //   68: bipush #56
    //   70: lushr
    //   71: l2i
    //   72: i2b
    //   73: bastore
    //   74: iconst_1
    //   75: istore_3
    //   76: iload_3
    //   77: bipush #8
    //   79: if_icmpge -> 102
    //   82: dup
    //   83: iload_3
    //   84: lload_0
    //   85: iload_3
    //   86: bipush #8
    //   88: imul
    //   89: lshl
    //   90: bipush #56
    //   92: lushr
    //   93: l2i
    //   94: i2b
    //   95: bastore
    //   96: iinc #3, 1
    //   99: goto -> 76
    //   102: new javax/crypto/spec/DESKeySpec
    //   105: dup_x1
    //   106: swap
    //   107: invokespecial <init> : ([B)V
    //   110: invokevirtual generateSecret : (Ljava/security/spec/KeySpec;)Ljavax/crypto/SecretKey;
    //   113: new javax/crypto/spec/IvParameterSpec
    //   116: dup
    //   117: bipush #8
    //   119: newarray byte
    //   121: invokespecial <init> : ([B)V
    //   124: invokevirtual init : (ILjava/security/Key;Ljava/security/spec/AlgorithmParameterSpec;)V
    //   127: iconst_3
    //   128: newarray long
    //   130: astore #8
    //   132: iconst_0
    //   133: istore #5
    //   135: ldc_w 'W1²vn,"±îÂÄÁOËÉ|b'
    //   138: dup
    //   139: astore #6
    //   141: invokevirtual length : ()I
    //   144: istore #7
    //   146: iconst_0
    //   147: istore #4
    //   149: aload #6
    //   151: iload #4
    //   153: iinc #4, 8
    //   156: iload #4
    //   158: invokevirtual substring : (II)Ljava/lang/String;
    //   161: ldc_w 'ISO-8859-1'
    //   164: invokevirtual getBytes : (Ljava/lang/String;)[B
    //   167: astore #9
    //   169: aload #8
    //   171: iload #5
    //   173: iinc #5, 1
    //   176: aload #9
    //   178: iconst_0
    //   179: baload
    //   180: i2l
    //   181: ldc2_w 255
    //   184: land
    //   185: bipush #56
    //   187: lshl
    //   188: aload #9
    //   190: iconst_1
    //   191: baload
    //   192: i2l
    //   193: ldc2_w 255
    //   196: land
    //   197: bipush #48
    //   199: lshl
    //   200: lor
    //   201: aload #9
    //   203: iconst_2
    //   204: baload
    //   205: i2l
    //   206: ldc2_w 255
    //   209: land
    //   210: bipush #40
    //   212: lshl
    //   213: lor
    //   214: aload #9
    //   216: iconst_3
    //   217: baload
    //   218: i2l
    //   219: ldc2_w 255
    //   222: land
    //   223: bipush #32
    //   225: lshl
    //   226: lor
    //   227: aload #9
    //   229: iconst_4
    //   230: baload
    //   231: i2l
    //   232: ldc2_w 255
    //   235: land
    //   236: bipush #24
    //   238: lshl
    //   239: lor
    //   240: aload #9
    //   242: iconst_5
    //   243: baload
    //   244: i2l
    //   245: ldc2_w 255
    //   248: land
    //   249: bipush #16
    //   251: lshl
    //   252: lor
    //   253: aload #9
    //   255: bipush #6
    //   257: baload
    //   258: i2l
    //   259: ldc2_w 255
    //   262: land
    //   263: bipush #8
    //   265: lshl
    //   266: lor
    //   267: aload #9
    //   269: bipush #7
    //   271: baload
    //   272: i2l
    //   273: ldc2_w 255
    //   276: land
    //   277: lor
    //   278: iconst_m1
    //   279: goto -> 305
    //   282: lastore
    //   283: iload #4
    //   285: iload #7
    //   287: if_icmplt -> 149
    //   290: aload #8
    //   292: putstatic wtf/opal/u7.d : [J
    //   295: iconst_3
    //   296: anewarray java/lang/Integer
    //   299: putstatic wtf/opal/u7.e : [Ljava/lang/Integer;
    //   302: goto -> 507
    //   305: dup_x2
    //   306: pop
    //   307: lstore #10
    //   309: bipush #8
    //   311: newarray byte
    //   313: dup
    //   314: iconst_0
    //   315: lload #10
    //   317: bipush #56
    //   319: lushr
    //   320: l2i
    //   321: i2b
    //   322: bastore
    //   323: dup
    //   324: iconst_1
    //   325: lload #10
    //   327: bipush #48
    //   329: lushr
    //   330: l2i
    //   331: i2b
    //   332: bastore
    //   333: dup
    //   334: iconst_2
    //   335: lload #10
    //   337: bipush #40
    //   339: lushr
    //   340: l2i
    //   341: i2b
    //   342: bastore
    //   343: dup
    //   344: iconst_3
    //   345: lload #10
    //   347: bipush #32
    //   349: lushr
    //   350: l2i
    //   351: i2b
    //   352: bastore
    //   353: dup
    //   354: iconst_4
    //   355: lload #10
    //   357: bipush #24
    //   359: lushr
    //   360: l2i
    //   361: i2b
    //   362: bastore
    //   363: dup
    //   364: iconst_5
    //   365: lload #10
    //   367: bipush #16
    //   369: lushr
    //   370: l2i
    //   371: i2b
    //   372: bastore
    //   373: dup
    //   374: bipush #6
    //   376: lload #10
    //   378: bipush #8
    //   380: lushr
    //   381: l2i
    //   382: i2b
    //   383: bastore
    //   384: dup
    //   385: bipush #7
    //   387: lload #10
    //   389: l2i
    //   390: i2b
    //   391: bastore
    //   392: aload_2
    //   393: swap
    //   394: invokevirtual doFinal : ([B)[B
    //   397: astore #12
    //   399: aload #12
    //   401: iconst_0
    //   402: baload
    //   403: i2l
    //   404: ldc2_w 255
    //   407: land
    //   408: bipush #56
    //   410: lshl
    //   411: aload #12
    //   413: iconst_1
    //   414: baload
    //   415: i2l
    //   416: ldc2_w 255
    //   419: land
    //   420: bipush #48
    //   422: lshl
    //   423: lor
    //   424: aload #12
    //   426: iconst_2
    //   427: baload
    //   428: i2l
    //   429: ldc2_w 255
    //   432: land
    //   433: bipush #40
    //   435: lshl
    //   436: lor
    //   437: aload #12
    //   439: iconst_3
    //   440: baload
    //   441: i2l
    //   442: ldc2_w 255
    //   445: land
    //   446: bipush #32
    //   448: lshl
    //   449: lor
    //   450: aload #12
    //   452: iconst_4
    //   453: baload
    //   454: i2l
    //   455: ldc2_w 255
    //   458: land
    //   459: bipush #24
    //   461: lshl
    //   462: lor
    //   463: aload #12
    //   465: iconst_5
    //   466: baload
    //   467: i2l
    //   468: ldc2_w 255
    //   471: land
    //   472: bipush #16
    //   474: lshl
    //   475: lor
    //   476: aload #12
    //   478: bipush #6
    //   480: baload
    //   481: i2l
    //   482: ldc2_w 255
    //   485: land
    //   486: bipush #8
    //   488: lshl
    //   489: lor
    //   490: aload #12
    //   492: bipush #7
    //   494: baload
    //   495: i2l
    //   496: ldc2_w 255
    //   499: land
    //   500: lor
    //   501: dup2_x1
    //   502: pop2
    //   503: pop
    //   504: goto -> 282
    //   507: return
  }
  
  private static x5 b(x5 paramx5) {
    return paramx5;
  }
  
  private static int a(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x62D3;
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
        throw new RuntimeException("wtf/opal/u7", exception);
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
    //   66: ldc_w 'wtf/opal/u7'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opa\\u7.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */