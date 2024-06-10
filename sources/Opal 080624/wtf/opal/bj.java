package wtf.opal;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.ImmutableClassToInstanceMap;
import com.google.common.collect.ImmutableMap;
import java.lang.invoke.MethodHandles;

public class bj {
  private final ImmutableClassToInstanceMap.Builder<d> B = ImmutableClassToInstanceMap.builder();
  
  private final ImmutableMap.Builder<String, d> g = ImmutableMap.builder();
  
  private static final long a = on.a(2858923106185114967L, 4612269073195816388L, MethodHandles.lookup().lookupClass()).a(120413029010579L);
  
  public bj t(Object[] paramArrayOfObject) {
    d d = (d)paramArrayOfObject[0];
    long l = ((Long)paramArrayOfObject[1]).longValue();
    l = a ^ l;
    this.B.put(d.getClass(), d);
    boolean bool = u_.w();
    try {
      this.g.put(d.k(), d);
      if (d.D() != null) {
        try {
        
        } catch (x5 x5) {
          throw a(null);
        } 
        u_.K(!bool);
      } 
    } catch (x5 x5) {
      throw a(null);
    } 
    return this;
  }
  
  public x2 X(Object[] paramArrayOfObject) {
    return new x2((ClassToInstanceMap<d>)this.B.build(), this.g.build());
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\bj.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */