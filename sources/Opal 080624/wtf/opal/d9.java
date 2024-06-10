package wtf.opal;

import java.lang.invoke.MethodHandles;

public final class d9 extends dx {
  private static final long c = on.a(939139312757355118L, -6618055009930351704L, MethodHandles.lookup().lookupClass()).a(164360393059618L);
  
  public d9(int paramInt, double paramDouble, lp paramlp, long paramLong) {
    super(paramInt, paramDouble, paramlp, (byte)i, j, k);
  }
  
  protected double s(Object[] paramArrayOfObject) {
    double d = ((Double)paramArrayOfObject[0]).doubleValue();
    long l = ((Long)paramArrayOfObject[1]).longValue();
    int[] arrayOfInt = dk.P();
    try {
      if (arrayOfInt == null) {
        try {
          if (d < 0.5D) {
          
          } else {
          
          } 
        } catch (x5 x5) {
          throw a(null);
        } 
        return 1.0D - Math.pow(-2.0D * d + 2.0D, 2.0D) / 2.0D;
      } 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\d9.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */