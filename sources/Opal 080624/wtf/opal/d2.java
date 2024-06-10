package wtf.opal;

import java.lang.invoke.MethodHandles;

public final class d2 extends dx {
  private final float n;
  
  private static final long c = on.a(962607030392885465L, 1477700001361187535L, MethodHandles.lookup().lookupClass()).a(258583284528957L);
  
  public d2(short paramShort, int paramInt1, double paramDouble, char paramChar, int paramInt2, float paramFloat, lp paramlp) {
    super(paramInt1, paramDouble, paramlp, (byte)i, j, k);
    this.n = paramFloat;
  }
  
  protected boolean E(Object[] paramArrayOfObject) {
    return true;
  }
  
  protected double s(Object[] paramArrayOfObject) {
    double d = ((Double)paramArrayOfObject[0]).doubleValue();
    long l = ((Long)paramArrayOfObject[1]).longValue();
    int[] arrayOfInt = dk.P();
    try {
      if (l >= 0L && arrayOfInt != null)
        d.p(new d[3]); 
    } catch (x5 x5) {
      throw a(null);
    } 
    return Math.max(0.0D, 1.0D + (this.n + 1.0F) * Math.pow(d - 1.0D, 3.0D) + this.n * Math.pow(d - 1.0D, 2.0D));
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\d2.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */