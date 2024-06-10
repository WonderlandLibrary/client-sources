package wtf.opal;

import java.lang.invoke.MethodHandles;

public abstract class dx {
  private final kr s = new kr();
  
  protected int Y;
  
  protected double E;
  
  protected lp b;
  
  private static d[] R;
  
  private static final long a = on.a(568971755430418115L, 2521903503508908564L, MethodHandles.lookup().lookupClass()).a(92584754274151L);
  
  protected dx(int paramInt1, double paramDouble, lp paramlp, byte paramByte, int paramInt2, int paramInt3) {
    d[] arrayOfD = F();
    try {
      this.Y = paramInt1;
      this.E = paramDouble;
      this.b = paramlp;
      if (d.D() != null)
        l(new d[1]); 
    } catch (x5 x5) {
      throw b(null);
    } 
  }
  
  public final double W(Object[] paramArrayOfObject) {
    return 1.0D - this.s.E(new Object[0]) / this.Y * this.E;
  }
  
  public final lp H(Object[] paramArrayOfObject) {
    return this.b;
  }
  
  public final dx p(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    lp lp1 = (lp)paramArrayOfObject[1];
    l1 = a ^ l1;
    long l2 = l1 ^ 0x4BCE73EC1872L;
    long l3 = l1 ^ 0x75763F7EC11BL;
    d[] arrayOfD = F();
    try {
      if (arrayOfD == null)
        try {
          if (this.b != lp1) {
            try {
              new Object[1];
              new Object[1];
              new Object[1];
            } catch (x5 x5) {
              throw b(null);
            } 
            new Object[1];
            this.s.d(new Object[] { Long.valueOf((long)(System.currentTimeMillis() - this.Y * (lp1.b(new Object[] { Long.valueOf(l2) }) ? P(new Object[] { Long.valueOf(l3) }).doubleValue() : (1.0D - P(new Object[] { Long.valueOf(l3) }).doubleValue())))) });
            this.b = lp1;
          } 
        } catch (x5 x5) {
          throw b(null);
        }  
    } catch (x5 x5) {
      throw b(null);
    } 
    return this;
  }
  
  public final void C(Object[] paramArrayOfObject) {
    int i = ((Integer)paramArrayOfObject[0]).intValue();
    this.Y = i;
  }
  
  public final boolean a(Object[] paramArrayOfObject) {
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
    //   14: checkcast wtf/opal/lp
    //   17: astore_2
    //   18: pop
    //   19: getstatic wtf/opal/dx.a : J
    //   22: lload_3
    //   23: lxor
    //   24: lstore_3
    //   25: lload_3
    //   26: dup2
    //   27: ldc2_w 10646849696271
    //   30: lxor
    //   31: lstore #5
    //   33: pop2
    //   34: invokestatic F : ()[Lwtf/opal/d;
    //   37: astore #7
    //   39: aload_0
    //   40: lload #5
    //   42: iconst_1
    //   43: anewarray java/lang/Object
    //   46: dup_x2
    //   47: dup_x2
    //   48: pop
    //   49: invokestatic valueOf : (J)Ljava/lang/Long;
    //   52: iconst_0
    //   53: swap
    //   54: aastore
    //   55: invokevirtual H : ([Ljava/lang/Object;)Z
    //   58: aload #7
    //   60: ifnonnull -> 88
    //   63: ifeq -> 107
    //   66: goto -> 73
    //   69: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   72: athrow
    //   73: aload_0
    //   74: getfield b : Lwtf/opal/lp;
    //   77: aload_2
    //   78: invokevirtual equals : (Ljava/lang/Object;)Z
    //   81: goto -> 88
    //   84: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   87: athrow
    //   88: aload #7
    //   90: ifnonnull -> 104
    //   93: ifeq -> 107
    //   96: goto -> 103
    //   99: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   102: athrow
    //   103: iconst_1
    //   104: goto -> 108
    //   107: iconst_0
    //   108: ireturn
    // Exception table:
    //   from	to	target	type
    //   39	66	69	wtf/opal/x5
    //   63	81	84	wtf/opal/x5
    //   88	96	99	wtf/opal/x5
  }
  
  public final void s(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x39F8D73DA227L;
    long l3 = l1 ^ 0x7920305DFF5EL;
    new Object[1];
    (new Object[2])[1] = this.b.U(new Object[] { Long.valueOf(l2) });
    new Object[2];
    p(new Object[] { Long.valueOf(l3) });
  }
  
  public final void j(Object[] paramArrayOfObject) {
    double d1 = ((Double)paramArrayOfObject[0]).doubleValue();
    this.E = d1;
  }
  
  protected boolean E(Object[] paramArrayOfObject) {
    return false;
  }
  
  public final Double P(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: getstatic wtf/opal/dx.a : J
    //   15: lload_2
    //   16: lxor
    //   17: lstore_2
    //   18: lload_2
    //   19: dup2
    //   20: ldc2_w 17067052980438
    //   23: lxor
    //   24: lstore #4
    //   26: dup2
    //   27: ldc2_w 59412721918433
    //   30: lxor
    //   31: lstore #6
    //   33: dup2
    //   34: ldc2_w 135033420678161
    //   37: lxor
    //   38: lstore #8
    //   40: pop2
    //   41: invokestatic F : ()[Lwtf/opal/d;
    //   44: astore #10
    //   46: aload_0
    //   47: getfield b : Lwtf/opal/lp;
    //   50: lload #4
    //   52: iconst_1
    //   53: anewarray java/lang/Object
    //   56: dup_x2
    //   57: dup_x2
    //   58: pop
    //   59: invokestatic valueOf : (J)Ljava/lang/Long;
    //   62: iconst_0
    //   63: swap
    //   64: aastore
    //   65: invokevirtual b : ([Ljava/lang/Object;)Z
    //   68: aload #10
    //   70: ifnonnull -> 216
    //   73: ifeq -> 197
    //   76: goto -> 83
    //   79: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   82: athrow
    //   83: aload_0
    //   84: aload #10
    //   86: lload_2
    //   87: lconst_0
    //   88: lcmp
    //   89: iflt -> 185
    //   92: ifnonnull -> 143
    //   95: goto -> 102
    //   98: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   101: athrow
    //   102: lload #8
    //   104: iconst_1
    //   105: anewarray java/lang/Object
    //   108: dup_x2
    //   109: dup_x2
    //   110: pop
    //   111: invokestatic valueOf : (J)Ljava/lang/Long;
    //   114: iconst_0
    //   115: swap
    //   116: aastore
    //   117: invokevirtual H : ([Ljava/lang/Object;)Z
    //   120: ifeq -> 142
    //   123: goto -> 130
    //   126: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   129: athrow
    //   130: aload_0
    //   131: getfield E : D
    //   134: invokestatic valueOf : (D)Ljava/lang/Double;
    //   137: areturn
    //   138: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   141: athrow
    //   142: aload_0
    //   143: aload_0
    //   144: getfield s : Lwtf/opal/kr;
    //   147: iconst_0
    //   148: anewarray java/lang/Object
    //   151: invokevirtual E : ([Ljava/lang/Object;)J
    //   154: l2d
    //   155: aload_0
    //   156: getfield Y : I
    //   159: i2d
    //   160: ddiv
    //   161: lload #6
    //   163: iconst_2
    //   164: anewarray java/lang/Object
    //   167: dup_x2
    //   168: dup_x2
    //   169: pop
    //   170: invokestatic valueOf : (J)Ljava/lang/Long;
    //   173: iconst_1
    //   174: swap
    //   175: aastore
    //   176: dup_x2
    //   177: dup_x2
    //   178: pop
    //   179: invokestatic valueOf : (D)Ljava/lang/Double;
    //   182: iconst_0
    //   183: swap
    //   184: aastore
    //   185: invokevirtual s : ([Ljava/lang/Object;)D
    //   188: aload_0
    //   189: getfield E : D
    //   192: dmul
    //   193: invokestatic valueOf : (D)Ljava/lang/Double;
    //   196: areturn
    //   197: aload_0
    //   198: lload #8
    //   200: iconst_1
    //   201: anewarray java/lang/Object
    //   204: dup_x2
    //   205: dup_x2
    //   206: pop
    //   207: invokestatic valueOf : (J)Ljava/lang/Long;
    //   210: iconst_0
    //   211: swap
    //   212: aastore
    //   213: invokevirtual H : ([Ljava/lang/Object;)Z
    //   216: aload #10
    //   218: lload_2
    //   219: lconst_0
    //   220: lcmp
    //   221: ifle -> 256
    //   224: ifnonnull -> 254
    //   227: ifeq -> 246
    //   230: goto -> 237
    //   233: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   236: athrow
    //   237: dconst_0
    //   238: invokestatic valueOf : (D)Ljava/lang/Double;
    //   241: areturn
    //   242: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   245: athrow
    //   246: aload_0
    //   247: iconst_0
    //   248: anewarray java/lang/Object
    //   251: invokevirtual E : ([Ljava/lang/Object;)Z
    //   254: aload #10
    //   256: ifnonnull -> 280
    //   259: ifeq -> 353
    //   262: goto -> 269
    //   265: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   268: athrow
    //   269: aload_0
    //   270: getfield Y : I
    //   273: goto -> 280
    //   276: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   279: athrow
    //   280: i2l
    //   281: lconst_0
    //   282: aload_0
    //   283: getfield Y : I
    //   286: i2l
    //   287: aload_0
    //   288: getfield s : Lwtf/opal/kr;
    //   291: iconst_0
    //   292: anewarray java/lang/Object
    //   295: invokevirtual E : ([Ljava/lang/Object;)J
    //   298: lsub
    //   299: invokestatic max : (JJ)J
    //   302: invokestatic min : (JJ)J
    //   305: l2d
    //   306: dstore #11
    //   308: aload_0
    //   309: dload #11
    //   311: aload_0
    //   312: getfield Y : I
    //   315: i2d
    //   316: ddiv
    //   317: lload #6
    //   319: iconst_2
    //   320: anewarray java/lang/Object
    //   323: dup_x2
    //   324: dup_x2
    //   325: pop
    //   326: invokestatic valueOf : (J)Ljava/lang/Long;
    //   329: iconst_1
    //   330: swap
    //   331: aastore
    //   332: dup_x2
    //   333: dup_x2
    //   334: pop
    //   335: invokestatic valueOf : (D)Ljava/lang/Double;
    //   338: iconst_0
    //   339: swap
    //   340: aastore
    //   341: invokevirtual s : ([Ljava/lang/Object;)D
    //   344: aload_0
    //   345: getfield E : D
    //   348: dmul
    //   349: invokestatic valueOf : (D)Ljava/lang/Double;
    //   352: areturn
    //   353: dconst_1
    //   354: aload_0
    //   355: aload_0
    //   356: getfield s : Lwtf/opal/kr;
    //   359: iconst_0
    //   360: anewarray java/lang/Object
    //   363: invokevirtual E : ([Ljava/lang/Object;)J
    //   366: l2d
    //   367: aload_0
    //   368: getfield Y : I
    //   371: i2d
    //   372: ddiv
    //   373: lload #6
    //   375: iconst_2
    //   376: anewarray java/lang/Object
    //   379: dup_x2
    //   380: dup_x2
    //   381: pop
    //   382: invokestatic valueOf : (J)Ljava/lang/Long;
    //   385: iconst_1
    //   386: swap
    //   387: aastore
    //   388: dup_x2
    //   389: dup_x2
    //   390: pop
    //   391: invokestatic valueOf : (D)Ljava/lang/Double;
    //   394: iconst_0
    //   395: swap
    //   396: aastore
    //   397: invokevirtual s : ([Ljava/lang/Object;)D
    //   400: dsub
    //   401: aload_0
    //   402: getfield E : D
    //   405: dmul
    //   406: invokestatic valueOf : (D)Ljava/lang/Double;
    //   409: areturn
    // Exception table:
    //   from	to	target	type
    //   46	76	79	wtf/opal/x5
    //   73	95	98	wtf/opal/x5
    //   83	123	126	wtf/opal/x5
    //   102	138	138	wtf/opal/x5
    //   216	230	233	wtf/opal/x5
    //   227	242	242	wtf/opal/x5
    //   254	262	265	wtf/opal/x5
    //   259	273	276	wtf/opal/x5
  }
  
  public final long y(Object[] paramArrayOfObject) {
    return this.s.E(new Object[0]);
  }
  
  public final boolean H(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x7E9CBD3CB9E3L;
    (new Object[3])[2] = Boolean.valueOf(false);
    new Object[3];
    (new Object[3])[1] = Long.valueOf(l2);
    new Object[3];
    return this.s.v(new Object[] { Long.valueOf(this.Y) });
  }
  
  public final void t(Object[] paramArrayOfObject) {
    this.s.z(new Object[0]);
  }
  
  protected abstract double s(Object[] paramArrayOfObject);
  
  public static void l(d[] paramArrayOfd) {
    R = paramArrayOfd;
  }
  
  public static d[] F() {
    return R;
  }
  
  static {
    if (F() != null)
      l(new d[4]); 
  }
  
  private static x5 b(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\dx.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */