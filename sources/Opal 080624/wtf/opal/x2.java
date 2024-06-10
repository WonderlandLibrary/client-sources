package wtf.opal;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.ImmutableMap;
import java.lang.invoke.MethodHandles;
import java.util.Collection;
import java.util.function.Consumer;

public final class x2 {
  private final ClassToInstanceMap<d> q;
  
  private final ImmutableMap<String, d> a;
  
  private static int[] X;
  
  private static final long b = on.a(-6756811514430946485L, -1032514520028799854L, MethodHandles.lookup().lookupClass()).a(129180138054580L);
  
  private x2(ClassToInstanceMap<d> paramClassToInstanceMap, ImmutableMap<String, d> paramImmutableMap) {
    this.q = paramClassToInstanceMap;
    this.a = paramImmutableMap;
  }
  
  public void X(Object[] paramArrayOfObject) {
    String str = (String)paramArrayOfObject[0];
    Consumer<d> consumer1 = (Consumer)paramArrayOfObject[1];
    long l1 = ((Long)paramArrayOfObject[2]).longValue();
    Consumer<by> consumer = (Consumer)paramArrayOfObject[3];
    l1 = b ^ l1;
    long l2 = l1 ^ 0x67C0BEC7AD37L;
    try {
      (new Object[2])[1] = str;
      new Object[2];
      consumer1.accept(j(new Object[] { Long.valueOf(l2) }));
    } catch (by by) {
      consumer.accept(by);
    } 
  }
  
  public d V(Object[] paramArrayOfObject) {
    Class clazz = (Class)paramArrayOfObject[0];
    return (d)this.q.getInstance(clazz);
  }
  
  public d j(Object[] paramArrayOfObject) throws by {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    String str = (String)paramArrayOfObject[1];
    l1 = b ^ l1;
    long l2 = l1 ^ 0x90E2F10645BL;
    d d = (d)this.a.get(str);
    boolean bool = u_.Q();
    try {
      if (bool)
        try {
          if (d == null)
            throw new by(l2, str); 
        } catch (by by) {
          throw a(null);
        }  
    } catch (by by) {
      throw a(null);
    } 
    return d;
  }
  
  public Collection g(Object[] paramArrayOfObject) {
    return this.q.values();
  }
  
  public static x2 O(Object... paramVarArgs) {
    long l1 = ((Long)paramVarArgs[0]).longValue();
    d[] arrayOfD1 = (d[])paramVarArgs[1];
    l1 = b ^ l1;
    long l2 = l1 ^ 0x319C82234DDEL;
    bj bj = O(new Object[0]);
    d[] arrayOfD2 = arrayOfD1;
    int i = arrayOfD2.length;
    boolean bool = u_.w();
    byte b = 0;
    label19: while (b < i) {
      d d = arrayOfD2[b];
      try {
        while (true) {
          new Object[2];
          if (l1 > 0L) {
            if (!bool) {
              bj.t(new Object[] { null, Long.valueOf(l2), d });
              b++;
              if (bool) {
                if (l1 >= 0L)
                  continue; 
                continue;
              } 
              continue label19;
            } 
          } else {
            break;
          } 
          break;
        } 
      } catch (x5 x5) {
        throw a(null);
      } 
      return bj.t(new Object[] { null, Long.valueOf(l2), d }).X(new Object[0]);
    } 
    break;
  }
  
  public static bj O(Object[] paramArrayOfObject) {
    return new bj();
  }
  
  public static void M(int[] paramArrayOfint) {
    X = paramArrayOfint;
  }
  
  public static int[] q() {
    return X;
  }
  
  static {
    if (q() == null)
      M(new int[2]); 
  }
  
  private static Exception a(Exception paramException) {
    return paramException;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\x2.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */