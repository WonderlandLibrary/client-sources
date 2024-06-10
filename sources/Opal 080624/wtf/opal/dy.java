package wtf.opal;

import java.lang.invoke.MethodHandles;
import java.util.function.Predicate;

public final class dy<T extends k3<?>> {
  private final T W;
  
  private final Predicate<T> b;
  
  private static final long a = on.a(6176867926032542878L, -4424955579097058332L, MethodHandles.lookup().lookupClass()).a(215064352062484L);
  
  public dy(long paramLong, k3 paramk3, Predicate<T> paramPredicate) {
    this.W = (T)paramk3;
    this.b = paramPredicate;
    int[] arrayOfInt = k3.i();
    try {
      if (d.D() != null)
        k3.K(new int[1]); 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  public boolean X() {
    // Byte code:
    //   0: getstatic wtf/opal/dy.a : J
    //   3: ldc2_w 136252351644313
    //   6: lxor
    //   7: lstore_1
    //   8: invokestatic i : ()[I
    //   11: astore_3
    //   12: aload_0
    //   13: getfield b : Ljava/util/function/Predicate;
    //   16: aload_0
    //   17: getfield W : Lwtf/opal/k3;
    //   20: invokeinterface test : (Ljava/lang/Object;)Z
    //   25: aload_3
    //   26: ifnonnull -> 72
    //   29: ifeq -> 90
    //   32: goto -> 39
    //   35: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   38: athrow
    //   39: aload_0
    //   40: getfield W : Lwtf/opal/k3;
    //   43: iconst_0
    //   44: anewarray java/lang/Object
    //   47: invokevirtual Q : ([Ljava/lang/Object;)Ljava/util/List;
    //   50: invokeinterface stream : ()Ljava/util/stream/Stream;
    //   55: <illegal opcode> test : ()Ljava/util/function/Predicate;
    //   60: invokeinterface allMatch : (Ljava/util/function/Predicate;)Z
    //   65: goto -> 72
    //   68: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   71: athrow
    //   72: aload_3
    //   73: ifnonnull -> 87
    //   76: ifeq -> 90
    //   79: goto -> 86
    //   82: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   85: athrow
    //   86: iconst_1
    //   87: goto -> 91
    //   90: iconst_0
    //   91: ireturn
    // Exception table:
    //   from	to	target	type
    //   12	32	35	wtf/opal/x5
    //   29	65	68	wtf/opal/x5
    //   72	79	82	wtf/opal/x5
  }
  
  public k3 W(Object[] paramArrayOfObject) {
    return (k3)this.W;
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\dy.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */