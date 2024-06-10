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
import net.minecraft.class_2743;

public final class uz extends u_<h> {
  private int I;
  
  private int N;
  
  private boolean c;
  
  private boolean S;
  
  private class_243 g = new class_243(0.0D, 0.0D, 0.0D);
  
  private final List<class_2596<?>> u;
  
  public final kt A;
  
  private final gm<b6> R;
  
  private final gm<p> D;
  
  private final gm<lb> w;
  
  private final gm<lu> e;
  
  private static int p;
  
  private static final long a = on.a(5237210515159169339L, -8818901022731670101L, MethodHandles.lookup().lookupClass()).a(219449892356617L);
  
  private static final String b;
  
  private static final long[] d;
  
  private static final Integer[] f;
  
  private static final Map h;
  
  public uz(long paramLong, h paramh) {
    super(paramh);
    int i = r();
    try {
      this.u = new ArrayList<>();
      this.A = new kt(b, this, 3.2D, 1.0D, 5.0D, 0.05D, l);
      this.R = this::lambda$new$0;
      this.D = this::lambda$new$1;
      this.w = this::lambda$new$2;
      this.e = this::lambda$new$3;
      if (i != 0)
        d.p(new d[4]); 
    } catch (x5 x5) {
      throw b(null);
    } 
  }
  
  public Enum V(Object[] paramArrayOfObject) {
    return l0.WATCHDOG;
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
    //   22: iconst_0
    //   23: putfield I : I
    //   26: aload_0
    //   27: sipush #855
    //   30: ldc2_w 2177142488976916388
    //   33: lload_2
    //   34: lxor
    //   35: <illegal opcode> e : (IJ)I
    //   40: putfield N : I
    //   43: aload_0
    //   44: iconst_0
    //   45: putfield c : Z
    //   48: aload_0
    //   49: iconst_0
    //   50: putfield S : Z
    //   53: aload_0
    //   54: new net/minecraft/class_243
    //   57: dup
    //   58: dconst_0
    //   59: dconst_0
    //   60: dconst_0
    //   61: invokespecial <init> : (DDD)V
    //   64: putfield g : Lnet/minecraft/class_243;
    //   67: aload_0
    //   68: getfield u : Ljava/util/List;
    //   71: invokeinterface clear : ()V
    //   76: aload_0
    //   77: lload #4
    //   79: iconst_1
    //   80: anewarray java/lang/Object
    //   83: dup_x2
    //   84: dup_x2
    //   85: pop
    //   86: invokestatic valueOf : (J)Ljava/lang/Long;
    //   89: iconst_0
    //   90: swap
    //   91: aastore
    //   92: invokespecial s : ([Ljava/lang/Object;)V
    //   95: return
  }
  
  public void X() {
    Objects.requireNonNull(b9.c.method_1562().method_48296());
    this.u.forEach(b9.c.method_1562().method_48296()::method_10743);
    this.u.clear();
    super.X();
  }
  
  private void lambda$new$3(lu paramlu) {
    long l = a ^ 0x18FAA3D53E93L;
    class_2596 class_2596 = paramlu.g(new Object[0]);
    int i = M();
    try {
      if (i != 0)
        try {
          if (class_2596 instanceof class_2743) {
          
          } else {
            return;
          } 
        } catch (x5 x5) {
          throw b(null);
        }  
    } catch (x5 x5) {
      throw b(null);
    } 
    class_2743 class_2743 = (class_2743)class_2596;
    try {
      if (i != 0)
        try {
          if (class_2743.method_11818() == b9.c.field_1724.method_5628()) {
            this.S = true;
            this.N = 0;
            paramlu.Z(new Object[0]);
          } else {
            return;
          } 
        } catch (x5 x5) {
          throw b(null);
        }  
    } catch (x5 x5) {
      throw b(null);
    } 
    this.g = new class_243(class_2743.method_11815() / 8000.0D, class_2743.method_11816() / 8000.0D, class_2743.method_11819() / 8000.0D);
  }
  
  private void lambda$new$2(lb paramlb) {
    // Byte code:
    //   0: getstatic wtf/opal/uz.a : J
    //   3: ldc2_w 13953133573992
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
    //   30: ifeq -> 286
    //   33: ifeq -> 275
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
    //   58: getfield I : I
    //   61: iload #4
    //   63: ifeq -> 176
    //   66: iconst_4
    //   67: if_icmpge -> 153
    //   70: goto -> 77
    //   73: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   76: athrow
    //   77: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   80: getfield field_1724 : Lnet/minecraft/class_746;
    //   83: invokevirtual method_24828 : ()Z
    //   86: iload #4
    //   88: ifeq -> 286
    //   91: goto -> 98
    //   94: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   97: athrow
    //   98: ifeq -> 275
    //   101: goto -> 108
    //   104: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   107: athrow
    //   108: aload #6
    //   110: iconst_0
    //   111: invokeinterface setOnGround : (Z)V
    //   116: aload #6
    //   118: aload #5
    //   120: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   123: getfield field_1724 : Lnet/minecraft/class_746;
    //   126: invokevirtual method_23318 : ()D
    //   129: invokevirtual method_12268 : (D)D
    //   132: ldc2_w 1.5625E-4
    //   135: dadd
    //   136: invokeinterface setY : (D)V
    //   141: iload #4
    //   143: ifne -> 275
    //   146: goto -> 153
    //   149: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   152: athrow
    //   153: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   156: getfield field_1724 : Lnet/minecraft/class_746;
    //   159: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   162: invokevirtual method_10214 : ()D
    //   165: ldc2_w 0.2
    //   168: dcmpl
    //   169: goto -> 176
    //   172: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   175: athrow
    //   176: iload #4
    //   178: ifeq -> 286
    //   181: ifle -> 275
    //   184: goto -> 191
    //   187: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   190: athrow
    //   191: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   194: getfield field_1724 : Lnet/minecraft/class_746;
    //   197: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   200: invokevirtual method_10214 : ()D
    //   203: ldc2_w 0.25
    //   206: dcmpg
    //   207: iload #4
    //   209: ifeq -> 286
    //   212: goto -> 219
    //   215: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   218: athrow
    //   219: ifge -> 275
    //   222: goto -> 229
    //   225: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   228: athrow
    //   229: aload_0
    //   230: getfield c : Z
    //   233: iload #4
    //   235: ifeq -> 286
    //   238: goto -> 245
    //   241: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   244: athrow
    //   245: ifne -> 275
    //   248: goto -> 255
    //   251: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   254: athrow
    //   255: aload #6
    //   257: iconst_1
    //   258: invokeinterface setOnGround : (Z)V
    //   263: aload_0
    //   264: iconst_1
    //   265: putfield c : Z
    //   268: goto -> 275
    //   271: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   274: athrow
    //   275: aload_1
    //   276: iconst_0
    //   277: anewarray java/lang/Object
    //   280: invokevirtual J : ([Ljava/lang/Object;)Lnet/minecraft/class_2596;
    //   283: instanceof net/minecraft/class_6374
    //   286: iload #4
    //   288: ifeq -> 345
    //   291: ifne -> 334
    //   294: goto -> 301
    //   297: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   300: athrow
    //   301: aload_1
    //   302: iconst_0
    //   303: anewarray java/lang/Object
    //   306: invokevirtual J : ([Ljava/lang/Object;)Lnet/minecraft/class_2596;
    //   309: instanceof net/minecraft/class_2827
    //   312: iload #4
    //   314: ifeq -> 345
    //   317: goto -> 324
    //   320: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   323: athrow
    //   324: ifeq -> 406
    //   327: goto -> 334
    //   330: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   333: athrow
    //   334: aload_0
    //   335: getfield N : I
    //   338: goto -> 345
    //   341: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   344: athrow
    //   345: iload #4
    //   347: ifeq -> 405
    //   350: sipush #11403
    //   353: ldc2_w 2179079940675653877
    //   356: lload_2
    //   357: lxor
    //   358: <illegal opcode> e : (IJ)I
    //   363: if_icmpge -> 406
    //   366: goto -> 373
    //   369: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   372: athrow
    //   373: aload_1
    //   374: iconst_0
    //   375: anewarray java/lang/Object
    //   378: invokevirtual Z : ([Ljava/lang/Object;)V
    //   381: aload_0
    //   382: getfield u : Ljava/util/List;
    //   385: aload_1
    //   386: iconst_0
    //   387: anewarray java/lang/Object
    //   390: invokevirtual J : ([Ljava/lang/Object;)Lnet/minecraft/class_2596;
    //   393: invokeinterface add : (Ljava/lang/Object;)Z
    //   398: goto -> 405
    //   401: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   404: athrow
    //   405: pop
    //   406: return
    // Exception table:
    //   from	to	target	type
    //   23	36	39	wtf/opal/x5
    //   57	70	73	wtf/opal/x5
    //   66	91	94	wtf/opal/x5
    //   77	101	104	wtf/opal/x5
    //   98	146	149	wtf/opal/x5
    //   108	169	172	wtf/opal/x5
    //   176	184	187	wtf/opal/x5
    //   181	212	215	wtf/opal/x5
    //   191	222	225	wtf/opal/x5
    //   219	238	241	wtf/opal/x5
    //   229	248	251	wtf/opal/x5
    //   245	268	271	wtf/opal/x5
    //   286	294	297	wtf/opal/x5
    //   291	317	320	wtf/opal/x5
    //   301	327	330	wtf/opal/x5
    //   324	338	341	wtf/opal/x5
    //   345	366	369	wtf/opal/x5
    //   350	398	401	wtf/opal/x5
  }
  
  private void lambda$new$1(p paramp) {
    long l = a ^ 0xB1D2F7165A8L;
    try {
      if (this.I < 4)
        paramp.q(new Object[] { new class_243(0.0D, paramp.S(new Object[0]).method_10214(), 0.0D) }); 
    } catch (x5 x5) {
      throw b(null);
    } 
  }
  
  private void lambda$new$0(b6 paramb6) {
    // Byte code:
    //   0: getstatic wtf/opal/uz.a : J
    //   3: ldc2_w 41253964755432
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 49480855048577
    //   13: lxor
    //   14: lstore #4
    //   16: dup2
    //   17: ldc2_w 66484722389988
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
    //   50: ldc2_w 61494560331702
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
    //   72: ifeq -> 95
    //   75: ifne -> 86
    //   78: goto -> 85
    //   81: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   84: athrow
    //   85: return
    //   86: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   89: getfield field_1724 : Lnet/minecraft/class_746;
    //   92: invokevirtual method_24828 : ()Z
    //   95: iload #11
    //   97: ifeq -> 241
    //   100: ifeq -> 237
    //   103: goto -> 110
    //   106: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   109: athrow
    //   110: aload_0
    //   111: getfield I : I
    //   114: iload #11
    //   116: ifeq -> 241
    //   119: goto -> 126
    //   122: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   125: athrow
    //   126: iconst_4
    //   127: if_icmpge -> 237
    //   130: goto -> 137
    //   133: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   136: athrow
    //   137: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   140: getfield field_1724 : Lnet/minecraft/class_746;
    //   143: invokevirtual method_6043 : ()V
    //   146: aload_0
    //   147: iload #11
    //   149: ifeq -> 228
    //   152: goto -> 159
    //   155: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   158: athrow
    //   159: getfield I : I
    //   162: iconst_3
    //   163: if_icmpne -> 227
    //   166: goto -> 173
    //   169: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   172: athrow
    //   173: lload #4
    //   175: iconst_1
    //   176: anewarray java/lang/Object
    //   179: dup_x2
    //   180: dup_x2
    //   181: pop
    //   182: invokestatic valueOf : (J)Ljava/lang/Long;
    //   185: iconst_0
    //   186: swap
    //   187: aastore
    //   188: invokestatic m : ([Ljava/lang/Object;)D
    //   191: lload #9
    //   193: dup2_x2
    //   194: pop2
    //   195: iconst_2
    //   196: anewarray java/lang/Object
    //   199: dup_x2
    //   200: dup_x2
    //   201: pop
    //   202: invokestatic valueOf : (D)Ljava/lang/Double;
    //   205: iconst_1
    //   206: swap
    //   207: aastore
    //   208: dup_x2
    //   209: dup_x2
    //   210: pop
    //   211: invokestatic valueOf : (J)Ljava/lang/Long;
    //   214: iconst_0
    //   215: swap
    //   216: aastore
    //   217: invokestatic k : ([Ljava/lang/Object;)V
    //   220: goto -> 227
    //   223: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   226: athrow
    //   227: aload_0
    //   228: dup
    //   229: getfield I : I
    //   232: iconst_1
    //   233: iadd
    //   234: putfield I : I
    //   237: aload_0
    //   238: getfield S : Z
    //   241: iload #11
    //   243: ifeq -> 286
    //   246: ifeq -> 708
    //   249: goto -> 256
    //   252: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   255: athrow
    //   256: aload_0
    //   257: dup
    //   258: getfield N : I
    //   261: iconst_1
    //   262: iadd
    //   263: putfield N : I
    //   266: sipush #20400
    //   269: ldc2_w 1700554264681149775
    //   272: lload_2
    //   273: lxor
    //   274: <illegal opcode> e : (IJ)I
    //   279: goto -> 286
    //   282: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   285: athrow
    //   286: istore #12
    //   288: aload_0
    //   289: getfield N : I
    //   292: sipush #20400
    //   295: ldc2_w 1700554264681149775
    //   298: lload_2
    //   299: lxor
    //   300: <illegal opcode> e : (IJ)I
    //   305: iload #11
    //   307: ifeq -> 412
    //   310: if_icmpne -> 395
    //   313: goto -> 320
    //   316: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   319: athrow
    //   320: aload_0
    //   321: getfield u : Ljava/util/List;
    //   324: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   327: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   330: invokevirtual method_48296 : ()Lnet/minecraft/class_2535;
    //   333: dup
    //   334: invokestatic requireNonNull : (Ljava/lang/Object;)Ljava/lang/Object;
    //   337: pop
    //   338: <illegal opcode> accept : (Lnet/minecraft/class_2535;)Ljava/util/function/Consumer;
    //   343: invokeinterface forEach : (Ljava/util/function/Consumer;)V
    //   348: aload_0
    //   349: getfield u : Ljava/util/List;
    //   352: invokeinterface clear : ()V
    //   357: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   360: getfield field_1724 : Lnet/minecraft/class_746;
    //   363: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   366: getfield field_1724 : Lnet/minecraft/class_746;
    //   369: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   372: getstatic net/minecraft/class_2350$class_2351.field_11052 : Lnet/minecraft/class_2350$class_2351;
    //   375: aload_0
    //   376: getfield g : Lnet/minecraft/class_243;
    //   379: invokevirtual method_10214 : ()D
    //   382: invokevirtual method_38499 : (Lnet/minecraft/class_2350$class_2351;D)Lnet/minecraft/class_243;
    //   385: invokevirtual method_18799 : (Lnet/minecraft/class_243;)V
    //   388: goto -> 395
    //   391: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   394: athrow
    //   395: aload_0
    //   396: getfield N : I
    //   399: sipush #26868
    //   402: ldc2_w 5877932759606907401
    //   405: lload_2
    //   406: lxor
    //   407: <illegal opcode> e : (IJ)I
    //   412: iload #11
    //   414: ifeq -> 490
    //   417: if_icmpne -> 485
    //   420: goto -> 427
    //   423: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   426: athrow
    //   427: iconst_0
    //   428: anewarray java/lang/Object
    //   431: invokestatic E : ([Ljava/lang/Object;)F
    //   434: f2d
    //   435: aload_0
    //   436: getfield A : Lwtf/opal/kt;
    //   439: invokevirtual z : ()Ljava/lang/Object;
    //   442: checkcast java/lang/Double
    //   445: invokevirtual doubleValue : ()D
    //   448: dmul
    //   449: lload #9
    //   451: dup2_x2
    //   452: pop2
    //   453: iconst_2
    //   454: anewarray java/lang/Object
    //   457: dup_x2
    //   458: dup_x2
    //   459: pop
    //   460: invokestatic valueOf : (D)Ljava/lang/Double;
    //   463: iconst_1
    //   464: swap
    //   465: aastore
    //   466: dup_x2
    //   467: dup_x2
    //   468: pop
    //   469: invokestatic valueOf : (J)Ljava/lang/Long;
    //   472: iconst_0
    //   473: swap
    //   474: aastore
    //   475: invokestatic k : ([Ljava/lang/Object;)V
    //   478: goto -> 485
    //   481: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   484: athrow
    //   485: aload_0
    //   486: getfield N : I
    //   489: iconst_2
    //   490: iload #11
    //   492: ifeq -> 594
    //   495: if_icmple -> 577
    //   498: goto -> 505
    //   501: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   504: athrow
    //   505: aload_0
    //   506: getfield N : I
    //   509: sipush #20400
    //   512: ldc2_w 1700554264681149775
    //   515: lload_2
    //   516: lxor
    //   517: <illegal opcode> e : (IJ)I
    //   522: iload #11
    //   524: ifeq -> 594
    //   527: goto -> 534
    //   530: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   533: athrow
    //   534: if_icmpeq -> 577
    //   537: goto -> 544
    //   540: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   543: athrow
    //   544: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   547: getfield field_1724 : Lnet/minecraft/class_746;
    //   550: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   553: getfield field_1724 : Lnet/minecraft/class_746;
    //   556: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   559: dconst_0
    //   560: ldc2_w 0.027
    //   563: dconst_0
    //   564: invokevirtual method_1031 : (DDD)Lnet/minecraft/class_243;
    //   567: invokevirtual method_18799 : (Lnet/minecraft/class_243;)V
    //   570: goto -> 577
    //   573: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   576: athrow
    //   577: aload_0
    //   578: getfield N : I
    //   581: iload #11
    //   583: ifeq -> 625
    //   586: iconst_5
    //   587: goto -> 594
    //   590: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   593: athrow
    //   594: if_icmple -> 708
    //   597: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   600: getfield field_1724 : Lnet/minecraft/class_746;
    //   603: iload #11
    //   605: ifeq -> 641
    //   608: goto -> 615
    //   611: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   614: athrow
    //   615: invokevirtual method_24828 : ()Z
    //   618: goto -> 625
    //   621: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   624: athrow
    //   625: ifeq -> 708
    //   628: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   631: getfield field_1724 : Lnet/minecraft/class_746;
    //   634: goto -> 641
    //   637: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   640: athrow
    //   641: dconst_0
    //   642: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   645: getfield field_1724 : Lnet/minecraft/class_746;
    //   648: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   651: invokevirtual method_10214 : ()D
    //   654: dconst_0
    //   655: invokevirtual method_18800 : (DDD)V
    //   658: aload_0
    //   659: iconst_0
    //   660: anewarray java/lang/Object
    //   663: invokevirtual y : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   666: checkcast wtf/opal/h
    //   669: iload #6
    //   671: i2s
    //   672: iload #7
    //   674: i2s
    //   675: iload #8
    //   677: iconst_3
    //   678: anewarray java/lang/Object
    //   681: dup_x1
    //   682: swap
    //   683: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   686: iconst_2
    //   687: swap
    //   688: aastore
    //   689: dup_x1
    //   690: swap
    //   691: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   694: iconst_1
    //   695: swap
    //   696: aastore
    //   697: dup_x1
    //   698: swap
    //   699: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   702: iconst_0
    //   703: swap
    //   704: aastore
    //   705: invokevirtual D : ([Ljava/lang/Object;)V
    //   708: return
    // Exception table:
    //   from	to	target	type
    //   62	78	81	wtf/opal/x5
    //   95	103	106	wtf/opal/x5
    //   100	119	122	wtf/opal/x5
    //   110	130	133	wtf/opal/x5
    //   126	152	155	wtf/opal/x5
    //   137	166	169	wtf/opal/x5
    //   159	220	223	wtf/opal/x5
    //   241	249	252	wtf/opal/x5
    //   246	279	282	wtf/opal/x5
    //   288	313	316	wtf/opal/x5
    //   310	388	391	wtf/opal/x5
    //   412	420	423	wtf/opal/x5
    //   417	478	481	wtf/opal/x5
    //   490	498	501	wtf/opal/x5
    //   495	527	530	wtf/opal/x5
    //   505	537	540	wtf/opal/x5
    //   534	570	573	wtf/opal/x5
    //   577	587	590	wtf/opal/x5
    //   594	608	611	wtf/opal/x5
    //   597	618	621	wtf/opal/x5
    //   625	634	637	wtf/opal/x5
  }
  
  public static void g(int paramInt) {
    p = paramInt;
  }
  
  public static int r() {
    return p;
  }
  
  public static int M() {
    int i = r();
    try {
      if (i == 0)
        return 82; 
    } catch (x5 x5) {
      throw b(null);
    } 
    return 0;
  }
  
  static {
    long l = a ^ 0x55EFD20E96EAL;
    g(0);
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b = 1; b < 8; b++)
      (new byte[8])[b] = (byte)(int)(l << b * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
  }
  
  private static x5 b(x5 paramx5) {
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
  
  private static int a(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x1A87;
    if (f[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l = d[i];
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
        throw new RuntimeException("wtf/opal/uz", exception);
      } 
      int j = (arrayOfByte3[4] & 0xFF) << 24 | (arrayOfByte3[5] & 0xFF) << 16 | (arrayOfByte3[6] & 0xFF) << 8 | arrayOfByte3[7] & 0xFF;
      f[i] = Integer.valueOf(j);
    } 
    return f[i].intValue();
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
    //   66: ldc_w 'wtf/opal/uz'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opa\\uz.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */