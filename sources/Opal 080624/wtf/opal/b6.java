package wtf.opal;

public final class b6 {
  private boolean k = true;
  
  private static int m;
  
  public void P(Object[] paramArrayOfObject) {
    boolean bool = ((Boolean)paramArrayOfObject[0]).booleanValue();
    this.k = bool;
  }
  
  public boolean W(Object[] paramArrayOfObject) {
    return this.k;
  }
  
  public static void J(int paramInt) {
    m = paramInt;
  }
  
  public static int k() {
    return m;
  }
  
  public static int r() {
    int i = k();
    try {
      if (i == 0)
        return 95; 
    } catch (x5 x5) {
      throw a(null);
    } 
    return 0;
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
  
  static {
    if (r() != 0)
      J(100); 
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\b6.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */