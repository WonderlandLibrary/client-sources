package wtf.opal;

import java.lang.invoke.MethodHandles;
import org.jetbrains.annotations.NotNull;

public class li<T> implements Comparable<li<?>> {
  private final Object x;
  
  private final gm<T> i;
  
  private final int f;
  
  private static final long a = on.a(-8012256184349287683L, 8388963580087510105L, MethodHandles.lookup().lookupClass()).a(64106955423551L);
  
  public li(Object paramObject, long paramLong, gm<T> paramgm, int paramInt) {
    this.x = paramObject;
    this.i = paramgm;
    int[] arrayOfInt = pl.O();
    try {
      this.f = paramInt;
      if (arrayOfInt == null)
        d.p(new d[3]); 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  public Object i(Object[] paramArrayOfObject) {
    return this.x;
  }
  
  public gm O(Object[] paramArrayOfObject) {
    return this.i;
  }
  
  public int f(Object[] paramArrayOfObject) {
    return this.f;
  }
  
  public int x(long paramLong, @NotNull li paramli) {
    paramLong = a ^ paramLong;
    int i = Integer.compare(this.f, paramli.f(new Object[0]));
    int[] arrayOfInt = pl.O();
    try {
      if (arrayOfInt != null)
        try {
        
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    return (i == 0) ? (System.identityHashCode(paramli) - System.identityHashCode(this)) : i;
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\li.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */