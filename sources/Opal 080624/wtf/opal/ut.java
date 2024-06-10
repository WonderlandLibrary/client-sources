package wtf.opal;

import java.lang.invoke.MethodHandles;
import net.minecraft.class_259;

public final class ut extends u_<s> {
  private boolean Z;
  
  private double s;
  
  private final gm<lz> r = this::lambda$new$0;
  
  private final gm<bw> S = this::lambda$new$1;
  
  private static String y;
  
  private static final long a = on.a(-3786260235875419368L, -7940161499022552051L, MethodHandles.lookup().lookupClass()).a(255208223223281L);
  
  public ut(s params) {
    super(params);
  }
  
  public void s(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    long l2 = l1 ^ 0x0L;
    this.s = (int)b9.c.field_1724.method_23318();
    this.Z = false;
    new Object[1];
    super.s(new Object[] { Long.valueOf(l2) });
  }
  
  public Enum V(Object[] paramArrayOfObject) {
    return kz.VERUS;
  }
  
  private void lambda$new$1(bw parambw) {
    long l = a ^ 0x55EE194663D4L;
    String str = R();
    try {
      if (str != null)
        try {
          if (parambw.x(new Object[0]).method_10264() <= this.s) {
          
          } else {
            return;
          } 
        } catch (x5 x5) {
          throw b(null);
        }  
    } catch (x5 x5) {
      throw b(null);
    } 
    parambw.I(new Object[] { class_259.method_1077() });
  }
  
  private void lambda$new$0(lz paramlz) {
    // Byte code:
    //   0: getstatic wtf/opal/ut.a : J
    //   3: ldc2_w 69159237275845
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 72560475048518
    //   13: lxor
    //   14: lstore #4
    //   16: dup2
    //   17: ldc2_w 123772375917797
    //   20: lxor
    //   21: lstore #6
    //   23: dup2
    //   24: ldc2_w 100138050800753
    //   27: lxor
    //   28: lstore #8
    //   30: pop2
    //   31: invokestatic R : ()Ljava/lang/String;
    //   34: astore #10
    //   36: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   39: getfield field_1724 : Lnet/minecraft/class_746;
    //   42: invokevirtual method_24828 : ()Z
    //   45: aload #10
    //   47: ifnull -> 179
    //   50: ifeq -> 163
    //   53: goto -> 60
    //   56: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   59: athrow
    //   60: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   63: getfield field_1724 : Lnet/minecraft/class_746;
    //   66: invokevirtual method_6043 : ()V
    //   69: lload #6
    //   71: iconst_1
    //   72: anewarray java/lang/Object
    //   75: dup_x2
    //   76: dup_x2
    //   77: pop
    //   78: invokestatic valueOf : (J)Ljava/lang/Long;
    //   81: iconst_0
    //   82: swap
    //   83: aastore
    //   84: invokestatic I : ([Ljava/lang/Object;)Z
    //   87: aload #10
    //   89: ifnull -> 179
    //   92: goto -> 99
    //   95: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   98: athrow
    //   99: ifeq -> 163
    //   102: goto -> 109
    //   105: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   108: athrow
    //   109: lload #4
    //   111: iconst_1
    //   112: anewarray java/lang/Object
    //   115: dup_x2
    //   116: dup_x2
    //   117: pop
    //   118: invokestatic valueOf : (J)Ljava/lang/Long;
    //   121: iconst_0
    //   122: swap
    //   123: aastore
    //   124: invokestatic m : ([Ljava/lang/Object;)D
    //   127: lload #8
    //   129: dup2_x2
    //   130: pop2
    //   131: iconst_2
    //   132: anewarray java/lang/Object
    //   135: dup_x2
    //   136: dup_x2
    //   137: pop
    //   138: invokestatic valueOf : (D)Ljava/lang/Double;
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
    //   153: invokestatic k : ([Ljava/lang/Object;)V
    //   156: goto -> 163
    //   159: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   162: athrow
    //   163: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   166: getfield field_1724 : Lnet/minecraft/class_746;
    //   169: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   172: invokevirtual method_10214 : ()D
    //   175: ldc2_w 0.33319999363422365
    //   178: dcmpl
    //   179: aload #10
    //   181: ifnull -> 280
    //   184: ifne -> 276
    //   187: goto -> 194
    //   190: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   193: athrow
    //   194: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   197: getfield field_1724 : Lnet/minecraft/class_746;
    //   200: aload #10
    //   202: ifnull -> 255
    //   205: goto -> 212
    //   208: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   211: athrow
    //   212: getfield field_5976 : Z
    //   215: ifeq -> 242
    //   218: goto -> 225
    //   221: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   224: athrow
    //   225: aload_0
    //   226: iconst_1
    //   227: putfield Z : Z
    //   230: aload #10
    //   232: ifnonnull -> 276
    //   235: goto -> 242
    //   238: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   241: athrow
    //   242: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   245: getfield field_1724 : Lnet/minecraft/class_746;
    //   248: goto -> 255
    //   251: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   254: athrow
    //   255: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   258: getfield field_1724 : Lnet/minecraft/class_746;
    //   261: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   264: getstatic net/minecraft/class_2350$class_2351.field_11052 : Lnet/minecraft/class_2350$class_2351;
    //   267: ldc2_w -0.0784000015258789
    //   270: invokevirtual method_38499 : (Lnet/minecraft/class_2350$class_2351;D)Lnet/minecraft/class_243;
    //   273: invokevirtual method_18799 : (Lnet/minecraft/class_243;)V
    //   276: aload_0
    //   277: getfield Z : Z
    //   280: aload #10
    //   282: ifnull -> 311
    //   285: ifeq -> 425
    //   288: goto -> 295
    //   291: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   294: athrow
    //   295: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   298: getfield field_1724 : Lnet/minecraft/class_746;
    //   301: invokevirtual method_24828 : ()Z
    //   304: goto -> 311
    //   307: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   310: athrow
    //   311: aload #10
    //   313: ifnull -> 345
    //   316: ifne -> 425
    //   319: goto -> 326
    //   322: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   325: athrow
    //   326: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   329: getfield field_1690 : Lnet/minecraft/class_315;
    //   332: getfield field_1903 : Lnet/minecraft/class_304;
    //   335: invokevirtual method_1434 : ()Z
    //   338: goto -> 345
    //   341: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   344: athrow
    //   345: aload #10
    //   347: ifnull -> 395
    //   350: ifeq -> 425
    //   353: goto -> 360
    //   356: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   359: athrow
    //   360: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   363: getfield field_1724 : Lnet/minecraft/class_746;
    //   366: invokevirtual method_23318 : ()D
    //   369: aload #10
    //   371: ifnull -> 433
    //   374: goto -> 381
    //   377: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   380: athrow
    //   381: aload_0
    //   382: getfield s : D
    //   385: dconst_1
    //   386: dadd
    //   387: dcmpl
    //   388: goto -> 395
    //   391: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   394: athrow
    //   395: ifle -> 425
    //   398: aload_0
    //   399: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   402: getfield field_1724 : Lnet/minecraft/class_746;
    //   405: invokevirtual method_23318 : ()D
    //   408: d2i
    //   409: i2d
    //   410: putfield s : D
    //   413: aload_0
    //   414: iconst_0
    //   415: putfield Z : Z
    //   418: goto -> 425
    //   421: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   424: athrow
    //   425: iconst_0
    //   426: anewarray java/lang/Object
    //   429: invokestatic U : ([Ljava/lang/Object;)F
    //   432: f2d
    //   433: lload #8
    //   435: dup2_x2
    //   436: pop2
    //   437: iconst_2
    //   438: anewarray java/lang/Object
    //   441: dup_x2
    //   442: dup_x2
    //   443: pop
    //   444: invokestatic valueOf : (D)Ljava/lang/Double;
    //   447: iconst_1
    //   448: swap
    //   449: aastore
    //   450: dup_x2
    //   451: dup_x2
    //   452: pop
    //   453: invokestatic valueOf : (J)Ljava/lang/Long;
    //   456: iconst_0
    //   457: swap
    //   458: aastore
    //   459: invokestatic k : ([Ljava/lang/Object;)V
    //   462: return
    // Exception table:
    //   from	to	target	type
    //   36	53	56	wtf/opal/x5
    //   50	92	95	wtf/opal/x5
    //   60	102	105	wtf/opal/x5
    //   99	156	159	wtf/opal/x5
    //   179	187	190	wtf/opal/x5
    //   184	205	208	wtf/opal/x5
    //   194	218	221	wtf/opal/x5
    //   212	235	238	wtf/opal/x5
    //   225	248	251	wtf/opal/x5
    //   280	288	291	wtf/opal/x5
    //   285	304	307	wtf/opal/x5
    //   311	319	322	wtf/opal/x5
    //   316	338	341	wtf/opal/x5
    //   345	353	356	wtf/opal/x5
    //   350	374	377	wtf/opal/x5
    //   360	388	391	wtf/opal/x5
    //   395	418	421	wtf/opal/x5
  }
  
  public static void B(String paramString) {
    y = paramString;
  }
  
  public static String R() {
    return y;
  }
  
  static {
    if (R() == null)
      B("Slb2s"); 
  }
  
  private static x5 b(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opa\\ut.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */