package wtf.opal;

import java.lang.invoke.MethodHandles;

public final class py {
  private final kr B = new kr();
  
  private final kj S;
  
  private final String W;
  
  private final String e;
  
  private final int n;
  
  private static final long a = on.a(-5402996344133089660L, -4570473194362705935L, MethodHandles.lookup().lookupClass()).a(3920013194255L);
  
  py(kj paramkj, String paramString1, String paramString2, long paramLong, int paramInt) {
    this.S = paramkj;
    this.W = paramString1;
    this.e = paramString2;
    d[] arrayOfD = xt.P();
    try {
      this.n = paramInt;
      if (arrayOfD != null)
        d.p(new d[3]); 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  public kj C(Object[] paramArrayOfObject) {
    return this.S;
  }
  
  public String u(Object[] paramArrayOfObject) {
    return this.W;
  }
  
  public String Y(Object[] paramArrayOfObject) {
    return this.e;
  }
  
  public int Q(Object[] paramArrayOfObject) {
    return this.n;
  }
  
  public boolean r(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x25CA65ABC946L;
    (new Object[3])[2] = Boolean.valueOf(false);
    new Object[3];
    (new Object[3])[1] = Long.valueOf(l2);
    new Object[3];
    return this.B.v(new Object[] { Long.valueOf(this.n) });
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\py.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */