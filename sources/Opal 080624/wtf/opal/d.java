package wtf.opal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class d implements b0 {
  @Expose
  @SerializedName("name")
  private final String i;
  
  private final String N;
  
  private final String e;
  
  private final kn K;
  
  @Expose
  @SerializedName("keybind")
  private final k8 h;
  
  @Expose
  @SerializedName("enabled")
  private boolean H;
  
  @Expose
  @SerializedName("hidden")
  private boolean V;
  
  private boolean C;
  
  @Expose
  @SerializedName("properties")
  private final List<k3<?>> S;
  
  private final di c;
  
  private final List<u_<?>> F;
  
  private ky<?> j;
  
  private static d[] O;
  
  private static final long ab = on.a(-5590526788316245913L, -3100967703150236211L, MethodHandles.lookup().lookupClass()).a(277877143272926L);
  
  private static final long fb;
  
  protected d(String paramString1, long paramLong, String paramString2, kn paramkn) {
    boolean bool = u_.Q();
    try {
      this.h = new k8(l1, -1);
      this.S = new ArrayList<>();
      this.c = new di((int)fb, 1.0D, l2, lp.FORWARDS);
      this.F = new ArrayList<>();
      this.i = paramString1;
      this.N = paramString1.toLowerCase().replace(" ", "");
      this.e = paramString2;
      this.K = paramkn;
      if (!bool)
        p(new d[4]); 
    } catch (x5 x5) {
      throw b(null);
    } 
  }
  
  public boolean P(Object[] paramArrayOfObject) {
    return this.V;
  }
  
  public void j(Object[] paramArrayOfObject) {
    boolean bool = ((Boolean)paramArrayOfObject[0]).booleanValue();
    this.V = bool;
  }
  
  public final String m(Object[] paramArrayOfObject) {
    return this.i;
  }
  
  public final String X(Object[] paramArrayOfObject) {
    return this.e;
  }
  
  public final boolean D(Object[] paramArrayOfObject) {
    return this.H;
  }
  
  public final boolean R(Object[] paramArrayOfObject) {
    return this.C;
  }
  
  public final void o(Object... paramVarArgs) {
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
    //   14: checkcast [Lwtf/opal/k3;
    //   17: astore_2
    //   18: pop
    //   19: getstatic wtf/opal/d.ab : J
    //   22: lload_3
    //   23: lxor
    //   24: lstore_3
    //   25: invokestatic w : ()Z
    //   28: aload_2
    //   29: astore #6
    //   31: istore #5
    //   33: aload #6
    //   35: arraylength
    //   36: istore #7
    //   38: iconst_0
    //   39: istore #8
    //   41: iload #8
    //   43: iload #7
    //   45: if_icmpge -> 213
    //   48: aload #6
    //   50: iload #8
    //   52: aaload
    //   53: astore #9
    //   55: iload #5
    //   57: lload_3
    //   58: lconst_0
    //   59: lcmp
    //   60: ifle -> 109
    //   63: ifne -> 101
    //   66: aload #9
    //   68: aload_0
    //   69: getfield j : Lwtf/opal/ky;
    //   72: if_acmpne -> 112
    //   75: goto -> 82
    //   78: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   81: athrow
    //   82: aload_0
    //   83: getfield S : Ljava/util/List;
    //   86: iconst_0
    //   87: aload #9
    //   89: invokeinterface add : (ILjava/lang/Object;)V
    //   94: goto -> 101
    //   97: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   100: athrow
    //   101: lload_3
    //   102: lconst_0
    //   103: lcmp
    //   104: ifle -> 131
    //   107: iload #5
    //   109: ifeq -> 131
    //   112: aload_0
    //   113: getfield S : Ljava/util/List;
    //   116: aload #9
    //   118: invokeinterface add : (Ljava/lang/Object;)Z
    //   123: pop
    //   124: goto -> 131
    //   127: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   130: athrow
    //   131: aload #9
    //   133: iload #5
    //   135: ifne -> 166
    //   138: instanceof wtf/opal/k6
    //   141: lload_3
    //   142: lconst_0
    //   143: lcmp
    //   144: iflt -> 210
    //   147: ifeq -> 205
    //   150: goto -> 157
    //   153: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   156: athrow
    //   157: aload #9
    //   159: goto -> 166
    //   162: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   165: athrow
    //   166: checkcast wtf/opal/k6
    //   169: astore #10
    //   171: iconst_0
    //   172: anewarray java/lang/Object
    //   175: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   178: iconst_0
    //   179: anewarray java/lang/Object
    //   182: invokevirtual s : ([Ljava/lang/Object;)Lwtf/opal/lj;
    //   185: aload_0
    //   186: aload #10
    //   188: iconst_2
    //   189: anewarray java/lang/Object
    //   192: dup_x1
    //   193: swap
    //   194: iconst_1
    //   195: swap
    //   196: aastore
    //   197: dup_x1
    //   198: swap
    //   199: iconst_0
    //   200: swap
    //   201: aastore
    //   202: invokevirtual A : ([Ljava/lang/Object;)V
    //   205: iinc #8, 1
    //   208: iload #5
    //   210: ifeq -> 41
    //   213: return
    // Exception table:
    //   from	to	target	type
    //   55	75	78	wtf/opal/x5
    //   66	94	97	wtf/opal/x5
    //   101	124	127	wtf/opal/x5
    //   131	150	153	wtf/opal/x5
    //   138	159	162	wtf/opal/x5
  }
  
  @SafeVarargs
  public final void Y(Object... paramVarArgs) {
    ky<?> ky1 = (ky)paramVarArgs[0];
    u_[] arrayOfU_ = (u_[])paramVarArgs[1];
    this.j = ky1;
    Collections.addAll(this.F, (u_<?>[])arrayOfU_);
  }
  
  public final void J(Object[] paramArrayOfObject) {
    boolean bool = ((Boolean)paramArrayOfObject[0]).booleanValue();
    this.C = bool;
  }
  
  public final k8 i(Object[] paramArrayOfObject) {
    return this.h;
  }
  
  public final kn X(Object[] paramArrayOfObject) {
    return this.K;
  }
  
  public final List e(Object[] paramArrayOfObject) {
    return this.S;
  }
  
  public final String k() {
    return this.N;
  }
  
  public final di t(Object[] paramArrayOfObject) {
    return this.c;
  }
  
  public final List Z(Object[] paramArrayOfObject) {
    return this.F;
  }
  
  public final ky V(Object[] paramArrayOfObject) {
    return this.j;
  }
  
  public final void a(Object[] paramArrayOfObject) {
    ky<?> ky1 = (ky)paramArrayOfObject[0];
    this.j = ky1;
  }
  
  public final void Q(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Boolean
    //   7: invokevirtual booleanValue : ()Z
    //   10: istore_2
    //   11: dup
    //   12: iconst_1
    //   13: aaload
    //   14: checkcast java/lang/Long
    //   17: invokevirtual longValue : ()J
    //   20: lstore_3
    //   21: pop
    //   22: getstatic wtf/opal/d.ab : J
    //   25: lload_3
    //   26: lxor
    //   27: lstore_3
    //   28: lload_3
    //   29: dup2
    //   30: ldc2_w 115620059708990
    //   33: lxor
    //   34: lstore #5
    //   36: dup2
    //   37: ldc2_w 93404510488748
    //   40: lxor
    //   41: lstore #7
    //   43: pop2
    //   44: invokestatic Q : ()Z
    //   47: aload_0
    //   48: iload_2
    //   49: putfield H : Z
    //   52: istore #9
    //   54: aload_0
    //   55: iload #9
    //   57: ifeq -> 112
    //   60: getfield H : Z
    //   63: ifeq -> 104
    //   66: goto -> 73
    //   69: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   72: athrow
    //   73: aload_0
    //   74: lload #5
    //   76: iconst_1
    //   77: anewarray java/lang/Object
    //   80: dup_x2
    //   81: dup_x2
    //   82: pop
    //   83: invokestatic valueOf : (J)Ljava/lang/Long;
    //   86: iconst_0
    //   87: swap
    //   88: aastore
    //   89: invokevirtual K : ([Ljava/lang/Object;)V
    //   92: iload #9
    //   94: ifne -> 130
    //   97: goto -> 104
    //   100: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   103: athrow
    //   104: aload_0
    //   105: goto -> 112
    //   108: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   111: athrow
    //   112: lload #7
    //   114: iconst_1
    //   115: anewarray java/lang/Object
    //   118: dup_x2
    //   119: dup_x2
    //   120: pop
    //   121: invokestatic valueOf : (J)Ljava/lang/Long;
    //   124: iconst_0
    //   125: swap
    //   126: aastore
    //   127: invokevirtual z : ([Ljava/lang/Object;)V
    //   130: return
    // Exception table:
    //   from	to	target	type
    //   54	66	69	wtf/opal/x5
    //   60	97	100	wtf/opal/x5
    //   73	105	108	wtf/opal/x5
  }
  
  public final void R(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: dup
    //   12: iconst_1
    //   13: aaload
    //   14: checkcast java/lang/Boolean
    //   17: invokevirtual booleanValue : ()Z
    //   20: istore #4
    //   22: pop
    //   23: getstatic wtf/opal/d.ab : J
    //   26: lload_2
    //   27: lxor
    //   28: lstore_2
    //   29: lload_2
    //   30: dup2
    //   31: ldc2_w 122747318922153
    //   34: lxor
    //   35: lstore #5
    //   37: pop2
    //   38: invokestatic Q : ()Z
    //   41: aload_0
    //   42: iload #4
    //   44: putfield H : Z
    //   47: istore #7
    //   49: iload #7
    //   51: ifeq -> 119
    //   54: aload_0
    //   55: getfield H : Z
    //   58: lload_2
    //   59: lconst_0
    //   60: lcmp
    //   61: iflt -> 131
    //   64: ifeq -> 130
    //   67: goto -> 74
    //   70: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   73: athrow
    //   74: iconst_0
    //   75: anewarray java/lang/Object
    //   78: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   81: iconst_0
    //   82: anewarray java/lang/Object
    //   85: invokevirtual q : ([Ljava/lang/Object;)Lwtf/opal/dm;
    //   88: lload #5
    //   90: aload_0
    //   91: iconst_2
    //   92: anewarray java/lang/Object
    //   95: dup_x1
    //   96: swap
    //   97: iconst_1
    //   98: swap
    //   99: aastore
    //   100: dup_x2
    //   101: dup_x2
    //   102: pop
    //   103: invokestatic valueOf : (J)Ljava/lang/Long;
    //   106: iconst_0
    //   107: swap
    //   108: aastore
    //   109: invokevirtual P : ([Ljava/lang/Object;)V
    //   112: goto -> 119
    //   115: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   118: athrow
    //   119: iload #7
    //   121: lload_2
    //   122: lconst_0
    //   123: lcmp
    //   124: iflt -> 131
    //   127: ifne -> 164
    //   130: iconst_0
    //   131: anewarray java/lang/Object
    //   134: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   137: iconst_0
    //   138: anewarray java/lang/Object
    //   141: invokevirtual q : ([Ljava/lang/Object;)Lwtf/opal/dm;
    //   144: aload_0
    //   145: iconst_1
    //   146: anewarray java/lang/Object
    //   149: dup_x1
    //   150: swap
    //   151: iconst_0
    //   152: swap
    //   153: aastore
    //   154: invokevirtual z : ([Ljava/lang/Object;)V
    //   157: goto -> 164
    //   160: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   163: athrow
    //   164: return
    // Exception table:
    //   from	to	target	type
    //   49	67	70	wtf/opal/x5
    //   54	112	115	wtf/opal/x5
    //   119	157	160	wtf/opal/x5
  }
  
  public final void D(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Integer
    //   7: invokevirtual intValue : ()I
    //   10: istore_2
    //   11: dup
    //   12: iconst_1
    //   13: aaload
    //   14: checkcast java/lang/Integer
    //   17: invokevirtual intValue : ()I
    //   20: istore_3
    //   21: dup
    //   22: iconst_2
    //   23: aaload
    //   24: checkcast java/lang/Integer
    //   27: invokevirtual intValue : ()I
    //   30: istore #4
    //   32: pop
    //   33: iload_2
    //   34: i2l
    //   35: bipush #48
    //   37: lshl
    //   38: iload_3
    //   39: i2l
    //   40: bipush #48
    //   42: lshl
    //   43: bipush #16
    //   45: lushr
    //   46: lor
    //   47: iload #4
    //   49: i2l
    //   50: bipush #32
    //   52: lshl
    //   53: bipush #32
    //   55: lushr
    //   56: lor
    //   57: getstatic wtf/opal/d.ab : J
    //   60: lxor
    //   61: lstore #5
    //   63: lload #5
    //   65: dup2
    //   66: ldc2_w 17486868918513
    //   69: lxor
    //   70: lstore #7
    //   72: dup2
    //   73: ldc2_w 55198661822051
    //   76: lxor
    //   77: lstore #9
    //   79: pop2
    //   80: invokestatic w : ()Z
    //   83: istore #11
    //   85: aload_0
    //   86: aload_0
    //   87: getfield H : Z
    //   90: iload #11
    //   92: ifne -> 106
    //   95: ifne -> 109
    //   98: goto -> 105
    //   101: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   104: athrow
    //   105: iconst_1
    //   106: goto -> 110
    //   109: iconst_0
    //   110: putfield H : Z
    //   113: aload_0
    //   114: iload #11
    //   116: ifne -> 171
    //   119: getfield H : Z
    //   122: ifeq -> 163
    //   125: goto -> 132
    //   128: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   131: athrow
    //   132: aload_0
    //   133: lload #7
    //   135: iconst_1
    //   136: anewarray java/lang/Object
    //   139: dup_x2
    //   140: dup_x2
    //   141: pop
    //   142: invokestatic valueOf : (J)Ljava/lang/Long;
    //   145: iconst_0
    //   146: swap
    //   147: aastore
    //   148: invokevirtual K : ([Ljava/lang/Object;)V
    //   151: iload #11
    //   153: ifeq -> 189
    //   156: goto -> 163
    //   159: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   162: athrow
    //   163: aload_0
    //   164: goto -> 171
    //   167: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   170: athrow
    //   171: lload #9
    //   173: iconst_1
    //   174: anewarray java/lang/Object
    //   177: dup_x2
    //   178: dup_x2
    //   179: pop
    //   180: invokestatic valueOf : (J)Ljava/lang/Long;
    //   183: iconst_0
    //   184: swap
    //   185: aastore
    //   186: invokevirtual z : ([Ljava/lang/Object;)V
    //   189: return
    // Exception table:
    //   from	to	target	type
    //   85	98	101	wtf/opal/x5
    //   110	125	128	wtf/opal/x5
    //   119	156	159	wtf/opal/x5
    //   132	164	167	wtf/opal/x5
  }
  
  public final void I(Object[] paramArrayOfObject) {
    long l = ((Long)paramArrayOfObject[0]).longValue();
    l = ab ^ l;
    boolean bool = u_.Q();
    try {
      if (bool)
        if (!this.C) {
        
        } else {
          this.C = false;
        }  
    } catch (x5 x5) {
      throw b(null);
    } 
  }
  
  protected void K(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    long l2 = l1 ^ 0x7F94732A6EB7L;
    long l3 = l1 ^ 0x5FDA8AF970FFL;
    (new Object[2])[1] = this;
    new Object[2];
    d1.q(new Object[0]).q(new Object[0]).P(new Object[] { Long.valueOf(l2) });
    boolean bool = u_.Q();
    try {
      if (bool)
        try {
          if (V(new Object[0]) != null) {
          
          } else {
            return;
          } 
        } catch (x5 x5) {
          throw b(null);
        }  
    } catch (x5 x5) {
      throw b(null);
    } 
    new Object[1];
    V(new Object[0]).s(new Object[] { Long.valueOf(l3) });
  }
  
  protected void z(Object[] paramArrayOfObject) {
    long l = ((Long)paramArrayOfObject[0]).longValue();
    d1.q(new Object[0]).q(new Object[0]).z(new Object[] { this });
    boolean bool = u_.w();
    try {
      if (!bool)
        try {
          if (V(new Object[0]) != null) {
          
          } else {
            return;
          } 
        } catch (x5 x5) {
          throw b(null);
        }  
    } catch (x5 x5) {
      throw b(null);
    } 
    V(new Object[0]).X();
  }
  
  public u_ V(Object[] paramArrayOfObject) {
    return this.F.stream().filter(this::lambda$getActiveMode$0).findFirst().orElse(null);
  }
  
  public String o(Object[] paramArrayOfObject) {
    long l = ((Long)paramArrayOfObject[0]).longValue();
    return null;
  }
  
  public void r() {
    long l = ab ^ 0x49E7482666CBL;
    int i = (int)((l ^ 0x27F0D15240B3L) >>> 48L);
    int j = (int)((l ^ 0x27F0D15240B3L) << 16L >>> 48L);
    int k = (int)((l ^ 0x27F0D15240B3L) << 32L >>> 32L);
    l ^ 0x27F0D15240B3L;
    D(new Object[] { null, null, Integer.valueOf(k), Integer.valueOf((short)j), Integer.valueOf((short)i) });
  }
  
  private boolean lambda$getActiveMode$0(u_ paramu_) {
    return paramu_.V(new Object[0]).equals(this.j.z());
  }
  
  public static void p(d[] paramArrayOfd) {
    O = paramArrayOfd;
  }
  
  public static d[] D() {
    return O;
  }
  
  static {
    long l = ab ^ 0x29DDA862EAB0L;
    p(null);
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b = 1; b < 8; b++)
      (new byte[8])[b] = (byte)(int)(l << b * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/NoPadding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
  }
  
  private static x5 b(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\d.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */