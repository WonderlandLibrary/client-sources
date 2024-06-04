package org.spongycastle.math.ec;

import java.math.BigInteger;

public class NafL2RMultiplier
  extends AbstractECMultiplier
{
  public NafL2RMultiplier() {}
  
  protected ECPoint multiplyPositive(ECPoint p, BigInteger k)
  {
    int[] naf = WNafUtil.generateCompactNaf(k);
    
    ECPoint addP = p.normalize();ECPoint subP = addP.negate();
    
    ECPoint R = p.getCurve().getInfinity();
    
    int i = naf.length;
    for (;;) { i--; if (i < 0)
        break;
      int ni = naf[i];
      int digit = ni >> 16;int zeroes = ni & 0xFFFF;
      
      R = R.twicePlus(digit < 0 ? subP : addP);
      R = R.timesPow2(zeroes);
    }
    
    return R;
  }
}
