package wtf.opal;

import java.lang.invoke.MethodHandles;

public final class kt extends k3<Double> {
  private final double I;
  
  private final double Z;
  
  private final double b;
  
  private boolean h;
  
  private static final long c = on.a(-6714342864746823505L, 2389215532098012632L, MethodHandles.lookup().lookupClass()).a(31826120500542L);
  
  public kt(String paramString, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
    super(paramString);
    V(new Object[] { Double.valueOf(paramDouble1) });
    this.I = paramDouble2;
    this.Z = paramDouble3;
    this.b = paramDouble4;
  }
  
  public kt(String paramString, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, boolean paramBoolean) {
    super(paramString);
    V(new Object[] { Double.valueOf(paramDouble1) });
    this.I = paramDouble2;
    this.Z = paramDouble3;
    this.b = paramDouble4;
    this.h = paramBoolean;
  }
  
  public kt(String paramString, u_ paramu_, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, long paramLong) {
    super(l, paramString, paramu_);
    V(new Object[] { Double.valueOf(paramDouble1) });
    this.I = paramDouble2;
    this.Z = paramDouble3;
    this.b = paramDouble4;
  }
  
  public kt(short paramShort, String paramString, u_ paramu_, double paramDouble1, double paramDouble2, double paramDouble3, int paramInt, double paramDouble4, char paramChar, boolean paramBoolean) {
    super(l2, paramString, paramu_);
    d[] arrayOfD = ke.s();
    try {
      V(new Object[] { Double.valueOf(paramDouble1) });
      this.I = paramDouble2;
      this.Z = paramDouble3;
      this.b = paramDouble4;
      this.h = paramBoolean;
      if (d.D() != null)
        ke.D(new d[4]); 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  public boolean f(Object[] paramArrayOfObject) {
    return this.h;
  }
  
  public double O(Object[] paramArrayOfObject) {
    return this.I;
  }
  
  public double T(Object[] paramArrayOfObject) {
    return this.Z;
  }
  
  public double w(Object[] paramArrayOfObject) {
    return this.b;
  }
  
  public void r(Object[] paramArrayOfObject) {
    long l = ((Long)paramArrayOfObject[0]).longValue();
    double d = ((Double)paramArrayOfObject[1]).doubleValue();
    l = c ^ l;
    new Object[3];
    (new Object[3])[2] = Double.valueOf(this.Z);
    new Object[3];
    (new Object[3])[1] = Double.valueOf(this.I);
    new Object[3];
    d = d6.M(new Object[] { Double.valueOf(d) });
    d = Math.round(d * 1.0D / this.b) / 1.0D / this.b;
    d[] arrayOfD = ke.s();
    try {
      V(new Object[] { Double.valueOf(d) });
      if (arrayOfD != null)
        d.p(new d[4]); 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\kt.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */