package org.spongycastle.crypto.modes.gcm;

import org.spongycastle.util.Arrays;

public class BasicGCMExponentiator implements GCMExponentiator {
  private int[] x;
  
  public BasicGCMExponentiator() {}
  
  public void init(byte[] x) {
    this.x = GCMUtil.asInts(x);
  }
  

  public void exponentiateX(long pow, byte[] output)
  {
    int[] y = GCMUtil.oneAsInts();
    
    if (pow > 0L)
    {
      int[] powX = Arrays.clone(x);
      do
      {
        if ((pow & 1L) != 0L)
        {
          GCMUtil.multiply(y, powX);
        }
        GCMUtil.multiply(powX, powX);
        pow >>>= 1;
      }
      while (pow > 0L);
    }
    
    GCMUtil.asBytes(y, output);
  }
}
