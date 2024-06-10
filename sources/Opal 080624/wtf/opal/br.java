package wtf.opal;

import java.lang.invoke.MethodHandles;

public final class br {
  private final int D;
  
  private final int s;
  
  private final int z;
  
  private final int w;
  
  private final int C;
  
  private final int d;
  
  private final long n;
  
  private static String[] J;
  
  private static final long a = on.a(-1058980410738988954L, 3487414399211688758L, MethodHandles.lookup().lookupClass()).a(29552324520157L);
  
  public br(int paramInt1, long paramLong, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
    this.D = paramInt1;
    this.s = paramInt2;
    String[] arrayOfString = h();
    try {
      this.z = paramInt3;
      this.w = paramInt4;
      this.C = paramInt5;
      this.d = paramInt6;
      this.n = System.currentTimeMillis();
      if (arrayOfString == null)
        d.p(new d[3]); 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  public long F(Object[] paramArrayOfObject) {
    return this.n;
  }
  
  public int A(Object[] paramArrayOfObject) {
    return this.D;
  }
  
  public int B(Object[] paramArrayOfObject) {
    return this.s;
  }
  
  public int b(Object[] paramArrayOfObject) {
    return this.z;
  }
  
  public int J(Object[] paramArrayOfObject) {
    return this.w;
  }
  
  public int o(Object[] paramArrayOfObject) {
    return this.C;
  }
  
  public int y(Object[] paramArrayOfObject) {
    return this.d;
  }
  
  public static void c(String[] paramArrayOfString) {
    J = paramArrayOfString;
  }
  
  public static String[] h() {
    return J;
  }
  
  static {
    if (h() == null)
      c(new String[5]); 
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\br.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */