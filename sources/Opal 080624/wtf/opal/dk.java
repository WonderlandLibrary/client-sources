package wtf.opal;

import java.lang.invoke.MethodHandles;

public final class dk extends dx {
  private static int[] N;
  
  private static final long c = on.a(-7743410890850289041L, -798633277234268703L, MethodHandles.lookup().lookupClass()).a(278029905378881L);
  
  public dk(int paramInt1, int paramInt2, double paramDouble, char paramChar, int paramInt3, lp paramlp) {
    super(paramInt1, paramDouble, paramlp, (byte)i, j, k);
  }
  
  protected double s(Object[] paramArrayOfObject) {
    double d = ((Double)paramArrayOfObject[0]).doubleValue();
    long l = ((Long)paramArrayOfObject[1]).longValue();
    return 1.0D - (d - 1.0D) * (d - 1.0D);
  }
  
  public static void O(int[] paramArrayOfint) {
    N = paramArrayOfint;
  }
  
  public static int[] P() {
    return N;
  }
  
  static {
    if (P() != null)
      O(new int[5]); 
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\dk.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */