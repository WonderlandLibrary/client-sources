package wtf.opal;

import java.lang.invoke.MethodHandles;

class bo extends Thread {
  final double e;
  
  final String B;
  
  private static final long a = on.a(2446686889262286268L, -8813797031789782282L, MethodHandles.lookup().lookupClass()).a(108141891049196L);
  
  bo(jx paramjx, String paramString1, double paramDouble, String paramString2) {
    super(paramString1);
  }
  
  public void run() {
    long l = a ^ 0x17C11DFD071AL;
    String str = j2.z();
    try {
      try {
        sleep((long)(this.e * 1000.0D));
        if (str == null)
          try {
            if (b9.c.method_1562() != null) {
            
            } else {
              return;
            } 
          } catch (InterruptedException interruptedException) {
            throw a(null);
          }  
      } catch (InterruptedException interruptedException) {
        throw a(null);
      } 
      b9.c.method_1562().method_45729(this.B);
    } catch (InterruptedException interruptedException) {
      interruptedException.printStackTrace();
    } 
  }
  
  private static InterruptedException a(InterruptedException paramInterruptedException) {
    return paramInterruptedException;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\bo.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */