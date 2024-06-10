package wtf.opal;

public class lc {
  private static boolean D;
  
  public static void m(boolean paramBoolean) {
    D = paramBoolean;
  }
  
  public static boolean j() {
    return D;
  }
  
  public static boolean Y() {
    boolean bool = j();
    try {
      if (!bool)
        return true; 
    } catch (x5 x5) {
      throw a(null);
    } 
    return false;
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
  
  static {
    if (j())
      m(true); 
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\lc.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */