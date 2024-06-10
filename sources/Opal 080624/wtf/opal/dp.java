package wtf.opal;

import java.lang.invoke.MethodHandles;

public final class dp extends dx {
  private static final long c = on.a(-2617239015182492521L, -826794053010852187L, MethodHandles.lookup().lookupClass()).a(263355717019713L);
  
  public dp(long paramLong, int paramInt, double paramDouble, lp paramlp) {
    super(paramInt, paramDouble, paramlp, (byte)i, j, k);
  }
  
  protected double s(Object[] paramArrayOfObject) {
    double d = ((Double)paramArrayOfObject[0]).doubleValue();
    long l = ((Long)paramArrayOfObject[1]).longValue();
    int[] arrayOfInt = dk.P();
    try {
      if (l > 0L && d.D() != null)
        dk.O(new int[1]); 
    } catch (x5 x5) {
      throw a(null);
    } 
    return -2.0D * Math.pow(d, 3.0D) + 3.0D * Math.pow(d, 2.0D);
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\dp.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */