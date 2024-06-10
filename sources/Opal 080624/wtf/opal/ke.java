package wtf.opal;

import java.lang.invoke.MethodHandles;

public final class ke extends k3<Boolean> {
  private static d[] U;
  
  private static final long b = on.a(-1378328993213654819L, 6322903022187470377L, MethodHandles.lookup().lookupClass()).a(68419395258843L);
  
  public ke(String paramString, boolean paramBoolean) {
    super(paramString);
    V(new Object[] { Boolean.valueOf(paramBoolean) });
  }
  
  public ke(long paramLong, String paramString, u_ paramu_, boolean paramBoolean) {
    super(l, paramString, paramu_);
    V(new Object[] { Boolean.valueOf(paramBoolean) });
  }
  
  public void x(Object[] paramArrayOfObject) {
    long l = ((Long)paramArrayOfObject[0]).longValue();
    l = b ^ l;
    d[] arrayOfD = s();
    try {
      if (arrayOfD == null)
        if (!z().booleanValue()) {
        
        } else {
          V(new Object[] { Boolean.valueOf(false) });
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  public static void D(d[] paramArrayOfd) {
    U = paramArrayOfd;
  }
  
  public static d[] s() {
    return U;
  }
  
  static {
    if (s() != null)
      D(new d[2]); 
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\ke.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */