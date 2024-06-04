package org.spongycastle.math.ec;

import java.math.BigInteger;

public class MontgomeryLadderMultiplier
  extends AbstractECMultiplier
{
  public MontgomeryLadderMultiplier() {}
  
  protected ECPoint multiplyPositive(ECPoint p, BigInteger k)
  {
    ECPoint[] R = { p.getCurve().getInfinity(), p };
    
    int n = k.bitLength();
    int i = n;
    for (;;) { i--; if (i < 0)
        break;
      int b = k.testBit(i) ? 1 : 0;
      int bp = 1 - b;
      R[bp] = R[bp].add(R[b]);
      R[b] = R[b].twice();
    }
    return R[0];
  }
}
