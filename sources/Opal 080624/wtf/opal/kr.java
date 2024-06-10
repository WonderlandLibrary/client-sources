package wtf.opal;

import java.lang.invoke.MethodHandles;

public final class kr {
  private long j = System.currentTimeMillis();
  
  private static String D;
  
  private static final long a = on.a(-4516199201276038875L, 1270142592751860466L, MethodHandles.lookup().lookupClass()).a(221564828689342L);
  
  public void z(Object[] paramArrayOfObject) {
    this.j = System.currentTimeMillis();
  }
  
  public boolean v(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    long l2 = ((Long)paramArrayOfObject[1]).longValue();
    boolean bool = ((Boolean)paramArrayOfObject[2]).booleanValue();
    l2 = a ^ l2;
    String str = F();
    try {
      if (str == null)
        try {
          if (System.currentTimeMillis() - this.j > l1) {
            try {
              if (str == null)
                try {
                  if (bool)
                    z(new Object[0]); 
                } catch (x5 x5) {
                  throw a(null);
                }  
            } catch (x5 x5) {
              throw a(null);
            } 
            return true;
          } 
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    return false;
  }
  
  public long E(Object[] paramArrayOfObject) {
    return System.currentTimeMillis() - this.j;
  }
  
  public void d(Object[] paramArrayOfObject) {
    long l = ((Long)paramArrayOfObject[0]).longValue();
    this.j = l;
  }
  
  public static void R(String paramString) {
    D = paramString;
  }
  
  public static String F() {
    return D;
  }
  
  static {
    if (F() != null)
      R("Kavqt"); 
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\kr.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */