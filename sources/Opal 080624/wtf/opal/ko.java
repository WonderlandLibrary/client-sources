package wtf.opal;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.class_1657;

public final class ko {
  private final List<dn> l = new ArrayList<>();
  
  private final Map<class_1657, lv> S = new ConcurrentHashMap<>();
  
  private static final long a = on.a(5009084692972581806L, -4224109417130551089L, MethodHandles.lookup().lookupClass()).a(84548197392705L);
  
  public ko(short paramShort, long paramLong, dn... paramVarArgs) {
    this.l.addAll(Arrays.asList(paramVarArgs));
    d[] arrayOfD = dn.i();
    try {
      if (arrayOfD != null)
        d.p(new d[2]); 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  public List b(Object[] paramArrayOfObject) {
    return this.l;
  }
  
  public Map W(Object[] paramArrayOfObject) {
    return this.S;
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\ko.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */