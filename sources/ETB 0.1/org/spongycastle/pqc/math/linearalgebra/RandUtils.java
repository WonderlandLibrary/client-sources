package org.spongycastle.pqc.math.linearalgebra;

import java.security.SecureRandom;

public class RandUtils
{
  public RandUtils() {}
  
  static int nextInt(SecureRandom rand, int n) {
    if ((n & -n) == n)
    {
      return (int)(n * (rand.nextInt() >>> 1) >> 31);
    }
    int bits;
    int value;
    do
    {
      bits = rand.nextInt() >>> 1;
      value = bits % n;
    }
    while (bits - value + (n - 1) < 0);
    
    return value;
  }
}
