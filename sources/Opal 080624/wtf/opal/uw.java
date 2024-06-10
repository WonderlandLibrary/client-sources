package wtf.opal;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_2172;

public final class uw {
  public static final CommandDispatcher<class_2172> h;
  
  public static final List<x6> E;
  
  private static final long a = on.a(8393560981776782265L, -7759458072932051917L, MethodHandles.lookup().lookupClass()).a(117004116735764L);
  
  private uw(long paramLong, uk paramuk) {
    // Byte code:
    //   0: getstatic wtf/opal/uw.a : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: lload_1
    //   7: dup2
    //   8: ldc2_w 117186665005477
    //   11: lxor
    //   12: lstore #4
    //   14: pop2
    //   15: invokestatic r : ()Z
    //   18: aload_0
    //   19: invokespecial <init> : ()V
    //   22: istore #6
    //   24: aload_3
    //   25: getfield D : Ljava/util/List;
    //   28: invokeinterface iterator : ()Ljava/util/Iterator;
    //   33: astore #7
    //   35: aload #7
    //   37: invokeinterface hasNext : ()Z
    //   42: ifeq -> 111
    //   45: aload #7
    //   47: invokeinterface next : ()Ljava/lang/Object;
    //   52: checkcast wtf/opal/x6
    //   55: astore #8
    //   57: aload #8
    //   59: lload #4
    //   61: iconst_2
    //   62: anewarray java/lang/Object
    //   65: dup_x2
    //   66: dup_x2
    //   67: pop
    //   68: invokestatic valueOf : (J)Ljava/lang/Long;
    //   71: iconst_1
    //   72: swap
    //   73: aastore
    //   74: dup_x1
    //   75: swap
    //   76: iconst_0
    //   77: swap
    //   78: aastore
    //   79: invokestatic P : ([Ljava/lang/Object;)V
    //   82: iload #6
    //   84: lload_1
    //   85: lconst_0
    //   86: lcmp
    //   87: iflt -> 95
    //   90: ifeq -> 127
    //   93: iload #6
    //   95: ifne -> 35
    //   98: lload_1
    //   99: lconst_0
    //   100: lcmp
    //   101: ifle -> 82
    //   104: goto -> 111
    //   107: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   110: athrow
    //   111: getstatic wtf/opal/uw.E : Ljava/util/List;
    //   114: <illegal opcode> apply : ()Ljava/util/function/Function;
    //   119: invokestatic comparing : (Ljava/util/function/Function;)Ljava/util/Comparator;
    //   122: invokeinterface sort : (Ljava/util/Comparator;)V
    //   127: return
    // Exception table:
    //   from	to	target	type
    //   57	98	107	wtf/opal/x5
  }
  
  public static void P(Object[] paramArrayOfObject) {
    x6 x6 = (x6)paramArrayOfObject[0];
    long l1 = ((Long)paramArrayOfObject[1]).longValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x4EBB22748F34L;
    E.removeIf(x6::lambda$add$0);
    new Object[2];
    x6.L(new Object[] { null, Long.valueOf(l2), h });
    E.add(x6);
    boolean bool = x6.r();
    try {
      if (d.D() != null) {
        try {
        
        } catch (x5 x5) {
          throw a(null);
        } 
        x6.b(!bool);
      } 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  public static void c(Object[] paramArrayOfObject) throws CommandSyntaxException {
    String str = (String)paramArrayOfObject[0];
    h.execute(str, b9.c.method_1562().method_2875());
  }
  
  public List p(Object[] paramArrayOfObject) {
    return E;
  }
  
  public static uk M(Object[] paramArrayOfObject) {
    return new uk();
  }
  
  private static boolean lambda$add$0(x6 paramx61, x6 paramx62) {
    return paramx62.o().equals(paramx61.o());
  }
  
  static {
    h = new CommandDispatcher();
    E = new ArrayList<>();
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opa\\uw.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */