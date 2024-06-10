package wtf.opal;

public class lr {
  private boolean F;
  
  private static boolean J;
  
  public final void Z(Object[] paramArrayOfObject) {
    this.F = true;
  }
  
  public final boolean X(Object[] paramArrayOfObject) {
    return this.F;
  }
  
  public static void V(boolean paramBoolean) {
    J = paramBoolean;
  }
  
  public static boolean y() {
    return J;
  }
  
  public static boolean V() {
    boolean bool = y();
    try {
      if (!bool)
        return true; 
    } catch (x5 x5) {
      throw b(null);
    } 
    return false;
  }
  
  private static x5 b(x5 paramx5) {
    return paramx5;
  }
  
  static {
    if (!y())
      V(true); 
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\lr.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */