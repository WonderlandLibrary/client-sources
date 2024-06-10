package wtf.opal;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.class_2596;

public final class ue extends u_<o> {
  private final List<class_2596<?>> E = new CopyOnWriteArrayList<>();
  
  private final kr m;
  
  private final gm<lb> f;
  
  private static boolean w;
  
  private static final long a = on.a(-524514025690418720L, -2011868246644524873L, MethodHandles.lookup().lookupClass()).a(174297275697784L);
  
  private static final long b;
  
  public ue(long paramLong, o paramo) {
    super(paramo);
    boolean bool = L();
    try {
      this.m = new kr();
      this.f = this::lambda$new$0;
      if (d.D() != null) {
        try {
        
        } catch (x5 x5) {
          throw b(null);
        } 
        d(!bool);
      } 
    } catch (x5 x5) {
      throw b(null);
    } 
  }
  
  public Enum V(Object[] paramArrayOfObject) {
    return xk.VERUS;
  }
  
  private void lambda$new$0(lb paramlb) {
    // Byte code:
    //   0: getstatic wtf/opal/ue.a : J
    //   3: ldc2_w 116984297074896
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 47261809154964
    //   13: lxor
    //   14: lstore #4
    //   16: pop2
    //   17: invokestatic L : ()Z
    //   20: istore #6
    //   22: iconst_0
    //   23: anewarray java/lang/Object
    //   26: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   29: iconst_0
    //   30: anewarray java/lang/Object
    //   33: invokevirtual l : ([Ljava/lang/Object;)Lwtf/opal/un;
    //   36: iconst_0
    //   37: anewarray java/lang/Object
    //   40: invokevirtual x : ([Ljava/lang/Object;)F
    //   43: fconst_1
    //   44: fcmpl
    //   45: iload #6
    //   47: ifne -> 191
    //   50: ifle -> 163
    //   53: goto -> 60
    //   56: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   59: athrow
    //   60: aload_0
    //   61: getfield m : Lwtf/opal/kr;
    //   64: getstatic wtf/opal/ue.b : J
    //   67: lload #4
    //   69: iconst_1
    //   70: iconst_3
    //   71: anewarray java/lang/Object
    //   74: dup_x1
    //   75: swap
    //   76: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   79: iconst_2
    //   80: swap
    //   81: aastore
    //   82: dup_x2
    //   83: dup_x2
    //   84: pop
    //   85: invokestatic valueOf : (J)Ljava/lang/Long;
    //   88: iconst_1
    //   89: swap
    //   90: aastore
    //   91: dup_x2
    //   92: dup_x2
    //   93: pop
    //   94: invokestatic valueOf : (J)Ljava/lang/Long;
    //   97: iconst_0
    //   98: swap
    //   99: aastore
    //   100: invokevirtual v : ([Ljava/lang/Object;)Z
    //   103: iload #6
    //   105: ifne -> 191
    //   108: goto -> 115
    //   111: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   114: athrow
    //   115: ifne -> 163
    //   118: goto -> 125
    //   121: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   124: athrow
    //   125: aload_1
    //   126: iconst_0
    //   127: anewarray java/lang/Object
    //   130: invokevirtual Z : ([Ljava/lang/Object;)V
    //   133: aload_0
    //   134: getfield E : Ljava/util/List;
    //   137: aload_1
    //   138: iconst_0
    //   139: anewarray java/lang/Object
    //   142: invokevirtual J : ([Ljava/lang/Object;)Lnet/minecraft/class_2596;
    //   145: invokeinterface add : (Ljava/lang/Object;)Z
    //   150: pop
    //   151: iload #6
    //   153: ifeq -> 238
    //   156: goto -> 163
    //   159: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   162: athrow
    //   163: aload_0
    //   164: getfield E : Ljava/util/List;
    //   167: iload #6
    //   169: ifne -> 233
    //   172: goto -> 179
    //   175: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   178: athrow
    //   179: invokeinterface isEmpty : ()Z
    //   184: goto -> 191
    //   187: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   190: athrow
    //   191: ifne -> 238
    //   194: aload_0
    //   195: getfield E : Ljava/util/List;
    //   198: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   201: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   204: invokevirtual method_48296 : ()Lnet/minecraft/class_2535;
    //   207: dup
    //   208: invokestatic requireNonNull : (Ljava/lang/Object;)Ljava/lang/Object;
    //   211: pop
    //   212: <illegal opcode> accept : (Lnet/minecraft/class_2535;)Ljava/util/function/Consumer;
    //   217: invokeinterface forEach : (Ljava/util/function/Consumer;)V
    //   222: aload_0
    //   223: getfield E : Ljava/util/List;
    //   226: goto -> 233
    //   229: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   232: athrow
    //   233: invokeinterface clear : ()V
    //   238: return
    // Exception table:
    //   from	to	target	type
    //   22	53	56	wtf/opal/x5
    //   50	108	111	wtf/opal/x5
    //   60	118	121	wtf/opal/x5
    //   115	156	159	wtf/opal/x5
    //   125	172	175	wtf/opal/x5
    //   163	184	187	wtf/opal/x5
    //   191	226	229	wtf/opal/x5
  }
  
  public static void d(boolean paramBoolean) {
    w = paramBoolean;
  }
  
  public static boolean d() {
    return w;
  }
  
  public static boolean L() {
    boolean bool = d();
    try {
      if (!bool)
        return true; 
    } catch (x5 x5) {
      throw b(null);
    } 
    return false;
  }
  
  static {
    long l = a ^ 0x72D420200023L;
    d(true);
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opa\\ue.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */