package wtf.opal;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.lang.invoke.MethodHandles;

public final class lw {
  private final Multimap<Integer, b0> f = (Multimap<Integer, b0>)HashMultimap.create();
  
  private static boolean W;
  
  private static final long a = on.a(7283686986169667925L, 2587284308151119521L, MethodHandles.lookup().lookupClass()).a(206161812739405L);
  
  public void e(Object[] paramArrayOfObject) {
    int i = ((Integer)paramArrayOfObject[0]).intValue();
    b0 b0 = (b0)paramArrayOfObject[1];
    this.f.put(Integer.valueOf(i), b0);
  }
  
  public void J(Object[] paramArrayOfObject) {
    int i = ((Integer)paramArrayOfObject[0]).intValue();
    long l = ((Long)paramArrayOfObject[1]).longValue();
    l = a ^ l;
    this.f.get(Integer.valueOf(i)).forEach(b0::r);
    boolean bool = x();
    try {
      if (d.D() != null) {
        try {
        
        } catch (x5 x5) {
          throw a(null);
        } 
        H(!bool);
      } 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  public static void H(boolean paramBoolean) {
    W = paramBoolean;
  }
  
  public static boolean M() {
    return W;
  }
  
  public static boolean x() {
    boolean bool = M();
    try {
      if (!bool)
        return true; 
    } catch (x5 x5) {
      throw a(null);
    } 
    return false;
  }
  
  static {
    if (x())
      H(true); 
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\lw.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */