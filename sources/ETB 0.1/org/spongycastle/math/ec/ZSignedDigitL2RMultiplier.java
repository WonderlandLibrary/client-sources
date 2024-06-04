package org.spongycastle.math.ec;

import java.math.BigInteger;

public class ZSignedDigitL2RMultiplier
  extends AbstractECMultiplier
{
  public ZSignedDigitL2RMultiplier() {}
  
  protected ECPoint multiplyPositive(ECPoint p, BigInteger k)
  {
    ECPoint addP = p.normalize();ECPoint subP = addP.negate();
    
    ECPoint R0 = addP;
    
    int n = k.bitLength();
    int s = k.getLowestSetBit();
    
    int i = n;
    for (;;) { i--; if (i <= s)
        break;
      R0 = R0.twicePlus(k.testBit(i) ? addP : subP);
    }
    
    R0 = R0.timesPow2(s);
    
    return R0;
  }
}
