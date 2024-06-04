package org.spongycastle.math.ec;

import java.math.BigInteger;










public class WNafL2RMultiplier
  extends AbstractECMultiplier
{
  public WNafL2RMultiplier() {}
  
  protected ECPoint multiplyPositive(ECPoint p, BigInteger k)
  {
    int width = Math.max(2, Math.min(16, getWindowSize(k.bitLength())));
    
    WNafPreCompInfo wnafPreCompInfo = WNafUtil.precompute(p, width, true);
    ECPoint[] preComp = wnafPreCompInfo.getPreComp();
    ECPoint[] preCompNeg = wnafPreCompInfo.getPreCompNeg();
    
    int[] wnaf = WNafUtil.generateCompactWindowNaf(width, k);
    
    ECPoint R = p.getCurve().getInfinity();
    
    int i = wnaf.length;
    




    if (i > 1)
    {
      int wi = wnaf[(--i)];
      int digit = wi >> 16;int zeroes = wi & 0xFFFF;
      
      int n = Math.abs(digit);
      ECPoint[] table = digit < 0 ? preCompNeg : preComp;
      

      if (n << 2 < 1 << width)
      {
        int highest = LongArray.bitLengths[n];
        

        int scale = width - highest;
        int lowBits = n ^ 1 << highest - 1;
        
        int i1 = (1 << width - 1) - 1;
        int i2 = (lowBits << scale) + 1;
        R = table[(i1 >>> 1)].add(table[(i2 >>> 1)]);
        
        zeroes -= scale;

      }
      else
      {

        R = table[(n >>> 1)];
      }
      
      R = R.timesPow2(zeroes);
    }
    
    while (i > 0)
    {
      int wi = wnaf[(--i)];
      int digit = wi >> 16;int zeroes = wi & 0xFFFF;
      
      int n = Math.abs(digit);
      ECPoint[] table = digit < 0 ? preCompNeg : preComp;
      ECPoint r = table[(n >>> 1)];
      
      R = R.twicePlus(r);
      R = R.timesPow2(zeroes);
    }
    
    return R;
  }
  






  protected int getWindowSize(int bits)
  {
    return WNafUtil.getWindowSize(bits);
  }
}
