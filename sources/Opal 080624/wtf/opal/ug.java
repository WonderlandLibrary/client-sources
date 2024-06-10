package wtf.opal;

import java.lang.invoke.MethodHandles;
import java.util.Deque;
import java.util.LinkedList;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.class_2596;

public final class ug extends u_<o> {
  private final kt J;
  
  private final kr s;
  
  private final kr U;
  
  private final Deque<class_2596<?>> H;
  
  private boolean T;
  
  private final gm<lb> z;
  
  private final gm<lu> e;
  
  private final gm<b6> m;
  
  private static final long a = on.a(-7103103029743772745L, -4352104863339654782L, MethodHandles.lookup().lookupClass()).a(59986369993328L);
  
  private static final String b;
  
  private static final long c;
  
  public ug(o paramo, long paramLong) {
    super(paramo);
    this.J = new kt(b, this, 400.0D, 100.0D, 500.0D, 50.0D, l);
    this.s = new kr();
    d[] arrayOfD = u8.e();
    try {
      this.U = new kr();
      this.H = new LinkedList<>();
      this.z = this::lambda$new$0;
      this.e = this::lambda$new$1;
      this.m = this::lambda$new$2;
      if (arrayOfD == null)
        d.p(new d[4]); 
    } catch (x5 x5) {
      throw b(null);
    } 
  }
  
  public boolean d(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x2B5903C3D712L;
    (new Object[3])[2] = Boolean.valueOf(false);
    new Object[3];
    (new Object[3])[1] = Long.valueOf(l2);
    new Object[3];
    return this.U.v(new Object[] { Long.valueOf(c) });
  }
  
  public Enum V(Object[] paramArrayOfObject) {
    return xk.WATCHDOG_BLINK_INV;
  }
  
  private void lambda$new$2(b6 paramb6) {
    // Byte code:
    //   0: getstatic wtf/opal/ug.a : J
    //   3: ldc2_w 63115062298625
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 44738194308772
    //   13: lxor
    //   14: lstore #4
    //   16: pop2
    //   17: invokestatic e : ()[Lwtf/opal/d;
    //   20: iconst_0
    //   21: anewarray java/lang/Object
    //   24: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   27: iconst_0
    //   28: anewarray java/lang/Object
    //   31: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   34: ldc wtf/opal/q
    //   36: iconst_1
    //   37: anewarray java/lang/Object
    //   40: dup_x1
    //   41: swap
    //   42: iconst_0
    //   43: swap
    //   44: aastore
    //   45: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   48: checkcast wtf/opal/q
    //   51: astore #7
    //   53: iconst_0
    //   54: anewarray java/lang/Object
    //   57: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   60: iconst_0
    //   61: anewarray java/lang/Object
    //   64: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   67: ldc wtf/opal/xw
    //   69: iconst_1
    //   70: anewarray java/lang/Object
    //   73: dup_x1
    //   74: swap
    //   75: iconst_0
    //   76: swap
    //   77: aastore
    //   78: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   81: checkcast wtf/opal/xw
    //   84: astore #8
    //   86: astore #6
    //   88: aload #7
    //   90: iconst_0
    //   91: anewarray java/lang/Object
    //   94: invokevirtual D : ([Ljava/lang/Object;)Z
    //   97: aload #6
    //   99: ifnull -> 147
    //   102: ifeq -> 131
    //   105: goto -> 112
    //   108: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   111: athrow
    //   112: aload #7
    //   114: iconst_0
    //   115: anewarray java/lang/Object
    //   118: invokevirtual U : ([Ljava/lang/Object;)Lnet/minecraft/class_1309;
    //   121: ifnonnull -> 162
    //   124: goto -> 131
    //   127: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   130: athrow
    //   131: aload #8
    //   133: iconst_0
    //   134: anewarray java/lang/Object
    //   137: invokevirtual D : ([Ljava/lang/Object;)Z
    //   140: goto -> 147
    //   143: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   146: athrow
    //   147: aload #6
    //   149: ifnull -> 184
    //   152: ifeq -> 180
    //   155: goto -> 162
    //   158: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   161: athrow
    //   162: aload_0
    //   163: getfield U : Lwtf/opal/kr;
    //   166: iconst_0
    //   167: anewarray java/lang/Object
    //   170: invokevirtual z : ([Ljava/lang/Object;)V
    //   173: goto -> 180
    //   176: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   179: athrow
    //   180: aload_0
    //   181: getfield T : Z
    //   184: aload #6
    //   186: ifnull -> 253
    //   189: ifne -> 200
    //   192: goto -> 199
    //   195: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   198: athrow
    //   199: return
    //   200: aload_0
    //   201: getfield s : Lwtf/opal/kr;
    //   204: aload_0
    //   205: getfield J : Lwtf/opal/kt;
    //   208: invokevirtual z : ()Ljava/lang/Object;
    //   211: checkcast java/lang/Double
    //   214: invokevirtual longValue : ()J
    //   217: lload #4
    //   219: iconst_1
    //   220: iconst_3
    //   221: anewarray java/lang/Object
    //   224: dup_x1
    //   225: swap
    //   226: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   229: iconst_2
    //   230: swap
    //   231: aastore
    //   232: dup_x2
    //   233: dup_x2
    //   234: pop
    //   235: invokestatic valueOf : (J)Ljava/lang/Long;
    //   238: iconst_1
    //   239: swap
    //   240: aastore
    //   241: dup_x2
    //   242: dup_x2
    //   243: pop
    //   244: invokestatic valueOf : (J)Ljava/lang/Long;
    //   247: iconst_0
    //   248: swap
    //   249: aastore
    //   250: invokevirtual v : ([Ljava/lang/Object;)Z
    //   253: aload #6
    //   255: ifnull -> 284
    //   258: ifeq -> 340
    //   261: goto -> 268
    //   264: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   267: athrow
    //   268: aload_0
    //   269: getfield H : Ljava/util/Deque;
    //   272: invokeinterface isEmpty : ()Z
    //   277: goto -> 284
    //   280: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   283: athrow
    //   284: ifne -> 335
    //   287: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   290: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   293: invokevirtual method_48296 : ()Lnet/minecraft/class_2535;
    //   296: aload_0
    //   297: getfield H : Ljava/util/Deque;
    //   300: invokeinterface poll : ()Ljava/lang/Object;
    //   305: checkcast net/minecraft/class_2596
    //   308: invokevirtual method_10743 : (Lnet/minecraft/class_2596;)V
    //   311: aload #6
    //   313: ifnull -> 340
    //   316: goto -> 323
    //   319: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   322: athrow
    //   323: aload #6
    //   325: ifnonnull -> 268
    //   328: goto -> 335
    //   331: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   334: athrow
    //   335: aload_0
    //   336: iconst_0
    //   337: putfield T : Z
    //   340: return
    // Exception table:
    //   from	to	target	type
    //   88	105	108	wtf/opal/x5
    //   102	124	127	wtf/opal/x5
    //   112	140	143	wtf/opal/x5
    //   147	155	158	wtf/opal/x5
    //   152	173	176	wtf/opal/x5
    //   184	192	195	wtf/opal/x5
    //   253	261	264	wtf/opal/x5
    //   258	277	280	wtf/opal/x5
    //   284	316	319	wtf/opal/x5
    //   287	328	331	wtf/opal/x5
  }
  
  private void lambda$new$1(lu paramlu) {
    long l = a ^ 0x1B4C8999D9CEL;
    try {
      if (paramlu.g(new Object[0]) instanceof net.minecraft.class_2708)
        this.U.z(new Object[0]); 
    } catch (x5 x5) {
      throw b(null);
    } 
  }
  
  private void lambda$new$0(lb paramlb) {
    // Byte code:
    //   0: getstatic wtf/opal/ug.a : J
    //   3: ldc2_w 101770869658996
    //   6: lxor
    //   7: lstore_2
    //   8: invokestatic e : ()[Lwtf/opal/d;
    //   11: astore #4
    //   13: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   16: getfield field_1724 : Lnet/minecraft/class_746;
    //   19: aload #4
    //   21: ifnull -> 47
    //   24: ifnull -> 79
    //   27: goto -> 34
    //   30: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   33: athrow
    //   34: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   37: getfield field_1724 : Lnet/minecraft/class_746;
    //   40: goto -> 47
    //   43: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   46: athrow
    //   47: getfield field_6012 : I
    //   50: aload #4
    //   52: ifnull -> 90
    //   55: ifne -> 79
    //   58: goto -> 65
    //   61: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   64: athrow
    //   65: aload_0
    //   66: getfield H : Ljava/util/Deque;
    //   69: invokeinterface clear : ()V
    //   74: return
    //   75: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   78: athrow
    //   79: aload_1
    //   80: iconst_0
    //   81: anewarray java/lang/Object
    //   84: invokevirtual J : ([Ljava/lang/Object;)Lnet/minecraft/class_2596;
    //   87: instanceof net/minecraft/class_2813
    //   90: aload #4
    //   92: ifnull -> 151
    //   95: ifeq -> 133
    //   98: goto -> 105
    //   101: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   104: athrow
    //   105: aload_0
    //   106: iconst_1
    //   107: putfield T : Z
    //   110: aload_0
    //   111: getfield s : Lwtf/opal/kr;
    //   114: iconst_0
    //   115: anewarray java/lang/Object
    //   118: invokevirtual z : ([Ljava/lang/Object;)V
    //   121: aload #4
    //   123: ifnonnull -> 325
    //   126: goto -> 133
    //   129: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   132: athrow
    //   133: aload_1
    //   134: iconst_0
    //   135: anewarray java/lang/Object
    //   138: invokevirtual J : ([Ljava/lang/Object;)Lnet/minecraft/class_2596;
    //   141: instanceof net/minecraft/class_2828
    //   144: goto -> 151
    //   147: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   150: athrow
    //   151: aload #4
    //   153: ifnull -> 248
    //   156: ifeq -> 230
    //   159: goto -> 166
    //   162: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   165: athrow
    //   166: aload_0
    //   167: getfield T : Z
    //   170: aload #4
    //   172: ifnull -> 248
    //   175: goto -> 182
    //   178: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   181: athrow
    //   182: ifeq -> 230
    //   185: goto -> 192
    //   188: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   191: athrow
    //   192: aload_0
    //   193: getfield H : Ljava/util/Deque;
    //   196: aload_1
    //   197: iconst_0
    //   198: anewarray java/lang/Object
    //   201: invokevirtual J : ([Ljava/lang/Object;)Lnet/minecraft/class_2596;
    //   204: invokeinterface add : (Ljava/lang/Object;)Z
    //   209: pop
    //   210: aload_1
    //   211: iconst_0
    //   212: anewarray java/lang/Object
    //   215: invokevirtual Z : ([Ljava/lang/Object;)V
    //   218: aload #4
    //   220: ifnonnull -> 325
    //   223: goto -> 230
    //   226: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   229: athrow
    //   230: aload_1
    //   231: iconst_0
    //   232: anewarray java/lang/Object
    //   235: invokevirtual J : ([Ljava/lang/Object;)Lnet/minecraft/class_2596;
    //   238: instanceof net/minecraft/class_2824
    //   241: goto -> 248
    //   244: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   247: athrow
    //   248: aload #4
    //   250: ifnull -> 304
    //   253: ifeq -> 286
    //   256: goto -> 263
    //   259: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   262: athrow
    //   263: aload_0
    //   264: getfield U : Lwtf/opal/kr;
    //   267: iconst_0
    //   268: anewarray java/lang/Object
    //   271: invokevirtual z : ([Ljava/lang/Object;)V
    //   274: aload #4
    //   276: ifnonnull -> 325
    //   279: goto -> 286
    //   282: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   285: athrow
    //   286: aload_1
    //   287: iconst_0
    //   288: anewarray java/lang/Object
    //   291: invokevirtual J : ([Ljava/lang/Object;)Lnet/minecraft/class_2596;
    //   294: instanceof net/minecraft/class_2885
    //   297: goto -> 304
    //   300: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   303: athrow
    //   304: ifeq -> 325
    //   307: aload_0
    //   308: getfield U : Lwtf/opal/kr;
    //   311: iconst_0
    //   312: anewarray java/lang/Object
    //   315: invokevirtual z : ([Ljava/lang/Object;)V
    //   318: goto -> 325
    //   321: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   324: athrow
    //   325: return
    // Exception table:
    //   from	to	target	type
    //   13	27	30	wtf/opal/x5
    //   24	40	43	wtf/opal/x5
    //   47	58	61	wtf/opal/x5
    //   55	75	75	wtf/opal/x5
    //   90	98	101	wtf/opal/x5
    //   95	126	129	wtf/opal/x5
    //   105	144	147	wtf/opal/x5
    //   151	159	162	wtf/opal/x5
    //   156	175	178	wtf/opal/x5
    //   166	185	188	wtf/opal/x5
    //   182	223	226	wtf/opal/x5
    //   192	241	244	wtf/opal/x5
    //   248	256	259	wtf/opal/x5
    //   253	279	282	wtf/opal/x5
    //   263	297	300	wtf/opal/x5
    //   304	318	321	wtf/opal/x5
  }
  
  static {
    long l = a ^ 0x3AEC5167E31EL;
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
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opa\\ug.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */