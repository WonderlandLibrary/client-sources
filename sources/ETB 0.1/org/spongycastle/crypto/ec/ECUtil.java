package org.spongycastle.crypto.ec;

import java.math.BigInteger;
import java.security.SecureRandom;

class ECUtil
{
  ECUtil() {}
  
  static BigInteger generateK(BigInteger n, SecureRandom random)
  {
    int nBitLength = n.bitLength();
    BigInteger k;
    do
    {
      k = new BigInteger(nBitLength, random);
    }
    while ((k.equals(org.spongycastle.math.ec.ECConstants.ZERO)) || (k.compareTo(n) >= 0));
    return k;
  }
}
