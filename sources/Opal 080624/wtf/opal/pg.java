package wtf.opal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class pg {
  private static final ExecutorService N = Executors.newSingleThreadExecutor();
  
  private static int[] w;
  
  static {
    c(null);
  }
  
  public static void c(int[] paramArrayOfint) {
    w = paramArrayOfint;
  }
  
  public static int[] X() {
    return w;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\pg.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */