package wtf.opal;

import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

class k_ extends Thread {
  private static final long a = on.a(-4756181778877476823L, -7696459042806926143L, MethodHandles.lookup().lookupClass()).a(126833174706450L);
  
  private static final long b;
  
  k_(b2 paramb2, String paramString) {
    super(paramString);
  }
  
  public void run() {
    long l = a ^ 0x393093E41DD9L;
    try {
      sleep(b);
      ds.b = false;
    } catch (InterruptedException interruptedException) {
      interruptedException.printStackTrace();
      ds.b = false;
    } 
  }
  
  static {
    long l = a ^ 0x4D2C87306ACEL;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b = 1; b < 8; b++)
      (new byte[8])[b] = (byte)(int)(l << b * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/NoPadding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\k_.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */